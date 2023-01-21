package com.flowengine.server.backend.service;

/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
public interface FlowService {

    /**
     * 启动流程
     */
    public void startFlow();

    /**
     * 下一个环节
     */
    public void next();

    /**
     * 回退
     */
    public void back();

    /**
     * 结束流程
     */
    public void endFlow();
}
