package com.flowengine.server.backend.dao.flow.impl;

import cn.hutool.core.util.StrUtil;
import com.flowengine.server.backend.dao.admin.impl.RoleDaoImpl;
import com.flowengine.server.backend.dao.flow.FlowRoleDao;
import com.flowengine.server.core.BaseDao;
import com.flowengine.server.model.UserCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
@Repository
public class FlowRoleDaoImpl extends BaseDao implements FlowRoleDao {

    private final static Log _logger = LogFactory.getLog(RoleDaoImpl.class);

    @Override
    public Integer queryTotal(String name, UserCache userCache) {

        List<Object> array = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT count(*) FROM public_flow_role_tbl a WHERE 1=1 ");

        if(StrUtil.isNotEmpty(name)) {
            sb.append(" and a.role_name like  ? ");
            array.add("%" + name + "%");
        }

        String sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForObject(sql, Integer.class, array.toArray());
    }

    @Override
    public List<Map<String, Object>> query(String name, Integer limit, Integer page, UserCache userCache) {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("a.op_id \"opId\",");
        sb.append("a.role_name \"roleName\",");
        sb.append("a.role_type \"roleType\",");
        sb.append("  case when a.role_type = 1 then '超级管理员' ");
        sb.append("   when a.role_type = 2 then '机构管理员' ");
        sb.append("   when a.role_type = 20 then '负责人' ");
        sb.append("   when a.role_type = 40 then '普通' ");
        sb.append("else '未知错误' end \"roleTypeDesc\",  ");
        sb.append("to_char(a.create_time, 'YYYY-MM-DD hh24:mi:ss') as \"createTime\" ");
        sb.append(" FROM public_flow_role_tbl a WHERE 1=1 "); //仅查询普通用户角色
        List<Object> array = new ArrayList<>();

        if(StrUtil.isNotEmpty(name)) {
            sb.append(" and a.role_name like ? ");
            array.add("%" + name + "%");
        }

        sb.append(" ORDER BY a.create_time desc LIMIT ? offset ? ");
        array.add(limit);
        array.add(limit*(page-1));

        String sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, array.toArray());
    }
}
