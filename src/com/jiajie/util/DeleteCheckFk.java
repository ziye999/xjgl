package com.jiajie.util;

import java.util.List;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.PrimaryKey;

import com.jiajie.bean.foreignkey.ForeignBean;
import com.jiajie.cache.ForeignCacheManager;

public class DeleteCheckFk {
	
	//通过类名获取表名
	 private static Configuration hibernateConf = new Configuration();
     private static PersistentClass getPersistentClass(Class clazz) {
            synchronized (DeleteCheckFk.class) {
                   PersistentClass pc = hibernateConf.getClassMapping(clazz.getName());
                   if (pc == null) {
                          hibernateConf = hibernateConf.addClass(clazz);
                          pc = hibernateConf.getClassMapping(clazz.getName());
                   }
                   return pc;
            }
     }
     /** 
      * 功能描述：获取实体对应表的主键字段名称，只适用于唯一主键的情况 
      *  
      * @param clazz 
      *            实体类 
      * @return 主键字段名称 
      */  
     public static String getPrimaryKey(Class<?> clazz) {  
      
      return getPrimaryKeys(clazz).getColumn(0).getName();  
      
     } 
     /** 
      * 功能描述：获取实体对应表的主键字段名称 
      *  
      * @param clazz 
      *            实体类 
      * @return 主键对象primaryKey ，可用primaryKey.getColumn(i).getName() 
      */  
     public static PrimaryKey getPrimaryKeys(Class<?> clazz) {  
      
      return getPersistentClass(clazz).getTable().getPrimaryKey();  
      
     }  
	//判断object类型返回表名
	public static String getTableNameForObj(Object obj) throws Exception {
		if(obj instanceof String) {
			String tableName = obj.toString();
			System.out.println(tableName.indexOf(" ", tableName.indexOf(" ")+1));
			if(tableName.indexOf(" ") <= 0 || tableName.indexOf(" ", tableName.indexOf(" ")+1) <= 0) {
				System.out.println("getTableNameForObj sql语句有误！");
				throw new Exception();
			}
			return tableName.substring(tableName.indexOf(" ")+1, tableName.indexOf(" ", tableName.indexOf(" ")+1));
		}else if(obj instanceof Object){
			return DeleteCheckFk.getPersistentClass(obj.getClass()).getTable().getName();
		}else {
			throw new Exception();
		}
	}
	//加载配置文件 .. 该方法启动加载，此处从缓存中加载
	public static List<ForeignBean> getForeignCache() {
		return ForeignCacheManager.getInstance().getForeignList();
	}
	
	//判断表是否在配置文件中 并且启动外键检查机制
	
	
}
