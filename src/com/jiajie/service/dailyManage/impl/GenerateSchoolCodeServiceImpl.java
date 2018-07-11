package com.jiajie.service.dailyManage.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.dailyManage.GenerateSchoolCodeService;
import com.jiajie.util.StringUtil;
import com.jiajie.bean.MsgBean;
import com.jiajie.bean.dailyManagement.CusXjSchoolcode;

@Service("generateSchoolCodeService")
@SuppressWarnings("all")
public class GenerateSchoolCodeServiceImpl extends ServiceBridge implements GenerateSchoolCodeService{

	public PageBean getListPage(String organCode, String xxids) {
		StringBuilder sb = new StringBuilder();
		sb.append("select zxxj.xx_jbxx_id XXID,cxjs.schoolcode XXDM,zxxj.XXMC,zxxj.XZXM,zxxj.YZBM,zxxj.XXDZ ");
		sb.append("from zxxs_xx_jbxx zxxj ");
		sb.append("left join cus_xj_schoolcode cxjs on zxxj.xx_jbxx_id = cxjs.xx_jbxx_id ");
		sb.append("left join ZXXS_XX_JBXX cmop on zxxj.xx_jbxx_id = cmop.xx_jbxx_id ");
		sb.append("where (cmop.SSZGJYXZDM='"+organCode+"' or cmop.SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+organCode+
					"') or cmop.SSZGJYXZDM IN (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+
					organCode+"')))");
		if(StringUtil.isNotNullOrEmpty(xxids)){	
			xxids = xxids.replaceAll(",","','");
			sb.append(" and zxxj.xx_jbxx_id in ('").append(xxids).append("')");			
		}
		return getSQLPageBean(sb.toString());
	}

	public MsgBean generate(String organCode, String xxids) {
		//String[] split = xxids.split(",");
		try {
			this.delete("delete from CUS_XJ_SCHOOLCODE");// where Xx_jbxx_id='"+split[i]+"'
			List ls = getListSQL("select SCHOOLCODE from cus_xj_schoolcode");//where REGION_CODE='"+organCode+"'
			String xxcd = String.format("%03d",ls.size());
			List lsxx = getListSQL("SELECT zxxj.xx_jbxx_id XXID FROM zxxs_xx_jbxx zxxj "+
								"LEFT JOIN ZXXS_XX_JBXX cmop ON zxxj.xx_jbxx_id = cmop.xx_jbxx_id "+
								"where (cmop.SSZGJYXZDM='"+organCode+"' or cmop.SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+organCode+
								"') or cmop.SSZGJYXZDM IN (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+
								organCode+"')))");
			for (int i = 0; i < lsxx.size(); i++) {
				Map<String, Object> mp = (Map<String, Object>) lsxx.get(i);
				CusXjSchoolcode cxs = new CusXjSchoolcode();
				cxs.setRegion_code(organCode);
				cxs.setXx_jbxx_id(mp.get("XXID").toString());				
				xxcd = canGen(cxs,xxcd,organCode,mp.get("XXID").toString());
				cxs.setSchoolcode(xxcd);
				save(cxs);
			}
			this.getMsgBean(true,"成功生成参考单位代码！");
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"生成参考单位代码失败！");
		}
		return MsgBean;
	}

	private String canGen(CusXjSchoolcode cxs,String xxcd,String organCode,String xx_jbxx_id){
		xxcd = String.format("%03d",Integer.valueOf(xxcd).intValue()+1);		
		List list = getListSQL("select SCHOOLCODE from cus_xj_schoolcode where schoolcode='"+
				xxcd+"'");//and REGION_CODE='"+organCode+"'
		if(list.size()>0){					
			canGen(cxs,xxcd,organCode,xx_jbxx_id);			
		}
		return xxcd;
	}
	
	public com.jiajie.bean.MsgBean update(String organCode, String xxids,
			String xxdm) {
		try {
			xxdm = "000"+xxdm;
			xxdm = xxdm.substring(xxdm.length()-3,xxdm.length());
			StringBuffer sb = new StringBuffer();
			sb.append("select SCHOOLCODE from cus_xj_schoolcode where schoolcode = '")
			.append(xxdm).append("' and xx_jbxx_id = '").append(xxids).append("'");
			List list = getListSQL(sb.toString());
			if(list.size() > 0){
				this.getMsgBean(false,"没有改动，不能修改!");
				return MsgBean;
			}else{
				List lst = getListSQL("select * from cus_xj_schoolcode where schoolcode='"+xxdm+"'");
				if (lst.size()>0){
					this.getMsgBean(false,"已存在同样的单位代码，不能修改!");
					return MsgBean;
				}else{
					sb.delete(0, sb.length());
					sb.append(" update cus_xj_schoolcode set schoolcode = '").append(xxdm)
					.append("' where XX_JBXX_ID ='").append(xxids).append("' and REGION_CODE = '")
					.append(organCode).append("'");
					update(sb.toString());
				}				
			}
			this.getMsgBean(true,"成功生成参考单位代码！");
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"生成参考单位代码失败！");
		}
		return MsgBean;
	}
	
}
