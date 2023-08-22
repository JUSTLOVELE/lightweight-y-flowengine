package com.flowengine.server.backend.service.flow.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.flowengine.common.utils.entity.PublicFlowMainEntity;
import com.flowengine.common.utils.entity.PublicFlowNodeCheckEntity;
import com.flowengine.common.utils.entity.PublicFlowNodeEntity;
import com.flowengine.common.utils.enums.FlowCheckTypeEnum;
import com.flowengine.common.utils.mapper.PublicFlowMainMapper;
import com.flowengine.common.utils.mapper.PublicFlowNodeCheckMapper;
import com.flowengine.common.utils.mapper.PublicFlowNodeMapper;
import com.flowengine.common.utils.mapper.PublicFlowTableNameMapper;
import com.flowengine.server.backend.dao.flow.MainDao;
import com.flowengine.server.backend.service.flow.MainService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.UUIDGenerator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023.2.22
 * @version 1.00.00
 * @history:
 */
@Service
@Transactional
public class MainServiceImpl extends BaseService implements MainService {

    @Resource
    private MainDao _mainDao;

    @Resource
    private PublicFlowMainMapper _mainMapper;

    @Resource
    private PublicFlowNodeMapper _nodeMapper;

    @Resource
    private PublicFlowNodeCheckMapper _nodeCheckMapper;

    @Resource
    private PublicFlowTableNameMapper _flowTableNameMapper;

    @Override
    public String edit(PublicFlowMainEntity mainEntity, String children) {

        PublicFlowMainEntity publicFlowMainEntity = _mainMapper.selectById(mainEntity.getOpId());
        publicFlowMainEntity.setIsStop(mainEntity.getIsStop());
        publicFlowMainEntity.setMainName(mainEntity.getMainName());
        publicFlowMainEntity.setDeptId(mainEntity.getDeptId());

        if(!mainEntity.getReferenceTableId().equals(publicFlowMainEntity.getReferenceTableId())) {
            String tableName = _flowTableNameMapper.queryTableNameByOpId(mainEntity.getReferenceTableId());
            publicFlowMainEntity.setReferenceTableName(tableName);
            publicFlowMainEntity.setReferenceTableId(publicFlowMainEntity.getReferenceTableId());
        }

        _mainMapper.updateById(publicFlowMainEntity);
        _nodeMapper.deleteByMainOpId(mainEntity.getOpId());
        _nodeCheckMapper.deleteByMainOpId(mainEntity.getOpId());
        setNode(children, mainEntity.getOpId());

        return renderOpSuccessList(1);
    }

    @Override
    public String delete(String opId) {

        _mainMapper.deleteById(opId);
        _nodeMapper.deleteByMainOpId(opId);
        _nodeCheckMapper.deleteByMainOpId(opId);
        return renderOpSuccessList(1);
    }

    @Override
    public String add(PublicFlowMainEntity mainEntity, String children) {
        //更新业务表名
        String tableName = _flowTableNameMapper.queryTableNameByOpId(mainEntity.getReferenceTableId());
        mainEntity.setReferenceTableName(tableName);
        _mainMapper.insert(mainEntity);
        setNode(children, mainEntity.getOpId());
        return renderOpSuccessList(1);
    }

    /**
     * 解析json获取PublicFlowNodeEntity集合
     * @param arrayJson
     * @param mainOpId
     * @return
     */
    private void setNode(String arrayJson, String mainOpId) {


        if(StrUtil.isNotEmpty(arrayJson)) {

            JSONArray objects = JSONUtil.parseArray(arrayJson);

            for(int i=0; i<objects.size(); i++) {

                JSONObject jsonObject = objects.getJSONObject(i);
                JSONObject nextNodeJson = jsonObject.getJSONObject(Constant.Key.NEXT_NODE);
                PublicFlowNodeEntity nodeEntity = new PublicFlowNodeEntity();
                nodeEntity.setOpId(UUIDGenerator.getUUID());
                nodeEntity.setMainId(mainOpId);
                nodeEntity.setLimitTime(jsonObject.getInt(Constant.Key.LIMIT_TIME));
                nodeEntity.setNextNode(nextNodeJson.toString());
                nodeEntity.setNodeName(jsonObject.getStr(Constant.Key.NODE_NAME));
                nodeEntity.setNodeKey(jsonObject.getStr(Constant.Key.NODE_KEY));
                nodeEntity.setNodeSort(i+1);
                nodeEntity.setNodeStatus(jsonObject.getStr(Constant.Key.NODE_STATUS));
                _nodeMapper.insert(nodeEntity);
                JSONArray nodeChecks = jsonObject.getJSONArray(Constant.Key.CHILDREN);

                if(nodeChecks != null && !nodeChecks.isEmpty()) {

                    for(int j=0; j<nodeChecks.size(); j++) {

                        JSONObject nodeChecksJSONObject = nodeChecks.getJSONObject(j);
                        PublicFlowNodeCheckEntity flowNodeCheckEntity = new PublicFlowNodeCheckEntity();
                        flowNodeCheckEntity.setOpId(UUIDGenerator.getUUID());
                        flowNodeCheckEntity.setNodeOpId(nodeEntity.getOpId());
                        flowNodeCheckEntity.setNodeType(nodeChecksJSONObject.getStr(Constant.Key.NODE_TYPE));
                        String checkType = nodeChecksJSONObject.getStr(Constant.Key.CHECK_TYPE);
                        flowNodeCheckEntity.setCheckType(checkType);
                        //checkType=20则不要写ref
                        if(!FlowCheckTypeEnum.ALL.getValue().equals(checkType)) {
                            flowNodeCheckEntity.setRefId(nodeChecksJSONObject.getStr(Constant.Key.PERSON));
                        }

                        flowNodeCheckEntity.setNodeSort(nodeChecksJSONObject.getInt(Constant.Key.SORT));
                        flowNodeCheckEntity.setMainId(mainOpId);
                        _nodeCheckMapper.insert(flowNodeCheckEntity);
                    }
                }
            }
        }
    }

    @Override
    public String query(Map<String, Object> param) {

        List<Map<String, Object>> datas = _mainDao.queryFlow(param);

        if(datas != null && datas.size() > 0) {
            return renderQuerySuccessList(_mainDao.queryFlowTotal(param), datas);
        }

        return renderQuerySuccessList(0, datas);
    }
}
