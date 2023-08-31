package com.flowengine.server.backend.dao.flow.impl;

import com.flowengine.server.backend.dao.flow.FlowDao;
import com.flowengine.server.core.BaseDao;
import com.flowengine.server.model.flow.model.StartFlowBO;
import com.flowengine.server.model.flow.model.TemplateFlowInstanceBean;
import com.flowengine.server.utils.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yangzl 2023/8/31
 * @version 1.00.00
 * @Description:
 * @history:
 */
public class FlowDaoImpl extends BaseDao implements FlowDao {

    private final static Log _logger = LogFactory.getLog(FlowDaoImpl.class);

    @Override
    public void insertStartFlowInstanceData(TemplateFlowInstanceBean flowInstanceBean, String tableName, StartFlowBO startFlowVO) {

        StringBuffer sb = new StringBuffer();
        sb.append("insert into " + tableName);
        sb.append("""
                (op_id, create_time, flow_status, task_op_id, main_id, org_id, dept_id, create_user_op_id)
                values (?,to_date(?, 'YYYY-MM-DD hh:mi:ss'),?,?,?,?,?,?)""");
        String sql = sb.toString();
        _logger.info(sql);
        List<Object> array = new ArrayList<>();
        array.add(flowInstanceBean.getOpId());
        array.add(DateUtil.toString(flowInstanceBean.getCreateTime(), DateUtil.YMDHMS));
        array.add(flowInstanceBean.getFlowStatus());
        array.add(flowInstanceBean);
        array.add(startFlowVO.getTaskOpId());
        array.add(startFlowVO.getMainId());
        array.add(startFlowVO.getOrgId());
        array.add(startFlowVO.getDeptId());
        array.add(startFlowVO.getCreateUserOpId());
        this.getJdbcTemplate().update(sql, array.toArray());
    }
}
