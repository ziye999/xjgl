package com.jiajie.service.core.impl;
/**
 * 用户信息
 * */
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.bean.system.TQxglUserinfo;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.UserInfoService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.Tools;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;
@Service("userInfoService")
@SuppressWarnings("all")
public class UserInfoServiceImpl extends ServiceBridge implements UserInfoService { 
	
	public MBspInfo getMBspInfo(String code) {
		MBspInfo mBspInfo = new MBspInfo();
		StringBuilder sb = new StringBuilder();		
		mBspInfo.setUserId(code);
		sb.append("select USERNAME from T_QXGL_USERINFO where USERCODE ='").append(code).append("'");
		List<Map<String, Object>> userList = this.getListSQL(sb.toString());
		if(userList != null && userList.size() > 0) {
			Map<String, Object> map =userList.get(0);
			mBspInfo.setUserName((String)map.get("USERNAME"));			
		}
		sb.delete(0, sb.length());	
		sb.append("select USER_TYPE,ORGAN_CODE from t_qxgl_usermapping where user_id ='").append(code).append("'");
		List<Map<String, Object>> userMappingList = this.getListSQL(sb.toString());
		String mapOrganCode = "";
		if(userMappingList != null && userMappingList.size() > 0) {
			Map<String, Object> map =userMappingList.get(0);
			mBspInfo.setUserType(Tools.getMap(userMappingList,"USER_TYPE"));
			mapOrganCode = (String)map.get("ORGAN_CODE");
		}
		sb.delete(0, sb.length());
		if ("2".equals(mBspInfo.getUserType())||"3".equals(mBspInfo.getUserType())) {
			sb.append("select XX_JBXX_ID,XXMC,SSZGJYXZDM,SSZGJYXZMC from ZXXS_XX_JBXX where XX_JBXX_ID ='").append(mapOrganCode).append("'");
			List<Map<String, Object>> xxjbxxList = this.getListSQL(sb.toString());
			if(xxjbxxList != null && xxjbxxList.size() > 0) {
				Map<String, Object> map =xxjbxxList.get(0);
				mBspInfo.getOrgan().setOrganCode((String)map.get("XX_JBXX_ID"));
				mBspInfo.getOrgan().setOrganName((String)map.get("XXMC"));
				mBspInfo.getOrgan().setOrganType("9");
				mBspInfo.getOrgan().setParentCode((String)map.get("SSZGJYXZDM"));
				
				mBspInfo.setRegionCode((String)map.get("SSZGJYXZDM"));
				mBspInfo.setRegionName((String)map.get("SSZGJYXZMC"));
				
				//添加所有上级机构
				sb.delete(0, sb.length());
				sb.append("select SSZGJYXZDM,REGION_CODE,PARENT_CODE from ZXXS_XX_JBXX where XX_JBXX_ID ='").append((String)map.get("XX_JBXX_ID")).append("'");
				MBspOrgan parentOrgans = new MBspOrgan();
				List<Map<String, Object>> parOrganList = this.getListSQL(sb.toString());
				if(parOrganList != null && parOrganList.size() > 0) {
					Map<String, Object> mapP =parOrganList.get(0);
					parentOrgans.setOrganCode((String)mapP.get("SSZGJYXZDM"));
					sb.delete(0, sb.length());
					sb.append("select * from COM_MEMS_ORGAN where REGION_CODE ='").append((String)mapP.get("REGION_CODE")).append("'");
					parOrganList = this.getListSQL(sb.toString());
					if(parOrganList != null && parOrganList.size() > 0) {
						MBspOrgan parentOrgan_m = new MBspOrgan();
						parentOrgan_m.setOrganCode((String)mapP.get("PARENT_CODE"));
						sb.delete(0, sb.length());
						sb.append("select * from COM_MEMS_ORGAN where REGION_CODE ='").append((String)mapP.get("PARENT_CODE")).append("'");
						parOrganList = this.getListSQL(sb.toString());
						if(parOrganList != null && parOrganList.size() > 0) {
							MBspOrgan parentOrgan_mm = new MBspOrgan();
							parentOrgan_mm.setOrganCode((String)mapP.get("PARENT_CODE"));
							parentOrgan_m.setParentOrgans(parentOrgan_mm);
						}
						parentOrgans.setParentOrgans(parentOrgan_m);
					}
					mBspInfo.getOrgan().setParentOrgans(parentOrgans);
				}
			}
		} else if ("1".equals(mBspInfo.getUserType())) {
			sb.append("select REGION_CODE,REGION_EDU,REGION_LEVEL,PARENT_CODE from V_COM_MEMS_ORGAN where region_code ='").append(mapOrganCode).append("'");
			List<Map<String, Object>> memsOrganList = this.getListSQL(sb.toString());
			if(memsOrganList != null && memsOrganList.size() > 0) {
				Map<String, Object> map =memsOrganList.get(0);
				mBspInfo.getOrgan().setOrganCode((String)map.get("REGION_CODE"));
				mBspInfo.getOrgan().setOrganName((String)map.get("REGION_EDU"));
				mBspInfo.getOrgan().setOrganType((String)map.get("REGION_LEVEL"));
				mBspInfo.getOrgan().setParentCode((String)map.get("PARENT_CODE"));
				mBspInfo.setRegionCode((String)map.get("REGION_CODE"));
				mBspInfo.setRegionName((String)map.get("REGION_EDU"));
				//获取当前组考单位的参考单位
				mBspInfo.getOrgan().setChildSchools(getSchoolsByOrganCode((String)map.get("REGION_CODE")));
				//获取所有下级机构
				sb.delete(0, sb.length());
				sb.append("select * from V_COM_MEMS_ORGAN where PARENT_CODE ='").append((String)map.get("REGION_CODE")).append("'");
				List<Map<String, Object>> organsList = this.getListSQL(sb.toString());
				if(organsList != null &&  organsList.size() > 0) {
					List<MBspOrgan> childOrgans = new ArrayList<MBspOrgan>();
					for (int i = 0; i < organsList.size(); i++) {
						MBspOrgan bspOrgan =new MBspOrgan();
						bspOrgan.setOrganCode(Tools.getMapByNum(organsList,"REGION_CODE",i));
						bspOrgan.setOrganName(Tools.getMapByNum(organsList,"REGION_EDU",i));
						bspOrgan.setOrganType(Tools.getMapByNum(organsList,"REGION_LEVEL",i));
						bspOrgan.setParentCode(Tools.getMapByNum(organsList,"PARENT_CODE",i));
						mBspInfo.setRegionCode(Tools.getMapByNum(organsList,"REGION_CODE",i));
						mBspInfo.setRegionName(Tools.getMapByNum(organsList,"REGION_EDU",i));
						childOrgans.add(bspOrgan);
						//获取组考单位的参考单位
						bspOrgan.setChildSchools(getSchoolsByOrganCode(Tools.getMapByNum(organsList,"REGION_CODE",i)));
					}
					mBspInfo.getOrgan().setChildOrgans(childOrgans);
				}				
			}
		}		
		return mBspInfo;
	}
	
	public List<MBspOrgan> getSchoolsByOrganCode(String organCode) {
		List<MBspOrgan> childSchools = null;
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ZXXS_XX_JBXX ");		
		sb.append("where SSZGJYXZDM = '").append(organCode).append("'");
		List<Map<String, Object>> schoolList = this.getListSQL(sb.toString());
		if(schoolList != null &&  schoolList.size() > 0) {
			childSchools = new ArrayList<MBspOrgan>();
			for (int i = 0; i < schoolList.size(); i++) {
				MBspOrgan bspOrgan =new MBspOrgan();
				bspOrgan.setOrganCode(Tools.getMapByNum(schoolList,"XX_JBXX_ID",i));
				bspOrgan.setOrganName(Tools.getMapByNum(schoolList,"XXMC",i));
				bspOrgan.setOrganType("9");
				bspOrgan.setParentCode(Tools.getMapByNum(schoolList,"SSZGJYXZDM",i));
				childSchools.add(bspOrgan);
			}
		}
		return childSchools;
	}
	
	public TQxglUserinfo getUserLogin(String loginid, String loginpwd) {		
		if(loginpwd.length() < 32) {
			loginpwd = new Md5Hash(loginid,loginpwd).toString();
			
			
			
		}
		List<TQxglUserinfo> list = getSession().createQuery("FROM TQxglUserinfo t WHERE (t.usertype=2 or t.usertype=1 or t.usertype=4) and  t.loginid= '"+loginid+"' and t.loginpwd = '"+loginpwd+"' and t.state = '1'").list();
		if(list != null && list.size() > 0) {
			//登录成功写入log
			TQxglUserinfo userinfo=(TQxglUserinfo)list.get(0);
			if(userinfo!=null){
				if(userinfo.getUsertype().equals("2")){
					 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式  
					String insertSql="INSERT INTO t_login_logs (`loginid`, `userid`, `logintime`, `type`, `username`) VALUES ('"+StringUtil.getUUID()+"', '"+userinfo.getUsercode()+"', '"+df.format(new Date())+"', '1', '"+userinfo.getLoginid()+"')";
					insert(insertSql);
				}
				return userinfo;
			}
			return null;
		}
		return null;
	}
	
	public String getRoleid(String loginid, String loginpwd) {
		if(loginpwd.length() < 32) {
			loginpwd = new Md5Hash(loginid,loginpwd).toString();
		}
		String sql = "select r.ROLECODE, r.ROLENAME from t_qxgl_userinfo u "+
				"left join t_qxgl_userrole ur on u.usercode=ur.usercode "+
				"left join t_qxgl_roleinfo r on ur.rolecode=r.rolecode "+
				"where ur.rolecode!='1' and loginid='"+loginid+"' order by r.state, r.rolecode";
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		if(list != null && list.size() > 0) {
			Map<String, Object> hm = list.get(0);
			if(hm.get("ROLECODE") != null) {
				return hm.get("ROLECODE").toString() + "@" + hm.get("ROLENAME").toString();
			} else {
				return "9@游客";
			}
		}
		return "9@游客";		
	}
	
	public String getUsercode(String loginid, String loginpwd) {
		if(loginpwd.length() < 32) {
			loginpwd = new Md5Hash(loginid,loginpwd).toString();
		}
		List<TQxglUserinfo> list = getSession().createQuery("FROM TQxglUserinfo t WHERE t.loginid= '"+loginid+"' and t.loginpwd = '"+loginpwd+"'").list();
		if(list != null && list.size() > 0) {
			return list.get(0).getUsercode();
		}
		return "";
	}
			
	public TQxglUserinfo findByUsercode(String usercode) {
		List<TQxglUserinfo> list = getSession().createQuery("FROM TQxglUserinfo t WHERE t.usercode = '"+usercode+"'").list();
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
		
	public boolean checkE2id(String e2id) {
		String sql="SELECT * FROM T_QXGL_USERINFO WHERE E2ID='"+e2id+"'";
		List list=getSession().createSQLQuery(sql).list();
		return list.size()>0;
	}

	public List getKCList(String user_id) {
		
		String sql="SELECT room.FJID,room.ROOMNAME from t_qxgl_usermapping  g join  ZXXS_XX_JBXX  b on g.ORGAN_CODE=b.SSZGJYXZDM   JOIN cus_kw_room room ON b.XX_JBXX_ID = room.SCHOOLID  where USER_ID='"+user_id+"' and dwlx=1";
		List list=getSession().createSQLQuery(sql).list();
		return list;
	}
}