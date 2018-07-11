package com.jiajie.service.exambasic;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExaminee;
import com.jiajie.util.bean.MBspInfo;

public interface ExamInfomationService {
	public PageBean getList(String xnxqId,String lcid,MBspInfo bspInfo);
	public String getKs(String pc,String kd,String kc,String lcid,String ks);
	public String getKsyz(String pc,String lcid,String sfzh);
	public PageBean getList(CusKwExaminee examinee,String schools,String kmid,String kdid,String kcid,String xmkhxjh,MBspInfo bspInfo);
	public Object getExamInfomationByKsid(String ksids);
	public PageBean getExamInfomationByBjid(String bjid,String lcid,String xmkhxjh);
	public PageBean ExamStudentCount(String type,String schools,String kslc,MBspInfo bspInfo);
}
