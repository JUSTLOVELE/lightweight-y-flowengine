package com.flowengine.server.backend.action.createmodel;

import com.flowengine.common.utils.entity.PublicFlowTableModuleEntity;
import com.flowengine.common.utils.entity.PublicFlowTableNameEntity;
import com.flowengine.server.backend.service.createmodule.TableManageService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.UUIDGenerator;
import edu.princeton.cs.algs4.ST;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023.07.24
 * @version 1.00.00
 * @history:
 */
@RestController
public class TableManageAction extends BaseAction {

    @Resource
    private TableManageService _tableManageService;

    /**
     * 新增表模块
     * @param entity
     * @param request
     * @return
     */
    @PostMapping(value = "/tableManageAction/add", produces = "application/json; charset=utf-8")
    public String add(PublicFlowTableNameEntity entity, HttpServletRequest request) {

        entity.setOpId(UUIDGenerator.getUUID());
        return _tableManageService.add(entity);
    }

    /**
     * 表格查询
     * @param limit
     * @param page
     * @return
     */
    @GetMapping(value = "/tableManageAction/tableQuery", produces = "application/json; charset=utf-8")
    public String tableQuery(Integer limit, Integer page, String tableNameDesc, String tableName) {

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.LIMIT, limit);
        param.put(Constant.Key.PAGE, page);
        param.put(Constant.Key.TABLE_NAME, tableName);
        param.put(Constant.Key.TABLE_NAME_DESC, tableNameDesc);

        return _tableManageService.tableQuery(param);
    }
}