package com.flowengine.server.env;

import com.flowengine.server.backend.service.flow.FlowInitService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Resource
    private FlowInitService _flowInitService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        _flowInitService.initFlowMainDatas();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Resource
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        //spring-data-redis的RedisTemplate<K, V>模板类在操作redis时默认使用JdkSerializationRedisSerializer来进行序列化(key会乱码)
        //手动指定键序列化类型使用stringRedisSerializer
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }
}
