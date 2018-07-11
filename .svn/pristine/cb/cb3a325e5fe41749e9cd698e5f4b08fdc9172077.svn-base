package com.jiajie.service.zzjg.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.xml.crypto.Data;

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

import net.rubyeye.xmemcached.transcoders.StringTranscoder;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.openxmlformats.schemas.presentationml.x2006.main.SldDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.crypto.hash.Md5Hash;

import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jiajie.bean.PageBean;
import com.jiajie.bean.UserInfo;
import com.jiajie.bean.basicsinfo.CusKwBuilding;
import com.jiajie.bean.examArrangement.CusKwExamschool;
import com.jiajie.bean.exambasic.CusKwExamround;
import com.jiajie.bean.exambasic.CusKwbmExamround;
import com.jiajie.bean.registrationSystem.ZxxsXnxq;
import com.jiajie.bean.zzjg.CusKwCkdw;
import com.jiajie.bean.zzjg.CusKwZkdw;
import com.jiajie.dao.DaoSupportImpl;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.basicsinfo.BuildingService;
import com.jiajie.service.core.impl.FileUpServiceImpl;
import com.jiajie.service.zzjg.CkdwService;
import com.jiajie.util.ExportKsInfo;
import com.jiajie.util.ImportUtil;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.OrganTree;
import com.jiajie.bean.MsgBean;
@Service("ckdwService")
@SuppressWarnings("all")
public class CkdwServiceImpl extends ServiceBridge implements CkdwService {
	
	public PageBean getList(CusKwCkdw ckdw, String dwlx, MBspInfo bspInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append("from CusKwCkdw where 0=0 ");
		if(ckdw.getSszgjyxzdm() != null && !"".equals(ckdw.getSszgjyxzdm())) {
			sb.append(" and sszgjyxzdm = '"+ckdw.getSszgjyxzdm()+"'");
		}
		if(ckdw.getXxmc() != null && !"".equals(ckdw.getXxmc())) {
			sb.append(" and xxmc like binary('%"+ckdw.getXxmc()+"%')");
		}
		sb.append(" and dwlx = '"+dwlx+"'");
		sb.append(" order by xxbsm desc");
		return getHQLPageBean(sb.toString());
	}
		
	public MsgBean saveOrUpdateCkdw(CusKwCkdw ckdw) {
		try {
			if (StringUtil.isNotNullOrEmpty(ckdw.getXxbsm().trim())&&StringUtil.isNotNullOrEmpty(ckdw.getXxmc().trim())&&
				StringUtil.isNotNullOrEmpty(ckdw.getXxdz().trim())&&StringUtil.isNotNullOrEmpty(ckdw.getXzxm().trim())) {								
				SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd HH:mm:ss",Locale.CHINA);
				//查询参考单位
				List ls = createHQLQuery("from CusKwZkdw where regioncode ='"+ckdw.getSszgjyxzdm()+"'").list();
				if (ls.size()>0) {
					CusKwZkdw zkdw = (CusKwZkdw)ls.get(0);
					ckdw.setSszgjyxzmc(zkdw.getRegionedu());
				}
				if (ckdw.getXxjbxxid()==null||"".equals(ckdw.getXxjbxxid())) {
					if ("1".equals(ckdw.getDwlx())) {
						List lsCk = this.createSQLQuery("select * from zxxs_xx_jbxx where dwlx='1' and SSZGJYXZDM='"+ckdw.getSszgjyxzdm()+"'").list();
						if (lsCk.size()>0) {
							return this.getMsgBean(false,"每个组织单位下最多只能设置一个组考单位！");
						}
					}else {
//						List lsYb = this.createSQLQuery("select * from ZXXS_XX_JBXX where yzbm ='"+ckdw.getYzbm()+"'").list();
//						if (lsYb.size()>0) {
//							this.getMsgBean(false,"重复的参考单位邮政编码不能保存！");
//							return MsgBean;
//						}
					}
					ckdw.setXxjbxxid(null);
					ckdw.setLrsj(format.format(new java.util.Date()));					
				}else {	
//					if (!"1".equals(ckdw.getDwlx())) {
//						List lsYb = this.createSQLQuery("select * from ZXXS_XX_JBXX where yzbm ='"+ckdw.getYzbm()+
//								"' and xx_jbxx_id<>'"+ckdw.getXxjbxxid()+"'").list();
//						if (lsYb.size()>0) {
//							this.getMsgBean(false,"重复的参考单位邮政编码不能保存！");
//							return MsgBean;
//						}
//					}					
					ckdw.setGxsj(format.format(new java.util.Date()));
				}				
				
				List lsInfo = this.createSQLQuery("select * from ZXXS_XX_JBXX where XXBSM ='"+ckdw.getXxbsm()+"'").list();
				if (lsInfo.size()>0&&ckdw.getXxjbxxid()!=null) {
					String xxmc = ckdw.getXxmc();  
					String sql = "update cus_kwbm_examround set EXAMNAME ='"+ckdw.getXxmc()+"' where dwid='"+ckdw.getXxjbxxid()+"'";
					this.update(ckdw);	
				
					this.update(sql);
					this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
				}else {
					if (lsInfo.size()>0) {
						this.getMsgBean(false,"重复的参考单位内容不能保存！");
					}else{
						this.save(ckdw);
						this.getMsgBean(true,MsgBean.SAVE_SUCCESS);
					}
				}				
			}else {
				this.getMsgBean(false,"输入格式有误，不能保存！");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.SAVE_ERROR);
		}
		return MsgBean;
	}

	public MsgBean delCkdw(String xxdms) {
		try {
			List ls = createHQLQuery("from CusKwBuilding where schoolid in ("+xxdms+")").list();
			if (ls.size()>0) {
				return this.getMsgBean(false,"已存在所属的考点，不能删除！");
			}
			List lsInfo = this.createSQLQuery("select * from cus_xj_schoolcode where xx_jbxx_id in ("+xxdms+")").list();
			if (lsInfo.size()>0) {
				return this.getMsgBean(false,"已生成参考单位代码，不能删除！");
			}
			StringBuffer sb = new StringBuffer();
			sb.append("delete from ZXXS_XX_JBXX where XX_JBXX_ID in(").append(xxdms).append(")");
			return delete(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return this.getMsgBean(false,MsgBean.DEL_ERROR);
		}
	}

	public MsgBean getCkdw(String xxdm) {
		try {
			Object obj = get(CusKwCkdw.class, xxdm);
			this.getMsgBean(obj);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.GETDATA_ERROR);
		}
		return MsgBean;
	}

	public Object getZkdw(MBspInfo bspInfo) {
		/*String sql = "from CusKwZkdw where parentcode in (select regioncode from CusKwZkdw where parentcode='"+bspInfo.getOrgan().getOrganCode()+"') order by parentcode,regioncode";
		List ls = createHQLQuery(sql).list();
		JSONArray ary = new JSONArray();
		if (ls==null||ls.size()<=0) {
			sql = "from CusKwZkdw where parentcode='"+bspInfo.getOrgan().getOrganCode()+"' order by parentcode,regioncode";
			ls = createHQLQuery(sql).list();
			if (ls==null||ls.size()<=0) {
				sql = "from CusKwZkdw where regioncode='"+bspInfo.getOrgan().getOrganCode()+"' order by parentcode,regioncode";
				ls = createHQLQuery(sql).list();
			}
		}
		for(int i=0;i<ls.size();i++){
			CusKwZkdw build = (CusKwZkdw)ls.get(i);
			JSONObject o = new JSONObject();
			o.put("id", build.getRegioncode());
			o.put("code", "");
			o.put("text", build.getRegionedu());
			o.put("leaf", new Boolean(true));
			ary.add(o);
		}*/
		List<OrganTree> list=getTreeList(null,0,bspInfo);
		JSONArray array=JSONArray.fromObject(list);
		return array.toString().replaceAll("\"", "'").replaceAll("'false'", "false").replaceAll("'true'", "true");
	}
	
	private List<OrganTree> getTreeList(String parentid,int i,MBspInfo bspInfo){
		String orgcode = bspInfo.getOrgan().getOrganCode();
		String sql="";
		//or T1.SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+parentid+"') or T1.SSZGJYXZDM IN (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='"+parentid+"')))
		if("1".equals(bspInfo.getUserType()) && orgcode.endsWith("0000000000") && (parentid==null || "".equals(parentid)) && i==0 ){
			//获取省级组考单位
			sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN WHERE REGION_CODE='"+orgcode+"' ORDER BY REGION_CODE ";
		}else if(i==1 && orgcode.endsWith("0000000000") || i==0 && orgcode.endsWith("00000000")){
			//获取市级组考单位
			if(orgcode.endsWith("00000000") && i==0){
				sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN WHERE REGION_CODE='"+orgcode+"' ";
			}else{
				sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN " +
						" WHERE PARENT_CODE='"+parentid+"'";
			}
		}else if(orgcode.endsWith("000000") && i==0 || i==1 && orgcode.endsWith("00000000") || orgcode.endsWith("0000000000") && i==2 ){
			//获取县级组考单位
			if(orgcode.endsWith("000000") && i==0){
				sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN WHERE REGION_CODE='"+orgcode+"' ";
			}else{
				sql = " SELECT distinct REGION_CODE AS CODEID,REGION_EDU AS CODENAME,1 AS CODETYPE FROM COM_MEMS_ORGAN " +
						" WHERE PARENT_CODE='"+parentid+"'";
			}
		}else{
			return null;
		}
		List<Map<String, Object>> list=getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		List<OrganTree> array=new ArrayList<OrganTree>();
		for (Map<String, Object> map : list) {
			OrganTree tree=new OrganTree();
			tree.setId(StringUtil.getString(map.get("CODEID")));
			tree.setText(StringUtil.getString(map.get("CODENAME")));
			tree.setIndex(Integer.parseInt(StringUtil.getString(map.get("CODETYPE"))));
			List<OrganTree> childList=getTreeList(StringUtil.getString(map.get("CODEID")),i+1,bspInfo);
			tree.setChildren(childList);
			if(childList!=null && childList.size()>0){
				tree.setLeaf("false");
			}else{
				tree.setLeaf("true");
			}
			array.add(tree);
		}
		return array;
	}
	//查询默认年度
	@Override
	public com.jiajie.bean.MsgBean getZx() {
		String sql = "select xnmc,xxkssj,xxjssj from  zxxs_xnxq where mr=1";
		List<Object> list = getListSQL(sql);
		if(list!=null && list.size()>0){
			
			getMsgBean(list.get(0));
			
		}else{getMsgBean(null );}
	
		return this.MsgBean;

	}
	//添加报名轮次
	@Override
	public com.jiajie.bean.MsgBean saveBm(CusKwbmExamround examround,String usertype) {

		    boolean isAdd = true;
		    if (examround.getLcid() != null)
		      isAdd = false;
		    try
		    {
		      if ((!StringUtil.isNotNullOrEmpty(examround.getDwid().trim()).booleanValue()) || 
		        (!StringUtil.isNotNullOrEmpty(examround.getExamname().trim()).booleanValue()) || 
		        (!StringUtil.isNotNullOrEmpty(examround.getXnxqId().trim()).booleanValue())) {
		        getMsgBean(false, "必填项为空不能保存！");
		      } else {
		        examround.setDwid(StringUtil.isNotNullOrEmpty(examround.getDwid().split(",")[0].trim()).booleanValue() ? examround.getDwid().split(",")[0].trim() : examround.getDwid().split(",")[1].trim());

		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		        List ls = getListSQL("SELECT * from cus_kw_examround where " + ((examround.getLcid() == null) || ("".equals(examround.getLcid())) ? "" : new StringBuilder("lcid<>'").append(examround.getLcid()).append("' and ").toString()) + 
		          "dwid='" + examround.getDwid() + "' and ((STARTDATE<=date('" + format.format(examround.getStartdate()) + "') and ENDDATE>=date('" + format.format(examround.getStartdate()) + 
		          "')) or (STARTDATE<=date('" + format.format(examround.getEnddate()) + "') and ENDDATE>=date('" + format.format(examround.getEnddate()) + 
		          "')) or (STARTDATE>=date('" + format.format(examround.getStartdate()) + "') and ENDDATE<=date('" + format.format(examround.getEnddate()) + "')))");
		        if (ls.size() > 0) {
		          return getMsgBean(false, "已存时间或考点冲突的考试轮次，不能保存！");
		        }
		        String[] xqxqs = examround.getXnxqId().split(",");
		        if (xqxqs.length == 2) {
		          examround.setXn(xqxqs[0]);
		          examround.setXqm(xqxqs[1]);
		        }
		        List lst = createSQLQuery("select * from cus_kw_examround where examname='" + examround.getExamname() + "'").list();
		        if (lst.size() > 0) {
		          if ((examround.getLcid() == null) || ("".equals(examround.getLcid()))) {
		            getMsgBean(false, "重复考试轮次信息不能保存！");
		          } else {
		            update(examround);
		            if (("2".equals(usertype)) && (isAdd)) {
		              CusKwExamschool examschool = new CusKwExamschool();
		              examschool.setLcid(examround.getLcid());
		              save(examschool);
		            }
		            getMsgBean(true, MsgBean.SAVE_SUCCESS);
		          }
		        } else {
		          if (xqxqs.length == 2) {
		            getSession().saveOrUpdate(examround);
		            getMsgBean(true, MsgBean.SAVE_SUCCESS);
		          } else {
		            getMsgBean(false, MsgBean.SAVE_ERROR);
		          }
		        }
		      }
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		      getMsgBean(false, MsgBean.SAVE_ERROR);
		    }
		    return this.MsgBean;
		  }

	//查询法制办
	public List<CusKwZkdw> getFzb(String regionedu) {
  		String hql = "from CusKwZkdw where regionedu ='"+regionedu+"'";
  		return getListHQL(hql);
  	}
	//查询考试轮次
	public List<CusKwExamround> getkl(String dwid,String xnxqId){
		String hql = "from CusKwExamround where dwid ='"+dwid+"' and xnxqId = '"+xnxqId+"'";
		return  getListHQL(hql);	
	}
	public List<ZxxsXnxq> getZ(){
		String hql = "from ZxxsXnxq  where mr=1 ";
		return  getListHQL(hql);
	
	}
 	 //查询参考单位
	  	public  List<CusKwCkdw> getCk(String xxmc) {
	  		String hql = "from CusKwCkdw where xxmc = '"+xxmc+"' ";
	  		return getListHQL(hql);
	  	}
	    //参考单位用户增加
	  	  public MsgBean exportKsInfo1(File file, MBspInfo bspInfo)
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
	  	      
	  	      HashMap rmap = (HashMap)ImportUtil.checkImportExcelModel(file, "cankao.xls");
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
	  	      List<UserInfo> xslist = new ArrayList<UserInfo>();
	  	      String key = null;
	  	      ExportKsInfo eki = ExportKsInfo.getInstance();
	  	      Session session = null;
	  		  SessionFactory sf = null;
	  		  WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
	  		  sf = (SessionFactory)wac.getBean("sessionFactory");
	  		  session = sf.openSession();
	  		  int a =0;
	  		  int b =0;
	  	      for (int i = 1; i < rows; i++) {
	  	        boolean flag = true;
	  	        String xx_jbxx_id = null;
	  	        String zkdwm = null;
	  	        String xx_jbxx_id1 =null;
	  	       
	  	        String SSZGJYXZMC = rs.getCell(1, i).getContents().trim().replace(" ", ""); //法制办
	  	        String organ_sel = rs.getCell(2, i).getContents().trim().replace(" ", ""); //参考单位
	  	        String username = rs.getCell(2, i).getContents().trim().replace(" ", "");//姓名
	  	        String loginid = rs.getCell(3, i).getContents().trim().replace(" ", "");//登陆账号
	  	    
	  	        total++;//考生数量
	  	        String loginpwd = rs.getCell(4, i).getContents().trim().replace(" ", "");//登陆密码
	  
	  	        String uid=UUID.randomUUID().toString().replaceAll("-", "");
	  	        List<CusKwZkdw> zk =getFzb(SSZGJYXZMC);
	  	        String SSZGJYXZDM = "";
	  	        String yzbm ="";
	  	        for (CusKwZkdw cusKwZkdw : zk) {
					SSZGJYXZDM = cusKwZkdw.getRegioncode();
				}
	  	        if(SSZGJYXZDM.length()>6){
					yzbm=SSZGJYXZDM.substring(0,6);
	  	        }
	  	        if(SSZGJYXZDM==null||SSZGJYXZDM.equals("")){
	  	        	continue;
	  	        }
//	  	 	 String sql1 = "insert into zxxs_xx_jbxx(XX_JBXX_ID,xxbsm,xxmc,SSZGJYXZDM,SSZGJYXZMC,yzbm,dwlx) values ('" + uid + "','" + System.currentTimeMillis()+"" + 
//	  	              "','" + organ_sel + "','"+SSZGJYXZDM+"','"+SSZGJYXZMC+"','"+yzbm+"','" + 2 + "') ";
//	  				  session.createSQLQuery(sql1).executeUpdate();
//	  				  session.flush();
	  	        //查询参考单位的id，所属法制办编号，邮政编码
//	  	       int a =session.createSQLQuery("select XX_JBXX_ID,SSZGJYXZDM,YZBM from zxxs_xx_jbxx where SSZGJYXZMC='"+SSZGJYXZMC+"' and   XXMC='"+organ_sel+"' and dwlx='2'").list().size();
	  	        List<Map<String,String>> lsZ =session.createSQLQuery("select XX_JBXX_ID,SSZGJYXZDM,YZBM from zxxs_xx_jbxx where SSZGJYXZDM='"+SSZGJYXZDM+"' and   XXMC='"+organ_sel+"' and dwlx='2'").setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
	  	      
	  	        if(lsZ==null || lsZ.size()==0){
	  				//新增参考单位信息
	  				 String sql1 = "insert into zxxs_xx_jbxx(XX_JBXX_ID,xxbsm,xxmc,SSZGJYXZDM,SSZGJYXZMC,yzbm,dwlx) values ('" + uid + "','" + System.currentTimeMillis()+"" + 
	  	              "','" + organ_sel + "','"+SSZGJYXZDM+"','"+SSZGJYXZMC+"','"+yzbm+"','" + 2 + "') ";
	  				  session.createSQLQuery(sql1).executeUpdate();
	  				  session.flush();
	  				  xx_jbxx_id1 = uid;
	  				  	System.out.println(sql1);
	  				  	a++;
	  		   }else {
	  			  xx_jbxx_id1 = lsZ.get(0).get("XX_JBXX_ID");
				}
	  	    String lcid = null;
	  	    String xnmc =null;
	  	    String xqmc = null;
	  	    String xxkssj = null;
	  	    String xxjssj =null;
	  	    String xnxString =null;
	  	    String dwid =null;
	  	    String dwlx=null;
	  	    List<ZxxsXnxq> zx = getZ();
			for (ZxxsXnxq zxxsXnxq : zx) {
				xnmc = zxxsXnxq.getXnmc();
				xqmc = zxxsXnxq.getXqmc();
				xxjssj = zxxsXnxq.getXxjssj();
				xxkssj = zxxsXnxq.getXxkssj();
				xnxString= zxxsXnxq.getXnmc()+","+zxxsXnxq.getXqmc();
				
			}
			List<CusKwExamround> cu = getkl(SSZGJYXZDM,xnxString);
		    for (CusKwExamround cusKwExamround : cu) {
				lcid = cusKwExamround.getLcid();
			}
	  	  
	  		CusKwbmExamround  examround = new CusKwbmExamround();
	  		String str5 = xxkssj.substring(0,4);
			String str6 = xxkssj.substring(4, 6);
			String str7 = xxkssj.substring(6,8);
							
			String str8 = str5+"-"+str6+"-"+str7;
				  
			String str1 = xxjssj.substring(0,4);
			String str2 = xxjssj.substring(4, 6);
			String str3 = xxjssj.substring(6,8);
					
			String str4 = str1+"-"+str2+"-"+str3;
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			 try {
				//开始时间和结束时间
				 Date data = df.parse(str8);
				Date date = df.parse(str4);
				examround.setStartdate(data);
				examround.setEnddate(date);
				System.out.println(data+"---");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	  	    examround.setDwid(xx_jbxx_id1);
			examround.setXnxqId(xnxString);
		    examround.setLcid(lcid);
		    examround.setXn(xnmc);
		    examround.setXqm(xqmc);
		    examround.setExamname(organ_sel);
		    examround.setExamtypem("1");
		    examround.setDwtype("2");
		
	  	    String[] xqxqs = examround.getXnxqId().split(",");
	        if (xqxqs.length == 2) {
	          examround.setXn(xqxqs[0]);
	          examround.setXqm(xqxqs[1]);
	        }
	       
	        List lst = createSQLQuery("select * from cus_kwbm_examround where examname='" + examround.getExamname() + "'and XNXQ_ID = '"+ examround.getXnxqId()+"'").list();
	        if (lst.size() > 0) {
	          if ((examround.getLcid() == null) || ("".equals(examround.getLcid()))) {
	            getMsgBean(false, "重复考试轮次信息不能保存！");
	          }
	        } else {
	         
	            session.saveOrUpdate(examround);
	            session.flush();
	            b++;
	        
	        }
//	  		

	  	        UserInfo u = new UserInfo();
	  	        u.setUsername(username);//姓名
	  	        u.setLoginid(loginid);//登陆账号
	  	        u.setLoginpwd(loginpwd);//登陆密码
//	  	        u.setOrgan_sel(organ_sel);//参考单位
	  	 
	  	   
	  	        u.setOrgan_sel(xx_jbxx_id1);//参考单位编号
	  	        
	  	   
	  	   
	  	      
	  	        xslist.add(u);
	  	      }
	  	      int c= 0;
	  	      for (int j = 0; j < xslist.size(); j++) {
	  	    	String xs_jbxx_id = StringUtil.getUUID();
	  	    	String userroleid = UUID.randomUUID().toString().replace("-","");	
				System.out.println(xslist.get(j));
				
				  List<Map<String,String>> lsZ1 =session.createSQLQuery("select usercode from t_qxgl_userinfo where username='"+xslist.get(j).getUsername()+"' and loginid='" + xslist.get(j).getLoginid()+ "'").setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
				 
				  //添加用户信息
				  if(lsZ1.size() > 0){
					  if ((lsZ1.get(0).get("usercode") == null) || ("".equals(lsZ1.get(0).get("usercode")))) {
						  mb.setMsg("重复考试轮次信息不能保存！");
				          } 
				  }else{
						session.createSQLQuery("insert into t_qxgl_userinfo(usercode,username,usertype,state,loginid,loginpwd,CDATE,QSSJ,JSSJ,flage) " +
								"values('"+xs_jbxx_id+"','"+xslist.get(j).getUsername()+"',4,1,'"+xslist.get(j).getLoginid()+"','"+new Md5Hash(xslist.get(j).getLoginpwd()).toString()+"',NOW(),NOW(),DATE_ADD(NOW(), INTERVAL 1 YEAR),1)").executeUpdate();
						session.createSQLQuery("insert into t_qxgl_usermapping(user_id,user_type,ORGAN_CODE) " +
								"values('"+xs_jbxx_id+"',4,'"+xslist.get(j).getOrgan_sel()+"')").executeUpdate();
						session.createSQLQuery("insert into T_QXGL_USERROLE(usercode,rolecode,userroleid) " +
								"values('"+xs_jbxx_id+"','f4af9ba70882462d960873da8a6f60ec','"+userroleid+"')").executeUpdate();
			  	        c++;
				  }
			}

	  	   
	  	      String mString="";
	  	      if (flag2) {
	  	        mb.setMsg("导入完成!一共导入参考单位" + a + "条! 导入报名轮次 "+b+" ,导入登陆账号"+c+"");
	  	      } else {
	  	    	  //出错到处的xls文件名
	  	        File f = new File(path + "/export/excel/temporary/" + 
	  	          new Date().getTime() + ".xls");
	  	        wwb = Workbook.createWorkbook(
	  	          f, 
	  	          Workbook.getWorkbook(new File(
	  	          path + "/export/excel/temp/examregistratio.xls")));
	  	        ws = wwb.getSheet(0);
	  	        mb.setSuccess(false);
	  	        mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
	  	        // Jxl操作Excel设置背景、字体颜色、对齐方式、列的宽度
	  	        WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, 
	  	          UnderlineStyle.NO_UNDERLINE, Colour.RED);
	  	        WritableCellFormat wcfFC = new WritableCellFormat(wfc);
//	  	        for (int j = 0; j < errXs.size(); j++) {
	  	        	 //第一个是代表列数,   
	  	            //第二是代表行数，   
	  	            //第三个代表要写入的内容   
//	  	        	//然后通过写sheet的方法addCell（）把内容写进sheet里面。   
//	  	          Label l4 = new Label(0, j + 1, (j + 1)+"");
//	  	          Label l5 = new Label(1, j + 1, ((XsInfo)errXs.get(j)).getStuName());//姓名
//	  	          Label l6 = new Label(2, j + 1, ((XsInfo)errXs.get(j)).getSfzjh());//身份证
//	  	          Label l7 = new Label(3, j + 1, ((XsInfo)errXs.get(j)).getStuSex());//性别
//	  	   
//	  	          Label i = new Label(4, j + 1, ((XsInfo)errXs.get(j)).getErrMsg(), wcfFC);//错误信息
//	  	       
//	  	          ws.addCell(l4);
//	  	          ws.addCell(l5);
//	  	          ws.addCell(l6);
//	  	          ws.addCell(l7);
//	  	         
//	  	          ws.addCell(i);
//	  	        }
	        mb.setMsg("导入完成!共导入" + (rows - 1) + "条!<br>失败" +1+ "条!<br><font color='red'>确认之后返回整理好的失败记录文件<br>可继续导入</font>");
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

		public MsgBean deletebm(String xxdms) {
//			String sql = "select sl from cus_kwbm_examround where dwid ='"+xxdms+"'";
		    Session session = null;
			  SessionFactory sf = null;
			   WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			  sf = (SessionFactory)wac.getBean("sessionFactory");
		        session = sf.openSession();
			String bmlcid;
			String examname;
			List<Map<String,String>> lsZ= session.createSQLQuery("select sl rs,bmlcid,examname from cus_kwbm_examround where dwid ="+xxdms+"").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		  
		   bmlcid = lsZ.get(0).get("bmlcid");
		   examname = lsZ.get(0).get("examname");
		   
			int a =	Integer.parseInt(session.createSQLQuery("select count(1) From cus_kw_examinee where bmlcid = '"+bmlcid+"'").uniqueResult().toString());
		  if(a>0){
			  getMsgBean(false,"单位存在考生，不能删除");
		  }else {
				String sql = "delete from cus_kwbm_examround where bmlcid='"+bmlcid+"'";
			
				
				String sql1 = "delete from zxxs_xx_jbxx where XX_JBXX_ID="+xxdms+"";
			
				
				String sql2 ="delete from t_qxgl_userinfo where username='"+examname+"'";
				
				delete(sql);
				delete(sql1);
				delete(sql2);
				getMsgBean(true,"参考单位删除成功");
			}
		
			
		  if(session!=null){
				session.close();
			}
			if(sf!=null){
				sf.close();
			}
			return MsgBean;
		}
		
		
	  
}
