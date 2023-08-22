package com.flowengine.server.backend.dao.flow.impl;

import cn.hutool.core.util.StrUtil;
import com.flowengine.server.backend.dao.flow.MainDao;
import com.flowengine.server.core.BaseDao;
import com.flowengine.server.utils.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:流程主表
 * @author yangzl 2023.02.22
 * @version 1.00.00
 * @history:
 */
@Repository
public class MainDaoImpl extends BaseDao implements MainDao  {

    private final static Log _logger = LogFactory.getLog(MainDaoImpl.class);

    @Override
    public Integer queryFlowTotal(Map<String, Object> param) {

        String mainName = (String) param.get(Constant.Key.NAME);
        String deptId = (String) param.get(Constant.Key.DEPT_ID);
        Integer isStop = (Integer) param.get(Constant.Key.IS_STOP);
        List<Object> array = new ArrayList<>();
        String text = """
                select
                   count(*)
                    from public_flow_main_tbl a where 1=1
                """;
        StringBuffer sb = new StringBuffer(text);

        if(StrUtil.isNotEmpty(mainName)) {

            sb.append(" and a.main_name like ? ");
            array.add("%" + mainName +"%");
        }

        if(isStop != null) {

            sb.append(" and a.is_stop = ? ");
            array.add(isStop);
        }

        if(StrUtil.isNotEmpty(deptId)) {

            sb.append(" and a.dept_id = ? ");
            array.add(deptId);
        }

        String sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForObject(sql, array.toArray(), Integer.class);
    }

    @Override
    public List<Map<String, Object>> queryFlow(Map<String, Object> param) {

        Integer limit = (Integer) param.get(Constant.Key.LIMIT);
        Integer page = (Integer) param.get(Constant.Key.PAGE);
        String mainName = (String) param.get(Constant.Key.NAME);
        String deptId = (String) param.get(Constant.Key.DEPT_ID);
        Integer isStop = (Integer) param.get(Constant.Key.IS_STOP);
        List<Object> array = new ArrayList<>();
        String text = """
                select
                    a.op_id "opId",
                    a.is_stop "isStop",
                    case when a.is_stop = 1 then '启用'
                        else '暂停' end "stopText",
                    a.main_name "mainName",
                    to_char(a.create_time,'YYYY-MM-DD') as "createTime",
                    a.reference_table_name "referenceTableName",
                    a.dept_id "deptId",
                    a.reference_table_id "referenceTableId",
                    (select b.dept_name from public_dept b where b.op_id = a.dept_id) "deptName",
                    a.create_user_name "createUserName"
                    from public_flow_main_tbl a where 1=1
                """;
        StringBuffer sb = new StringBuffer(text);

        if(StrUtil.isNotEmpty(mainName)) {

            sb.append(" and a.main_name like ? ");
            array.add("%" + mainName +"%");
        }

        if(isStop != null) {

            sb.append(" and a.is_stop = ? ");
            array.add(isStop);
        }

        if(StrUtil.isNotEmpty(deptId)) {

            sb.append(" and a.dept_id = ? ");
            array.add(deptId);
        }

        sb.append(" ORDER BY a.create_time DESC LIMIT ? offset ? ");
        array.add(limit);
        array.add(limit*(page-1));
        String sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, array.toArray());
    }
}
