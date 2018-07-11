package com.jiajie.service.signup;

import java.io.File;
import java.util.List;

import net.rubyeye.xmemcached.transcoders.StringTranscoder;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.XsInfo;

public interface SignupService {
	
	//查询考生
	public PageBean getList();
	
	//根据条件查询,姓名，身份证号，单位，成绩,年度，轮次
	public PageBean getlist(String xm,String SFZJH,String bmlcid,String zp);
	
	//查询考生详细信息
	public MsgBean getList(String SFZJH);
	public Object getList1(String SFZJH);
	//查询政治面貌
	public Object getZzmm();
	
	//查询民族
	public Object getMz(String dictCode);
	
	//查询文化程度
	public Object getWhcd();
	//查询执法类型
	public Object getZflb();
	//查询发证单位
	public Object getFzdw();
	//删除考生
	public MsgBean delte(String bmlcid,String sfzjh);
	//添加考生信息
	public MsgBean saveBean(XsInfo xsInfo,String bmlcid,String ckdm,MBspInfo bspInfo);
	// 上传考生信息
	public MsgBean exportKsInfo(File file, MBspInfo bspInfo, String bmlcid,String CKDW);
	//关联照片
	public abstract MsgBean glzp(String examround,String descDir1);
	//修改考生信息
	public MsgBean update(XsInfo xsinfo,String bmlcid);
	public Object getZkdw(String zkdw);
}
