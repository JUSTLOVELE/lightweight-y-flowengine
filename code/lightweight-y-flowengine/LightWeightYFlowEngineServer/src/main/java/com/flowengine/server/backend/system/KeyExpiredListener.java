package com.flowengine.server.backend.system;

import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author yangzl 2023/8/11
 * @version 1.00.00
 * @Description:redis Key过期监听
 * @Copyright: Copyright (c) 2023 THINK WIN BIG DATA All Rights Reserved
 * @Company: 福建星云大数据应用服务有限公司
 * @history:
 */
//@Component
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    @Resource
    private SessionService _sessionService;

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

        String key = message.toString();

        if(key.startsWith("user_token_")) {
            //缓存到期
            key = key.replace("user_token_", "");
            _sessionService.clearCache(key);
        }
    }
}