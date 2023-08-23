package com.flowengine.common.utils.entity.createmodel;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 建模引擎-列元素数据表;
 * @author yangzl 2023.07.19
 * @version 1.00.00
 * @Description:
 * @history:
 */
@TableName("public_flow_table_column_tbl")
public class PublicFlowTableColumnEntity {
    /** 主键 */
    @TableId
    private String opId ;
    /** 表名 */
    private String tableName ;
    /** 列所属的表id */
    private String tableOpId ;
    /** 列名 */
    private String columnName ;
    /** 列类型 */
    private String columnType ;
    /** 权限1-100 */
    private Integer authority ;
    /** 所属角色，逗号分割 */
    private String roleOpId ;
    /** 所属科室，逗号分割 */
    private String deptOpId ;
    /** 所属人员，逗号分割 */
    private String userOpId ;

    private Date createTime = new Date();

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

    public String getTableOpId() {
        return tableOpId;
    }

    public void setTableOpId(String tableOpId) {
        this.tableOpId = tableOpId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
