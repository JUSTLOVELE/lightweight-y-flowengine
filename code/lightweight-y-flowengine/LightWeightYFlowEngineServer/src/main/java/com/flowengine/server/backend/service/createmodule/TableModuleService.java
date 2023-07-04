package com.flowengine.server.backend.service.createmodule;

import com.flowengine.common.utils.entity.PublicFlowTableModuleEntity;

import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023-06-30
 * @version 1.00.00
 * @history:
 */
public interface TableModuleService {

    /**
     * 删除
     * @param opId
     * @return
     */
    public String delete(String opId);

    /**
     * 编辑表模块
     * @param entity
     * @return
     */
    public String edit(PublicFlowTableModuleEntity entity);

    /**
     * 新增表模块
     * @param entity
     * @return
     */
    public String add(PublicFlowTableModuleEntity entity);

    /**
     * 查询
     * @param param
     * @return
     */
    public String query(Map<String, Object> param);
}
