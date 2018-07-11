package com.jiajie.service.cheatStu.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.ss.usermodel.Row;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.cheatStu.LackOfTestStudentService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.exambasic.CusKwMonitorteacher;
import com.jiajie.bean.exambasic.CusKwSkipstu;
@Service("lackOfTestStudentServiceImpl")
@SuppressWarnings("all")
public class LackOfTestStudentServiceImpl extends ServiceBridge implements LackOfTestStudentService {
	public PageBean getExamRounds(String xn,String xq,String userId,MBspInfo bspInfo){
		MBspOrgan orgn = bspInfo.getOrgan();
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT T1.LCID,T1.XN,T3.MC AS XQM,T1.EXAMNAME,date_format(T1.STARTDATE,'%Y-%m-%d') AS STARTDATE,date_format(T1.ENDDATE,'%Y-%m-%d') AS ENDDATE,COUNT(T2.QKID) AS WJRS");
		sql.append(" from CUS_KW_EXAMROUND t1");
		sql.append(" LEFT JOIN com_mems_organ zk ON zk.region_code = t1.dwid");
		sql.append(" LEFT JOIN com_mems_organ zk1 ON zk1.region_code = zk.PARENT_CODE");
		sql.append(" left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=t1.DWID");
		sql.append(" LEFT JOIN CUS_KW_SKIPSTU t2 on T1.LCID=T2.LCID");
		sql.append(" LEFT JOIN ZD_XQ t3 on T3.DM=T1.XQM");
		sql.append(" WHERE (T1.DWID='"+orgn.getOrganCode());
		sql.append("' OR zk.region_code = '"+orgn.getOrganCode()+"' OR zk1.region_code = '"+orgn.getOrganCode());
		sql.append("' OR zk1.PARENT_CODE = '"+orgn.getOrganCode()+"' or xx.SSZGJYXZDM='"+orgn.getOrganCode());
		sql.append("' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgn.getOrganCode());
		sql.append("') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+orgn.getOrganCode()+"')))");
						
		if(xn!=null && !"".equals(xn)){
			sql.append(" AND T1.XN='");
			sql.append(xn);
			sql.append("' ");
		}
		if(xq!=null && !"".equals(xq)){			
			sql.append(" AND T1.XQM='");
			sql.append(xq);
			sql.append("'");
		}
		sql.append(" GROUP BY T1.LCID,T1.XN,T3.MC,T1.EXAMNAME,T1.STARTDATE,T1.ENDDATE");
		sql.append(" ORDER BY T1.STARTDATE DESC");
		return getSQLPageBean(sql.toString());
	}

	public PageBean getLackOfTestStudent(String lcId, String xm_kh_xjh,
			String schoolId) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT T1.QKID,T1.LCID,T1.DETAIL,T2.KSCODE,T2.SFZJH,T2.XJH,T2.XM,T3.KMID,T3.SUBJECTNAME,T4.XXMC,T5.MC,T5.DM");
		sql.append(" FROM CUS_KW_SKIPSTU T1");
		sql.append(" LEFT JOIN CUS_KW_EXAMINEE T2 ON T1.KSID=T2.KSID");
		sql.append(" LEFT JOIN CUS_KW_EXAMSUBJECT T3 ON T1.KMID=T3.KMID");
		sql.append(" LEFT JOIN ZXXS_XX_JBXX T4 ON T2.XXDM=T4.XX_JBXX_ID ");
		sql.append(" LEFT JOIN ZD_XB T5 ON T2.XBM=T5.DM");
		sql.append(" WHERE 1=1");
		if(StringUtil.isNotNullOrEmpty(lcId)){
			sql.append(" AND T1.LCID='");
			sql.append(lcId);
			sql.append("'");
		}
		if(StringUtil.isNotNullOrEmpty(xm_kh_xjh)){
			sql.append(" AND (T2.KSCODE LIKE binary('%");
			sql.append(xm_kh_xjh);
			sql.append("%') OR T2.XM LIKE binary('%");
			sql.append(xm_kh_xjh);
			sql.append("%') OR T2.XJH LIKE binary('%");
			sql.append(xm_kh_xjh);
			sql.append("%'))");
		}
		if(StringUtil.isNotNullOrEmpty(schoolId)){
			sql.append(" AND T2.XXDM in (");
			sql.append(schoolId);
			sql.append(")");
		}
		sql.append(" ORDER BY substr(T3.SUBJECTNAME,11,length(substr(T3.SUBJECTNAME,11))-length('批'))+0 asc");
		return getSQLPageBean(sql.toString());
	}

	public MsgBean deleteLackOfTestStudents(String ids) {
		String sql="DELETE FROM CUS_KW_SKIPSTU where QKID in ("+ids+")";
		return deleteBySql(sql);
	}
	public MsgBean importLackOfTestStudent(String lcId,
			File upload,String userId) {
		try{			
			String sql="SELECT SUBJECTNAME from CUS_KW_EXAMSUBJECT where LCID='"+lcId+"'";
            List list=getSession().createSQLQuery(sql).list();
			
			Workbook rwb = Workbook.getWorkbook(upload);
			Sheet rs = rwb.getSheet(0);
			int clos=rs.getColumns();//得到所有的列
	        int rows=rs.getRows();//得到所有的行
	        
	        if(clos!=list.size()+3){
	        	return getMsgBean(false, "导入失败，模板错误！");
	        }
	        List<String> title_list=new ArrayList<String>();
	        List<CusKwSkipstu> list_stu=new ArrayList<CusKwSkipstu>();
	        List<List<CusKwSkipstu>> list_s=new ArrayList<List<CusKwSkipstu>>();
	        String ksid="";
	        one:for (int i = 1; i < rows; i++) {
	            for (int j = 0; j < clos; j++) {
	            	if(i==1){
	            		String title_val =  rs.getCell(j, i).getContents().trim();
	            		if(title_val!=null && !"".equals(title_val)){
	            			title_list.add(title_val);
	            			if(j>1 && j<clos-1){
	            				if(!title_val.equals(list.get(j-2))){
	            					return getMsgBean(false, "导入失败，模板错误！");
	            				}
	            			}
	            			continue;
	            		}else{
	            			clos=j+1;
	            			break;
	            		}
	            	}
	            	String title_str=title_list.get(j);
	            	//第一个是列数，第二个是行数
	            	String cellVal =  rs.getCell(j, i).getContents().trim();//身份证号
	            	//如果第一列考生身份证号为空，直接返回	            	
	            	if("身份证号".equals(title_str)){
	            		j++;
	            		String title_str2=title_list.get(j);
	            		//第一个是列数，第二个是行数
		            	String cellVal2 =  rs.getCell(j, i).getContents().trim();//姓名
		            	//如果第一列考生考号为空，直接返回		            	
		            	if(!StringUtil.isNotNullOrEmpty(cellVal) || !StringUtil.isNotNullOrEmpty(cellVal2)){
	            			break one;
	            		};
	            		ksid=getKsId(lcId, cellVal2, cellVal,null,null);
	            		if(!StringUtil.isNotNullOrEmpty(ksid)){
	            			return getMsgBean(false, "导入失败，考生信息错误！");
	            		}
	            		continue;
	            	}	            	
	            	if(StringUtil.isNotNullOrEmpty(cellVal) && !"缺考原因".equals(title_str)){
	            		if("缺考".equals(cellVal)){	            			
		            		String kmid=getKskmId(lcId,title_str);
		            		if(!StringUtil.isNotNullOrEmpty(kmid)){
		            			return getMsgBean(false, "导入失败，科目信息为空或错误！");
		            		}
		            		CusKwSkipstu stu=new CusKwSkipstu();
		            		stu.setCdate(new Date());
		            		stu.setCjr(userId);
		            		stu.setKmid(kmid);
		            		stu.setKsid(ksid);
		            		stu.setLcid(lcId);
		            		list_stu.add(stu);
		            		List ls = getListSQL("select * from CUS_KW_SKIPSTU where lcid='"+lcId+"' and ksid='"+ksid+"' and kmid='"+kmid+"'");
		            		if (ls.size()>0) {
		            			return getMsgBean(false, "导入失败，缺考考生信息重复！");
		            		}
	            		}	            		
	            		continue;
	            	}	            	
	            	if("缺考原因".equals(title_str)){
	            		for (CusKwSkipstu cusKwSkipstu : list_stu) {
	            			if(!StringUtil.isNotNullOrEmpty(cellVal)){
		            			return getMsgBean(false, "导入失败，缺考原因为空！");
		            		}
	            			cusKwSkipstu.setDetail(cellVal);
	            			//getSession().save(cusKwSkipstu);
						}
	            		list_s.add(list_stu);
	            	}
	            }	            
	    	}
	        for (List<CusKwSkipstu> list2 : list_s) {
				for (CusKwSkipstu cusKwSkipstu : list2) {
					getSession().save(cusKwSkipstu);
				}
			}
	    	this.getMsgBean(true,MsgBean.EXPORT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.EXPORT_ERROR);
		}
		return MsgBean;
	}		
	
	public MsgBean saveLackOfTestStudent(Map<String, String> map) {
		String lcId,qkId,sfzjh,xjh,xm,xb,km,qkyy,userId;
		Boolean isAdd=true;
		lcId=map.get("lcId");		
		qkId=map.get("qkId");
		sfzjh=map.get("kh");
		xjh=map.get("xjh");
		xm=map.get("xm");
		xb=map.get("xb");
		km=map.get("km");
		qkyy=map.get("qkyy");
		userId=map.get("userId");
		String ksid=getKsId(lcId, xm, sfzjh,xjh,xb);
		if(!StringUtil.isNotNullOrEmpty(ksid)){
			return getMsgBean(false, "考生信息错误！");
		}
		CusKwSkipstu stu=new CusKwSkipstu();
		stu.setCjr(userId);
		stu.setDetail(qkyy);
		stu.setKmid(km);
		stu.setKsid(ksid);
		stu.setLcid(lcId);
		if(qkId==null || "".equals(qkId)){
			stu.setCdate(new Date());
			stu.setQkid(null);
		}else{
			stu.setQkid(qkId);
			isAdd=false;
		}
		getSession().saveOrUpdate(stu);
		if(isAdd)
			return getMsgBean(true, "添加成功！");
		else
			return getMsgBean(true, "修改成功！");
	}

	private String getKsId(String lcId,String xm,String sfzjh,String xjh,String xb){
		String ksid=null;
		String sql="SELECT KSID from CUS_KW_EXAMINEE WHERE SFZJH='"+sfzjh+"' AND XM='"+xm+"' AND LCID='"+lcId+"'";
		if(StringUtil.isNotNullOrEmpty(xjh)){
			sql+=" AND XJH='"+xjh+"'";
		}
		if(StringUtil.isNotNullOrEmpty(xb)){
			sql+=" AND XBM='"+xb+"'";
		}
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		if(list!=null && list.size()>0){
			ksid=list.get(0).get("KSID").toString();
		}
		return ksid;
	}
	private String getKskmId(String lcId,String kmmc){
		String kmid=null;
		String sql="SELECT KMID FROM CUS_KW_EXAMSUBJECT WHERE SUBJECTNAME='"+kmmc+"' AND LCID='"+lcId+"'";
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		if(list!=null && list.size()>0){
			kmid=list.get(0).get("KMID").toString();
		}
		return kmid;
	}
		
	public InputStream getExcelInputStream(String lcId) {  
		//将OutputStream转化为InputStream  
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
		putDataOnOutputStream(out,lcId);  
		return new ByteArrayInputStream(out.toByteArray());  
	}  
	  
	private void putDataOnOutputStream(OutputStream os,String lcId) {  
		jxl.write.Label label;  
		WritableWorkbook workbook;  
		try {  
			workbook = Workbook.createWorkbook(os);  
			WritableSheet sheet = workbook.createSheet("Sheet1", 0); 
			WritableFont wf_merge_title = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD,
					false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
			WritableCellFormat wff_merge_title = new WritableCellFormat(wf_merge_title);
			label = new jxl.write.Label(0, 1, "身份证号",wff_merge_title); sheet.addCell(label); 
			label = new jxl.write.Label(1, 1, "姓名",wff_merge_title); sheet.addCell(label);
			String sql="SELECT SUBJECTNAME from CUS_KW_EXAMSUBJECT where LCID='"+lcId+"'";
			List list=getSession().createSQLQuery(sql).list();
			int i = 0;
			if(list!=null && list.size()>0){
				for (; i < list.size(); i++) {
					label = new jxl.write.Label(i+2, 1, StringUtil.getString(list.get(i)),wff_merge_title); sheet.addCell(label);
				}
			}
			label = new jxl.write.Label(2+i, 1, "缺考原因",wff_merge_title); sheet.addCell(label);
			WritableFont wf_merge = new WritableFont(WritableFont.ARIAL,16,WritableFont.NO_BOLD,
					false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED);
			WritableCellFormat wff_merge = new WritableCellFormat(wf_merge);
			label = new jxl.write.Label(0, 0, "说明：请在缺考科目下填写“缺考”！",wff_merge); sheet.addCell(label);  
			sheet.mergeCells(0, 0, 2+i, 0);
			workbook.write();  
			workbook.close();  
		} catch (Exception e) {  
			e.printStackTrace();  
		}
	}  
	
	public MsgBean submitQk(String lcid,String userID) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);		
			String dateStr = format.format(new Date());
			String sql = "select distinct KSID,KMID from CUS_KW_EXAMINEE where LCID='"+lcid+
					"' and (ISDL is null or ISDL='') and ksid in "+
					"(SELECT distinct a.KSID from CUS_KW_EXAMINEE a "+
					"left join cus_kw_examschedule f on f.LCID = a.LCID and f.KMID = a.KMID "+
					"WHERE a.LCID='"+lcid+"' and '"+dateStr+"'>f.endtime)";
			List ls = getListSQL(sql);			
			for (int i=0; i<ls.size(); i++) {
				Map<String, Object> mp = (Map<String, Object>) ls.get(i);
				String ksid = mp.get("KSID")==null?"":mp.get("KSID").toString();
				String kmid = mp.get("KMID")==null?"":mp.get("KMID").toString();
				
				String sqlKs = "select * from CUS_KW_SKIPSTU where lcid='"+lcid+
						"' and kmid='"+kmid+"' and KSID='"+ksid+"'";
				List lsKs = getListSQL(sqlKs);
				if (lsKs.size()<=0) {				
					insert("insert into CUS_KW_SKIPSTU (LCID, KSID, KMID, DETAIL, CJR, CDATE, QKID) values('"+
						lcid+"','"+ksid+"','"+kmid+"','缺考','"+userID+"','"+dateStr+"','"+StringUtil.getUUID()+"')");
				}
			}
			update("update CUS_KW_EXAMROUND a set QK=IFNULL((SELECT COUNT(distinct ksid) FROM cus_kw_skipstu b WHERE b.LCID = a.LCID),0) where a.lcid='"+lcid+"'");
			this.getMsgBean(true,"自动登记成功");
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"自动登记失败");
		}
		return MsgBean;
	}
}
