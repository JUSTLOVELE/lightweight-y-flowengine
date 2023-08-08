package com.flowengine.server.backend.service.flow.impl;

import com.flowengine.common.utils.mapper.PublicSubCfgMapper;
import com.flowengine.server.backend.service.flow.NodeService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.utils.Constant;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/7
 * @version 1.00.00
 * @Description:
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NodeServiceImpl extends BaseService implements NodeService {

    @Resource
    private PublicSubCfgMapper _publicSubCfgMapper;

    @Override
    public String getCheckTypeCombobox() {

        List<Map<String, Object>> datas = _publicSubCfgMapper.getCombobox(Constant.Key.FLOW_NODE_CHECK_TYPE);
        return getJSON(datas);
    }

    @Override
    public String getNodeStatusCombobox() {

        List<Map<String, Object>> datas = _publicSubCfgMapper.getCombobox(Constant.Column.NODE_STATUS);
        return getJSON(datas);
    }

    @Override
    public String getNodeTypeCombobox() {

        List<Map<String, Object>> nodeTypeComboboxDatas = _publicSubCfgMapper.getNodeTypeCombobox();
        return getJSON(nodeTypeComboboxDatas);
    }
}
