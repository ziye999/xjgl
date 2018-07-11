package com.jiajie.service.examArrangement;

import java.util.List;
import java.util.Map;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;

public interface ExamSiteSchoolService {
	public PageBean getList(String jyj,String lcid);
	public MsgBean saveSiteSchool(String lcId,List<Map<String, Object>> list);
	public MsgBean deleteSiteSchool(String lcId,List<Map<String, Object>> list);
}
