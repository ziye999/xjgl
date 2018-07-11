package com.jiajie.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;



public class CompressFileUits {

	    /** 
	     * 解压到指定目录 
	     * @param zipPath 原路径
	     * @param descDir 解压到的目标路径
	     * @author*/  
	    public static void unZipFiles(String zipPath,String descDir)throws IOException{  
	        unZipFiles(new File(zipPath), descDir);  
	    }  
	    /** 
	     * 解压文件到指定目录 
	     * @param zipFile 
	     * @param descDir 
	     * @author isea533 
	     */  
	    @SuppressWarnings("rawtypes")  
	    public static void unZipFiles(File zipFile,String descDir)throws IOException{  
	        File pathFile = new File(descDir);  
	        if(!pathFile.exists()){  
	            pathFile.mkdirs();  
	        }  
//	        ZipFile zip = new ZipFile(zipFile); 
	        try{
		        ZipFile zip = new ZipFile(zipFile,Charset.forName("utf-8"));
		        for(Enumeration entries = zip.entries();entries.hasMoreElements();){  
		            ZipEntry entry = (ZipEntry)entries.nextElement();  
		            String pam = String.valueOf(File.separatorChar);
		            String zipEntryName = entry.getName().replaceAll("\\/","\\"+pam);  
		            InputStream in = zip.getInputStream(entry);  
		            String outPath = (descDir+File.separatorChar+zipEntryName); 
		            //判断路径是否存在,不存在则创建文件路径  
		            File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separatorChar)));  
		            if(!file.exists()){  
		                file.mkdirs();  
		            }  
		            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
		            if(new File(outPath).isDirectory()){  
		                continue;  
		            }  
		            //输出文件路径信息  
//		            System.out.println(outPath);  
		              
		            OutputStream out = new FileOutputStream(outPath);  
		            byte[] buf1 = new byte[1024];  
		            int len;  
		            while((len=in.read(buf1))>0){  
		                out.write(buf1,0,len);  
		            }  
		            in.close();  
		            out.close();  
	            }  
	        	System.out.println("******************utf-8解压完毕********************");  
	        }catch(IllegalArgumentException e ){
		    	System.out.println("utf_8解压异常捕获，使用gbk解压");
		    	unZipFilesGBK(zipFile,descDir);
		    }
	    }  
	    
	    @SuppressWarnings("rawtypes")  
	    public static void unZipFilesGBK(File zipFile,String descDir)throws IOException{  
	        File pathFile = new File(descDir);  
	        if(!pathFile.exists()){  
	            pathFile.mkdirs();  
	        }  
//	        ZipFile zip = new ZipFile(zipFile); 
	        ZipFile zip = new ZipFile(zipFile,Charset.forName("gbk"));
	        for(Enumeration entries = zip.entries();entries.hasMoreElements();){  
	            ZipEntry entry = (ZipEntry)entries.nextElement();  
	            String pam = String.valueOf(File.separatorChar);
	            String zipEntryName = entry.getName().replaceAll("\\/","\\"+pam);  
	            InputStream in = zip.getInputStream(entry);  
	            String outPath = (descDir+File.separatorChar+zipEntryName); 
	            //判断路径是否存在,不存在则创建文件路径  
	            File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separatorChar)));  
	            if(!file.exists()){  
	                file.mkdirs();  
	            }  
	            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
	            if(new File(outPath).isDirectory()){  
	                continue;  
	            }  
	            //输出文件路径信息  
	            System.out.println(outPath);  
	              
	            OutputStream out = new FileOutputStream(outPath);  
	            byte[] buf1 = new byte[1024];  
	            int len;  
	            while((len=in.read(buf1))>0){  
	                out.write(buf1,0,len);  
	            }  
	            in.close();  
	            out.close();  
	            }  
	        System.out.println("******************gbk解压完毕********************");  
	    }  



	     /**
	      * 获取文件夹下的所有文件
	      * 递归遍历指定文件夹下的文件
	      * @param folder
	      * @param keyWord
	      * @return
	      */
	     public static File[] searchFile(File folder, final String keyWord) {// 递归查找包含关键字的文件
			 
	         File[] subFolders = folder.listFiles();
	  
	         List<File> result = new ArrayList<File>();// 声明一个集合
	         for (int i = 0; i < subFolders.length; i++) {// 循环显示文件夹或文件
	             if (subFolders[i].isFile()) {// 如果是文件则将文件添加到结果列表中
	                 result.add(subFolders[i]);
	                 continue;
	             } else {// 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
	                 File[] foldResult = searchFile(subFolders[i], keyWord);
	                 for (int j = 0; j < foldResult.length; j++) {// 循环显示文件
	                     result.add(foldResult[j]);// 文件保存到集合中
	                 }
	             }
	         }
	         File files[] = new File[result.size()];// 声明文件数组，长度为集合的长度
	         result.toArray(files);// 集合数组化
	         return files;
	     }

}
