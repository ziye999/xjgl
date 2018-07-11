package com.jiajie.util;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;


public class MemCached {

	public MemcachedClient mcc;

	protected static MemCached memCached;
 
	protected MemCached() {
		Properties prop = new Properties();
		try {			
			String path =this.getClass().getResource("").getPath().split("classes")[0].substring(1)+
					"classes/memcache.properties";
			InputStream in =  new FileInputStream(path);
			prop.load(in);
			String configServers = prop.getProperty("memcache.serversList"); 
			MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(configServers));
			mcc = builder.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public static MemCached getInstance() {
		if(memCached == null){
			 memCached = new MemCached();
		}
		return memCached;
	}

	
	public boolean set(String key, Object value) {
		return set(key, value , 0);
	}

	public boolean set(String key, Object value, int expiry) {
		try {
			return mcc.set(key, expiry , value);
		} catch (Exception e) {
		}
		return false;
	}
	public Object get(String key) {
		try {
			return mcc.get(key);
		}catch(Exception e) {
			// TODO Auto-generated catch block
		}
		return null;
	}
	
	public boolean delete(String key) {
		try {
			return mcc.delete(key);
		}catch(Exception e) {
		}
		return false;
	}
}