package com.flowengine.server.backend.dao.flow;

import com.flowengine.server.model.flow.model.StartFlowBO;
import com.flowengine.server.model.flow.model.TemplateFlowInstanceBean;
import com.flowengine.server.model.flow.model.TemplateFlowInstanceFlowBean;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/31
 * @version 1.00.00
 * @Description:
 * @history:
 */
public interface FlowDao {

    /**
     * 查询对应表的数据
     * @param opId
     * @param tableName
     * @return
     */
    public List<Map<String, Object>> queryFlowInstanceFlow(String opId, String tableName);

    /**
     * 插入下一阶段的流程流转表
     * @param flowInstanceFlowBean
     * @param tableName
     */
    public void insertNextFlowInstanceFlowData(TemplateFlowInstanceFlowBean flowInstanceFlowBean, String tableName);

    /**
     * 插入开始阶段的流程流转表
     * @param flowInstanceFlowBean
     * @param tableName
     */
    public void insertStartFlowInstanceFlowData(TemplateFlowInstanceFlowBean flowInstanceFlowBean, String tableName);

    /**
     * 插入开始阶段的流程实例表
     * @param flowInstanceBean
     */
    public void insertStartFlowInstanceData(TemplateFlowInstanceBean flowInstanceBean, String tableName, StartFlowBO startFlowVO);
}
