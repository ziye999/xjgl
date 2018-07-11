package com.jiajie.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jiajie.util.FunctionMenu;
/**
 * 
 * 
 * 项目名称：zypj 类名称：ApplicationListener 类描述： 创建人：rock0304 创建时间：Nov 20, 2014 3:45:59
 * PM 修改人：rock0304 修改时间：Nov 20, 2014 3:45:59 PM 修改备注：
 * 
 * @version
 * 
 */
public class ApplicationListener implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent event) {
	}

	public void contextInitialized(ServletContextEvent event) {
		@SuppressWarnings("unused")
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(event.getServletContext());
		// 加载菜单到缓存
		FunctionMenu.getMenuInstance().initMenuInfoToCache(wac);
		// 加载权限组到缓存		
		FunctionMenu.getMenuInstance().initRoleInfoToCache(wac);
		// 加载组菜单到缓存		
		FunctionMenu.getMenuInstance().initRoleMenuToCache(wac);
		// 加载用户组到缓存		
		FunctionMenu.getMenuInstance().initUserRoleToCache(wac);
	}
}
