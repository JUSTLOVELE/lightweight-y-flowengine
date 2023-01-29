package com.flowengine.server.backend.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.flowengine.server.backend.service.FlowService;
import com.flowengine.server.entity.PublicFlowInstanceEntity;
import com.flowengine.server.entity.PublicFlowInstanceFlowEntity;
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

    @Autowired
    private PublicFlowInstanceFlowMapper _publicFlowInstanceFlowMapper;

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
        columns.put(Constant.Column.NODE_KEY, Constant.Value.START);
        //获取起始节点
        List<PublicFlowNodeEntity> publicFlowNodeEntities = _publicFlowNodeMapper.selectByMap(columns);
        //正常起始节点只会有一个
        if(publicFlowNodeEntities == null || publicFlowNodeEntities.size() != 1) {
            throw new RuntimeException("起始节点必须要有一个");
        }

        PublicFlowNodeEntity startNode = publicFlowNodeEntities.get(0);
        //生成流程实例表
        PublicFlowInstanceFlowEntity flowInstanceFlowEntity = new PublicFlowInstanceFlowEntity();
        flowInstanceFlowEntity.setOpId(UUIDGenerator.getUUID());
        flowInstanceFlowEntity.setInstanceId(publicFlowInstanceEntity.getOpId());
        flowInstanceFlowEntity.setTaskOpId(publicFlowInstanceEntity.getTaskOpId());
        flowInstanceFlowEntity.setCreateTime(publicFlowInstanceEntity.getCreateTime());
        flowInstanceFlowEntity.setNodeId(startNode.getOpId());
        flowInstanceFlowEntity.setNodeKey(startNode.getNodeKey());
        flowInstanceFlowEntity.setOrgId(publicFlowInstanceEntity.getOrgId());
        flowInstanceFlowEntity.setDeptId(publicFlowInstanceEntity.getDeptId());
        flowInstanceFlowEntity.setLastNodeId(startNode.getLastNodeId());
        flowInstanceFlowEntity.setLastNodeKey(startNode.getLastNodeKey());
        String nextNode = startNode.getNextNode();
        JSONArray nextArray = JSONUtil.parseArray(nextNode);

        if(StrUtil.isEmpty(startFlowVO.getKey())) {
            //如果没有指定key就说明是第一个
            JSONObject nextJSON = nextArray.getJSONObject(0);
            flowInstanceFlowEntity.setNextNodeId(nextJSON.getStr(Constant.Key.NEXT_NODE_ID));
            flowInstanceFlowEntity.setNextNodeKey(nextJSON.getStr(Constant.Key.NEXT_NODE_KEY));
        }else{
            //循环寻找key
            for(int i=0; i<nextArray.size(); i++) {

                JSONObject jsonObject = nextArray.getJSONObject(i);
                String key = jsonObject.getStr(Constant.Key.KEY);

                if(startFlowVO.getKey().equals(key)) {

                    flowInstanceFlowEntity.setNextNodeId(jsonObject.getStr(Constant.Key.NEXT_NODE_ID));
                    flowInstanceFlowEntity.setNextNodeKey(jsonObject.getStr(Constant.Key.NEXT_NODE_KEY));
                }
            }
        }

        flowInstanceFlowEntity.setOperationTime(new Date());
        flowInstanceFlowEntity.setUserOpId(startFlowVO.getCreateUserOpId());
        flowInstanceFlowEntity.setFlowStatus(1); //0:未操作;1:已操作;注意这里仅仅是是否操作过，如果不通过也是属于操作的也就是1
        _publicFlowInstanceFlowMapper.insert(flowInstanceFlowEntity);
        //插入一条当前自己的发起的流程数据后要插入下一条流程的信息，意思就是转发给下一个用户的流程数据

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
