package com.flowengine.server.backend.service.createmodule.impl;

import com.flowengine.common.utils.entity.PublicFlowTableNameEntity;
import com.flowengine.common.utils.mapper.PublicFlowTableModuleMapper;
import com.flowengine.common.utils.mapper.PublicFlowTableNameMapper;
import com.flowengine.server.backend.dao.createmodule.TableManageDao;
import com.flowengine.server.backend.service.createmodule.TableManageService;
import com.flowengine.server.core.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public String add(PublicFlowTableNameEntity entity) {

        String moduleName = _flowTableModuleMapper.queryModuleNameById(entity.getTableModuleId());
        entity.setTableModuleName(moduleName);
        _flowTableNameMapper.insert(entity);
        return renderOpSuccessList(1);
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
