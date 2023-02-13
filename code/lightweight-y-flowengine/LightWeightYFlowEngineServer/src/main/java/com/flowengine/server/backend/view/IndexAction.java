package com.flowengine.server.backend.view;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yangzl 2021.07.14
 * @version 1.00.00
 * @Description:
 * @history:
 */
@RestController
public class IndexAction {

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("html/index");
    }

    @RequestMapping("/default")
    public ModelAndView defaultPage() {
        return new ModelAndView("html/default");
    }

    /**
     * 登录页面
     * http://localhost:8080/flow/login
     *
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView home() {
//        return "/html/login";
        return new ModelAndView("html/login");
    }

    @RequestMapping("/reLogin")
    public ModelAndView reLogin() {
//        return "/html/login";
        return new ModelAndView("html/login");
    }
}