package com.jiajie.service.examResults.impl;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.examResults.CusKwStuScore;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.examResults.ExamUpdateService;
import com.jiajie.util.SecUtils;
import com.jiajie.util.StringUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

@Service("examUpdateService")
public class ExamUpdateServiceImpl extends ServiceBridge
  implements ExamUpdateService
{
  public List getSubject(String lcid, String lc_find)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("select distinct kch as KM from cus_kw_examsubject where lcid in ('")
      .append((lcid == null) || ("".equals(lcid)) || ("null".equalsIgnoreCase(lcid)) ? 
      lc_find : lcid.replaceAll(" ", "").replaceAll(",", "','")).append("')");
    List list = getListSQL(sb.toString());
    return list;
  }

  public PageBean getListPage(String lcid, String xuexiao, String xm_kh_xjh, String lc_find, String kmid, String kdid, String kcid) {
    StringBuilder sb1 = new StringBuilder();
    sb1.append("select distinct kch as KM,SCORE from cus_kw_examsubject where lcid in ('")
      .append((lcid == null) || ("".equals(lcid)) || ("null".equalsIgnoreCase(lcid)) ? 
      lc_find : lcid.replaceAll(" ", "").replaceAll(",", "','")).append("')");
    List kms = getListSQL(sb1.toString());
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT concat(t.KH,'') as KH,t.XJH as XJH,t.XM as XM,group_concat(distinct t.sxfs) as SXFS ");
    for (int i = 0; i < kms.size(); i++) {
      Map map = (Map)kms.get(i);
      if (i != kms.size() - 1)
        sb.append(", score AS " + map.get("KM").toString().replaceAll("-", "") + i);
      else {
        sb.append(", score AS " + map.get("KM").toString().replaceAll("-", "") + i);
      }
    }
    sb.append(" FROM (");
    sb.append(" select ks.KSCODE as KH,ks.xjh as XJH,ks.XM,ckes.KMID,ifnull(ckss.SCORE,0) as SCORE,concat(ckes.KCH,'上限分数',ckes.score) SXFS ")
      .append(" from cus_kw_examinee ks")
      .append(" left join cus_kw_stuscore_bk ckss on ckss.XJH=ks.XJH and ckss.LCID=ks.LCID")
      .append(" left join zxxs_xs_jbxx zxs on ckss.xjh = zxs.grbsm ")
      .append(" left join cus_kw_examsubject ckes on ckes.LCID=ks.LCID")
      .append(" where ks.LCID IN ('")
      
      .append((lcid == null) || ("".equals(lcid)) || ("null".equalsIgnoreCase(lcid)) ? lc_find : lcid.replaceAll(" ", "").replaceAll(",", "','")).append("') ");
    if (StringUtil.isNotNullOrEmpty(xuexiao).booleanValue()) {
      sb.append(" and zxs.xx_jbxx_id in( ").append(xuexiao).append(")");
    }
    if (StringUtil.isNotNullOrEmpty(xm_kh_xjh).booleanValue()) {
      sb.append(" and (ks.KSCODE like binary('%").append(xm_kh_xjh)
        .append("%') or ks.xjh like binary('%").append(xm_kh_xjh)
        .append("%') or ks.xm like binary('%").append(xm_kh_xjh).append("%'))");
    }
    if ((kmid != null) && (!"".equals(kmid))) {
      sb.append(" AND ks.KMID='" + kmid + "' ");
    }
    if ((kdid != null) && (!"".equals(kdid))) {
      sb.append(" and EXISTS(select 'X' from CUS_KW_ROOMSTUDENT st where st.LCID=ks.LCID and st.XH=ks.XJH and st.KDID='" + kdid + "')");
    }
    if ((kcid != null) && (!"".equals(kcid))) {
      sb.append(" and EXISTS(select 'X' from CUS_KW_ROOMSTUDENT st where st.LCID=ks.LCID and st.XH=ks.XJH and st.KCID='" + kcid + "') ");
    }
    sb.append(") t GROUP BY t.KH,t.XJH,t.XM ");
    PageBean pageBean = getSQLPageBean(sb.toString());
    return pageBean;
  }

  public MsgBean submitJkCj(String idInfo, String lcid, String lc_find, String userID) {
    try {
      String lcidParam = (lcid == null) || ("".equals(lcid)) || ("null".equalsIgnoreCase(lcid)) ? 
        lc_find : lcid.replaceAll(" ", "").replaceAll(",", "','");
      String[] split = idInfo.split(";");
      String ids = "";
      for (int i = 0; i < split.length; i++) {
        ids = ids + "'" + split[i] + "',";
      }
      if (!"".equals(ids)) {
        ids = ids.substring(0, ids.length() - 1);
        String sql = "select xs.KMID,ks.KSCODE,ks.XJH,sum(xs.SCORE) SCORE from (select exam_info_id,score,KMID,XS_JBXX_ID from CUS_EXAM_DETAIL a left join PAPER_BASIC b on b.PAPER_ID=a.PAPER_ID group by EXAM_INFO_ID,KMID,XS_JBXX_ID) xs left join zxxs_xs_jbxx xsjb on xsjb.XS_JBXX_ID=xs.XS_JBXX_ID left join cus_kw_examinee ks on ks.SFZJH=xsjb.SFZJH where ks.lcid in ('" + 
          lcidParam + "') " + 
          "and xs.KMID in (select kmid from cus_kw_examsubject where lcid in ('" + lcidParam + "')) " + 
          "and ks.KSCODE in (" + ids + ") " + 
          "group by xs.KMID,ks.KSCODE,ks.XJH";
        List ls = getListSQL(sql);
        if ((ls != null) && (ls.size() > 0)) {
          for (int i = 0; i < ls.size(); i++) {
            Map mp = (Map)ls.get(i);
            String kmid = mp.get("KMID") == null ? "" : mp.get("KMID").toString();
            String kscode = mp.get("KSCODE") == null ? "" : mp.get("KSCODE").toString();
            String xjh = mp.get("XJH") == null ? "" : mp.get("XJH").toString();
            String score = mp.get("SCORE") == null ? "" : mp.get("SCORE").toString();

            String sqlKs = "select SCORE from cus_kw_stuscore where lcid in ('" + lcidParam + 
              "') and kmid='" + kmid + "' and examcode='" + kscode + "' and xjh='" + xjh + "'";
            List lsKs = getListSQL(sqlKs);
            if (lsKs.size() > 0) {
              Map mpKs = (Map)lsKs.get(0);
              String scoreKs = mpKs.get("SCORE") == null ? "" : mpKs.get("SCORE").toString();
              if (!scoreKs.equals(score))
                update("update cus_kw_stuscore set SCORE=" + score + ",SCOREJM='" + SecUtils.encode(score) + "',WRITER='" + userID + 
                  "' where lcid in ('" + lcidParam + "') and kmid='" + kmid + "' and examcode='" + kscode + "' and xjh='" + xjh + "'");
            }
            else {
              insert("insert into cus_kw_stuscore(XSCJID,LCID,KMID,EXAMCODE,XJH,SCORE,SCOREJM,WRITER) values('" + StringUtil.getUUID() + 
                "','" + lcidParam + "','" + kmid + "','" + kscode + "','" + xjh + "'," + score + ",'" + SecUtils.encode(score) + "','" + userID + "')");
            }
          }
          getMsgBean(true, "未提交成绩" + ls.size() + "个，代提交成功！");
        } else {
          getMsgBean(true, "成绩全部已提！不必代提交！");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      getMsgBean(false, "成绩代提交失败！");
    }
    return this.MsgBean;
  }

  public MsgBean deleteStuScore(String delInfo, String lcid, String lc_find) {
    try {
      StringBuffer sb = new StringBuffer();
      sb.append("delete from cus_kw_stuscore where lcid in ('")
        .append((lcid == null) || ("".equals(lcid)) || ("null".equalsIgnoreCase(lcid)) ? 
        lc_find : lcid.replaceAll(" ", "").replaceAll(",", "','"))
        .append("') and examcode in (");
      String[] split = delInfo.split(";");
      String ids = "";
      for (int i = 0; i < split.length; i++)
      {
        ids = ids + "'" + split[i] + "',";
      }
      sb.append(ids.substring(0, ids.length() - 1)).append(")");
      delete(sb.toString());
      getMsgBean(true, MsgBean.DEL_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      getMsgBean(false, MsgBean.DEL_ERROR);
    }
    return this.MsgBean;
  }

  public MsgBean addStuScore(String lcid, String kh, String xjh, String addInfo, String userID, String lc_find) {
    StringBuffer sb = new StringBuffer();
    sb.append("select KSID,XJH from cus_kw_examinee where lcid in ('")
      .append((lcid == null) || ("".equals(lcid)) || ("null".equalsIgnoreCase(lcid)) ? 
      lc_find : lcid.replaceAll(" ", "").replaceAll(",", "','"))
      .append("')");
    if ((xjh != null) && (!"".equals(xjh))) {
      sb.append(" and xjh='").append(xjh).append("'");
    }
    sb.append(" and kscode='").append(kh).append("'");
    List listSQL = getListSQL(sb.toString());
    if (listSQL.size() == 0) {
      getMsgBean(false, "考号错误，本场考试没有该学生");
      return this.MsgBean;
    }
    xjh = ((HashMap)listSQL.get(0)).get("XJH").toString();

    StringBuffer sb1 = new StringBuffer();
    sb1.append("select XSCJID from cus_kw_stuscore where lcid in ('")
      .append((lcid == null) || ("".equals(lcid)) || ("null".equalsIgnoreCase(lcid)) ? 
      lc_find : lcid.replaceAll(" ", "").replaceAll(",", "','"))
      .append("')");

    if ((xjh != null) && (!"".equals(xjh))) {
      sb1.append(" and xjh='").append(xjh).append("'");
    }
    sb1.append(" and examcode='").append(kh).append("'");
    List listSQL1 = getListSQL(sb1.toString());
    if (listSQL1.size() != 0) {
      getMsgBean(false, "该考生有了成绩,不需要添加");
      return this.MsgBean;
    }
    String[] split = addInfo.split(",");
    try {
      for (int i = 0; i < split.length; i++) {
        List lst = getListSQL("select distinct KCH from cus_kw_examsubject where kmid='" + split[i].split("=")[0] + 
          "' and score<" + split[i].split("=")[1]);
        if (lst.size() > 0) {
          String ksmc = ((HashMap)lst.get(0)).get("KCH").toString();
          getMsgBean(false, "填写分数超出" + ksmc + "上限分数，不能保存！");
          return this.MsgBean;
        }
        CusKwStuScore ckss = new CusKwStuScore();
        ckss.setLcid(lcid);
        ckss.setXjh(xjh);
        ckss.setKmid(split[i].split("=")[0]);
        ckss.setWriter(userID);
        ckss.setExamcode(kh);
        ckss.setScore(Float.valueOf(Float.parseFloat(split[i].split("=")[1])));
        save(ckss);
      }

      getMsgBean(true, MsgBean.SAVE_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      getMsgBean(false, MsgBean.SAVE_ERROR);
    }
    return this.MsgBean;
  }

  public MsgBean updateStuScore(String lcid, String kh, String xjh, String addInfo, String lc_find, String userid) {
    try {
      String[] split = addInfo.split(",");
      for (int i = 0; i < split.length; i++) {
        List lstSx = getListSQL("select KCH from cus_kw_examsubject where kmid='" + split[i].split("=")[0] + 
          "' and score<" + split[i].split("=")[1]);
        if (lstSx.size() > 0) {
          String ksmc = ((HashMap)lstSx.get(0)).get("KCH").toString();
          getMsgBean(false, "填写分数超出" + ksmc + "上限分数，不能保存！");
          return this.MsgBean;
        }
        List lst = createSQLQuery("select * from cus_kw_stuscore where lcid in ('" + (
          (lcid == null) || ("".equals(lcid)) || ("null".equalsIgnoreCase(lcid)) ? lc_find : lcid.replaceAll(" ", "").replaceAll(",", "','")) + 
          "') and  examcode = '" + kh + "'" + (
          (xjh == null) || ("".equals(xjh)) ? "" : new StringBuilder(" and xjh = '").append(xjh).append("'").toString()) + 
          " and kmid ='" + split[i].split("=")[0] + "'").list();
        StringBuffer sb = new StringBuffer();
        if (lst.size() > 0) {
          sb.append("update cus_kw_stuscore set score = ").append(split[i].split("=")[1])
            .append(" where lcid in ('")
            .append((lcid == null) || ("".equals(lcid)) || ("null".equalsIgnoreCase(lcid)) ? 
            lc_find : lcid.replaceAll(" ", "").replaceAll(",", "','"))
            .append("') and examcode = ").append(kh);
          if ((xjh != null) && (!"".equals(xjh))) {
            sb.append(" and xjh='").append(xjh).append("'");
          }
          sb.append(" and kmid='").append(split[i].split("=")[0]).append("'");
          update(sb.toString());
        } else {
          List lstXs = getListSQL("select XJH from cus_kw_examinee where 0=0" + (
            (lcid == null) || ("".equals(lcid)) ? "" : new StringBuilder(" and lcid='").append(lcid).append("'").toString()) + (
            (kh == null) || ("".equals(kh)) ? "" : new StringBuilder(" and kscode='").append(kh).append("'").toString()));
          if (lstXs.size() > 0) {
            xjh = ((HashMap)lstXs.get(0)).get("XJH").toString();
          }
          sb.append("insert into cus_kw_stuscore(XSCJID,LCID,KMID,EXAMCODE,XJH,SCORE,WRITER) ");
          sb.append("values('").append(StringUtil.getUUID()).append("','");
          sb.append((lcid == null) || ("".equals(lcid)) || ("null".equalsIgnoreCase(lcid)) ? 
            lc_find : lcid.replaceAll(" ", "").replaceAll(",", "','")).append("','");
          sb.append(split[i].split("=")[0]).append("','").append(kh).append("','");
          sb.append(xjh).append("',").append(split[i].split("=")[1]).append(",'");
          sb.append(userid).append("')");
          insert(sb.toString());
        }
      }

      getMsgBean(true, MsgBean.SAVE_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      getMsgBean(false, MsgBean.SAVE_ERROR);
    }
    return this.MsgBean;
  }

public MsgBean submitXk(String idInfo, String lcid, String lc_find,
		String userID) {
	// TODO Auto-generated method stub
	return null;
}
}