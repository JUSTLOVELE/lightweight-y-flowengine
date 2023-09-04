package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicFlowNodeCheckEntity;
import org.apache.ibatis.annotations.Delete;
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
public interface PublicFlowNodeCheckMapper extends BaseMapper<PublicFlowNodeCheckEntity> {



    /**
     * 根据流程主表的主键查询其节点
     * @param mainId
     * @return
     */
    String QUERY_NODES_BY_MAINID_SQL = """
            select 
                a.ref_id "person",
                a.ref_id "refId",
                a.node_type "nodeType",
                a.check_type "checkType",
                (case when a.check_type = '1' then '人员'
                    when a.check_type = '2' then '部门'
                    when a.check_type = '10' then '角色'
                    when a.check_type = '20' then '所有人'
                else '未知' end ) "checkTypeLabel",
                (case when a.node_type = '1' then '非会签'
                    when a.node_type = '2' then '会签'
                    when a.node_type = '3' then '依次逐个处理'
                else '未知' end ) "nodeTypeLabel",
                a.node_sort sort
            from public_flow_node_check_tbl a where a.node_op_id = #{nodeOpId} order by a.node_sort
            """;
    @Select(QUERY_NODES_BY_MAINID_SQL)
    public List<Map<String, Object>> queryNodeChecksByNodeId(@Param("nodeOpId") String nodeOpId);

    /**
     * 删除
     * @param mainId
     * @return
     */
    @Delete("delete from public_flow_node_check_tbl where main_id = #{mainId}")
    public int deleteByMainOpId(@Param("mainId") String mainId);
}
