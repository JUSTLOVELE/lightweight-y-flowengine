package com.flowengine.common.utils.mapper.createmodel;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.createmodel.PublicFlowTableModuleEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author yangzl 2023.07.04
 * @version 1.00.00
 * @Description:
 * @Copyright:
 * @Company:
 * @history:
 */
public interface PublicFlowTableModuleMapper extends BaseMapper<PublicFlowTableModuleEntity> {

    /**
     * 根据主键查询模块名
     * @param opId
     * @return
     */
    @Select("select a.module_name from public_flow_table_module_tbl a where a.op_id = #{opId}")
    public String queryModuleNameById(@Param("opId") String opId);
}
