package com.flowengine.common.utils.entity.createmodel;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author yangzl 2023.07.4
 * @version 1.00.00
 * @Description:
 * @history:
 */
@TableName("public_flow_table_module_tbl")
public class PublicFlowTableModuleEntity implements Serializable {
    /** 主键 */
    @TableId
    private String opId ;
    /** 模块name */
    private String moduleName ;
    /** 权限1-100 */
    private Integer authority ;
    /** 所属角色，逗号分割 */
    private String roleOpId ;
    /** 所属科室，逗号分割 */
    private String deptOpId ;
    /** 所属人员，逗号分割 */
    private String userOpId;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

    public String getRoleOpId() {
        return roleOpId;
    }

    public void setRoleOpId(String roleOpId) {
        this.roleOpId = roleOpId;
    }

    public String getDeptOpId() {
        return deptOpId;
    }

    public void setDeptOpId(String deptOpId) {
        this.deptOpId = deptOpId;
    }

    public String getUserOpId() {
        return userOpId;
    }

    public void setUserOpId(String userOpId) {
        this.userOpId = userOpId;
    }
}
