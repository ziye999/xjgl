package com.jiajie.service.resultsStatisticalCollection.impl;

import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.resultsStatisticalCollection.CusKwStandard;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.resultsStatisticalCollection.SetStandardService;
import com.jiajie.util.bean.MBspInfo;
@Service("setStandardService")
@SuppressWarnings("all")
public class SetStandardServiceImpl extends ServiceBridge implements SetStandardService {

	public PageBean getList(String xn,String lcid,MBspInfo bspInfo) {
		String sql = "select distinct rd.LCID,rd.EXAMNAME,xq.mc XQ,rd.XN,rd.DWID,"+
				"CONCAT(date_format(rd.startdate,'%Y/%m/%d'),'--',date_format(rd.enddate,'%Y/%m/%d')) DT,"+
				"sd.UPLINE,sd.DOWNLINE,sd.BZID "+
				"from cus_kw_examround rd "+
				"left join zd_xq xq on xq.dm=rd.xqm "+
				"left join "+
				"(select bzid,lcid,CONCAT('前',upline,'%为优秀') upline,CONCAT('后',downline,'%为不合格') downline from cus_kw_standard where bztype='1' "+
				"union all "+
				"select bzid,lcid,CONCAT('满分的',upline,'%以上为优秀') upline,CONCAT('满分的',downline,'%以下为不合格') downline from cus_kw_standard where bztype='2') sd on sd.lcid=rd.lcid "+
				"where 1=1";
		if ("1".equals(bspInfo.getUserType())) {
			sql += " and (rd.dwid in (select xx_jbxx_id from zxxs_xx_jbxx) or rd.dwid='"+bspInfo.getOrgan().getOrganCode()+"' or rd.dwid in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+
			"') or rd.dwid IN (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+
			bspInfo.getOrgan().getOrganCode()+"')))";		
		}else if ("2".equals(bspInfo.getUserType())||"3".equals(bspInfo.getUserType())) {
			sql += " and (rd.dwid='"+bspInfo.getOrgan().getOrganCode()+"' or rd.dwid in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+
			"') or rd.dwid IN (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+
			bspInfo.getOrgan().getOrganCode()+"')))";
		}
		String nn = "";
		String xq = "";
		if(xn!=null && !"".equals(xn)){
			String[] str = xn.split(" ");
			nn = str[0].substring(0, 4);
			xq = str[1];
		}
		if(nn!=null && !"".equals(nn)){
			sql += " and rd.xn='"+nn+"'";
		}
		if(xq!=null && !"".equals(xq)){
			sql += " and xq.mc = '"+xq+"'";
		}
		if(!"".equals(lcid) && lcid !=null){ 
			sql+=" and rd.lcid = '"+lcid+"'";
		}
		return getSQLPageBean(sql);
	}

	public com.jiajie.bean.MsgBean saveStand(CusKwStandard cusKwStandard) {
		try {
			saveOrUpdate(cusKwStandard);
			this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}

	public com.jiajie.bean.MsgBean updateStand(String bzid, String bztype,
			String upline, String downline) {
		try {
			String sql ="update cus_kw_standard set bztype='"+bztype+"',upline='"+upline+"',downline='"+downline+"' where bzid='"+bzid+"'";
			update(sql);
			return getMsgBean(true, "设置成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getMsgBean(false, "设置失败！");
		}
	}

}
