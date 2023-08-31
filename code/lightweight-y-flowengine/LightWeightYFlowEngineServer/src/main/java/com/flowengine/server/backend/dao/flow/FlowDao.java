package com.flowengine.server.backend.dao.flow;

import com.flowengine.server.model.flow.model.StartFlowBO;
import com.flowengine.server.model.flow.model.TemplateFlowInstanceBean;

/**
 * @author yangzl 2023/8/31
 * @version 1.00.00
 * @Description:
 * @history:
 */
public interface FlowDao {

    /**
     * 查询开始阶段的流程实例表
     * @param flowInstanceBean
     */
    public void insertStartFlowInstanceData(TemplateFlowInstanceBean flowInstanceBean, String tableName, StartFlowBO startFlowVO);
}
