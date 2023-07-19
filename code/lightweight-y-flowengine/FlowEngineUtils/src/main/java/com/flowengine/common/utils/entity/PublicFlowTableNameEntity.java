package com.flowengine.common.utils.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 建模引擎-表模块;
 * @author yangzl 2023.07.19
 * @version 1.00.00
 * @Description:
 * @history:
 */
@TableName("public_flow_table_name_tbl")
public class PublicFlowTableNameEntity implements Serializable {
    /** 主键 */
    @TableId
    private String opId ;
    /** 表名 */
    private String tableName ;
    /** 中文描述 */
    private String tableNameDesc ;
    /** 表所属模块id */
    private String tableModuleId ;
    /** 权限1-100 */
    private Integer authority ;
    /** 表所属模块name */
    private String tableModuleName ;
    /** 所属角色，逗号分割 */
    private String roleOpId ;
    /** 所属科室，逗号分割 */
    private String deptOpId ;
    /** 所属人员，逗号分割 */
    private String userOpId ;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableNameDesc() {
        return tableNameDesc;
    }

    public void setTableNameDesc(String tableNameDesc) {
        this.tableNameDesc = tableNameDesc;
    }

    public String getTableModuleId() {
        return tableModuleId;
    }

    public void setTableModuleId(String tableModuleId) {
        this.tableModuleId = tableModuleId;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

    public String getTableModuleName() {
        return tableModuleName;
    }

    public void setTableModuleName(String tableModuleName) {
        this.tableModuleName = tableModuleName;
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
