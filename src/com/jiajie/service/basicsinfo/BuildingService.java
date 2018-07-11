package com.jiajie.service.basicsinfo;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.basicsinfo.CusKwBuilding;
import com.jiajie.util.bean.MBspInfo;

public interface BuildingService {
	public PageBean getList(CusKwBuilding building,MBspInfo bspInfo);
	public MsgBean saveOrUpdateBuilding(CusKwBuilding building);
	public MsgBean delBuilding(String lfids);
	public MsgBean getBuilding(String lfid);
	public Object getCkdw(MBspInfo bspInfo);
	//增加人： 包敏 增加时间：    2018-05-18 增加原因: 关联考点
	public MsgBean relateBuilding(CusKwBuilding building);
	public MsgBean getrelateBuilding(String schoolid);
	//增加人： 包敏 增加时间：    2018-05-18 增加原因: 关联考点
}
