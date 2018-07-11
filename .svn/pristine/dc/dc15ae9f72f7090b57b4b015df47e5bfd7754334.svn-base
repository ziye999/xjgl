package com.jiajie.service.core.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.ExportDataService;
import com.jiajie.util.ChartGraphics;
import com.jiajie.util.SecUtils;
@Service("exportDataService")
public class ExportDataServiceImpl extends ServiceBridge implements ExportDataService {

	public Map<String, Object> ExportRoomData() {
//		getSession().createSQLQuery("delete  from cus_kw_room").executeUpdate();
		List<Map<String,Object>> list = getSession().createSQLQuery("select kdmc,lc,kcmc,kcbh from kdkc_bak").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		for (int i = 0; i < list.size(); i++) {
			String kdmc = list.get(i).get("kdmc").toString();
			String lc =  list.get(i).get("lc").toString();
			String kcmc =  list.get(i).get("kcmc").toString();
			String kcbh =  list.get(i).get("kcbh").toString();
			String seats =  "10";
			Map<String,String> map = (Map<String,String>)getSession().createSQLQuery("select max(lf_id) as lfid,max(school_id_qg) as sch from cus_kw_building where buildname = '"+kdmc+"'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
			if(map!=null && map.size()>0){
				getSession().createSQLQuery("insert into cus_kw_room(fjid,roomcode,roomname,lfid,floor,seats,type_m,schoolid)values('"+kcbh+"',1,'"+kcmc+"','"+map.get("lfid")+"',"+lc+","+seats+",1,'"+map.get("sch")+"')").executeUpdate();
			}else{
				System.out.println(kdmc+"    === ");
			}
		}
		return null;
	}

	public void genScoreImg() {
		try {
			List<Map<String,Object>> lm = getSession().createSQLQuery("select '唐孝连' as xm,'432524196007041217' as sfzjh,score,sfhg from  exam_score_detail where sfzjh = '432524198001062817'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			ChartGraphics cg = new ChartGraphics();
//			String path = "/var/www/kaoshi.worldve.com/ksgl/ksgltest/ksnibuhuimingbai/";
			String path = "D:/test/sfzjh";
			for (int i = 0; i < lm.size(); i++) {
				String sfz = (String)lm.get(i).get("sfzjh");
				String xm = (String)lm.get(i).get("xm");
				String score = lm.get(i).get("score")+"";
				String sfhg = lm.get(i).get("sfhg")+"";
				if("1".equals(sfhg)){
					sfhg = "合格";
				}else{
					sfhg = "未合格";
				}
				
				File f = new File(path+File.separator+sfz.substring(0,6));
				if(!f.exists()){
					f.mkdirs();
				}
				String url = path+File.separator+sfz.substring(0,6)+File.separator+SecUtils.encode(sfz+xm).replace("/","-")+".jpg";
				cg.graphicsGeneration(xm, sfz, score, sfhg, url);
				System.out.println(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
