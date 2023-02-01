package com.flowengine.server.model;

/**
 * @Description:
 * @author yangzl 2023.2.1
 * @version 1.00.00
 * @history:
 */
public class EndFlowBO {

    private OpinionBO opinionBO;

    private FlowRunBO flowRunBO;

    public EndFlowBO(FlowRunBO flowRunBO, OpinionBO opinionBO) {

        if(flowRunBO == null || opinionBO == null) {
            throw new RuntimeException("入参不允许为空");
        }

        this.flowRunBO = flowRunBO;
        this.opinionBO = opinionBO;
    }

    public OpinionBO getOpinionBO() {
        return opinionBO;
    }

    public void setOpinionBO(OpinionBO opinionBO) {
        this.opinionBO = opinionBO;
    }

    public FlowRunBO getFlowRunBO() {
        return flowRunBO;
    }

    public void setFlowRunBO(FlowRunBO flowRunBO) {
        this.flowRunBO = flowRunBO;
    }
}
