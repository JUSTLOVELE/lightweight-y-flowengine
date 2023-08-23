package com.flowengine.common.utils.entity.createmodel;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author yangzl 2023/8/23
 * @version 1.00.00
 * @Description: 建模引擎-表-业务流程表;
 * @history:
 */
@TableName("public_flow_table_flow_instance_tbl")
public class PublicFlowTableFlowInstanceEntity {
    /** 主键 */
    @TableId
    private String opId ;
    /** 创建时间 */
    private Date createTime = new Date();
    /** public_flow_table_name_tbl的主键 */
    private String tableOpId ;
    /** 1:流程实例;2:流程流转 */
    private Integer tableType ;
    /** 表名 */
    private String tableName ;

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
    /** public_flow_table_name_tbl的主键 */
    public String getTableOpId(){
        return this.tableOpId;
    }
    /** public_flow_table_name_tbl的主键 */
    public void setTableOpId(String tableOpId){
        this.tableOpId=tableOpId;
    }
    /** 1:流程实例;2:流程流转 */
    public Integer getTableType(){
        return this.tableType;
    }
    /** 1:流程实例;2:流程流转 */
    public void setTableType(Integer tableType){
        this.tableType=tableType;
    }
    /** 表名 */
    public String getTableName(){
        return this.tableName;
    }
    /** 表名 */
    public void setTableName(String tableName){
        this.tableName=tableName;
    }
}
