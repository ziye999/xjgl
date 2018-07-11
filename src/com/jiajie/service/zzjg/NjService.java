package com.jiajie.service.zzjg;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;

public interface NjService {
	public PageBean getList(String xx_jbxx_id,String njmc,MBspInfo bspInfo);
	public MsgBean del(String xx_njxx_id);
	public MsgBean update(String xx_njxx_id,String xx_jbxx_id,String njmc,String rxnf,String jyjd,String djdm,String njzt);	
}
