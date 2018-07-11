package com.jiajie.action.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;

import com.jiajie.service.core.DropListService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.Tools;
import com.jiajie.util.bean.OrganTree;

@SuppressWarnings("serial")
public class DropListAction extends BaseAction{
	
	@Autowired
	private DropListService service;
	
	private String dictCode;
	
	private String params;
	
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		try {
			this.params = new String(params.getBytes("iso8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}			
	}

	public void getDictList() {
		writerPrint(service.getDictList(dictCode));
	}

	public DropListService getService() {
		return service;
	}

	public void setService(DropListService service) {
		this.service = service;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	//获取全国系统年度下拉
	public void xn() {
		writerPrint(service.getXn(getOrgId(getUserid()),getBspInfo()));
	}

	//获取考试类型
	public void examtype() {
		writerPrint(service.getExamtype(getBspInfo()));
	}

	//获取考试周次
	public void weeks() {
		writerPrint(service.getWeeks(params, getOrgId(getUserid())));
	}
	
	//获取课程
	public void course(){
		String dwid=getBspInfo().getOrgan().getOrganCode();
		writerPrint(service.getCourse(dwid));
	}
	
	//获取年度
	public void xnxq(){
		writerPrint(service.getXnxq(getBspInfo()));
	}
	
	//获取考试轮次(根据当前登录参考单位及参考单位所属单位及上级单位)
	public void examround() {
		String xnxqid=params;
		String userId=getUserid();		
		writerPrint(service.getExamRound(xnxqid,userId,getBspInfo()));
	}
	
	public void ExamRoundByXnxq() {
		String xnxqid=params;
		writerPrint(service.getExamRoundByXnxq(xnxqid,getBspInfo()));
	}
	
	//获取等级
	public void grade() {
		String userType=getBspInfo().getUserType();//"052A000A3B6C43D785D17B5E54F28F6F";
		String dwid=getBspInfo().getOrgan().getOrganCode();
		writerPrint(service.getGrade(userType,dwid));
	}	
	
	//根据参考单位code，获取等级
	public void gradeBySchool(){
		writerPrint(service.getgradeBySchool(params));
	}
	
	public void njBySchool(){
		writerPrint(service.getnjBySchool(params));
	}
	
	//获取课程
	public void classByGrade(){
		HttpServletRequest request = getRequest();
		String schoolId = request.getParameter("schoolId")==null?"":request.getParameter("schoolId").toString();
		writerPrint(service.getClassByGrade(params,schoolId));
	}
	
	//参考单位用户获取课程
	public void classByGradeAndSchool(){
		String schoolId=getSchoolid().replaceAll("'", "");
		writerPrint(service.getClassByGrade(params,schoolId));
	}
	
	//获取考试时长
	public void timeLength() {
		writerPrint(service.getTimeLength());
	}
	//获取登陆参考单位所属组考单位
	public void jyj(){
		//根据上级组考单位获取县级组考单位
		writerPrint(service.getJyj(params,getBspInfo()));
	}
	//获取参考单位下拉
	public void school(){
		writerPrint(service.getSchool(params,getBspInfo()));
	}
	//获取登陆参考单位所属组考单位的主管单位
	public void sjjyj(){
		/*需要先判断登录的权限为参考单位或者组考单位，如果为参考单位则获取参考单位所属组考单位
		 * 如果为省级组考单位，则获取所有市级组考单位，如果为市级组考单位，则获取当前市级组考单位信息
		 */
		writerPrint(service.getSjjyj(getBspInfo()));
	}
	
	//获取考点名称
	public void kaoDianMc(){
		writerPrint(service.kaoDianMc(params,getBspInfo()));
	}
	//获取教学楼
	public void jiaoXueLou(){
		String schoolid=getSchoolid();
		if(!StringUtil.isNotNullOrEmpty(params) || schoolid.substring(1,schoolid.length()-1).equals(params)){
			if(schoolid.contains(",")){
				schoolid="''";
			}
			params=null;
		}
		writerPrint(service.jiaoXueLou(params,schoolid));
	}	
	//获取考场
	public void examRoom(){
		writerPrint(service.examRoom(params));
	}	
	//根据考试轮次获取考试科目
	public void getKskmBykslc(){
		writerPrint(service.getKskmBykslc(params));
	}	
	//年度数据加载
	public void getXnxqListPage() {
		writerPrint(service.getXnxqListPage());
	}
	//组织机构树加载
	/**
	 * type的设置情况：type=0不获取参考单位数据，只选择组考单位
	 * 				  type=1单选
	 *                不设置type参考单位组考单位复选
	 * 					
	 */
	public void getOrganTree() {
		String type = getRequest().getParameter("type")==null?"":getRequest().getParameter("type").toString();
		String paramOrganCode = getRequest().getParameter("organCode")==null?"":getRequest().getParameter("organCode").toString();
		String organCode =  getBspInfo().getOrgan().getOrganCode();
		
		while (organCode.indexOf("00") > 0) {
			organCode = organCode.replaceAll("00", "");
		}
		if(paramOrganCode != null && !"".equals(paramOrganCode)) {
			organCode = paramOrganCode;
		}
		List<Map<String, String>> list = (List<Map<String, String>>) service.getOrganTree(organCode,type);
		List<OrganTree> organTreeList = new ArrayList<OrganTree>();
		for (int i = 0; i < list.size(); i++) {
			String organCode_m = list.get(i).get("REGION_CODE");
			if(getBspInfo().getOrgan().getOrganCode().equals(organCode_m)) {
				OrganTree organTree = new OrganTree();
				if(!"1".equals(type))organTree.setChecked("false");
				organTree.setText(list.get(i).get("REGION_EDU"));
				organTree.setId(organCode_m);
				List<OrganTree> childrenList = Tools.packageOrganTree(list, list.get(i).get("REGION_CODE"),type);
				if(childrenList == null || childrenList.size() <= 0) {
					organTree.setLeaf("true");
				}
				organTree.setChildren(childrenList);
				organTreeList.add(organTree);
			}
		}
		try {
			getResponse().getWriter().write(new Gson().toJson(organTreeList).replaceAll("\"", "'").replaceAll("'false'", "false").replaceAll("'true'", "true"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//增加人： 包敏   增加时间: 2018-05-17  增加原因： 获取考点信息表
	//组考考点树加载
		/**
		 * type的设置情况：type=0不获取参考单位数据，只选择组考单位
		 * 				  type=1单选
		 *                不设置type参考单位组考单位复选
		 * 					
		 */
		public void getPointTree() {
			String type = getRequest().getParameter("type")==null?"":getRequest().getParameter("type").toString();
			String paramOrganCode = getRequest().getParameter("organCode")==null?"":getRequest().getParameter("organCode").toString();
			String organCode =  getBspInfo().getOrgan().getOrganCode();
			
			//while (organCode.indexOf("00") > 0) {
			//	organCode = organCode.replaceAll("00", "");
			//}
			if(paramOrganCode != null && !"".equals(paramOrganCode)) {
				organCode = paramOrganCode;
			}
			List<Map<String, String>> list = (List<Map<String, String>>) service.getPointTree(organCode);
			List<OrganTree> organTreeList = new ArrayList<OrganTree>();
			for (int i = 0; i < list.size(); i++) {
				String organCode_m = list.get(i).get("REGION_CODE");
				System.out.println(getBspInfo().getOrgan().getOrganCode());
				if(getBspInfo().getOrgan().getOrganCode().equals(organCode_m)) {
					OrganTree organTree = new OrganTree();
					if(!"1".equals(type))organTree.setChecked("false");
					organTree.setText(list.get(i).get("REGION_EDU"));
					organTree.setId(organCode_m);
					List<OrganTree> childrenList = Tools.packageOrganTree(list, list.get(i).get("REGION_CODE"),type);
					if(childrenList == null || childrenList.size() <= 0) {
						organTree.setLeaf("true");
					}
					organTree.setChildren(childrenList);
					organTreeList.add(organTree);
				}
			}
			try {
				getResponse().getWriter().write(new Gson().toJson(organTreeList).replaceAll("\"", "'").replaceAll("'false'", "false").replaceAll("'true'", "true"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//增加人： 包敏   增加时间: 2018-05-17  增加原因： 获取考点信息表
	
	//获取等级课程树
	public void getGradeClassTree() {
		String type = getRequest().getParameter("type")==null?"":getRequest().getParameter("type").toString();
		String paramSchoolCode = getRequest().getParameter("schoolCode");
		String organCode =  getBspInfo().getOrgan().getOrganCode();
		
		if(paramSchoolCode != null && !"".equals(paramSchoolCode)) {
			organCode = paramSchoolCode;
		}
		List<Map<String, String>> list = (List<Map<String, String>>) service.getGradeClassTree(organCode,getBspInfo());
		List<OrganTree> organTreeList = new ArrayList<OrganTree>();
		for (int i = 0; i < list.size(); i++) {
			String organCode_m = list.get(i).get("CODE");
			String codetype = list.get(i).get("CODETYPE");
			if (("1".equals(getBspInfo().getUserType())&&"0".equals(codetype))||
				("2".equals(getBspInfo().getUserType())&&"1".equals(codetype))||
				("3".equals(getBspInfo().getUserType())&&"2".equals(codetype))) {	
				OrganTree organTree = new OrganTree();
				if(!"0".equals(type)) organTree.setChecked("false");
				organTree.setText(list.get(i).get("NAME"));
				organTree.setId(organCode_m);
				List<OrganTree> childrenList = Tools.packageGradeClassTree(list, list.get(i).get("CODE"),type);
				if(childrenList == null || childrenList.size() <= 0) {
					organTree.setLeaf("true");
				}
				organTree.setChildren(childrenList);
				organTreeList.add(organTree);
			}
		}
		try {
			getResponse().getWriter().write(new Gson().toJson(organTreeList).replaceAll("\"", "'").replaceAll("'false'", "false").replaceAll("'true'", "true"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//获取学籍状态下拉
	public void getXjzt() {
		String state = getRequest().getParameter("state");//状态，0为所有状态，1为学籍状态为有，2为学籍状态转变成无
		writerPrint(service.getXjzt(state));
	}	
	public void getKsKm(){
		writerPrint(service.getKsKm(params));
	}
	public void getNjFromEnum() {
		writerPrint(service.getNjFromEnum());
	}
	//获取所有课程信息
	public void getKeCheng(){
		writerPrint(service.getKeCheng());
	}	
	//根据登录参考单位获取课程信息
	public void getKeChengBySchool(){
		writerPrint(service.getKeChengBySchool(getBspInfo()));
	}
	//获取所有角色信息
	public void getRole(){
		System.out.println("------");
		writerPrint(service.getRole());
	}
	/**
	 * 获取树菜单，登陆组考单位下的参考单位等级课程
	 * 不显示课程，设置：type=5
	 */
	public void getOrganNjBjTree() {
		String type = getRequest().getParameter("type")==null?"":getRequest().getParameter("type").toString();
		writerPrint(service.getOrganNjBjTree(type,getBspInfo()));
	}
	
	//获取结业类型
	public void jieye(){
		writerPrint(service.getJieye());
	}
	//获取毕业年年度
	public void bynd(){ 
		writerPrint(service.bynd());
	}	
	//获取性别
	public void sys_enum_xb(){
		writerPrint(service.sysEnumXb());
	}
	//获取等级
	public void sys_enum_dj(){
		writerPrint(service.sysEnumDj());
	}

}
