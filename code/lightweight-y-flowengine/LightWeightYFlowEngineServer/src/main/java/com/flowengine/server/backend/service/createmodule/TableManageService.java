package com.flowengine.server.backend.service.createmodule;

import com.flowengine.common.utils.entity.createmodel.PublicFlowTableNameEntity;

import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023-07-24
 * @version 1.00.00
 * @history:
 */
public interface TableManageService {

    /**
     * 业务关联的流程表名查询
     * @param param
     * @return
     */
    public String tableFlowInstanceQuery(Map<String, Object> param);

    /**
     * 获取下拉框
     * @return
     */
    public String getCombobox();

    /**
     * 执行SQL
     * @param param
     * @return
     */
    public String executeSQL(Map<String, Object> param);

    /**
     * 编辑
     * @param entity
     * @return
     */
    public String edit(PublicFlowTableNameEntity entity);

    /**
     * 表格查询
     * @param param
     * @return
     */
    public String columnTableQuery(Map<String, Object> param);

    /**
     * 删除
     * @param param
     * @return
     */
    public String delete(Map<String, Object> param);

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
