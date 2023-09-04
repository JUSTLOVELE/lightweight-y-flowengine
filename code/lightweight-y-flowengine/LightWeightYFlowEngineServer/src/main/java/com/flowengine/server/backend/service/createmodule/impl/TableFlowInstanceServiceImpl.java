package com.flowengine.server.backend.service.createmodule.impl;

import com.flowengine.common.utils.UUIDGenerator;
import com.flowengine.common.utils.entity.createmodel.PublicFlowTableFlowInstanceEntity;
import com.flowengine.common.utils.mapper.createmodel.PublicFlowTableFlowInstanceMapper;
import com.flowengine.server.backend.dao.createmodule.TableFlowInstanceDao;
import com.flowengine.server.backend.dao.createmodule.TableManageDao;
import com.flowengine.server.backend.service.createmodule.TableFlowInstanceService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.utils.Constant;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/23
 * @version 1.00.00
 * @Description:
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TableFlowInstanceServiceImpl extends BaseService implements TableFlowInstanceService {

    @Resource
    private PublicFlowTableFlowInstanceMapper _flowTableFlowInstanceMapper;

    @Resource
    private TableManageDao _tableManageDao;


    @Resource
    private TableFlowInstanceDao _tableFlowInstanceDao;

    @Override
    public void dropTableByTableOpId(String tableOpId) {

        List<Map<String, Object>> tableFlowInstances = _flowTableFlowInstanceMapper.queryByTableOpId(tableOpId);
        List<String> tables = new ArrayList<>();

        for(Map<String, Object> tableFlowInstance: tableFlowInstances) {

            String tableName = (String) tableFlowInstance.get(Constant.Key.TABLE_NAME);
            int count = _tableFlowInstanceDao.queryCountByTableName(tableName);

            if(count > 0) {
                throw new RuntimeException("删除失败!,该流程已产生数据!");
            }

            tables.add(tableName);
        }

        for(String table: tables) {
            _tableManageDao.executeDropSQL(table);
        }


    }

    @Override
    public void createFlowAndFlowInstance(String tableId, String tableName) {

        PublicFlowTableFlowInstanceEntity flowInstanceEntity = new PublicFlowTableFlowInstanceEntity();
        flowInstanceEntity.setOpId(UUIDGenerator.getUUID());
        flowInstanceEntity.setTableOpId(tableId);
        flowInstanceEntity.setTableType(1);
        tableName = tableName.replace("_tbl", ""); //把末尾的tbl去掉
        flowInstanceEntity.setTableName(tableName + "_flow_instance_tbl");
        _flowTableFlowInstanceMapper.insert(flowInstanceEntity);
        PublicFlowTableFlowInstanceEntity flowInstanceFlowEntity = new PublicFlowTableFlowInstanceEntity();
        flowInstanceFlowEntity.setOpId(UUIDGenerator.getUUID());
        flowInstanceFlowEntity.setTableOpId(tableId);
        flowInstanceFlowEntity.setTableType(2);
        flowInstanceFlowEntity.setTableName(tableName + "_flow_instance_flow_tbl");
        _flowTableFlowInstanceMapper.insert(flowInstanceFlowEntity);
        PublicFlowTableFlowInstanceEntity flowCommentEntity = new PublicFlowTableFlowInstanceEntity();
        flowCommentEntity.setOpId(UUIDGenerator.getUUID());
        flowCommentEntity.setTableOpId(tableId);
        flowCommentEntity.setTableType(3);
        flowCommentEntity.setTableName(tableName + "_flow_comment_tbl");
        _flowTableFlowInstanceMapper.insert(flowCommentEntity);
        String createFlowInstanceSQL = getCreateFlowInstanceSQL(flowInstanceEntity.getTableName());
        String createFlowInstanceFlowSQL = getCreateFlowInstanceFlowSQL(flowInstanceFlowEntity.getTableName());
        String createFlowCommentSQL = getCreateFlowCommentSQL(flowCommentEntity.getTableName());
        _tableManageDao.executeCreateSQL(createFlowInstanceSQL);
        _tableManageDao.executeCreateSQL(createFlowInstanceFlowSQL);
        _tableManageDao.executeCreateSQL(createFlowCommentSQL);
    }

    private String getCreateFlowCommentSQL(String tableName) {

        String sql = """
                
               (
                    op_id VARCHAR(32) NOT NULL,
                    instance_id VARCHAR(32),
                    task_op_id VARCHAR(32),
                    create_time TIMESTAMP,
                    node_id VARCHAR(32),
                    flow_comment VARCHAR(900),
                    flow_sort INTEGER,
                    flow_result INTEGER,
                    instance_flow_id VARCHAR(255),
                    PRIMARY KEY (op_id)
                )
                """;

        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE ").append(tableName);
        sb.append(sql);

        return sb.toString();
    }

    public String getCreateFlowInstanceSQL(String tableName) {

        String sql = """
                (
                    op_id VARCHAR(32) NOT NULL,
                    create_time TIMESTAMP,
                    end_time TIMESTAMP,
                    flow_status INTEGER,
                    is_over_time INTEGER,
                    total_time INTEGER,
                    task_op_id VARCHAR(32),
                    user_appraise INTEGER,
                    appraise_content VARCHAR(900),
                    main_id VARCHAR(32),
                    org_id VARCHAR(32),
                    dept_id VARCHAR(32),
                    public_flow_comment VARCHAR(900),
                    public_flow_attachment TEXT,
                    private_flow_attachment TEXT,
                    create_user_op_id VARCHAR(32),
                    PRIMARY KEY (op_id)
                )
                """;
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE ").append(tableName);
        sb.append(sql);

        return sb.toString();
    }


    public String getCreateFlowInstanceFlowSQL(String tableName) {

        String sql = """
                (
                    op_id VARCHAR(32) NOT NULL,
                    instance_id VARCHAR(32),
                    task_op_id VARCHAR(32),
                    create_time TIMESTAMP,
                    node_id VARCHAR(32),
                    node_key VARCHAR(255),
                    org_id VARCHAR(32),
                    dept_id VARCHAR(32),
                    last_node_id VARCHAR(32),
                    last_node_key VARCHAR(255),
                    next_node_id VARCHAR(32),
                    next_node_key VARCHAR(900),
                    operation_time TIMESTAMP,
                    user_op_id VARCHAR(32),
                    last_op_id VARCHAR(32),
                    flow_status INTEGER,
                    flow_result INTEGER,
                    flow_comment VARCHAR(900),
                    header_comment VARCHAR(900),
                    back_comment VARCHAR(900),
                    flow_sort INTEGER,
                    PRIMARY KEY (op_id)
                )
                """;
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE ").append(tableName);
        sb.append(sql);

        return sb.toString();
    }
}
