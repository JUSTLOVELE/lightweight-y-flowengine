package com.flowengine.server.backend.service.flow;

import com.flowengine.server.entity.PublicFlowMainEntity;
import com.flowengine.server.entity.PublicFlowNodeEntity;

import java.util.List;
import java.util.Map;

/**
 * @Description:流程主表
 * @author yangzl 2023.02.22
 * @version 1.00.00
 * @history:
 */
public interface MainService {

    /**
     *
     * @param mainEntity
     * @param children
     * @return
     */
    public String add(PublicFlowMainEntity mainEntity, String children);

                       /**
     * 查询
     * @param param
     * @return
     */
    public String query(Map<String, Object> param);
}
