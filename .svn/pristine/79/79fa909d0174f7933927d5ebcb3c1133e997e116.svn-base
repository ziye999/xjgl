package com.jiajie.service.examResults;

import java.io.File;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface ResultsResgisterService {

	PageBean getListPage(String xnxq_id, MBspInfo bspInfo);

	MsgBean importResults(String userid, File upload, String lcid);
	
	MsgBean checkCj(String lcid,String userID);
	
	MsgBean submitCj(String lcid,String userID);
	
	MsgBean createTemplate(String lcid);

	MsgBean checkFile(String lcid);

}
