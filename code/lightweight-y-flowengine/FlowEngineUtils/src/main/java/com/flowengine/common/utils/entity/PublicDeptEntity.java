package com.flowengine.common.utils.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangzl 2023.03.23
 * @version 1.00.00
 * @Description: 科室表
 * @history:
 */
@TableName("public_dept")
public class PublicDeptEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键 */
    @TableId
    private String opId ;
    /** 机构id */
    private String orgId ;
    /** 科室名称 */
    private String deptName ;
    /** 创建时间 */
    private Date createTime ;

    /** 主键 */
    public String getOpId(){
        return this.opId;
    }
    /** 主键 */
    public void setOpId(String opId){
        this.opId=opId;
    }
    /** 机构id */
    public String getOrgId(){
        return this.orgId;
    }
    /** 机构id */
    public void setOrgId(String orgId){
        this.orgId=orgId;
    }
    /** 科室名称 */
    public String getDeptName(){
        return this.deptName;
    }
    /** 科室名称 */
    public void setDeptName(String deptName){
        this.deptName=deptName;
    }
    /** 创建时间 */
    public Date getCreateTime(){
        return this.createTime;
    }
    /** 创建时间 */
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
}
