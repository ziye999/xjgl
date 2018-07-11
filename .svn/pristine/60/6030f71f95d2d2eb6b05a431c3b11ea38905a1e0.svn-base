package cn.hnjj.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.jiajie.service.exambasic.impl.ExamzjServiceImpl;
import com.jiajie.util.ZipMain;

public class PaperAnswer implements Serializable{

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;

	private String xsid;
	
	/*
	 * 描述：學生所有答案信息，
	 * 格式：key 試題id value 答案id
	 */
	private Map<String,String> answerMap;

	public String getXsid() {
		return xsid;
	}

	public void setXsid(String xsid) {
		this.xsid = xsid;
	}

	public Map<String, String> getAnswerMap() {
		return answerMap;
	}

	public void setAnswerMap(Map<String, String> answerMap) {
		this.answerMap = answerMap;
	}
	
	public static void main(String[] args) throws Exception {
		ZipMain zm = new ZipMain();
		String lj = ExamzjServiceImpl.class.getResource("").toString().replace("file:/", "").replace("classes/com/jiajie/service/exambasic/impl/", "")+"examPaper"+File.separator+new Date().getTime();
		zm.decryptUnzip("D:/test/0f980937d76348b9991b000ce0016c7e.ca", lj, "hnjjrjsyb..asdfghjkl");
		File f = new File(lj);
		File[] fs = f.listFiles();
		 
	}
}
