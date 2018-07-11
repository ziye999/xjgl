package com.jiajie.service.examArrangement;

import java.util.List;
import java.util.Map;

import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface DataPrintService {  
	public List<Map<String, Object>> kaohaomenqian(String lcid,String kmid,String kcid,String kdid);
	public Map<String, Object> kaohaoduizhaobiaotou(String xnxq,String lcid);
	public List<Map<String, Object>> kaohaoduizhao(String lcid,String kmid,String school,String selecttype);
	public List<Map<String, Object>> kaodianjiankao(String lcid,String kdid);
	public List<Map<String, Object>> kaodianfenbu(String lcid,String kdid);
	public Map<String, Object> kaodianfenbubiaotou(String xnxq,String lcid,String kdid);
	public List<Map<String, Object>> kaoshiricheng(String lcid,String kdid,String school,String nj,MBspInfo bspInfo); 
	public Map<String, Object> kaoshirichengbiaotou(String xnxq,String lcid,String school,String nj,MBspInfo bspInfo);
	public PageBean getExamBasicInfo(String lcid,String kmid,String kcid,String kdid);
	public PageBean getExamCard(String lcid,String kmid,String school,String kcid,String kdid,String sfzjh,String xuexiao,MBspInfo bspInfo);
	public PageBean getExamDjInfo(String lcid,String kmid,String kcid,String kdid);
	public PageBean getExamKsInfo(String lcid,String kmid,String kdid);
}
