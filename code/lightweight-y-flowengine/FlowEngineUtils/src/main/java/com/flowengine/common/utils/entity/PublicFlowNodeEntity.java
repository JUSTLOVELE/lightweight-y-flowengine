package com.flowengine.common.utils.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @author yangzl 2023.03.13
 * @version 1.00.00
 * @Description:
 * @history:
 */
@TableName("public_flow_node_tbl")
public class PublicFlowNodeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String opId ;
    /** 流程主表id */
    private String mainId ;
    /** 上一个环节 */
    private String lastNodeId ;
    /** 上一个环节key */
    private String lastNodeKey ;
    /** 限制时间,单位天 */
    private Integer limitTime ;
    /** 下一个环节;下一个环节*/
    private String nextNode ;
    /** 当前环节名称 */
    private String nodeName ;

    private String nodeKey ;
    /** 节点排序：1,2,3,4 */
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

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
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
