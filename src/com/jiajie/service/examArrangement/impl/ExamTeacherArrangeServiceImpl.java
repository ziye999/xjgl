package com.jiajie.service.examArrangement.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jxl.Sheet;
import jxl.Workbook;

import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.examArrangement.ExamTeacherArrangeService;
import com.jiajie.util.StringUtil;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.examArrangement.CusKwPatrol;
import com.jiajie.bean.examArrangement.CusKwRoomeye;
import com.jiajie.bean.exambasic.CusKwMonitorteacher;
import com.mysql.jdbc.StringUtils;

@Service("examTeacherArrangeServiceImpl")
@SuppressWarnings("all")
public class ExamTeacherArrangeServiceImpl extends ServiceBridge implements ExamTeacherArrangeService {

	public PageBean getExamTeacherInfo(Map<String, String> map) {
		String lcId=map.get("lcId");
		String is_lrjkls=map.get("is_lrjkls");
		String shizhou=map.get("shizhou");
		String quxian=map.get("quxian");
		String xuexiao=map.get("xuexiao");
		String name=map.get("name");
		String gh=map.get("gh");
		String organ = map.get("organ");
		StringBuffer sql=new StringBuffer();
		if("true".equals(is_lrjkls)){
			sql.append("SELECT T1.JKLSID,T1.LCID,T1.SCHOOLNAME,T1.TEANAME,T1.TEATYPE,T1.GH,");
			sql.append("T1.XBM,T1.SUBJECT,T1.ZYRKXD,T1.MAINFLAG,T1.SCHOOLID,T1.JSID,T1.SUBJECT as KCMC,T4.MC AS XB,");
			sql.append("CASE WHEN T1.MAINFLAG='1' THEN '是' ELSE '否' END as ISBZR");
			sql.append(" FROM CUS_KW_MONITORTEACHER t1");			
			sql.append(" LEFT JOIN ZD_XB t4 on T1.XBM=T4.DM");
			sql.append(" WHERE 1=1 and t1.TEATYPE='1'");
			sql.append(" AND T1.LCID='").append(lcId).append("'");
			if(name!=null && !"".equals(name)){
				sql.append(" AND (T1.TEANAME LIKE binary('%").append(name).append("%')");
				sql.append(" OR T1.GH LIKE binary('%").append(name).append("%')) ");
			}
		}else{
			sql.append("SELECT T1.JKLSID,T1.LCID,T1.SCHOOLNAME,T1.TEANAME,T1.TEATYPE,T1.GH,");
			sql.append("T1.XBM,T1.SUBJECT,T1.ZYRKXD,T1.MAINFLAG,T1.SCHOOLID,T1.JSID,T1.SUBJECT as KCMC,T4.MC AS XB,");
			sql.append("CASE WHEN T1.MAINFLAG='1' THEN '是' ELSE '否' END as ISBZR");
			sql.append(" FROM CUS_KW_MONITORTEACHER t1");			
			sql.append(" LEFT JOIN ZD_XB t4 on T1.XBM=T4.DM");
			sql.append(" LEFT JOIN ZXXS_XX_JBXX t5 on t1.SCHOOLID=T5.XX_JBXX_ID");
			sql.append(" LEFT JOIN COM_MEMS_ORGAN t6 on T5.SSZGJYXZDM=T6.REGION_CODE");
			sql.append(" LEFT JOIN COM_MEMS_ORGAN t7 on t6.PARENT_CODE=t7.REGION_CODE");
			sql.append(" WHERE 1=1 and t1.TEATYPE='0'");
			sql.append(" AND T1.LCID='").append(lcId).append("'");
			if(organ!=null && !"".equals(organ)){
				sql.append(" AND T1.SCHOOLID in ('").append(organ.replaceAll(",", "','")).append("')");
			}
			if(name!=null && !"".equals(name)){
				sql.append(" AND (T1.TEANAME LIKE binary('%").append(name).append("%')");
				sql.append(" OR T1.GH LIKE binary('%").append(name).append("%')) ");
			}
		}		
		PageBean bean=getSQLPageBean(sql.toString());
		return bean;
	}

	public PageBean getTeacherInfo(Map<String, String> map) {
		String lcId=map.get("lcId");
		String shizhou=map.get("shizhou");
		String quxian=map.get("quxian");
		String xuexiao=map.get("xuexiao");
		String name=map.get("name");
		String gh=map.get("gh");
		String organ=map.get("organ");
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT T1.JSID,T1.XM,T1.GH,T1.XBM,T5.MC AS XB,T1.BZR_M,T1.SCHOOLID,T2.XXMC,T1.RJKMM,T6.bjmc as KCMC,");
		sql.append("CASE WHEN T1.BZR_M='1' THEN '是' ELSE '否' END as ISBZR");
		sql.append(" from CUS_KW_TEACHER T1");
		sql.append(" LEFT JOIN ZXXS_XX_JBXX T2 ON T1.SCHOOLID=T2.XX_JBXX_ID");
		sql.append(" LEFT JOIN COM_MEMS_ORGAN T3 ON T2.SSZGJYXZDM=T3.REGION_CODE");
		sql.append(" LEFT JOIN COM_MEMS_ORGAN T4 ON T3.parent_CODE=T4.REGION_CODE");
		sql.append(" LEFT JOIN ZD_XB T5 ON T1.XBM=T5.DM");
		sql.append(" LEFT JOIN zxxs_xx_bjxx T6 ON T1.RJKMM=T6.xx_bjxx_id");
		sql.append(" WHERE T1.JSID NOT IN (SELECT DISTINCT JSID from CUS_KW_MONITORTEACHER WHERE LCID='");
		sql.append(lcId).append("' and JSID is not null)");
		if(name!=null && !"".equals(name)){
			sql.append(" AND (T1.XM LIKE binary('%").append(name).append("%')");
			sql.append(" OR T1.GH LIKE binary('%").append(name).append("%')) ");
		}
		if(organ!=null && !"".equals(organ)){
			sql.append(" AND T1.SCHOOLID in ('").append(organ.replaceAll(",", "','")).append("')");
		}
		sql.append(" ORDER BY T1.XM");
		PageBean bean=getSQLPageBean(sql.toString());
		return bean;
	}

	public MsgBean saveChouQuTeacher(String lcId,List<Map<String, Object>> list) {		
		for (Map<String, Object> map : list) {
			CusKwMonitorteacher monitor=new CusKwMonitorteacher();
			List ls = getListSQL("select * from CUS_KW_MONITORTEACHER where LCID='"+lcId+
					"' and gh='"+StringUtil.getString(map.get("gh"))+"'");
			if (ls.size()>0) {
				return getMsgBean(true, "重复监考老师信息不能抽取！");
			}
			monitor.setGh(StringUtil.getString(map.get("gh")));
			monitor.setLcid(lcId);
			monitor.setMainflag(StringUtil.getString(map.get("bzrm")));
			monitor.setSchoolname(StringUtil.getString(map.get("xxmc")));
			monitor.setSubject(StringUtil.getString(map.get("rjkmm")));
			monitor.setXbm(StringUtil.getString(map.get("xbm")));
			monitor.setTeatype("0");
			monitor.setTeaname(StringUtil.getString(map.get("xm")));
			monitor.setSchoolid(StringUtil.getString(map.get("schoolid")));
			monitor.setJsid(StringUtil.getString(map.get("jsid")));
			getSession().save(monitor);
		}
		return getMsgBean(true, "保存成功！");
	}

	public MsgBean saveLuRuTeacher(CusKwMonitorteacher cusKwMonitorteacher) {
		Boolean bool=false;
		if(cusKwMonitorteacher.getJklsid()==null||cusKwMonitorteacher.getJklsid().equals("")){
			bool=true;
			cusKwMonitorteacher.setJklsid(null);
			List ls = getListSQL("select * from CUS_KW_MONITORTEACHER where LCID='"+cusKwMonitorteacher.getLcid()+
					"' and gh='"+cusKwMonitorteacher.getGh()+"'");
			if (ls.size()>0) {
				return getMsgBean(true, "重复监考老师信息不能录入！");
			}	
		}else {
			List ls = getListSQL("select * from CUS_KW_MONITORTEACHER where LCID='"+cusKwMonitorteacher.getLcid()+
					"' and gh='"+cusKwMonitorteacher.getGh()+"'");
			if (ls.size()>0) {
				Map result = (Map) ls.get(0);
				String subject = result.get("SUBJECT").toString();
				String jkName = result.get("TEANAME").toString();
				if (subject.equals(cusKwMonitorteacher.getSubject())&&jkName.equals(cusKwMonitorteacher.getTeaname())) {
					return getMsgBean(true, "重复监考老师信息不能录入！");
				}
			}
		}
		getSession().saveOrUpdate(cusKwMonitorteacher);
		if(bool)
			return getMsgBean(true, "保存成功！");
		else{
			return getMsgBean(true, "修改成功！");			
		}		
	}

	public String findSchoolid(String schoolname){
		String sql = "select XX_JBXX_ID from zxxs_xx_jbxx where xxmc='"+schoolname+"'";
		List ls = getListSQL(sql);
		String xx_jbxx_id = "";
		if (ls.size()>0) {
			Map result = (Map) ls.get(0);
			xx_jbxx_id = result.get("XX_JBXX_ID").toString();			
		}
		return xx_jbxx_id; 
	}
	
	public String findSubject(String lcid,String subjectname){
		String sql = "select KMID from cus_kw_examsubject where subjectname='"+subjectname+"' and lcid='"+lcid+"'";
		List ls = getListSQL(sql);
		String kmid = "";
		if (ls.size()>0) {
			Map result = (Map) ls.get(0);
			kmid = result.get("KMID").toString();			
		}
		return kmid; 
	}
	
	public String findJsid(String xm,String gh,String xbm){
		String sql = "select JSID from cus_kw_teacher where xm='"+xm+"' and gh='"+gh+"' and xbm='"+xbm+"'";
		List ls = getListSQL(sql);
		String jsid = "";
		if (ls.size()>0) {
			Map result = (Map) ls.get(0);
			jsid = result.get("JSID").toString();			
		}
		return jsid; 
	}
	
	public MsgBean saveDaoRuTeacher(File file ,String lcId) {
		try {
			Workbook rwb = Workbook.getWorkbook(file);
			Sheet rs = rwb.getSheet(0);
			int clos=rs.getColumns();//得到所有的列
	        int rows=rs.getRows();//得到所有的行
	        List<String> title_list=new ArrayList<String>();
	        one:for (int i = 0; i < rows; i++) {
	        	CusKwMonitorteacher monitor=new CusKwMonitorteacher();
	            for (int j = 0; j < clos; j++) {
	            	if(i==0){
	            		String title_val =  rs.getCell(j, i).getContents();
	            		if(title_val!=null && !"".equals(title_val)){
	            			title_list.add(title_val);
	            			continue;
	            		}else{
	            			clos=j+1;
	            			break;
	            		}
	            	}
	            	String title_str="";
	            	try{
	            	 title_str=title_list.get(j>=title_list.size()?title_list.size()-1:j);
	            	}catch(Exception e){
	            		e.printStackTrace();
	            	}
	            	//第一个是列数，第二个是行数
	            	String cellVal =  rs.getCell(j, i).getContents();
	            	//如果第一列考生考号为空，直接返回	            	
	            	if("所属单位".equals(title_str)){
	            		String xx_jbxx_id = findSchoolid(cellVal);
	            		monitor.setSchoolname(cellVal);	            		
	            		monitor.setSchoolid(xx_jbxx_id);
	            		continue;
	            	}
	            	if("科目".equals(title_str)){
	            		//String subject = findSubject(lcId,cellVal);
	            		monitor.setSubject(cellVal);	            		
	            		continue;
	            	}
	            	if("姓名".equals(title_str)){
	            		if(cellVal==null || "".equals(cellVal) ){
		            		continue one;
		            	}
	            		monitor.setTeaname(cellVal);
	            		continue;
	            	}
	            	if("工号".equals(title_str)){
	            		monitor.setGh(cellVal);
	            		continue;
	            	}
	            	if("性别".equals(title_str)){
	            		if(cellVal!=null || !"".equals(cellVal)){
	            			cellVal="男".equals(cellVal)?"1":"2";
	                  	}else{
	                  		cellVal="0";
	                  	}
	            		monitor.setXbm(cellVal);
	            		continue;
	            	}
	            	String jsid = findJsid(monitor.getTeaname(),monitor.getGh(),monitor.getXbm());
	            	monitor.setJsid(jsid);
	            }
	            List ls = getListSQL("select * from CUS_KW_MONITORTEACHER where LCID='"+lcId+
						"' and gh='"+monitor.getGh()+"'");
				if (ls.size()>0) {
					return getMsgBean(true, "重复监考老师信息不能导入！");
				}
	            if(i!=0){
	            	monitor.setTeatype("0");
	            	monitor.setLcid(lcId);
	            	//getSession().save(monitor);
	            	insert("insert into CUS_KW_MONITORTEACHER (JKLSID,LCID,SCHOOLNAME,TEANAME,TEATYPE,GH,XBM,SUBJECT,SCHOOLID, JSID) values ("+
	            	"'"+StringUtil.getUUID()+"','"+monitor.getLcid()+"','"+monitor.getSchoolname()+"','"+monitor.getTeaname()+"','"+monitor.getTeatype()+
	            	"','"+monitor.getGh()+"','"+monitor.getXbm()+"','"+monitor.getSubject()+"','"+monitor.getSchoolid()+"','"+monitor.getJsid()+"')");
	            }
        	}
        	this.getMsgBean(true,MsgBean.EXPORT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.EXPORT_ERROR);
		}
		return MsgBean;
	}

	public MsgBean deleteExamTeacher(String ids) {
		List lsInfo = this.createSQLQuery("select * from CUS_KW_ROOMEYE where jklsid in ("+ids+")").list();
		if (lsInfo.size()>0) {
			return this.getMsgBean(false,"已安排监考老师，不能删除！");
		}
		lsInfo = this.createSQLQuery("select * from CUS_KW_PATROL where jklsid in ("+ids+")").list();
		if (lsInfo.size()>0) {
			return this.getMsgBean(false,"已安排巡考老师，不能删除！");
		}
		String sql="DELETE FROM CUS_KW_MONITORTEACHER where JKLSID in ("+ids+")";
		return delete(sql);
	}

	public PageBean getIAListPage(String lcid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.LCID as LCID,a.KDID as KDID,a.POINTNAME as POINTNAME,a.KCS as KCS,");
		sb.append("a.LSSL as LSSL,a.YAPXK as YAPXK,a.YAPJK as YAPJK,a.LSSL-a.YAPXK-a.YAPJK as SYRS from (");
		sb.append("select T1.LCID,T1.KDID,t1.POINTNAME,");
		sb.append("(select COUNT(*) from cus_kw_examroom t2 where t1.lcid = t2.lcid and T1.kdid=T2.kdid) as KCS, ");
		sb.append("(select COUNT(*) from cus_kw_monitorteacher t3 where t1.lcid = t3.lcid) as LSSL, ");
		sb.append("(select COUNT(*) from CUS_KW_PATROL t4 where t1.lcid = t4.lcid and t1.kdid=t4.kdid) as YAPXK,");
		sb.append("(select COUNT(distinct(jklsid)) from CUS_KW_ROOMEYE t5 where t1.lcid = t5.lcid and t1.kdid=t5.kdid) as YAPJK ");
		sb.append("from cus_kw_examschool t1 ");
		sb.append("where t1.lcid = '").append(lcid).append("') a");
		return getSQLPageBean(sb.toString());
	}

	public PageBean getPatrolArrangeTeaListPage(String lcid, String xklssl,String kdid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t1.JKLSID,T1.TEANAME,t1.GH,t3.NAME as XB ");
		sb.append("from CUS_KW_MONITORTEACHER t1 ");
		sb.append("left join SYS_ENUM_VALUE t3 on T1.xbm =t3.code and DIC_TYPE ='ZD_GB_XBM' ");
		sb.append("where T1.LCID='").append(lcid).append("' ");
		//排除监考安排的老师
		sb.append("and t1.jklsid not in (select jklsid from cus_kw_roomeye t2 where T1.LCID=t2.lcid) ");
		//排除其他考点巡考老师
		sb.append("and t1.jklsid not in (select jklsid from CUS_KW_PATROL t3 where t3.LCID=t1.lcid and t3.kdid!='").append(kdid).append("')");
		
		PageBean pageBean = getSQLPageBean(sb.toString());
		List resultList = pageBean.getResultList();
		List list = new ArrayList();
		int xklsslnum = Integer.valueOf(xklssl);
		if(resultList.size() >= xklsslnum) {
			for (int i = 0; i < xklsslnum; i++) {
				if(resultList.size() != 1) {
					int rannum = new Random().nextInt(resultList.size()-1);
					list.add(resultList.get(rannum));
					resultList.remove(rannum);
				}else {
					list.add(resultList.get(0));
					resultList.remove(0);
				}				
			}
		}
		pageBean.setResultList(list);
		return pageBean;
	}

	public MsgBean savePatrolArrange(String lcid, String kdid, String jklsids, String xkfws,String teanames) {
		//删除该轮次下已经安排的巡考老师信息
		try {
			StringBuilder sb = new StringBuilder();
			if(jklsids != null && xkfws != null) {
				String[] jklsidArray = jklsids.split(",");
				String[] xkfwArray = xkfws.split(",");
				String[] teaNameArray = teanames.split(",");
				
				if(jklsidArray.length > 0) {
					sb.append("delete from CUS_KW_PATROL where 1=1 ");
					sb.append(" and LCID = '").append(lcid).append("'");
					sb.append(" and kdid = '").append(kdid).append("'");
					delete(sb.toString());
				}							
				if((jklsidArray.length>1&&jklsidArray.length==xkfwArray.length&&xkfwArray.length==teaNameArray.length)||
				   (jklsidArray.length==1&&jklsidArray.length==teaNameArray.length&&!StringUtils.isNullOrEmpty(xkfws))) {
					for (int i = 0; i < teaNameArray.length; i++) {
						String jklsid = jklsidArray[i];
						String teaname = teaNameArray[i];
						String rang = xkfwArray[i];
						CusKwPatrol cusKwPatrol = new CusKwPatrol();
						cusKwPatrol.setKdid(kdid);
						cusKwPatrol.setLcid(lcid);
						cusKwPatrol.setJklsid(jklsid);
						cusKwPatrol.setRang(rang);
						cusKwPatrol.setTeaname(teaname);
						save(cusKwPatrol);
					}
					this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
				}else {
					this.getMsgBean(false,"未输入巡考范围不能保存");
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}

	public PageBean getInvigilatorArrangeTeaListPage(String jklssl,
			String sfcxap, String nj, String lcid, String kdid) {
		PageBean pageBean = new PageBean();
		StringBuilder sb = new StringBuilder();
		//获取该轮次下可以安排的老师
		sb.append("select T1.JKLSID,T1.LCID,T1.SCHOOLNAME,T1.TEANAME,T1.TEATYPE,T1.GH,");
		sb.append("T1.XBM,T1.SUBJECT,T1.ZYRKXD,T1.MAINFLAG,T1.SCHOOLID,T1.JSID ");
		sb.append("from cus_kw_monitorteacher t1 ");
		sb.append("where t1.lcid = '").append(lcid).append("' ");
		sb.append("and T1.jklsid not in (select jklsid from cus_kw_patrol t2 where t2.lcid = t1.lcid) ");
		sb.append("and T1.jklsid not in (select jklsid from cus_kw_roomeye t3 where t3.lcid = t1.lcid and t3.kdid!='");
		sb.append(kdid).append("' ").append(nj==null||"".equals(nj)?"":" and t3.njid!='"+nj+"'").append(") ");
		List<Map<String, Object>> tealist = getListSQL(sb.toString());
		//获取该轮次该考点该年级下考场数
		sb.delete(0, sb.length());
		sb.append("select t1.KCID,t4.KCBH,COUNT(*) as KCRS ");
		sb.append("from CUS_KW_ROOMSTUDENT t1 ");
		sb.append("LEFT join ZXXS_XS_JBXX t2 on T1.xh = t2.grbsm ");
		sb.append("LEFT join ZXXS_XX_NJXX t3 on t2.xx_njxx_id = t3.xx_njxx_id ");
		sb.append("left join cus_kw_examroom t4 on t1.kcid = t4.kcid ");
		sb.append("where t1.lcid='").append(lcid).append("' and T1.kdid='").append(kdid);
		sb.append("' ").append(nj==null||"".equals(nj)?"":" and t3.xjnjdm='"+nj+"' ");
		sb.append("group by t1.kcid,t4.kcbh ");
		List<Map<String, Object>> kclist = getListSQL(sb.toString());
		if(kclist.size() == 0) {
			List resultList = new ArrayList();
			Map map = new HashMap();
			map.put("FLAG", "0");
			map.put("MSG", "该年级考场未安排学生考试！");
			resultList.add(map);
			pageBean.setResultList(resultList);
			return pageBean;
		}
		//计算总共需要多少个监考老师
		int cont = Integer.valueOf(jklssl)*kclist.size();
		if(tealist.size() < cont) {
			List resultList = new ArrayList();
			Map map = new HashMap();
			map.put("FLAG", "0");
			map.put("MSG", "监考老师人数不足，请添加监考老师！</br>(可安排老师人数:"+tealist.size()+",需安排监考老师人数："+cont+")");
			resultList.add(map);
			pageBean.setResultList(resultList);
			return pageBean;
		}
		
		//获取该轮次该年级下考试科目数
		sb.delete(0, sb.length());
		sb.append("select t1.RCID,t1.LCID,t1.KMID,t1.EXAMDATE,t1.SDID,");
		sb.append("date_format(t1.starttime,'%Y-%m-%d %H:%i:%s') as STARTTIME,");
		sb.append("date_format(t1.endtime,'%Y-%m-%d %H:%i:%s') as ENDTIME,");
		sb.append("t1.TIMELENGTH,t2.KMID,t2.XNXQ_ID,t2.SUBJECTNAME,t2.RESULTSTYLE,");
		sb.append("t2.KCH,t2.NJ,t2.LCID,t2.SCORE,t2.TIMELENGTH,t2.XN,t2.XQ,t4.NAME ");
		sb.append("from cus_kw_examschedule t1 ");
		sb.append("LEFT join cus_kw_examsubject t2 on t1.kmid = t2.kmid ");
		sb.append("left join SYS_ENUM_VALUE t4 on t4.code = t2.nj and t4.DIC_TYPE = 'ZD_XT_NJDM' ");	
		sb.append("where t1.lcid = '").append(lcid).append("' ");
		sb.append(nj==null||"".equals(nj)?"":" and (nj = '"+nj+"' or nj is null) ");
		List<Map<String, Object>> kmlist = getListSQL(sb.toString());
		if(kmlist.size() == 0) {
			List resultList = new ArrayList();
			Map map = new HashMap();
			map.put("FLAG", "0");
			map.put("MSG", "该年级未安排考试科目！");
			resultList.add(map);
			pageBean.setResultList(resultList);
			return pageBean;
		}
		//安排监考老师
		sb.delete(0, sb.length());
		
		//抽取主监考老师
		List<Map<String, Object>> zjkList = extractionTheMainProctor(kmlist, tealist, kclist, jklssl, sfcxap);
		jklssl = String.valueOf(Integer.valueOf(jklssl) - 1);
		List<Map<String, Object>> resultList;
		if(!"0".equals(jklssl)) {
			resultList = extractionProctor(kmlist, tealist, kclist, jklssl, sfcxap,zjkList);
		}else {
			resultList = zjkList;
		}
		int lsrs = 0;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < resultList.size(); i++) {
			String jkids = resultList.get(i).get("JKIDS").toString();
			String[] jkidAry = jkids.split(",");
			for (int j = 0; j < jkidAry.length; j++) {
				map.put(jkidAry[j], jkidAry[j]);
			}
		}
		lsrs = map.size();
		resultList.get(0).put("LSRSCONT", lsrs);		
		pageBean.setResultList(resultList);
		return pageBean;
	}
	//抽取监考老师
	public List<Map<String, Object>> extractionProctor(List<Map<String, Object>> kmlist,List<Map<String, Object>> tealist,
			List<Map<String, Object>> kclist,String jklssl,String sfcxap,List<Map<String, Object>> zjkList) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		int k = 0;
		String kch = "";
		for (int i = 0; i < kmlist.size(); i++) {
			List<Map<String, Object>> bzrList = new ArrayList();
			List<Map<String, Object>> nobzrList = new ArrayList();
			List<Map<String, Object>> tealist_m = new ArrayList();
			tealist_m.addAll(tealist);
			//移除当前科目已经安排的班主任
			String rcid = kmlist.get(i).get("RCID")==null?"":kmlist.get(i).get("RCID").toString();
			String ksrq = kmlist.get(i).get("EXAMDATE")==null?"":kmlist.get(i).get("EXAMDATE").toString().replace(" 00:00:00", "");
			String starttime = kmlist.get(i).get("STARTTIME")==null?"":kmlist.get(i).get("STARTTIME").toString().substring(11, kmlist.get(i).get("STARTTIME").toString().length());
			String endtime = kmlist.get(i).get("ENDTIME")==null?"":kmlist.get(i).get("ENDTIME").toString().substring(11, kmlist.get(i).get("ENDTIME").toString().length());
			String kssj = starttime+"-"+ endtime;
			String njname = kmlist.get(i).get("NAME")==null?"":kmlist.get(i).get("NAME").toString();
			String km = kmlist.get(i).get("SUBJECTNAME")==null?"":kmlist.get(i).get("SUBJECTNAME").toString();
			String kmid = kmlist.get(i).get("KMID")==null?"":kmlist.get(i).get("KMID").toString();
			
			for (int j = 0; j < zjkList.size(); j++) {
				String kmid_m = zjkList.get(j).get("KMID")==null?"":zjkList.get(j).get("KMID").toString();
				String jklsid_m = zjkList.get(j).get("ZJKID")==null?"":zjkList.get(j).get("ZJKID").toString();
				if(kmid_m.equals(kmid)) {
					for (int l = 0; l < tealist_m.size(); l++) {
						String jklsid = tealist_m.get(l).get("JKLSID").toString();
						if(jklsid_m.equals(jklsid)) {
							tealist_m.remove(l);
						}
					}
				}
			}
			//按课程号排序教师信息
			for (int j = 0; j < tealist_m.size(); j++) {
				String subject = tealist_m.get(j).get("RCID")==null?"":tealist_m.get(j).get("RCID").toString();
				if(!"".equals(subject) && subject.equals(kch)) {
					tealist_m.add(tealist_m.remove(j));
				}
			}
			int x = 0;
			for (int j = 0; j < kclist.size(); j++) {
				String kcid = kclist.get(j).get("KCID")==null?"":kclist.get(j).get("KCID").toString();
				String kc = kclist.get(j).get("KCBH")==null?"":kclist.get(j).get("KCBH").toString()+"考场";
				String kcrs = kclist.get(j).get("KCRS")==null?"":kclist.get(j).get("KCRS").toString();
				Map<String, Object> tearesMap = new HashMap<String, Object>();
				tearesMap.put("RCID", rcid);
				tearesMap.put("KSRQ", ksrq);
				tearesMap.put("KSSJ", kssj);
				tearesMap.put("NJ", njname);
				tearesMap.put("KM", km);
				tearesMap.put("KCID", kcid);
				tearesMap.put("KC", kc);
				tearesMap.put("KCRS", kcrs);
				String jklsids = zjkList.get(k).get("ZJKID") + ",";
				String jklsnames = zjkList.get(k).get("ZJKNAME") + ",";
				tearesMap.put("ZJKID", zjkList.get(k).get("ZJKID"));
				tearesMap.put("ZJKNAME", zjkList.get(k).get("ZJKNAME"));
				for (int j2 = 0; j2 < Integer.valueOf(jklssl); j2++) {
					tearesMap.put("MAINFLAG", j2);
					int rannum;
					Map<String, Object> teaMap = new HashMap<String, Object>();
					if(tealist_m.size() != 1) {//抽取
						rannum = new Random().nextInt(tealist_m.size()-1);
						teaMap = tealist_m.get(rannum);
						tealist_m.remove(rannum);
					}else {//抽取
						teaMap = tealist_m.get(0);
						tealist_m.remove(0);
					}
					jklsids += teaMap.get("JKLSID");
					jklsnames += teaMap.get("TEANAME");
					if(j2 != Integer.valueOf(jklssl)-1) {
						jklsids += ",";
						jklsnames += ",";
					}
				}
				if(i == 0) {
					tearesMap.put("JKIDS", jklsids);
					tearesMap.put("JKNAMES", jklsnames);
				}else {
					if("0".equals(sfcxap)) {
						Map teaMap_m = resultList.get(x);
						tearesMap.put("JKIDS", teaMap_m.get("JKIDS"));
						tearesMap.put("JKNAMES", teaMap_m.get("JKNAMES"));
					}else {
						tearesMap.put("JKIDS", jklsids);
						tearesMap.put("JKNAMES", jklsnames);
					}
				}
				resultList.add(tearesMap);
				x++;
				k++;
			}
			kch = kmlist.get(i).get("KCH")==null?"":kmlist.get(i).get("KCH").toString();
		}
		return resultList;
	}
		
	//抽取主监考
	public List<Map<String, Object>> extractionTheMainProctor(List<Map<String, Object>> kmlist,List<Map<String, Object>> tealist,
			List<Map<String, Object>> kclist,String jklssl,String sfcxap) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		String kch = "";
		for (int i = 0; i < kmlist.size(); i++) {
			List<Map<String, Object>> bzrList = new ArrayList();
			List<Map<String, Object>> nobzrList = new ArrayList();
			List<Map<String, Object>> tealist_m = new ArrayList();
			tealist_m.addAll(tealist);
			//按课程号排序教师信息
			for (int j = 0; j < tealist_m.size(); j++) {
				String subject = tealist_m.get(j).get("RCID")==null?"":tealist_m.get(j).get("RCID").toString();
				if(!"".equals(subject) && subject.equals(kch)) {
					tealist_m.add(tealist_m.remove(j));
				}
			}
			String rcid = kmlist.get(i).get("RCID")==null?"":kmlist.get(i).get("RCID").toString();
			String ksrq = kmlist.get(i).get("EXAMDATE")==null?"":kmlist.get(i).get("EXAMDATE").toString().replace(" 00:00:00", "");
			String starttime = kmlist.get(i).get("STARTTIME")==null?"":kmlist.get(i).get("STARTTIME").toString().substring(11, kmlist.get(i).get("STARTTIME").toString().length());
			String endtime = kmlist.get(i).get("ENDTIME")==null?"":kmlist.get(i).get("ENDTIME").toString().substring(11, kmlist.get(i).get("ENDTIME").toString().length());
			String kssj = starttime+"-"+ endtime;
			String njname = kmlist.get(i).get("NAME")==null?"":kmlist.get(i).get("NAME").toString();
			String km = kmlist.get(i).get("SUBJECTNAME")==null?"":kmlist.get(i).get("SUBJECTNAME").toString();
			String kmid = kmlist.get(i).get("KMID")==null?"":kmlist.get(i).get("KMID").toString();
			int x = 0;
			for (int j = 0; j < kclist.size(); j++) {
				String kcid = kclist.get(j).get("KCID")==null?"":kclist.get(j).get("KCID").toString();
				String kc = kclist.get(j).get("KCBH")==null?"":kclist.get(j).get("KCBH").toString()+"考场";
				String kcrs = kclist.get(j).get("KCRS")==null?"":kclist.get(j).get("KCRS").toString();
				Map<String, Object> tearesMap = new HashMap<String, Object>();
				tearesMap.put("RCID", rcid);
				tearesMap.put("KSRQ", ksrq);
				tearesMap.put("KSSJ", kssj);
				tearesMap.put("NJ", njname);
				tearesMap.put("KM", km);
				tearesMap.put("KCID", kcid);
				tearesMap.put("KC", kc);
				tearesMap.put("KCRS", kcrs);
				tearesMap.put("KMID", kmid);
				String jklsids = "";
				String jklsnames = "";
				tearesMap.put("MAINFLAG", "1");
				String jklsid = "";
				String teaname = "";
				int rannum;
				Map<String, Object> teaMap = new HashMap<String, Object>();
				if(i == 0) {//i为0则第一轮科目考场抽取
					/*if("1".equals(sfbzrjk)) {//是否班主任为监考老师
						if(bzrList.size() > 0) {//监考老师人数大于0时
							if(bzrList.size() != 1) {
								rannum = new Random().nextInt(bzrList.size()-1);
								teaMap = bzrList.get(rannum);
								bzrList.remove(rannum);
							}else {
								teaMap = bzrList.get(0);
								bzrList.remove(0);
							}
						}else {//但监考老师人数不足时,抽取其他老师为监考老师
							if(nobzrList.size() != 1) {
								rannum = new Random().nextInt(nobzrList.size()-1);
								teaMap = nobzrList.get(rannum);
								nobzrList.remove(rannum);
							}else {
								teaMap = nobzrList.get(0);
								nobzrList.remove(0);
							}
						}
					}else {*///不抽取班主任为监考老师，随便抽
						if(tealist_m.size() != 1) {
							rannum = new Random().nextInt(tealist_m.size()-1);
							teaMap = tealist_m.get(rannum);
							tealist_m.remove(rannum);
						}else {
							teaMap = tealist_m.get(0);
							tealist_m.remove(0);
						}
					//}
					tearesMap.put("ZJKID", teaMap.get("JKLSID"));
					tearesMap.put("ZJKNAME", teaMap.get("TEANAME"));
					tearesMap.put("JKIDS", teaMap.get("JKLSID"));
					tearesMap.put("JKNAMES", teaMap.get("TEANAME"));
					resultList.add(tearesMap);
				}else {//i不为0时，则为的二轮科目考场抽取，则要判断是否重新抽取
					if("0".equals(sfcxap)) {//不重新安排，则把以前安排好的主监考老师复制一遍
						Map teaMap_m = resultList.get(x);
						tearesMap.put("ZJKID", teaMap_m.get("ZJKID"));
						tearesMap.put("ZJKNAME", teaMap_m.get("ZJKNAME"));
						tearesMap.put("JKIDS", teaMap_m.get("JKIDS"));
						tearesMap.put("JKNAMES", teaMap_m.get("JKNAMES"));
						resultList.add(tearesMap);
					}else {//重新安排则重抽
						/*if("1".equals(sfbzrjk)) {//是否班主任为监考老师
							if(bzrList.size() > 0) {//监考老师人数大于0时
								if(bzrList.size() != 1) {
									rannum = new Random().nextInt(bzrList.size()-1);
									teaMap = bzrList.get(rannum);
									bzrList.remove(rannum);
								}else {
									teaMap = bzrList.get(0);
									bzrList.remove(0);
								}
							}else {//但监考老师人数不足时,抽取其他老师为监考老师
								if(nobzrList.size() != 1) {
									rannum = new Random().nextInt(nobzrList.size()-1);
									teaMap = nobzrList.get(rannum);
									nobzrList.remove(rannum);
								}else {
									teaMap = nobzrList.get(0);
									nobzrList.remove(0);
								}
							}
						}else {*///不抽取班主任为监考老师，随便抽
							if(tealist_m.size() != 1) {
								rannum = new Random().nextInt(tealist_m.size()-1);
								teaMap = tealist_m.get(rannum);
								tealist_m.remove(rannum);
							}else {
								teaMap = tealist_m.get(0);
								tealist_m.remove(0);
							}
						//}
						tearesMap.put("ZJKID", teaMap.get("JKLSID"));
						tearesMap.put("ZJKNAME", teaMap.get("TEANAME"));
						tearesMap.put("JKIDS", teaMap.get("JKLSID"));
						tearesMap.put("JKNAMES", teaMap.get("TEANAME"));
						resultList.add(tearesMap);
					}
				}
				x++;//记录课程考场抽取的次数
			}
			kch = kmlist.get(i).get("KCH")==null?"":kmlist.get(i).get("KCH").toString();
		}
		return resultList;
	}

	public MsgBean saveInvigilatorArrange(
			List<Map<String, Object>> list) {
		try {
			if(list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					String zjkid = map.get("zjkid").toString();
					String njid = map.get("nj")==null?"":map.get("nj").toString();
					String zjkname = map.get("zjkname").toString();
					String jkids = map.get("jkids").toString();
					String jknames = map.get("jknames").toString();
					String lcid = map.get("lcid").toString();
					String kdid = map.get("kdid").toString();
					String kcid = map.get("kcid").toString();
					String rcid = map.get("rcid").toString();
					if(i == 0) {
						//删除该轮次下该年级原有数据，
						StringBuilder sb = new StringBuilder();
						sb.append("delete from CUS_KW_ROOMEYE where 1=1 ");
						sb.append(" and LCID = '").append(lcid).append("'");
						sb.append(" and kdid = '").append(kdid).append("'");
						sb.append(njid==null||"".equals(njid)?"":" and njid = '"+njid+"'");
						delete(sb.toString());
					}
					String[] jkidAry = jkids.split(",");
					String[] jknameAry = jknames.split(",");
					for (int j = 0; j < jkidAry.length; j++) {
						CusKwRoomeye jkroomeye = new CusKwRoomeye();
						jkroomeye.setJklsid(jkidAry[j]);
						jkroomeye.setTeaname(jknameAry[j]);
						jkroomeye.setKcid(kcid);
						jkroomeye.setKdid(kdid);
						jkroomeye.setLcid(lcid);
						if(j == 0) {
							jkroomeye.setMainflag("1");
						}else {
							jkroomeye.setMainflag("0");
						}
						jkroomeye.setNjid(njid);
						jkroomeye.setRcid(rcid);
						save(jkroomeye);
					}					
				}
			}
			this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}

	public PageBean getIASListPage(String lcid, String kdid, String njid, String kmid, String kcid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t1.NJID,t5.KCID,T1.RCID,group_concat(T1.jkapid) as JKAPIDS,");
		sb.append("date_format(t2.examdate,'%Y-%m-%d') as KSRQ,");
		sb.append("CONCAT(date_format(t2.starttime,'%H:%i:%s'),'-',date_format(t2.endtime,'%H:%i:%s')) as KSSJ,");
		sb.append("t4.name as NJ,t3.subjectname as KM,");
		sb.append("(select CONCAT(tx.kcbh,'考场') from cus_kw_examroom tx where tx.kcid = t1.kcid) as KC,");
		sb.append("(select COUNT(*) from cus_kw_roomstudent t0 where t0.kcid =T1.kcid and t0.lcid =T1.lcid and t0.kdid =T1.kdid) as KCRS,");
		sb.append("(select t.TEANAME from cus_kw_roomeye t where t1.lcid=t.lcid and t1.kdid=t.kdid and t1.kcid=t.kcid and t1.rcid=t.rcid ");
		sb.append("and t.mainflag=1) as ZJKNAME,");
		sb.append("group_concat(t1.TEANAME) as JKNAMES,t1.LCID,t1.KDID ");
		sb.append("from cus_kw_roomeye t1 ");
		sb.append("left join cus_kw_examschedule t2 on t2.rcid = T1.rcid ");
		sb.append("left join cus_kw_examsubject t3 on t3.kmid = t2.kmid ");
		sb.append("left join SYS_ENUM_VALUE t4 on t4.code = t3.nj and t4.DIC_TYPE = 'ZD_XT_NJDM' ");
		sb.append("left join cus_kw_examroom t5 on t5.kcid = t1.kcid ");
		sb.append("where 1=1 ");
		if(!StringUtil.isNull(lcid)) {
			sb.append("and T1.LCID='").append(lcid).append("' ");
		}
		if(!StringUtil.isNull(kdid)) {
			sb.append("and T1.kdid='").append(kdid).append("' ");
		}
		if(!StringUtil.isNull(njid)) {
			sb.append("and T1.njid='").append(njid).append("' ");
		}
		if(!StringUtil.isNull(kmid)) {
			sb.append("and t3.kmid='").append(kmid).append("' ");
		}
		if(!StringUtil.isNull(kcid)) {
			sb.append("and T1.kcid='").append(kcid).append("' ");
		}		
		sb.append("group by t5.kcid,t1.lcid,t1.kdid,t1.kcid,t1.rcid,t1.njid,t2.examdate,t2.starttime,t2.endtime,t4.name,t3.subjectname ");
		sb.append("order by t1.lcid,t1.kdid,t1.rcid,t1.njid,t2.examdate,t2.starttime,t2.endtime,t4.name,t3.subjectname,t1.kcid ");
		return getSQLPageBean(sb.toString());
	}

	public PageBean getPASListPage(String lcid, String kdid, String xmgh) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t1.TEANAME,t2.GH,T3.name as XB,T1.rang as XKFW ");
		sb.append("from cus_kw_patrol t1 ");
		sb.append("left join cus_kw_monitorteacher t2 on T1.jklsid = T2.jklsid ");
		sb.append("left join SYS_ENUM_VALUE t3 on t2.xbm =t3.code and DIC_TYPE ='ZD_GB_XBM' ");
		sb.append("where 1=1 ");
		if(!StringUtil.isNull(lcid)) {
			sb.append("and T1.LCID='").append(lcid).append("' ");
		}
		if(!StringUtil.isNull(kdid)) {
			sb.append("and T1.kdid='").append(kdid).append("' ");
		}
		if(!StringUtil.isNull(xmgh)) {
			sb.append("and (T2.TEANAME like binary('%").append(xmgh).append("%') or T2.GH like binary('%").append(xmgh).append("%'))");
		}
		return getSQLPageBean(sb.toString());
	}
	
	public PageBean hasArrangeInvigilatorTeacher(String lcid, String kdid,
			String kcid, String rcid,String njid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t1.JKAPID,t1.JKLSID,t1.TEANAME,T2.GH,t3.name as SFBZR,t5.name as SFZJK,");
		sb.append("CONCAT(t4.kcbh,'考场') as KCMC ");
		sb.append("from cus_kw_roomeye t1 ");
		sb.append("left join cus_kw_monitorteacher t2 on t1.jklsid = t2.jklsid ");
		sb.append("left join SYS_ENUM_VALUE t3 on t3.code = t2.mainflag and t3.DIC_TYPE='ZD_BB_SFBZ' ");
		sb.append("left join SYS_ENUM_VALUE t5 on t5.code = t1.mainflag and t5.DIC_TYPE='ZD_BB_SFBZ' ");
		sb.append("left join cus_kw_examroom t4 on t1.kcid = t4.kcid ");
		sb.append("where 1=1 ");
		if(!StringUtil.isNull(lcid)) {
			sb.append(" and t1.lcid = '").append(lcid).append("' ");
		}
		if(!StringUtil.isNull(kdid)) {
			sb.append(" and t1.kdid = '").append(kdid).append("' ");
		}
		if(!StringUtil.isNull(kcid)) {
			sb.append(" and t1.kcid = '").append(kcid).append("' ");
		}
		if(!StringUtil.isNull(rcid)) {
			sb.append(" and t1.rcid = '").append(rcid).append("' ");
		}
		sb.append(" order by t1.mainflag desc");
		return getSQLPageBean(sb.toString());
	}
	public PageBean noneArrangeInvigilatorTeacher(String lcid, String kdid,
			String kcid, String rcid,String njid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select '' as JKAPID,t1.JKLSID,t1.TEANAME,t1.GH,t3.name as SFBZR,'' as KCMC ");
		sb.append("from cus_kw_monitorteacher t1 ");
		sb.append("left join SYS_ENUM_VALUE t3 on t3.code = t1.mainflag and t3.DIC_TYPE='ZD_BB_SFBZ' ");
		sb.append("where 1=1 ");
		sb.append("and t1.jklsid not in (select t2.jklsid from cus_kw_roomeye t2 where t2.lcid = '").append(lcid).append("') ");
		sb.append("and t1.jklsid not in (select jklsid from cus_kw_patrol where lcid = '").append(lcid).append("') ");
		sb.append("UNION ALL ");
		sb.append("select t1.JKAPID,t1.JKLSID,t1.TEANAME,T2.GH,t3.name as SFBZR,CONCAT(t4.kcbh,'考场') as KCMC ");
		sb.append("from cus_kw_roomeye t1 ");
		sb.append("left join cus_kw_monitorteacher t2 on t1.jklsid = t2.jklsid ");
		sb.append("left join SYS_ENUM_VALUE t3 on t3.code = t2.mainflag and t3.DIC_TYPE='ZD_BB_SFBZ' ");
		sb.append("left join SYS_ENUM_VALUE t5 on t5.code = t1.mainflag and t5.DIC_TYPE='ZD_BB_SFBZ' ");
		sb.append("left join cus_kw_examroom t4 on t1.kcid = t4.kcid ");
		sb.append("where t1.lcid = '").append(lcid).append("' ");
		sb.append("and t1.kdid = '").append(kdid).append("' ");
		sb.append(njid==null||"".equals(njid)?"":"and t1.njid = '"+njid+"' ");
		sb.append("and t1.rcid != '").append(rcid).append("' ");
		sb.append("and t1.jklsid not in (select t2.jklsid from cus_kw_roomeye t2 where t2.lcid='").append(lcid).append("' ");
		sb.append("and t2.kdid = '").append(kdid).append("' ");
		sb.append(njid==null||"".equals(njid)?"":"and t2.njid = '"+njid+"' ");
		sb.append("and t2.rcid = '").append(rcid).append("') ");			
		sb.append("order by kcmc,sfbzr");
		return getSQLPageBean(sb.toString());
	}	

	public MsgBean saveAdjustInvigilator(String lcid,
			String kdid, String kcid, String rcid,String njid, String iszjklsid,
			String jklsids,String teanames) {
		try{
			if(StringUtil.isNull(lcid) || StringUtil.isNull(kdid) || StringUtil.isNull(kcid) || StringUtil.isNull(rcid)) {
				this.getMsgBean(false,MsgBean.SAVE_ERROR);
				return MsgBean;
			}
			StringBuilder sb = new StringBuilder();
			//删除原监考老师
			sb.append("delete from cus_kw_roomeye where lcid='").append(lcid)
			.append("' and kdid='").append(kdid)
			.append("' and kcid='").append(kcid)
			.append("' and rcid='").append(rcid).append("'");
			delete(sb.toString());
			
			String[] jklsAry = jklsids.split(",");
			String[] teanameAry = teanames.split(",");
			for (int i = 0; i < jklsAry.length; i++) {
				String jklsid = jklsAry[i];
				String teaname = teanameAry[i];
				String mainflag = "0";
				CusKwRoomeye cusKwRoomeye = new CusKwRoomeye();
				cusKwRoomeye.setJklsid(jklsid);
				cusKwRoomeye.setKcid(kcid);
				cusKwRoomeye.setKdid(kdid);
				cusKwRoomeye.setLcid(lcid);
				if(iszjklsid.equals(jklsid)) {
					mainflag = "1";
				}
				cusKwRoomeye.setMainflag(mainflag);
				cusKwRoomeye.setNjid(njid);
				cusKwRoomeye.setRcid(rcid);
				cusKwRoomeye.setTeaname(teaname);
				save(cusKwRoomeye);
			}
			this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}
	
}
