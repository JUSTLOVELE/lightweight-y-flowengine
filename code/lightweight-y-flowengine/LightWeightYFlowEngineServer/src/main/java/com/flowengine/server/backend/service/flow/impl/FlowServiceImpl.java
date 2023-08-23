package com.flowengine.server.backend.service.flow.impl;

import com.flowengine.server.backend.service.flow.FlowService;
import com.flowengine.server.model.BackFlowBO;
import com.flowengine.server.model.EndFlowBO;
import com.flowengine.server.model.NextFlowBO;
import com.flowengine.server.model.StartFlowBO;
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


    @Override
    public void startFlow(StartFlowBO startFlowVO) {

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
