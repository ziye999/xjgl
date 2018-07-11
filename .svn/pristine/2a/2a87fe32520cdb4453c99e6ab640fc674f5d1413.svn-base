package com.jiajie.service.exambasic;

import java.io.File;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;


public abstract interface ExamService
{
 

public abstract MsgBean glzp(String examround);

public abstract PageBean getExamCard(String sfzjh);

public abstract MsgBean exportPhontoInfo(File upload, MBspInfo bspInfo,
		String lcid);



public abstract MsgBean exportKsInfo(File upload, MBspInfo bspInfo,
		String parameter);


public abstract PageBean searchSore(String lcid, String kmid,String kcid, String roomid,
		String sfzh, String xm, String pageindex, String pagesize);

public abstract MsgBean createScore(String lcid, String kmid, String kdid, String kcid, String sfzh, String xuexiao);

public abstract Object queryScore(String xm, String sfzh);

public abstract MsgBean getExamKdDetail();

public abstract PageBean getscoreList(String pageindex, String pagesize,String sfzjh);

public abstract MsgBean getExamZkdwDetail();

public abstract MsgBean exportKsxxgz(File upload, MBspInfo bspInfo);

public abstract MsgBean exportKswj(File upload, MBspInfo bspInfo);

public abstract MsgBean exportXls(String lcid, String kmid, String kcid,
		String kdid,String xuehao);

public abstract MsgBean creatmm();

public abstract MsgBean checksj();

public abstract Object ruku();

public abstract MsgBean exportPaper(String sfzjh);

public abstract Object getExamKdDetailSDD();


}