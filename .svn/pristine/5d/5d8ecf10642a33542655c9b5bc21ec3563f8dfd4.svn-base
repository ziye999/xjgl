package com.jiajie.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

import org.hibernate.Session;
import org.hibernate.transform.Transformers; 

import com.jiajie.service.core.impl.FileUpServiceImpl;

public class GenerateExcel {  
	public Map<String, Object> getExamKdksInfo(Session session) {
		String dt =new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success",true);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		WritableSheet ws2 = null;
		WritableSheet ws3 = null;
		WritableSheet ws4 = null;
		try {
			String path = FileUpServiceImpl.class.getResource("").toString();
			path = path.substring(6,path.lastIndexOf("xjgl")+4);
			File f = new File(path+"/export/excel/temporary/exam_"
					+ sdf.format(new Date())+"_"+new Date().getTime()+".xls");  
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
            
			for (int i = 2; i < rows; i++) {
				String roomid = ws.getCell(5, i).getContents().trim().replace(" ", "");
				List<Map<String,String>> list = session.createSQLQuery("select kcid,kmid,starttime From cus_kw_examroom er join cus_kw_examschedule es on er.LCID = es.LCID join cus_kw_examround r on es.lcid=r.lcid WHERE DATE_FORMAT(es.EXAMDATE,'%Y-%m-%d') = '"+dt+"' and er.ROOMID = '"+roomid+"' order by starttime").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if(list!=null && list.size()>0){
					for (int j = 0; j < list.size(); j++) { 
						Map<String,Integer> statu = (Map<String,Integer>) session.createSQLQuery("select down_status,submit_status From cus_kw_roomsubject where roomid = '"+roomid+"' and kmid = '"+list.get(j).get("kmid")+"'").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
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
								kss = session.createSQLQuery("select count(1) From cus_kw_examinee where kcid = '"+list.get(j).get("kcid")+"' and kmid = '"+list.get(j).get("kmid")+"'").uniqueResult().toString();
								tjs = session.createSQLQuery("select count(1) From cus_kw_examinee where kcid = '"+list.get(j).get("kcid")+"' and kmid = '"+list.get(j).get("kmid")+"' and istj = 1").uniqueResult().toString();
								qks = session.createSQLQuery("select count(1) From cus_kw_examinee where kcid = '"+list.get(j).get("kcid")+"' and kmid = '"+list.get(j).get("kmid")+"' and (isks is null or isks = 0)").uniqueResult().toString();
								lfs = session.createSQLQuery("select count(1) from cus_kw_stuscore_bk s ,cus_kw_examinee e where s.xjh = e.xjh and e.kcid = '"+list.get(j).get("kcid")+"' and e.kmid = '"+list.get(j).get("kmid")+"' and s.score = 0").uniqueResult().toString();
								wsf = session.createSQLQuery("select count(1) from cus_kw_stuscore_bk s ,cus_kw_examinee e where s.xjh = e.xjh and e.kcid = '"+list.get(j).get("kcid")+"' and e.kmid = '"+list.get(j).get("kmid")+"' and s.score >= 50").uniqueResult().toString();;
								lsf = session.createSQLQuery("select count(1) from cus_kw_stuscore_bk s ,cus_kw_examinee e where s.xjh = e.xjh and e.kcid = '"+list.get(j).get("kcid")+"' and e.kmid = '"+list.get(j).get("kmid")+"' and s.score >= 60").uniqueResult().toString();;
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
						}else if(list.get(j).get("starttime").indexOf("13:30:00")>0){
							ws3.addCell(new Label(6, i,list.get(j).get("starttime"),wcfFC)); 
							ws3.addCell(new Label(7,i, xz,wcfFC)); 
							ws3.addCell(new Label(8, i,tj,wcfFC)); 
							ws3.addCell(new Label(9, i,kss,wcfFC)); 
							ws3.addCell(new Label(10, i,tjs,wcfFC)); 
							ws3.addCell(new Label(11, i,qks,wcfFC)); 
							ws3.addCell(new Label(12, i,lfs,wcfFC)); 
							ws3.addCell(new Label(13, i,wsf,wcfFC)); 
							ws3.addCell(new Label(14, i,lsf,wcfFC)); 
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
	
	public Map<String, Object> getExamProblemInfo(Session session) {
		String dt =new SimpleDateFormat("YYYY-MM-dd").format(new Date());
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
            sb.append(" from (select xs_jbxx_id,sum(EXAM_TYPE_ID=1) as dx,sum(EXAM_TYPE_ID= 2) as ddx,sum(EXAM_TYPE_ID=3) as pd,count(1) as s from ( SELECT ei.exam_info_id,EXAM_TYPE_ID,xs_jbxx_id FROM cus_exam_detail_bk2 b,exam_info ei where ei.exam_info_id = b.exam_info_id group by exam_info_id,xs_jbxx_id) t group by xs_jbxx_id UNION");
            sb.append(" select xsid xs_jbxx_id , 0 as dx, 0 as ddx, 0 as pd,-1 s from da_log where DATE_FORMAT(optdate,'%Y-%m-%d')='").append(dt).append("') tt ");
            sb.append(" ,cus_kw_examinee e where tt.xs_jbxx_id=e.XS_JBXX_ID and e.kmid in (select kmid from cus_kw_examschedule where DATE_FORMAT(starttime,'%Y-%m-%d')='").append(dt).append("') ");
            sb.append("and (SELECT score FROM cus_kw_stuscore_bk WHERE xjh = e.xjh ) < 60");
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
            	ws.addCell(new Label(11, i+1,errlist.get(i).get("nscore").toString(),wcfFC));
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
	
	public Map<String, Object> getDetailByKd(Session session,String kdbh,String kdmc,String city,int score) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success",true);
		WritableWorkbook wwb = null;
		WritableSheet ws = null; 
		try {
			String path = FileUpServiceImpl.class.getResource("").toString();
			path = path.substring(6,path.lastIndexOf("xjgl")+4);
			File pf =new File(path+"/export/excel/temporary/"+city);
			if(!pf.exists()){
				pf.mkdirs();
			}
			File f = new File(path+"/export/excel/temporary/"+city+"/"+kdmc+".xls");  
			wwb = Workbook.createWorkbook(
					f,
					Workbook.getWorkbook(new File(
							path+"/export/excel/temp/kdqk.xls")));
			ws = wwb.getSheet(0); 
			WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,   
                    UnderlineStyle.NO_UNDERLINE,Colour.BLACK);   
            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            StringBuffer sb=new StringBuffer();
            sb.append("select *From exam_score_detail where kdbh = '").append(kdbh).append("' ORDER BY  kdmc,kcmc,kssj ,zwh  ");
//            sb.append("select *from (SELECT xxdm, (select region_edu from com_mems_organ where REGION_CODE = d.dwid) as zkdw,(select xxmc from zxxs_xx_jbxx where XX_JBXX_ID = e.xxdm) as ckdw,");
//            sb.append("xm,case when xbm = 1 then '男' else '女' end as xb,sfzjh,(select starttime from cus_kw_examschedule where kmid = e.kmid) as kssj,'"+kdmc+"' as kdmc,");
//            sb.append("(select roomname from cus_kw_room rm,cus_kw_examroom eer where eer.ROOMID = rm.FJID and eer.KCID = e.kcid) as kcmc,zwh,");
//            sb.append("case when (select score from cus_kw_stuscore_bk where xjh = e.xjh) >= "+score+" and (select count(1) from exam_wj where sfzjh = e.sfzjh) =0  then '合格' else '未合格' end as sfhg,");
//            sb.append("(select wj from exam_wj where sfzjh = e.sfzjh) as wj,");
//            sb.append("case when  (select bz from exam_wj where sfzjh = e.sfzjh) is not null then (select bz from exam_wj where sfzjh = e.sfzjh) when (select score from cus_kw_stuscore_bk where xjh = e.xjh) is null then '缺考' end as bz");
//            sb.append(" FROM cus_kw_examinee E,cus_kw_examround d WHERE d.lcid = e.lcid and KCID IN (SELECT KCID FROM cus_kw_examroom ER ,cus_kw_room R WHERE ER.ROOMID = R.FJID AND R.LFID = '"+kdbh+"')");
//            sb.append("and kmid in (select kmid from cus_kw_examschedule where starttime > '2016-12-23') ) tt order by xxdm,kssj");
            System.out.println(sb.toString());
            List<Map<String,Object>> errlist=session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            for (int i = 0; i < errlist.size(); i++) {
            	String wj = (String)errlist.get(i).get("wj");
            	if(wj==null){
            		wj = "";
            	}
            	String bz = (String)errlist.get(i).get("bz");
            	if(bz==null){
            		bz="";
            	}
            	String sfhg = errlist.get(i).get("sfhg").toString();
//            	if("1".equals(sfhg)){
//            		sfhg = "合格";
//            	}else{
//            		sfhg = "未合格";
//            	}
            	ws.addCell(new Label(0, i+1,errlist.get(i).get("zkdw").toString(),wcfFC)); 
            	ws.addCell(new Label(1, i+1,errlist.get(i).get("ckdw").toString(),wcfFC));
            	ws.addCell(new Label(2, i+1,errlist.get(i).get("xm").toString(),wcfFC));
            	ws.addCell(new Label(3, i+1,errlist.get(i).get("xb").toString(),wcfFC));
            	ws.addCell(new Label(4, i+1,errlist.get(i).get("sfzjh").toString(),wcfFC));
            	ws.addCell(new Label(5, i+1,errlist.get(i).get("kssj")+"",wcfFC));
            	ws.addCell(new Label(6, i+1,errlist.get(i).get("kdmc").toString(),wcfFC));
            	ws.addCell(new Label(7, i+1,errlist.get(i).get("kcmc").toString(),wcfFC));
            	ws.addCell(new Label(8, i+1,errlist.get(i).get("zwh").toString(),wcfFC));
            	ws.addCell(new Label(9, i+1,sfhg,wcfFC));
            	ws.addCell(new Label(10, i+1,wj,wcfFC));
            	ws.addCell(new Label(11, i+1,bz,wcfFC)); 
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
	
	public Map<String, Object> getDetailByCkdw(Session session,String zkdw,String ckdw,String city,int score) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success",true);
		WritableWorkbook wwb = null;
		WritableSheet ws = null; 
		try {
			String path = FileUpServiceImpl.class.getResource("").toString();
			path = path.substring(6,path.lastIndexOf("xjgl")+4);
			File pf =new File(path+"/export/excel/temporary/"+city);
			if(!pf.exists()){
				pf.mkdirs();
			}
			File pf2 =new File(path+"/export/excel/temporary/"+city+"/"+zkdw);
			if(!pf2.exists()){
				pf2.mkdirs();
			}
			File f = new File(File.separator +path+"/export/excel/temporary/"+city+"/"+zkdw+"/"+zkdw+"_"+ckdw+".xls");  
			wwb = Workbook.createWorkbook(
					f,
					Workbook.getWorkbook(new File(
							path+"/export/excel/temp/kdqk.xls")));
			ws = wwb.getSheet(0); 
			WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,   
                    UnderlineStyle.NO_UNDERLINE,Colour.BLACK);   
            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            StringBuffer sb=new StringBuffer();
            sb.append("select *From exam_score_detail where zkdw = '"+zkdw+"' and ckdw = '").append(ckdw).append("' ORDER BY  kssj ,kcmc,zwh  ");
//            sb.append("select *from (SELECT xxdm, (select region_edu from com_mems_organ where REGION_CODE = d.dwid) as zkdw,(select xxmc from zxxs_xx_jbxx where XX_JBXX_ID = e.xxdm) as ckdw,");
//            sb.append("xm,case when xbm = 1 then '男' else '女' end as xb,sfzjh,(select starttime from cus_kw_examschedule where kmid = e.kmid) as kssj,'"+kdmc+"' as kdmc,");
//            sb.append("(select roomname from cus_kw_room rm,cus_kw_examroom eer where eer.ROOMID = rm.FJID and eer.KCID = e.kcid) as kcmc,zwh,");
//            sb.append("case when (select score from cus_kw_stuscore_bk where xjh = e.xjh) >= "+score+" and (select count(1) from exam_wj where sfzjh = e.sfzjh) =0  then '合格' else '未合格' end as sfhg,");
//            sb.append("(select wj from exam_wj where sfzjh = e.sfzjh) as wj,");
//            sb.append("case when  (select bz from exam_wj where sfzjh = e.sfzjh) is not null then (select bz from exam_wj where sfzjh = e.sfzjh) when (select score from cus_kw_stuscore_bk where xjh = e.xjh) is null then '缺考' end as bz");
//            sb.append(" FROM cus_kw_examinee E,cus_kw_examround d WHERE d.lcid = e.lcid and KCID IN (SELECT KCID FROM cus_kw_examroom ER ,cus_kw_room R WHERE ER.ROOMID = R.FJID AND R.LFID = '"+kdbh+"')");
//            sb.append("and kmid in (select kmid from cus_kw_examschedule where starttime > '2016-12-23') ) tt order by xxdm,kssj");
            System.out.println(sb.toString());
            List<Map<String,Object>> errlist=session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            for (int i = 0; i < errlist.size(); i++) {
            	String wj = (String)errlist.get(i).get("wj");
            	if(wj==null){
            		wj = "";
            	}
            	String bz = (String)errlist.get(i).get("bz");
            	if(bz==null){
            		bz="";
            	}
            	String sfhg = errlist.get(i).get("sfhg").toString();
//            	if("1".equals(sfhg)){
//            		sfhg = "合格";
//            	}else{
//            		sfhg = "未合格";
//            	}
            	ws.addCell(new Label(0, i+1,errlist.get(i).get("zkdw").toString(),wcfFC)); 
            	ws.addCell(new Label(1, i+1,errlist.get(i).get("ckdw").toString(),wcfFC));
            	ws.addCell(new Label(2, i+1,errlist.get(i).get("xm").toString(),wcfFC));
            	ws.addCell(new Label(3, i+1,errlist.get(i).get("xb").toString(),wcfFC));
            	ws.addCell(new Label(4, i+1,errlist.get(i).get("sfzjh").toString(),wcfFC));
            	ws.addCell(new Label(5, i+1,errlist.get(i).get("kssj")+"",wcfFC));
            	ws.addCell(new Label(6, i+1,errlist.get(i).get("kdmc")+"",wcfFC));
            	ws.addCell(new Label(7, i+1,errlist.get(i).get("kcmc")+"",wcfFC));
            	ws.addCell(new Label(8, i+1,errlist.get(i).get("zwh")+"",wcfFC));
            	
            	ws.addCell(new Label(9, i+1,sfhg,wcfFC));
            	ws.addCell(new Label(10, i+1,wj,wcfFC));
            	ws.addCell(new Label(11, i+1,bz,wcfFC)); 
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
	
	public Map<String, Object> getDetailByZkdw(Session session,String zkdw,String city,int score) {
		System.out.println(zkdw);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success",true);
		WritableWorkbook wwb = null;
		WritableSheet ws = null; 
		try {
			String path = FileUpServiceImpl.class.getResource("").toString();
			path = path.substring(6,path.lastIndexOf("xjgl")+4);
			File pf =new File(File.separator+path+"/export/excel/temporary/"+city);
			if(!pf.exists()){
				pf.mkdirs();
			}
			File f = new File(File.separator+path+"/export/excel/temporary/"+city+"/"+zkdw+".xls");  
			wwb = Workbook.createWorkbook(
					f,
					Workbook.getWorkbook(new File(
							File.separator+path+"/export/excel/temp/kdqk.xls")));
			
			ws = wwb.getSheet(0);
			WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,   
                    UnderlineStyle.NO_UNDERLINE,Colour.BLACK);   
            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            StringBuffer sb=new StringBuffer();
            sb.append("select *From exam_score_detail where zkdw = '"+zkdw+"' ORDER BY  kssj ,kcmc,zwh ");
//            sb.append("select *from (SELECT xxdm, (select region_edu from com_mems_organ where REGION_CODE = d.dwid) as zkdw,(select xxmc from zxxs_xx_jbxx where XX_JBXX_ID = e.xxdm) as ckdw,");
//            sb.append("xm,case when xbm = 1 then '男' else '女' end as xb,sfzjh,(select starttime from cus_kw_examschedule where kmid = e.kmid) as kssj,'"+kdmc+"' as kdmc,");
//            sb.append("(select roomname from cus_kw_room rm,cus_kw_examroom eer where eer.ROOMID = rm.FJID and eer.KCID = e.kcid) as kcmc,zwh,");
//            sb.append("case when (select score from cus_kw_stuscore_bk where xjh = e.xjh) >= "+score+" and (select count(1) from exam_wj where sfzjh = e.sfzjh) =0  then '合格' else '未合格' end as sfhg,");
//            sb.append("(select wj from exam_wj where sfzjh = e.sfzjh) as wj,");
//            sb.append("case when  (select bz from exam_wj where sfzjh = e.sfzjh) is not null then (select bz from exam_wj where sfzjh = e.sfzjh) when (select score from cus_kw_stuscore_bk where xjh = e.xjh) is null then '缺考' end as bz");
//            sb.append(" FROM cus_kw_examinee E,cus_kw_examround d WHERE d.lcid = e.lcid and KCID IN (SELECT KCID FROM cus_kw_examroom ER ,cus_kw_room R WHERE ER.ROOMID = R.FJID AND R.LFID = '"+kdbh+"')");
//            sb.append("and kmid in (select kmid from cus_kw_examschedule where starttime > '2016-12-23') ) tt order by xxdm,kssj");
            List<Map<String,Object>> errlist=session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            for (int i = 0; i < errlist.size(); i++) {
            	String wj = (String)errlist.get(i).get("wj");
            	if(wj==null){
            		wj = "";
            	}
            	String bz = (String)errlist.get(i).get("bz");
            	if(bz==null){
            		bz="";
            	}
            	String sfhg = errlist.get(i).get("score").toString();
//            	if("1".equals(sfhg)){
//            		sfhg = "合格";
//            	}else{
//            		sfhg = "未合格";
//            	}
            	ws.addCell(new Label(0, i+1,errlist.get(i).get("zkdw").toString(),wcfFC)); 
            	ws.addCell(new Label(1, i+1,errlist.get(i).get("ckdw").toString(),wcfFC));
            	ws.addCell(new Label(2, i+1,errlist.get(i).get("xm").toString(),wcfFC));
            	ws.addCell(new Label(3, i+1,errlist.get(i).get("xb").toString(),wcfFC));
            	ws.addCell(new Label(4, i+1,errlist.get(i).get("sfzjh").toString(),wcfFC));
            	ws.addCell(new Label(5, i+1,errlist.get(i).get("kssj")+"",wcfFC));
            	ws.addCell(new Label(6, i+1,errlist.get(i).get("kdmc")+"",wcfFC));
            	ws.addCell(new Label(7, i+1,errlist.get(i).get("kcmc")+"",wcfFC));
            	ws.addCell(new Label(8, i+1,errlist.get(i).get("zwh")+"",wcfFC));
            	ws.addCell(new Label(9, i+1,sfhg,wcfFC));
            	ws.addCell(new Label(10, i+1,wj,wcfFC));
            	ws.addCell(new Label(11, i+1,bz,wcfFC)); 
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
	public Map<String, Object> getDetailByKdSDD(Session session,String ckdw) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success",true);
		WritableWorkbook wwb = null;
		WritableSheet ws = null; 
		try {
			String path = FileUpServiceImpl.class.getResource("").toString();
			path = path.substring(6,path.lastIndexOf("xjgl")+4);
			File pf =new File(path+"/export/excel/temporary/sdd");
			if(!pf.exists()){
				pf.mkdirs();
			}
			File f = new File(path+"/export/excel/temporary/sdd/"+ckdw+".xls");  
			wwb = Workbook.createWorkbook(
					f,
					Workbook.getWorkbook(new File(
							path+"/export/excel/temp/kdqk.xls")));
			ws = wwb.getSheet(0); 
			WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,   
                    UnderlineStyle.NO_UNDERLINE,Colour.BLACK);   
            WritableCellFormat wcfFC = new WritableCellFormat(wfc);
            StringBuffer sb=new StringBuffer();
            sb.append("select * From exam_score_detail where ckdw = '").append(ckdw).append("' and kdmc='湖南电大考点' ORDER BY  kdmc,kcmc,kssj ,zwh  ");
            System.out.println(sb.toString());
            List<Map<String,Object>> errlist=session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            for (int i = 0; i < errlist.size(); i++) {
            	String wj = (String)errlist.get(i).get("wj");
            	if(wj==null){
            		wj = "";
            	}
            	String bz = (String)errlist.get(i).get("bz");
            	if(bz==null){
            		bz="";
            	}
            	String sfhg = errlist.get(i).get("score").toString();
//            	if("1".equals(sfhg)){
//            		sfhg = "合格";
//            	}else{
//            		sfhg = "未合格";
//            	}
            	ws.addCell(new Label(0, i+1,errlist.get(i).get("zkdw").toString(),wcfFC)); 
            	ws.addCell(new Label(1, i+1,errlist.get(i).get("ckdw").toString(),wcfFC));
            	ws.addCell(new Label(2, i+1,errlist.get(i).get("xm").toString(),wcfFC));
            	ws.addCell(new Label(3, i+1,errlist.get(i).get("xb").toString(),wcfFC));
            	ws.addCell(new Label(4, i+1,errlist.get(i).get("sfzjh").toString(),wcfFC));
            	ws.addCell(new Label(5, i+1,errlist.get(i).get("kssj")+"",wcfFC));
            	ws.addCell(new Label(6, i+1,errlist.get(i).get("kdmc")+"",wcfFC));
            	ws.addCell(new Label(7, i+1,errlist.get(i).get("kcmc")+"",wcfFC));
            	ws.addCell(new Label(8, i+1,errlist.get(i).get("zwh")+"",wcfFC));
            	ws.addCell(new Label(9, i+1,sfhg,wcfFC));
            	ws.addCell(new Label(10, i+1,wj,wcfFC));
            	ws.addCell(new Label(11, i+1,bz,wcfFC)); 
			}
			result.put("data", f.getPath());
			result.put("count",errlist.size());
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
