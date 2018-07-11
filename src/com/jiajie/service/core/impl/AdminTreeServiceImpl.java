package com.jiajie.service.core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.jiajie.bean.SystemBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.AdminTreeService;
import com.sun.xml.xsom.impl.scd.Iterators.Map;
@Service("treeService")
@SuppressWarnings("all")
public class AdminTreeServiceImpl extends ServiceBridge implements AdminTreeService{

	public Object getAdminRegionTree(HttpServletRequest request) {
		String node = request.getParameter("node");
		String xzqhdm = null;
		if(node==null || !node.equals("root")){
			xzqhdm=node;
		}
		String hql = "select DM,MC from ZD_XZQH ";
		if("root".equals(node)){
			//返回顶级模块列表
			hql = hql + " where dm='ROOT'  ";
		}else if("ROOT".equals(node)){
			//返回省级
			hql = hql + " where substr(dm,3,4)='0000' order by dm ";
			
		}else{
			if(xzqhdm.endsWith("0000")){
				hql =  " select CONCAT(T2.mc,T1.mc) as MC,T1.DM from ZD_XZQH t1 " +
						" left join ZD_XZQH t2 on CONCAT(substr(T1.dm,1,2),'0000')=T2.DM " +
						" where substr(T1.dm,1,2) = '"+xzqhdm.substring(0, 2)+
						"' and substr(T1.dm,5,2)='00' and T1.dm<>'"+xzqhdm+
						"' order by T1.dm ";
			}else if(xzqhdm.endsWith("00")){
				hql = " select CONCAT(t3.mc,T2.mc,t1.mc) as MC,t1.DM from ZD_XZQH  t1 " +
						" left join ZD_XZQH t2 on CONCAT(substr(T1.dm,1,4),'00')=T2.DM " +
						" left join ZD_XZQH t3 on CONCAT(substr(T1.dm,1,2),'0000')=t3.dm " +
						" where substr(T1.dm,1,4) = '"+xzqhdm.substring(0, 4)+"' and T1.dm<>'"+xzqhdm+"'";
			}
		}
		List ls = (List) getListSQL(hql);
		JSONArray array = new JSONArray();
		for(int i=0;i<ls.size();i++){
			HashMap xzqh = (HashMap)ls.get(i);
			JSONObject o = new JSONObject();
			o.put("dm", xzqh.get("DM")==null?"":xzqh.get("DM").toString());
			o.put("text", xzqh.get("MC")==null?"":xzqh.get("MC").toString());
			o.put("id", xzqh.get("DM")==null?"":xzqh.get("DM").toString());
			o.put("leaf", false);
			if(xzqhdm!=null && !xzqhdm.endsWith("0000") && xzqhdm.endsWith("00")){
				o.put("leaf", true);
			}
			array.put(o);
		}
		return array.toString();
	}
	
}
