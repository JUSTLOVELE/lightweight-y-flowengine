package com.flowengine.server.env;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;


/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
@Component
public class SchedulingConfig {

	private static final Log _logger = LogFactory.getLog(SchedulingConfig.class);

	
	/**
	 */
	//@Scheduled(cron = "0 30 23 * * ?" )
	public void clearTemporySaveData() {

	}
}
