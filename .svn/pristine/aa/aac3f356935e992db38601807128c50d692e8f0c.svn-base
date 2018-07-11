package com.jiajie.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.hnjj.bean.StudentInfo;

public class ZipMain { 
	public static void encryptZip(String srcFile, String destfile, String keyStr) throws Exception {
		File temp = new File(UUID.randomUUID().toString() + ".cf");
		temp.deleteOnExit();
		new ZipUtil().zip(srcFile, temp.getAbsolutePath());
		new CipherUtil().encrypt(temp.getAbsolutePath(), destfile, keyStr);
		temp.delete();
	}
 
	public static void decryptUnzip(String srcfile, String destfile, String keyStr) throws Exception {
		File temp = new File(UUID.randomUUID().toString() + ".zip");
		temp.deleteOnExit();
		new CipherUtil().decrypt(srcfile, temp.getAbsolutePath(), keyStr);
		new ZipUtil().unZip(temp.getAbsolutePath(),destfile);
		temp.delete();
	}
	 
	public static void main(String[] args) throws Exception {    
//		
//		FileInputStream fi = new FileInputStream(new File("D:/nihao/pcid.cs")); 
//		ObjectInputStream it = new ObjectInputStream(fi); 
//		List<StudentInfo> xsids = (List<StudentInfo>) it.readObject();
//		System.out.println(xsids.size());
//		for (int i = 0; i < xsids.size(); i++) {
//			System.out.println(xsids.get(i).getXsid()+" == "+xsids.get(i).getBegindate());
//		}
//		fi.close();
//		it.close();
//		
//		ObjectInputStream	in = new ObjectInputStream(fi);
//		Map<String,String>	ksStatu= (Map<String,String>)in.readObject();
//		Iterator<String> iter2 = ksStatu.keySet().iterator();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		while(iter2.hasNext()){ 
//			String key = iter2.next();
//			String value = ksStatu.get(key); 
//			System.out.println(sdf.format(new Date(Long.parseLong(key.split(",")[0])))+" :::: "+sdf.format(new Date(Long.parseLong(key.split(",")[1])))+"  =  " + value);
//		}
//		
		ZipMain.decryptUnzip("D:/e39b932ab2314dfe98d46322d53cdad4.ca", "D:/cf", "hnjjrjsyb..asdfghjkl");
//		ZipMain.decryptUnzip("D:/nihao/5a80d219a0984570b19b132de8f0772e.cf", "D:/nihao", "hnjjrjsyb..asdfghjkl");
//		ZipMain.encryptZip("C:/Users/Administrator/Documents/Tencent Files/371645807/FileRecv/002594881e534b3fb73b577660556620.jpg",  "D:/nihao/002594881e534b3fb73b577660556620.tx", "hnjjrjsyb..asdfghjkl");
		
		
		
	}
	   
}
