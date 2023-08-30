package com.flowengine.server.backend.service.flow.impl;

import com.flowengine.server.Main;
import com.flowengine.server.backend.service.flow.FlowService;
import com.flowengine.server.model.flow.model.StartFlowBO;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 1、startFlowTest：开始流程测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes ={Main.class, FlowServiceImplTest.class})
public class FlowServiceImplTest {

    @Resource
    private FlowService _flowService;

    @Test
    public void startFlowTest() {

        StartFlowBO startFlowBO = new StartFlowBO();
        _flowService.startFlow(startFlowBO);
    }
}
