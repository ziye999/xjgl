package com.jiajie.action;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.jiajie.bean.MsgBean;
import com.opensymphony.xwork2.ActionSupport;
import com.jiajie.bean.PageBean;
import com.jiajie.service.BaseService;
import com.jiajie.util.bean.MBspInfo;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String userid = "1669834";
	private BaseService baseService;
	@SuppressWarnings("unused")
	private String schoolid;
	private MBspInfo bspInfo;
	public MBspInfo getBspInfo() {
		if(bspInfo == null) {
			HttpSession session=request.getSession();
			setBspInfo((MBspInfo)request.getSession().getAttribute("mBspInfo"));
		}
		return bspInfo;
	}
	public void setBspInfo(MBspInfo bspInfo) {
		this.bspInfo = bspInfo;
	}
	
	public BaseService getBaseService() {
		return baseService;
	}
	@Resource(name="baseServiceImpl")
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	
	public void writerPrint(Object robj) {
		try {
			
			PrintWriter pw = null;
			System.out.println(robj.toString());
			if(robj==null){
				return;
			};
			if (response!=null){
				response.setCharacterEncoding("utf-8");
				pw = response.getWriter();
				if(robj instanceof MsgBean){//判断左边的对象是否是右边的实例
					//GSON则是谷歌提供的用来解析Json数据的一个Java类库
					// toJson 将bean对象转换为json字符串
					pw.print(new Gson().toJson(robj));
				}
				if(robj instanceof String){
					pw.print(robj);
				}
				// **序列化List**
				if(robj instanceof java.util.List ){
					pw.print(new Gson().toJson(robj));
				}
				if(robj instanceof JSONObject){
					pw.print(new Gson().toJson(robj));
				}
				if(robj instanceof JSONArray){
					pw.print(new Gson().toJson(robj));
				}
				if(robj instanceof PageBean){
					pw.print(new Gson().toJson(robj));
				}			
				pw.flush();			
				pw.close();
			}						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletRequest getRequest() {
		return this.request;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	public HttpServletResponse getResponse() {
		return this.response;
	}
		
	/**
	 * 获取机构id
	 * @param userid 用户id
	 * @return 组考单位id
	 */
	protected String getOrgId(String userid){
		return baseService.getOrgId(userid);	
	}
	public String getUserid() {
		//if(bspInfo == null) {
			userid = ((MBspInfo)request.getSession().getAttribute("mBspInfo")).getUserId();
		//}
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
		//获得参考单位id
		schoolid = getOrgId(userid);
	}

	public String getSchoolid() {
		String schoolid =baseService.getSchoolId(getUserid());
		schoolid="'"+schoolid.replace(",", "','")+"'";
		/*if("2".equals(getBspInfo().getUserType())) {
			schoolid = getBspInfo().getOrgan().getOrganCode();
		}*/
		return schoolid;

	}
	public List<String> getSchoolids() {
		return baseService.getSchoolids(bspInfo);
	}
	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}
}
