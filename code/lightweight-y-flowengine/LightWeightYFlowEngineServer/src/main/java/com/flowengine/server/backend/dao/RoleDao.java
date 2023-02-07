package com.flowengine.server.backend.dao;


import com.flowengine.server.model.RoleTreeVO;
import com.flowengine.server.model.UserCache;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2022.08.26
 * @version 1.00.00
 * @history:
 */
public interface RoleDao {

    /**
     * 新增角色
     * @param param
     */
    public void addRole(Map<String, Object> param);

    /**
     * 删除
     * @param opId
     */
    public void delete(String opId);

    /**
     * 根据用户id查找角色id
     */
    public List<Map<String, Object>> findRoleIdByUserId(String userId);

    /**
     * 根据角色id查询菜单
     * @param roleIds
     * @return
     */
    public List<RoleTreeVO> findMenusByRoles(String roleIds);

    /**
     * 通过角色id查找菜单id
     */
    public List<Map<String, Object>> findMenuIdByRoleId(String roleId);

    /**
     * 编辑角色菜单
     * @param opId
     * @param menuIds
     */
    public void editRoleMenus(String opId, String[] menuIds);

    /**
     * 为角色新增菜单
     * @param opId
     * @param menuIds
     */
    public void addRoleMenus(String opId, String[] menuIds);

    /**
     * 查询角色总数
     * @param name
     * @return
     */
    public Integer queryTotal(String name, UserCache userCache);

    /**
     * 角色查询
     * @param name
     * @param limit
     * @param page
     * @return
     */
    public List<Map<String, Object>> query(String name, Integer limit, Integer page, UserCache userCache);
}
