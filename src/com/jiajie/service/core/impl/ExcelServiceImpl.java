package com.jiajie.service.core.impl;
/**
 * 下载Excel表格
 * **/

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.basicsinfo.CusKwBuilding;
import com.jiajie.dao.DaoSupportImpl;
import com.jiajie.exceptions.SystemException;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.basicsinfo.BuildingService;
import com.jiajie.service.core.ExcelService;
import com.jiajie.util.ExcelUtil;
import com.jiajie.bean.MsgBean;
@Service("excelService")
@SuppressWarnings("all")
public class ExcelServiceImpl extends ServiceBridge implements ExcelService {

	public MsgBean exportExcel(HttpServletRequest request) {
		String path =request.getSession().getServletContext().getRealPath("") + "\\export\\excel\\temp";
		String headers = request.getParameter("headers")==null?"":request.getParameter("headers");
		String dataset = request.getParameter("dataset")==null?"":request.getParameter("dataset");
		JSONArray arrayHeader  = JSONArray.fromObject(headers);
		JSONArray arraydataset = JSONArray.fromObject(dataset);
		OutputStream out ;
		String temp = java.util.UUID.randomUUID().toString();
		try {
		  	out = new FileOutputStream(path+"\\"+temp+".xls");
		  	String excelTitle = "第一个工作簿";
		  	if(!isNull(request.getParameter("excelTitle")==null?"":request.getParameter("excelTitle"))){
		  		excelTitle = request.getParameter("excelTitle");
		  	}
			ExcelUtil.exportExcel(excelTitle,arrayHeader,arraydataset,out);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.debug(e.getMessage() +"导出Excel失败!");
			throw new SystemException("导出Excel失败!");
		}
		return this.getMsgBean(temp,false);
	}
	
	public MsgBean exportExcelByLink(String path,String sql,String excelTitle,String headers) {
		List<Map<String,Object>> dataset = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		OutputStream out ; 
		JSONArray arrayHeader  = JSONArray.fromObject(headers);
		JSONArray arraydataset  = JSONArray.fromObject(dataset);
		String temp = java.util.UUID.randomUUID().toString();
		try {
		  	out = new FileOutputStream(path+"\\"+temp+".xls");
		  	if(excelTitle==null || "".equals(excelTitle)){
		  		excelTitle = "第一个工作簿";
		  	}
			ExcelUtil.exportExcel(excelTitle,arrayHeader,arraydataset,out);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.debug(e.getMessage() +"导出Excel失败!");
			throw new SystemException("导出Excel失败!");
		}
		return this.getMsgBean(temp,false);
	}

	protected boolean isNull(String value){
		if(value==null){
			return true;
		}
		if(value.equals("")){
			return true;
		}
		if(value.equals("null") || value.equals("NULL") || value.equals("undefined")){
			return true;
		}
		return false;
		
	}

}
