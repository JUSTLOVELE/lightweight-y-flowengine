package com.flowengine.server.backend.service.admin.impl;

import com.flowengine.common.utils.CommonConstant;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TokenServiceImpl extends BaseService implements TokenService {

    @Resource
    private PublicUserMapper _publicUserMapper;

    @Resource
    private YmlProjectConfig _ymlProjectConfig;

    private static final Log _logger = LogFactory.getLog(TokenServiceImpl.class);

    @Override
    public void setLoginToken(PublicUserEntity user) {

        if(user == null) {
            throw new RuntimeException("user为空");
        }

        user.setLastLogin(new Date());
        user.setAccessToken(UUIDGenerator.getUUID());
        user.setAccessTokenLimit(_ymlProjectConfig.getAccessTokenLimit());
        user.setRefreshToken(UUIDGenerator.getUUID());
        user.setRefreshTokenLimit(_ymlProjectConfig.getRefreshTokenLimit());
        _publicUserMapper.updateById(user);
    }

    @Override
    public void setLoginToken(String userOpId) {

        PublicUserEntity publicUserEntity = _publicUserMapper.selectById(userOpId);
        this.setLoginToken(publicUserEntity);
    }

    /**
     *
     * @param sign
     * @param user
     * @return
     */
    @Override
    public boolean verifyToken(String sign, PublicUserEntity user) {

        String timestamp = DateUtil.toString(user.getLastLogin(), DateUtil.YMDHMS);
        String newSign = createAndGetSign(timestamp, user.getAccessToken(), user.getRefreshToken());

        return sign.equals(newSign);
    }

    @Override
    public String createAndGetSign(String timestamp, String accessToken, String refreshToken) {

        String content = timestamp + ";" + accessToken + ";" + refreshToken + ";";
        return  RSA.signBySHA256WithRSA(content, _ymlProjectConfig.getSk());
    }

    @Override
    public String reRefreshToken(String accessToken, String sign) {

        PublicUserEntity publicUserEntity = _publicUserMapper.queryByAccessToken(accessToken);

        if(publicUserEntity == null) {
            throw new RuntimeException("根据accessToken查询用户失败!刷新reRefreshToken失败!");
        }

        if(!verifyToken(sign, publicUserEntity)) {
            throw new RuntimeException("验证签名失败!");
        }

        publicUserEntity.setRefreshToken(UUIDGenerator.getUUID());
        _publicUserMapper.updateById(publicUserEntity);
        String timestamp = DateUtil.toString(publicUserEntity.getLastLogin(), DateUtil.YMDHMS);
        String newSign = createAndGetSign(timestamp, publicUserEntity.getAccessToken(), publicUserEntity.getRefreshToken());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(CommonConstant.Token.SIGN, newSign);
        data.put(CommonConstant.Token.REFRESH_TOKEN, publicUserEntity.getRefreshToken());

        return getJSON(data);
    }
}

