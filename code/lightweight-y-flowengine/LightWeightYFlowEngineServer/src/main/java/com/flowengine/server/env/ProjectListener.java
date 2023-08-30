package com.flowengine.server.env;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.stereotype.Component;

/**
 * @author yangzl 2023/8/28
 * @version 1.00.00
 * @Description:
 * @history:
 */
public class ProjectListener implements SpringApplicationRunListener {

    public ProjectListener(SpringApplication application, String[] args) {
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        SpringApplicationRunListener.super.starting(bootstrapContext);
        System.out.println("项目启动");
    }
}
