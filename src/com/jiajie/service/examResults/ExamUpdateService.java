package com.jiajie.service.examResults;

import java.util.List;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;

public interface ExamUpdateService {

	List getSubject(String lcid,String lc_find);

	PageBean getListPage(String lcid, String xuexiao, String xm_kh_xjh, String lc_find, String kmid, String kdid, String kcid);

	MsgBean submitJkCj(String idInfo, String lcid, String lc_find,String userID);
	
	MsgBean submitXk(String idInfo, String lcid, String lc_find,String userID);
	
	MsgBean deleteStuScore(String delInfo, String lcid, String lc_find);

	MsgBean addStuScore(String lcid, String kh, String xjh, String addInfo, String userId, String lc_find);

	MsgBean updateStuScore(String lcid, String kh, String xjh, String addInfo, String lc_find, String userid);

}
