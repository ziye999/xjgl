package com.jiajie.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.auth.DefaultBceSessionCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.GeneratePresignedUrlRequest;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.baidubce.services.sts.StsClient;
import com.baidubce.services.sts.model.GetSessionTokenRequest;
import com.baidubce.services.sts.model.GetSessionTokenResponse;

public class FileUploadWIthBaiDu {
	private BosClient client;
	
	public FileUploadWIthBaiDu(){
		System.out.println("进入.....");
		String ACCESS_KEY_ID = "a45318d23a474972a4ce381b7ca8e8ba";
	    String SECRET_ACCESS_KEY = "6fc1a0fa8feb415b8f2590d005f58b2a";
	    // 初始化一个BosClient
	    BosClientConfiguration config = new BosClientConfiguration();
	    config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
	    config.setMaxConnections(150);
	    client = new BosClient(config);
	}
	
	public void run(String fileUrl,String bosUrl) throws Exception{
		System.out.println(fileUrl+"   开始.....");
		PutObject(client,"kaoshi",fileUrl,bosUrl);
		System.out.println(fileUrl+"   结束.....");
	}	
	
	//BosClient client 客户端  String bucketName  bos中的bucket名称，objectKey文件夹以及文件名
	private void PutObject(BosClient client, String bucketName,String fileUrl, String objectKey)throws Exception {
	    // 获取指定文件 
		byte[] buffer = null; 
	    File file = new File(fileUrl);  
	    if(file == null){
	    	System.out.println("无法上传");
	    }
        FileInputStream fis = new FileInputStream(file);  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        byte[] b = new byte[100*1024];  
        int n;  
        while ((n = fis.read(b)) != -1)  
        {  
            bos.write(b, 0, n);  
        }  
        fis.close();  
        bos.close();  
        buffer = bos.toByteArray();   
	    // 以文件形式上传Object
	    PutObjectResponse putObjectFromFileResponse = client.putObject(bucketName, objectKey, buffer);
	}
	 
	public String getUrl(String durl,int expried){
		try {
			String ACCESS_KEY_ID = "a45318d23a474972a4ce381b7ca8e8ba";
			String SECRET_ACCESS_KEY = "6fc1a0fa8feb415b8f2590d005f58b2a";
			BceClientConfiguration config = new BceClientConfiguration();
			config.setEndpoint("http://sts.bj.baidubce.com");
			config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID,SECRET_ACCESS_KEY));
			StsClient stsClient = new StsClient(config);
			JSONObject stsBody = new JSONObject();
			JSONArray accessControlList = new JSONArray();
			JSONObject acl = new JSONObject();
			acl.put("eid", "eid01");
			acl.put("service", "bce:bos");
			acl.put("region", "bj");
			acl.put("effect", "Allow");
			
			JSONArray bucketList = new JSONArray();
			bucketList.put("kaoshi/*");    //getObject、putObject用到
			acl.put("resource", bucketList);
			JSONArray permissionList = new JSONArray();
			permissionList.put("READ");
			permissionList.put("WRITE");
			permissionList.put("LIST");	
			permissionList.put("GetObject");	
			acl.put("permission", permissionList);
			accessControlList.put(acl);
			stsBody.put("id","testACL");
			stsBody.put("accessControlList",accessControlList);
			System.out.println("...............");
			//发送请求STS 临时AK、SK以及Token
			GetSessionTokenRequest request = new GetSessionTokenRequest();
			request.setAcl(stsBody.toString());
			request.setDurationSeconds(3600);
			GetSessionTokenResponse stsCredentials = stsClient.getSessionToken(request);
//			BOS测试STS验证；	
			BosClientConfiguration bosconfig = new BosClientConfiguration();    // 初始化一个带有STS验证的BosClient
			bosconfig.setEndpoint("http://bj.bcebos.com");
			bosconfig.setCredentials(new DefaultBceSessionCredentials(stsCredentials.getAccessKeyId(),stsCredentials.getSecretAccessKey(), stsCredentials.getSessionToken()));    //STS返回的临时AK/SK及Token
			BosClient bosClient = new BosClient(bosconfig);
//	      获取Object ULR
//		    注：Baidu原版的SDK生成的URL无法直接使用浏览器访问通过鉴权认证，因为默认浏览器是不会携带sts的x-bce-security-token 这个header的；
//		    可以使用火狐浏览器手动添加sts的x-bce-security-token header可访问，其他接口如get、PutObject等方法封装了该header，故可以正常访问
	        GeneratePresignedUrlRequest presignedUrlRequest = new GeneratePresignedUrlRequest("kaoshi", durl);
	        presignedUrlRequest.withExpiration(expried);     
	        presignedUrlRequest.getRequestParameters().put("x-bce-security-token", stsCredentials.getSessionToken());
	        System.out.println("...............");
	        URL url = bosClient.generatePresignedUrl(presignedUrlRequest);
	        return url+"";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
		
} 
	 
}
