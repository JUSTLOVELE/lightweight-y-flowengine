package com.flowengine.server.backend.dao.admin;


import com.flowengine.server.model.MenuVO;
import com.flowengine.server.model.UserCache;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2019-12-03
 * @version 1.00.00
 * @history:
 */
public interface MenuDao {

	/**
	 * 页面查询总数
	 * @param param
	 * @return
	 */
	public  int queryTotal(Map<String, Object> param);

	/**
	 * 页面查询
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> query(Map<String, Object> param);

	/**
	 * 同步树
	 * @param user
	 * @return
	 */
	public MenuVO createMenuTree(UserCache user);
}
