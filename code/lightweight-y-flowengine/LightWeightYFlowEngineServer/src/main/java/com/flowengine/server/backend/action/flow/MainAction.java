package com.flowengine.server.backend.action.flow;

import com.flowengine.server.backend.service.flow.MainService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:流程主表
 * @author yangzl 2023.02.22
 * @version 1.00.00
 * @history:
 */
@RestController
public class MainAction extends BaseAction {

    @Autowired
    private MainService _mainService;

    /**
     * flowManage页面查询
     * @param flowName
     * @param startTime
     * @param endTime
     * @param limit
     * @param page
     * @param request
     * @return
     */
    @GetMapping(value = "/mainAction/query", produces = "application/json; charset=utf-8")
    public String query(@RequestParam(required = false) String flowName,
                        String startTime,
                        String endTime,
                        @RequestParam(required = false) Integer limit,
                        @RequestParam(required = false)  Integer page,
                        HttpServletRequest request) {

        UserCache userSession = SessionUtils.getUserSession(request);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(Constant.Key.NAME, flowName);
        param.put(Constant.Key.PAGE, page);
        param.put(Constant.Key.LIMIT, limit);
        param.put(SessionUtils.USER_SESSION, userSession);

        return _mainService.query(param);
    }
}
