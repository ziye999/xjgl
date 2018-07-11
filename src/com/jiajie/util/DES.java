package com.jiajie.util;

import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES {

  public DES() {
  }

  /**
   * ͳһ��¼��Կ
   */
  private final static byte[] EncryptionKEY = "OHMYGODZHENFANREN".getBytes();
  private final static byte[] EncryptionIV = "KJHISUHD".getBytes();
  private final static String DES = "DES/CBC/PKCS5Padding";
  private static Cipher cipher;

  /**
   * ����
   */
  public final static String _GBK = "GBK";

  /**
   * DES����
   * @param s String ��������
   * @return String ����
   */
  public static String decodeDES(String s) {
    try {
      byte[] enbyte = new sun.misc.BASE64Decoder().decodeBuffer(s);
      DESKeySpec dks = new DESKeySpec(EncryptionKEY);
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      SecretKey key = keyFactory.generateSecret(dks);
      IvParameterSpec iv = new IvParameterSpec(EncryptionIV);
      AlgorithmParameterSpec paramSpec = iv;
      cipher = Cipher.getInstance(DES);
      cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
      byte[] cipherText = cipher.doFinal(enbyte);
      return (new String(cipherText, _GBK));
    }
    catch (Exception e) {
      //e.printStackTrace();
    }
    return s;
  }

  /**
   * DES����
   * @param s String ��������
   * @return String ����
   */
  public static String encodeDES(String s) {
    try {
      DESKeySpec dks = new DESKeySpec(EncryptionKEY);
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      SecretKey key = keyFactory.generateSecret(dks);
      IvParameterSpec iv = new IvParameterSpec(EncryptionIV);
      AlgorithmParameterSpec paramSpec = iv;
      cipher = Cipher.getInstance(DES);
      cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
      byte[] cipherText = cipher.doFinal(s.getBytes());
      String str = new sun.misc.BASE64Encoder().encode(cipherText);
      return str;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return s;
  } 
   
}
