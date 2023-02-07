package com.flowengine.server.backend.dao.impl;


import com.flowengine.common.utils.CommonConstant;
import com.flowengine.server.backend.dao.MenuDao;
import com.flowengine.server.core.BaseDao;
import com.flowengine.server.model.MenuVO;
import com.flowengine.server.model.UserCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
