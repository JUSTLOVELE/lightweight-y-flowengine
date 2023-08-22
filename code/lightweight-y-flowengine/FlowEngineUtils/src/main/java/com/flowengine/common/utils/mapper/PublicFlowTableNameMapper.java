package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicFlowTableNameEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 建模引擎-表模块;
 * @author yangzl 2023.07.19
 * @version 1.00.00
 * @Description:
 * @history:
 */
@Repository
public interface PublicFlowTableNameMapper extends BaseMapper<PublicFlowTableNameEntity> {

    /**
     * 根据opId查询表名
     * @param opId
     * @return
     */
    @Select("select table_name from public_flow_table_name_tbl where op_id = #{opId}")
    public String queryTableNameByOpId(@Param("opId") String opId);

    /**
     * 获取下拉框数据
     * @return
     */
    @Select("select op_id value, '(' ||table_name_desc || ')' || table_name   label from public_flow_table_name_tbl")
    public List<Map<String, Object>> getCombobox();
}
