package com.jiajie.service.basicsinfo.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.basicsinfo.CusKwBuilding;
import com.jiajie.bean.basicsinfo.CusKwTeacher;
import com.jiajie.bean.dailyManagement.CusXjBreakstudy;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.basicsinfo.TeacherService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.OrganTree;
@Service("teacherService")
@SuppressWarnings("all")
public class TeacherServiceImpl extends ServiceBridge implements TeacherService{

	public com.jiajie.bean.MsgBean delTeacher(HttpServletRequest request,String jsid) {
		try {
			String sql = "from CusKwTeacher where jsid in ("+jsid+")";
			List ls = createHQLQuery(sql).list();
			if (ls.size()>0) {
				List lsSy = getListSQL("select * from cus_kw_monitorteacher where jsid in ("+jsid+")");
				if (lsSy.size()>0) {
					return this.getMsgBean(false,"这个老师已经安排监考，不能删除！");
				}
				lsSy = getListSQL("select * from cus_kw_patrol where JKLSID in ("+jsid+")");
				if (lsSy.size()>0) {
					return this.getMsgBean(false,"这个老师已经安排巡考，不能删除！");
				}
				
				CusKwTeacher bs = (CusKwTeacher)ls.get(0);
				String savePath = request.getSession().getServletContext().getRealPath(File.separator+"uploadFile"+File.separator+"photo");
				
				if (StringUtil.isNotNullOrEmpty(bs.getPhotopath())) {
					String filename = bs.getPhotopath().substring(bs.getPhotopath().lastIndexOf(File.separator));
					File savefile = new File(new File(savePath), filename);
					StringUtil.deleteDir(savefile);
				}				
				StringBuffer sb = new StringBuffer();
				sb.append("delete from CUS_KW_TEACHER where jsid in(").append(jsid).append(")");
				return delete(sb.toString());
			}else {
				return this.getMsgBean(false,MsgBean.DEL_ERROR);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return this.getMsgBean(false,MsgBean.DEL_ERROR);
		}
	}

	public PageBean getList(CusKwTeacher teacher,MBspInfo bspInfo) {
		String sqlKc = "SELECT XX_JBXX_ID from ZXXS_XX_JBXX WHERE DWLX='1' and SSZGJYXZDM='"+teacher.getSchoolid()+"'";
		List lsKc = getListSQL(sqlKc);
		String schoolid = "";
		if (lsKc.size()>0) {
			Map<String, Object> mp = (Map<String, Object>) lsKc.get(0);
			schoolid = mp.get("XX_JBXX_ID").toString();
		}
		String sql = " select T1.JSID,T1.XM,t1.XBM,T2.mc as XB,T1.JTZZ,date_format(T1.CSRQ,'%Y-%m-%d') as CSRQ,"+
				" t1.JG,CONCAT(t3.mc,t4.mc,t5.mc) as JGMC,t1.SCHOOLID,(select xxmc from ZXXS_XX_JBXX where xx_jbxx_id=t1.SCHOOLID) XXMC,"+
				" t1.SFZJLXM,t6.mc as SFZJLX,T1.SFZJH,t1.LXDH,T1.ZZMMM,t7.mc as ZZMM,T1.JKZKM,t8.mc as JKZK,"+
				" t1.BZR_M,t9.mc as BZR,t1.MZM,t10.mc as MZ,t1.GH,t1.RJKMM,t1.rjkmm as RJKM,T1.PHOTOPATH"+
				" from CUS_KW_TEACHER t1"+
				" left join zd_xb t2 on T1.XBM=T2.dm"+
				" left join ZD_XZQH t3 on CONCAT(SUBSTR(t1.jg, 1, 2),'0000')=t3.dm"+
				" left join ZD_XZQH t4 on CONCAT(SUBSTR(t1.jg, 1, 4),'00')=t4.dm"+
				" left join ZD_XZQH t5 on t1.jg=t5.dm"+
				" left join ZD_ZJLX t6 on t1.sfzjlxm=t6.dm"+
				" left join ZD_ZZMM t7 on t1.zzmmm=t7.dm"+
				" left join ZD_JKZK t8 on t1.jkzkm=t8.dm"+
				" left join ZD_SFBZ t9 on t1.bzr_m=t9.dm"+				
				" left join ZD_MZ t10 on t1.mzm=t10.dm"+
				" where 0=0";
		if(teacher.getSchoolid()!=null && !"".equals(teacher.getSchoolid())){
			sql += " and t1.schoolid='"+schoolid+"'";
		}
		if(teacher.getXm()!=null && !"".equals(teacher.getXm())){
			sql += " and (t1.gh like binary('%"+teacher.getXm()+"%') or t1.xm like binary('%"+teacher.getXm()+"%'))";
		}
		sql +=	" order by T1.jsid desc";
		return getSQLPageBean(sql);
	}

	public com.jiajie.bean.MsgBean getTeacher(String jsid) {
		try {
			Object obj = get(CusKwTeacher.class, jsid);
			this.getMsgBean(obj);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.GETDATA_ERROR);
		}
		return MsgBean;
	}

	public com.jiajie.bean.MsgBean saveOrUpdateTeacher(CusKwTeacher teacher) {
		//判断是否有工号与身份证号相同教师
		if(teacher.getJsid()==null || "".equals(teacher.getJsid())) {
			String sql = "select * from cus_kw_teacher where gh='"+teacher.getGh()+"'";
			List list = getListSQL(sql);
			if(list != null && list.size()>0){
				this.getMsgBean(false,"该职工编号已存在！");
				return MsgBean;			
			}
			sql = "select * from cus_kw_teacher where SFZJH='"+teacher.getSfzjh()+"'";
			List listSf = getListSQL(sql);
			if(listSf != null && listSf.size()>0){
				this.getMsgBean(false,"该职工身份证号号已存在！");
				return MsgBean;
			}
			String sqlKc = "SELECT XX_JBXX_ID from ZXXS_XX_JBXX WHERE DWLX='1' and SSZGJYXZDM='"+teacher.getSchoolid()+"'";
			List lsKc = getListSQL(sqlKc);
			String schoolid = "";
			if (lsKc.size()>0) {
				Map<String, Object> mp = (Map<String, Object>) lsKc.get(0);
				schoolid = mp.get("XX_JBXX_ID").toString();
			}
			teacher.setSchoolid(schoolid);
		}else {
			String sql = " select JSID,GH from cus_kw_teacher where gh='"+teacher.getGh()+"'";
			List list = getListSQL(sql);
			if(list != null && list.size()>0){
				Map result = (Map) list.get(0);
				String jsid = result.get("JSID").toString();
				String gh = result.get("GH").toString();
				if (!teacher.getJsid().equals(jsid)&&teacher.getGh().equals(gh)) {
					this.getMsgBean(false,"该职工编号已存在！");
					return MsgBean;
				}				
			}
			sql = " select JSID,GH from cus_kw_teacher where SFZJH='"+teacher.getSfzjh()+"'";
			List listSf = getListSQL(sql);
			if(listSf != null && listSf.size()>0){
				Map result = (Map) listSf.get(0);
				String jsid = result.get("JSID").toString();
				if (!teacher.getJsid().equals(jsid)) {
					this.getMsgBean(false,"该职工身份证号已存在！");
					return MsgBean;
				}
			}	
		}
		try {			
			saveOrUpdate(teacher);
			this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}
	
	public Object getCkdw(MBspInfo bspInfo) {
		/*String sql = "from CusKwCkdw where sszgjyxzdm ='"+bspInfo.getOrgan().getOrganCode()+"' order by sszgjyxzdm,xxbsm";
		List ls = createHQLQuery(sql).list();
		JSONArray ary = new JSONArray();
		if (ls==null||ls.size()<=0) {
			sql = "from CusKwCkdw where sszgjyxzdm in (select regioncode from CusKwZkdw where parentcode='"+bspInfo.getOrgan().getOrganCode()+"') order by sszgjyxzdm,xxbsm";
			ls = createHQLQuery(sql).list();
			if (ls==null||ls.size()<=0) {
				sql = "from CusKwCkdw where sszgjyxzdm in (select regioncode from CusKwZkdw where parentcode in (select regioncode from CusKwZkdw where parentcode='"+bspInfo.getOrgan().getOrganCode()+"')) order by sszgjyxzdm,xxbsm";
				ls = createHQLQuery(sql).list();
			}
		}
		for(int i=0;i<ls.size();i++){
			CusKwCkdw ckdw = (CusKwCkdw)ls.get(i);
			JSONObject o = new JSONObject();
			o.put("id", ckdw.getXxjbxxid());
			o.put("code", "");
			o.put("text", ckdw.getXxmc());
			o.put("leaf", new Boolean(true));
			ary.add(o);
		}
		return ary.toString();*/
		List<OrganTree> list=getTreeList(null,0,bspInfo);
		JSONArray array=JSONArray.fromObject(list);
		return array.toString().replaceAll("\"", "'").replaceAll("'false'", "false").replaceAll("'true'", "true");
	}
	
	private List<OrganTree> getTreeList(String parentid,int i,MBspInfo bspInfo){
		String orgcode = bspInfo.getOrgan().getOrganCode();
		String sql="";
		//or T1.SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+parentid+"') or T1.SSZGJYXZDM IN (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+parentid+"')))
		if("1".equals(bspInfo.getUserType()) && orgcode.endsWith("0000000000") && (parentid==null || "".equals(parentid)) && i==0 ){
			//获取省级组考单位
			sql = " SELECT distinct a.REGION_CODE AS CODEID,a.REGION_EDU AS CODENAME,"+
					"case when (select count(XX_JBXX_ID) from ZXXS_XX_JBXX where dwlx='1' and SSZGJYXZDM=a.REGION_CODE)>0 then 2 else 1 end AS CODETYPE "+
					"FROM COM_MEMS_ORGAN a "+
					"WHERE a.REGION_CODE='"+orgcode+"' ORDER BY a.REGION_CODE ";
		}else if(i==1 && orgcode.endsWith("0000000000") || i==0 && orgcode.endsWith("00000000")){
			//获取市级组考单位
			if(orgcode.endsWith("00000000") && i==0){
				sql = " SELECT distinct a.REGION_CODE AS CODEID,a.REGION_EDU AS CODENAME,"+
						"case when (select count(XX_JBXX_ID) from ZXXS_XX_JBXX where dwlx='1' and SSZGJYXZDM=a.REGION_CODE)>0 then 2 else 1 end AS CODETYPE "+
						"FROM COM_MEMS_ORGAN a "+
						"WHERE a.REGION_CODE='"+orgcode+"' ";
			}else{
				sql = " SELECT distinct a.REGION_CODE AS CODEID,a.REGION_EDU AS CODENAME,"+
						"case when (select count(XX_JBXX_ID) from ZXXS_XX_JBXX where dwlx='1' and SSZGJYXZDM=a.REGION_CODE)>0 then 2 else 1 end AS CODETYPE "+
						"FROM COM_MEMS_ORGAN a " +
						"WHERE a.PARENT_CODE='"+parentid+"' ";
						//" SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME,1 AS CODETYPE FROM ZXXS_XX_JBXX T1 " +
						//" WHERE T1.SSZGJYXZDM='"+parentid+"' and T1.dwlx='1' ";UNION ALL
			}
		}else if(orgcode.endsWith("000000") && i==0 || i==1 && orgcode.endsWith("00000000") || orgcode.endsWith("0000000000") && i==2 ){
			//获取县级组考单位
			if(orgcode.endsWith("000000") && i==0){
				sql = " SELECT distinct a.REGION_CODE AS CODEID,a.REGION_EDU AS CODENAME,"+
						"case when (select count(XX_JBXX_ID) from ZXXS_XX_JBXX where dwlx='1' and SSZGJYXZDM=a.REGION_CODE)>0 then 2 else 1 end AS CODETYPE "+
						"FROM COM_MEMS_ORGAN a "+
						"WHERE a.REGION_CODE='"+orgcode+"' ";
			}else{
				sql = " SELECT distinct a.REGION_CODE AS CODEID,a.REGION_EDU AS CODENAME,"+
						"case when (select count(XX_JBXX_ID) from ZXXS_XX_JBXX where dwlx='1' and SSZGJYXZDM=a.REGION_CODE)>0 then 2 else 1 end AS CODETYPE "+
						"FROM COM_MEMS_ORGAN a " +
						"WHERE a.PARENT_CODE='"+parentid+"' ";
						//" SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME,1 AS CODETYPE FROM ZXXS_XX_JBXX T1 " +
						//" WHERE T1.SSZGJYXZDM='"+parentid+"' and T1.dwlx='1' ";UNION ALL
			}
		/*}else if(("2".equals(bspInfo.getUserType()) && i==0 && (parentid==null || "".equals(parentid))) ||
				orgcode.endsWith("000000") && i==1 || i==2 && orgcode.endsWith("00000000") || orgcode.endsWith("0000000000") && i==3){
			//获取参考单位
			sql = " SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME,2 AS CODETYPE FROM ZXXS_XX_JBXX T1 where T1.dwlx='1' ";
			if("1".equals(bspInfo.getUserType())){
				sql += " and T1.SSZGJYXZDM='"+parentid+"' ";
			}else if("2".equals(bspInfo.getUserType())){
				sql += " and T1.XX_JBXX_ID='"+orgcode+"' ";
			}*/					
		}else{
			return null;
		}
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		List<OrganTree> array=new ArrayList<OrganTree>();
		for (Map<String, Object> map : list) {
			OrganTree tree=new OrganTree();
			tree.setId(StringUtil.getString(map.get("CODEID")));
			tree.setText(StringUtil.getString(map.get("CODENAME")));
			tree.setIndex(Integer.parseInt(StringUtil.getString(map.get("CODETYPE"))));
			List<OrganTree> childList=getTreeList(StringUtil.getString(map.get("CODEID")),i+1,bspInfo);
			tree.setChildren(childList);
			if(childList!=null && childList.size()>0){
				tree.setLeaf("false");
			}else{
				tree.setLeaf("true");
			}
			array.add(tree);
		}
		return array;
	}
}
