package com.jiajie.util;
import java.awt.Color;
import java.io.OutputStream;
import java.util.Iterator;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class PdfUtil {
	
	   /**

	    * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以Pdf 的形式输出到指定IO设备上
	    * @param title
	    *            表格标题名
	    * @param headers
	    *            表格属性列名数组
	    * @param dataset
	    *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	    *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	    * @param out
	    *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	    * @param pattern
	    *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	    */
  
	   @SuppressWarnings("unchecked")
	public static void exportPdf(String title, JSONArray headers,
			   JSONArray dataset, OutputStream out) {
		   Document document = new Document();
		   try {
		   BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		   Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);
			PdfWriter.getInstance(document, out);
			// 步骤 3:打开文档
			   document.open();
			   //创建一个表格
			   PdfPTable table = new PdfPTable(headers.size());
			   //定义一个表格单元
			   Paragraph paragraph = new Paragraph(title+"\n\n", fontChinese);
			   paragraph.setAlignment(1);
			   paragraph.setFont(new Font(bfChinese, 20, Font.BOLD));
			   document.add(paragraph);
			   //定义一个表格单元的跨度
			   PdfPCell cell = new PdfPCell();
			   cell.setColspan(headers.size());
			   //把单元加到表格中
			   for (int i = 0; i < headers.size(); i++) {
				   JSONObject o = headers.getJSONObject(i);
				   cell = new PdfPCell(new Paragraph(o.getString("header"), fontChinese));
				   cell.setBackgroundColor(new BaseColor(0,204,255));
				   table.addCell(cell);
			   }
			   Iterator it = dataset.iterator();
			      int index = 0;
		         while (it.hasNext()) {
			         index++;
			         JSONObject  t = (JSONObject) it.next();
			         for (int i = 0; i < headers.size(); i++) {
			        	 JSONObject o = headers.getJSONObject(i);
			        	String value = t.get(o.get("dataIndex").toString()).toString().equals("null")?"":t.get(o.get("dataIndex").toString()).toString();
			        	cell = new PdfPCell(new Paragraph(value, fontChinese));
			        	table.addCell(cell);
			         }
		      }
			   //增加到文档中
			   document.add(table);
			  } catch (Exception de) {
			   System.err.println(de.getMessage());
			  } 

			  // 步骤 5:关闭文档
			  document.close();
			
//			
//			
//		   // 声明一个工作薄
//	      HSSFWorkbook workbook = new HSSFWorkbook();
//	      // 生成一个表格
//	      HSSFSheet sheet = workbook.createSheet(title);
//	      // 设置表格默认列宽度为15个字节
//	     sheet.setDefaultColumnWidth( 17);
//	      // 生成一个样式
//	      HSSFCellStyle style = workbook.createCellStyle();
//	      // 设置这些样式
//	      style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//	      style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//	      style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//	      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//	      style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//	      style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//	      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	      // 生成一个字体
//	      HSSFFont font = workbook.createFont();
//	      font.setColor(HSSFColor.VIOLET.index);
//	      font.setFontHeightInPoints((short) 12);
//	      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//	      // 把字体应用到当前的样式
//	      style.setFont(font);
//	      // 生成并设置另一个样式
//	      HSSFCellStyle style2 = workbook.createCellStyle();
//	      style2.setFillForegroundColor(HSSFColor.WHITE.index);
//	      style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//	      style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//	      style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//	      style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
//	      style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//	      style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//	      style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//	      // 生成另一个字体
//	      HSSFFont font2 = workbook.createFont();
//	      font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//	      // 把字体应用到当前的样式
//	      style2.setFont(font2);
//	      // 声明一个画图的顶级管理器
//	      HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//	      // 定义注释的大小和位置,详见文档
//	      HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
//	      // 设置注释内容
//	      comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
//	      // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//	      comment.setAuthor("leno");
//	      //产生表格标题行
//	      HSSFRow row = sheet.createRow(0);
//	       for (int i = 0; i < headers.size(); i++) {
//	         HSSFCell cell = row.createCell(i);
//	        
//	         cell.setCellStyle(style);
//	         JSONObject o = headers.getJSONObject(i);
//	      
//	         HSSFRichTextString text = new HSSFRichTextString(o.getString("header"));
//	         cell.setCellValue(text);
//	      	}
//	          Iterator it = dataset.iterator();
//		      int index = 0;
//	         while (it.hasNext()) {
//		         index++;
//		         row = sheet.createRow(index);
//		         JSONObject  t = (JSONObject) it.next();
//		         for (int i = 0; i < headers.size(); i++) {
//		        	 HSSFCell cell = row.createCell(i);
//		        	 JSONObject o = headers.getJSONObject(i);
//		        	 cell.setCellStyle(style2);
//		        	String value = t.get(o.get("dataIndex").toString()).toString().equals("null")?"":t.get(o.get("dataIndex").toString()).toString();
//			         cell.setCellValue(value);
//		         }
//	      }
//	      
//	      
//	      try {
//	         workbook.write(out);
//	      } catch (IOException e) {
//	    	  e.printStackTrace();
//	      }
	   }

}
