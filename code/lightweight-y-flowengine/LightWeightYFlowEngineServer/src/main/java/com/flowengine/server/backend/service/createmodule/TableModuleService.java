package com.flowengine.server.backend.service.createmodule;

import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023-06-30
 * @version 1.00.00
 * @history:
 */
public interface TableModuleService {

    /**
     * 查询
     * @param param
     * @return
     */
    public String query(Map<String, Object> param);
}
