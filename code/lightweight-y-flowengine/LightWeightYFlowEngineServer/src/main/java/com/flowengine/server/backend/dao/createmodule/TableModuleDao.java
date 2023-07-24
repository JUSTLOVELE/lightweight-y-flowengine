package com.flowengine.server.backend.dao.createmodule;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023-06-30
 * @version 1.00.00
 * @history:
 */
public interface TableModuleDao {

    /**
     * 获取下拉框数据
     * @return
     */
    public List<Map<String, Object>> getCombobox();

    /**
     * 查询总数
     * @param param
     * @return
     */
    public  int queryTotal(Map<String, Object> param);

    /**
     * 查询
     * @param param
     * @return
     */
    public List<Map<String, Object>> query(Map<String, Object> param);
}
