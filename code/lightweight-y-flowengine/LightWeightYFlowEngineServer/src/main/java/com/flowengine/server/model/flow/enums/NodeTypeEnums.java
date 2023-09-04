package com.flowengine.server.model.flow.enums;

/**
 * @author yangzl 2023/9/4
 * @version 1.00.00
 * @Description:
 * @history:
 */
public enum NodeTypeEnums {

    UN_COUNTERSIGN("1", "非会签"),

    COUNTERSIGN("2", "会签"),

    ONE_BY_ONE("3", "按顺序处理")

    ;

    private String value;

    private String text;

    NodeTypeEnums(String value, String text) {
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
