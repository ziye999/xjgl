package com.jiajie.service.scoreModify;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface ModifyScoreService {

	PageBean getListPage(String xnxq_id, MBspInfo bspInfo);

	MsgBean autoModify(String lcid,String userID);

	PageBean cheatListPage(String lcid,String km,String xm_kh_xjh);

	MsgBean manualModify(String lcid, String xgkf, String userid);

	
}
