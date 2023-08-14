package com.flowengine.server.backend.service.flow.impl;

import com.flowengine.server.backend.dao.flow.FlowRoleDao;
import com.flowengine.server.backend.service.flow.FlowRoleService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.model.UserCache;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
@Transactional(rollbackFor = Exception.class)
public class FlowRoleServiceImpl extends BaseService implements FlowRoleService {

    @Resource
    private FlowRoleDao _flowRoleDao;

    @Override
    public String query(String name, Integer limit, Integer page, UserCache userCache) {

        if(limit == null) {
            return renderFailureList("limit为空");
        }

        if(page == null) {
            return renderFailureList("page为空");
        }

        List<Map<String, Object>> roles = _flowRoleDao.query(name, limit, page, userCache);

        if(roles != null && roles.size() > 0) {
            return renderQuerySuccessList(_flowRoleDao.queryTotal(name, userCache), roles);
        }else {
            return renderQuerySuccessList(0, roles);
        }
    }
}
