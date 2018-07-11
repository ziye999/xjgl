package com.jiajie.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class ImportUtil {
	
	/*
	 * 功能   校验批量导入模板是否正确
	 * 作者   吴彬彬
	 * 参数   导入的文件       模板的名称
	 */
	public static Map<String,Object> checkImportExcelModel(File impfile,String modelPath) {
			Map<String,Object> rmap = new HashMap<String, Object>();
		try {
			 WorkbookSettings workbooksetting = new WorkbookSettings();
		     workbooksetting .setCellValidationDisabled(true);
			Workbook wbin  = Workbook.getWorkbook(impfile,workbooksetting);
			String path = ImportUtil.class.getResource("").toString();
			path = path.substring(6,path.indexOf("xjgl/WEB-INF")+4);
			
			Workbook wbm  = Workbook.getWorkbook(new File(File.separator+path+"/export/excel/temp/"+modelPath),workbooksetting);
			Sheet rs = wbin.getSheet(0);
			Sheet rs2 = wbm.getSheet(0);
			int col = rs2.getColumns();
			if(rs.getColumns()!=col){
				if(rs.getColumns()!=col+1){
					rmap.put("success",false);
				}else{
					if(rs.getCell(col,1 ).getContents()!=null && !"".equals(rs.getCell(col,1 ).getContents().trim())){
						rmap.put("success",false);
					}
				}
			}
			for (int i = 0; i < col; i++) {
				if(!rs2.getCell(i,1).getContents().equals(rs.getCell(i, 1).getContents())){
					rmap.put("success",false);
				}
			}
			rmap.put("success",true);
			rmap.put("col",col);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return rmap;
	}
	 
}
