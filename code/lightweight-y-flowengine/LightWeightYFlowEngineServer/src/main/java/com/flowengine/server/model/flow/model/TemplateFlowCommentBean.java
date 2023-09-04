package com.flowengine.server.model.flow.model;

import com.flowengine.common.utils.UUIDGenerator;

import java.util.Date;

/**
 * @author yangzl 2023/9/4
 * @version 1.00.00
 * @Description:
 * @history:
 */
public class TemplateFlowCommentBean {
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
    /** 处理意见 */
    private String flowComment ;
    /** 顺序 */
    private Integer flowSort ;
    /** 处理结果:0:不通过;1:通过 */
    private Integer flowResult ;
    /** 绑定的流程流转主键 */
    private String instanceFlowId ;
    /** 操作人 */
    private String userOpId ;
    /** 科室id */
    private String deptId ;
    /** 机构id */
    private String orgId ;

    public TemplateFlowCommentBean() {

        this.opId = UUIDGenerator.getUUID();
        this.createTime = new Date();
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
    /** 处理意见 */
    public String getFlowComment(){
        return this.flowComment;
    }
    /** 处理意见 */
    public void setFlowComment(String flowComment){
        this.flowComment=flowComment;
    }
    /** 顺序 */
    public Integer getFlowSort(){
        return this.flowSort;
    }
    /** 顺序 */
    public void setFlowSort(Integer flowSort){
        this.flowSort=flowSort;
    }
    /** 处理结果:0:不通过;1:通过 */
    public Integer getFlowResult(){
        return this.flowResult;
    }
    /** 处理结果:0:不通过;1:通过 */
    public void setFlowResult(Integer flowResult){
        this.flowResult=flowResult;
    }
    /** 绑定的流程流转主键 */
    public String getInstanceFlowId(){
        return this.instanceFlowId;
    }
    /** 绑定的流程流转主键 */
    public void setInstanceFlowId(String instanceFlowId){
        this.instanceFlowId=instanceFlowId;
    }
    /** 操作人 */
    public String getUserOpId(){
        return this.userOpId;
    }
    /** 操作人 */
    public void setUserOpId(String userOpId){
        this.userOpId=userOpId;
    }
    /** 科室id */
    public String getDeptId(){
        return this.deptId;
    }
    /** 科室id */
    public void setDeptId(String deptId){
        this.deptId=deptId;
    }
    /** 机构id */
    public String getOrgId(){
        return this.orgId;
    }
    /** 机构id */
    public void setOrgId(String orgId){
        this.orgId=orgId;
    }
}
