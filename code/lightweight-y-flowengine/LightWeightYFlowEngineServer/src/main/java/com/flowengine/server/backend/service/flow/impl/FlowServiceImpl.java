package com.flowengine.server.backend.service.flow.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.flowengine.common.utils.UUIDGenerator;
import com.flowengine.common.utils.entity.PublicFlowNodeEntity;
import com.flowengine.common.utils.mapper.PublicFlowNodeCheckMapper;
import com.flowengine.common.utils.mapper.PublicFlowNodeMapper;
import com.flowengine.server.backend.dao.flow.FlowDao;
import com.flowengine.server.backend.service.flow.FlowInitService;
import com.flowengine.server.backend.service.flow.FlowService;
import com.flowengine.server.model.BackFlowBO;
import com.flowengine.server.model.EndFlowBO;
import com.flowengine.server.model.NextFlowBO;
import com.flowengine.server.model.flow.enums.FlowInstanceFlowFlowStatusEnum;
import com.flowengine.server.model.flow.enums.FlowResultEnum;
import com.flowengine.server.model.flow.enums.FlowStatusEnums;
import com.flowengine.server.model.flow.model.FlowMainToTableBean;
import com.flowengine.server.model.flow.model.StartFlowBO;
import com.flowengine.server.model.flow.model.TemplateFlowInstanceBean;
import com.flowengine.server.model.flow.model.TemplateFlowInstanceFlowBean;
import com.flowengine.server.utils.Constant;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Resource
    private FlowInitService _flowInitService;

    @Resource
    private PublicFlowNodeMapper _flowNodeMapper;

    @Resource
    private FlowDao _flowDao;

    @Resource
    private PublicFlowNodeCheckMapper _flowNodeCheckMapper;

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
        //获取当前流程的起始节点
        PublicFlowNodeEntity startNode = _flowNodeMapper.getStartNode(startFlowVO.getMainId());
        //插入业务流程流转表
        TemplateFlowInstanceFlowBean flowInstanceFlowBean = new TemplateFlowInstanceFlowBean();
        flowInstanceFlowBean.setInstanceId(flowInstanceBean.getOpId());
        flowInstanceFlowBean.setTaskOpId(startFlowVO.getTaskOpId());
        flowInstanceFlowBean.setNodeId(startNode.getOpId());
        flowInstanceFlowBean.setNodeKey(startNode.getNodeKey());
        flowInstanceFlowBean.setOrgId(startFlowVO.getOrgId());
        flowInstanceFlowBean.setDeptId(startFlowVO.getDeptId());
        flowInstanceFlowBean.setLastNodeId(startNode.getLastNodeId());
        flowInstanceFlowBean.setLastNodeKey(startNode.getLastNodeKey());
        String nextNode = startNode.getNextNode();
        JSONObject nextJson = JSONUtil.parseObj(nextNode);
        JSONObject jsonObject = nextJson.getJSONObject((StrUtil.isNotEmpty(startFlowVO.getKey()) ? startFlowVO.getKey() : Constant.Value.START));

        if(jsonObject != null) {

            flowInstanceFlowBean.setNextNodeId(jsonObject.getStr(Constant.Key.NEXT_NODE_ID));
            flowInstanceFlowBean.setNextNodeKey(jsonObject.getStr(Constant.Key.NEXT_NODE_KEY));
        }else {
            throw new RuntimeException("初始节点必须配置为start");
        }

        flowInstanceFlowBean.setFlowSort(1);//初始
        flowInstanceFlowBean.setOperationTime(new Date());
        flowInstanceFlowBean.setUserOpId(startFlowVO.getCreateUserOpId());
        flowInstanceFlowBean.setFlowResult(FlowResultEnum.PASS. getValue());
        //0:未操作;1:已操作;注意这里仅仅是是否操作过，如果不通过也是属于操作的也就是1
        flowInstanceFlowBean.setFlowStatus(FlowInstanceFlowFlowStatusEnum.OPERATED.getValue());
        _flowDao.insertStartFlowInstanceFlowData(flowInstanceFlowBean, flowMainToTableBean.getFlowInstanceFlowTableName());
        //插入一条当前自己的发起的流程数据后要插入下一条流程的信息，意思就是转发给下一个用户的流程数据
        _flowDao.insertNextFlowInstanceFlowData(getNextFlowInstanceFlowBean(flowInstanceFlowBean), flowMainToTableBean.getFlowInstanceFlowTableName());
    }

    private TemplateFlowInstanceFlowBean getNextFlowInstanceFlowBean(TemplateFlowInstanceFlowBean flowInstanceFlowBean) {
        
        TemplateFlowInstanceFlowBean nextFlowInstanceFlowBean = new TemplateFlowInstanceFlowBean();
        nextFlowInstanceFlowBean.setOpId(UUIDGenerator.getUUID());
        nextFlowInstanceFlowBean.setInstanceId(flowInstanceFlowBean.getInstanceId());
        nextFlowInstanceFlowBean.setTaskOpId(flowInstanceFlowBean.getTaskOpId());
        nextFlowInstanceFlowBean.setCreateTime(flowInstanceFlowBean.getCreateTime());
        nextFlowInstanceFlowBean.setNodeId(flowInstanceFlowBean.getNextNodeId());
        nextFlowInstanceFlowBean.setNodeKey(flowInstanceFlowBean.getNextNodeKey());
        nextFlowInstanceFlowBean.setOrgId(flowInstanceFlowBean.getOrgId());
        //可能会流转到下一个科室，所以在next中更新
        //nextFlowInstanceFlowEntity.setDeptId(flowInstanceFlowEntity.getDeptId());
        nextFlowInstanceFlowBean.setLastNodeId(flowInstanceFlowBean.getNodeId());
        nextFlowInstanceFlowBean.setLastNodeKey(flowInstanceFlowBean.getNodeKey());
        nextFlowInstanceFlowBean.setLastOpId(flowInstanceFlowBean.getOpId());
        nextFlowInstanceFlowBean.setFlowStatus(FlowInstanceFlowFlowStatusEnum.WAIT_OPERATE.getValue());
        nextFlowInstanceFlowBean.setFlowSort(flowInstanceFlowBean.getFlowSort()+1);

        return nextFlowInstanceFlowBean;
    }

    @Override
    public void next(NextFlowBO nextFlowVO) {

        if(StrUtil.isEmpty(nextFlowVO.getFlowRunBO().getInstanceFlowId())) {
            throw new RuntimeException("流程流转id不能为空");
        }

        if(StrUtil.isEmpty(nextFlowVO.getFlowRunBO().getMainId())) {
            throw new RuntimeException("mainId不能为空");
        }
        //先获取业务表的数据
        FlowMainToTableBean flowMainToTableBean = _flowInitService.getMainToTableDataFromRedis(nextFlowVO.getFlowRunBO().getMainId());
        //获取当前的流转业务数据
        List<Map<String, Object>> flowInstanceFlowDatas = _flowDao.queryFlowInstanceFlow(nextFlowVO.getFlowRunBO().getInstanceFlowId(), flowMainToTableBean.getFlowInstanceFlowTableName());
        //需要判断当前环节是否完结
        //1、获取节点主键
        String nodeOpId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NODE_ID);
        //2、获取所有审核的关键数据
        List<Map<String, Object>> checkPersons = _flowNodeCheckMapper.queryNodeChecksByNodeId(nodeOpId);

        for(Map<String, Object> check: checkPersons) {
            //2.1 如果是非会签，因为当前用户已经审核了，所以直接过

            //2.2 如果是会签，则要判断
        }


//
//        PublicFlowInstanceFlowEntity flowInstanceFlowEntity = _publicFlowInstanceFlowMapper.selectById(nextFlowVO.getFlowRunBO().getInstanceFlowId());
//
//        if(flowInstanceFlowEntity == null) {
//            throw new RuntimeException("根据流程流转id查询不到流程流转对象");
//        }
//
//        if(StrUtil.isNotEmpty(nextFlowVO.getFlowRunBO().getOrgId())) {
//            flowInstanceFlowEntity.setOrgId(nextFlowVO.getFlowRunBO().getOrgId());
//        }
//
//        if(StrUtil.isNotEmpty(nextFlowVO.getFlowRunBO().getDeptId())) {
//            flowInstanceFlowEntity.setDeptId(nextFlowVO.getFlowRunBO().getDeptId());
//        }
//
//        flowInstanceFlowEntity.setOperationTime(new Date());
//        flowInstanceFlowEntity.setUserOpId(nextFlowVO.getFlowRunBO().getUserOpId());
//        flowInstanceFlowEntity.setFlowStatus(FlowInstanceFlowFlowStatusEnum.OPERATED.getValue());
//        //下一个环节通常都是pass
//        if(nextFlowVO.getFlowRunBO().getFlowResultEnum() == null) {
//            flowInstanceFlowEntity.setFlowResult(FlowResultEnum.PASS.getValue());
//        }else {
//            flowInstanceFlowEntity.setFlowResult(nextFlowVO.getFlowRunBO().getFlowResultEnum().getValue());
//        }
//
//        setOpinion(flowInstanceFlowEntity, nextFlowVO.getOpinionBO());
//        PublicFlowNodeEntity publicFlowNodeEntity = _flowNodeMapper.selectById(flowInstanceFlowEntity.getNodeId());
//        JSONObject nextJson = JSONUtil.parseObj(publicFlowNodeEntity.getNextNode());
//        JSONObject jsonObject = nextJson.getJSONObject((StrUtil.isNotEmpty(nextFlowVO.getFlowRunBO().getKey()) ? nextFlowVO.getFlowRunBO().getKey() : Constant.Key.NEXT));
//        flowInstanceFlowEntity.setNextNodeKey(jsonObject.getStr(Constant.Key.NEXT_NODE_KEY));
//        flowInstanceFlowEntity.setNextNodeId(jsonObject.getStr(Constant.Key.NEXT_NODE_ID));
//        _publicFlowInstanceFlowMapper.updateById(flowInstanceFlowEntity);
//        Integer refType = jsonObject.getInt(Constant.Key.REF_TYPE);
//        String refId = jsonObject.getStr(Constant.Key.REF_ID);
//        _publicFlowInstanceFlowMapper.insert(getFlowInstanceFlowEntity(flowInstanceFlowEntity, refType, refId));
    }

//    private void setOpinion(PublicFlowInstanceFlowEntity flowInstanceFlowEntity, OpinionBO opinionBO) {
//
//        flowInstanceFlowEntity.setHeaderComment(opinionBO.getHeaderComment());
//        flowInstanceFlowEntity.setBackComment(opinionBO.getBackComment());
//        flowInstanceFlowEntity.setFlowComment(opinionBO.getFlowComment());
//    }

    @Override
    public void back(BackFlowBO backFlowBO) {

    }

    @Override
    public void endFlow(EndFlowBO endFlowBO) {

    }
}
