package com.flowengine.common.utils.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId
    private String opId ;
    /** 流程主表id */
    private String mainId ;
    /** 上一个环节 */
    private String lastNodeId ;
    /** 上一个环节key */
    private String lastNodeKey ;
    /** 限制时间,单位天 */
    private Integer limitTime ;
    /** 下一个环节(json配置)**/
    private String nextNode ;
    /** 当前环节名称 */
    private String nodeName ;
    /** 当前环节key;关键字start开始;ing:中间环节;或者其他指定的key也表示中间环节;end结束; */
    private String nodeKey ;
    /** 节点排序：1,2,3,4 */
    private Integer nodeSort ;
    /** 1:开始;2:常规节点;10:结束 */
    private String nodeStatus ;
    /** 主键 */
    public String getOpId(){
    return this.opId;
    }
    /** 主键 */
    public void setOpId(String opId){
        this.opId=opId;
    }
    /** 流程主表id */
    public String getMainId(){
        return this.mainId;
    }
    /** 流程主表id */
    public void setMainId(String mainId){
        this.mainId=mainId;
    }
    /** 上一个环节 */
    public String getLastNodeId(){
        return this.lastNodeId;
    }
    /** 上一个环节 */
    public void setLastNodeId(String lastNodeId){
        this.lastNodeId=lastNodeId;
    }
    /** 上一个环节key */
    public String getLastNodeKey(){
        return this.lastNodeKey;
    }
    /** 上一个环节key */
    public void setLastNodeKey(String lastNodeKey){
        this.lastNodeKey=lastNodeKey;
    }
    /** 限制时间,单位天 */
    public Integer getLimitTime(){
        return this.limitTime;
    }
    /** 限制时间,单位天 */
    public void setLimitTime(Integer limitTime){
        this.limitTime=limitTime;
    }
    /** 下一个环节(json配置);下一个环节
     [{
     "key":"",
     "nextNodeId":"",
     "nextNodeKey":""
     }] */
    public String getNextNode(){
        return this.nextNode;
    }
    /** 下一个环节(json配置);下一个环节
     [{
     "key":"",
     "nextNodeId":"",
     "nextNodeKey":""
     }] */
    public void setNextNode(String nextNode){
        this.nextNode=nextNode;
    }
    /** 当前环节名称 */
    public String getNodeName(){
        return this.nodeName;
    }
    /** 当前环节名称 */
    public void setNodeName(String nodeName){
        this.nodeName=nodeName;
    }
    /** 当前环节key;关键字start开始;ing:中间环节;或者其他指定的key也表示中间环节;end结束; */
    public String getNodeKey(){
        return this.nodeKey;
    }
    /** 当前环节key;关键字start开始;ing:中间环节;或者其他指定的key也表示中间环节;end结束; */
    public void setNodeKey(String nodeKey){
        this.nodeKey=nodeKey;
    }
    /** 节点排序：1,2,3,4 */
    public Integer getNodeSort(){
        return this.nodeSort;
    }
    /** 节点排序：1,2,3,4 */
    public void setNodeSort(Integer nodeSort){
        this.nodeSort=nodeSort;
    }
    /** 1:开始;2:常规节点;10:结束 */
    public String getNodeStatus(){
        return this.nodeStatus;
    }
    /** 1:开始;2:常规节点;10:结束 */
    public void setNodeStatus(String nodeStatus){
        this.nodeStatus=nodeStatus;
    }
}
