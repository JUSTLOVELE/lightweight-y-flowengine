package com.flowengine.server.model.flow.model;


import com.flowengine.common.utils.UUIDGenerator;

import java.util.Date;

/**
 * @author yangzl 2023/8/30
 * @version 1.00.00
 * @Description:
 * @history:
 */
public class TemplateFlowInstanceFlowBean {
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

    private String userOpId;
    /** 就这个表的上一个环节的主键 */
    private String lastOpId ;
    /** 流程状态;0:未操作;1:已操作;注意这里仅仅是是否操作过，如果不通过也是属于操作的也就是1 */
    private Integer flowStatus ;
    /** 处理结果:0:不通过;1:通过 */
    private Integer flowResult ;
    /** 顺序 */
    private Integer flowSort ;

    public TemplateFlowInstanceFlowBean() {

        this.createTime = new Date();
        this.opId = UUIDGenerator.getUUID();
    }

    /** 主键 */
    public String getOpId(){
        return this.opId;
    }
    /** 主键 */
    public void setOpId(String opId){
        this.opId=opId;
    }
    /** 流程实例表主键 */
    public String getInstanceId(){
        return this.instanceId;
    }
    /** 流程实例表主键 */
    public void setInstanceId(String instanceId){
        this.instanceId=instanceId;
    }
    /** 绑定的业务主键 */
    public String getTaskOpId(){
        return this.taskOpId;
    }
    /** 绑定的业务主键 */
    public void setTaskOpId(String taskOpId){
        this.taskOpId=taskOpId;
    }
    /** 创建时间 */
    public Date getCreateTime(){
        return this.createTime;
    }
    /** 创建时间 */
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
    /** 环节id */
    public String getNodeId(){
        return this.nodeId;
    }
    /** 环节id */
    public void setNodeId(String nodeId){
        this.nodeId=nodeId;
    }
    /**  */
    public String getNodeKey(){
        return this.nodeKey;
    }
    /**  */
    public void setNodeKey(String nodeKey){
        this.nodeKey=nodeKey;
    }
    /** 机构id */
    public String getOrgId(){
        return this.orgId;
    }
    /** 机构id */
    public void setOrgId(String orgId){
        this.orgId=orgId;
    }
    /** 部门id */
    public String getDeptId(){
        return this.deptId;
    }
    /** 部门id */
    public void setDeptId(String deptId){
        this.deptId=deptId;
    }
    /**  */
    public String getLastNodeId(){
        return this.lastNodeId;
    }
    /**  */
    public void setLastNodeId(String lastNodeId){
        this.lastNodeId=lastNodeId;
    }
    /**  */
    public String getLastNodeKey(){
        return this.lastNodeKey;
    }
    /**  */
    public void setLastNodeKey(String lastNodeKey){
        this.lastNodeKey=lastNodeKey;
    }
    /**  */
    public String getNextNodeId(){
        return this.nextNodeId;
    }
    /**  */
    public void setNextNodeId(String nextNodeId){
        this.nextNodeId=nextNodeId;
    }
    /**  */
    public String getNextNodeKey(){
        return this.nextNodeKey;
    }
    /**  */
    public void setNextNodeKey(String nextNodeKey){
        this.nextNodeKey=nextNodeKey;
    }
    /**  */
    public Date getOperationTime(){
        return this.operationTime;
    }
    /**  */
    public void setOperationTime(Date operationTime){
        this.operationTime=operationTime;
    }
    /** 就这个表的上一个环节的主键 */
    public String getLastOpId(){
        return this.lastOpId;
    }
    /** 就这个表的上一个环节的主键 */
    public void setLastOpId(String lastOpId){
        this.lastOpId=lastOpId;
    }
    /** 流程状态;0:未操作;1:已操作;注意这里仅仅是是否操作过，如果不通过也是属于操作的也就是1 */
    public Integer getFlowStatus(){
        return this.flowStatus;
    }
    /** 流程状态;0:未操作;1:已操作;注意这里仅仅是是否操作过，如果不通过也是属于操作的也就是1 */
    public void setFlowStatus(Integer flowStatus){
        this.flowStatus=flowStatus;
    }
    /** 处理结果:0:不通过;1:通过 */
    public Integer getFlowResult(){
        return this.flowResult;
    }
    /** 处理结果:0:不通过;1:通过 */
    public void setFlowResult(Integer flowResult){
        this.flowResult=flowResult;
    }
    /** 顺序 */
    public Integer getFlowSort(){
        return this.flowSort;
    }
    /** 顺序 */
    public void setFlowSort(Integer flowSort){
        this.flowSort=flowSort;
    }

    public String getUserOpId() {
        return userOpId;
    }

    public void setUserOpId(String userOpId) {
        this.userOpId = userOpId;
    }
}
