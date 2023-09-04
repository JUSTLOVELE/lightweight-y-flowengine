package com.flowengine.server.backend.action.flow;

import com.flowengine.common.utils.UUIDGenerator;
import com.flowengine.common.utils.entity.PublicFlowRoleEntity;
import com.flowengine.common.utils.entity.PublicFlowRoleUserGrantEntity;
import com.flowengine.server.backend.service.flow.FlowRoleService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.SessionUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
@RestController
public class FlowRoleAction extends BaseAction {

    @Resource
    private FlowRoleService _flowRoleService;

    /**
     * 获取role_type下拉框
     * @return
     */
    @GetMapping(value = "/flowRoleAction/getCombobox", produces = "application/json; charset=utf-8")
    public String getCombobox() {
        return _flowRoleService.getCombobox();
    }

    /**
     * 删除
     * @param opId
     * @return
     */
    @PostMapping(value = "/flowRoleAction/delete", produces = "application/json; charset=utf-8")
    public String delete(String opId) {
        return _flowRoleService.delete(opId);
    }

    /**
     * 编辑
     * @param roleName
     * @param roleType
     * @param persons
     * @return
     */
    @PostMapping(value = "/flowRoleAction/edit", produces = "application/json; charset=utf-8")
    public String edit(String opId, String roleName, Integer roleType, @RequestParam("persons[]") String[] persons) {
        return _flowRoleService.edit(opId, roleName, roleType, persons);
    }

    /**
     * 新增
     * @param roleName
     * @param roleType
     * @param persons
     * @return
     */
    @PostMapping(value = "/flowRoleAction/add", produces = "application/json; charset=utf-8")
    public String add(String roleName, Integer roleType, @RequestParam("persons[]") String[] persons) {

        PublicFlowRoleEntity flowRoleEntity = new PublicFlowRoleEntity();
        flowRoleEntity.setOpId(UUIDGenerator.getUUID());
        flowRoleEntity.setRoleName(roleName);
        flowRoleEntity.setRoleType(roleType);
        List<PublicFlowRoleUserGrantEntity> flowRoleUserGrantEntities = null;

        if(persons != null && persons.length > 0) {

            flowRoleUserGrantEntities = new ArrayList<>();

            for(String person: persons) {

                PublicFlowRoleUserGrantEntity grantEntity = new PublicFlowRoleUserGrantEntity();
                grantEntity.setOpId(UUIDGenerator.getUUID());
                grantEntity.setUserOpId(person);
                grantEntity.setFlowRoleId(flowRoleEntity.getOpId());
                flowRoleUserGrantEntities.add(grantEntity);
            }
        }

        return _flowRoleService.add(flowRoleEntity, flowRoleUserGrantEntities);
    }

    /**
     * 获取role_type下拉框
     * @return
     */
    @GetMapping(value = "/flowRoleAction/getRoleTypeCombobox", produces = "application/json; charset=utf-8")
    public String getRoleTypeCombobox() {
        return _flowRoleService.getRoleTypeCombobox();
    }

    /**
     * 查询
     *
     * @return
     */
    @GetMapping(value = "/flowRoleAction/query", produces = "application/json; charset=utf-8")
    public String query(String roleName, Integer roleType, Integer limit, Integer page, HttpServletRequest request) {

        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.LIMIT, limit);
        param.put(Constant.Key.PAGE, page);
        param.put(Constant.Key.ROLE_NAME, roleName);
        param.put(Constant.Key.ROLE_TYPE, roleType);

        return _flowRoleService.query(param);
    }

}
