package com.flowengine.server.backend.service.flow.impl;


import com.flowengine.server.backend.dao.flow.MainDao;
import com.flowengine.server.backend.service.flow.MainService;
import com.flowengine.server.core.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private MainDao _mainDao;

    @Override
    public String query(Map<String, Object> param) {

        List<Map<String, Object>> datas = _mainDao.queryFlow(param);

        if(datas != null && datas.size() > 0) {
            return renderQuerySuccessList(_mainDao.queryFlowTotal(param), datas);
        }

        return renderQuerySuccessList(0, datas);
    }
}
