package com.flowengine.server.backend.dao.admin.impl;

import cn.hutool.core.util.StrUtil;
import com.flowengine.server.backend.dao.admin.DictConfigDao;
import com.flowengine.server.core.BaseDao;
import com.flowengine.server.utils.Constant;
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
 * @history:
 */
@Repository
public class DictConfigDaoImpl extends BaseDao implements DictConfigDao {

    private final static Log _logger = LogFactory.getLog(DictConfigDaoImpl.class);

    @Override
    public int queryTotal(Map<String, Object> param) {

        String cfgName = (String) param.get(Constant.Key.CFG_NAME);
        String keyCode = (String) param.get(Constant.Key.KEY_CODE);
        List<Object> array = new ArrayList<>();
        String sql = """
				select count(*)  from public_cfg a where 1=1 
				""";
        StringBuffer sb = new StringBuffer(sql);

        if(StrUtil.isNotEmpty(cfgName)) {
            sb.append(" and a.cfg_name = ? ");
            array.add("%" + cfgName + "%");
        }

        if(StrUtil.isNotEmpty(keyCode)) {
            sb.append(" and a.key_code = ? ");
            array.add("%" + keyCode + "%");
        }

        sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForObject(sql, Integer.class, array.toArray());
    }

    @Override
    public List<Map<String, Object>> query(Map<String, Object> param) {

        Integer page = (Integer) param.get(Constant.Key.PAGE);
        Integer limit = (Integer) param.get(Constant.Key.LIMIT);
        String cfgName = (String) param.get(Constant.Key.CFG_NAME);
        String keyCode = (String) param.get(Constant.Key.KEY_CODE);
        List<Object> array = new ArrayList<>();
        String sql = """
				select
				    a.op_id "opId",
				    a.org_id "orgId",
				    a.cfg_name "cfgName",
				    a.key_code "keyCode",
				    a.sort "sort",
				    a.cfg_type "cfgType",
				    case when a.cfg_type = 1 then '系统配置'
                        else '系统字典' end "cfgTypeText",
				    a.dept_id "deptId" from public_cfg a where 1=1 
				""";
        StringBuffer sb = new StringBuffer(sql);

        if(StrUtil.isNotEmpty(cfgName)) {
            sb.append(" and a.cfg_name = ? ");
            array.add("%" + cfgName + "%");
        }

        if(StrUtil.isNotEmpty(keyCode)) {
            sb.append(" and a.key_code = ? ");
            array.add("%" + keyCode + "%");
        }

        sb.append(" ORDER BY a.sort asc LIMIT ? offset ? ");
        array.add(limit);
        array.add(limit*(page-1));
        sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, array.toArray());
    }

}
