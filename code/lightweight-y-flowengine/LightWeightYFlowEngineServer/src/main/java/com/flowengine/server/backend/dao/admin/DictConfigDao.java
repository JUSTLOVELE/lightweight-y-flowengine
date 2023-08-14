package com.flowengine.server.backend.dao.admin;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @history:
 */
public interface DictConfigDao {

    /**
     * 页面查询总数
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
