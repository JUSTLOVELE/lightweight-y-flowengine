package com.flowengine.server.backend.dao.flow;

import com.flowengine.server.model.UserCache;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
public interface FlowRoleDao {

    /**
     * 查询角色总数
     * @param name
     * @return
     */
    public Integer queryTotal(String name, UserCache userCache);

    /**
     * 角色查询
     * @param name
     * @param limit
     * @param page
     * @return
     */
    public List<Map<String, Object>> query(String name, Integer limit, Integer page, UserCache userCache);
}
