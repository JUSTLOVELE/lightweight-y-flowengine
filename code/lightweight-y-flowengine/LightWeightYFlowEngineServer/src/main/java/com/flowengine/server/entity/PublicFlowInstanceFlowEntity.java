package com.flowengine.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
@Deprecated
@TableName("public_flow_instance_flow_tbl")
public class PublicFlowInstanceFlowEntity implements Serializable {
    /** 主键 */
    private String opId ;
    /** 流程实例表主键 */
    private String instanceId ;
    /** 绑定的业务主键 */
    private String taskOpId ;
    /** 创建时间 */
    private Date createTime ;
    /** 环节id */
    private String nodeId ;
    /**  */
    private String nodeKey ;
    /** 机构id */
    private String orgId ;
    /** 部门id */
    private String deptId ;
    /**  */
    private String lastNodeId ;
    /**  */
    private String lastNodeKey ;
    /**  */
    private String nextNodeId ;
    /**  */
    private String nextNodeKey ;
    /**  */
    private Date operationTime ;
    /**  */
    private String userOpId ;
    /**  */
    private String userName ;
    /** 就这个表的上一个环节的主键 */
    private String lastOpId ;
    /** 流程状态;0:未操作;1:已操作;注意这里仅仅是是否操作过，如果不通过也是属于操作的也就是1 */
    private Integer flowStatus ;
    /** 处理结果:0:不通过;1:通过 */
    private Integer flowResult ;
    /** 处理意见 */
    private String flowComment ;
    /** 领导处理意见 */
    private String headerComment ;
    /** 回退意见 */
    private String backComment ;

    private Integer flowSort;

    private Integer refType;

    private String refId;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskOpId() {
        return taskOpId;
    }

    public void setTaskOpId(String taskOpId) {
        this.taskOpId = taskOpId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
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

    public String getLastNodeId() {
        return lastNodeId;
    }

    public void setLastNodeId(String lastNodeId) {
        this.lastNodeId = lastNodeId;
    }

    public String getLastNodeKey() {
        return lastNodeKey;
    }

    public void setLastNodeKey(String lastNodeKey) {
        this.lastNodeKey = lastNodeKey;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public String getNextNodeKey() {
        return nextNodeKey;
    }

    public void setNextNodeKey(String nextNodeKey) {
        this.nextNodeKey = nextNodeKey;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getUserOpId() {
        return userOpId;
    }

    public void setUserOpId(String userOpId) {
        this.userOpId = userOpId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastOpId() {
        return lastOpId;
    }

    public void setLastOpId(String lastOpId) {
        this.lastOpId = lastOpId;
    }

    public Integer getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
    }

    public Integer getFlowResult() {
        return flowResult;
    }

    public void setFlowResult(Integer flowResult) {
        this.flowResult = flowResult;
    }

    public String getFlowComment() {
        return flowComment;
    }

    public void setFlowComment(String flowComment) {
        this.flowComment = flowComment;
    }

    public String getHeaderComment() {
        return headerComment;
    }

    public void setHeaderComment(String headerComment) {
        this.headerComment = headerComment;
    }

    public String getBackComment() {
        return backComment;
    }

    public void setBackComment(String backComment) {
        this.backComment = backComment;
    }

    public Integer getFlowSort() {
        return flowSort;
    }

    public void setFlowSort(Integer flowSort) {
        this.flowSort = flowSort;
    }

    public Integer getRefType() {
        return refType;
    }

    public void setRefType(Integer refType) {
        this.refType = refType;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
}
