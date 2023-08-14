package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicSubCfgEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/7
 * @version 1.00.00
 * @Description:
 * @history:
 */
@Repository
public interface PublicSubCfgMapper extends BaseMapper<PublicSubCfgEntity> {

    /**
     * 查询子表
     */
    String QUERY_SUB_SQL = """
            
            select 
                sub_cfg_value value,
                sub_cfg_name name,
                sub_cfg_value_type "subCfgValueType"
            from public_sub_cfg where cfg_id = #{cfgId}
            
            """;
    @Select(QUERY_SUB_SQL)
    public List<Map<String, Object>> querWithCfgId(@Param("cfgId") String cfgId);

    /**
     * 查询nodetype作为下拉框数据
     * @return
     */
    @Select("select sub_cfg_value value, sub_cfg_name label from public_sub_cfg where key_code = #{keyCode}")
    public List<Map<String, Object>> getCombobox(@Param("keyCode") String keyCode);

    /**
     * 查询nodetype作为下拉框数据
     * @return
     */
    @Select("select sub_cfg_value value, sub_cfg_name label from public_sub_cfg where key_code = 'node_type'")
    public List<Map<String, Object>> getNodeTypeCombobox();
}
