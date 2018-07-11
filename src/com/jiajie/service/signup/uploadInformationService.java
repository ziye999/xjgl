package com.jiajie.service.signup;

import java.io.File;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface uploadInformationService {
	public abstract PageBean getListPage(String xnxq,String sfzjh,MBspInfo bsp);
	public abstract MsgBean exportKsxxgz(File upload, MBspInfo bspInfo);
}
