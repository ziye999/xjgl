package com.jiajie.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;


public class LogoutCaptchaFilter extends LogoutFilter { 
	protected String getRedirectUrl(ServletRequest req, ServletResponse resp,Subject subject) {
		HttpServletRequest request = (HttpServletRequest) req;
		String redirectUrl = request.getParameter("returnUrl");
		redirectUrl = getRedirectUrl();
		return redirectUrl;
	}
}
