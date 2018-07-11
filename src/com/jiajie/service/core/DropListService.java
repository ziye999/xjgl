package com.jiajie.service.core;

import java.util.Map;

import com.jiajie.util.bean.MBspInfo;

public interface DropListService {
	public Object getDictList(String dictCode);
	//获取年度
	public Object getXn(String orgId, MBspInfo bspInfo);
	//获取考试类型
	public Object getExamtype(MBspInfo bspInfo);
	//获取星期
	public Object getWeeks(String params, String orgId);
	//获取课程
	public Object getCourse(String dwid);
	//获取年度
	public Object getXnxq(MBspInfo bspInfo);
	//获取考试轮次
	public Object getExamRound(String xnxqid,String userId,MBspInfo bspInfo);	
	public Object getExamRoundByXnxq(String xnxqid,MBspInfo bspInfo);
	//获取等级
	public Object getGrade(String userType,String dwid);
	//获取课程
	public Object getClassByGrade(String njid);
	public Object getClassByGrade(String njid,String schoolId);
	//获取考试时长
	public Object getTimeLength();
	//获取登陆参考单位所属组考单位
	public Object getJyj(String sjjyj,MBspInfo bspInfo);
	//获取登陆参考单位
	public Object getSchool(String xjjyj,MBspInfo bspInfo);
	//获取登陆参考单位所属组考单位的主管单位
	public Object getSjjyj(MBspInfo bspInfo);	
	//获取考点名称
	public Object kaoDianMc(String lcid,MBspInfo bspInfo);
	//获取教学楼
	public Object jiaoXueLou(String kdId,String schoolid);
	//获取考场
	public Object examRoom(String kdId);	
	//根据考试轮次获取考试科目
	public Object getKskmBykslc(String lcid);
	//获取年度数据
	public Object getXnxqListPage();	
	//根据参考单位id获取等级
	public Object getgradeBySchool(String schId);
	public Object getnjBySchool(String schId);
	//获取组织机构树
	public Object getOrganTree(String organCode,String type);
	
	//增加人： 包敏   增加时间: 2018-05-17  增加原因： 获取考点信息表
	public Object getPointTree(String organCode);
	//增加人： 包敏   增加时间: 2018-05-17  增加原因： 获取考点信息表
	
	//获取等级课程树
	public Object getGradeClassTree(String schoolCode,MBspInfo bspInfo);
	//获取组织机构等级课程树
	public String getOrganNjBjTree(String type,MBspInfo bspInfo);
	//获取学籍状态
	public Object getXjzt(String state);	
	//获得考试科目
	public Object getKsKm(String params);		
	//从数据字典获取等级
	public Object getNjFromEnum();
	//获得课程
	public Object getKeCheng();	
	//获取结业类型
	public Object getJieye();	
	//获取毕业年年度
	public Object bynd();
	//获取性别
	public Object sysEnumXb();
	//获取等级
	public Object sysEnumDj();
	//根据参考单位代码获取课程信息
	public Object getKeChengBySchool(MBspInfo bspInfo);
	//获得角色
	public Object getRole();
	public Map<String, Object> getUserDz(String lcid,String kdid);
}
