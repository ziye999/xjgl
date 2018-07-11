package com.jiajie.util; 
import java.security.Key;  
import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;  
import javax.crypto.SecretKey;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESKeySpec;
public class SecUtils {  
    private static final String KEY_ALGORITHM="DES";  
    private static final String CIPHER_ALGORITHM="DES/ECB/PKCS5Padding";  
    private static final String KEY = "DFKDFJ8DIR23232FDFDA3478384JEKD8RK43435EKFD8J3DSFED4";
    public static byte[] initkey() throws Exception{  
        KeyGenerator kg=KeyGenerator.getInstance(KEY_ALGORITHM);  
        SecretKey secretKey=kg.generateKey();  
        return secretKey.getEncoded();  
    }  
    public static Key toKey(byte[] key) throws Exception{  
        //实例化Des密钥  
        DESKeySpec dks=new DESKeySpec(key);  
        //实例化密钥工厂  
        SecretKeyFactory keyFactory=SecretKeyFactory.getInstance(KEY_ALGORITHM);  
        //生成密钥  
        SecretKey secretKey=keyFactory.generateSecret(dks);  
        return secretKey;  
    }  
    public static String encode(String data) throws Exception{  
        Key k=toKey(KEY.getBytes("utf-8"));  
        Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);  
        cipher.init(Cipher.ENCRYPT_MODE, k);  
        return new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes("utf-8")));  
    }  
    public static String decode(String str){  
    	try {
    		byte[] data = new sun.misc.BASE64Decoder().decodeBuffer(str);
    		Key k =toKey(KEY.getBytes("utf-8"));  
    		Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);  
    		cipher.init(Cipher.DECRYPT_MODE, k);  
    		return new String(cipher.doFinal(data),"utf-8");  
		} catch (Exception e) {
			return "";
		}
    }  
     
}  