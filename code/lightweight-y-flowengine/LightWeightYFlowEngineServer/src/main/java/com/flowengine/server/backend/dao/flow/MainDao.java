package com.flowengine.server.backend.dao.flow;


import java.util.List;
import java.util.Map;

public interface MainDao {

    /**
     * 总数
     * @param param
     * @return
     */
    public Integer queryFlowTotal(Map<String, Object> param);

    /**
     * 查询流程
     * @param param
     * @return
     */
    public List<Map<String, Object>> queryFlow(Map<String, Object> param);
}
