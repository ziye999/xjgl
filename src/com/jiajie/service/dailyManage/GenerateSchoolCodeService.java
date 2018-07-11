package com.jiajie.service.dailyManage;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;

public interface GenerateSchoolCodeService {

	PageBean getListPage(String organCode, String xxids);

	MsgBean generate(String organCode, String xxids);

	MsgBean update(String organCode, String xxids, String xxdm);

}
