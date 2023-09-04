package com.flowengine.server.model;

import com.flowengine.server.model.flow.enums.FlowResultEnum;

/**
 * @Description:
 * @author yangzl 2023.2.1
 * @version 1.00.00
 * @history:
 */
public class EndFlowBO {

    private String flowComment;


    /**public_flow_instance_flow_tbl的主键**/
    private String instanceFlowId;

    private String orgId;

    private String deptId;

    private String userOpId;

    private FlowResultEnum flowResultEnum;

    private String key;

    private String mainId;

    public String getFlowComment() {
        return flowComment;
    }

    public void setFlowComment(String flowComment) {
        this.flowComment = flowComment;
    }

    public String getInstanceFlowId() {
        return instanceFlowId;
    }

    public void setInstanceFlowId(String instanceFlowId) {
        this.instanceFlowId = instanceFlowId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserOpId() {
        return userOpId;
    }

    public void setUserOpId(String userOpId) {
        this.userOpId = userOpId;
    }

    public FlowResultEnum getFlowResultEnum() {
        return flowResultEnum;
    }

    public void setFlowResultEnum(FlowResultEnum flowResultEnum) {
        this.flowResultEnum = flowResultEnum;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }
}
