package com.jiajie.service.basicsinfo;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.basicsinfo.CusKwRoom;
import com.jiajie.util.bean.MBspInfo;

public interface RoomService {
	public PageBean getList(CusKwRoom room, MBspInfo bspInfo);
	public MsgBean saveOrUpdateRoom(CusKwRoom room);
	public MsgBean delRoom(String fjids);
	public Object getBuilding(MBspInfo bspInfo);
	public Object checkRoomcodeRepeat(String roomcode);
	public MsgBean loadRoom(String fjid);
}
