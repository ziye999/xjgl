package com.jiajie.service.examArrangement.impl;

import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.examArrangement.DataPrintService;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

@Service("dataPrintServiceImpl")
public class DataPrintServiceImpl extends ServiceBridge
  implements DataPrintService
{
  public List<Map<String, Object>> kaohaomenqian(String lcid, String kmid, String kcid, String kdid)
  {
    String sql = "select distinct T3.KCBH,T1.KSCODE,T1.XJH as XH,T1.XM,T1.SFZJH,t1.ZWH,";
    sql = sql + "(select date_format(t.STARTTIME,'%Y-%m-%d %H:%i') from cus_kw_examschedule t where t.KMID=t1.KMID) SJ,";
    sql = sql + "(select SUBJECTNAME from cus_kw_examsubject t where t.KMID=t1.KMID) PCMC,T4.XXDZ,T4.XXMC ";
    sql = sql + "from cus_kw_examinee t1 ";
    sql = sql + "LEFT JOIN CUS_KW_EXAMSCHOOL T8 ON T8.KDID=T1.KDID AND T8.LCID=T1.LCID ";
    sql = sql + "left join cus_kw_examroom t3 on t3.KCID=T1.KCID and t3.LCID=t1.LCID ";
    sql = sql + "left join ZXXS_XX_JBXX T4 ON T4.XX_JBXX_ID=T8.XXDM ";
    sql = sql + "where t3.lcid='" + lcid + "' and t3.kcid='" + kcid + "'";
    sql = sql + ((kmid == null) || ("".equals(kmid)) ? "" : new StringBuilder(" and T1.kmid='").append(kmid).append("'").toString());
    sql = sql + ((kdid == null) || ("".equals(kdid)) ? "" : new StringBuilder(" and t3.kdid='").append(kdid).append("'").toString());
    sql = sql + " order by SJ asc,T1.ZWH";
    List list = getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
    return list;
  }

  public List<Map<String, Object>> kaohaoduizhao(String lcid, String kmid, String school, String selecttype)
  {
    String sql = null;
    if (selecttype.equals("true")) {
      sql = "select distinct T3.KCBH,T1.KSCODE,T1.XJH as XH,T1.XM,T1.SFZJH,";
      sql = sql + "(select date_format(t.STARTTIME,'%Y-%m-%d %H:%i') from cus_kw_examschedule t where t.KMID=t1.KMID) PCMC ";
      sql = sql + "from cus_kw_examinee t1 ";
      sql = sql + "left join cus_kw_examroom t3 on t3.KCID=T1.KCID and t3.LCID=t1.LCID ";
      sql = sql + "where t2.lcid='" + lcid + "'";
      sql = sql + ((kmid == null) || ("".equals(kmid)) ? "" : new StringBuilder(" and t2.kmid='").append(kmid).append("'").toString());
      sql = sql + ((school == null) || ("".equals(school)) ? "" : new StringBuilder(" and t1.xxdm='").append(school).append("'").toString());
      sql = sql + " order by PCMC asc,T1.SFZJH";
    } else {
      sql = "select distinct IFNULL(t2.NJMC,'') NJMC,IFNULL(t3.BJMC,'') BJMC,ta.LCID,ta.KSCODE,ta.XJH,ta.XM,ta.XXDM,ta.NJ,ta.BH,ta.XBM,ta.SFZJH,ta.FLAG,";
      sql = sql + "(select date_format(t.STARTTIME,'%Y-%m-%d %H:%i') from cus_kw_examschedule t where t.KMID=ta.KMID) PCMC ";
      sql = sql + "from cus_kw_examinee ta ";
      sql = sql + "left join ZXXS_XX_BJXX t3 on T3.XX_BJXX_ID=TA.BH ";
      sql = sql + "left join ZXXS_XX_NJXX t2 on t2.XX_NJXX_ID=t3.XX_NJXX_ID ";
      sql = sql + "left join SYS_ENUM_VALUE t1 on t1.CODE=t2.xjnjdm AND t2.njzt='1' AND t1.DIC_TYPE='ZD_XT_NJDM' ";
      sql = sql + "where ta.lcid='" + lcid + "'";
      sql = sql + ((kmid == null) || ("".equals(kmid)) ? "" : new StringBuilder(" and ta.kmid='").append(kmid).append("'").toString());
      sql = sql + ((school == null) || ("".equals(school)) ? "" : new StringBuilder(" and ta.xxdm='").append(school).append("'").toString());
      sql = sql + " order by PCMC asc,ta.SFZJH";
    }
    List list = getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
    return list;
  }

  public List<Map<String, Object>> kaodianjiankao(String lcid, String kdid)
  {
    String sql = "select distinct t1.TEANAME,t1.MAINFLAG,t2.KCBH,t3.ROOMNAME,T4.BUILDNAME,";
    sql = sql + "(select count(*) from cus_kw_examinee l4 where l4.KCID=T1.KCID and l4.LCID=t2.LCID and l4.KDID=t2.KDID) STUCOUNT,";
    sql = sql + "date_format(T5.EXAMDATE,'%Y-%m-%d') EXAMDATE, date_format(T5.STARTTIME,'%Y-%m-%d %H:%i:%s') STARTTIME ";
    sql = sql + "from cus_kw_roomeye t1 ";
    sql = sql + "left join cus_kw_examroom t2 on t2.KCID=t1.KCID ";
    sql = sql + "left join cus_kw_room t3 on t3.FJID=t2.ROOMID ";
    sql = sql + "left join cus_kw_building t4 on T4.LF_ID=T3.LFID ";
    sql = sql + "left join cus_kw_examschedule t5 on T5.RCID=T1.RCID and t5.lcid=t2.lcid ";
    sql = sql + "where t2.lcid='" + lcid + "' and t2.kdid='" + kdid + "' ";
    sql = sql + "order by t2.KCBH,T5.EXAMDATE,T5.STARTTIME,t1.MAINFLAG desc";
    List list = getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
    return list;
  }

  public List<Map<String, Object>> kaodianfenbu(String lcid, String kdid)
  {
    String sql = "select distinct BUILDNAME,FLOOR,ROOMNAME,KCBH,";

    sql = sql + "(select IFNULL(min(kscode),'0') from cus_kw_examinee l4 where l4.KCID=T1.KCID and l4.LCID=t1.LCID and l4.KDID=t1.KDID) MINKSCODE,";
    sql = sql + "(select IFNULL(max(kscode),'0') from cus_kw_examinee l5 where l5.KCID=T1.KCID and l5.LCID=t1.LCID and l5.KDID=t1.KDID) MAXKSCODE,";
    sql = sql + "(SELECT count(distinct l5.XJH) FROM cus_kw_examinee l5 WHERE l5.KCID=T1.KCID AND l5.LCID=t1.LCID AND l5.KDID=t1.KDID) KSNUM,";
    sql = sql + "(select count(distinct ks.kmid) from cus_kw_examinee ks where ks.KCID=T1.KCID and ks.LCID=t1.LCID and ks.KDID=t1.KDID) KMNUM ";
    sql = sql + "from cus_kw_examroom t1 ";
    sql = sql + "left join cus_kw_room t2 on T2.FJID=T1.ROOMID ";
    sql = sql + "left join cus_kw_building t3 on T3.LF_ID=t2.LFID ";
    sql = sql + "where t1.lcid='" + lcid + "' and t1.kdid='" + kdid;
    sql = sql + "' order by BUILDNAME,FLOOR desc,KCBH";
    List list = getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
    return list;
  }

  public List<Map<String, Object>> kaoshiricheng(String lcid, String kdid, String school, String nj, MBspInfo bspInfo)
  {
    String sql = "select distinct a.DZ,date_format(a.examdate, '%Y-%m-%d') GDATE,";
    sql = sql + "cast(group_concat(CONCAT(date_format(a.STARTTIME,'%H:%i'),'--',date_format(a.ENDTIME,'%H:%i')) order by a.starttime) as char) KMS ";
    sql = sql + "from (select t1.EXAMDATE, t2.SUBJECTNAME, t1.STARTTIME, t1.ENDTIME,";
    sql = sql + "(SELECT cast(group_concat(distinct (SELECT BUILDNAME FROM cus_kw_building WHERE lf_id = d.lfid),";
    sql = sql + "' ','第',d.floor,'层 ',d.roomname) as char) FROM cus_kw_examinee a ";
    sql = sql + "left join CUS_KW_EXAMROOM c on c.kcid = a.kcid ";
    sql = sql + "left join cus_kw_room d on d.fjid = c.roomid WHERE a.kmid = t1.KMID and a.kdid='" + kdid + "') DZ ";
    sql = sql + "from cus_kw_examschedule t1 ";
    sql = sql + "left join cus_kw_examsubject t2 on t2.kmid=t1.kmid ";
    sql = sql + "left join cus_kw_examround t3 on t3.LCID=T1.LCID ";
    sql = sql + "where t3.LCID='" + lcid + "' ";
    if ((("1".equals(bspInfo.getUserType())) || ("2".equals(bspInfo.getUserType()))) && (StringUtils.isNotEmpty(school)))
      sql = sql + "and T3.DWID in ('" + school.replaceAll(",", "','") + "') ";
    else if ("3".equals(bspInfo.getUserType())) {
      sql = sql + "and T3.DWID='" + bspInfo.getOrgan().getOrganCode() + "' ";
    }
    if (StringUtils.isNotEmpty(nj)) {
      sql = sql + "and t2.nj='" + nj + "' ";
    }
    sql = sql + "order by starttime) a group by date_format(a.examdate,'%Y-%m-%d') ";
    List list = getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
    return list;
  }

  public PageBean getExamBasicInfo(String lcid, String kmid, String kcid, String kdid)
  {
    String sql = " SELECT distinct T1.KSCODE,t1.XJH,T1.XM,T1.SFZJH,T4.PATH,cast(CONCAT(T5.KCBH,'考场') as char) AS KCBH,T1.ZWH, (select SUBJECTNAME from cus_kw_examsubject t where t.KMID=t1.KMID) PCMC, (select date_format(t.STARTTIME,'%Y-%m-%d %H:%i') from cus_kw_examschedule t where t.KMID=t1.KMID) KSSJ, (SELECT (select cast(CONCAT(date_format(ap.examdate, '%Y-%m-%d'),' ',date_format(ap.STARTTIME, '%H:%i'),'--',date_format(ap.ENDTIME, '%H:%i')) as char) kstime from cus_kw_examschedule ap where ap.KMID=t.KMID) FROM cus_kw_examsubject t WHERE t.KMID = t1.KMID) SJ,T6.XXDZ,T6.XXMC FROM CUS_KW_EXAMINEE T1 LEFT JOIN ZXXS_XS_PIC T4 ON T4.XS_JBXX_ID=T1.XS_JBXX_ID LEFT JOIN CUS_KW_EXAMROOM T5 ON T5.LCID=T1.LCID and T5.KCID=T1.KCID LEFT JOIN CUS_KW_EXAMSCHOOL T8 ON T8.KDID=T1.KDID AND T8.LCID=T1.LCID LEFT JOIN ZXXS_XX_JBXX T6 ON T6.XX_JBXX_ID=T8.XXDM WHERE T1.KCID='" + 
      kcid + "' AND T1.LCID='" + lcid + "'" + (
      (kmid == null) || ("".equals(kmid)) ? "" : new StringBuilder(" and T1.kmid='").append(kmid).append("'").toString()) + (
      (kdid == null) || ("".equals(kdid)) ? "" : new StringBuilder(" and T1.kdid='").append(kdid).append("'").toString()) + 
      " ORDER BY KSSJ asc,T1.ZWH";
    return getSQLPageBean(sql);
  }

  public PageBean getExamCard(String lcid, String kmid, String school, String kcid, String kdid, String sfzjh, String xuexiao, MBspInfo bspInfo)
  {
    String sql = " SELECT distinct T1.KSID,T1.KSCODE,T1.XM,T1.SFZJH,T1.XJH,T3.PATH,T6.XXDZ,(select xxmc from zxxs_xx_jbxx where xx_jbxx_id = T1.XXDM) AS XX,T8.POINTNAME,(select (select ADDRESS from cus_kw_building where LF_ID=a.lfid) from cus_kw_room a where a.FJID=T9.ROOMID) KDDZ,(select (select BUILDNAME from cus_kw_building where LF_ID=a.lfid) from cus_kw_room a where a.FJID=T9.ROOMID) BUILDNAME,(select a.FLOOR from cus_kw_room a where a.FJID=T9.ROOMID) FLOORS,(select ROOMNAME from cus_kw_room where FJID=T9.ROOMID) ROOMNAME,T9.KCBH,T1.ZWH,(select cast(CONCAT(date_format(t.STARTTIME,'%Y-%m-%d %H:%i'),'-',date_format(t.ENDTIME,'%H:%i')) as char) from cus_kw_examschedule t where t.KMID=t1.KMID) PCMC,(select mc from zd_xb where dm=T1.xbm) XB  FROM CUS_KW_EXAMINEE T1 LEFT JOIN ZXXS_XS_PIC T3 ON T3.XS_JBXX_ID=T1.XS_JBXX_ID LEFT JOIN CUS_KW_EXAMSCHOOL T8 ON T1.KDID=T8.KDID AND T1.LCID=T8.LCID LEFT JOIN ZXXS_XX_JBXX T6 ON T6.XX_JBXX_ID=T8.XXDM LEFT JOIN CUS_KW_EXAMROOM T9 ON T1.KCID=T9.KCID AND T1.LCID=T9.LCID ";
    if ((xuexiao != null) && (!"".equals(xuexiao))) {
        sql = sql + " left join zxxs_xs_jbxx t10 on T1.sfzjh=t10.sfzjh ";
      }
    sql=sql+" where 1=1 ";
    if ((lcid != null) && (!"".equals(lcid))) {
      sql = sql + " and T1.LCID='" + lcid + "'";
    }
    if ((kmid != null) && (!"".equals(kmid))) {
      sql = sql + " and T1.KMID='" + kmid + "'";
    }
    if ((kcid != null) && (!"".equals(kcid))) {
      sql = sql + " and T1.KCID='" + kcid + "'";
    }
    if ((kdid != null) && (!"".equals(kdid))) {
      sql = sql + " and T1.KDID='" + kdid + "'";
    }
    if ((("1".equals(bspInfo.getUserType())) || ("2".equals(bspInfo.getUserType()))) && (!"".equals(school)))
      sql = sql + " AND T1.XXDM='" + school + "'";
    else if ("3".equals(bspInfo.getUserType())) {
      sql = sql + " AND T1.XXDM='" + bspInfo.getOrgan().getOrganCode() + "'";
    }
    if ((sfzjh != null) && (!"".equals(sfzjh))) {
      sql = sql + " AND T1.SFZJH='" + sfzjh + "'";
    }
    if ((xuexiao != null) && (!"".equals(xuexiao))) {
      sql = sql + " AND t10.bmdz in ('" + xuexiao.replaceAll(",", "','") + "')";
    }
    if ((xuexiao != null) && (!"".equals(xuexiao)))
      sql = sql + " ORDER BY PCMC asc,T1.KSCODE";
    else {
      sql = sql + " ORDER BY T1.KSCODE";
    }
    System.out.println(sql);
    List ls = getListSQL(sql);
    if ((sfzjh != null) && (!"".equals(sfzjh)) && (ls.size() > 0)) {
      update("update cus_kw_examinee set ISKS='1' where lcid='" + lcid + "' and SFZJH='" + sfzjh + "'");
    }
    return getSQLPageBean(sql);
  }

  public Map<String, Object> kaodianfenbubiaotou(String xnxq, String lcid, String kdid)
  {
    Map map = new HashMap();
    String sql = "SELECT distinct POINTNAME,EXAMNAME from CUS_KW_EXAMSCHOOL t1,CUS_KW_EXAMROUND t2 where t1.lcid=t2.lcid and t1.lCID='" + lcid + "' and kdid='" + kdid + "'";
    List list = getListSQL(sql);
    if (list.size() > 0)
      map.putAll((Map)list.get(0));
    String[] xnxqs = xnxq.split(",");
    sql = "select a.* from (select distinct t1.XNMC,t1.xqmc as XQCODE,t2.name as XQMC from ZXXS_XNXQ t1 inner join SYS_ENUM_VALUE t2 on t1.XQMC=t2.CODE and t2.DIC_TYPE='ZD_XT_XQMC' where t1.yxbz = '1' group by t1.xnmc,t1.xqmc,t2.name order by t1.xnmc,t2.name) a where a.xnmc='" + xnxqs[0] + "' and a.xqcode='" + xnxqs[1] + "'";
    list = getListSQL(sql);
    if (list.size() > 0)
      map.putAll((Map)list.get(0));
    return map;
  }

  public Map<String, Object> kaohaoduizhaobiaotou(String xnxq, String lcid) {
    Map map = new HashMap();
    String sql = "select EXAMNAME from CUS_KW_EXAMROUND where lcid='" + lcid + "'";
    List list = getListSQL(sql);
    if (list.size() > 0)
      map.putAll((Map)list.get(0));
    String[] xnxqs = xnxq.split(",");
    sql = "select a.* from (select distinct t1.XNMC,t1.xqmc as XQCODE,t2.name as XQMC from ZXXS_XNXQ t1 inner join SYS_ENUM_VALUE t2 on t1.XQMC=t2.CODE and t2.DIC_TYPE='ZD_XT_XQMC' where t1.yxbz = '1' group by t1.xnmc,t1.xqmc,t2.name order by t1.xnmc,t2.name) a where a.xnmc='" + xnxqs[0] + "' and a.xqcode='" + xnxqs[1] + "'";
    list = getListSQL(sql);
    if (list.size() > 0)
      map.putAll((Map)list.get(0));
    return map;
  }

  public Map<String, Object> kaoshirichengbiaotou(String xnxq, String lcid, String school, String nj, MBspInfo bspInfo)
  {
    Map map = new HashMap();
    String sql = "select EXAMNAME from CUS_KW_EXAMROUND where lcid='" + lcid + "'";
    List list = getListSQL(sql);
    if (list.size() > 0)
      map.putAll((Map)list.get(0));
    String[] xnxqs = xnxq.split(",");
    sql = "select a.* from (select distinct t1.XNMC,t1.xqmc as XQCODE,t2.name as XQMC from ZXXS_XNXQ t1 inner join SYS_ENUM_VALUE t2 on t1.XQMC=t2.CODE and t2.DIC_TYPE='ZD_XT_XQMC' where t1.yxbz = '1' group by t1.xnmc,t1.xqmc,t2.name order by t1.xnmc,t2.name) a where a.xnmc='" + xnxqs[0] + "' and a.xqcode='" + xnxqs[1] + "'";
    list = getListSQL(sql);
    if (list.size() > 0)
      map.putAll((Map)list.get(0));
    sql = "select distinct a.CODENAME as NJMC from (SELECT T2.CODE AS CODEID, T2.NAME AS CODENAME from ZXXS_XX_NJXX t1 LEFT JOIN SYS_ENUM_VALUE t2 on T1.XJNJDM=T2.CODE AND t2.DIC_TYPE='ZD_XT_NJDM' AND t2.FLAG='1' LEFT JOIN t_qxgl_usermapping t3 on t3.ORGAN_CODE = t1.XX_JBXX_ID WHERE t1.NJZT='1' and t3.USER_ID='" + 
      bspInfo.getUserId() + "' ORDER BY CODEID) a where a.CODEID='" + nj + "'";
    list = getListSQL(sql);
    if (list.size() > 0)
      map.putAll((Map)list.get(0));
    return map;
  }

  public PageBean getExamDjInfo(String lcid, String kmid, String kcid, String kdid) {
    String sql = "SELECT DISTINCT count(distinct T1.SFZJH) ksnum,T7.BUILDNAME as KDMC,cast(CONCAT(T5.KCBH, '考场') as char) AS KCBH,cast(CONCAT(IFNULL(min(T1.kscode),'0'),'-',IFNULL(max(T1.kscode),'0')) as char) KHQZ,(SELECT t.subjectname FROM cus_kw_examsubject t WHERE t.KMID = t1.KMID) PCMC,(SELECT case when substr(t.subjectname,9,1)='-' then substr(t.subjectname,10) else t.subjectname end FROM cus_kw_examsubject t WHERE t.kmid = t1.kmid) PC,(SELECT (SELECT cast(CONCAT(date_format(ap.examdate, '%Y-%m-%d'),' ',date_format(ap.STARTTIME, '%H:%i'),'--',date_format(ap.ENDTIME, '%H:%i')) as char) kstime FROM cus_kw_examschedule ap WHERE ap.KMID = t.KMID) FROM cus_kw_examsubject t WHERE t.KMID = t1.KMID) SJ FROM CUS_KW_EXAMINEE T1 LEFT JOIN CUS_KW_EXAMROOM T5 ON T5.LCID=T1.LCID and T1.KCID=T5.KCID LEFT JOIN CUS_KW_EXAMSCHOOL T6 ON T6.LCID = T1.LCID AND T6.KDID = T5.KDID LEFT JOIN CUS_KW_BUILDING T7 ON T7.SCHOOL_ID_QG = T6.XXDM WHERE T1.KCID='" + 
      kcid + "' AND T1.LCID='" + lcid + "'" + (
      (kmid == null) || ("".equals(kmid)) ? "" : new StringBuilder(" and T1.kmid='").append(kmid).append("'").toString()) + (
      (kdid == null) || ("".equals(kdid)) ? "" : new StringBuilder(" and T1.kdid='").append(kdid).append("'").toString()) + 
      " group by T7.BUILDNAME,T5.KCBH,t1.KMID having count(DISTINCT T1.SFZJH)>0 order by sj asc";
    return getSQLPageBean(sql);
  }

  public PageBean getExamKsInfo(String lcid, String kmid, String kdid) {
    String sql = "SELECT DISTINCT T7.BUILDNAME as KDMC,(SELECT (SELECT cast(CONCAT(date_format(ap.examdate, '%Y-%m-%d'),' ',date_format(ap.STARTTIME, '%H:%i'),'--',date_format(ap.ENDTIME, '%H:%i')) as char) kstime FROM cus_kw_examschedule ap WHERE ap.KMID = t.KMID) FROM cus_kw_examsubject t WHERE t.KMID = t1.KMID) SJ FROM CUS_KW_EXAMINEE T1 LEFT JOIN CUS_KW_EXAMSCHOOL T6 ON T6.LCID = T1.LCID AND T6.KDID = T1.KDID LEFT JOIN CUS_KW_BUILDING T7 ON T7.SCHOOL_ID_QG = T6.XXDM WHERE T1.LCID='" + 
      lcid + "'" + (
      (kmid == null) || ("".equals(kmid)) ? "" : new StringBuilder(" and T1.kmid='").append(kmid).append("'").toString()) + (
      (kdid == null) || ("".equals(kdid)) ? "" : new StringBuilder(" and T1.kdid='").append(kdid).append("'").toString()) + " order by sj";
    return getSQLPageBean(sql);
  }
}