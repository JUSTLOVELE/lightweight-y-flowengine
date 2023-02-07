package com.flowengine.common.utils;


import com.srm.common.utils.enums.CacheTimeUnit;

/**
 * @Description:缓存对象
 * @author yangzl 2018-1-23
 * @version 1.00.00
 * @history:
 */
public class CacheObject {

	private Object cache;
	
	private long startTime;
	
	private long endTime;

	public Object getCache() {
		return cache;
	}
	
	public static CacheObject getCache(Object c, CacheTimeUnit timeUnit, int unitValue) {
		
		CacheObject cacheObject = new CacheObject();
		cacheObject.setCache(c, timeUnit, unitValue);
		
		return cacheObject;
	}
	
	public void setCache(Object cache, CacheTimeUnit timeUnit, int value) {
		
		this.cache = cache;
		this.startTime = System.currentTimeMillis();
		this.endTime = this.startTime + value*timeUnit.getSecond()*1000;
	}

	public void setCache(Object cache) {
		this.cache = cache;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
}
