package com.jiajie.service.core.impl;
/**
 * 对话框提醒
 * **/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
 


import org.springframework.stereotype.Service;

import com.jiajie.bean.MsgBean;
import com.jiajie.service.ServiceBridge; 
import com.jiajie.service.core.MessageBoxService; 
import com.jiajie.util.bean.MBspInfo;
@Service("messageBoxService")
@SuppressWarnings("all")
public class MessageBoxServiceImpl extends ServiceBridge implements MessageBoxService{
	private final static String TITLE="title";
	private final static String CONTENT="content";
	
	public Map<String,Object> findMENUbyID(String id){
		return (Map<String, Object>) this.getSQLUniqueResult("select MENUCODE,PARENTID from T_QXGL_MENUINFO where MENUCODE='"+id+"'",new Object[]{});
	}
	/**
	 * 肄业申请审核提醒
	 * @param bspInfo
	 * @return
	 */
	public Map<String,Object> getYiyeBox(MBspInfo bspInfo){
		List list = this.getListSQL("select t3.YYID,t3.XJH,t3.XM,t3.REASON,t3.CDATE,t3.MEMO,t3.FILEPATH,t3.CJR,t3.FLAG"+
				" from ZXXS_XS_JBXX t2,CUS_XJ_BREAKSTUDY t3"+
				" where T2.GRBSM=t3.XJH and t3.FLAG='0' and (t2.XXSSZGJYXZDM='"+bspInfo.getOrgan().getOrganCode()+
				"' or t2.XXSSZGJYXZDM in (select region_code from COM_MEMS_ORGAN where parent_code='"+bspInfo.getOrgan().getOrganCode()+
				"') or t2.XXSSZGJYXZDM in (select region_code from COM_MEMS_ORGAN where parent_code in (select region_code from COM_MEMS_ORGAN where parent_code='"+
				bspInfo.getOrgan().getOrganCode()+"')))");
		if(list!=null && list.size()>0){
			Map<String,Object> mapbox = this.findMENUbyID("100206");
			mapbox.put(CONTENT,"有肄业申请未审核!"); 
			return mapbox;
		}
		return null;
	}
	/**
	 * 照片更新审核提醒
	 * @param bspInfo
	 * @return
	 */
	public Map<String,Object> getUpdatePhotoBox(MBspInfo bspInfo){
		List list = this.getListSQL("select T1.XX_JBXX_ID,t1.FLAG,t2.XXBSM,"+
				"t2.XXMC,t2.XXYWMC,t2.XXDZ,t2.SSZGJYXZDM,t2.SSZGJYXZMC,t2.XZXM,t2.XZSJHM,t2.BGDH,t2.YZBM,t2.DWDZXX,t2.XXXZ "+
				"from zxxs_xs_zpcj t1 " +
				"left join ZXXS_XX_JBXX t2 on t1.xx_jbxx_id=t2.xx_jbxx_id " +
				"where t1.flag='0' and T2.SSZGJYXZDM='"+bspInfo.getOrgan().getOrganCode()+"' ");
		if(list!=null && list.size()>0){
			Map<String,Object> mapbox = this.findMENUbyID("100903");
			mapbox.put(CONTENT,"有照片更新需要审核!"); 
			return mapbox;
		}
		return null;
	}
	/**
	 * 审核提醒
	 * @param bspInfo
	 * @return
	 */
	public List getExamInfoBox(MBspInfo bspInfo){
		List resultList = new ArrayList();
		List list = this.getListSQL("select LCID,EXAMNAME,DWID,DWTYPE,WJFLAG,SHFLAG,BBFLAG,DLFLAG,CJXZFLAG,WJCJXZFLAG" +
				" from CUS_KW_EXAMROUND where DWTYPE='1' and "+
				" (WJFLAG='0' or SHFLAG='0' or BBFLAG='0' or DLFLAG='0' or CJXZFLAG='0' or WJCJXZFLAG='0') " +
				" and dwid='"+bspInfo.getOrgan().getOrganCode()+"' ");
		boolean wj=true,sh=true,bb=true,dl=true,cjxz=true,wjcjxz=true;
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (HashMap)list.get(i);
				String wjflag = map.get("WJFLAG")==null?"":map.get("WJFLAG").toString();
				if("0".equals(wjflag) && wj){
					Map<String,Object> mapbox = this.findMENUbyID("000711");
					mapbox.put(CONTENT,"有违纪考生名单需要审核!"); 
					wj = false;
					resultList.add(mapbox);
				}
				String shflag = map.get("SHFLAG")==null?"":map.get("SHFLAG").toString();
				if("0".equals(shflag) && sh){
					Map<String,Object> mapbox = this.findMENUbyID("000303");
					mapbox.put(CONTENT,"有考生名单需要审核!"); 
					sh = false;
					resultList.add(mapbox);
				}
				String bbflag = map.get("BBFLAG")==null?"":map.get("BBFLAG").toString();
				if("0".equals(bbflag) && bb){
					Map<String,Object> mapbox = this.findMENUbyID("00030301");
					mapbox.put(CONTENT,"有补报考生名单需要审核!"); 
					bb = false;
					resultList.add(mapbox);
				}
				String dlflag = map.get("DLFLAG")==null?"":map.get("DLFLAG").toString();
				if("0".equals(dlflag) && dl){
					Map<String,Object> mapbox = this.findMENUbyID("00030302");
					mapbox.put(CONTENT,"有删除考生名单需要审核!"); 
					dl = false;
					resultList.add(mapbox);
				}
				String cjxzflag = map.get("CJXZFLAG")==null?"":map.get("CJXZFLAG").toString();
				if("0".equals(cjxzflag) && cjxz){
					Map<String,Object> mapbox = this.findMENUbyID("000802");
					mapbox.put(CONTENT,"有成绩修正需要审核!"); 
					cjxz = false;
					resultList.add(mapbox);
				}
				String wjcjxzflag = map.get("WJCJXZFLAG")==null?"":map.get("WJCJXZFLAG").toString();
				if("0".equals(wjcjxzflag) && wjcjxz){
					Map<String,Object> mapbox = this.findMENUbyID("000802");
					mapbox.put(CONTENT,"有违纪成绩修正需要审核!"); 
					wjcjxz = false;
					resultList.add(mapbox);
				}
			}
			return resultList;
		}
		return null;
	}
	public MsgBean getMessage(MBspInfo bspInfo) { 
		List data = new ArrayList();
		Map<String,Object> mapbox = this.getYiyeBox(bspInfo);
		if(mapbox!=null)
			data.add(mapbox); 
		mapbox = this.getUpdatePhotoBox(bspInfo);
		if(mapbox!=null)
			data.add(mapbox); 
		List list = this.getExamInfoBox(bspInfo);
		data.addAll(list);
		return this.getMsgBean("消息框",data);
	}
	
	
}
