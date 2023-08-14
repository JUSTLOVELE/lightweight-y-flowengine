package com.flowengine.server.backend.action.admin;

import com.flowengine.server.backend.service.admin.OrgService;
import com.flowengine.server.core.BaseAction;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @history:
 */
@RestController
public class OrgAction extends BaseAction {

    @Resource
    private OrgService _orgService;

    /**
     * 获取下拉框
     * @return
     */
    @GetMapping(value = "/orgAction/getCombobox", produces = "application/json;charset=utf-8")
    public String getCombobox() {
        return _orgService.getCombobox();
    }

}
