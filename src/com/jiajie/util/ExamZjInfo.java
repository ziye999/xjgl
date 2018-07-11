package com.jiajie.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class ExamZjInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ExamZjInfo examZjInfo = new ExamZjInfo();
	
	public static String url = null;
	
	static{
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = ExamZjInfo.class.getResourceAsStream("/constant.properties"); 
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		url = prop.getProperty("url");
	}
	
	//当前组卷用户
	private String userName;
	
	//当前是否正在整理
	private static boolean paperClearFlag;
	
	public static boolean isPaperClearFlag() {
		return paperClearFlag;
	}
 
	public static void setPaperClearFlag(boolean paperClearFlag) {
		ExamZjInfo.paperClearFlag = paperClearFlag;
	}

	public String getUserName(){
		return this.userName;
	}
	
	//状态
	private Integer status = 1;
	//自定义锁
	private boolean lock;
	
	private List<String> sqlList;
	//当前组卷所有考生
	private List<String> kslist;
	
	private Map<String,List<String>> addks;
	//当前完成的Thread总数
	private int doneThreads = 0;
	
	private int threads = 0;
	
	private boolean isOk ;
	
	private StringBuffer error;
	
	private boolean err;
	
	public StringBuffer getError() {
		return error;
	}

	public boolean isErr() {
		return err;
	}
	
	public boolean isDoneByThreads(){
		return isOk;
	}

	public void addErrorInfo(String info){
		err = true;
		if(error.indexOf(info)==-1){
			error.append(info);
		}
	}
	
	public synchronized void addThreadsNum(){
		threads = threads +1; 
	}
	
	public synchronized void addDoneThreadsNum(){
		doneThreads = doneThreads +1; 
		if(doneThreads==threads){
			isOk = true;
		}
		System.out.println(threads+"             "+doneThreads);
	}
	
	public Map<String, List<String>> getAddks() {
		return addks;
	}

	public int getDoneThreadsNum() {
		return threads;
	}

	public void isDone() {
		status = 1;
	}

	public synchronized void addKtToKs(String xsid,String cus){
		if(addks.get(xsid)==null){
			List<String> list = new ArrayList<String>();
			list.add(cus);
			addks.put(xsid, list);
		}else{
			addks.get(xsid).add(cus);
		}
	}
	
	public Integer getStatus() {
		while(lock){
			
		}
		return status;
	}
	
	public synchronized void init(String userName){
		lock = true;
		if(status!=0){
			status = 0; 
			this.userName = userName;
			kslist = null;
			addks = new HashMap<String, List<String>>();
			threads = 0;
			doneThreads = 0;
			isOk = false;
			error = new StringBuffer();
			err = false;
		}
		lock = false;
	}
	 
	public List<String> getKslist() {
		return kslist;
	}

	public void setKslist(List<String> kslist) {
		this.kslist = kslist;
	}
 
	private ExamZjInfo() {
	}
	 
	
	public synchronized void addSqlByKey(String cus){ 
		sqlList.add(cus); 
	}
	
	public List<String> getSqlsBykey(){ 
		return sqlList;
	}
	
	public synchronized String assignObjectByKey(){ 
		if(kslist==null || kslist.size()==0){
			kslist = null;
			return null;
		}else{
			String res = kslist.remove(0).toString(); 
			return res.toString();
		}
	}
	
	public static ExamZjInfo getInstance(){
		return examZjInfo;
	}
	
}

