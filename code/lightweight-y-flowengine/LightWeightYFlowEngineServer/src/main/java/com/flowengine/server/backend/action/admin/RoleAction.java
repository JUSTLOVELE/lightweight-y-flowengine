package com.flowengine.server.backend.action.admin;

import cn.hutool.core.util.StrUtil;

import com.flowengine.common.utils.CommonConstant;
import com.flowengine.server.backend.service.admin.RoleService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.model.RoleTreeVO;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2022.08.26
 * @version 1.00.00
 * @history:
 */
@RestController
public class RoleAction extends BaseAction {

    @Autowired
    private RoleService _roleService;

    /**
     * 删除角色
     *
     * @param opId
     * @return
     */
    @GetMapping(value = "/roleAction/delete", produces = "application/json; charset=utf-8")
    public String delete(String opId) {
        return _roleService.delete(opId);
    }

    /**
     * 编辑角色
     *
     * @param opId
     * @return
     */
    @PostMapping(value = "/roleAction/edit", produces = "application/json; charset=utf-8")
    public String edit(String name, String orgId, String menuIds, String opId) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put(Constant.Key.ORG_ID, orgId);
        param.put(Constant.Key.NAME, name);
        param.put(Constant.Key.MENU_ID, menuIds);
        param.put(Constant.Key.OP_ID, opId);

        return _roleService.edit(param);
    }

    /**
     * 新增角色
     *
     * @param name
     * @param menuIds
     * @param request
     * @return
     */
    @PostMapping(value = "/roleAction/add", produces = "application/json; charset=utf-8")
    public String add(String name, String menuIds, HttpServletRequest request) {

        Map<String, Object> param = new HashMap<String, Object>();
        UserCache userCache = SessionUtils.getUserSession(request);
        param.put(Constant.Key.ORG_ID, userCache.getOrgId());
        param.put(Constant.Key.NAME, name);
        param.put(Constant.Key.MENU_ID, menuIds);

        return _roleService.add(param);
    }

    /**
     * 查询角色
     *
     * @return
     */
    @GetMapping(value = "/roleAction/query", produces = "application/json; charset=utf-8")
    public String query(String name, Integer limit, Integer page, HttpServletRequest request) {
        return _roleService.query(name, limit, page, SessionUtils.getUserSession(request));
    }

    /**
     * 根据登录用户的权限 创建角色可选菜单树
     *
     * @param session
     * @param request
     * @return
     */
    @GetMapping(value = "/roleAction/createRoleTree", produces = "application/json; charset=utf-8")
    public void createRoleTree(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        UserCache user = SessionUtils.getUserSession(request);
        String roleId = request.getParameter(Constant.Key.ROLE_ID);
        RoleTreeVO roleTreeVO = null;

        if (StrUtil.isNotEmpty(roleId)) {// 存在表示回填

            List<String> arrays = _roleService.findMenuIdByRoleId(roleId);
            roleTreeVO = _roleService.createMyRoleTree(user.getOpId(), arrays);

        } else {
            roleTreeVO = _roleService.createMyRoleTree(user.getOpId(), null);
        }

        List<RoleTreeVO> list = new ArrayList<RoleTreeVO>();
        list.add(roleTreeVO);
        renderJson(response, renderSuccessList(1, list, CommonConstant.SUCCESS_QUERY_MSG));
        clearList(list);
        roleTreeVO = null;
    }

    /**
     * 获得下拉框值
     * @return
     */
    @GetMapping(value = "/roleAction/getCombox", produces = "application/json; charset=utf-8")
    public String getCombox(HttpServletRequest request) {

        UserCache userSession = SessionUtils.getUserSession(request);
        return _roleService.getCombox(userSession.getOrgId());
    }
}
