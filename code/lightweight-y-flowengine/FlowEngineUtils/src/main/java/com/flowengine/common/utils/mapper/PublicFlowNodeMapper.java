package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicFlowNodeEntity;
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
public interface PublicFlowNodeMapper extends BaseMapper<PublicFlowNodeEntity> {

    /**
     *
     * @param mainId
     * @return
     */
    @Select("select * from public_flow_node_tbl where main_id = #{mainId} and node_key = 'start' ")
    public PublicFlowNodeEntity getStartNode(@Param("mainId") String mainId);

    /**
     * 根据流程主表的主键查询其节点
     * @param mainId
     * @return
     */
    String QUERY_NODES_BY_MAINID_SQL = """
            select 
                a.op_id "opId",
                a.node_name "nodeName",
                a.node_key "nodeKey",
                a.limit_time "limitTime",
                a.node_status "nodeStatus",
                (case when a.node_status = '1' then '开始'
                    when a.node_status = '2' then '常规节点'
                    when a.node_status = '10' then '结束'
                else '未知' end ) "nodeStatusText",
                a.next_node "nextNode"
            from public_flow_node_tbl a where a.main_id = #{mainId} order by a.node_sort
            """;
    @Select(QUERY_NODES_BY_MAINID_SQL)
    public List<Map<String, Object>> queryNodesByMainId(@Param("mainId") String mainId);

    /**
     * 删除
     * @param mainId
     * @return
     */
    @Delete("delete from public_flow_node_tbl where main_id = #{mainId}")
    public int deleteByMainOpId(@Param("mainId") String mainId);
}
