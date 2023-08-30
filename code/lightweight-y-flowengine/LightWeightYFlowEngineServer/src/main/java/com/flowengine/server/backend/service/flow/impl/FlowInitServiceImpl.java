package com.flowengine.server.backend.service.flow.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.flowengine.common.utils.mapper.PublicFlowMainMapper;
import com.flowengine.common.utils.mapper.createmodel.PublicFlowTableFlowInstanceMapper;
import com.flowengine.server.backend.service.flow.FlowInitService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.model.flow.enums.TableFlowInstanceTableType;
import com.flowengine.server.model.flow.model.FlowMainToTableBean;
import com.flowengine.server.utils.Constant;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/30
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FlowInitServiceImpl extends BaseService implements FlowInitService {

    @Resource
    private PublicFlowMainMapper _publicFlowMainMapper;

    @Resource
    private PublicFlowTableFlowInstanceMapper _publicFlowTableFlowInstanceMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public FlowMainToTableBean getMainToTableDataFromRedis(String mainOpId) {

        String json = (String) redisTemplate.opsForValue().get(mainOpId);
        JSONObject entries = JSONUtil.parseObj(json);
        FlowMainToTableBean flowMainToTableBean = new FlowMainToTableBean();
        flowMainToTableBean.setFlowInstanceFlowTableName(entries.getStr(Constant.Flow.FLOW_INSTANCE_FLOW_TABLE_NAME));
        flowMainToTableBean.setFlowInstanceTableName(entries.getStr(Constant.Flow.FLOW_INSTANCE_TABLE_NAME));
        flowMainToTableBean.setReferenceTableName(entries.getStr(Constant.Flow.REFERENCE_TABLE_NAME));

        return flowMainToTableBean;
    }

    @Override
    public void deleteFlowMainDataFromRedis(String mainOpId) {
        redisTemplate.delete(mainOpId);
    }

    @Override
    public void initFlowMainData(String mainOpId, String referenceTableName, String referenceTableId) {

        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put(Constant.Flow.REFERENCE_TABLE_NAME, referenceTableName);
        List<Map<String, Object>> tableFlowInstances = _publicFlowTableFlowInstanceMapper.queryByTableOpId(referenceTableId);

        for(Map<String, Object> tableFlowInstance: tableFlowInstances) {

            Integer tableType = (Integer) tableFlowInstance.get(Constant.Key.TABLE_TYPE);
            String tableName = (String) tableFlowInstance.get(Constant.Key.TABLE_NAME);

            if(tableType == TableFlowInstanceTableType.FLOW_INSTANCE.getValue()) {
                jsonMap.put(Constant.Flow.FLOW_INSTANCE_TABLE_NAME, tableName);
            }else if (tableType == TableFlowInstanceTableType.FLOW_INSTANCE_FLOW.getValue()) {
                jsonMap.put(Constant.Flow.FLOW_INSTANCE_FLOW_TABLE_NAME, tableName);
            }
        }

        redisTemplate.opsForValue().set(mainOpId, getJSON(jsonMap));
    }

    @Override
    public void initFlowMainDatas() {

        List<Map<String, Object>> mainDatas = _publicFlowMainMapper.queryAllData();

        for(Map<String, Object> mainData: mainDatas) {

            String opId = (String) mainData.get(Constant.Key.OP_ID);
            String referenceTableName = (String) mainData.get(Constant.Flow.REFERENCE_TABLE_NAME);
            String referenceTableId = (String) mainData.get(Constant.Flow.REFERENCE_TABLE_ID);
            initFlowMainData(opId, referenceTableName, referenceTableId);
        }
    }
}
