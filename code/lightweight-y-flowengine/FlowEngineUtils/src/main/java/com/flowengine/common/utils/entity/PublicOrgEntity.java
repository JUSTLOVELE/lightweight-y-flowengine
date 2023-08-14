package com.flowengine.common.utils.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @history:
 */
@TableName("public_org")
public class PublicOrgEntity {
    /** 主键 */
    private String opId ;
    /** 类型 */
    private int category ;
    /** 机构编码 */
    private String orgCode ;
    /** 机构名称 */
    private String orgName ;
    /** 区域id */
    private String areaId ;

    /** 主键 */
    public String getOpId(){
        return this.opId;
    }
    /** 主键 */
    public void setOpId(String opId){
        this.opId=opId;
    }
    /** 类型 */
    public int getCategory(){
        return this.category;
    }
    /** 类型 */
    public void setCategory(int category){
        this.category=category;
    }
    /** 机构编码 */
    public String getOrgCode(){
        return this.orgCode;
    }
    /** 机构编码 */
    public void setOrgCode(String orgCode){
        this.orgCode=orgCode;
    }
    /** 机构名称 */
    public String getOrgName(){
        return this.orgName;
    }
    /** 机构名称 */
    public void setOrgName(String orgName){
        this.orgName=orgName;
    }
    /** 区域id */
    public String getAreaId(){
        return this.areaId;
    }
    /** 区域id */
    public void setAreaId(String areaId){
        this.areaId=areaId;
    }
}
