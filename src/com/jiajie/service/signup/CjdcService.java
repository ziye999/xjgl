package com.jiajie.service.signup;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface CjdcService {
	
	public PageBean getCj(String xn,MBspInfo mbs);
	
	public  MsgBean dxsl(MBspInfo mbs);

}
