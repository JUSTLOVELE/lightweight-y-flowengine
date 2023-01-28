package com.flowengine.server.model.enums;

/**
 * @Description:
 * @author yangzl 2023.1.28
 * @version 1.00.00
 * @history:
 */
public enum OverTimeEnum {

    NO_OVER_TIME(0, "未超时"),

    OVER_TIME(1, "超时")
    ;

    private int value;

    private String text;

    OverTimeEnum(int value, String text) {
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
