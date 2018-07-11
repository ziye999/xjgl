package com.jiajie.service.core.impl;
/**
 * 上传文件
 * */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.SecurityUtils;
import org.aspectj.util.FileUtil;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.jiajie.action.core.UpFileAction;
import com.jiajie.bean.*;
import com.jiajie.bean.basicsinfo.CusKwTeacher;
import com.jiajie.bean.cheatStu.CusKwCheatstu;
import com.jiajie.bean.exambasic.CusKwExaminee;
import com.jiajie.bean.exambasic.CusKwExamst;
import com.jiajie.exceptions.SystemException;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.FileUpService;
import com.jiajie.util.CardUtils;
import com.jiajie.util.FileIoUtils;
import com.jiajie.util.IdCardUtils;
import com.jiajie.util.ImportUtil;
import com.jiajie.util.SecUtils;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;
import com.opensymphony.xwork2.ActionContext;
@Service("fileUpService")
@SuppressWarnings("all")
public class FileUpServiceImpl extends ServiceBridge implements FileUpService{

	private String path ;
	private final static int BUFFER_SIZE = 16 * 1024;  
	public SimpleDateFormat  formats = new SimpleDateFormat("yyyy-MM-dd");
	
	public void setUp() {		
	}
	
	/**
	 * 文件上传
	 * */
	public MsgBean saveUpFileInfo(File file,String distPath) throws IOException{
		InputStream in = null;  
        BufferedOutputStream out = null;  
        try {  
            in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);  
            out = new BufferedOutputStream(new FileOutputStream(distPath),BUFFER_SIZE);  
            byte[] buffer = new byte[BUFFER_SIZE];  
            int len = 0;  
            try {  
                while ((len=in.read(buffer)) > 0) {  
                    out.write(buffer, 0, len);  
                }  
            } catch (IOException e) {  
                e.printStackTrace(); 
                throw new SystemException("上传图片失败!");
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace(); 
            throw new SystemException("图片未找到!");
        }finally{  
            if(null!=in){  
                try {  
                    in.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if(null!=out){  
                try {  
                    out.close();  
                } catch (IOException e) {  
                	
                    e.printStackTrace();  
                }  
            }  
        }  
        String  newPath = "../"+distPath;
		return this.getMsgBean(distPath,false);
	}
	
	public MsgBean exportExcelTeacherInfo(File file,MBspInfo bspInfo){
		List<CusKwTeacher> list=new ArrayList<CusKwTeacher>();
		try {
			Workbook rwb = Workbook.getWorkbook(file);
			Sheet rs = rwb.getSheet(0);
			int clos=rs.getColumns();//得到所有的列
            int rows=rs.getRows();//得到所有的行
            one:for (int i = 2; i < rows; i++) {
                for (int j = 0; j < clos; j++) {
                	CusKwTeacher teacher = new CusKwTeacher();
                    //第一个是列数，第二个是行数
                	String xm =  rs.getCell(j++, i).getContents().trim();
                	//如果第一列姓名为空，直接返回
                	if(xm==null || "".equals(xm)){
                		continue one;
                	}
                	teacher.setXm(xm);
                	teacher.setXbm(getDMByMc(rs.getCell(j++, i).getContents().trim(), "ZD_XB"));
                	teacher.setMzm(getDMByMc(rs.getCell(j++, i).getContents().trim(), "ZD_MZ"));
                	teacher.setGh(rs.getCell(j++, i).getContents().trim());
                	teacher.setCsrq(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getCell(j++, i).getContents().trim()));
                	String sheng = getDMByMc(rs.getCell(j++, i).getContents().trim(), "ZD_XZQH");
                	String shi = getDMByMc(rs.getCell(j++, i).getContents().trim(), "ZD_XZQH");
                	String qu = getDMByMc(rs.getCell(j++, i).getContents().trim(), "ZD_XZQH");
                	if(qu != null && !"".equals(qu)){
                		teacher.setJg(qu);
                	}else if(shi != null && !"".equals(shi)){
                		teacher.setJg(shi);
                	}else if(sheng != null && !"".equals(sheng)){
                		teacher.setJg(sheng);
                	}else{
                		teacher.setJg("");
                	}
                	teacher.setSfzjlxm(getDMByMc(rs.getCell(j++, i).getContents().trim(), "ZD_ZJLX"));
                	teacher.setSfzjh(rs.getCell(j++, i).getContents().trim());
                	if(!IdCardUtils.IDCardValidate(teacher.getSfzjh())){
                		return this.getMsgBean(false,"第"+(i+1)+"行身份证号格式不正确！");
                	}
                	//判断是否存在身份证号与工号相同的教师
                	String sql = " select * from cus_kw_teacher where gh='"+teacher.getGh()+"' and sfzjh='"+teacher.getSfzjh()+"' ";
        			List jsList = getListSQL(sql);
        			if(jsList != null && jsList.size()>0){
        				this.getMsgBean(false,"第"+(i+1)+"行存在身份证号与工号相同教师！");
        				return MsgBean;
        			}
        			if(list!=null && list.size()>0){
        				for(int s=0;s<list.size();s++){
        					CusKwTeacher tea = (CusKwTeacher)list.get(s);
        					if(teacher.getGh().equals(tea.getGh()) && teacher.getSfzjh().equals(tea.getSfzjh())){
        						this.getMsgBean(false,"第"+(i+1)+"行存在身份证号与工号相同教师！");
                				return MsgBean;
        					}
        				}
        			}
                	teacher.setZzmmm(getDMByMc(rs.getCell(j++, i).getContents().trim(), "ZD_ZZMM"));
                	teacher.setJtzz(rs.getCell(j++, i).getContents().trim());
                    teacher.setLxdh(rs.getCell(j++, i).getContents().trim());
                    teacher.setJkzkm(getDMByMc(rs.getCell(j++, i).getContents().trim(), "ZD_JKZK"));
                    teacher.setRjkmm(rs.getCell(j++, i).getContents().trim());
                    String ssdw = rs.getCell(j++, i).getContents().trim();
                    HashMap map = getDataBySql("select XX_JBXX_ID from ZXXS_XX_JBXX where dwlx='1' and xxmc='"+ssdw+"'");
                    if(map == null){
                		return this.getMsgBean(false,"第"+(i+1)+"行所属单位错误！");
                	}
                    teacher.setSchoolid(map.get("XX_JBXX_ID").toString());
                    list.add(teacher);
                    j = clos;
                }
            }
            if(list!=null && list.size()>0){
            	for(int i=0;i<list.size();i++){
            		saveOrUpdate(list.get(i));
            	}
            }
            this.getMsgBean(true,MsgBean.EXPORT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.EXPORT_ERROR);
		}
		return MsgBean;
	}
	
	public MsgBean exportExcelCheatStuFile(File file,CusKwCheatstu cheatStu,MBspInfo bspInfo){
		List<CusKwCheatstu> list=new ArrayList<CusKwCheatstu>();
		try {
			Workbook rwb = Workbook.getWorkbook(file);
			Sheet rs = rwb.getSheet(0);
			int clos=rs.getColumns();//得到所有的列
            int rows=rs.getRows();//得到所有的行
            one:for (int i = 2; i < rows-1; i++) {
                for (int j = 0; j < clos; j++) {
                	CusKwCheatstu student = new CusKwCheatstu();
                	student.setCjr(cheatStu.getCjr());
                	student.setCdate(cheatStu.getCdate());
                	student.setLcid(cheatStu.getLcid());
                    //第一个是列数，第二个是行数
                	String sfzjh =  rs.getCell(j++, i).getContents().trim();
                	//如果第一列考生身份证件号为空，直接返回
                	if(sfzjh==null || "".equals(sfzjh)){
                		return this.getMsgBean(false,"第"+i+"行考生身份证号为空！");
                	}
                	String sql = " SELECT KSID FROM CUS_KW_EXAMINEE WHERE SFZJH='"+sfzjh+
                			"' AND XXDM in (select XX_JBXX_ID from zxxs_xx_jbxx where (SSZGJYXZDM='"+bspInfo.getOrgan().getOrganCode()+
                			"' or SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+bspInfo.getOrgan().getOrganCode()+
                			"') or SSZGJYXZDM in (select REGION_CODE from com_mems_organ where PARENT_CODE in (select REGION_CODE from com_mems_organ where PARENT_CODE='"+
                			bspInfo.getOrgan().getOrganCode()+"')))) " +
                			" AND LCID='"+cheatStu.getLcid()+"' ";
                	HashMap map = getDataBySql(sql);
                	if(map == null){
                		//无该考号考生信息
                		return this.getMsgBean(false,"第"+i+"行考生不存在！");
                	}
                	student.setKsid(map.get("KSID").toString());
                	String kskm =  rs.getCell(j++, i).getContents().trim();
                	//如果第二列考试科目为空，直接返回
                	if(kskm==null || "".equals(kskm)){
                		return this.getMsgBean(false,"第"+i+"行考试科目为空！");
                	}
                	sql = " SELECT DISTINCT T1.KMID FROM CUS_KW_EXAMSUBJECT T1 " +
                			" LEFT JOIN CUS_KW_EXAMINEE T2 ON T1.LCID=T2.LCID AND T2.SFZJH='"+sfzjh+"' " +
                			" WHERE T1.SUBJECTNAME='"+kskm+"' AND T1.LCID='"+cheatStu.getLcid()+"' ";
                	map = getDataBySql(sql);
                	if(map == null){
                		return this.getMsgBean(false,"第"+i+"行考试科目错误！");
                	}
                	student.setKmid(map.get("KMID").toString());
                	String wjlx =  rs.getCell(j++, i).getContents().trim();
                	if(!"".equals(wjlx) && "扣除分数".equals(wjlx)){
                		student.setOpttype("1");
                	}else if("计0分".equals(wjlx)){
                		student.setOpttype("2");
                	}else {
                		return this.getMsgBean(false,"第"+i+"行违纪类型有误为空！");
                	}
                	String kcfs = rs.getCell(j++, i).getContents().trim();
                	Pattern patt = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
                	if(!patt.matcher(kcfs).matches()){
                		return this.getMsgBean(false,"第"+i+"行扣除分数必须为数字！");
                	}
                	if (Float.parseFloat(kcfs)>=100) {
                		return this.getMsgBean(false,"第"+i+"行扣除分数不超出两位数字！");
                	}
                	if("1".equals(student.getOpttype()) && !"".equals(kcfs)){
                		student.setScore(Float.parseFloat(kcfs));
                	}else{
                		student.setScore(Float.parseFloat("0"));
                	}
                	student.setDetail(rs.getCell(j++, i).getContents().trim());
                    list.add(student);
                    j=clos;
                }
            }
            if(list.size()>0){
            	//根据考试轮次ID删除本轮次所有违纪考生信息
            	String sql = " DELETE from CUS_KW_CHEATSTU WHERE LCID='"+cheatStu.getLcid()+"' ";
    			deleteBySql(sql);
            	for(int s=0;s<list.size();s++){
            		CusKwCheatstu stu = list.get(s);
            		saveOrUpdate(stu);
            	}
            	update("update CUS_KW_EXAMROUND set WJFLAG=null where lcid='"+cheatStu.getLcid()+"'");
            	this.getMsgBean(true,MsgBean.EXPORT_SUCCESS);
            }else{
            	this.getMsgBean(false,"未导入数据!");
            }
		} catch (Exception e) {
			e.printStackTrace();
			this.getMsgBean(false,MsgBean.EXPORT_ERROR);
		}
		return MsgBean;
	}
	
	public String getDMByMc(String mc,String table){
		String sql = " SELECT DM FROM "+table+" where mc='"+mc+"' ";
		PageBean pb = getSQLPageBean(sql);
		if(pb.getResultList().size()>0){
			HashMap hm = (HashMap) pb.getResultList().get(0);
			return hm.get("DM").toString();
		}
		return "";
		
	}
	
	public HashMap getDataBySql(String sql){
		PageBean pb = getSQLPageBean(sql);
		if(pb.getResultList().size()>0){
			HashMap hm = (HashMap) pb.getResultList().get(0);
			return hm;
		}
		return null;
	}
	
	public MsgBean importExamstInfo(File file, MBspInfo bspInfo) { 
		MsgBean mb = new MsgBean();
		String examType = "";
		mb.setSuccess(true);
		String path = FileUpServiceImpl.class.getResource("").toString();
		path = path.substring(6,path.lastIndexOf("xjgl")+4);
		File f = new File(path+"/export/excel/temporary/examst_"
				+ new Date().getTime() + ".xls"); 
		List<CusKwExamst> list = new ArrayList<CusKwExamst>();
		System.out.println(path);
		Workbook rwb = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		int successNum = 0;
		int failNum = 0;
		int k = 0;
		StringBuffer sb = new StringBuffer();
		boolean flag2 = true;
		try {
			rwb = Workbook.getWorkbook(file);
			Sheet rs = rwb.getSheet(0);
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行
			//首先判断导入模板是否正确
			HashMap<String,Object> rmap = (HashMap<String, Object>) ImportUtil.checkImportExcelModel(file, "examst.xls");
			if(!(Boolean)rmap.get("success")){
				mb.setSuccess(false);
				mb.setMsg("您导入的模板格式错误,请下载模板导入");
				return mb;
			} 
			//判断导入的是否没有数据的模板
			if(rows==2){
				mb.setSuccess(false);
				mb.setMsg("您导入的模板是空模板,请填充好数据再进行导入");
				return mb;
			}
			clos = (Integer)rmap.get("col");
			for (int i = 2; i < rows; i++) {
				sb.delete(0,sb.length());
				boolean flag = true;
				CusKwExamst st = new CusKwExamst(); 
				for (int j = 0; j < clos; j++) {
					String km = rs.getCell(j++, i).getContents();
					if (km == null || "".equals(km)) {
						sb.append("科目不能为空!");
						flag = false;
					}else{
//						String kch = getCusKwExamSubjectByName(km);
//						if (kch == null) {
//							sb.append("您填写的科目不存在!");
//							flag = false;
//						}else{
//							st.setKch(kch);
							st.setKch(km);
//						} 
					}
					String tx = rs.getCell(j++,i).getContents().trim();
					if(tx==null || "".equals(tx)){
						sb.append("题型不能为空!");
						flag = false;
					}else{
						Object et = getSession().createSQLQuery("select dm from zd_sttype where mc = '"+tx+"'").uniqueResult();
						if(et!=null && !"".equals(et.toString().trim())){
							examType = tx;
							st.setExam_type_id(et.toString());
						}else{
							sb.append("题型填写错误,请在[").append(getSession().createSQLQuery("select group_concat(mc) from zd_sttype").uniqueResult().toString()).append(")]中选择一个填入!");
							flag = false;
						}
					}
					
					String sco = rs.getCell(j++,i).getContents();
					if(isNum(sco)){
						st.setScores(sco);
						double s = Double.parseDouble(sco);
						if(s>99){
							sb.append("分数不能超过99");
							flag =false;
						}
						Double sco2 = checkScoreByKmTx(st.getKch(),st.getExam_type_id());
						if(sco2!=null && sco2!=-1 && sco2!=s){ 
							sb.append("同题型分数必须统一!分数为:"+sco2+"!您填写的分数为:"+sco);
							flag =false;
						}
					}else{
						sb.append("分数只能为数字");
						flag =false;
					} 
					st.setExam_info_bh(getExamBh());
					String zt = rs.getCell(j++,i).getContents();
					if("是".equals(zt)){
						st.setExam_info_zt("1"); 
					}else{
						st.setExam_info_zt("0");
					}
					String tm = rs.getCell(j++,i).getContents().trim();
					st.setExam_cont(SecUtils.encode(tm));
//					if(Integer.parseInt(getSession().createSQLQuery("select case when count(1) = 0 then 0 else 1 end from exam_info where exam_cont = '"+st.getExam_cont()+"'").uniqueResult().toString())>0){
//						Log.error(tm+"   =====================================      ");
//						sb.append("该题已经存在!");
//						flag =false;
//					}
					String answ = rs.getCell(j++,i).getContents().toUpperCase();
					StringBuffer sbs = new StringBuffer();
					for (int s = 0; s < answ.length(); s++) {
						if("ABCDEFGHIJKLMNOPQRSTUVWXYZ".contains(answ.charAt(s)+"")){
							sbs.append(answ.charAt(s)+"");
						}
					} 
					byte[] b = sbs.toString().getBytes();
					Arrays.sort(b);
					answ = new String(b);
					if(tx.equals("判断题")){
						if("Y".equals(answ) || "N".equals(answ)){
							st.setExam_answ(SecUtils.encode(answ));
						}else{
							sb.append("判断题的答案只能是'Y'或'N'");
							flag =false;
						}
					}else{
						st.setExam_answ(SecUtils.encode(answ));
					}
					k = j;
					j = clos;
					System.out.println(flag);
				}
				if (flag) {
					successNum++;
					st.setLrr(bspInfo.getUserId());
					st.setLrsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					getSession().flush();
					getSession().saveOrUpdate(st);
					getSession().flush();
					int bh = (int)'A';
					if("单选".equals(examType) || "多选".equals(examType)){
						for(int a = k; k < rs.getColumns() ; k ++){
							String xx = rs.getCell(k,i).getContents();
							StringBuffer sbsql = new StringBuffer();
							if(xx!=null && !"".equals(xx)){
								sbsql.delete(0,sbsql.length());
								sbsql.append("insert into EXAM_INFO_OPT(EXAM_OPT_ID,EXAM_INFO_ID,EXAM_BH,EXAM_OPT_DES) values('");
								sbsql.append(StringUtil.getUUID()).append("','");
								sbsql.append(st.getExam_info_id()).append("','");
								sbsql.append((char)bh).append("','").append(xx).append("')");
								getSession().createSQLQuery(sbsql.toString()).executeUpdate();
								bh++;
							}
						}
					}
				} else {
					failNum++;
					// copy一份模板发现错误时输出
					if (flag2) {
						wwb = Workbook.createWorkbook(
								f,
								Workbook.getWorkbook(new File(
										path+"/export/excel/temp/examst.xls")));
						ws = wwb.getSheet(0);
						mb.setSuccess(false);
						mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
						flag2 = false;
					}
					for (int j = 0; j < clos; j++) { 
						Label l = new Label(j, i-successNum,rs.getCell(j,i).getContents());
						ws.addCell(l);
					}
					WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,   
		                    UnderlineStyle.NO_UNDERLINE,Colour.RED);   
		            WritableCellFormat wcfFC = new WritableCellFormat(wfc);  
		            sb.append("源数据的第"+(i+1)+"行出现的错误!!!");
					ws.addCell(new Label(clos, i-successNum, sb.toString(),wcfFC));
				}
			} 
			if(flag2){
				mb.setMsg("全部导入完成!一共导入"+(rows-2)+"条!");
			}else{
				wwb.write();
				mb.setMsg("导入完成!共导入"+(rows-2)+"条!<br>成功"+successNum+"条!<br>失败"+failNum+"条!<br><font color='red'>确认之后返回整理好的失败记录文件<br>可继续导入</font>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rwb.close();
				if(!flag2){
					wwb.close();
				}
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mb;
	}  
	
	public com.jiajie.bean.MsgBean exportExcelInfo(File file, MBspInfo bspInfo,String lcid) {
		MsgBean mb = new MsgBean();
		mb.setSuccess(true);
		String path = FileUpServiceImpl.class.getResource("").toString();
		path = path.substring(6,path.lastIndexOf("xjgl")+4);
		List<CusKwExamst> list = new ArrayList<CusKwExamst>();
		System.out.println(path);
		Workbook rwb = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		int successNum = 0;
		int failNum = 0;
		int k = 0;
		StringBuffer sb = new StringBuffer();
		boolean flag2 = true;
		try {
			rwb = Workbook.getWorkbook(file);
			Sheet rs = rwb.getSheet(0);
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行
			//首先判断导入模板是否正确
			HashMap<String,Object> rmap = (HashMap<String, Object>) ImportUtil.checkImportExcelModel(file, "examregistration.xls");
			if(!(Boolean)rmap.get("success")){
				mb.setSuccess(false);
				mb.setMsg("您导入的模板格式错误,请下载模板导入");
				return mb;
			} 
			//判断导入的是否没有数据的模板
			if(rows==2){
				mb.setSuccess(false);
				mb.setMsg("您导入的模板是空模板,请填充好数据再进行导入");
				return mb;
			}
			clos = (Integer)rmap.get("col");
			for (int i = 1; i < rows; i++) {
				sb.delete(0,sb.length());
				boolean flag = true;
				CusKwExamst st = new CusKwExamst();  
				String xx_jbxx_id = null;
				String zkdwm = null;
				String yzbm = null;
				String xm    = rs.getCell(0, i).getContents().trim().replace(" ", "");
            	String xb    = rs.getCell(1, i).getContents().trim().replace(" ", "");
            	String sfzh  = rs.getCell(2, i).getContents().trim().replace(" ", "");                	
            	String ckdwh = rs.getCell(3, i).getContents().trim().replace(" ", "");
            	String ckdw  = rs.getCell(4, i).getContents().trim().replace(" ", "");
            	String gw  	 = rs.getCell(5, i).getContents().trim().replace(" ", "");
            	String xbm = null;
            	if("男".equals(xb))xbm = "1";
            	else if("女".equals(xb))xbm="0";
            	else{flag =false;
        		sb.append("性别填写有误!");};
            	if("".equals(xm)){ 
            		flag =false;
            		sb.append("姓名不能为空!");
            	}
            	if("".equals(xb)){ 
            		flag =false;
            		sb.append("性别不能为空!");
            	}
            	if("".equals(sfzh)){ 
            		flag =false;
            		sb.append("身份证件号不能为空!");
            	}
            	if(!CardUtils.isIDCard(sfzh)){
            		flag =false;
            		sb.append("请检查身份证的有效性!");
            	}
            	if("".equals(ckdwh)){ 
            		flag =false;
            		sb.append("参考单位编码不能为空!");
            	}
            	if("".equals(ckdw)){ 
            		flag =false;
            		sb.append("参考单位地址不能为空!");
            	}
            	if("".equals(gw)){ 
            		flag =false;
            		sb.append("工作岗位不能为空!");
            	}
            	getSession().flush();
            	int fn = Integer.parseInt(getSession().createSQLQuery("select count(1) From zxxs_xs_jbxx where sfzjh = '"+sfzh+"'").uniqueResult().toString());
            	if(fn>0){
            		flag =false;
            		sb.append("身份证件号重复!");
            	}
            	getSession().flush();
            	List<Map<String,String>> lsZ = getSession().createSQLQuery("select XX_JBXX_ID,SSZGJYXZDM,YZBM from zxxs_xx_jbxx where XXBSM='"+ckdwh+"' and XXMC='"+ckdw+"' and dwlx='2'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            	if(lsZ==null || lsZ.size()==0){
            		flag =false;
            		sb.append("参考单位编码与单位名称不匹配，请核对！");
            	}else{
            		xx_jbxx_id =lsZ.get(0).get("xx_jbxx_id"); 
            		zkdwm = lsZ.get(0).get("xxsszgjyxzdm")==null?"":lsZ.get(0).get("xxsszgjyxzdm").toString();
            		yzbm = lsZ.get(0).get("yzbm")==null?"":lsZ.get(0).get("yzbm").toString();
            	}
				if (flag) {
				   getSession().flush();
				   String xs_jbxx_id = StringUtil.getUUID();
               	   getSession().createSQLQuery("insert into zxxs_xs_jbxx(xs_jbxx_id,grbsm,xm,xbm,sfzjlxm,sfzjh,xx_jbxx_id,xxsszgjyxzdm) "+
                  			"values('"+xs_jbxx_id+"','G"+sfzh+"','"+xm+"','"+xbm+"','1','"+sfzh+
                   			"','"+xx_jbxx_id+"','"+zkdwm+"')").executeUpdate();
//	               	SimpleDateFormat format = new SimpleDateFormat("yyyyMM",Locale.CHINA);		
//	    			String dateStr = format.format(new Date());
//	    			NumberFormat nf = NumberFormat.getInstance();
//               	   String kscode = yzbm+dateStr+nf.format(i);
//               	   getSession().createSQLQuery("insert into cus_kw_examinee(KSID,LCID,KSCODE,XJH,XM,XXDM,XBM,SFZJH,GW) values('"+StringUtil.getUUID()+
//           				"','"+lcid+"','"+kscode+"','G"+sfzh+"','"+xm+"','"+
//           				xx_jbxx_id+"','"+xbm+"','"+sfzh+"','"+gw+"')").executeUpdate(); 
				} else {
					failNum++;
					// copy一份模板发现错误时输出
					if (flag2) {
						File f = new File(path+"/export/excel/temporary/examst_"
								+ new Date().getTime() + ".xls"); 
						wwb = Workbook.createWorkbook(
								f,
								Workbook.getWorkbook(new File(
										path+"/export/excel/temp/examregistration.xls")));
						ws = wwb.getSheet(0);
						mb.setSuccess(false);
						mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
						flag2 = false;
					}
					for (int j = 0; j < clos; j++) { 
						Label l = new Label(j,failNum,rs.getCell(j,i).getContents());
						ws.addCell(l);
					}
					WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,   
		                    UnderlineStyle.NO_UNDERLINE,Colour.RED);   
		            WritableCellFormat wcfFC = new WritableCellFormat(wfc);  
		            sb.append("源数据的第"+(i+1)+"行出现的错误!!!");
					ws.addCell(new Label(clos, i-successNum, sb.toString(),wcfFC));
				}
			} 
			if(flag2){
				mb.setMsg("全部导入完成!一共导入"+(rows-2)+"条!");
			}else{
				wwb.write();
				mb.setMsg("导入完成!共导入"+(rows-2)+"条!<br>成功"+successNum+"条!<br>失败"+failNum+"条!<br><font color='red'>确认之后返回整理好的失败记录文件<br>可继续导入</font>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rwb.close();
				if(!flag2){
					wwb.close();
				}
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		List<CusKwExaminee> list = new ArrayList<CusKwExaminee>();
//		try {
//			SimpleDateFormat format = new SimpleDateFormat("yyyyMM",Locale.CHINA);		
//			String dateStr = format.format(new Date());
//			
//			NumberFormat nf = NumberFormat.getInstance();
//			nf.setGroupingUsed(false);
//			nf.setMaximumIntegerDigits(6);
//			nf.setMinimumIntegerDigits(6);
//			
//			Workbook rwb = Workbook.getWorkbook(file);
//			Sheet rs = rwb.getSheet(0);
//			int rows=rs.getRows();//得到所有的行             
//            String khao = "0";
//            String zkdwm = "";
//        	String yzbm = "";
//        	String xx_jbxx_id = "";
//        	String msg = "";
//        	List lsXb = getListSQL("select DM,MC from zd_xb");
//        	Map<String, String> mapXb = new HashMap<String, String>();
//        	Map<String, String> mapDw = new HashMap<String, String>();
//        	for (int i=0; i<lsXb.size(); i++) {
//				Map<String, Object> mp = (Map<String, Object>)lsXb.get(i);
//				String dm = mp.get("DM")==null?"":mp.get("DM").toString();
//				String mc = mp.get("MC")==null?"":mp.get("MC").toString();
//				mapXb.put(mc, dm);
//        	}	
//        	for (int i = 1; i < rows; i++) {
//            	//第一个是列数，第二个是行数
//            	String xm    = rs.getCell(0, i).getContents().trim();
//            	String xb    = rs.getCell(1, i).getContents().trim();
//            	String sfzh  = rs.getCell(2, i).getContents().trim();                	
//            	String ckdwh = rs.getCell(3, i).getContents().trim();
//            	String ckdw  = rs.getCell(4, i).getContents().trim();
//            	String gw  	 = rs.getCell(5, i).getContents().trim();
//            	if(xm==null||"".equals(xm)){
//            		continue;
//            	}
//            	if(sfzh==null||"".equals(sfzh)){
//            		continue;
//            	}else if (sfzh.length()>18) {
//            		msg = msg+"第"+i+"行考生,"+sfzh+",身份证号过长！</br>";            		
//            	}
//            	if(!IdCardUtils.IDCardValidate(sfzh)){
//            		msg = msg+"第"+i+"行考生,"+sfzh+",身份证号格式不正确！</br>";
//            	}
//            	sfzh = sfzh.toUpperCase();
//            	if(ckdwh==null||"".equals(ckdwh)||ckdw==null||"".equals(ckdw)){
//            		this.getMsgBean(false,"导入参考单位信息不能为空！");
//        			return MsgBean;
//            	}
//            	if ("".equals(zkdwm)) {
//            		List lsKs = getListSQL("select a.xx_jbxx_id,a.xxsszgjyxzdm,(select YZBM from zxxs_xx_jbxx where XX_JBXX_ID=a.xx_jbxx_id) yzbm from zxxs_xs_jbxx a where a.sfzjh='"+sfzh+"'");
//                    if (lsKs.size()>0) {
//                    	Map<String, String> mp = (Map<String, String>)lsKs.get(0);                    	
//                    	xx_jbxx_id = mp.get("xx_jbxx_id").toString();
//                		zkdwm = mp.get("xxsszgjyxzdm")==null?"":mp.get("xxsszgjyxzdm").toString();
//                		yzbm = mp.get("yzbm")==null?"":mp.get("yzbm").toString();
//                    }else {
//                    	List lsZ = getListSQL("select XX_JBXX_ID,SSZGJYXZDM,YZBM from zxxs_xx_jbxx where XXBSM='"+ckdwh+"' and XXMC='"+ckdw+"' and dwlx='2'");
//                    	if (lsZ != null && lsZ.size()>0) {
//                    		Map<String, String> mp = (Map<String, String>)lsZ.get(0);
//                    		xx_jbxx_id = mp.get("XX_JBXX_ID").toString();
//                    		zkdwm = mp.get("SSZGJYXZDM")==null?"":mp.get("SSZGJYXZDM").toString();
//                    		yzbm = mp.get("YZBM")==null?"":mp.get("YZBM").toString();                		
//                    	}else {
//                    		delete("delete from zxxs_xs_jbxx where sfzjh='"+sfzh+"'");
//                    		delete("delete from cus_kw_examinee where lcid='"+lcid+"' and XXDM='"+xx_jbxx_id+"'");
//                    		this.getMsgBean(false,"导入考生单位"+ckdwh+"不在这轮考试的参考单位范围内，不能导入！");
//                        	return MsgBean;
//                    	}
//                    }
//                    List lsZk = getListSQL("select a.dwid from cus_kw_examround a "+
//        					"LEFT JOIN COM_MEMS_ORGAN T4 ON T4.PARENT_CODE = a.dwid "+
//        					"LEFT JOIN COM_MEMS_ORGAN T5 ON T5.PARENT_CODE = T4.REGION_CODE "+
//        					"LEFT join zxxs_xx_jbxx xx on xx.XX_JBXX_ID=a.dwid "+
//        					"where a.lcid='"+lcid+"' and (a.dwid='"+xx_jbxx_id+"' or a.dwid='"+zkdwm+"' or T4.REGION_CODE='"+zkdwm+
//        					"' OR T5.REGION_CODE='"+zkdwm+"' or xx.SSZGJYXZDM='"+zkdwm+"')");
//                    if (lsZk.size()<=0) {
//                    	delete("delete from zxxs_xs_jbxx where sfzjh='"+sfzh+"'");
//                    	delete("delete from cus_kw_examinee where lcid='"+lcid+"' and XXDM='"+xx_jbxx_id+"'");
//                    	this.getMsgBean(false,"导入考生单位"+ckdwh+"不在这轮考试的参考单位范围内，不能导入！");
//                    	return MsgBean;
//                    }
//                    List lsDw = getListSQL("select XX_JBXX_ID,YZBM,XXBSM,XXMC from zxxs_xx_jbxx where SSZGJYXZDM='"+zkdwm+"'");	
//                	for (int ii=0; ii<lsDw.size(); ii++) {
//        				Map<String, Object> mp = (Map<String, Object>)lsDw.get(ii);
//        				String id = mp.get("XX_JBXX_ID")==null?"":mp.get("XX_JBXX_ID").toString();
//        				String dwyzbm = mp.get("YZBM")==null?"":mp.get("YZBM").toString();	
//        				String dwbh = mp.get("XXBSM")==null?"":mp.get("XXBSM").toString();
//        				String dwmc = mp.get("XXMC")==null?"":mp.get("XXMC").toString();
//        				mapDw.put(dwbh, id+","+dwmc+","+dwyzbm);
//                	}
//            	}else {
//            		String dwStr = mapDw.get(ckdwh);
//            		if (dwStr==null||"".equals(dwStr)) {
//            			this.getMsgBean(false,"导入考生单位"+ckdwh+"不在这轮考试的参考单位范围内，不能导入！");
//            			return MsgBean;
//            		}
//            		String[] dwAry = dwStr.split(",");
//            		xx_jbxx_id = dwAry.length>0?dwAry[0]:"";
//            		if ("".equals(xx_jbxx_id)){
//            			this.getMsgBean(false,"导入考生单位"+ckdwh+"不在这轮考试的参考单位范围内，不能导入！");
//            			return MsgBean;
//            		}
//            		String dwmc = dwAry.length>1?dwAry[1]:"";
//            		yzbm = dwAry.length>2?dwAry[2]:"";
//            		if (!ckdw.equals(dwmc)) {
//            			delete("delete from zxxs_xs_jbxx where sfzjh='"+sfzh+"'");
//            			delete("delete from cus_kw_examinee where lcid='"+lcid+"' and XXDM='"+xx_jbxx_id+"'");
//            			this.getMsgBean(false,"导入考生单位"+ckdwh+"不在这轮考试的参考单位范围内，不能导入！");
//            			return MsgBean;
//            		}
//            	}            	                
//                
//            	String xbm = mapXb.get(xb); 
//            	String kscode = yzbm+dateStr+nf.format(Integer.parseInt(khao)+i);
//                List lstE = getListSQL("select * FROM cus_kw_examinee WHERE lcid='"+lcid+"' and sfzjh = '"+sfzh+"'");
//            	if (lstE.size()>0) {
//            		update("update zxxs_xs_jbxx set xm='"+xm+"',xbm='"+xbm+"',xxsszgjyxzdm='"+zkdwm+"' where sfzjh='"+sfzh+"'");
//            		update("update cus_kw_examinee set KSCODE='"+kscode+"',XM='"+xm+"',XXDM='"+xx_jbxx_id+"',XBM='"+xbm+"',GW='"+gw+
//            				"' where lcid='"+lcid+"' and sfzjh = '"+sfzh+"'"); 
//            		continue;
//            	}
//            	String xjh = "G"+sfzh;                           	
//            	List lstU = getListSQL("select USERCODE from T_QXGL_USERINFO where usertype='3' and LOGINID='"+sfzh+"'");
//            	if (lstU==null||lstU.size()<=0) {
//            		String xs_jbxx_id = StringUtil.getUUID();
//                	insert("insert into zxxs_xs_jbxx(xs_jbxx_id,grbsm,xm,xbm,sfzjlxm,sfzjh,xx_jbxx_id,xxsszgjyxzdm) "+
//                			"values('"+xs_jbxx_id+"','"+xjh+"','"+xm+"','"+xbm+"','1','"+sfzh+
//                			"','"+xx_jbxx_id+"','"+zkdwm+"')");
//                	String pass = sfzh.substring(sfzh.length()-6, sfzh.length());
//    				insert("insert into T_QXGL_USERINFO(usercode,username,usertype,loginid,loginpwd,flage) "+
//    						"values('"+xs_jbxx_id+"','"+xm+"','3','"+sfzh+"',password('"+pass+"'),'1')");
//            	}            	
//            	insert("insert into cus_kw_examinee(KSID,LCID,KSCODE,XJH,XM,XXDM,XBM,SFZJH,GW) values('"+StringUtil.getUUID()+
//        				"','"+lcid+"','"+kscode+"','"+xjh+"','"+xm+"','"+
//        				xx_jbxx_id+"','"+xbm+"','"+sfzh+"','"+gw+"')");            					               
//            }
//        	rwb.close();
//            if ("".equals(msg)) {
//            	update("update CUS_KW_EXAMROUND a set SL=IFNULL((SELECT COUNT(*) FROM CUS_KW_EXAMINEE b WHERE b.LCID=a.LCID),0) where a.lcid='"+lcid+"'");
//                this.getMsgBean(true,MsgBean.EXPORT_SUCCESS);
//            }else {
//            	return this.getMsgBean(false,msg);
//            }
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.getMsgBean(false,MsgBean.EXPORT_ERROR);
//		}
		return mb;
	}
	
	private Double checkScoreByKmTx(String kch, String exam_type_id) {
		// TODO Auto-generated method stub
		String sql = "select case when max(scores) is null then -1 else  max(scores) end from exam_info where exam_type_id = '"+exam_type_id+"'";
		Object obj = getSession().createSQLQuery(sql).uniqueResult();
		if(obj==null){
			return null;
		}else{
			return ((Double)obj);
		}
	}

	private String getCusKwExamSubjectByName(String km) {
		String sql = "select NAME From sys_enum_value WHERE DIC_TYPE = 'ZD_KM' and name = '" + km+"'";
		return getSession().createSQLQuery(sql).uniqueResult()==null?null:(String)getSession().createSQLQuery(sql).uniqueResult();
	}
	
	private String getExamBh() {
		String sql = "select case when max(exam_info_bh) is null or LOCATE(year(sysdate()),max(exam_info_bh)) = 0 then concat(year(sysdate()),'100001') else max(exam_info_bh)+1 end as bh From exam_info";
		return getSession().createSQLQuery(sql).uniqueResult()==null?null:(String)getSession().createSQLQuery(sql).uniqueResult();
	}
	
	private boolean isNum(Object str){
		try {
			Integer.parseInt(str.toString());
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	public Map<String, Object> getExamProblemInfo(Session session,String dt) {
		//String dt =new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		dt="2017-12-11";
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success",true);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		WritableWorkbook wwb = null;
		WritableSheet ws = null; 
		try {
			String path = FileUpServiceImpl.class.getResource("").toString();
			path = path.substring(6,path.lastIndexOf("xjgl")+4);
			File f = new File(path+"/export/excel/temporary/error_"
					+ sdf.format(new Date())+"_"+new Date().getTime()+".xls");  
			wwb = Workbook.createWorkbook(
					f,
					Workbook.getWorkbook(new File(
							path+"/export/excel/temp/examerror.xls")));
			ws = wwb.getSheet(0); 
			WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,   
                    UnderlineStyle.NO_UNDERLINE,Colour.BLACK);   
            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            StringBuffer sb=new StringBuffer();
            sb.append("select dx,ddx,pd,xm,sfzjh,(select starttime from cus_kw_examschedule where kmid=e.kmid ) kssj,");
            sb.append("(select buildname from cus_kw_building b ,cus_kw_room r ,cus_kw_examroom er where b.LF_ID=r.LFID and er.ROOMID=r.FJID and er.KCID=e.KCID) bname,");
            sb.append("(select ROOMNAME from cus_kw_building b ,cus_kw_room r ,cus_kw_examroom er where b.LF_ID=r.LFID and er.ROOMID=r.FJID and er.KCID=e.KCID) rname,");
            sb.append("zwh,case when s=-1 then '' else s end s,(select score from cus_kw_stuscore_bk where xjh=e.xjh) score,(SELECT case when s = 0 then score when s> 0 THEN score+(35-dx)+(15-ddx)*2+(35-pd) end as score FROM cus_kw_stuscore_bk WHERE xjh = e.xjh ) nscore,(select case when count(1)>0 then '危险' else '' end from exam_ks_dangerous where sfzjh = e.SFZJH) wx, case when s=-1 then '文件损坏' else '' end bz");
            sb.append(" from (select xs_jbxx_id,sum(EXAM_TYPE_ID=1) as dx,sum(EXAM_TYPE_ID= 2) as ddx,sum(EXAM_TYPE_ID=3) as pd,count(1) as s from ( SELECT ei.exam_info_id,EXAM_TYPE_ID,xs_jbxx_id FROM cus_exam_detail_bk2 b,exam_info ei,paper_basic bc where ei.exam_info_id = b.exam_info_id  and bc.paper_id=b.paper_id  group by exam_info_id,xs_jbxx_id) t group by xs_jbxx_id UNION");
            sb.append(" select xsid xs_jbxx_id , 0 as dx, 0 as ddx, 0 as pd,-1 s from da_log where DATE_FORMAT(optdate,'%Y-%m-%d')='").append(dt).append("') tt ");
            sb.append(" ,cus_kw_examinee e where tt.xs_jbxx_id=e.XS_JBXX_ID and e.kmid in (select kmid from cus_kw_examschedule where DATE_FORMAT(starttime,'%Y-%m-%d')='").append(dt).append("') ");
            sb.append("and e.bh < 60");
            System.out.println(sb.toString());
            List<Map<String,Object>> errlist=session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            for (int i = 0; i < errlist.size(); i++) {
            	ws.addCell(new Label(0, i+1,errlist.get(i).get("xm").toString(),wcfFC)); 
            	ws.addCell(new Label(1, i+1,errlist.get(i).get("sfzjh").toString(),wcfFC));
            	ws.addCell(new Label(2, i+1,errlist.get(i).get("kssj").toString(),wcfFC));
            	ws.addCell(new Label(3, i+1,errlist.get(i).get("bname").toString(),wcfFC));
            	ws.addCell(new Label(4, i+1,errlist.get(i).get("rname").toString(),wcfFC));
            	ws.addCell(new Label(5, i+1,errlist.get(i).get("zwh").toString(),wcfFC));
            	ws.addCell(new Label(6, i+1,errlist.get(i).get("s").toString(),wcfFC));
            	ws.addCell(new Label(7, i+1,errlist.get(i).get("dx").toString(),wcfFC));
            	ws.addCell(new Label(8, i+1,errlist.get(i).get("ddx").toString(),wcfFC));
            	ws.addCell(new Label(9, i+1,errlist.get(i).get("pd").toString(),wcfFC));
            	ws.addCell(new Label(10, i+1,errlist.get(i).get("score").toString(),wcfFC));
            	ws.addCell(new Label(11, i+1,errlist.get(i).get("nscore")+"",wcfFC));
            	ws.addCell(new Label(12, i+1,errlist.get(i).get("wx").toString(),wcfFC));
            	ws.addCell(new Label(13, i+1,errlist.get(i).get("bz").toString(),wcfFC));
			}
			result.put("data", f.getPath());
			wwb.write();    
			
		} catch (Exception e) { 
			result.put("success", false);
			result.put("msg", "服务器异常，请联系管理员");
			e.printStackTrace();
		}  finally { 
			try {
				if(wwb!=null){
					wwb.close(); 
				}
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result ;
	}  
	public Map<String, Object> info(String dt,String room,String type) {
			Session session =getSession();
			return getExamProblemInfo(session,dt);
	}
	public Map<String, Object> getExamKdksInfo(String dt,String room,String type) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success",true);
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		WritableSheet ws2 = null;
		WritableSheet ws3 = null;
		WritableSheet ws4 = null;
		try {
			String path = FileUpServiceImpl.class.getResource("").toString();
			path = path.substring(6,path.lastIndexOf("xjgl")+4);
			File f = new File(path+"/export/excel/temporary/"
					+ new Date().getTime()+".xls");  
//			FileIoUtils.Copy(path+"/export/excel/temp/examkdinfo.xls", f.getPath());
			wwb = Workbook.createWorkbook(
					f,
					Workbook.getWorkbook(new File(
							path+"/export/excel/temp/examkdinfo.xls")));
			ws = wwb.getSheet(0);
			ws2 = wwb.getSheet(1);
			ws3 = wwb.getSheet(2);
			ws4 = wwb.getSheet(3);   
			int rows = ws.getRows();// 得到所有的行 
			WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,   
                    UnderlineStyle.NO_UNDERLINE,Colour.BLACK);   
            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            boolean roomflag = false;
            if(room!=null && !"".equals(room.trim())){
            	roomflag = true;
            }
			for (int i = 2; i < rows; i++) {
				String roomid = ws.getCell(5, i).getContents().trim().replace(" ", "");
				if(roomflag){
					if(room.indexOf(roomid)==-1){
						continue;
					}
				}
				List<Map<String,String>> list = getSession().createSQLQuery("select kcid,kmid,starttime From cus_kw_examroom er join cus_kw_examschedule es on er.LCID = es.LCID join cus_kw_examround r on es.lcid=r.lcid WHERE DATE_FORMAT(es.EXAMDATE,'%Y-%m-%d') = '"+dt+"' and er.ROOMID = '"+roomid+"' order by starttime").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if(list!=null && list.size()>0){
					for (int j = 0; j < list.size(); j++) {
						if(list.get(j).get("starttime").indexOf("08:30:00")>0 && type.indexOf("a")==-1){
							continue;
						}else if(list.get(j).get("starttime").indexOf("11:00:00")>0 && type.indexOf("b")==-1){
							continue;
						}else if(list.get(j).get("starttime").indexOf("14:30:00")>0 && type.indexOf("c")==-1){
							continue;
						}else if(list.get(j).get("starttime").indexOf("16:00:00")>0 && type.indexOf("d")==-1){
							continue;
						} 
						Map<String,Integer> statu = (Map<String,Integer>) getSession().createSQLQuery("select down_status,submit_status From cus_kw_roomsubject where roomid = '"+roomid+"' and kmid = '"+list.get(j).get("kmid")+"'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
						String tj = "未提交";
						String xz = "未下载";
						String kss ="";
						String tjs ="";
						String qks ="";
						String lfs ="";
						String wsf ="";
						String lsf ="";
						if(statu!=null){
							if(statu.get("down_status")==1){
								xz = "已下载";
							}
							if(statu.get("submit_status")==1){
								tj = "已提交";
								kss = getSession().createSQLQuery("select count(1) From cus_kw_examinee where kcid = '"+list.get(j).get("kcid")+"' and kmid = '"+list.get(j).get("kmid")+"'").uniqueResult().toString();
								tjs = getSession().createSQLQuery("select count(1) From cus_kw_examinee where kcid = '"+list.get(j).get("kcid")+"' and kmid = '"+list.get(j).get("kmid")+"' and istj = 1").uniqueResult().toString();
								qks = getSession().createSQLQuery("select count(1) From cus_kw_examinee where kcid = '"+list.get(j).get("kcid")+"' and kmid = '"+list.get(j).get("kmid")+"' and (isks is null or isks = 0)").uniqueResult().toString();
								lfs = getSession().createSQLQuery("select count(1) from cus_kw_stuscore_bk s ,cus_kw_examinee e where s.xjh = e.xjh and e.kcid = '"+list.get(j).get("kcid")+"' and s.kmid = '"+list.get(j).get("kmid")+"' and s.score = 0").uniqueResult().toString();
								wsf = getSession().createSQLQuery("select count(1) from cus_kw_stuscore_bk s ,cus_kw_examinee e where s.xjh = e.xjh and e.kcid = '"+list.get(j).get("kcid")+"' and s.kmid = '"+list.get(j).get("kmid")+"' and s.score >= 50").uniqueResult().toString();;
								lsf = getSession().createSQLQuery("select count(1) from cus_kw_stuscore_bk s ,cus_kw_examinee e where s.xjh = e.xjh and e.kcid = '"+list.get(j).get("kcid")+"' and s.kmid = '"+list.get(j).get("kmid")+"' and s.score >= 60").uniqueResult().toString();;
							}
						} 
						if(list.get(j).get("starttime").indexOf("08:30:00")>0){
							ws.addCell(new Label(6, i,list.get(j).get("starttime"),wcfFC)); 
							ws.addCell(new Label(7,i, xz,wcfFC)); 
							ws.addCell(new Label(8, i,tj,wcfFC)); 
							ws.addCell(new Label(9, i,kss,wcfFC)); 
							ws.addCell(new Label(10, i,tjs,wcfFC)); 
							ws.addCell(new Label(11, i,qks,wcfFC)); 
							ws.addCell(new Label(12, i,lfs,wcfFC)); 
							ws.addCell(new Label(13, i,wsf,wcfFC)); 
							ws.addCell(new Label(14, i,lsf,wcfFC)); 
						}else if(list.get(j).get("starttime").indexOf("11:00:00")>0){
							ws2.addCell(new Label(6, i,list.get(j).get("starttime"),wcfFC)); 
							ws2.addCell(new Label(7,i, xz,wcfFC)); 
							ws2.addCell(new Label(8, i,tj,wcfFC)); 
							ws2.addCell(new Label(9, i,kss,wcfFC)); 
							ws2.addCell(new Label(10, i,tjs,wcfFC)); 
							ws2.addCell(new Label(11, i,qks,wcfFC)); 
							ws2.addCell(new Label(12, i,lfs,wcfFC)); 
							ws2.addCell(new Label(13, i,wsf,wcfFC)); 
							ws2.addCell(new Label(14, i,lsf,wcfFC)); 
						}else if(list.get(j).get("starttime").indexOf("14:30:00")>0){
							ws3.addCell(new Label(6, i,list.get(j).get("starttime"),wcfFC)); 
							ws3.addCell(new Label(7,i, xz,wcfFC)); 
							ws3.addCell(new Label(8, i,tj,wcfFC)); 
							ws3.addCell(new Label(9, i,kss,wcfFC)); 
							ws3.addCell(new Label(10, i,tjs,wcfFC)); 
							ws3.addCell(new Label(11, i,qks,wcfFC)); 
							ws3.addCell(new Label(12, i,lfs,wcfFC)); 
							ws3.addCell(new Label(13, i,wsf,wcfFC)); 
							ws3.addCell(new Label(14, i,lsf,wcfFC)); 
						}else if(list.get(j).get("starttime").indexOf("16:00:00")>0){
							ws4.addCell(new Label(6, i,list.get(j).get("starttime"),wcfFC)); 
							ws4.addCell(new Label(7,i, xz,wcfFC)); 
							ws4.addCell(new Label(8, i,tj,wcfFC)); 
							ws4.addCell(new Label(9, i,kss,wcfFC)); 
							ws4.addCell(new Label(10, i,tjs,wcfFC)); 
							ws4.addCell(new Label(11, i,qks,wcfFC)); 
							ws4.addCell(new Label(12, i,lfs,wcfFC));
							ws4.addCell(new Label(13, i,wsf,wcfFC)); 
							ws4.addCell(new Label(14, i,lsf,wcfFC)); 
						}
					}
				} 
			}
			result.put("data", f.getPath().substring(f.getPath().indexOf("export")));
			wwb.write();    
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "服务器异常，请联系管理员");
			e.printStackTrace();
		}  finally {
			try {
				if(wwb!=null){
					wwb.close(); 
				}
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result ;
	} 
}

