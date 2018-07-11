package com.jiajie.service.registration.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwAdditionee;
import com.jiajie.bean.exambasic.CusKwExaminee;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.registration.ExamRegistrationService;
import com.jiajie.service.registration.ExamStuModifyService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;
import com.jiajie.bean.MsgBean;
import com.mysql.jdbc.StringUtils;

@Service("examStuModifyService")
@SuppressWarnings("all")
public class ExamStuModifyServiceImpl extends ServiceBridge implements ExamStuModifyService{

	public PageBean getExamRoundListPage(String xnxqId, MBspInfo bspInfo) {
		String nn = "";
		String xq = "";
		if(xnxqId!=null && !"".equals(xnxqId)){
			String[] str = xnxqId.split(",");
			nn = str[0];
			xq = str[1];
		}
		String sql = "select s1.* from (select DISTINCT rd.LCID,rd.XN,rd.XQM,rd.EXAMNAME,rd.STARTWEEK,rd.STARTDAY,"+
				"rd.STARTDATE,rd.ENDWEEK,rd.ENDDAY,rd.ENDDATE,rd.EXAMTYPEM,rd.DWID,rd.DWTYPE,rd.SHFLAG,xq.mc XQ,rd.SL,"+
				"IFNULL(jb.region_edu,(select (select region_edu from com_mems_organ where region_code=a.SSZGJYXZDM) from zxxs_xx_jbxx a where a.xx_jbxx_id=rd.dwid)) MC,"+
				"IFNULL((select xxmc from zxxs_xx_jbxx where xx_jbxx_id = rd.dwid),'全部') CKDW,"+
				"case when rd.SHFLAG='1' then '已审核' when rd.SHFLAG='0' then '已上报' else '未上报' end as SHZT "+
				"from cus_kw_examround rd "+ 
				"left join com_mems_organ jb on jb.region_code=rd.dwid "+
				"left join com_mems_organ jb1 on jb1.region_code=jb.parent_code "+
				"left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=rd.DWID "+
				"left join zd_xq xq on rd.xqm=xq.dm "+
				"where (rd.dwid='"+bspInfo.getOrgan().getOrganCode()+
				"' or jb.region_code='"+bspInfo.getOrgan().getOrganCode()+"' or jb1.region_code='"+bspInfo.getOrgan().getOrganCode()+
				"' OR jb1.PARENT_CODE = '"+bspInfo.getOrgan().getOrganCode()+"' or xx.SSZGJYXZDM='"+bspInfo.getOrgan().getOrganCode()+
				"' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+
				"') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+"')))) s1 where 1=1";
		if(nn!=null && !"".equals(nn)){
			sql += " and s1.xn='"+nn+"'";
		}
		if(xq!=null && !"".equals(xq)){
			sql += " and s1.xqm = '"+xq+"'";
		}
		return getSQLPageBean(sql); 
	}

	public PageBean getExamStuByRoundIdListPage(String xxdm, String njdm, String bjdm, MBspInfo bspInfo) {				
		return null;
	}

	public PageBean getExamInStu(String lcid, String schools, String xmkhxjh, MBspInfo bspInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct t1.KSID,t1.LCID,t1.XM,t5.NAME as XB,T1.KSCODE,T1.XJH,T1.SFZJH ");
		sb.append("from cus_kw_examinee t1 ");
		sb.append("left join SYS_ENUM_VALUE t5 on t5.code = T1.XBM and t5.DIC_TYPE='ZD_GB_XBM' AND t5.FLAG='1' ");
		sb.append("where 1=1 ");
		if(!StringUtil.isNull(lcid)) {
			sb.append(" and t1.lcid ='").append(lcid).append("'");
		}
		if(!StringUtil.isNull(xmkhxjh)) {
			sb.append(" and (t1.kscode like binary('%").append(xmkhxjh).append("%') or T1.SFZJH like binary('%").append(xmkhxjh).append("%') or t1.xm like binary('%").append(xmkhxjh).append("%'))");
		}
		if(!StringUtil.isNull(schools)) {
			sb.append(" and t1.xxdm in('").append(schools.replaceAll(",", "','")).append("')");
		}		
		sb.append(" order by t1.KSCODE ");
		return getSQLPageBean(sb.toString()); 
	}
	public PageBean getExamNotStu(String lcid, String schools, String xmkhxjh, MBspInfo bspInfo) {
		List lsZk = getListSQL(
				"select GROUP_CONCAT(distinct concat(a.dwid,',',xx1.XX_JBXX_ID,',',xx2.XX_JBXX_ID,',',xx3.XX_JBXX_ID))  xxid from cus_kw_examround a "+
				"LEFT JOIN COM_MEMS_ORGAN T4 ON T4.PARENT_CODE = a.dwid "+
				"LEFT JOIN COM_MEMS_ORGAN T5 ON T5.PARENT_CODE = T4.REGION_CODE "+
				"LEFT join zxxs_xx_jbxx xx1 on xx1.SSZGJYXZDM=a.dwid "+
				"LEFT join zxxs_xx_jbxx xx2 on xx2.SSZGJYXZDM=T4.REGION_CODE "+
				"LEFT join zxxs_xx_jbxx xx3 on xx3.SSZGJYXZDM=T5.REGION_CODE "+
				"where a.lcid='"+lcid+"'");
		String xxid = "";
		if (lsZk != null && lsZk.size()>0) {
    		Map<String, String> mp = (Map<String, String>)lsZk.get(0);
    		xxid = mp.get("xxid")==null?"":mp.get("xxid").toString();
		}	
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct t1.XM,t4.NAME as XB,T1.GRBSM as XJH,T1.SFZJH ");
		sb.append("from ZXXS_XS_JBXX t1 ");				
		sb.append("left join SYS_ENUM_VALUE t4 on t4.code = T1.XBM and t4.DIC_TYPE='ZD_GB_XBM' AND t4.FLAG='1' ");
		sb.append("where 1=1 ");
		if (!StringUtil.isNull(xxid)) {
			sb.append("and t1.xx_jbxx_id in ('").append(xxid.replaceAll(",", "','")).append("')");
		}		
		if(!StringUtil.isNull(lcid)) {
			sb.append(" and t1.grbsm not in (select xjh from cus_kw_examinee where lcid='").append(lcid).append("')");
		}
		if(!StringUtil.isNull(xmkhxjh)) {
			sb.append(" and (t1.GRBSM like binary('%").append(xmkhxjh).append("%') or t1.xm like binary('%").append(xmkhxjh).append("%') or T1.SFZJH like binary('%").append(xmkhxjh).append("%'))");
		}
		if(!StringUtil.isNull(schools)) {
			sb.append(" and t1.xx_jbxx_id in('").append(schools.replaceAll(",", "','")).append("')");
		}		
		sb.append(" order by t1.GRBSM");
		return getSQLPageBean(sb.toString()); 
	}
	
	public MsgBean delExamStu(String lcid,String ksids,String reason,MBspInfo bspInfo) {
		try {
			if(ksids != null && !StringUtil.isNull(ksids)) {
				String[] idAry = ksids.split(",");
				for (int i=0; i<idAry.length; i++) {
					String ksid = idAry[i];
					if (StringUtils.isNullOrEmpty(ksid)||"''".equals(ksid)) {
						continue;
					}					
					delete("delete from T_QXGL_USERINFO where loginid in (select sfzjh from cus_kw_examinee where ksid="+ksid+")");
		            delete("delete from zxxs_xs_jbxx where sfzjh in (select sfzjh from cus_kw_examinee where ksid="+ksid+")");
					delete("delete from CUS_KW_EXAMINEE where ksid="+ksid);					
				}
				update("update CUS_KW_EXAMROUND a set SL=IFNULL((SELECT COUNT(*) FROM CUS_KW_EXAMINEE b WHERE b.LCID=a.LCID),0) where a.lcid='"+lcid+"'");
				this.getMsgBean(true,MsgBean.DEL_SUCCESS);
			}else {
				this.getMsgBean(false,MsgBean.DEL_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.DEL_ERROR);
		}
		return MsgBean;
	}

	public MsgBean supExamStu(String lcid, String xjhs,MBspInfo bspInfo) {
		try {
			StringBuilder sb  = new StringBuilder();
			sb.append("select distinct t1.GRBSM,t1.XM,t1.XX_JBXX_ID,t2.XJNJDM AS NJ,t1.XX_BJXX_ID,t1.XBM,t1.SFZJH ");
			sb.append("from ZXXS_XS_JBXX t1 ");
			sb.append("left join ZXXS_XX_NJXX t2 on t2.XX_NJXX_ID = T1.XX_NJXX_ID ");
			sb.append("where t1.grbsm in(").append(xjhs).append(")");
			List<Map<String, Object>> list = getListSQL(sb.toString()); 
			
			sb.delete(0, sb.length());
			sb.append("select max(kscode) as KSCODE from CUS_KW_EXAMINEE where lcid='").append(lcid).append("'");
			List<Map<String, Object>> kscodeList = getListSQL(sb.toString()); 
			long kscode = 0;
			if(kscodeList != null && kscodeList.size() > 0) {
				kscode = Long.valueOf(kscodeList.get(0).get("KSCODE").toString());
			}
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					kscode++;
					String xjh = list.get(i).get("GRBSM") == null?"":list.get(i).get("GRBSM").toString();
					String xm = list.get(i).get("XM") == null?"":list.get(i).get("XM").toString();
					String xxdm = list.get(i).get("XX_JBXX_ID") == null?"":list.get(i).get("XX_JBXX_ID").toString();
					String nj = list.get(i).get("NJ") == null?"":list.get(i).get("NJ").toString();
					String bh = list.get(i).get("XX_BJXX_ID") == null?"":list.get(i).get("XX_BJXX_ID").toString();
					String xbm = list.get(i).get("XBM") == null?"":list.get(i).get("XBM").toString();
					String sfzjh = list.get(i).get("SFZJH") == null?"":list.get(i).get("SFZJH").toString();
					String ksid = UUID.randomUUID().toString().replace("-","");
					String scksid = UUID.randomUUID().toString().replace("-","");
					
					List ls = createHQLQuery("from CusKwExaminee where xjh='"+xjh+"' and sfzjh='"+sfzjh+"' and lcid='"+lcid+"'").list();
					if (ls.size()>0) {
                		this.getMsgBean(false,"导入考生信息重复！");
            			return MsgBean;
                	}
					CusKwExaminee cusKwExaminee = new CusKwExaminee();
					cusKwExaminee.setLcid(lcid);
					cusKwExaminee.setKscode(kscode+"");
					cusKwExaminee.setBh(bh);
					cusKwExaminee.setNj(nj);
					cusKwExaminee.setSfzjh(sfzjh);
					cusKwExaminee.setXbm(xbm);
					cusKwExaminee.setXjh(xjh);
					cusKwExaminee.setXm(xm);
					cusKwExaminee.setXxdm(xxdm);
					cusKwExaminee.setKsid(ksid);
					cusKwExaminee.setFlag("1");
					insert("insert into cus_kw_examinee(KSID,LCID,KSCODE,XJH,XM,XXDM,XBM,SFZJH,Flag) values('"+
							ksid+"','"+lcid+"','"+cusKwExaminee.getKscode()+"','"+cusKwExaminee.getXjh()+"','"+
							cusKwExaminee.getXm()+"','"+cusKwExaminee.getXxdm()+"','"+cusKwExaminee.getXbm()+"','"+cusKwExaminee.getSfzjh()+"','1')");
					CusKwAdditionee additionee = new CusKwAdditionee();
					additionee.setBh(bh);
					additionee.setKscode(new BigDecimal(kscode));
					additionee.setLcid(lcid);
					additionee.setNj(nj);
					additionee.setSfzjh(sfzjh);
					additionee.setXbm(xbm);
					additionee.setXjh(xjh);
					additionee.setXm(xm);
					additionee.setXxdm(xxdm);
					additionee.setScksid(scksid);
					save(additionee);
				}
				update("update CUS_KW_EXAMROUND a set SL=IFNULL((SELECT COUNT(*) FROM CUS_KW_EXAMINEE b WHERE b.LCID=a.LCID),0) where a.lcid='"+lcid+"'");
			}
			this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}

	public MsgBean uploadExamStu(String lcid, MBspInfo bspInfo) {
		try{
			String sql = "update CUS_KW_EXAMINEE set flag='0' where lcid='"+lcid+"'";
			this.update(sql);
			this.getMsgBean(true,"上报成功！");
		}catch(Exception e){ 
			this.getMsgBean(false,"上报失败！");
		}
		return MsgBean;
	}
	
}
