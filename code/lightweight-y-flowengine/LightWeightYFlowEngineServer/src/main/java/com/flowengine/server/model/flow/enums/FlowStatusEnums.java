package com.flowengine.server.model.flow.enums;

/**
 * @author yangzl 2023/8/31
 * @version 1.00.00
 * @Description:
 * @history:
 */
public enum FlowStatusEnums {

    ING(0, "未结束"),
    END(1,  "结束")
    ;

    private int value;

    private String text;

    FlowStatusEnums(int value, String text) {

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
