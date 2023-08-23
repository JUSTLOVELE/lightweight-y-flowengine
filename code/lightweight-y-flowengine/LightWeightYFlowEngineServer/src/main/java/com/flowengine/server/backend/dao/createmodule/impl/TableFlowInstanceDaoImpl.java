package com.flowengine.server.backend.dao.createmodule.impl;

import com.flowengine.server.backend.dao.createmodule.TableFlowInstanceDao;
import com.flowengine.server.core.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author yangzl 2023/8/23
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
@Repository
public class TableFlowInstanceDaoImpl extends BaseDao implements TableFlowInstanceDao {

    @Override
    public int queryCountByTableName(String tableName) {

        String sql = "select 1 as \"c\" from " + tableName + " limit 1";
        return this.getJdbcTemplate().queryForObject(sql, Integer.class);
    }
}
