package com.jiajie.service.examineePrint;

import java.io.File;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface ResultsListService {

	PageBean getListPage(String xnxq, MBspInfo bspInfo);
	PageBean getStudentScoreListPage(String lcid,String zkdw,String bmlcid,String name,String sfzjh,String sfhg,String wj);
	public MsgBean checkStatue(String lcid,String zkdw,String bmlcid);
}
