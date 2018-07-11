package com.jiajie.service.cheatStu.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.cheatStu.CusKwCheatstu;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.cheatStu.CheatStudentService;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;
import com.sun.xml.xsom.impl.scd.Iterators.Map;

@Service("cheatService")
@SuppressWarnings("all")
public class CheatStudentServiceImpl extends ServiceBridge implements CheatStudentService{
	
	/**
	 * 获取当前所有考试轮次信息
	 */
	public PageBean getList(String xn,String xq,MBspInfo bspInfo){
		MBspOrgan orgn = bspInfo.getOrgan();
		String sql = "SELECT T.* FROM (SELECT T1.DWTYPE,T1.DWID,T1.WJFLAG,T1.LCID,T1.XN,T2.NAME AS XQ,"+
				"T1.EXAMNAME,T3.MC AS EXAMTYPE,IFNULL((SELECT T5.REGION_EDU FROM COM_MEMS_ORGAN T5 WHERE T1.DWID=T5.REGION_CODE),"+
				"(SELECT (select region_edu from com_mems_organ where region_code=T4.SSZGJYXZDM) FROM ZXXS_XX_JBXX T4 WHERE T1.DWID=T4.XX_JBXX_ID)) AS DWMC,"+
				"IFNULL((select xxmc from zxxs_xx_jbxx where xx_jbxx_id = T1.dwid),'全部') CKDW,"+
				"IFNULL((SELECT COUNT(DISTINCT T6.wjid) FROM CUS_KW_CHEATSTU T6 left join CUS_KW_EXAMINEE ks on ks.ksid=T6.ksid WHERE T1.LCID=T6.LCID GROUP BY T6.LCID),0) AS WJKSS,"+
				"case when T1.WJFLAG='1' then '已审核' when T1.WJFLAG='0' then '已上报' else '未上报' end WJZT"+
				" FROM CUS_KW_EXAMROUND T1 "+
				" LEFT JOIN com_mems_organ zk ON zk.region_code = T1.dwid"+
				" LEFT JOIN com_mems_organ zk1 ON zk1.region_code = zk.PARENT_CODE"+
				" left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=T1.DWID"+
				" LEFT JOIN SYS_ENUM_VALUE T2 ON T1.XQM=T2.CODE AND T2.DIC_TYPE='ZD_XT_XQMC' "+
				" LEFT JOIN CUS_KW_EXAMTYPE T3 ON T1.EXAMTYPEM=T3.DM ";
		sql += " WHERE (T1.DWID='"+orgn.getOrganCode()+
				"' OR zk.region_code = '"+orgn.getOrganCode()+"' OR zk1.region_code = '"+orgn.getOrganCode()+
				"' OR zk1.PARENT_CODE = '"+orgn.getOrganCode()+"' or xx.SSZGJYXZDM='"+orgn.getOrganCode()+
				"' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgn.getOrganCode()+
				"') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgn.getOrganCode()+"')))";
		if(xn != null && !"".equals(xn)){
			sql += " AND T1.XN='"+xn+"' ";
		}
		if(xq != null && !"".equals(xq)){
			sql += " AND T1.XQM='"+xq+"' ";
		}
		sql += " ) T ORDER BY T.DWID,T.XN,T.LCID   ";
		return getSQLPageBean(sql);
	}
	
	/**
	 * 获取所有考试轮次
	 */
	public PageBean getExamRound(String xn,String xq,MBspInfo bspInfo){
		MBspOrgan orgn = bspInfo.getOrgan();
		String sql = "SELECT t.* FROM (SELECT T1.DWTYPE,T1.WJFLAG,T1.LCID,T1.XN,T2.NAME AS XQ," +
				"T1.EXAMNAME,T3.MC AS EXAMTYPE,IFNULL((SELECT T5.REGION_EDU FROM COM_MEMS_ORGAN T5 WHERE T1.DWID=T5.REGION_CODE)," +
				"(SELECT (select region_edu from com_mems_organ where region_code=T4.SSZGJYXZDM) FROM ZXXS_XX_JBXX T4 WHERE T1.DWID=T4.XX_JBXX_ID)) AS DWMC," +
				"IFNULL((select xxmc from zxxs_xx_jbxx where xx_jbxx_id = T1.dwid),'全部') CKDW," +
				"IFNULL((SELECT COUNT(DISTINCT T6.wjid) FROM CUS_KW_CHEATSTU T6 left join CUS_KW_EXAMINEE ks on ks.ksid=T6.ksid WHERE T1.LCID=T6.LCID  GROUP BY T6.LCID),0) AS WJKSS," +
				"case when T1.WJFLAG='1' then '已审核' when T1.WJFLAG='0' then '已上报' else '未上报' end WJZT"+
				" FROM CUS_KW_EXAMROUND T1 " +
				" LEFT JOIN com_mems_organ zk ON zk.region_code = T1.dwid"+
				" LEFT JOIN com_mems_organ zk1 ON zk1.region_code = zk.PARENT_CODE"+
				" left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=T1.DWID"+
				" LEFT JOIN SYS_ENUM_VALUE T2 ON T1.XQM=T2.CODE AND T2.DIC_TYPE='ZD_XT_XQMC' " +
				" LEFT JOIN CUS_KW_EXAMTYPE T3 ON T1.EXAMTYPEM=T3.DM WHERE 1=1 ";		
		sql += " AND (T1.DWID='"+orgn.getOrganCode()+
				"' OR zk.region_code = '"+orgn.getOrganCode()+"' OR zk1.region_code = '"+orgn.getOrganCode()+
				"' OR zk1.PARENT_CODE = '"+orgn.getOrganCode()+"' or xx.SSZGJYXZDM='"+orgn.getOrganCode()+
				"' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgn.getOrganCode()+
				"') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgn.getOrganCode()+"')))";		
		sql += " AND T1.WJFLAG IS NOT NULL AND T1.DWTYPE='1' ";
		if(xn != null && !"".equals(xn)){
			sql += " AND T1.XN='"+xn+"' ";
		}
		if(xq != null && !"".equals(xq)){
			sql += " AND T1.XQM='"+xq+"' ";
		}
		sql += ") t WHERE t.WJKSS>0 ORDER BY t.LCID ";
		return getSQLPageBean(sql);
	}
	
	/**
	 * 根据考试轮次获取当前轮次的违纪考生信息
	 */
	public PageBean getListByLcid(String lcid,String schools,String xmkhxjh,MBspInfo bspInfo,String flag,String wjid){
		List lst = getListSQL("select WJFLAG from CUS_KW_EXAMROUND where lcid='"+lcid+"'");
		if(lst != null && lst.size()>0){
			String wjflag = ((HashMap)lst.get(0)).get("WJFLAG")==null?"":((HashMap)lst.get(0)).get("WJFLAG").toString();
			if ("0".equals(wjflag)) {
				update("UPDATE CUS_KW_CHEATSTU SET FLAG='0' WHERE LCID='"+lcid+"'");
			}else if ("1".equals(wjflag)) {
				update("UPDATE CUS_KW_CHEATSTU SET FLAG='1' WHERE LCID='"+lcid+"'");
			}else {
				update("UPDATE CUS_KW_CHEATSTU SET FLAG='' WHERE LCID='"+lcid+"'");
			}
		}	
		String sql = "SELECT T1.KMID,T1.WJID,T1.LCID,T2.KSCODE,T2.SFZJH,T2.XJH,T2.XM,T3.NAME AS XB,T2.XXDM,T7.SUBJECTNAME, " +
				" T5.REGION_CODE AS XJCODE,T6.REGION_CODE AS SJJYJ,convert(T1.SCORE,char(10)) as SCORE,T1.DETAIL,T8.XXMC,T9.NAME AS NJMC,T10.BJMC, " +
				" (CASE T1.OPTTYPE when '1' then '扣除分数' when '2' then '记0分' END) AS OPTTYPENAME,T1.OPTTYPE,T1.FLAG," +
				" case when T1.FLAG='1' then '已审核' when T1.FLAG='0' then '已上报' else '未上报' end as ZT"+
				" FROM CUS_KW_CHEATSTU T1"+
				" LEFT JOIN CUS_KW_EXAMINEE T2 ON T1.KSID=T2.KSID " +
				" LEFT JOIN SYS_ENUM_VALUE T9 on T2.NJ=T9.CODE AND DIC_TYPE='ZD_XT_NJDM' " +
				" LEFT JOIN ZXXS_XX_BJXX T10 ON T2.BH=T10.XX_BJXX_ID " +
				" LEFT JOIN ZXXS_XX_JBXX T8 ON T2.XXDM=T8.XX_JBXX_ID " +
				" LEFT JOIN SYS_ENUM_VALUE T3 ON T2.XBM=T3.CODE AND T3.DIC_TYPE='ZD_GB_XBM' " +
				" LEFT JOIN COM_MEMS_ORGAN T5 ON T8.SSZGJYXZDM=T5.REGION_CODE " +
				" LEFT JOIN COM_MEMS_ORGAN T6 ON T5.PARENT_CODE=T6.REGION_CODE " +
				" LEFT JOIN CUS_KW_EXAMSUBJECT T7 ON T1.KMID=T7.KMID " +
				" LEFT JOIN CUS_KW_EXAMROUND T88 ON T1.LCID=T88.LCID WHERE 1=1 " ;
		if("1".equals(flag)){
			//如果是组考单位组织的考试，必须要审核完成才能作为打印数据。参考单位组织考试，都可以打印 AND T88.WJFLAG='1'
			sql += " AND (( T88.DWTYPE='1') OR T88.DWTYPE='2') ";
		}
		if(lcid != null && !"".equals(lcid)){
			sql += " AND T1.LCID='"+lcid+"' ";
		}
		if("1".equals(bspInfo.getUserType())||"3".equals(bspInfo.getUserType())){
			if(schools != null && !"".equals(schools) && !"undefined".equals(schools)){
				sql += " AND T2.XXDM IN ('"+schools.replaceAll(",", "','")+"') ";
			}
		}else if("3".equals(bspInfo.getUserType())){
			if(schools != null && !"".equals(schools) && !"undefined".equals(schools)){
				sql += " AND T2.BH IN ('"+schools.replaceAll(",", "','")+"') ";
			}
			sql += " AND T2.XXDM='"+bspInfo.getOrgan().getOrganCode()+"' ";
		}
		if(xmkhxjh != null && !"".equals(xmkhxjh)){
			sql += " AND (T2.KSCODE LIKE binary('%"+xmkhxjh+"%') OR T2.XJH LIKE binary('%"+xmkhxjh+"%') OR T2.XM LIKE binary('%"+xmkhxjh+"%')) ";
		}
		if(wjid != null && !"".equals(wjid)){
			sql += " AND T1.WJID IN ('"+wjid.replaceAll(",", "','")+"') ";
		}
		sql += " ORDER BY substr(T7.SUBJECTNAME,11,length(substr(T7.SUBJECTNAME,11))-length('批'))+0 asc";
		PageBean pb = getSQLPageBean(sql);
		List list = new ArrayList();
		if(pb != null){
			List rList = pb.getResultList();	
			if(rList != null && rList.size()>0){
				for(int i=0;i<rList.size();i++){
					HashMap map = (HashMap) rList.get(i);
					String str = (String)map.get("DETAIL");
					if(str != null){
						String wjqq = str;//Clob clobToString
						map.put("WJQQ", wjqq);
					}else{
						map.put("WJQQ", "");
					}
					list.add(map);
				}
			}
		}
		pb.setResultList(list);
		return pb;
	}
	
	/**
	 * 删除违纪考生
	 */
	public com.jiajie.bean.MsgBean deleteCheatStu(String wjids){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("delete from CUS_KW_CHEATSTU where wjid in(").append(wjids).append(")");
			return delete(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return this.getMsgBean(false,MsgBean.DEL_ERROR);
		}
	}
	
	/**
	 * 上报违纪考生
	 */
	public MsgBean reportedCheatStu(String lcid){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" UPDATE CUS_KW_EXAMROUND SET WJFLAG='0' WHERE LCID='"+lcid+"' ");
			update(sb.toString());
			sb.delete(0, sb.length());
			sb.append(" UPDATE CUS_KW_CHEATSTU SET FLAG='0' WHERE LCID='"+lcid+"' ");
			update(sb.toString());
			return this.getMsgBean(true,"上报成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return this.getMsgBean(false,"上报失败！");
		}
	}
	
	/**
	 * 审核违纪考生
	 */
	public MsgBean auditCheatStudent(String lcid){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" UPDATE CUS_KW_EXAMROUND SET WJFLAG='1' WHERE WJFLAG='0' AND LCID='"+lcid+"' ");
			update(sb.toString());
			sb.delete(0, sb.length());
			sb.append(" UPDATE CUS_KW_CHEATSTU SET FLAG='1' WHERE FLAG='0' AND LCID='"+lcid+"' ");
			update(sb.toString());
			return this.getMsgBean(true,"审核成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return this.getMsgBean(false,"审核失败！");
		}
	}
		
	public MsgBean saveOrUpdateCheatStu(CusKwCheatstu cheatStu,MBspInfo bspInfo){
		//判断考生号是否存在
		String sql  = "SELECT KSID FROM CUS_KW_EXAMINEE WHERE LCID='"+cheatStu.getLcid()+"' AND SFZJH='"+cheatStu.getKskh()+"' ";
		if("3".equals(bspInfo.getUserType())){
			sql += " AND XXDM='"+bspInfo.getOrgan().getOrganCode()+"' ";
		}
		List list = getListSQL(sql);
		if(list != null && list.size()>0){
			cheatStu.setKsid(((HashMap)list.get(0)).get("KSID").toString());
			try {
				List lst = getListSQL("select * from cus_kw_cheatstu where ksid='"+cheatStu.getKsid()+
				"' and kmid='"+cheatStu.getKmid()+"' and detail='"+cheatStu.getDetail()+"'");
				if(lst != null && lst.size()>0){
					this.getMsgBean(false,"该考生违纪记录已存在！");						
				}else {
					saveOrUpdate(cheatStu);
					update("update CUS_KW_EXAMROUND set WJFLAG=null where lcid='"+cheatStu.getLcid()+"'"); 
					this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
				}				
			} catch (Exception e) {
				e.printStackTrace();
				this.getMsgBean(false,MsgBean.SAVE_ERROR);
			}
			return MsgBean;
		}else {
			this.getMsgBean(false,"输入的考生不存在！");
			return MsgBean;
		}
	}
	
	/*public static String clobToString(Clob clob){
			String reString = "";
			Reader is = null;
			try {
				is = clob.getCharacterStream();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 得到流
			BufferedReader br = new BufferedReader(is);
			String s = null;
			try {
				s = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			StringBuffer sb = new StringBuffer();
			while (s != null) {
				// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
				sb.append(s);
				try {
					s = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			reString = sb.toString();
			return reString;
	}*/
}
