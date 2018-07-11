package com.jiajie.service.exambasic.impl;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.bean.exambasic.CusKwExamst;
import com.jiajie.bean.exambasic.ExamInfo;
import com.jiajie.bean.exambasic.ExamInfoOpt;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.filter.DownloadExamFilter;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.impl.FileUpServiceImpl;
import com.jiajie.service.exambasic.ExamService;
import com.jiajie.util.ChartGraphics;
import com.jiajie.util.DES;
import com.jiajie.util.ExamZjInfo;
import com.jiajie.util.ExportKsInfo;
import com.jiajie.util.ExportKsTask;
import com.jiajie.util.FileIoUtils;
import com.jiajie.util.FileUploadWIthBaiDu;
import com.jiajie.util.GenerateExcel;
import com.jiajie.util.ImportUtil;
import com.jiajie.util.MD5;
import com.jiajie.util.SecUtils;
import com.jiajie.util.StringUtil;
import com.jiajie.util.ZipMain;
import com.jiajie.util.ZipUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.XsInfo;
import com.sun.mail.handlers.message_rfc822;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute.Space;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.scripting.bsh.BshScriptUtils.BshExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Service("examService")
public class ExamServiceImpl extends ServiceBridge
  implements ExamService
{
  private static final Log log = LogFactory.getLog(ExamServiceImpl.class);
  public MsgBean glzp(String examround) {
	System.out.println(examround);
	//通过lcid去找参考单位编码；
    MsgBean mb = new MsgBean();
    mb.setShow(true);
    String dwbm=examround.replaceAll("'", "");
    //String dwbm="123456";
    String ZPCJ_ID = UUID.randomUUID().toString().replaceAll("-", "");
    String WJMC = dwbm;
    String CJSJ = DateFormatUtils.format(new Date(), "yyyyMMdd HH:mm:ss");
    ArrayList xxidAry = new ArrayList();
    try {
	  String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	  Session session = null;
	  SessionFactory sf = null;
	  String lj = ServletActionContext.getServletContext().getRealPath("uploadFile"+File.separator+"photo")+File.separator+dwbm+ File.separator ;
      File df = new File(lj);
      File[] dfs = df.listFiles();
      int total=0;//总照片数
      int countok=0;//成功数
      int lcnum=0;
      if (dfs != null) {
        Set set = new HashSet();
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        sf = (SessionFactory)wac.getBean("sessionFactory");
        session = sf.openSession();
         lcnum=Integer.valueOf(session.createSQLQuery("select sl from CUS_KW_EXAMROUND where lcid='"+dwbm+"'").uniqueResult()+"");
          for (int j = 0; j < dfs.length; j++)
        	  {
        		 String entryName=dfs[j].getName();
        		 System.out.println(entryName);
        		 String cardID = org.apache.commons.lang.StringUtils.split(new String(entryName.getBytes("UTF-8")), ".")[0];
        		 String type = org.apache.commons.lang.StringUtils.split(new String(entryName.getBytes("UTF-8")), ".")[1];
	        		 if(type.equals("db")){
	        			 continue;
	        		 }
	        		total++;
        	        List lstXs = checkCardID(cardID);
        	        if ((lstXs == null) || (lstXs.size() <= 0)) {
        	          String ZPCJ_MXID = UUID.randomUUID().toString().replaceAll("-", "");
        	          String WTSM = "身份证件号码" + cardID + "在系统中未找到！";
        	          String WJLJ = "";
        	          saveZPCJ_MX_SJPT(ZPCJ_MXID, ZPCJ_ID, WTSM, WJLJ);
        	        } else {
        	          String xxid = ((Map)lstXs.get(0)).get("XX_JBXX_ID").toString();
        	          String xsid=((Map)lstXs.get(0)).get("XS_JBXX_ID").toString();
        	          if (!xxidAry.contains(xxid)) {
        	            xxidAry.add(xxid);
        	          }
        	          countok++;
        	          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	          String cjsj = sdf.format(new Date());
        	          String path = dwbm+ File.separator+cardID+"."+type;
        	          String sql = "select * from zxxs_xs_pic where xs_jbxx_id='" + xsid + "' ";
        	          List list = getListSQL(sql);
        	          path = path.replaceAll("\\\\", "/");
        	          if ((list != null) && (list.size() > 0)) {
        	            sql = " update zxxs_xs_pic set path='" + path + "',gxr='" + 0 + 
        	              "',gxsj='" + cjsj + "' where xs_jbxx_id='" + xsid + "' ";
        	            update(sql);
        	          } else {
        	            sql = "insert into zxxs_xs_pic(xs_jbxx_id,path,gxr,gxsj) values ('" + xsid + "','" + path + 
        	              "','" + 0 + "','" + cjsj + "') ";
        	            insert(sql);
        	          }
        	        }
        	  }
          }
          List list = getSJPT(ZPCJ_ID);
          mb.setSuccess(true);
         
          if ((list != null) && (list.size() > 0)) {
            for (int jj = 0; jj < xxidAry.size(); jj++) {
              String XX_JBXX_ID = ((String)xxidAry.get(jj)).toString();
              saveZPCJ(ZPCJ_ID, WJMC, CJSJ, XX_JBXX_ID, "0");
            }
            mb.setMsg("有" + list.size() + "个存在数据采集问题请检查！");
            String path = FileUpServiceImpl.class.getResource("").toString();
		    path = path.substring(6, path.lastIndexOf("xjgl") + 4);
		    path=File.separator+path;
		    Workbook rwb = null;
		    WritableWorkbook wwb = null;
		    WorkbookSettings workbooksetting = new WorkbookSettings();
		    WritableSheet ws = null;
            File f = new File(path + "/export/excel/temporary/" + 
			          new Date().getTime() + ".xls");
			        wwb = Workbook.createWorkbook(
			          f, 
			          Workbook.getWorkbook(new File(
			          path + "/export/excel/temp/zp.xls")));
			        ws = wwb.getSheet(0);
			        mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
			        WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, 
			          UnderlineStyle.NO_UNDERLINE, Colour.RED);
			        WritableCellFormat wcfFC = new WritableCellFormat(wfc);
			        for (int j = 0; j < list.size(); j++) {
			          Map m =(Map)list.get(j);
			          Label l0 = new Label(0, j + 1, (j + 1)+"");
			          Label l1 = new Label(1, j + 1, m.get("WTSM")+"");
			          ws.addCell(l0);
			          ws.addCell(l1);
			        }
			        wwb.write();
			        wwb.close();
			        
            
          } else {
            for (int jj = 0; jj < xxidAry.size(); jj++) {
              String XX_JBXX_ID = ((String)xxidAry.get(jj)).toString();
              String sqlXx = "select * from ZXXS_XS_ZPCJ where ZPCJ_ID='" + ZPCJ_ID + "'";
              List lstXx = getListSQL(sqlXx);
              if (lstXx.size() > 0) {
                String sql = "update ZXXS_XS_ZPCJ set WJMC='" + WJMC + "',CJSJ='" + CJSJ + "',XX_JBXX_ID='" + XX_JBXX_ID + "',FLAG='0' where ZPCJ_ID='" + ZPCJ_ID + "'";
                update(sql);
              } else {
                saveZPCJ(ZPCJ_ID, WJMC, CJSJ, XX_JBXX_ID, "0");
              }
              //更新为审核
              StringBuffer sb = new StringBuffer();
              sb.append(" UPDATE ZXXS_XS_ZPCJ SET FLAG='1' WHERE FLAG='0' AND ZPCJ_ID='" + ZPCJ_ID + "' ");
              update(sb.toString());
          }
            mb.setMsg("关联照片成功,该轮次共有："+lcnum+" 人,其中关联照片成功："+countok);
        }
    }catch (IOException e) {
          e.printStackTrace();
          mb.setSuccess(false);
          mb.setMsg(MsgBean.EXPORT_ERROR);
        } catch (BiffException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return mb;
}
      public List<Map<String, Object>> getSJPT(String ZPCJ_ID) {
    	    String sql = "select * from ZXXS_XS_ZPCJ_MX_SJPT where ZPCJ_ID='" + ZPCJ_ID + "'";
    	    return getListSQL(sql);
    	  }
        public List<Map<String, Object>> checkCardID(String cardID) {
        	try {
        		String sql = "select XX_JBXX_ID,XS_JBXX_ID from ZXXS_XS_JBXX where sfzjh='" + cardID + "'";
                List list = getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
                return list;
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
            
          }
        public void saveZPCJ(String ZPCJ_ID, String WJMC, String CJSJ, String XX_JBXX_ID, String FLAG) {
            String sql = "insert into ZXXS_XS_ZPCJ(ZPCJ_ID,WJMC,CJSJ,XX_JBXX_ID,FLAG) values('" + ZPCJ_ID + "','" + WJMC + "','" + CJSJ + "','" + XX_JBXX_ID + "','" + FLAG + "')";
            insert(sql);
          }
          public void saveZPCJ_MX_SJPT(String ZPCJ_MXID, String ZPCJ_ID, String WTSM, String WJLJ) {
            String sql = "insert into ZXXS_XS_ZPCJ_MX_SJPT(ZPCJ_MXID,ZPCJ_ID,WTSM,WJLJ)VALUES('" + ZPCJ_MXID + "','" + ZPCJ_ID + "','" + WTSM + "','" + WJLJ + "')";
            insert(sql);
          }
          /**
      	 * 获取准考证信息
      	 */
      	public PageBean getExamCard(String sfzjh){
      		return null;
      	}
		public MsgBean exportPhontoInfo(File upload, MBspInfo bspInfo,
				String lcid) {
			// TODO Auto-generated method stub
			return null;
		}
		//上传考生信息
		  public MsgBean exportKsInfo(File file, MBspInfo bspInfo, String lcid)
		  {
		    MsgBean mb = new MsgBean();
		    mb.setSuccess(true);
		    String path = FileUpServiceImpl.class.getResource("").toString();
		    path = path.substring(6, path.lastIndexOf("xjgl") + 4);
		    List list = new ArrayList();
		    Workbook rwb = null;
		    WritableWorkbook wwb = null;
		    //WorkbookSettings 是使应用程序可以使用各种高级工作簿属性设置，若不使用则相关属性会是默认值 
		    WorkbookSettings workbooksetting = new WorkbookSettings();
		    WritableSheet ws = null;
		    int k = 0;
		    StringBuffer sb = new StringBuffer();
		    boolean flag2 = true;
		    try {
		    	
		    	workbooksetting.setCellValidationDisabled(true);
		    	rwb = Workbook.getWorkbook(file,workbooksetting);
		      Sheet rs = rwb.getSheet(0);
		      //xsl有几行数据
		      int clos = rs.getColumns();
		      //总共的数据个数（格子）
		      int rows = rs.getRows();
		      int total=0;
		      
		      HashMap rmap = (HashMap)ImportUtil.checkImportExcelModel(file, "examregistration.xls");
		      MsgBean localMsgBean1;
		      if (!((Boolean)rmap.get("success")).booleanValue()) {
		        mb.setSuccess(false);
		        mb.setMsg("您导入的模板格式错误,请下载模板导入");
		        return mb;
		      }
		      if (rows == 1) {
		        mb.setSuccess(false);
		        mb.setMsg("您导入的模板是空模板,请填充好数据再进行导入");
		        return mb;
		      }

		       clos = ((Integer)rmap.get("col")).intValue();
		      List xslist = new ArrayList();
		      String key = null;
		      ExportKsInfo eki = ExportKsInfo.getInstance();
		      Session session = null;
			  SessionFactory sf = null;
			   WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			  sf = (SessionFactory)wac.getBean("sessionFactory");
		        session = sf.openSession();
		        String SSZGJYXZDM="";//所属法制办编码
		        String SSZGJYXZMC="";//所属法制办名称
		        String yzbm="";//邮政编码
		        
		      
			  List<Map<String,String>> lsZ1 =session.createSQLQuery("select ex.dwid,o.region_edu from cus_kw_examround ex LEFT JOIN com_mems_organ o on ex.dwid=o.region_code where  lcid='"+lcid+"'").setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			  if(lsZ1==null || lsZ1.size()==0){
					 mb.setSuccess(false);
				     mb.setMsg("查不到该轮次信息");
				     return mb;
			  }else {
					SSZGJYXZDM=lsZ1.get(0).get("dwid")+"";
					SSZGJYXZMC=lsZ1.get(0).get("region_edu")+"";
					if(SSZGJYXZDM.length()>6){
					yzbm=SSZGJYXZDM.substring(0,6);
					}
			  }
			  for (int i = 1; i < rows; i++) {
			        boolean flag = true;
			        String xx_jbxx_id = null;
			        String zkdwm = null;

			        String xm = rs.getCell(1, i).getContents().trim().replace(" ", "");
			        String xb = rs.getCell(3, i).getContents().trim().replace(" ", "");
			        String sfzh = rs.getCell(2, i).getContents().trim().replace(" ", "").replace("x", "X");
			        if(sfzh==null|| sfzh.equals("")){
			        	continue;
			        }
			        total++;
			        String ckdw = rs.getCell(6, i).getContents().trim().replace(" ", "");
			        String uid=UUID.randomUUID().toString().replaceAll("-", "");
			        List<Map<String,String>> lsZ =session.createSQLQuery("select XX_JBXX_ID,SSZGJYXZDM,YZBM from zxxs_xx_jbxx where SSZGJYXZDM='"+SSZGJYXZDM+"' and   XXMC='"+ckdw+"' and dwlx='2'").setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
					if(lsZ==null || lsZ.size()==0){
						 String sql = "insert into zxxs_xx_jbxx(XX_JBXX_ID,xxbsm,xxmc,SSZGJYXZDM,SSZGJYXZMC,yzbm,dwlx) values ('" + uid + "','" + System.currentTimeMillis()+"" + 
	    	              "','" + ckdw + "','"+SSZGJYXZDM+"','"+SSZGJYXZMC+"','"+yzbm+"','" + 2 + "') ";
						  session.createSQLQuery(sql).executeUpdate();
						  session.flush();
						  System.out.println(sql);
					}else {
						
					}
			        String gw = rs.getCell(7, i).getContents().trim().replace(" ", "");
			        if ((i == 1) && ((sfzh == null) || ("".equals(sfzh)))) {
			          mb.setSuccess(false);
			          mb.setMsg("第一行数据的学生身份证不能为空");
			          return mb;
			        }
			        if (key == null) {
			          key = lcid + "," + sfzh;
			          
			          int st = eki.isGoOk(key);
			          if (st == 0) {
			            mb.setSuccess(false);
			            mb.setMsg("当前较多人数导入，请稍后再试!");
			            return mb;
			          }
			          if (st == -1) {
			            mb.setSuccess(false);
			            mb.setMsg("当前导入的文件,已有人正在导入!");
			            return mb;
			          }
			        }
			        XsInfo xs = new XsInfo();
			        xs.setSfzjh(sfzh);
			        xs.setStuName(xm);
			        xs.setParticipatingUntisCode(SSZGJYXZDM);//所属组考单位编码
			        xs.setParticipatingUntisName(ckdw);
			        xs.setStuSex(xb);
			        xs.setWorkspace(gw);
			        xs.setKsbh(i);
			        xslist.add(xs);
			      }
		      int statu = eki.isGoOk(key, xslist);
		      List errXs = null;
		      if (statu > 0) {
		    	  //线程池
		        ScheduledExecutorService service = 
		          Executors.newScheduledThreadPool(statu);
		        for (int i = 0; i < statu; i++) {
		          service.schedule(new ExportKsTask(key), 1L, 
		            TimeUnit.MILLISECONDS);
		        }
		        while (!eki.getStatuByKey(key)) {
		          System.out.print("");
		        }
		        //xecutorService停止接收新的任务并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。当所有提交任务执行完毕，线程池即被关闭。
		        service.shutdown();

		        eki.gobackUesdThreads(statu);
		        errXs = eki.getErrorData(key);
		        eki.removeTaskByKey(key);
		        if ((errXs != null) && (errXs.size() > 0))
		          flag2 = false;
		      }
		      else {
		        mb.setSuccess(false);
		        if (statu == 0)
		          mb.setMsg("当前较多人数导入，请稍后再试!");
		        else if (statu == -1) {
		          mb.setMsg("当前导入的文件,已有人正在导入!");
		        }
		        return mb;
		      }
		      int n = Integer.parseInt(getSession().createSQLQuery("select count(1) from cus_kw_examinee where lcid = '" + lcid + "'").uniqueResult().toString());
		      System.out.println("总人数为："+n);
		      getSession().createSQLQuery("update CUS_KW_EXAMROUND set sl = " + n + " where lcid = '" + lcid + "'").executeUpdate();
		      String mString="";
		      if (flag2) {
		        mb.setMsg("全部导入完成!一共导入" + (rows - 1) + "条!");
		      } else {
		        File f = new File(path + "/export/excel/temporary/" + 
		          new Date().getTime() + ".xls");
		        wwb = Workbook.createWorkbook(
		          f, 
		          Workbook.getWorkbook(new File(
		          path + "/export/excel/temp/examregistration.xls")));
		        ws = wwb.getSheet(0);
		        mb.setSuccess(false);
		        mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
		        // Jxl操作Excel设置背景、字体颜色、对齐方式、列的宽度
		        WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, 
		          UnderlineStyle.NO_UNDERLINE, Colour.RED);
		        WritableCellFormat wcfFC = new WritableCellFormat(wfc);
		        for (int j = 0; j < errXs.size(); j++) {
		          Label l0 = new Label(0, j + 1, (j + 1)+"");
		          Label l1 = new Label(1, j + 1, ((XsInfo)errXs.get(j)).getStuName());
		          Label l2 = new Label(2, j + 1, ((XsInfo)errXs.get(j)).getSfzjh());
		          Label l3 = new Label(3, j + 1, ((XsInfo)errXs.get(j)).getStuSex());
		          Label l4 = new Label(4, j + 1, "");
		          Label l5 = new Label(5, j + 1, "");
		          Label l6 = new Label(6, j + 1, ((XsInfo)errXs.get(j)).getParticipatingUntisName());
		          Label l7 = new Label(7, j + 1, ((XsInfo)errXs.get(j)).getWorkspace());//岗位
		          Label l8 = new Label(8, j + 1, "");
		          Label l9 = new Label(9, j + 1, ((XsInfo)errXs.get(j)).getErrMsg(), wcfFC);
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
		        }
		        mb.setMsg("导入完成!共导入" + (rows - 1) + "条!<br>失败" + errXs.size() + "条!<br><font color='red'>确认之后返回整理好的失败记录文件<br>可继续导入</font>");
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
		public PageBean searchSore(String lcid,String kmid,String kdid,String kcid,String sfzh,String xuexiao,String pageindex,String pagesize) {
			StringBuffer sb=new StringBuffer();
			
			sb.append("SELECT o.REGION_EDU,xxxx.xxmc as XXMC,ex.xm as XM,case when jbxx.XBM=1 then '男' else  '女' end as XB,ex.SFZJH,e.STARTTIME,b.BUILDNAME,kr.ROOMNAME,ex.ZWH,")
			.append("ifnull(ex.bh,0) as SCORE,ifnull(ex.nj,'未开考') AS KSJG,")
			.append("case when  ISKS is null  then '' when ISKS=1 then ''")
		    .append(" else  '缺考' end as BZ,case when ex.istj= 1 then '是' else '否' end as CJZT FROM cus_kw_examinee ex LEFT JOIN zxxs_xs_jbxx jbxx ON ex.XS_JBXX_ID = jbxx.XS_JBXX_ID LEFT JOIN com_mems_organ o ON jbxx.XXSSZGJYXZDM = o.REGION_CODE")
		    .append(" LEFT JOIN zxxs_xx_jbxx xxxx ON jbxx.XX_JBXX_ID = xxxx.XX_JBXX_ID LEFT JOIN cus_kw_examschedule e ON ex.kmid = e.kmid LEFT JOIN cus_kw_examroom room ON ex.kcid = room.kcid JOIN cus_kw_room kr ON room.roomid = kr.fjid")
		    .append(" LEFT JOIN cus_kw_building b ON kr.lfid = b.lf_id").append(" where ex.lcid='").append(lcid).append("'");
			if (StringUtil.isNotNullOrEmpty(xuexiao).booleanValue()) {
			      sb.append(" and zxs.xx_jbxx_id in( ").append(xuexiao).append(")");
			    }
			
			if(StringUtil.isNotNullOrEmpty(kmid))
				sb.append(" and ex.kmid='").append(kmid).append("'");
			if(StringUtil.isNotNullOrEmpty(kdid)){
				sb.append(" and ex.kdid='").append(kdid).append("'");
			}
			if(StringUtil.isNotNullOrEmpty(kcid)){
				sb.append(" and ex.kcid='").append(kcid).append("'");
			}
			if(StringUtil.isNotNullOrEmpty(sfzh)){
				sb.append(" and (ex.sfzjh like '%").append(sfzh).append("%' or ex.xm like '%").append(sfzh).append("%' )" );
			}
//			if(StringUtil.isNotNullOrEmpty(xm)){
//				sb.append(" and ex.xm like '%").append(xm).append("%'");
//			}
			sb.append(" order by ex.ksid desc ");
			System.out.println(sb.toString());
			return getSQLPageBean(sb.toString(),Integer.valueOf(pageindex),Integer.valueOf(pagesize));
		}
		public MsgBean createScore(String lcid, String kmid, String kdid,
				String kcid, String sfzh, String xuexiao) {
			int total=0;
			int ok=0;
			try {
				StringBuffer sb=new StringBuffer();
				sb.append("SELECT * from exam_score_detail where type=0");
				List<Map<String,Object>> lm = getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				ChartGraphics cg = new ChartGraphics();
//				String path = "/var/www/kaoshi.worldve.com/ksgl/ksgltest/ksnibuhuimingbai/";
				total=lm.size();
				String path="/var/www/kaoshi.worldve.com/xjgl/xjgl/socresImg";
				//String path = "D:/Java/tomcat/apache-tomcat-7.0.82/webapps/xjgl/socresImg";
				for (int i = 0; i < lm.size(); i++) {
					
					System.out.println("开始生成："+(i+1)+" 个");
					String sfz = (String)lm.get(i).get("sfzjh");
					String xm = (String)lm.get(i).get("xm");
					String score = lm.get(i).get("score")+"";//分数
					String sfhg = lm.get(i).get("sfhg")+"";
					try {
						File f = new File(path+File.separator+sfz.substring(0,6));
						if(!f.exists()){
							f.mkdirs();
						}
						String url = path+File.separator+sfz.substring(0,6)+File.separator+SecUtils.encode(sfz+xm).replace("/","-")+".jpg";
						cg.graphicsGeneration(xm, sfz, score, sfhg, url);
						ok++;
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			String msg="满足该查询的共有："+total+"其中生成："+ok+"人！";
			this.getMsgBean(true, msg);
			return MsgBean;
		}
		public static void main(String[] args) {
			ChartGraphics cg = new ChartGraphics();
			String sfz = "43048119850718001X";
			String xm = "阳海波";
			String score = "58";//分数
			String sfhg = "未合格";
			String path = "D:/Java/tomcat/apache-tomcat-7.0.82/webapps/xjgl/socresImg";
			String url = null;
			try {
				File f = new File(path+File.separator+sfz.substring(0,6));
				if(!f.exists()){
					f.mkdirs();
				}
				url = path+File.separator+sfz.substring(0,6)+File.separator+SecUtils.encode(sfz+xm).replace("/","-")+".jpg";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cg.graphicsGeneration(xm, sfz, score, sfhg, url);
			
		}
		public PageBean searchSore(String lcid, String kmid, String roomid,
				String sfzh, String xm) {
			// TODO Auto-generated method stub
			return null;
		}
		public Object queryScore(String xm, String sfzh) {
			System.out.println("coreaaaaa");
			String path="/var/www/kaoshi.worldve.com/xjgl/xjgl/socresImg";
			//String path = "D:/Java/tomcat/apache-tomcat-7.0.82/webapps/xjgl/socresImg";
			try {
				String url=path+File.separator+sfzh.substring(0,6)+File.separator+SecUtils.encode(sfzh+xm).replace("/","-")+".jpg";
				File f = new File(url);
				if(f.exists()){
					return "socresImg"+File.separator+sfzh.substring(0,6)+File.separator+SecUtils.encode(sfzh+xm).replace("/","-")+".jpg";
				}
				else {
					if(xm.length()>1){
						xm=xm.substring(0,1)+"　"+xm.substring(1,2);
					}
					url=path+File.separator+sfzh.substring(0,6)+File.separator+SecUtils.encode(sfzh+xm).replace("/","-")+".jpg";
					 f = new File(url);
					 if(f.exists()){
						return "socresImg"+File.separator+sfzh.substring(0,6)+File.separator+SecUtils.encode(sfzh+xm).replace("/","-")+".jpg";
					}
					 else
					 {
						 return "no";
					 }
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		public com.jiajie.bean.MsgBean getExamKdDetail() {
			Session session= getSession();
			GenerateExcel ge = new GenerateExcel();
			List<Map<String,String>> list = getSession().createSQLQuery("select lf_id as kdbh,BUILDNAME as kdmc,towncity as city FROM cus_kw_building").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			ZipUtil zip = new ZipUtil();
			Random rd  = new Random(0);
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> map = ge.getDetailByKd(session, list.get(i).get("kdbh"), list.get(i).get("kdmc"),list.get(i).get("city"), 50);
				if((Boolean)map.get("success")){
					String code = Math.abs(rd.nextInt())+""; 
					getSession().createSQLQuery("insert into exam_mm(kdbh,kdmc,code) values('"+list.get(i).get("kdbh")+"','"+list.get(i).get("kdmc")+"','"+code+"')").executeUpdate();
					zip.zipFileAndEncode(map.get("data").toString(), code);
				}
			} 
			//this.s
			return null;
		}
		public PageBean getscoreList(String pageindex, String pagesize,String sfzhjh) {
			StringBuffer sb=new StringBuffer();
			sb.append("select * from exam_score_detail where 1=1 ");
			if(StringUtil.isNotNullOrEmpty(sfzhjh)){
				sb.append(" and (sfzjh like '%").append(sfzhjh).append("%' or xm like '%").append(sfzhjh).append("%') ");
			}
			System.out.println(sb.toString());
			return getSQLPageBean(sb.toString(),Integer.valueOf(pageindex),Integer.valueOf(pagesize));
		}
		public MsgBean getExamZkdwDetail() {
			Session session= getSession();
			GenerateExcel ge = new GenerateExcel();
//			List<Map<String,String>> list = getSession().createSQLQuery("select ckdw,zkdw,'湖南省' as city from exam_score_detail where (select towncity from cus_kw_building where LF_ID =  kdbh) = '湖南省' group by ckdw,zkdw").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//			ZipUtil zip = new ZipUtil();
//			Random rd  = new Random(1);
//			for (int i = 0; i < list.size(); i++) {
//				System.out.println("  = = = = = "+i+"  = = = = = ");
//				Map<String,Object> map = ge.getDetailByCkdw(session, list.get(i).get("zkdw"), list.get(i).get("ckdw"),list.get(i).get("city"), 50);
//				if((Boolean)map.get("success")){
//					String code = Math.abs(rd.nextInt())+""; 
//					getSession().createSQLQuery("insert into exam_mm_ckdw(zkdw,ckdw,code) values('"+list.get(i).get("zkdw")+"','"+list.get(i).get("ckdw")+"','"+code+"')").executeUpdate();
//					zip.zipFileAndEncode(map.get("data").toString(), code);
//				}
//			} 
//			System.out.println("ok");
			  MsgBean mb = new MsgBean();
			    mb.setShow(true);
			List<Map<String,String>> list = getSession().createSQLQuery("select region_edu as zkdw,LEFT(region_edu,3) as city From com_mems_organ where region_edu not like '%湘西%'  UNION all select region_edu as zkdw,LEFT(region_edu,2) as city From com_mems_organ where region_edu  like '%湘西%'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			ZipUtil zip = new ZipUtil();
			Random rd  = new Random(1);
			try {
				for (int i = 0; i < list.size(); i++) {
					System.out.println("  = = = = = "+i+"  = = = = = ");
					
					Map<String,Object> map = ge.getDetailByZkdw(session, list.get(i).get("zkdw")+"",list.get(i).get("city")+"", 50);
					if((Boolean)map.get("success")){
						String code = Math.abs(rd.nextInt())+""; 
						getSession().createSQLQuery("insert into exam_mm_zkdw(zkdw,code) values('"+list.get(i).get("zkdw")+"','"+code+"')").executeUpdate();
						zip.zipFileAndEncode(map.get("data").toString(), code);
					}
				} 
				mb.setMsg("生成成功");
				mb.setSuccess(true);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return mb;
		}

		  public MsgBean exportKswj(File file, MBspInfo bspInfo)
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
		      
		      HashMap rmap = (HashMap)ImportUtil.checkImportExcelModel(file, "wj.xls");
		      MsgBean localMsgBean1;
		      if (!((Boolean)rmap.get("success")).booleanValue()) {
		        mb.setSuccess(false);
		        mb.setMsg("您导入的模板格式错误,请下载模板导入");
		        return mb;
		      }
		      if (rows == 1) {
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
		      for (int i = 3; i < rows; i++) {
		        String xm = rs.getCell(1, i).getContents().trim().replace(" ", "");
		        String sfzh = rs.getCell(2, i).getContents().trim().replace(" ", "").replace("x", "X");;
		        String kssj = rs.getCell(3, i).getContents().trim().replace(" ", "");
		        String bz = rs.getCell(4, i).getContents().trim().replace(" ", "");
		        String sm = rs.getCell(5, i).getContents().trim().replace(" ", "");
		        if(sfzh==null|| sfzh.equals("")){
		        	continue;
		        }
		       if ((i == 3) && ((sfzh == null) || ("".equals(sfzh)))) {
		          mb.setSuccess(false);
		          mb.setMsg("第一行数据的身份证不能为空");
		          return mb;
		        }
		        String sqlStr="select * from exam_score_detail where sfzjh='"+sfzh+"'";
		        List<Map<String,String>> maplist = session.createSQLQuery(sqlStr).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if(maplist!=null && maplist.size()>0)
				{
					Map map =maplist.get(0);
					String id=map.get("id")+"";
					sqlStr="update exam_score_detail set score=0,sfhg='未合格',wj='"+bz+"',bz='违纪处理',yscore="+map.get("score")+" where id ="+id;
					session.createSQLQuery(sqlStr).executeUpdate();
					 total++;
				}else {
					flag2=false;
					Map map =new HashMap();
					map.put("xm", xm);
					map.put("sfzh", sfzh);
					map.put("kssj", kssj);
					map.put("bz", bz);
					map.put("sm", sm);
					map.put("msg", "未找到");
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
		          path + "/export/excel/temp/wj.xls")));
		        ws = wwb.getSheet(0);
		        mb.setSuccess(false);
		        mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
		        WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, 
		          UnderlineStyle.NO_UNDERLINE, Colour.RED);
		        WritableCellFormat wcfFC = new WritableCellFormat(wfc);
		        for (int j = 0; j < xslist.size(); j++) {
		          Label l0 = new Label(0, j + 3, (j + 1)+"");
		          Label l1 = new Label(1, j + 3, xslist.get(j).get("xm")+"");
		          Label l2 = new Label(2, j + 3, xslist.get(j).get("sfzh"));
		          Label l3 = new Label(3, j + 3, xslist.get(j).get("kssj"));
		          Label l4 = new Label(4, j + 3, xslist.get(j).get("bz"));
		          Label l5 = new Label(5, j + 3, xslist.get(j).get("sm"));
		          Label l6 = new Label(6, j + 3, xslist.get(j).get("msg"), wcfFC);
		          ws.addCell(l0);
		          ws.addCell(l1);
		          ws.addCell(l2);
		          ws.addCell(l3);
		          ws.addCell(l4);
		          ws.addCell(l5);
		          ws.addCell(l6);
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
		            if(rs.getCell(1, i).getType()==CellType.NUMBER){  
		                String tempTime = rs.getCell(1, i).getContents();   
		                long tempTimeLong = Long.valueOf(tempTime).intValue();  //将数字转化成long型   
		                  
		                long ss = (tempTimeLong - 70 * 365 - 17 - 2) * 24 * 3600 * 1000;  //excel的那个数字是距离1900年的天数   
		                                                                                          //java 是距离1970年天数，但是明明期间只有17个闰年   
		                                                                                          //我尝试的结果要减19才能正确，原因不明   
		                  
		                Date dss = new Date(ss);                              //用这个数字初始化一个Date   
		                  
		                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");   
		                  
		                String sss = formatter.format(dss); 
			            kssj=sss;
		                System.out.println("==========Number============"+sss);  
		            }else if(rs.getCell(1, i).getType()==CellType.DATE){  
		                String mystr= rs.getCell(1,i).getContents();  
		                kssj=mystr;
		            }  
		            System.out.println(sfzh);
		            
		            System.out.println(sfzh1);
			       if ( ((sfzh == null) || ("".equals(sfzh)))||((sfzh1 == null) || ("".equals(sfzh1)))) {
			          mb.setSuccess(false);
			          mb.setMsg("原身份证跟正确身份证不能为空！");
			          return mb;
			        }
			       if ( ((xb == null) || ("".equals(xb)))||((xb1 == null) || ("".equals(xb1)))) {
				          mb.setSuccess(false);
				          mb.setMsg("原性别跟更正性别不能为空！");
				          return mb;
				        }
			        String sqlStr="select * from exam_score_detail where sfzjh='"+sfzh+"'";
			        List<Map<String,String>> maplist = session.createSQLQuery(sqlStr).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
					if(maplist!=null && maplist.size()>0)
					{
						Map map =maplist.get(0);
						String id=map.get("id")+"";
						 if ( ((ckdw != null) && (!"".equals(ckdw)))&&((ckdw1 != null) && (!"".equals(ckdw1)))) {
							 sqlStr="update exam_score_detail set sfzjh='"+sfzh1+"',ckdw='"+ckdw1+"',xm='"+xm1+"',xb='"+xb1+"',bz='信息更正',ysfzh='"+map.get("sfzjh")+"' ,yxb='"+map.get("xb")+"',yxm='"+map.get("xm")+"',yckdw='"+map.get("ckdw")+"' where id ="+id;
					        }
						 else{
							 sqlStr="update exam_score_detail set sfzjh='"+sfzh1+"',xm='"+xm1+"',xb='"+xb1+"',bz='信息更正',ysfzh='"+map.get("sfzjh")+"' ,yxb='"+map.get("xb")+"',yxm='"+map.get("xm")+"',yckdw='"+map.get("ckdw")+"' where id ="+id;
						 }
						
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
		public MsgBean exportXls(String lcid, String kmid, String kcid,
				String kdid,String xuexiao) {
			
			
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
	               .append(" JOIN cus_kw_room kr ON room.roomid = kr.fjid LEFT JOIN cus_kw_building b ON kr.lfid = b.lf_id  where 1=1 ");
	           if(StringUtil.isNotNullOrEmpty(lcid)){
	        	   sb.append(" and ex.lcid='").append(lcid).append("'");
	           }
	           if(StringUtil.isNotNullOrEmpty(kmid)){
	        	   sb.append(" and ex.kmid='").append(kmid).append("'");
	           }
	           if(StringUtil.isNotNullOrEmpty(kcid)){
	        	   sb.append(" and ex.kcid='").append(kcid).append("'");
	           }
	           if(StringUtil.isNotNullOrEmpty(kdid)){
	        	   sb.append(" and ex.kdid='").append(kdid).append("'");
	           }
	           if ((xuexiao != null) && (!"".equals(xuexiao))) {
	        	      sb.append(" AND jbxx.bmdz in ('" + xuexiao.replaceAll(",", "','") + "')");
	           }
	           sb.append(" ORDER BY kssj");
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
				// TODO: handle exception
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
		public MsgBean creatmm() {
				MsgBean mb = new MsgBean();
			    mb.setSuccess(true);
			    boolean re=true;
			    if(re){
			    	return mb;//关闭接口
			    }
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
					 path=File.separator+path;
					  File f = new File(path + "/export/excel/temporary/" + 
					          new Date().getTime() + ".xls");
					wwb = Workbook.createWorkbook(
							f,
							Workbook.getWorkbook(new File(
									File.separator+path+"/export/excel/temp/mm.xls")));
			      Sheet rs = wwb.getSheet(0);
			      int clos = rs.getColumns();
			      int rows = rs.getRows();
			      int total=0;
			      List<Map<String, String>> xslist = new ArrayList();
			      String key = null;
			      Session session = null;
				  SessionFactory sf = null;
				   WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
				  sf = (SessionFactory)wac.getBean("sessionFactory");
			        session = sf.openSession();
			        List  list1 =new ArrayList();
			      for (int i = 2; i < rows; i++) {
			        String kssj ="";
			        String yhzh = rs.getCell(5, i).getContents().trim().replace(" ", "");
			        String kdmc = rs.getCell(1, i).getContents().trim().replace(" ", "");
			        String kddz = rs.getCell(2, i).getContents().trim().replace(" ", "");
			        String lc = rs.getCell(3, i).getContents().trim().replace(" ", "");
			        String kcmc = rs.getCell(4, i).getContents().trim().replace(" ", "");
			        String zh = rs.getCell(5, i).getContents().trim().replace(" ", "");
			       
			        String sqlStr="select * from t_qxgl_userinfo where USERTYPE=2 and loginid ='"+yhzh+"'";
			        List<Map<String,String>> maplist = session.createSQLQuery(sqlStr).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
					
			        if(maplist!=null && maplist.size()>0)
					{
						Map map =maplist.get(0);
						String id=map.get("USERCODE")+"";
						if(list1.contains(zh)){
							Map map1 =new HashMap();
							map1.put("kdmc", kdmc);
							map1.put("kddz", kddz);
							map1.put("lc", lc);
							map1.put("kcmc", kcmc);
							map1.put("zh", zh);
							map1.put("mm", "");
							xslist.add(map1);
							System.out.println("11111111111111111111111111111");
							continue;
						}
						String pwdString=getStringRandom(7).toLowerCase();
						sqlStr="update t_qxgl_userinfo set loginpwd='"+MD5.md5(pwdString)+"' where usercode ='"+id+"'";
						session.createSQLQuery(sqlStr).executeUpdate();
						Map map1 =new HashMap();
						map1.put("kdmc", kdmc);
						map1.put("kddz", kddz);
						map1.put("lc", lc);
						map1.put("kcmc", kcmc);
						map1.put("zh", zh);
						map1.put("mm", pwdString);
						xslist.add(map1);
						list1.add(zh);
					}
			      }
			         f = new File(path + "/export/excel/temporary/" + 
			          new Date().getTime() + ".xls");
			        wwb = Workbook.createWorkbook(
			          f, 
			          Workbook.getWorkbook(new File(
			          path + "/export/excel/temp/mm.xls")));
			        ws = wwb.getSheet(0);
			        mb.setSuccess(false);
			        mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
			        WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, 
			          UnderlineStyle.NO_UNDERLINE, Colour.RED);
			        WritableCellFormat wcfFC = new WritableCellFormat(wfc);
			        for (int j = 0; j < xslist.size(); j++) {
			          Label l0 = new Label(0, j + 2, (j + 1)+"");
			          Label l1 = new Label(1, j + 2, xslist.get(j).get("kdmc")+"");
			          Label l2 = new Label(2, j + 2, xslist.get(j).get("kddz"));
			          Label l3 = new Label(3, j + 2, xslist.get(j).get("lc"));
			          Label l4 = new Label(4, j + 2, xslist.get(j).get("kcmc"));
			          Label l5 = new Label(5, j + 2, xslist.get(j).get("zh"));
			          Label l6 = new Label(6, j + 2, xslist.get(j).get("mm"),wcfFC);
			          ws.addCell(l0);
			          ws.addCell(l1);
			          ws.addCell(l2);
			          ws.addCell(l3);
			          ws.addCell(l4);
			          ws.addCell(l5);
			          ws.addCell(l6);
			        
			        }
			       try {
			    	   if(wwb==null){
			    		   System.out.println("1111111111111111111");
			    	   }
			    	   wwb.write();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			       
			       
			      
			    } catch (Exception e) {
			    	
			      e.printStackTrace();
			      return mb;
			     
			    }
			    finally
			    {
			      try
			      {
			    	  wwb.close();
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
		//生成随机数字和字母,  
	    public String getStringRandom(int length) {  

	        String val = "";  
	        Random random = new Random();        
	        //length为几位密码 
	        for(int i = 0; i < length; i++) {          
	            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
	            //输出字母还是数字  
	            if( "char".equalsIgnoreCase(charOrNum) ) {  
	                //输出是大写字母还是小写字母  
	                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
	                val += (char)(random.nextInt(26) + temp);  
	            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
	                val += String.valueOf(random.nextInt(10));  
	            }  
	        }  
	        return val;  
	    }
		public MsgBean checksj() {
			 Session session = null;
			 SessionFactory sf = null;
			String lj = ExamzjServiceImpl.class.getResource("").toString().replace("file:/", "").replace("classes/com/jiajie/service/exambasic/impl/", "") + "sj";
	        File df = new File(lj);
	        File[] dfs = df.listFiles();
	        if (dfs != null) {
	          Set set = new HashSet();
	          WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	          sf = (SessionFactory)wac.getBean("sessionFactory");
	          session = sf.openSession();
	          String ksid="";
	          for (int i = dfs.length-1; i >=0; i--) {//  int i = 0; i < dfs.length; i++  zhangxin  edit
	            if (!dfs[i].isDirectory())
	              continue;
	            File[] fs = dfs[i].listFiles();
	            for (int j = 0; j < fs.length; j++) {
	                ksid = fs[j].getName().split(".")[0];
	                String sqlString ="select  *from cus_kw_examinee where ksid='"+ksid+"'";
	                List list =session.createSQLQuery(sqlString).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	                if(list==null||list.size()==0){
	                	System.out.println("file:"+fs[j].getName());
	                	
	                	continue;
	                }
	                System.out.println(j);
	        }}
	          }
	          return null;  
		}
		public MsgBean ruku() {
			int okcoun=0;
			Session session = null;
			SessionFactory sf = null;
			FileInputStream fi =null;  
			ObjectInputStream in = null;
			PreparedStatement ps = null;
			Connection conn = null;
			ResultSet rs  = null;
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext(); 
			
		    String path = FileUpServiceImpl.class.getResource("").toString();
		    path = path.substring(6, path.lastIndexOf("xjgl") + 4);
		    path=File.separator+path+File.separator+"sj";
			
			String lj =path ;
			System.out.println(lj);
	        File df = new File(lj);
	        File[] fs = df.listFiles();
	        System.out.println(fs.length);
	        if (fs != null) {
	          Set set = new HashSet();
	          sf = (SessionFactory)wac.getBean("sessionFactory");   
	          session = sf.openSession();
				try {
					conn = SessionFactoryUtils.getDataSource(sf).getConnection();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String ksid="";
	          for (int i = 0; i < fs.length; i++) {//  int i = 0; i < dfs.length; i++  zhangxin  edit
	        	  	ksid = fs[i].getName();
	        	  	System.out.println(ksid);
	                String sqlString ="select xs_jbxx_id, xxdm ,kmid,lcid,kscode,xjh,ksid,xm,concat(xbm,'') as xbm,sfzjh from   cus_kw_examinee where ksid='"+ksid+"'";
	                List list =session.createSQLQuery(sqlString).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	                if(list==null||list.size()==0){
	                	System.out.println("file:"+fs[i].getName() +"在系统没有找到");
	                	continue;
	                }
	                Map m  =(Map)list.get(0);
	                sqlString =" select *from t_ks_temp where ksid ='"+ksid+"'";
	                list = session.createSQLQuery(sqlString).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	                if(list!=null&&list.size()>0){
	                	System.out.println("file:"+fs[i].getName() +"已处理过，不能重复处理");
	                	continue;
	                }
	                double sumSco = 0;
	                String submitTime="";
	                String xsid=m.get("xs_jbxx_id")+"";
	                String sfzjh=m.get("sfzjh")+"";
	                try {
						fi = new FileInputStream(fs[i]);
						in = new ObjectInputStream(fi); 
						Map<String,String> pl= (Map<String,String>)in.readObject();
						if(pl!=null && pl.size()>0){  
							Iterator<String> iter =  pl.keySet().iterator();
							String paperId = pl.get("paperId");
							while(iter.hasNext()){
								String key = iter.next();
								if(key.equals("submitTime")){
									submitTime=pl.get(key);
									continue;
								}
								if(!"paperId".equals(key) &&key.indexOf("_")==-1){
									String da = pl.get(key);
									byte[] bt = da.replace("##","").replace(key+"_", "").getBytes(); 
									Arrays.sort(bt);
									String nd = new String(bt);
									String sc = null;
									String sql = "select case when count(1) = 0 then 0 else concat(scores,'') end from exam_info where exam_info_id = '"+key+"' and exam_answ = '"+SecUtils.encode(nd)+"'";
									ps =conn.prepareStatement(sql);
									rs = ps.executeQuery();
									while(rs.next()){
										sc  = rs.getString(1);
									}
									double sco = Double.parseDouble(sc);
									sumSco = sumSco + sco;
									conn.prepareStatement("insert into cus_exam_detail_bk2(id,paper_id,exam_info_id,xs_jbxx_id,exam_bhs,score) values(replace(uuid(),'-',''),'"+paperId+"','"+key+"','"+xsid+"','"+nd+"',"+sco+")").execute();
								}else{
								}
							}
						}
					} catch (Exception e) {
						System.out.println("///");
						e.printStackTrace();
					}
					try {
						Map<String,String> xsinfo = new HashMap<String,String>();
						xsinfo.put("xxdm", m.get("xxdm")+"");
						xsinfo.put("kmid", m.get("kmid")+"");
						xsinfo.put("lcid", m.get("lcid")+"");
						xsinfo.put("kscode", m.get("kscode")+"");
						xsinfo.put("xjh", m.get("xjh")+"");
						xsinfo.put("ksid",m.get("ksid")+"");
						xsinfo.put("xm",m.get("xm")+"");
						xsinfo.put("xbm",m.get("xbm")+""); 
						String ksjg="未合格";
						if(sumSco>=60)
							ksjg="合格";
						int istj = 1;
						int isdl = 1; 
						ps = conn.prepareCall("select max(xscjid) from cus_kw_stuscore_bk where ksid ='"+xsinfo.get("ksid")+"' and kmid = '"+xsinfo.get("kmid")+"'");
						rs = ps.executeQuery();
						String cjid = null;
						while(rs.next()){
							cjid = rs.getString(1);
						}
						if(cjid!=null){ 
							conn.prepareCall("update cus_kw_stuscore_bk set score="+sumSco+",WRITER='"+submitTime+"',scorejm = '"+SecUtils.encode(sumSco+"")+"' where xscjid ='"+cjid+"'").execute();
						}else{
							conn.prepareCall("insert into cus_kw_stuscore_bk(xscjid,lcid,kmid,examcode,xjh,score,WRITER,scorejm,ksid,xm,xbm,xxdm) values(replace(uuid(),'-',''),'"+xsinfo.get("lcid")+"','"+xsinfo.get("kmid")+"','"+xsinfo.get("kscode")+"','"+xsinfo.get("xjh")+"',"+sumSco+",'"+submitTime+"','"+SecUtils.encode(sumSco+"")+"','"+xsinfo.get("ksid")+"','"+xsinfo.get("xm")+"',"+xsinfo.get("xbm")+",'"+xsinfo.get("xxdm")+"')").execute();
						}
						conn.prepareCall("update cus_kw_examinee set nj='"+ksjg+"',BH='"+sumSco+"', isks = 1,isdl="+isdl+" ,istj = "+istj+" where ksid = '"+xsinfo.get("ksid")+"'").execute();
						String dtsj =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
						System.out.println("insert into t_ks_temp(ksid,date) values('"+ksid+"','"+dtsj+"')");
//						conn.prepareStatement("insert into t_ks_temp(ksid,date,score) values('"+ksid+"','"+dtsj+"',"+sumSco+")").execute();
//						sqlString =" select *from exam_score_detail where (sfzjh ='"+sfzjh+"' or ysfzh='"+sfzjh+"')";
//			            list = session.createSQLQuery(sqlString).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//			                if(list!=null&&list.size()>0){
//			                	Map m1 =(Map)list.get(0);
//			                	String idString=m1.get("id")+"";
//			                	conn.prepareCall("update exam_score_detail set sfhg='"+ksjg+"',score='"+sumSco+"' where id = '"+idString+"'").execute();
//			                }
						okcoun++;
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
			
	          }
	          
		}
	        MsgBean mb = new MsgBean();
	        mb.setMsg("共处理："+okcoun+"个");
		    mb.setSuccess(true);
		    return mb;
	        
		}
		public MsgBean exportPaper(String sfzh) {
			 MsgBean mb = new MsgBean();
			 WritableWorkbook wwb = null;
			 WritableSheet ws = null; 
			try {
				if("".equals(sfzh)){
					mb.setMsg("身份证不能为空");
	            	mb.setSuccess(false);
	            	return mb;
				}
				String path = FileUpServiceImpl.class.getResource("").toString();
				path = path.substring(6,path.lastIndexOf("xjgl")+4);
				 path=File.separator+path;
				  File f = new File(path + "/export/excel/temporary/" + 
				          new Date().getTime() + ".xls");
				wwb = Workbook.createWorkbook(
						f,
						Workbook.getWorkbook(new File(
								File.separator+path+"/export/excel/temp/paperinfo.xls")));
				
				ws = wwb.getSheet(0);
			   
				WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,   
	                    UnderlineStyle.NO_UNDERLINE,Colour.BLACK);   
	            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
	            StringBuffer sb=new StringBuffer();
	            sb.append("SELECT * from zxxs_xs_jbxx where sfzjh='").append(sfzh).append("'");
	            List<Map<String,Object>> list = getSession().createSQLQuery(sb.toString()).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
	            if (list==null||list.size()==0){
	            	mb.setMsg("该身份证没有找到");
	            	mb.setSuccess(false);
	            	return mb;
	            }
	            Map map =list.get(0);
	            String msg="";
	            String xs_jbxx_id =map.get("XS_JBXX_ID")+"";
	            sb.setLength(0);
	            sb.append("SELECT SCORES,EXAM_TYPE_ID,ei.exam_info_id, xs_jbxx_id,ei.EXAM_CONT,ei.EXAM_ANSW,EXAM_BHS,b.EXAM_BHS,b.SCORE FROM cus_exam_detail_bk2 b,exam_info ei,paper_basic bc WHERE ei.exam_info_id = b.exam_info_id and bc.paper_id=b.paper_id ");
	            sb.append(" and XS_JBXX_ID='").append(xs_jbxx_id).append("' GROUP BY exam_info_id,xs_jbxx_id order by EXAM_TYPE_ID ");
	            list=getListSQL(sb.toString());
	            if(list.size()<85){
	            	msg="注意：该考生做题数不足85题，只有："+list.size();
	            }else if (list.size()>85){
	            	msg="注意：该考生做题数超过85题，有："+list.size();
	            }else{
	            	msg="该考生做题数85题";
	            }
	            for (int i = 0; i < list.size(); i++) {
	            	String exam_info_id=list.get(i).get("exam_info_id").toString();
	            	String content=list.get(i).get("EXAM_CONT").toString();//试题内容
	            	content=SecUtils.decode(content);
	            	String answer=list.get(i).get("EXAM_ANSW").toString();//正确答案
	            	answer=SecUtils.decode(answer);
	            	String EXAM_TYPE_ID =list.get(i).get("EXAM_TYPE_ID").toString();
	            	if("1".equals(EXAM_TYPE_ID)){
	            		EXAM_TYPE_ID="单选题";
	            	}else if ("2".equals(EXAM_TYPE_ID)){
	            		EXAM_TYPE_ID="多选题";
	            	}else if("3".equals(EXAM_TYPE_ID)){
	            		EXAM_TYPE_ID="判断题";
	            	}else {
	            		System.out.println("no....");
	            		continue;
	            	}
	            	ws.addCell(new Label(0, i+1,EXAM_TYPE_ID,wcfFC)); //题型
	            	int   SCORES   =   (new   Double(list.get(i).get("SCORES").toString())).intValue();  
	            	ws.addCell(new Label(1, i+1,SCORES+"",wcfFC));//分值
	            	ws.addCell(new Label(2, i+1,content,wcfFC));//内容
	            	if("判断题".equals(EXAM_TYPE_ID)){
	            		ws.addCell(new Label(3, i+1,"正确",wcfFC));//正确
	            		ws.addCell(new Label(4, i+1,"错误",wcfFC));//错误
	            		ws.addCell(new Label(5, i+1,"",wcfFC));//空
	            		ws.addCell(new Label(6, i+1,"",wcfFC));//空
	            	}
	            	else{
	            		sb.setLength(0);
		                sb.append("SELECT * from exam_info_opt where  exam_info_id='").append(exam_info_id).append("'  ORDER BY  EXAM_BH");
		                List <Map <String,Object>> examInfoList=getListSQL(sb.toString());
		                //循环选项
		                for (int j = 0; j < examInfoList.size(); j++) {
							String exam_bh=examInfoList.get(j).get("EXAM_BH")+"";
							String exam_opt_des=examInfoList.get(j).get("EXAM_OPT_DES")+"";
							ws.addCell(new Label(3+j, i+1,exam_opt_des,wcfFC));//选项
						}
	            	}
	            	if(answer.equals("Y")){
	            		answer="正确";
	            	}else if (answer.equals("N")){
	            		answer="错误";
	            	}
	            	ws.addCell(new Label(7, i+1,answer,wcfFC));//正确答案
	            	String ksanswer=list.get(i).get("EXAM_BHS").toString();
	            	if(ksanswer.equals("Y")){
	            		ksanswer="正确";
	            	}else if (ksanswer.equals("N")){
	            		ksanswer="错误";
	            	}
	            	ws.addCell(new Label(8, i+1,ksanswer,wcfFC));//考生答案
	            	int   SCORE   =   (new   Double(list.get(i).get("SCORE").toString())).intValue();  
	            	ws.addCell(new Label(9, i+1,SCORE+"",wcfFC));//得分
	            	String sfdd="是";
	            	if(!answer.equals(ksanswer)){
	            		sfdd="否";
	            	}
	            	
	            	ws.addCell(new Label(10, i+1,sfdd,wcfFC));//是否答对
				}
	            mb.setSuccess(true);
			    mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
			    mb.setMsg(msg);
				wwb.write();    
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				  mb.setSuccess(false);
			}
			  finally
			    {
			      try
			      {
			    	  if(wwb!=null){
			    		  wwb.close();
			    	  }
			    	  
			      }
			      catch (WriteException e) {
			        e.printStackTrace();
			      } catch (IOException e) {
			        e.printStackTrace();
			      }
			    }
			return mb;
		}
		public com.jiajie.bean.MsgBean getExamKdDetailSDD() {
			Session session= getSession();
			GenerateExcel ge = new GenerateExcel();
			int count=0;
			List<Map<String,String>> list = getSession().createSQLQuery("SELECT  ckdw  from exam_score_detail  where  kdmc ='湖南电大考点'  GROUP BY ckdw").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			ZipUtil zip = new ZipUtil();
			Random rd  = new Random(0);
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> map = ge.getDetailByKdSDD(session, list.get(i).get("ckdw"));
				if((Boolean)map.get("success")){
					String code = Math.abs(rd.nextInt())+""; 
					count +=Integer.valueOf(map.get("count")+"");
					//getSession().createSQLQuery("insert into exam_mm(kdbh,kdmc,code) values('11111','"+list.get(i).get("ckdw")+"','"+code+"')").executeUpdate();
					//zip.zipFileAndEncode(map.get("data").toString(), code);
				}
			} 
			//this.s
			System.out.println("总共："+count+ "人");
			return new MsgBean();
		}
}