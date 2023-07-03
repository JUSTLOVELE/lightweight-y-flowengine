package com.flowengine.server.backend.service.createmodule.impl;

import com.flowengine.server.backend.dao.createmodule.TableModuleDao;
import com.flowengine.server.backend.service.createmodule.TableModuleService;
import com.flowengine.server.core.Base;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023-06-30
 * @version 1.00.00
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TableModuleServiceImpl extends Base implements TableModuleService {

    @Resource
    private TableModuleDao _tableModuleDao;

    @Override
    public String query(Map<String, Object> param) {

        List<Map<String, Object>> datas = _tableModuleDao.query(param);

        if(datas != null && datas.size() > 0) {
            return renderQuerySuccessList(_tableModuleDao.queryTotal(param), datas);
        }else {
            return renderQuerySuccessList(0, datas);
        }
    }
}
