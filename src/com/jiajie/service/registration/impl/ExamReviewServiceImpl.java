package com.jiajie.service.registration.impl;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExaminee;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.registration.ExamReviewService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service("examReviewService")
public class ExamReviewServiceImpl extends ServiceBridge
  implements ExamReviewService
{
  public PageBean getList(String xn,String mc,MBspInfo mbf)
  {
    String nn = "";
    String xq = "";
    if ((xn != null) && (!"".equals(xn))) {
      String[] str = xn.split(" ");
      nn = str[0].substring(0, 4);
      xq = str[1];
    }

    String sql = "select DISTINCT s1.* from (select lx.mc LX,xq.mc XQ,rd.LCID,rd.XN,rd.XQM,rd.EXAMNAME,rd.STARTWEEK,rd.STARTDAY,rd.STARTDATE,rd.ENDWEEK,rd.ENDDAY,rd.ENDDATE,rd.EXAMTYPEM,rd.DWID,rd.DWTYPE,rd.SHFLAG,case when rd.SHFLAG='1' then '已审核' when rd.SHFLAG='0' then '已上报' else '未上报' end as SHZT,case when rd.SFFB='0' then '已发布' else '未发布' end as SFFB,rd.SL,IFNULL((select region_edu from com_mems_organ where region_code=rd.dwid),(SELECT (select region_edu from com_mems_organ where region_code=a.SSZGJYXZDM) FROM zxxs_xx_jbxx a WHERE a.xx_jbxx_id = rd.dwid)) MC,IFNULL((select xxmc from zxxs_xx_jbxx where xx_jbxx_id = rd.dwid),'全部') CKDW from cus_kw_examround rd left join com_mems_organ jb on jb.region_code = rd.dwid left join com_mems_organ jb1 on jb1.region_code = jb.parent_code left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=rd.DWID  left join cus_kw_examtype lx on rd.examtypem=lx.dm left join zd_xq xq on rd.xqm=xq.dm where (rd.dwid='" + 
      mbf.getOrgan().getOrganCode() + 
      "' or jb.region_code='" + mbf.getOrgan().getOrganCode() + "' or jb1.region_code='" + mbf.getOrgan().getOrganCode() + 
      "' OR jb1.PARENT_CODE = '" + mbf.getOrgan().getOrganCode() + "' or xx.SSZGJYXZDM='" + mbf.getOrgan().getOrganCode() + 
      "' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='" + mbf.getOrgan().getOrganCode() + 
      "') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='" + mbf.getOrgan().getOrganCode() + "')))) s1 where 1=1";
    if ((nn != null) && (!"".equals(nn))) {
      sql = sql + " and s1.xn='" + nn + "'";
    }
    if ((xq != null) && (!"".equals(xq))) {
      sql = sql + " and s1.xq = '" + xq + "'";
    }
    if((mc != null) && (!"".equals(mc))){
    	sql = sql + " and s1.mc like BINARY '%" + mc + "%'";
    }
    return getSQLPageBean(sql);
  }

  public PageBean getXSList(String lcid, String xx, String nj, String bj, String xjh) {
    String sql = "select xx.XXMC,xb.mc XB,ee.KSID,ee.LCID,ee.KSCODE,ee.XJH,ee.XM,ee.XXDM,ee.NJ,ee.BH,ee.XBM,ee.SFZJH,ee.FLAG from cus_kw_examinee ee left join zd_xb xb on xb.dm=ee.xbm left join zxxs_xx_jbxx xx on ee.xxdm=xx.xx_jbxx_id where ee.lcid='" + 
      lcid + "' and ee.flag='0'";
    if ((xx != null) && (!"".equals(xx))) {
      sql = sql + " and ee.xxdm='" + xx + "'";
    }
    if ((nj != null) && (!"".equals(nj))) {
      sql = sql + " and ee.nj='" + nj + "'";
    }
    if ((bj != null) && (!"".equals(bj))) {
      sql = sql + " and ee.bh='" + bj + "'";
    }
    if ((xjh != null) && (!"".equals(xjh))) {
      sql = sql + " and ee.xjh like binary('%" + xjh + "%')";
    }
    return getSQLPageBean(sql);
  }

  public MsgBean updateShxs(String ksid) {
    try {
      ksid = ksid.substring(0, ksid.length() - 1);
      String sql = "update cus_kw_examinee set flag='1' where ksid in (" + ksid + ")";
      update(sql);
      return getMsgBean(true, "审核成功！");
    } catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "审核失败！");
  }

  public MsgBean updateScEaxm(String lcid, String score, String timelen, String start1, String start2, String start3, String start4, String start5, String start6)
  {
    try
    {
      List lsQb = getListSQL("select * from cus_kw_examinee where lcid='" + lcid + "'");
      List lsSj = getListSQL("select distinct SFZJH from cus_kw_examinee where lcid='" + lcid + "'");
      if (lsSj.size() != lsQb.size()) {
        return getMsgBean(false, "考生信息重复，不能自动生成考试批次！");
      }

      Integer lsCj = Integer.valueOf(Integer.parseInt(getSession().createSQLQuery("select count(1) from cus_kw_stuscore where lcid = '" + lcid + "'").uniqueResult().toString()));
      if (lsCj.intValue() > 0) {
        return getMsgBean(false, "已经产生答题信息的考试轮次，不能自动生成考试批次！");
      }
      List lsDKs = getListSQL("select * from cus_kw_examinee where lcid='" + lcid + "' and (XXDM is null or XXDM='')");
      if (lsDKs.size() > 0) {
        return getMsgBean(false, "考生信息缺参考单位请核对后，再自动生成考试批次！");
      }
      Pattern patt = Pattern.compile("^(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})$");
      if ((start1 != null) && (!"".equals(start1)) && 
        (!patt.matcher(start1).matches())) {
        return getMsgBean(false, "上午考试开始时间1格式不正确！");
      }

      if ((start2 != null) && (!"".equals(start2)) && 
        (!patt.matcher(start2).matches())) {
        return getMsgBean(false, "上午考试开始时间2格式不正确！");
      }

      if ((start3 != null) && (!"".equals(start3)) && 
        (!patt.matcher(start3).matches())) {
        return getMsgBean(false, "下午考试开始时间1格式不正确！");
      }

      if ((start4 != null) && (!"".equals(start4)) && 
        (!patt.matcher(start4).matches())) {
        return getMsgBean(false, "下午考试开始时间2格式不正确！");
      }

      if ((start5 != null) && (!"".equals(start5)) && 
        (!patt.matcher(start5).matches())) {
        return getMsgBean(false, "晚上考试开始时间1格式不正确！");
      }

      if ((start6 != null) && (!"".equals(start6)) && 
        (!patt.matcher(start6).matches())) {
        return getMsgBean(false, "晚上考试开始时间2格式不正确！");
      }

      List ls = getListSQL("select distinct c.XX_JBXX_ID from zxxs_xx_jbxx a left join cus_kw_examinee b on b.xxdm=a.xx_jbxx_id left join zxxs_xx_jbxx c on c.SSZGJYXZDM=a.SSZGJYXZDM where b.lcid='" + 
        lcid + "' and c.dwlx='1'");
      String xxdms = "";
      int maxPc = 0;
      Map apZw = new HashMap();
      Map apPc = new HashMap();
      Map apXs = new HashMap();
      if ((ls != null) && (ls.size() > 0)) {
        delete("delete from CUS_KW_POINTINFO where lcid='" + lcid + "'");
        delete("delete from CUS_KW_EXAMSCHOOL where lcid='" + lcid + "'");
        delete("delete from cus_kw_examroom where lcid='" + lcid + "'");
        delete("delete from cus_kw_examsubject where lcid='" + lcid + "'");
        delete("delete from cus_kw_examschedule where lcid='" + lcid + "'");
        delete("delete from cus_kw_roomstudent where lcid='" + lcid + "'");
        update("update cus_kw_examinee set kmid='' where lcid='" + lcid + "'");
        for (int i = 0; i < ls.size(); i++) {
          Map mp = (Map)ls.get(i);
          String xxdm = mp.get("XX_JBXX_ID") == null ? "" : mp.get("XX_JBXX_ID").toString();
          insert("insert into CUS_KW_POINTINFO (LCID, XXDM, APID) values ('" + 
            lcid + "', '" + xxdm + "', '" + StringUtil.getUUID() + "')");
          xxdms = xxdms + xxdm + ",";
        }
      }
      if (!"".equals(xxdms)) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String xxdmParam = xxdms.substring(0, xxdms.length() - 1);
        //修改人： 包敏   修改日期： 2018-06-04  修改原因： 关联考点
       // String sqlKd = "SELECT distinct xx.XXMC,rm.SCHOOLID,sum(rm.floor) LCS,sum(rm.seats) ZWS FROM cus_kw_room rm INNER JOIN zxxs_xx_jbxx xx ON rm.schoolid = xx.xx_jbxx_id WHERE rm.FLAG=1 and rm.schoolid in ('" + 
        //  xxdmParam.replaceAll(",", "','") + "') " + 
        //  "GROUP BY xx.xxmc,xx.xxbsm,xx.sszgjyxzdm,rm.schoolid " + 
        //  "ORDER BY xx.xxmc DESC";
        //String sqlKd = "SELECT distinct xx.XXMC,rm.SCHOOLID,sum(rm.floor) LCS,sum(rm.seats) ZWS FROM cus_kw_room rm INNER JOIN zxxs_xx_jbxx xx ON rm.schoolid = xx.xx_jbxx_id WHERE rm.FLAG=1 and ( rm.schoolid in ('" + 
        //        xxdmParam.replaceAll(",", "','") + "') " + 
        //		"or  rm.fjid in (select fjid from cus_kw_zk_room where school_id_qg in ('" +   xxdmParam.replaceAll(",", "','") +"') ) ) " +
        //        "GROUP BY xx.xxmc,xx.xxbsm,xx.sszgjyxzdm,rm.schoolid " + 
        //        "ORDER BY xx.xxmc DESC";
        
      String sqlKd = "SELECT distinct xx.XXMC,rm.SCHOOLID,sum(rm.floor) LCS,sum(rm.seats) ZWS FROM (select kw.* from cus_kw_room kw " + 
                     "union select r.fjid,r.roomcode,r.roomname,r.lfid,r.floor,r.seats,r.type_m, r.flag,zk.school_id_qg from  cus_kw_zk_room zk " +
                     "LEFT JOIN  cus_kw_room r on zk.fjid= r.fjid)  as rm INNER JOIN zxxs_xx_jbxx xx ON rm.schoolid = xx.xx_jbxx_id WHERE rm.FLAG=1 and rm.schoolid in ('" + xxdmParam.replaceAll(",", "','") + "') " + 
          "GROUP BY xx.xxmc,xx.xxbsm,xx.sszgjyxzdm,rm.schoolid " + 
          "ORDER BY xx.xxmc DESC";
      //修改人： 包敏   修改日期： 2018-06-04  修改原因： 关联考点
        List lsKd = getListSQL(sqlKd);
        if ((lsKd != null) && (lsKd.size() > 0)) {
          for (int i = 0; i < lsKd.size(); i++) {
            Map mp = (Map)lsKd.get(i);
            String xxmc = mp.get("XXMC") == null ? "" : mp.get("XXMC").toString(); 
            String schoolid = mp.get("SCHOOLID") == null ? "" : mp.get("SCHOOLID").toString();
            String lcs = mp.get("LCS") == null ? "0" : mp.get("LCS").toString();
            String zws = mp.get("ZWS") == null ? "0" : mp.get("ZWS").toString();
            insert("insert into CUS_KW_EXAMSCHOOL (LCID, XXDM, POINTNAME, ROOMCOUNT, SEATCOUNT, KDID) values ('" + 
              lcid + "', '" + StringUtil.getString(schoolid) + "', '" + StringUtil.getString(xxmc) + 
              "', " + lcs + ", " + zws + ", '" + StringUtil.getUUID() + "')");
          }
        }
        
        //修改人： 包敏   修改日期： 2018-06-04 修改原因： 关联考场
        //String sqlKc = "SELECT distinct sum(SEATS) SEATS,SCHOOLID,LFID from CUS_KW_ROOM WHERE FLAG=1 and SCHOOLID in ('" + 
        //  xxdmParam.replaceAll(",", "','") + "') " + 
        //  "and lfid in (select lf_id from cus_kw_building) group by SCHOOLID,LFID";
       // String sqlKc = "SELECT distinct sum(SEATS) SEATS,SCHOOLID,LFID from CUS_KW_ROOM WHERE FLAG=1 and ( SCHOOLID in ('" + 
       //         xxdmParam.replaceAll(",", "','") + "') " + 
       // 		"or fjid in (select fjid from cus_kw_zk_room where school_id_qg in ('" + xxdmParam.replaceAll(",", "','") + "'))) " +
       //         "and lfid in (select lf_id from cus_kw_building) group by SCHOOLID,LFID";
        String sqlKc = "SELECT distinct sum(SEATS) SEATS,SCHOOLID,LFID from (select kw.* from cus_kw_room kw " + 
                       "union  select r.fjid,r.roomcode,r.roomname,r.lfid,r.floor,r.seats,r.type_m, r.flag,zk.school_id_qg from  cus_kw_zk_room zk " +
                       "LEFT JOIN  cus_kw_room r on zk.fjid= r.fjid) as rm WHERE FLAG=1 and SCHOOLID in ('" + xxdmParam.replaceAll(",", "','") + "') " + 
                       "and lfid in (select lf_id from cus_kw_building) group by SCHOOLID,LFID";
        
        //修改人： 包敏   修改日期： 2018-06-04 修改原因： 关联考场
        
        List lsKc = getListSQL(sqlKc);
        Integer seatsR = Integer.valueOf(0);
        int zwsTotal;
        if ((lsKc != null) && (lsKc.size() > 0)) {
          for (int iii = 0; iii < lsKc.size(); iii++) {//娄底市值  常德所有
            Map mpZzw = (Map)lsKc.get(iii);

            String xxdm = mpZzw.get("SCHOOLID").toString();
            String lfid = mpZzw.get("LFID").toString();
            //修改人： 包敏  修改日期： 2018-06-04 修改原因： 关联考点
            //String sql = "SELECT distinct T1.POINTNAME,T2.BUILDNAME,T3.ROOMNAME,T3.FJID,case when T3.SEATS='' then 0 ELSE T3.SEATS end AS YXZWS,T4.MC AS JSLX,T1.KDID FROM CUS_KW_EXAMSCHOOL T1 LEFT JOIN CUS_KW_BUILDING T2 on T1.XXDM=T2.SCHOOL_ID_QG LEFT JOIN CUS_KW_ROOM T3 on (T2.LF_ID=T3.LFID and T3.FLAG=1) LEFT JOIN ZD_JSLX T4 on T3.TYPE_M=T4.DM WHERE T1.LCID='" + 
            //  lcid + "' AND T1.XXDM='" + xxdm + "' and T3.LFID='" + lfid + 
            //  "' ORDER BY T1.POINTNAME,T2.BUILDNAME,T3.ROOMNAME";
            String sql = "SELECT distinct T1.POINTNAME,T2.BUILDNAME,T3.ROOMNAME,T3.FJID,case when T3.SEATS='' then 0 ELSE T3.SEATS end AS YXZWS,T4.MC AS JSLX,T1.KDID FROM CUS_KW_EXAMSCHOOL T1 LEFT JOIN " +
            "(select LF_ID, SCHOOL_ID_QG,BUILDNAME from CUS_KW_BUILDING b " + 
            " union select LFID as LF_ID ,SCHOOL_ID_QG,(select BUILDNAME from CUS_KW_BUILDING where LF_ID= LFID) from cus_kw_zk_building c " +
            " )  T2 on T1.XXDM=T2.SCHOOL_ID_QG LEFT JOIN CUS_KW_ROOM T3 on (T2.LF_ID=T3.LFID and T3.FLAG=1) LEFT JOIN ZD_JSLX T4 on T3.TYPE_M=T4.DM WHERE T1.LCID='" + 
              lcid + "' AND T1.XXDM='" + xxdm + "' and T3.LFID='" + lfid + 
              "' ORDER BY T1.POINTNAME,T2.BUILDNAME,T3.ROOMNAME";
            //修改人： 包敏  修改日期： 2018-06-04 修改原因： 关联考点
            List list = getListSQL(sql);
            int lie = 1;
            if ((list != null) && (list.size() > 0)) {
              String sql1 = "SELECT IFNULL(MAX(kcbh),0) as MAXKCBH from CUS_KW_EXAMROOM where LCID='" + lcid + 
                "' and kdid in (SELECT kdid from CUS_KW_EXAMSCHOOL where xxdm='" + xxdm + "')";
              List max_kcbh = getSession().createSQLQuery(sql1).list();
              int maxKcbh = StringUtil.getInteger(max_kcbh.get(0)).intValue();
              for (int i = 0; i < list.size(); i++) {
                Map map = (Map)list.get(i);
                Integer yxzws = StringUtil.getInteger(map.get("YXZWS"));
                String roomid = StringUtil.getString(map.get("FJID"));
                String kdid = StringUtil.getString(map.get("KDID"));

                int hang = yxzws.intValue();
                maxKcbh++;
                // 武陵源，永定区  移到市直
                insert("insert into CUS_KW_EXAMROOM (LCID, KDID, ROOMID, SEATS, XS, YS, KCBH, KCID) values ('" + 
                  lcid + "', '" + kdid + "', '" + roomid + "', " + yxzws + ", " + hang + ", " + lie + ", '" + maxKcbh + "', '" + StringUtil.getUUID() + "')");
              }
            }
          }

          int ksnum = 0;
          zwsTotal = 0;
          String sqlZw = "select sum(ifnull(a.SEATS,0)) ZSEATS,a.KDID,cast(GROUP_CONCAT(CONCAT(a.KCID,'#',a.SEATS) order by a.KCBH) as char) KCIDS,(select xxdm from CUS_KW_EXAMSCHOOL where kdid=a.kdid) as XXDM from CUS_KW_EXAMROOM a where a.lcid='" + 
            lcid + 
            "' and a.kdid in (SELECT kdid from CUS_KW_EXAMSCHOOL where xxdm in ('" + xxdmParam.replaceAll(",", "','") + "')) group by a.KDID";
          List lsZw = getListSQL(sqlZw);
          if ((lsZw != null) && (lsZw.size() > 0)) {
            for (int b = 0; b < lsZw.size(); b++) {
              Map mpZw = (Map)lsZw.get(b);
              Integer zwsZ = StringUtil.getInteger(mpZw.get("ZSEATS").toString());
              String kdid = mpZw.get("KDID").toString();
              String kcid = mpZw.get("KCIDS").toString();
              String xxdm = mpZw.get("XXDM").toString();
              String sqlKs = "select a.KSID,a.KSCODE,a.XJH,a.XM,a.XBM,a.SFZJH,(select SSZGJYXZDM from zxxs_xx_jbxx where xx_jbxx_id=a.XXDM) as SSZGJYXZDM from cus_kw_examinee a where a.lcid='" + 
                lcid + 
                "' and EXISTS(select 'X' from zxxs_xx_jbxx aa where aa.xx_jbxx_id=a.xxdm " + 
                "and aa.SSZGJYXZDM in (select SSZGJYXZDM from zxxs_xx_jbxx where dwlx='1' and xx_jbxx_id='" + xxdm + "')) order by a.xxdm";
              List lsKs = getListSQL(sqlKs);
              ksnum += lsKs.size();
              zwsTotal += zwsZ.intValue();
              if (zwsZ.intValue() > 0) {
                apXs.put(xxdm + ";" + kdid + ";" + kcid, lsKs);
              }
              String[] kcidAry = kcid.split(",");
              for (int jjj = 0; jjj < kcidAry.length; jjj++)
                if ((kcidAry[jjj] != null) && (!"".equals(kcidAry[jjj]))) {
                  String kcidStr = kcidAry[jjj].split("#")[0];
                  apZw.put(kcidStr, zwsZ.toString());
                }
            }
            if (zwsTotal > 0) {
              int maxPcCur = Math.round(new Float(Math.ceil(ksnum / zwsTotal)).floatValue()) + (ksnum % zwsTotal > 0 ? 1 : 0);
              if (maxPcCur > maxPc) {
                maxPc = maxPcCur;
              }
            }
          }
          if (maxPc > 0) {
            String xn = "";
            String xq = "";
            String xnxq = "";
            String startdate = "";
            String ts = "";
            String sqlLc = "select xn,xqm,concat(xn,'年度 第',xqm,'季') xqmc,startdate,datediff(enddate,startdate) ts from cus_kw_examround where lcid='" + 
              lcid + "'";
            List lsLc = getListSQL(sqlLc);
            if (lsLc.size() > 0) {
              Map mp = (Map)lsLc.get(0);
              xn = mp.get("xn").toString();
              xq = mp.get("xqm").toString();
              xnxq = mp.get("xqmc").toString();
              startdate = mp.get("startdate").toString();
              ts = mp.get("ts").toString();
            }

            int tsNum = Integer.valueOf(ts).intValue();
            int dayPc = 0;
            int curT = 0;
            int curT1 = 0;
            int curTPC = 0;
            int curTPC1 = 0;
            int curTPC2 = 0;
            int curTPC3 = 0;
            int curTPC4 = 0;
            int curTPC5 = 0;
            int curTPC6 = 0;
            if ((start1 != null) && (!"".equals(start1))) {
              curTPC++;
              curTPC1++;
              curTPC2++;
              curTPC3++;
              curTPC4++;
              curTPC5++;
              curTPC6++;
            }
            if ((start2 != null) && (!"".equals(start2))) {
              curTPC++;
              curTPC2++;
              curTPC3++;
              curTPC4++;
              curTPC5++;
              curTPC6++;
            }
            if ((start3 != null) && (!"".equals(start3))) {
              curTPC++;
              curTPC3++;
              curTPC4++;
              curTPC5++;
              curTPC6++;
            }
            if ((start4 != null) && (!"".equals(start4))) {
              curTPC++;
              curTPC4++;
              curTPC5++;
              curTPC6++;
            }
            if ((start5 != null) && (!"".equals(start5))) {
              curTPC++;
              curTPC5++;
              curTPC6++;
            }
            if ((start6 != null) && (!"".equals(start6))) {
              curTPC++;
              curTPC6++;
            }
            String eDT = "";
            int iii = 0;
            for (int ii = 1; ii <= maxPc; ii++) {
              String curKmid = StringUtil.getUUID();
              insert("insert into CUS_KW_EXAMSUBJECT (XNXQ_ID, SUBJECTNAME, RESULTSTYLE, KCH, LCID, SCORE, TIMELENGTH, XN, XQ, KMID) values ('" + 
                xnxq + "', '公共法律知识测试-第" + ii + "批', '1', '公共法律知识测试', '" + lcid + "', " + score + ", " + timelen + ", '" + xn + "', '" + xq + "', '" + curKmid + "')");

              Date sD = formatD.parse(startdate);
              cal.setTime(sD);
              if (curT == 0) curT1 = curT;
              cal.add(5, curT1);
              int w = cal.get(7) - 1;

              String curDT = formatT.format(cal.getTime());
              String curD = formatD.format(cal.getTime());
              String sDT = "";
              if ((start1 != null) && (!"".equals(start1)) && (dayPc == curTPC1 - 1)) {
                sDT = curD + " " + start1;
              }
              if ((start2 != null) && (!"".equals(start2)) && (dayPc == curTPC2 - 1)) {
                sDT = curD + " " + start2;
                if ((!"".equals(eDT)) && (formatT.parse(sDT).before(formatT.parse(eDT)))) {
                  return getMsgBean(false, "考试安排时间有误，生成失败！");
                }
              }
              if ((start3 != null) && (!"".equals(start3)) && (dayPc == curTPC3 - 1)) {
                sDT = curD + " " + start3;
                if ((!"".equals(eDT)) && (formatT.parse(sDT).before(formatT.parse(eDT)))) {
                  return getMsgBean(false, "考试安排时间有误，生成失败！");
                }
              }
              if ((start4 != null) && (!"".equals(start4)) && (dayPc == curTPC4 - 1)) {
                sDT = curD + " " + start4;
                if ((!"".equals(eDT)) && (formatT.parse(sDT).before(formatT.parse(eDT)))) {
                  return getMsgBean(false, "考试安排时间有误，生成失败！");
                }
              }
              if ((start5 != null) && (!"".equals(start5)) && (dayPc == curTPC5 - 1)) {
                sDT = curD + " " + start5;
                if ((!"".equals(eDT)) && (formatT.parse(sDT).before(formatT.parse(eDT)))) {
                  return getMsgBean(false, "考试安排时间有误，生成失败！");
                }
              }
              if ((start6 != null) && (!"".equals(start6)) && (dayPc == curTPC6 - 1)) {
                sDT = curD + " " + start6;
                if ((!"".equals(eDT)) && (formatT.parse(sDT).before(formatT.parse(eDT)))) {
                  return getMsgBean(false, "考试安排时间有误，生成失败！");
                }
              }
              if (!"".equals(sDT)) {
                Date sDate = formatT.parse(sDT);
                cal.setTime(sDate);
                cal.add(12, Integer.valueOf(timelen).intValue());
                eDT = formatT.format(cal.getTime());
                insert("insert into cus_kw_examschedule(RCID,LCID,KMID,EXAMDATE,STARTTIME,ENDTIME,TIMELENGTH) values ('" + 
                  StringUtil.getUUID() + "','" + lcid + "','" + curKmid + "','" + curDT + ":00','" + sDT + ":00','" + eDT + ":00'," + timelen + ")");
                apPc.put(iii, curKmid);
                dayPc++;
                iii++;
                if (dayPc == curTPC) {
                  curT++;
                  curT1++;
                  dayPc = 0;
                }
              }
            }
            List lsCur = getListSQL("SELECT DWID,STARTDATE,ENDDATE from cus_kw_examround where lcid='" + lcid + "'");
            if (lsCur.size() > 0) {
              Map mpCur = (Map)lsCur.get(0);
              String dwid = mpCur.get("DWID").toString();
              String startlc = mpCur.get("STARTDATE").toString();
              String endlc = mpCur.get("ENDDATE").toString();
              List lsCk = getListSQL("SELECT * from cus_kw_examround where lcid<>'" + lcid + "' and dwid='" + dwid + 
                "' and ((STARTDATE<=date('" + startlc + "') and ENDDATE>=date('" + startlc + "')) or (STARTDATE<=date('" + endlc + "') and ENDDATE>=date('" + endlc + 
                "')) or (STARTDATE>=date('" + startlc + "') and ENDDATE<=date('" + endlc + "')))");
              String endDT = formatD.format(cal.getTime());
              if (lsCk.size() > 0) {
                return getMsgBean(false, "考试轮次时间段无法安排" + lsSj.size() + "人考试，请把时间延迟到：" + endDT + "！");
              }
              if (!endDT.equals(endlc))
                update("update cus_kw_examround set enddate='" + endDT + "' where lcid='" + lcid + "'");
            }
          }
          else {
            return getMsgBean(false, "考场座位不足以安排考生，生成失败！");
          }
        } else {
          return getMsgBean(false, "未设置考场座位信息，生成失败！");
        }
        Set<String> set = apXs.keySet();   
        for (String Key : set) {
          String xxdm = Key.split(";")[0];
          String kdid = Key.split(";")[1];
          String kcids = Key.split(";")[2];
          String[] kcidAry = kcids.split(",");
          List lsKs = (List)apXs.get(Key);
          for (int j = 0; j < maxPc; j++) {
            String curKmid = (String)apPc.get(j);
            int curTzws = 0;
            for (int jj = 0; jj < kcidAry.length; jj++) {
              String kcid = kcidAry[jj].split("#")[0];
              String zws = kcidAry[jj].split("#")[1];
              int zwsCTotal = Integer.valueOf((String)apZw.get(kcid)).intValue();
              if (zwsCTotal * j <= lsKs.size()) {
                List tmpLstP = lsKs.subList(zwsCTotal * j, zwsCTotal * (j + 1) > lsKs.size() ? lsKs.size() : zwsCTotal * (j + 1));

                int zwsI = Integer.valueOf(zws).intValue();
                if (curTzws <= tmpLstP.size()) {
                  List tmpLst = tmpLstP.subList(curTzws, curTzws + zwsI > tmpLstP.size() ? tmpLstP.size() : curTzws + zwsI);
                  curTzws += zwsI;
                  for (int jjj = 0; jjj < tmpLst.size(); jjj++) {
                    Map mpKs = (Map)tmpLst.get(jjj);
                    String ksid = mpKs.get("KSID") == null ? "" : mpKs.get("KSID").toString();
                    String kscode = mpKs.get("KSCODE") == null ? "" : mpKs.get("KSCODE").toString();
                    String xh = mpKs.get("XJH") == null ? "" : mpKs.get("XJH").toString();
                    String xm = mpKs.get("XM") == null ? "" : mpKs.get("XM").toString();
                    String zkdwm = mpKs.get("SSZGJYXZDM") == null ? "" : mpKs.get("SSZGJYXZDM").toString();
                    String xbm = mpKs.get("XBM") == null ? "" : mpKs.get("XBM").toString();
                    String sfzjh = mpKs.get("SFZJH") == null ? "" : mpKs.get("SFZJH").toString();

                    List lstU = getListSQL("select xs_jbxx_id from zxxs_xs_jbxx where grbsm='" + xh + "'");
                    String xs_jbxx_id = "";
                    if (lstU.size() <= 0) {
                      xs_jbxx_id = StringUtil.getUUID();
                      insert("insert into zxxs_xs_jbxx(xs_jbxx_id,grbsm,xm,xbm,sfzjlxm,sfzjh,xx_jbxx_id,xxsszgjyxzdm) values('" + 
                        xs_jbxx_id + "','" + xh + "','" + xm + "','" + xbm + "','1','" + sfzjh + 
                        "','" + xxdm + "','" + zkdwm + "')");
                      List lstUQ = getListSQL("select USERCODE from T_QXGL_USERINFO where usertype='3' and LOGINID='" + sfzjh + "'");
                      if (lstUQ.size() <= 0) {
                        String pass = sfzjh.substring(sfzjh.length() - 6, sfzjh.length());
                        insert("insert into T_QXGL_USERINFO(usercode,username,usertype,loginid,loginpwd,flage) values('" + 
                          xs_jbxx_id + "','" + xm + "','3','" + sfzjh + "',password('" + pass + "'),'1')");
                      }
                    } else {
                      xs_jbxx_id = ((String)((Map)lstU.get(0)).get("xs_jbxx_id")).toString();
                    }
                    update("update cus_kw_examinee set kmid='" + curKmid + "',xs_jbxx_id='" + xs_jbxx_id + 
                      "',kdid='" + kdid + "',kcid='" + kcid + "',zwh=" + (jjj + 1) + " where KSID='" + ksid + "'");
                    System.out.println("111111111111111111111111:"+xh);
                    insert("insert into CUS_KW_ROOMSTUDENT (KCID,KDID,LCID,KMID,XH,KSCODE,X,Y,ZWH,APID) values('" + 
                      kcid + "','" + kdid + "','" + lcid + "','" + curKmid + "','" + xh + "','" + kscode + "'," + (jjj + 1) + ",1," + (jjj + 1) + ",'" + StringUtil.getUUID() + "')");
                  }
                  if (curTzws * (j + 1) <= lsKs.size()) continue;
                }
              }
            }
          }
        }
        if (maxPc == 0) {
          return getMsgBean(true, "没有考生可以安排考试！");
        }
        return getMsgBean(true, "生成" + maxPc + "批次成功！");
      }

      if (maxPc == 0) {
        return getMsgBean(true, "没有考生可以安排考试！");
      }
      return getMsgBean(true, "生成" + maxPc + "批次成功！");
    }
    catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "生成失败！");
  }

  public MsgBean updateScBEaxm(String lcid, String score, String timelen, String start1, String start2, String start3, String start4, String start5, String start6)
  {
    try
    {
      List lsQb = getListSQL("select * from cus_kw_examinee where lcid='" + lcid + "'");
      List lsSj = getListSQL("select distinct SFZJH from cus_kw_examinee where lcid='" + lcid + "'");
      if (lsSj.size() != lsQb.size()) {
        return getMsgBean(false, "考生信息重复，不能补排考试批次！");
      }
      List lsDKs = getListSQL("select * from cus_kw_examinee where lcid='" + lcid + "' and (XXDM is null or XXDM='')");
      if (lsDKs.size() > 0) {
        return getMsgBean(false, "考生信息缺参考单位请核对后，再补排考试批次！");
      }
      Pattern patt = Pattern.compile("^(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})$");
      if ((start1 != null) && (!"".equals(start1)) && 
        (!patt.matcher(start1).matches())) {
        return getMsgBean(false, "上午考试开始时间1格式不正确！");
      }

      if ((start2 != null) && (!"".equals(start2)) && 
        (!patt.matcher(start2).matches())) {
        return getMsgBean(false, "上午考试开始时间2格式不正确！");
      }

      if ((start3 != null) && (!"".equals(start3)) && 
        (!patt.matcher(start3).matches())) {
        return getMsgBean(false, "下午考试开始时间1格式不正确！");
      }

      if ((start4 != null) && (!"".equals(start4)) && 
        (!patt.matcher(start4).matches())) {
        return getMsgBean(false, "下午考试开始时间2格式不正确！");
      }

      if ((start5 != null) && (!"".equals(start5)) && 
        (!patt.matcher(start5).matches())) {
        return getMsgBean(false, "晚上考试开始时间1格式不正确！");
      }

      if ((start6 != null) && (!"".equals(start6)) && 
        (!patt.matcher(start6).matches())) {
        return getMsgBean(false, "晚上考试开始时间2格式不正确！");
      }

      List lsPc = getListSQL("select * from cus_kw_examsubject where lcid='" + lcid + "'");
      String xxdms = "";
      int maxPc = 0;
      Map apZw = new HashMap();
      Map apPc = new HashMap();
      Map apXs = new HashMap();
      List ls = getListSQL("select distinct c.XX_JBXX_ID from zxxs_xx_jbxx a left join cus_kw_examinee b on b.xxdm=a.xx_jbxx_id left join zxxs_xx_jbxx c on c.SSZGJYXZDM=a.SSZGJYXZDM where b.lcid='" + 
        lcid + "' and c.dwlx='1' and (b.kmid='' or b.kmid='null' or b.kmid is null)");
      for (int i = 0; i < ls.size(); i++) {
        Map mp = (Map)ls.get(i);
        String xxdm = mp.get("XX_JBXX_ID") == null ? "" : mp.get("XX_JBXX_ID").toString();
        xxdms = xxdms + xxdm + ",";
        delete("delete from cus_kw_roomstudent where lcid='" + lcid + "' and kmid='null'");
      }
      if (!"".equals(xxdms)) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String xxdmParam = xxdms.substring(0, xxdms.length() - 1);
        Integer seatsR = Integer.valueOf(0);
        int ksnum = 0;
        int zwsTotal = 0;
        String sqlZw = "select sum(ifnull(a.SEATS,0)) ZSEATS,a.KDID,cast(GROUP_CONCAT(CONCAT(a.KCID,'#',a.SEATS) order by a.KCBH) as char) KCIDS,(select xxdm from CUS_KW_EXAMSCHOOL where kdid=a.kdid) as XXDM from CUS_KW_EXAMROOM a where a.lcid='" + 
          lcid + 
          "' and a.kdid in (SELECT kdid from CUS_KW_EXAMSCHOOL where xxdm in ('" + xxdmParam.replaceAll(",", "','") + "')) group by a.KDID";
        List lsZw = getListSQL(sqlZw);
        if ((lsZw != null) && (lsZw.size() > 0)) {
          for (int b = 0; b < lsZw.size(); b++) {
            Map mpZw = (Map)lsZw.get(b);
            Integer zwsZ = StringUtil.getInteger(mpZw.get("ZSEATS").toString());
            String kdid = mpZw.get("KDID").toString();
            String kcid = mpZw.get("KCIDS").toString();
            String xxdm = mpZw.get("XXDM").toString();
            String sqlKs = "select a.KSID,a.KSCODE,a.XJH,a.XM,a.XBM,a.SFZJH,(select SSZGJYXZDM from zxxs_xx_jbxx where xx_jbxx_id=a.XXDM) as SSZGJYXZDM from cus_kw_examinee a where a.lcid='" + 
              lcid + 
              "' and (a.kmid='' or a.kmid='null' or a.kmid is null) order by RAND()";
            List lsKs = getListSQL(sqlKs);
            ksnum += lsKs.size();
            zwsTotal += zwsZ.intValue();
            if (zwsZ.intValue() > 0) {
              apXs.put(xxdm + ";" + kdid + ";" + kcid, lsKs);
            }
            String[] kcidAry = kcid.split(",");
            for (int jjj = 0; jjj < kcidAry.length; jjj++)
              if ((kcidAry[jjj] != null) && (!"".equals(kcidAry[jjj]))) {
                String kcidStr = kcidAry[jjj].split("#")[0];
                apZw.put(kcidStr, zwsZ.toString());
              }
          }
          if (zwsTotal > 0) {
            int maxPcCur = Math.round(new Float(Math.ceil(ksnum / zwsTotal)).floatValue()) + (ksnum % zwsTotal > 0 ? 1 : 0);
            if (maxPcCur > maxPc) {
              maxPc = maxPcCur;
            }
          }
        }
        maxPc += lsPc.size();
        String xq;
        if (maxPc > lsPc.size()) {
          String xn = "";
          xq = "";
          String xnxq = "";
          String startdate = "";
          String ts = "";
          String sqlLc = "select xn,xqm,concat(xn,'年度 第',xqm,'季') xqmc,enddate as startdate,1 as ts from cus_kw_examround where lcid='" + 
            lcid + "'";
          List lsLc = getListSQL(sqlLc);
          if (lsLc.size() > 0) {
            Map mp = (Map)lsLc.get(0);
            xn = mp.get("xn").toString();
            xq = mp.get("xqm").toString();
            xnxq = mp.get("xqmc").toString();
            startdate = mp.get("startdate").toString();
            ts = mp.get("ts").toString();
          }

          int tsNum = Integer.valueOf(ts).intValue();
          int dayPc = 0;
          int curT = 0;
          int curT1 = 0;
          int curTPC = 0;
          int curTPC1 = 0;
          int curTPC2 = 0;
          int curTPC3 = 0;
          int curTPC4 = 0;
          int curTPC5 = 0;
          int curTPC6 = 0;
          if ((start1 != null) && (!"".equals(start1))) {
            curTPC++;
            curTPC1++;
            curTPC2++;
            curTPC3++;
            curTPC4++;
            curTPC5++;
            curTPC6++;
          }
          if ((start2 != null) && (!"".equals(start2))) {
            curTPC++;
            curTPC2++;
            curTPC3++;
            curTPC4++;
            curTPC5++;
            curTPC6++;
          }
          if ((start3 != null) && (!"".equals(start3))) {
            curTPC++;
            curTPC3++;
            curTPC4++;
            curTPC5++;
            curTPC6++;
          }
          if ((start4 != null) && (!"".equals(start4))) {
            curTPC++;
            curTPC4++;
            curTPC5++;
            curTPC6++;
          }
          if ((start5 != null) && (!"".equals(start5))) {
            curTPC++;
            curTPC5++;
            curTPC6++;
          }
          if ((start6 != null) && (!"".equals(start6))) {
            curTPC++;
            curTPC6++;
          }
          String eDT = "";
          int iii = 0;
          for (int ii = lsPc.size() + 1; ii <= maxPc; ii++) {
            String curKmid = StringUtil.getUUID();
            insert("insert into CUS_KW_EXAMSUBJECT (XNXQ_ID, SUBJECTNAME, RESULTSTYLE, KCH, LCID, SCORE, TIMELENGTH, XN, XQ, KMID) values ('" + 
              xnxq + "', '公共法律知识测试-第" + ii + "批', '1', '公共法律知识测试', '" + lcid + "', " + score + ", " + timelen + ", '" + xn + "', '" + xq + "', '" + curKmid + "')");

            Date sD = formatD.parse(startdate);
            cal.setTime(sD);
            if (curT == 0) curT1 = curT;
            cal.add(5, curT1);
            int w = cal.get(7) - 1;
            if (w == 6) {
              curT += 2;
              curT1 += 2;
              cal.add(5, 2);
            }
            if (w == 0) {
              curT1++;
              if (ii % curTPC == 1) {
                cal.add(5, 1);
              }
            }
            String curDT = formatT.format(cal.getTime());
            String curD = formatD.format(cal.getTime());
            String sDT = "";
            if ((start1 != null) && (!"".equals(start1)) && (dayPc == curTPC1 - 1)) {
              sDT = curD + " " + start1;
            }
            if ((start2 != null) && (!"".equals(start2)) && (dayPc == curTPC2 - 1)) {
              sDT = curD + " " + start2;
              if ((!"".equals(eDT)) && (formatT.parse(sDT).before(formatT.parse(eDT)))) {
                return getMsgBean(false, "考试安排时间有误，补排失败！");
              }
            }
            if ((start3 != null) && (!"".equals(start3)) && (dayPc == curTPC3 - 1)) {
              sDT = curD + " " + start3;
              if ((!"".equals(eDT)) && (formatT.parse(sDT).before(formatT.parse(eDT)))) {
                return getMsgBean(false, "考试安排时间有误，补排失败！");
              }
            }
            if ((start4 != null) && (!"".equals(start4)) && (dayPc == curTPC4 - 1)) {
              sDT = curD + " " + start4;
              if ((!"".equals(eDT)) && (formatT.parse(sDT).before(formatT.parse(eDT)))) {
                return getMsgBean(false, "考试安排时间有误，生成失败！");
              }
            }
            if ((start5 != null) && (!"".equals(start5)) && (dayPc == curTPC5 - 1)) {
              sDT = curD + " " + start5;
              if ((!"".equals(eDT)) && (formatT.parse(sDT).before(formatT.parse(eDT)))) {
                return getMsgBean(false, "考试安排时间有误，补排失败！");
              }
            }
            if ((start6 != null) && (!"".equals(start6)) && (dayPc == curTPC6 - 1)) {
              sDT = curD + " " + start6;
              if ((!"".equals(eDT)) && (formatT.parse(sDT).before(formatT.parse(eDT)))) {
                return getMsgBean(false, "考试安排时间有误，补排失败！");
              }
            }
            if (!"".equals(sDT)) {
              Date sDate = formatT.parse(sDT);
              cal.setTime(sDate);
              cal.add(12, Integer.valueOf(timelen).intValue());
              eDT = formatT.format(cal.getTime());
              insert("insert into cus_kw_examschedule(RCID,LCID,KMID,EXAMDATE,STARTTIME,ENDTIME,TIMELENGTH) values ('" + 
                StringUtil.getUUID() + "','" + lcid + "','" + curKmid + "','" + curDT + ":00','" + sDT + ":00','" + eDT + ":00'," + timelen + ")");
              apPc.put(iii, curKmid);
              dayPc++;
              iii++;
              if (dayPc == curTPC) {
                curT++;
                curT1++;
                dayPc = 0;
              }
            }
          }
          List lsCur = getListSQL("SELECT DWID,STARTDATE,ENDDATE from cus_kw_examround where lcid='" + lcid + "'");
          if (lsCur.size() > 0) {
            Map mpCur = (Map)lsCur.get(0);
            String dwid = mpCur.get("DWID").toString();
            String startlc = mpCur.get("STARTDATE").toString();
            String endlc = mpCur.get("ENDDATE").toString();
            String endDT = formatD.format(cal.getTime());
            if (!endDT.equals(endlc))
              update("update cus_kw_examround set enddate='" + endDT + "' where lcid='" + lcid + "'");
          }
        }
        else {
          return getMsgBean(false, "考场座位不足以安排考生，补排失败！");
        }
        Set<String> set = apXs.keySet();   
        for (String Key : set) {
          String xxdm = Key.split(";")[0];
          String kdid = Key.split(";")[1];
          String kcids = Key.split(";")[2];
          String[] kcidAry = kcids.split(",");
          List lsKs = (List)apXs.get(Key);
          int iii = 0;
          for (int j = lsPc.size(); j < maxPc; j++) {
            String curKmid = (String)apPc.get(iii);
            iii++;
            int curTzws = 0;
            for (int jj = 0; jj < kcidAry.length; jj++) {
              String kcid = kcidAry[jj].split("#")[0];
              String zws = kcidAry[jj].split("#")[1];
              int zwsCTotal = Integer.valueOf((String)apZw.get(kcid)).intValue();
              if (zwsCTotal * (j - lsPc.size()) <= lsKs.size()) {
                List tmpLstP = lsKs.subList(zwsCTotal * (j - lsPc.size()), zwsCTotal * (j - lsPc.size() + 1) > lsKs.size() ? lsKs.size() : zwsCTotal * (j - lsPc.size() + 1));

                int zwsI = Integer.valueOf(zws).intValue();
                if (curTzws <= tmpLstP.size()) {
                  List tmpLst = tmpLstP.subList(curTzws, curTzws + zwsI > tmpLstP.size() ? tmpLstP.size() : curTzws + zwsI);
                  curTzws += zwsI;
                  for (int jjj = 0; jjj < tmpLst.size(); jjj++) {
                    Map mpKs = (Map)tmpLst.get(jjj);
                    String ksid = mpKs.get("KSID") == null ? "" : mpKs.get("KSID").toString();
                    String kscode = mpKs.get("KSCODE") == null ? "" : mpKs.get("KSCODE").toString();
                    String xh = mpKs.get("XJH") == null ? "" : mpKs.get("XJH").toString();
                    String xm = mpKs.get("XM") == null ? "" : mpKs.get("XM").toString();
                    String zkdwm = mpKs.get("SSZGJYXZDM") == null ? "" : mpKs.get("SSZGJYXZDM").toString();
                    String xbm = mpKs.get("XBM") == null ? "" : mpKs.get("XBM").toString();
                    String sfzjh = mpKs.get("SFZJH") == null ? "" : mpKs.get("SFZJH").toString();

                    List lstU = getListSQL("select xs_jbxx_id from zxxs_xs_jbxx where grbsm='" + xh + "'");
                    String xs_jbxx_id = "";
                    if (lstU.size() <= 0) {
                      xs_jbxx_id = StringUtil.getUUID();
                      insert("insert into zxxs_xs_jbxx(xs_jbxx_id,grbsm,xm,xbm,sfzjlxm,sfzjh,xx_jbxx_id,xxsszgjyxzdm) values('" + 
                        xs_jbxx_id + "','" + xh + "','" + xm + "','" + xbm + "','1','" + sfzjh + 
                        "','" + xxdm + "','" + zkdwm + "')");
                      List lstUQ = getListSQL("select USERCODE from T_QXGL_USERINFO where usertype='3' and LOGINID='" + sfzjh + "'");
                      if (lstUQ.size() <= 0) {
                        String pass = sfzjh.substring(sfzjh.length() - 6, sfzjh.length());
                        insert("insert into T_QXGL_USERINFO(usercode,username,usertype,loginid,loginpwd,flage) values('" + 
                          xs_jbxx_id + "','" + xm + "','3','" + sfzjh + "',password('" + pass + "'),'1')");
                      }
                    } else {
                      xs_jbxx_id = ((String)((Map)lstU.get(0)).get("xs_jbxx_id")).toString();
                    }
                    update("update cus_kw_examinee set kmid='" + curKmid + "',xs_jbxx_id='" + xs_jbxx_id + 
                      "',kdid='" + kdid + "',kcid='" + kcid + "',zwh=" + (jjj + 1) + " where KSID='" + ksid + "'");
                    insert("insert into CUS_KW_ROOMSTUDENT (KCID,KDID,LCID,KMID,XH,KSCODE,X,Y,ZWH,APID) values('" + 
                      kcid + "','" + kdid + "','" + lcid + "','" + curKmid + "','" + xh + "','" + kscode + "'," + (jjj + 1) + ",1," + (jjj + 1) + ",'" + StringUtil.getUUID() + "')");
                  }
                  if (curTzws * (j + 1) <= lsKs.size()) continue;
                }
              }
            }
          }
        }
        if (maxPc == lsPc.size()) {
          return getMsgBean(true, "没有考生可以重新安排考试！");
        }
        return getMsgBean(true, "补排生成" + (maxPc - lsPc.size()) + "批次成功！");
      }

      if (maxPc == lsPc.size()) {
        return getMsgBean(true, "没有考生可以重新安排考试！");
      }
      return getMsgBean(true, "补排生成" + (maxPc - lsPc.size()) + "批次成功！");
    }
    catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "补排失败！");
  }

  public MsgBean updateCFEaxminee()
  {
    try {
      String sqlCx1 = "select b.ksid1 from (select sfzjh,min(ksid) ksid1 from cus_kw_examinee where sfzjh in (select a.sfzjh from (select sfzjh,kscode,lcid,count(sfzjh) from cus_kw_examinee group by sfzjh,kscode,lcid having count(sfzjh)>1) a) group by sfzjh) b";

      List lsCx1 = getListSQL(sqlCx1);
      if (lsCx1 != null) {
        for (int i = 0; i < lsCx1.size(); i++) {
          Map mp = (Map)lsCx1.get(i);
          String ksid = mp.get("ksid1") == null ? "" : mp.get("ksid1").toString();
          delete("delete from cus_kw_examinee where ksid='" + ksid + "'");
        }
      }
      String sqlCx2 = "select b.usercode1 from (select LOGINID,min(usercode) usercode1 from t_qxgl_userinfo where loginid in (select a.loginid from (select loginid,count(loginid) from t_qxgl_userinfo group by loginid having count(loginid)>1) a) group by LOGINID) b";

      List lsCx2 = getListSQL(sqlCx2);
      if (lsCx2 != null) {
        for (int i = 0; i < lsCx2.size(); i++) {
          Map mp = (Map)lsCx2.get(i);
          String usercode = mp.get("usercode1") == null ? "" : mp.get("usercode1").toString();
          delete("delete from t_qxgl_userinfo where usercode='" + usercode + "'");
        }
      }
      String sqlCx3 = "select b.xs_jbxx_id1 from (select sfzjh,min(xs_jbxx_id) xs_jbxx_id1 from zxxs_xs_jbxx where sfzjh in (select a.sfzjh from (select sfzjh,count(sfzjh) from zxxs_xs_jbxx group by sfzjh having count(sfzjh)>1) a) group by sfzjh) b";

      List lsCx3 = getListSQL(sqlCx3);
      if (lsCx3 != null) {
        for (int i = 0; i < lsCx3.size(); i++) {
          Map mp = (Map)lsCx3.get(i);
          String xsjbxxid = mp.get("xs_jbxx_id1") == null ? "" : mp.get("xs_jbxx_id1").toString();
          delete("delete from zxxs_xs_jbxx where xs_jbxx_id='" + xsjbxxid + "'");
        }
      }
      return getMsgBean(true, "校验成功！");
    } catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "校验失败！");
  }

  public MsgBean updateZPEaxminee(String distPath)
  {
    try {
      String sqlCx = "select xs_jbxx_id,path from zxxs_xs_pic";
      List lsCx = getListSQL(sqlCx);
      if (lsCx != null) {
        for (int i = 0; i < lsCx.size(); i++) {
          Map mp = (Map)lsCx.get(i);
          String xs_jbxx_id = mp.get("xs_jbxx_id") == null ? "" : mp.get("xs_jbxx_id").toString();
          String path = mp.get("path") == null ? "" : mp.get("path").toString();
          String[] pathAry = path.split("_");
          if (pathAry.length > 1) {
            String pathTmp = pathAry[1];
            String num = pathTmp.split("\\.")[0];
            if (("".equals(num)) || 
              (Integer.valueOf(num).intValue() <= 0)) continue;
            for (int ii = Integer.valueOf(num).intValue() - 1; ii >= 0; ii--) {
              if (pathTmp.split("\\.").length > 1) {
                String fileStr = pathAry[0] + "_" + ii + "." + pathTmp.split("\\.")[1];
                File tempI = new File(distPath + File.separator + StringUtil.ReplaceAll_Z(fileStr));
                if (tempI.isFile()) {
                  tempI.delete();
                }
              }
            }
          }
        }
      }

      String sqlCx1 = "select xs_jbxx_id,path from zxxs_xs_pic where XS_JBXX_ID not in (select XS_JBXX_ID from zxxs_xs_jbxx)";
      List lsCx1 = getListSQL(sqlCx1);
      if (lsCx1 != null) {
        for (int i = 0; i < lsCx1.size(); i++) {
          Map mp = (Map)lsCx1.get(i);
          String xs_jbxx_id = mp.get("xs_jbxx_id") == null ? "" : mp.get("xs_jbxx_id").toString();
          String path = mp.get("path") == null ? "" : mp.get("path").toString();
          String fileStr = distPath + File.separator + StringUtil.ReplaceAll_Z(path);
          delete("delete from zxxs_xs_pic where xs_jbxx_id='" + xs_jbxx_id + "'");
          File temp = new File(fileStr);
          if (temp.isFile()) {
            temp.delete();
          }
        }
      }
      return getMsgBean(true, "清除成功！");
    } catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "清除失败！");
  }

  public MsgBean updateBBEaxminee()
  {
    try {
      String sqlCx = "select lcid,count(ksid) ksnum  from cus_kw_examinee where lcid in (select lcid from cus_kw_examround where SHFLAG='1') and (kmid='' or kmid is null) group by lcid";

      String msg = "";
      List lsCx = getListSQL(sqlCx);
      if (lsCx != null) {
        for (int i = 0; i < lsCx.size(); i++) {
          Map mp = (Map)lsCx.get(i);
          String lcid = mp.get("lcid") == null ? "" : mp.get("lcid").toString();
          String ksnum = mp.get("ksnum") == null ? "" : mp.get("ksnum").toString();
          String sqlKc = "select kmid,count(ksid) ksnum,(select sum(seats) from cus_kw_examroom where lcid='" + 
            lcid + 
            "') kczw,(select examname from cus_kw_examround where lcid='" + lcid + 
            "') examname from cus_kw_examinee where lcid='" + lcid + 
            "' and kmid<>'' and kmid is not null group by KMID order by count(ksid) asc limit 1";
          List lsKc = getListSQL(sqlKc);
          if (lsKc != null) {
            for (int ii = 0; ii < lsKc.size(); ii++) {
              Map mpKc = (Map)lsKc.get(ii);
              String kmid = mpKc.get("kmid") == null ? "" : mpKc.get("kmid").toString();
              String zpksnum = mpKc.get("ksnum") == null ? "" : mpKc.get("ksnum").toString();
              String kczw = mpKc.get("kczw") == null ? "" : mpKc.get("kczw").toString();
              String examname = mpKc.get("examname") == null ? "" : mpKc.get("examname").toString();
              int yzw = Integer.valueOf(kczw).intValue() - Integer.valueOf(zpksnum).intValue();
              int kss = Integer.valueOf(ksnum).intValue();
              String sqlK = "select a.kdid,a.kcid,a.seats,(select count(ksid) from cus_kw_examinee where kmid='" + 
                kmid + 
                "' and kcid=a.kcid) ks,(select cast(GROUP_CONCAT(ksid) as char) ksids from cus_kw_examinee where lcid='" + lcid + 
                "' and (kmid='' or kmid is null)) ksids from cus_kw_examroom a  where a.lcid='" + lcid + 
                "' and a.seats>(select count(ksid) from cus_kw_examinee where kmid='" + kmid + "' and kcid=a.kcid)";
              List lsK = getListSQL(sqlK);
              if (yzw < kss) {
                msg = msg + examname + "考试没有多余座位安排，需要重新排考！</br>";
              }
              if (lsK != null) {
                for (int jj = 0; jj < lsK.size(); jj++) {
                  Map mpK = (Map)lsK.get(jj);
                  String kdid = mpK.get("kdid") == null ? "" : mpK.get("kdid").toString();
                  String kcid = mpK.get("kcid") == null ? "" : mpK.get("kcid").toString();
                  String seats = mpK.get("seats") == null ? "" : mpK.get("seats").toString();
                  String ks = mpK.get("ks") == null ? "" : mpK.get("ks").toString();
                  String ksids = mpK.get("ksids") == null ? "" : mpK.get("ksids").toString();
                  int zwh = Integer.valueOf(ks).intValue();
                  String[] ksidAry = ksids.split(",");
                  int seatsN = Integer.valueOf(seats).intValue();
                  int ksN = Integer.valueOf(ks).intValue();
                  int yzwN = seatsN - ksN;
                  if (yzwN < ksN)
                    for (int j = 0; j < yzwN; j++) {
                      List lsKs = getListSQL("select (select xs_jbxx_id from zxxs_xs_jbxx where sfzjh=a.sfzjh) xs_jbxx_id,a.xjh,a.kscode from cus_kw_examinee a where a.ksid='" + ksidAry[j] + "'");
                      Map mpKs = (Map)lsKs.get(0);
                      String xs_jbxx_id = mpKs.get("xs_jbxx_id") == null ? "" : mpKs.get("xs_jbxx_id").toString();
                      String xjh = mpKs.get("xjh") == null ? "" : mpKs.get("xjh").toString();
                      String kscode = mpKs.get("kscode") == null ? "" : mpKs.get("kscode").toString();
                      update("update cus_kw_examinee set kmid='" + kmid + "',xs_jbxx_id='" + xs_jbxx_id + 
                        "',kdid='" + kdid + "',kcid='" + kcid + "',zwh=" + (zwh + j + 1) + " where KSID='" + ksidAry[j] + "'");
                      insert("insert into CUS_KW_ROOMSTUDENT (KCID,KDID,LCID,KMID,XH,KSCODE,X,Y,ZWH,APID) values('" + 
                        kcid + "','" + kdid + "','" + lcid + "','" + kmid + "','" + xjh + "','" + kscode + "'," + (zwh + j + 1) + ",1," + (zwh + j + 1) + ",'" + StringUtil.getUUID() + "')");
                    }
                  else {
                    for (int j = 0; j < ksidAry.length; j++) {
                      List lsKs = getListSQL("select (select xs_jbxx_id from zxxs_xs_jbxx where sfzjh=a.sfzjh) xs_jbxx_id,a.xjh,a.kscode from cus_kw_examinee a where a.ksid='" + ksidAry[j] + "'");
                      Map mpKs = (Map)lsKs.get(0);
                      String xs_jbxx_id = mpKs.get("xs_jbxx_id") == null ? "" : mpKs.get("xs_jbxx_id").toString();
                      String xjh = mpKs.get("xjh") == null ? "" : mpKs.get("xjh").toString();
                      String kscode = mpKs.get("kscode") == null ? "" : mpKs.get("kscode").toString();
                      update("update cus_kw_examinee set kmid='" + kmid + "',xs_jbxx_id='" + xs_jbxx_id + 
                        "',kdid='" + kdid + "',kcid='" + kcid + "',zwh=" + (zwh + j + 1) + " where KSID='" + ksidAry[j] + "'");
                      insert("insert into CUS_KW_ROOMSTUDENT (KCID,KDID,LCID,KMID,XH,KSCODE,X,Y,ZWH,APID) values('" + 
                        kcid + "','" + kdid + "','" + lcid + "','" + kmid + "','" + xjh + "','" + kscode + "'," + (zwh + j + 1) + ",1," + (zwh + j + 1) + ",'" + StringUtil.getUUID() + "')");
                    }
                  }
                }
              }
            }
          }
        }
      }
      if (!"".equals(msg)) {
        return getMsgBean(true, msg);
      }
      return getMsgBean(true, "安排成功！");
    }
    catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "安排失败！");
  }

  public MsgBean updateSHEaxminee(String lcid) {
    try {
      String sql = "update cus_kw_examinee set flag='1' where lcid='" + lcid + "'";
      update(sql);
      String sql2 = "update cus_kw_examround set shflag='1' where lcid='" + lcid + "'";
      update(sql2);
      return getMsgBean(true, "审核成功！");
    } catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "审核失败！");
  }

  public MsgBean updateSHEaxmineeNo(String lcid)
  {
    try {
      String sql = "update cus_kw_examinee set flag='' where lcid='" + lcid + "'";
      update(sql);
      String sql2 = "update cus_kw_examround set shflag='' where lcid='" + lcid + "'";
      update(sql2);
      return getMsgBean(true, "取消审核成功！");
    } catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "取消审核失败！");
  }

  public PageBean getBbList(String lcid, String xx, String nj, String bj, String xjh)
  {
    String sql = "select xx.XXMC,xb.mc XB,ee.KSID,ee.LCID,ee.KSCODE,ee.XJH,ee.XM,ee.XXDM,ee.NJ,ee.BH,ee.XBM,ee.SFZJH,ee.FLAG from cus_kw_additionee ee left join zd_xb xb on xb.dm=ee.xbm left join zxxs_xx_jbxx xx on ee.xxdm=xx.xx_jbxx_id where ee.lcid='" + 
      lcid + "' and (ee.flag='' or ee.flag='0')";
    if ((xx != null) && (!"".equals(xx))) {
      sql = sql + " and ee.xxdm='" + xx + "'";
    }
    if ((nj != null) && (!"".equals(nj))) {
      sql = sql + " and ee.nj='" + nj + "'";
    }
    if ((bj != null) && (!"".equals(bj))) {
      sql = sql + " and ee.bh='" + bj + "'";
    }
    if ((xjh != null) && (!"".equals(xjh))) {
      sql = sql + " and ee.xjh like binary('%" + xjh + "%')";
    }
    return getSQLPageBean(sql);
  }

  public MsgBean updateBb(String ksid) {
    try {
      ksid = ksid.substring(0, ksid.length() - 1);
      String sql2 = "select * from cus_kw_additionee where scksid in (" + ksid + ")";
      List ls = getListSQL(sql2);
      if ((ls != null) && (ls.size() > 0)) {
        for (int i = 0; i < ls.size(); i++) {
          Map mp = (Map)ls.get(i);
          CusKwExaminee examinee = new CusKwExaminee();
          examinee.setLcid(mp.get("LCID").toString());
          examinee.setKscode(mp.get("KSCODE").toString());
          examinee.setXjh(mp.get("XJH").toString());
          examinee.setXm(mp.get("XM").toString());
          examinee.setXxdm(mp.get("XXDM").toString());
          examinee.setNj(mp.get("NJ").toString());
          examinee.setBh(mp.get("BH").toString());
          examinee.setXbm(mp.get("XBM").toString());
          examinee.setSfzjh(mp.get("SFZJH").toString());
          examinee.setFlag("1");
          save(examinee);
        }
      }
      String sql = "update cus_kw_additionee set flag='1' where scksid in (" + ksid + ")";
      update(sql);
      return getMsgBean(true, "审核成功！");
    } catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "审核失败！");
  }

  public PageBean getKsList(String lcid, String xx, String nj, String bj, String xjh)
  {
    String sql = "select xx.XXMC,xb.mc XB,ee.KSID,ee.LCID,ee.KSCODE,ee.XJH,ee.XM,ee.XXDM,ee.NJ,ee.BH,ee.XBM,ee.SFZJH,ee.FLAG from cus_kw_examinee ee left join zd_xb xb on xb.dm=ee.xbm left join zxxs_xx_jbxx xx on ee.xxdm=xx.xx_jbxx_id where ee.lcid in ('" + 
      lcid.replaceAll(" ", "").replaceAll(",", "','") + "')";
    if ((xx != null) && (!"".equals(xx))) {
      sql = sql + " and ee.xxdm='" + xx + "'";
    }
    if ((nj != null) && (!"".equals(nj))) {
      sql = sql + " and ee.nj='" + nj + "'";
    }
    if ((bj != null) && (!"".equals(bj))) {
      sql = sql + " and ee.bh='" + bj + "'";
    }
    if ((xjh != null) && (!"".equals(xjh))) {
      sql = sql + " and ee.sfzjh like binary('%" + xjh + "%')";
    }
    return getSQLPageBean(sql);
  }

  public MsgBean updateDl(String scksid) {
    try {
      scksid = scksid.substring(0, scksid.length() - 1);
      String sql = "delete from cus_kw_delexaminee where scksid in (" + scksid + ")";
      delete(sql);
      return getMsgBean(true, "审核成功！");
    } catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "审核失败！");
  }

  public MsgBean updateBBEaxminee(String lcid)
  {
    try {
      String sql3 = "select SCKSID,LCID,XJH,XM,XXDM,NJ,BH,XBM,SFZJH,KSCODE,FLAG,REASON from cus_kw_additionee where lcid ='" + lcid + "' and (flag='' or flag='0')";
      List ls = getListSQL(sql3);
      if ((ls != null) && (ls.size() > 0)) {
        for (int i = 0; i < ls.size(); i++) {
          Map mp = (Map)ls.get(i);
          CusKwExaminee examinee = new CusKwExaminee();
          examinee.setLcid(mp.get("LCID").toString());
          examinee.setKscode(mp.get("KSCODE").toString());
          examinee.setXjh(mp.get("XJH").toString());
          examinee.setXm(mp.get("XM").toString());
          examinee.setXxdm(mp.get("XXDM").toString());
          examinee.setNj(mp.get("NJ").toString());
          examinee.setBh(mp.get("BH").toString());
          examinee.setXbm(mp.get("XBM").toString());
          examinee.setSfzjh(mp.get("SFZJH").toString());
          examinee.setFlag("1");
          save(examinee);
        }
      }
      String sql = "update cus_kw_additionee set flag='1' where lcid='" + lcid + "' and (flag='' or flag='0')";
      update(sql);
      String sql2 = "update cus_kw_examround set bbflag='1' where lcid='" + lcid + "'";
      update(sql2);
      return getMsgBean(true, "审核成功！");
    } catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "审核失败！");
  }

  public MsgBean updateDLEaxminee(String lcid)
  {
    try {
      String sql = "update cus_kw_examinee set flag='1' where lcid='" + lcid + "'";
      update(sql);
      String sql2 = "update cus_kw_examround set dlflag='1' where lcid='" + lcid + "'";
      update(sql2);
      return getMsgBean(true, "审核成功！");
    } catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "审核失败！");
  }
  
  public MsgBean fbZT(String lcid)
  {
    try {
      boolean flag = true;
      String ssql = "SELECT count(*) FROM cus_kw_examsubject where lcid='"+lcid+"'";
      Integer fn = Integer.parseInt(getSession().createSQLQuery(ssql).uniqueResult().toString());
      if(fn<=0){
    	  flag = false;
    	  getMsgBean(false, "未生成考试批次不能发布，请先生成考试批次！");
      }
      if(flag){
    	  String sql = "update cus_kw_examround set SFFB='0' where lcid='" + lcid + "'";
          update(sql);
          getMsgBean(true, "发布成功！");
      }
     
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return this.MsgBean;
  }
  
  public MsgBean fbCJ(String lcid)
  {
   
    	  String sql = "update cus_kw_examround set FBCJ='0' where lcid='" + lcid + "'";
          update(sql);
          return getMsgBean(true, "发布成绩成功！");
     
  }
}