package com.flowengine.server.utils;


import com.flowengine.server.model.UserCache;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * @Description:
 * @author yangzl 2017-12-28
 * @version 1.00.00
 * @history:
 */
public class SessionUtils {
	public final static String USER_SESSION = "userInfoSession";
	
	public static void addUserSession(HttpServletRequest request, Object object){
		
		HttpSession session = request.getSession();
		session.setAttribute(USER_SESSION,object);
	}
	
	public static UserCache getUserSession(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		UserCache object = (UserCache) session.getAttribute(USER_SESSION);
		if(object == null){
			return null;
		}
		return object;
	}
	
	public static void removeUserSession(HttpServletRequest request){
		
		HttpSession session = request.getSession(false);
		session.removeAttribute(USER_SESSION);
	}
}
