package com.jiajie.service.examArrangement.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.bean.exambasic.CusKwExamschedule;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.examArrangement.ExamTimeArrangeService;
import com.jiajie.util.StringUtil;
import com.mysql.jdbc.StringUtils;

@Service("examTimeArrangeServiceImpl")
@SuppressWarnings("all")
public class ExamTimeArrangeServiceImpl extends ServiceBridge implements ExamTimeArrangeService {

	public List<Map<String, Object>> getSubjectNo(String lcId, String kd) {
		String sql="SELECT KMID AS ID,SUBJECTNAME as NAME,FORMAT(TIMELENGTH/60,1) AS DURATION"+
				" from CUS_KW_EXAMSUBJECT "+
				" WHERE LCID='"+lcId+"'"+
				(kd==null||"".equals(kd)?"":" AND KMID in (select kmid from cus_kw_roomstudent where lcid='"+lcId+"' and kdid='"+kd+"')")+
				" AND KMID NOT IN (SELECT KMID FROM CUS_KW_EXAMSCHEDULE WHERE LCID='"+lcId+"')";
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		return list;
	}

	public List<Map<String, Object>> getDuration(String lcId) {
		CusKwExamround round=(CusKwExamround)getSession().get(CusKwExamround.class, lcId);
		Date startTime=round.getStartdate();
		Date endTime=round.getEnddate();
		SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");		
		String end=fmt.format(endTime);
		
		Calendar endCalendar=Calendar.getInstance();
		endCalendar.setTime(endTime);
		
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(startTime);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		while(true){
			Map<String, Object> map=new HashMap<String, Object>();
			String start=fmt.format(calendar.getTime());
			if(calendar.equals(endCalendar)){
				map.put("Id", end);
				map.put("Name", getWeekName(calendar.get(Calendar.DAY_OF_WEEK))+" <br/> "+end);
				list.add(map);
				break;
			}else{
				map.put("Id", start);
				map.put("Name", getWeekName(calendar.get(Calendar.DAY_OF_WEEK))+" <br/> "+start);
				list.add(map);
			}
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		return list;
	}
			
	public List<Map<String, Object>> getDefault(String lcId, String kd) {
		String sql="SELECT T1.KMID AS ID,T2.SUBJECTNAME AS NAME,date_format(T1.EXAMDATE,'%Y-%m-%d') AS RESOURCEID,"+
				   " CONCAT('2011-09-01T',date_format(T1.STARTTIME,'%H:%i')) AS STARTDATE,"+//%Y-%m-%d  %H:%i:%s
				   " CONCAT('2011-09-01T',date_format(T1.ENDTIME,'%H:%i')) AS ENDDATE,"+//%Y-%m-%d  %H:%i:%s
				   " T1.TIMELENGTH AS DURATION"+
				   " from CUS_KW_EXAMSCHEDULE t1"+ 
				   " LEFT JOIN CUS_KW_EXAMSUBJECT t2 on T1.KMID=T2.KMID"+
				   " WHERE T1.LCID='"+lcId+"'"+
				   (kd==null||"".equals(kd)?"":" AND T2.KMID in (select kmid from cus_kw_roomstudent where lcid='"+lcId+"' and kdid='"+kd+"')");
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		for (Map<String, Object> map : list) {
			map.put("Id", map.get("ID"));
			map.put("Name", map.get("NAME"));
			map.put("ResourceId", map.get("RESOURCEID"));
			map.put("StartDate", map.get("STARTDATE"));
			map.put("EndDate", map.get("ENDDATE"));
			map.put("Duration", map.get("DURATION"));
			map.remove("ID");
			map.remove("NAME");
			map.remove("RESOURCEID");
			map.remove("STARTDATE");
			map.remove("ENDDATE");
			map.remove("DURATION");
		}		
		return list;
	}
	
	public String saveExamTimeArrange(String lcId,String kd,List<Map<String, Object>> list,String ids) {
		ids = ids.replace(",","','");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List lst= getSession().createSQLQuery(
				"select * from CUS_KW_EXAMSCHEDULE where 0=0 "+
				(ids==null||"".equals(ids)?"":"and kmid in ('"+ids+"')")+
				(kd==null||"".equals(kd)?"":" AND KMID in (select kmid from cus_kw_roomstudent where lcid='"+lcId+"' and kdid='"+kd+"')")).list();
		if (!StringUtils.isNullOrEmpty(ids)) {
			delete("delete from CUS_KW_EXAMSCHEDULE where 0=0 "+
				(ids==null||"".equals(ids)?"":"and kmid in ('"+ids+"')")+
				(kd==null||"".equals(kd)?"":" AND KMID in (select kmid from cus_kw_roomstudent where lcid='"+lcId+"' and kdid='"+kd+"')"));
		}
		int same = 0;
		for (Map<String, Object> map : list) {
			String id=map.get("id").toString();
			List schedule= getSession().createQuery("FROM CusKwExamschedule t where t.lcid='"+lcId+"' and t.kmid='"+id+"'").list();
			CusKwExamschedule schedul=new CusKwExamschedule();
			if(schedule!=null && schedule.size()>0){
				schedul=(CusKwExamschedule)schedule.get(0);
			}
			Date resourceId=StringUtil.getDate(map.get("resourceId"));
			Date startDate=StringUtil.getDateAll(map.get("startDate"));
			Date endDate=StringUtil.getDateAll(map.get("endDate"));			
			schedul.setLcid(lcId);			
			if (schedul.getExamdate()!=null&&map.get("resourceId").equals(schedul.getExamdate().toString().substring(0,10))&&
				schedul.getStarttime()!=null&&map.get("startDate").equals(schedul.getStarttime().toString().substring(0,19))&&
				schedul.getEndtime()!=null&&map.get("endDate").equals(schedul.getEndtime().toString().substring(0,19))) {
				same++;				
			}else {
				if (kd!=null&&!"".equals(kd)) {
					String sqlLc = "select xnxq_id,xn,xq,score,timelength,"+
							"case when substr(subjectname,9,1)='-' "+
							"then substr(subjectname,11,length(substr(subjectname,11))-length('批')) "+
							"else '0' end pc "+
							"from CUS_KW_EXAMSUBJECT where kmid='"+id+"'";
					List lsLc = getListSQL(sqlLc);
					if (lsLc.size()>0) {
						Map<String, Object> mp = (Map<String, Object>) lsLc.get(0);
						String xn = mp.get("xn").toString();
						String xq = mp.get("xq").toString();
						String xnxq = mp.get("xnxq_id").toString();
						String score = mp.get("score").toString();
						String timelen = mp.get("timelength").toString();
						String pc = mp.get("pc").toString();
						String newKmid = StringUtil.getUUID();
						insert("insert into CUS_KW_EXAMSUBJECT(XNXQ_ID, SUBJECTNAME, RESULTSTYLE, KCH, LCID, SCORE, TIMELENGTH, XN, XQ, KMID) "+
								"values ('"+xnxq+"', '公共法律知识测试-补"+pc+"批', '1', '公共法律知识测试', '"+lcId+"', "+score+", "+timelen+", '"+xn+"', '"+xq+"', '"+newKmid+"')");
						update("update cus_kw_roomstudent set kmid='"+newKmid+"' where lcid='"+lcId+"' and kmid='"+id+"'");
						update("update cus_kw_examinee set kmid='"+newKmid+"' where lcid='"+lcId+"' and kmid='"+id+"'");
						getSession().saveOrUpdate(schedul);
						insert("insert into cus_kw_examschedule(RCID,LCID,KMID,EXAMDATE,SDID,STARTTIME,ENDTIME,TIMELENGTH) "+
								"values('"+StringUtil.getUUID()+"','"+lcId+"','"+newKmid+"','"+map.get("resourceId")+"','5','"+map.get("startDate")+"','"+map.get("endDate")+"',"+timelen+")");
						getSession().saveOrUpdate(schedul);
						continue;
					}					
				}
			}
			schedul.setKmid(id);
			schedul.setExamdate(resourceId);
			schedul.setStarttime(startDate);
			schedul.setEndtime(endDate);						
			short sht=Short.valueOf(StringUtil.getString((endDate.getTime()-startDate.getTime())/1000/60/60));
			schedul.setTimelength(sht);
			getSession().saveOrUpdate(schedul);			
		}
		
		if (same!=list.size()||!StringUtils.isNullOrEmpty(ids)) {
			if (!StringUtils.isNullOrEmpty(ids)&&lst.size()==0) {
				return "没做考试安排，不需要保存！";
			}else {
				return "考试时间安排保存成功！";
			}
		}else {
			return "没做考试安排，不需要保存！";
		}
	}

	/**
	 * 创建考试时间安排模板
	 */
	public MsgBean createTemplate(String lcid){
		try {
			String realpath = ServletActionContext.getServletContext().getRealPath("export/excel/TimeArrangeTemplate");
			File path = new File(realpath);
			if(!path.exists()) {
				path.mkdirs();//不存在该路径则自动生成
			}
			StringBuilder sb = new StringBuilder();
			sb.append("select t1.EXAMNAME,T1.STARTDATE,T1.ENDDATE,");
			sb.append("(SELECT count(distinct T2.KMID) FROM CUS_KW_EXAMSUBJECT T2 ");
			sb.append("WHERE T1.LCID=T2.LCID GROUP BY t2.LCID) AS KSKM ");
			sb.append("from cus_kw_examround t1 ");
			sb.append("where lcid='").append(lcid).append("'");
			List examList = getListSQL(sb.toString());
			if(examList.size() == 0){
				this.getMsgBean(false,"没有该考试信息！");
				return MsgBean;
			}else{
				Map<String, Object> examMap = (HashMap<String, Object>)examList.get(0);
				String examname = examMap.get("EXAMNAME").toString();
				String start = examMap.get("STARTDATE").toString();
				String end = examMap.get("ENDDATE").toString();
				String subject = examMap.get("KSKM").toString();
				File file = new File(path,"/"+lcid+".xls");
				if(file.exists()){
					path.delete();
				}
				FileOutputStream fileout = new FileOutputStream(file);
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet();
				sheet.setColumnWidth(0, 3766);
				//-------第0行:说明---------------
				HSSFRow rowHeard = sheet.createRow(0);//第0行
				rowHeard.setHeightInPoints(Float.parseFloat("28"));
				HSSFCell cellHeard = rowHeard.createCell(0);//第0行第1个单元格
				HSSFFont font = wb.createFont();
				font.setFontName("宋体"); 
				font.setFontHeightInPoints((short) 14);
				font.setColor(HSSFFont.COLOR_RED);
				HSSFCellStyle cellStyle =wb.createCellStyle();
		        cellStyle.setFont(font);
				String msg = "说明：请参考实例数据填写,导入时请将示例数据删除！";
				cellHeard.setCellValue(msg);
				cellHeard.setCellStyle(cellStyle);//设置单元格样式
				//-------第1行:考试信息---------------
				rowHeard = sheet.createRow(1);//第1行
				rowHeard.setHeightInPoints(Float.parseFloat("28"));
				cellHeard = rowHeard.createCell(0);//第1行第1个单元格
				msg = "考试名称："+examname+";考试批次："+subject+"批;考试日期："+start+"至"+end+";";
				font.setColor(HSSFFont.COLOR_NORMAL);
				font.setFontHeightInPoints((short) 14);
				cellStyle =wb.createCellStyle();
				cellStyle.setFont(font);
				cellHeard.setCellValue(msg);
				cellHeard.setCellStyle(cellStyle);//设置单元格样式
				//-------第2行:列标题---------------
				rowHeard = sheet.createRow(2);//第2行
				rowHeard.setHeightInPoints(Float.parseFloat("32"));
				cellHeard = rowHeard.createCell(0);
				cellHeard.setCellValue("考试日期");
				cellHeard.setCellStyle(cellStyle);//设置单元格样式
				cellHeard = rowHeard.createCell(1);
				cellHeard.setCellValue("考试时间");
				cellHeard.setCellStyle(cellStyle);//设置单元格样式
				cellHeard = rowHeard.createCell(2);
				cellHeard.setCellValue("考试批次");
				cellHeard.setCellStyle(cellStyle);//设置单元格样式
				//-------第3行:示例数据---------------				
				String sql = "select substr(t1.EXAMDATE,1,10) KSRQ,"+
						 "CONCAT(substr(t1.STARTTIME,12,5),'-',substr(t1.ENDTIME,12,5)) KSSJ,"+
						 "t2.SUBJECTNAME PCMC "+ 	
						 "from cus_kw_examschedule t1 "+
						 "left join CUS_KW_EXAMSUBJECT t2 on t2.lcid=t1.lcid and t2.kmid=t1.kmid "+ 
						 "where t1.lcid = '"+lcid+"' order by t1.STARTTIME asc";
				List pclst = getListSQL(sql);
				for (int i=0; i<pclst.size(); i++) {
					Map<String, Object> pcMap = (HashMap<String, Object>)pclst.get(0);
					String ksrq = pcMap.get("KSRQ").toString();
					String kssj = pcMap.get("KSSJ").toString();
					String pcmc = pcMap.get("PCMC").toString();					
					rowHeard = sheet.createRow(3+i);//第3行
					rowHeard.setHeightInPoints(Float.parseFloat("32"));
					cellHeard = rowHeard.createCell(0);
					cellHeard.setCellValue(ksrq);
					cellHeard.setCellStyle(cellStyle);//设置单元格样式
					cellHeard = rowHeard.createCell(1);
					cellHeard.setCellValue(kssj);
					cellHeard.setCellStyle(cellStyle);//设置单元格样式
					cellHeard = rowHeard.createCell(2);
					cellHeard.setCellValue(pcmc);
					cellHeard.setCellStyle(cellStyle);//设置单元格样式					
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
	
	/**
	 * 验证考试时间安排模板是否存在
	 */
	public MsgBean checkFile(String lcid){
		String realpath = ServletActionContext.getServletContext().getRealPath("export/excel/TimeArrangeTemplate");
		File path = new File(realpath,"/"+lcid+".xls");
		if(path.exists()){
			this.getMsgBean(true,MsgBean.EXPORT_SUCCESS);
		}else{
			this.getMsgBean(false,MsgBean.EXPORT_SUCCESS);
		}
		return MsgBean;
	}
	
	public String getWeekName(int week){
		String weekName="";
		switch (week) {
			case 1:
				weekName="星期日";
				break;
			case 2:
				weekName="星期一";
				break;
			case 3:
				weekName="星期二";
				break;
			case 4:
				weekName="星期三";
				break;
			case 5:
				weekName="星期四";
				break;
			case 6:
				weekName="星期五";
				break;
			case 7:
				weekName="星期六";
				break;
			default:
				break;
		}
		return weekName;
	}
	
}
