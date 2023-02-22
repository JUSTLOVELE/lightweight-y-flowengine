package com.flowengine.server.backend.service.admin.impl;

import cn.hutool.core.util.StrUtil;
import com.flowengine.common.utils.CommonConstant;
import com.flowengine.common.utils.UUIDGenerator;
import com.flowengine.common.utils.entity.PublicRoleEntity;
import com.flowengine.common.utils.mapper.PublicRoleMapper;
import com.flowengine.server.backend.dao.admin.RoleDao;
import com.flowengine.server.backend.service.admin.RoleService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.model.RoleTreeVO;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2022.08.26
 * @version 1.00.00
 * @history:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseService implements RoleService {

    private final static Log _logger = LogFactory.getLog(RoleServiceImpl.class);

    @Autowired
    private PublicRoleMapper _publicRoleMapper;

    @Autowired
    private RoleDao _roleDao;

    @Override
    public String edit(Map<String, Object> param) {

        String orgId = (String) param.get(Constant.Key.ORG_ID);
        String name = (String) param.get(Constant.Key.NAME);
        String menuIds = (String) param.get(Constant.Key.MENU_ID);
        String opId = (String) param.get(Constant.Key.OP_ID);

        if(StrUtil.isEmpty(opId)) {
            return renderFailureList("opId不能为空");
        }

        if(StrUtil.isEmpty(name)) {
            return renderFailureList("name不能为空");
        }

        PublicRoleEntity roleEntity = _publicRoleMapper.selectById(opId);

        if(roleEntity == null) {
            return renderFailureList("根据角色Id查询不到角色");
        }

        roleEntity.setName(name);

        if(StrUtil.isNotEmpty(orgId)) {
            roleEntity.setOrgId(orgId);
        }

        try {

            _publicRoleMapper.updateById(roleEntity);

            if(StrUtil.isNotEmpty(menuIds)) {

                menuIds += "21";//固定加上顶级id,21
                String[] array = menuIds.split(",");
                _roleDao.editRoleMenus(opId, array);
            }

            return renderSuccessList(0, CommonConstant.SUCCESS_EDIT_MSG);
        } catch (Exception e) {

            _logger.error("", e);
            return renderFailureList(CommonConstant.FAILURE_EDIT_MSG);
        }
    }

    @Override
    public String delete(String opId) {
        try {
            _roleDao.delete(opId);
            return renderSuccessList(0, CommonConstant.SUCCESS_DELETE_MSG);
        } catch (Exception e) {
            _logger.error("", e);
            return renderFailureList(CommonConstant.FAILURE_DELETE_MSG);
        }
    }

    @Override
    public String add(Map<String, Object> param) {

        String name = (String) param.get(Constant.Key.NAME);
        String menuIds = (String) param.get(Constant.Key.MENU_ID);

        if(StrUtil.isEmpty(name)) {
            return renderFailureList("角色名称为必填项");
        }

        try {

            String roleId = UUIDGenerator.getUUID();
            param.put(Constant.Key.OP_ID, roleId);
            _roleDao.addRole(param);

            if(StrUtil.isNotEmpty(menuIds)) {

                menuIds += "21"; //新增顶级id,21
                String [] array = menuIds.split(",");
                _roleDao.addRoleMenus(roleId, array);
            }

            return renderSuccessList(0, CommonConstant.SUCCESS_SAVE_MSG);
        } catch (Exception e) {
            _logger.error("", e);
            return renderFailureList(CommonConstant.FAILURE_MSG);
        }
    }

    @Override
    public String query(String name, Integer limit, Integer page, UserCache userCache) {

        if(limit == null) {
            return renderFailureList("limit为空");
        }

        if(page == null) {
            return renderFailureList("page为空");
        }

        List<Map<String, Object>> roles = _roleDao.query(name, limit, page, userCache);

        if(roles != null && roles.size() > 0) {
            return renderQuerySuccessList(_roleDao.queryTotal(name, userCache), roles);
        }else {
            return renderQuerySuccessList(0, roles);
        }
    }

    @Override
    public RoleTreeVO createMyRoleTree(String opId, List<String> arrays) {

        List<Map<String, Object>> roles = findRoleIdByUserId(opId);
        String roleIds = "";

        for(int i=0; i<roles.size(); i++){

            roleIds += "'"+(String) roles.get(i).get(Constant.Key.ROLE_ID)+"',";
        }

        List<RoleTreeVO> list = _roleDao.findMenusByRoles(roleIds.substring(0,roleIds.length()-1));

        if(arrays!=null && arrays.size()>0){//回填已有菜单

            for(int i=0; i<list.size(); i++){

                RoleTreeVO rt = list.get(i);

                if(arrays.indexOf(rt.getId())!=-1){

                    rt.setChecked(true);
                }
            }
        }

        RoleTreeVO root = new RoleTreeVO();

        for(int i=0; i<list.size();i++){

            RoleTreeVO rt = list.get(i);

            if(rt.getType()==0){

                rt.setExpanded(true);
                makeUpTree(rt,list);
                root=rt;
                break;
            }
        }

        list.clear();
        list=null;
//		sortTree(root);
        return root;
    }

    private void makeUpTree(RoleTreeVO rt, List<RoleTreeVO> list) {

        for(int i=0; i<list.size(); i++){

            RoleTreeVO r = list.get(i);

            if(rt.getId().equals(r.getParentId())){

                rt.getChildren().add(r);

                if(rt.getType() != 2) {
                    rt.setLeaf(false);
                }

                makeUpTree(r, list);
            }
        }
    }

    /**
     * 根据用户id查找角色id
     */
    public List<Map<String, Object>> findRoleIdByUserId(String userId){
        return _roleDao.findRoleIdByUserId(userId);
    }

    @Override
    public List<String> findMenuIdByRoleId(String roleId) {

        List<Map<String, Object>> menus = _roleDao.findMenuIdByRoleId(roleId);

        if(menus != null && menus.size() > 0){

            List<String> list = new ArrayList<String>();

            for(Map<String, Object> menu : menus) {
                list.add(menu.get(Constant.Key.MENU_ID).toString());
            }

            return list;
        }

        return null;
    }

    @Override
    public String getCombox(String orgId) {

        List<Map<String, Object>> datas = _publicRoleMapper.getCombox(orgId);
        return getJSON(datas);
    }
}
