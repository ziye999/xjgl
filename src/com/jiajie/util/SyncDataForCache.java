package com.jiajie.util;

import com.jiajie.service.exambasic.impl.ExamzjServiceImpl;
import com.jiajie.util.bean.KsInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public final class SyncDataForCache
{
  private static final Log log = LogFactory.getLog(SyncDataForCache.class);
  private static SyncDataForCache sdf;

  private SyncDataForCache()
  {
    ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
    int am = 13;
    int pm = 17;
    int ps = 1800;
    if (Constant.isOk()) {
      long t1 = 0L;
      long t2 = 0L;
      Calendar cal = Calendar.getInstance();
      int h = cal.get(11);
      int m = cal.get(12);
      int s = cal.get(13);
      if (h >= am) {
        t1 = (am + 24) * 60 * 60 - h * 60 * 60 - m * 60 - s;
        if (h >= pm)
          t2 = (pm + 24) * 60 * 60 - h * 60 * 60 - m * 60 - s - ps;
        else
          t2 = pm * 60 * 60 - h * 60 * 60 - m * 60 - s - ps;
      }
      else {
        t1 = am * 60 * 60 - h * 60 * 60 - m * 60 - s;
        t2 = pm * 60 * 60 - h * 60 * 60 - m * 60 - s - ps;
      }
      System.out.println("init success ," + t2 + " seconds after will start ");
      log.error("init success ,AM " + t1 + " seconds after will start ");
      log.error("init success ,PM " + t2 + " seconds after will start ");
      service.scheduleAtFixedRate(new SyncDataRunable(), t1, 86400L, TimeUnit.SECONDS);
      service.scheduleAtFixedRate(new SyncDataRunable(), t2, 86400L, TimeUnit.SECONDS);
    }
  }

  public static SyncDataForCache getInstance() {
    if (sdf == null) {
      sdf = new SyncDataForCache();
    }
    return sdf;
  }
  class SyncDataRunable implements Runnable {
    SyncDataRunable() {
    }
    public void run() { String roomid = null;
      String dtsj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
      System.out.println(dtsj);
      org.hibernate.Session session = null;
      SessionFactory sf = null;
      ZipMain zm = new ZipMain();
      ObjectInputStream in = null;
      FileInputStream fi = null;
      String ap = "AM";
      try {
        if (new Date().getHours() > 15) {
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
            for (int i = 0; i < dfs.length; i++) {
              if (!dfs[i].isDirectory())
              {
                continue;
              }
              String kmid = null;
              Map ksStatu = null;
              File[] fs = dfs[i].listFiles();
              for (int j = 0; j < fs.length; j++)
              {
                if (fs[j].getName().startsWith("ksxs_")) {
                  kmid = fs[j].getName().replace("ksxs_", "");
                  fi = new FileInputStream(fs[j]);
                  in = new ObjectInputStream(fi);
                  ksStatu = (Map)in.readObject();
                  fi.close();
                  in.close();
                }
                else if (fs[j].getName().indexOf(".log") > 0) {
                  roomid = fs[j].getName().replace(".log", "");
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
                  SyncDataForCache.log.error(ksinfo.getXsid() + " = " + ksinfo.getKsStatu() + " = " + ksinfo.getPath());
                  eki.assKstoList(ksinfo);
                }
              }

            }

            int threads = 200;
            SyncDataForCache.log.error("renshu++++" + eki.getKss());
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

          if (session != null) {
            List fs = new ArrayList();
            GenerateExcel ge = new GenerateExcel();
            Map map = ge.getExamKdksInfo(session);
            Map map2 = ge.getExamProblemInfo(session);
            if (((Boolean)map.get("success")).booleanValue()) {
              fs.add(map.get("data").toString());
            }
            if (((Boolean)map2.get("success")).booleanValue()) {
              fs.add(map2.get("data").toString());
            }

            javax.mail.Session se = EmailUtil.getSession();
            Message msg = new MimeMessage(se);
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            String title = sdf.format(new Date()) + "统计数据报告";
            msg.setSubject(title);
            Multipart mainPart = new MimeMultipart();

            BodyPart html = new MimeBodyPart();

            html.setContent(title, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);

            for (int i = 0; i < fs.size(); i++) {
              MimeBodyPart mdp = new MimeBodyPart();

              FileDataSource fds = new FileDataSource((String)fs.get(i));

              mdp.setDataHandler(new DataHandler(fds));

              mdp.setFileName(fds.getName());
              mainPart.addBodyPart(mdp);
            }

            msg.setContent(mainPart);
            EmailUtil.SendMail(se, msg);
          }
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
        ExamKsInfo.isDone();
      }
    }
  }
}