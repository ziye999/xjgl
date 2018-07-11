package com.jiajie.service.sysManagement.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.system.TQxglMenuinfo;
import com.jiajie.bean.system.TQxglRolemenu;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.sysManagement.RoleinfoglService;
import com.jiajie.util.StringUtil;
@Service("roleinfoglService")
@SuppressWarnings("all")
public class RoleinfoglServiceImpl extends ServiceBridge implements RoleinfoglService {

	public PageBean getList() {
		String sql = "select ROLECODE,ROLENAME,MENO,STATE,"+
			 		"case when STATE='0' then '不可用' when STATE='1' then '可用' end ZT "+
			 		"from t_qxgl_roleinfo";
		return getSQLPageBean(sql);
	}

	public String delRoleinfo(String rolecode) {
		String rtn = "";
		List lsInfo = this.createSQLQuery("select * from T_QXGL_USERROLE where rolecode='"+rolecode+"'").list();
		if (lsInfo.size()>0) {
			rtn = "正在使用的角色不能删除！";
		}else{
			try {			
				String sql = "delete from t_qxgl_rolemenu where rolecode='"+rolecode+"'";
				delete(sql);
				sql = "delete from t_qxgl_roleinfo where rolecode='"+rolecode+"'";
				delete(sql);
				rtn = "删除成功！";
			} catch (Exception e) {
				e.printStackTrace();
				rtn = "删除失败！";
			}
		}		
		return rtn;
	}

	public com.jiajie.bean.MsgBean updateRole(String rolecode, String rolename,
			String meno, String state, String bloor) {
		try {
			String sql = "";
			if(StringUtil.isNotNullOrEmpty(rolecode.trim())){
				if("0".equals(bloor)){
					sql += "update t_qxgl_roleinfo set meno='"+meno+"',state='"+state+"' where rolecode='"+rolecode+"'";
				}else{
					List ls = findRolename(rolename);
					if(ls!=null&&ls.size()>0){
						this.getMsgBean(false,"角色名称已存在！");
						return MsgBean;
					}
					sql += "update t_qxgl_roleinfo set rolename='"+rolename+"',meno='"+meno+"',state='"+state+"' where rolecode='"+rolecode+"'";
				}
				update(sql);
				this.getMsgBean(true,"修改成功！");
			}else {
				if (StringUtil.isNotNullOrEmpty(rolename.trim())&&StringUtil.isNotNullOrEmpty(meno.trim())){
					List ls = findRolename(rolename);
					if(ls!=null&&ls.size()>0){
						this.getMsgBean(false,"角色名称已存在！");
						return MsgBean;
					}
					rolecode = getRoleCode();
					sql = "insert into t_qxgl_roleinfo(rolecode,rolename,meno,state) values('"+rolecode+"','"+rolename+"','"+meno+"','"+state+"')";
					this.update(sql);
					this.getMsgBean(true,"增加成功！");
				}else {
					this.getMsgBean(false,"空白内容不能保存！");
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"修改失败！");
		}
		return MsgBean;
	}
	
	public List findRolename(String rolename){
		String sql = "select * from t_qxgl_roleinfo where rolename='"+rolename+"'";
		return getListSQL(sql); 
	}
	
	private String getRoleCode(){
		String rolecode = UUID.randomUUID().toString().replace("-","");		
		return rolecode;
	}

	public Object getRoleTree(String rolecode) {
		String sql = "select distinct mu.MENUCODE,mu.MENUNAME,mu.PARENTID,rm.ROLECODE "+
				"from t_qxgl_menuinfo mu "+
				"left join t_qxgl_rolemenu rm on rm.menucode=mu.menucode and rm.rolecode='"+
				rolecode+"' order by mu.MENUCODE asc";
		return getListSQL(sql);
	}

	public com.jiajie.bean.MsgBean addRoleMenu(String rolecode, String menucode) {
		String[] str = menucode.split(",");
		findRoleMenu(rolecode);
		try {
			for(int i=0;i<str.length;i++){
				TQxglRolemenu rolemenu = new TQxglRolemenu();
				rolemenu.setMenucode(str[i]);
				rolemenu.setRolecode(rolecode);
				saveOrUpdate(rolemenu);
			}
		this.getMsgBean(true,"授权成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"授权失败！");
		}
		return MsgBean;
	}
	public void findRoleMenu(String rolecode){
		String sql = "delete from t_qxgl_rolemenu where rolecode='"+rolecode+"'";
		delete(sql);
	}
}
