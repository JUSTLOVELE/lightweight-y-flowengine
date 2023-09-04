package com.flowengine.server.model.flow.model;

/**
 * @author yangzl 2023/8/30
 * @version 1.00.00
 * @Description:
 * @history:
 */
public class FlowMainToTableBean {
    /**业务表名**/
    public String referenceTableName;
    /**流程实例表名**/
    public String flowInstanceTableName;
    /**流程实例流转表名**/
    public String flowInstanceFlowTableName;
    /**流转意见表**/
    public String flowCommentTableName;

    public String getReferenceTableName() {
        return referenceTableName;
    }

    public void setReferenceTableName(String referenceTableName) {
        this.referenceTableName = referenceTableName;
    }

    public String getFlowInstanceTableName() {
        return flowInstanceTableName;
    }

    public void setFlowInstanceTableName(String flowInstanceTableName) {
        this.flowInstanceTableName = flowInstanceTableName;
    }

    public String getFlowInstanceFlowTableName() {
        return flowInstanceFlowTableName;
    }

    public void setFlowInstanceFlowTableName(String flowInstanceFlowTableName) {
        this.flowInstanceFlowTableName = flowInstanceFlowTableName;
    }

    public String getFlowCommentTableName() {
        return flowCommentTableName;
    }

    public void setFlowCommentTableName(String flowCommentTableName) {
        this.flowCommentTableName = flowCommentTableName;
    }
}
