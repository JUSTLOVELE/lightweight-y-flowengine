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
@TableName("public_flow_role_user_grant")
public class PublicFlowRoleUserGrantEntity {
    /**  */
    @TableId
    private String opId ;
    /**  */
    private Date createTime = new Date();
    /** public_user_tbl主键 */
    private String userOpId ;
    /** public_flow_role_tbl主键 */
    private String flowRoleId ;

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
    /** public_user_tbl主键 */
    public String getUserOpId(){
        return this.userOpId;
    }
    /** public_user_tbl主键 */
    public void setUserOpId(String userOpId){
        this.userOpId=userOpId;
    }
    /** public_flow_role_tbl主键 */
    public String getFlowRoleId(){
        return this.flowRoleId;
    }
    /** public_flow_role_tbl主键 */
    public void setFlowRoleId(String flowRoleId){
        this.flowRoleId=flowRoleId;
    }
}
