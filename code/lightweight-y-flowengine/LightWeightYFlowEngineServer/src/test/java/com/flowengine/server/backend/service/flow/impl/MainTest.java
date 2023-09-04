package com.flowengine.server.backend.service.flow.impl;

import cn.hutool.core.util.XmlUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
public class MainTest {

    @Test
    public void XMLTest() {

        outer:
        for(int i=0; i<10; i++) {
            System.out.println("i=" + i);
            for(int j=0; j<10; j++) {
                System.out.println("j=" + j);
                if(j==5) {
                    System.out.println("end");
                    break outer;
                }
            }
        }

    }
}
