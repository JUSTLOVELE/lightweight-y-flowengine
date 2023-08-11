package com.flowengine.server.backend.action.flow;

import com.flowengine.server.backend.service.flow.MainService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.entity.PublicFlowMainEntity;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.SessionUtils;
import com.flowengine.server.utils.UUIDGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
     * 新增
     * @param mainEntity
     * @param children
     * @return
     */
    @PostMapping(value = "/mainAction/add", produces = "application/json; charset=utf-8")
    public String add(PublicFlowMainEntity mainEntity, String children, HttpServletRequest request) {

        UserCache userSession = SessionUtils.getUserSession(request);
        mainEntity.setOpId(UUIDGenerator.getUUID());
        mainEntity.setCreateTime(new Date());
        mainEntity.setCreateUserId(userSession.getOpId());
        mainEntity.setCreateUserName(userSession.getUserName());
        mainEntity.setOrgId(userSession.getOrgId());
        mainEntity.setDeptId(userSession.getDeptId());

        return _mainService.add(mainEntity, children);
    }

    /**
     * flowPage页面查询
     * @param mainName
     * @param isStop
     * @param deptId
     * @param limit
     * @param page
     * @param request
     * @return
     */
    @GetMapping(value = "/mainAction/query", produces = "application/json; charset=utf-8")
    public String query(@RequestParam(required = false) String mainName,
                        Integer isStop,
                        String deptId,
                        @RequestParam(required = false) Integer limit,
                        @RequestParam(required = false)  Integer page,
                        HttpServletRequest request) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put(Constant.Key.NAME, mainName);
        param.put(Constant.Key.PAGE, page);
        param.put(Constant.Key.LIMIT, limit);
        param.put(Constant.Key.DEPT_ID, deptId);
        param.put(Constant.Key.IS_STOP, isStop);

        return _mainService.query(param);
    }
}
