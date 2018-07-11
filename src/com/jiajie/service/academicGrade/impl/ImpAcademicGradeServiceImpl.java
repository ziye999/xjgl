package com.jiajie.service.academicGrade.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;

import org.springframework.stereotype.Service;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.dailyManagement.ZxxsXsXsby;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.academicGrade.ImpAcademicGradeService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
@Service("impAcademicGradeService")
@SuppressWarnings("all")
public class ImpAcademicGradeServiceImpl extends ServiceBridge implements ImpAcademicGradeService {

	public PageBean getList(String xmxjh,String schools,MBspInfo bspInfo) {
		String sql = "select fc.XS_XSBY_ID,xx.XXMC,bj.BJMC,nj.NJMC,fc.GRBSM,fc.XM,fc.BYND,fc.BYJYJD,fc.BYJYNY "+
				"FROM zxxs_xs_xsby fc "+
				"LEFT JOIN zxxs_xx_jbxx xx ON xx.xx_jbxx_id = fc.xx_jbxx_id "+
				"LEFT JOIN zxxs_xx_bjxx bj ON bj.xx_bjxx_id = fc.xx_bjxx_id "+
				"LEFT JOIN zxxs_xx_njxx nj ON nj.XX_NJXX_ID = fc.xx_njxx_id "+
				"where 1=1";		
		if("1".equals(bspInfo.getUserType())){
			if(schools != null && !"".equals(schools)){
				sql += " AND fc.xx_jbxx_id IN ('"+schools.replaceAll(",", "','")+"') ";
			}
		}else if("2".equals(bspInfo.getUserType())||"3".equals(bspInfo.getUserType())){
			if(schools != null && !"".equals(schools)){
				sql += " AND fc.xx_bjxx_id IN ('"+schools.replaceAll(",", "','")+"') ";
			}
			sql += " AND fc.xx_jbxx_id='"+bspInfo.getOrgan().getOrganCode()+"' ";
		}				
		if(xmxjh!=null&&!"".equals(xmxjh)){
			sql += " and (fc.xm like binary('%"+xmxjh+"%') or fc.GRBSM like binary('%"+xmxjh+"%'))";
		}
		return getSQLPageBean(sql); 
	}

	public com.jiajie.bean.MsgBean updateCj(String tgid, String byjyny, String byjyjd, MBspInfo bspInfo) {
		String sql = "";
		if (StringUtil.isNotNullOrEmpty(byjyny)) {
			byjyny = byjyny.split(",")[0];
		}
		if (StringUtil.isNotNullOrEmpty(byjyjd)) {
			byjyjd = byjyjd.split(",")[0];
		}
		Pattern pattern = Pattern.compile("[0-9]*");
    	Matcher isNum = pattern.matcher(byjyjd);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	if(!isNum.matches()){
    		sql = "update zxxs_xs_xsby set byjyny='"+byjyny+"',byjyjd='"+byjyjd+"',gxr='"+bspInfo.getUserId()+
    				"',gxsj='"+sdf.format(new Date())+"' where xs_xsby_id='"+tgid+"'";
    	}else{
    		sql = "update zxxs_xs_xsby set byjyny='"+byjyny+"',byjyjd='"+byjyjd+"',gxr='"+bspInfo.getUserId()+
    				"',gxsj='"+sdf.format(new Date())+"' where xs_xsby_id='"+tgid+"'";
    	}
		try {
			update(sql);
			this.getMsgBean(true,"修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"修改失败！");
		}
		return MsgBean;
	}

	public com.jiajie.bean.MsgBean delCj(String tgid) {
		String sql = "delete from zxxs_xs_xsby where xs_xsby_id in ('"+tgid.replaceAll(",", "','")+"')";
		try {
			delete(sql);
			this.getMsgBean(true,"删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,"删除失败！");
		}
		return MsgBean;
	}

	public com.jiajie.bean.MsgBean exportExcelInfo(File file,String orcode) {
		try {
			Workbook rwb = Workbook.getWorkbook(file);
			Sheet rs = rwb.getSheet(0);
			int clos=rs.getColumns();//得到所有的列
            int rows=rs.getRows();//得到所有的行
            String cwkh = "";//考号不存在
            String ycz="";//通过记录已存在
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            one:for (int i = 1; i < rows; i++) {
            	for (int j = 0; j < clos; j++) {
            		ZxxsXsXsby zxxsXsXsby = new ZxxsXsXsby();
            		//第一个是列数，第二个是行数
                	String sfzjh = rs.getCell(j++, i).getContents();
                	String xm = rs.getCell(j++, i).getContents();
                	String tgny = rs.getCell(j++, i).getContents();
                	String tgjd = rs.getCell(j++, i).getContents();
                	String tgbh = rs.getCell(j++, i).getContents();
                	if (tgny!=null&&!"".equals(tgny)) {
                		format.parse(tgny);
                	}                	
                	//如果第一列姓名为空，直接返回
                	if(sfzjh==null || "".equals(sfzjh)){
                		continue one;
                	}
                	List lsXs = findXs(sfzjh,tgbh);
                	if(lsXs!=null&&lsXs.size()>0){
                		ycz += i +",";
                		continue one;
                	}
                	List ls = findXjh(sfzjh,xm);
                	if(ls!=null&&ls.size()>0){
                		Map<String, Object> mp = (Map<String, Object>) ls.get(0);
                		String xx_y = mp.get("XX_JBXX_ID")==null?"":mp.get("XX_JBXX_ID").toString();
                		String kc_y = mp.get("XX_BJXX_ID")==null?"":mp.get("XX_BJXX_ID").toString();
                		String dj_y = mp.get("XX_NJXX_ID")==null?"":mp.get("XX_NJXX_ID").toString();
                		String xs_y = mp.get("XS_JBXX_ID").toString();
                		String xm_y = mp.get("XM").toString();
                		String xb_y = mp.get("XBM")==null?"":mp.get("XBM").toString();
                		String csrq = mp.get("CSRQ")==null?"":mp.get("CSRQ").toString();
                		String xjh = mp.get("GRBSM")==null?"":mp.get("GRBSM").toString();
                		String id = UUID.randomUUID().toString().replace("-","");                		                		
                		
                		zxxsXsXsby.setXs_xsby_id(id);                		
            			zxxsXsXsby.setXx_jbxx_id(xx_y);
            			zxxsXsXsby.setXx_bjxx_id(kc_y);
            			zxxsXsXsby.setXx_njxx_id(dj_y);
            			zxxsXsXsby.setXs_jbxx_id(xs_y);
            			zxxsXsXsby.setGrbsm(xjh);
            			zxxsXsXsby.setXm(xm_y);
            			zxxsXsXsby.setXbm(xb_y);
            			zxxsXsXsby.setCsrq(csrq);
            			zxxsXsXsby.setBynd(tgny.substring(0, 4));
            			zxxsXsXsby.setByjyny(tgny);
            			zxxsXsXsby.setBysj(tgny.substring(0, 6));
            			zxxsXsXsby.setByjyjd(tgjd);
            			zxxsXsXsby.setByzsh(tgbh);
                	}else{
                		cwkh += i +",";
                		continue one;
                	}                	                	
                	save(zxxsXsXsby);
                }
            }
            String xx = "";
            if(!"".equals(cwkh)){
            	xx += "第"+cwkh.substring(0, cwkh.length()-1)+"行考生的身份证号不正确！";
            	this.getMsgBean(true,xx);
            }
            if(!"".equals(ycz)){
            	xx += "第"+ycz.substring(0,ycz.length()-1)+"行的考生已存在相同的通过记录！";
            	this.getMsgBean(true,xx);
            }
            if("".equals(cwkh)&&"".equals(ycz)){
            	 this.getMsgBean(true,MsgBean.EXPORT_SUCCESS);
            }
		}catch (ParseException ex) {
			ex.printStackTrace();
			this.getMsgBean(false,"通过日期格式错误！");		    
		}catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.EXPORT_ERROR);
		}
		return MsgBean;
	}
	public List findXjh(String sfzjh,String xm){
		String sql = "select XX_JBXX_ID,XX_BJXX_ID,XX_NJXX_ID,XS_JBXX_ID,XM,XBM,CSRQ,GRBSM from zxxs_xs_jbxx where sfzjh='"+sfzjh+"' and xm='"+xm+"'";
		return getListSQL(sql); 
	}
	public List findXs(String kh,String tgbh){
		String sql = "select * from zxxs_xs_xsby where byzsh='"+tgbh+"'";//grbsm in (select xjh from cus_kw_examinee where kscode='"+kh+"') and 
		return getListSQL(sql); 
	}
}
