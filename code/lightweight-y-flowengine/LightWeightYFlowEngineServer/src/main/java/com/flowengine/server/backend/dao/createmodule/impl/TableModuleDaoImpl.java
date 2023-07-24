package com.flowengine.server.backend.dao.createmodule.impl;

import cn.hutool.core.util.StrUtil;
import com.flowengine.server.backend.dao.createmodule.TableModuleDao;
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
 * @author yangzl 2023-06-30
 * @version 1.00.00
 * @history:
 */
@Repository
public class TableModuleDaoImpl extends BaseDao implements TableModuleDao {

    private final static Log _logger = LogFactory.getLog(TableModuleDaoImpl.class);

    @Override
    public List<Map<String, Object>> getCombobox() {

        String sql = """
                select a.op_id  value , a.module_name label from public_flow_table_module_tbl a
                """;
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql);
    }

    @Override
    public int queryTotal(Map<String, Object> param) {

        String moduleName = (String) param.get(Constant.Key.MODULE_NAME);
        List<Object> array = new ArrayList<>();
        String sql = """
				select count(*) from public_flow_table_module_tbl a where 1=1 
				""";
        StringBuffer sb = new StringBuffer(sql);

        if(StrUtil.isNotEmpty(moduleName)) {
            sb.append(" and a.module_name like ? ");
            array.add(moduleName + "%");
        }

        sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForObject(sql, array.toArray(), Integer.class);
    }

    @Override
    public List<Map<String, Object>> query(Map<String, Object> param) {

        Integer page = (Integer) param.get(Constant.Key.PAGE);
        Integer limit = (Integer) param.get(Constant.Key.LIMIT);
        String moduleName = (String) param.get(Constant.Key.MODULE_NAME);
        List<Object> array = new ArrayList<>();
        String sql = """
				select
				    a.op_id "opId",
				    a.module_name "moduleName",
				    a.authority "authority" from public_flow_table_module_tbl a where 1=1 
				""";
        StringBuffer sb = new StringBuffer(sql);

        if(StrUtil.isNotEmpty(moduleName)) {
            sb.append(" and a.module_name like ? ");
            array.add(moduleName + "%");
        }

        sb.append(" ORDER BY a.op_id asc LIMIT ? offset ? ");
        array.add(limit);
        array.add(limit*(page-1));
        sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, array.toArray());
    }
}
