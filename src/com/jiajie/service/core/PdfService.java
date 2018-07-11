package com.jiajie.service.core;

import javax.servlet.http.HttpServletRequest;

import com.jiajie.bean.MsgBean;

public interface PdfService {
	public MsgBean exportPdf(HttpServletRequest request);
}
