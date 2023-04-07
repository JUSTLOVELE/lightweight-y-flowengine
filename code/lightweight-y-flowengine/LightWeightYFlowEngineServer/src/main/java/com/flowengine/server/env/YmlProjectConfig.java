package com.flowengine.server.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description:YmlProjectConfig 读取projectconfig配置信息
 * @author yangzl 2019-11-06
 * @version 1.00.00
 * @history:
 */
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "projectconfig")    
public class YmlProjectConfig {

	private String encryptKey;

	private String pk;

	private String sk;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getSk() {
		return sk;
	}

	public void setSk(String sk) {
		this.sk = sk;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}
}
