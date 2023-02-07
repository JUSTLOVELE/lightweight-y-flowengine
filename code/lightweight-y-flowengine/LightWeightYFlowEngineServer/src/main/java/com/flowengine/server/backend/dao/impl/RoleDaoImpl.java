package com.flowengine.server.backend.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.flowengine.common.utils.UUIDGenerator;
import com.flowengine.common.utils.entity.PublicRoleEntity;
import com.flowengine.common.utils.mapper.PublicRoleMapper;
import com.flowengine.server.backend.dao.RoleDao;
import com.flowengine.server.core.BaseDao;
import com.flowengine.server.model.RoleTreeVO;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2022.08.26
 * @version 1.00.00
 * @history:
 */
@Repository
public class RoleDaoImpl extends BaseDao implements RoleDao {

    private final static Log _logger = LogFactory.getLog(RoleDaoImpl.class);

    @Autowired
    private PublicRoleMapper _publicRoleMapper;

    @Override
    public void addRole(Map<String, Object> param) {

        String orgId = (String) param.get(Constant.Key.ORG_ID);
        String name = (String) param.get(Constant.Key.NAME);
        String opId = (String) param.get(Constant.Key.OP_ID);
        PublicRoleEntity role = new PublicRoleEntity();
        role.setName(name);
        role.setCreateTime(new Date());
        role.setOrgId(orgId);
        role.setCategory(0);

        if(StrUtil.isNotEmpty(opId)) {
            role.setOpId(opId);
        }else {
            role.setOpId(UUIDGenerator.getUUID());
        }

        _publicRoleMapper.insert(role);
    }

    @Override
    public void delete(String opId) {

        String sql = "DELETE FROM public_role_menu_grant WHERE role_id = ?";
        Object[] obj = new Object[] {opId};
        _logger.info(sql);
        this.getJdbcTemplate().update(sql, obj);
        sql = "DELETE FROM public_user_role_grant WHERE role_id = ?";
        this.getJdbcTemplate().update(sql, obj);
        sql = "DELETE FROM public_role WHERE op_id = ?";
        this.getJdbcTemplate().update(sql, obj);
    }

    @Override
    public List<Map<String, Object>> findRoleIdByUserId(String userId) {
        String sql = "select a.role_id \"roleId\" from public_user_role_grant a where a.user_op_id = ?";
        _logger.info(sql);
        return this.getJdbcTemplate().queryForList(sql, new Object[]{userId});
    }

    @Override
    public List<RoleTreeVO> findMenusByRoles(String roleIds) {

        String sql = "select op_id id, parent_id \"parentId\", text, type from public_menu where op_id in ( ";
        sql += "select menu_id from public_role_menu_grant where role_id in ("+roleIds+")) ORDER BY sort ASC ";
        _logger.info(sql);
        RowMapper<RoleTreeVO> rowMapper = new BeanPropertyRowMapper<RoleTreeVO>(RoleTreeVO.class);
        List<RoleTreeVO> roleTreeVOS = this.getJdbcTemplate().query(sql, rowMapper);

        return roleTreeVOS;
    }

    @Override
    public List<Map<String, Object>> findMenuIdByRoleId(String roleId) {

        String sql = "SELECT a.menu_id \"menuId\" FROM public_role_menu_grant a WHERE a.role_id = ?";
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, new Object[] {roleId});
    }

    @Override
    public void editRoleMenus(String opId, String[] menuIds) {

        String sql = "DELETE FROM public_role_menu_grant WHERE role_id = ?";
        Object[] obj = new Object[] {opId};
        _logger.info(sql);
        this.getJdbcTemplate().update(sql, obj);
        addRoleMenus(opId, menuIds);
    }

    @Override
    public void addRoleMenus(String opId, String[] menuIds) {

        if(StrUtil.isEmpty(opId)) {
            return ;
        }

        if(menuIds == null || menuIds.length == 0) {
            return;
        }

        String sql = " INSERT INTO public_role_menu_grant (menu_id, role_id) VALUES(?,?)";
        List<Object[]> batchArgs = new ArrayList<Object[]>();

        for(String menuId : menuIds) {

            Object[] o = new Object[] {menuId, opId};
            batchArgs.add(o);
        }

        this.getJdbcTemplate().batchUpdate(sql, batchArgs);
    }

    @Override
    public Integer queryTotal(String name, UserCache userCache) {

        List<Object> array = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT count(*) FROM public_role a WHERE org_id = ? ");
        array.add(userCache.getOrgId());

        if(StrUtil.isNotEmpty(name)) {
            sb.append(" and a.name like  ? ");
            array.add("%" + name + "%");
        }

        String sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForObject(sql, Integer.class, array.toArray());
    }

    @Override
    public List<Map<String, Object>> query(String name, Integer limit, Integer page, UserCache userCache) {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("a.op_id \"opId\", a.org_id \"orgId\",");
        sb.append("(select b.org_name from public_org b where b.op_id = a.org_id)\"orgName\" ,");
        sb.append("a.name ,");
        sb.append("to_char(a.create_time, 'YYYY-MM-DD hh24:mi:ss') as \"createTime\" ");
        sb.append(" FROM public_role a WHERE org_id = ? "); //仅查询普通用户角色
        List<Object> array = new ArrayList<>();
        array.add(userCache.getOrgId());

        if(StrUtil.isNotEmpty(name)) {
            sb.append(" and a.name like ? ");
            array.add("%" + name + "%");
        }

        sb.append(" ORDER BY a.create_time desc LIMIT ? offset ? ");
        array.add(limit);
        array.add(limit*(page-1));

        String sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, array.toArray());
    }
}
