package com.flowengine.server.backend.dao.createmodule;

/**
 * @author yangzl 2023/8/23
 * @version 1.00.00
 * @Description:
 * @history:
 */
public interface TableFlowInstanceDao {

    /**
     * 根据表名查询其总数
     * @param tableName
     * @return
     */
    public int queryCountByTableName(String tableName);
}
