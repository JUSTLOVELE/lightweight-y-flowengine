package com.flowengine.server.backend.service.flow;

import com.flowengine.common.utils.entity.PublicFlowMainEntity;

import java.util.Map;

/**
 * @Description:流程主表
 * @author yangzl 2023.02.22
 * @version 1.00.00
 * @history:
 */
public interface MainService {

    /**
     * 编辑
     * @param mainEntity
     * @param children
     * @return
     */
    public String edit(PublicFlowMainEntity mainEntity, String children);

    /**
     * 删除
     * @param opId
     * @return
     */
    public String delete(String opId);

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
