package com.jiajie.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.jiajie.action.signup.SignUpAction;

public class bmtime {
	
	public static boolean checkTime(){
		Properties properties = new Properties();
		InputStream in = SignUpAction.class.getClassLoader().getResourceAsStream("kssj.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String year = properties.getProperty("year");
		String month = properties.getProperty("month");
		String startday = properties.getProperty("startday");
		String endday = properties.getProperty("endday");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = df.format(new Date());
		String nowyear = str.substring(0, 4);
		String nowmonth = str.substring(6, 7);
		String nowday = str.substring(8, 10);
		if(nowyear.equals(year)&&nowmonth.equals(month)&&(((Integer.valueOf(startday)<=Integer.valueOf(nowday))&&(Integer.valueOf(endday))>=Integer.valueOf(nowday)))){	
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean checkPZ(){
		Properties properties = new Properties();
		InputStream in = SignUpAction.class.getClassLoader().getResourceAsStream("kssj.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String year = properties.getProperty("year");
		String pzmonth = properties.getProperty("pzmonth");
		String pzday = properties.getProperty("pzday");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = df.format(new Date());
		String nowyear = str.substring(0, 4);
		String nowmonth = str.substring(6, 7);
		String nowday = str.substring(8, 10);
		if(nowyear.equals(year)&&nowmonth.equals(pzmonth)&&nowday.equals(pzday)){
			return true;
		}else{
			return false;
		}
	}
}
