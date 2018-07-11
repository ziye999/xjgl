package com.jiajie.util;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
    
public class PDFReport{    
    Document document = new Document();// 建立一个Document对象        
        
    private static Font headfont ;// 设置字体大小    
    private static Font keyfont;// 设置字体大小    
    private static Font textfont;// 设置字体大小    
        
    
        
    static{    
        BaseFont bfChinese;    
        try {    
            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);    
            bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);    
            headfont = new Font(bfChinese, 18, Font.BOLD);// 设置字体大小    
            keyfont = new Font(bfChinese, 15, Font.NORMAL);// 设置字体大小    
            textfont = new Font(bfChinese, 12, Font.NORMAL);// 设置字体大小    
        } catch (Exception e) {             
            e.printStackTrace();    
        }     
    }    
        
        
    public PDFReport(File file) {            
         document.setPageSize(PageSize.A4);// 设置页面大小    
         try {    
            PdfWriter.getInstance(document,new FileOutputStream(file));    
            document.open();     
        } catch (Exception e) {    
            e.printStackTrace();    
        }     
            
            
    }    
    int maxWidth = 520;    
        
        
     public PdfPCell createCell(int border,String value,com.lowagie.text.Font font,int align){    
         PdfPCell cell = new PdfPCell(); 
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);            
         cell.setHorizontalAlignment(align);        
         cell.setPhrase(new Phrase(value,font)); 
         cell.setBorder(border);
         cell.setMinimumHeight(22);
        return cell;    
    }    
        
     public PdfPCell createCell(String value,com.lowagie.text.Font font){    
         PdfPCell cell = new PdfPCell();    
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);    
         cell.setHorizontalAlignment(Element.ALIGN_CENTER);     
         cell.setPhrase(new Phrase(value,font));    
        return cell;    
    }    
    
     public PdfPCell createCell(String value,com.lowagie.text.Font font,int align,int colspan){    
         PdfPCell cell = new PdfPCell();    
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);    
         cell.setHorizontalAlignment(align);        
         cell.setColspan(colspan);    
         cell.setPhrase(new Phrase(value,font));    
        return cell;    
    }    
    public PdfPCell createCell(String value,com.lowagie.text.Font font,int align,int colspan,boolean boderFlag){    
         PdfPCell cell = new PdfPCell();    
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);    
         cell.setHorizontalAlignment(align);        
         cell.setColspan(colspan);    
         cell.setPhrase(new Phrase(value,font));    
         cell.setPadding(3.0f);    
         if(!boderFlag){    
             cell.setBorder(0);    
             cell.setPaddingTop(15.0f);    
             cell.setPaddingBottom(8.0f);    
         }    
        return cell;    
    }    
     public PdfPTable createTable(int colNumber){    
        PdfPTable table = new PdfPTable(colNumber);    
        try{     
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);    
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);    
        }catch(Exception e){    
            e.printStackTrace();    
        }    
        return table;    
    }    
     public PdfPTable createTable(float[] widths){    
            PdfPTable table = new PdfPTable(widths);    
            try{
                table.setTotalWidth(maxWidth);    
                table.setLockedWidth(true);
                
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
            }catch(Exception e){
                e.printStackTrace();
            }
            return table;    
     } 
     
     public PdfPTable createBlankTable(){    
         PdfPTable table = new PdfPTable(1);    
         table.getDefaultCell().setBorder(0);  
         table.addCell("");             
         table.setSpacingAfter(20.0f);    
         table.setSpacingBefore(20.0f);    
         return table;    
     }    
         
     public void generatePDF(List<Map<String,Object>> kslist,String path) throws Exception{
    	 try {
			
		
    	 for (int i = 0; i < kslist.size(); i++) {
    		 document.newPage();
    		 document.addAuthor("湖南省法制办");
    		 PdfPTable table = createTable(2);   
         	 float[] f= new float[2];
         	 f[0] = 3f;
         	 f[1] = 7f;
         	 table.setWidths(f);
         	 table.setLockedWidth(true);
    		 PdfPCell cell = createCell(1,"湖南省行政执法人员资格电子化考试", headfont, Element.ALIGN_CENTER);
    		 cell.setColspan(2);
    		 cell.setBorderWidthLeft(1);
    		 cell.setBorderWidthRight(1);
    		 table.addCell(cell);     
    		 PdfPCell cell1 = createCell(0,"准考证", keyfont, Element.ALIGN_CENTER);
    		 cell1.setColspan(2);
    		 cell1.setBorderWidthLeft(1);
    		 cell1.setBorderWidthRight(1);
    		 table.addCell(cell1); 
    		 Image img = null;
    		 try {
    			 String url = path+"uploadFile"+File.separator+"photo"+File.separator+kslist.get(i).get("PATH");
    			 img = Image.getInstance(path+File.separator+"uploadFile"+File.separator+"photo"+File.separator+kslist.get(i).get("PATH"));
			} catch (Exception e) {
				img = Image.getInstance(path+"img"+File.separator+"tx_m.png");
			} 
    		 img.scaleAbsolute(80,100);
    		 PdfPCell cell2 = new PdfPCell(img);
    		 cell2.setPaddingTop(8);
    		 cell2.setVerticalAlignment(Element.ALIGN_TOP);            
             cell2.setHorizontalAlignment(Element.ALIGN_CENTER);        
    		 cell2.setRowspan(9); 
    		 cell2.setBorderWidthRight(0); 
    		 cell2.setBorderWidthTop(0);
    		 table.addCell(cell2); 
    		 PdfPCell cell3 = createCell(0,"姓名："+kslist.get(i).get("XM"), textfont, Element.ALIGN_LEFT);
    		 cell3.setBorderWidthRight(1);
    		 PdfPCell cell4 = createCell(0,"性别："+kslist.get(i).get("XB"), textfont, Element.ALIGN_LEFT);
    		 cell4.setBorderWidthRight(1);
    		 PdfPCell cell5 = createCell(0,"考场号：第"+kslist.get(i).get("KCBH").toString()+"考场", textfont, Element.ALIGN_LEFT);
    		 cell5.setBorderWidthRight(1);
    		 PdfPCell cell6 = createCell(0,"座位号："+kslist.get(i).get("ZWH"), textfont, Element.ALIGN_LEFT);
    		 cell6.setBorderWidthRight(1);
    		 PdfPCell cell7 = createCell(0,"身份证号："+kslist.get(i).get("SFZJH"), textfont, Element.ALIGN_LEFT);
    		 cell7.setBorderWidthRight(1);
    		 PdfPCell cell8 = createCell(0,"考试时间："+kslist.get(i).get("PCMC"), textfont, Element.ALIGN_LEFT);
    		 cell8.setBorderWidthRight(1);
    		 PdfPCell cell9 = createCell(0,"参考单位："+kslist.get(i).get("XX"), textfont, Element.ALIGN_LEFT);
    		 cell9.setBorderWidthRight(1); 
    		 table.addCell(cell3);
    		 table.addCell(cell4); 
    		 table.addCell(cell5); 
    		 table.addCell(cell6); 
    		 table.addCell(cell7); 
    		 table.addCell(cell8); 
    		 table.addCell(cell9);  
    		 PdfPCell pcell = createCell(0,"考场地址："+kslist.get(i).get("KDDZ")+" "+"第"+kslist.get(i).get("FLOORS")+"楼"+kslist.get(i).get("ROOMNAME"), textfont, Element.ALIGN_LEFT);
    		 pcell.setBorderWidthRight(1);
    		 table.addCell(pcell);
    		 PdfPCell kh = createCell(0,"", textfont, Element.ALIGN_LEFT);
    		 kh.setBorderWidthBottom(1);
    		 kh.setBorderWidthRight(1);
    		 table.addCell(kh);
    		 document.add(table); 
    		 document.add(createBlankTable());
    		 PdfPTable table2 = createTable(1);  
    		 PdfPCell kscell = createCell(1,"考生须知", headfont, Element.ALIGN_CENTER);
    		 kscell.setBorderWidthLeft(1);
    		 kscell.setBorderWidthRight(1);
    		 table2.addCell(kscell);
    		 PdfPCell kscell1 =createCell(0,"  一、考生必须于开考前15分钟凭准考证和有效《居民身份证》原件进入考场，不按要求携带证件者，不得进入考场，如身份证有遗失者，需要出示派出所证明；开考30分钟后不得入场；开考30分钟内不得离场。", textfont, Element.ALIGN_LEFT);
    		 kscell1.setBorderWidthLeft(1);
    		 kscell1.setBorderWidthRight(1);
    		 kscell1.setPaddingLeft(10);
    		 PdfPCell kscell2 =createCell(0,"  二、考生签到后对号入座，将准考证、身份证放置桌面左上角备查。", textfont, Element.ALIGN_LEFT);
    		 kscell2.setBorderWidthLeft(1);
    		 kscell2.setBorderWidthRight(1);
    		 kscell2.setPaddingLeft(10);
    		 PdfPCell kscell3 =createCell(0,"  三、考生必须按照准考证规定的考试时间、场次、座位号参加考试，按要求输入本人身份证信息进入考试系统，认真阅读考场纪律，按照答题要求作答。在系统显示身份证信息时，考生须认真核对，发现错漏须及时向监考老师提出，监考老师在《考生信息更改表》上更正后，考生签名确认。", textfont, Element.ALIGN_LEFT);
    		 kscell3.setBorderWidthLeft(1);
    		 kscell3.setBorderWidthRight(1);
    		 kscell3.setPaddingLeft(10);
    		 PdfPCell kscell4 =createCell(0,"  四、考试人员应严格遵守以下规定，违反者按作弊论处，成绩无效，并且将考生信息报省法制办和省电大。", textfont, Element.ALIGN_LEFT);
    		 kscell4.setBorderWidthLeft(1);
    		 kscell4.setBorderWidthRight(1);
    		 kscell4.setPaddingLeft(10);
    		 PdfPCell kscell5 =createCell(0,"    （一）不得携带手机、电子接收（发射）及储存器材等物品进入考场；", textfont, Element.ALIGN_LEFT);
    		 kscell5.setBorderWidthLeft(1);
    		 kscell5.setBorderWidthRight(1);
    		 kscell5.setPaddingLeft(10);
    		 PdfPCell kscell6 =createCell(0,"    （二）不得携带任何与考试有关的书籍和资料；", textfont, Element.ALIGN_LEFT);
    		 kscell6.setBorderWidthLeft(1);
    		 kscell6.setBorderWidthRight(1);
    		 kscell6.setPaddingLeft(10);
    		 PdfPCell kscell7 =createCell(0,"    （三）不得就试题答案内容向监考人员询问；", textfont, Element.ALIGN_LEFT);
    		 kscell7.setBorderWidthLeft(1);
    		 kscell7.setBorderWidthRight(1);
    		 kscell7.setPaddingLeft(10);
    		 PdfPCell kscell8 =createCell(0,"    （四）保持考场安静、清洁，禁止吸烟，严禁交头接耳、左顾右盼、夹带、偷看资料，不准窥视他人屏幕。", textfont, Element.ALIGN_LEFT);
    		 kscell8.setBorderWidthLeft(1);
    		 kscell8.setBorderWidthRight(1);
    		 kscell8.setPaddingLeft(10);
    		 PdfPCell kscell9 =createCell(0,"  五、由他人冒名顶替考试的成绩无效，被替考人员、指使他人或者代替他人考试者报省法制办处理。", textfont, Element.ALIGN_LEFT);
    		 kscell9.setBorderWidthLeft(1);
    		 kscell9.setBorderWidthRight(1);
    		 kscell9.setPaddingLeft(10);
    		 PdfPCell kscell10 =createCell(0,"  六、考生应当尊重考场工作人员、自觉接受监考人员的监督和检查，服从监考人员安排。", textfont, Element.ALIGN_LEFT);
    		 kscell10.setBorderWidthLeft(1);
    		 kscell10.setBorderWidthRight(1);
    		 kscell10.setPaddingLeft(10);
    		 PdfPCell kscell11 =createCell(0,"  七、机器出现故障须举手询问，不得擅自处理；若违规操作造成相关设备损坏，应照价赔偿。", textfont, Element.ALIGN_LEFT);
    		 kscell11.setBorderWidthLeft(1);
    		 kscell11.setBorderWidthRight(1);
    		 kscell11.setPaddingLeft(10);
    		 PdfPCell kscell12 =createCell(0,"  八、考试时间终止或提前交卷后，考试人员应按要求结束考试程序，退离考场。不得在考场附近逗留，喧哗。", textfont, Element.ALIGN_LEFT);
    		 kscell12.setBorderWidthLeft(1);
    		 kscell12.setBorderWidthRight(1);
    		 kscell12.setPaddingLeft(10);
    		 PdfPCell kscell13 =createCell(0,"  九、任何与本场考试无关的人员，一律不得擅自进入考场。", textfont, Element.ALIGN_LEFT);
    		 kscell13.setBorderWidthLeft(1);
    		 kscell13.setBorderWidthRight(1);
    		 kscell13.setPaddingLeft(10);
    		 PdfPCell kscell14 =createCell(0,"  十、考试结束，考生按考试要求结束考试程序，离开考场，提前结束考试退场者不得在考场内逗留喧哗。", textfont, Element.ALIGN_LEFT);
    		 kscell14.setBorderWidthLeft(1);
    		 kscell14.setBorderWidthRight(1);
    		 kscell14.setPaddingLeft(10);
    		 PdfPCell kscell15 =createCell(0,"  十一、考生应在考试前一天熟悉考场地址和交通路线。", textfont, Element.ALIGN_LEFT);
    		 kscell15.setBorderWidthLeft(1);
    		 kscell15.setBorderWidthRight(1);
    		 kscell15.setBorderWidthBottom(1);
    		 kscell15.setPaddingLeft(10);
    		 table2.addCell(kscell1); 
    		 table2.addCell(kscell2);
    		 table2.addCell(kscell3);
    		 table2.addCell(kscell4);
    		 table2.addCell(kscell5);
    		 table2.addCell(kscell6);
    		 table2.addCell(kscell7);
    		 table2.addCell(kscell8);
    		 table2.addCell(kscell9);
    		 table2.addCell(kscell10);
    		 table2.addCell(kscell11);
    		 table2.addCell(kscell12);
    		 table2.addCell(kscell13);
    		 table2.addCell(kscell14);
    		 table2.addCell(kscell15);
    		 document.add(table2);
    	 }
    	 } catch (Exception e) {
    		 e.printStackTrace();
    	 }
    		 document.close();    
     }     
}  