package com.flowengine.server.env;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
@Configuration
@MapperScan({"com.flowengine.server.mapper", "com.flowengine.common.utils.mapper"})
public class Config {

    private final static Log _logger = LogFactory.getLog(Config.class);

    @Autowired
    private YmlProjectConfig _ymlProjectConfig;

    @Bean
    public PropertySourcesPlaceholderConfigurer properties () {

        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        //这里可以Import很多个
        yaml.setResources(new ClassPathResource("myConfig.yml"));//File引入
        configurer.setProperties(yaml.getObject());
        return configurer;
    }
//
//    @Bean
//    public MinioClient minioClient() {
//
//        try {
//
//            String minioUrl = _ymlProjectConfig.getMiniouploadurl();
//            MinioClient client = new MinioClient(minioUrl, _ymlProjectConfig.getMinioaccesskey(),
//                    _ymlProjectConfig.getMiniosecretkey());
//
//            if (client.bucketExists(_ymlProjectConfig.getMiniobucketname())) {
//                _logger.info("已创建桶");
//            } else {
//                client.makeBucket(_ymlProjectConfig.getMiniobucketname());
//                _logger.info("成功创建桶");
//            }
//
//            // client.setBucketPolicy(_ymlProjectConfig.getMiniobucketname(), policy);
//            return client;
//        } catch (Exception e) {
//            _logger.error("", e);
//            return null;
//        }
//    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 配置分页插件、乐观锁插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //配置分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        //配置乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        return interceptor;
    }
}