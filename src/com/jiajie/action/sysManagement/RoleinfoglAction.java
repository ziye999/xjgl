package com.jiajie.action.sysManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.system.TQxglMenuinfo;
import com.jiajie.service.sysManagement.RoleinfoglService;
import com.jiajie.util.FunctionMenu;

@SuppressWarnings("serial")
public class RoleinfoglAction extends BaseAction{
	private String rolecode;
	private String rolename;
	private String menucode;
	private String meno;
	private String state;
	private String bloor;
	
	@Autowired
	private RoleinfoglService service;

	public void getListPage(){
		writerPrint(service.getList());
	}
	
	public void delRole(){
		writerPrint(service.delRoleinfo(rolecode));
	}
	
	public void update(){
		writerPrint(service.updateRole(rolecode, rolename, meno, state, bloor)); 
	}
	
	public void getMenuinfoTree(){
		List<Map<String,Object>> list = (List<Map<String, Object>>) service.getRoleTree(rolecode);
		List<TQxglMenuinfo> menuinfos = new ArrayList<TQxglMenuinfo>();
		for(int i=0;i<1;i++){
			TQxglMenuinfo menuinfo = new TQxglMenuinfo();
			if(rolecode.equals(list.get(i).get("ROLECODE")==null?"":list.get(i).get("ROLECODE").toString())){
				menuinfo.setChecked("true");
			}else{
				menuinfo.setChecked("false");
			}
			menuinfo.setId(list.get(i).get("MENUCODE").toString());
			menuinfo.setText(list.get(i).get("MENUNAME").toString());
			menuinfo.setMenucode(list.get(i).get("ROLECODE")==null?"":list.get(i).get("ROLECODE").toString());
			List<TQxglMenuinfo> childrenList = packageRoleTree(list,list.get(i).get("MENUCODE").toString());
			if(childrenList == null || childrenList.size() <= 0) {
				menuinfo.setLeaf("true");
			}
			menuinfo.setChildren(childrenList);
			menuinfos.add(menuinfo);
		}
		try {
			getResponse().getWriter().write(new Gson().toJson(menuinfos).replaceAll("\"", "'").replaceAll("'false'", "false").replaceAll("'true'", "true"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<TQxglMenuinfo> packageRoleTree(List<Map<String, Object>> list,String parentid){
		List<TQxglMenuinfo> menuinfos = new ArrayList<TQxglMenuinfo>();
		for(int i=0;i<list.size();i++){
			String parent = list.get(i).get("PARENTID").toString();
			if(parent.equals(parentid)){
				String name = list.get(i).get("MENUNAME").toString();
				TQxglMenuinfo menuinfo = new TQxglMenuinfo();
				if(rolecode.equals(list.get(i).get("ROLECODE")==null?"":list.get(i).get("ROLECODE").toString())){
					menuinfo.setChecked("true");
				}else{
					menuinfo.setChecked("false");
				}
				menuinfo.setText(name);
				menuinfo.setId(list.get(i).get("MENUCODE").toString());
				menuinfo.setMenucode(list.get(i).get("ROLECODE")==null?"":list.get(i).get("ROLECODE").toString());
				List<TQxglMenuinfo> childrenList = packageRoleTree(list,list.get(i).get("MENUCODE").toString());
				if(childrenList == null || childrenList.size() <= 0) {
					menuinfo.setLeaf("true");
				}
				menuinfo.setChildren(childrenList);
				menuinfos.add(menuinfo);
			}
		}
		return menuinfos;
	}
	
	public void addRoleMenu(){
		writerPrint(service.addRoleMenu(rolecode, menucode));
		HttpServletRequest request = getRequest();
		ApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		// 加载菜单到缓存
		FunctionMenu.getMenuInstance().initMenuInfoToCache(wac);
		// 加载权限组到缓存		
		FunctionMenu.getMenuInstance().initRoleInfoToCache(wac);
		// 加载组菜单到缓存		
		FunctionMenu.getMenuInstance().initRoleMenuToCache(wac);
		// 加载用户组到缓存		
		FunctionMenu.getMenuInstance().initUserRoleToCache(wac);
	}
	
	public RoleinfoglService getService() {
		return service;
	}

	public void setService(RoleinfoglService service) {
		this.service = service;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBloor() {
		return bloor;
	}

	public void setBloor(String bloor) {
		this.bloor = bloor;
	}

	public String getMenucode() {
		return menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}
}
