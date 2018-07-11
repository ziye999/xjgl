package com.jiajie.action.exambasic;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.MsgBean;
import com.jiajie.service.exambasic.ExamzjService;
import com.jiajie.service.exambasic.impl.ExamzjServiceImpl;
import com.jiajie.util.DES;
import com.jiajie.util.ExamKsInfo;
import com.jiajie.util.FileUploadWIthBaiDu;
import com.jiajie.util.SyncData;
import com.jiajie.util.TimeToStr;
import com.jiajie.util.ZipMain;
import com.jiajie.util.bean.KsInfo;
import com.jiajie.util.bean.MBspInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Controller
public class ExamzjAction extends BaseAction
{
  private static final Log log = LogFactory.getLog(ExamzjServiceImpl.class);
  private static final long serialVersionUID = 1L;
  private ExamzjService examzjService;
  private String examround;
  private String course;
  private String pType;
  private String examTime;
  private String examDate;
  private String score;
  private String statu;
  private String kmid;
  private String paperId;
  private File examfile;
  private Integer zjzt;
  private String roomid;
  private String kd;

  public Integer getZjzt()
  {
    return this.zjzt;
  }

  public void setZjzt(Integer zjzt) {
    this.zjzt = zjzt;
  }

  public String getKd() {
    return this.kd;
  }

  public void setKd(String kd) {
    this.kd = kd;
  }

  public String getRoomid() {
    return this.roomid;
  }

  public void setRoomid(String roomid) {
    this.roomid = roomid;
  }

  public File getExamfile() {
    return this.examfile;
  }

  public void setExamfile(File examfile) {
    this.examfile = examfile;
  }

  public String getPaperId() {
    return this.paperId;
  }

  public void setPaperId(String paperId) {
    this.paperId = paperId;
  }

  public String getKmid() {
    return this.kmid;
  }

  public void setKmid(String kmid) {
    this.kmid = kmid;
  }

  public String getPType() {
    return this.pType;
  }

  public void setPType(String pType) {
    this.pType = pType;
  }

  public String getExamTime() {
    return this.examTime;
  }

  public void setExamTime(String examTime) {
    this.examTime = examTime;
  }

  public String getExamDate() {
    return this.examDate;
  }

  public void setExamDate(String examDate) {
    this.examDate = examDate;
  }

  public String getScore() {
    return this.score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getStatu() {
    return this.statu;
  }

  public void setStatu(String statu) {
    this.statu = statu;
  }

  public String getExamround() {
    return this.examround;
  }

  public void setExamround(String examround) {
    this.examround = examround;
  }

  public String getCourse() {
    return this.course;
  }

  public void setCourse(String course) {
    this.course = course;
  }
  @Autowired
  public void setExamzjService(ExamzjService examzjService) {
    this.examzjService = examzjService;
  }

  public void getListPage() {
    writerPrint(this.examzjService.getList(getParamList(), getBspInfo()));
  }

  public void getSysDate() {
    writerPrint(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
  }

  public void downExamPaper() throws IOException {
    try {
      JSONObject result = new JSONObject();
      String de = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
      Date dt = new SimpleDateFormat("yyyy-MM-dd").parse(this.kd);
      Date dn = new SimpleDateFormat("yyyy-MM-dd").parse(de);
      String version = getRequest().getParameter("version");
      boolean flag = false;
      System.out.println("访问的端口是："+getRequest().getServerPort());
      if (getRequest().getServerPort() != 8080) {
        flag = true;
        if (dt.getTime() > dn.getTime()) {
          Calendar cal = Calendar.getInstance();
          if (dt.getTime() - dn.getTime() > 86400000L) {
            result.put("success", "false");
            result.put("msg", "只能下载第二天的试卷!");
            writerPrint(result);
            return;
          }
          if (cal.get(11) < 16) {
            result.put("success", "false");
            result.put("msg", "请在16点45之后下载第二天试卷。");
            writerPrint(result);
            return;
          }if ((cal.get(11) == 16) && (cal.get(12) <= 45)) {
            result.put("success", "false");
            result.put("msg", "请在16点45之后下载第二天试卷。");
            writerPrint(result);
            return;
          }
        } else if (dt.getTime() < dn.getTime()) {
          result.put("success", "false");
          result.put("msg", "不能下载以往试卷!");
          writerPrint(result);
          return;
        }
      }

      Map map = this.examzjService.downExamPaper(this.roomid, this.kd, flag, version);
      if (!((Boolean)map.get("success")).booleanValue()) {
        result.put("success", map.get("success"));
        result.put("msg", map.get("msg"));
      } else {
        result.put("success", map.get("success"));
        result.put("data", map.get("data"));
      }
      System.out.println(result.toString());
      writerPrint(result);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public void getRoomName() {
    String code = getRequest().getParameter("code");
    if (code != null)
      writerPrint(this.examzjService.getRoomName(code));
  }

  public List<String> getParamList()
  {
    List paramList = new ArrayList();
    if ((this.course != null) && (!"".equals(this.course))) {
      paramList.add(" and s.kch = '" + this.course + "' ");
    }
    if ((this.examround != null) && (!"".equals(this.examround))) {
      paramList.add(" and s.lcid = '" + this.examround + "' ");
    }
    if (this.zjzt != null) {
      if (this.zjzt.intValue() != 0) {
        if (this.zjzt.intValue() == 1)
          paramList.add(" and s.zjzt = 1 and s.zjrs = ns.yzjrs ");
        else
          paramList.add(" and s.zjzt <> 0 and s.zjrs <> ns.yzjrs ");
      }
      else {
        paramList.add(" and s.zjzt = " + this.zjzt);
      }
    }
    return paramList;
  }

  public void getKm() {
    writerPrint(this.examzjService.getKm());
  }

  public void saveSetInfo() {
    writerPrint(this.examzjService.saveSetInfo(getSaveInfoList(), getBspInfo()));
  }

  public void getSzInfo() {
    writerPrint(this.examzjService.getSzInfo(this.paperId));
  }

  public void getKmszInfo() {
    writerPrint(this.examzjService.getKmszInfo(this.paperId, this.kmid, getBspInfo()));
  }

  public String previewExamInfo() throws Exception {
    getRequest().setAttribute("examInfo", this.examzjService.getExamInfo(this.paperId));
    return "previewExamInfo";
  }

  public void saveExamPaperInfo() {
    String ksxs = null;
    String room = null;
    String kcid = getRequest().getParameter("kcid");
    if ("test".equals(kcid)) {
      writerPrint("success");
    } else {
      String ap = "AM";
      if (new Date().getHours() > 13) {
        ap = "PM";
      }
      String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
      String lj = ExamzjServiceImpl.class.getResource("").toString().replace("file:/", "").replace("classes/com/jiajie/service/exambasic/impl/", "") + "examPaper" + File.separator + time + ap + File.separator + TimeToStr.getTimeStr();
      if (lj != null) {
        ZipMain zm = new ZipMain();
        try {
        	
         if(this.examfile==null){
        	 writerPrint("不要乱来哦");
        	 return ;
         }
          ZipMain.decryptUnzip(this.examfile.getPath(), lj, "hnjjrjsyb..asdfghjkl");
          File f = new File(lj);
          File[] fs = f.listFiles();
          if (fs.length >= 3) {
            boolean flag1 = false;
            boolean flag2 = false;
            for (int i = 0; i < fs.length; i++) {
              if ((flag1) && (flag2)) {
                break;
              }
              if (fs[i].getName().startsWith("ksxs_")) {
                ksxs = fs[i].getName().replace("ksxs_", "");
                flag1 = true;
              } else if (fs[i].getName().indexOf(".log") > 0) {
                room = fs[i].getName().replace(".log", "");
                flag2 = true;
              }
            }
            if ((ksxs != null) && (room != null)) {
              this.examzjService.saveExamPaperInfo(null, ksxs, room);
              writerPrint("success");
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void saveKmblExamInfo()
  {
    String kmp = getRequest().getParameter("kmMap").replace("\"", "").replace("[", "").replace("]", "");
    String[] kms = kmp.split(",");
    Map kmMap = new HashMap();
    for (String km : kms) {
      kmMap.put(km.split(":")[0], km.split(":")[1]);
    }
    String pms = getRequest().getParameter("pms").replace("\"", "").replace("[", "").replace("]", "");
    String[] params = pms.split(",");
    Object paramMap = new HashMap();
    for (String pm : params) {
      String key = pm.split("_")[0].replace("type", "") + ":" + (String)kmMap.get(pm.split("_")[1]);
      ((Map)paramMap).put(key, getRequest().getParameter(pm));
    }
    writerPrint(this.examzjService.saveKmblExamInfo(this.paperId, (Map)paramMap, getBspInfo()));
  }

  public void getKssjPro() {
    System.out.println(ExamKsInfo.getInstance().getKss());
  }

  public void saveSetExamInfo() {
    Map mapInfo = (Map)this.examzjService.getSzInfo(this.paperId).getData();
    List sttype = (List)mapInfo.get("sttype");
    Map paramMap = new HashMap();
    Map sttypeMap = new HashMap();
    for (int i = 0; i < sttype.size(); i++) {
      sttypeMap.put((String)((Map)sttype.get(i)).get("id"), getRequest().getParameter((String)((Map)sttype.get(i)).get("id")) + "##" + getRequest().getParameter(new StringBuilder(String.valueOf((String)((Map)sttype.get(i)).get("id"))).append("_fz").toString()));
    }
    paramMap.put("sttype", sttypeMap);
    writerPrint(this.examzjService.saveSetExamInfo(this.paperId, paramMap, getBspInfo()));
  }

  public void ksZj() {
    writerPrint(this.examzjService.ksZj(this.paperId, this.kmid, getBspInfo(), getRequest().getSession().getServletContext()));
  }

  public void ksZj2() {
    if (getRequest().getServerPort() == 8080)
      writerPrint(this.examzjService.ksZj2(this.paperId, this.kmid, getBspInfo(), getRequest().getSession().getServletContext()));
  }

  public Map<String, String> getSaveInfoList()
  {
    Map map = new HashMap();
    map.put("kmid", this.kmid);
    map.put("examDate", this.examDate);
    map.put("examTime", this.examTime);
    map.put("score", this.score);
    map.put("statu", this.statu);
    map.put("pType", this.pType);
    map.put("paperId", this.paperId);
    return map;
  }

  public void checkAuthorization() {
    MsgBean msgBean = new MsgBean();
    if ("1".equals(getBspInfo().getUserType()))
      msgBean.setSuccess(false);
    else {
      msgBean.setSuccess(false);
    }
    writerPrint(msgBean);
  }

  public void syncDataB() {
//    if (getRequest().getServerPort() != 8080) {
//      return;
//    }
    String roomid = null;
    String dtsj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    Session session = null;
    SessionFactory sf = null;
    ZipMain zm = new ZipMain();
    ObjectInputStream in = null;
    FileInputStream fi = null;
    String ap = "AM";
    try {
      if (new Date().getHours() > 13) {
        ap = "PM";
      }
      if (ExamKsInfo.isPaperClearFlag()) {
        ExamKsInfo eki = ExamKsInfo.getInstance();
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String lj = ExamzjServiceImpl.class.getResource("").toString().replace("file:/", "").replace("classes/com/jiajie/service/exambasic/impl/", "") + "examPaper" + File.separator + time + ap;
        File df = new File(lj);
        File[] dfs = df.listFiles();
        if (dfs != null) {
          Set set = new HashSet();
          WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
          sf = (SessionFactory)wac.getBean("sessionFactory");
          session = sf.openSession();
          for (int i = dfs.length-1; i >=0; i--) {//  int i = 0; i < dfs.length; i++  zhangxin  edit
            if (!dfs[i].isDirectory())
              continue;
            boolean ksflag = false;
            boolean rmflag = false;
            String kmid = null;
            Map ksStatu = null;
            File[] fs = dfs[i].listFiles();
            for (int j = 0; j < fs.length; j++) {
              if ((ksflag) && (rmflag)) {
                if (ksStatu == null) break;
                break;
              }
              if (fs[j].getName().startsWith("ksxs_")) {
                kmid = fs[j].getName().replace("ksxs_", "");
                String sqlString ="select  *from cus_kw_examschedule where kmid='"+kmid+"'";
                
                List list =session.createSQLQuery(sqlString).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
                
                if(list==null||list.size()==0){
                	System.out.println("file:"+fs[j].getName());
                	continue;
                }
                Map m =(Map)list.get(0);
                fi = new FileInputStream(fs[j]);
                in = new ObjectInputStream(fi);
                ksStatu = (Map)in.readObject();
                fi.close();
                in.close();
                ksflag = true;
              } else if (fs[j].getName().indexOf(".log") > 0) {
                roomid = fs[j].getName().replace(".log", "");
                rmflag = true;
              }
            }
            if ((kmid == null) || (ksStatu == null) || (roomid == null)) {
              continue;
            }
            if (!eki.getRoomkm().contains(roomid + "," + kmid)) {
              eki.getRoomkm().add(roomid + "," + kmid);
              Iterator iter2 = ksStatu.keySet().iterator();
              while (iter2.hasNext()) {
                KsInfo ksinfo = new KsInfo();
                String xsid = (String)iter2.next();
                ksinfo.setXsid(xsid);
                ksinfo.setKmid(kmid);
                ksinfo.setRoomid(roomid);
                ksinfo.setKsStatu((String)ksStatu.get(xsid));
                File f_ks = new File(dfs[i].getPath() + File.separator + xsid + ".ca");
                if ((f_ks.exists()) && (!"0".equals(ksStatu.get(xsid)))) {
                  ksinfo.setPath(f_ks.getPath());
                }
                log.error(ksinfo.getXsid() + " = " + ksinfo.getKsStatu() + " = " + ksinfo.getPath());
                eki.assKstoList(ksinfo);
                
              }
            }

          }
//          int w=0;
//          if(w==0){
//        	  return;
//          }
          int threads = 200;
          log.error("renshu++++" + eki.getKss());
          if (eki.getKss() > 0) {
            if (eki.getKss() / 3 < 200) {
              threads = eki.getKss() / 3 + 1;
            }
            ScheduledExecutorService service = 
              Executors.newScheduledThreadPool(threads);
            for (int i = 0; i < threads; i++) {
              service.schedule(new SyncData(), 1L, TimeUnit.MILLISECONDS);
            }
            while (!eki.isDoneByThreads()) {
              System.out.print("");
            }
            System.out.println(new Date().getTime());
            service.shutdown();
            Iterator iter = eki.getRoomkm().iterator();
            while (iter.hasNext()) {
              String rk = (String)iter.next();
              String key = rk.split(",")[0];
              String km = rk.split(",")[1];
              String rsid = (String)session.createSQLQuery("select rsid from cus_kw_roomsubject where kmid = '" + km + "' and roomid = '" + key + "'").uniqueResult();
              if (rsid != null)
                session.createSQLQuery("update cus_kw_roomsubject set submit_status =1 where rsid ='" + rsid + "'").executeUpdate();
              else {
                session.createSQLQuery("insert into cus_kw_roomsubject(rsid,kmid,roomid,submit_status) values(replace(uuid(),'-',''),'" + km + "','" + key + "'," + 1 + ")").executeUpdate();
              }
            }
          }
        }
        getResponse().getOutputStream().println("ok ...");
      } else {
        getResponse().getOutputStream().println("execting ...");
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session != null) {
        session.close();
      }
      if (sf != null) {
        sf.close();
      }
    }
    ExamKsInfo.isDone();
  }
  public static void main(String[] args) {
	  
	  try {
		  //ZipMain.decryptUnzip("D:\\baiduyundownload\\e18054db70f24b8c80ac48079c7aa692.cf", "D:\\baiduyundownload\\jiajie", "hnjjrjsyb..asdfghjkl");
		  ZipMain.decryptUnzip("D:\\Java\\tomcat\\apache-tomcat-7.0.82\\webapps\\xjgl\\WEB-INF\\examPaper\\2018-03-12PM\\11111\\0c2ca19e2ebe4cb0baddfbb039dad757.cf", "D:\\Java\\tomcat\\apache-tomcat-7.0.82\\webapps\\xjgl\\WEB-INF\\examPaper\\2018-03-12PM\\11111", "hnjjrjsyb..asdfghjkl");
		  
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}

//	 String string="Rt/RdBEplbv+lM5VMzYuVH20pC3dnOGHTSD7HVeH9GYFShhvsQ+UL3TxHyyhuFT9llMIGIATMXp4\r\nF9BdP8s4yx5h9FMSp4zY87DrNw7ssUj+CudMPxHG34P0k22PjnWzm0+Yq5zigr95JbkSYyXD9QNj\r\nguBxq1wsJBZTG5P1Kz/RLOYp4gUkMfizEdQ9yQNjD8aln/cdiVbbPxGzSqOXoFBhjhaMj3lSAK2G\r\naKxsrIUPScBt7Yoesc2Z06pfWeUYnK4NGzWGZBVszts0OBUc+2CxwznedbLOO2BHGzllcm93IFVy\r\nMaD5QbI8cxVJDinruRt+zB1HebN671g/pTqD21NkCqecHh7Bpb73Ovfq1JcebMMYbr40ce2xj2RK\r\nNxdWoQ/N3r3pRW9ghyljOeKy9rs5s+xjoc+ykKz84wKqLEViR71Nrc0kQSljyKy6E3yIoCyb9fWp\r\noHUyLXcF4oPxycEvNUtCEsi2QVPERxA11vozzMnFibCrXus7ra8U6CRc8ZGk29D5NlVRQALGEW+g\r\nrEIN7JQVK/dLzk/hw6zuBtKA+ORvK9rmf262eZ53/efoCMWQsT74+3pIaVt4T+yUxDBS29y0ODTB\r\ncTpEZ2XIFC0TMSswlPU/7EpKeM5GTN6nVB3qVXFWPfM7BEN9lYFOmtCpwn9C67Joosi82vUPpm9K\r\nIrcDdZT2x1t26Nss8sW7v5tVUkvPGJp3LlgJy4LWnYQxDFZQJZXoZw3L2y3rA/Aw9c8jItdqGDMK\r\nO+V+jqj4P1m9AcPgJmN1kgNo9+YaRNa67UAjz6eqR94Ko15PF5VwnoIzXYpyX5uFqy5I21YnOFAU\r\nMhAjd8Qwu6KIbSaj2iGmfPmhR2ViP7erP9LNgbXO6rNe2mG4AfedHbm1gQmeEkR7U2motcy3GOiu\r\nU6jfNy++6z5PCq61M0EiztExiQpAQ8A0qlJkFgFQFoHJE4tu3yLoM05jnciZ2KNR7hSLf/8v3RN2\r\nTOywYovCLexSaLajPpC5Y36FrKWmRgUNm3HJFT7xLIktZoSubR3J/ejWmNTgwbJGC1mDnD4WXrWx\r\n3oQrxhk6dHPhkaPkdZil4FryHK7rb/TjnXl5r9iGAEN4/g\u003d\u003d";
//	System.out.println(DES.decodeDES(string));
//	    FileUploadWIthBaiDu baiDu=new FileUploadWIthBaiDu();
//	     String url = baiDu.getUrl("2017-10-24/6c5bffcb885b4c458fabc426173ab94a.cf", 300);
//	     System.out.println(url);
}
}