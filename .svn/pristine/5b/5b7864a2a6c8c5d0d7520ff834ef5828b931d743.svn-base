package com.jiajie.text;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jiajie.bean.MsgBean;
import com.jiajie.service.core.impl.FileUpServiceImpl;
import com.jiajie.util.ExporKsTasksk;
import com.jiajie.util.ExportKsInfo;
import com.jiajie.util.ImportUtil;
import com.jiajie.util.bean.XsInfo;
import com.mysql.jdbc.PreparedStatement;

public class ttestt {

	public static void main(String[] args) {
		String newName = UUID.randomUUID().toString();
		newName = newName.replace("-", "");
		System.out.println(newName);
	}

	public void export(File file) {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://10.1.17.59:3306/zfks";
		String user = "root";
		String password = "root";
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement ps = conn.createStatement();
			Workbook rwb = null;
			// WorkbookSettings 是使应用程序可以使用各种高级工作簿属性设置，若不使用则相关属性会是默认值
			WorkbookSettings workbooksetting = new WorkbookSettings();
			WritableSheet ws = null;
			int k = 0;
			StringBuffer sb = new StringBuffer();
			WritableWorkbook wwb = null;
			workbooksetting.setCellValidationDisabled(true);
			rwb = Workbook.getWorkbook(file, workbooksetting);
			Sheet rs = rwb.getSheet(0);
			// xsl有几行数据
			int clos = rs.getColumns();
			// 总共的数据个数（格子）
			int rows = rs.getRows();
			int total = 0;
			for (int i = 1; i < rows; i++){
				String ckdw = rs.getCell(2, i).getContents().trim().replace(" ", "");
				String zzdw = rs.getCell(1, i).getContents().trim().replace(" ", "");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
}
