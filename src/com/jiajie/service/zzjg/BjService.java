package com.jiajie.service.zzjg;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface BjService {
	public PageBean getList(String xx_jbxx_id,String bjmc,MBspInfo bspInfo);	
	public MsgBean del(String xx_bjxx_id);
	public MsgBean update(String xx_bjxx_id,String xx_jbxx_id,String bjmc,String xx_njxx_id,String bjzt);	
}
