package com.flowengine.server.backend.service.createmodule.impl;

import com.flowengine.common.utils.entity.createmodel.PublicFlowTableModuleEntity;
import com.flowengine.common.utils.mapper.createmodel.PublicFlowTableModuleMapper;
import com.flowengine.server.backend.dao.createmodule.TableModuleDao;
import com.flowengine.server.backend.service.createmodule.TableModuleService;
import com.flowengine.server.core.Base;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023-06-30
 * @version 1.00.00
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TableModuleServiceImpl extends Base implements TableModuleService {

    @Resource
    private TableModuleDao _tableModuleDao;

    @Autowired
    private PublicFlowTableModuleMapper _publicFlowTableModuleMapper;

    @Override
    public String edit(PublicFlowTableModuleEntity tableModuleEntity) {

        PublicFlowTableModuleEntity entity = _publicFlowTableModuleMapper.selectById(tableModuleEntity.getOpId());

        if(!entity.getModuleName().equals(tableModuleEntity.getModuleName())) {
            entity.setModuleName(tableModuleEntity.getModuleName());
        }

        if(entity.getAuthority() != tableModuleEntity.getAuthority() && tableModuleEntity.getAuthority() != null) {
            entity.setAuthority(tableModuleEntity.getAuthority());
        }

        _publicFlowTableModuleMapper.updateById(entity);
        return renderOpSuccessList(1);
    }

    @Override
    public String getCombobox() {
        return getJSON(_tableModuleDao.getCombobox());
    }

    @Override
    public String delete(String opId) {

        _publicFlowTableModuleMapper.deleteById(opId);
        return renderOpSuccessList(1);
    }

    @Override
    public String add(PublicFlowTableModuleEntity tableModuleEntity) {

        _publicFlowTableModuleMapper.insert(tableModuleEntity);
        return renderOpSuccessList(1);
    }

    @Override
    public String query(Map<String, Object> param) {

        List<Map<String, Object>> datas = _tableModuleDao.query(param);

        if(datas != null && datas.size() > 0) {
            return renderQuerySuccessList(_tableModuleDao.queryTotal(param), datas);
        }else {
            return renderQuerySuccessList(0, datas);
        }
    }
}
