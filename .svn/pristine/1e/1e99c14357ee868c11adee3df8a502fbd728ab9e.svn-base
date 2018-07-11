package com.jiajie.action.examResults;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.service.examResults.ScoresQueryService;
import com.jiajie.util.StringUtil;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class ScoresQueryAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private ScoresQueryService scoresQueryService;
	private String lcId;
	private String xmKhXjh;
	private String schoolId;
	private String nj;
	private String bj;
	private List<Map<String, Object>> list;
	private List<Map<String, Object>> kmList;
	
	public void getKskm() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/text;charset=utf-8");
		PrintWriter out=response.getWriter();
		List<Map<String, Object>> list=scoresQueryService.getKskm(lcId);
		JSONArray array=JSONArray.fromObject(list);		
		out.print(array.toString());
	}	
	public void getUserType() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/text;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.print(getBspInfo().getUserType());
	}
	public void getListPage() throws IOException{
		if(StringUtil.isNotNullOrEmpty(schoolId))
		schoolId="'"+schoolId.replaceAll(",", "','")+"'";
		Map<String , String> map=new HashMap<String, String>();
		map.put("lcId", lcId);
		map.put("schoolId", schoolId);
		map.put("xmKhXjh", xmKhXjh);
		map.put("nj", nj);
		map.put("bj", bj);
		writerPrint(scoresQueryService.getStudentScores(map));
	}
	
	public String printData(){
		if(StringUtil.isNotNullOrEmpty(schoolId))
			schoolId="'"+schoolId.replaceAll(",", "','")+"'";
		Map<String , String> map=new HashMap<String, String>();
		map.put("lcId", lcId);
		map.put("schoolId", schoolId);
		map.put("xmKhXjh", xmKhXjh);
		map.put("nj", nj);
		map.put("bj", bj);
		list=scoresQueryService.printStudentScores(map);
		kmList=scoresQueryService.getKskm(lcId);
		return "printData";
	}
	
	public ScoresQueryService getScoresQueryService() {
		return scoresQueryService;
	}
	@Resource(name="scoresQueryServiceImpl")
	public void setScoresQueryService(ScoresQueryService scoresQueryService) {
		this.scoresQueryService = scoresQueryService;
	}
	public String getLcId() {
		return lcId;
	}
	public void setLcId(String lcId) {
		this.lcId = lcId;
	}
	public String getXmKhXjh() {
		return xmKhXjh;
	}
	public void setXmKhXjh(String xmKhXjh) {
		this.xmKhXjh = xmKhXjh;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getNj() {
		return nj;
	}
	public void setNj(String nj) {
		this.nj = nj;
	}
	public String getBj() {
		return bj;
	}
	public void setBj(String bj) {
		this.bj = bj;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
	public List<Map<String, Object>> getKmList() {
		return kmList;
	}
	public void setKmList(List<Map<String, Object>> kmList) {
		this.kmList = kmList;
	}

}
