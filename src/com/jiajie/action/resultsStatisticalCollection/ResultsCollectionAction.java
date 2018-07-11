package com.jiajie.action.resultsStatisticalCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.resultsStatisticalCollection.CusKwExecise;
import com.jiajie.service.resultsStatisticalCollection.ResultsCollectionService;

@SuppressWarnings("serial")
public class ResultsCollectionAction extends BaseAction{

	private File upload;
	private String lcid;
	private String valueStr;
	private String xnxq_id;
	
	@Autowired
	private ResultsCollectionService rcService;
	
	public void getListPage() {
		PageBean pageBean = rcService.getList(getBspInfo().getOrgan().getOrganCode(),getBspInfo().getUserType(),xnxq_id); 
		writerPrint(pageBean);
	}
	
	public void resultsListPage() {
		PageBean pageBean = rcService.getList(lcid); 
		writerPrint(pageBean);
	}
	
	public void importFile() throws IOException{
		MsgBean mb = rcService.importResults(getUserid(), upload, lcid);
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("text/html; charset=gbk");
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public void updateResults() {
		List<CusKwExecise> ckeList = getDTOList(valueStr);
		writerPrint(rcService.updateResults(ckeList));
	}
	
	private List<CusKwExecise> getDTOList(String jsonString){ 
		JSONArray array = JSONArray.fromObject(jsonString); 
		List<CusKwExecise> list = new ArrayList<CusKwExecise>();
		
		for(Iterator iter = array.iterator(); iter.hasNext();){ 
			CusKwExecise cke = new CusKwExecise();
			JSONObject jsonObject = (JSONObject)iter.next(); 
			cke.setXtcjid(jsonObject.get("xtcid").toString());
			Object mf = jsonObject.get("mf");
			if(mf != null){
				cke.setFullscore(Float.valueOf(mf.toString()));
			}
			Object pjf = jsonObject.get("pjf");
			if(pjf != null){
				cke.setEvascore(Float.valueOf(pjf.toString()));
			}
			Object zgf = jsonObject.get("zgf");
			if(zgf != null){
				cke.setHighscore(Float.valueOf(zgf.toString()));
			}
			Object zdf = jsonObject.get("zdf");
			if(zdf != null){
				cke.setLowscore(Float.valueOf(zdf.toString()));
			}
			list.add(cke);
		} 
		return list; 
	} 
	
	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public ResultsCollectionService getRcService() {
		return rcService;
	}

	public void setRcService(ResultsCollectionService rcService) {
		this.rcService = rcService;
	}
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	public String getValueStr() {
		return valueStr;
	}

	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}
	
	public String getXnxq_id() {
		return xnxq_id;
	}

	public void setXnxq_id(String xnxq_id) {
		this.xnxq_id = xnxq_id;
	}
}
