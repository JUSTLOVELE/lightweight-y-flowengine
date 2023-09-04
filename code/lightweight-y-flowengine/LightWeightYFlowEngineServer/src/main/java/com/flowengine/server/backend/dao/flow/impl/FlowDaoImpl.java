package com.flowengine.server.backend.dao.flow.impl;

import com.flowengine.common.utils.DateUtil;
import com.flowengine.server.backend.dao.flow.FlowDao;
import com.flowengine.server.core.BaseDao;
import com.flowengine.server.model.flow.model.StartFlowBO;
import com.flowengine.server.model.flow.model.TemplateFlowInstanceBean;
import com.flowengine.server.model.flow.model.TemplateFlowInstanceFlowBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public List<Map<String, Object>> queryFlowInstanceFlow(String opId, String tableName) {

        StringBuffer sb = new StringBuffer();
        sb.append("""
                select 
                 node_id "nodeId",
                 node_key "nodeKey",
                 instance_id "instanceId",
                 task_op_id "taskOpId",
                 
                 flow_sort "flowSort" from 
                """);
        sb.append(tableName);
        sb.append(" where op_id = ? ");
        String sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, new Object[] {opId});
    }

    @Override
    public void insertNextFlowInstanceFlowData(TemplateFlowInstanceFlowBean bean, String tableName) {

        StringBuffer sb = new StringBuffer();
        sb.append("insert into " + tableName);
        sb.append("""
                (op_id,         create_time,    instance_id,    task_op_id,     node_id,
                node_key,       org_id,         last_node_id,   last_node_key,  last_op_id,
                flow_sort,    flow_status
                )values (?, to_date(?, 'YYYY-MM-DD hh:mi:ss'), ), ?, ?, ?,
                ?,?,?,?,?,
                ?,?
                )
                """);
        String sql = sb.toString();
        _logger.info(sql);
        List<Object> array = new ArrayList<>();
        //------------------------
        array.add(bean.getOpId());
        array.add(DateUtil.toString(bean.getCreateTime(), DateUtil.YMDHMS));
        array.add(bean.getInstanceId());
        array.add(bean.getTaskOpId());
        array.add(bean.getNodeId());
        //------------------------
        array.add(bean.getNodeKey());
        array.add(bean.getOrgId());
        array.add(bean.getLastNodeId());
        array.add(bean.getLastNodeKey());
        array.add(bean.getLastOpId());
        //-------------------------
        array.add(bean.getFlowSort());
        array.add(bean.getFlowStatus());
        this.getJdbcTemplate().update(sql, array.toArray());
    }

    @Override
    public void insertStartFlowInstanceFlowData(TemplateFlowInstanceFlowBean bean, String tableName) {

        StringBuffer sb = new StringBuffer();
        sb.append("insert into " + tableName);
        sb.append("""
                (op_id,         create_time,    instance_id,    task_op_id,     node_id,
                node_key,       org_id,         dept_id,        
                next_node_id,   next_node_key,  flow_sort,      user_op_id,     operation_time,
                flow_result,    flow_status
                )values (?, to_date(?, 'YYYY-MM-DD hh:mi:ss'), ), ?, ?, ?,
                ?,?,?,
                ?,?,?,?,to_date(?, 'YYYY-MM-DD hh:mi:ss'),
                ?,?
                )
                """);
        String sql = sb.toString();
        _logger.info(sql);
        List<Object> array = new ArrayList<>();
        //------------------------
        array.add(bean.getOpId());
        array.add(DateUtil.toString(bean.getCreateTime(), DateUtil.YMDHMS));
        array.add(bean.getInstanceId());
        array.add(bean.getTaskOpId());
        array.add(bean.getNodeId());
        //------------------------
        array.add(bean.getNodeKey());
        array.add(bean.getOrgId());
        array.add(bean.getDeptId());
        //-------------------------
        array.add(bean.getNextNodeId());
        array.add(bean.getNextNodeKey());
        array.add(bean.getFlowSort());
        array.add(bean.getUserOpId());
        array.add(DateUtil.toString(bean.getOperationTime(), DateUtil.YMDHMS));
        //-------------------------
        array.add(bean.getFlowResult());
        array.add(bean.getFlowStatus());
        this.getJdbcTemplate().update(sql, array.toArray());
    }

    @Override
    public void insertStartFlowInstanceData(TemplateFlowInstanceBean bean, String tableName, StartFlowBO startFlowVO) {

        StringBuffer sb = new StringBuffer();
        sb.append("insert into " + tableName);
        sb.append("""
                (op_id, create_time, flow_status, task_op_id, main_id, org_id, dept_id, create_user_op_id)
                values (?,to_date(?, 'YYYY-MM-DD hh:mi:ss'),?,?,?,?,?,?)""");
        String sql = sb.toString();
        _logger.info(sql);
        List<Object> array = new ArrayList<>();
        array.add(bean.getOpId());
        array.add(DateUtil.toString(bean.getCreateTime(), DateUtil.YMDHMS));
        array.add(bean.getFlowStatus());
        array.add(bean);
        array.add(startFlowVO.getTaskOpId());
        array.add(startFlowVO.getMainId());
        array.add(startFlowVO.getOrgId());
        array.add(startFlowVO.getDeptId());
        array.add(startFlowVO.getCreateUserOpId());
        this.getJdbcTemplate().update(sql, array.toArray());
    }
}
