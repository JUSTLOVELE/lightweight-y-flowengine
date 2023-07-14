package com.flowengine.server.backend.view;

import cn.hutool.core.util.StrUtil;
import com.flowengine.common.utils.CommonConstant;
import com.flowengine.common.utils.RSA;
import com.flowengine.common.utils.entity.PublicUserEntity;
import com.flowengine.server.backend.service.admin.TokenService;
import com.flowengine.server.backend.service.admin.UserService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.env.YmlProjectConfig;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.DateUtil;
import com.flowengine.server.utils.SessionUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2019-11-29
 * @version 1.00.00
 * @history:
 */
@RestController
public class LoginAction extends BaseAction {

    @Autowired
    private UserService _userService;

    @Resource
    private TokenService _tokenService;

    @Resource
    private YmlProjectConfig _ymlProjectConfig;

    @RequestMapping(value = "/loginAction/error", produces = "application/json; charset=utf-8")
    public String error() {
        return ConfigMenuAction.ERROR_404;
    }

    @RequestMapping("/loginAction/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession();
        session.removeAttribute(SessionUtils.USER_SESSION);
        return renderLogOutSuccess();
    }

    @PostMapping(value = "/loginAction/login", produces = "application/json; charset=utf-8")
    public String login(HttpServletRequest request,
                        String userId,
                        String password,
                        HttpServletResponse httpServletResponse) {

//        JSONObject entries = JSONUtil.parseObj(param);
//        String userId = entries.getStr(Constant.Key.USERID);
//        String password = entries.getStr(Constant.Key.PASSWORD);

        if(StrUtil.isEmpty(userId)) {
            return  renderFailureList(CommonConstant.USER_ID_IS_NOT_NULL);
        }

        if(StrUtil.isEmpty(password)) {
            return renderFailureList(CommonConstant.PWD_IS_NOT_NULL);
        }

        PublicUserEntity entity = _userService.getPublicUserEntity(userId, password);

        if(entity == null) {
            return  renderFailureList(CommonConstant.NOT_FIND_USER);
        }

        _tokenService.getLoginToken(entity);
        UserCache user = new UserCache(entity);
        List<Map<String, Object>> roleIds = _userService.queryRoleId(user.getOpId());

        if(roleIds != null && roleIds.size() > 0) {

            List<String> rs = new ArrayList<String>();

            for(Map<String, Object> roleId : roleIds) {

                rs.add(roleId.get(Constant.Key.ROLE_ID).toString());
            }

            user.setRoleIds(rs);
        }

        SessionUtils.addUserSession(entity.getAccessToken(), user);
        List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(Constant.Key.USER_NAME, ( user.getUserName() != null) ? user.getUserName() : user.getUserId());
        String timestamp = DateUtil.toString(user.getLastLogin(), DateUtil.YMDHMS);
        String content = timestamp + ";" + user.getAccessToken() + ";" + user.getRefreshToken() + ";";
        data.put(Constant.Token.SIGN, RSA.signBySHA256WithRSA(content, _ymlProjectConfig.getSk()));
        data.put(Constant.Token.TIMESTAMP, timestamp);
        data.put(Constant.Token.ACCESS_TOKEN, user.getAccessToken());
        data.put(Constant.Token.REFRESH_TOKEN, user.getRefreshToken());
        data.put(Constant.Token.DARK_MODE, user.getDarkMode());
        data.put(Constant.Token.OP_ID, user.getOpId());
        datas.add(data);
        return renderQuerySuccessList(1, datas);
    }

//
//    @RequestMapping(value = "/loginAction/login2", produces = "application/json; charset=utf-8")
//    public String login2(HttpServletRequest request, String userId, String password, HttpServletResponse httpServletResponse) {
//
//        if(StrUtil.isEmpty(userId)) {
//            return  renderFailureList(CommonConstant.USER_ID_IS_NOT_NULL);
//        }
//
//        if(StrUtil.isEmpty(password)) {
//            return renderFailureList(CommonConstant.PWD_IS_NOT_NULL);
//        }
//
//        PublicUserEntity entity = _userService.getPublicUserEntity(userId, password);
//
//        if(entity == null) {
//            return  renderFailureList(CommonConstant.NOT_FIND_USER);
//        }
//
//        UserCache user = new UserCache(entity);
//        List<Map<String, Object>> roleIds = _userService.queryRoleId(user.getOpId());
//
//        if(roleIds != null && roleIds.size() > 0) {
//
//            List<String> rs = new ArrayList<String>();
//
//            for(Map<String, Object> roleId : roleIds) {
//
//                rs.add(roleId.get(Constant.Key.ROLE_ID).toString());
//            }
//
//            user.setRoleIds(rs);
//        }
//
//        SessionUtils.addUserSession(request, user);
//        List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put(Constant.Key.USER_NAME, ( user.getUserName() != null) ? user.getUserName() : user.getUserId());
//        datas.add(data);
//        return renderQuerySuccessList(1, datas);
//    }
}
