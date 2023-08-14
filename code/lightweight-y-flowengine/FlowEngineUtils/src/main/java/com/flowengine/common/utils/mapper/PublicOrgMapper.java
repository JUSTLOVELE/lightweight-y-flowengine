package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicOrgEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @history:
 */
@Repository
public interface PublicOrgMapper extends BaseMapper<PublicOrgEntity> {

    @Select("select op_id value, org_name label from public_org")
    public List<Map<String, Object>> queryByCombobox();
}
