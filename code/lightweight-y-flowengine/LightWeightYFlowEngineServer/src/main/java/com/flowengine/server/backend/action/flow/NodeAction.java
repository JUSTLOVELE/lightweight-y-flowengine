package com.flowengine.server.backend.action.flow;

import com.flowengine.server.backend.action.admin.UserAction;
import com.flowengine.server.backend.service.flow.NodeService;
import com.flowengine.server.core.BaseAction;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzl 2023/8/7
 * @version 1.00.00
 * @Description:
 * @history:
 */
@RestController
public class NodeAction extends BaseAction {

    @Resource
    private NodeService _nodeService;

    @Resource
    private UserAction _userAction;

    @Resource
    private DeptAction _deptAction;

    @Resource
    private FlowRoleAction _flowRoleAction;

    /**
     * 获取审批类型下拉框
     * @return
     */
    @GetMapping(value = "/nodeAction/getCheckCombobox")
    public String getCheckCombobox(String value) {

        switch (value) {
            case "1":
                return _userAction.getUserCombox();
            case "2":
                return _deptAction.getCombobox();
            case "10":
                return _flowRoleAction.getCombobox();
        }

        return "[]";
    }



    /**
     * 获取审批类型下拉框
     * @return
     */
    @GetMapping(value = "/nodeAction/getCheckTypeCombobox")
    public String getCheckTypeCombobox() {
        return _nodeService.getCheckTypeCombobox();
    }

    /**
     * 获取环节状态下拉框
     * @return
     */
    @GetMapping(value = "/nodeAction/getNodeStatusCombobox")
    public String getNodeStatusCombobox() {
        return _nodeService.getNodeStatusCombobox();
    }

    /**
     * 获取环节类型下拉框
     * @return
     */
    @GetMapping(value = "/nodeAction/getNodeTypeCombobox")
    public String getNodeTypeCombobox() {
        return _nodeService.getNodeTypeCombobox();
    }
}
