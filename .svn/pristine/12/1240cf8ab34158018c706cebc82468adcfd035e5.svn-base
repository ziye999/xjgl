package com.jiajie.service.cheatStu;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.MsgBean;
import com.jiajie.util.bean.MBspInfo;

public interface LackOfTestStudentService {
	public PageBean getExamRounds(String xn,String xq,String userId,MBspInfo bspInfo);
	public PageBean getLackOfTestStudent(String lcId,String xm_kh_xjh,String schoolId);
	public MsgBean deleteLackOfTestStudents(String ids);
	public MsgBean importLackOfTestStudent(String lcId,File upload,String userId);
	public MsgBean saveLackOfTestStudent(Map<String, String> map);
	public InputStream getExcelInputStream(String lcId);
	MsgBean submitQk(String lcid,String userID);
}
