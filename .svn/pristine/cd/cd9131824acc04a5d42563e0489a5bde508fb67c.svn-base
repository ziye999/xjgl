package com.jiajie.service.core;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.cheatStu.CusKwCheatstu;
import com.jiajie.util.bean.MBspInfo;

public interface FileUpService {
	public MsgBean saveUpFileInfo(File file,String distPath) throws IOException;
	public MsgBean exportExcelTeacherInfo(File file,MBspInfo bspInfo);
	public MsgBean importExamstInfo(File file,MBspInfo bspInfo);
	public MsgBean exportExcelCheatStuFile(File file,CusKwCheatstu cheatStu,MBspInfo bspInfo);
	public MsgBean exportExcelInfo(File file,MBspInfo bspInfo,String lcid);
	public Map<String,Object> getExamKdksInfo(String dt,String room,String type);
	public  Map<String,Object> info(String dt, String room, String type);
}
