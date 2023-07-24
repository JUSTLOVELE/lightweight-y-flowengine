package com.flowengine.server.backend.dao.createmodule.impl;

import cn.hutool.core.util.StrUtil;
import com.flowengine.server.backend.dao.createmodule.TableManageDao;
import com.flowengine.server.core.BaseDao;
import com.flowengine.server.utils.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2023-07-19
 * @version 1.00.00
 * @history:
 */
@Repository
public class TableManageDaoImpl extends BaseDao implements TableManageDao {

    private final static Log _logger = LogFactory.getLog(TableManageDaoImpl.class);

    @Override
    public int queryTotal(Map<String, Object> param) {

        String tableName = (String) param.get(Constant.Key.TABLE_NAME);
        String tableNameDesc = (String) param.get(Constant.Key.TABLE_NAME_DESC);
        List<Object> array = new ArrayList<>();
        String sql = """
				select count(*) from public_flow_table_name_tbl a where 1=1 
				""";
        StringBuffer sb = new StringBuffer(sql);

        if(StrUtil.isNotEmpty(tableName)) {
            sb.append(" and a.table_name like ? ");
            array.add(tableName + "%");
        }

        if(StrUtil.isNotEmpty(tableNameDesc)) {
            sb.append(" and a.table_name_desc like ? ");
            array.add(tableNameDesc + "%");
        }

        sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForObject(sql, array.toArray(), Integer.class);
    }

    @Override
    public List<Map<String, Object>> query(Map<String, Object> param) {

        Integer page = (Integer) param.get(Constant.Key.PAGE);
        Integer limit = (Integer) param.get(Constant.Key.LIMIT);
        String tableName = (String) param.get(Constant.Key.TABLE_NAME);
        String tableNameDesc = (String) param.get(Constant.Key.TABLE_NAME_DESC);

        List<Object> array = new ArrayList<>();
        String sql = """
				select
				    a.op_id "opId",
				    a.table_name "tableName",
				    a.table_module_id "tableModuleId",
                    a.table_module_name "tableModuleName",
                    to_char(a.create_time, 'YYYY-MM-DD') as "createTime",
				    a.table_name_desc "tableNameDesc" from public_flow_table_name_tbl a where 1=1 
				""";
        StringBuffer sb = new StringBuffer(sql);

        if(StrUtil.isNotEmpty(tableName)) {
            sb.append(" and a.table_name like ? ");
            array.add(tableName + "%");
        }

        if(StrUtil.isNotEmpty(tableNameDesc)) {
            sb.append(" and a.table_name_desc like ? ");
            array.add(tableNameDesc + "%");
        }

        sb.append(" ORDER BY a.op_id asc LIMIT ? offset ? ");
        array.add(limit);
        array.add(limit*(page-1));
        sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, array.toArray());
    }
}
