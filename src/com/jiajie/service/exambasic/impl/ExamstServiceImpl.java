package com.jiajie.service.exambasic.impl;

import cn.hnjj.bean.StudentInfo;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExamst;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.impl.FileUpServiceImpl;
import com.jiajie.service.exambasic.ExamstService;
import com.jiajie.util.ExamZjInfo;
import com.jiajie.util.ExportKsInfo;
import com.jiajie.util.ExportKsTask;
import com.jiajie.util.FileIoUtils;
import com.jiajie.util.FileUploadWIthBaiDu;
import com.jiajie.util.ImportUtil;
import com.jiajie.util.SecUtils;
import com.jiajie.util.StringUtil;
import com.jiajie.util.ZipMain;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.XsInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

@Service("examstService")
public class ExamstServiceImpl extends ServiceBridge
  implements ExamstService
{
	
  private static final Log log = LogFactory.getLog(ExamstServiceImpl.class);

  public PageBean getList(List<String> datalist, MBspInfo mBspInfo) { PageBean bean = null;
    StringBuffer sql = new StringBuffer();
    sql.append("select i.exam_info_id, i.kch as KM,t.mc as TX,t.dm as txid ,i.scores as FZ,i.exam_info_bh as STBH,i.exam_info_zt as SFQY,i.exam_cont as TM,i.exam_answ as DA,i.lrr as lrcode,lr.username as LRR,i.lrsj AS LRSJ,xr.username AS XGR,i.xgsj AS XGSJ ");
    sql.append(" from exam_info i inner join t_qxgl_userinfo lr on i.lrr = lr.usercode");
    sql.append(" inner join zd_sttype t on i.exam_type_id = t.dm left join t_qxgl_userinfo xr on xr.usercode = i.xgr  where 1=1");
    for (int i = 0; i < datalist.size(); i++) {
      sql.append((String)datalist.get(i));
    }
    bean = getSQLPageBean(sql.toString());
    for (int i = 0; i < bean.getResultList().size(); i++) {
      Map map = (Map)bean.getResultList().get(i);
      map.put("DA", SecUtils.decode((String)map.get("DA")));
      map.put("TM", SecUtils.decode((String)map.get("TM")));
    }
    return bean; }

  public void delSts(String sts)
  {
    getSession().createSQLQuery("delete from exam_info_opt where exam_info_id in (" + sts + ")").executeUpdate();
    getSession().createSQLQuery("delete from exam_info where exam_info_id in (" + sts + ")").executeUpdate();
  }

  public List<Map<String, String>> getTx() {
    return getSession().createSQLQuery("select mc as text ,dm as value from zd_sttype  ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
  }

  public List<Map<String, String>> getXz() {
    return getSession().createSQLQuery("select mc as text ,dm as value from zd_stxz  ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
  }

  public List<Map<String, String>> getNd() {
    return getSession().createSQLQuery("select mc as text ,dm as value from zd_DEGREE   ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
  }

  public MsgBean optExamst(CusKwExamst examst, List<String> li, MBspInfo bspInfo) {
    try {
      boolean flag = true;
      if ((examst.getExam_info_id() != null) && (!"".equals(examst.getExam_info_id()))) {
        String count = getSession().createSQLQuery("select count(1) from exam_info where exam_info_id != '" + examst.getExam_info_id() + "' and exam_info_bh = '" + examst.getExam_info_bh() + "'").uniqueResult().toString();
        if (!"0".equals(count)) {
          flag = false;
        } else {
          String et = getSession().createSQLQuery("select mc from zd_sttype where dm = '" + examst.getExam_type_id() + "'").uniqueResult().toString();
          if (("判断题".equals(et)) && 
            (!"Y".equals(examst.getExam_answ())) && (!"N".equals(examst.getExam_answ()))) {
            getMsgBean(false, "判断题答案只能是Y或N!");
            return this.MsgBean;
          }

          examst.setXgr(bspInfo.getUserId());
          examst.setXgsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
          Map m = (Map)getSession().createSQLQuery("select lrr ,lrsj from exam_info where exam_info_id = '" + examst.getExam_info_id() + "'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
          examst.setLrr((String)m.get("lrr"));
          examst.setLrsj((String)m.get("lrsj"));
          update(examst);
        }
      } else {
        String count = getSession().createSQLQuery("select count(1) from exam_info where exam_info_bh = '" + examst.getExam_info_bh() + "'").uniqueResult().toString();
        if (!"0".equals(count)) {
          flag = false;
        } else {
          examst.setLrr(bspInfo.getUserId());
          examst.setLrsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
          save(examst);
        }
      }
      if (!flag) {
        getMsgBean(false, "试题编号已经存在!");
      } else {
        int chr = 65;
        for (int i = 0; i < li.size(); i++) {
          StringBuffer sbsql = new StringBuffer();
          Object examOptId = getSession().createSQLQuery("select exam_opt_id from exam_info_opt where exam_info_id = '" + examst.getExam_info_id() + "' and exam_bh = '" + (char)chr + "'").uniqueResult();
          if (examOptId == null) {
            sbsql.append("insert into exam_info_opt(exam_opt_id,exam_info_id,exam_bh,exam_opt_des) values('");
            sbsql.append(StringUtil.getUUID()).append("','").append(examst.getExam_info_id()).append("','");
            sbsql.append((char)chr).append("','").append((String)li.get(i)).append("')");
          } else {
            sbsql.append("update exam_info_opt set exam_opt_des = '");
            sbsql.append((String)li.get(i)).append("' where exam_opt_id = '");
            sbsql.append(examOptId.toString()).append("'");
          }
          getSession().createSQLQuery(sbsql.toString()).executeUpdate();
          chr++;
        }
        getMsgBean(true, MsgBean.SAVE_SUCCESS);
      }
    } catch (Exception e) {
      e.printStackTrace();
      getMsgBean(false, MsgBean.SAVE_ERROR);
    }
    return this.MsgBean;
  }

  public List<String> getOptInfo(String stid) {
    List li = getSession().createSQLQuery("select exam_opt_des from exam_info_opt where exam_info_id = '" + stid + "'  order by exam_bh").list();
    return li;
  }

  public List<Map<String, String>> getXnxq() {
    String sql = "select  concat(xn,'年度 ',case when xq = 1 then '第1季' when xq = 2 then '第2季' end) as text ,concat(xn,',',xq) as value From cus_kw_examsubject group by xn,xq";
    return getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
  }

  public List<Map<String, String>> getKsmc(String examst_xnxq) {
    String sql = "select distinct r.examname as text ,r.lcid as value From cus_kw_examsubject s ,cus_kw_examround r where s.lcid = r.lcid";
    if ((examst_xnxq != null) && (!"".equals(examst_xnxq))) {
      sql = sql + " and concat(s.xn,',',s.xq) ='" + examst_xnxq + "'";
    }
    return getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
  }

  public List<Map<String, String>> getKskm(String examst_ksmc) {
    String sql = "select subjectname as text ,kmid as value from cus_kw_examSubject";
    if ((examst_ksmc != null) && (!"".equals(examst_ksmc))) {
      sql = sql + " where lcid = '" + examst_ksmc + "'";
    }
    return getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
  }

  public List<Map<String, String>> getKm() {
    return getSession().createSQLQuery("select NAME AS text ,NAME AS value From sys_enum_value WHERE DIC_TYPE = 'ZD_KM'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
  }

  public Map<String, Object> paperClear(String dt, String room)
  {
    ObjectOutputStream out = null;
    ObjectOutputStream out2 = null;
    FileOutputStream fos = null;
    FileOutputStream fos2 = null;
    Map result = new HashMap();
    boolean flag = true;

    int paperSum = 0;

    ExamZjInfo.setPaperClearFlag(true);
    boolean roomflag = true;
    String urlstr = "delete from cus_kw_urllist where 1=1  and rq = '" + dt + "'";
    String sqlstr = "select s.kmid ,date_format(examdate,'%Y-%m-%d') as examdate from cus_kw_examsubject s ,cus_kw_examschedule e where s.kmid = e.kmid and date_format(examdate,'%Y-%m-%d') = '" + dt + "'";
    if ((room != null) && (!"".equals(room.trim()))) {
      roomflag = false;
      sqlstr = sqlstr + " and s.lcid in (select lcid From cus_kw_Examroom where roomid = '" + room + "') ";
      urlstr = urlstr + " and roomid = '" + room + "'";
    }

    List pclist = getSession().createSQLQuery(sqlstr).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

    String paperUrl = ExamstServiceImpl.class.getResource("").toString().replace("file:/", "").replace("classes/com/jiajie/service/exambasic/impl/", "") + "exam";

    String fileurl = ExamstServiceImpl.class.getResource("").toString().replace("file:/", "").replace("WEB-INF/classes/com/jiajie/service/exambasic/impl/", "exam");

    Map map = new HashMap();

    for (int i = 0; i < pclist.size(); i++) {
      String kmid = (String)((Map)pclist.get(i)).get("kmid");
      String rq = (String)((Map)pclist.get(i)).get("examdate");

      File pcfile = new File(paperUrl + File.separator + kmid);
      if (pcfile.exists()) {
        File[] sjfiles = pcfile.listFiles();
        paperSum += sjfiles.length;
        log.error("INFO===:" + kmid + " has " + sjfiles.length + " files");
        for (int j = 0; j < sjfiles.length; j++) {
          String xsid = null;

          if (sjfiles[j].getName().indexOf(".cf") > 0)
            xsid = sjfiles[j].getName().replace(".cf", "");
          else if (sjfiles[j].getName().indexOf(".tx") > 0) {
            xsid = sjfiles[j].getName().replace(".tx", "");
          }
          String sfz = (String)getSession().createSQLQuery("select sfzjh from cus_kw_examinee where ksid = '" + xsid + "'").uniqueResult();
          if (sfz != null) {
            String kcid = (String)getSession().createSQLQuery("select kcid from cus_kw_examinee where xjh = 'G" + sfz + "' and kmid = '" + kmid + "'").uniqueResult();
            if (kcid != null) {
              String roomid = (String)getSession().createSQLQuery("select roomid From cus_kw_examroom where kcid = '" + kcid + "'").uniqueResult();
              if (roomid != null) {
                if (roomflag) {
                  String uriId = UUID.randomUUID().toString().replace("-", "");
                  File fd = new File(fileurl + File.separator + roomid + "##" + rq);
                  if (!fd.exists()) {
                    fd.mkdirs();
                  }
                  FileIoUtils.Copy(sjfiles[j].getPath(), fd.getPath() + File.separator + sjfiles[j].getName());
                  map.put(roomid + "##" + rq, uriId);
                }
                else if (room.equals(roomid)) {
                  String uriId = UUID.randomUUID().toString().replace("-", "");
                  File fd = new File(fileurl + File.separator + roomid + "##" + rq);
                  if (!fd.exists()) {
                    fd.mkdirs();
                  }
                  FileIoUtils.Copy(sjfiles[j].getPath(), fd.getPath() + File.separator + sjfiles[j].getName());
                  map.put(roomid + "##" + rq, uriId);
                }
              }
              else {
                flag = false;
                log.error("ERROR+++: no found this roomid,bh is " + xsid + " ,kcid is" + kcid);
              }
            } else {
              flag = false;
              log.error("ERROR+++: no found this kcid,bh is " + xsid + " ,kmid is" + kmid);
            }
          }
          else {
            flag = false;
            log.error("ERROR+++: no found this student,bh is " + xsid);
          }
        }
      }
      else {
        flag = false;
        log.error("ERROR+++:" + kmid + " no exists!");
      }
    }

    Iterator iter = map.keySet().iterator();

    getSession().createSQLQuery(urlstr).executeUpdate();
    getSession().flush();
    StringBuilder sql = new StringBuilder();
    while (iter.hasNext()) {
      String key = (String)iter.next();
      String roomid = key.split("##")[0];
      String rq = key.split("##")[1];
      if (dt.equals(rq)) {
        String uriId = (String)map.get(key);

        List list = getSession().createSQLQuery("select er.kcid,er.lcid from cus_kw_examroom er ,cus_kw_examround r where r.lcid = er.LCID and roomid = '" + roomid + "' and r.lcid in (select lcid from cus_kw_examschedule where date_format(examdate,'%Y-%m-%d') = '" + rq + "') order by r.startdate desc").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        if ((list == null) || (list.size() == 0)) {
          log.error(roomid + " rq " + rq + " no data!");
        } else {
          List xsidinfo = new ArrayList();
          Map pcid = new TreeMap();
          for (int k = 0; k < list.size(); k++) {
            String lcid = (String)((Map)list.get(k)).get("lcid");
            String kcid = (String)((Map)list.get(k)).get("kcid");
            List kms = getSession().createSQLQuery("select kmid,starttime ,endtime from cus_kw_examschedule where lcid = '" + lcid + "' and date_format(examdate,'%Y-%m-%d') = '" + rq + "' order by starttime ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < kms.size(); i++) {
              try {
                System.out.println(sdf.parse((String)((Map)kms.get(i)).get("starttime")).getTime() + "," + sdf.parse((String)((Map)kms.get(i)).get("endtime")) + " = = =" + roomid + " = = = " + rq);
                pcid.put(sdf.parse((String)((Map)kms.get(i)).get("starttime")).getTime() + "," + sdf.parse((String)((Map)kms.get(i)).get("endtime")).getTime(), (String)((Map)kms.get(i)).get("kmid"));
                sql.delete(0, sql.length());
                sql.append("select '").append((String)((Map)kms.get(i)).get("starttime")).append("' as starttime ,'").append((String)((Map)kms.get(i)).get("endtime")).append("' as endtime, '").append((String)((Map)kms.get(i)).get("kmid")).append("' as kmid ,ksid as xsid,sfzjh as sfzh,'123456' as pwd,xm as username,kcid,zwh from cus_kw_examinee where concat('G',sfzjh) in (select xh from cus_kw_roomstudent t where kcid = '");
                sql.append(kcid).append("' and kmid = '").append((String)((Map)kms.get(i)).get("kmid")).append("')");
                xsidinfo.addAll(getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
              } catch (ParseException e) {
                flag = false;
                log.error("ERROR+++:km init failed ..." + (String)((Map)kms.get(i)).get("kmid"));
                e.printStackTrace();
              }
            }
          }
          List xsids = new ArrayList();

          String roominfo = roomid + "##" + rq;
          File obj = new File(fileurl + File.separator + roominfo + File.separator + "students.cs");
          for (int i = 0; i < xsidinfo.size(); i++) {
        	StudentInfo s = new StudentInfo();
            s.setPwd(((Map)xsidinfo.get(i)).get("sfzh").toString().substring(((Map)xsidinfo.get(i)).get("sfzh").toString().length() - 6).toUpperCase());
            s.setXsid(((Map)xsidinfo.get(i)).get("xsid").toString());
            s.setSfzh(((Map)xsidinfo.get(i)).get("sfzh").toString());
            s.setXm(((Map)xsidinfo.get(i)).get("username").toString().toUpperCase());
            s.setPcid(((Map)xsidinfo.get(i)).get("kmid").toString());
            s.setKcid(((Map)xsidinfo.get(i)).get("kcid").toString());
            s.setZwh(((Map)xsidinfo.get(i)).get("zwh").toString());
            xsids.add(s);
            System.out.println(s.getSfzh() + " = == = = = " + roomid + "   = = = " + rq);
          }
          try {
            fos = new FileOutputStream(fileurl + File.separator + roominfo + File.separator + "students.cs");
            out = new ObjectOutputStream(fos);
            out.writeObject(xsids);
            fos2 = new FileOutputStream(fileurl + File.separator + roominfo + File.separator + "pcid.cs");
            out2 = new ObjectOutputStream(fos2);
            out2.writeObject(pcid);
          } catch (Exception e) {
            flag = false;
            log.error(roomid + "  rq  " + rq + " pcid students init failed");
            try
            {
              if (fos != null) {
                fos.close();
              }
              if (fos2 != null) {
                fos2.close();
              }
              if (out != null) {
                out.close();
              }
              if (out2 != null)
                out2.close();
            }
            catch (Exception e2) {
              flag = false;
              log.error("ERROR+++:" + roomid + "  rq  " + rq + " pcid students init failed");
            }
          }
          finally
          {
            try
            {
              if (fos != null) {
                fos.close();
              }
              if (fos2 != null) {
                fos2.close();
              }
              if (out != null) {
                out.close();
              }
              if (out2 != null)
                out2.close();
            }
            catch (Exception e2) {
              flag = false;
              log.error("ERROR+++:" + roomid + "  rq  " + rq + " pcid students init failed");
            }
          }
        }
        getSession().createSQLQuery("insert into cus_kw_urllist(uriId,roomid,rq,optdate) values('" + uriId + "','" + roomid + "','" + rq + "',sysdate())").executeUpdate();
      }

    }

    File[] fo = new File(fileurl).listFiles();
    for (int i = 0; i < fo.length; i++) {
      if (fo[i].getName().indexOf(".cf") > 0) {
        fo[i].delete();
      }

    }

    File[] f = new File(fileurl).listFiles();
    FileUploadWIthBaiDu fuwd = new FileUploadWIthBaiDu();
    if ((room != null) && (!"".equals(room.trim()))) {
      String filename = (String)map.get(room + "##" + dt);
      if (filename != null) {
        String uri = fileurl + File.separator + filename + ".cf";
        try {
          ZipMain.encryptZip(fileurl + File.separator + room + "##" + dt, uri, "hnjjrjsyb..asdfghjkl");
          fuwd.run(uri, dt + "/" + filename + ".cf");
        } catch (Exception e) {
          flag = false;
          log.error("ERROR+++:encrypZip failed url is " + fileurl + File.separator + room + "##" + dt);
          e.printStackTrace();
        }
      }
    } else {
      for (int i = 0; i < f.length; i++) {
        try {
          String filename = (String)map.get(f[i].getName());
          if (filename != null) {
            String uri = fileurl + File.separator + filename + ".cf";
            ZipMain.encryptZip(f[i].getPath(), uri, "hnjjrjsyb..asdfghjkl");
            String rq = f[i].getName().split("##")[1];
            if (rq.equals(dt)) {
              System.out.println(f[i].getPath() + ".cf" + "    =    " + rq + File.separator + f[i].getName() + ".cf");
              fuwd.run(uri, rq + "/" + filename + ".cf");
            }
          }
        } catch (Exception e) {
          flag = false;
          log.error("ERROR+++:encrypZip failed url is " + f[i].getPath());
          e.printStackTrace();
        }
      }
    }

    File[] foo = new File(fileurl).listFiles();
    for (int i = 0; i < foo.length; i++) {
      if (foo[i].getName().indexOf(".cf") > 0) {
        foo[i].delete();
      }
    }
    if (flag) {
      log.error("INFO===:成功");
      result.put("info", "success");
    } else {
      log.error("INFO===:失败");
      result.put("info", "failed");
    }
    ExamZjInfo.setPaperClearFlag(false);
    return result;
  }

  public PageBean getDwxs(String sz, String xxmc, String xh) {
    return null;
  }

  public MsgBean exportKsInfo(File file, MBspInfo bspInfo, String lcid)
  {
    MsgBean mb = new MsgBean();
    mb.setSuccess(true);
    String path = FileUpServiceImpl.class.getResource("").toString();
    path = path.substring(6, path.lastIndexOf("xjgl") + 4);
    List list = new ArrayList();
    Workbook rwb = null;
    WritableWorkbook wwb = null;
    WritableSheet ws = null;
    int k = 0;
    StringBuffer sb = new StringBuffer();
    boolean flag2 = true;
    try {
      rwb = Workbook.getWorkbook(file);
      Sheet rs = rwb.getSheet(0);
      int clos = rs.getColumns();
      int rows = rs.getRows();
      int total=0;
      
      HashMap rmap = (HashMap)ImportUtil.checkImportExcelModel(file, "examregistration.xls");
      MsgBean localMsgBean1;
      if (!((Boolean)rmap.get("success")).booleanValue()) {
        mb.setSuccess(false);
        mb.setMsg("您导入的模板格式错误,请下载模板导入");
        return mb;
      }
      if (rows == 1) {
        mb.setSuccess(false);
        mb.setMsg("您导入的模板是空模板,请填充好数据再进行导入");
        return mb;
      }

       clos = ((Integer)rmap.get("col")).intValue();
      List xslist = new ArrayList();
      String key = null;
      ExportKsInfo eki = ExportKsInfo.getInstance();
      for (int i = 1; i < rows; i++) {
        boolean flag = true;
        String xx_jbxx_id = null;
        String zkdwm = null;
        String yzbm = null;
        String xm = rs.getCell(1, i).getContents().trim().replace(" ", "");
        String xb = rs.getCell(3, i).getContents().trim().replace(" ", "");
        String sfzh = rs.getCell(2, i).getContents().trim().replace(" ", "");
        if(sfzh==null|| sfzh.equals("")){
        	continue;
        }
        total++;
        //String ckdwh = rs.getCell(3, i).getContents().trim().replace(" ", "");
        String ckdw = rs.getCell(6, i).getContents().trim().replace(" ", "");
        String gw = rs.getCell(7, i).getContents().trim().replace(" ", "");
        if ((i == 1) && ((sfzh == null) || ("".equals(sfzh)))) {
          mb.setSuccess(false);
          mb.setMsg("第一行数据的学生身份证不能为空");
          return mb;
        }
        if (key == null) {
          key = lcid + "," + sfzh;
          int st = eki.isGoOk(key);
          if (st == 0) {
            mb.setSuccess(false);
            mb.setMsg("当前较多人数导入，请稍后再试!");
            return mb;
          }
          if (st == -1) {
            mb.setSuccess(false);
            mb.setMsg("当前导入的文件,已有人正在导入!");
            return mb;
          }
        }
        XsInfo xs = new XsInfo();
        xs.setSfzjh(sfzh);
        xs.setStuName(xm);
        //xs.setParticipatingUntisCode(ckdwh);
        xs.setParticipatingUntisName(ckdw);
        xs.setStuSex(xb);
        xs.setWorkspace(gw);
        xs.setKsbh(i);
        xslist.add(xs);
      }
      int statu = eki.isGoOk(key, xslist);
      List errXs = null;
      if (statu > 0) {
        ScheduledExecutorService service = 
          Executors.newScheduledThreadPool(statu);
        for (int i = 0; i < statu; i++) {
          service.schedule(new ExportKsTask(key), 1L, 
            TimeUnit.MILLISECONDS);
        }
        while (!eki.getStatuByKey(key)) {
          System.out.print("");
        }
        service.shutdown();

        eki.gobackUesdThreads(statu);
        errXs = eki.getErrorData(key);
        eki.removeTaskByKey(key);
        if ((errXs != null) && (errXs.size() > 0))
          flag2 = false;
      }
      else {
        mb.setSuccess(false);
        if (statu == 0)
          mb.setMsg("当前较多人数导入，请稍后再试!");
        else if (statu == -1) {
          mb.setMsg("当前导入的文件,已有人正在导入!");
        }
        return mb;
      }
      int n = Integer.parseInt(getSession().createSQLQuery("select count(1) from cus_kw_examinee where lcid = '" + lcid + 

"'").uniqueResult().toString());
      getSession().createSQLQuery("update CUS_KW_EXAMROUND set sl = " + n + " where lcid = '" + lcid + "'").executeUpdate();
      String mString="";
      if (flag2) {
        mb.setMsg("全部导入完成!一共导入" + (rows - 1) + "条!");
      } else {
        File f = new File(path + "/export/excel/temporary/" + 
          new Date().getTime() + ".xls");
        wwb = Workbook.createWorkbook(
          f, 
          Workbook.getWorkbook(new File(
          path + "/export/excel/temp/examregistration.xls")));
        ws = wwb.getSheet(0);
        mb.setSuccess(false);
        mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
        WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, 
          UnderlineStyle.NO_UNDERLINE, Colour.RED);
        WritableCellFormat wcfFC = new WritableCellFormat(wfc);
        for (int j = 0; j < errXs.size(); j++) {
          Label l0 = new Label(0, j + 1, (j + 1)+"");
          Label l1 = new Label(1, j + 1, ((XsInfo)errXs.get(j)).getStuName());
          Label l2 = new Label(2, j + 1, ((XsInfo)errXs.get(j)).getSfzjh());
          Label l3 = new Label(3, j + 1, ((XsInfo)errXs.get(j)).getStuSex());
          Label l4 = new Label(4, j + 1, "");
          Label l5 = new Label(5, j + 1, "");
          Label l6 = new Label(6, j + 1, ((XsInfo)errXs.get(j)).getParticipatingUntisName());
          Label l7 = new Label(7, j + 1, ((XsInfo)errXs.get(j)).getWorkspace());//岗位
          Label l8 = new Label(8, j + 1, "");
          Label l9 = new Label(9, j + 1, ((XsInfo)errXs.get(j)).getErrMsg(), wcfFC);
          ws.addCell(l0);
          ws.addCell(l1);
          ws.addCell(l2);
          ws.addCell(l3);
          ws.addCell(l4);
          ws.addCell(l5);
          ws.addCell(l6);
          ws.addCell(l7);
          ws.addCell(l8);
          ws.addCell(l9);
        }
        mb.setMsg("导入完成!共导入" + (rows - 1) + "条!<br>失败" + errXs.size() + "条!<br><font color='red'>确认之后返回整理好的失败记录文件<br>可继续导入</font>");
        wwb.write();
       
      }
    } catch (Exception e) {
    	
      e.printStackTrace();
      return mb;
     
    }
    finally
    {
      try
      {
        rwb.close();
        if (!flag2)
          wwb.close();
      }
      catch (WriteException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return mb;
  }

  public MsgBean ExportKsZkzPdf()
  {
    return null;
  }

public MsgBean exportBkKsInfo(File paramFile, MBspInfo paramMBspInfo,
		String paramString) {
	// TODO Auto-generated method stub
	return null;
}
}