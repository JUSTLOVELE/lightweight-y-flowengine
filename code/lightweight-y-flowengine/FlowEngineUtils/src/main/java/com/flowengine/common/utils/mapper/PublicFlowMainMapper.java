package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicFlowMainEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/22
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
@Repository
public interface PublicFlowMainMapper extends BaseMapper<PublicFlowMainEntity> {


    String QUERY_ALL_DATA_SQL = """
            select 
            op_id "opId",
            reference_table_name "referenceTableName",
            reference_table_id "referenceTableId"
            from public_flow_main_tbl
            """;

    @Select(QUERY_ALL_DATA_SQL)
    public List<Map<String, Object>> queryAllData();

    @Select("select 1 as \"c\" from public_flow_main_tbl WHERE reference_table_id = #{refOpId} limit 1")
    public int queryCountByRefOpId(@Param("refOpId") String refOpId);
}
