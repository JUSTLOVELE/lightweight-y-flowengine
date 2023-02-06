package com.flowengine.server.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author yangzl 2022.08.22
 * @version 1.00.00
 * @Description:
 * @Copyright:
 * @Company:
 * @history:
 */
public abstract class BaseDao extends Base {

    @Autowired
    protected JdbcTemplate _jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return this._jdbcTemplate;
    }
}
