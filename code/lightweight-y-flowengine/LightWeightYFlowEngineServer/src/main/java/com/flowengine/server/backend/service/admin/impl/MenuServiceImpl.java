package com.flowengine.server.backend.service.admin.impl;


import com.flowengine.server.backend.dao.admin.MenuDao;
import com.flowengine.server.backend.service.admin.MenuService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.model.MenuVO;
import com.flowengine.server.model.UserCache;
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

	@Override
	public String query(Map<String, Object> param) {

		List<Map<String, Object>> datas = _menuDao.query(param);

		if(datas != null && datas.size() > 1) {

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
