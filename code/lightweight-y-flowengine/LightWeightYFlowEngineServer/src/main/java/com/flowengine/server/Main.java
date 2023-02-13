package com.flowengine.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class Main {
    /**
     * http://localhost:8080/flow/login
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}