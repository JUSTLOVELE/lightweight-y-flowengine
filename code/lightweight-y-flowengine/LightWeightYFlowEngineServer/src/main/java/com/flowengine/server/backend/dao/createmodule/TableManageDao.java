package com.flowengine.server.backend.dao.createmodule;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023-07-19
 * @version 1.00.00
 * @history:
 */
public interface TableManageDao {


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
