package com.flowengine.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
@TableName("public_flow_node_tbl")
public class PublicFlowNodeEntity implements Serializable {
    /** 主键 */
    private String opId ;
    /** 流程主表id */
    private String mainId ;
    /** 上一个环节 */
    private String lastNodeId ;
    /**  */
    private String lastNodeKey ;
    /** 限制时间,单位天 */
    private Integer limitTime ;
    /** 下一个环节 */
    private String nextNodeId ;
    /**  */
    private String nextNodeKey ;
    /** 当前环节名称 */
    private String nodeName ;
    /**  */
    private String nodeKey ;
    /** 节点排序 */
    private Integer nodeSort ;
    /** 1:开始;2:常规节点;10:结束 */
    private Integer nodeStatus ;
    /** 引用id,有必要时使用 */
    private String refId ;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String getLastNodeId() {
        return lastNodeId;
    }

    public void setLastNodeId(String lastNodeId) {
        this.lastNodeId = lastNodeId;
    }

    public String getLastNodeKey() {
        return lastNodeKey;
    }

    public void setLastNodeKey(String lastNodeKey) {
        this.lastNodeKey = lastNodeKey;
    }

    public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public String getNextNodeKey() {
        return nextNodeKey;
    }

    public void setNextNodeKey(String nextNodeKey) {
        this.nextNodeKey = nextNodeKey;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public Integer getNodeSort() {
        return nodeSort;
    }

    public void setNodeSort(Integer nodeSort) {
        this.nodeSort = nodeSort;
    }

    public Integer getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(Integer nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
}
