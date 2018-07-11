package com.jiajie.service.dailyManage;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;

public interface BreakStudyService {

	PageBean getListPage(String organCode, String shcools,String flag, String xj_xm_sfz);

	MsgBean audit(String yyids);
	
}
