package com.jiajie.cache;

import java.util.List;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

import com.jiajie.bean.foreignkey.ForeignBean;

public class ForeignCacheManager {
	private static ForeignCacheManager instance;
	private static int checkedOut=0;
	public static JCS foreignCache;
	private ForeignCacheManager()
	{
		try
		{
			foreignCache=JCS.getInstance("foreignCache");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	public static ForeignCacheManager getInstance()
	{
		synchronized(ForeignCacheManager.class)
		{
			if(instance==null)
			{
				instance=new ForeignCacheManager();
			}
			
		}
		synchronized(instance)
		{
			instance.checkedOut++;
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<ForeignBean> getForeignList()
	{
		return (List<ForeignBean>)foreignCache.get("Fg");
	}
	public void putCache(List<ForeignBean> foreignBeanList) {
		try {
			foreignCache.put("Fg", foreignBeanList);
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

}
