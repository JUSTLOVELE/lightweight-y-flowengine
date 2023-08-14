package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicFlowRoleUserGrantEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangzl 2023.08.14
 * @version 1.00.00
 * @Description:
 * @history:
 */
@Repository
public interface PublicFlowRoleUserGrantMapper extends BaseMapper<PublicFlowRoleUserGrantEntity> {

    @Select("select user_op_id from public_flow_role_user_grant where flow_role_id = #{flowRoleId}")
    public List<String> queryWithFlowRoleId(@Param("flowRoleId") String flowRoleId);
}
