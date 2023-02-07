package com.flowengine.server.backend.service;



import com.flowengine.common.utils.entity.PublicUserEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2021-10-28
 * @version 1.00.00
 * @history:
 */
public interface UserService {

    /**
     * 获取下拉框
     * @return
     */
    public String getUserCombox();

    /**
     * 编辑个人信息
     * @param param
     * @return
     */
    public String editSelf(HttpServletRequest request, Map<String, Object> param);

    /**
     * 编辑
     * @param param
     * @return
     */
    public String edit(Map<String, Object> param);

    /**
     * 新增
     * @param param
     * @return
     */
    public String add(Map<String, Object> param);

    /**
     * 删除用户
     * @param opId
     * @return
     */
    public String delete(String opId);

    /**
     * 查询用户(业主单位用户)
     * @param param
     * @return
     */
    public String query(Map<String, Object> param);

    /**
     * 根据userOpId查询角色id
     * @param userOpId
     * @return
     */
    public List<Map<String, Object>> queryRoleId(String userOpId);

    /**
     * 登录页登录使用
     * @param userId
     * @param password
     * @return
     */
    public PublicUserEntity getPublicUserEntity(String userId, String password);
}


