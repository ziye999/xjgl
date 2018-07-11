package com.jiajie.action.roomsubject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.jiajie.action.BaseAction; 
import com.jiajie.service.roomsubject.RoomSubjectService;

@Controller
public class RoomSubjectAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RoomSubjectService roomSubjectService;
	@Autowired
	public void setRoomSubjectService(RoomSubjectService roomSubjectService) {
		this.roomSubjectService = roomSubjectService;
	}

	private String examdate;
	public String getExamdate() {
		return examdate;
	}

	public void setExamdate(String examdate) {
		this.examdate = examdate;
	}

	private String roomid;

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	 
	public void getListPage() { 
		writerPrint(roomSubjectService.getList(getParamList(), getBspInfo()));
	} 
	
	public String getParamList() {
		StringBuilder sb = new StringBuilder();
		if (roomid != null && !"".equals(roomid)) {
			sb.append(" and t.roomid = '" + roomid + "' ");
		} 
		if (examdate != null && !"".equals(examdate)) {
			sb.append(" and DATE_FORMAT(t.EXAMDATE,'%Y-%m-%d') = '" + examdate + "' ");
		} 
		return sb.toString();
	}
	
	public void getRoom(){
		writerPrint(roomSubjectService.getRoom(getBspInfo()));
	}
	
}
