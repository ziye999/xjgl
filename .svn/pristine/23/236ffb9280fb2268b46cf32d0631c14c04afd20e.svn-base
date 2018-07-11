package com.jiajie.service.basicsinfo.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.basicsinfo.CusKwBuilding;
import com.jiajie.bean.basicsinfo.CusKwRoom;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.basicsinfo.RoomService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.OrganTree;
@Service("roomService")
@SuppressWarnings("all")
public class RoomServiceImpl extends ServiceBridge implements RoomService {
		
	public PageBean getList(CusKwRoom room, MBspInfo bspInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select t1.FJID,t1.ROOMCODE,t1.ROOMNAME,t1.LFID,t1.FLOOR,t1.SEATS,t1.TYPE_M,t1.SCHOOLID,t2.mc AS TYPENAME ");
		sb.append("from CUS_KW_ROOM t1 ");
		sb.append("left join ZD_JSLX t2 on t1.type_m = t2.dm ");
		sb.append("where 0=0 ");
		if(room.getSchoolid() != null && !"".equals(room.getSchoolid())) {
			sb.append("and t1.schoolid='"+room.getSchoolid()+"' ");
		}
		if(room.getLfid() != null && !"".equals(room.getLfid())) {
			sb.append("and t1.lfid='"+room.getLfid().split("_")[1]+"' ");
		}
		if(room.getRoomname() != null && !"".equals(room.getRoomname())) {
			sb.append("and t1.roomname like binary('%"+room.getRoomname()+"%') ");
		}
		if(room.getFloor() != null && !"".equals(room.getFloor())) {
			sb.append("and t1.floor like binary('%"+room.getFloor()+"%') ");
		}
		sb.append("order by t1.fjid desc");
		return getSQLPageBean(sb.toString());
	}

	public MsgBean saveOrUpdateRoom(CusKwRoom room) {
		try {
			if (room.getLfid().indexOf("_")>0) {
				room.setLfid(room.getLfid().split("_")[1]);
			}			
			List lst = createHQLQuery("from CusKwRoom where roomname='"+room.getRoomname()+"'").list();			
			String sql = "select SCHOOL_ID_QG from CUS_KW_BUILDING where lf_id='"+room.getLfid()+"'";
			List ls = getListSQL(sql);
			String schoolid = "";
			if (ls.size()>0) {
				Map result = (Map) ls.get(0);
				schoolid = result.get("SCHOOL_ID_QG")==null?"":result.get("SCHOOL_ID_QG").toString();
				room.setSchoolid(schoolid);
			}			
			if (room.getTypeM()==null||"".equals(room.getTypeM())) {
				room.setTypeM("1");
			}
			if ("".equals(schoolid)) {
				this.getMsgBean(false,"没有设置对应的考点不能保存！");				
			}else {
				getSession().clear();	
				if (lst.size()>0) {
					if (room.getFjid()==null||"".equals(room.getFjid())) {
						room.setFjid(UUID.randomUUID().toString().replace("-",""));
						save(room);
					}else {					
						update(room);
					}
				}else{				
					saveOrUpdate(room);				
				}
				this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}

	public MsgBean delRoom(String lfids) {
		try {
			List ls = getListSQL("SELECT * from CUS_KW_EXAMROOM where ROOMID in ("+lfids+")");
			if (ls.size()>0) {
				return this.getMsgBean(false,"已存在安排的考试，不能删除！");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("delete from CUS_KW_ROOM where fjid in(").append(lfids).append(")");
			return delete(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return this.getMsgBean(false,MsgBean.DEL_ERROR);
		}
	}

	public Object getBuilding(MBspInfo bspInfo) {
		/*String sql = "from CusKwBuilding where schoolid='"+bspInfo.getOrgan().getOrganCode()+"' order by buildname";
		List ls = createHQLQuery(sql).list();
		JSONArray ary = new JSONArray();
		if (ls==null||ls.size()<=0) {
			sql = "from CusKwBuilding where schoolid in (select xxjbxxid from CusKwCkdw where sszgjyxzdm='"+bspInfo.getOrgan().getOrganCode()+"') order by buildname";
			ls = createHQLQuery(sql).list();
			if (ls==null||ls.size()<=0) {
				sql = "from CusKwBuilding where schoolid in (select xxjbxxid from CusKwCkdw where sszgjyxzdm in (select regioncode from CusKwZkdw where parentcode='"+bspInfo.getOrgan().getOrganCode()+"')) order by buildname";
				ls = createHQLQuery(sql).list();
				if (ls==null||ls.size()<=0) {
					sql = "from CusKwBuilding where schoolid in (select xxjbxxid from CusKwCkdw where sszgjyxzdm in (select regioncode from CusKwZkdw where parentcode in (select regioncode from CusKwZkdw where parentcode='"+bspInfo.getOrgan().getOrganCode()+"'))) order by buildname";
					ls = createHQLQuery(sql).list();
				}
			}
		}
		for(int i=0;i<ls.size();i++){
			CusKwBuilding build = (CusKwBuilding)ls.get(i);
			JSONObject o = new JSONObject();
			o.put("id", build.getLfid());
			o.put("code", "");
			o.put("text", build.getBuildname());
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
						"WHERE a.PARENT_CODE='"+parentid+"' UNION ALL "+
						"SELECT distinct CONCAT(T2.XX_JBXX_ID,'_',T1.LF_ID) AS CODEID,T1.BUILDNAME AS CODENAME,3 AS CODETYPE"+
						" FROM cus_kw_building T1"+
						" LEFT JOIN ZXXS_XX_JBXX T2 ON T1.SCHOOL_ID_QG=T2.XX_JBXX_ID"+
						" LEFT JOIN COM_MEMS_ORGAN T3 ON T3.REGION_CODE=T2.SSZGJYXZDM"+
						" WHERE T2.dwlx='1' and T3.REGION_CODE='"+parentid+"'";
						//"SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME,1 AS CODETYPE FROM ZXXS_XX_JBXX T1 " +
						//"WHERE T1.dwlx='1' and T1.SSZGJYXZDM='"+parentid+"' ";UNION ALL 
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
						"WHERE a.PARENT_CODE='"+parentid+"' UNION ALL "+
						"SELECT distinct CONCAT(T2.XX_JBXX_ID,'_',T1.LF_ID) AS CODEID,T1.BUILDNAME AS CODENAME,3 AS CODETYPE"+
						" FROM cus_kw_building T1"+
						" LEFT JOIN ZXXS_XX_JBXX T2 ON T1.SCHOOL_ID_QG=T2.XX_JBXX_ID"+
						" LEFT JOIN COM_MEMS_ORGAN T3 ON T3.REGION_CODE=T2.SSZGJYXZDM"+
						" WHERE T2.dwlx='1' and T3.REGION_CODE='"+parentid+"'";
						//"SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME,1 AS CODETYPE FROM ZXXS_XX_JBXX T1 " +
						//"WHERE T1.dwlx='1' and T1.SSZGJYXZDM='"+parentid+"' ";UNION ALL
			}
		/*}else if(("2".equals(bspInfo.getUserType()) && i==0 && (parentid==null || "".equals(parentid))) ||
				orgcode.endsWith("000000") && i==1 || i==2 && orgcode.endsWith("00000000") || orgcode.endsWith("0000000000") && i==3){
			//获取参考单位
			sql = " SELECT distinct T1.XX_JBXX_ID AS CODEID,T1.XXMC AS CODENAME,2 AS CODETYPE FROM ZXXS_XX_JBXX T1 WHERE T1.dwlx='1' ";
			if("1".equals(bspInfo.getUserType())){
				sql += " and T1.SSZGJYXZDM='"+parentid+"' ";
			}else if("2".equals(bspInfo.getUserType())){
				sql += " and T1.XX_JBXX_ID='"+orgcode+"' ";
			}*/
		}else if(("2".equals(bspInfo.getUserType()) && i==0 && (parentid==null || "".equals(parentid))) || 
				("2".equals(bspInfo.getUserType()) && i==1) ||
				orgcode.endsWith("000000") && i==2 || i==3 && orgcode.endsWith("00000000") || orgcode.endsWith("0000000000") && i==4){
			//获取考点
			sql = " SELECT distinct CONCAT(T2.XX_JBXX_ID,'_',T1.LF_ID) AS CODEID,T1.BUILDNAME AS CODENAME,3 AS CODETYPE"+
					" FROM cus_kw_building T1"+
					" LEFT JOIN ZXXS_XX_JBXX T2 ON T1.SCHOOL_ID_QG=T2.XX_JBXX_ID"+
					" LEFT JOIN COM_MEMS_ORGAN T3 ON T3.REGION_CODE=T2.SSZGJYXZDM"+
					" WHERE T2.dwlx='1' and T3.REGION_CODE='"+parentid+"'";	
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
			if(tree.getIndex()!=3){
				tree.setLeaf("false");
			}else{
				tree.setLeaf("true");
			}
			array.add(tree);
		}
		return array;
	}
	
	public Object checkRoomcodeRepeat(String roomcode) {
		boolean flag = true;
		StringBuffer sb = new StringBuffer();
		sb.append("select * from CUS_KW_ROOM where roomcode = '").append(roomcode).append("'");
		PageBean pageBean = getSQLPageBean(sb.toString());
		List resultList = pageBean.getResultList();
		if(resultList != null && resultList.size() > 0) {
			flag = false;
		}
		return String.valueOf(flag);
	}
	
	public MsgBean loadRoom(String fjid) {
		try {
			Object obj = get(CusKwBuilding.class, fjid);
			StringBuffer sb = new StringBuffer();
			sb.append("select t1.FJID,t1.ROOMCODE,t1.ROOMNAME,t1.LFID,t1.FLOOR,t1.SEATS,t1.TYPE_M,t1.SCHOOLID,t2.mc AS TYPENAME,t3.BUILDNAME ");
			sb.append("from CUS_KW_ROOM t1 ");
			sb.append("left join ZD_JSLX t2 on t1.type_m = t2.dm ");
			sb.append("left join CUS_KW_BUILDING t3 on t1.lfid = t3.lf_id ");
			sb.append("where t1.fjid='"+fjid+"' ");
			List resultList = getSQLPageBean(sb.toString()).getResultList();
			if(resultList != null && resultList.size() > 0) {
				this.getMsgBean(resultList.get(0));
			}else {
				this.getMsgBean(false,MsgBean.GETDATA_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.GETDATA_ERROR);
		}
		return MsgBean;
	}

}
