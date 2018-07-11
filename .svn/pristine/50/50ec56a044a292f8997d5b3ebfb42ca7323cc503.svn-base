package com.jiajie.service.examArrangement;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface ExamSchoolArrangeService {
	public PageBean getList(String organ_sel,String lcid,MBspInfo bspInfo);
	public PageBean getSeating(String kdid,String lcid,String xxdm);
	public PageBean getStudent(String lcid,String jyj,String xxdm,String nj,String bj);
	public MsgBean addStudentArrange(String ksids,String lcid,String xxdm,String kdid);
	public MsgBean updateData();
	public MsgBean autoStudentArrange(String lcid,String kdid,String xxdms,String aprses);
	public MsgBean deleteArrange(String lcid,String kdid);
	public MsgBean deleteSchoolArrange();
	
	MsgBean deleteCKSchool(String lcid,String xxcode);
	MsgBean saveCKSchool(String lcid,String xxcode);
}
