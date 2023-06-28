package com.flowengine.server.backend.dao.admin.impl;


import cn.hutool.core.util.StrUtil;
import com.flowengine.common.utils.CommonConstant;
import com.flowengine.server.backend.dao.admin.MenuDao;
import com.flowengine.server.core.BaseDao;
import com.flowengine.server.model.MenuVO;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2019-12-03
 * @version 1.00.00
 * @history:
 */
@Repository
public class MenuDaoImpl extends BaseDao implements MenuDao {

	private final static Log _logger = LogFactory.getLog(MenuDaoImpl.class);

	@Override
	public int queryTotal(Map<String, Object> param) {

		String opId = (String) param.get(Constant.Key.OP_ID);
		String parentId = (String) param.get(Constant.Key.PARENT_ID);
		String menuText = (String) param.get(Constant.Key.MENU_TEXT);
		List<Object> array = new ArrayList<>();
		String sql = """
				select count(*)  from public_menu a where 1=1 
				""";
		StringBuffer sb = new StringBuffer(sql);

		if(StrUtil.isNotEmpty(opId)) {
			sb.append(" and a.op_id = ? ");
			array.add(opId);
		}

		if(StrUtil.isNotEmpty(parentId)) {
			sb.append(" and a.parent_id = ? ");
			array.add(parentId);
		}

		if(StrUtil.isNotEmpty(menuText)) {
			sb.append(" and a.text like ? ");
			array.add(menuText + "%");
		}

		sql = sb.toString();
		_logger.info(sql);

		return this.getJdbcTemplate().queryForObject(sql, Integer.class, array.toArray());
	}

	@Override
	public List<Map<String, Object>> query(Map<String, Object> param) {

		Integer page = (Integer) param.get(Constant.Key.PAGE);
		Integer limit = (Integer) param.get(Constant.Key.LIMIT);
		String opId = (String) param.get(Constant.Key.OP_ID);
		String parentId = (String) param.get(Constant.Key.PARENT_ID);
		String menuText = (String) param.get(Constant.Key.MENU_TEXT);
		List<Object> array = new ArrayList<>();
		String sql = """
				select
				    a.op_id "opId",
				    a.parent_id "parentId",
				    a.url "url",
				    a.text "text",
				    a.type "type",
				    to_char(a.available_flag, '999') "availableFlag",
				    to_char(a.sort, '999') "sort",
				    a.sys "sys",
				    a.icon "icon",
				    a.category "category"
				    from public_menu a where 1=1 
								
				""";
		StringBuffer sb = new StringBuffer(sql);

		if(StrUtil.isNotEmpty(opId)) {
			sb.append(" and a.op_id = ? ");
			array.add(opId);
		}

		if(StrUtil.isNotEmpty(parentId)) {
			sb.append(" and a.parent_id = ? ");
			array.add(parentId);
		}

		if(StrUtil.isNotEmpty(menuText)) {
			sb.append(" and a.text like ? ");
			array.add(menuText + "%");
		}

		sb.append(" ORDER BY a.sort asc LIMIT ? offset ? ");
		array.add(limit);
		array.add(limit*(page-1));
		sql = sb.toString();
		_logger.info(sql);

		return this.getJdbcTemplate().queryForList(sql, array.toArray());
	}

	@Override
	public MenuVO createMenuTree(UserCache user) {
		
		String sql = "SELECT text, url, op_id \"opId\", parent_id \"parentId\", type, icon  FROM public_menu   WHERE op_id IN (SELECT DISTINCT menu_id FROM public_role_menu_grant WHERE role_id IN(SELECT op_id FROM public_role WHERE op_id IN (SELECT role_id  FROM public_user_role_grant  WHERE user_op_id = ?))) ORDER BY sort ";
		_logger.info(sql);
		RowMapper<MenuVO> rowMapper = new BeanPropertyRowMapper<MenuVO>(MenuVO.class);
		List<MenuVO> menuVOs = this.getJdbcTemplate().query(sql, new Object[] {user.getOpId()}, rowMapper);

		if(menuVOs != null){
			MenuVO menuVO = buildMenuTree(menuVOs);
			menuVOs.clear();
			menuVOs = null;
			return menuVO;
		}else{
			return null;
		}
	}
	
	public MenuVO buildMenuTree(List<MenuVO> menuVOs){
		
		MenuVO mVO = null;
		
		for(MenuVO menuVO : menuVOs){
			
			//寻找到顶级菜单
			if(menuVO.getType() != null && menuVO.getType() == CommonConstant.Menu.ROOT){
				mVO = menuVO;
				break;
			}
		}
		if(mVO != null){
			menuVOs.remove(mVO);
			buildMenuTree(menuVOs, mVO);
		}
		return mVO;
	}
	
	public void buildMenuTree(List<MenuVO> menuVOs, MenuVO menuVO){
		
		String parentId = menuVO.getOpId();
		
		for(MenuVO mVO : menuVOs){
			
			if(mVO.getParentId().equals(parentId)){
				
				if(mVO.getType() == CommonConstant.Menu.LEAF){
					
					mVO.setLeaf(true);
				}
				
				menuVO.getChildren().add(mVO);
				buildMenuTree(menuVOs, mVO);
			}
		}
	}
	

}
