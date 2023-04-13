package com.flowengine.server.backend.service.admin.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flowengine.common.utils.CacheObject;
import com.flowengine.common.utils.CacheService;
import com.flowengine.common.utils.RSA;
import com.flowengine.common.utils.entity.PublicUserEntity;
import com.flowengine.common.utils.mapper.PublicUserMapper;
import com.flowengine.server.backend.service.admin.TokenService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.env.YmlProjectConfig;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.DateUtil;
import com.flowengine.server.utils.SessionUtils;
import com.flowengine.server.utils.UUIDGenerator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class TokenServiceImpl extends BaseService implements TokenService {

    @Resource
    private PublicUserMapper _publicUserMapper;

    @Resource
    private YmlProjectConfig _ymlProjectConfig;

    @Override
    public void getLoginToken(PublicUserEntity user) {

        if(user == null) {
            throw new RuntimeException("user为空");
        }

        user.setLastLogin(new Date());
        user.setAccessToken(UUIDGenerator.getUUID());
        user.setAccessTokenLimit(120);
        user.setRefreshToken(UUIDGenerator.getUUID());
        user.setRefreshTokenLimit(2);
        _publicUserMapper.updateById(user);
    }

    @Override
    public void getLoginToken(String userOpId) {

        PublicUserEntity publicUserEntity = _publicUserMapper.selectById(userOpId);
        this.getLoginToken(publicUserEntity);
    }

    /**
     *
     * @param accessToken
     * @return
     */
    @Override
    public boolean verifyToken(String accessToken) {

//        JSONObject entries = JSONUtil.parseObj(token);
//        String accessToken = entries.getStr(Constant.Token.ACCESS_TOKEN);
//        String sign = entries.getStr(Constant.Token.SIGN);
//        String timestamp = entries.getStr(Constant.Token.TIMESTAMP);
//        String refreshToken = entries.getStr(Constant.Token.REFRESH_TOKEN);
//        String content = timestamp + ";" + accessToken + ";" + refreshToken + ";";
//        boolean verify = RSA.verify(content, _ymlProjectConfig.getPk(), sign);
//
//        return verify;
        return false;
    }

    @Override
    public String reRefreshToken(String token) {
        return null;
    }
}

