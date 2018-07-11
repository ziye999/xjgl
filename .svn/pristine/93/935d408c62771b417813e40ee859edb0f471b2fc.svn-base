package com.jiajie.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.jiajie.bean.system.TQxglMenuinfo;
import com.jiajie.bean.system.TQxglRoleinfo;
import com.jiajie.bean.system.TQxglRolemenu;
import com.jiajie.bean.system.TQxglUserrole;
import com.jiajie.service.core.MenuinfoService;
import com.jiajie.service.core.RoleinfoService;
import com.jiajie.service.core.RolemenuService;
import com.jiajie.service.core.UserroleService;



import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class FunctionMenu {
	private static FunctionMenu MENU = null;
	private static Cache CACHE = null;
	private Element element = null;
	public static FunctionMenu getMenuInstance() {
		if (null == MENU) {
			MENU = new FunctionMenu();
		}
		if (null == CACHE) {
			CacheManager manager = CacheManager.create();
			CACHE = manager.getCache(BaseConstant.CACHE_NAME);
		}
		return MENU;
	}

	public void initMenuInfoToCache(ApplicationContext alc) {
		MenuinfoService menuinfoService = (MenuinfoService) alc
		.getBean("menuinfoService");
		element = new Element(BaseConstant.MENUINFO_CACHE, menuinfoService.findAll());
		CACHE.put(element);
	}
	public void initRoleInfoToCache(ApplicationContext alc) {
		RoleinfoService roleinfoService = (RoleinfoService) alc
		.getBean("roleinfoService");
		if(roleinfoService != null) {
			element = new Element(BaseConstant.ROLEINFO_CACHE, roleinfoService.findAll());
			CACHE.put(element);
		}
	}
	public void initRoleMenuToCache(ApplicationContext alc) {
		RolemenuService rolemenuService = (RolemenuService) alc
		.getBean("rolemenuService");
		if(rolemenuService != null) {
			element = new Element(BaseConstant.ROLEMENU_CACHE, rolemenuService.findAll());
			CACHE.put(element);
		}
	}
	public void initUserRoleToCache(ApplicationContext alc) {
		UserroleService userroleService = (UserroleService) alc
		.getBean("userroleService");
		if(userroleService != null) {
			element = new Element(BaseConstant.USERROLE_CACHE, userroleService.findAll());
			CACHE.put(element);
		}
	}
	@SuppressWarnings("unchecked")
	public List<TQxglMenuinfo> getMenuInfoFromCache(ApplicationContext alc) {
		synchronized (this) {
			element = CACHE.get(BaseConstant.MENUINFO_CACHE);
			element = null;
			if (element == null) {
				// 调用实际的方法
				initMenuInfoToCache(alc);
			}
		}
		return (List<TQxglMenuinfo>)element.getObjectValue();
	}
	@SuppressWarnings("unchecked")
	public List<TQxglRoleinfo> getRoleInfoFromCache(ApplicationContext alc) {
		synchronized (this) {
			element = CACHE.get(BaseConstant.ROLEINFO_CACHE);
			if (element == null) {
				// 调用实际的方法
				initRoleInfoToCache(alc);
			}
		}
		return (List<TQxglRoleinfo>)element.getObjectValue();
	}
	@SuppressWarnings("unchecked")
	public List<TQxglRolemenu> getRoleMenuFromCache(ApplicationContext alc) {
		synchronized (this) {
			element = CACHE.get(BaseConstant.ROLEMENU_CACHE);
			if (element == null) {
				// 调用实际的方法
				initRoleMenuToCache(alc);
			}
		}
		return (List<TQxglRolemenu>)element.getObjectValue();
	}	
	@SuppressWarnings("unchecked")
	public List<TQxglUserrole> getUserRoleFromCache(ApplicationContext alc) {
		synchronized (this) {
			element = CACHE.get(BaseConstant.USERROLE_CACHE);
			if (element == null) {
				// 调用实际的方法
				initUserRoleToCache(alc);
			}
		}
		return (List<TQxglUserrole>)element.getObjectValue();
	}	
	//获取某用户的菜单
	public List<TQxglMenuinfo> getMenuInfoByUser(String usercode,ApplicationContext alc) {
		List<TQxglUserrole> userroleList = getUserRoleFromCache(alc);
//		for (TQxglUserrole tQxglUserrole : userroleList) {
//			System.out.println(tQxglUserrole.getUsercode()+"用户主键");
//			System.out.println(tQxglUserrole.getRolecode()+"角色主键");
//		}
		List<TQxglUserrole> userroleList_m = new ArrayList<TQxglUserrole>();
		if(userroleList != null && userroleList.size() > 0) {
			for (int i = 0; i < userroleList.size(); i++) {
				TQxglUserrole userrole = userroleList.get(i);
				if(userrole.getUsercode().equals(usercode)) {
					userroleList_m.add(userrole);
				}
			}
		}
		List<TQxglRolemenu> rolemenuList = getRoleMenuFromCache(alc);
//		for (TQxglRolemenu tQ : rolemenuList) {
//			System.out.println("角色菜单"+tQ.getRolecode());
//			System.out.println("菜单主键"+tQ.getMenucode());
//		}
		List<TQxglRolemenu> rolemenuList_m = new ArrayList<TQxglRolemenu>();
		if(rolemenuList != null && rolemenuList.size() > 0) {
			for (int i = 0; i < rolemenuList.size(); i++) {
				TQxglRolemenu rolemenu = rolemenuList.get(i);
				for (int j = 0; j < userroleList_m.size(); j++) {
					TQxglUserrole userrole = userroleList_m.get(j);					
					if(rolemenu.getRolecode().equals(userrole.getRolecode())) {
						rolemenuList_m.add(rolemenu);
					}
				}
			}
		}
		List<TQxglMenuinfo> menuinfoList = getMenuInfoFromCache(alc);
//		for (TQxglMenuinfo tQ : menuinfoList) {
//			System.out.println("菜单主键"+tQ.getMenucode());
//			System.out.println("菜单"+tQ.getMenuname());
//		}
		List<TQxglMenuinfo> menuinfoList_m = new ArrayList<TQxglMenuinfo>();
		if(menuinfoList != null && menuinfoList.size() > 0) {
			for (int i = 0; i < menuinfoList.size(); i++) {
				TQxglMenuinfo menuinfo = menuinfoList.get(i);
				for (int j = 0; j < rolemenuList_m.size(); j++) {
					TQxglRolemenu rolemenu = rolemenuList_m.get(j);					
					if(menuinfo.getMenucode().equals(rolemenu.getMenucode())) {
						if (!menuinfoList_m.contains(menuinfo)) {
							menuinfoList_m.add(menuinfo);
						}
					}
				}
			}
		}
		
		return menuinfoList_m;
	}
	
}
