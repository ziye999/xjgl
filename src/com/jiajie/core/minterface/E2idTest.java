package com.jiajie.core.minterface;

import java.io.IOException;


public class E2idTest {
	//接入系统ID
	private static String account = "fxm001";
	//接入系统密码
	private static String password = "fxm001";
	//秘钥
	private static String key = "fxm001";

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args)  {
		try {
			eeidApply();//EEID申请
			getPersonEEID();//EEID查询
			eeidValidation();//EEID验证
			eeidOperate();//EEID挂失、解挂
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * EEID申请
	 * @param ClassType	申请数据类型：1.学生 2.教师；3.机构；4.外籍学生 5.外籍教师
	 * @param Name	姓名/部门名称	String/25个中文和50个英文/
	 * @param SexyCode	性别（非机构类型时输入）	String/25个中文和50个英文
	 * @param SFZH	身份证号/部门代码	数字或英文/18/
	 * @param PhoneNumber	手机号码	Number/
	 * @param Email	电子油箱	String/100/
	 * @param UnitName	所在院校/机构名称
	 * @return
	 * @throws IOException
	 */
	public static String eeidApply() throws IOException{
		String server = "eeidServices";
		String method = "eeidApply";
		
		
		StringBuffer json = new StringBuffer("{");
		json.append("\"ClassType\":\"").append("1").append("\",");
		json.append("\"Name\":\"").append("test").append("\",");
		json.append("\"SexyCode\":\"").append("男").append("\",");
		json.append("\"SFZH\":\"").append("430381198705055555").append("\",");
		json.append("\"PhoneNumber\":\"").append("15888888888").append("\",");
		json.append("\"UnitName\":\"").append("测试院校").append("\"");
		json.append("}"); 
		Object[] parameter = new Object[]{json};
		
		return EduInterface.remote(server, method, account, password, key, parameter);
	}
	
	/**
	 * 根据姓名与身份证号码，获取E2id等信息
	 * @return
	 * @throws IOException
	 */
	public static String getPersonEEID() throws IOException{
		String server = "eeidServices";
		String method = "getPersonEEID";
		
		String name = "姓名";//姓名
		String sfzh = "430381198705055555";//身份证号
		Object[] parameter = new Object[]{name, sfzh};
		
		return EduInterface.remote(server, method, account, password, key, parameter);
	}
	
	/**
	 * eeid验证
	 * @return
	 * @throws IOException
	 */
	public static String eeidValidation() throws IOException{
		String server = "eeidServices";
		String method = "eeidValidation";
		
		String name = "姓名";//姓名
		String sfzh = "430381198705055555";//身份证号
		String e2id = "123456";//e2id
		Object[] parameter = new Object[]{name, e2id};
		
		return EduInterface.remote(server, method, account, password, key, parameter);
	}
	
	/**
	 * 挂失、解挂
	 * @return
	 * @throws IOException
	 */
	public static String eeidOperate() throws IOException{
		String server = "eeidServices";
		String method = "eeidOperate";
		
		//operateType 操作类别：2.EEID挂失 3.EEID解挂 
		//name 名称：姓名/部门名称	String/25个中文和50个英文
		//SFZH身份证号/部门代码	数字或英文/18/ 
		//EEID教育电子身份号	Number/12/ 
		
		StringBuffer json = new StringBuffer("{");
		json.append("\"operateType\":\"").append("2").append("\",");
		json.append("\"name\":\"").append("test").append("\",");
		json.append("\"SFZH\":\"").append("430381198705055555").append("\",");
		json.append("\"EEID\":\"").append("123456").append("\"");
		json.append("}"); 
		Object[] parameter = new Object[]{json};
		
		return EduInterface.remote(server, method, account, password, key, parameter);
	}

}

