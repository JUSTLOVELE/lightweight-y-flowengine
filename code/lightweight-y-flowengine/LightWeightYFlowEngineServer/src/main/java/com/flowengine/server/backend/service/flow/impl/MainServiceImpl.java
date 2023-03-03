package com.flowengine.server.backend.service.flow.impl;


import com.flowengine.server.backend.dao.flow.MainDao;
import com.flowengine.server.backend.service.flow.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023.2.22
 * @version 1.00.00
 * @history:
 */
@Service
@Transactional
public class MainServiceImpl implements MainService {

    @Autowired
    private MainDao mainDao;

    @Override
    public String query(Map<String, Object> param) {
        return null;
    }
}
