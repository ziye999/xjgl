package com.jiajie.dao;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.registrationSystem.ZxxsXnxq;

public interface DaoSupport {

	/**
	 * 保存一个实例对象
	 * @param obj
	 * @return
	 */
	public java.io.Serializable save(Object obj);
	
	/**
	 * 根据对象的序列化ID加载一个对象
	 * 直接加载
	 * @param paramClass
	 * @param paramSerializable
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public Object get(Class paramClass,java.io.Serializable paramSerializable);
	/**
	 * 根据对象的序列化ID加载一个对象
	 * lazy加载
	 * @param paramClass
	 * @param paramSerializable
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object load(Class paramClass,java.io.Serializable paramSerializable);
	
	/**
	 * 更新一个实例对象
	 * @param obj
	 * @return
	 */
	public void update(Object obj);
	
	/**
	 * 删除一个实例对象
	 * @param obj
	 * @return
	 */
	public MsgBean delete(Object obj);


	/**
	 * 插入
	 * @param obj
	 * @return
	 */
	public int insert(String sql);
	/**
	 * 删除
	 * @param obj
	 * @return
	 */
	public MsgBean delete(String sql);
	/**
	 * 修改
	 * @param sql
	 */
	public void update(String sql);
	/**
	 * 查询
	 * @param sql
	 * @return 
	 */
	public List<ZxxsXnxq> seleList(String sql);
	public void saveZxxsXnxq(ZxxsXnxq zxxsXnxq);
	@SuppressWarnings("unchecked")
	public List getListSQL(String sql);
	
	
	/**
	 * 查询
	 * @param sql
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List getListHQL(String hql);
	/**
	 * 保存实例对象或者更新
	 * @param obj
	 * 
	 */
	public void saveOrUpdate(Object obj);
	/**
	 * 获得连接对象
	 * @return
	 */
	public java.sql.Connection getConnection();
	/**
	 * 创建Query对象根据HQL语句
	 * @param Hql
	 * @return
	 */
	public Query createHQLQuery(String hql);
	public Query createSQLQuery(String sql);
	/**
	 * 获取Session对象
	 * @return
	 */
	public Session getSession();
	
	//全国系统数据源
	/**
	 * 保存一个实例对象
	 * @param obj
	 * @return
	 */
	public java.io.Serializable saveQG(Object obj);
	
	/**
	 * 根据对象的序列化ID加载一个对象
	 * 直接加载
	 * @param paramClass
	 * @param paramSerializable
	 * @return
	 */
	
	public Object getQG(Class paramClass,java.io.Serializable paramSerializable);
	/**
	 * 根据对象的序列化ID加载一个对象
	 * lazy加载
	 * @param paramClass
	 * @param paramSerializable
	 * @return
	 */
	public Object loadQG(Class paramClass,java.io.Serializable paramSerializable);
	
	/**
	 * 更新一个实例对象
	 * @param obj
	 * @return
	 */
	public void updateQG(Object obj);
	
	/**
	 * 删除一个实例对象
	 * @param obj
	 * @return
	 */
	public void deleteQG(Object obj);


	/**
	 * 插入
	 * @param obj
	 * @return
	 */
	public int insertQG(String sql);
	/**
	 * 删除
	 * @param obj
	 * @return
	 */
	public int deleteQG(String sql);
	/**
	 * 修改
	 * @param sql
	 */
	public void updateQG(String sql);
	/**
	 * 查询
	 * @param sql
	 * @return 
	 */
	public java.util.List getListSQLQG(String sql);
	
	
	/**
	 * 查询
	 * @param sql
	 * @return 
	 */
	public java.util.List getListHQLQG(String hql);
	/**
	 * 保存实例对象或者更新
	 * @param obj
	 * 
	 */
	public void saveOrUpdateQG(Object obj);
	/**
	 * 获得连接对象
	 * @return
	 */
	public java.sql.Connection getConnectionQG();
	/**
	 * 创建Query对象根据HQL语句
	 * @param Hql
	 * @return
	 */
	public Query createHQLQueryQG(String hql);
	public Query createSQLQueryQG(String sql);
	/**
	 * 获取Session对象
	 * @return
	 */
	public Session getSessionQG();
}
