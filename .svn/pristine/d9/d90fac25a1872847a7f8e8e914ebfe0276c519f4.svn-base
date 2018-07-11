package com.jiajie.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.jiajie.util.PageContext;

public class PageBeanFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
		FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httprequest = (HttpServletRequest)request;				
		String url = httprequest.getRequestURL().toString();
		url = url.substring(url.indexOf(".do") - 4, url.indexOf(".do"));
		if("Page".equals(url)) {
			PageContext.setRowIndex(getPageRowIndex(httprequest));
			PageContext.setPageSize(getPageSize(httprequest));
			PageContext.setShowPages(Boolean.parseBoolean(httprequest.getParameter("showtotalpage")));
		}
						
		try{
			if (chain!=null) {
				chain.doFilter(request, response);
			}
		}catch(Exception e){
		}finally{
			if("Page".equals(url)) {
				PageContext.resetPageSize();
				PageContext.resetRowIndex();
			}
		}
	}

	public void init(FilterConfig cfg) throws ServletException {
	}
	
	private int getPageRowIndex(HttpServletRequest request){	
		String currentPageIndex = request.getParameter("start");
		int pageindex = 0;
		if(currentPageIndex  != null && !currentPageIndex.trim().equals("")){
			try{
				pageindex = Integer.parseInt(currentPageIndex);
			}catch(Exception e){}
		}
		return pageindex;
	}
	private int getPageSize(HttpServletRequest request){
		String currentPageSize = request.getParameter("limit");
		if(currentPageSize !=null && !currentPageSize.trim().equals("")){
			try{
				return Integer.parseInt(currentPageSize);
			}catch(Exception e){}
		}
		return 20;
	}
}