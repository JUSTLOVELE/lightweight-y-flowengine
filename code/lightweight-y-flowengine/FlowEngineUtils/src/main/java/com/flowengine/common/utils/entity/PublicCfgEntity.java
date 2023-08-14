package com.flowengine.common.utils.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @history:
 */
@TableName("public_cfg")
public class PublicCfgEntity {
    /** 主键 */
    @TableId
    private String opId ;
    /** 机构id */
    private String orgId ;
    /** 配置名 */
    private String cfgName ;
    /** 关键字 */
    private String keyCode ;
    /** json格式 */
    private String jsonDesc ;

    private String comboboxDesc;
    /** 排序 */
    private Integer sort ;
    /** 所属科室 */
    private String deptId ;

    private Integer cfgType;

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
    /** 配置名 */
    public String getCfgName(){
        return this.cfgName;
    }
    /** 配置名 */
    public void setCfgName(String cfgName){
        this.cfgName=cfgName;
    }
    /** 关键字 */
    public String getKeyCode(){
        return this.keyCode;
    }
    /** 关键字 */
    public void setKeyCode(String keyCode){
        this.keyCode=keyCode;
    }
    /** json格式 */
    public String getJsonDesc(){
        return this.jsonDesc;
    }
    /** json格式 */
    public void setJsonDesc(String jsonDesc){
        this.jsonDesc=jsonDesc;
    }
    /** 排序 */
    public Integer getSort(){
        return this.sort;
    }
    /** 排序 */
    public void setSort(Integer sort){
        this.sort=sort;
    }
    /** 所属科室 */
    public String getDeptId(){
        return this.deptId;
    }
    /** 所属科室 */
    public void setDeptId(String deptId){
        this.deptId=deptId;
    }

    public Integer getCfgType() {
        return cfgType;
    }

    public void setCfgType(Integer cfgType) {
        this.cfgType = cfgType;
    }

    public String getComboboxDesc() {
        return comboboxDesc;
    }

    public void setComboboxDesc(String comboboxDesc) {
        this.comboboxDesc = comboboxDesc;
    }
}
