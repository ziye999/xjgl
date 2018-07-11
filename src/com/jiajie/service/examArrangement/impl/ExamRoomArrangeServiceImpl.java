package com.jiajie.service.examArrangement.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.examArrangement.CusKwRoomstudent;
import com.jiajie.bean.exambasic.CusKwExamroom;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.examArrangement.ExamRoomArrangeService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.bean.MsgBean;

@Service("examRoomArrangeServiceImpl")
@SuppressWarnings("all")
public class ExamRoomArrangeServiceImpl extends ServiceBridge implements
		ExamRoomArrangeService {

	// 获取轮次信息
	public PageBean getExamRounds(String xnxqId,String userId,MBspInfo bspInfo) {
		String sql = "SELECT T1.LCID,T1.EXAMNAME,T1.STARTWEEK,T1.STARTDAY,"
				+ "date_format(T1.STARTDATE,'%Y-%m-%d %H:%i:%s') as STARTDATE,T1.ENDWEEK,T1.ENDDAY,"
				+ "date_format(T1.ENDDATE,'%Y-%m-%d %H:%i:%s') as ENDDATE,"
				+ "t4.MC as EXAMTYPEM,T1.XN as XNMC,t3.MC as XQMC,T6.COUNTKD,T6.COUNTKS,IFNULL(T7.CKXXSL,0) CKXXSL"
				+ " from CUS_KW_EXAMROUND t1"
				+ " left join COM_MEMS_ORGAN zk on zk.region_code=t1.dwid"
		        + " left join COM_MEMS_ORGAN zk1 on zk1.region_code=zk.PARENT_CODE"
				+ " left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=t1.DWID"
				+ " LEFT JOIN ZD_XQ t3 on t1.XQM=t3.DM"
				+ " LEFT JOIN CUS_KW_EXAMTYPE t4 on t1.EXAMTYPEM=t4.DM "
				+ " LEFT JOIN (SELECT T1.LCID,COUNT(DISTINCT T2.KDID) AS COUNTKD,COUNT(DISTINCT t3.SFZJH) COUNTKS FROM CUS_KW_EXAMROUND t1"
				+ " LEFT JOIN CUS_KW_EXAMSCHOOL t2 on T1.LCID=T2.LCID"
				+ " LEFT JOIN CUS_KW_EXAMINEE t3 on T1.LCID=T3.LCID GROUP BY T1.LCID) T6 ON T1.LCID=T6.LCID"
				+ " LEFT JOIN (SELECT LCID,COUNT(DISTINCT XXDM) CKXXSL FROM CUS_KW_POINTINFO GROUP BY LCID) t7 ON T1.LCID=T7.LCID"
				+ " where 0=0";
		sql+=" and (T1.DWID='"+bspInfo.getOrgan().getOrganCode()+
			"' or zk.region_code='"+bspInfo.getOrgan().getOrganCode()+"' or zk1.region_code='"+bspInfo.getOrgan().getOrganCode()+
			"' OR zk1.PARENT_CODE = '"+bspInfo.getOrgan().getOrganCode()+"' or xx.SSZGJYXZDM='"+bspInfo.getOrgan().getOrganCode()+
			"' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+
			"') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+"')))";								
		if (xnxqId != null && !"".equals(xnxqId)) {
			String xn = xnxqId.split(",")[0];
			String xq = xnxqId.split(",")[1];
			sql += " and T1.XN='" + xn + "' " + " and T1.XQM='" + xq + "'";
		}
		sql += "  ORDER BY T1.STARTDATE DESC";
		return getSQLPageBean(sql);
	}

	//获取考场信息
	public PageBean getExamRooms(String lcId, String schoolId, String lfId, String jslx) {
		String sql = "SELECT distinct T5.KCID,T1.POINTNAME,T2.BUILDNAME,T3.ROOMNAME,T3.FJID as ROOMID,"
				+ "case when T3.SEATS='' then 0 ELSE T3.SEATS end AS YXZWS,T4.MC AS JSLX,T5.KCBH,"
				+ "case when T5.SEATS='' then 0 else T5.SEATS end AS SEATS,"
				+ "case when T5.XS='' then 0 ELSE T5.XS end AS XS,"
				+ "case when T5.YS='' then 0 ELSE T5.YS end AS YS"
				+ " FROM CUS_KW_EXAMROOM T5"
				+ " INNER JOIN CUS_KW_ROOM T3 ON T3.FJID = T5.ROOMID"
				+ " INNER JOIN CUS_KW_BUILDING T2 ON T2.LF_ID = T3.LFID"
				+ " INNER JOIN CUS_KW_EXAMSCHOOL T1 ON T1.XXDM = T2.SCHOOL_ID_QG"
				+ " INNER JOIN ZD_JSLX T4 ON T4.DM = T3.TYPE_M"
				+ " WHERE T5.LCID='" + lcId + "'";
		if (schoolId != null && !"".equals(schoolId)) {
			sql += " AND T5.KDID='" + schoolId + "'";
		}

		if (lfId != null && !"".equals(lfId)) {
			sql += " AND T2.LF_ID='" + lfId + "'";
		}
		if (jslx != null && !"".equals(jslx)) {
			sql += " AND T3.TYPE_M='" + jslx + "'";
		}
		sql += " ORDER BY T1.POINTNAME,T2.BUILDNAME,T3.ROOMNAME,T5.KCBH";
		return getSQLPageBean(sql);
	}

	public Boolean saveExamRooms(String lcId, String schoolId, String lfId,
			String jslx, Integer hang, Integer lie) {
		boolean bool = false;
		String sql = "SELECT IFNULL(MIN(SEATS),0) as SEATS from CUS_KW_ROOM t1 "+
		"INNER JOIN CUS_KW_EXAMSCHOOL t2 on T1.SCHOOLID=T2.XXDM WHERE 1=1";
		if (schoolId != null && !"".equals(schoolId)) {
			sql += " AND T2.KDID='" + schoolId + "'";
		}
		if (lfId != null && !"".equals(lfId)) {
			sql += " AND T1.LFID='" + lfId + "'";
		}
		if (jslx != null && !"".equals(jslx)) {
			sql += " AND T1.TYPE_M='" + jslx + "'";
		}
		List list_minSeats = getSession().createSQLQuery(sql).list();
		Integer minSeats = 0;
		if (list_minSeats != null && list_minSeats.size() > 0) {
			minSeats = StringUtil.getInteger(list_minSeats.get(0));
		}
		if (minSeats >= hang * lie) {
			sql = "SELECT T1.POINTNAME,T2.BUILDNAME,T3.ROOMNAME,T3.FJID,"
					+ "case when T3.SEATS='' then 0 ELSE T3.SEATS  end AS YXZWS,T4.MC AS JSLX,T5.KCBH,"
					+ "case when T5.SEATS='' then 0 else T5.SEATS end AS SEATS,"
					+ "case when T5.XS='' then 0 ELSE T5.XS end AS XS,"
					+ "case when T5.YS='' then 0 ELSE T5.YS end AS YS"
					+ " FROM CUS_KW_EXAMSCHOOL T1"
					+ " INNER JOIN CUS_KW_BUILDING T2 on T1.XXDM=T2.SCHOOL_ID_QG"
					+ " INNER JOIN CUS_KW_ROOM T3 on T2.LF_ID=T3.LFID"
					+ " LEFT JOIN  ZD_JSLX T4 on T3.TYPE_M=T4.DM"
					+ " LEFT JOIN CUS_KW_EXAMROOM T5 on T3.FJID=T5.ROOMID"
					+ " WHERE T1.LCID='" + lcId + "'";
			if (schoolId != null && !"".equals(schoolId)) {
				sql += " AND T1.KDID='" + schoolId + "'";
			}
			if (lfId != null && !"".equals(lfId)) {
				sql += " AND T2.LF_ID='" + lfId + "'";
			}
			if (jslx != null && !"".equals(jslx)) {
				sql += " AND T3.TYPE_M='" + jslx + "'";
			}
			sql += " ORDER BY T1.POINTNAME,T2.BUILDNAME,T3.ROOMNAME";
			List<Map<String, Object>> list = getSession().createSQLQuery(sql)
					.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
					.list();
			if (list != null && list.size() > 0) {
				sql = "SELECT IFNULL(MAX(kcbh),0) as MAXKCBH from CUS_KW_EXAMROOM where LCID='"+lcId+
						"' and KDID='"+schoolId+"'";
				List max_kcbh = getSession().createSQLQuery(sql).list();
				int maxKcbh = StringUtil.getInteger(max_kcbh.get(0)) + 1;// 最大考场编号
				int i = 0;
				for (; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					Integer yxzws = StringUtil.getInteger(map.get("YXZWS"));
					String roomid = StringUtil.getString(map.get("FJID"));
					sql = "FROM CusKwExamroom room WHERE room.lcid='"+lcId+"' and room.roomid='"+roomid+"'";
					List<CusKwExamroom> list_room = getSession().createQuery(sql).list();
					CusKwExamroom room = new CusKwExamroom();
					if (list_room != null && list_room.size() > 0) {
						room = list_room.get(0);
					}
					Long seats = Long.valueOf(hang * lie + "");
					if (room.getKcbh() == null || "".equals(room.getKcbh())) {
						room.setKcbh(maxKcbh + "");
						maxKcbh++;
					}
					room.setKdid(schoolId);
					room.setLcid(lcId);
					room.setRoomid(roomid);
					room.setSeats(seats);
					room.setXs(Short.valueOf(hang + ""));
					room.setYs(Short.valueOf(lie + ""));
					saveOrUpdate(room);
				}
				if (i == list.size()) {
					bool = true;
				}
			}
		}
		return bool;
	}

	public MsgBean saveOrUpdateExamRooms(String lcId, String schoolId,
			String kcId, String roomId, Integer hang, Integer lie) {
		Long seats = Long.valueOf(hang * lie + "");
		CusKwExamroom room = new CusKwExamroom();
		if (kcId != null && !"".equals(kcId)) {
			room = (CusKwExamroom) getSession().get(CusKwExamroom.class, kcId);
		} else if (room.getKcbh() == null || "".equals(room.getKcbh())) {
			String sql = "SELECT IFNULL(MAX(kcbh),0) as MAXKCBH from CUS_KW_EXAMROOM where LCID='"+lcId+
					"' and KDID='" + schoolId + "'";
			List max_kcbh = getSession().createSQLQuery(sql).list();
			int maxKcbh = StringUtil.getInteger(max_kcbh.get(0)) + 1;// 最大考场编号
			room.setKcbh(maxKcbh + "");
		}
		room.setKdid(schoolId);
		room.setLcid(lcId);
		room.setRoomid(roomId);
		room.setSeats(seats);
		room.setXs(Short.valueOf(hang + ""));
		room.setYs(Short.valueOf(lie + ""));
		saveOrUpdate(room);
		return getMsgBean(true, "设置成功！");
	}

	/**
	 * 安排考场学生
	 * 
	 * @param lcid:轮次id kcapzgz:考场安排总规则 wskc:尾数考场 zwpl:座位排列 pksx:排考顺序
	 * 
	 */
	public Object arrangeStu(String lcid, String kcapzgz, String wskc,
			String zwpl, String pksx, MBspInfo bspInfo) {
		List lsAp = getListSQL("select kmid from CUS_KW_EXAMSUBJECT where lcid='"+lcid+"' ORDER BY substr(SUBJECTNAME,11,length(substr(SUBJECTNAME,11))-length('批'))+0 asc");
		Map<String, String> apPc = new HashMap<String, String>();
		for (int i=0; i<lsAp.size(); i++) {
			Map<String, Object> mp = (Map<String, Object>) lsAp.get(i);
			String kmid = mp.get("kmid")==null?"":mp.get("kmid").toString();
			apPc.put((i+1)+"", kmid);
		}
		// 用总座位数和总考生数比较
		String ksslSql = "select count(ksid) ksnum from CUS_KW_EXAMINEE where flag='1' and lcid='"+lcid+"'";
		// 考生总数
		List ksslList = getListSQL(ksslSql);
		if (ksslList!=null&&ksslList.size()>0) {
			for (int ii=0; ii<ksslList.size(); ii++) {
				Map<String, Object> mpKs = (Map<String, Object>) ksslList.get(ii);
				int kssl = Integer.parseInt(mpKs.get("ksnum")==null?"0":mpKs.get("ksnum").toString());
				
				String zwslSql = "select IFNULL(sum(seats),0) from CUS_KW_EXAMROOM where lcid='"+lcid+"'";
				// 座位总数
				List zwslList = getSession().createSQLQuery(zwslSql).list();
				int zwsl = Integer.parseInt(zwslList.get(0).toString());
				if (kssl > zwsl) {
					return "考场座位不足，请重新安排考场！";
				}
				// 按排考总规则（先大后小/先小后大）查出所有座位的list
				String kcSql = "";
				if ("kcapzgz1".equals(kcapzgz)) {
					kcSql = "SELECT * FROM CUS_KW_EXAMROOM WHERE LCID='"+lcid+"' ORDER BY SEATS DESC";
				} else if ("kcapzgz2".equals(kcapzgz)) {
					kcSql = "SELECT * FROM CUS_KW_EXAMROOM WHERE LCID='"+lcid+"' ORDER BY SEATS ASC";
				}
				List<Map<String, Object>> kcList = getSession().createSQLQuery(kcSql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
				// 按排考顺序排序（随机/按学号/按课程）查出单个等级学生

				// 分别对多个等级考生，对号入座
				int kcnumb = 0;
				String ksSql = "";
				if ("pksx1".equals(pksx)) {// 随机
					ksSql = "SELECT * FROM CUS_KW_EXAMINEE WHERE LCID='"+lcid+"' and flag='1' order by RAND()";
				} else if ("pksx2".equals(pksx)) {// 按学号
					ksSql = "SELECT * FROM CUS_KW_EXAMINEE WHERE LCID='"+lcid+"' and flag='1' ORDER BY XJH";						
				} else if ("pksx3".equals(pksx)) {// 按课程
					ksSql = "SELECT * FROM CUS_KW_EXAMINEE WHERE LCID='"+lcid+"' and flag='1' ORDER BY BH";
				}
				// 查询考生信息
				List<Map<String, Object>> ksList = getSession().createSQLQuery(ksSql)
						.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
				int ksrs = ksList.size();// 考生人数；
				int yapksrs = 0;// 已安排考生人数
				int nj_kskc = kcnumb;// 年级开始考场
				delete("delete FROM CUS_KW_ROOMSTUDENT where lcid='"+lcid+"'");					
				one: for (int j = kcnumb; j < kcList.size(); j++) {// 循环考场信息				
					if (wskc==null||"".equals(wskc)) {
						wskc = "0";
					}				
					if ((ksrs - yapksrs) < Integer.parseInt(wskc)) {// 剩余考试跟尾数考生比较,安排尾数考生
						int add_kcnumb = 0;
						int add_zws = 1;
						int wapks = ksrs - yapksrs;
						for (int wsks = 0; wsks < wapks; wsks++) {	
							String curKmid = apPc.get((nj_kskc + add_kcnumb)+"");
							Map<String, Object> map = kcList.get(nj_kskc + add_kcnumb);
							if (add_kcnumb < j - nj_kskc) {
								add_kcnumb++;
							} else {
								add_kcnumb = 0;
								add_zws++;
							}
							int seats = StringUtil.getInteger(map.get("SEATS"));
							String kdid = StringUtil.getString(map.get("KDID"));
							String kcid = StringUtil.getString(map.get("KCID"));

							Map<String, Object> ksxx = ksList.get(yapksrs);
							yapksrs++;
							String xjh = StringUtil.getString(ksxx.get("XJH"));
							BigDecimal kscode = BigDecimal.valueOf(Long.parseLong(ksxx.get("KSCODE").toString()));
							String ksid = ksxx.get("KSID")==null?"":ksxx.get("KSID").toString();
							List lstU = getListSQL("select xs_jbxx_id from zxxs_xs_jbxx where grbsm='"+xjh+"'");
							String xs_jbxx_id = ((Map<String, String>)lstU.get(0)).get("xs_jbxx_id").toString();
							update("update cus_kw_examinee set kmid='"+curKmid+"',xs_jbxx_id='"+xs_jbxx_id+
									"',kdid='"+kdid+"',kcid='"+kcid+"',zwh="+(seats + add_zws)+" where KSID='"+ksid+"'");
							CusKwRoomstudent roomStudent = new CusKwRoomstudent();
							roomStudent.setKcid(kcid);
							roomStudent.setKdid(kdid);
							roomStudent.setKscode(kscode);
							roomStudent.setLcid(lcid);
							roomStudent.setKmid(curKmid);
							roomStudent.setXh(xjh);
							roomStudent.setZwh((short) (seats + add_zws));
							save(roomStudent);
						}
						break;
					}
					kcnumb = j;
					String curKmid = apPc.get(j+"");
					Map<String, Object> map = kcList.get(j);				
					int x = StringUtil.getInteger(map.get("XS"));// 行
					int y = StringUtil.getInteger(map.get("YS"));// 列
					int seats = StringUtil.getInteger(map.get("SEATS"));
					String kdid = StringUtil.getString(map.get("KDID"));
					String kcid = StringUtil.getString(map.get("KCID"));
					
					short ks_x = 1;
					short ks_y = 1;				
					for (int k = 0; k < seats; k++) {					
						if (yapksrs >= ksrs) break;
						Map<String, Object> ksxx = ksList.get(yapksrs);
						yapksrs++;
						String xjh = StringUtil.getString(ksxx.get("XJH"));
						BigDecimal kscode = BigDecimal.valueOf(Long.parseLong(ksxx.get("KSCODE").toString()));
						String ksid = ksxx.get("KSID")==null?"":ksxx.get("KSID").toString();
						List lstU = getListSQL("select xs_jbxx_id from zxxs_xs_jbxx where grbsm='"+xjh+"'");
						String xs_jbxx_id = ((Map<String, String>)lstU.get(0)).get("xs_jbxx_id").toString();
						update("update cus_kw_examinee set kmid='"+curKmid+"',xs_jbxx_id='"+xs_jbxx_id+
								"',kdid='"+kdid+"',kcid='"+kcid+"',zwh="+(k+1)+" where KSID='"+ksid+"'");												
						CusKwRoomstudent roomStudent = new CusKwRoomstudent();					
						roomStudent.setKcid(kcid);
						roomStudent.setKdid(kdid);
						roomStudent.setKscode(kscode);
						roomStudent.setLcid(lcid);
						roomStudent.setKmid(curKmid);
						roomStudent.setXh(xjh);
						roomStudent.setX(ks_x);
						roomStudent.setY(ks_y);
						roomStudent.setZwh((short) (k + 1));					
						save(roomStudent);
						if (ks_x % x == 0) {
							ks_x = 1;
							ks_y++;
						} else {
							ks_x++;
						}
					}
				}
				if (yapksrs < ksrs) {
					return "考场不足，还有考生未安排！";
				}else {
					return "success";
				}
			}
		}
		return "考场不足，还有考生未安排！";
	}

}
