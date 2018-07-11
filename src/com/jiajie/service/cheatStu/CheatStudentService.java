package com.jiajie.service.cheatStu;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.cheatStu.CusKwCheatstu;
import com.jiajie.util.bean.MBspInfo;

public interface CheatStudentService {

	public PageBean getList(String xn,String xq,MBspInfo bspInfo);
	public PageBean getListByLcid(String lcid,String schools,String xmkhxjh,MBspInfo bspInfo,String flag,String wjid);
	public MsgBean deleteCheatStu(String wjids);
	public MsgBean saveOrUpdateCheatStu(CusKwCheatstu cheatStu,MBspInfo bspInfo);
	public MsgBean reportedCheatStu(String lcid);
	public PageBean getExamRound(String xn,String xq,MBspInfo bspInfo);
	public MsgBean auditCheatStudent(String lcid);
}
