package com.jiajie.service.registration;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface ExamStuModifyService {
	public PageBean getExamRoundListPage(String xnxqId,MBspInfo bspInfo);
	public PageBean getExamInStu(String lcid,String schools,String xmkhxjh,MBspInfo bspInfo);
	public PageBean getExamNotStu(String lcid,String schools,String xmkhxjh,MBspInfo bspInfo);	
	
	public MsgBean delExamStu(String lcid,String ksids,String reason,MBspInfo bspInfo);
	public MsgBean supExamStu(String lcid,String xjhs,MBspInfo bspInfo);
	
	public MsgBean uploadExamStu(String lcid,MBspInfo bspInfo);
		
	public PageBean getExamStuByRoundIdListPage(String xxdm,String njdm,String bjdm,MBspInfo bspInfo);
}
