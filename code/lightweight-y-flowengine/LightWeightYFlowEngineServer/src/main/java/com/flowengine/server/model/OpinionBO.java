package com.flowengine.server.model;

/**
 * @Description:意见对象，有领导处理意见、回退意见、普通处理意见
 * @author yangzl 2023.1.28
 * @version 1.00.00
 * @history:
 */
public class OpinionBO {

    private String flowComment;

    private String headerComment;

    private String backComment;

    public String getFlowComment() {
        return flowComment;
    }

    public void setFlowComment(String flowComment) {
        this.flowComment = flowComment;
    }

    public String getHeaderComment() {
        return headerComment;
    }

    public void setHeaderComment(String headerComment) {
        this.headerComment = headerComment;
    }

    public String getBackComment() {
        return backComment;
    }

    public void setBackComment(String backComment) {
        this.backComment = backComment;
    }
}
