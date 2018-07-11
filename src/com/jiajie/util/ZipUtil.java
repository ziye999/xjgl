package com.jiajie.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.io.FileUtils;

/**
 * ���ļ����ļ��н���ѹ���ͽ�ѹ
 *
 */
public class ZipUtil {
	/**�õ���ǰϵͳ�ķָ���*/
//	private static String separator = System.getProperty("file.separator");

	/**
	 * ��ӵ�ѹ���ļ���
	 * @param out
	 * @param f
	 * @param base
	 * @throws Exception
	 */
	private void directoryZip(ZipOutputStream out, File f, String base) throws Exception {
		// ��������Ŀ¼
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			// ����ѹ������Ŀ¼
			out.putNextEntry(new ZipEntry(base + "/"));
			if (base.length() == 0) {
				base = "";
			} else {
				base = base + "/";
			}
			for (int i = 0; i < fl.length; i++) {
				directoryZip(out, fl[i], base + fl[i].getName());
			}
		} else {
			// ��ѹ���ļ�����rar��
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			byte[] bb = new byte[10240];
			int aa = 0;
			while ((aa = in.read(bb)) != -1) {
				out.write(bb, 0, aa);
			}
			in.close();
		}
	}

	/**
	 * ѹ���ļ�
	 * 
	 * @param zos
	 * @param file
	 * @throws Exception
	 */
	private void fileZip(ZipOutputStream zos, File file) throws Exception {
		if (file.isFile()) {
			zos.putNextEntry(new ZipEntry(file.getName()));
			FileInputStream fis = new FileInputStream(file);
			byte[] bb = new byte[10240];
			int aa = 0;
			while ((aa = fis.read(bb)) != -1) {
				zos.write(bb, 0, aa);
			}
			fis.close();
			System.out.println(file.getName());
		} else {
			directoryZip(zos, file, "");
		}
	}

	/**
	 * ��ѹ���ļ�
	 * 
	 * @param zis
	 * @param file
	 * @throws Exception
	 */
	private void fileUnZip(ZipInputStream zis, File file) throws Exception {
		ZipEntry zip = zis.getNextEntry();
		if (zip == null)
			return;
		String name = zip.getName();
		File f = new File(file.getAbsolutePath() + "/" + name);
		if (zip.isDirectory()) {
			f.mkdirs();
			fileUnZip(zis, file);
		} else {
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			byte b[] = new byte[10240];
			int aa = 0;
			while ((aa = zis.read(b)) != -1) {
				fos.write(b, 0, aa);
			}
			fos.close();
			fileUnZip(zis, file);
		}
	}
	
	/**
	 * ���filePath������Ӧ��Ŀ¼
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	private File mkdirFiles(String filePath) throws IOException{
		File file = new File(filePath);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
		
		return file;
	}

	/**
	 * ��zipBeforeFileĿ¼�µ��ļ�ѹ��������Ϊָ�����ļ�zipAfterFile
	 * 
	 * @param zipBeforeFile		ѹ��֮ǰ���ļ�
	 * @param zipAfterFile		ѹ��֮����ļ�
	 * @throws Exception 
	 * @throws  
	 */
	public void zip(String zipBeforeFile, String zipAfterFile) throws Exception {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(mkdirFiles(zipAfterFile)));
		fileZip(zos, new File(zipBeforeFile));
		zos.close();
	}

	/**
	 * ��ѹ���ļ�unZipBeforeFile������unZipAfterFileĿ¼��
	 * 
	 * @param unZipBeforeFile		��ѹ֮ǰ���ļ�
	 * @param unZipAfterFile		��ѹ֮����ļ�
	 * @throws Exception 
	 */
	public void unZip(String unZipBeforeFile, String unZipAfterFile) throws Exception {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(unZipBeforeFile));
			File f = new File(unZipAfterFile);
			f.mkdirs();
			fileUnZip(zis, f);
			zis.close(); 
	}
	
	public boolean zipFileAndEncode(String path,String code){
		boolean flag= false;
		File f = new File(path);
		if(f.exists()){
			if(f.isFile()){
				try {
					ZipFile zipFile = new ZipFile(f.getParent()+File.separator+f.getName().replace(".xls","")+".zip");
					ZipParameters parameters = new ZipParameters();
		            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 
		            parameters.setEncryptFiles(true);
		            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		            parameters.setPassword(code);
		            zipFile.addFile(f, parameters);
		            flag = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}  
}
