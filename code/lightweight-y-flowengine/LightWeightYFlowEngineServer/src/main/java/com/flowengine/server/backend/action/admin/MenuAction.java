package com.flowengine.server.backend.action.admin;


import com.flowengine.common.utils.CommonConstant;
import com.flowengine.common.utils.entity.PublicMenuEntity;
import com.flowengine.server.backend.service.admin.MenuService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.model.MenuVO;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2019-12-03
 * @version 1.00.00
 * @history:
 */
@RequestMapping("/menuAction")
@Controller
public class MenuAction extends BaseAction {

    @Autowired
    private MenuService _menuService;

    private final static Log _logger = LogFactory.getLog(MenuAction.class);

    /**
     * 删除
     * @param opId
     * @return
     */
    @PostMapping(value = "/delete", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String delete(String opId) {
        return _menuService.delete(opId);
    }

    /**
     * 编辑
     * @param menuEntity
     * @return
     */
    @PostMapping(value = "/edit", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String edit(PublicMenuEntity menuEntity) {
        return _menuService.edit(menuEntity);
    }

    /**
     * 新增
     * @param menuEntity
     * @return
     */
    @PostMapping(value = "/add", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String add(PublicMenuEntity menuEntity) {

        menuEntity.setButtonType(21);//默认值
        menuEntity.setSys(1);//默认值
        menuEntity.setCategory(0); //默认值

        return _menuService.add(menuEntity);
    }


    /**
     * 查询登录用户
     * @param limit
     * @param opId
     * @param parentId
     * @param menuText
     * @param page
     * @return
     */
    @GetMapping(value = "/query", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String query(@RequestParam(required = true) Integer limit,
                        String opId,
                        String parentId,
                        String menuText,
                        @RequestParam(required = true)  Integer page) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put(Constant.Key.PAGE, page);
        param.put(Constant.Key.LIMIT, limit);
        param.put(Constant.Key.OP_ID, opId);
        param.put(Constant.Key.PARENT_ID, parentId);
        param.put(Constant.Key.MENU_TEXT, menuText);

        return _menuService.query(param);
    }

    /**
     * 	创建导航菜单
     */
    @RequestMapping(value = "/createMenu")
    public void createMenu(HttpServletRequest request, HttpServletResponse response) {

        try {

            UserCache user = SessionUtils.getUserSession(request);
            MenuVO menuVO = _menuService.createMenuTree(user);

            if (menuVO != null) {

                List<MenuVO> list = menuVO.getChildren();
                renderJson(response, renderSuccessList(1, list, CommonConstant.SUCCESS_QUERY_MSG));
                clearList(list);
                menuVO = null;
            }
        }catch (Exception e) {
            renderJson(response, renderFailureList(e.getMessage()));
            _logger.error("", e);
        }
    }
}
