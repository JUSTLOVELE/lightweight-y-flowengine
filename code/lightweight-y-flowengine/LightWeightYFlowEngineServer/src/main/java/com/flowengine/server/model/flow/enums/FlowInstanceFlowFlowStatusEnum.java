package com.flowengine.server.model.flow.enums;

/**
 * @Description:
 * @author yangzl 2023.1.29
 * @version 1.00.00
 * @history:
 */
public enum FlowInstanceFlowFlowStatusEnum {

    WAIT_OPERATE(0, "未操作"),
    OPERATED(1, "已操作")
            ;

    private int value;

    private String text;

    FlowInstanceFlowFlowStatusEnum(int value, String text) {
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
