package com.jiajie.service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.jiajie.bean.MsgBean;
import com.opensymphony.xwork2.ActionSupport;
import com.jiajie.bean.PageBean;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.StringUtil;

@Service("baseServiceImpl")
@SuppressWarnings("all")
public class BaseServiceImpl extends ServiceBridge implements BaseService{
	public String getOrgId(String userid){
		String sql="select USER_TYPE from t_qxgl_usermapping WHERE USER_ID='"+userid+"'";
		List list=getSession().createSQLQuery(sql).list();
		String orgid="";
		if(list!=null && list.size()>0){
			if("2".equals(list.get(0).toString())){
				sql="SELECT US.USER_ID,x.SSZGJYXZDM as ORGAN_CODE,X.XXMC"+
						" FROM t_qxgl_usermapping us"+
						" LEFT JOIN ZXXS_XX_JBXX x ON US.ORGAN_CODE = x.XX_JBXX_ID"+
						" WHERE US.USER_ID='"+userid+"'";
				List<Map<String, Object>> listM=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
				if(listM!=null && listM.size()>0){
					orgid=listM.get(0).get("ORGAN_CODE").toString();
				}
			}else{
				sql="SELECT US.USER_ID,US.ORGAN_CODE,X.REGION_EDU"+
						" FROM t_qxgl_usermapping us"+
						" LEFT JOIN COM_MEMS_ORGAN x ON US.ORGAN_CODE = x.REGION_CODE"+
						" where US.USER_ID='"+userid+"'";
				List<Map<String, Object>> listM=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
				if(listM!=null && listM.size()>0){
					orgid=listM.get(0).get("ORGAN_CODE").toString();
				}
			}
		}
		return orgid;
	}
	public List<String> getSchoolids(MBspInfo mBspInfo) {
		List<String> reslist = new ArrayList<String>();
		if("1".equals(mBspInfo.getUserType())||"2".equals(mBspInfo.getUserType())) {
			StringBuffer sb = new StringBuffer();
			sb.append("select * from ZXXS_XX_JBXX where dwlx='2' and sszgjyxzdm = '").append(mBspInfo.getOrgan().getOrganCode()).append("'");
			List<Map<String, Object>> list = getListSQL(sb.toString());
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					String schoolid = list.get(i).get("XX_JBXX_ID") == null?"":list.get(i).get("XX_JBXX_ID").toString();
					reslist.add(schoolid);
				}
			}
		}else if("3".equals(mBspInfo.getUserType())) {
			reslist.add(mBspInfo.getOrgan().getOrganCode());
		}
		return reslist;
	}

	public String getSchoolId(String userid) {
		String sql="select USER_TYPE from t_qxgl_usermapping WHERE USER_ID='"+userid+"'";
		List list=getSession().createSQLQuery(sql).list();
		String schoolid="";
		if(list!=null && list.size()>0){
			if("2".equals(list.get(0).toString())||"1".equals(list.get(0).toString())){
				sql="SELECT ORGAN_CODE from t_qxgl_usermapping where USER_TYPE='2' and USER_ID='"+userid+"'";
				List schoolIdList=getSession().createSQLQuery(sql).list();
				if(schoolIdList!=null && schoolIdList.size()>0){
					schoolid=StringUtil.getString(schoolIdList.get(0));
				}
			}else{
				sql="SELECT group_concat(XX_JBXX_ID) as SCHOOLID from ZXXS_XX_JBXX WHERE dwlx='2'"+ 
					" and XX_JBXX_ID in (SELECT ORGAN_CODE FROM t_qxgl_usermapping WHERE USER_ID = '"+userid+
					"') or SSZGJYXZDM in (SELECT REGION_CODE FROM COM_MEMS_ORGAN"+ 
					" where PARENT_CODE in (SELECT ORGAN_CODE FROM t_qxgl_usermapping WHERE USER_ID = '"+userid+
					"') or REGION_CODE in (SELECT ORGAN_CODE FROM t_qxgl_usermapping WHERE USER_ID = '"+userid+
					"') or PARENT_CODE in (SELECT REGION_CODE FROM com_mems_organ where parent_code in (SELECT ORGAN_CODE FROM t_qxgl_usermapping WHERE USER_ID = '"+userid+"')))";
				List schoolIdList=getSession().createSQLQuery(sql).list();
				if(schoolIdList!=null && schoolIdList.size()>0){
					schoolid=StringUtil.getString(schoolIdList.get(0));
				}
				
			}
			
		}
		return schoolid;
	}
	
}
