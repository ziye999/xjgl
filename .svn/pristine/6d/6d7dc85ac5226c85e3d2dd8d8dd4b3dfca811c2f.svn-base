package com.jiajie.action.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jiajie.action.BaseAction;
import com.jiajie.exceptions.SystemException;
import com.jiajie.service.core.ExcelService;

@SuppressWarnings("serial")
public class ExcelAction extends BaseAction{
	
	private String link;
	private String excelTitle;
	private String headers;
	
	public String getExcelTitle() {
		return excelTitle;
	}

	public void setExcelTitle(String excelTitle) {
		this.excelTitle = excelTitle;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	private ExcelService service;
	
	public void exportExcel() throws IOException {
		HttpServletRequest request =  getRequest();
		writerPrint(service.exportExcel(request));
	}
	
	public void downLoadExcelByTag() {		
		String tag = getRequest().getParameter("tag") == null?"":getRequest().getParameter("tag");		
		String path =getRequest().getSession().getServletContext().getRealPath("") + "\\export\\excel\\temp";
	
		getResponse().setHeader("Content-Disposition", "attachment;filename="+new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())+".xls");
		OutputStream out = null ;
		FileInputStream in = null;
		BufferedInputStream bis = null;
		File f = null;
		try {
			out = getResponse().getOutputStream();
			f = new File(path+"\\"+tag+".xls");
			in = new FileInputStream(f);
			bis = new BufferedInputStream(in);
			byte[] buf = new byte[bis.available()];
			while ((bis.read(buf)) != -1) {}
			out.write(buf);
			out.flush();
		} catch (Exception e) {
			throw new SystemException("下载失败!");
		}finally{
			try {
				if(out!=null)out.close();
				if(in!=null)in.close();
				if(bis!=null)bis.close();
				if(f!=null){
					f.delete();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void exportExcelByLink() throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		if(link!=null && !"".equals(link)){
			String sql = null;
			String claName = link; 
			Class<?> cla = Class.forName(claName); 
			Method[] mes = cla.getMethods();
			for (int i = 0; i < mes.length; i++) {
				if(mes[i].getName().equals("getSqlString")){
					Class[] clas = mes[i].getParameterTypes(); 
					sql = (String)mes[i].invoke(cla.newInstance());
				}
			} 
			System.out.println(sql);
			System.out.println(excelTitle);
			System.out.println(headers);
			String path =getRequest().getSession().getServletContext().getRealPath("") + "\\export\\excel\\temp";
			writerPrint(service.exportExcelByLink(path,sql,excelTitle,headers));
		}else{
			writerPrint("fail");
		}
	}
	
	@Resource(name="excelService")
	public void setService(ExcelService service) {
		this.service = service;
	}
	
}
