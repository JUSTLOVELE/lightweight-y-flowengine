package com.flowengine.server.backend.service.flow.impl;

import com.flowengine.common.utils.UUIDGenerator;
import com.flowengine.common.utils.entity.PublicFlowRoleEntity;
import com.flowengine.common.utils.entity.PublicFlowRoleUserGrantEntity;
import com.flowengine.common.utils.mapper.PublicFlowRoleMapper;
import com.flowengine.common.utils.mapper.PublicFlowRoleUserGrantMapper;
import com.flowengine.server.backend.dao.flow.FlowRoleDao;
import com.flowengine.server.backend.service.admin.DictConfigService;
import com.flowengine.server.backend.service.flow.FlowRoleService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FlowRoleServiceImpl extends BaseService implements FlowRoleService {

    @Resource
    private FlowRoleDao _flowRoleDao;

    @Resource
    private DictConfigService _dictConfigService;

    @Resource
    private PublicFlowRoleMapper _flowRoleMapper;

    @Resource
    private PublicFlowRoleUserGrantMapper _flowRoleUserGrantMapper;

    @Override
    public String getCombobox() {
        return getJSON(_flowRoleMapper.getCombobox());
    }

    @Override
    public String delete(String opId) {

        deleteFlowRoleUserGrantWithFlowRoleId(opId);
        _flowRoleMapper.deleteById(opId);

        return renderOpSuccessList(1);
    }

    @Override
    public String edit(String opId, String roleName, Integer roleType, String[] persons) {

        PublicFlowRoleEntity flowRoleEntity = _flowRoleMapper.selectById(opId);
        flowRoleEntity.setRoleName(roleName);
        flowRoleEntity.setRoleType(roleType);
        deleteFlowRoleUserGrantWithFlowRoleId(opId);

        if(persons != null) {

            for(String person: persons) {

                PublicFlowRoleUserGrantEntity grantEntity = new PublicFlowRoleUserGrantEntity();
                grantEntity.setOpId(UUIDGenerator.getUUID());
                grantEntity.setUserOpId(person);
                grantEntity.setFlowRoleId(flowRoleEntity.getOpId());
                _flowRoleUserGrantMapper.insert(grantEntity);
            }
        }

        return renderOpSuccessList(1);
    }

    private void deleteFlowRoleUserGrantWithFlowRoleId(String opId) {

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Column.FLOW_ROLE_ID, opId);
        _flowRoleUserGrantMapper.deleteByMap(param);
    }

    @Override
    public String add(PublicFlowRoleEntity flowRoleEntity, List<PublicFlowRoleUserGrantEntity> flowRoleUserGrantEntities) {

        _flowRoleMapper.insert(flowRoleEntity);

        if(flowRoleUserGrantEntities != null && !flowRoleUserGrantEntities.isEmpty()) {

            for(PublicFlowRoleUserGrantEntity entity: flowRoleUserGrantEntities) {
                _flowRoleUserGrantMapper.insert(entity);
            }
        }

        return renderOpSuccessList(1);
    }

    @Override
    public String getRoleTypeCombobox() {
        return _dictConfigService.queryComboboxWithKeyCode(Constant.Dict.FLOW_ROLE_ROLE_TYPE);
    }

    @Override
    public String query(Map<String, Object> param) {

        Integer limit = (Integer) param.get(Constant.Key.LIMIT);
        Integer page = (Integer) param.get(Constant.Key.PAGE);

        if(limit == null) {
            return renderFailureList("limit为空");
        }

        if(page == null) {
            return renderFailureList("page为空");
        }

        List<Map<String, Object>> roles = _flowRoleDao.query(param);

        for(Map<String, Object> role: roles) {

            String opId = (String) role.get(Constant.Key.OP_ID);
            List<String> persons = _flowRoleUserGrantMapper.queryWithFlowRoleId(opId);
            role.put("persons", persons);
        }

        if(roles != null && roles.size() > 0) {
            return renderQuerySuccessList(_flowRoleDao.queryTotal(param), roles);
        }else {
            return renderQuerySuccessList(0, roles);
        }
    }
}
