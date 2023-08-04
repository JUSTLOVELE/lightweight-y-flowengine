package com.flowengine.server.backend.service.flow;

import java.util.Map;

/**
 * @Description:科室
 * @author yangzl 2023.03.22
 * @version 1.00.00
 * @history:
 */
public interface DeptService {

    /**
     * 获取科室下拉框
     * @return
     */
    public String getCombobox();

    /**
     *
     * @param param
     * @return
     */
    public String selectQuery(Map<String, Object> param);
}
