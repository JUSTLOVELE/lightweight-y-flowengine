package com.flowengine.server.backend.service;


import com.flowengine.server.model.MenuVO;
import com.flowengine.server.model.UserCache;

/**
 * @Description:
 * @author yangzl 2019-12-03
 * @version 1.00.00
 * @history:
 */
public interface MenuService {

	public MenuVO createMenuTree(UserCache user);
}
