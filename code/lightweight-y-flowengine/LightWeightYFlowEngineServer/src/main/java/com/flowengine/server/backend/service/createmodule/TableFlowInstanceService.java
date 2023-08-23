package com.flowengine.server.backend.service.createmodule;

/**
 * @author yangzl 2023/8/23
 * @version 1.00.00
 * @Description:
 * @history:
 */
public interface TableFlowInstanceService {

    /**
     * 根据tableOpId删除对应的表
     * @param tableOpId
     */
    public void dropTableByTableOpId(String tableOpId);

    /**
     * 创建流程、流程实例等库表
     * @param tableId
     * @param tableName
     */
    public void createFlowAndFlowInstance(String tableId, String tableName);
}
