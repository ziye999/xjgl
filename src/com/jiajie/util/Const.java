/**
 * 
 */
package com.jiajie.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author gj
 *
 */
public class Const {
	
	public static String getConst(String key){
		InputStream inputStream = Const.class.getResourceAsStream("/constant.properties");  
        Properties p = new Properties();  
        try {  
            p.load(inputStream);  
            inputStream.close();  
        } catch (IOException e1) {  
            e1.printStackTrace();  
        }  
        
        return p.getProperty(key);
		
	}
	
	public static void main(String[] args) {
		System.out.println(Const.getConst("memsUrl"));
	}
	
}
