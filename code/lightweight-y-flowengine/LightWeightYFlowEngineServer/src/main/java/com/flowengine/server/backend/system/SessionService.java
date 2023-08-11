package com.flowengine.server.backend.system;

import cn.hutool.core.util.StrUtil;

import com.flowengine.common.utils.entity.PublicUserEntity;
import com.flowengine.common.utils.mapper.PublicUserMapper;
import com.flowengine.server.env.YmlProjectConfig;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.SessionUtils;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author yangzl 2023/8/11
 * @version 1.00.00
 * @Description: 缓存服务
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
@Component
public class SessionService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private PublicUserMapper _userMapper;

    @Resource
    private YmlProjectConfig _ymlProjectConfig;

    private static final Log _logger = LogFactory.getLog(SessionService.class);

    public UserCache getCache(String accessToken) {

        try {
            UserCache user = SessionUtils.getUserSession(accessToken);

            if(user != null) {
                return user;
            }

            ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
            String redisKey = "user_token_" + accessToken;
            String opId = (String) stringObjectValueOperations.get(redisKey);

            if(StrUtil.isNotEmpty(opId)) {

                PublicUserEntity publicUserEntity = _userMapper.selectById(opId);
                user = new UserCache(publicUserEntity);
                setCache(accessToken, user, publicUserEntity.getAccessToken());

                return user;
            }

        }catch (Exception e) {
            _logger.error("", e);
        }

        return null;
    }

    /**
     * 设置缓存
     * @param accessToken
     * @param user
     */
    public void setCache(String accessToken, UserCache user, String oldToken) {

        SessionUtils.addUserSession(accessToken, user);
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        String redisKey = "user_token_" + accessToken;
        stringObjectValueOperations.set(redisKey, user.getOpId(), _ymlProjectConfig.getAccessTokenLimit(), TimeUnit.MINUTES);

        if(StrUtil.isNotEmpty(oldToken)) {
            redisTemplate.delete("user_token_" + oldToken);
        }
    }

    /**
     * 清理缓存
     * @param accessToken
     */
    public void clearCache(String accessToken) {
        SessionUtils.removeUserSession(accessToken);
    }
}
