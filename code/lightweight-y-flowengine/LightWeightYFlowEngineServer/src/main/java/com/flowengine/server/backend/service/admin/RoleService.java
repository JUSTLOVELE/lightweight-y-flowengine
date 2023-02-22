package com.flowengine.server.backend.service.admin;


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
public interface RoleService {

    /**
     * 编辑角色
     * @param param
     * @return
     */
    public String edit(Map<String, Object> param);

    /**
     * 删除角色
     * @param opId
     * @return
     */
    public String delete(String opId);

    /**
     * 新增角色
     * @param param
     * @return
     */
    public String add(Map<String, Object> param);

    /**
     * 查询角色
     * @param name
     * @param limit
     * @param page
     * @return
     */
    public String query(String name, Integer limit, Integer page, UserCache userCache);

    /**
     * 创建菜单树
     * @param opId 当前登录用户id
     * @param arrays 所选角色菜单ids
     * @return
     */
    public RoleTreeVO createMyRoleTree(String opId, List<String> arrays);

    /**
     * 通过角色id查找菜单id
     * @param roleId
     * @return
     */
    public List<String> findMenuIdByRoleId(String roleId);

    /**
     * 获取下拉框值
     * @return
     */
    public String getCombox(String orgId);
}
