package com.jiajie.service.signup.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.impl.FileUpServiceImpl;
import com.jiajie.service.signup.uploadInformationService;
import com.jiajie.util.ImportUtil;
import com.jiajie.util.bean.MBspInfo;

@Service("uploadInformationService")
public class uploadInformationServiceImpl extends ServiceBridge implements uploadInformationService{

	@Override
	public PageBean getListPage(String xnxq,String sfzjh,MBspInfo bspInfo) {
		String sql = "select T.* from (SELECT i.XX_JBXX_ID,xx.SSZGJYXZDM,i.OLDXM,i.OLDSFZJH,i.XN,i.XQ as XQM,xq.MC as XQ,i.CDATE,xx.SSZGJYXZMC AS MC,i.OLDXXMC,IFNULL(i.NEWXM,'') AS NEWXM,IFNULL(i.NEWSFZJH,'') AS NEWSFZJH,IFNULL(i.NEWXXMC,'') AS NEWXXMC,IFNULL((case i.NEWXBM when '1' then '男' when '2' then '女' end),'') AS NEWXB,case i.STATUE when '0' then '已申请' when '1' then '已修改' when '2' then '审核不通过' end as STATUE,case OLDXBM when '1' then '男' when '2' then '女' end as OLDXB FROM cus_kw_informationupdate i left join zxxs_xx_jbxx xx on i.XX_JBXX_ID=xx.XX_JBXX_ID left join zd_xq xq on i.xq=xq.DM) T where 1=1";
		if ((xnxq != null) && (!"".equals(xnxq))) {
			String xn = xnxq.substring(0, 4);
			String xq = xnxq.substring(8, 9);
			sql += " AND T.XN='" + xn + "'" + " AND T.XQM='" + xq + "'";
		}
		if ((sfzjh != null) && (!"".equals(sfzjh))) {
			sql = sql + " AND T.OLDSFZJH='" + sfzjh + "'";
		}
		if ("4".equals(bspInfo.getUserType())) {
			sql = sql + " AND T.XX_JBXX_ID='" + bspInfo.getOrgan().getOrganCode()
					+ "'";
		}
		if ("2".equals(bspInfo.getUserType())) {
			sql = sql + " AND T.SSZGJYXZDM='" + bspInfo.getOrgan().getOrganCode()
					+ "'";
		}
		return getSQLPageBean(sql); 
	}
	
	public MsgBean exportKsxxgz(File file, MBspInfo bspInfo)
	  {
		    MsgBean mb = new MsgBean();
		    mb.setSuccess(true);
		    String path = FileUpServiceImpl.class.getResource("").toString();
		    path = path.substring(6, path.lastIndexOf("xjgl") + 4);
		    path=File.separator+path;
		    List list = new ArrayList();
		    Workbook rwb = null;
		    WritableWorkbook wwb = null;
		    WorkbookSettings workbooksetting = new WorkbookSettings();
		    WritableSheet ws = null;
		    int k = 0;
		    StringBuffer sb = new StringBuffer();
		    boolean flag2 = true;
		    try {
		    	workbooksetting .setCellValidationDisabled(true);
		    	rwb = Workbook.getWorkbook(file,workbooksetting);
		      Sheet rs = rwb.getSheet(0);
		      int clos = rs.getColumns();
		      int rows = rs.getRows();
		      int total=0;
		      
		      HashMap rmap = (HashMap)ImportUtil.checkImportExcelModel(file, "xxgz.xls");
		      MsgBean localMsgBean1;
		      if (!((Boolean)rmap.get("success")).booleanValue()) {
		        mb.setSuccess(false);
		        mb.setMsg("您导入的模板格式错误,请下载模板导入");
		        return mb;
		      }
		      if (rows == 4) {
		        mb.setSuccess(false);
		        mb.setMsg("您导入的模板是空模板,请填充好数据再进行导入");
		        return mb;
		      }
		      clos = ((Integer)rmap.get("col")).intValue();
		      List<Map<String, String>> xslist = new ArrayList();
		      String key = null;
		      Session session = null;
			  SessionFactory sf = null;
			   WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			  sf = (SessionFactory)wac.getBean("sessionFactory");
		        session = sf.openSession();
		      for (int i = 4; i < rows; i++) {
		        String kssj ="";
		    	Cell cell = rs.getCell(1, i);
		    	
		        String xm = rs.getCell(2, i).getContents().trim().replace(" ", "");
		        String xb = rs.getCell(3, i).getContents().trim().replace(" ", "");
		        String sfzh = rs.getCell(4, i).getContents().trim().replace(" ", "").replace("x", "X");
		        String ckdw = rs.getCell(5, i).getContents().trim().replace(" ", "");
		        String xm1 = rs.getCell(6, i).getContents().trim().replace(" ", "");
		        String xb1 = rs.getCell(7, i).getContents().trim().replace(" ", "");
		        String sfzh1 = rs.getCell(8, i).getContents().trim().replace(" ", "").replace("x", "X");
		        String ckdw1 = rs.getCell(9, i).getContents().trim().replace(" ", "");
		        if(sfzh==null|| sfzh.equals("")){
		        	continue;
		        }
		       if ( ((sfzh == null) || ("".equals(sfzh)))) {
		          mb.setSuccess(false);
		          mb.setMsg("原身份证不能为空！");
		          return mb;
		        }
		       if ( ((xm == null) || ("".equals(xm)))) {
			          mb.setSuccess(false);
			          mb.setMsg("原姓名不能为空！");
			          return mb;
			        }
		       if ( ((xb == null) || ("".equals(xb)))) {
			          mb.setSuccess(false);
			          mb.setMsg("原性别不能为空！");
			          return mb;
			        }
		        String sqlStr="select * from zxxs_xs_jbxx where sfzjh='"+sfzh+"'";
		        List<Map<String,String>> maplist = session.createSQLQuery(sqlStr).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if(maplist!=null && maplist.size()>0)
				{	
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String str = df.format(new Date());
					String xxjbxxid = bspInfo.getOrgan().getOrganCode();
					Map map =maplist.get(0);
					String xsjbxxid=map.get("XS_JBXX_ID")+"";
					String bmlcid = session.createSQLQuery("select BMLCID from cus_kw_examinee where SFZJH = '"+sfzh+"'").uniqueResult().toString();
					String xn = session.createSQLQuery("select XN from cus_kwbm_examround where BMLCID = '"+bmlcid+"'").uniqueResult().toString();
					String xq = session.createSQLQuery("select XQM from cus_kwbm_examround where BMLCID = '"+bmlcid+"'").uniqueResult().toString();

					 sqlStr = "INSERT INTO cus_kw_informationupdate (XS_JBXX_ID,XX_JBXX_ID,XN,XQ,OLDXM,OLDXBM,OLDSFZJH,OLDXXMC,NEWXM,NEWXBM,NEWSFZJH,NEWXXMC,CDATE,UPDATEDATE,STATUE) VALUES ('"
					+xsjbxxid+"','"+xxjbxxid+"','"+xn+"','"+xq+"','"+xm+"','"+xb+"','"+sfzh+"','"+ckdw+"','"+xm1+"','"+xb1+"','"
							 +sfzh1+"','"+ckdw1+"','"+str+"','"+""+"','"+"0"+"')";

					session.createSQLQuery(sqlStr).executeUpdate();
				    total++;
				}else {
					flag2=false;
					Map map =new HashMap();
					map.put("kssj", kssj);
					map.put("xm", xm);
					map.put("xb", xb);
					map.put("sfzh", sfzh);
					map.put("ckdw", ckdw);
					map.put("xm1", xm1);
					map.put("xb1", xb1);
					map.put("sfzh1", sfzh1);
					map.put("ckdw1", ckdw1);
					map.put("msg", "该身份证没有找到");
					xslist.add(map);
					
				}
		        
		      }
		      if (flag2) {
		        mb.setMsg("全部导入完成!一共导入" + total + "条!");
		      } else {
		        File f = new File(path + "/export/excel/temporary/" + 
		          new Date().getTime() + ".xls");
		        wwb = Workbook.createWorkbook(
		          f, 
		          Workbook.getWorkbook(new File(
		          path + "/export/excel/temp/xxgz.xls")));
		        ws = wwb.getSheet(0);
		        mb.setSuccess(false);
		        mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
		        WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, 
		          UnderlineStyle.NO_UNDERLINE, Colour.RED);
		        WritableCellFormat wcfFC = new WritableCellFormat(wfc);
		        for (int j = 0; j < xslist.size(); j++) {
		          Label l0 = new Label(0, j + 4, (j + 1)+"");
		          Label l1 = new Label(1, j + 4, xslist.get(j).get("kssj")+"");
		          Label l2 = new Label(2, j + 4, xslist.get(j).get("xm"));
		          Label l3 = new Label(3, j + 4, xslist.get(j).get("xb"));
		          Label l4 = new Label(4, j + 4, xslist.get(j).get("sfzh"));
		          Label l5 = new Label(5, j + 4, xslist.get(j).get("ckdw"));
		          Label l6 = new Label(6, j + 4, xslist.get(j).get("xm1"));
		          Label l7 = new Label(7, j + 4, xslist.get(j).get("xb1"));
		          Label l8 = new Label(8, j + 4, xslist.get(j).get("sfzh1"));
		          Label l9 = new Label(9, j + 4, xslist.get(j).get("ckdw1"));
		          Label l10 = new Label(10, j + 4, xslist.get(j).get("msg"), wcfFC);
		          ws.addCell(l0);
		          ws.addCell(l1);
		          ws.addCell(l2);
		          ws.addCell(l3);
		          ws.addCell(l4);
		          ws.addCell(l5);
		          ws.addCell(l6);
		          ws.addCell(l7);
		          ws.addCell(l8);
		          ws.addCell(l9);
		          ws.addCell(l10);
		        }
		        mb.setMsg("导入完成!共导入" + total + "条!<br>失败" + xslist.size() + "条!<br><font color='red'>确认之后返回整理好的失败记录文件<br>可继续导入</font>");
		       try {
		    	   if(wwb==null){
		    		   System.out.println("1111111111111111111");
		    	   }
		    	   wwb.write();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		       
		       
		      }
		    } catch (Exception e) {
		    	
		      e.printStackTrace();
		      return mb;
		     
		    }
		    finally
		    {
		      try
		      {
		        rwb.close();
		        if (!flag2)
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
