package com.flowengine.server.backend.service.admin;

import com.flowengine.common.utils.entity.PublicCfgEntity;

import java.util.Map;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @history:
 */
public interface DictConfigService {

    /**
     * 新增
     * @param cfgEntity
     * @param children
     * @return
     */
    public String edit(PublicCfgEntity cfgEntity, String children);

    /**
     * 查询
     * @param param
     * @return
     */
    public String querySub(Map<String, Object> param);

    /**
     * 删除
     * @param opId
     * @return
     */
    public String delete(String opId);

    /**
     * 查询
     * @param param
     * @return
     */
    public String query(Map<String, Object> param);

    /**
     * 新增
     * @param cfgEntity
     * @param children
     * @return
     */
    public String add(PublicCfgEntity cfgEntity, String children);
}
