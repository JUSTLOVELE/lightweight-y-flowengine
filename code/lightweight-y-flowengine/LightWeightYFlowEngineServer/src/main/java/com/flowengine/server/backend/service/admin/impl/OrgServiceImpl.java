package com.flowengine.server.backend.service.admin.impl;

import com.flowengine.common.utils.mapper.PublicOrgMapper;
import com.flowengine.server.backend.service.admin.OrgService;
import com.flowengine.server.core.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrgServiceImpl extends BaseService implements OrgService {

    @Resource
    private PublicOrgMapper _orgMapper;

    @Override
    public String getCombobox() {
        return getJSON(_orgMapper.queryByCombobox());
    }
}
