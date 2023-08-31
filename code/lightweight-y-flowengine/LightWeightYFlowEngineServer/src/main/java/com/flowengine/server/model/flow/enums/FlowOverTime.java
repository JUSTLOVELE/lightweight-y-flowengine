package com.flowengine.server.model.flow.enums;

/**
 * @author yangzl 2023/8/31
 * @version 1.00.00
 * @Description:
 * @history:
 */
public enum FlowOverTime {

    NO_TIMEOUT(0, "未超时"),
    TIMEOUT(1,  "超时")
    ;

    private int value;

    private String text;

    FlowOverTime(int value, String text) {

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
