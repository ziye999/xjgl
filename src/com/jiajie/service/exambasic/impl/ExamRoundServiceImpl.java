package com.jiajie.service.exambasic.impl;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.examArrangement.CusKwExamschool;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.exambasic.ExamRoundService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;
import java.io.PrintStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

@Service("examRoundService")
public class ExamRoundServiceImpl extends ServiceBridge implements ExamRoundService{
  public PageBean getList(String xnxq, MBspInfo bsp)
  {
    String xn = null;
    String xq = null;
    String sql = "select er.LCID,er.XN,zx.name as XQM,er.EXAMNAME,er.STARTWEEK,er.STARTDAY,date_format(er.startdate,'%Y-%m-%d') as STARTDATE,er.ENDWEEK,er.ENDDAY,date_format(er.enddate,'%Y-%m-%d') as ENDDATE,et.mc as EXAMTYPEMC,er.XNXQ_ID,et.dm as EXAMTYPEM,er.DWID,IFNULL((select region_edu from com_mems_organ where region_code=er.dwid),(select (select region_edu from com_mems_organ where region_code=a.SSZGJYXZDM) from zxxs_xx_jbxx a where a.xx_jbxx_id=er.dwid)) ZKDW,IFNULL((select xxmc from zxxs_xx_jbxx where xx_jbxx_id = er.dwid),'全部') CKDW from cus_kw_examround er left join com_mems_organ zk on zk.region_code=er.dwid left join com_mems_organ zk1 on zk1.region_code=zk.PARENT_CODE left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=er.DWID LEFT join zxxs_xnxq xnxq on er.xnxq_id = xnxq.xnxq_id LEFT join cus_kw_examtype et on er.examtypem = et.dm LEFT join sys_enum_value zx on er.xqm = zx.code and zx.DIC_TYPE = 'ZD_XT_XQMC' where 0=0 ";

    sql = sql + "and (er.dwid='" + bsp.getOrgan().getOrganCode() + 
      "' or zk.region_code='" + bsp.getOrgan().getOrganCode() + "' or zk1.region_code='" + bsp.getOrgan().getOrganCode() + 
      "' OR zk1.PARENT_CODE = '" + bsp.getOrgan().getOrganCode() + "' or xx.SSZGJYXZDM='" + bsp.getOrgan().getOrganCode() + 
      "' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='" + bsp.getOrgan().getOrganCode() + 
      "') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='" + bsp.getOrgan().getOrganCode() + "')))";
    if ((xnxq != null) && (!"".equals(xnxq))) {
      xn = xnxq.split(",")[0];
      xq = xnxq.split(",")[1];
      if ((xn != null) && (!"".equals(xn))) {
        sql = sql + " and er.XN='" + xn + "'";
      }
      if ((xq != null) && (!"".equals(xq))) {
        sql = sql + " and er.XQM='" + xq + "'";
      }
    }
    sql = sql + " order by er.lcid desc";
    return getSQLPageBean(sql);
  }

  public MsgBean saveOrUpdateExamround(CusKwExamround examround, String usertype) {
    boolean isAdd = true;
    if (examround.getLcid() != null)
      isAdd = false;
    try
    {
      if ((!StringUtil.isNotNullOrEmpty(examround.getDwid().trim()).booleanValue()) || 
        (!StringUtil.isNotNullOrEmpty(examround.getExamname().trim()).booleanValue()) || 
        (!StringUtil.isNotNullOrEmpty(examround.getXnxqId().trim()).booleanValue())) {
        getMsgBean(false, "必填项为空不能保存！");
      } else {
        examround.setDwid(StringUtil.isNotNullOrEmpty(examround.getDwid().split(",")[0].trim()).booleanValue() ? examround.getDwid().split(",")[0].trim() : examround.getDwid().split(",")[1].trim());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List ls = getListSQL("SELECT * from cus_kw_examround where " + ((examround.getLcid() == null) || ("".equals(examround.getLcid())) ? "" : new StringBuilder("lcid<>'").append(examround.getLcid()).append("' and ").toString()) + 
          "dwid='" + examround.getDwid() + "' and ((STARTDATE<=date('" + format.format(examround.getStartdate()) + "') and ENDDATE>=date('" + format.format(examround.getStartdate()) + 
          "')) or (STARTDATE<=date('" + format.format(examround.getEnddate()) + "') and ENDDATE>=date('" + format.format(examround.getEnddate()) + 
          "')) or (STARTDATE>=date('" + format.format(examround.getStartdate()) + "') and ENDDATE<=date('" + format.format(examround.getEnddate()) + "')))");
        if (ls.size() > 0) {
          return getMsgBean(false, "已存时间或考点冲突的考试轮次，不能保存！");
        }
        String[] xqxqs = examround.getXnxqId().split(",");
        if (xqxqs.length == 2) {
          examround.setXn(xqxqs[0]);
          examround.setXqm(xqxqs[1]);
        }
        List lst = createSQLQuery("select * from cus_kw_examround where examname='" + examround.getExamname() + "'").list();
        if (lst.size() > 0) {
          if ((examround.getLcid() == null) || ("".equals(examround.getLcid()))) {
            getMsgBean(false, "重复考试轮次信息不能保存！");
          } else {
            update(examround);
            if (("2".equals(usertype)) && (isAdd)) {
              CusKwExamschool examschool = new CusKwExamschool();
              examschool.setLcid(examround.getLcid());
              save(examschool);
            }
            getMsgBean(true, MsgBean.SAVE_SUCCESS);
          }
        } else {
          if (xqxqs.length == 2) {
            getSession().saveOrUpdate(examround);
            getMsgBean(true, MsgBean.SAVE_SUCCESS);
          } else {
            getMsgBean(false, MsgBean.SAVE_ERROR);
          }
          if (("2".equals(usertype)) && (isAdd)) {
            CusKwExamschool examschool = new CusKwExamschool();
            examschool.setLcid(examround.getLcid());
            save(examschool);
          }
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      getMsgBean(false, MsgBean.SAVE_ERROR);
    }
    return this.MsgBean;
  }

  public MsgBean delExamround(String lcids, String schoolid) {
    try {
      List ls = createHQLQuery("from CusKwExamsubject where lcid in(" + lcids + ")").list();
      if (ls.size() > 0) {
        getMsgBean(false, "已经有存在的考试科目，不能删除！");
      } else {
        StringBuffer sb = new StringBuffer();
        sb.append("delete from CUS_KW_EXAMROUND where lcid in(").append(lcids).append(")");
        delete(sb.toString());
        String sql = "DELETE FROM CUS_KW_EXAMSCHOOL WHERE LCID IN(" + lcids + ") AND XXDM=" + schoolid;
        delete(sql);
        getMsgBean(true, MsgBean.DEL_SUCCESS);
      }
    } catch (Exception e) {
      e.printStackTrace();
      getMsgBean(false, MsgBean.DEL_ERROR);
    }
    return this.MsgBean;
  }

  public MsgBean getExamround(String lcid) {
    try {
      Object obj = get(CusKwExamround.class, lcid);
      getMsgBean(obj);
    } catch (Exception e) {
      e.printStackTrace();
      getMsgBean(false, MsgBean.GETDATA_ERROR);
    }
    return this.MsgBean;
  }

  public MsgBean nylcid(String lcid, String lcid2) {
    int r = Integer.parseInt(getSession().createSQLQuery("select count(1) from cus_kw_examround WHERE LCID = '" + lcid + "'").uniqueResult().toString());
    if (r > 0) {
      List list = getSession().createSQLQuery("select kmid,round(count(1)) n,max(zwh) z  from cus_kw_examinee WHERE LCID = '" + lcid2 + "' group by kmid,kcid order by count(1),kcid limit 2").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
      if ((list != null) && (list.size() == 2)) {
        BigInteger bi = (BigInteger)((Map)list.get(1)).get("n");
        System.out.println(bi.intValue());
        int flag = ((BigInteger)((Map)list.get(1)).get("n")).intValue() - ((BigInteger)((Map)list.get(0)).get("n")).intValue();
        if (flag >= r) {
          String kmid = ((Map)list.get(0)).get("kmid").toString();
          int zwh = Integer.parseInt(((Map)list.get(0)).get("z").toString());
          Map map = (Map)getSession().createSQLQuery("SELECT lcid,kmid,zwh,kcid,kdid FROM cus_kw_examinee WHERE KMID = '" + kmid + "' AND zwh=" + zwh).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
          List kss = getSession().createSQLQuery("select ksid from cus_kw_examinee WHERE LCID = '" + lcid + "' ").list();
          for (int i = 0; i < kss.size(); i++) {
            getSession().createSQLQuery("update cus_kw_examinee set lcid = '" + (String)map.get("lcid") + "',kmid='" + (String)map.get("kmid") + "',kcid='" + (String)map.get("kcid") + "',kdid='" + (String)map.get("kdid") + "',zwh=" + (zwh + i + 1) + " where ksid = '" + (String)kss.get(i) + "'").executeUpdate();
            Map m = (Map)getSession().createSQLQuery("select xjh,kscode from cus_kw_examinee WHERE ksid = '" + (String)kss.get(i) + "' ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
            String apid = (String)getSession().createSQLQuery("select apid from cus_kw_roomstudent WHERE xh = '" + (String)m.get("xjh") + "' ").uniqueResult();
            if (apid != null)
              getSession().createSQLQuery("update cus_kw_roomstudent set lcid = '" + (String)map.get("lcid") + "',kmid='" + (String)map.get("kmid") + "',kcid=''" + (String)map.get("kcid") + "',kdid='" + (String)map.get("kdid") + "',zwh=" + (zwh + i + 1) + ",xh='" + (String)m.get("xjh") + "',kscode='" + (String)m.get("kscode") + "',y=1,x=" + (zwh + i + 1) + " where apid = '" + apid + "'").executeUpdate();
            else {
              getSession().createSQLQuery("insert into cus_kw_roomstudent(apid,lcid,kmid,kcid,kdid,zwh,x,y,kscode,xh) values(replace(uuid(),'-',''),'" + (String)map.get("lcid") + "','" + (String)map.get("kmid") + "','" + (String)map.get("kcid") + "','" + (String)map.get("kdid") + "'," + (zwh + i + 1) + "," + (zwh + i + 1) + ",1,'" + (String)m.get("kscode") + "','" + (String)m.get("xjh") + "')").executeUpdate();
            }
          }
          getSession().createSQLQuery("update cus_kw_examinee e set xs_jbxx_id = (select xs_jbxx_id from zxxs_xs_jbxx where sfzjh = e.sfzjh ) where kmid = '" + kmid + "'").executeUpdate();
          getMsgBean(true, "success==成功");
        } else {
          getMsgBean(false, "failed===批次排不下了");
        }
      }
    }
    return this.MsgBean;
  }
}