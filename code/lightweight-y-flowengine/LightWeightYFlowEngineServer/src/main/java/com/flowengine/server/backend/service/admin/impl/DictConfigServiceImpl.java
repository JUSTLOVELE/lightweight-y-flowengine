package com.flowengine.server.backend.service.admin.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.XML;
import com.flowengine.common.utils.entity.PublicCfgEntity;
import com.flowengine.common.utils.entity.PublicSubCfgEntity;
import com.flowengine.common.utils.mapper.PublicCfgMapper;
import com.flowengine.common.utils.mapper.PublicSubCfgMapper;
import com.flowengine.server.backend.dao.admin.DictConfigDao;
import com.flowengine.server.backend.service.admin.DictConfigService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.UUIDGenerator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description: 字典服务
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DictConfigServiceImpl extends BaseService implements DictConfigService {

    @Resource
    private PublicCfgMapper _cfgMapper;

    @Resource
    private PublicSubCfgMapper _subCfgMapper;

    @Resource
    private DictConfigDao _dictConfigDao;

    @Override
    public String edit(PublicCfgEntity entity, String children) {

        List<PublicSubCfgEntity> subCfgEntities = getSubCfg(entity, children);
        _cfgMapper.updateById(entity);
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.Column.CFG_ID, entity.getOpId());
        _subCfgMapper.deleteByMap(map);

        if(subCfgEntities != null && !subCfgEntities.isEmpty()) {

            for(PublicSubCfgEntity subCfgEntity: subCfgEntities) {
                _subCfgMapper.insert(subCfgEntity);
            }
        }

        return renderOpSuccessList(1);
    }

    @Override
    public String querySub(Map<String, Object> param) {

        String cfgId = (String) param.get(Constant.Key.OP_ID);
        List<Map<String, Object>> maps = _subCfgMapper.querWithCfgId(cfgId);
        return renderQuerySuccessList(maps.size(), maps);
    }

    @Override
    public String delete(String opId) {

        _cfgMapper.deleteById(opId);
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.Column.CFG_ID, opId);
        _subCfgMapper.deleteByMap(map);

        return renderOpSuccessList(1);
    }

    @Override
    public String query(Map<String, Object> param) {

        List<Map<String, Object>> datas = _dictConfigDao.query(param);

        if(datas != null && datas.size() > 0) {

            return renderQuerySuccessList(_dictConfigDao.queryTotal(param), datas);
        }else  {
            return renderQuerySuccessList(0);
        }
    }

    private List<PublicSubCfgEntity> getSubCfg(PublicCfgEntity cfgEntity, String children) {

        List<PublicSubCfgEntity> subCfgEntities = null;

        if(StrUtil.isNotEmpty(children)) {

            JSONArray childrens = JSONUtil.parseArray(children);
            subCfgEntities = new ArrayList<>();
            Map<Object, Object> jsonMap = new HashMap<>();
            List<Map<String, Object>> comboboxList = new ArrayList<>();

            for(int i=0; i<childrens.size(); i++) {

                JSONObject jsonObject = childrens.getJSONObject(i);
                Integer subCfgValueType = jsonObject.getInt(Constant.Key.SUB_CFG_VALUE_TYPE);
                String value = jsonObject.getStr(Constant.Key.VALUE);
                String name = jsonObject.getStr(Constant.Key.NAME);
                PublicSubCfgEntity subCfgEntity = new PublicSubCfgEntity();
                subCfgEntity.setOpId(UUIDGenerator.getUUID());
                subCfgEntity.setCfgId(cfgEntity.getOpId());
                subCfgEntity.setKeyCode(cfgEntity.getKeyCode());
                subCfgEntity.setSubCfgValueType(subCfgValueType);
                subCfgEntity.setSubCfgValue(value);
                subCfgEntity.setSubCfgName(name);
                subCfgEntities.add(subCfgEntity);
                Map<String, Object> comboboxMap = new HashMap<>();

                if(subCfgValueType == 2) {
                    jsonMap.put(Integer.valueOf(value), name);
                    comboboxMap.put(Constant.Key.VALUE, Integer.valueOf(value));
                }else {

                    comboboxMap.put(Constant.Key.VALUE, value);
                    jsonMap.put(value, name);
                }

                comboboxMap.put(Constant.Key.LABEL, name);
                comboboxList.add(comboboxMap);
            }

            cfgEntity.setJsonDesc(getJSON(jsonMap));
            cfgEntity.setComboboxDesc(getJSON(comboboxList));
        }

        return subCfgEntities;
    }

    @Override
    public String add(PublicCfgEntity cfgEntity, String children) {

        List<PublicSubCfgEntity> subCfgEntities = getSubCfg(cfgEntity, children);
        _cfgMapper.insert(cfgEntity);

        if(subCfgEntities != null && !subCfgEntities.isEmpty()) {

            for(PublicSubCfgEntity subCfgEntity: subCfgEntities) {
                _subCfgMapper.insert(subCfgEntity);
            }
        }

        return renderOpSuccessList(1);
    }
}
