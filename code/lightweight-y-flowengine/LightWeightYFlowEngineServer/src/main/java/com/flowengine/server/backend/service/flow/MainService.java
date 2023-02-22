package com.flowengine.server.backend.service.flow;

import java.util.Map;

/**
 * @Description:流程主表
 * @author yangzl 2023.02.22
 * @version 1.00.00
 * @history:
 */
public interface MainService {

    /**
     * 查询
     * @param param
     * @return
     */
    public String query(Map<String, Object> param);
}
