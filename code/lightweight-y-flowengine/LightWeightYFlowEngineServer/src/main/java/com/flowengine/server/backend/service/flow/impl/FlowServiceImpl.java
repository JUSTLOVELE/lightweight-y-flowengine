package com.flowengine.server.backend.service.flow.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.flowengine.common.utils.DateUtil;
import com.flowengine.common.utils.UUIDGenerator;
import com.flowengine.common.utils.entity.PublicFlowNodeEntity;
import com.flowengine.common.utils.mapper.PublicFlowNodeCheckMapper;
import com.flowengine.common.utils.mapper.PublicFlowNodeMapper;
import com.flowengine.common.utils.mapper.PublicFlowRoleUserGrantMapper;
import com.flowengine.server.backend.dao.flow.FlowDao;
import com.flowengine.server.backend.service.flow.FlowInitService;
import com.flowengine.server.backend.service.flow.FlowService;
import com.flowengine.server.model.BackFlowBO;
import com.flowengine.server.model.EndFlowBO;
import com.flowengine.server.model.NextFlowBO;
import com.flowengine.server.model.flow.enums.*;
import com.flowengine.server.model.flow.model.*;
import com.flowengine.server.utils.Constant;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


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

    @Resource
    private PublicFlowRoleUserGrantMapper _flowRoleUserGrantMapper;

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
        String nextNode = _flowNodeMapper.queryNextJsonByOpId(nextFlowInstanceFlowBean.getNodeId());
        JSONObject nextJson = JSONUtil.parseObj(nextNode);
        JSONObject jsonObject = nextJson.getJSONObject(nextFlowInstanceFlowBean.getNodeKey());

        if(jsonObject != null) {

            nextFlowInstanceFlowBean.setNextNodeId(jsonObject.getStr(Constant.Key.NEXT_NODE_ID));
            nextFlowInstanceFlowBean.setNextNodeKey(jsonObject.getStr(Constant.Key.NEXT_NODE_KEY));
        }else {
            throw new RuntimeException("没有配置下一环节数据");
        }

        return nextFlowInstanceFlowBean;
    }

    @Override
    public void next(NextFlowBO nextFlowVO) {

        if(StrUtil.isEmpty(nextFlowVO.getInstanceFlowId())) {
            throw new RuntimeException("流程流转id不能为空");
        }

        if(StrUtil.isEmpty(nextFlowVO.getMainId())) {
            throw new RuntimeException("mainId不能为空");
        }
        //先获取业务表的数据
        FlowMainToTableBean flowMainToTableBean = _flowInitService.getMainToTableDataFromRedis(nextFlowVO.getMainId());
        //获取当前的流转业务数据
        List<Map<String, Object>> flowInstanceFlowDatas = _flowDao.queryFlowInstanceFlow(nextFlowVO.getInstanceFlowId(), flowMainToTableBean.getFlowInstanceFlowTableName());
        //需要判断当前环节是否完结
        //1、获取节点主键
        String nodeOpId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NODE_ID);
        String instanceId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.INSTANCE_ID);
        String taskOpId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.TASK_OP_ID);
        //2、获取所有审核的关键数据
        List<Map<String, Object>> checkPersons = _flowNodeCheckMapper.queryNodeChecksByNodeId(nodeOpId);
        boolean isFinished = true; //该环节是否完成
        outer:
        for(Map<String, Object> check: checkPersons) {

            String nodeType = (String) check.get(Constant.Flow.NODE_TYPE);

            if(NodeTypeEnums.UN_COUNTERSIGN.getValue().equals(nodeType)) {
                //2.1 如果是非会签，因为当前用户已经审核了，所以直接过
                continue;
            }else{
                //会签和按顺序处理一起搞了，不另外实现
                //2.2 如果是会签，则要判断
                String checkType = (String) check.get(Constant.Flow.CHECK_TYPE);
                String refId = (String) check.get(Constant.Key.REF_ID);

                switch (Objects.requireNonNull(NodeCheckTypeEnums.getInstanceByValue(checkType))) {

                   case PERSON :

                       List<String> persons = new ArrayList<>();
                       persons.add(refId);
                       persons.add(nextFlowVO.getUserOpId());
                       int count = _flowDao.queryCommentCountByUserOpId(persons, nodeOpId, nextFlowVO.getInstanceFlowId(),  flowMainToTableBean.flowCommentTableName);

                       if(count != 1) {
                           //没有审核
                           isFinished = false;
                           break outer;
                       }

                       break;
                   case DEPT:

                       List<String> depts = new ArrayList<>();
                       depts.add(refId);
                       depts.add(nextFlowVO.getUserOpId());
                       int deptCount = _flowDao.queryCommentCountByDeptId(depts, nodeOpId, nextFlowVO.getInstanceFlowId(),  flowMainToTableBean.flowCommentTableName);

                       if(deptCount != 1) {
                           //没有审核
                           isFinished = false;
                           break outer;
                       }

                       break;
                   case FLOW_ROLE:

                       List<String> userOpIds = _flowRoleUserGrantMapper.queryWithFlowRoleId(refId);

                       if(_flowDao.queryCommentCountByUserOpId(userOpIds, nodeOpId, nextFlowVO.getInstanceFlowId(), flowMainToTableBean.flowCommentTableName) != 1) {
                           //没有审核
                           isFinished = false;
                           break outer;
                       }

                       break;
                   case COPY:
                        //不实现
                       break;
                   case ALL:
                       //若是所有人则直接跳过
                       break;

                   default:
                       break;
                }
            }
        }

        TemplateFlowCommentBean templateFlowCommentBean = new TemplateFlowCommentBean();
        templateFlowCommentBean.setInstanceId(instanceId);
        templateFlowCommentBean.setTaskOpId(taskOpId);
        templateFlowCommentBean.setNodeId(nodeOpId);
        templateFlowCommentBean.setFlowComment(nextFlowVO.getFlowComment());
        templateFlowCommentBean.setFlowSort(_flowDao.queryCommentSortByInstanceFlowId(nextFlowVO.getInstanceFlowId(), flowMainToTableBean.getFlowCommentTableName()));
        templateFlowCommentBean.setFlowResult(nextFlowVO.getFlowResultEnum().getValue());
        templateFlowCommentBean.setInstanceFlowId(nextFlowVO.getInstanceFlowId());
        templateFlowCommentBean.setUserOpId(nextFlowVO.getUserOpId());
        templateFlowCommentBean.setDeptId(nextFlowVO.getDeptId());
        templateFlowCommentBean.setOrgId(nextFlowVO.getOrgId());
        _flowDao.insertComment(templateFlowCommentBean, flowMainToTableBean.getFlowCommentTableName());

        if(isFinished) {
            //true表示当前环节完成了
            //先更新当前流程流转的数据
            Map<String, Object> param = new HashMap<>();
            param.put(Constant.Key.TABLE_NAME, flowMainToTableBean.getFlowInstanceFlowTableName());
            param.put(Constant.Key.OP_ID, nextFlowVO.getInstanceFlowId());
            param.put(Constant.Flow.FLOW_STATUS, 1);
            param.put(Constant.Flow.FLOW_RESULT, nextFlowVO.getFlowResultEnum().getValue());
            _flowDao.updateNextFlowInstanceFlow(param);
            //再插入新的待办
            TemplateFlowInstanceFlowBean nextFlowInstanceFlowBean = new TemplateFlowInstanceFlowBean();
            nextFlowInstanceFlowBean.setOpId(UUIDGenerator.getUUID());
            nextFlowInstanceFlowBean.setInstanceId(instanceId);
            nextFlowInstanceFlowBean.setTaskOpId(taskOpId);
            nextFlowInstanceFlowBean.setCreateTime(new Date());
            String nextNodeId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NEXT_NODE_ID);
            String nextNodeKey = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NEXT_NODE_KEY);
            nextFlowInstanceFlowBean.setNodeId(nextNodeId); //当前流转数据的下一节点就是新插入的当前节点
            nextFlowInstanceFlowBean.setNodeKey(nextNodeKey);
            nextFlowInstanceFlowBean.setOrgId(nextFlowVO.getOrgId());
            String nextNode = _flowNodeMapper.queryNextJsonByOpId(nextNodeId);
            JSONObject nextJson = JSONUtil.parseObj(nextNode);
            JSONObject jsonObject = nextJson.getJSONObject(nextNodeKey);

            if(jsonObject != null) {

                nextFlowInstanceFlowBean.setNextNodeId(jsonObject.getStr(Constant.Key.NEXT_NODE_ID));
                nextFlowInstanceFlowBean.setNextNodeKey(jsonObject.getStr(Constant.Key.NEXT_NODE_KEY));
            }else {
                throw new RuntimeException("没有配置下一环节数据");
            }
            //可能会流转到下一个科室，所以在next中更新
            //nextFlowInstanceFlowEntity.setDeptId(flowInstanceFlowEntity.getDeptId());
            nextFlowInstanceFlowBean.setLastNodeId(nodeOpId);
            nextFlowInstanceFlowBean.setLastNodeKey((String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NODE_KEY));
            nextFlowInstanceFlowBean.setLastOpId(nextFlowVO.getInstanceFlowId());
            nextFlowInstanceFlowBean.setFlowStatus(FlowInstanceFlowFlowStatusEnum.WAIT_OPERATE.getValue());
            int sort = (int) flowInstanceFlowDatas.get(0).get(Constant.Flow.FLOW_SORT);
            nextFlowInstanceFlowBean.setFlowSort(sort + 1);
            _flowDao.insertNextFlowInstanceFlowData(nextFlowInstanceFlowBean, flowMainToTableBean.getFlowInstanceFlowTableName());
        }
    }

    @Override
    public void back(BackFlowBO backFlowBO) {

        if(StrUtil.isEmpty(backFlowBO.getInstanceFlowId())) {
            throw new RuntimeException("流程流转id不能为空");
        }

        if(StrUtil.isEmpty(backFlowBO.getMainId())) {
            throw new RuntimeException("mainId不能为空");
        }
        //先获取业务表的数据
        FlowMainToTableBean flowMainToTableBean = _flowInitService.getMainToTableDataFromRedis(backFlowBO.getMainId());
        //更新旧数据
        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.TABLE_NAME, flowMainToTableBean.getFlowInstanceFlowTableName());
        param.put(Constant.Key.OP_ID, backFlowBO.getInstanceFlowId());
        param.put(Constant.Flow.FLOW_STATUS, 1);
        param.put(Constant.Flow.FLOW_RESULT, 0); //回退一定是不通过
        _flowDao.updateNextFlowInstanceFlow(param);
        //插入流转意见
        //获取当前的流转业务数据
        List<Map<String, Object>> flowInstanceFlowDatas = _flowDao.queryFlowInstanceFlow(backFlowBO.getInstanceFlowId(), flowMainToTableBean.getFlowInstanceFlowTableName());
        String nodeOpId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NODE_ID);
        String instanceId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.INSTANCE_ID);
        String taskOpId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.TASK_OP_ID);
        TemplateFlowCommentBean templateFlowCommentBean = new TemplateFlowCommentBean();
        templateFlowCommentBean.setInstanceId(instanceId);
        templateFlowCommentBean.setTaskOpId(taskOpId);
        templateFlowCommentBean.setNodeId(nodeOpId);
        templateFlowCommentBean.setFlowComment(backFlowBO.getFlowComment());
        templateFlowCommentBean.setFlowSort(_flowDao.queryCommentSortByInstanceFlowId(backFlowBO.getInstanceFlowId(), flowMainToTableBean.getFlowCommentTableName()));
        templateFlowCommentBean.setFlowResult(backFlowBO.getFlowResultEnum().getValue());
        templateFlowCommentBean.setInstanceFlowId(backFlowBO.getInstanceFlowId());
        templateFlowCommentBean.setUserOpId(backFlowBO.getUserOpId());
        templateFlowCommentBean.setDeptId(backFlowBO.getDeptId());
        templateFlowCommentBean.setOrgId(backFlowBO.getOrgId());
        _flowDao.insertComment(templateFlowCommentBean, flowMainToTableBean.getFlowCommentTableName());
        //再插入新的待办
        TemplateFlowInstanceFlowBean nextFlowInstanceFlowBean = new TemplateFlowInstanceFlowBean();
        nextFlowInstanceFlowBean.setOpId(UUIDGenerator.getUUID());
        nextFlowInstanceFlowBean.setInstanceId(instanceId);
        nextFlowInstanceFlowBean.setTaskOpId(taskOpId);
        nextFlowInstanceFlowBean.setCreateTime(new Date());
        String nextNodeId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NEXT_NODE_ID);
        String nextNodeKey = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NEXT_NODE_KEY);
        nextFlowInstanceFlowBean.setNodeId(nextNodeId); //当前流转数据的下一节点就是新插入的当前节点
        nextFlowInstanceFlowBean.setNodeKey(nextNodeKey);
        nextFlowInstanceFlowBean.setOrgId(backFlowBO.getOrgId());
        String nextNode = _flowNodeMapper.queryNextJsonByOpId(nextNodeId);
        JSONObject nextJson = JSONUtil.parseObj(nextNode);
        JSONObject jsonObject = nextJson.getJSONObject(nextNodeKey);

        if(jsonObject != null) {

            nextFlowInstanceFlowBean.setNextNodeId(jsonObject.getStr(Constant.Key.NEXT_NODE_ID));
            nextFlowInstanceFlowBean.setNextNodeKey(jsonObject.getStr(Constant.Key.NEXT_NODE_KEY));
        }else {
            throw new RuntimeException("没有配置下一环节数据");
        }
        //可能会流转到下一个科室，所以在next中更新
        //nextFlowInstanceFlowEntity.setDeptId(flowInstanceFlowEntity.getDeptId());
        nextFlowInstanceFlowBean.setLastNodeId(nodeOpId);
        nextFlowInstanceFlowBean.setLastNodeKey((String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NODE_KEY));
        nextFlowInstanceFlowBean.setLastOpId(backFlowBO.getInstanceFlowId());
        nextFlowInstanceFlowBean.setFlowStatus(FlowInstanceFlowFlowStatusEnum.WAIT_OPERATE.getValue());
        int sort = (int) flowInstanceFlowDatas.get(0).get(Constant.Flow.FLOW_SORT);
        nextFlowInstanceFlowBean.setFlowSort(sort + 1);
        _flowDao.insertNextFlowInstanceFlowData(nextFlowInstanceFlowBean, flowMainToTableBean.getFlowInstanceFlowTableName());
    }

    @Override
    public void endFlow(EndFlowBO endFlowBO) {

        if(StrUtil.isEmpty(endFlowBO.getInstanceFlowId())) {
            throw new RuntimeException("流程流转id不能为空");
        }

        if(StrUtil.isEmpty(endFlowBO.getMainId())) {
            throw new RuntimeException("mainId不能为空");
        }
        //先获取业务表的数据
        FlowMainToTableBean flowMainToTableBean = _flowInitService.getMainToTableDataFromRedis(endFlowBO.getMainId());
        //1、更新流程流转
        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.TABLE_NAME, flowMainToTableBean.getFlowInstanceFlowTableName());
        param.put(Constant.Key.OP_ID, endFlowBO.getInstanceFlowId());
        param.put(Constant.Flow.FLOW_STATUS, 1);
        param.put(Constant.Flow.FLOW_RESULT, endFlowBO.getFlowResultEnum().getValue()); //结束肯定是通过啦
        _flowDao.updateNextFlowInstanceFlow(param);
        //2、插入流程意见
        List<Map<String, Object>> flowInstanceFlowDatas = _flowDao.queryFlowInstanceFlow(endFlowBO.getInstanceFlowId(), flowMainToTableBean.getFlowInstanceFlowTableName());
        String nodeOpId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NODE_ID);
        String instanceId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.INSTANCE_ID);
        String taskOpId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.TASK_OP_ID);
        TemplateFlowCommentBean templateFlowCommentBean = new TemplateFlowCommentBean();
        templateFlowCommentBean.setInstanceId(instanceId);
        templateFlowCommentBean.setTaskOpId(taskOpId);
        templateFlowCommentBean.setNodeId(nodeOpId);
        templateFlowCommentBean.setFlowComment(endFlowBO.getFlowComment());
        templateFlowCommentBean.setFlowSort(_flowDao.queryCommentSortByInstanceFlowId(endFlowBO.getInstanceFlowId(), flowMainToTableBean.getFlowCommentTableName()));
        templateFlowCommentBean.setFlowResult(endFlowBO.getFlowResultEnum().getValue());
        templateFlowCommentBean.setInstanceFlowId(endFlowBO.getInstanceFlowId());
        templateFlowCommentBean.setUserOpId(endFlowBO.getUserOpId());
        templateFlowCommentBean.setDeptId(endFlowBO.getDeptId());
        templateFlowCommentBean.setOrgId(endFlowBO.getOrgId());
        _flowDao.insertComment(templateFlowCommentBean, flowMainToTableBean.getFlowCommentTableName());
        //3、插入流程流转结束数据
        TemplateFlowInstanceFlowBean nextFlowInstanceFlowBean = new TemplateFlowInstanceFlowBean();
        nextFlowInstanceFlowBean.setOpId(UUIDGenerator.getUUID());
        nextFlowInstanceFlowBean.setInstanceId(instanceId);
        nextFlowInstanceFlowBean.setTaskOpId(taskOpId);
        nextFlowInstanceFlowBean.setCreateTime(new Date());
        String nextNodeId = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NEXT_NODE_ID);
        String nextNodeKey = (String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NEXT_NODE_KEY);
        nextFlowInstanceFlowBean.setNodeId(nextNodeId); //当前流转数据的下一节点就是新插入的当前节点
        nextFlowInstanceFlowBean.setNodeKey(nextNodeKey);
        nextFlowInstanceFlowBean.setOrgId(endFlowBO.getOrgId());
        //nextFlowInstanceFlowEntity.setDeptId(flowInstanceFlowEntity.getDeptId());
        nextFlowInstanceFlowBean.setLastNodeId(nodeOpId);
        nextFlowInstanceFlowBean.setLastNodeKey((String) flowInstanceFlowDatas.get(0).get(Constant.Flow.NODE_KEY));
        nextFlowInstanceFlowBean.setLastOpId(endFlowBO.getInstanceFlowId());
        nextFlowInstanceFlowBean.setFlowStatus(FlowInstanceFlowFlowStatusEnum.OPERATED.getValue());
        int sort = (int) flowInstanceFlowDatas.get(0).get(Constant.Flow.FLOW_SORT);
        nextFlowInstanceFlowBean.setFlowSort(sort + 1);
        _flowDao.insertNextFlowInstanceFlowData(nextFlowInstanceFlowBean, flowMainToTableBean.getFlowInstanceFlowTableName());
        //4、更新流程实例数据
        TemplateFlowInstanceBean endBean = new TemplateFlowInstanceBean();
        endBean.setOpId(instanceId);
        endBean.setEndTime(new Date());
        endBean.setFlowStatus(FlowStatusEnums.END.getValue());
        //计算所有环节的总时间
        Map<String, Object> columns = new HashMap<>();
        columns.put(Constant.Column.MAIN_ID, endFlowBO.getMainId());
        List<PublicFlowNodeEntity> publicFlowNodeEntities = _flowNodeMapper.selectByMap(columns);
        int totalTime = 0;

        for(PublicFlowNodeEntity entity: publicFlowNodeEntities) {
            totalTime += entity.getLimitTime();
        }
        //计算到创建时间位置的时间
        List<Map<String, Object>> datas = _flowDao.queryFlowInstance(instanceId, flowMainToTableBean.flowInstanceTableName);
        Date createTime = (Date) datas.get(0).get(Constant.Key.CREATE_TIME);
        int days = DateUtil.differentDaysByMillisecond(createTime, endBean.getEndTime());
        endBean.setTotalTime(days);
        endBean.setIsOverTime((totalTime > days) ? 0 : 1);
        _flowDao.updateEndFlow(endBean, flowMainToTableBean.getFlowInstanceTableName());
    }
}
