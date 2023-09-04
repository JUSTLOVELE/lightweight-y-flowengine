package com.flowengine.server.model.flow.enums;

/**
 * @author yangzl 2023/9/4
 * @version 1.00.00
 * @Description:
 * @history:
 */
public enum NodeCheckTypeEnums {
    //审批类型,可能为1,2,10,20;1:人员;2:部门;10:角色;11:抄送;20:所有人;
    PERSON("1", "人员"),

    DEPT("2", "科室"),

    FLOW_ROLE("10", "角色"),

    COPY("11", "抄送"),

    ALL("20", "所有人")
    ;

    private String value;

    private String text;

    public static NodeCheckTypeEnums getInstanceByValue(String value) {

        for(NodeCheckTypeEnums enums: NodeCheckTypeEnums.values()) {

            if(enums.value.equals(value)) {
                return enums;
            }
        }

        return null;
    }

    NodeCheckTypeEnums(String value, String text) {
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
