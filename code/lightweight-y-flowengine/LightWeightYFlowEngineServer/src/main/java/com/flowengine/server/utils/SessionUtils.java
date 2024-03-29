package com.flowengine.server.utils;


import cn.hutool.core.util.StrUtil;
import com.flowengine.common.utils.CacheObject;
import com.flowengine.common.utils.CommonConstant;
import com.flowengine.server.model.UserCache;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @author yangzl 2017-12-28
 * @version 1.00.00
 * @history:
 */
public class SessionUtils {
	public final static String USER_SESSION = "userInfoSession";

	private final static Map<String, UserCache> cacheMap = new ConcurrentHashMap<String, UserCache>();

	public static void addUserSession(HttpServletRequest request, UserCache userCache){
		String accessToken = request.getHeader(CommonConstant.Token.ACCESS_TOKEN);
		addUserSession(accessToken, userCache);
	}

	public static void addUserSession(String accessToken, UserCache userCache){
		cacheMap.put(accessToken, userCache);
	}

	public static UserCache getUserSession(String accessToken){

		if(StrUtil.isEmpty(accessToken)) {
			return null;
		}

		return cacheMap.get(accessToken);
	}

	public static UserCache getUserSession(HttpServletRequest request){
		String accessToken = request.getHeader(CommonConstant.Token.ACCESS_TOKEN);
		return getUserSession(accessToken);
	}

	public static void removeUserSession(String accessToken){
		cacheMap.remove(accessToken);
	}

	public static void removeUserSession(HttpServletRequest request){
		String accessToken = request.getHeader(CommonConstant.Token.ACCESS_TOKEN);
		removeUserSession(accessToken);
	}

//	public static void addUserSession(HttpServletRequest request, Object object){
//
//		HttpSession session = request.getSession();
//		session.setAttribute(USER_SESSION,object);
//	}
//
//	public static UserCache getUserSession(HttpServletRequest request){
//
//		HttpSession session = request.getSession();
//		UserCache object = (UserCache) session.getAttribute(USER_SESSION);
//		if(object == null){
//			return null;
//		}
//		return object;
//	}
//
//	public static void removeUserSession(HttpServletRequest request){
//
//		HttpSession session = request.getSession(false);
//		session.removeAttribute(USER_SESSION);
//	}
}
