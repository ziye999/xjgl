package com.jiajie.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

public class UserCaptchaFilter extends UserFilter {
	//未登录重定向到登陆页
	protected void redirectToLogin(ServletRequest req, ServletResponse resp)
			throws IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String loginUrl;
		//后台地址跳转到后台登录地址，前台需要登录的跳转到shiro配置的登录地址
		loginUrl = getLoginUrl();
		WebUtils.issueRedirect(request, response, loginUrl);
	}
}
