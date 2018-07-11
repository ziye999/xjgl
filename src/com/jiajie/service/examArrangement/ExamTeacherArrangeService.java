package com.jiajie.service.examArrangement;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwMonitorteacher;

public interface ExamTeacherArrangeService {
	public PageBean getExamTeacherInfo(Map<String, String> map);
	public PageBean getTeacherInfo(Map<String, String> map);
	public MsgBean saveChouQuTeacher(String lcId,List<Map<String, Object>> list);
	public MsgBean saveLuRuTeacher(CusKwMonitorteacher cusKwMonitorteacher);
	public MsgBean saveDaoRuTeacher(File file,String lcId);
	public MsgBean deleteExamTeacher(String ids);
	
	public PageBean getIAListPage(String lcid);
	public PageBean getPatrolArrangeTeaListPage(String lcid,String xklssl,String kdid);
	public PageBean getInvigilatorArrangeTeaListPage(String jklssl,String sfcxap,String nj,String lcid,String kdid);		
	
	public MsgBean savePatrolArrange(String lcid,String kdid,String jklsids,String xkfws,String teanames);
	public MsgBean saveInvigilatorArrange(List<Map<String, Object>> list);
	
	public PageBean getIASListPage(String lcid,String kdid,String njid,String kmid,String kcid);
	public PageBean getPASListPage(String lcid,String kdid,String xmgh);
	
	public PageBean hasArrangeInvigilatorTeacher(String lcid,String kdid,String kcid,String rcid,String njid);
	public PageBean noneArrangeInvigilatorTeacher(String lcid, String kdid,String kcid, String rcid,String njid);
	public MsgBean saveAdjustInvigilator(String lcid,String kdid,String kcid,String rcid,String njid,String iszjklsid,String jklsids,String teanames);
}
