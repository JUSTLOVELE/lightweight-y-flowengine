package com.flowengine.server.backend.action.createmodel;

import com.flowengine.common.utils.UUIDGenerator;
import com.flowengine.common.utils.entity.createmodel.PublicFlowTableNameEntity;
import com.flowengine.server.backend.service.createmodule.TableManageService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.utils.Constant;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * 表-列查询
     * @param opId
     * @return
     */
    @GetMapping(value = "/tableManageAction/tableFlowInstanceQuery", produces = "application/json; charset=utf-8")
    public String tableFlowInstanceQuery(String opId) {

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.OP_ID, opId);

        return _tableManageService.tableFlowInstanceQuery(param);
    }

    /**
     * 获得下拉框
     * @return
     */
    @GetMapping(value = "/tableManageAction/getCombobox", produces = "application/json;charset=utf-8")
    public String getCombobox() {
        return _tableManageService.getCombobox();
    }

    /**
     * 执行SQL
     * @param tableOpId
     * @param sql
     * @return
     */
    @PostMapping(value = "/tableManageAction/excuteSQL", produces = "application/json; charset=utf-8")
    public String excuteSQL(@RequestParam(required = true) String tableOpId,
                            @RequestParam(required = true) String tableName,
                            @RequestParam(required = true) String sql) {

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.OP_ID, tableOpId);
        param.put(Constant.Key.TABLE_NAME, tableName);
        param.put(Constant.Key.SQL, sql);

        return _tableManageService.executeSQL(param);
    }

    /**
     * 新增表
     * @param entity
     * @param request
     * @return
     */
    @PostMapping(value = "/tableManageAction/edit", produces = "application/json; charset=utf-8")
    public String edit(PublicFlowTableNameEntity entity, HttpServletRequest request) {
        return _tableManageService.edit(entity);
    }

    /**
     * 表-列查询
     * @param opId
     * @return
     */
    @GetMapping(value = "/tableManageAction/columnTableQuery", produces = "application/json; charset=utf-8")
    public String columnTableQuery(String opId) {

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.OP_ID, opId);

        return _tableManageService.columnTableQuery(param);
    }

    /**
     * 删除表
     * @param opId
     * @return
     */
    @PostMapping(value = "/tableManageAction/delete", produces = "application/json; charset=utf-8")
    public String delete(String opId, String tableName) {

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.OP_ID, opId);
        param.put(Constant.Key.TABLE_NAME, tableName);

        return _tableManageService.delete(param);
    }

    /**
     * 新增表
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
