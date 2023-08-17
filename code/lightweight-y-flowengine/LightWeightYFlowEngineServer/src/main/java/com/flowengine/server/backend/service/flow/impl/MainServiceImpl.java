package com.flowengine.server.backend.service.flow.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.flowengine.server.backend.dao.flow.MainDao;
import com.flowengine.server.backend.service.flow.MainService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.entity.PublicFlowMainEntity;
import com.flowengine.server.entity.PublicFlowNodeEntity;
import com.flowengine.server.mapper.PublicFlowMainMapper;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.UUIDGenerator;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public String add(PublicFlowMainEntity mainEntity, String children) {

        //_mainMapper.insert(mainEntity);
        return renderOpSuccessList(1);
    }

    /**
     * 解析json获取PublicFlowNodeEntity集合
     * @param arrayJson
     * @param mainEntity
     * @return
     */
    private List<PublicFlowNodeEntity> getPublicFlowNodeEntities(String arrayJson, PublicFlowMainEntity mainEntity) {

        List<PublicFlowNodeEntity> nodeEntities = null;

        if(StrUtil.isNotEmpty(arrayJson)) {

            nodeEntities = new ArrayList<>();
            JSONArray objects = JSONUtil.parseArray(arrayJson);

            for(int i=0; i<objects.size(); i++) {

                JSONObject jsonObject = objects.getJSONObject(i);
                JSONObject nextNodeJson = jsonObject.getJSONObject(Constant.Key.NEXT_NODE);
                PublicFlowNodeEntity nodeEntity = new PublicFlowNodeEntity();
                nodeEntity.setOpId(UUIDGenerator.getUUID());
                nodeEntity.setMainId(mainEntity.getOpId());
                nodeEntity.setLimitTime(jsonObject.getInt(Constant.Key.LIMIT_TIME));
                nodeEntity.setNextNode(nextNodeJson.toString());
                nodeEntity.setNodeName(jsonObject.getStr(Constant.Key.NODE_NAME));
                nodeEntity.setNodeKey(jsonObject.getStr(Constant.Key.NODE_KEY));
                nodeEntity.setNodeType(jsonObject.getStr(Constant.Key.NODE_TYPE));
                nodeEntity.setNodeSort(i+1);
                nodeEntity.setNodeStatus(jsonObject.getStr(Constant.Key.NODE_STATUS));
                nodeEntity.setCheckType(jsonObject.getStr(Constant.Key.CHECK_TYPE));
                nodeEntities.add(nodeEntity);
            }
        }

        return nodeEntities;
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
