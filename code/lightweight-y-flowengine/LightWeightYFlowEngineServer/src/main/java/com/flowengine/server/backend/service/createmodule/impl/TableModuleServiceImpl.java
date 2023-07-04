package com.flowengine.server.backend.service.createmodule.impl;

import com.flowengine.common.utils.entity.PublicFlowTableModuleEntity;
import com.flowengine.common.utils.mapper.PublicFlowTableModuleMapper;
import com.flowengine.server.backend.dao.createmodule.TableModuleDao;
import com.flowengine.server.backend.service.createmodule.TableModuleService;
import com.flowengine.server.core.Base;
import jakarta.annotation.Resource;
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

    @Resource
    private PublicFlowTableModuleMapper _publicFlowTableModuleMapper;

    @Override
    public String delete(String opId) {

        _publicFlowTableModuleMapper.deleteById(opId);
        return renderDeleteSuccessList(1);
    }

    @Override
    public String edit(PublicFlowTableModuleEntity entity) {

        PublicFlowTableModuleEntity publicFlowTableModuleEntity = _publicFlowTableModuleMapper.selectById(entity.getOpId());

        if(!publicFlowTableModuleEntity.getModuleName().equals(entity.getModuleName())) {
            publicFlowTableModuleEntity.setModuleName(entity.getModuleName());
        }

        if(!publicFlowTableModuleEntity.getAuthority().equals(entity.getAuthority())) {
            publicFlowTableModuleEntity.setAuthority(entity.getAuthority());
        }

        _publicFlowTableModuleMapper.updateById(publicFlowTableModuleEntity);
        return renderOpSuccessList(1);
    }

    @Override
    public String add(PublicFlowTableModuleEntity entity) {

        _publicFlowTableModuleMapper.insert(entity);
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
