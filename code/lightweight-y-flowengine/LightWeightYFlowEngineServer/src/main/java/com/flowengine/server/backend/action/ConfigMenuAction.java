package com.flowengine.server.backend.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangzl 2021.07.05
 * @version 1.00.00
 * @Description:
 * @history:
 */
@RequestMapping("/configMenuAction")
@Controller
public class ConfigMenuAction {

    public final static String ERROR_404 = "html/404";

    public final static String INDEX = "html/index";

    @RequestMapping("editPwd")
    public String editPwd() {
        return "html/admin/editPwd";
    }

    //用户管理
    @RequestMapping("userPage")
    public String userPage() {
        return "html/admin/userPage";
    }

    //角色管理
    @RequestMapping("rolePage")
    public String rolePage() {
        return "html/admin/rolePage";
    }


}
