package com.flowengine.server.backend.service.flow;

/**
 * @author yangzl 2023/8/7
 * @version 1.00.00
 * @Description:
 * @history:
 */
public interface NodeService {

    /**
     * 根据流程主表的主键去查询其节点
     * @param mainId
     * @return
     */
    public String queryNodesByMainId(String mainId);

    /**
     * 获取审批类型下拉框
     * @return
     */
    public String getCheckTypeCombobox();

    /**
     * 获取环节类型下拉框
     * @return
     */
    public String getNodeStatusCombobox();

    /**
     * 获取环节类型下拉框
     * @return
     */
    public String getNodeTypeCombobox();
}
