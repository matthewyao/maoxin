package com.maoxin.util;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class PageNavManagerInterceptor extends HandlerInterceptorAdapter {

	private static Map<String, String> pageNavTypeMap = new HashMap<String, String>();

	static {
	    pageNavTypeMap.put("/staff/add", "staff");
		pageNavTypeMap.put("/staff/all", "staff");
		pageNavTypeMap.put("/staff/delete", "staff");
		pageNavTypeMap.put("/staff/search", "staff");

		pageNavTypeMap.put("/company/add", "company");
		pageNavTypeMap.put("/company/all", "company");
		pageNavTypeMap.put("/company/delete", "company");

		pageNavTypeMap.put("/manage/add", "manage");
		pageNavTypeMap.put("/manage/all", "manage");
		pageNavTypeMap.put("/manage/delete", "manage");

	    pageNavTypeMap.put("/deduct/add", "deduct");
		pageNavTypeMap.put("/deduct/all", "deduct");
		pageNavTypeMap.put("/deduct/delete", "deduct");
		pageNavTypeMap.put("/deduct/search", "deduct");

	    pageNavTypeMap.put("/report/add", "report");
		pageNavTypeMap.put("/report/all", "report");
		pageNavTypeMap.put("/report/delete", "report");
		pageNavTypeMap.put("/report/search", "report");

	}

	/*
	 * Will be called before the actual handler will be executed
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		String currentUrl = request.getServletPath();
		String pageNavType = pageNavTypeMap.get(currentUrl);
		session.setAttribute("pageNavType", pageNavType);
		return super.preHandle(request, response, handler);
	}

	/*
	 * Will be called after the handler is executed (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.web.servlet.ModelAndView)
	 */

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	/*
	 * 
	 * It is called after the complete request has finished (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#
	 * afterCompletion(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

}
