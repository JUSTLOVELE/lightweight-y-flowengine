package com.flowengine.server.model.flow.enums;

/**
 * @author yangzl 2023/8/30
 * @version 1.00.00
 * @Description:
 * @history:
 */
public enum TableFlowInstanceTableTypeEnums {

    FLOW_INSTANCE(1, "流程实例"),
    FLOW_INSTANCE_FLOW(2,  "流程流转"),
    FLOW_COMMENT(3, "流转意见")
    ;

    private int value;

    private String text;

    TableFlowInstanceTableTypeEnums(int value, String text) {

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
