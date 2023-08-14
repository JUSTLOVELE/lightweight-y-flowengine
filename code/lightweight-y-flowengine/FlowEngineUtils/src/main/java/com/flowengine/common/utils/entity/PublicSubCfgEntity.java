package com.flowengine.common.utils.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author yangzl 2023/8/7
 * @version 1.00.00
 * @Description:
 * @history:
 */
@TableName("public_sub_cfg")
public class PublicSubCfgEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键 */
    private String opId ;
    /** 主表id */
    private String cfgId ;
    /** 值 */
    private String subCfgValue ;
    /** name */
    private String subCfgName ;
    /** 关键字 */
    private String keyCode ;

    private Integer subCfgValueType;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getCfgId() {
        return cfgId;
    }

    public void setCfgId(String cfgId) {
        this.cfgId = cfgId;
    }

    public String getSubCfgValue() {
        return subCfgValue;
    }

    public void setSubCfgValue(String subCfgValue) {
        this.subCfgValue = subCfgValue;
    }

    public String getSubCfgName() {
        return subCfgName;
    }

    public void setSubCfgName(String subCfgName) {
        this.subCfgName = subCfgName;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public Integer getSubCfgValueType() {
        return subCfgValueType;
    }

    public void setSubCfgValueType(Integer subCfgValueType) {
        this.subCfgValueType = subCfgValueType;
    }
}
