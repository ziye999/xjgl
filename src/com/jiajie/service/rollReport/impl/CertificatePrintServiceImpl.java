package com.jiajie.service.rollReport.impl; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.rollReport.CertificatePrintService;
import com.jiajie.util.bean.MBspInfo;

@Service("certificatePrintService")
public class CertificatePrintServiceImpl extends ServiceBridge implements CertificatePrintService{

	public List<Map<String,Object>> getStuCertiInfo(MBspInfo mp,String xjh,Integer zstype,String zgjyj){
		StringBuffer sql = new StringBuffer();
		String jesylx ="1";
		switch(zstype){
			case 1:
				sql.append("select (select p.path from zxxs_xs_pic p where p.xs_jbxx_id=j.xs_jbxx_id) as PATH,");
				sql.append("CONCAT('湖南省',(select bjmc from zxxs_xx_bjxx where xx_bjxx_id=j.xx_bjxx_id),'通过证书') as TITLE,");
				sql.append("'通过' as BYTYPE,j.JG,");
				sql.append("(select bjmc from zxxs_xx_bjxx where xx_bjxx_id=j.xx_bjxx_id) as XD,");
				sql.append("(select njmc from zxxs_xx_njxx where xx_njxx_id=j.xx_njxx_id) as XDNS,xj.XXMC,b.GRBSM,b.XX_JBXX_ID,b.XM,");
				sql.append("case when b.xbm=1 then '男' else '女' end as XBM,DATE_FORMAT(b.csrq,'%Y%m%d') as CSRQ,");
				sql.append("b.BYJYNY,b.BYZSH,j.SFZJH,DATE_FORMAT(b.Bysj,'%Y%m') as BYSJ,");
				sql.append("DATE_FORMAT(b.byjyny,'%Y%m') as LQSJ ");
				sql.append("From zxxs_xs_xsby b,zxxs_xs_jbxx j,zxxs_xx_jbxx xj ");
				sql.append("where b.xs_jbxx_id = j.xs_jbxx_id ");
				sql.append("and b.xx_jbxx_id = xj.xx_jbxx_id ");
				sql.append("and not exists(select 1 from CUS_XJ_PRINTRECORD t ");
				sql.append("where t.xjh = b.grbsm and t.dyflag = 1 and t.lx_m='");
				sql.append(zstype).append("') ");
				break;
			case 2:
				sql.append("select (select p.path from zxxs_xs_pic p where p.xs_jbxx_id=j.xs_jbxx_id) as PATH,");
				sql.append("CONCAT('湖南省普通',case when b.BYJYJD=1 then '初级' when b.BYJYJD=2 then '中级' when b.BYJYJD=3 then '高级' end,'结业证书') as TITLE,");
				sql.append("'不通过' as BYTYPE,j.JG,");
				sql.append("case when b.BYJYJD=1 then '初级' when b.BYJYJD=2 then '中级' when b.BYJYJD=3 then '高级' end as XD,");
				sql.append("case when b.BYJYJD=1 then '六' when b.BYJYJD=2 then '三' when b.BYJYJD=3 then '三' end as XDNS,xj.XXMC,b.GRBSM,b.XX_JBXX_ID,b.XM,");
				sql.append("case when b.xbm=1 then '男' else '女' end as XBM,DATE_FORMAT(b.csrq,'%Y%m%d') as CSRQ,");
				sql.append("b.BYJYNY,b.BYZSH,j.SFZJH,DATE_FORMAT(b.Bysj,'%Y%m') as BYSJ,");
				sql.append("DATE_FORMAT(b.byjyny,'%Y%m') as LQSJ ");
				sql.append("From zxxs_xs_xsby b,zxxs_xs_jbxx j,zxxs_xx_jbxx xj ");
				sql.append("where b.xs_jbxx_id = j.xs_jbxx_id ");
				sql.append("and b.xx_jbxx_id = xj.xx_jbxx_id ");
				sql.append("and not exists(select 1 from CUS_XJ_PRINTRECORD t ");
				sql.append("where t.xjh = b.grbsm and t.dyflag = 1 and t.lx_m='");
				sql.append(zstype).append("') ");
				break;
			case 3:
				sql.append("select (select p.path from zxxs_xs_pic p where p.xs_jbxx_id = j.xs_jbxx_id) as PATH,");
				sql.append("CONCAT('湖南省普通',case when b.BYJYJD=1 then '初级' when b.BYJYJD=2 then '中级' when b.BYJYJD=3 then '高级' end,'肄业证书') as TITLE,");
				sql.append("'不通过' as BYTYPE,j.JG,");
				sql.append("case when b.BYJYJD=1 then '初级' when b.BYJYJD=2 then '中级' when b.BYJYJD=3 then '高级' end as XD,");
				sql.append("case when b.BYJYJD=1 then '六' when b.BYJYJD=2 then '三' when b.BYJYJD=3 then '三' end as XDNS,xj.XXMC,b.GRBSM,b.XX_JBXX_ID,b.XM,");
				sql.append("case when b.xbm=1 then '男' else '女' end as XBM,DATE_FORMAT(b.csrq,'%Y%m%d') as CSRQ,");
				sql.append("b.BYJYNY,b.BYZSH,j.SFZJH,DATE_FORMAT(b.Bysj,'%Y%m') as BYSJ,");
				sql.append("DATE_FORMAT(b.byjyny,'%Y%m') as LQSJ ");
				sql.append("From zxxs_xs_xsby b,zxxs_xs_jbxx j,zxxs_xx_jbxx xj ");
				sql.append("where b.xs_jbxx_id = j.xs_jbxx_id ");
				sql.append("and b.xx_jbxx_id = xj.xx_jbxx_id ");
				sql.append("and not exists(select 1 from CUS_XJ_PRINTRECORD t ");
				sql.append("where t.xjh = b.grbsm and t.dyflag = 1 and t.lx_m='");
				sql.append(zstype).append("') ");
				sql.append("and exists (select 1 from cus_xj_breakstudy y where y.xjh=j.grbsm and y.flag=1)");
				break;
			case 4:
				sql.append("select (select p.path from zxxs_xs_pic p where p.xs_jbxx_id = j.xs_jbxx_id) as PATH,");
				sql.append("CONCAT('湖南省普通',case when b.BYJYJD=1 then '初级' when b.BYJYJD=2 then '中级' when b.BYJYJD=3 then '高级' end,'学历证明书') as TITLE,");
				sql.append("'学历证明书' as BYTYPE,j.JG,");
				sql.append("case when b.BYJYJD=1 then '初级' when b.BYJYJD=2 then '中级' when b.BYJYJD=3 then '高级' end as XD,");
				sql.append("case when b.BYJYJD=1 then '六' when b.BYJYJD=2 then '三' when b.BYJYJD=3 then '三' end as XDNS,xj.XXMC,b.GRBSM,b.XX_JBXX_ID,b.XM,");
				sql.append("case when b.xbm=1 then '男' else '女' end as XBM,DATE_FORMAT(b.csrq,'%Y%m%d') as CSRQ,");
				sql.append("b.BYJYNY,b.BYZSH,j.SFZJH,DATE_FORMAT(b.Bysj,'%Y%m') as BYSJ,");
				sql.append("DATE_FORMAT(b.byjyny,'%Y%m') as LQSJ ");
				sql.append("From zxxs_xs_xsby b,zxxs_xs_jbxx j,zxxs_xx_jbxx xj ");
				sql.append("where b.xs_jbxx_id = j.xs_jbxx_id ");
				sql.append("and b.xx_jbxx_id = xj.xx_jbxx_id ");
				sql.append("and not exists(select 1 from CUS_XJ_PRINTRECORD t ");
				sql.append("where t.xjh = b.grbsm and t.dyflag = 1 and t.lx_m='");
				sql.append(zstype).append("') ");
				break; 
		} 
		if(xjh!=null && !"".equals(xjh)){
			sql.append(" and b.grbsm like binary('%").append(xjh).append("%')");
		}
		if("3".equals(mp.getUserType())){
			sql.append(" and b.xx_jbxx_id = '").append(mp.getOrgan().getOrganCode()).append("'");
		}else if("1".equals(mp.getUserType())||"2".equals(mp.getUserType())){
			if(zgjyj != null && !"".equals(zgjyj)){
				zgjyj=zgjyj.replace(",","','");
				sql.append(" and b.xx_jbxx_id in ('").append(zgjyj).append("')");
			}else{
				sql.append(" and b.xx_jbxx_id in (");
				sql.append("select t.xx_jbxx_id ");
				sql.append("from ZXXS_XX_JBXX t,V_COM_MEMS_ORGAN v ");
				sql.append("where t.SSZGJYXZDM = v.REGION_CODE ");
				sql.append("and (v.PARENT_CODE ='").append(mp.getOrgan().getOrganCode());
				sql.append("' or v.REGION_CODE = '").append(mp.getOrgan().getOrganCode());
				sql.append("' or v.REGION_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='");
				sql.append(mp.getOrgan().getOrganCode()).append("'))").append("))");
			}
		} 
		sql.append(" order by b.BYJYJD ");
		List<Map<String,Object>> list = this.getSession().createSQLQuery(sql.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		
		
//	    StringBuffer s = new StringBuffer();
//	    s.append("SELECT *FROM (select T1.*,ROWNUM AS ROW_NUM FROM ( ").append(sql).append(") T1) WHERE ROW_NUM >").append(start).append(" and ROW_NUM <= ").append(start+limit);
//	    List<Map<String,Object>> list = this.getSession().createSQLQuery(s.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list(); 
//	    StringBuffer sb = new StringBuffer();  
//	    for (int i = 0; i < list.size(); i++) {
//	    	String kg = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
//			sb.append("<div style='background-color: white;'><br><p align='center' style='font-size: 32px'>湖南省普通小学毕业证书</p><br>");
//			sb.append("<table align='center'> <tr><td style='border: 1px solid;width: 400px;height: 350px'>");
//			sb.append(kg).append("(加盖钢印有效)<img src='' width='100px' height='110px'><br><br><br>");
//			sb.append(kg).append("学籍号:<b>").append(list.get(i).get("GRBSM")).append("</b><br>");
//			sb.append(kg).append("身份证号:<b>").append(list.get(i).get("SFZJH")).append("</b><br>");
//			sb.append(kg).append("毕业证编号:<b>").append(list.get(i).get("BYZSH")).append("</b><br>");
//			sb.append(kg).append("查询网址:<b>").append("http://www.chsi.com.cn/").append("</b><br>").append("</td> <td style='border-bottom: 1px solid;border-right: 1px solid;border-top: 1px solid;width: 400px;height: 350px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
////			sb.append("学生 周毅，性别 男，二零零零 年 五 月 一十六 日出生。于 二零零七 年 九 月至 二零一三 年 六 月在本校修业六年期满，达到小学毕业标准，准予毕业。")
//			sb.append("学生:<b>").append(list.get(i).get("XM")).append("</b>,性别 :<b>").append(list.get(i).get("XBM")).append("</b> , <b>");
//			sb.append(this.toUpperInfo(list.get(i).get("CSRQ").toString(),"yyyyMMdd")).append("</b> 出生. 于<b>");
//			sb.append(this.toUpperInfo(list.get(i).get("LQSJ").toString(),"yyyyMM")).append("</b>至<b>").append(this.toUpperInfo(list.get(i).get("BYSJ").toString(),"yyyyMM")).append("</b>在本校修业").append("<b>六</b>").append("年时间期满，达到小学毕业标准，准予毕业。");
//			sb.append("<br><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;校长(印章):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参考单位(公章):");
//			sb.append("<br><h2 align='right'>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 日 </h2>");
//			sb.append("</td><tr> </table> </div>");
//		} 
//		PageBean pb  = new PageBean();
//		int total = Integer.parseInt(this.getSession().createSQLQuery(new StringBuffer().append("select count(1) from (").append(sql).append(")").toString()).list().get(0).toString());
//		pb.setTotalPages(total);
//		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
//		Map<String,String> map = new HashMap<String, String>();
//		map.put("stuinfo",sb.toString());
//		resultList.add(map);
//		pb.setResultList(resultList);  
		return list;
	}
	
	public String toUpperInfo(String info,String code){
		String d="";
		try {
			String _code = "";
			if(code.toUpperCase().contains("M")){
				if(code.toUpperCase().contains("D")){
					_code = "yyyy-M-d";
				}else{
					_code="yyyy-M";
				}
			}
			d = new SimpleDateFormat(_code).format(new SimpleDateFormat(code).parse(info));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		Map<String,String> map = new HashMap<String, String>();
		map.put("0","零");
		map.put("1","一");
		map.put("2","二");
		map.put("3","三");
		map.put("4","四");
		map.put("5","五");
		map.put("6","六");
		map.put("7","七");
		map.put("8","八");
		map.put("9","九"); 
		map.put("10","十"); 
		map.put("11","十一"); 
		map.put("12","十二"); 
		String[] str = d.split("-");
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < str.length; j++) {
			if(j == 0){
				for (int i = 0; i < str[0].length(); i++) {
					String key = str[0].charAt(i)+"";
					String value = map.get(key);
					sb.append(value);   
				}
			}
			if(j==1){
				sb.append(" 年 ").append(map.get(str[1])).append(" 月 ");
			}
			if(j==2){
				if(str[2].length()==1 || "10".equals(str[2])){
					sb.append(map.get(str[2]));
				}else{
					for (int i = 0; i < str[2].length(); i++) {
						String key = str[2].charAt(i)+"";
						String value = map.get(key);
						if(i == 0){
							sb.append(value).append("十");
						}else{
							if(!"0".equals(key)){
								sb.append(value);
							}
						}
					}
				}
				sb.append(" 日 ");
			} 
		}		
		return sb.toString();
	}    
}
