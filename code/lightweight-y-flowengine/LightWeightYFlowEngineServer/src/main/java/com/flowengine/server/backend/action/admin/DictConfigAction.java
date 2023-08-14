package com.flowengine.server.backend.action.admin;

import com.flowengine.common.utils.entity.PublicCfgEntity;
import com.flowengine.server.backend.service.admin.DictConfigService;
import com.flowengine.server.core.BaseAction;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.SessionUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangzl 2023/8/14
 * @version 1.00.00
 * @Description:
 * @history:
 */
@RestController
public class DictConfigAction extends BaseAction {

    @Resource
    private DictConfigService _dictConfigService;


    /**
     * 编辑
     * @param cfgEntity
     * @param children
     * @return
     */
    @PostMapping(value = "/dictConfigAction/edit", produces = "application/json; charset=utf-8")
    public String edit(PublicCfgEntity cfgEntity, String children) {
        return _dictConfigService.edit(cfgEntity, children);
    }

    /**
     * 子表查询
     *
     * @return
     */
    @GetMapping(value = "/dictConfigAction/querySub", produces = "application/json; charset=utf-8")
    public String querySub(String opId) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put(Constant.Key.OP_ID, opId);
        return _dictConfigService.querySub(param);
    }

    /**
     * 删除
     * @return
     */
    @PostMapping(value = "/dictConfigAction/delete", produces = "application/json; charset=utf-8")
    public String delete(String opId) {
        return _dictConfigService.delete(opId);
    }

    /**
     * 查询
     *
     * @return
     */
    @GetMapping(value = "/dictConfigAction/query", produces = "application/json; charset=utf-8")
    public String query(String cfgName, String keyCode, Integer limit, Integer page, HttpServletRequest request) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put(Constant.Key.PAGE, page);
        param.put(Constant.Key.LIMIT, limit);
        param.put(Constant.Key.CFG_NAME, cfgName);
        param.put(Constant.Key.KEY_CODE, keyCode);

        return _dictConfigService.query(param);
    }

    /**
     * 新增
     * @param cfgEntity
     * @param children
     * @return
     */
    @PostMapping(value = "/dictConfigAction/add", produces = "application/json; charset=utf-8")
    public String add(PublicCfgEntity cfgEntity, String children) {
        return _dictConfigService.add(cfgEntity, children);
    }
}
