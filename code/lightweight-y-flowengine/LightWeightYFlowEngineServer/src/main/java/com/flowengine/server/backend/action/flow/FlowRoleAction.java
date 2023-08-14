package com.flowengine.server.backend.action.flow;

import com.flowengine.server.backend.service.flow.FlowRoleService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.utils.SessionUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
@RestController
public class FlowRoleAction extends BaseAction {

    @Resource
    private FlowRoleService _flowRoleService;

    /**
     * 查询
     *
     * @return
     */
    @GetMapping(value = "/flowRoleAction/query", produces = "application/json; charset=utf-8")
    public String query(String name, Integer limit, Integer page, HttpServletRequest request) {
        return _flowRoleService.query(name, limit, page, SessionUtils.getUserSession(request));
    }

}
