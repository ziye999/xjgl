package com.jiajie.service.userRole.impl; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.UserInfo;
import com.jiajie.bean.system.TQxglUserinfo;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.userRole.UserManagerService; 
import com.jiajie.util.StringUtil;

@Service("userManagerService")
public class UserManagerServiceImpl extends ServiceBridge implements UserManagerService{
	public PageBean getUserListPage(String xm, String dlzh,String userType) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.USERCODE,t.USERNAME,t.FLAGE,");
		sql.append("case when t.usertype='3' then '参考单位' when t.usertype='2' then '组考单位' when t.usertype='1' then '系统管理员' end as USERTYPE,");
		sql.append("t.LOGINID,t.EMAIL,case when T.SEX=1 then '男' else '女' end as SEX,t.E2ID,T.SEX AS SEXV,t.WX,");
		sql.append("date_format(t.cdate,'%Y-%m-%d') as CREATETIME,case when t.state='1' then '是' when t.state='0' then '否' end as STATE,");
		sql.append("t.state as STATEV,t.LOGINPWD,date_format(t.qssj,'%Y-%m-%d') as QSSJ,date_format(t.jssj,'%Y-%m-%d') as JSSJ,");
		sql.append("t.SJHM,t.JTDH,V.id AS JGZ,v.text AS JG,t.usertype as USERTYPEV,r.ROLECODE ");
		sql.append("from t_qxgl_userinfo t,t_qxgl_usermapping m,t_qxgl_userrole r,");
		sql.append("(select region_code as id,REGION_EDU as text From V_COM_MEMS_ORGAN union all select xx_jbxx_id as id,xxmc as text From zxxs_xx_jbxx) v ");
		sql.append("where t.usercode = m.user_id ");
		sql.append("and t.usercode = r.usercode ");
		sql.append("and m.organ_code = v.id ");
		if(xm != null && !"".equals(xm)){
			sql.append("and t.username like binary('%").append(xm).append("%') ");
		}
		if(dlzh != null && !"".equals(dlzh)){
			sql.append("and t.loginid like binary('%").append(dlzh).append("%') ");		
		}
		if(userType != null && !"".equals(userType)){
			sql.append("and t.usertype = '").append(userType).append("'");		
		}
		return getSQLPageBean(sql.toString());
	} 
	  
	public Map<String, Object> opUser(UserInfo userInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(!StringUtil.isNotNullOrEmpty(userInfo.getOrgan_sel().trim())){
			result.put("success", false);
			result.put("data","未选择所属单位,不能保存!");
			return result;
		}
		if (!StringUtil.isNotNullOrEmpty(userInfo.getLoginid().trim())||
			!StringUtil.isNotNullOrEmpty(userInfo.getUsername().trim())||
			!StringUtil.isNotNullOrEmpty(userInfo.getLoginpwd().trim())){
			result.put("success", false);
			result.put("data","未填写必填项,不能保存!");
			return result;
		}
		String oc = userInfo.getOrgan_sel().trim();
		Session session = getSession();
		if ("1".equals(userInfo.getUsertype())||"2".equals(userInfo.getUsertype())) {
			int n = Integer.parseInt(session.createSQLQuery("select count(1) as SM from com_mems_organ where region_code='"+oc.split(",")[0].trim()+"'").list().get(0).toString());
			if (n<=0) {
				result.put("success", false);
				result.put("data","选择所属单位不是组考单位,不能保存!");
				return result;
			}
		}else if ("3".equals(userInfo.getUsertype())) {
			int n = Integer.parseInt(session.createSQLQuery("select count(1) as SM from zxxs_xx_jbxx where xx_jbxx_id='"+oc.split(",")[0].trim()+"'").list().get(0).toString());
			if (n<=0) {
				result.put("success", false);
				result.put("data","选择所属单位不是参考单位,不能保存!");
				return result;
			}
		}		
		StringBuffer sql = new StringBuffer(); 
		StringBuffer sql2 =  new StringBuffer(); 
		StringBuffer sql3 =  new StringBuffer(); 
		String uc = userInfo.getUsercode(); 
		if(uc!=null && !"".equals(uc)){
			int n = Integer.parseInt(session.createSQLQuery("select count(1) from t_qxgl_userinfo where usercode != '"+uc+"' and loginid = '"+userInfo.getLoginid()+"'").list().get(0).toString());
			if(n>0){
				result.put("success", false);
				result.put("data","登录账号已被其他用户注册,请重新填写!");
				return result;
			}
			String password = session.createSQLQuery("select loginpwd from t_qxgl_userinfo where usercode='"+uc+"'").list().get(0).toString();
			sql.append("update t_qxgl_userinfo t set t.EMAIL='").append(userInfo.getEmail());
			sql.append("',t.WX='").append(userInfo.getWx()).append("',t.JTDH='").append(userInfo.getJtdh());
			sql.append("',t.SJHM='").append(userInfo.getSjhm()).append("',t.JSSJ=STR_TO_DATE('").append(userInfo.getJssj());
			sql.append("','%Y-%m-%d'),t.QSSJ=STR_TO_DATE('").append(userInfo.getQssj());
			sql.append("','%Y-%m-%d'),t.STATE='").append(userInfo.getState().equals("是")?"1":"0");
			sql.append("',t.SEX='").append(userInfo.getSex().equals("男")?"1":"2").append("',t.E2ID='").append(userInfo.getE2id());
			sql.append("',t.LOGINID='").append(userInfo.getLoginid()).append("',t.USERNAME='").append(userInfo.getUsername()).append("'");
			sql.append(",t.usertype='").append(userInfo.getUsertype()).append("'");
			if(!userInfo.getLoginpwd().equals(password)){
				sql.append(",t.loginpwd='").append(new Md5Hash(userInfo.getLoginpwd()).toString()).append("'");
			}			
			String rolecode = userInfo.getRolem().trim();
			if(oc!=null && !"".equals(oc)){
				int count = Integer.parseInt(getSession().createSQLQuery("select count(1) from V_COM_MEMS_ORGAN v where v.REGION_CODE in ('"
						+oc.replaceAll(" ", "").replaceAll(",", "','")+"')").list().get(0).toString());
				sql2.append("update t_qxgl_usermapping set user_type='").append(userInfo.getUsertype())
				.append("',organ_code='")
				.append("".equals(oc.split(",")[0].trim())?oc.replaceAll(" ", "").replaceAll(",", ""):oc.split(",")[0].trim())
				.append("' where user_id='").append(uc).append("'");				
			}
			if(rolecode!=null && !"".equals(rolecode)){
				sql3.append("update T_QXGL_USERROLE set rolecode='").append(rolecode)
				.append("' where usercode='").append(uc).append("'");
			}
			sql.append(" where t.usercode='").append(uc).append("'");
		}else{ 
			int n = Integer.parseInt(session.createSQLQuery("select count(1) from t_qxgl_userinfo where loginid='"+userInfo.getLoginid()+"'").list().get(0).toString());
			if(n>0){
				result.put("success", false);
				result.put("data","登录账号已被其他用户注册,请重新填写!");
				return result;
			}
			String usercode = UUID.randomUUID().toString().replace("-","");
			String userroleid = UUID.randomUUID().toString().replace("-","");			
			String rolecode = userInfo.getRolem().trim();
			sql.append("insert into t_qxgl_userinfo(USERCODE,USERNAME,USERTYPE,LOGINID,LOGINPWD,E2ID ,SEX,STATE,CDATE," +
					"QSSJ,JSSJ,SJHM ,JTDH,WX,EMAIL,FLAGE) ");
			int count = Integer.parseInt(session.createSQLQuery("select count(1) from V_COM_MEMS_ORGAN v where v.REGION_CODE in ('"+
					oc.replaceAll(" ", "").replaceAll(",", "','")+"')").list().get(0).toString());
			sql.append(" values('").append(usercode).append("','").append(userInfo.getUsername()).append("','").append(userInfo.getUsertype()).append("','");
			sql.append(userInfo.getLoginid()).append("','").append(new Md5Hash(userInfo.getLoginpwd()).toString()).append("','").append(userInfo.getE2id()).append("','");
			sql.append(userInfo.getSex().equals("男")?"1":"2").append("','").append(userInfo.getState().equals("是")?"1":"0").append("',now(),STR_TO_DATE('");
			sql.append(userInfo.getQssj()).append("','%Y-%m-%d'),STR_TO_DATE('").append(userInfo.getJssj()).append("','%Y-%m-%d'),'").append(userInfo.getSjhm()).append("','");
			sql.append(userInfo.getJtdh()).append("','").append(userInfo.getWx()).append("','").append(userInfo.getEmail()).append("','1')");
			sql2.append("insert into t_qxgl_usermapping(user_id,user_type,ORGAN_CODE) values('").append(usercode).append("','").append(userInfo.getUsertype()).append("','")
			.append("".equals(oc.split(",")[0].trim())?oc.replaceAll(" ", "").replaceAll(",", ""):oc.split(",")[0].trim()).append("')");
			sql3.append("insert into T_QXGL_USERROLE(usercode,rolecode,userroleid) values('").append(usercode).append("','").append(rolecode).append("','").append(userroleid).append("')");
		}
		int count = session.createSQLQuery(sql.toString()).executeUpdate();
		if(count==1){
			if(!"".equals(sql2.toString().trim())){
				int n = session.createSQLQuery(sql2.toString()).executeUpdate();
				if(n!=1){
					try {
						throw new Exception("操作用户失败!");
					} catch (Exception e) {
						result.put("success",false);
						result.put("data",e.getMessage());
						System.err.println(e.getMessage());
					}
				}
			}
			if(!"".equals(sql3.toString().trim())){
				session.createSQLQuery(sql3.toString()).executeUpdate();
			}
			result.put("success",true);
			result.put("data","操作用户成功!");
		}else{
			try {
				throw new Exception("操作用户失败!");
			} catch (Exception e) {
				result.put("success",false);
				result.put("data",e.getMessage());
				System.err.println(e.getMessage());
			}
		} 
		return result;
	}

	public String deleteUsers(String users) { 
		users = users.replace(" ","").replace(",", "','");
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_qxgl_usermapping where user_id in ('").append(users).append("')");
		int count = getSession().createSQLQuery(sql.toString()).executeUpdate();
		if(count>0){
			sql.delete(0,sql.length());
			sql.append("delete from t_qxgl_userinfo where usercode in ('").append(users).append("')");
			getSession().createSQLQuery(sql.toString()).executeUpdate();
			
			sql.delete(0,sql.length());
			sql.append("delete from T_QXGL_USERROLE where usercode in ('").append(users).append("')");
			getSession().createSQLQuery(sql.toString()).executeUpdate();
		}
		return "删除成功!";
	} 
	
	public MsgBean updatePassword(String users,String oldpass,String newpass1,String newpass2) {
		oldpass = oldpass.split(",")[0];
		newpass1 = newpass1.split(",")[0];
		newpass2 = newpass2.split(",")[0];
		if (newpass1.equals(newpass2)) {
			List<TQxglUserinfo> list = getSession().createQuery("FROM TQxglUserinfo t WHERE t.usercode = '"+users+"'").list();
			if(list != null && list.size() > 0) {
				TQxglUserinfo user = (TQxglUserinfo)list.get(0);
				String loginid = user.getLoginid();
				String loginpass = user.getLoginpwd();
				
				if(oldpass.length() < 32) {
					oldpass = new Md5Hash(loginid,oldpass).toString();
				}
				if(newpass1.length() < 32) {
					newpass1 = new Md5Hash(loginid,newpass1).toString();
				}
				if (loginpass.equals(oldpass)) {
					getSession().createSQLQuery("update t_qxgl_userinfo set loginpwd='"+newpass1+
							"' where usercode='"+users+
							"' and loginpwd='"+oldpass+"'").executeUpdate();
					return this.getMsgBean(true,"密码修改成功!");
				}else {
					return this.getMsgBean(false,"输入旧密码不对，不能修改!");
				}
			}else {
				return this.getMsgBean(false,"用户信息有误，不能修改!");
			}
		}else {
			return this.getMsgBean(false,"两次输入的新密码不一致，不能修改!");
		}				
	}  
	 
}
