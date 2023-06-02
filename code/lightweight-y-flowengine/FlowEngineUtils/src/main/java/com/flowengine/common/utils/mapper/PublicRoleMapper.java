package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicRoleEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2022/08/26
 * @version 1.00.00
 * @Description:
 * @Copyright:
 * @Company:
 * @history:
 */
@Repository
public interface PublicRoleMapper extends BaseMapper<PublicRoleEntity> {

    @Select("select a.op_id value, a.name label from public_role a where a.org_id = #{orgId}")
    public List<Map<String, Object>> getCombox(@Param("orgId") String orgId);
}
