package com.jiajie.service.scoreModify.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.scoreModify.CusKwModifyScore;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.resultsStatisticalCollection.SetStandardService;
import com.jiajie.service.scoreModify.ModifyScoreService;
import com.jiajie.service.scoreModify.StuScoreService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;

@Service("stuScoreService")
@SuppressWarnings("all")
public class StuScoreServiceImpl extends ServiceBridge implements StuScoreService {

	public PageBean getListPage() {
		String sql = getSqlString();
		return getSQLPageBean(sql); 
	}
	
	public String getSqlString(){
		StringBuilder sb = new StringBuilder(); 
		sb.append("select KS.KSID ,Z.REGION_NAME AS ZKDW,C.XXMC AS CKDW ,KS.SFZJH,concat(E.EXAMDATE,'') AS KSSJ,S.SCOREJM AS CJ from cus_kw_examinee ks ,zxxs_xx_jbxx c ,com_mems_organ z ,cus_kw_stuscore s,cus_kw_examschedule E ");
		sb.append("where S.KSID = ks.KSID and ks.XXDM = c.XX_JBXX_ID  and z.REGION_CODE = c.SSZGJYXZDM AND KS.KMID = E.KMID");
		return sb.toString();
	}
 
	   
}
