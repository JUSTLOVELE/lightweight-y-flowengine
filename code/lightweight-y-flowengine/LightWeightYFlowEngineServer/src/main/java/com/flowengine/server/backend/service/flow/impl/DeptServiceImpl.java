package com.flowengine.server.backend.service.flow.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flowengine.common.utils.mapper.PublicDeptMapper;
import com.flowengine.server.backend.service.flow.DeptService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.SessionUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description:科室
 * @author yangzl 2023.03.22
 * @version 1.00.00
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DeptServiceImpl extends BaseService implements DeptService {

    @Resource
    private PublicDeptMapper _publicDeptMapper;

    @Override
    public String getCombobox() {

        List<Map<String, Object>> combobox = _publicDeptMapper.getCombobox();
        return getJSON(_publicDeptMapper.getCombobox());
    }

    @Override
    public String selectQuery(Map<String, Object> param) {

        UserCache userCache = (UserCache) param.get(SessionUtils.USER_SESSION);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("op_id value", "dept_name label");
        queryWrapper.ge("org_id", userCache.getOrgId());
        List<Map<String, Object>> datas = _publicDeptMapper.selectMaps(queryWrapper);

        return renderQuerySuccessList(datas.size(), datas);
    }
}
