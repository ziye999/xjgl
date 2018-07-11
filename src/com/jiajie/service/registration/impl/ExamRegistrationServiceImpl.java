package com.jiajie.service.registration.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;

import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExaminee;
import com.jiajie.bean.system.TQxglUserinfo;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.registration.ExamRegistrationService;
import com.jiajie.util.IdCardUtils;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;

@Service("examRegistrationService")
@SuppressWarnings("all")
public class ExamRegistrationServiceImpl extends ServiceBridge implements ExamRegistrationService{

	public PageBean getList(String xn,String dwid,MBspInfo mbf) {
		String nn = "";
		String xq = "";
		if(xn!=null && !"".equals(xn)){
			String[] str = xn.split(" ");
			nn = str[0].substring(0, 4);
			xq = str[1];
		}
		String sql = "select DISTINCT s1.* from (select DISTINCT rd.LCID,rd.XN,rd.XQM,rd.EXAMNAME,rd.STARTWEEK,rd.STARTDAY,"+
				"rd.STARTDATE,rd.ENDWEEK,rd.ENDDAY,rd.ENDDATE,rd.EXAMTYPEM,rd.DWID,rd.DWTYPE,"+
				"case rd.SHFLAG when '1' then '已审核' when '0' then '已上报' else '未上报' end as SHFLAG,xq.mc XQ,rd.SL,"+
				"IFNULL(jb.region_edu,(select (select region_edu from com_mems_organ where region_code=a.SSZGJYXZDM) from zxxs_xx_jbxx a where a.xx_jbxx_id=rd.dwid)) MC,"+
				"IFNULL((select xxmc from zxxs_xx_jbxx where xx_jbxx_id = rd.dwid),'全部') CKDW,"+
				"(select count(SHFLAG) from cus_kwbm_examround  where LCID=rd.LCID and SHFLAG='1') TGDW "+				
				"from cus_kw_examround rd "+ 
				"left join com_mems_organ jb on jb.region_code = rd.dwid "+
				"left join com_mems_organ jb1 on jb1.region_code = jb.parent_code "+
				"left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=rd.DWID  "+
				"left join zd_xq xq on rd.xqm=xq.dm "+
				"where (rd.dwid='"+mbf.getOrgan().getOrganCode()+
				"' or jb.region_code='"+mbf.getOrgan().getOrganCode()+"' or jb1.region_code='"+mbf.getOrgan().getOrganCode()+
				"' OR jb1.PARENT_CODE = '"+mbf.getOrgan().getOrganCode()+"' or xx.SSZGJYXZDM='"+mbf.getOrgan().getOrganCode()+
				"' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+mbf.getOrgan().getOrganCode()+
				"') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+mbf.getOrgan().getOrganCode()+"')))) s1 where 1=1";
		if(nn!=null && !"".equals(nn)){
			sql += " and s1.xn='"+nn+"'";
		}
		if(xq!=null && !"".equals(xq)){
			sql += " and s1.xq = '"+xq+"'";
		}
		System.out.println(sql);
		return getSQLPageBean(sql); 
	}
	
	public PageBean getXsList(String lcid){
		String sql ="select xb.mc XB,ee.KSID,ee.LCID,ee.KSCODE,ee.XJH,ee.XM,ee.XXDM,ee.NJ,ee.BH,"+
				"ee.XBM,ee.SFZJH,ee.FLAG,xx.XXMC,bj.BJMC,nj.name NJMC "+
				"from cus_kw_examinee ee " +
				"left join zd_xb xb on xb.dm=ee.xbm " +
				"left join zxxs_xx_jbxx xx on xx.xx_jbxx_id=ee.xxdm "+
				"left join (select * from SYS_ENUM_VALUE where DIC_TYPE='ZD_XT_NJDM') nj on nj.code=ee.nj "+
				"left join zxxs_xx_bjxx bj on bj.xx_bjxx_id=ee.bh " +
				"where ee.lcid='"+lcid+"'";
		return getSQLPageBean(sql);
	}

	public Object getSjjyj(String dwid,String dwtype) {
		String sql=" SELECT REGION_CODE AS CODEID,REGION_EDU AS CODENAME FROM COM_MEMS_ORGAN ";
		if("1".equals(dwtype)) {
			/*if(!"".equals(dwid) && dwid.endsWith("0000000000")){
				//省级组考单位
				sql = sql + " WHERE SUBSTR(REGION_CODE, 1, 2)='"+dwid.substring(0, 2)+"' " +
						" AND SUBSTR(REGION_CODE, 5, 8)='00000000' " +
						" AND SUBSTR(REGION_CODE, 3, 2)<>'00' ";
			}else if(!"".equals(dwid) && dwid.endsWith("00000000")){*/
				//市级组考单位
				sql = sql + " WHERE REGION_CODE IN ('"+dwid+"')";
			//}
		}else if("2".equals(dwtype)){
			//参考单位
			sql = sql + " WHERE REGION_CODE IN ( SELECT T1.PARENT_CODE FROM COM_MEMS_ORGAN T1 " +
			" LEFT JOIN ZXXS_XX_JBXX T2 ON T2.sszgjyxzdm=T1.REGION_CODE " +
			" WHERE T2.XX_JBXX_ID='"+dwid+"' ) ";
		}
		List list=getListSQL(sql);
		return list;
	}
	
	public Object getJyj(String sjjyj,String dwtype,String xxdm){
		String sql=" SELECT T1.REGION_CODE AS CODEID,T1.REGION_EDU AS CODENAME FROM COM_MEMS_ORGAN T1 " ;
		if("1".equals(dwtype)){
			sql += "WHERE SUBSTR(T1.REGION_CODE, 1, 4)='"+sjjyj.substring(0, 4)+"' " +
					" AND T1.REGION_CODE<>'"+sjjyj+"' " ;
			if(!"".equals(xxdm) && !"0".equals(xxdm.substring(4, 5))){ 
				sql += " and t1.REGION_CODE='"+xxdm+"'";
			}
		}else if("2".equals(dwtype)){
			sql += " LEFT JOIN ZXXS_XX_JBXX T2 ON T2.sszgjyxzdm=T1.REGION_CODE " +
			" WHERE T2.XX_JBXX_ID='"+xxdm+"' ";
		}
		List list=getListSQL(sql);
		return list;
	}
	
	//获取登陆参考单位
	public Object getSchool(String xjjyj,String dwtype,String dwid){
		String sql = "";
		if("1".equals(dwtype)){
			sql = " SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME FROM ZXXS_XX_JBXX T1 " +						
					" WHERE (T1.SSZGJYXZDM='"+xjjyj+"' or T1.SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+xjjyj+
					"') or T1.SSZGJYXZDM IN (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+
					xjjyj+"'))) ";
		}else if("2".equals(dwtype)){
			sql = " SELECT distinct XX_JBXX_ID AS CODEID,XXMC AS CODENAME FROM ZXXS_XX_JBXX " +
					" WHERE XX_JBXX_ID='"+dwid+"' ";
		}
		List list=getListSQL(sql);
		return list;
	}

	public PageBean getCqks(String xx,String lcid,MBspInfo bspInfo,String khao,String sfzjh) {
		String orgCode = bspInfo.getOrgan().getOrganCode();
		String sql = "select distinct xb.mc XB,xs.grbsm XJH,xs.XM,xs.SFZJH,t5.XXMC,"+
				"xs.XX_JBXX_ID,xs.XX_BJXX_ID,substr(xs.BMDM,1,4) as BMDM,T5.YZBM "+
				"from zxxs_xs_jbxx xs "+
				"LEFT JOIN zd_xb xb on xb.dm=xs.xbm "+				
				"LEFT JOIN ZXXS_XX_JBXX T5 ON T5.xx_jbxx_id=xs.xx_jbxx_id "+//参考单位所属县组考单位
				"LEFT JOIN COM_MEMS_ORGAN T6 ON T6.REGION_CODE=T5.SSZGJYXZDM "+//参考单位所属市组考单位
				"LEFT JOIN COM_MEMS_ORGAN T7 ON T7.REGION_CODE=T6.PARENT_CODE "+//参考单位所属省组考单位
				"where xs.sfzjh not in (select sfzjh from cus_kw_examinee where lcid='"+lcid+"')";
		if(xx!=null && !"".equals(xx)){
			sql += " and xs.xx_jbxx_id in ('"+xx.replaceAll(",", "','")+"') ";
		}
		if(sfzjh!=null && !"".equals(sfzjh)){
			sql += " and xs.SFZJH like binary('%"+sfzjh+"%')";
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM",Locale.CHINA);		
		String dateStr = format.format(new Date());
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMaximumIntegerDigits(6);
		nf.setMinimumIntegerDigits(6);		
		
		PageBean pb = getSQLPageBean(sql);
		List list = pb.getResultList();
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (HashMap)list.get(i);
				String yzbm = (String)map.get("YZBM");				
				String xxdm = (String)map.get("XX_JBXX_ID");
				List lsKh = getListSQL("select count(ksid)+1 KH from cus_kw_examinee where lcid='"+lcid+"' and xxdm='"+xxdm+"'");
				if (lsKh!=null&&lsKh.size()>0) {
					Map<String, Object> mp = (Map<String, Object>)lsKh.get(0);
					String kh = mp.get("KH")==null?"1":mp.get("KH").toString();
					if (Integer.valueOf(kh).intValue()>Integer.valueOf(khao).intValue()) {
						khao = kh;
					}
				}
				String kscode = (yzbm==null?"":yzbm)+dateStr+nf.format(Integer.parseInt(khao)+i);
				kscode = getKscode(lcid,kscode);
				map.put("KSCODE", kscode);
			}
		}
		return pb;
	}
	public String getKscode(String lcid,String kscode){
		List lstE = getListSQL("select * FROM cus_kw_examinee WHERE lcid='"+lcid+"' and KSCODE = '"+kscode+"'");
    	if (lstE.size()>0) {
    		kscode = (Integer.valueOf(kscode).intValue()+1)+"";
    		getKscode(lcid,kscode);
    	}
    	return kscode;
	}
	
	public com.jiajie.bean.MsgBean saveEaxminee(String lcid,String khao,String xjh) {
		try {
			if(xjh!=null && !"".equals(xjh)){
				String[] xjhs = xjh.split(",");
				String[] kscode = khao.split(",");
				for(int i=0;i<xjhs.length;i++){
					List list = getXsByxjh("","",xjhs[i].toString(),lcid);
					if(list!=null && list.size()>0){
						Map<String, Object> mp = (Map<String, Object>)list.get(0);
						CusKwExaminee examinee = new CusKwExaminee();
						examinee.setLcid(lcid);
						examinee.setKscode(kscode[i]);
						examinee.setXjh(mp.get("GRBSM")==null?"":mp.get("GRBSM").toString());
						examinee.setXm(mp.get("XM")==null?"":mp.get("XM").toString());
						examinee.setXxdm(mp.get("XX_JBXX_ID")==null?"":mp.get("XX_JBXX_ID").toString());
						//examinee.setNj(mp.get("XJNJDM")==null?"":mp.get("XJNJDM").toString());
						//examinee.setBh(mp.get("XX_BJXX_ID")==null?"":mp.get("XX_BJXX_ID").toString());
						examinee.setXbm(mp.get("XBM")==null?"":mp.get("XBM").toString());
						examinee.setSfzjh(mp.get("SFZJH")==null?"":mp.get("SFZJH").toString());
						//saveOrUpdate(examinee);	
						insert("insert into cus_kw_examinee(KSID,LCID,KSCODE,XJH,XM,XXDM,XBM,SFZJH) values('"+
								StringUtil.getUUID()+"','"+lcid+"','"+kscode[i]+"','"+examinee.getXjh()+"','"+
								examinee.getXm()+"','"+examinee.getXxdm()+"','"+examinee.getXbm()+"','"+examinee.getSfzjh()+"')");
					}
				}
			}
			update("update CUS_KW_EXAMROUND a set SL=IFNULL((SELECT COUNT(*) FROM CUS_KW_EXAMINEE b WHERE b.LCID=a.LCID),0) where a.lcid='"+lcid+"'");
			this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}

	public com.jiajie.bean.MsgBean updateEaxminee(String lcid) {
		String str = "select dwid,shflag  from cus_kwbm_examround where sl is not null and sl!=0 and sl!='' and  LCID='"+lcid+"'";
		List ls = getListSQL(str);
		boolean flag = true;
		if(ls!=null&&ls.size()>0){
			for(int i = 0;i<ls.size();i++){
				Map<String, Object> map = (Map<String, Object>)ls.get(i);
				if(!"1".equals(map.get("shflag")) || !"3".equals(map.get("shflag"))){
					flag = false;
					break;
				}
			}
			if(!flag){
				return getMsgBean(false,"该轮次下有考生的参考单位没有全部审核通过，无法上报轮次！");
			}
			try {
				String sql = "update cus_kw_examinee set flag='0' where lcid='"+lcid+"'";
				update(sql);
				String sql2 = "update cus_kw_examround set shflag='0' where lcid='"+lcid+"'";
				update(sql2);
				return getMsgBean(true, "上报成功！");
			} catch (Exception e) {
				e.printStackTrace();
				return getMsgBean(false, "上报失败！");
			}
		}else{
			return getMsgBean(false, "上报失败！");
		}
		
				
	}

	public List getKh(String lcid, String khao) {
		String sql = "select * from cus_kw_examinee where lcid='"+lcid+"' and kscode='"+khao+"'";
		return getListSQL(sql);
	}	
	
	public List getXsByxjh(String ckdwh,String ckdw,String xjh,String lcid){
		String sql = "select xs.XS_JBXX_ID,xs.GRBSM,xs.XX_JBXX_ID,xs.XX_NJXX_ID,xs.XX_BJXX_ID,"+
				"xs.XM,xs.XBM,xs.CSRQ,xs.CSDM,xs.JG,xs.MZM,xs.BMDM,xs.BMDZ,xs.SFZJLXM,xs.SFZJH,"+
				"xs.JTZZ,xs.TXDZ,xs.LXDH,xs.YZBM,xs.ZZMM,xs.XXSSZGJYXZDM,nj.XJNJDM "+
				"from zxxs_xs_jbxx xs "+
				"left join zxxs_xx_njxx nj on nj.xx_njxx_id=xs.xx_njxx_id "+
				"where xs.grbsm='"+xjh+"' "+
				(xjh==null||"".equals(xjh)?"":"and xs.grbsm not in(select xjh from cus_kw_examinee where lcid='"+lcid+"') ")+				
				((ckdwh==null||"".equals(ckdwh))&&(ckdw==null||"".equals(ckdw))?"":"and xs.XX_JBXX_ID in (select XX_JBXX_ID from zxxs_xx_jbxx where xxbsm='"+ckdwh+"' and xxmc='"+ckdw+"')");
		return getListSQL(sql);
	}
	
	public com.jiajie.bean.MsgBean exportExcelInfo(File file, MBspInfo bspInfo,String lcid) {
		List<CusKwExaminee> list = new ArrayList<CusKwExaminee>();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMM",Locale.CHINA);		
			String dateStr = format.format(new Date());
			
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			nf.setMaximumIntegerDigits(6);
			nf.setMinimumIntegerDigits(6);
			
			Workbook rwb = Workbook.getWorkbook(file);
			Sheet rs = rwb.getSheet(0);
			int rows=rs.getRows();//得到所有的行             
            String khao = "0";
            String zkdwm = "";
        	String yzbm = "";
        	String xx_jbxx_id = "";
        	String msg = "";
        	List lsXb = getListSQL("select DM,MC from zd_xb");
        	Map<String, String> mapXb = new HashMap<String, String>();
        	Map<String, String> mapDw = new HashMap<String, String>();
        	for (int i=0; i<lsXb.size(); i++) {
				Map<String, Object> mp = (Map<String, Object>)lsXb.get(i);
				String dm = mp.get("DM")==null?"":mp.get("DM").toString();
				String mc = mp.get("MC")==null?"":mp.get("MC").toString();
				mapXb.put(mc, dm);
        	}
        	Map<String, String> mapSfzjh = new HashMap<String, String>();
        	for (int i = 1; i < rows; i++) {
            	//第一个是列数，第二个是行数
            	String xm    = rs.getCell(0, i).getContents().trim();
            	String xb    = rs.getCell(1, i).getContents().trim();
            	String sfzh  = rs.getCell(2, i).getContents().trim();                	
            	String ckdwh = rs.getCell(3, i).getContents().trim();
            	String ckdw  = rs.getCell(4, i).getContents().trim();
            	String gw  	 = rs.getCell(5, i).getContents().trim();
            	if(xm==null||"".equals(xm)){
            		continue;
            	}
            	if(sfzh==null||"".equals(sfzh)){
            		continue;
            	}else if (sfzh.length()>18) {
            		msg = msg+"第"+i+"行考生,"+sfzh+",身份证号过长！</br>";            		
            	}
            	if(!IdCardUtils.IDCardValidate(sfzh)){
            		msg = msg+"第"+i+"行考生,"+sfzh+",身份证号格式不正确！</br>";            		
            	}
            	if (!"".equals(msg)) {
            		continue;
            	}
            	sfzh = sfzh.toUpperCase();
            	if(ckdwh==null||"".equals(ckdwh)||ckdw==null||"".equals(ckdw)){
            		this.getMsgBean(false,"导入参考单位信息不能为空！");
        			return MsgBean;
            	}
            	if ("".equals(zkdwm)) {
            		List lsKs = getListSQL("select a.xx_jbxx_id,a.xxsszgjyxzdm,(select YZBM from zxxs_xx_jbxx where XX_JBXX_ID=a.xx_jbxx_id) yzbm from zxxs_xs_jbxx a where a.sfzjh='"+sfzh+"'");
                    if (lsKs.size()>0) {
                    	Map<String, String> mp = (Map<String, String>)lsKs.get(0);                    	
                    	xx_jbxx_id = mp.get("xx_jbxx_id").toString();
                		zkdwm = mp.get("xxsszgjyxzdm")==null?"":mp.get("xxsszgjyxzdm").toString();
                		yzbm = mp.get("yzbm")==null?"":mp.get("yzbm").toString();
                    }else {
                    	List lsZ = getListSQL("select XX_JBXX_ID,SSZGJYXZDM,YZBM from zxxs_xx_jbxx where XXBSM='"+ckdwh+"' and XXMC='"+ckdw+"' and dwlx='2'");
                    	if (lsZ != null && lsZ.size()>0) {
                    		Map<String, String> mp = (Map<String, String>)lsZ.get(0);
                    		xx_jbxx_id = mp.get("XX_JBXX_ID").toString();
                    		zkdwm = mp.get("SSZGJYXZDM")==null?"":mp.get("SSZGJYXZDM").toString();
                    		yzbm = mp.get("YZBM")==null?"":mp.get("YZBM").toString();                		
                    	}else {
                    		delete("delete from zxxs_xs_jbxx where sfzjh='"+sfzh+"'");
                    		delete("delete from cus_kw_examinee where lcid='"+lcid+"' and XXDM='"+xx_jbxx_id+"'");
                    		this.getMsgBean(false,"导入考生单位"+ckdwh+"不在这轮考试的参考单位范围内，不能导入！");
                        	return MsgBean;
                    	}
                    }
                    List lsZk = getListSQL("select a.dwid from cus_kw_examround a "+
        					"LEFT JOIN COM_MEMS_ORGAN T4 ON T4.PARENT_CODE = a.dwid "+
        					"LEFT JOIN COM_MEMS_ORGAN T5 ON T5.PARENT_CODE = T4.REGION_CODE "+
        					"LEFT join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=a.dwid "+
        					"where a.lcid='"+lcid+"' and (a.dwid='"+xx_jbxx_id+"' or a.dwid='"+zkdwm+"' or T4.REGION_CODE='"+zkdwm+
        					"' OR T5.REGION_CODE='"+zkdwm+"' or xx.SSZGJYXZDM='"+zkdwm+"')");
                    if (lsZk.size()<=0) {
                    	delete("delete from zxxs_xs_jbxx where sfzjh='"+sfzh+"'");
                    	delete("delete from cus_kw_examinee where lcid='"+lcid+"' and XXDM='"+xx_jbxx_id+"'");
                    	this.getMsgBean(false,"导入考生单位"+ckdwh+"不在这轮考试的参考单位范围内，不能导入！");
                    	return MsgBean;
                    }
                    List lsDw = getListSQL("select XX_JBXX_ID,YZBM,XXBSM,XXMC from zxxs_xx_jbxx where SSZGJYXZDM='"+zkdwm+"' and dwlx='2'");	
                	for (int ii=0; ii<lsDw.size(); ii++) {
        				Map<String, Object> mp = (Map<String, Object>)lsDw.get(ii);
        				String id = mp.get("XX_JBXX_ID")==null?"":mp.get("XX_JBXX_ID").toString().trim();
        				String dwyzbm = mp.get("YZBM")==null?"":mp.get("YZBM").toString().trim();	
        				String dwbh = mp.get("XXBSM")==null?"":mp.get("XXBSM").toString().trim();
        				String dwmc = mp.get("XXMC")==null?"":mp.get("XXMC").toString().trim();
        				mapDw.put(dwbh, id+","+dwmc+","+dwyzbm);
                	}
            	}else {
            		String dwStr = mapDw.get(ckdwh);
            		if (dwStr==null||"".equals(dwStr)) {
            			this.getMsgBean(false,"导入考生单位"+ckdwh+"不在这轮考试的参考单位范围内，不能导入！");
            			return MsgBean;
            		}
            		String[] dwAry = dwStr.split(",");
            		xx_jbxx_id = dwAry.length>0?dwAry[0]:"";
            		if ("".equals(xx_jbxx_id)){
            			this.getMsgBean(false,"导入考生单位"+ckdwh+"不在这轮考试的参考单位范围内，不能导入！");
            			return MsgBean;
            		}
            		String dwmc = dwAry.length>1?dwAry[1]:"";
            		yzbm = dwAry.length>2?dwAry[2]:"";
            		if (!ckdw.equals(dwmc.trim())) {
            			delete("delete from zxxs_xs_jbxx where sfzjh='"+sfzh+"'");
            			delete("delete from cus_kw_examinee where lcid='"+lcid+"' and XXDM='"+xx_jbxx_id+"'");
            			this.getMsgBean(false,"导入考生单位"+ckdwh+"不在这轮考试的参考单位范围内，不能导入！");
            			return MsgBean;
            		}
            	}            	                
                
            	String xbm = mapXb.get(xb); 
            	if (xbm==null||"".equals(xbm)) {
            		msg = msg+"第"+i+"行考生,"+sfzh+",缺少性别信息！</br>";
            	}
            	String kscode = yzbm+dateStr+nf.format(Integer.parseInt(khao)+i);
            	mapSfzjh.put(sfzh,i+"");
            	if (!(i+"").equals(mapSfzjh.get(sfzh))) {
            		msg = msg+"第"+i+"行考生,"+sfzh+",身份证存在重复！</br>";
            	}
                List lstE = getListSQL("select * FROM cus_kw_examinee WHERE lcid='"+lcid+"' and sfzjh = '"+sfzh+"'");                
            	if (lstE.size()>0) {
            		update("update zxxs_xs_jbxx set xm='"+xm+"',xbm='"+xbm+"',xxsszgjyxzdm='"+zkdwm+"' where sfzjh='"+sfzh+"'");
            		update("update cus_kw_examinee set KSCODE='"+kscode+"',XM='"+xm+"',XXDM='"+xx_jbxx_id+"',XBM='"+xbm+"',GW='"+gw+
            				"' where lcid='"+lcid+"' and sfzjh = '"+sfzh+"'"); 
            		continue;
            	}
            	String xjh = "G"+sfzh;
            	List lstX = getListSQL("select xs_jbxx_id from zxxs_xs_jbxx where sfzjh='"+sfzh+"'");
            	String xs_jbxx_id = StringUtil.getUUID();
            	if (lstX==null||lstX.size()<=0) {
            		insert("insert into zxxs_xs_jbxx(xs_jbxx_id,grbsm,xm,xbm,sfzjlxm,sfzjh,xx_jbxx_id,xxsszgjyxzdm) "+
                			"values('"+xs_jbxx_id+"','"+xjh+"','"+xm+"','"+xbm+"','1','"+sfzh+
                			"','"+xx_jbxx_id+"','"+zkdwm+"')");
            	}else {
            		Map<String, Object> mp = (Map<String, Object>)lstX.get(0);
            		xs_jbxx_id = mp.get("xs_jbxx_id")==null?"":mp.get("xs_jbxx_id").toString().trim();            		
            	}
            	List lstU = getListSQL("select USERCODE from T_QXGL_USERINFO where usertype='3' and LOGINID='"+sfzh+"'");
            	if (lstU==null||lstU.size()<=0) {            		                	
                	String pass = sfzh.substring(sfzh.length()-6, sfzh.length());
    				insert("insert into T_QXGL_USERINFO(usercode,username,usertype,loginid,loginpwd,flage) "+
    						"values('"+xs_jbxx_id+"','"+xm+"','3','"+sfzh+"',password('"+pass+"'),'1')");
            	}            	
            	insert("insert into cus_kw_examinee(KSID,LCID,KSCODE,XJH,XM,XXDM,XBM,SFZJH,GW) values('"+StringUtil.getUUID()+
        				"','"+lcid+"','"+kscode+"','"+xjh+"','"+xm+"','"+
        				xx_jbxx_id+"','"+xbm+"','"+sfzh+"','"+gw+"')");            					               
            }
        	rwb.close();
            if ("".equals(msg)) {
            	update("update CUS_KW_EXAMROUND a set SL=IFNULL((SELECT COUNT(*) FROM CUS_KW_EXAMINEE b WHERE b.LCID=a.LCID),0) where a.lcid='"+lcid+"'");
                this.getMsgBean(true,MsgBean.EXPORT_SUCCESS);
            }else {
            	return this.getMsgBean(false,msg);
            }
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.EXPORT_ERROR);
		}
		return MsgBean;
	}
	
	public int exportKsInfo(String kmid,File file) {
		int rtn = 0;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Map<String, String> mapAll = (Map<String, String>)ois.readObject();
			for (String Key : mapAll.keySet()){
				if ("1".equals(mapAll.get(Key))) {//1正在考试
					update("update cus_kw_examinee set isks='1' where sfzjh='"+Key+"' and kmid='"+kmid+"'");
					update("update cus_kw_examinee set isdl='1' where sfzjh='"+Key+"' and kmid='"+kmid+"'");
					update("update cus_kw_examinee set isdt='1' where sfzjh='"+Key+"' and kmid='"+kmid+"'");
				}else if ("2".equals(mapAll.get(Key))) {//2已经提交
					update("update cus_kw_examinee set isks='1' where sfzjh='"+Key+"' and kmid='"+kmid+"'");
					update("update cus_kw_examinee set isdl='1' where sfzjh='"+Key+"' and kmid='"+kmid+"'");
					update("update cus_kw_examinee set isdt='1' where sfzjh='"+Key+"' and kmid='"+kmid+"'");					
					update("update cus_kw_examinee set istj='1' where sfzjh='"+Key+"' and kmid='"+kmid+"'");
				}else if ("3".equals(mapAll.get(Key))||"5".equals(mapAll.get(Key))) {//3已经登录
					update("update cus_kw_examinee set isks='1' where sfzjh='"+Key+"' and kmid='"+kmid+"'");
					update("update cus_kw_examinee set isdl='1' where sfzjh='"+Key+"' and kmid='"+kmid+"'");
				}else if ("4".equals(mapAll.get(Key))) {//4已经验证					
					update("update cus_kw_examinee set isks='1' where sfzjh='"+Key+"' and kmid='"+kmid+"'");
				}
			}
			rtn = 1;
		}catch (Exception e) {
			e.printStackTrace();			
		}finally{
			return rtn;
		}
	}	
}
