package com.jiajie.core.minterface;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

/**
 * edu接口测试
 * @author vivi207
 */
public class EduInterface {
	static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	//接口地址
	private static String URL = "http://www.hneeid.com:8079/eduInterface/interface/interface";
	
	
	public static void main(String[] args) throws IOException {
		//接入系统ID
		String account = "fxm001";
		//接入系统密码
		String password = "fxm001";
		//秘钥
		String key = "fxm001";
		
		//请求服务ID
		String server = "Cloud_id_service";
		//请求服务方法
		String method = "idSearch";
		
		//初始化密码加密
		password = md5(password);
		
		//生成签名
		Object[] parameter = new Object[]{"431111111982"};
		
		
		String data = remote(server, method, account, password, key, parameter);
	}
	
	/**
	 * 签名加密
	 * @param server
	 * @param method
	 * @param parameter
	 * @param key
	 * @return
	 */
	public static String encrypt(String server,String method, Object[] parameter, String key){
		StringBuffer encryptStr = new StringBuffer();
		encryptStr.append(server).append(method);
		if(parameter!=null && parameter.length>0){
			for(Object obj : parameter){
				encryptStr.append(obj);
			}
		}
		encryptStr.append(key);
		String encrypt = md5(encryptStr.toString());
		return encrypt;
	}
	
	/**
	 * 请求接口
	 * @param encrypt
	 * @param parameter
	 * @throws IOException
	 */
	public static String remote(String server, String method, String account, String password, String key,  Object[] parameter) throws IOException{
		String encrypt = encrypt(server, method, parameter , key);
		
		URL url = new URL(URL);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setConnectTimeout(3600000);
		
		//设置Head
		conn.addRequestProperty("account", account);
		conn.addRequestProperty("password", password);
		conn.addRequestProperty("encrypt", encrypt);
		
		StringBuffer param = new StringBuffer();
		param.append("&server="+server);
		param.append("&method="+method);
		//传递参数
		if(parameter!=null && parameter.length>0){
			for(Object o : parameter){
				param.append("&parameter=").append(java.net.URLEncoder.encode(o==null?"":o.toString()));
			}
		}
		conn.getOutputStream().write(param.toString().getBytes());
		
		if(conn.getResponseCode()==200){
			//请求成功
			return streamToString(conn.getInputStream());
		}else{
			//请求失败
			return streamToString(conn.getErrorStream());
		}
	}
	
	/**
	 * 将流中字符读取成字符串
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String streamToString(InputStream in) throws IOException{
		InputStreamReader inr = null;
		char[] tmp = new char[128];
		int len;
		StringBuffer text = new StringBuffer();
		try {
			inr = new InputStreamReader(in, "UTF-8");
			while((len=inr.read(tmp))>-1){
				text.append(tmp,0 , len);
			}
			return text.toString();
		}finally{
			if(inr!=null){
				inr.close();
			}
		}
	}
	
	/**
	 * md5加密
	 * @param source
	 * @return
	 */
	public final static String md5(String source) {
		return getMD5(source.getBytes());
	}
	public final static String getMD5(byte[] source) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
