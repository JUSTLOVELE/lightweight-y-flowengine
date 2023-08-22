package com.flowengine.common.utils.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author yangzl 2023/8/22
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
@TableName("public_flow_node_check_tbl")
public class PublicFlowNodeCheckEntity {

    /**  */
    @TableId
    private String opId ;
    /**  */
    private Date createTime = new Date();
    /** 环节表的主键 */
    private String nodeOpId ;
    /** 根据check_type来定 */
    private String refId ;
    /** 看说明;1：非会签（有一个通过就可以）；2：会签（都要审核通过）；3：依次逐个处理（按顺序会签）； */
    private String nodeType ;
    /**审批类型,可能为1,2,10,20;**/
    private String checkType ;
    /** 节点排序：1,2,3,4 */
    private Integer nodeSort ;

    private String mainId;
    /**  */
    public String getOpId(){
    return this.opId;
    }
    /**  */
    public void setOpId(String opId){
        this.opId=opId;
    }
    /**  */
    public Date getCreateTime(){
        return this.createTime;
    }
    /**  */
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
    /** 环节表的主键 */
    public String getNodeOpId(){
        return this.nodeOpId;
    }
    /** 环节表的主键 */
    public void setNodeOpId(String nodeOpId){
        this.nodeOpId=nodeOpId;
    }
    /** 根据check_type来定 */
    public String getRefId(){
        return this.refId;
    }
    /** 根据check_type来定 */
    public void setRefId(String refId){
        this.refId=refId;
    }
    /** 看说明;1：非会签（有一个通过就可以）；2：会签（都要审核通过）；3：依次逐个处理（按顺序会签）； */
    public String getNodeType(){
        return this.nodeType;
    }
    /** 看说明;1：非会签（有一个通过就可以）；2：会签（都要审核通过）；3：依次逐个处理（按顺序会签）； */
    public void setNodeType(String nodeType){
        this.nodeType=nodeType;
    }
    /** 审批类型,可能为1,2,10,20;;审批类型,可能为1,2,10,20;
     1:人员;
     2:部门;
     10:角色;
     20:所有人 */
    public String getCheckType(){
        return this.checkType;
    }
    /** 审批类型,可能为1,2,10,20;;审批类型,可能为1,2,10,20;
     1:人员;
     2:部门;
     10:角色;
     20:所有人 */
    public void setCheckType(String checkType){
        this.checkType=checkType;
    }
    /** 节点排序：1,2,3,4 */
    public Integer getNodeSort(){
        return this.nodeSort;
    }
    /** 节点排序：1,2,3,4 */
    public void setNodeSort(Integer nodeSort){
        this.nodeSort=nodeSort;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }
}
