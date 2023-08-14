package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicCfgEntity;
import org.apache.ibatis.annotations.Param;
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
public interface PublicCfgMapper extends BaseMapper<PublicCfgEntity> {

    @Select("select combobox_desc \"comboboxDesc\" from public_cfg where key_code = #{keyCode} ")
    public List<Map<String, Object>> queryComboboxWithKeyCode(@Param("keyCode") String keyCode);

}
