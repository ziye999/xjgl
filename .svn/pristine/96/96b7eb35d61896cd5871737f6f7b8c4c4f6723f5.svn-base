package com.jiajie.service.examArrangement;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface ExamRoomArrangeService {
	public PageBean getExamRounds(String xnxqId,String userId,MBspInfo bspInfo);
	public PageBean getExamRooms(String lcId,String schoolId,String lfId,String jslx);
	public Boolean saveExamRooms(String lcId,String schoolId,String lfId,String jslx,Integer hang,Integer lie);
	public MsgBean saveOrUpdateExamRooms(String lcId,String schoolId,String kcId,String roomId,Integer hang,Integer lie);
	public Object arrangeStu(String lcid, String kcapzgz, String wskc, String zwpl, String pksx,MBspInfo bspInfo);
}
