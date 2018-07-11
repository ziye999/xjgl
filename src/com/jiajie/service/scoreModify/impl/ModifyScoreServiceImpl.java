package com.jiajie.service.scoreModify.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.scoreModify.CusKwModifyScore;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.resultsStatisticalCollection.SetStandardService;
import com.jiajie.service.scoreModify.ModifyScoreService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;

@Service("modifyScoreService")
@SuppressWarnings("all")
public class ModifyScoreServiceImpl extends ServiceBridge implements ModifyScoreService {

	public PageBean getListPage(String xnxq_id, MBspInfo bspInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select er.LCID,er.XN,zx.mc as XQ,er.examname as KSMC,");
		sb.append("CONCAT(date_format(er.startdate,'%Y-%m-%d'),' -- ',date_format(er.enddate,'%Y-%m-%d')) as KSSJ,");
		sb.append("IFNULL(lc.kssl,0) as CKRS,IFNULL(ckc.wjrc,0) as WJRC");
		sb.append(" from cus_kw_examround er");		
		sb.append(" LEFT JOIN com_mems_organ jb ON jb.region_code = er.dwid");
		sb.append(" LEFT JOIN com_mems_organ jb1 ON jb1.region_code = jb.parent_code");
		sb.append(" left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=er.DWID");
		sb.append(" LEFT join (select lcid,count(lcid) kssl from cus_kw_examinee Group by lcid) lc on lc.lcid=er.lcid");
		sb.append(" LEFT join (select wj.lcid,count(DISTINCT ks.kscode) wjrc from cus_kw_cheatstu wj left join CUS_KW_EXAMINEE ks on ks.ksid=wj.ksid Group by wj.lcid) ckc on ckc.lcid=er.lcid");
		sb.append(" LEFT join zd_xq zx on er.xqm = zx.dm");
		sb.append(" where 0=0");
		sb.append(" and (er.dwid='"+bspInfo.getOrgan().getOrganCode()+
				"' OR jb.region_code = '"+bspInfo.getOrgan().getOrganCode()+"' OR jb1.region_code = '"+bspInfo.getOrgan().getOrganCode()+
				"' OR jb1.PARENT_CODE = '"+bspInfo.getOrgan().getOrganCode()+"' or xx.SSZGJYXZDM='"+bspInfo.getOrgan().getOrganCode()+
				"' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+
				"') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+"')))");
		sb.append(" and er.wjflag ='1' ");
		if(xnxq_id!=null && !"".equals(xnxq_id)){
			String[] xnxqs = xnxq_id.split(",");
			if(xnxqs.length == 2) {
				sb.append("and er.xn='"+xnxqs[0]+"' and er.xqm='"+xnxqs[1]+"'");
			}
		}
		return getSQLPageBean(sb.toString()); 
	}

	public com.jiajie.bean.MsgBean autoModify(String lcid,String userID) {
		StringBuffer sb = new StringBuffer();
		sb.append("select cke.KSCODE, cke.XJH, TRUNCATE(IFNULL(ckc.score,0),1) as KCFS, ")
		.append("ckc.KMID, TRUNCATE(IFNULL(cks.SCORE,0),1) as YSCJ, TRUNCATE(IFNULL(cks.SCORE-ckc.score,0),1) as ZZFS, ")
		.append("cker.XNXQ_ID, cke.XXDM, cke.XS_JBXX_ID, cker.EXAMTYPEM, km.KCH ")
		.append(" from cus_kw_examinee cke ")
		.append(" left join cus_kw_cheatstu ckc on cke.ksid = ckc.ksid ")
		.append(" left join cus_kw_stuscore cks on cke.kscode = cks.EXAMCODE and cke.xjh = cks.xjh and ckc.kmid = cks.kmid ")
		.append(" left join cus_kw_examround cker on ckc.lcid = cker.lcid ")
		.append(" left join cus_kw_examsubject km on km.kmid=ckc.kmid ") 
		.append(" where ckc.lcid = '").append(lcid).append("'");		
		try {
			List<Map<String,Object>> ksxxs = getListSQL(sb.toString());	
			if (ksxxs.size()>0) {
				delete("delete from CUS_KW_MODIFYSCORE where lcid='"+lcid+"'");
			}
			for (Map<String, Object> map : ksxxs) {
				if ((new BigDecimal(map.get("ZZFS")==null?"0":map.get("ZZFS").toString())).floatValue()>=0) {
					CusKwModifyScore ckms = new CusKwModifyScore();
					ckms.setLcid(lcid);
					ckms.setKmid(map.get("KMID").toString());
					ckms.setExamcode(map.get("KSCODE").toString());
					ckms.setXjh(map.get("XJH").toString());				
					ckms.setYscj((new BigDecimal(map.get("YSCJ")==null?"0":map.get("YSCJ").toString())).floatValue());
					ckms.setKcfs((new BigDecimal(map.get("KCFS")==null?"0":map.get("KCFS").toString())).floatValue());
					ckms.setZzcj((new BigDecimal(map.get("ZZFS")==null?"0":map.get("ZZFS").toString())).floatValue());
					ckms.setXgr(userID);
					if (ckms.getKcfs()>0) {
						save(ckms);
						StringBuffer sb1 = new StringBuffer();
						sb1.append("update cus_kw_stuscore set score =").append(map.get("ZZFS"))
						.append(" where kmid = '").append(map.get("KMID").toString())
						.append("' and xjh = '").append(map.get("XJH").toString())
						.append("' and examcode = ").append(map.get("KSCODE"))
						.append(" and lcid = '").append(lcid).append("'");
						update(sb1.toString());
					}															
				}				
			}
			if(ksxxs.size() > 0 ){
				StringBuffer sb1 = new StringBuffer();
				sb1.append("update cus_kw_examround set wjcjxzflag='1',cjxzflag='' where lcid = '")
				.append(lcid).append("'");
				update(sb1.toString());
			}
			this.getMsgBean(true ,"成绩已修正");
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"修正失败");
		}
		return MsgBean;
	}

	public PageBean cheatListPage(String lcid,String km,String xm_kh_xjh) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct ckc.wjid as WJID, cker.examname as KSMC, ckes.subjectname as KM,")
		.append("cke.KSCODE as KH, cke.sfzjh as SFZJH, cke.xm as XM, zx.mc as XB, cks.SCORE as YSCJ,IFNULL(ckc.score,0) as KCFS,")		
		.append("case when cks.SCORE<ckc.score then null else cks.SCORE-ckc.score end as ZZFS")
		.append(" from cus_kw_examinee cke")
		.append(" left join cus_kw_cheatstu ckc on cke.ksid = ckc.ksid")
		.append(" left join cus_kw_stuscore cks on cke.kscode = cks.EXAMCODE and cke.xjh = cks.xjh and ckc.kmid = cks.kmid")
		.append(" left join cus_kw_examround cker on ckc.lcid = cker.lcid")
		.append(" left join cus_kw_examsubject ckes on ckc.kmid = ckes.kmid")
		.append(" left join zd_xb zx on zx.dm = cke.xbm")
		.append(" where ckc.lcid in ('")
		.append(lcid.replaceAll(" ", "").replaceAll(",", "','")).append("')");
		if(StringUtil.isNotNullOrEmpty(km)){
			sb.append(" and ckc.kmid = '").append(km).append("'");
		}
		if(StringUtil.isNotNullOrEmpty(xm_kh_xjh)){
			sb.append(" and (cke.xm like binary('%").append(xm_kh_xjh).append("%') or cke.sfzjh like binary('%")
			.append(xm_kh_xjh).append("%') or cke.kscode like binary('%").append(xm_kh_xjh).append("%'))");
		}
		return getSQLPageBean(sb.toString()); 
	}

	public com.jiajie.bean.MsgBean manualModify(String lcid, String xgkf,
			String userID) {
		boolean flag = true;
		if(StringUtil.isNotNullOrEmpty(xgkf)){
			flag = updateWjkf(xgkf);
		}
		if(flag){
			MsgBean msgBean = autoModify(lcid, userID);
			return msgBean;
		}else{
			this.getMsgBean(false,"修正失败");
			return MsgBean;
		}
	}
	
	public boolean updateWjkf(String xgkf){
		try {
			String[] split = xgkf.split(";");
			for (int i = 0; i < split.length; i++) {
				String[] split2 = split[i].split("=");
				if (split2.length>1&&StringUtil.isNotNullOrEmpty(split2[1])) {
					StringBuffer sb = new StringBuffer();
					sb.append(" update cus_kw_cheatstu set score = ")
					.append(split2[1]).append(" where wjid = '").append(split2[0])
					.append("'");
					update(sb.toString());
				}								
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	
}
