package com.jiajie.service.examResults.impl;
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
import com.jiajie.util.SecUtils;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;
import com.jiajie.bean.MsgBean;
import com.mysql.jdbc.StringUtils;

@Service("resultsResgisterService")
@SuppressWarnings("all")
public class ResultResgisterServiceImpl extends ServiceBridge implements ResultsResgisterService{

	public PageBean getListPage(String xnxq_id, MBspInfo bspInfo) {
		String xn = "";
		String xq = "";
		if(xnxq_id != null && !"".equals(xnxq_id)){
			String [] str = xnxq_id.split(",");
			xn = str[0].toString();
			xq = str[1].toString();
		}
		MBspOrgan orgn = bspInfo.getOrgan();
		String sql = "SELECT T.* FROM (SELECT T1.DWTYPE,T1.DWID,T1.WJFLAG,T1.LCID,T1.XN,T2.NAME AS XQ,"+
				"T1.EXAMNAME,T3.MC AS EXAMTYPE,IFNULL((SELECT T5.REGION_EDU FROM COM_MEMS_ORGAN T5 WHERE T1.DWID=T5.REGION_CODE),"+
				"(SELECT (select region_edu from com_mems_organ where region_code=T4.SSZGJYXZDM) FROM ZXXS_XX_JBXX T4 WHERE T1.DWID=T4.XX_JBXX_ID)) AS DWMC,"+
				"IFNULL((select xxmc from zxxs_xx_jbxx where xx_jbxx_id = T1.dwid),'全部') CKDW,T1.SL " +
				"FROM CUS_KW_EXAMROUND T1 " +
				"left join com_mems_organ zk on zk.region_code=T1.dwid "+
		        "left join com_mems_organ zk1 on zk1.region_code=zk.PARENT_CODE "+
				"left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=T1.DWID "+
				"LEFT JOIN SYS_ENUM_VALUE T2 ON T1.XQM=T2.CODE AND T2.DIC_TYPE='ZD_XT_XQMC' " +
				"LEFT JOIN CUS_KW_EXAMTYPE T3 ON T1.EXAMTYPEM=T3.DM ";
		sql += " WHERE (T1.dwid='"+orgn.getOrganCode()+
				"' OR zk.region_code='"+orgn.getOrganCode()+"' OR zk1.region_code='"+orgn.getOrganCode()+
				"' OR zk1.PARENT_CODE = '"+orgn.getOrganCode()+"' or xx.SSZGJYXZDM='"+orgn.getOrganCode()+
				"' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgn.getOrganCode()+
				"') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgn.getOrganCode()+"'))) ";
		if(xn != null && !"".equals(xn)){
			sql += " AND T1.XN='"+xn+"' ";
		}
		if(xq != null && !"".equals(xq)){
			sql += " AND T1.XQM='"+xq+"' ";
		}
		sql += " ) T  ORDER BY T.DWID,T.XN,T.LCID";
		return getSQLPageBean(sql);
	}

	public MsgBean importResults(String userid, File upload, String lcid) {
		
		String sql = "select KSCODE from cus_kw_ExAMinee where lcid = '"+lcid+"'";		
		List list = getListSQL(sql);
		Set<String> kh = new HashSet<String>();
		for (Object object : list) {
			String tmpkh = object.toString().split("=")[1];
			tmpkh = tmpkh.substring(0, tmpkh.length()-1);
			kh.add(tmpkh);
		}
		
		try {
			Workbook rwb = Workbook.getWorkbook(upload);
			Sheet rs = rwb.getSheet(0);
			int clos=rs.getColumns();//得到所有的列
			int rows=rs.getRows();//得到所有的行s
			String khmsg = "本场考试没有考号：";
		    for (int i = 1; i < rows; i++) {
		    	if(!kh.contains(rs.getCell(0, i).getContents())){
		    		khmsg += rs.getCell(0, i).getContents()+"、";
		    	}
			}
		    
		    if(!"本场考试没有考号：".equals(khmsg)){
		    	khmsg = khmsg.substring(0,khmsg.length()-1);
		    	this.getMsgBean(false,khmsg);
		    	return MsgBean;
		    }		    
		    
			String lie [] = new String[clos-2];
			String lieF [] = new String[clos-2];
			StringBuilder sb = new StringBuilder();
			sb.append("select KMID,SCORE from cus_kw_examsubject where subjectname in('");
			
			for (int j = 2; j < clos; j++) {
				String value = rs.getCell(j, 0).getContents();
				sb.append(value);
				if(j == clos-1){
					sb.append("')");
				}else{
					sb.append("','");
				}
			}
			sb.append(" and lcid = '").append(lcid).append("'");
			
			List listSQL = getListSQL(sb.toString());
			if(listSQL.size() != clos-2){
				this.getMsgBean(false,"请使用正确的模版");
			}else{
				for (int i = 0; i < listSQL.size(); i++) {
					String kmStr = listSQL.get(i).toString().split(",")[0];
					lie[i] = kmStr.split("=")[1];					
					
					Map result = (Map) listSQL.get(i);
					String score =result.get("SCORE").toString();
					lieF[i] = score;
				}
				
				List<CusKwStuScore> ckss = new ArrayList<CusKwStuScore>();
				for (int i = 1; i < rows; i++) {
					for (int j = 2; j < clos; j++) {
						if (!isNumeric(rs.getCell(j, i).getContents())) {
							this.getMsgBean(false,"成绩格式不正确！");
						    return MsgBean;
						}else if (Double.valueOf((rs.getCell(j, i).getContents())).doubleValue()>
								Double.valueOf(lieF[j-2]).doubleValue()) {
							this.getMsgBean(false,"成绩超出上限！");
						    return MsgBean;
						}
						String tmpKmid = lie[j-2];						
						List listXs = getListSQL("select XJH from cus_kw_examinee where lcid='"+lcid+"' and kscode='"+
								rs.getCell(0, i).getContents()+"' and xm='"+rs.getCell(1, i).getContents()+"'");
						String xjh = "";
						if (listXs.size()>0) {
							Map result = (Map) listXs.get(0);
							xjh =result.get("XJH").toString();
						}
						this.delete("delete from cus_kw_stuscore where lcid='"+lcid+"' and KMID='"+tmpKmid+
								"' and xjh='"+xjh+"'");
						
						StringBuilder sql2 = new StringBuilder();
						String xscjid = UUID.randomUUID().toString().replace("-","");
						sql2.append("insert into cus_kw_stuscore(XSCJID,LCID,KMID,EXAMCODE,XJH,SCORE,WRITER) values(")
						.append("'").append(xscjid).append("','")
						.append(lcid).append("','").append(tmpKmid).append("','").append(rs.getCell(0, i).getContents())
						.append("','").append(xjh).append("',");
						if(StringUtil.isNotNullOrEmpty(rs.getCell(j, i).getContents())){
							sql2.append(rs.getCell(j, i).getContents());
						}else{
							sql2.append("0");
						}
						sql2.append(",'").append(userid).append("')");
						insert(sql2.toString());
					}
				}
				this.getMsgBean(true,MsgBean.EXPORT_SUCCESS);
			}	           
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.EXPORT_ERROR);
		}
		return MsgBean;
	}

	private boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("^\\d+$|-\\d+$|\\d+\\.\\d+$|-\\d+\\.\\d+$");  
	    return pattern.matcher(str).matches();     
	}

	public MsgBean checkCj(String lcid,String userID) {
		try {
			String sql = "select XSCJID,SCORE,SCOREJM from cus_kw_stuscore where lcid='"+lcid+"'";
			List ls = getListSQL(sql);
			int checknum = 0;
			for (int i=0; i<ls.size(); i++) {
				Map<String, Object> mp = (Map<String, Object>) ls.get(i);
				String xscjid = mp.get("XSCJID")==null?"":mp.get("XSCJID").toString();
				String score = mp.get("SCORE")==null?"":mp.get("SCORE").toString();
				String scorejm = mp.get("SCOREJM")==null?"":mp.get("SCOREJM").toString();
				String score1 = SecUtils.decode(scorejm);
				if (!score.equals(score1)&&!StringUtils.isNullOrEmpty(score1)) {
					update("update cus_kw_stuscore set SCORE="+score1+",WRITER='"+userID+"' where XSCJID='"+xscjid+"'");
					checknum++;
				}				
			}
			this.getMsgBean(true,checknum+"个成绩校验成功");
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"成绩校验失败");
		}
		return MsgBean;
	}
	
	public MsgBean submitCj(String lcid,String userID) {
		try {
			String sql = "select KMID,KSCODE,XJH,KSID,XM,XBM,XXDM,XS_JBXX_ID "+
					"from cus_kw_examinee "+
					"where lcid='"+lcid+"'";			
			List ls = getListSQL(sql);
			for (int i=0; i<ls.size(); i++) {
				Map<String, Object> mp = (Map<String, Object>) ls.get(i);
				String kmid = mp.get("KMID")==null?"":mp.get("KMID").toString();
				String kscode = mp.get("KSCODE")==null?"":mp.get("KSCODE").toString();
				String xjh = mp.get("XJH")==null?"":mp.get("XJH").toString();
				String ksid = mp.get("KSID")==null?"":mp.get("KSID").toString();
				String xm = mp.get("XM")==null?"":mp.get("XM").toString();
				String xbm = mp.get("XBM")==null?"":mp.get("XBM").toString();
				String xxdm = mp.get("XXDM")==null?"":mp.get("XXDM").toString();
				String xsid = mp.get("XS_JBXX_ID")==null?"":mp.get("XS_JBXX_ID").toString();
				
				String sqlT = "select RCID from cus_kw_examschedule where DATE_FORMAT(SYSDATE(),'%Y-%m-%d %H:%i:%s')>ENDTIME and kmid='"+kmid+"'";
				List lsT = getListSQL(sqlT);
				String rcid = lsT.size()>0?(((Map<String, Object>)lsT.get(0)).get("RCID")==null?"":
					((Map<String, Object>)lsT.get(0)).get("RCID").toString()):"";
				if (!"".equals(rcid)) {
					String sqlKsd = "select sum(t.score) as SCORES from("+
							"select distinct a.exam_info_id,a.score "+
							"from CUS_EXAM_DETAIL a "+ 
							"left join PAPER_BASIC b on b.PAPER_ID=a.PAPER_ID "+
							"where a.XS_JBXX_ID='"+xsid+"' and b.kmid='"+kmid+"') t";
					List lsKsd = getListSQL(sqlKsd);
					String score = "";
					if (lsKsd.size()>0) {
						Map<String, Object> mpKs = (Map<String, Object>) lsKsd.get(0);
						score = mpKs.get("SCORES")==null?"":mpKs.get("SCORES").toString();
					}
					if ("".equals(score)) {
						update("update cus_kw_examinee set isdt='',dtsj='' where xjh='"+xjh+"'");
						continue;
					}
					String sqlKs = "select SCORE from cus_kw_stuscore where lcid in ('"+lcid+
							"') and kmid='"+kmid+"' and examcode='"+kscode+"' and xjh='"+xjh+"'";
					List lsKs = getListSQL(sqlKs);
					if (lsKs.size()>0) {
						Map<String, Object> mpKs = (Map<String, Object>) lsKs.get(0);
						String scoreKs = mpKs.get("SCORE")==null?"":mpKs.get("SCORE").toString();
						if (!scoreKs.equals(score)) {
							update("update cus_kw_stuscore set SCORE="+scoreKs+",SCOREJM='"+SecUtils.encode(score)+"',WRITER='"+userID+
							"' where lcid in ('"+lcid+"') and kmid='"+kmid+"' and examcode='"+kscode+"' and xjh='"+xjh+"'");
						}
					}else {
						insert("insert into cus_kw_stuscore(XSCJID,LCID,KMID,EXAMCODE,XJH,SCORE,SCOREJM,WRITER,KSID,XM,XBM,XXDM) values('"+StringUtil.getUUID()+
						"','"+lcid+"','"+kmid+"','"+kscode+"','"+xjh+"',"+score+",'"+SecUtils.encode(score)+"','"+userID+"','"+ksid+"','"+xm+"','"+xbm+"','"+xxdm+"')");
						update("update cus_kw_examinee set ISTJ='1' where ksid='"+ksid+"'");
					}
					this.getMsgBean(true,"代提交成功");
				}else {
					this.getMsgBean(false,"考试未结束！不能代提交！");
					break;
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"代提交失败");
		}
		return MsgBean;
	}
	
	public MsgBean createTemplate(String lcid) {
		try {
			String realpath = ServletActionContext.getServletContext().getRealPath("export/excel/resultsTemplate");
			File path = new File(realpath);
			if(!path.exists()) {
				path.mkdirs();//不存在该路径则自动生成
			}
			StringBuilder sb = new StringBuilder();
			sb.append("select SUBJECTNAME from cus_kw_examsubject where lcid= '").append(lcid).append("'");
			PageBean examSubject = getSQLPageBean(sb.toString());
			List subjects = examSubject.getResultList();
			if(subjects.size() == 0){
				this.getMsgBean(false,"请先设置本次考试的考试科目");
				return MsgBean;
			}else{
				File file = new File(path,"/"+lcid+".xls");
				if(file.exists()){
					path.delete();
				}
				FileOutputStream fileout = new FileOutputStream(file);
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet();
				sheet.autoSizeColumn(1);
				HSSFRow rowHeard = sheet.createRow(0);
				
				HSSFCell cellHeard = rowHeard.createCell(0);
				cellHeard.setCellValue("考号");
				cellHeard = rowHeard.createCell(1);
				cellHeard.setCellValue("姓名");
				int index = 2;
				for (Object object : subjects) {
					cellHeard = rowHeard.createCell(index);
					String subject = object.toString().split("=")[1];
					subject = subject.substring(0, subject.length()-1);
					cellHeard.setCellValue(subject);
					index ++;
				}
				wb.write(fileout);
				fileout.flush();
				fileout.close();
				this.getMsgBean(true,"生成模版成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"生成模版失败");
		}
		return MsgBean;
	}

	public com.jiajie.bean.MsgBean checkFile(String lcid) {
		String realpath = ServletActionContext.getServletContext().getRealPath("export/excel/resultsTemplate");
		File path = new File(realpath,"/"+lcid+".xls");
		if(path.exists()){
			 this.getMsgBean(true,MsgBean.EXPORT_SUCCESS);
		}else{
			this.getMsgBean(false,MsgBean.EXPORT_SUCCESS);
		}
		return MsgBean;
	} 
}
