package com.flowengine.server.backend.service.admin;


import com.flowengine.common.utils.entity.PublicMenuEntity;
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
	 * 删除
	 * @param opId
	 * @return
	 */
	public String delete(String opId);

	/**
	 * 编辑
	 * @param menuEntity
	 * @return
	 */
	public String edit(PublicMenuEntity menuEntity);

	/**
	 * 新增
	 * @param menuEntity
	 * @return
	 */
	public String add(PublicMenuEntity menuEntity);

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
