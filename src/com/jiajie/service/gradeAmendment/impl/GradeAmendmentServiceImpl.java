package com.jiajie.service.gradeAmendment.impl;

import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.gradeAmendment.GradeAmendmentService;
import com.jiajie.util.StringUtil;
@Service("gradeAmendmentService")
@SuppressWarnings("all")
public class GradeAmendmentServiceImpl extends ServiceBridge implements GradeAmendmentService {

	public PageBean getList(String lcid, String xnxq) {
		String nn = "";
		String xq = "";
		if(xnxq!=null && !"".equals(xnxq)){
			String[] str = xnxq.split(" ");
			nn = str[0].substring(0, 4);
			xq = str[1];
		}
		String sql = "select distinct lx.mc LX,xq.mc XQ,rd.LCID,rd.XNXQ_ID,rd.XQM,rd.EXAMNAME,rd.STARTWEEK,rd.STARTDAY,rd.STARTDATE,rd.ENDWEEK,"+
				"rd.ENDDAY,rd.ENDDATE,rd.EXAMTYPEM,rd.DWID,rd.DWTYPE,rd.XN,rd.WJFLAG,rd.SHFLAG,rd.BBFLAG,rd.DLFLAG,rd.CJXZFLAG,rd.WJCJXZFLAG,"+
				"case when rd.CJXZFLAG='1' then '已审核' else '未审核' end as CJXZZT "+
				"from cus_kw_examround rd "+
				"left join cus_kw_examtype lx on rd.examtypem=lx.dm " +
				"left join zd_xq xq on rd.xqm=xq.dm where cjxzflag is not null";				
		if(nn!=null && !"".equals(nn)){
			sql += " and rd.xn='"+nn+"'";
		}
		if(xq!=null && !"".equals(xq)){
			sql += " and xq.mc = '"+xq+"'";
		}
		if(lcid !=null && !"".equals(lcid)){
			sql += " and rd.lcid = '"+lcid+"'";
		}
		return getSQLPageBean(sql); 
	}
	
	public com.jiajie.bean.MsgBean updateSH(String lcid) {
		try {
			String sql = "update cus_kw_modifyscore set flag='1' where lcid='"+lcid+"' and flag='0'"; 
			update(sql);
			String sql2 = "update cus_kw_examround set cjxzflag='1' where lcid='"+lcid+"'";
			update(sql2);
			return getMsgBean(true, "审核成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return getMsgBean(false, "审核失败！");
		}
	}
	
	public PageBean getXzxsList(String lcid,String xx,String nj,String bj,String sfzjh) {
		String sql = "select distinct mf.XGCJID,mf.LCID,mf.KMID,mf.EXAMCODE,mf.XJH,mf.YSCJ,"+
				"mf.KCFS,mf.ZZCJ,mf.XGR,mf.FLAG,ei.XM,sb.SUBJECTNAME,ei.SFZJH "+
				"from cus_kw_modifyscore mf "+
				"left join cus_kw_examinee ei on ei.kscode=mf.examcode "+
				"left join cus_kw_examsubject sb on sb.kmid=mf.kmid where mf.lcid in ('"+
				lcid.replaceAll(" ", "").replaceAll(",", "','")+"')";
		if(xx!=null && !"".equals(xx)){
			sql += " and ei.xxdm = '"+xx+"'";
		}
		if(nj!=null && !"".equals(nj)){
			sql += " and ei.nj = '"+nj+"'";
		}
		if(bj!=null && !"".equals(bj)){
			sql += " and ei.bh = '"+bj+"'";
		}
		if(sfzjh!=null && !"".equals(sfzjh)){
			sql += " and ei.SFZJH like binary('%"+sfzjh+"%')";
		}
		return getSQLPageBean(sql); 
	}
}
