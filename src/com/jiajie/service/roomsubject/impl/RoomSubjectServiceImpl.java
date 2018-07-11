package com.jiajie.service.roomsubject.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.roomsubject.RoomSubjectService;
import com.jiajie.util.bean.MBspInfo;
@Service("roomSubjectService")
public class RoomSubjectServiceImpl extends ServiceBridge implements RoomSubjectService {

	public PageBean getList(String dt, MBspInfo mBspInfo) { 
		StringBuilder sql = new StringBuilder();
		sql.append("select ROOMNAME ,DATE_FORMAT(EXAMDATE,'%Y-%m-%d') as EXAMDATE,starttime ,ENDTIME,ROOMID,case when down_status is null or down_status = 0 then '未下载' when down_status = 1 then '已下载' when down_status = -1 then '下载失败' end as DOWN_STATUS ,");
		sql.append(" case when submit_status is null or submit_status = 0 then '未提交' when submit_status = 1 then '已提交' when submit_status = -1 then '提交失败' end as SUBMIT_STATUS ");
		sql.append(" from (select roomname ,EXAMDATE,starttime ,ENDTIME ,roomid ,(select down_status from cus_kw_roomsubject WHERE kmid = es.kmid and roomid = r.FJID) as down_status,");
		sql.append(" (select submit_status from cus_kw_roomsubject WHERE kmid = es.kmid and roomid = r.FJID) as submit_status ");
		sql.append(" from (select * from cus_kw_room where 1=1 and SCHOOLID in (SELECT X.XX_JBXX_ID fROM t_qxgl_usermapping U,zxxs_xx_jbxx X WHERE U.ORGAN_CODE = X.SSZGJYXZDM AND USER_ID = '");
		sql.append(mBspInfo.getUserId()).append("')) r ,cus_kw_examroom er,cus_kw_examschedule es where r.FJID = er.roomid and er.lcid = es.lcid ) t where 1=1 ");
		sql.append(dt).append(" order by t.starttime DESC");
		return getSQLPageBean(sql.toString());
	}

	public List<Map<String, String>> getRoom(MBspInfo bspInfo) { 
		return (List<Map<String, String>>)getSession().createSQLQuery("SELECT roomname as text, FJID as value FROM cus_kw_room WHERE SCHOOLID IN (SELECT X.XX_JBXX_ID FROM t_qxgl_usermapping U,zxxs_xx_jbxx X WHERE U.ORGAN_CODE = X.SSZGJYXZDM AND USER_ID = '"+bspInfo.getUserId()+"')").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}
