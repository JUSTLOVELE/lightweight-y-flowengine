package com.flowengine.server.backend.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.flowengine.common.utils.mapper.PublicUserMapper;
import com.flowengine.server.backend.dao.UserDao;
import com.flowengine.server.core.BaseDao;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.SessionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2022.08.24
 * @version 1.00.00
 * @Description:
 * @history:
 */
@Repository
public class UserDaoImpl extends BaseDao implements UserDao {

    private final static Log _logger = LogFactory.getLog(UserDaoImpl.class);

    @Autowired
    private PublicUserMapper _publicUserMapper;

    @Override
    public boolean isExitUserId(String userId) {
        return false;
    }

    @Override
    public void bandingRole(String userOpId, String roleOpId) {

    }

    @Override
    public void deleteUser(String opId) {

    }

    @Override
    public Integer queryTotal(Map<String, Object> param) {

        String name = (String) param.get(Constant.Key.NAME);
        UserCache user = (UserCache) param.get(SessionUtils.USER_SESSION);
        List<Object> array = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COUNT(*) FROM public_user_tbl a WHERE 1=1 ");
        if(StrUtil.isNotEmpty(name)) {
            sb.append(" and user_name like ? ");
            array.add("%" + name + "%");
        }

        String sql = sb.toString();
        _logger.info(sql);
        return this.getJdbcTemplate().queryForObject(sql, Integer.class, array.toArray());
    }

    @Override
    public List<Map<String, Object>> query(Map<String, Object> param) {

        String name = (String) param.get(Constant.Key.NAME);
        Integer page = (Integer) param.get(Constant.Key.PAGE);
        Integer limit = (Integer) param.get(Constant.Key.LIMIT);
        UserCache user = (UserCache) param.get(SessionUtils.USER_SESSION);
        List<Object> array = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT a.op_id \"opId\", a.user_phone \"userPhone\",");
        sb.append("a.user_name \"userName\",");
        sb.append("a.user_id \"userId\",");
        sb.append(" a.user_sex sex, cast(a.user_category as VARCHAR) \"userCategory\",");
        sb.append("a.org_id \"orgId\",");
        sb.append("(SELECT b.role_id FROM public_user_role_grant b WHERE b.user_op_id = a.op_id LIMIT 1) \"roleId\",");
        sb.append("(select b.org_name from public_org b where b.op_id = a.org_id) \"orgName\",");
        sb.append("to_char(a.create_time, 'YYYY-MM-DD') as \"createTime\" ");
        sb.append(" FROM public_user_tbl a WHERE 1=1 ");

        if(StrUtil.isNotEmpty(name)) {
            sb.append("and user_name like ? ");
            array.add("%" + name + "%");
        }

        sb.append("ORDER BY a.create_time DESC LIMIT ? offset ? ");
        array.add(limit);
        array.add(limit*(page-1));
        String sql = sb.toString();
        _logger.info(sql);

        return this.getJdbcTemplate().queryForList(sql, array.toArray());
    }

    @Override
    public List<Map<String, Object>> queryRoleId(String userOpId) {
        return null;
    }

    @Override
    public List<Map<String, Object>> queryCommonUser(Map<String, Object> param) {
        return null;
    }

    @Override
    public int queryCommonUserTotal(Map<String, Object> param) {
        return 0;
    }
}
