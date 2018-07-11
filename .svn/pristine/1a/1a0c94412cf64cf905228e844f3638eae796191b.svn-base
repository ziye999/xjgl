package com.jiajie.service.zzjg.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.basicsinfo.CusKwBuilding;
import com.jiajie.bean.basicsinfo.CusKwRoom;
import com.jiajie.bean.zzjg.CusKwZkdw;
import com.jiajie.dao.DaoSupportImpl;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.basicsinfo.BuildingService;
import com.jiajie.service.zzjg.ZkdwService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.OrganTree;
import com.jiajie.bean.MsgBean;
@Service("zkdwService")
@SuppressWarnings("all")
public class ZkdwServiceImpl extends ServiceBridge implements ZkdwService {
	
	public PageBean getList(CusKwZkdw zkdw, MBspInfo bspInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select t1.REGION_CODE as REGIONCODE,t1.REGION_EDU as REGIONEDU,");
		sb.append("t1.REGION_NAME as REGIONNAME,t1.REGION_SHORT as REGIONSHORT,");
		sb.append("t1.REGION_LEVEL as REGIONLEVEL,t1.AREA_TYPE as AREATYPE,");
		sb.append("t1.in_use as INUSEM,t2.mc as INUSE,t1.PARENT_CODE as PARENTCODE ");
		sb.append("from COM_MEMS_ORGAN t1 ");
		sb.append("left join ZD_SFBZ t2 on t1.IN_USE=t2.dm where 0=0 ");
		if(zkdw.getRegioncode() != null && !"".equals(zkdw.getRegioncode())) {
			sb.append(" and t1.PARENT_CODE='"+zkdw.getRegioncode()+"'");
		}
		if(zkdw.getRegionedu() != null && !"".equals(zkdw.getRegionedu())) {
			sb.append(" and t1.REGION_EDU like binary('%"+zkdw.getRegionedu()+"%')");
		}
		if(zkdw.getRegionname() != null && !"".equals(zkdw.getRegionname())) {
			sb.append(" and t1.REGION_NAME like binary('%"+zkdw.getRegionname()+"%')");
		}
		sb.append(" order by t1.REGION_CODE desc");
		return getSQLPageBean(sb.toString());		
	}

	public MsgBean saveOrUpdateZkdw(CusKwZkdw zkdw) {
		try {			
			if (StringUtil.isNotNullOrEmpty(zkdw.getRegioncode().trim())&&StringUtil.isNotNullOrEmpty(zkdw.getRegionedu().trim())&&
				StringUtil.isNotNullOrEmpty(zkdw.getRegionname().trim())&&StringUtil.isNotNullOrEmpty(zkdw.getRegionshort().trim())) {				
				List ls = createHQLQuery("from CusKwZkdw where regioncode='"+zkdw.getRegioncode()+"'").list();
				if (ls.size()>0) {
					update("update COM_MEMS_ORGAN set region_edu='"+zkdw.getRegionedu()+"'"+						
							(zkdw.getRegionname()==null||"".equals(zkdw.getRegionname())?"":",region_name='"+zkdw.getRegionname()+"'")+
							(zkdw.getRegionshort()==null||"".equals(zkdw.getRegionshort())?"":",region_short='"+zkdw.getRegionshort()+"'")+
							(zkdw.getRegionlevel()==null||"".equals(zkdw.getRegionlevel())?"":",region_level='"+zkdw.getRegionlevel()+"'")+
							(zkdw.getAreatype()==null||"".equals(zkdw.getAreatype())?"":",area_type='"+zkdw.getAreatype()+"'")+
							(zkdw.getInuse()==null||"".equals(zkdw.getInuse())?"":",in_use='"+zkdw.getInuse()+"'")+
							" where REGION_CODE='"+zkdw.getRegioncode()+"'");
				}else {
					List lsInfo = this.createSQLQuery("select * from COM_MEMS_ORGAN where REGION_CODE ='"+zkdw.getRegioncode()+"'").list();
					if (lsInfo.size()>0) {
						this.getMsgBean(false,"重复的组考单位内容不能保存！");
					}else{
						SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd HH:mm:ss",Locale.CHINA);
						update("insert into COM_MEMS_ORGAN (REGION_EDU, REGION_NAME, REGION_SHORT, REGION_LEVEL, "+
								"PARENT_CODE, AREA_TYPE, IN_USE, SJC, REGION_CODE) values ('"+zkdw.getRegionedu()+
								"', '"+zkdw.getRegionname()+"', '"+zkdw.getRegionshort()+"', '"+zkdw.getRegionlevel()+
								"', '"+zkdw.getParentcode()+"', '"+zkdw.getAreatype()+"', '"+zkdw.getInuse()+
								"', '"+(format.format(new java.util.Date()))+"', '"+zkdw.getRegioncode()+"')");
						update("insert into zxxs_xx_jbxx (XX_JBXX_ID,XXBSM,XXMC,XXDZ,SSZGJYXZDM,SSZGJYXZMC,DWLX) values ('"+
								StringUtil.getUUID()+"', '"+zkdw.getRegioncode()+"_01', '"+zkdw.getRegionedu()+"_组考', '"+
								zkdw.getRegionname()+"', '"+zkdw.getRegioncode()+"', '"+zkdw.getRegionname()+"', '1')");
					}									
				}			
				this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
			}else {
				this.getMsgBean(false,"输入格式有误，不能保存！");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}

	public MsgBean delZkdw(String regioncodes) {
		try {
			List ls = createHQLQuery("from CusKwCkdw where sszgjyxzdm in ("+regioncodes+")").list();
			if (ls.size()>0) {
				return this.getMsgBean(false,"已存在下属的组考或参考单位，不能删除！");
			}
			List lsU = createSQLQuery("select * from t_qxgl_usermapping where ORGAN_CODE in ("+regioncodes+")").list();
			if (lsU.size()>0) {
				return this.getMsgBean(false,"已存在所属的用户，不能删除！");
			}
			delete("delete from zxxs_xx_jbxx where SSZGJYXZDM in ("+regioncodes+")");
			StringBuffer sb = new StringBuffer();
			sb.append("delete from COM_MEMS_ORGAN where REGION_CODE in(").append(regioncodes).append(")");
			return delete(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return this.getMsgBean(false,MsgBean.DEL_ERROR);
		}
	}

	public MsgBean getZkdw(String regioncode) {
		try {			
			Object obj = get(CusKwZkdw.class, regioncode);
			this.getMsgBean(obj);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.GETDATA_ERROR);
		}
		return MsgBean;
	}

	public Object getPZkdw(MBspInfo bspInfo) {		
		/*List ls = createHQLQuery("from CusKwZkdw where regioncode in (select parentcode from CusKwZkdw) order by regioncode").list();
		JSONArray ary = new JSONArray();
		for(int i=0;i<ls.size();i++){
			CusKwZkdw build = (CusKwZkdw)ls.get(i);
			JSONObject o = new JSONObject();
			o.put("id", build.getRegioncode());
			o.put("code", "");
			o.put("text", build.getRegionedu());
			o.put("leaf", new Boolean(true));
			ary.add(o);
		}*/
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
			sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN WHERE REGION_CODE='"+orgcode+"' ORDER BY REGION_CODE ";
		}else if(i==1 && orgcode.endsWith("0000000000") || i==0 && orgcode.endsWith("00000000")){
			//获取市级组考单位
			if(orgcode.endsWith("00000000") && i==0){
				sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN WHERE REGION_CODE='"+orgcode+"' ";
			}else{
				sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN " +
						" WHERE PARENT_CODE='"+parentid+"'";
			}
		}else if(orgcode.endsWith("000000") && i==0 || i==1 && orgcode.endsWith("00000000") || orgcode.endsWith("0000000000") && i==2 ){
			//获取县级组考单位
			if(orgcode.endsWith("000000") && i==0){
				sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,2 AS CODETYPE FROM COM_MEMS_ORGAN WHERE REGION_CODE='"+orgcode+"' ";
			}else{
				sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,2 AS CODETYPE FROM COM_MEMS_ORGAN " +
						" WHERE PARENT_CODE='"+parentid+"'";
			}
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
	
	public Object checkZkdwcodeRepeat(String regioncode) {
		boolean flag = true;
		StringBuffer sb = new StringBuffer();
		sb.append("select * from COM_MEMS_ORGAN where REGION_CODE = '").append(regioncode).append("'");
		PageBean pageBean = getSQLPageBean(sb.toString());
		List resultList = pageBean.getResultList();
		if(resultList != null && resultList.size() > 0) {
			flag = false;
		}
		return String.valueOf(flag);
	}

	public MsgBean loadZkdw(String regioncode) {
		try {
			Object obj = get(CusKwZkdw.class, regioncode);
			StringBuffer sb = new StringBuffer();
			sb.append("from CusKwZkdw where regioncode='"+regioncode+"' ");
			List resultList = getHQLPageBean(sb.toString()).getResultList();
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
