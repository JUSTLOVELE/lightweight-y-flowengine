package com.flowengine.server.backend.service.flow.impl;

import cn.hutool.core.util.StrUtil;
import com.flowengine.server.backend.dao.flow.FlowDao;
import com.flowengine.server.backend.service.flow.FlowInitService;
import com.flowengine.server.backend.service.flow.FlowService;
import com.flowengine.server.model.BackFlowBO;
import com.flowengine.server.model.EndFlowBO;
import com.flowengine.server.model.NextFlowBO;
import com.flowengine.server.model.flow.enums.FlowOverTime;
import com.flowengine.server.model.flow.enums.FlowStatusEnums;
import com.flowengine.server.model.flow.model.FlowMainToTableBean;
import com.flowengine.server.model.flow.model.StartFlowBO;
import com.flowengine.server.model.flow.model.TemplateFlowInstanceBean;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
@Service
@Transactional
public class FlowServiceImpl implements FlowService {

    @Resource
    private FlowInitService _flowInitService;

    @Resource
    private FlowDao _flowDao;

    @Override
    public void startFlow(StartFlowBO startFlowVO) {

        if(StrUtil.isEmpty(startFlowVO.getTaskOpId())) {
            throw new RuntimeException("绑定的业务主键不可以为空");
        }

        if(StrUtil.isEmpty(startFlowVO.getMainId())) {
            throw new RuntimeException("绑定的流程主键不可以为空");
        }

        if(StrUtil.isEmpty(startFlowVO.getCreateUserOpId())) {
            throw new RuntimeException("创建用户的主键不可以为空");
        }
        //先获取业务表的数据
        FlowMainToTableBean flowMainToTableBean = _flowInitService.getMainToTableDataFromRedis(startFlowVO.getMainId());
        //插入业务流程实例表
        TemplateFlowInstanceBean flowInstanceBean = new TemplateFlowInstanceBean();
        flowInstanceBean.setFlowStatus(FlowStatusEnums.ING.getValue());
        flowInstanceBean.setTaskOpId(startFlowVO.getTaskOpId());
        flowInstanceBean.setMainId(startFlowVO.getMainId());
        flowInstanceBean.setOrgId(startFlowVO.getOrgId());
        flowInstanceBean.setDeptId(startFlowVO.getDeptId());
        flowInstanceBean.setCreateUserOpId(startFlowVO.getCreateUserOpId());
        _flowDao.insertStartFlowInstanceData(flowInstanceBean, flowMainToTableBean.getFlowInstanceTableName(), startFlowVO);
        //插入业务流程流转表
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
        JSONObject nextJson = JSONUtil.parseObj(nextNode);
        Integer refType = null;
        String refId = null;
        JSONObject jsonObject = nextJson.getJSONObject((StrUtil.isNotEmpty(startFlowVO.getKey()) ? startFlowVO.getKey() : Constant.Value.START));

        if(jsonObject != null) {

            flowInstanceFlowEntity.setNextNodeId(jsonObject.getStr(Constant.Key.NEXT_NODE_ID));
            flowInstanceFlowEntity.setNextNodeKey(jsonObject.getStr(Constant.Key.NEXT_NODE_KEY));
            refType = jsonObject.getInt(Constant.Key.REF_TYPE);
            refId = jsonObject.getStr(Constant.Key.REF_ID);
        }else {
            throw new RuntimeException("初始节点必须配置为start");
        }

        flowInstanceFlowEntity.setFlowSort(1);//初始
        flowInstanceFlowEntity.setOperationTime(new Date());
        flowInstanceFlowEntity.setUserOpId(startFlowVO.getCreateUserOpId());
        flowInstanceFlowEntity.setFlowResult(FlowResultEnum.PASS.
                getValue());
        flowInstanceFlowEntity.setFlowStatus(FlowInstanceFlowFlowStatusEnum.OPERATED.getValue()); //0:未操作;1:已操作;注意这里仅仅是是否操作过，如果不通过也是属于操作的也就是1
        _publicFlowInstanceFlowMapper.insert(flowInstanceFlowEntity);
        //插入一条当前自己的发起的流程数据后要插入下一条流程的信息，意思就是转发给下一个用户的流程数据
        _publicFlowInstanceFlowMapper.insert(getFlowInstanceFlowEntity(flowInstanceFlowEntity, refType, refId));
    }

    private PublicFlowInstanceFlowEntity getFlowInstanceFlowEntity(PublicFlowInstanceFlowEntity flowInstanceFlowEntity,
                                                                   Integer refType,
                                                                   String refId) {

        PublicFlowInstanceFlowEntity nextFlowInstanceFlowEntity = new PublicFlowInstanceFlowEntity();
        nextFlowInstanceFlowEntity.setOpId(UUIDGenerator.getUUID());
        nextFlowInstanceFlowEntity.setInstanceId(flowInstanceFlowEntity.getInstanceId());
        nextFlowInstanceFlowEntity.setTaskOpId(flowInstanceFlowEntity.getTaskOpId());
        nextFlowInstanceFlowEntity.setCreateTime(flowInstanceFlowEntity.getCreateTime());
        nextFlowInstanceFlowEntity.setNodeId(flowInstanceFlowEntity.getNextNodeId());
        nextFlowInstanceFlowEntity.setNodeKey(flowInstanceFlowEntity.getNextNodeKey());
        nextFlowInstanceFlowEntity.setOrgId(flowInstanceFlowEntity.getOrgId());
        //nextFlowInstanceFlowEntity.setDeptId(flowInstanceFlowEntity.getDeptId());
        nextFlowInstanceFlowEntity.setLastNodeId(flowInstanceFlowEntity.getNodeId());
        nextFlowInstanceFlowEntity.setLastNodeKey(flowInstanceFlowEntity.getNodeKey());
        nextFlowInstanceFlowEntity.setLastOpId(flowInstanceFlowEntity.getOpId());
        nextFlowInstanceFlowEntity.setFlowStatus(FlowInstanceFlowFlowStatusEnum.WAIT_OPERATE.getValue());
        nextFlowInstanceFlowEntity.setFlowSort(flowInstanceFlowEntity.getFlowSort()+1);
        nextFlowInstanceFlowEntity.setRefType(refType);
        nextFlowInstanceFlowEntity.setRefId(refId);
    }

    @Override
    public void next(NextFlowBO nextFlowVO) {

    }

    @Override
    public void back(BackFlowBO backFlowBO) {

    }

    @Override
    public void endFlow(EndFlowBO endFlowBO) {

    }
}
