package com.jiajie.service.examineePrint.impl;
/**
 * 考试成绩登记
 * **/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.examResults.CusKwStuScore;
import com.jiajie.bean.resultsStatisticalCollection.CusKwExecise;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.examResults.ResultsResgisterService;
import com.jiajie.service.examineePrint.ResultsListService;
import com.jiajie.util.SecUtils;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;
import com.jiajie.bean.MsgBean;
import com.mysql.jdbc.StringUtils;

@Service("resultsListService")
@SuppressWarnings("all")
public class ResultListServiceImpl extends ServiceBridge implements ResultsListService{

	public PageBean getListPage(String xnxq, MBspInfo bspInfo) {
		String xn = "";
		String xq = "";
//		System.out.println(xnxq);
		if(xnxq != null && !"".equals(xnxq)){
			xn = xnxq.substring(0, 4);
			xq = xnxq.substring(8, 9);
		}
		MBspOrgan orgn = bspInfo.getOrgan();
		String sql = "SELECT DISTINCT T.*,(T.KSSL-T.YK) QK FROM (SELECT T1.BMLCID,T1.XQM,T1.XN,T2.NAME AS XQ,T1.EXAMNAME AS KSMC,IFNULL((SELECT T5.REGION_EDU FROM COM_MEMS_ORGAN T5 WHERE T1.DWID=T5.REGION_CODE),(SELECT (select region_edu from com_mems_organ where region_code=T4.SSZGJYXZDM) FROM ZXXS_XX_JBXX T4 WHERE T1.DWID=T4.XX_JBXX_ID)) AS ZKDW,IFNULL((select xxmc from zxxs_xx_jbxx where xx_jbxx_id = T1.dwid),'全部') CKDW,T1.SL AS KSSL,(SELECT COUNT(BH) FROM cus_kw_examinee WHERE BMLCID=T1.BMLCID AND BH>59) AS HGRS,(SELECT COUNT(BH) FROM cus_kw_examinee WHERE BMLCID=T1.BMLCID AND BH<60) AS WHGRS,(SELECT COUNT(c.isks) FROM  cus_kw_examinee c WHERE c.BMLCID = T1.BMLCID and c.isks='1') YK FROM CUS_KWBM_EXAMROUND T1 left join com_mems_organ zk on zk.region_code=T1.dwid left join com_mems_organ zk1 on zk1.region_code=zk.PARENT_CODE left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=T1.DWID LEFT JOIN SYS_ENUM_VALUE T2 ON T1.XQM=T2.CODE AND T2.DIC_TYPE='ZD_XT_XQMC'";
		sql += " WHERE (T1.dwid='"+orgn.getOrganCode()+
				"' OR zk.region_code='"+orgn.getOrganCode()+"' OR zk1.region_code='"+orgn.getOrganCode()+
				"' OR zk1.PARENT_CODE = '"+orgn.getOrganCode()+"' or xx.SSZGJYXZDM='"+orgn.getOrganCode()+
				"' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgn.getOrganCode()+
				"') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgn.getOrganCode()+"')))) T where 1=1 ";
		if(xnxq==null || "".equals(xnxq)){
			sql += "and T.XN=(select xnmc from zxxs_xnxq where mr='1')";
		}else{
			if(xn != null && !"".equals(xn)){
				sql += " AND T.XN='"+xn+"' ";
			}
			if(xq != null && !"".equals(xq)){
				sql += " AND T.XQM='"+xq+"' ";
			}
		} 
		System.out.println(sql);
		return getSQLPageBean(sql);
	}
	
	public PageBean getStudentScore(String bmlcid,String name,String sfzjh,String sfhg,String wj){
		String str = "select FBCJ FROM cus_kw_examround T join cus_kwbm_examround T1 on T.lcid=T1.lcid where T1.BMLCID='"+bmlcid+"'";
		String sql = "select s.xm,s.xb,s.zkdw,s.ckdw,s.sfzjh,s.kssj,s.kdmc,s.kcmc,s.zwh,s.score,s.sfhg,s.wj,s.bz from exam_score_detail s left join cus_kw_examinee e on s.sfzjh=e.sfzjh join cus_kwbm_examround r on r.BMLCID=e.BMLCID where 1=1";
		if(bmlcid != null && !"".equals(bmlcid)){
			sql += " AND e.BMLCID='" + bmlcid + "'";
		}
		if ((name != null) && (!"".equals(name))) {
			sql +=  " AND s.xm like '%" + name + "%'";
		}
		if ((sfzjh != null) && (!"".equals(sfzjh))) {
			sql +=  " AND s.sfzjh='" + sfzjh + "'";
		}
		if((sfhg != null) && (!"".equals(sfhg))){
			if(sfhg.equals("2")){
				sql += " AND s.sfhg='合格'";
			}
			if(sfhg.equals("3")){
				sql += " AND s.sfhg='未合格'";
			}
		}
		if((wj != null) && (!"".equals(wj))){
			if(wj.equals("2")){
				sql += " AND s.wj!=''";
			}
			if(wj.equals("3")){
				sql += " AND s.wj=''";
			}
		}
		System.out.println(sql);
		return getSQLPageBean(sql);
	}
	
	public MsgBean checkStatue(String bmlcid){
		String sql = "select SFFB FROM cus_kw_examround T join cus_kwbm_examround T1 on T.lcid=T1.lcid where T1.BMLCID='"+bmlcid+"'";
		List ls = getListSQL(sql);
		return getMsgBean(ls.get(0));
	}
}
