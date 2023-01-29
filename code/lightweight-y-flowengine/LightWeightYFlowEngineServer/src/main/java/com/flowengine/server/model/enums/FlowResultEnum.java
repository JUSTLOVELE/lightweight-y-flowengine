package com.flowengine.server.model.enums;
/**
 * @Description:
 * @author yangzl 2023.1.28
 * @version 1.00.00
 * @history:
 */
public enum FlowResultEnum {

    NO_PASS(0, "不通过"),
    PASS(1, "通过")
    ;

    private int value;

    private String text;

    FlowResultEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
