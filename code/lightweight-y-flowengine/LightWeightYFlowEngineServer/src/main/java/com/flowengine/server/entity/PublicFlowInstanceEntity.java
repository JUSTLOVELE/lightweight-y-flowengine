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
@TableName("public_flow_instance_tbl")
public class PublicFlowInstanceEntity implements Serializable {

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

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
    }

    public Integer getIsOverTime() {
        return isOverTime;
    }

    public void setIsOverTime(Integer isOverTime) {
        this.isOverTime = isOverTime;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public String getTaskOpId() {
        return taskOpId;
    }

    public void setTaskOpId(String taskOpId) {
        this.taskOpId = taskOpId;
    }

    public Integer getUserAppraise() {
        return userAppraise;
    }

    public void setUserAppraise(Integer userAppraise) {
        this.userAppraise = userAppraise;
    }

    public String getAppraiseContent() {
        return appraiseContent;
    }

    public void setAppraiseContent(String appraiseContent) {
        this.appraiseContent = appraiseContent;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
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

    public String getPublicFlowComment() {
        return publicFlowComment;
    }

    public void setPublicFlowComment(String publicFlowComment) {
        this.publicFlowComment = publicFlowComment;
    }

    public String getPublicFlowAttachment() {
        return publicFlowAttachment;
    }

    public void setPublicFlowAttachment(String publicFlowAttachment) {
        this.publicFlowAttachment = publicFlowAttachment;
    }

    public String getPrivateFlowAttachment() {
        return privateFlowAttachment;
    }

    public void setPrivateFlowAttachment(String privateFlowAttachment) {
        this.privateFlowAttachment = privateFlowAttachment;
    }
}
