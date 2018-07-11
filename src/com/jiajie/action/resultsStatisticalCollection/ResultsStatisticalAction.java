package com.jiajie.action.resultsStatisticalCollection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.script.ScriptEngineFactory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiajie.action.BaseAction;
import com.jiajie.service.resultsStatisticalCollection.ResultsStatisticalService;
import com.sun.corba.se.impl.ior.WireObjectKeyTemplate;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class ResultsStatisticalAction extends BaseAction{
	private ResultsStatisticalService resultsStatisticalService;
	private Map<String, Object> map=new HashMap<String,Object>();
	private List<Map<String, Object>> list;
	private String tjlx;
	private String xjnj;
	private String tjfw;
	private String tjkm;
	private String username;
	private String time;
	
	private void  getTimeAndUserName(){
		username=getBspInfo().getUserName();
		Date date=new Date();
		SimpleDateFormat formatter =new SimpleDateFormat ("yyyy-MM-dd  HH:mm:ss");          
		time=formatter.format(date);
	}
	
	public void getLungCi(){
		String schoolid=getSchoolid();
		if(schoolid.contains(",")){
			schoolid=null;
		}		
		writerPrint(resultsStatisticalService.getLungCi(getUserid(),schoolid,getBspInfo()));
	}
	public void getXueXiao(){
		writerPrint(resultsStatisticalService.getXueXiao(getUserid(),getBspInfo()));
	}
	public void getNianJi(){
		writerPrint(resultsStatisticalService.getNianJi(getUserid(),getBspInfo()));
	}
	public void getBanJi(){
		writerPrint(resultsStatisticalService.getBanJi(getUserid(),getBspInfo()));
	}
	
	public String printDataForLC(){
		getTimeAndUserName();
		String usertype=getBspInfo().getUserType();
		String schoolid=getSchoolid();
		String returnStr=null;
		tjfw="'"+tjfw.replace(",", "','")+"'";
		list=resultsStatisticalService.printDataForLC(tjlx, tjfw,usertype,schoolid,xjnj);
		if("01".equals(tjlx))
			returnStr= "printDataForLC_yxl";
		else if("02".equals(tjlx))
			returnStr= "printDataForLC_hgl";
		else if("03".equals(tjlx))
			returnStr= "printDataForLC_pjf";
		else if("04".equals(tjlx))
			returnStr= "printDataForLC_zgf_zdf";
		else if("05".equals(tjlx))
			returnStr= "printDataForLC_pjfbzc";
		else if("06".equals(tjlx))
			returnStr= "printDataForLC_gkksjbqk";
		return returnStr;
	}
	
	public String printDataForXX(){
		getTimeAndUserName();
		String returnStr=null;		
		list=resultsStatisticalService.printDataForXX(tjlx, tjfw,tjkm,xjnj);
		if("01".equals(tjlx))
			returnStr= "printDataForXX_yxl";
		else if("02".equals(tjlx))
			returnStr= "printDataForXX_hgl";
		else if("03".equals(tjlx))
			returnStr= "printDataForXX_pjf";
		else if("04".equals(tjlx))
			returnStr= "printDataForXX_zgf_zdf";
		else if("05".equals(tjlx))
			returnStr= "printDataForXX_pjfbzc";
		else if("06".equals(tjlx))
			returnStr= "printDataForXX_gkksjbqk";
		return returnStr;
	}
	
	public String printDataForNJ(){
		getTimeAndUserName();
		String returnStr=null;
		String schoolid=getSchoolid();
		list=resultsStatisticalService.printDataForNJ(tjlx, tjfw,tjkm,schoolid);
		if("01".equals(tjlx))
			returnStr= "printDataForNJ_yxl";
		else if("02".equals(tjlx))
			returnStr= "printDataForNJ_hgl";
		else if("03".equals(tjlx))
			returnStr= "printDataForNJ_pjf";
		else if("04".equals(tjlx))
			returnStr= "printDataForNJ_zgf_zdf";
		else if("05".equals(tjlx))
			returnStr= "printDataForNJ_pjfbzc";
		else if("06".equals(tjlx))
			returnStr= "printDataForNJ_gkksjbqk";
		return returnStr;
	}
		
	public String printDataForBJ(){
		getTimeAndUserName();
		String returnStr=null;
		String schoolid=getSchoolid();
		list=resultsStatisticalService.printDataForBJ(tjlx, tjfw,tjkm,schoolid);
		if("01".equals(tjlx))
			returnStr= "printDataForBJ_yxl";
		else if("02".equals(tjlx))
			returnStr= "printDataForBJ_hgl";
		else if("03".equals(tjlx))
			returnStr= "printDataForBJ_pjf";
		else if("04".equals(tjlx))
			returnStr= "printDataForBJ_zgf_zdf";
		else if("05".equals(tjlx))
			returnStr= "printDataForBJ_pjfbzc";
		else if("06".equals(tjlx))
			returnStr= "printDataForBJ_gkksjbqk";
		return returnStr;
	}
	
	public ResultsStatisticalService getResultsStatisticalService() {
		return resultsStatisticalService;
	}

	@Resource(name="resultsStatisticalServiceImpl")
	public void setResultsStatisticalService(
			ResultsStatisticalService resultsStatisticalService) {
		this.resultsStatisticalService = resultsStatisticalService;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public String getTjfw() {
		return tjfw;
	}
	public void setTjfw(String tjfw) {
		this.tjfw = tjfw;
	}
	public String getTjlx() {
		return tjlx;
	}
	public void setTjlx(String tjlx) {
		this.tjlx = tjlx;
	}
	public String getTjkm() {
		return tjkm;
	}
	public void setTjkm(String tjkm) {
		if(tjkm!=null && !"".equals(tjkm))
			tjkm="'"+tjkm.replaceAll(",", "','")+"'";
		this.tjkm = tjkm;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getXjnj() {
		return xjnj;
	}

	public void setXjnj(String xjnj) {
		this.xjnj = xjnj;
	}
}
