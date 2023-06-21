package com.flowengine.server.backend.service.admin;


import com.flowengine.server.model.MenuVO;
import com.flowengine.server.model.UserCache;

import java.util.Map;

/**
 * @Description:
 * @author yangzl 2019-12-03
 * @version 1.00.00
 * @history:
 */
public interface MenuService {


	/**
	 * 页面查询
	 * @param param
	 * @return
	 */
	public String query(Map<String, Object> param);

	/**
	 * 创建菜单树
	 * @param user
	 * @return
	 */
	public MenuVO createMenuTree(UserCache user);
}
