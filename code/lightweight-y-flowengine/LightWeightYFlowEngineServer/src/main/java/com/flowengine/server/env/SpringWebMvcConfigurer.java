package com.flowengine.server.env;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;


/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
@Configuration
//@EnableWebMvc
public class SpringWebMvcConfigurer implements WebMvcConfigurer {

//	@Bean
//	public DeviceResolverHandlerInterceptor
//	        deviceResolverHandlerInterceptor() {
//	    return new DeviceResolverHandlerInterceptor();
//	}
//
//	@Bean
//	public DeviceHandlerMethodArgumentResolver
//	        deviceHandlerMethodArgumentResolver() {
//	    return new DeviceHandlerMethodArgumentResolver();
//	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new ProcessorInterceptors());
		//引入spring-mobile
		//registry.addInterceptor(deviceResolverHandlerInterceptor());
		//registry.addInterceptor(new ProcessorInterceptors()).addPathPatterns("/**");
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arg0) {
		//引入springmobile
		//arg0.add(deviceHandlerMethodArgumentResolver());
	}
	//最新的方式已经修改在配置文件中了不需要在这里配置了
//	@Bean
//	public CommonsMultipartResolver commonsMultipartResolver() {
//
//		CommonsMultipartResolver c = new CommonsMultipartResolver();
//		c.setMaxUploadSize(1024000000);
//		c.setMaxInMemorySize(2048000);
//		c.setDefaultEncoding("utf-8");
//		return c;
//	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	}
	
	@Bean
	public ViewResolver viewResolver() {
		//配置视图解析器
		InternalResourceViewResolver i = new InternalResourceViewResolver();
		i.setSuffix(".html");
		i.setPrefix("/static/");
		i.setExposeContextBeansAsAttributes(true);
		i.setCache(true);
		return i;
	}

	@Override
	public void addCorsMappings(CorsRegistry arg0) {
//		arg0.addMapping("/**")
//				.allowedHeaders("*")
//				.allowedOriginPatterns("*")
//				.allowCredentials(true)
//				.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
//				.maxAge(3600);

//		arg0.addMapping("/**")
//				.allowedOriginPatterns("http://localhost:8080")
//				.allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
//				.allowedHeaders("*")
//				.exposedHeaders("access-control-allow-headers",
//						"access-control-allow-methods",
//						"access-control-allow-origin",
//						"access-control-max-age",
//						"access_token",
//						"X-Frame-Options")
//				.allowCredentials(true).maxAge(3600);
	}

	@Override
	public void addFormatters(FormatterRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry arg0) {
		// TODO Auto-generated method stub
		arg0.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

	}

	/*资源处理器*/
/*    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/img/**").addResourceLocations("/WEB-INF/"+"/img/");
       registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/"+"/static/");
    }*/

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addViewControllers(ViewControllerRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Validator getValidator() {
		// TODO Auto-generated method stub
		return null;
	}
}
