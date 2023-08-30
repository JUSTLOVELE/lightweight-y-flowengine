package com.flowengine.server.model.flow.model;

import java.util.Date;

/**
 * @author yangzl 2023/8/30
 * @version 1.00.00
 * @Description: 流程实例表（模版）
 * @history:
 */
public class TemplateFlowInstanceBean {
    /** 主键 */
    private String opId ;
    /** 创建时间 */
    private Date createTime ;
    /** 结束时间 */
    private Date endTime ;
    /** 流程状态;0:未结束;1:结束 */
    private Integer flowStatus ;
    /** 是否超时;0:未超时;1:超时 */
    private Integer isOverTime ;
    /** 总耗时 */
    private Integer totalTime ;
    /** 绑定的业务主键 */
    private String taskOpId ;
    /** 用户评价： -1:不满意 0:基本满意 1:满意 */
    private Integer userAppraise ;
    /** 评价内容 */
    private String appraiseContent ;
    /** 流程id */
    private String mainId ;
    /** 机构id */
    private String orgId ;
    /** 部门id */
    private String deptId ;
    /** 流程总体意见,也就是最终反馈的意见 */
    private String publicFlowComment ;
    /** 附件id,多个附件用逗号风格，一个流程最多不超过30个附件；公开附件信息 */
    private String publicFlowAttachment ;
    /** 附件id,多个附件用逗号风格，一个流程最多不超过30个附件；不公开附件信息 */
    private String privateFlowAttachment ;
    /** 创建人 */
    private String createUserOpId ;

    /** 主键 */
    public String getOpId(){
        return this.opId;
    }
    /** 主键 */
    public void setOpId(String opId){
        this.opId=opId;
    }
    /** 创建时间 */
    public Date getCreateTime(){
        return this.createTime;
    }
    /** 创建时间 */
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
    /** 结束时间 */
    public Date getEndTime(){
        return this.endTime;
    }
    /** 结束时间 */
    public void setEndTime(Date endTime){
        this.endTime=endTime;
    }
    /** 流程状态;0:未结束;1:结束 */
    public Integer getFlowStatus(){
        return this.flowStatus;
    }
    /** 流程状态;0:未结束;1:结束 */
    public void setFlowStatus(Integer flowStatus){
        this.flowStatus=flowStatus;
    }
    /** 是否超时;0:未超时;1:超时 */
    public Integer getIsOverTime(){
        return this.isOverTime;
    }
    /** 是否超时;0:未超时;1:超时 */
    public void setIsOverTime(Integer isOverTime){
        this.isOverTime=isOverTime;
    }
    /** 总耗时 */
    public Integer getTotalTime(){
        return this.totalTime;
    }
    /** 总耗时 */
    public void setTotalTime(Integer totalTime){
        this.totalTime=totalTime;
    }
    /** 绑定的业务主键 */
    public String getTaskOpId(){
        return this.taskOpId;
    }
    /** 绑定的业务主键 */
    public void setTaskOpId(String taskOpId){
        this.taskOpId=taskOpId;
    }
    /** 用户评价： -1:不满意 0:基本满意 1:满意 */
    public Integer getUserAppraise(){
        return this.userAppraise;
    }
    /** 用户评价： -1:不满意 0:基本满意 1:满意 */
    public void setUserAppraise(Integer userAppraise){
        this.userAppraise=userAppraise;
    }
    /** 评价内容 */
    public String getAppraiseContent(){
        return this.appraiseContent;
    }
    /** 评价内容 */
    public void setAppraiseContent(String appraiseContent){
        this.appraiseContent=appraiseContent;
    }
    /** 流程id */
    public String getMainId(){
        return this.mainId;
    }
    /** 流程id */
    public void setMainId(String mainId){
        this.mainId=mainId;
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
    /** 流程总体意见,也就是最终反馈的意见 */
    public String getPublicFlowComment(){
        return this.publicFlowComment;
    }
    /** 流程总体意见,也就是最终反馈的意见 */
    public void setPublicFlowComment(String publicFlowComment){
        this.publicFlowComment=publicFlowComment;
    }
    /** 附件id,多个附件用逗号风格，一个流程最多不超过30个附件；公开附件信息 */
    public String getPublicFlowAttachment(){
        return this.publicFlowAttachment;
    }
    /** 附件id,多个附件用逗号风格，一个流程最多不超过30个附件；公开附件信息 */
    public void setPublicFlowAttachment(String publicFlowAttachment){
        this.publicFlowAttachment=publicFlowAttachment;
    }
    /** 附件id,多个附件用逗号风格，一个流程最多不超过30个附件；不公开附件信息 */
    public String getPrivateFlowAttachment(){
        return this.privateFlowAttachment;
    }
    /** 附件id,多个附件用逗号风格，一个流程最多不超过30个附件；不公开附件信息 */
    public void setPrivateFlowAttachment(String privateFlowAttachment){
        this.privateFlowAttachment=privateFlowAttachment;
    }
    /** 创建人 */
    public String getCreateUserOpId(){
        return this.createUserOpId;
    }
    /** 创建人 */
    public void setCreateUserOpId(String createUserOpId){
        this.createUserOpId=createUserOpId;
    }
}
