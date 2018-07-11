package com.jiajie.service.dailyManage;

import java.util.List;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface PhotoService {  
	MsgBean saveUpFileInfo(String newfileName,String distPath); 
	public PageBean getPhotoList(String schoolname,MBspInfo bspInfo,String distPath);
	public List getPhotoListReport(String zpcjId,String distPath,MBspInfo bspInfo);
	public MsgBean auditPhoto(String zpcjId);
	public List auditPhotoUpdate(String zpcjId,String distPath,MBspInfo bspInfo);
	public MsgBean updateStudentPhoto(List list,MBspInfo bspInfo);
}
