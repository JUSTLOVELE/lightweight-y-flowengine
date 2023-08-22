package com.flowengine.common.utils.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangzl 2023.03.13
 * @version 1.00.00
 * @Description:
 * @history:
 */
@TableName("public_flow_main_tbl")
public class PublicFlowMainEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId
    private String opId ;
    /** 关键字 */
    private String keyCode ;
    /** 是否启用;1:启用;0:不启用 */
    private Integer isStop ;
    /** 描述 */
    private String flowDesc ;
    /** 类型 */
    private Integer mainType ;
    /** 流程名称 */
    private String mainName ;
    /** 创建人主键 */
    private String createUserId ;
    /** 创建人姓名 */
    private String createUserName ;
    /** 创建时间 */
    private Date createTime ;
    /** 机构主键 */
    private String orgId ;
    /** 科室id */
    private String deptId ;

    private String referenceTableName;

    private String referenceTableId;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public Integer getIsStop() {
        return isStop;
    }

    public void setIsStop(Integer isStop) {
        this.isStop = isStop;
    }

    public String getFlowDesc() {
        return flowDesc;
    }

    public void setFlowDesc(String flowDesc) {
        this.flowDesc = flowDesc;
    }

    public Integer getMainType() {
        return mainType;
    }

    public void setMainType(Integer mainType) {
        this.mainType = mainType;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getReferenceTableName() {
        return referenceTableName;
    }

    public void setReferenceTableName(String referenceTableName) {
        this.referenceTableName = referenceTableName;
    }

    public String getReferenceTableId() {
        return referenceTableId;
    }

    public void setReferenceTableId(String referenceTableId) {
        this.referenceTableId = referenceTableId;
    }
}
