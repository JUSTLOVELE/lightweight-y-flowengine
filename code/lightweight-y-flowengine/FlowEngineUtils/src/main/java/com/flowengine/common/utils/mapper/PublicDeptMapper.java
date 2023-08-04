package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicDeptEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author yangzl 2023.03.23
 * @version 1.00.00
 * @Description:
 * @Copyright:
 * @Company:
 * @history:
 */
@Repository
public interface PublicDeptMapper extends BaseMapper<PublicDeptEntity> {

    /**
     * 获取下拉框数据
     * @return
     */
    @Select("select op_id value, dept_name label from public_dept")
    public List<Map<String, Object>> getCombobox();
}
