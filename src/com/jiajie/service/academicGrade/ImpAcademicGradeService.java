package com.jiajie.service.academicGrade;

import java.io.File;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface ImpAcademicGradeService {
	PageBean getList(String xmxjh,String schools,MBspInfo bspInfo);
	
	MsgBean updateCj(String cjid,String byjyny,String byjyjd,MBspInfo bspInfo);
	
	MsgBean delCj(String cjid);
	
	MsgBean exportExcelInfo(File file,String orcode);
}
