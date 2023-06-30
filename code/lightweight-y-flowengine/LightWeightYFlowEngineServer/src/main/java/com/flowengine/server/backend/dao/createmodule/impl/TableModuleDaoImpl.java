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
    public List<Map<String, Object>> query(Map<String, Object> param) {


        Integer page = (Integer) param.get(Constant.Key.PAGE);
        Integer limit = (Integer) param.get(Constant.Key.LIMIT);
        String opId = (String) param.get(Constant.Key.OP_ID);
        String parentId = (String) param.get(Constant.Key.PARENT_ID);
        String menuText = (String) param.get(Constant.Key.MENU_TEXT);
        List<Object> array = new ArrayList<>();
        String sql = """
				select
				    a.op_id "opId",
				    a.parent_id "parentId",
				    a.url "url",
				    a.text "text",
				    a.type::TEXT "type",
				    a.available_flag::TEXT "availableFlag",
				    a.sort "sort",
				    a.sys "sys",
				    a.icon "icon",
				    a.category "category"
				    from public_menu a where 1=1 
								
				""";
        StringBuffer sb = new StringBuffer(sql);

        if(StrUtil.isNotEmpty(opId)) {
            sb.append(" and a.op_id = ? ");
            array.add(opId);
        }

        if(StrUtil.isNotEmpty(parentId)) {
            sb.append(" and a.parent_id = ? ");
            array.add(parentId);
        }

        if(StrUtil.isNotEmpty(menuText)) {
            sb.append(" and a.text like ? ");
            array.add(menuText + "%");
        }

        sb.append(" ORDER BY a.sort asc LIMIT ? offset ? ");
        array.add(limit);
        array.add(limit*(page-1));
        sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, array.toArray());
    }
}
