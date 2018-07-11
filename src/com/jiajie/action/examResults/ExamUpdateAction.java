package com.jiajie.action.examResults;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.service.examResults.ExamUpdateService;
import com.jiajie.util.StringUtil;

@SuppressWarnings("serial")
public class ExamUpdateAction extends BaseAction{
	private String lcid;
	private String xnxq_id;
	private String delInfo;
	private String kh;
	private String xjh;
	private String addInfo;
	private String isUpdate;
	private String xuexiao;
	private String xm_kh_xjh;
	private String lc_find;	
	private String kmid;
	private String kdid;
	private String kcid;

	@Autowired
	private ExamUpdateService euService;
	
	public void getListPage() {
		if(StringUtil.isNotNullOrEmpty(xuexiao))
			xuexiao="'"+xuexiao.replaceAll(",", "','")+"'";
		writerPrint(euService.getListPage(lcid,xuexiao,xm_kh_xjh,lc_find,kmid,kdid,kcid));
	}
	
	public void getSubject(){
		writerPrint(euService.getSubject(lcid,lc_find));
	}
	
	public void submitJkCj(){
		writerPrint(euService.submitJkCj(delInfo,lcid,lc_find,getUserid()));
	}
	
	public void submitXk(){
		writerPrint(euService.submitXk(delInfo,lcid,lc_find,getUserid()));
	}
	
	public void deleteStuScore(){
		writerPrint(euService.deleteStuScore(delInfo,lcid,lc_find));
	}
	
	public void addOrUpdateStuScore(){
		if(isUpdate.equals("Yes")){
			writerPrint(euService.updateStuScore(lcid,kh,xjh,addInfo,lc_find,getUserid()));
		}else{
			writerPrint(euService.addStuScore(lcid,kh,xjh,addInfo,getUserid(),lc_find));
		}
	}
	
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}
	
	public String getXnxq_id() {
		return xnxq_id;
	}

	public void setXnxq_id(String xnxq_id) {
		this.xnxq_id = xnxq_id;
	}

	public ExamUpdateService getEuService() {
		return euService;
	}

	public void setEuService(ExamUpdateService euService) {
		this.euService = euService;
	}
	
	public String getDelInfo() {
		return delInfo;
	}

	public void setDelInfo(String delInfo) {
		this.delInfo = delInfo;
	}
	
	public String getKh() {
		return kh;
	}

	public void setKh(String kh) {
		this.kh = kh;
	}

	public String getXjh() {
		return xjh;
	}

	public void setXjh(String xjh) {
		this.xjh = xjh;
	}

	public String getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
	
	public String getXuexiao() {
		return xuexiao;
	}

	public void setXuexiao(String xuexiao) {
		this.xuexiao = xuexiao;
	}

	public String getXm_kh_xjh() {
		return xm_kh_xjh;
	}

	public void setXm_kh_xjh(String xm_kh_xjh) {
		this.xm_kh_xjh = xm_kh_xjh;
	}
	
	public String getLc_find() {
		return lc_find;
	}

	public void setLc_find(String lc_find) {
		this.lc_find = lc_find;
	}
		
	public String getKmid() {
		return kmid;
	}

	public void setKmid(String kmid) {
		this.kmid = kmid;
	}
	
	public String getKdid() {
		return kdid;
	}

	public void setKdid(String kdid) {
		this.kdid = kdid;
	}
	
	public String getKcid() {
		return kcid;
	}

	public void setKcid(String kcid) {
		this.kcid = kcid;
	}
	
}
