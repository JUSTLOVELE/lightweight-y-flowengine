package com.flowengine.common.utils.mapper.createmodel;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.createmodel.PublicFlowTableFlowInstanceEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/23
 * @version 1.00.00
 * @Description:
 * @history:
 */
@Repository
public interface PublicFlowTableFlowInstanceMapper extends BaseMapper<PublicFlowTableFlowInstanceEntity> {




    /**
     * 根据tableOpId删除数据
     * @param tableOpId
     * @return
     */
    @Delete("delete from public_flow_table_flow_instance_tbl where table_op_id = #{tableOpId} ")
    public int deleteByTableOpId(@Param("tableOpId") String tableOpId);

    /**
     * 业务关联的流程表名查询
     */
    String QUERY_BY_TABLEOPID_SQL = """
            select 
            table_name "tableName",
            table_type "tableType",
            to_char(create_time, 'YYYY-MM-DD') "createTime",
            (case when table_type = 1 then '流程实例'
            when table_type = 2 then '流程流转'
            else '未知' end )"tableTypeText"
            from public_flow_table_flow_instance_tbl where table_op_id = #{tableOpId}
            """;

    @Select(QUERY_BY_TABLEOPID_SQL)
    public List<Map<String, Object>> queryByTableOpId(@Param("tableOpId") String tableOpId);
}
