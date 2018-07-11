package com.jiajie.core.minterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EduSSOInterface {
	public static void main(String[] args) {
		try {
			//http://218.76.27.33:7000/eduSSO/login.jsp?accountId=bceddf8b-45aa-487e-bfdc-7cc1cb434be5
			//http://218.76.27.33:7000/eduSSO/login.jsp?accountId=f797282b-84c2-461a-812c-d0df136a8ece
//			String message =EduSSOInterface.invoke("http://218.76.27.33:7000/eduSSO/login.jsp", "accountId="+"bceddf8b-45aa-487e-bfdc-7cc1cb434be5");
			
			String message = EduSSOInterface.invoke("http://218.76.27.32/eduSSO/sso/loginMessage", "key="+"70bda041-cc87-4f7d-9755-248cfeedfa2c");
//			EduSSOInterface.invoke("http://218.76.27.33:7000/eduSSO/loginOut.jsp", "accountId="+"346ccd66-69ce-4a92-a874-264539a45640");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String invoke(String urlStr, String params) throws Exception{
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setConnectTimeout(3600000);
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C)");
		 
		if(params!=null && !"".equals(params)){
			conn.getOutputStream().write(params.toString().getBytes());
			conn.getOutputStream().flush();
		}
		if(conn.getResponseCode()==200){
			//请求成功
			return streamToString(conn.getInputStream());
		}else{
			//请求失败
			return streamToString(conn.getErrorStream());
		}
		
	}
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

}
