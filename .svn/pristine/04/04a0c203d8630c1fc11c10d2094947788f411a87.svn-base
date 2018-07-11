package com.jiajie.action.basicsinfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.basicsinfo.CusKwRoom;
import com.jiajie.service.basicsinfo.RoomService;

@SuppressWarnings("serial")
public class RoomAction extends BaseAction{
	
	private CusKwRoom room;
	
	private String fjids;
	
	@Autowired  
	private RoomService roomService;
	
	public void getListPage() {
		writerPrint(roomService.getList(room, getBspInfo()));
	}
	
	public void loadRoom(){
		writerPrint(roomService.loadRoom(room.getFjid()));
	}

	public void addRoom() {
		if("".equals(room.getFjid())) {
			room.setFjid(null);
		}
		writerPrint(roomService.saveOrUpdateRoom(room));
	}
	public RoomService getRoomService() {
		return roomService;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public void delRoom() {
		writerPrint(roomService.delRoom(fjids));
	}

	public void getBuilding() {
		writerPrint(roomService.getBuilding(getBspInfo()));
	}
	
	public void checkRoomcodeRepeat() {
		writerPrint(roomService.checkRoomcodeRepeat(room.getRoomcode()));
	}
	

	public CusKwRoom getRoom() {
		return room;
	}
	public void setRoom(CusKwRoom room) {
		this.room = room;
	}
	public String getFjids() {
		return fjids;
	}
	public void setFjids(String fjids) {
		this.fjids = fjids;
	}
	
}
