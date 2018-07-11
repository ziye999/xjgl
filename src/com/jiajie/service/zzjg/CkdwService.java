package com.jiajie.service.zzjg;

import java.io.File;
import java.util.List;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.bean.exambasic.CusKwbmExamround;
import com.jiajie.bean.registrationSystem.ZxxsXnxq;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.bean.zzjg.CusKwZkdw;
import com.jiajie.util.bean.MBspInfo;

public interface CkdwService {
	public PageBean getList(CusKwCkdw ckdw, String dwlx, MBspInfo bspInfo);
	public MsgBean saveOrUpdateCkdw(CusKwCkdw ckdw);
	public MsgBean delCkdw(String lfids);
	public MsgBean getCkdw(String lfid);
	public Object getZkdw(MBspInfo bspInfo);
	
	//查询默认年度
	public MsgBean getZx();
	
	//添加报名轮次
	public MsgBean saveBm(CusKwbmExamround exam,String usertype);
	
	public List<CusKwZkdw> getFzb(String zk) ;
	
	public List<CusKwExamround> getkl(String dwid,String xnxqId);
	public List<ZxxsXnxq> getZ();
  	public  List<CusKwCkdw> getCk(String xxmc);
  	
  	public MsgBean exportKsInfo1(File file, MBspInfo bspInfo);
  	
   public MsgBean deletebm(String xxdms);
}
