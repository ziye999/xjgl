package com.jiajie.action.registration;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.service.registration.ExamReviewService;

@SuppressWarnings("serial")
public class ExamReviewAction extends BaseAction{
	
	@Autowired
	private ExamReviewService reviewService;
	
	private String xn;
	private String mc;
	private String ksid;
	private String lcid;
	private String xx;
	private String nj;
	private String bj;
	private String xjh;	
	private String score;
	private String timelen;	
	private String start1;
	private String start2;
	private String start3;
	private String start4;
	private String start5;
	private String start6;
	
	public void getListPage(){
		writerPrint(reviewService.getList(xn,mc,getBspInfo()));
	}
	//多选考生名单审核
	public void updateShxs() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = reviewService.updateShxs(ksid);
		response.getWriter().print(new Gson().toJson(mb));
	}
	//多选补报学生审核
	public void updateBb() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = reviewService.updateBb(ksid);
		response.getWriter().print(new Gson().toJson(mb));
	}
	//多选删除学生审核
	public void updateDl() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = reviewService.updateDl(ksid);
		response.getWriter().print(new Gson().toJson(mb));
	}
	//自动生成考试批次
	public void updateSc() throws IOException{
		writerPrint(reviewService.updateScEaxm(lcid,score,timelen,start1,start2,start3,start4,start5,start6));
	}
	//补报重新生成考试批次
	public void updateScB() throws IOException{
		writerPrint(reviewService.updateScBEaxm(lcid,score,timelen,start1,start2,start3,start4,start5,start6));
	}
	//所有考生名单校验
	public void updateAllCf() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = reviewService.updateCFEaxminee();
		response.getWriter().print(new Gson().toJson(mb));
	}
	//重复考生照片清除
	public void updateAllZp() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo");
		MsgBean mb = reviewService.updateZPEaxminee(distPath);
		response.getWriter().print(new Gson().toJson(mb));
	}
	//补报考生安排
	public void updateAllBb() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		String distPath = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo");
		MsgBean mb = reviewService.updateBBEaxminee();
		response.getWriter().print(new Gson().toJson(mb));
	}
	//所有考生名单审核
	public void updateAll() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = reviewService.updateSHEaxminee(lcid);
		response.getWriter().print(new Gson().toJson(mb));
	}
	//取消所有考生名单审核
	public void updateAllno() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = reviewService.updateSHEaxmineeNo(lcid);
		response.getWriter().print(new Gson().toJson(mb));
	}
	//所有补报考生审核
	public void updateBB() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = reviewService.updateBBEaxminee(lcid);
		response.getWriter().print(new Gson().toJson(mb));
	}
	//所有删除考生审核
	public void updateDL() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = reviewService.updateDLEaxminee(lcid);
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	//发布
	public void fbZT() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = reviewService.fbZT(lcid);
		response.getWriter().print(new Gson().toJson(mb));
	}
	
	public void fbCJ() throws IOException{
		HttpServletResponse response= getResponse();
		response.setContentType("text/html;charset=utf-8");
		MsgBean mb = reviewService.fbCJ(lcid);
		response.getWriter().print(new Gson().toJson(mb));
	}
			
	public String getXn() {
		return xn;
	}

	public void setXn(String xn) {
		this.xn = xn;
	}

	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getXx() {
		return xx;
	}

	public void setXx(String xx) {
		this.xx = xx;
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

	public String getKsid() {
		return ksid;
	}

	public void setKsid(String ksid) {
		this.ksid = ksid;
	}

	public String getXjh() {
		return xjh;
	}

	public void setXjh(String xjh) {
		this.xjh = xjh;
	}
	
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	public String getTimelen() {
		return timelen;
	}

	public void setTimelen(String timelen) {
		this.timelen = timelen;
	}
	
	public String getStart1() {
		return start1;
	}

	public void setStart1(String start1) {
		this.start1 = start1;
	}
	
	public String getStart2() {
		return start2;
	}

	public void setStart2(String start2) {
		this.start2 = start2;
	}
	
	public String getStart3() {
		return start3;
	}

	public void setStart3(String start3) {
		this.start3 = start3;
	}
	
	public String getStart4() {
		return start4;
	}

	public void setStart4(String start4) {
		this.start4 = start4;
	}
	
	public String getStart5() {
		return start5;
	}

	public void setStart5(String start5) {
		this.start5 = start5;
	}
	
	public String getStart6() {
		return start6;
	}

	public void setStart6(String start6) {
		this.start6 = start6;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
}
