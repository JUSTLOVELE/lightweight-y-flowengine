package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicSubCfgEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/7
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
@Repository
public interface PublicSubCfgMapper extends BaseMapper<PublicSubCfgEntity> {

    /**
     * 查询nodetype作为下拉框数据
     * @return
     */
    @Select("select sub_cfg_value value, sub_cfg_name label from public_sub_cfg where key_code = 'node_type'")
    public List<Map<String, Object>> getNodeTypeCombobox();
}
