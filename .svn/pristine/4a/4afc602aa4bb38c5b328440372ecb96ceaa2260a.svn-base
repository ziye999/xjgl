package com.jiajie.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileIoUtils { 
	public static void moveFile(String oldPath, String newPath) {
		//文件原地址 
		File oldFile = new File(oldPath);
		//文件新（目标）地址  
		//new一个新文件夹 
		File fnewpath = new File(newPath); 
		//判断文件夹是否存在 
		if(!fnewpath.exists()) 
		fnewpath.mkdirs(); 
		//将文件移到新文件里 
		File fnew = new File(newPath+File.separator+oldFile.getName()); 
		if(fnew.exists())
			fnew.delete();
		oldFile.renameTo(fnew); 
	}

	public static void Copy(String oldPath, String newPath)  {
		int byteread = 0;
		File oldfile = new File(oldPath);
		if (oldfile.exists()) {
			InputStream inStream = null;
			FileOutputStream fs = null;
			try {
				inStream = new FileInputStream(oldPath);
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if(inStream!=null){
						inStream.close();
					}
					if(fs!=null){
						fs.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
