package com.flowengine.server.backend.service.admin.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flowengine.common.utils.RSA;
import com.flowengine.common.utils.entity.PublicUserEntity;
import com.flowengine.common.utils.mapper.PublicUserMapper;
import com.flowengine.server.backend.service.admin.TokenService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.env.YmlProjectConfig;
import com.flowengine.server.utils.Constant;
import com.flowengine.server.utils.DateUtil;
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
    public String getLoginToken(PublicUserEntity user) {

        if(user == null) {
            return renderFailureList("用户不能为空");
        }

        user.setLastLogin(new Date());
        String accessToken = UUIDGenerator.getUUID();
        String refreshToken = UUIDGenerator.getUUID();
        String signToken = UUIDGenerator.getUUID();
        String timestamp = DateUtil.toString(user.getLastLogin(), DateUtil.YMDHMS);
        user.setAccessToken(accessToken);
        user.setAccessTokenLimit(120);
        user.setRefreshToken(refreshToken);
        user.setRefreshTokenLimit(2);
        user.setSignToken(signToken);
        String content = timestamp + ";" + accessToken + ";" + refreshToken + ";" + signToken;
        Map<String, Object> webToken = new HashMap<>();
        webToken.put(Constant.Token.SIGN, RSA.signBySHA256WithRSA(content, _ymlProjectConfig.getSk()));
        webToken.put(Constant.Token.TIMESTAMP, timestamp);
        webToken.put(Constant.Token.ACCESS_TOKEN, accessToken);
        webToken.put(Constant.Token.REFRESH_TOKEN, refreshToken);
        List<Map<String, Object>> datas = new ArrayList<>();
        datas.add(webToken);
        _publicUserMapper.updateById(user);

        return renderQuerySuccessList(1, datas);
    }

    @Override
    public String getLoginToken(String userOpId) {

        PublicUserEntity publicUserEntity = _publicUserMapper.selectById(userOpId);
        return (publicUserEntity != null) ? this.getLoginToken(publicUserEntity) : renderFailureList("查询不到用户");
    }

    /**
     *
     * @param token : param structure:
     *      * {
     *      * 	"sign":"",
     *      *   "timestamp":"",
     *      *   "access_token":"",
     *      *   "refresh_token":""
     *      * }
     * @return
     */
    @Override
    public boolean verifyToken(String token) {

        JSONObject entries = JSONUtil.parseObj(token);
        String accessToken = entries.getStr(Constant.Token.ACCESS_TOKEN);
        String signToken = _publicUserMapper.getSignTokenByAccessToken(accessToken);
        String sign = entries.getStr(Constant.Token.SIGN);
        String timestamp = entries.getStr(Constant.Token.TIMESTAMP);
        String refreshToken = entries.getStr(Constant.Token.REFRESH_TOKEN);
        String content = timestamp + ";" + accessToken + ";" + refreshToken + ";" + signToken;
        boolean verify = RSA.verify(content, _ymlProjectConfig.getPk(), sign);
        return verify;
    }

    @Override
    public String reAccessToken(String refreshToken) {
        return null;
    }
}

