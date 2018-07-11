package com.jiajie.action.exambasic;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.service.exambasic.ExamRoundService;
import com.jiajie.util.bean.MBspInfo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class ExamRoundAction extends BaseAction
{
  private CusKwExamround examround;
  private String startdate;
  private String enddate;
  private String lcids;
  private String lcid;

  @Autowired
  private ExamRoundService service;

  public String getLcid()
  {
    return this.lcid;
  }

  public void setLcid(String lcid) {
    this.lcid = lcid;
  }

  public void getListPage() {
    HttpServletRequest request = getRequest();
    String xnxq = request.getParameter("xn") == null ? "" : request.getParameter("xn").toString();
    PageBean pageBean = this.service.getList(xnxq, getBspInfo());
    writerPrint(pageBean);
  }

  public void loadBuilding() {
    writerPrint(this.service.getExamround(this.examround.getLcid()));
  }

  public void nylcid() {
    String lcid = getRequest().getParameter("jiu");
    String lcid2 = getRequest().getParameter("xin");
    writerPrint(this.service.nylcid(lcid, lcid2));
  }

  public void addBuilding()
  {
    this.examround.setLcid(this.lcid);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      this.examround.setStartdate(sdf.parse(this.startdate));
      this.examround.setEnddate(sdf.parse(this.enddate));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    String usertype = getBspInfo().getUserType();
    if ("".equals(this.examround.getLcid())) {
      this.examround.setLcid(null);
    }
    this.examround.setDwtype(getBspInfo().getUserType());
    writerPrint(this.service.saveOrUpdateExamround(this.examround, usertype));
  }

  public void delBuilding() {
    String schoolid = getSchoolid();
    if (schoolid.contains(",")) {
      schoolid = "''";
    }
    writerPrint(this.service.delExamround(this.lcids, schoolid));
  }

  public CusKwExamround getExamround() {
    return this.examround;
  }

  public void setExamround(CusKwExamround examround) {
    this.examround = examround;
  }

  public String getLcids() {
    return this.lcids;
  }

  public void setLcids(String lcids) {
    this.lcids = lcids;
  }

  public ExamRoundService getService() {
    return this.service;
  }
  public String getStartdate() {
    return this.startdate;
  }

  public void setStartdate(String startdate) {
    this.startdate = startdate;
  }

  public String getEnddate() {
    return this.enddate;
  }

  public void setEnddate(String enddate) {
    this.enddate = enddate;
  }
  public void setService(ExamRoundService service) {
    this.service = service;
  }
}