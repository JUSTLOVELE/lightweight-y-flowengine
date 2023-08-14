package com.flowengine.server.backend.service.flow;

import com.flowengine.server.model.UserCache;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
public interface FlowRoleService {

    /**
     * 查询角色
     * @param name
     * @param limit
     * @param page
     * @return
     */
    public String query(String name, Integer limit, Integer page, UserCache userCache);
}
