package com.jiajie.service.exambasic.impl;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExamst;
import com.jiajie.bean.exambasic.ExamInfo;
import com.jiajie.bean.exambasic.ExamInfoOpt;
import com.jiajie.filter.DownloadExamFilter;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.exambasic.ExamzjService;
import com.jiajie.util.DES;
import com.jiajie.util.ExamZjInfo;
import com.jiajie.util.FileIoUtils;
import com.jiajie.util.FileUploadWIthBaiDu;
import com.jiajie.util.MD5;
import com.jiajie.util.SecUtils;
import com.jiajie.util.StringUtil;
import com.jiajie.util.ZipMain;
import com.jiajie.util.bean.MBspInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Service("examzjService")
public class ExamzjServiceImpl extends ServiceBridge
  implements ExamzjService
{
  private static final Log log = LogFactory.getLog(ExamzjServiceImpl.class);

  @Transactional(readOnly=true)
  public PageBean getList(List<String> datalist, MBspInfo mBspInfo) { StringBuffer sql = new StringBuffer();
    sql.append("select  r.examname,b.paper_id as paperId,s.kmid,s.xnxq_id as xnxq ,s.kch,s.subjectname ,b.paper_desc as paperDesc,");
    sql.append("case when b.PAPER_TYPE = 1 then '统一组卷' when b.paper_type=0 then '个人个性组卷' end as paperType,b.paper_type as ptype,");
    sql.append("s.timelength as examTime,b.exam_date as examDate,s.score as score,case when b.paper_status = 0 then '失效' when b.paper_status = 1 then '启用' end as statu ,b.PAPER_CRE_USER as creUser,");
    sql.append("b.paper_status as statuValue ,b.paper_cre_date as creDate,b.xgr,b.xgsj,case when s.zjzt = 0 then '未组' when s.zjzt = 1 and s.zjrs = ns.yzjrs then '已组已完成'  when s.zjzt <> 0 and s.zjrs <> ns.yzjrs then '已组未完成' end as zjzt,s.zjrs, ns.yzjrs   from cus_kw_examsubject s inner join (select kmid ,count(1) as yzjrs from cus_kw_examinee group by kmid) ns on  s.kmid=ns.kmid  left join paper_basic b on s.kmid = b.kmid inner join cus_kw_examround r on r.lcid = s.lcid  where 1 = 1 ");
    for (int i = 0; i < datalist.size(); i++) {
      sql.append((String)datalist.get(i));
    }
    sql.append(" order by b.exam_date desc");
    System.out.println(sql.toString());
    return getSQLPageBean(sql.toString()); }

  public void delSts(String sts)
  {
    getSession().createSQLQuery(
      "delete from exam_info where exam_info_id in (" + sts + ")")
      .executeUpdate();
  }
  @Transactional(readOnly=true)
  public List<Map<String, String>> getKm() {
    return getSession()
      .createSQLQuery(
      "select subjectname as text,kmid as value from cus_kw_examsubject group by subjectname,kmid ")
      .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
  }

  public MsgBean optExamst(CusKwExamst examst, MBspInfo bspInfo) {
    if ((examst.getExam_info_id() != null) && 
      (!"".equals(examst.getExam_info_id()))) {
      examst.setXgr(bspInfo.getUserId());
      examst.setXgsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        .format(new Date()));
      Map m = (Map)getSession()
        .createSQLQuery(
        "select lrr ,lrsj from exam_info where exam_info_id = '" + 
        examst.getExam_info_id() + "'")
        .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
        .uniqueResult();
      examst.setLrr((String)m.get("lrr"));
      examst.setLrsj((String)m.get("lrsj"));
    } else {
      examst.setLrr(bspInfo.getUserId());
      examst.setLrsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        .format(new Date()));
    }
    saveOrUpdate(examst);
    getMsgBean(true, MsgBean.SAVE_SUCCESS);
    return this.MsgBean;
  }

  public MsgBean saveSetInfo(Map<String, String> map, MBspInfo mBspInfo)
  {
    String s = getSession()
      .createSQLQuery(
      "select count(1) From cus_kw_examsubject s,cus_kw_examround r  where r.lcid = s.lcid and  kmid = '" + 
      (String)map.get("kmid") + 
      "' and r.startdate <='" + 
      (String)map.get("examDate") + 
      "' and r.enddate >= '" + 
      (String)map.get("examDate") + "'").uniqueResult()
      .toString();
    if ("0".equals(s)) {
      getMsgBean(false, "考试日期小于考试轮次开始时间或者大于结束时间");
    } else {
      StringBuffer sql = new StringBuffer();
      if ((map.get("paperId") != null) && (!"".equals(map.get("paperId")))) {
        sql.append("update paper_basic set PAPER_TYPE = ")
          .append((String)map.get("pType")).append(",EXAM_TIME = ");
        sql.append((String)map.get("examTime")).append(",EXAM_DATE = '")
          .append((String)map.get("examDate"));
        sql.append("',PAPER_VALUE = ").append((String)map.get("score"))
          .append(",").append("PAPER_STATUS = ");
        sql.append((String)map.get("statu")).append(",xgr = '")
          .append(mBspInfo.getUserId())
          .append("',xgsj=sysdate() where paper_id = '");
        sql.append((String)map.get("paperId")).append("'");
      } else {
        sql.append("insert into PAPER_BASIC(PAPER_ID,PAPER_DESC,PAPER_TYPE,KMID,EXAM_TIME,EXAM_DATE,PAPER_VALUE,PAPER_STATUS,");
        sql.append("PAPER_CRE_USER,PAPER_CRE_DATE) values('");
        sql.append(StringUtil.getUUID()).append("',getPaperName('")
          .append((String)map.get("kmid")).append("'),")
          .append((String)map.get("pType"));
        sql.append(",'").append((String)map.get("kmid")).append("','")
          .append((String)map.get("examTime")).append("','")
          .append((String)map.get("examDate"));
        sql.append("',").append((String)map.get("score")).append(",")
          .append((String)map.get("statu")).append(",'")
          .append(mBspInfo.getUserId());
        sql.append("',sysdate())");
      }
      getSession().createSQLQuery(sql.toString()).executeUpdate();
      getMsgBean(true, MsgBean.SAVE_SUCCESS);
    }
    return this.MsgBean;
  }

  public MsgBean getSzInfo(String paperId) {
    String sql_1 = "select t.mc as text,concat('sttype',dm) as id,case when ts is null then 0 else ts end as value,case when t.score is null then 0 else t.score end as t_score from (select * from zd_sttype z left join (select max(scores) as score,exam_type_id as id from exam_info group by exam_type_id) as s on z.dm = s.id) as t left join exam_type e on e.exam_type_id = t.dm and e.paper_id ='" + 
      paperId + "'";

    Map map = new HashMap();
    map.put("sttype", 
      getSession().createSQLQuery(sql_1)
      .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
      .list());

    getMsgBean(true, "成功", map, true);
    return this.MsgBean;
  }

  public MsgBean saveSetExamInfo(String paperId, Map<String, Map<String, String>> paramMap, MBspInfo mBspInfo)
  {
    StringBuffer sbsql = new StringBuffer();
    int n = Integer.parseInt(getSession()
      .createSQLQuery(
      "select count(1) from exam_type where paper_id = '" + 
      paperId + "'").uniqueResult().toString());
    if (n == 0) {
      Iterator iterator = ((Map)paramMap.get("sttype")).keySet()
        .iterator();
      while (iterator.hasNext()) {
        sbsql.delete(0, sbsql.length());
        String key = (String)iterator.next();
        String ts = ((String)((Map)paramMap.get("sttype")).get(key)).split("##")[0];
        String score = ((String)((Map)paramMap.get("sttype")).get(key)).split("##")[1];
        sbsql.append("insert into exam_type(param_type_id,paper_id,exam_type_id,ts,score,cjr,cjsj) values('");
        sbsql.append(StringUtil.getUUID()).append("','")
          .append(paperId).append("','")
          .append(key.replace("sttype", ""));
        sbsql.append("','").append(ts).append("','").append(score)
          .append("','").append(mBspInfo.getUserId());
        sbsql.append("','")
          .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
          .format(new Date())).append("')");
        getSession().createSQLQuery(sbsql.toString()).executeUpdate();
      }

    }
    else
    {
      Iterator iterator = ((Map)paramMap.get("sttype")).keySet()
        .iterator();
      while (iterator.hasNext()) {
        sbsql.delete(0, sbsql.length());
        String key = (String)iterator.next();
        String ts = ((String)((Map)paramMap.get("sttype")).get(key)).split("##")[0];
        String score = ((String)((Map)paramMap.get("sttype")).get(key)).split("##")[1];
        sbsql.append("update exam_type set ts = '").append(ts)
          .append("',score = '").append(score);
        sbsql.append("',xgr = '")
          .append(mBspInfo.getUserId())
          .append("',xgsj = '")
          .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
          .format(new Date()));
        sbsql.append("' where paper_id = '").append(paperId)
          .append("' and exam_type_id = '")
          .append(key.replace("sttype", "")).append("'");
        getSession().createSQLQuery(sbsql.toString()).executeUpdate();
      }

    }

    getSession().createSQLQuery(
      "delete from exam_km where paper_id = '" + paperId + "'")
      .executeUpdate();
    getMsgBean(true);
    return this.MsgBean;
  }

  public MsgBean ksZj(String paperId, String pcid, MBspInfo bspInfo, ServletContext serlvetContext)
  {
    zjByTx(paperId, pcid, bspInfo, serlvetContext);
    return this.MsgBean;
  }

  public MsgBean ksZj2(String paperId, String pcid, MBspInfo bspInfo, ServletContext serlvetContext)
  {
    zjByTx2(paperId, pcid, bspInfo, serlvetContext);
    return this.MsgBean;
  }

  public List<Map<String, Object>> getExamInfo(String paperId) {
    StringBuffer sbsql = new StringBuffer();
    sbsql.append("select e.exam_cont as cont,e.exam_info_id as examInfoId,e.exam_answ as examAnsw,");
    sbsql.append("(select group_concat(concat(exam_bh,':',exam_opt_des,'##')) from (select exam_bh ,exam_opt_des,exam_info_id");
    sbsql.append(" from exam_info_opt order by exam_bh) t where t.exam_info_id = e.exam_info_id )as examOpt");
    sbsql.append(" From paper_detail d,exam_info e where d.exam_info_id = e.exam_info_id and d.paper_id ='");
    sbsql.append(paperId).append("' and xs_jbxx_id is null order by px");
    List liMap = getSession()
      .createSQLQuery(sbsql.toString())
      .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    for (int i = 0; i < liMap.size(); i++) {
      Map map = (Map)liMap.get(i);
      map.put("examAnsw", SecUtils.decode(map.get("examAnsw").toString()));
      map.put("cont", SecUtils.decode(map.get("cont").toString()));
    }
    return liMap;
  }

  public void zjByTxNy(String paperId, MBspInfo bspInfo)
  {
    Map txMap = new HashMap();
    txMap.put(Integer.valueOf(1), "单选题");
    txMap.put(Integer.valueOf(2), "多选题");
    txMap.put(Integer.valueOf(3), "填空题");
    txMap.put(Integer.valueOf(4), "问题题");
    txMap.put(Integer.valueOf(5), "判断题");
    Map nyMap = new HashMap();
    nyMap.put(Integer.valueOf(1), "易");
    nyMap.put(Integer.valueOf(2), "中");
    nyMap.put(Integer.valueOf(3), "难");
    Map logMap = new HashMap();
    List l = new ArrayList();
    List l2 = new ArrayList();
    List exams = new ArrayList();
    String sql = "select round(fz/score) as txs,exam_type_id as tid From exam_type where paper_id ='" + 
      paperId + "' and fz>0";
    String sql2 = "select degree_id as did,percent from exam_ny where  paper_id = '" + 
      paperId + "' and percent >0";
    l = getSession().createSQLQuery(sql)
      .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    l2 = getSession().createSQLQuery(sql2)
      .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    int paperType = Integer.parseInt(getSession()
      .createSQLQuery(
      "select paper_type From paper_basic  where paper_id = '" + 
      paperId + "'").uniqueResult().toString());

    String info = "";
    int zts = 0;
    int zrs = 0;
    getSession().createSQLQuery(
      "delete from paper_detail where paper_id = '" + paperId + "'")
      .executeUpdate();
    for (int i = 0; i < l.size(); i++) {
      String tid = ((Map)l.get(i)).get("tid").toString();
      int txs = (int)Double.parseDouble(((Map)l.get(i)).get("txs").toString());
      for (int j = 0; j < l2.size(); j++) {
        String did = ((Map)l2.get(j)).get("did").toString();
        double precent = ((Double)((Map)l2.get(j)).get("percent")).doubleValue();
        int ts = (int)(txs * precent / 100.0D);
        zts += ts;
        if (paperType == 0)
        {
          List kslist = getSession()
            .createSQLQuery(
            "select  xs.xs_jbxx_id from cus_kw_examinee ei ,zxxs_xs_jbxx xs where ei.sfzjh = xs.sfzjh and ei.lcid = (select k.lcid from cus_kw_examsubject k where k.kmid = (select kmid from paper_basic where paper_id = '" + 
            paperId + "'))").list();
          zrs = kslist.size();

          for (int k = 0; k < kslist.size(); k++) {
            StringBuffer sb = new StringBuffer();

            sb.append(
              "SELECT exam_info_id ,scores FROM exam_info e where kch = (select kch from paper_basic b,cus_kw_examsubject s where s.kmid = b.kmid and paper_id = '")
              .append(paperId);
            sb.append("') and EXAM_TYPE_ID = '").append(tid)
              .append("' and DEGREE_ID = '").append(did);
            sb.append(
              "' and not exists (select 1 from paper_detail where paper_id = '")
              .append(paperId)
              .append("' and exam_info_id = e.exam_info_id and xs_jbxx_id = '");
            sb.append(kslist.get(k).toString())
              .append("')  ORDER BY rand() LIMIT ")
              .append(ts);
            exams = getSession()
              .createSQLQuery(sb.toString())
              .setResultTransformer(
              Transformers.ALIAS_TO_ENTITY_MAP)
              .list();
            for (int n = 0; n < exams.size(); n++) {
              sb.delete(0, sb.length());
              sb.append("insert into PAPER_DETAIL(XH,PAPER_ID,EXAM_INFO_ID,ZT,PX,CJR,CJSJ,xs_jbxx_id,score)values(replace(uuid(),'-',''),'");
              sb.append(paperId).append("','")
                .append(((Map)exams.get(n)).get("exam_info_id"))
                .append("','1','");
              sb.append(n).append("','")
                .append(bspInfo.getUserId())
                .append("',sysdate(),'")
                .append(kslist.get(k).toString())
                .append("','")
                .append(((Map)exams.get(n)).get("scores"))
                .append("')");
              getSession().createSQLQuery(sb.toString())
                .executeUpdate();
            }
            if (exams.size() < ts) {
              info = "题库中题型:<font color='red'>" + 
                (String)txMap.get(Integer.valueOf(Integer.parseInt(tid))) + 
                "</font>;难易度:<font color='red'>" + 
                (String)nyMap.get(Integer.valueOf(Integer.parseInt(did))) + 
                "</font>;的题库量不够,本应组题:<font color='red'>" + 
                ts + ";</font>组题完成:<font color='red'>" + 
                exams.size() + "</font>";
            }
            if (logMap.containsKey(kslist.get(k).toString()))
              logMap.put(kslist.get(k).toString(), 
                Integer.valueOf(exams.size() + 
                ((Integer)logMap.get(kslist.get(k))).intValue()));
            else
              logMap.put(kslist.get(k).toString(), Integer.valueOf(exams.size()));
          }
        }
        else if (paperType == 1) {
          StringBuffer sb = new StringBuffer();

          sb.append(
            "SELECT exam_info_id ,scores FROM exam_info e where kch = (select kch from paper_basic b,cus_kw_examsubject s where s.kmid = b.kmid and paper_id = '")
            .append(paperId);
          sb.append("') and EXAM_TYPE_ID = '").append(tid)
            .append("' and DEGREE_ID = '").append(did);
          sb.append("' ORDER BY rand() LIMIT ").append(ts);
          exams = getSession()
            .createSQLQuery(sb.toString())
            .setResultTransformer(
            Transformers.ALIAS_TO_ENTITY_MAP).list();
          for (int n = 0; n < exams.size(); n++) {
            sb.delete(0, sb.length());
            sb.append("insert into PAPER_DETAIL(XH,PAPER_ID,EXAM_INFO_ID,ZT,PX,CJR,CJSJ)values(replace(uuid(),'-',''),'");
            sb.append(paperId).append("','")
              .append(((Map)exams.get(n)).get("exam_info_id"))
              .append("','1','");
            sb.append(n).append("','").append(bspInfo.getUserId())
              .append("',sysdate())");
            getSession().createSQLQuery(sb.toString())
              .executeUpdate();
          }
          if (exams.size() < ts) {
            info = "题库中题型:<font color='red'>" + 
              (String)txMap.get(Integer.valueOf(Integer.parseInt(tid))) + 
              "</font>;难易度:<font color='red'>" + 
              (String)nyMap.get(Integer.valueOf(Integer.parseInt(did))) + 
              "</font>;的题库量不够,本应组题:<font color='red'>" + ts + 
              ";</font>组题完成:<font color='red'>" + 
              exams.size() + "</font>";
          }
          if (logMap.containsKey("tizj"))
            logMap.put("tizj", Integer.valueOf(exams.size() + ((Integer)logMap.get("tizj")).intValue()));
          else {
            logMap.put("tizj", Integer.valueOf(exams.size()));
          }
        }
      }
    }
    String logInfo = null;
    if (paperType == 0) {
      int zjrs = 0;
      int wwszj = 0;
      Iterator iter = logMap.keySet().iterator();
      boolean flag = true;
      while (iter.hasNext()) {
        String key = (String)iter.next();
        if (((Integer)logMap.get(key)).intValue() == zts) {
          zjrs++;
        } else {
          flag = false;
          wwszj++;
        }
      }
      if (flag)
        logInfo = "个性组卷成功!<br>组卷人数为:<font color='red'>" + zrs + 
          "</font>;每人组题:<font color='red'>" + zts + "</font>";
      else
        logInfo = "<font color='red'>个性组卷未完全!</font><br>组卷人数为:<font color='red'>" + 
          zrs + 
          "</font>;每人应组题:<font color='red'>" + 
          zts + 
          "</font>;<font color='red'>" + 
          zjrs + 
          "</font>人组卷:<font color='red'>" + 
          zts + 
          "</font>;<font color='red'>" + 
          wwszj + 
          "</font>人组卷未满;<font color='red'>" + 
          zts + 
          "</font><br>" + info;
    }
    else if (paperType == 1) {
      if (((Integer)logMap.get("tizj")).intValue() == zts)
        logInfo = "统一组卷成功!<br>共组题:<font color='red'>" + zts + "</font>";
      else {
        logInfo = "<font color='red'>统一组卷未完全!</font><br>应组题:<font color='red'>" + 
          zts + 
          "</font>;组题完成:<font color='red'>" + 
          logMap.get("tizj") + "</font><br>" + info;
      }
    }

    getMsgBean(true, logInfo);
  }

  public MsgBean getKmszInfo(String paperId, String kmid, MBspInfo bspInfo) {
    if ((paperId == null) || ("".equals(paperId))) {
      Object obj = getSession().createSQLQuery(
        "select paper_Id from paper_basic where kmid = '" + kmid + 
        "'").uniqueResult();
      if (obj != null) {
        paperId = obj.toString();
      } else {
        paperId = StringUtil.getUUID();
        Map paperInfo = (Map)getSession()
          .createSQLQuery(
          "select concat(s.xnxq_id,kch) as pd, s.kmid,s.score,s.timelength,date_format(m.examdate,'%Y-%m-%d') as examdate From cus_kw_examsubject s ,cus_kw_examschedule m where m.kmid = s.kmid and s.kmid = '" + 
          kmid + "'")
          .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
          .uniqueResult();
        StringBuffer sb = new StringBuffer();
        sb.append("insert into paper_basic(paper_id,paper_desc,paper_type,kmid,exam_time,exam_date,paper_value,paper_status,paper_cre_user,paper_cre_date) values('");
        sb.append(paperId).append("','");
        sb.append(paperInfo.get("pd")).append("',0,'");
        sb.append(kmid).append("','");

        sb.append(paperInfo.get("timelength")).append("','");
        sb.append(paperInfo.get("examdate")).append("','");
        sb.append(paperInfo.get("score")).append("',1,'");
        sb.append(bspInfo.getUserId()).append("',sysdate())");
        getSession().createSQLQuery(sb.toString()).executeUpdate();
        getSession().flush();
      }
    }

    List txbls = getSession()
      .createSQLQuery(
      "select dm as id,mc,(select max(scores) from exam_info where exam_type_id = id) as score,(select sum(ts) from exam_km where paper_id = '" + 
      paperId + 
      "' and exam_type_id = id) as txts From zd_sttype")
      .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    if ((txbls != null) && (txbls.size() > 0)) {
      Map map = new HashMap();
      map.put("paperId", paperId);
      Map kmMap = null;
      map.put("txbl", txbls);
      List kmnlist = getSession()
        .createSQLQuery(
        "select concat(exam_type_id,'_',kch) as ekid,count(1) as n from exam_info group by kch,exam_type_id")
        .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
        .list();
      Map kch = new HashMap();
      for (int i = 0; i < kmnlist.size(); i++) {
        kch.put(((Map)kmnlist.get(i)).get("ekid").toString(), 
          Integer.valueOf(Integer.parseInt(((Map)kmnlist.get(i)).get("n").toString())));
      }
      map.put("kMap", kch);
      map.put("kch", 
        getSession()
        .createSQLQuery(
        "select kch,exam_type_id as tid from exam_info group by kch,exam_type_id")
        .setResultTransformer(
        Transformers.ALIAS_TO_ENTITY_MAP).list());
      List kmbls = getSession()
        .createSQLQuery(
        "select kmmc,case when ts is null then 0 else ts end as ts,exam_type_id as tid From exam_km where paper_id = '" + 
        paperId + "'")
        .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
        .list();
      getSession()
        .createSQLQuery(
        "select kmmc,case when ts is null then 0 else ts end as ts,exam_type_id as tid From exam_km where paper_id = '" + 
        paperId + "'")
        .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
        .list();
      if (kmbls != null) {
        kmMap = new HashMap();
        for (int i = 0; i < kmbls.size(); i++) {
          kmMap.put(
            (String)((Map)kmbls.get(i)).get("tid") + "_" + 
            (String)((Map)kmbls.get(i)).get("kmmc"), ((Map)kmbls.get(i))
            .get("ts"));
        }
      }
      map.put("kmts", kmMap);
      getMsgBean(true, "成功", map, true);
    }
    return this.MsgBean;
  }

  public MsgBean saveKmblExamInfo(String paperId, Map<String, String> paramMap, MBspInfo bspInfo)
  {
    Iterator iter = paramMap.keySet().iterator();
    StringBuffer sb = new StringBuffer();
    while (iter.hasNext()) {
      String key = (String)iter.next();
      sb.delete(0, sb.length());
      Object obj = getSession().createSQLQuery(
        "select PARAM_KM_ID from exam_km where paper_id = '" + 
        paperId + "' and kmmc = '" + key.split(":")[1] + 
        "' and EXAM_TYPE_ID = '" + key.split(":")[0] + 
        "'").uniqueResult();
      sb.delete(0, sb.length());
      if (obj != null) {
        String kmsetId = obj.toString();
        if ("0".equals(paramMap.get(key))) {
          sb.append("delete from exam_km")
            .append(" where PARAM_KM_ID = '").append(kmsetId)
            .append("'");
        } else {
          sb.append("update exam_km set ts = ")
            .append((String)paramMap.get(key)).append(" ,xgr = '")
            .append(bspInfo.getUserId());
          sb.append("' ,xgsj = sysdate()")
            .append(" where PARAM_KM_ID = '").append(kmsetId)
            .append("'");
        }
        getSession().createSQLQuery(sb.toString()).executeUpdate();
      }
      else if (Integer.parseInt((String)paramMap.get(key)) > 0) {
        sb.append("insert into exam_km(PARAM_KM_ID,PAPER_ID,KMMC,EXAM_TYPE_ID,TS,CJR,CJSJ) values(replace(uuid(),'-',''),'");
        sb.append(paperId).append("','").append(key.split(":")[1])
          .append("','").append(key.split(":")[0])
          .append("',");
        sb.append((String)paramMap.get(key)).append(",'")
          .append(bspInfo.getUserId()).append("',sysdate())");
        getSession().createSQLQuery(sb.toString()).executeUpdate();
      }
    }

    getMsgBean(true);
    return this.MsgBean;
  }

  public void zjByTx2(String paperId, String pcid, MBspInfo bspInfo, ServletContext serlvetContext)
  {
    Session mysession = getSessionFactory().openSession();
    mysession.beginTransaction();
    String kmtype = "1";
    List list2 = mysession.createSQLQuery("select concat(s.xnxq_id,kch) as pd, s.kmid,s.score,s.timelength,date_format(m.examdate,'%Y-%m-%d') as examdate From cus_kw_examsubject s ,cus_kw_examschedule m,(select kmid as skmid from cus_kw_examinee where kmid is not null group by kmid ) t where m.kmid = s.kmid and s.kmid = t.skmid and s.zjzt = 0").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    for (int i = 0; i < list2.size(); i++) {
      Map paperInfo = (Map)list2.get(i);
      String pid = (String)mysession.createSQLQuery("select paper_id from paper_basic where kmid = '" + paperInfo.get("kmid") + "'").uniqueResult();
      if (pid == null) {
        pid = StringUtil.getUUID();
        StringBuffer sb = new StringBuffer();
        sb.append("insert into paper_basic(paper_id,paper_desc,paper_type,kmid,exam_time,exam_date,paper_value,paper_status,paper_cre_user,paper_cre_date) values('");
        sb.append(pid).append("','");
        sb.append(paperInfo.get("pd")).append("',0,'");
        sb.append(paperInfo.get("kmid")).append("','");
        sb.append(paperInfo.get("timelength")).append("','");
        sb.append(paperInfo.get("examdate")).append("','");
        sb.append(paperInfo.get("score")).append("',1,'");
        sb.append(1).append("',sysdate())");
        mysession.createSQLQuery(sb.toString()).executeUpdate();
      }
      if ("1".equals(kmtype))
        kmtype = "2";
      else {
        kmtype = "1";
      }
      mysession.createSQLQuery("delete from exam_KM where paper_id = '" + pid + "'").executeUpdate();
      List km = mysession.createSQLQuery("select param_km_id,exam_type_id,ts,kmmc,cjr From exam_KM_bak WHERE paper_id = '" + kmtype + "'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
      for (int j = 0; j < km.size(); j++) {
        mysession.createSQLQuery("insert into exam_KM(param_km_id,paper_id,exam_type_id,ts,kmmc,cjr,cjsj) values(replace(uuid(),'-',''),'" + pid + "','" + ((Map)km.get(j)).get("exam_type_id") + "'," + ((Map)km.get(j)).get("ts") + ",'" + ((Map)km.get(j)).get("kmmc") + "','" + ((Map)km.get(j)).get("cjr") + "',sysdate())").executeUpdate();
      }
    }
    mysession.getTransaction().commit();
    List pklist = getSession().createSQLQuery("select s.kmid,b.paper_id from cus_kw_examsubject s ,paper_basic b where b.kmid = s.kmid and zjzt = 0").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    Connection conn = null;
    System.out.println();
    ExamZjInfo examinfo = ExamZjInfo.getInstance();
    for (int s = 0; s < pklist.size(); s++) {
      Session session2 = getSessionFactory().openSession();
      session2.beginTransaction();
      try {
        paperId = (String)((Map)pklist.get(s)).get("paper_id");
        pcid = (String)((Map)pklist.get(s)).get("kmid");
        if (examinfo.getStatus().intValue() == 0) {
          getMsgBean(false, "用户【" + examinfo.getUserName() + 
            "】正在组卷,请稍候再进行组卷!");
          return;
        }
        int threads = 100;
        String info = null;
        int kmflag = Integer.parseInt(session2
          .createSQLQuery(
          "select count(1) from exam_km where paper_id  = '" + 
          paperId + "'").uniqueResult().toString());
        if (kmflag == 0) {
          getMsgBean(false, "请先设置科目比例，然后组卷");
          continue;
        }
        List exams = new ArrayList();
        Map kmid = (Map)session2
          .createSQLQuery(
          "select s.kmid,s.lcid,s.subjectname,concat(s.score,'') as score,concat(s.timelength,'') as timelength,es.starttime,es.endtime from cus_kw_examsubject s,cus_kw_examschedule ES where s.kmid = es.kmid and s.kmid = '" + 
          pcid + "'")
          .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
          .uniqueResult();
        if (kmid == null) {
          getMsgBean(false, "未设置考试信息！");
          continue;
        }if ((kmid.get("starttime") == null) || 
          ("null".equals(kmid.get("starttime")))) {
          getMsgBean(false, "未设置考试时间！");
          continue;
        }
        int sjpd = Integer.parseInt(session2
          .createSQLQuery(
          "select count(1) from cus_kw_stuscore where kmid ='" + 
          pcid + "'").uniqueResult().toString());
        if (sjpd > 0) {
          getMsgBean(false, "已经考试并产生成绩,不允许再次组卷");
          continue;
        }

        List kslist = session2
          .createSQLQuery(
          "select xs.xs_jbxx_id from zxxs_xs_jbxx xs where exists (select 1 from cus_kw_examinee where xs_jbxx_id = xs.xs_jbxx_id and kmid = '" + 
          pcid + "')").list();

        int zrs1 = kslist.size();
        log.error("paperId:" + paperId + ":数据库查询到的人数：" + zrs1);
        int paperType = 0;

        if ((kslist == null) || (kslist.size() == 0)) {
          getMsgBean(false, "该批次没有学生");
          continue;
        }

        if (kslist.size() / threads < 3) {
          if (kslist.size() / 3 == 0)
            threads = 1;
          else {
            threads = kslist.size() / 3;
          }
        }

        if (paperType == 0) {
          examinfo.init(bspInfo.getUserName());
          int zts = Integer.parseInt(session2
            .createSQLQuery(
            "select sum(ts) from exam_km where paper_id = '" + 
            paperId + "'").uniqueResult()
            .toString()) * 
            kslist.size();
          Map pamap = new HashMap();
          String lj = ExamzjServiceImpl.class
            .getResource("")
            .toString()
            .replace("file:/", "")
            .replace("classes/com/jiajie/service/exambasic/impl/", 
            "") + 
            "exam" + File.separator + pcid;
          pamap.put("paperId", paperId);
          pamap.put("kmid", kmid);
          pamap.put("userid", bspInfo.getUserId());
          pamap.put("serlvetContext", serlvetContext);
          File dir = new File(lj);
          if (!dir.exists()) {
            dir.mkdir();
          }
          pamap.put("path", lj);
          examinfo.setKslist(kslist);
          List l = session2
            .createSQLQuery(
            "select sum(ts) as txs,exam_type_id as tid,kmmc From exam_km where paper_id ='" + 
            paperId + 
            "' and ts > 0 group by kmmc,exam_type_id")
            .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
            .list();
          pamap.put("exams", l);
          ScheduledExecutorService service = 
            Executors.newScheduledThreadPool(threads);
          for (int i = 0; i < threads; i++) {
            service.schedule(new ExamZjTask(pamap), 1L, 
              TimeUnit.MILLISECONDS);
          }
          service.shutdown();
          int i_i = 0;
          StringBuilder sq = new StringBuilder();
          while (!examinfo.isDoneByThreads()) {
            System.out.print("");
          }
          Map ksmap = examinfo.getAddks();
          Iterator iter = ksmap.keySet().iterator();
          int num = 0;
          int ztts = 0;
          int znum = 0;
          conn = getConnection();
          conn.prepareStatement(
            "delete from paper_detail where paper_id = '" + paperId + 
            "'").executeUpdate();
          while (iter.hasNext()) {
            num++;
            String key = (String)iter.next();
            List list = (List)ksmap.get(key);
            for (int i = 0; i < list.size(); i++) {
              znum++;
              sq.append("(replace(uuid(),'-',''),'").append(paperId)
                .append("','").append(key).append("',")
                .append((String)list.get(i)).append("'1','");
              sq.append(i).append("','").append(bspInfo.getUserId())
                .append("',sysdate()),");
            }
            if (!iter.hasNext()) {
              ztts = list.size();
            }
            if (znum % 1000 == 0) {
              sq.delete(sq.length() - 1, sq.length());
              conn.prepareStatement(
                "insert into PAPER_DETAIL(XH,PAPER_ID,xs_jbxx_id,score,EXAM_INFO_ID,ZT,PX,CJR,CJSJ)values" + 
                sq.toString()).executeUpdate();
              sq.delete(0, sq.length());
            }
            System.out.println(znum);
          }
          System.out.println(sq.length() + "    qqq");
          if (sq.length() > 0) {
            sq.delete(sq.length() - 1, sq.length());
            conn.prepareStatement(
              "insert into PAPER_DETAIL(XH,PAPER_ID,xs_jbxx_id,score,EXAM_INFO_ID,ZT,PX,CJR,CJSJ)values" + 
              sq.toString()).executeUpdate();
            sq.delete(0, sq.length());
            sq = null;
          }
          conn.close();
          int zjzt = 1;
          info = "组卷学生：" + num + "人;每人组题;" + ztts;
          log.error("paperId:" + paperId + ":组卷人数：" + num);
          if (num != zrs1) {
            zjzt = 2;
            log.error("出现问题:paperId:" + paperId + ":数据库查询到的人数：" + zrs1 + 
              ";组卷人数：" + num);
            info = "出现问题:应组卷人数：" + zrs1 + ";<br>组卷人数：" + num;
          }

          session2.createSQLQuery(
            "update cus_kw_examsubject set zjzt = " + zjzt + 
            ",zjrs = " + num + " where kmid ='" + pcid + 
            "'").executeUpdate();
          examinfo.isDone();
          getMsgBean(true, info + "<br>" + 
            examinfo.getError().toString());
        }
      } catch (Exception e) {
        session2.getTransaction().rollback();
        examinfo.isDone();
        if (conn != null) {
          try {
            conn.close();
          } catch (SQLException e1) {
            e1.printStackTrace();
          }
        }
        e.printStackTrace();
        getMsgBean(true, "组卷失败！");
      }
      session2.getTransaction().commit();
    }
  }

  public void zjByTx(String paperId, String pcid, MBspInfo bspInfo, ServletContext serlvetContext)
  {
    Connection conn = null;
    System.out.println();
    ExamZjInfo examinfo = ExamZjInfo.getInstance();
    try {
      if (examinfo.getStatus().intValue() == 0) {
        getMsgBean(false, "用户【" + examinfo.getUserName() + 
          "】正在组卷,请稍候再进行组卷!");
        return;
      }
      int threads = 100;
      String info = null;
      int kmflag = Integer.parseInt(getSession()
        .createSQLQuery(
        "select count(1) from exam_km where paper_id  = '" + 
        paperId + "'").uniqueResult().toString());
      if (kmflag == 0) {
        getMsgBean(false, "请先设置科目比例，然后组卷");
        return;
      }
//      int photoflag = Integer.parseInt(getSession()
//    	        .createSQLQuery(
//    	        "select count(1) from exam_photo where paper_id  = '" + 
//    	        paperId + "'").uniqueResult().toString());
//    	      if (photoflag == 0) {
//    	        getMsgBean(false, "请先下载照片，然后组卷");
//    	        return;
//    	      }
      List exams = new ArrayList();
      Map kmid = (Map)getSession()
        .createSQLQuery(
        "select s.kmid,s.lcid,s.subjectname,concat(s.score,'') as score,concat(s.timelength,'') as timelength,es.starttime,es.endtime from cus_kw_examsubject s,cus_kw_examschedule ES where s.kmid = es.kmid and s.kmid = '" + 
        pcid + "'")
        .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
        .uniqueResult();
      if (kmid == null) {
        getMsgBean(false, "未设置考试信息！");
        return;
      }if ((kmid.get("starttime") == null) || 
        ("null".equals(kmid.get("starttime")))) {
        getMsgBean(false, "未设置考试时间！");
        return;
      }

      List kslist = getSession()
        .createSQLQuery(
        "select xs.xs_jbxx_id from zxxs_xs_jbxx xs where exists (select 1 from cus_kw_examinee where xs_jbxx_id = xs.xs_jbxx_id and kmid = '" + 
        pcid + "')").list();

      int zrs1 = kslist.size();
      log.error("paperId:" + paperId + ":数据库查询到的人数：" + zrs1);
      int paperType = Integer.parseInt(getSession()
        .createSQLQuery(
        "select paper_type From paper_basic  where paper_id = '" + 
        paperId + "'").uniqueResult().toString());
      if ((kslist == null) || (kslist.size() == 0)) {
        getMsgBean(false, "该批次没有学生");
        return;
      }

      if (kslist.size() / threads < 2) {
        if (kslist.size() / 2 == 0)
          threads = 1;
        else {
          threads = kslist.size() / 2;
        }
      }

      if (paperType == 0) {
        examinfo.init(bspInfo.getUserName());
        int zts = Integer.parseInt(getSession()
          .createSQLQuery(
          "select sum(ts) from exam_km where paper_id = '" + 
          paperId + "'").uniqueResult()
          .toString()) * 
          kslist.size();
        Map pamap = new HashMap();
        String lj = ExamzjServiceImpl.class
          .getResource("")
          .toString()
          .replace("file:/", "")
          .replace("classes/com/jiajie/service/exambasic/impl/", 
          "") + 
          "exam" + File.separator + pcid;
        pamap.put("paperId", paperId);
        pamap.put("kmid", kmid);
        pamap.put("userid", bspInfo.getUserId());
        pamap.put("serlvetContext", serlvetContext);
        File dir = new File(lj);
        if (!dir.exists()) {
          dir.mkdir();
        }
        pamap.put("path", lj);
        examinfo.setKslist(kslist);
        List l = getSession()
          .createSQLQuery(
          "select sum(ts) as txs,exam_type_id as tid,kmmc From exam_km where paper_id ='" + 
          paperId + 
          "' and ts > 0 group by kmmc,exam_type_id")
          .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
          .list();
        pamap.put("exams", l);
        ScheduledExecutorService service = 
          Executors.newScheduledThreadPool(threads);
        for (int i = 0; i < threads; i++) {
          service.schedule(new ExamZjTask(pamap), 1L, 
            TimeUnit.MILLISECONDS);
        }
        service.shutdown();
        int i_i = 0;
        StringBuilder sq = new StringBuilder();
        while (!examinfo.isDoneByThreads()) {
          System.out.print("");
        }
        Map ksmap = examinfo.getAddks();
        Iterator iter = ksmap.keySet().iterator();
        int num = 0;
        int ztts = 0;
        int znum = 0;
        conn = getConnection();
        conn.prepareStatement(
          "delete from paper_detail where paper_id = '" + paperId + 
          "'").executeUpdate();
        while (iter.hasNext()) {
          num++;
          String key = (String)iter.next();
          List list = (List)ksmap.get(key);
          for (int i = 0; i < list.size(); i++) {
            znum++;
            sq.append("(replace(uuid(),'-',''),'").append(paperId)
              .append("','").append(key).append("',")
              .append((String)list.get(i)).append("'1','");
            sq.append(i).append("','").append(bspInfo.getUserId())
              .append("',sysdate()),");
          }
          if (!iter.hasNext()) {
            ztts = list.size();
          }
          if (znum % 1000 == 0) {
            sq.delete(sq.length() - 1, sq.length());
            conn.prepareStatement(
              "insert into PAPER_DETAIL(XH,PAPER_ID,xs_jbxx_id,score,EXAM_INFO_ID,ZT,PX,CJR,CJSJ)values" + 
              sq.toString()).executeUpdate();
            sq.delete(0, sq.length());
          }
          System.out.println(znum);
        }
        System.out.println(sq.length() + "    qqq");
        System.out.println(sq.toString());
        if (sq.length() > 0) {
          sq.delete(sq.length() - 1, sq.length());
          conn.prepareStatement(
            "insert into PAPER_DETAIL(XH,PAPER_ID,xs_jbxx_id,score,EXAM_INFO_ID,ZT,PX,CJR,CJSJ)values" + 
            sq.toString()).executeUpdate();
          sq.delete(0, sq.length());
          sq = null;
        }
        conn.close();
        int zjzt = 1;
        info = "组卷学生：" + num + "人;每人组题;" + ztts;
        log.error("paperId:" + paperId + ":组卷人数：" + num);
        if (num != zrs1) {
          zjzt = 2;
          log.error("出现问题:paperId:" + paperId + ":数据库查询到的人数：" + zrs1 + 
            ";组卷人数：" + num);
          info = "出现问题:应组卷人数：" + zrs1 + ";<br>组卷人数：" + num;
        }

        getSession().createSQLQuery(
          "update cus_kw_examsubject set zjzt = " + zjzt + 
          ",zjrs = " + num + " where kmid ='" + pcid + 
          "'").executeUpdate();
        examinfo.isDone();
        getMsgBean(true, info + "<br>" + 
          examinfo.getError().toString());
      }
    } catch (Exception e) {
      examinfo.isDone();
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
      }
      e.printStackTrace();
      getMsgBean(true, "组卷失败！");
    }
  }

  @Transactional(readOnly=false, rollbackFor={Exception.class})
  public int saveExamPaperInfo(File examfile, String kmid, String roomid) {
    List list = getSession().createSQLQuery("select rsid from cus_kw_roomsubject where kmid = '" + kmid + "' and roomid = '" + roomid + "'").list();
    if ((list != null) && (list.size() > 0)) {
      for (int i = 0; i < list.size(); i++) {
        getSession().createSQLQuery("update cus_kw_roomSubject set submit_status = 1 ,optdate = sysdate() where rsid = '" + (String)list.get(i) + "'").executeUpdate();
      }
    }
    return 1;
  }

  public String getRoomName(String code)
  {
    return (String)getSession()
      .createSQLQuery(
      "select concat(b.buildname,roomname) from cus_kw_room c,cus_kw_building b where c.lfid = b.lf_id and c.FJID = '" + 
      code + "'").uniqueResult();
  }

  @Transactional(readOnly=false, rollbackFor={Exception.class})
  public Map<String, Object> downExamPaper(String roomid, String kd, boolean flag, String version)
  {
    Map result = new HashMap();
    result.put("success", Boolean.valueOf(true));
    if ((version == null) || ("".equals(version))) {
      String rtid = (String)getSession().createSQLQuery(
        "select roomid from cus_kw_roomExcept where roomid = '" + roomid + 
        "'").uniqueResult();
      if ((rtid != null) && (!"".equals(rtid))) {
        result.put("success", Boolean.valueOf(false));
        result.put("msg", "请更新最新版本！");
        return result;
      }
    } else {
      String superpwd = MD5.md5(roomid);
      String version2 = superpwd.substring(superpwd.length() - 8, superpwd.length() - 2);
      if (!version.equals(version2)) {
        result.put("success", Boolean.valueOf(false));
        result.put("msg", "找不到此资源！");
        return result;
      }
    }
    String url = (String)getSession().createSQLQuery(
      "select uriId from cus_kw_urllist where roomid = '" + roomid + 
      "' and rq = '" + kd + "'").uniqueResult();
    if (url != null) {
      log.error(kd + "/" + url + ".cf" + "    =-=-=-=-=");
      long startTime=System.currentTimeMillis();   //获取开始时间
      FileUploadWIthBaiDu baiDu=new FileUploadWIthBaiDu();
      url = baiDu.getUrl(kd + "/" + url + ".cf", 300);
      long endTime=System.currentTimeMillis(); //获取结束时间
	   System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
      url = DES.encodeDES(url);
      log.error(url + "  = = = url");
      result.put("data", url);
      System.out.println("flag:"+flag);
      if (flag) {
        Map map = (Map)getSession().createSQLQuery("select er.kcid,er.lcid from cus_kw_examroom er ,cus_kw_examround r where r.lcid = er.LCID and roomid = '" + roomid + "' and r.lcid in (select lcid from cus_kw_examschedule where date_format(examdate,'%Y-%m-%d') = '" + kd + "') order by r.startdate desc limit 1").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
        List kms = getSession().createSQLQuery("select kmid,starttime ,endtime from cus_kw_examschedule where lcid = '" + (String)map.get("lcid") + "' and date_format(examdate,'%Y-%m-%d') = '" + kd + "' order by starttime ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        for (int i = 0; i < kms.size(); i++) {
          String rsid = (String)getSession().createSQLQuery("select rsid from cus_kw_roomsubject where kmid = '" + (String)((Map)kms.get(i)).get("kmid") + "' and roomid = '" + roomid + "'").uniqueResult();
          if (rsid != null)
            getSession().createSQLQuery("update cus_kw_roomsubject set optdate = sysdate() ,down_status = 1 where rsid = '" + rsid + "'").executeUpdate();
          else{
        	  getSession().createSQLQuery("insert into cus_kw_roomsubject(rsid,kmid,roomid,down_status,optdate) values(replace(uuid(),'-',''),'" + (String)((Map)kms.get(i)).get("kmid") + "','" + roomid + "'," + 1 + ",sysdate())").executeUpdate();
        	  System.out.println("insert");
          }
        }
      }
    }
    else {
      result.put("success", Boolean.valueOf(false));
      result.put("msg", "找不到此资源");
    }

    return result;
  }

  private String init(String kcid) throws IOException {
    String path = DownloadExamFilter.class.getResource("").toString()
      .replace("classes/com/jiajie/filter/", "")
      .replace("file:/", "") + 
      "exam";
    File fkcid = new File(path + File.separator + kcid);
    if (!fkcid.exists()) {
      fkcid.mkdirs();
    }
    return path;
  }
  class ExamZjTask implements Runnable {
    private Session session;
    private Map<String, Object> pamap;

    private void setSession(Session session) { this.session = session; }

    public Map<String, Object> getPamap()
    {
      return this.pamap;
    }

    public void setPamap(Map<String, Object> pamap) {
      this.pamap = pamap;
    }

    public ExamZjTask(Map pamap) {
      this.pamap = pamap;
    }

    public void run() {
      StringBuilder sbr = new StringBuilder();
      ExamZjInfo exam = ExamZjInfo.getInstance();
      try {
        exam.addThreadsNum();
        boolean flag = true;
        ServletContext serlvetContext = 
          (ServletContext)this.pamap
          .get("serlvetContext");
        ApplicationContext ac1 = 
          WebApplicationContextUtils.getRequiredWebApplicationContext(serlvetContext);
        SessionFactory sessionFactory = (SessionFactory)ac1
          .getBean("sessionFactory");
        setSession(sessionFactory.openSession());
        Map kminfo = 
          (Map)this.pamap
          .get("kmid");
        while (flag) {
          String xsid = exam.assignObjectByKey();
          if (xsid != null) {
            int zts = 0;
            List l = 
              (List)this.pamap
              .get("exams");
            Map ei = new HashMap();
            List list = new ArrayList();
            for (int j = 0; j < l.size(); j++) {
              List examids = new ArrayList();
              sbr.delete(0, sbr.length());
              sbr.append(
                "SELECT exam_info_id ,concat(scores,'') as scores FROM (SELECT * FROM  exam_info where kch = '")
                .append(((Map)l.get(j)).get("kmmc"));
              sbr.append("' and EXAM_TYPE_ID = '").append(
                ((Map)l.get(j)).get("tid").toString());
              sbr.append("' and sfqy is null ORDER BY EXAM_INFO_ID LIMIT 8) E ORDER BY rand() LIMIT ").append(
                ((Map)l.get(j)).get("txs"));
              List exams = this.session
                .createSQLQuery(sbr.toString())
                .setResultTransformer(
                Transformers.ALIAS_TO_ENTITY_MAP)
                .list();
              if (exams == null) {
                exam.addErrorInfo("题型:<font color='red'>" + 
                  ((Map)l.get(j)).get("txs").toString() + 
                  "</font>;<br>科目:<font color='red'>" + 
                  ((Map)l.get(j)).get("kmmc") + 
                  "</font>;<br>应组<font color='red'>" + 
                  ((Map)l.get(j)).get("txs").toString() + 
                  "</font>道题;<br>只组了<font color='red'>0</font>道题<br>");
              } else {
                if (exams.size() != Integer.parseInt(((Map)l.get(j))
                  .get("txs").toString())) {
                  exam.addErrorInfo("题型:<font color='red'>" + 
                    ((Map)l.get(j)).get("txs").toString() + 
                    "</font>;<br>科目:<font color='red'>" + 
                    ((Map)l.get(j)).get("kmmc") + 
                    "</font>;<br>应组<font color='red'>" + 
                    ((Map)l.get(j)).get("txs").toString() + 
                    "</font>道题;<br>只组了<font color='red'>" + 
                    exams.size() + "</font>道题<br>");
                }
                for (int k = 0; k < exams.size(); k++) {
                  zts++;
                  Map paperinfo = new HashMap();
                  paperinfo.put("xh", list == null ? 1 : 
                    list.size() + 1);
                  paperinfo.put("paper_id", 
                    this.pamap.get("paperId").toString());
                  paperinfo.put("kmid", kminfo.get("kmid")
                    .toString());
                  paperinfo.put("exam_info_id", ((Map)exams.get(k))
                    .get("exam_info_id").toString());
                  paperinfo.put("xs_jbxx_id", xsid);
                  list.add(paperinfo);
                  examids.add(((Map)exams.get(k))
                    .get("exam_info_id").toString());
                  exam.addKtToKs(
                    xsid, 
                    ((Map)exams.get(k)).get("scores")
                    .toString() + 
                    ",'" + 
                    ((Map)exams.get(k))
                    .get("exam_info_id")
                    .toString() + "',");
                }
                if (ei.get(((Map)l.get(j)).get("tid").toString()) == null)
                  ei.put(((Map)l.get(j)).get("tid").toString(), 
                    examids);
                else {
                  ((List)ei.get(((Map)l.get(j)).get("tid").toString()))
                    .addAll(examids);
                }
              }
            }
            Iterator iter = ei.keySet().iterator();
            Map exammap = new HashMap();
            while (iter.hasNext()) {
              String key = (String)iter.next();
              StringBuffer sb = new StringBuffer(
                "from ExamInfo where exam_info_id in (");
              for (int i = 0; i < ((List)ei.get(key)).size(); i++) {
                sb.append("'").append((String)((List)ei.get(key)).get(i))
                  .append("',");
              }
              sb.delete(sb.length() - 1, sb.length());
              System.out.println(sb.toString());
              exammap.put(
                key, 
                this.session.createQuery(sb
                .toString() + 
                ") order by exam_info_bh").list());
            }
            System.out.println();

            Map xsinfo = (Map)this.session
              .createSQLQuery(
              "select case when xbm = 1 then '男' else '女' end as xb ,xm ,sfzjh from zxxs_xs_jbxx where xs_jbxx_id = '" + 
              xsid + "'")
              .setResultTransformer(
              Transformers.ALIAS_TO_ENTITY_MAP)
              .uniqueResult();
            xsinfo.put(
              "img", 
              (String)this.session.createSQLQuery(
              "select max(path) from zxxs_xs_pic where xs_jbxx_id = '" + 
              xsid + "'").uniqueResult());
            xsid = (String)this.session.createSQLQuery("select ksid from cus_kw_Examinee where xs_jbxx_id = '" + xsid + "'").uniqueResult();
            wirteToHtml(xsid, exammap, zts, kminfo, 
              this.pamap.get("path").toString(), 
              this.pamap.get("paperId").toString(), xsinfo);
          } else {
            flag = false;
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      } finally {
        this.session.close();
      }
      exam.addDoneThreadsNum();
    }

    private void wirteToHtml(String xsid, Map<String, List<ExamInfo>> m,
			int zts, Map<String, Object> kminfo, String path,
			String paperId, Map<String, String> xsinfo)
			throws ParseException {

		System.out.println("---------------------------------------------------");
		File f = null;
		FileOutputStream fos = null;
		String tr = "";
		String imgf = "";
		String img = "../img/mrzp_img.jpg";
		if (xsinfo.get("img") != null) {
			img = xsid + ".jpg";
			imgf = path.replace("WEB-INF", "uploadFile")
					.replace("exam", "photo/")
					.replace(kminfo.get("kmid").toString(), "")
					+ xsinfo.get("img");
		}
		try {
			f = new File(path + File.separator + xsid + ".html");
			if (!f.exists()) {
				f.createNewFile();
			} else {
				f.delete();
				f.createNewFile();
			}
			fos = new FileOutputStream(f);
			// 第一步
			StringBuffer sb = new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			long starttime = 1211;
			int kssj = Integer
					.parseInt(kminfo.get("timelength").toString()) * 60;

			
			sb.append(
			"<!DOCTYPE html><html> <head> <meta http-equiv='Content-Type' content='text/html;charset=UTF-8'/> <link rel='stylesheet' href='../css/css.css'>")
			.append("  </head>");
	sb.append("<body onload='init()'>");
			
	// top
	sb.append("<div class='title'>湖南行政执法人员资格电子化考试</div>");
	sb.append("<input type='hidden' name='paperId'  id='paperId' value='")
	.append(paperId).append("' />");
	sb.append("<div class='div1'>共：<span id='zg'>").append(zts).append("</span> 题&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			sb.append("已答：<span id='yd'>0</span> 题&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			sb.append("未答：<span id='wd'>").append(zts).append("</span> 题&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			sb.append("答卷时间：<span id='djsj'>").append(kminfo.get("timelength").toString()).append("</span> 分钟&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			sb.append("试卷总分：<span id='sjzf'>").append(kminfo.get("score").toString()).append("</span> 分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			sb.append("剩余时间：<span class='time' id='sysj'></span>");
			sb.append("</div>");
	//left 
	sb.append(" <div class='left'>");
		sb.append(" <div class='grxx'>");
			sb.append("<img  onerror='this.src='"+img+"'  class='userimg' src='").append(img).append("' />");
			sb.append("<div class='item'>考生姓名：").append(xsinfo.get("xm")).append("</div>");
			sb.append("<div class='item'>考生性别：").append(xsinfo.get("xb")).append("</div>");
			sb.append("<div class='item'>身份证号：").append(xsinfo.get("sfzjh")).append("</div>");
			sb.append("<div class='clear'></div>");
		sb.append("</div>");
		sb.append("<div class='tm-div'>");
			sb.append("<div class='div2'><div class='div2-0'>颜色说明</div><div class='div2-1'>未做</div><div class='div2-2'>已做</div></div>");
			fos.write(sb.toString().getBytes("utf-8"));
			sb.delete(0, sb.length());
			sb.append(" <form id='examForm'>");
			sb.append(" <div class='right'>");
			TreeMap<String, List<ExamInfo>> exammap = new TreeMap<String, List<ExamInfo>>(
					m);
			Iterator<String> iter = exammap.keySet().iterator();
			StringBuffer tb = new StringBuffer();
			while (iter.hasNext()) {
				String key = iter.next();
				String tx = "一、单选题";
				String tbtxid = "d";
				if (key.equals("2")) {
					tx = "二、多选题";
					tbtxid = "o";
				}
				if(key.equals("3")){
					tx = "三、判断题";
					tbtxid = "o";
				}
				List<ExamInfo> list = exammap.get(key);
				Collections.shuffle(list);
				sb.append(
						"<div class='right-title'>")
						.append(tx).append(" （")
						.append(list.get(0).getScores())
						.append("分/题，共" + list.size() + "题）</div>");
				tb.append(
				"<div class='tm'>")
				.append(tx).append("</div>");//定位的
				for (int i = 0; i < list.size(); i++) {
					String onclick="\"choiceTm('" + list.get(i).getExam_info_id() + "')\"";
					tb.append("<div class='tm-item' id='menu_").append(list.get(i).getExam_info_id()).append("'").append(" onclick=").append(onclick).append(" >").append((i+1)).append("</div>");
					sb.append("<table class='table1' id='").append(list.get(i).getExam_info_id()).append("' name='").append("name_").append(list.get(i).getExam_info_id()).append("'>");
						sb.append("<tr class='table-title'><td>").append((i + 1)).append(".").append(list.get(i).getExam_cont()).append("</td></tr>");
					List<ExamInfoOpt> opts = new ArrayList<ExamInfoOpt>(
							list.get(i).getOpts());
					if (key.equals("1")) {
						for (int j = 0; j < opts.size(); j++) {
							sb.append("<tr class='table-tm'>").append("<td><input type='radio' id='")
									.append(list.get(i).getExam_info_id()).append("_").append(opts.get(j).getExam_bh()).append("' name='").append(list.get(i).getExam_info_id())
									.append("' onclick='choiceAnswer(this)' value='").append(list.get(i).getExam_info_id()).append("_").append(opts.get(j).getExam_bh()).append("' />")
									.append("<label for='").append(list.get(i).getExam_info_id()).append("_").append(opts.get(j).getExam_bh())
									.append("' >").append(opts.get(j).getExam_bh()).append(".").append(opts.get(j).getExam_opt_des()).append("</label></td></tr>");
						}
					} else if (key.equals("2")) {
						for (int j = 0; j < opts.size(); j++) {
							sb.append("<tr class='table-tm'>").append("<td><input type='checkbox' id='")
									.append(list.get(i).getExam_info_id()).append("_").append(opts.get(j).getExam_bh()).append("' name='").append(list.get(i).getExam_info_id())
									.append("' onclick='choiceAnswer(this)' value='").append(list.get(i).getExam_info_id()).append("_").append(opts.get(j).getExam_bh()).append("' />")
									.append("<label for='").append(list.get(i).getExam_info_id()).append("_").append(opts.get(j).getExam_bh())
									.append("' >").append(opts.get(j).getExam_bh()).append(".").append(opts.get(j).getExam_opt_des()).append("</label></td></tr>");
						}
					} else if (key.equals("3")) {
						//判断题
						sb.append("<tr class='table-tm'>").append("<td><input type='radio' id='").append(list.get(i).getExam_info_id()).append("_Y")
						.append("' name='").append(list.get(i).getExam_info_id()).append("' onclick='choiceAnswer(this)' value=''  />")
						.append("<label for='").append(list.get(i).getExam_info_id()).append("_Y")
						.append("' >").append("是").append("</label></td></tr>");
						
						sb.append("<tr class='table-tm'>").append("<td><input type='radio' id='").append(list.get(i).getExam_info_id()).append("_N")
						.append("' name='").append(list.get(i).getExam_info_id()).append("' onclick='choiceAnswer(this)' value=''  />")
						.append("<label for='").append(list.get(i).getExam_info_id()).append("_N")
						.append("' >").append("否").append("</label></td></tr>");
					}
					sb.append("</table>");
					
				}
				
				
			}
			System.out.println("---------------------------------------------------");
			tb.append("<div class='height10'></div>");
			tb.append("</div>").append("<div class='jj' onclick='submitMyAnswer()'><label>交 卷</label></div>");
			tb.append("</div>");
			sb.append("</div></form>");
			sb.append("</body>");
			sb.append("<script src='../js/main.js'></script>");
			sb.append("</html>");
			fos.write(tb.toString().getBytes("utf-8"));
			fos.write(sb.toString().getBytes("utf-8"));
			sb.delete(0, sb.length());
			tb.delete(0, tb.length());
			sb = null;
			tb = null;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
					try {
						ZipMain.encryptZip(path + File.separator + xsid
								+ ".html", path + File.separator + xsid
								+ ".cf", "hnjjrjsyb..asdfghjkl");
						log.error(imgf + "   FESDFDF  ");
						if (imgf != null && !"".equals(imgf)) {
							try {
								FileIoUtils.Copy(imgf, path
										+ File.separator + xsid + ".jpg");
								ZipMain.encryptZip(path + File.separator
										+ xsid + ".jpg", path
										+ File.separator + xsid + ".tx",
										"hnjjrjsyb..asdfghjkl");
								File tx = new File( path+ File.separator + xsid + ".jpg");
								if(tx.exists()){
									tx.delete();
								}
							} catch (Exception e2) {
								log.error("WZDXSTP++==" + xsid);
							}
						}
						f.delete();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
}