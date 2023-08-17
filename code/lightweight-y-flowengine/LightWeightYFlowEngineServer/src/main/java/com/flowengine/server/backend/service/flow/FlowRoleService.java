package com.flowengine.server.backend.service.flow;

import com.flowengine.common.utils.entity.PublicFlowRoleEntity;
import com.flowengine.common.utils.entity.PublicFlowRoleUserGrantEntity;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
public interface FlowRoleService {

    /**
     *
     * @return
     */
    public String getCombobox();

    /**
     * 删除
     */
    public String delete(String opId);

    /**
     * 编辑
     * @param roleName
     * @param roleType
     * @param persons
     * @return
     */
    public String edit(String opId, String roleName, Integer roleType, String[] persons);

    /**
     *
     * @return
     */
    public String add(PublicFlowRoleEntity flowRoleEntity, List<PublicFlowRoleUserGrantEntity> flowRoleUserGrantEntities);

    /**
     * 获取role_type下拉框
     * @return
     */
    public String getRoleTypeCombobox();

    /**
     * 查询角色
     * @param param
     * @return
     */
    public String query(Map<String, Object> param);
}
