package com.jiajie.service.basicsinfo.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.basicsinfo.CusKwBuilding;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.dao.DaoSupportImpl;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.basicsinfo.BuildingService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.OrganTree;
import com.jiajie.bean.MsgBean;
@Service("buildingService")
@SuppressWarnings("all")
public class BuildingServiceImpl extends ServiceBridge implements BuildingService {
	
	public PageBean getList(CusKwBuilding building, MBspInfo bspInfo) {
		String sqlKc = "SELECT XX_JBXX_ID from ZXXS_XX_JBXX WHERE DWLX='1' and SSZGJYXZDM='"+building.getSchoolid()+"'";
		List lsKc = getListSQL(sqlKc);
		String schoolid = "";
		if (lsKc.size()>0) {
			Map<String, Object> mp = (Map<String, Object>) lsKc.get(0);
			schoolid = mp.get("XX_JBXX_ID").toString();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("from CusKwBuilding where 0=0 ");
		if(building.getSchoolid() != null && !"".equals(building.getSchoolid())) {
			sb.append(" and schoolid = '"+schoolid+"'");
		}
		if(building.getBuildname() != null && !"".equals(building.getBuildname())) {
			sb.append(" and buildname like binary('%"+building.getBuildname()+"%')");
		}
		sb.append("order by lfid desc");
		return getHQLPageBean(sb.toString());
	}

	public MsgBean saveOrUpdateBuilding(CusKwBuilding building) {
		try {						
			String schoolid = "";
			if (building.getLfid()==null||"".equals(building.getLfid())) {
				String sqlKc = "SELECT XX_JBXX_ID from ZXXS_XX_JBXX WHERE DWLX='1' and SSZGJYXZDM='"+building.getSchoolid()+"'";
				List lsKc = getListSQL(sqlKc);				
				if (lsKc.size()>0) {
					Map<String, Object> mp = (Map<String, Object>) lsKc.get(0);
					schoolid = mp.get("XX_JBXX_ID")==null?"":mp.get("XX_JBXX_ID").toString();
					building.setSchoolid(schoolid);
				}								
			}
			List ls = createHQLQuery("from CusKwBuilding where buildname='"+
					building.getBuildname()+"' and schoolid='"+building.getSchoolid()+"'").list();
			if (ls.size()>0) {				
				if (building.getLfid()==null||"".equals(building.getLfid())) {
					this.getMsgBean(false,"楼房名称重复不能保存！");
				}else {
					CusKwBuilding build = (CusKwBuilding)ls.get(0);
					if (!building.getLfid().equals(build.getLfid())&&
						building.getBuildname().equals(build.getBuildname())) {
						this.getMsgBean(false,"楼房名称重复不能保存！");
					}else {
						getSession().clear();
						saveOrUpdate(building);
						this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
					}
				}				
			}else{
				if ("".equals(building.getSchoolid())) {
					this.getMsgBean(false,"没有设置对应的组考单位不能保存！");
				}else {
					getSession().clear();
					saveOrUpdate(building);
					this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
				}
			}						
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}

	public MsgBean delBuilding(String lfids) {
		try {
			List ls = createHQLQuery("from CusKwRoom where lfid in ("+lfids+")").list();
			if (ls.size()>0) {
				return this.getMsgBean(false,"已存在所属的考场，不能删除！");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("delete from CUS_KW_BUILDING where lf_id in(").append(lfids).append(")");
			return delete(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return this.getMsgBean(false,MsgBean.DEL_ERROR);
		}
	}

	public MsgBean getBuilding(String lfid) {
		try {
			Object obj = get(CusKwBuilding.class, lfid);
			this.getMsgBean(obj);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.GETDATA_ERROR);
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
						//" SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME,2 AS CODETYPE FROM ZXXS_XX_JBXX T1 " +
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
						//" SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME,2 AS CODETYPE FROM ZXXS_XX_JBXX T1 " +
						//" WHERE T1.SSZGJYXZDM='"+parentid+"' and T1.dwlx='1' ";UNION ALL
			}
		/*}else if(("2".equals(bspInfo.getUserType()) && i==0 && (parentid==null || "".equals(parentid))) ||
				orgcode.endsWith("000000") && i==1 || i==2 && orgcode.endsWith("00000000") || orgcode.endsWith("0000000000") && i==3){
			//获取参考单位
			sql = " SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME,2 AS CODETYPE FROM ZXXS_XX_JBXX T1 where T1.dwlx='1'";
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
	//增加人： 包敏 增加时间：    2018-05-18 增加原因: 关联考点
	public MsgBean relateBuilding(CusKwBuilding building) {		
		try{
			String xx_jbxx_id ="";
            String lfid =  building.getLfid().split("_")[1].replace(",", "");
			String schoolid = building.getSchoolid();
			String sql ="SELECT XX_JBXX_ID from ZXXS_XX_JBXX WHERE DWLX='1' and SSZGJYXZDM='" + schoolid + "' ";
			List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			for (Map<String, Object> map : list){
			    xx_jbxx_id = StringUtil.getString(map.get("XX_JBXX_ID"));
			}
			sql= "select * from cus_kw_zk_building where LFID ='" + lfid + "' and SCHOOL_ID_QG ='" + xx_jbxx_id + "'";
			list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			if (list.size() >0){
				this.getMsgBean(true, "关联考点已增加，不可重复增加。");
			}
			else{
				sql ="insert cus_kw_zk_building(ID,LFID,SCHOOL_ID_QG) values( '" + StringUtil.getUUID()   + "','" + lfid + "', '" + xx_jbxx_id +"')";
				this.insert(sql);
				this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
			}
		}catch(Exception ex){
			this.getMsgBean(true,MsgBean.SAVE_ERROR);
		}
		
		return MsgBean;
	}
	//增加人： 包敏 增加时间：    2018-05-18 增加原因: 关联考点

	public MsgBean getrelateBuilding(String schoolid) {
		try{
			String xx_jbxx_id ="";
      
			String sql ="SELECT XX_JBXX_ID from ZXXS_XX_JBXX WHERE DWLX='1' and SSZGJYXZDM='" + schoolid + "' ";
			List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			for (Map<String, Object> map : list){
			    xx_jbxx_id = StringUtil.getString(map.get("XX_JBXX_ID"));
			}
			sql= "select * from cus_kw_zk_building where  SCHOOL_ID_QG ='" + xx_jbxx_id + "'";
			list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			for (Map<String, Object> map : list){
			    CusKwBuilding building  = new CusKwBuilding();
			    building.setLfid(StringUtil.getString(map.get("LFID")));
			    building.setSchoolid(xx_jbxx_id);
			    this.getMsgBean(building);
			}
			
			
		}catch(Exception ex){
			this.getMsgBean(true,MsgBean.SAVE_ERROR);
		}
		
		return MsgBean;
		
	}
}
