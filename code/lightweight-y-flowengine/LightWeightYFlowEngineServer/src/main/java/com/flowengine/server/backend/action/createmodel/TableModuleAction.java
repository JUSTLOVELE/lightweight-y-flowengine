package com.flowengine.server.backend.action.createmodel;

import com.flowengine.common.utils.UUIDGenerator;
import com.flowengine.common.utils.entity.createmodel.PublicFlowTableModuleEntity;
import com.flowengine.server.backend.service.createmodule.TableModuleService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.utils.Constant;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023-06-30
 * @version 1.00.00
 * @history:
 */
@RestController
public class TableModuleAction extends BaseAction {

    @Resource
    private TableModuleService _tableModuleService;

    /**
     * 查询角色
     *
     * @return
     */
    @GetMapping(value = "/tableModuleAction/getCombobox", produces = "application/json; charset=utf-8")
    public String getCombobox() {
        return _tableModuleService.getCombobox();
    }

    /**
     * 删除角色
     *
     * @param opId
     * @return
     */
    @PostMapping(value = "/tableModuleAction/delete", produces = "application/json; charset=utf-8")
    public String delete(String opId) {
        return _tableModuleService.delete(opId);
    }

    /**
     * 新增表模块
     * @param entity
     * @param request
     * @return
     */
    @PostMapping(value = "/tableModuleAction/edit", produces = "application/json; charset=utf-8")
    public String edit(PublicFlowTableModuleEntity entity, HttpServletRequest request) {
        return _tableModuleService.edit(entity);
    }

    /**
     * 新增表模块
     * @param entity
     * @param request
     * @return
     */
    @PostMapping(value = "/tableModuleAction/add", produces = "application/json; charset=utf-8")
    public String add(PublicFlowTableModuleEntity entity, HttpServletRequest request) {
        entity.setOpId(UUIDGenerator.getUUID());
        return _tableModuleService.add(entity);
    }

    /**
     * 查询角色
     *
     * @return
     */
    @GetMapping(value = "/tableModuleAction/query", produces = "application/json; charset=utf-8")
    public String query(String moduleName, Integer limit, Integer page, HttpServletRequest request) {

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.LIMIT, limit);
        param.put(Constant.Key.PAGE, page);
        param.put(Constant.Key.MODULE_NAME, moduleName);

        return _tableModuleService.query(param);
    }
}
