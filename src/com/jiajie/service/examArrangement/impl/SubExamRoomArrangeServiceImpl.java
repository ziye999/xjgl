package com.jiajie.service.examArrangement.impl;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.hibernate.transform.Transformers;
import org.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import com.jiajie.bean.examArrangement.CusKwRoomstudent;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.bean.exambasic.CusKwExamschedule;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.examArrangement.SubExamRoomArrangeService;
import com.jiajie.util.StringUtil;
import com.jiajie.bean.MsgBean;
import com.mysql.jdbc.StringUtils;

@Service("subExamRoomArrangeService")
@SuppressWarnings("all")
public class SubExamRoomArrangeServiceImpl extends ServiceBridge implements SubExamRoomArrangeService {

	public List<Map<String, Object>> getExamRoomArrange(String lcId,String kmid,String lfid,String kdid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct CONCAT(t4.examname,'考场分布表') as BT,CONCAT(t4.xn,'年度 ',t5.name) as FBT,t3.BUILDNAME,CONCAT(t2.floor,'层') as LC,");
		sb.append("CONCAT(t1.kcbh,'考场') as KCMC,t2.ROOMNAME,t1.SEATS,");
		sb.append("(select count(distinct xjh) from cus_kw_examinee where t1.kcid = kcid and t1.kdid = kdid and t1.lcid = lcid and kmid=t44.kmid) as ANRS,");
		sb.append("CONCAT((select IFNULL(min(kscode),'0') from cus_kw_examinee where t1.kcid = kcid and t1.kdid = kdid and t1.lcid = lcid and (kmid is null or kmid=t44.kmid)),'--',");
		sb.append("(select IFNULL(max(kscode),'0') from cus_kw_examinee where t1.kcid = kcid and t1.kdid = kdid and t1.lcid = lcid and (kmid is null or kmid=t44.kmid))) as KHQZ, ");
		sb.append("t1.KCID,t1.LCID,t1.KDID,t1.ROOMID ");
		sb.append("from CUS_KW_EXAMROOM t1 ");
		sb.append("left join CUS_KW_ROOM t2 on t1.roomid = fjid ");
		sb.append("left join CUS_KW_BUILDING t3 on t3.lf_id = t2.lfid ");
		sb.append("left join CUS_KW_EXAMROUND t4 on t4.lcid = t1.lcid ");
		sb.append("LEFT JOIN cus_kw_examsubject t44 ON t44.lcid = t1.lcid ");
		sb.append("left join SYS_ENUM_VALUE t5 on t4.xqm = t5.code and t5.DIC_TYPE = 'ZD_XT_XQMC' ");
		sb.append("where 1=1 and t1.lcid = '").append(lcId)
		.append("' and t2.lfid = '").append(lfid)
		.append("' and t1.kdid = '").append(kdid).append("' ");
		if (!StringUtils.isNullOrEmpty(kmid)) {
			sb.append("and t44.kmid='").append(kmid).append("' ");
		}
		sb.append("order by t3.BUILDNAME asc,t2.floor asc,t1.kcbh asc");
		return getListSQL(sb.toString());
	}

	public List<Map<String, Object>> getKcStus(String lcid, String kcid) {

		List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select t1.APID,ifnull(t2.XM,(select xm from cus_kw_examinee where xjh=t1.xh)) as XM,");
			sb.append("t3.XXMC,t4.BJMC,CONCAT('考号：',t1.kscode) as KH,t1.X,t1.Y,t5.XS,t5.YS ");
			sb.append("from cus_kw_roomstudent t1 ");
			sb.append("left join cus_kw_examroom t5 on t5.kcid = t1.kcid and t5.KDID=t1.KDID and t5.LCID=t1.LCID ");
			sb.append("left join ZXXS_XS_JBXX t2 on t1.xh = t2.GRBSM ");
			sb.append("left join ZXXS_XX_JBXX t3 on t3.XX_JBXX_ID = t2.XX_JBXX_ID ");
			sb.append("left join ZXXS_XX_BJXX t4 on t4.XX_BJXX_ID = t2.XX_BJXX_ID ");
			sb.append("where 1=1 ");
			sb.append(" and t5.lcid = '").append(lcid).append("' and T5.kcid = '").append(kcid).append("' ");
			sb.append(" order by t1.y,t1.x");
			
			List<Map<String, Object>> list = getListSQL(sb.toString());
			
			if(list != null && list.size() > 0) {
				//获取考点行数和列数
				int xs = Integer.valueOf(list.get(0).get("XS").toString());
				int ys = Integer.valueOf(list.get(0).get("YS").toString());
				
				for (int i = 0; i < ys; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					List<Map<String, Object>> clist = new ArrayList<Map<String,Object>>();
					for (int j = 0; j < xs; j++) {
						Map<String, Object> cmap = new HashMap<String, Object>();
						cmap.put("height", 80);
						
						cmap.put("header", false);
						StringBuilder html = new StringBuilder();
						String id = "meapid_"+i+j;
						html.append("<input id='").append(id).append("' name='").append(id).append("' type='hidden' value='").append(i+1).append(",").append(j+1).append("'>");
						for (int k = 0; k < list.size(); k++) {
							if(list.get(k).get("X") != null) {
								int x = Integer.valueOf(list.get(k).get("X").toString())-1;
								int y = Integer.valueOf(list.get(k).get("Y").toString())-1;
								if(y == i && x == j) {
									String apid = list.get(k).get("APID").toString();
									html.append("<center><input name='apid_sys' type='hidden' value='").append(apid).append("'>");
									html.append("<label class='zwtdlabelname'>").append(list.get(k).get("XM")==null?"":list.get(k).get("XM").toString()).append("</label></br>");
									html.append("<label class='zwtdlabel'>").append(list.get(k).get("KH")==null?"":list.get(k).get("KH").toString()).append("</label></br>");
									html.append("<label class='zwtdlabel'>").append(list.get(k).get("XXMC")==null?"":list.get(k).get("XXMC").toString()).append("</label></br>");
									html.append("<label class='zwtdlabel'>").append(list.get(k).get("BJMC")==null?"":list.get(k).get("BJMC").toString()).append("</label>");
									html.append("</center>");
									break;
								}
							}
						}
						cmap.put("id", "");
						cmap.put("meapid", id);
						cmap.put("x", i+1);
						cmap.put("y", j+1);
						cmap.put("html", html.toString());
						clist.add(cmap);
					}
					map.put("id", "");
					map.put("items", clist);
					resList.add(map);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resList;
	}

	public MsgBean updataKcStu(JSONArray jsonObj) {
		try {
			for (int i = 0; i < jsonObj.size(); i++) {
				JSONObject jsonObject = (JSONObject) jsonObj.get(i);
				String apid = jsonObject.getString("apid");
				String[] xys = jsonObject.getString("xys").split(",");
				Short y = Short.valueOf(xys[0]);
				Short x = Short.valueOf(xys[1]);
				StringBuffer sb = new StringBuffer();
				sb.append("update CUS_KW_ROOMSTUDENT set x=").append(x).append(",y=").append(y).append(" where apid='").append(apid).append("'");
				update(sb.toString());				
			}
			this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}
	
}
