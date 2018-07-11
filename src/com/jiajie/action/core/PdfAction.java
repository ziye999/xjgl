package com.jiajie.action.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.jiajie.action.BaseAction;
import com.jiajie.exceptions.SystemException;
import com.jiajie.service.core.PdfService;

@SuppressWarnings("serial")
public class PdfAction extends BaseAction{
	
	private PdfService service;
	
	public void exportPdf() throws IOException {
		HttpServletRequest request =  getRequest();
		writerPrint(service.exportPdf(request));
	}
	
	public void downLoadPdfByTag() {		
		String tag = getRequest().getParameter("tag") == null?"":getRequest().getParameter("tag");		
		String path =getRequest().getSession().getServletContext().getRealPath("") + "\\export\\pdf\\temp";
	
		getResponse().setHeader("Content-Disposition", "attachment;filename="+new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())+".pdf");
		OutputStream out = null ;
		FileInputStream in = null;
		BufferedInputStream bis = null;
		File f = null;
		try {
			out = getResponse().getOutputStream();
			f = new File(path+"\\"+tag+".pdf");
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
	
	@Resource(name="pdfService")
	public void setService(PdfService service) {
		this.service = service;
	}
	
}
