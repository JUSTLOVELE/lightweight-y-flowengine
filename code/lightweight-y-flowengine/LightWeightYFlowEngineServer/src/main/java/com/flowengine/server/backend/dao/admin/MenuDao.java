package com.flowengine.server.backend.dao.admin;


import com.flowengine.server.model.MenuVO;
import com.flowengine.server.model.UserCache;

/**
 * @Description:
 * @author yangzl 2019-12-03
 * @version 1.00.00
 * @history:
 */
public interface MenuDao {

	/**
	 * 同步树
	 * @param user
	 * @return
	 */
	public MenuVO createMenuTree(UserCache user);
}
