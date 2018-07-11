package com.jiajie.service.signup.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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

import org.springframework.stereotype.Service;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.impl.FileUpServiceImpl;
import com.jiajie.service.signup.CjdcService;
import com.jiajie.util.bean.MBspInfo;
@Service("CjdcService")
public class CjdcServiceImpl extends ServiceBridge implements CjdcService{

	@Override
	public PageBean getCj(String xn,MBspInfo msb) {
		String sql = null;
		if(msb.getUserName().equals("xx")){
			String nn = "";
			String xq = "";
			if(xn!=null && !"".equals(xn)){
				String[] str = xn.split(" ");
				nn = str[0].substring(0, 4);
				xq = str[1].substring(1, 2);	
			}
		
		
			 sql ="SELECT c.nj,c.XM,c.SFZJH,CASE WHEN c.xbm = 1 THEN '男' ELSE '女' END AS xbm ,z1.JTDM," +
					"z1.ZW,(SELECT zflb FROM zd_zflb WHERE id = z1.zflx) zflx," +
					"z1.zffw,z.XXMC,z1.fzdw fzdw , z1.ZY,e.XN," +
					"(select mc FROM zd_xq where dm = e.XQM ) xq," +
					"(SELECT mc FROM  zd_mz WHERE dm = z1.MZM ) MZM ," +
					" (SELECT mc FROM zd_zzmm WHERE dm = z1.ZZMM ) ZZMM," +
					"(SELECT mc FROM zd_xd WHERE dm = z1.WHCD ) WHCD ,z.SSZGJYXZMC " +
					"FROM cus_kw_examinee c  " +
					"JOIN cus_kwbm_examround e ON  e.bmlcid = c.bmlcid " +
					" JOIN zxxs_xx_jbxx z ON  z.XX_JBXX_ID = c.XXDM " +
					"JOIN zxxs_xs_jbxx z1 ON z1.sfzjh= c.sfzjh " +
					"WHERE c.nj='合格'";
	
				if(nn!=null && !"".equals(nn)){
					sql += " and e.XN='"+nn+"'";
				}
				if(xq!=null && !"".equals(xq)){
					sql += " and e.XQM= '"+xq+"'";
				}
			
		}
		return getSQLPageBean(sql);
		
	}

	@Override
	public com.jiajie.bean.MsgBean dxsl(MBspInfo mbs) {
		if(mbs.getUserName().equals("xx")){
			MsgBean mb = new MsgBean();
			WritableWorkbook wwb = null;
			WritableSheet ws = null;
			try {
				String path = FileUpServiceImpl.class.getResource("").toString();
				path = path.substring(6,path.lastIndexOf("xjgl")+4);
				path=File.separator+path;
				File f = new File(path + "/export/excel/temporary/" +
						new Date().getTime() + ".xls");
				wwb = Workbook.createWorkbook(f,Workbook.getWorkbook(new File(File.separator+path+"/export/excel/temp/daochu.xls")));
				ws = wwb.getSheet(0);
				mb.setSuccess(true);
				mb.setData(f.getPath().substring(f.getPath().indexOf("export")));
				WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,
						UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
				WritableCellFormat wcfFC = new WritableCellFormat(wfc);
				
				String sql ="SELECT c.nj,c.XM,c.SFZJH,CASE WHEN c.xbm = 1 THEN '男' ELSE '女' END AS xbm ," +
						"z1.ZW,(SELECT zflb FROM zd_zflb WHERE id = z1.zflx) zflx," +
						"z1.zffw,z.XXMC,IFNULL(z1.fzdw,'') fzdw , z1.ZY,e.XN," +
						"(select mc FROM zd_xq where dm = e.XQM ) xq," +
						"(SELECT mc FROM  zd_mz WHERE dm = z1.MZM ) MZM ," +
						" (SELECT mc FROM zd_zzmm WHERE dm = z1.ZZMM ) ZZMM,(SELECT mc FROM zd_xd WHERE dm = z1.WHCD ) WHCD ,z.SSZGJYXZMC " +
						"FROM cus_kw_examinee c  " +
						"JOIN cus_kwbm_examround e ON  e.bmlcid = c.bmlcid " +
						" JOIN zxxs_xx_jbxx z ON  z.XX_JBXX_ID = c.XXDM " +
						"JOIN zxxs_xs_jbxx z1 ON z1.sfzjh= c.sfzjh " +
						"WHERE c.nj='合格'";
				
			
				List<Map<String,Object>> list=getListSQL(sql);
				for (int i = 0; i < list.size(); i++) {
					ws.addCell(new Label(0, i+1,(i + 1)+""));
					ws.addCell(new Label(1, i+1,""));
					ws.addCell(new Label(2, i+1,""));
					ws.addCell(new Label(3, i+1,list.get(i).get("XM").toString()));
					ws.addCell(new Label(4, i+1,list.get(i).get("SFZJH").toString()));
					ws.addCell(new Label(5, i+1,list.get(i).get("xbm").toString()));
					ws.addCell(new Label(6, i+1,list.get(i).get("ZW").toString()));
					ws.addCell(new Label(7, i+1,list.get(i).get("zflx").toString()));
					ws.addCell(new Label(8, i+1,list.get(i).get("zffw").toString()));
					ws.addCell(new Label(9, i+1,list.get(i).get("XXMC").toString()));
					ws.addCell(new Label(10, i+1,list.get(i).get("fzdw").toString()));
					ws.addCell(new Label(11, i+1,""));
					ws.addCell(new Label(12, i+1,""));
					ws.addCell(new Label(13, i+1,list.get(i).get("MZM").toString()));
					ws.addCell(new Label(14, i+1,list.get(i).get("ZZMM").toString()));
					ws.addCell(new Label(15, i+1,list.get(i).get("WHCD").toString()));
					ws.addCell(new Label(16, i+1,list.get(i).get("SSZGJYXZMC").toString()));
					ws.addCell(new Label(17, i+1,list.get(i).get("XN").toString(),wcfFC));
					ws.addCell(new Label(18, i+1,list.get(i).get("xq").toString()));
			
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
		
		}else{
			return getMsgBean(false,"登录账号不对！");
		}
		
		
	}

}
