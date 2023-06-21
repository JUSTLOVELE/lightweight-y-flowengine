package com.flowengine.server.backend.action.admin;


import com.flowengine.common.utils.CommonConstant;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * 查询登录用户
     * @param name
     * @param limit
     * @param page
     */
    @GetMapping(value = "/query", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String query(@RequestParam(required = false) String name,
                        @RequestParam(required = true) Integer limit,
                        @RequestParam(required = true)  Integer page) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put(Constant.Key.NAME, name);
        param.put(Constant.Key.PAGE, page);
        param.put(Constant.Key.LIMIT, limit);

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
