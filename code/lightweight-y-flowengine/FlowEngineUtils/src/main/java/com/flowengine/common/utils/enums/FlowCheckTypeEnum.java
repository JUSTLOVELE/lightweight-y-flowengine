package com.flowengine.common.utils.enums;

/**
 * @author yangzl 2023/8/22
 * @version 1.00.00
 * @Description: public_flow_node_check_tbl的check_type
 * @history:
 */
public enum FlowCheckTypeEnum {
    //1:人员;2:部门;10:角色;20:所有人
    PERSON("1", "人员"),

    DEPT("2", "部门"),

    FLOW_ROLE("10", "角色"),

    ALL("20", "所有人")
    ;

    private String value;

    private String text;

    FlowCheckTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
