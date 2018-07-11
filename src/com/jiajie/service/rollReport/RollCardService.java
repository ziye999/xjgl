package com.jiajie.service.rollReport;

import java.util.List;
import java.util.Map;

import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface RollCardService {
	public  List<Map<String, Object>> getSchoolClassList(String organ,String bjids);
	public List<Map<String,Object>> getPicList(String organ,String bjids);
	public PageBean getClassList(String orgn,String nj,MBspInfo bspInfo);
	public PageBean getList(String orgn,String nj,String njbj,String xbm,String xmxjh,MBspInfo bspInfo);
	public PageBean getStudentList(String xjh);	
}
