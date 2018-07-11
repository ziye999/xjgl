package com.jiajie.service.signup;

import java.io.File;
import java.util.List;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface ManagementService {
	public PageBean getList(String xn,String dwid,MBspInfo mbf);
	
	public PageBean getXsList(String lcid);
	
	public Object getSjjyj(String dwid,String dwtype);//获取市级组考单位名称
	
	//获取登陆参考单位所属组考单位
	public Object getJyj(String sjjyj,String dwtype,String xxdm);
	
	//获取登陆参考单位
	public Object getSchool(String xjjyj,String dwtype,String dwid);
	
	public PageBean getCqks(String xx,String lcid,MBspInfo bspInfo,String khao,String sfzjh);//按条件抽取考生
	
	public MsgBean saveEaxminee(String lcid,String khao,String xjh);
	
	public MsgBean updateEaxminee(String bmlcid);
	
	public List getKh(String lcid,String khao);
	
	public MsgBean exportExcelInfo(File file,MBspInfo bspInfo,String lcid);
	public int exportKsInfo(String kmid,File file);
	
	public List getXsByxjh(String ckdwh,String ckdw,String xjh,String lcid);
}
