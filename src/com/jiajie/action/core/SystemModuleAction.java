package com.jiajie.action.core;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import com.jiajie.action.BaseAction;
import com.jiajie.bean.system.TQxglMenuinfo;

@SuppressWarnings("serial")
public class SystemModuleAction extends BaseAction{
	@Autowired
	//加载一级菜单
	public void querySystemRootTree() {		
		HttpSession session = ServletActionContext.getRequest().getSession();
		List<TQxglMenuinfo> menutreeList = (List<TQxglMenuinfo>)session.getAttribute("menutreeList");
		JSONArray array = new JSONArray();
		for (int i = 0; i < menutreeList.size(); i++) {
			String parentId = menutreeList.get(i).getParentid();
			if("00".equals(parentId)) {
				String moduleCode = menutreeList.get(i).getMenucode();
				String moduleName = menutreeList.get(i).getMenuname();
				JSONObject o = new JSONObject();
				o.put("id", moduleCode);
				o.put("title", moduleName);
				o.put("iconCls","p-icons-card");
				array.add(o);
			}
		}
//		for (int i = 0; i < li.size(); i++) {
//			String moduleCode = li.get(i).get("MODULE_CODE").toString();
//			String moduleName = li.get(i).get("MODULE_NAME").toString();
//			JSONObject o = new JSONObject();
//			o.put("id", moduleCode);
//			o.put("title", moduleName);
//			o.put("iconCls","p-icons-card");
//			array.add(o);
//		}
		writerPrint(array.toString());		
	}
	//加载二级菜单
	public void querySystemModule() {
		String node = getRequest().getParameter("node");
		HttpSession session = ServletActionContext.getRequest().getSession();
		List<TQxglMenuinfo> menutreeList = (List<TQxglMenuinfo>)session.getAttribute("menutreeList");
		JSONArray array = new JSONArray();
		for (int i = 0; i < menutreeList.size(); i++) {
			String parentid = menutreeList.get(i).getParentid();
			if(parentid.startsWith(node)) {
				String menuId = menutreeList.get(i).getMenucode();
				String menuName = menutreeList.get(i).getMenuname();
				String moduleCode = menutreeList.get(i).getParentid();
				String scriptURL = menutreeList.get(i).getLinkfile();
				String icon = "";
				boolean leaf = true;
				String isLeaf = "0";
				if(!"0".equals(isLeaf)) {
					leaf = false; 
				}
				JSONObject o = new JSONObject();
				o.put("id", menuId);
				o.put("text", menuName);
				o.put("service", "");
				o.put("moduleid", moduleCode);
				o.put("scriptURL", scriptURL);
				o.put("icon",icon);
				o.put("leaf",leaf);
				array.add(o);
			}
		}
		writerPrint(array.toString());
	}
}
