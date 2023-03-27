package com.flowengine.server.backend.action.admin;

import cn.hutool.core.util.StrUtil;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestAction extends BaseAction {

    @RequestMapping(value = "/test/test1", produces = "application/json; charset=utf-8")
    public String queryToDeal(HttpServletRequest request, @RequestBody String opId) {

        HttpSession session = request.getSession();
        Object l = session.getAttribute("l");
        System.out.println(session.getId());
        System.out.println("opId=" + opId);

        if(l == null) {
            session.setAttribute("l", "test");
            return renderFailureList("没数据");
        }else {
            System.out.println("test");
            return renderQuerySuccessList(1);
        }
    }
}
