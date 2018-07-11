package com.jiajie.action.examArrangement;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.service.core.DropListService;
import com.jiajie.service.examArrangement.DataPrintService;
import com.jiajie.util.BarCode;
import com.jiajie.util.PDFReport;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class DataPrintAction extends BaseAction{
	private Map<String, Object> map=new HashMap<String, Object>();
	private DataPrintService dataPrintService;
	private DropListService dropListService;
	private List<Map<String, Object>> dataList;

	/**
	 * 考试门签
	 * @return
	 * @throws Exception
	 */
	public String kaoshimenqian() throws Exception{
		HttpServletRequest request = getRequest();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();		
		String kmid = request.getParameter("kmid")==null?"":request.getParameter("kmid").toString();
		String kcid = request.getParameter("kcid")==null?"":request.getParameter("kcid").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		List<Map<String, Object>> list=dataPrintService.kaohaomenqian(lcid,kmid,kcid,kdid);
		map.put("list", list);
		request.setAttribute("objDz", dropListService.getUserDz(lcid,kdid));
		return "kaoshimenqian";
	}
	/**
	 * 考号学生信息对照
	 * @return
	 * @throws Exception
	 */
	public String kaohaoduizhao() throws Exception{
		HttpServletRequest request = getRequest();
		String xnxq = request.getParameter("xnxq")==null?"":request.getParameter("xnxq").toString();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
		String kmid = request.getParameter("kmid")==null?"":request.getParameter("kmid").toString();
		String school = request.getParameter("school")==null?"":request.getParameter("school").toString();
		String selecttype = request.getParameter("selecttype")==null?"":request.getParameter("selecttype").toString();
		map.put("list", dataPrintService.kaohaoduizhao(lcid,kmid,school,selecttype));
		request.setAttribute("obj", dataPrintService.kaohaoduizhaobiaotou(xnxq, lcid));
		return "kaohaoduizhao";
	}
	
	/**
	 * 考点监考
	 * @return
	 * @throws Exception
	 */
	public String kaodianjiankao() throws Exception{
		HttpServletRequest request = getRequest();
		String xnxq = request.getParameter("xnxq")==null?"":request.getParameter("xnxq").toString();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		List<Map<String, Object>> list=dataPrintService.kaodianjiankao(lcid,kdid);
		map.put("list", list);
		request.setAttribute("obj", dataPrintService.kaodianfenbubiaotou(xnxq, lcid, kdid));
		return "kaodianjiankao";
	}
	/**
	 * 考场分布表
	 * @return
	 * @throws Exception
	 */
	public String kaodianfenbu() throws Exception{ 
		HttpServletRequest request = getRequest();
		String xnxq = request.getParameter("xnxq")==null?"":request.getParameter("xnxq").toString();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		List<Map<String, Object>> list=dataPrintService.kaodianfenbu(lcid,kdid);
		map.put("list", list); 
		request.setAttribute("obj", dataPrintService.kaodianfenbubiaotou(xnxq, lcid, kdid));
		request.setAttribute("objDz", dropListService.getUserDz(lcid,kdid));
		return "kaodianfenbu";
	}
	/**
	 * 考试日程
	 * @return
	 * @throws Exception
	 */
	public String kaoshiricheng() throws Exception{
		HttpServletRequest request = getRequest();
		String xnxq = request.getParameter("xnxq")==null?"":request.getParameter("xnxq").toString();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid");
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		String school = request.getParameter("school")==null?"":request.getParameter("school");
		String nj = request.getParameter("nj")==null?"":request.getParameter("nj");
		List<Map<String, Object>> list=dataPrintService.kaoshiricheng(lcid,kdid,school,nj,getBspInfo());
		map.put("list", list);
		request.setAttribute("obj", dataPrintService.kaoshirichengbiaotou(xnxq, lcid, school, nj, getBspInfo()));
		request.setAttribute("objDz", dropListService.getUserDz(lcid,kdid));
		return "kaoshiricheng";
	}
 
	
	/**
	 * 获取考生基本信息对照表
	 * @return
	 */
	public String getExamBasicInfo(){
		HttpServletRequest request = getRequest();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();		
		String kmid = request.getParameter("kmid")==null?"":request.getParameter("kmid").toString();
		String kcid = request.getParameter("kcid")==null?"":request.getParameter("kcid").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		PageBean pb=dataPrintService.getExamBasicInfo(lcid,kmid,kcid,kdid);
		dataList = pb.getResultList();
		request.setAttribute("objDz", dropListService.getUserDz(lcid,kdid));
		return "getExamBasicInfo";
	}
	
	/**
	 * 获取准考证信息
	 * @return
	 */
	public String getExamCard(){
		HttpServletRequest request = getRequest();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
		String kmid = request.getParameter("kmid")==null?"":request.getParameter("kmid").toString();
		String kcid = request.getParameter("kcid")==null?"":request.getParameter("kcid").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		String school = request.getParameter("school")==null?"":request.getParameter("school").toString();
		String sfzjh = request.getParameter("sfzjh")==null?"":request.getParameter("sfzjh").toString();
		String xuexiao = request.getParameter("xuexiao")==null?"":request.getParameter("xuexiao").toString();
		PageBean pb=dataPrintService.getExamCard(lcid,kmid,school,kcid,kdid,sfzjh,xuexiao,getBspInfo());
		dataList = pb.getResultList();
		return "getExamCard";
	}
	
	
	
	/**
	 * 导出pdf准考证
	 * @return
	 * @throws Exception 
	 */
	public void getExportPdf() throws Exception{
		JSONObject json = new JSONObject();
		json.put("success", true);  
		HttpServletRequest request = getRequest();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
		String kmid = request.getParameter("kmid")==null?"":request.getParameter("kmid").toString();
		String kcid = request.getParameter("kcid")==null?"":request.getParameter("kcid").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		String school = request.getParameter("school")==null?"":request.getParameter("school").toString();
		String sfzjh = request.getParameter("sfzjh")==null?"":request.getParameter("sfzjh").toString();
		String xuexiao = request.getParameter("xuexiao")==null?"":request.getParameter("xuexiao").toString();
		PageBean pb=dataPrintService.getExamCard(lcid,kmid,school,kcid,kdid,sfzjh,xuexiao,getBspInfo());
		if(pb.getResultList()!=null && pb.getResultList().size()>0){
			String url = "export"+File.separator+"pdf"+File.separator+"temp"+File.separator+new Date().getTime()+".pdf";
			String path = request.getSession().getServletContext().getRealPath("/")+File.separator;
			File f = new File(path+url);
			f.createNewFile();
			new PDFReport(f).generatePDF(pb.getResultList(),path);
			json.put("data", url);
		}else{
			json.put("success",false);
			json.put("msg","没有查询到准考证信息!");
		} 
		getResponse().setCharacterEncoding("UTF-8");  
		getResponse().setContentType("text/html; charset=gbk");
		getResponse().getWriter().print(json);
	}
	
	/**
	 * 获取考生座位信息
	 * @return
	 */
	public String getExamSeatInfo(){
		/*HttpServletRequest request = getRequest();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
		String kcid = request.getParameter("kcid")==null?"":request.getParameter("kcid").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		String kmid = request.getParameter("kmid")==null?"":request.getParameter("kmid").toString();
		String flag = request.getParameter("flag")==null?"":request.getParameter("flag").toString();
		PageBean pb=dataPrintService.getExamBasicInfo(lcid,kmid,kcid,kdid);
		dataList = pb.getResultList();
		if("false".equals(flag)){*/
			return "getExamSeatInfo";
		/*}
		return "getExamSeatInfo_photo";*/
	}
	
	public String getExamDjInfo(){
		HttpServletRequest request = getRequest();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();		
		String kmid = request.getParameter("kmid")==null?"":request.getParameter("kmid").toString();
		String kcid = request.getParameter("kcid")==null?"":request.getParameter("kcid").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		PageBean pb=dataPrintService.getExamDjInfo(lcid,kmid,kcid,kdid);
		dataList = pb.getResultList();
		request.setAttribute("objDz", dropListService.getUserDz(lcid,kdid));
		return "examDjInfo";
	}
	
	public String getExamKsInfo(){
		HttpServletRequest request = getRequest();
		String lcid = request.getParameter("lcid")==null?"":request.getParameter("lcid").toString();
		String kmid = request.getParameter("kmid")==null?"":request.getParameter("kmid").toString();
		String kdid = request.getParameter("kdid")==null?"":request.getParameter("kdid").toString();
		PageBean pb=dataPrintService.getExamKsInfo(lcid,kmid,kdid);
		dataList = pb.getResultList();
		request.setAttribute("objDz", dropListService.getUserDz(lcid,kdid));
		return "examKsInfo";
	}
	
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public DataPrintService getDataPrintService() {
		return dataPrintService;
	}
	@Resource(name="dataPrintServiceImpl")
	public void setDataPrintService(DataPrintService dataPrintService) {
		this.dataPrintService = dataPrintService;
	}
	public DropListService getDropListService() {
		return dropListService;
	}
	@Resource(name="dropListServiceImpl")
	public void setDropListService(DropListService dropListService) {
		this.dropListService = dropListService;
	}
	public List<Map<String, Object>> getDataList() {
		return dataList;
	}
	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

}
