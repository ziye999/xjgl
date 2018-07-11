package com.jiajie.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jiajie.service.core.UserInfoService;
import com.jiajie.util.bean.MBspInfo;


public class UserInfoFilter implements Filter {
	public void destroy() {
		this.config = null; 
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());  
		UserInfoService service = (UserInfoService)context.getBean("userInfoService");
		HttpServletRequest httprequest = (HttpServletRequest)request;
		
		if(httprequest.getParameter("userId") != null && httprequest.getSession().getAttribute("mBspInfo") == null) {
			String userId = httprequest.getParameter("userId")==null?"":httprequest.getParameter("userId").toString();
			MBspInfo mBspInfo = service.getMBspInfo(userId);
			mBspInfo.setUserId(userId);
			httprequest.getSession().setAttribute("mBspInfo", mBspInfo);
		}
		try{
			chain.doFilter(request, response);
		}catch(Exception e){
		}finally{
		}
	}
	private FilterConfig config; // 后面主要用来记录登录日志 

	public void init(FilterConfig cfg) throws ServletException {
		this.config = cfg; 
	}
}