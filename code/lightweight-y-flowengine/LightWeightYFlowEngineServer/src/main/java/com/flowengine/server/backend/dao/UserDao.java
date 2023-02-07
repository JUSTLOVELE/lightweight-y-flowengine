package com.flowengine.server.backend.dao;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2022.08.24
 * @version 1.00.00
 * @Description:
 * @history:
 */
public interface UserDao {

    /**
     * 查询用户id是否存在
     * @param userId
     * @return true:存在
     * 		   false:不存在
     */
    public boolean isExitUserId(String userId);

    /**
     * 绑定成功
     * @param userOpId
     * @param roleOpId
     * @return
     */
    public void bandingRole(String userOpId, String roleOpId);

    /**
     *
     * @param opId
     */
    public void deleteUser(String opId);

    /**
     * 查询用户总数
     * @param param
     * @return
     */
    public Integer queryTotal(Map<String, Object> param);

    /**
     * 查询用户
     * @param param
     * @return
     */
    public List<Map<String, Object>> query(Map<String, Object> param);

    /**
     * 根据userOpId查询角色id
     * @param userOpId
     * @return
     */
    public List<Map<String, Object>> queryRoleId(String userOpId);

    /**
     * 查询非登录的普通用户
     * @param param
     * @return
     */
    public List<Map<String, Object>> queryCommonUser(Map<String, Object> param);

    /**
     * 查询非登录的普通用户的数量
     * @param param
     * @return
     */
    public int queryCommonUserTotal(Map<String, Object> param);
}
