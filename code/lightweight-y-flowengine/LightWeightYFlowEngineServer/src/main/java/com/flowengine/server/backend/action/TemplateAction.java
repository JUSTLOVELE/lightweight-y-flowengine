package com.flowengine.server.backend.action;

import com.flowengine.server.core.BaseAction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023.07.19
 * @version 1.00.00
 * @history:
 */
@RestController
public class TemplateAction extends BaseAction {

    /**
     * 表格查询
     * @param limit
     * @param page
     * @return
     */
    @GetMapping(value = "/templateAction/tableQuery", produces = "application/json; charset=utf-8")
    public String tableQuery(Integer limit, Integer page, String tableNameDesc, String tableName) {


        List<Map<String, Object>> datas = new ArrayList<>();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("date", "2023-07-19");
        data1.put("name", "张三");
        data1.put("address", "福州");
        Map<String, Object> data2 = new HashMap<>();
        data2.put("date", "2023-08-19");
        data2.put("name", "李四");
        data2.put("address", "南平");
        Map<String, Object> data3 = new HashMap<>();
        data3.put("date", "2023-09-19");
        data3.put("name", "王五");
        data3.put("address", "建瓯");
        datas.add(data2);
        datas.add(data1);
        datas.add(data3);

        return renderQuerySuccessList(30, datas);
    }
}
