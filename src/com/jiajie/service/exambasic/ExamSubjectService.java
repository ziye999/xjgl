package com.jiajie.service.exambasic;

import java.util.Map;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExamsubject;
import com.jiajie.util.bean.MBspInfo;

public interface ExamSubjectService {
	public PageBean getList(Map<String, Object> map,MBspInfo mBspInfo);
	public MsgBean saveOrUpdateExamSubject(CusKwExamsubject examsubject,String xnxqValue);
	public MsgBean copyExamSubject(CusKwExamsubject examsubject,String xnxqValue,String dwid);
	public MsgBean deleteExamSubject(String ids);
	public MsgBean getExamSubject(String id);
}
