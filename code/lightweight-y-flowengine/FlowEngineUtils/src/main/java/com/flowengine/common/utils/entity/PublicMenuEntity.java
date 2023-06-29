package com.flowengine.common.utils.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @Description:用户表
 * @Copyright:
 * @Company:
 * @author yangzl 2023.06.29
 * @version 1.00.00
 * @history:
 */
@TableName("public_menu")
public class PublicMenuEntity  implements Serializable {
    /** 主键 */
    @TableId
    private String opId ;
    /** 父id */
    private String parentId ;
    /**  */
    private String url ;
    /**菜单名**/
    private String text;
    /** 0:根节点;1:叶节点;2:叶子节点 */
    private Integer type ;
    /** 默认值21 */
    private Integer buttonType ;
    /** 1:可用;0:不可用 */
    private Integer availableFlag ;
    /** 类别 */
    private Integer category;
    /** 排序 */
    private Integer sort ;
    /** 所属平台 */
    private Integer sys ;
    /**  */
    private String icon ;

    /** 主键 */
    public String getOpId(){
        return this.opId;
    }
    /** 主键 */
    public void setOpId(String opId){
        this.opId=opId;
    }
    /** 父id */
    public String getParentId(){
        return this.parentId;
    }
    /** 父id */
    public void setParentId(String parentId){
        this.parentId=parentId;
    }
    /**  */
    public String getUrl(){
        return this.url;
    }
    /**  */
    public void setUrl(String url){
        this.url=url;
    }
    /** 0:根节点;1:叶节点;2:叶子节点 */
    public Integer getType(){
        return this.type;
    }
    /** 0:根节点;1:叶节点;2:叶子节点 */
    public void setType(Integer type){
        this.type=type;
    }
    /** 默认值21 */
    public Integer getButtonType(){
        return this.buttonType;
    }
    /** 默认值21 */
    public void setButtonType(Integer buttonType){
        this.buttonType=buttonType;
    }
    /** 1:可用;0:不可用 */
    public Integer getAvailableFlag(){
        return this.availableFlag;
    }
    /** 1:可用;0:不可用 */
    public void setAvailableFlag(Integer availableFlag){
        this.availableFlag=availableFlag;
    }
    /** 排序 */
    public Integer getSort(){
        return this.sort;
    }
    /** 排序 */
    public void setSort(Integer sort){
        this.sort=sort;
    }
    /** 所属平台 */
    public Integer getSys(){
        return this.sys;
    }
    /** 所属平台 */
    public void setSys(Integer sys){
        this.sys=sys;
    }
    /**  */
    public String getIcon(){
        return this.icon;
    }
    /**  */
    public void setIcon(String icon){
        this.icon=icon;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}