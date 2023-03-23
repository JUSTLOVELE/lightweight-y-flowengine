package com.flowengine.server.backend.action.flow;

import com.flowengine.server.backend.service.flow.DeptService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.SessionUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:科室
 * @author yangzl 2023.03.22
 * @version 1.00.00
 * @history:
 */
@RestController
public class DeptAction extends BaseAction {

    @Resource
    private DeptService _deptService;

    @GetMapping(value = "/deptAction/selectQuery", produces = "application/json;charset=utf-8")
    public String selectQuery(HttpServletRequest request) {

        UserCache userSession = SessionUtils.getUserSession(request);
        Map<String, Object> param = new HashMap<>();
        param.put(SessionUtils.USER_SESSION, userSession);

        return _deptService.selectQuery(param);
    }
}
