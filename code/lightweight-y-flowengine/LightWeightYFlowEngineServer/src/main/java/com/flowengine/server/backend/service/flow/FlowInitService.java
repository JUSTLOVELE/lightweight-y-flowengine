package com.flowengine.server.backend.service.flow;

import com.flowengine.server.model.flow.model.FlowMainToTableBean;

/**
 * @author yangzl 2023/8/30
 * @version 1.00.00
 * @Description:
 * @history:
 */
public interface FlowInitService {

    /**
     * 从redis中获取流程主表id对应的表数据
     * @param mainOpId
     * @return
     */
    public FlowMainToTableBean getMainToTableDataFromRedis(String mainOpId);

    /**
     * 删除redis中的对应的流程主表数据
     * @param mainOpId
     */
    public void deleteFlowMainDataFromRedis(String mainOpId);

    /**
     * 设置到redis中的单条流程主表的数据
     * @param mainOpId
     * @param referenceTableName
     * @param referenceTableId
     */
    public void initFlowMainData(String mainOpId, String referenceTableName, String referenceTableId);

    /**
     * 初始化流程主表的redis数据
     */
    public void initFlowMainDatas();
}
