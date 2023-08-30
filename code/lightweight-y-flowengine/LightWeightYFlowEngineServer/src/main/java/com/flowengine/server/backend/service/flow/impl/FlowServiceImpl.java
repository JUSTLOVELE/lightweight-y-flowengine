package com.flowengine.server.backend.service.flow.impl;

import com.flowengine.server.backend.service.flow.FlowInitService;
import com.flowengine.server.backend.service.flow.FlowService;
import com.flowengine.server.model.BackFlowBO;
import com.flowengine.server.model.EndFlowBO;
import com.flowengine.server.model.NextFlowBO;
import com.flowengine.server.model.flow.model.FlowMainToTableBean;
import com.flowengine.server.model.flow.model.StartFlowBO;
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

    @Override
    public void startFlow(StartFlowBO startFlowVO) {
        //先获取业务表的数据
        FlowMainToTableBean flowMainToTableBean = _flowInitService.getMainToTableDataFromRedis(startFlowVO.getMainId());
        //插入业务流程实例表和业务流程流转表

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
