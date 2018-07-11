package com.jiajie.service.core;

import javax.servlet.http.HttpServletRequest;

import com.jiajie.bean.MsgBean;

public interface ExcelService {
	public MsgBean exportExcel(HttpServletRequest request);
	
	public MsgBean exportExcelByLink(String path,String sql,String excelTitle,String headers);
}
