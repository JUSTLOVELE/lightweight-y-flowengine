package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicFlowRoleEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023.08.14
 * @version 1.00.00
 * @Description:
 * @history:
 */
@Repository
public interface PublicFlowRoleMapper extends BaseMapper<PublicFlowRoleEntity> {

    /**
     * 获取下拉框
     * @return
     */
    @Select("select role_name label, op_id value from public_flow_role_tbl")
    public List<Map<String, Object>> getCombobox();
}
