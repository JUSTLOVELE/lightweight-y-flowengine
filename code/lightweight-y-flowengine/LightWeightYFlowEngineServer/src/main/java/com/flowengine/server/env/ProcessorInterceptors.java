package com.flowengine.server.env;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowengine.common.utils.CommonConstant;
import com.flowengine.server.model.UserCache;
import com.flowengine.server.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2019-7-11
 * @version 1.00.00
 * @history:
 */
public class ProcessorInterceptors implements HandlerInterceptor{
	
	private static final Log _logger = LogFactory.getLog(ProcessorInterceptors.class);
	private static final String[] IGNORE_URI = {"test", "api", "404", "error","login","/js/","/image/", "/img/", "/component/", "/css/", "lib", "bigData"};

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));//支持跨域请求
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");//是否支持cookie跨域
		response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");//Origin, X-Requested-With, Content-Type, Accept,Access-Token
        String url = request.getRequestURL().toString();

        for (String s : IGNORE_URI) {

            if (url.contains(s)) {

                return true;
            }
        }

        UserCache user = (UserCache) request.getSession().getAttribute(SessionUtils.USER_SESSION);

        if (user == null) {
            _logger.info("session outtime");
            if (request.getHeader("x-requested-with") != null &&
                    request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                response.addHeader("sessionstatus", "timeout");
                response.setContentType("text/html; charset=utf-8");
                response.getWriter().print(renderFailureList("查无缓存"));
                return false;
            } else {
                response.sendRedirect(request.getContextPath()+ "/login");
                return false;
            }
        }

        return true;
    }
	
	public String renderFailureList(String errorMsg) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CommonConstant.Key.CODE, CommonConstant.Status.FAILURE_CODE);
		map.put(CommonConstant.Key.SUCCESS, false);
		map.put(CommonConstant.Key.TOTAL, 0);
		map.put(CommonConstant.Key.DESC, errorMsg);
		
		return getJSON(map);
	}
	
	public String getJSON(Object obj) {
		
		ObjectMapper mapper = new ObjectMapper();
		String msg = "";
		try {
			msg = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			_logger.debug(e.getMessage());
		}
		_logger.info(msg);
		return msg ;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
