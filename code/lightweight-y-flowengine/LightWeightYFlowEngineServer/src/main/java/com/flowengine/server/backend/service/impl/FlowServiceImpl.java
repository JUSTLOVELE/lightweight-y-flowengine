package com.flowengine.server.backend.service.impl;

import cn.hutool.core.util.StrUtil;
import com.flowengine.server.backend.service.FlowService;
import com.flowengine.server.entity.PublicFlowInstanceEntity;
import com.flowengine.server.entity.PublicFlowNodeEntity;
import com.flowengine.server.mapper.PublicFlowInstanceFlowMapper;
import com.flowengine.server.mapper.PublicFlowInstanceMapper;
import com.flowengine.server.mapper.PublicFlowNodeMapper;
import com.flowengine.server.model.StartFlowVO;
import com.flowengine.server.model.enums.FlowStatusEnum;
import com.flowengine.server.model.enums.OverTimeEnum;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
@Service
@Transactional
public class FlowServiceImpl implements FlowService {

    @Autowired
    private PublicFlowInstanceMapper _publicFlowInstanceMapper;

    @Autowired
    private PublicFlowNodeMapper _publicFlowNodeMapper;

    @Override
    public void startFlow(StartFlowVO startFlowVO) {

        if(StrUtil.isEmpty(startFlowVO.getTaskOpId())) {
            throw new RuntimeException("绑定的业务主键不可以为空");
        }

        if(StrUtil.isEmpty(startFlowVO.getMainId())) {
            throw new RuntimeException("绑定的流程主键不可以为空");
        }

        if(StrUtil.isEmpty(startFlowVO.getCreateUserOpId())) {
            throw new RuntimeException("创建用户的主键不可以为空");
        }
        //生成流程主表数据
        PublicFlowInstanceEntity publicFlowInstanceEntity = new PublicFlowInstanceEntity();
        publicFlowInstanceEntity.setOpId(UUIDGenerator.getUUID());
        publicFlowInstanceEntity.setCreateTime(new Date());
        publicFlowInstanceEntity.setFlowStatus(FlowStatusEnum.START.getValue());
        publicFlowInstanceEntity.setIsOverTime(OverTimeEnum.NO_OVER_TIME.getValue());

        if(StrUtil.isNotEmpty(startFlowVO.getTaskOpId())) {
            publicFlowInstanceEntity.setTaskOpId(startFlowVO.getTaskOpId());
        }

        if(StrUtil.isNotEmpty(startFlowVO.getMainId())) {
            publicFlowInstanceEntity.setMainId(startFlowVO.getMainId());
        }

        if(StrUtil.isNotEmpty(startFlowVO.getOrgId())) {
            publicFlowInstanceEntity.setOrgId(startFlowVO.getOrgId());
        }

        if(StrUtil.isNotEmpty(startFlowVO.getDeptId())) {
            publicFlowInstanceEntity.setDeptId(startFlowVO.getDeptId());
        }

        if(StrUtil.isNotEmpty(startFlowVO.getCreateUserOpId())) {
            publicFlowInstanceEntity.setCreateUserOpId(startFlowVO.getCreateUserOpId());
        }

        _publicFlowInstanceMapper.insert(publicFlowInstanceEntity);
        //根据main去查询对应的流程和环节信息
        Map<String, Object> columns = new HashMap<>();
        columns.put(Constant.Column.MAIN_ID, startFlowVO.getMainId());
        columns.put();
        //获取起始节点
        List<PublicFlowNodeEntity> publicFlowNodeEntities = _publicFlowNodeMapper.selectByMap(columns);


    }

    @Override
    public void next() {

    }

    @Override
    public void back() {

    }

    @Override
    public void endFlow() {

    }
}
