package com.flowengine.common.utils.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author yangzl 2023.08.14
 * @version 1.00.00
 * @Description:
 * @history:
 */
@TableName("public_flow_role_tbl")
public class PublicFlowRoleEntity {
    /**  */
    @TableId
    private String opId ;
    /**  */
    private Date createTime = new Date();
    /** 类型;1:超级管理员2:机构管理员20:负责人40:普通 */
    private Integer roleType ;
    /** 角色名称 */
    private String roleName ;

    /**  */
    public String getOpId(){
        return this.opId;
    }
    /**  */
    public void setOpId(String opId){
        this.opId=opId;
    }
    /**  */
    public Date getCreateTime(){
        return this.createTime;
    }
    /**  */
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
    /** 类型;1:超级管理员2:机构管理员20:负责人40:普通 */
    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    /** 角色名称 */
    public String getRoleName(){
        return this.roleName;
    }
    /** 角色名称 */
    public void setRoleName(String roleName){
        this.roleName=roleName;
    }
}
