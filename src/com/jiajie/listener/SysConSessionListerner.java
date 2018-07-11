package com.jiajie.listener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener; 
import com.jiajie.util.MemCached;
import com.jiajie.util.SysContent;

public class SysConSessionListerner implements HttpSessionListener{

	public void sessionCreated(HttpSessionEvent se) {
//		HttpServletRequest req = SysContent.getRequest();
//		if(req!=null){
//			HttpServletResponse resp = SysContent.getResponse();
//			Cookie[] cooks =  req.getCookies();
//			boolean  flag = true;
//			if(cooks!=null){
//				for (int i = 0; i < cooks.length; i++) {
//					Cookie cook = cooks[i];
//					if("KSGLSESSIONID".equals(cook.getName())){
//						flag = false;
//						if(cook.getValue()==null || "".equals(cook.getValue())){
//							Cookie cookie = new Cookie("KSGLSESSIONID",se.getSession().getId());
//							se.getSession().setAttribute("KSGLSESSIONID", se.getSession().getId());
//							resp.addCookie(cookie);
//						}else{
//							se.getSession().setAttribute("KSGLSESSIONID", cook.getValue());
//							se.getSession().setAttribute("mBspInfo",MemCached.getInstance().get(cook.getValue()));														
//						}
//					}
//				}
//				if(flag){
//					Cookie cookie = new Cookie("KSGLSESSIONID",se.getSession().getId());
//					se.getSession().setAttribute("KSGLSESSIONID", se.getSession().getId());
//					resp.addCookie(cookie);
//				}
//			}else{
//				se.getSession().setAttribute("KSGLSESSIONID", se.getSession().getId());
//				Cookie cookie = new Cookie("KSGLSESSIONID",se.getSession().getId());
//				resp.addCookie(cookie);
//			}
//		}
	}

	public void sessionDestroyed(HttpSessionEvent se) {  
		HttpServletRequest req = SysContent.getRequest();
		if(req!=null){
			Cookie[] cooks =  req.getCookies();
			if(cooks!=null){
				for (int i = 0; i < cooks.length; i++) {
					Cookie cook = cooks[i];
					if("KSGLSESSIONID".equals(cook.getName())){
						if(cook.getValue()!=null && !"".equals(cook.getValue())){
							MemCached.getInstance().delete(cook.getValue());
						}
					} 
				}
			}
		}
	}
	 
}
