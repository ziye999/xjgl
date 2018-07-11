package com.jiajie.service.examArrangement.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.examArrangement.CusKwExamschool;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.examArrangement.ExamSiteSchoolService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
@Service("examSiteSchoolService")
@SuppressWarnings("all")
public class ExamSiteSchoolServiceImpl extends ServiceBridge implements ExamSiteSchoolService {

	public PageBean getList(String jyj,String lcid) {
		String sql = "select xx.XXMC,xx.SSZGJYXZDM,xx.XXBSM,rm.SCHOOLID,sum(rm.floor) LCS,sum(rm.seats) ZWS,"+
				"(select count(*) from cus_kw_examschool where lcid='"+lcid+"' and xxdm=rm.schoolid) SETSTATUS,"+
				"case when (select count(*) from cus_kw_examschool where lcid='"+lcid+"' and xxdm=rm.schoolid)=0 then '未设置' else '已设置' end ZT"+
				" from cus_kw_room rm"+
				" inner join zxxs_xx_jbxx xx on rm.schoolid=xx.xx_jbxx_id"+
				" where 1=1";
		if(jyj!=null&&!"".equals(jyj)){
			String[] str = jyj.split(",");
			if(str.length>0){
				String str2 = "";
				for(int i=0;i<str.length;i++){
					str2 += "'"+str[i]+"',";
				}
				jyj = str2.substring(0, str2.length()-1);
			}
			sql += " and xx.sszgjyxzdm in ("+jyj+")";
		}
		sql += " group by xx.xxmc,xx.xxbsm,xx.sszgjyxzdm,rm.schoolid order by SETSTATUS desc";
		return getSQLPageBean(sql); 
	}

	public com.jiajie.bean.MsgBean saveSiteSchool(String lcId,List<Map<String, Object>> list) {
		try {
			for (Map<String, Object> map : list) {
				CusKwExamschool scl = new CusKwExamschool();
				scl.setLcid(lcId);
				if (map==null) {
					continue;
				}
				scl.setPointname(StringUtil.getString(map.get("xxmc")));
				scl.setRoomcount(Integer.parseInt(map.get("lcs").toString()));
				scl.setSeatcount(Integer.parseInt(map.get("zws").toString()));
				scl.setXxdm(StringUtil.getString(map.get("schoolid")));
				getSession().save(scl);
			}
			this.getMsgBean(true,"已设置！");
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}
	public com.jiajie.bean.MsgBean deleteSiteSchool(String lcId,List<Map<String, Object>> list) {
		try {
			String xxdm = "";
			for (Map<String, Object> map : list) {
				xxdm+="'"+StringUtil.getString(map.get("schoolid"))+"',";
			}
			xxdm=xxdm.substring(0, xxdm.length()-1);
			String sql = "delete FROM cus_kw_examschool where lcid='"+lcId+"' and xxdm in("+xxdm+")";
			this.deleteBySql(sql);
			this.getMsgBean(true,"已取消!");
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}
}
