package com.jiajie.util;

/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;*/
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * StringUtil 
 */
public class StringUtil {
	public static final String LOGIN_RANDOM_CODE_KEY = "sessionloginrandomcode";
	/**
	 * 将字符串拆分成数组
	 */
	public static String[] str2Arr(String str,String tag){
		if(ValidateUtil.isValid(str)){
			return str.split(tag);
		}
		return null ;
	}

	/**
	 * 判断数组中是否含有指定的串
	 */
	public static boolean contains(String[] arr, String value) {
		if(ValidateUtil.isValid(arr)){
			for(String s : arr){
				if(s.equals(value)){
					return true ;
				}
			}
		}
		return false;
	}

	/**
	 * 将数组转换成字符串,使用","分隔
	 */
	public static String arr2Str(Object[] value) {
		String temp = "" ;
		if(ValidateUtil.isValid(value)){
			for(Object o : value){
				temp = temp + o + ",";
			}
			return temp.substring(0, temp.length() -1 );
		}
		return null;
	}
	
	public static Boolean isNull(String value) {
		if (null == value || "".equals(value)) {
			return true;
		}
		return false;
	}
	
	public static String getString(Object obj){
		if(obj==null){
			return null;
		}else{
			return obj.toString();
		}
	}
	
	public static Integer getInteger(Object obj){
		if(obj==null){
			return null;
		}else{
			return Integer.valueOf(obj.toString());
		}
	}
	
	public static Double getDouble(Object obj){
		if(obj==null){
			return null;
		}else{
			return Double.valueOf(obj.toString());
		}
	}
	//判断值是否为null，将值转换成Date类型
	public static Date getDate(Object obj){
		if(obj!=null && !"".equals(obj.toString())){
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = format.parse(obj.toString());
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
		return null;
	}
	  
	public static Date getDateAll(Object obj){
		if(obj!=null && !"".equals(obj.toString())){
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
			Date date = null;
			try {
				date = format.parse(obj.toString());
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
		return null;
	}
	
	/**
	 *
	 * Description:将Clob对象转换为String对象,Blob处理方式与此相同
	 *
	 * @param clob
	 * @return
	 * @throws Exception
	 * @mail sunyujia@yahoo.cn
	 * @blog blog.csdn.ne t/sunyujia/
	 * @since：Oct 1, 2008 7:19:57 PM
	 */
	/*public static String oracleClob2Str(Clob clob) throws Exception {
       return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
   	}*/
   
	public static String getUUID() {  
		UUID uuid = UUID.randomUUID();  
		String str = uuid.toString();  
		// 去掉"-"符号  
		String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);  
		return temp;  
	}  
   
	/*public static String ClobToString(Clob clob) {
       String reString = "";
       Reader is = null;
       try {
           is = clob.getCharacterStream();
       } catch (SQLException e) {
           e.printStackTrace();
       }
       // 得到流
       BufferedReader br = new BufferedReader(is);
       String s = null;
       try {
           s = br.readLine();
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       StringBuffer sb = new StringBuffer();
       while (s != null) {
           //执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
           sb.append(s);
           try {
               s = br.readLine();
           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
       }
       reString = sb.toString();
       return reString;
   }*/
   
   public static String getWeekName(int week){
		String weekName="";
		switch (week) {
			case 1:
				weekName="星期日";
				break;
			case 2:
				weekName="星期一";
				break;
			case 3:
				weekName="星期二";
				break;
			case 4:
				weekName="星期三";
				break;
			case 5:
				weekName="星期四";
				break;
			case 6:
				weekName="星期五";
				break;
			case 7:
				weekName="星期六";
				break;
			default:
				break;
		}
		return weekName;
	}
   /**
    * 判断当前日期是星期几
    * 
    * @param pTime 修要判断的时间
    * @return dayForWeek 判断结果
    * @Exception 发生异常
    */
	public static String dayForWeek(String pTime) {
		try {
			if (pTime==null) return "";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();

			c.setTime(format.parse(pTime));

			int dayForWeek = c.get(Calendar.DAY_OF_WEEK);
			return getWeekName(dayForWeek);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(dayForWeek("2015-06-15"));
	}
	
   public static Boolean isNotNullOrEmpty(String str){
	   if(str!=null && !"".equals(str))
		   return true;
	   else
		   return false;
   }
   
   public static boolean deleteDir(File dir) {
       if (dir.isDirectory()) {
           String[] children = dir.list();
           for (int i=0; i<children.length; i++) {
               boolean success = deleteDir(new File(dir, children[i]));
               if (!success) {
                   return false;
               }
           }
       }
       // 目录此时为空，可以删除
       return dir.delete();
   }
   
   /** 
    * @param path 
    * @return curPath 
    * @see 把path中的所有的多个“\”“/”转成开始的第一个 
    */ 
   public static String SignSolve(String path){ 
	   int mark = 0; 
	   String curPath = ""; 
	   for (int i = 0; i < path.length(); i++) { 
		   //ASCII比对“/”=47 
		   if ((int) path.charAt(i) == 47) { 
			   mark++; 
			   if (mark == 1) 
				   curPath += path.charAt(i); 
		   }else if ((int) path.charAt(i) == 92) { 
			   mark++; 
			   if (mark == 1) curPath += path.charAt(i); 
		   }else { 
			   mark = 0; 
		   } 

		   if (mark == 0) { 
			   curPath += path.charAt(i); 
		   } 
	   } 
	   return curPath; 
   } 
   
   /** 
    * @param path 
    * @return 路径中的所有斜杠换成“\” 
    * @exception java.lang.StringIndexOutOfBoundsException 
   */ 
   public static String ReplaceAll_Z(String path){ 
	   path=SignSolve(path); 
	   if(path!=null&&path!=""){ 
		   path=path.replaceAll("/", "\\\\"); 
	   } 
	   return path; 
   } 

}
