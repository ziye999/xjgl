package com.jiajie.service.resultsStatisticalCollection.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.jiajie.bean.MsgBean;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.basicsinfo.CusKwTeacher;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.bean.resultsStatisticalCollection.CusKwExecise;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.resultsStatisticalCollection.ResultsCollectionService;

@Service("resultsCollectionService")
@SuppressWarnings("all")
public class ResultsCollectionServiceImpl extends ServiceBridge implements ResultsCollectionService{

	public PageBean getList(String dwid,String userType,String xnxq_id) {
		String sql = "";
		StringBuffer sb = new StringBuffer();
		sb.append(" select er.lcid as LCID,er.XN,xq.name as XQ,er.examname as KSMC,")
		.append("IFNULL(org.region_edu,(select xxmc from zxxs_xx_jbxx where xx_jbxx_id=er.dwid)) as ZKDW,IFNULL(lc.kssl,0) as KSSL ")
		.append(" from cus_kw_examround er")
		.append(" LEFT join com_mems_organ org on org.region_code=er.dwid")
		.append(" LEFT join com_mems_organ org1 on org1.region_code=org.parent_code ")
		.append(" left join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=er.DWID ")
		.append(" LEFT join (select lcid,count(lcid) kssl from cus_kw_examinee Group by lcid) lc on lc.lcid=er.lcid ")
		.append(" LEFT join SYS_ENUM_VALUE xq on er.XQM=xq.CODE and DIC_TYPE='ZD_XT_XQMC'")
		.append(" LEFT join zd_xq zx on xq.code = zx.dm ")			
		.append(" where (er.dwid='"+dwid+
				"' OR jb.region_code = '"+dwid+"' OR jb1.region_code = '"+dwid+
				"' OR jb1.PARENT_CODE = '"+dwid+"' or xx.SSZGJYXZDM='"+dwid+
				"' or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+dwid+
				"') or xx.SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+dwid+"')))");
		if(xnxq_id!=null && !"".equals(xnxq_id)){
			String[] xnxqs = xnxq_id.split(",");
			if(xnxqs.length == 2) {
				sb.append("and er.xn='"+xnxqs[0]+"' and er.xqm='"+xnxqs[1]+"'");
			}
		}
		return getSQLPageBean(sb.toString());
	}
	
	public MsgBean importResults(String userId, File file,String lcid) {
		List<CusKwExecise> list = new ArrayList<CusKwExecise>();
		try {
			Workbook rwb = Workbook.getWorkbook(file);
			Sheet rs = rwb.getSheet(0);
			int clos=rs.getColumns();//得到所有的列
			int rows=rs.getRows();//得到所有的行
			if(clos != 5){
				this.getMsgBean(false,"导入失败！请按照模版导入数据。");
				return MsgBean;
			}
			List<CusKwExecise> cke = new ArrayList<CusKwExecise>();
			  one:for (int i = 1; i < rows; i++) {
					CusKwExecise ckExecise = new CusKwExecise();
	                for (int j = 0; j < clos; j++) {
	                	String value =  rs.getCell(j, i).getContents();
	                	switch (j) {
						case 0:
							ckExecise.setExtype(value);
							break;
						case 1:
							if(!isNumeric(value)){
								this.getMsgBean(false,"导入失败！'满分' 应为数字");
								return MsgBean;
							}
							ckExecise.setFullscore(Float.valueOf(value));
							break;
						case 2:
							if(!isNumeric(value)){
								this.getMsgBean(false,"导入失败！'平均分' 应为数字");
								return MsgBean;
							}
							ckExecise.setEvascore(Float.valueOf(value));
							break;
						case 3:
							if(!isNumeric(value)){
								this.getMsgBean(false,"导入失败！'最高分' 应为数字");
								return MsgBean;
							}
							ckExecise.setHighscore(Float.valueOf(value));
							break;
						case 4:
							if(!isNumeric(value)){
								this.getMsgBean(false,"导入失败！'最低分' 应为数字");
								return MsgBean;
							}
							ckExecise.setLowscore(Float.valueOf(value));
							break;
						}
	                	ckExecise.setLcid(lcid);
	                	ckExecise.setOprater(userId);
	                	ckExecise.setCdate(new Date());
	                }
	                cke.add(ckExecise);
	            }
			for (CusKwExecise ckExecise : cke) {
				saveOrUpdate(ckExecise);
			}
	            this.getMsgBean(true,MsgBean.EXPORT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.EXPORT_ERROR);
		}
		return MsgBean;
	}

	public PageBean getList(String lcid) {
		String sql = "select es.xtcjid as XTCJID,er.XN,xq.name XQ,er.EXAMNAME as KSMC,es.extype as TX,"+
				"es.FULLSCORE as MF,es.EVASCORE as PJF,es.HIGHSCORE as ZGF,es.LOWSCORE as ZDF "+
				"from cus_kw_execise es "+ 
				"left join cus_kw_examround er on es.lcid = er.lcid "+
				"left join SYS_ENUM_VALUE xq on er.XQM=xq.CODE and DIC_TYPE='ZD_XT_XQMC' where es.lcid = '"+lcid+"'";
		return getSQLPageBean(sql);
	}
		
	public MsgBean updateResults(List<CusKwExecise> ckeList) {
		try {
			for (CusKwExecise results : ckeList) {
				String sql = updateSql(results);
				update(sql);
			}
			this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}
	
	private String updateSql(CusKwExecise results){
		StringBuffer sb = new StringBuffer("update cus_kw_execise set ");
		int flage = 0;
		if(results.getFullscore() != null){
			sb.append(" fullscore = "+results.getFullscore()+" ,");
		}
		if(results.getEvascore() != null){
			sb.append(" evascore = "+results.getEvascore()+" ,");
		}
		if(results.getHighscore() != null){
			sb.append(" highscore = "+results.getHighscore()+" ,");
		}
		if(results.getLowscore() != null){
			sb.append(" lowscore = "+results.getLowscore()+" ,");
		}
		sb  = new StringBuffer(sb.substring(0, sb.length() - 1));
	
		sb.append(" where xtcjid = '"+results.getXtcjid()+"'");
		return sb.toString();
	}
	
	private boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("^\\d+$|-\\d+$|\\d+\\.\\d+$|-\\d+\\.\\d+$");  
	    return pattern.matcher(str).matches();     
	} 

}
