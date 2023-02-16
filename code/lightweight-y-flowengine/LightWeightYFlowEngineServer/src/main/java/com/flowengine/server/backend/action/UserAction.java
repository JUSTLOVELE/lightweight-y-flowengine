package com.flowengine.server.backend.action;

import com.flowengine.common.utils.CommonConstant;
import com.flowengine.server.backend.service.UserService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2022.08.24
 * @version 1.00.00
 * @history:
 */
@RestController
public class UserAction extends BaseAction {

    @Autowired
    private UserService _userService;

    /**
     * 获取待办
     * @return
     */
    @GetMapping(value = "/userAction/queryToDeal", produces = "application/json; charset=utf-8")
    public String queryToDeal() {

//        List<Map<String, Object>> notice = _noticeService.queryAll();
//        int noticeSize = notice.size();
//        List<Map<String, Object>> pops = _popActiveService.queryPublishData();
//        int popsSize = pops.size();
//        notice.addAll(pops);
//        Map<String, Object> result = new HashMap<>();
//        result.put("code", 100);
//        result.put("success", true);
//        result.put("number", noticeSize + popsSize);
//        result.put("datas", notice);
         return renderQuerySuccessList(1);
    }


    /**
     * 获取作品类型的下拉框
     * @return
     */
    @GetMapping(value = "/userAction/getUserCombox", produces = "application/json; charset=utf-8")
    public String getUserCombox() {
       return _userService.getUserCombox();
    }

    /**
     * 编辑用户
     * @return
     */
    @RequestMapping(value = "/userAction/editSelf", produces = "application/json; charset=utf-8")
    public String editSelf(String pwd,
                           String oldPwd,
                           String userId,
                           String userName,
                           String userPhone,
                           String orgId,
                           HttpServletRequest request) {

        Map<String, Object> data = new HashMap<>();
        data.put(Constant.Key.NAME, userName);
        data.put(Constant.Key.USER_ID, userId);
        data.put(Constant.Key.USER_PHONE, userPhone);
        data.put(Constant.Key.ORG_ID, orgId);
        data.put(SessionUtils.USER_SESSION, SessionUtils.getUserSession(request));
        data.put(Constant.Key.OLD_PWD, oldPwd);
        data.put(Constant.Key.PASSWORD, pwd);

        return _userService.editSelf(request,data);
    }

    /**
     * 获取缓存中的用户信息
     * @return
     */
    @RequestMapping(value = "/userAction/getUserInfo", produces = "application/json; charset=utf-8")
    public String getUserInfo(HttpServletRequest request) {

        UserCache userSession = SessionUtils.getUserSession(request);
        Map<String, Object> data = new HashMap<>();
        data.put(Constant.Key.NAME, userSession.getUserName());
        data.put(Constant.Key.USER_ID, userSession.getUserId());
        data.put(Constant.Key.USER_PHONE, userSession.getUserPhone());
        List<Map<String, Object>> datas = new ArrayList<>();
        datas.add(data);

        return renderQuerySuccessList(1, datas);
    }

    /**
     * 编辑
     * @param userId
     * @param userName
     * @param userPhone
     * @param orgId
     * @param sex
     * @param opId
     * @return
     */
    @PostMapping(value = "/userAction/edit", produces = "application/json; charset=utf-8")
    public String edit(String userId,
                      String userName,
                      String userPhone,
                      String orgId,
                      String sex,
                       String userCategory,
                       String roleId,
                      String opId) {

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.NAME, userName);
        param.put(Constant.Key.USER_ID, userId);
        param.put(Constant.Key.USER_PHONE, userPhone);
        param.put(Constant.Key.ORG_ID, orgId);
        param.put(Constant.Key.SEX, sex);
        param.put(Constant.Key.OP_ID, opId);
        param.put(Constant.Key.ROLE_ID, roleId);
        param.put(Constant.Key.USER_CATEGORY, userCategory);
        _userService.edit(param);

        return renderSuccessList(1, CommonConstant.SUCCESS_EDIT_MSG);
    }

    /**
     * 删除用户
     * @param opId
     * @return
     */
    @RequestMapping(value = "/userAction/delete", produces = "application/json; charset=utf-8")
    public String delete(@RequestParam(required = true) String opId) {
        return _userService.delete(opId);
    }

    /**
     * 新增
     * @param userId
     * @param password
     * @param userName
     * @param userPhone
     * @param orgId
     * @param sex
     * @return
     */
    @PostMapping(value = "/userAction/add", produces = "application/json; charset=utf-8")
    public String add(String userId,
                      String password,
                      String userName,
                      String userPhone,
                      String orgId,
                      String roleId,
                      String userCategory,
                      String sex) {

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.NAME, userName);
        param.put(Constant.Key.USER_ID, userId);
        param.put(Constant.Key.USER_PHONE, userPhone);
        param.put(Constant.Key.PASSWORD, password);
        param.put(Constant.Key.ORG_ID, orgId);
        param.put(Constant.Key.SEX, sex);
        param.put(Constant.Key.ROLE_ID, roleId);
        param.put(Constant.Key.USER_CATEGORY, userCategory);
        _userService.add(param);

        return renderSuccessList(1, CommonConstant.SUCCESS_SAVE_MSG);
    }

    /**
     * 查询登录用户
     * @param name
     * @param limit
     * @param page
     */
    @GetMapping(value = "/userAction/query", produces = "application/json; charset=utf-8")
    public String query(@RequestParam(required = false) String name,
                        @RequestParam(required = true) Integer limit,
                        @RequestParam(required = true)  Integer page,
                        HttpServletRequest request) {

        UserCache userSession = SessionUtils.getUserSession(request);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(Constant.Key.NAME, name);
        param.put(Constant.Key.PAGE, page);
        param.put(Constant.Key.LIMIT, limit);
        param.put(SessionUtils.USER_SESSION, userSession);

        return _userService.query(param);
    }
}
