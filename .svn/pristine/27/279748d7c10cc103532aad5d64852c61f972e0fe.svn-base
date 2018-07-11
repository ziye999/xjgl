package com.jiajie.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * ʹ��AES���ļ����м��ܺͽ���
 *
 */
public class CipherUtil {
	/**
	 * ʹ��AES���ļ����м��ܺͽ���
	 * 
	 */
	private static String type = "AES";

	/**
	 * ���ļ�srcFile���ܺ�洢ΪdestFile
	 * @param srcFile     ����ǰ���ļ�
	 * @param destFile    ���ܺ���ļ�
	 * @param privateKey  ��Կ
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public void encrypt(String srcFile, String destFile, String privateKey) throws GeneralSecurityException, IOException {
		Key key = getKey(privateKey);
		Cipher cipher = Cipher.getInstance(type + "/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(mkdirFiles(destFile));

			crypt(fis, fos, cipher);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.flush();
				fos.close();
			}
		}
	}

	/**
	 * ���ļ�srcFile���ܺ�洢ΪdestFile
	 * @param srcFile     ����ǰ���ļ�
	 * @param destFile    ���ܺ���ļ�
	 * @param privateKey  ��Կ
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public void decrypt(String srcFile, String destFile, String privateKey) throws GeneralSecurityException, IOException {
		Key key = getKey(privateKey);
		Cipher cipher = Cipher.getInstance(type + "/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);

		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(mkdirFiles(destFile));

			crypt(fis, fos, cipher);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally { 
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.flush();
				fos.close();
			}
		}
	}

	/**
	 * ���filePath������Ӧ��Ŀ¼
	 * @param filePath		Ҫ�������ļ�·��
	 * @return	file		�ļ�
	 * @throws IOException
	 */
	private File mkdirFiles(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();

		return file;
	}

	/**
	 * ���ָ���ַ����Կ
	 * @param secret  		Ҫ�����Կ���ַ�
	 * @return secretKey    ��ɺ����Կ
	 * @throws GeneralSecurityException
	 */
	private static Key getKey(String secret) throws GeneralSecurityException {
		KeyGenerator kgen = KeyGenerator.getInstance(type);
		kgen.init(128, new SecureRandom(secret.getBytes()));
		SecretKey secretKey = kgen.generateKey();
		return secretKey;
	}

	/**
	 * ���ܽ�����
	 * @param in		���ܽ���ǰ����
	 * @param out		���ܽ��ܺ����
	 * @param cipher	���ܽ���
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	private static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {
		int blockSize = cipher.getBlockSize() * 1000;
		int outputSize = cipher.getOutputSize(blockSize);

		byte[] inBytes = new byte[blockSize];
		byte[] outBytes = new byte[outputSize];

		int inLength = 0;
		boolean more = true;
		while (more) {
			inLength = in.read(inBytes);
			if (inLength == blockSize) {
				int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
				out.write(outBytes, 0, outLength);
			} else {
				more = false;
			}
		}
		if (inLength > 0)
			outBytes = cipher.doFinal(inBytes, 0, inLength);
		else
			outBytes = cipher.doFinal();
		out.write(outBytes);
	}
}
