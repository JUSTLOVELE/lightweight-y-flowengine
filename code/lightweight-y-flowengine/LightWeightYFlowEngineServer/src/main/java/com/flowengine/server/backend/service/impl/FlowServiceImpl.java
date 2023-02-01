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
import com.flowengine.server.model.*;
import com.flowengine.server.model.enums.FlowInstanceFlowFlowStatusEnum;
import com.flowengine.server.model.enums.FlowResultEnum;
import com.flowengine.server.model.enums.FlowStatusEnum;
import com.flowengine.server.model.enums.OverTimeEnum;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.DateUtil;
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

    @Autowired
    private PublicFlowNodeMapper _flowNodeMapper;

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

        return nextFlowInstanceFlowEntity;
    }

    @Override
    public void next(NextFlowBO nextFlowVO) {

        if(StrUtil.isEmpty(nextFlowVO.getFlowRunBO().getInstanceFlowId())) {
            throw new RuntimeException("流程流转id不能为空");
        }

        PublicFlowInstanceFlowEntity flowInstanceFlowEntity = _publicFlowInstanceFlowMapper.selectById(nextFlowVO.getFlowRunBO().getInstanceFlowId());

        if(flowInstanceFlowEntity == null) {
            throw new RuntimeException("根据流程流转id查询不到流程流转对象");
        }

        if(StrUtil.isNotEmpty(nextFlowVO.getFlowRunBO().getOrgId())) {
            flowInstanceFlowEntity.setOrgId(nextFlowVO.getFlowRunBO().getOrgId());
        }

        if(StrUtil.isNotEmpty(nextFlowVO.getFlowRunBO().getDeptId())) {
            flowInstanceFlowEntity.setDeptId(nextFlowVO.getFlowRunBO().getDeptId());
        }

        flowInstanceFlowEntity.setOperationTime(new Date());
        flowInstanceFlowEntity.setUserOpId(nextFlowVO.getFlowRunBO().getUserOpId());
        flowInstanceFlowEntity.setFlowStatus(FlowInstanceFlowFlowStatusEnum.OPERATED.getValue());
        //下一个环节通常都是pass
        if(nextFlowVO.getFlowRunBO().getFlowResultEnum() == null) {
            flowInstanceFlowEntity.setFlowResult(FlowResultEnum.PASS.getValue());
        }else {
            flowInstanceFlowEntity.setFlowResult(nextFlowVO.getFlowRunBO().getFlowResultEnum().getValue());
        }

        setOpinion(flowInstanceFlowEntity, nextFlowVO.getOpinionBO());
        PublicFlowNodeEntity publicFlowNodeEntity = _flowNodeMapper.selectById(flowInstanceFlowEntity.getNodeId());
        JSONObject nextJson = JSONUtil.parseObj(publicFlowNodeEntity.getNextNode());
        JSONObject jsonObject = nextJson.getJSONObject((StrUtil.isNotEmpty(nextFlowVO.getFlowRunBO().getKey()) ? nextFlowVO.getFlowRunBO().getKey() : Constant.Key.NEXT));
        flowInstanceFlowEntity.setNextNodeKey(jsonObject.getStr(Constant.Key.NEXT_NODE_KEY));
        flowInstanceFlowEntity.setNextNodeId(jsonObject.getStr(Constant.Key.NEXT_NODE_ID));
        _publicFlowInstanceFlowMapper.updateById(flowInstanceFlowEntity);
        Integer refType = jsonObject.getInt(Constant.Key.REF_TYPE);
        String refId = jsonObject.getStr(Constant.Key.REF_ID);
        _publicFlowInstanceFlowMapper.insert(getFlowInstanceFlowEntity(flowInstanceFlowEntity, refType, refId));
    }

    private void setOpinion(PublicFlowInstanceFlowEntity flowInstanceFlowEntity, OpinionBO opinionBO) {

        flowInstanceFlowEntity.setHeaderComment(opinionBO.getHeaderComment());
        flowInstanceFlowEntity.setBackComment(opinionBO.getBackComment());
        flowInstanceFlowEntity.setFlowComment(opinionBO.getFlowComment());
    }

    @Override
    public void back(BackFlowBO backFlowBO) {

        if(StrUtil.isEmpty(backFlowBO.getFlowRunBO().getInstanceFlowId())) {
            throw new RuntimeException("流程流转id不能为空");
        }

        PublicFlowInstanceFlowEntity flowInstanceFlowEntity = _publicFlowInstanceFlowMapper.selectById(backFlowBO.getFlowRunBO().getInstanceFlowId());

        if(flowInstanceFlowEntity == null) {
            throw new RuntimeException("根据流程流转id查询不到流程流转对象");
        }

        if(StrUtil.isNotEmpty(backFlowBO.getFlowRunBO().getOrgId())) {
            flowInstanceFlowEntity.setOrgId(backFlowBO.getFlowRunBO().getOrgId());
        }

        if(StrUtil.isNotEmpty(backFlowBO.getFlowRunBO().getDeptId())) {
            flowInstanceFlowEntity.setDeptId(backFlowBO.getFlowRunBO().getDeptId());
        }

        flowInstanceFlowEntity.setOperationTime(new Date());
        flowInstanceFlowEntity.setUserOpId(backFlowBO.getFlowRunBO().getUserOpId());
        flowInstanceFlowEntity.setFlowStatus(FlowInstanceFlowFlowStatusEnum.OPERATED.getValue());
        //这里是回退所以通常是back
        if(backFlowBO.getFlowRunBO().getFlowResultEnum() == null) {
            flowInstanceFlowEntity.setFlowResult(FlowResultEnum.NO_PASS.getValue());
        }else {
            flowInstanceFlowEntity.setFlowResult(backFlowBO.getFlowRunBO().getFlowResultEnum().getValue());
        }

        setOpinion(flowInstanceFlowEntity, backFlowBO.getOpinionBO());
        PublicFlowNodeEntity publicFlowNodeEntity = _flowNodeMapper.selectById(flowInstanceFlowEntity.getNodeId());
        JSONObject nextJson = JSONUtil.parseObj(publicFlowNodeEntity.getNextNode());
        JSONObject jsonObject = nextJson.getJSONObject((StrUtil.isNotEmpty(backFlowBO.getFlowRunBO().getKey()) ? backFlowBO.getFlowRunBO().getKey() : Constant.Key.BACK));
        flowInstanceFlowEntity.setNextNodeKey(jsonObject.getStr(Constant.Key.NEXT_NODE_KEY));
        flowInstanceFlowEntity.setNextNodeId(jsonObject.getStr(Constant.Key.NEXT_NODE_ID));
        _publicFlowInstanceFlowMapper.updateById(flowInstanceFlowEntity);
        Integer refType = jsonObject.getInt(Constant.Key.REF_TYPE);
        String refId = jsonObject.getStr(Constant.Key.REF_ID);
        _publicFlowInstanceFlowMapper.insert(getFlowInstanceFlowEntity(flowInstanceFlowEntity, refType, refId));
    }

    @Override
    public void endFlow(EndFlowBO endFlowBO) {

        if(StrUtil.isEmpty(endFlowBO.getFlowRunBO().getInstanceFlowId())) {
            throw new RuntimeException("流程流转id不能为空");
        }

        PublicFlowInstanceFlowEntity flowInstanceFlowEntity = _publicFlowInstanceFlowMapper.selectById(endFlowBO.getFlowRunBO().getInstanceFlowId());

        if(flowInstanceFlowEntity == null) {
            throw new RuntimeException("根据流程流转id查询不到流程流转对象");
        }

        if(StrUtil.isNotEmpty(endFlowBO.getFlowRunBO().getOrgId())) {
            flowInstanceFlowEntity.setOrgId(endFlowBO.getFlowRunBO().getOrgId());
        }

        if(StrUtil.isNotEmpty(endFlowBO.getFlowRunBO().getDeptId())) {
            flowInstanceFlowEntity.setDeptId(endFlowBO.getFlowRunBO().getDeptId());
        }

        flowInstanceFlowEntity.setOperationTime(new Date());
        flowInstanceFlowEntity.setUserOpId(endFlowBO.getFlowRunBO().getUserOpId());
        flowInstanceFlowEntity.setFlowStatus(FlowInstanceFlowFlowStatusEnum.OPERATED.getValue());
        //这里是流程结束所以应该是要pass
        if(endFlowBO.getFlowRunBO().getFlowResultEnum() == null) {
            flowInstanceFlowEntity.setFlowResult(FlowResultEnum.PASS.getValue());
        }else {
            flowInstanceFlowEntity.setFlowResult(endFlowBO.getFlowRunBO().getFlowResultEnum().getValue());
        }

        setOpinion(flowInstanceFlowEntity, endFlowBO.getOpinionBO());
        PublicFlowNodeEntity publicFlowNodeEntity = _flowNodeMapper.selectById(flowInstanceFlowEntity.getNodeId());
        JSONObject nextJson = JSONUtil.parseObj(publicFlowNodeEntity.getNextNode());
        JSONObject jsonObject = nextJson.getJSONObject(Constant.Key.END);//这里是END环节所以只能要end
        flowInstanceFlowEntity.setNextNodeKey(jsonObject.getStr(Constant.Key.NEXT_NODE_KEY));
        flowInstanceFlowEntity.setNextNodeId(jsonObject.getStr(Constant.Key.NEXT_NODE_ID));
        _publicFlowInstanceFlowMapper.updateById(flowInstanceFlowEntity);
        PublicFlowInstanceFlowEntity endEntity = getFlowInstanceFlowEntity(flowInstanceFlowEntity, null, null);
        endEntity.setFlowStatus(FlowInstanceFlowFlowStatusEnum.OPERATED.getValue());
        endEntity.setFlowResult(FlowResultEnum.PASS.getValue());
        _publicFlowInstanceFlowMapper.insert(endEntity);
        PublicFlowInstanceEntity publicFlowInstanceEntity = _publicFlowInstanceMapper.selectById(endEntity.getInstanceId());
        publicFlowInstanceEntity.setFlowStatus(FlowStatusEnum.END.getValue());
        publicFlowInstanceEntity.setEndTime(new Date());
        //计算所有环节的总时间
        Map<String, Object> columns = new HashMap<>();
        columns.put(Constant.Column.MAIN_ID, publicFlowInstanceEntity.getMainId());
        List<PublicFlowNodeEntity> publicFlowNodeEntities = _publicFlowNodeMapper.selectByMap(columns);
        int totalTime = 0;
        for(PublicFlowNodeEntity entity: publicFlowNodeEntities) {
            totalTime += entity.getLimitTime();
        }
        //计算到创建时间位置的时间
        int days = DateUtil.differentDaysByMillisecond(publicFlowInstanceEntity.getCreateTime(), publicFlowInstanceEntity.getEndTime());
        publicFlowInstanceEntity.setTotalTime(days);
        publicFlowInstanceEntity.setIsOverTime((totalTime > days) ? 0 : 1);
        _publicFlowInstanceMapper.updateById(publicFlowInstanceEntity);
    }
}
