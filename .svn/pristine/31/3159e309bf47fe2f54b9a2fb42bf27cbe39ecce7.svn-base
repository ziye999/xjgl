package com.jiajie.service.examArrangement.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.examArrangement.CusKwPointinfo;
import com.jiajie.bean.examArrangement.CusKwPointstu;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.examArrangement.ExamSchoolArrangeService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;

@Service("arrangeService")
@SuppressWarnings("all")
public class ExamSchoolArrangeServiceImpl extends ServiceBridge implements ExamSchoolArrangeService{
	public PageBean getList(String organ_sel,String lcid,MBspInfo bspInfo){
		String organCode ="";
		if(StringUtil.isNotNullOrEmpty(organ_sel)){
			organCode="'"+organ_sel.replaceAll(",", "','")+"'";
		}else{
			organCode="'"+bspInfo.getOrgan().getOrganCode()+"'";			
		}
		String sql="SELECT distinct T1.XXBSM,T1.XX_JBXX_ID,T1.XXMC,T1.XXDZ,T2.REGION_NAME AS XIAN,T3.REGION_NAME AS SHI,"+
				"case when (select count(apid) from CUS_KW_POINTINFO where LCID = '"+lcid+
				"' and XXDM=T1.XX_JBXX_ID)>0 then '是' else '否' end AS SFCK"+				
				" FROM ZXXS_XX_JBXX T1"+				
				" LEFT JOIN COM_MEMS_ORGAN T2 ON T1.SSZGJYXZDM=T2.REGION_CODE"+
				" LEFT JOIN COM_MEMS_ORGAN T3 ON T2.PARENT_CODE=T3.REGION_CODE"+
				" LEFT JOIN COM_MEMS_ORGAN T4 ON T3.PARENT_CODE=T4.REGION_CODE"+
				" WHERE T1.dwlx='1' and (T2.REGION_CODE IN ("+organCode+") OR T3.REGION_CODE IN ("+organCode+") OR T4.REGION_CODE IN ("+organCode+"))";
		PageBean pb = getSQLPageBean(sql);
		return pb;
	}
	
	public PageBean getSeating(String kdid,String lcid,String xxdm){
		String sql = " SELECT T1.XX_JBXX_ID AS XXDM,T2.LCID,T1.XXMC,T1.XXBSM, IFNULL(T2.ZRS, 0) ZRS, IFNULL(T3.YAP, 0) YAP, " +
				" (IFNULL(T2.ZRS, 0)-IFNULL(T3.YAP, 0)) SYRS  FROM " +
				" (SELECT XX.XX_JBXX_ID,XX.XXMC,XX.XXBSM FROM ZXXS_XX_JBXX XX " +				
				" XX.SSZGJYXZDM IN (SELECT SSZGJYXZDM FROM ZXXS_XX_JBXX " +
				" WHERE XX_JBXX_ID='"+xxdm+"')) T1 LEFT JOIN " +
				" (SELECT COUNT(*) ZRS, XXDM,LCID FROM CUS_KW_EXAMINEE WHERE LCID='"+lcid+"' GROUP BY XXDM,LCID) T2 on T1.XX_JBXX_ID=T2.XXDM " +
				" LEFT JOIN (SELECT SUM(STUCOUNT) YAP, XXDM FROM CUS_KW_POINTINFO GROUP BY XXDM) T3 on T1.XX_JBXX_ID=T3.XXDM WHERE (IFNULL(T2.ZRS, 0)-IFNULL(T3.YAP, 0))>0 ";
		return getSQLPageBean(sql);
	}
	
	public PageBean getStudent(String lcid,String jyj,String xxdm,String nj,String bj){
		String sql = " SELECT T1.XM,T1.XJH,T1.KSCODE,T2.NAME AS XB,T3.NAME AS NJMC,T4.BJMC,T1.KSID,T5.XXMC,T1.XXDM FROM CUS_KW_EXAMINEE T1 " +
				" LEFT JOIN SYS_ENUM_VALUE T2 ON T1.XBM=T2.CODE AND T2.DIC_TYPE='ZD_GB_XBM' " +
				" LEFT JOIN SYS_ENUM_VALUE T3 ON T1.NJ=T3.CODE AND T3.DIC_TYPE='ZD_XT_NJDM' " +
				" LEFT JOIN ZXXS_XX_BJXX T4 ON T1.BH=T4.XX_BJXX_ID " +
				" LEFT JOIN ZXXS_XX_JBXX T5 ON T1.XXDM=T5.XX_JBXX_ID " +				
				" WHERE T1.SSZGJYXZDM='" +jyj+"' and T1.KSID NOT IN (SELECT XSID FROM CUS_KW_POINTSTU WHERE LCID='"+lcid+"') " +
				" AND T1.LCID='"+lcid+"' ";
		if(xxdm != null && !"".equals(xxdm)){
			sql += " AND T1.XXDM='"+xxdm+"' ";
		}
		if(nj != null && !"".equals(nj)){
			sql += " AND T1.NJ='"+nj+"' ";
		}
		if(bj != null && !"".equals(bj)){
			sql += " AND T1.BH='"+bj+"' ";
		}
		return getSQLPageBean(sql);
	}
	
	public MsgBean addStudentArrange(String ksids,String lcid,String xxdms,String kdid){
		boolean flag = true;
		String[] ksidObj = new String[1];
		String[] xxdmObj = new String[1];
		if(ksids.contains(",")){
			ksidObj = ksids.split(",");
			xxdmObj = xxdms.split(",");
		}else{
			ksidObj[0] = ksids;
			xxdmObj[0] = xxdms;
		}
		for(int i=0;i<ksidObj.length;i++){
			String ksid = ksidObj[i];
			String xxdm = xxdmObj[i];
			CusKwPointstu stu = new CusKwPointstu();
			stu.setKdid(kdid);
			stu.setLcid(lcid);
			stu.setXxdm(xxdm);
			stu.setXsid(ksid);
			try {
				saveOrUpdate(stu);
				this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				this.getMsgBean(false,MsgBean.SAVE_ERROR);
			}
		}
		return MsgBean;
	}
	
	public MsgBean updateData(){
		//执行数据更新
		String sql = " SELECT LCID,KDID,XXDM,COUNT(*) AS RS FROM CUS_KW_POINTSTU " +
				" GROUP BY LCID,KDID,XXDM ";
		List list = getListSQL(sql);
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (HashMap)list.get(i);
				String lcid1 = map.get("LCID")==null?"":map.get("LCID").toString();
				String kdid1 = map.get("KDID")==null?"":map.get("KDID").toString();
				String xxdm1 = map.get("XXDM")==null?"":map.get("XXDM").toString();
				String rs = map.get("RS")==null?"":map.get("RS").toString();
				//判断考点信息表是否有人数统计记录
				sql = " SELECT APID FROM CUS_KW_POINTINFO WHERE LCID='"+lcid1+"' " +
						" AND KDID='"+kdid1+"' AND XXDM='"+xxdm1+"' ";
				List subList = getListSQL(sql);
				CusKwPointinfo info = new CusKwPointinfo();
				//info.setKdid(kdid1);
				info.setLcid(lcid1);
				info.setXxdm(xxdm1);
				//info.setStucount(Integer.parseInt(rs));
				if(subList.size()>0){
					info.setApid(((HashMap)subList.get(0)).get("APID").toString());
				}
				try {
					saveOrUpdate(info);
					this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
				} catch (Exception e) {
					this.getMsgBean(false,MsgBean.SAVE_ERROR);
				}
			}
		}
		return MsgBean;
	}
	
	public MsgBean autoStudentArrange(String lcid,String kdid,String xxdms,String aprses){
		String[] xxdmObj = new String[1];
		String[] aprsObj = new String[1];
		if(xxdms.contains(",")){
			xxdmObj = xxdms.split(",");
			aprsObj = aprses.split(",");
		}else{
			xxdmObj[0] = xxdms;
			aprsObj = aprses.split(",");
		}
		one:for(int i=0;i<xxdmObj.length;i++){
			String xxdm = xxdmObj[i];
			//根据考试轮次获取当前参考单位已报名的考生，并且该考生未安排到考点
			String sql = " SELECT KSID FROM CUS_KW_EXAMINEE WHERE LCID='"+lcid+"' AND XXDM='"+xxdm+"' " +
					" AND KSID NOT IN (SELECT XSID FROM CUS_KW_POINTSTU WHERE LCID='"+lcid+"' AND XXDM='"+xxdm+"') ";
			List list = getListSQL(sql);
			if(list != null && list.size() > 0){
				for(int j=0;j<list.size();j++){
					if(j==Integer.parseInt(aprsObj[i])){
						continue one;
					}
					String ksid = ((HashMap)list.get(j)).get("KSID")==null?"":((HashMap)list.get(j)).get("KSID").toString();
					CusKwPointstu stu = new CusKwPointstu();
					stu.setKdid(kdid);
					stu.setLcid(lcid);
					stu.setXxdm(xxdm);
					stu.setXsid(ksid);
					try {
						saveOrUpdate(stu);
						this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
					} catch (Exception e) {
						this.getMsgBean(false,MsgBean.SAVE_ERROR);
					}
				}
			}
		}
		return MsgBean;
	}
	
	public MsgBean deleteSchoolArrange(){
		//清空考点考生统计表
		try {
			String sql = " DELETE FROM CUS_KW_POINTINFO ";
			return deleteBySql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return this.getMsgBean(false,MsgBean.DEL_ERROR);
		}
	}
	
	public MsgBean deleteArrange(String lcid,String kdid){
		//清空考生安排
		try {
			String sql = " DELETE FROM CUS_KW_POINTSTU WHERE LCID='"+lcid+"' AND KDID='"+kdid+"' ";
			return deleteBySql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return this.getMsgBean(false,MsgBean.DEL_ERROR);
		}
	}

	public com.jiajie.bean.MsgBean deleteCKSchool(String lcid, String xxcode) {
		String xxdm="'"+xxcode.replaceAll(",", "','")+"'";
		String sql="DELETE FROM CUS_KW_POINTINFO WHERE LCID='"+lcid+"' AND XXDM IN ("+xxdm+")";
		delete(sql);
		return this.getMsgBean(false,MsgBean.DEL_SUCCESS);
	}

	public com.jiajie.bean.MsgBean saveCKSchool(String lcid, String xxcode) {
		String[] xxdm=xxcode.split(",");
		for (int i = 0; i < xxdm.length; i++) {
			CusKwPointinfo pointinfo=new CusKwPointinfo();
			pointinfo.setLcid(lcid);
			pointinfo.setXxdm(xxdm[i]);
			getSession().save(pointinfo);
		}
		return this.getMsgBean(false,MsgBean.SAVE_SUCCESS);
	}
}
