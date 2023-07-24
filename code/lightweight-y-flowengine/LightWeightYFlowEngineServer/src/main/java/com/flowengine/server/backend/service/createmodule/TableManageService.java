package com.flowengine.server.backend.service.createmodule;

import com.flowengine.common.utils.entity.PublicFlowTableNameEntity;

import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023-07-24
 * @version 1.00.00
 * @history:
 */
public interface TableManageService {

    /**
     * 新增
     * @param entity
     * @return
     */
    public String add(PublicFlowTableNameEntity entity);

                      /**
     * 表格查询
     * @param param
     * @return
     */
    public String tableQuery(Map<String, Object> param);
}
