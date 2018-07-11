package com.jiajie.action.exambasic;

import cn.hnjj.bean.StudentInfo;
import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.exambasic.CusKwExamst;
import com.jiajie.service.exambasic.ExamstService;
import com.jiajie.service.exambasic.impl.ExamstServiceImpl;
import com.jiajie.util.ExamKsInfo;
import com.jiajie.util.ExamZjInfo;
import com.jiajie.util.bean.MBspInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Controller
public class ExamstAction extends BaseAction
{
  private static final long serialVersionUID = 1L;
  private ExamstService examstService;
  private File upload;
  private String kch;
  private String stbh;
  private String exam_type_id;
  private CusKwExamst examst;
  private String stid;
  private String examst_xnxq;
  private String examst_ksmc;
  private String examst_kskm;

  public File getUpload()
  {
    return this.upload;
  }

  public void setUpload(File upload) {
    this.upload = upload;
  }

  public String getStbh()
  {
    return this.stbh;
  }

  public void setStbh(String stbh) {
    this.stbh = stbh;
  }

  public String getExamst_xnxq() {
    return this.examst_xnxq;
  }

  public void setExamst_xnxq(String examst_xnxq) {
    this.examst_xnxq = examst_xnxq;
  }

  public String getExamst_ksmc() {
    return this.examst_ksmc;
  }

  public void setExamst_ksmc(String examst_ksmc) {
    this.examst_ksmc = examst_ksmc;
  }

  public String getExamst_kskm()
  {
    return this.examst_kskm;
  }

  public void setExamst_kskm(String examst_kskm) {
    this.examst_kskm = examst_kskm;
  }

  public String getStid() {
    return this.stid;
  }

  public void setStid(String stid) {
    this.stid = stid;
  }

  public CusKwExamst getExamst() {
    return this.examst;
  }

  public void setExamst(CusKwExamst examst) {
    this.examst = examst;
  }

  public String getKch() {
    return this.kch;
  }

  public void setKch(String kch) {
    this.kch = kch;
  }

  public String getExam_type_id() {
    return this.exam_type_id;
  }

  public void setExam_type_id(String exam_type_id) {
    this.exam_type_id = exam_type_id;
  }
  @Autowired
  public void setExamstService(ExamstService examstService) {
    this.examstService = examstService;
  }

  public void getListPage() {
    writerPrint(this.examstService.getList(getParamList(), getBspInfo()));
  }

  public void replaceSt() {
    writerPrint(this.examstService.getList(getParamList(), getBspInfo()));
  }

  public List<String> getParamList()
  {
    List paramList = new ArrayList();
    if ((this.kch != null) && (!"".equals(this.kch))) {
      paramList.add(" and i.kch = '" + this.kch + "' ");
    }
    if ((this.stbh != null) && (!"".equals(this.stbh))) {
      paramList.add(" and i.exam_info_bh like '%" + this.stbh + "%' ");
    }

    if ((this.exam_type_id != null) && (!"".equals(this.exam_type_id))) {
      paramList.add(" and i.exam_type_id = '" + this.exam_type_id + "' ");
    }
    return paramList;
  }

  public void delSts() {
    String sts = getRequest().getParameter("sts");
    this.examstService.delSts(sts);
  }

  public void getKskm() {
    writerPrint(this.examstService.getKskm(this.examst_ksmc));
  }

  public void getXnxq() {
    writerPrint(this.examstService.getXnxq());
  }

  public void getKsmc() {
    writerPrint(this.examstService.getKsmc(this.examst_xnxq));
  }

  public void getKm() {
    writerPrint(this.examstService.getKm());
  }

  public void getTx() {
    writerPrint(this.examstService.getTx());
  }

  public void getXz() {
    writerPrint(this.examstService.getXz());
  }

  public void getNd() {
    writerPrint(this.examstService.getNd());
  }

  public void optExamstInfo() {
    int optlen = Integer.parseInt(getRequest().getParameter("optList"));
    List li = new ArrayList();
    if (optlen != 0) {
      optlen = (optlen - 2) / 2;
      for (int i = 97; i < 97 + optlen; i++) {
        li.add(getRequest().getParameter("xx_" + (char)i));
      }
    }
    writerPrint(this.examstService.optExamst(this.examst, li, getBspInfo()));
  }

  public void exportKsInfo() {
    System.out.println("1111111111111111111111111111111111111111111111");
    try {
      MsgBean mb = this.examstService.exportKsInfo(this.upload, getBspInfo(), getRequest().getParameter("lcid"));
      System.out.println("1111111111111111111111111111111111111111111111");
      HttpServletResponse response = getResponse();
      response.setCharacterEncoding("UTF-8");
      response.setContentType("text/html; charset=gbk");
      response.getWriter().print(new Gson().toJson(mb));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
//上传补考考生信息
  public void exportBkKsInfo() {
    MsgBean mb = this.examstService.exportBkKsInfo(this.upload, getBspInfo(), getRequest().getParameter("lcid"));
    HttpServletResponse response = getResponse();
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=gbk");
    try {
      response.getWriter().print(new Gson().toJson(mb));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void getOptInfo() {
    writerPrint(this.examstService.getOptInfo(this.stid));
  }

  public void paperClearByRoom() throws IOException, ParseException {
    FileOutputStream fos = null;
    ObjectOutputStream out = null;
    String dt = getRequest().getParameter("dt");
    String roomid = getRequest().getParameter("roomid");
    if ((dt == null) || ("".equals(dt))) {
      writerPrint("请选择日期");
      return;
    }
    if ((roomid == null) || ("".equals(roomid))) {
      writerPrint("请选择考场");
      return;
    }
    WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
    SessionFactory sf = (SessionFactory)wac.getBean("sessionFactory");
    Session session = sf.openSession();
    List list = session.createSQLQuery("select er.kcid,er.lcid from cus_kw_examroom er ,cus_kw_examround r where r.lcid = er.LCID and roomid = '" + roomid + "' and r.lcid in (select lcid from cus_kw_examschedule where date_format(examdate,'%Y-%m-%d') = '" + dt + "') order by r.startdate desc").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    StringBuffer sql = new StringBuffer();
    String fileurl = ExamstServiceImpl.class.getResource("").toString().replace("file:/", "").replace("WEB-INF/classes/com/jiajie/service/exambasic/impl/", "exam");
    List xsidinfo = new ArrayList();
    Map pcid = new TreeMap();
    for (int k = 0; k < list.size(); k++) {
      String lcid = (String)((Map)list.get(k)).get("lcid");
      String kcid = (String)((Map)list.get(k)).get("kcid");
      List kms = session.createSQLQuery("select kmid,starttime ,endtime from cus_kw_examschedule where lcid = '" + lcid + "' and date_format(examdate,'%Y-%m-%d') = '" + dt + "' order by starttime ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      for (int i = 0; i < kms.size(); i++) {
        System.out.println(sdf.parse((String)((Map)kms.get(i)).get("starttime")).getTime() + "," + sdf.parse((String)((Map)kms.get(i)).get("endtime")) + " = = =" + roomid + " = = = " + dt);
        pcid.put(sdf.parse((String)((Map)kms.get(i)).get("starttime")).getTime() + "," + sdf.parse((String)((Map)kms.get(i)).get("endtime")).getTime(), (String)((Map)kms.get(i)).get("kmid"));
        sql.delete(0, sql.length());
        sql.append("select '").append((String)((Map)kms.get(i)).get("starttime")).append("' as starttime ,'").append((String)((Map)kms.get(i)).get("endtime")).append("' as endtime, '").append((String)((Map)kms.get(i)).get("kmid")).append("' as kmid ,ksid as xsid,sfzjh as sfzh,'123456' as pwd,xm as username from cus_kw_examinee where concat('G',sfzjh) in (select xh from cus_kw_roomstudent t where kcid = '");
        sql.append(kcid).append("' and kmid = '").append((String)((Map)kms.get(i)).get("kmid")).append("')");
        xsidinfo.addAll(session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
      }
    }
    List xsids = new ArrayList();

    String roominfo = roomid + "##" + dt;
    File obj = new File(fileurl + File.separator + roominfo + File.separator + "students.cs");
    for (int i = 0; i < xsidinfo.size(); i++) {
      StudentInfo s = new StudentInfo();
      s.setPwd(((String)((Map)xsidinfo.get(i)).get("sfzh")).substring(((String)((Map)xsidinfo.get(i)).get("sfzh")).length() - 6).toUpperCase());
      s.setXsid((String)((Map)xsidinfo.get(i)).get("xsid"));
      s.setSfzh((String)((Map)xsidinfo.get(i)).get("sfzh"));
//      s.setUsername(((String)((Map)xsidinfo.get(i)).get("username")).toUpperCase());
//      s.setBegindate((String)((Map)xsidinfo.get(i)).get("starttime"));
//      s.setEnddate((String)((Map)xsidinfo.get(i)).get("endtime"));
      s.setPcid((String)((Map)xsidinfo.get(i)).get("kmid"));
      xsids.add(s);
      System.out.println(s.getSfzh() + " = == = = = " + roomid + "   = = = " + dt);
    }
    fos = new FileOutputStream(fileurl + File.separator + roominfo + File.separator + "students.cs");
    out = new ObjectOutputStream(fos);
    out.writeObject(xsids);
  }

  public void paperClear() throws IOException {
    String dt = getRequest().getParameter("dt");
    String room = getRequest().getParameter("room");
    if ((dt == null) || ("".equals(dt))) {
      writerPrint("请选择日期");
      return;
    }
    if (getBspInfo() != null) {
      if ("1".equals(getBspInfo().getUserId())) {
        if (!"8080".equals(getRequest().getServerPort())) {
          writerPrint("Permission denied ");
        }
        else if (ExamZjInfo.isPaperClearFlag())
          writerPrint("paper Clearing ...");
        else {
          writerPrint(this.examstService.paperClear(dt, room).get("info"));
        }
      }
      else
        writerPrint("Permission denied ");
    }
    else
      writerPrint("Permission denied ");
  }

  public void getDwxs()
  {
    String sz = getRequest().getParameter("sszgjyxzdm");
    String xxmc = getRequest().getParameter("xxmc");
    String xh = getRequest().getParameter("xh");
    writerPrint(this.examstService.getDwxs(sz, xxmc, xh));
  }

  public void getKspro() {
    try {
      getResponse().getOutputStream().print(ExamKsInfo.getInstance().getKss());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}