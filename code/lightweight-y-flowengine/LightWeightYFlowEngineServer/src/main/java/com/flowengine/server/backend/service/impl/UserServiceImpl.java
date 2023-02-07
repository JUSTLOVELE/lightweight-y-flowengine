package com.flowengine.server.backend.service.impl;

import cn.hutool.core.util.StrUtil;

import com.flowengine.common.utils.AccountValidatorUtil;
import com.flowengine.common.utils.CommonConstant;
import com.flowengine.common.utils.EncryptUtil;
import com.flowengine.common.utils.UUIDGenerator;
import com.flowengine.common.utils.entity.PublicUserEntity;
import com.flowengine.common.utils.mapper.PublicUserMapper;
import com.flowengine.server.backend.dao.UserDao;
import com.flowengine.server.backend.service.UserService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.env.YmlProjectConfig;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2022.08.22
 * @version 1.00.00
 * @Description: 案例action
 * @Copyright:
 * @Company:
 * @history:
 */
@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private YmlProjectConfig _ymlProjectConfig;

    @Autowired
    private PublicUserMapper _userMapper;

    @Autowired
    private UserDao _userDao;

    @Override
    public String getUserCombox() {

        List<Map<String, Object>> userCombox = _userMapper.getUserCombox();
        return getJSON(userCombox);
    }

    @Override
    public String editSelf(HttpServletRequest request, Map<String, Object> data) {

        UserCache userCache = (UserCache) data.get(SessionUtils.USER_SESSION);
        PublicUserEntity userEntity = _userMapper.selectById(userCache.getOpId());
        String userName = (String) data.get(Constant.Key.NAME);

        if(StrUtil.isNotEmpty(userName) && !userName.equals(userEntity.getUserName())) {
            userEntity.setUserName(userName);
        }

        String userId = (String) data.get(Constant.Key.USER_ID);

        if(StrUtil.isNotEmpty(userId) && !userId.equals(userEntity.getUserId()) && !_userDao.isExitUserId(userId)) {
            userEntity.setUserId(userId);
        }

        String userPhone = (String) data.get(Constant.Key.USER_PHONE);

        if(StrUtil.isNotEmpty(userPhone) && AccountValidatorUtil.isMobile(userPhone)) {
            userEntity.setUserPhone(userPhone);
        }else {
            return renderPrintFailureList("手机格式错误");
        }

        String oldPwd = (String) data.get(Constant.Key.OLD_PWD);
        String pwd = (String) data.get(Constant.Key.PASSWORD);

        if(StrUtil.isNotEmpty(oldPwd) && StrUtil.isNotEmpty(pwd)) {

            oldPwd = EncryptUtil.get3DESEncrypt(oldPwd, _ymlProjectConfig.getEncryptKey());

            if(!oldPwd.equals(userEntity.getUserPassword())) {
                return renderPrintFailureList("请输入正确的旧密码");
            }

            if(!AccountValidatorUtil.isPassword(pwd)) {
                return renderPrintFailureList("新密码格式请输入数字和英文字母的组合");
            }

            pwd = EncryptUtil.get3DESEncrypt(pwd, _ymlProjectConfig.getEncryptKey());
            userEntity.setUserPassword(pwd);

        }

        _userMapper.updateById(userEntity);
        userCache.reCache(userEntity);
        SessionUtils.removeUserSession(request);
        SessionUtils.addUserSession(request,userCache);

        return renderUpdateSuccessList(0);
    }

    @Override
    public String edit(Map<String, Object> param) {

        String userName = (String) param.get(Constant.Key.NAME);
        String userId = (String) param.get(Constant.Key.USER_ID);
        String userPhone = (String) param.get(Constant.Key.USER_PHONE);
        String password = (String) param.get(Constant.Key.PASSWORD);
        String orgId = (String) param.get(Constant.Key.ORG_ID);
        String sex = (String) param.get(Constant.Key.SEX);
        String opId = (String) param.get(Constant.Key.OP_ID);
        String roleId = (String) param.get(Constant.Key.ROLE_ID);
        String userCategory = (String) param.get(Constant.Key.USER_CATEGORY);
        PublicUserEntity userEntity = _userMapper.selectById(opId);
        userEntity.setUserId(userId);
        userEntity.setUserPhone(userPhone);
        userEntity.setUserName(userName);
        userEntity.setOrgId(orgId);
        userEntity.setUserSex(sex);
        _userMapper.updateById(userEntity);
        userEntity.setUserCategory(Integer.valueOf(userCategory));

        if(StrUtil.isNotEmpty(roleId)) {

            _userMapper.deleteUserRoleGrant(opId);
            _userMapper.insertUserRoleGrant(opId, roleId);
        }

        return renderEditSuccessList(1);
    }

    @Override
    public String add(Map<String, Object> param) {

        String userName = (String) param.get(Constant.Key.NAME);
        String userId = (String) param.get(Constant.Key.USER_ID);
        String userPhone = (String) param.get(Constant.Key.USER_PHONE);
        String password = (String) param.get(Constant.Key.PASSWORD);
        String orgId = (String) param.get(Constant.Key.ORG_ID);
        String sex = (String) param.get(Constant.Key.SEX);
        String roleId = (String) param.get(Constant.Key.ROLE_ID);
        String userCategory = (String) param.get(Constant.Key.USER_CATEGORY);
        PublicUserEntity userEntity = new PublicUserEntity();
        userEntity.setOpId(UUIDGenerator.getUUID());
        userEntity.setCreateTime(new Date());
        userEntity.setUserId(userId);
        userEntity.setUserPhone(userPhone);
        userEntity.setUserName(userName);
        userEntity.setIsStop(1);//启用
        userEntity.setUserPassword(EncryptUtil.get3DESEncrypt(password, _ymlProjectConfig.getEncryptKey()));
        userEntity.setOrgId(orgId);
        userEntity.setUserSex(sex);
        userEntity.setUserCategory(Integer.valueOf(userCategory));
        _userMapper.insert(userEntity);

        if(StrUtil.isNotEmpty(roleId)) {
            _userMapper.insertUserRoleGrant(userEntity.getOpId(), roleId);
        }

        return renderEditSuccessList(1);
    }

    @Override
    public String delete(String opId) {

        _userMapper.deleteUserRoleGrant(opId);
        _userMapper.deleteById(opId);
        return renderSuccessList(1, CommonConstant.SUCCESS_DELETE_MSG);
    }

    @Override
    public String query(Map<String, Object> param) {

        List<Map<String, Object>> users = _userDao.query(param);

        if(users != null && users.size() > 0) {
            return renderQuerySuccessList(_userDao.queryTotal(param), users);
        }else {
            return renderQuerySuccessList(0, users);
        }
    }

    @Override
    public List<Map<String, Object>> queryRoleId(String userOpId) {
        return _userMapper.getRoleIds(userOpId);
    }

    @Override
    public PublicUserEntity getPublicUserEntity(String userId, String password) {

        password = EncryptUtil.get3DESEncrypt(password, _ymlProjectConfig.getEncryptKey());
        Map<String, Object> param = new HashMap<>();
        param.put(Constant.Key.USER_ID, userId);
        param.put(Constant.Key.USER_PASSWORD, password);
        List<PublicUserEntity> publicUserEntities = _userMapper.selectByMap(param);

        if(publicUserEntities != null && publicUserEntities.size() == 1) {
            return  publicUserEntities.get(0);
        }

        return null;
    }
}
