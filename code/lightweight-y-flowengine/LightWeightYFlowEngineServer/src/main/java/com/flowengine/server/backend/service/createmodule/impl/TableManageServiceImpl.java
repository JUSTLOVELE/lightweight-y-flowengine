package com.flowengine.server.backend.service.createmodule.impl;

import cn.hutool.core.util.StrUtil;
import com.flowengine.common.utils.UUIDGenerator;
import com.flowengine.common.utils.entity.PublicFlowTableColumnEntity;
import com.flowengine.common.utils.entity.PublicFlowTableNameEntity;
import com.flowengine.common.utils.mapper.PublicFlowTableColumnMapper;
import com.flowengine.common.utils.mapper.PublicFlowTableModuleMapper;
import com.flowengine.common.utils.mapper.PublicFlowTableNameMapper;
import com.flowengine.server.backend.dao.createmodule.TableManageDao;
import com.flowengine.server.backend.service.createmodule.TableManageService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.utils.Constant;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023-07-24
 * @version 1.00.00
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TableManageServiceImpl extends BaseService implements TableManageService {

    @Resource
    private TableManageDao _tableManageDao;

    @Resource
    private PublicFlowTableNameMapper _flowTableNameMapper;

    @Resource
    private PublicFlowTableModuleMapper _flowTableModuleMapper;

    @Resource
    private PublicFlowTableColumnMapper _flowTableColumnMapper;

    private final static Log _logger = LogFactory.getLog(TableManageServiceImpl.class);

    @Override
    public String executeSQL(Map<String, Object> param) {

        String tableOpId = (String) param.get(Constant.Key.OP_ID);
        String tableName = (String) param.get(Constant.Key.TABLE_NAME);
        String sql = (String) param.get(Constant.Key.SQL);
        _tableManageDao.executeAlterSQL(sql);
        insertColumns(tableOpId, tableName);

        return renderOpSuccessList(1);
    }

    @Override
    public String edit(PublicFlowTableNameEntity entity) {

        PublicFlowTableNameEntity publicFlowTableNameEntity = _flowTableNameMapper.selectById(entity.getOpId());
        publicFlowTableNameEntity.setTableName(entity.getTableName());
        publicFlowTableNameEntity.setTableNameDesc(entity.getTableNameDesc());

        if(!entity.getTableModuleId().equals(publicFlowTableNameEntity.getTableModuleId())) {

            publicFlowTableNameEntity.setTableModuleId(entity.getTableModuleId());
            String moduleName = _flowTableModuleMapper.queryModuleNameById(entity.getTableModuleId());
            publicFlowTableNameEntity.setTableModuleName(moduleName);
        }

        _flowTableNameMapper.updateById(publicFlowTableNameEntity);

        if(StrUtil.isNotEmpty(entity.getSql())) {

            _tableManageDao.executeAlterSQL(entity.getSql());
            insertColumns(publicFlowTableNameEntity);
        }

        return renderQuerySuccessList(1);
    }

    private void insertColumns(PublicFlowTableNameEntity entity) {
        insertColumns(entity.getOpId(), entity.getTableName());
    }

    private void insertColumns(String tableOpId, String tableName) {

        Map<String,Object> p = new HashMap<>();
        p.put(Constant.Column.TABLE_OP_ID, tableOpId);
        _flowTableColumnMapper.deleteByMap(p);
        List<Map<String, Object>> columns = _tableManageDao.queryInformationSchema(tableName);

        for(Map<String, Object> column: columns) {

            PublicFlowTableColumnEntity columnEntity = new PublicFlowTableColumnEntity();
            columnEntity.setOpId(UUIDGenerator.getUUID());
            columnEntity.setTableName(tableName);
            columnEntity.setTableOpId(tableOpId);
            columnEntity.setColumnName((String) column.get(Constant.Column.COLUMN_NAME));
            columnEntity.setColumnType((String) column.get(Constant.Column.DATA_TYPE));
            _flowTableColumnMapper.insert(columnEntity);
        }
    }

    @Override
    public String columnTableQuery(Map<String, Object> param) {

        List<Map<String, Object>> datas = _tableManageDao.columnQuery(param);

        if(datas != null && datas.size() > 0) {
            return renderQuerySuccessList(datas.size(), datas);
        }else {
            return renderQuerySuccessList(0, datas);
        }
    }

    @Override
    public String delete(Map<String, Object> param) {

        String tableName = (String) param.get(Constant.Key.TABLE_NAME);
        int total = _tableManageDao.queryTableCount(tableName);

        if(total > 0) {
            return renderPrintFailureList("该表已经产生业务数据,请联系超级管理员进行删除!");
        }

        _tableManageDao.executeDropSQL(tableName);
        String opId = (String) param.get(Constant.Key.OP_ID);
        Map<String,Object> p = new HashMap<>();
        p.put(Constant.Column.TABLE_OP_ID, opId);
        _flowTableColumnMapper.deleteByMap(p);
        _flowTableNameMapper.deleteById(opId);

        return renderOpSuccessList(1);
    }

    @Override
    public String add(PublicFlowTableNameEntity entity) {

        try {

            String moduleName = _flowTableModuleMapper.queryModuleNameById(entity.getTableModuleId());
            entity.setTableModuleName(moduleName);
            _tableManageDao.executeCreateSQL(entity.getSql());
            _flowTableNameMapper.insert(entity);
            insertColumns(entity);

            return renderOpSuccessList(1);
        }catch (Exception e) {
            _logger.error("", e);
            return renderPrintFailureList(e.getMessage());
        }
    }

    @Override
    public String tableQuery(Map<String, Object> param) {

        List<Map<String, Object>> datas = _tableManageDao.query(param);

        if(datas != null && datas.size() > 0) {
            return renderQuerySuccessList(_tableManageDao.queryTotal(param), datas);
        }else {
            return renderQuerySuccessList(0, datas);
        }
    }
}
