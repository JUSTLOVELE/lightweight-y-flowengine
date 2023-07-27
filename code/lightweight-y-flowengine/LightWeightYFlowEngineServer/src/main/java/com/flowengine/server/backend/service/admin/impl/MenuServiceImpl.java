package com.flowengine.server.backend.service.admin.impl;


import cn.hutool.core.util.StrUtil;
import com.flowengine.common.utils.entity.PublicMenuEntity;
import com.flowengine.common.utils.mapper.PublicMenuMapper;
import com.flowengine.server.backend.dao.admin.MenuDao;
import com.flowengine.server.backend.service.admin.MenuService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.model.MenuVO;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2019-12-03
 * @version 1.00.00
 * @history:
 */
@Service(value = "menuServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseService implements MenuService {

	@Autowired
	private MenuDao _menuDao;

	@Resource
	private PublicMenuMapper _publicMenuMapper;

	@Override
	public String delete(String opId) {
		//需要没有叶子节点
		int count = _publicMenuMapper.queryCountWithParentId(opId);

		if(count > 0) {
			return renderPrintFailureList("该节点下存在子节点,请删除所有的子节点!");
		}

		//删除管理员引用
		_publicMenuMapper.deleteRoleMenu(opId, Constant.ROOT_ROLE_ID);
		//最后才删除
		_publicMenuMapper.deleteById(opId);

		return renderDeleteSuccessList(1);
	}

	@Override
	public String edit(PublicMenuEntity menuEntity) {

		PublicMenuEntity publicMenuEntity = _publicMenuMapper.selectById(menuEntity.getOpId());

		if(!publicMenuEntity.getParentId().equals(menuEntity.getParentId())) {
			publicMenuEntity.setParentId(menuEntity.getParentId());
		}

		if(StrUtil.isEmpty(publicMenuEntity.getUrl()) && StrUtil.isNotEmpty(menuEntity.getUrl())) {
			publicMenuEntity.setUrl(menuEntity.getUrl());
		}else if(StrUtil.isNotEmpty(publicMenuEntity.getUrl()) && StrUtil.isEmpty(menuEntity.getUrl())) {
			publicMenuEntity.setUrl(null);
		}else if(StrUtil.isNotEmpty(publicMenuEntity.getUrl()) && StrUtil.isNotEmpty(menuEntity.getUrl()) && !publicMenuEntity.getUrl().equals(menuEntity.getUrl())) {
			publicMenuEntity.setUrl(menuEntity.getUrl());
		}

		if(!publicMenuEntity.getText().equals(menuEntity.getText())) {
			publicMenuEntity.setText(menuEntity.getText());
		}

		if(publicMenuEntity.getType() != menuEntity.getType()) {
			publicMenuEntity.setType(menuEntity.getType());
		}

		if(publicMenuEntity.getAvailableFlag() != menuEntity.getAvailableFlag()) {
			publicMenuEntity.setAvailableFlag(menuEntity.getAvailableFlag());
		}

		if(publicMenuEntity.getSort() != menuEntity.getSort()) {
			publicMenuEntity.setSort(menuEntity.getSort());
		}

		_publicMenuMapper.updateById(publicMenuEntity);

		return renderOpSuccessList(1);
	}

	@Override
	public String add(PublicMenuEntity menuEntity) {

		_publicMenuMapper.insert(menuEntity);
		_publicMenuMapper.insertRoleMenu(menuEntity.getOpId(), Constant.ROOT_ROLE_ID);//新增权限后默认管理员要有
		return renderOpSuccessList(1);
	}

	@Override
	public String query(Map<String, Object> param) {

		List<Map<String, Object>> datas = _menuDao.query(param);

		if(datas != null && datas.size() > 0) {

			return renderQuerySuccessList(_menuDao.queryTotal(param), datas);
		}else  {
			return renderQuerySuccessList(0);
		}
	}

	@Override
	public MenuVO createMenuTree(UserCache user) {
		return _menuDao.createMenuTree(user);
	}

}
