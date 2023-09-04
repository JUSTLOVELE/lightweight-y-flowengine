package com.flowengine.server.backend.dao.flow;

import com.flowengine.server.model.flow.model.StartFlowBO;
import com.flowengine.server.model.flow.model.TemplateFlowCommentBean;
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
     * 查询流程实例对应表的数据
     * @param opId
     * @param instanceFlowtableName
     * @return
     */
    public List<Map<String, Object>> queryFlowInstance(String opId, String instanceFlowtableName);

    /**
     * 结束更新流程实例数据
     * @param templateFlowInstanceBean
     * @param instanceTableName
     */
    public void updateEndFlow(TemplateFlowInstanceBean templateFlowInstanceBean, String instanceTableName);

    /**
     * 更新
     * @param param
     */
    public void updateNextFlowInstanceFlow(Map<String, Object> param);

    /**
     * 插入流转意见
     * @param bean
     * @param commentTableName
     */
    public void insertComment(TemplateFlowCommentBean bean, String commentTableName);

    /**
     * 查询排序
     * @param instanceFlowId
     * @param commentTableName
     * @return
     */
    public int queryCommentSortByInstanceFlowId(String instanceFlowId, String commentTableName);

    /**
     * 根据deptId和nodeId查询
     * @param deptIds
     * @param nodeId
     * @param instanceFlowId
     * @param commentTableName
     * @return
     */
    public int queryCommentCountByDeptId(List<String> deptIds, String nodeId, String instanceFlowId, String commentTableName);

    /**
     * 根据userOpId和nodeId查询
     * @param ids
     * @param nodeId
     * @param instanceFlowId
     * @param commentTableName
     * @return
     */
    public int queryCommentCountByUserOpId(List<String> ids, String nodeId, String instanceFlowId, String commentTableName);

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
