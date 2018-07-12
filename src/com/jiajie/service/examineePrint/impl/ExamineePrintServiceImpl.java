package com.jiajie.service.examineePrint.impl;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.impl.FileUpServiceImpl;
import com.jiajie.service.examineePrint.ExamineePrintService;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@SuppressWarnings("all")
@Service("examineePrintServiceImpl")
public class ExamineePrintServiceImpl extends ServiceBridge implements
		ExamineePrintService {
	public PageBean getExamCard(String xnxq, String sfzjh, String name,
			MBspInfo bspInfo) {
//		String sql = " SELECT distinct T10.XNXQ_ID,T1.KSID,T1.KSCODE,T1.XM,T1.SFZJH,T1.XJH,T3.PATH,T6.XXDZ,(select xxmc from zxxs_xx_jbxx where xx_jbxx_id = T1.XXDM) AS XX,T8.POINTNAME,(select (select ADDRESS from cus_kw_building where LF_ID=a.lfid) from cus_kw_room a where a.FJID=T9.ROOMID) KDDZ,(select (select BUILDNAME from cus_kw_building where LF_ID=a.lfid) from cus_kw_room a where a.FJID=T9.ROOMID) BUILDNAME,(select a.FLOOR from cus_kw_room a where a.FJID=T9.ROOMID) FLOORS,(select ROOMNAME from cus_kw_room where FJID=T9.ROOMID) ROOMNAME,T9.KCBH,T1.ZWH,(select cast(CONCAT(date_format(t.STARTTIME,'%Y-%m-%d %H:%i'),'-',date_format(t.ENDTIME,'%H:%i')) as char) from cus_kw_examschedule t where t.KMID=t1.KMID) PCMC,(select mc from zd_xb where dm=T1.xbm) XB FROM CUS_KW_EXAMINEE T1 LEFT JOIN ZXXS_XS_PIC T3 ON T3.XS_JBXX_ID=T1.XS_JBXX_ID LEFT JOIN CUS_KW_EXAMSCHOOL T8 ON T1.KDID=T8.KDID AND T1.LCID=T8.LCID LEFT JOIN ZXXS_XX_JBXX T6 ON T6.XX_JBXX_ID=T8.XXDM LEFT JOIN CUS_KW_EXAMROOM T9 ON T1.KCID=T9.KCID AND T1.LCID=T9.LCID LEFT JOIN CUS_KW_EXAMROUND T10 ON T1.LCID=T10.LCID";
		String sql = "  SELECT distinct T1.LCID,T1.BMLCID,T10.XNXQ_ID,T1.KSID,T1.KSCODE,T1.XM,T1.SFZJH,T1.XJH,T3.PATH,T6.XXDZ,(select xxmc from zxxs_xx_jbxx where xx_jbxx_id = T1.XXDM) AS XX,T8.POINTNAME,(select (select ADDRESS from cus_kw_building where LF_ID=a.lfid) from cus_kw_room a where a.FJID=T9.ROOMID) KDDZ,(select (select BUILDNAME from cus_kw_building where LF_ID=a.lfid) from cus_kw_room a where a.FJID=T9.ROOMID) BUILDNAME,(select a.FLOOR from cus_kw_room a where a.FJID=T9.ROOMID) FLOORS,(select ROOMNAME from cus_kw_room where FJID=T9.ROOMID) ROOMNAME,T9.KCBH,T1.ZWH,(select cast(CONCAT(date_format(t.STARTTIME,'%Y-%m-%d %H:%i'),'-',date_format(t.ENDTIME,'%H:%i')) as char) from cus_kw_examschedule t where t.KMID=t1.KMID) PCMC,(select mc from zd_xb where dm=T1.xbm) XB FROM CUS_KW_EXAMINEE T1 LEFT JOIN ZXXS_XS_PIC T3 ON T1.XS_JBXX_ID=T3.XS_JBXX_ID LEFT JOIN CUS_KW_EXAMSCHOOL T8 ON T1.KDID=T8.KDID AND T1.LCID=T8.LCID LEFT JOIN ZXXS_XX_JBXX T6 ON T1.XXDM=T6.XX_JBXX_ID LEFT JOIN CUS_KW_EXAMROOM T9 ON T1.KCID=T9.KCID AND T1.LCID=T9.LCID JOIN CUS_KWBM_EXAMROUND T10 ON T1.BMLCID=T10.BMLCID  ";
		sql = sql + " where 1=1";

		if ((xnxq != null) && (!"".equals(xnxq))) {
			String xn = xnxq.substring(0, 4);
			String xq = xnxq.substring(8, 9);
			sql += " AND T10.XN='" + xn + "'" + " AND T10.XQM='" + xq + "'";
		}
		if ((name != null) && (!"".equals(name))) {
			sql = sql + " and T1.XM like '%" + name + "%'";
		}
		if ((sfzjh != null) && (!"".equals(sfzjh))) {
			sql = sql + " AND T1.SFZJH='" + sfzjh + "'";
		}
		if ("4".equals(bspInfo.getUserType())) {
			sql = sql + " AND T1.XXDM='" + bspInfo.getOrgan().getOrganCode()
					+ "'";
		}
		if ("2".equals(bspInfo.getUserType())) {
			List<MBspOrgan> lst = bspInfo.getOrgan().getChildSchools();
			String str = new String();
			for (int i = 0; i < lst.size(); i++) {
				if (i == lst.size() - 1) {
					str = str + "'" + lst.get(i).getOrganCode() + "'";
				} else {
					str = str + "'" + lst.get(i).getOrganCode() + "',";
				}

			}
			sql = sql + " AND T1.XXDM in (" + str + ")";
		}

		sql = sql + " ORDER BY T1.KSCODE";
		System.out.println(sql);
		List ls = getListSQL(sql);
		return getSQLPageBean(sql);
	}
	
	public MsgBean dayinZks(String xnxq,MBspInfo bspInfo){
		String xn = xnxq.substring(0, 4);
		String xq = xnxq.substring(8, 9);
		String sql = "select SFFB FROM cus_kw_examround T join cus_kwbm_examround T1 on T.lcid=T1.lcid where T1.XN='"
				+xn+"' and T1.XQM='"+xq+"' and T1.DWID='"+bspInfo.getOrgan().getOrganCode()+"'";
		List ls = getListSQL(sql);
		return getMsgBean(ls.get(0));
	}

	@Override
	public com.jiajie.bean.MsgBean exporInvoiceXls(String name,String state) {
		MsgBean mb = new MsgBean();
        WebApplicationContext wac = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
        SessionFactory sf = null;
        Session session = null;
		try {
			String path = FileUpServiceImpl.class.getResource("").toString();
			path = path.substring(6,path.lastIndexOf("xjgl")+4);
			path=File.separator+path;
			File f = new File(path + "/export/excel/temp/" +
					new Date().getTime() + ".xls");
            wwb = Workbook.createWorkbook(f,Workbook.getWorkbook(new File(File.separator+path+"/export/excel/temp/invoice.xls")));
			ws = wwb.getSheet(0);
			mb.setSuccess(true);
			mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
			WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,
					UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
			WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            wac = ContextLoader.getCurrentWebApplicationContext();
            sf = (SessionFactory)wac.getBean("sessionFactory");
            session = sf.openSession();
			StringBuffer sb=new StringBuffer();
			StringBuffer sb1=new StringBuffer();
			if(name != null && !name.equals("")){
				sb.append("SELECT * FROM cus_kw_invoice_excal WHERE ISUSE = '1' AND STATE !='0' AND NAME like binary('%" + name + "%') ");
				sb1.append("SELECT DISTINCT(BMLCID) FROM cus_kw_invoice_excal WHERE ISUSE = '1' AND STATE !='0' AND NAME like binary('%" + name + "%') ");

			}else {
				sb.append("SELECT * FROM cus_kw_invoice_excal WHERE ISUSE = '1' AND STATE !='0'");
				sb1.append("SELECT DISTINCT(BMLCID) FROM cus_kw_invoice_excal WHERE ISUSE = '1' AND STATE !='0'");
			}
			if(state != null && !state.equals("")){
				sb.append(" AND STATE ='" + state + "'");
				sb1.append(" AND STATE ='" + state + "'");
			}
			System.out.println(sb.toString());
			List<Map<String,Object>> list=session.createSQLQuery(sb.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			for (int i = 0; i < list.size(); i++) {
				ws.addCell(new Label(0, i+1,list.get(i).get("ID").toString()));
				ws.addCell(new Label(1, i+1,""));
				ws.addCell(new Label(2, i+1,list.get(i).get("NAME").toString()));
				ws.addCell(new Label(3, i+1,list.get(i).get("DUTY").toString()));
				ws.addCell(new Label(4, i+1,list.get(i).get("ADDRESS").toString()));
				ws.addCell(new Label(5, i+1,list.get(i).get("TELLPHONE")+""));
				ws.addCell(new Label(6, i+1,list.get(i).get("BANKNAME").toString()));
				ws.addCell(new Label(7, i+1,list.get(i).get("BANKNUM").toString()));
				ws.addCell(new Label(8, i+1,list.get(i).get("PHONE").toString()));
				ws.addCell(new Label(9, i+1,list.get(i).get("EMAIL").toString()));
				ws.addCell(new Label(10, i+1,list.get(i).get("REMARK").toString()));
				ws.addCell(new Label(11, i+1,list.get(i).get("GOODSNAME").toString()));
				ws.addCell(new Label(12, i+1,list.get(i).get("RATE").toString()));
				ws.addCell(new Label(13, i+1,""));
				ws.addCell(new Label(14, i+1,""));
				ws.addCell(new Label(15, i+1,list.get(i).get("NUMBER").toString()));
				ws.addCell(new Label(16, i+1,list.get(i).get("PRICE").toString()));
				ws.addCell(new Label(17, i+1,list.get(i).get("AMOUNT").toString()));
				ws.addCell(new Label(18, i+1,""));
				ws.addCell(new Label(19, i+1,""));
				ws.addCell(new Label(20, i+1,""));
				ws.addCell(new Label(21, i+1,""));
				ws.addCell(new Label(22, i+1,""));
				ws.addCell(new Label(23, i+1,""));
				String sql = "update cus_kw_invoice_excal set STATE='1' WHERE ID = '" + list.get(i).get("ID").toString() +"'";
				update(sql);
			}
			List<Map<String,Object>> list1=session.createSQLQuery(sb1.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			for(int i = 0; i < list1.size(); i++){
				String a =  list1.get(i).get("BMLCID").toString();
				String sql = "update cus_kwbm_examround set fpzt='2' WHERE BMLCID ='" + a + "'";
				update(sql);
			}
			wwb.write();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				wwb.close();
			}
			catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mb;
	}

	public MsgBean exportXls(String xnxq,String sfzjh,String name,MBspInfo bspInfo) {
		MsgBean mb = new MsgBean();
		WritableWorkbook wwb = null;
		WritableSheet ws = null; 
		try {
			String path = FileUpServiceImpl.class.getResource("").toString();
			path = path.substring(6,path.lastIndexOf("xjgl")+4);
			 path=File.separator+path;
			  File f = new File(path + "/export/excel/temporary/" + 
			          new Date().getTime() + ".xls");
			wwb = Workbook.createWorkbook(f,Workbook.getWorkbook(new File(File.separator+path+"/export/excel/temp/kdqkinfo.xls")));
			ws = wwb.getSheet(0);
		    mb.setSuccess(true);
		    mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
			WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,   
                    UnderlineStyle.NO_UNDERLINE,Colour.BLACK);   
            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            StringBuffer sb=new StringBuffer();
            sb.append("SELECT o.REGION_EDU as zkdw,xxxx.xxmc as ckdw,ex.xm,case when jbxx.XBM=1 then '男' else  '女' end as xb,ex.SFZJH as sfzjh,e.STARTTIME as kssj,b.BUILDNAME as kdmc,kr.ROOMNAME as kcmc,ex.ZWH as zwh")
            .append(" FROM cus_kw_examinee ex").append(" LEFT JOIN zxxs_xs_jbxx jbxx ON ex.XS_JBXX_ID = jbxx.XS_JBXX_ID")
             .append( " LEFT JOIN com_mems_organ o ON jbxx.XXSSZGJYXZDM = o.REGION_CODE")
             .append("  LEFT JOIN zxxs_xx_jbxx xxxx ON jbxx.XX_JBXX_ID = xxxx.XX_JBXX_ID")
              .append(" LEFT JOIN cus_kw_examschedule e ON ex.kmid = e.kmid")
               .append(" LEFT JOIN cus_kw_examroom room ON ex.kcid = room.kcid")
               .append(" JOIN cus_kw_room kr ON room.roomid = kr.fjid LEFT JOIN cus_kw_building b ON kr.lfid = b.lf_id left join cus_kwbm_examround round on ex.bmlcid=round.bmlcid where 1=1 ");
            if ((xnxq != null) && (!"".equals(xnxq))) {
    			String xn = xnxq.substring(0, 4);
    			String xq = xnxq.substring(8, 9);
    			sb.append(" AND round.XN='" + xn + "'" + " AND round.XQM='" + xq + "'");
//    			sql += " AND T10.XN='" + xn + "'" + " AND T10.XQM='" + xq + "'";
    		}
    		if ((name != null) && (!"".equals(name))) {
    			sb.append(" and ex.XM like '%" + name + "%'");
//    			sql = sql + " and T1.XM like '%" + name + "%'";
    		}
    		if ((sfzjh != null) && (!"".equals(sfzjh))) {
    			sb.append(" AND ex.SFZJH='" + sfzjh + "'");
//    			sql = sql + " AND T1.SFZJH='" + sfzjh + "'";
    		}
    		if ("4".equals(bspInfo.getUserType())) {
    			sb.append(" AND ex.XXDM='" + bspInfo.getOrgan().getOrganCode() + "'");
//    			sql = sql + " AND T1.XXDM='" + bspInfo.getOrgan().getOrganCode() + "'";
    		}
    		if ("2".equals(bspInfo.getUserType())) {
    			List<MBspOrgan> lst = bspInfo.getOrgan().getChildSchools();
    			String str = new String();
    			for (int i = 0; i < lst.size(); i++) {
    				if (i == lst.size() - 1) {
    					str = str + "'" + lst.get(i).getOrganCode() + "'";
    				} else {
    					str = str + "'" + lst.get(i).getOrganCode() + "',";
    				}
    			}
    			sb.append(" AND ex.XXDM in (" + str + ")");
//    			sql = sql + " AND T1.XXDM in (" + str + ")";
    		}
           sb.append(" ORDER BY kssj");
           System.out.println(sb.toString());
           List<Map<String,Object>> list=getListSQL(sb.toString());
           for (int i = 0; i < list.size(); i++) {
        	   ws.addCell(new Label(0, i+1,list.get(i).get("zkdw").toString(),wcfFC)); 
        	   ws.addCell(new Label(1, i+1,list.get(i).get("ckdw").toString(),wcfFC));
        	   ws.addCell(new Label(2, i+1,list.get(i).get("xm").toString(),wcfFC));
        	   ws.addCell(new Label(3, i+1,list.get(i).get("xb").toString(),wcfFC));
        	   ws.addCell(new Label(4, i+1,list.get(i).get("sfzjh").toString(),wcfFC));
        	   ws.addCell(new Label(5, i+1,list.get(i).get("kssj")+"",wcfFC));
        	   ws.addCell(new Label(6, i+1,list.get(i).get("kdmc").toString(),wcfFC));
        	   ws.addCell(new Label(7, i+1,list.get(i).get("kcmc").toString(),wcfFC));
        	   ws.addCell(new Label(8, i+1,list.get(i).get("zwh").toString(),wcfFC));
			}
			wwb.write();    
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		  finally
		    {
		      try
		      {
		    	  wwb.close();
		      }
		      catch (WriteException e) {
		        e.printStackTrace();
		      } catch (IOException e) {
		        e.printStackTrace();
		      }
		    }
		return mb;
	}
}