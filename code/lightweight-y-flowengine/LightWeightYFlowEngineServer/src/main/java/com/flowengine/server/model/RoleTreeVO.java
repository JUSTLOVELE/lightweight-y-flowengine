package com.flowengine.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzl 2021.07.14
 * @version 1.00.00
 * @Description:
 * @history:
 */
public class RoleTreeVO {


    private String id;
    private boolean leaf = true;
    private String text;
    private boolean expanded = false;
    private Integer type;
    private String parentId;
    private boolean checked = false;
    private List<RoleTreeVO> children = new ArrayList<RoleTreeVO>();

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean isLeaf() {
        return leaf;
    }
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public List<RoleTreeVO> getChildren() {
        return children;
    }
    public void setChildren(List<RoleTreeVO> children) {
        this.children = children;
    }
    public boolean isExpanded() {
        return expanded;
    }
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
