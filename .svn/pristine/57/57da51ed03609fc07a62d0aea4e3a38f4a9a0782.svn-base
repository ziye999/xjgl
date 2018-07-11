package com.jiajie.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.foreignkey.ForeignBean;
import com.jiajie.bean.foreignkey.ForeignColumnBean;
import com.jiajie.bean.foreignkey.ForeignTableBean;
import com.jiajie.bean.registrationSystem.ZxxsXnxq;
import com.jiajie.util.DeleteCheckFk;

// 这个@Transactional注解对子类中的方法也有效！
@Transactional
@SuppressWarnings("unchecked")
public abstract class DaoSupportImpl implements DaoSupport {

	@Resource
	private SessionFactory sessionFactory;
	
	private MsgBean checkForeignKey(Object obj) {
		MsgBean msgBean = new MsgBean();
		msgBean.setSuccess(true);
		String tableName = "";
		String conds = "";
		//获取obj的表名
		try {
			tableName = DeleteCheckFk.getTableNameForObj(obj);
		} catch (Exception e) {
			System.out.println("无法获取表名！");
			msgBean.setSuccessMsg(false, MsgBean.DEL_ERROR);
			e.printStackTrace();
			return msgBean;
		}
		//加载配置文件
		List<ForeignBean> foreignList = DeleteCheckFk.getForeignCache();
		String delSql = obj.toString();
		if(foreignList != null && foreignList.size() > 0) {
			//检查表是否在配置文件中，且是启用外键检查状态
			for (int i = 0; i < foreignList.size(); i++) {
				ForeignBean foreignBean = foreignList.get(i);
				if(foreignBean.getForeignName() != null && tableName.equals(foreignBean.getForeignName())) {
					//存在于配置文件中，检查是否要检测外键
					if(foreignBean.isEnable()) {
						//进行外键关联检测 
						List<ForeignColumnBean> foreignColumnist = foreignBean.getForeignColumnList();
						for (int j = 0; j < foreignColumnist.size(); j++) {
							ForeignColumnBean foreignColumnBean = foreignColumnist.get(j);
							List<ForeignTableBean> foreignTableList = foreignColumnBean.getForeignTableList();
							for (int k = 0; k < foreignTableList.size(); k++) {
								ForeignTableBean foreignTableBean = foreignTableList.get(k);
								//查询删除的id
								String selectId = "";
								selectId = delSql.replace("delete", "select " + foreignColumnBean.getColumnName() + " from ");
								//执行查询
								List fIdList = getListSQL(selectId);
								if(fIdList != null && fIdList.size() > 0) {
									List fIdListAry = new ArrayList();
									for (int l = 0; l < fIdList.size(); l++) {
										fIdListAry.add(((Map)fIdList.get(l)).get(foreignColumnBean.getColumnName()));
									}
									//查询外键
									StringBuffer sb = new StringBuffer();
									sb.append("select ")
									.append(foreignTableBean.getIdName())
									.append(" from ")
									.append(foreignTableBean.getTableName())
									.append(" where ")
									.append(foreignTableBean.getIdName())
									.append(" in ").append(fIdListAry.toString().replace("[", "('").replace("]", "')").replaceAll(",", "','").replaceAll(" ", ""))
									.append(" group by ").append(foreignTableBean.getIdName());
									//查询外键数
									List fTIdList = getListSQL(sb.toString());
									if(fTIdList != null && fTIdList.size() > 0) {
										List fTIdListArray = new ArrayList();
										for (int l = 0; l < fTIdList.size(); l++) {
											fTIdListArray.add(((Map)fTIdList.get(l)).get(foreignTableBean.getIdName()));
										}
										msgBean.setSuccess(false);
										if(msgBean.getMsg() !=null && !"".equals(msgBean.getMsg())) {
											msgBean.setMsg(msgBean.getMsg() + "有"+fTIdListArray.size()+"条数据被["+foreignTableBean.getTableComment()+"]引用,不能删除！<br/>");
										}else {
											msgBean.setMsg("有"+fTIdList.size()+"条数据被["+foreignTableBean.getTableComment()+"]引用,不能删除！<br/>");
										}
										delSql += " and "+ foreignColumnBean.getColumnName() + " not in" + fTIdListArray.toString().replace("[", "('").replace("]", "')").replaceAll(",", "','").replaceAll(" ", "");
									}
								}
							}
						}
					}
				}
			}
		}
		if(msgBean.isSuccess()) {
			msgBean = deleteBySql(delSql);
		}else {
			deleteBySql(delSql);
		}
		msgBean.setSuccess(true);
		return msgBean;
	}
	
	//暂时不启用
	private MsgBean deleteCheckFroeign(Object obj) {
		MsgBean msgBean = new MsgBean();
		msgBean = checkForeignKey(obj);
		if(!msgBean.isSuccess()) {//是否外键关联
			return msgBean;//删除失败
		}
		return delete(obj);
	}
	public MsgBean delete(String sql) {
		MsgBean msgBean = new MsgBean();
		return checkForeignKey(sql);//delete(sql);
	}
	
	public MsgBean delete(Object obj) {
		MsgBean msgBean = new MsgBean();
		try {
			getSession().delete(obj);
			msgBean.setSuccessMsg(true, MsgBean.DEL_SUCCESS);
		} catch (Exception e) {
			msgBean.setSuccessMsg(false, MsgBean.DEL_ERROR);
			e.printStackTrace();
		}
		return msgBean;
	}
	public MsgBean deleteBySql(String sql) {
		MsgBean msgBean = new MsgBean();
		try {
			getSession().createSQLQuery(sql).executeUpdate();
			msgBean.setSuccessMsg(true, MsgBean.DEL_SUCCESS);
		} catch (Exception e) {
			msgBean.setSuccessMsg(false, MsgBean.DEL_ERROR);
			e.printStackTrace();
		}
		return msgBean;
	}
	public Object get(Class paramClass, Serializable paramSerializable) {
		return getSession().get(paramClass, paramSerializable);
	}
	public int insert(String sql) {
		return getSession().createSQLQuery(sql).executeUpdate();
	}
	public Object load(Class paramClass, Serializable paramSerializable) {
		return getSession().load(paramClass, paramSerializable);
	}
	public Serializable save(Object obj) {
		return getSession().save(obj);
	}
	public void update(Object obj) {
		 getSession().update(obj);
	}
	public void update(String sql) {
		getSession().createSQLQuery(sql).executeUpdate();
	}
	public void saveOrUpdate(Object obj) {
		getSession().saveOrUpdate(obj);
	}
	public void saveZxxsXnxq(ZxxsXnxq zxxsXnxq){
		getSession().save(zxxsXnxq);
	}
	public Connection getConnection() {
		try {
			return SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	protected org.hibernate.SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	public Query createHQLQuery(String hql) {
		return getSession().createQuery(hql);
	}
	public Query createSQLQuery(String sql) {
		return getSession().createSQLQuery(sql);
	}
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	public void beginTransaction(){
		getSession().beginTransaction();
	}
	public void rollback(){
		getSession().getTransaction().rollback();
	}
	public void commit(){
		getSession().getTransaction().commit();
	}
	
	public Object getHQLUniqueResult(String hql){
		return getHQLUniqueResult(hql,null);
	}
	public Object getHQLUniqueResult(String hql,Object params){
		return getHQLUniqueResult(hql,new Object[]{params});
	}
	  
	public Object getHQLUniqueResult(String hql,Object[] params) {
		Query h = getSession().createQuery(hql);
		if(params != null && params.length > 0){
			for(int i=0; i<params.length; i++){
				h.setParameter(i, params[i]);
			}
		}
		return h.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).uniqueResult();
	}
		
	public Object getSQLUniqueResult(String sql){
		return getHQLUniqueResult(sql,null);
	}
	
	public Object getSQLUniqueResult(String sql,Object params){
		return getHQLUniqueResult(sql,new Object[]{params});
	}
	
	public Object getSQLUniqueResult(String sql,Object[] params) {
		Query h = getSession().createSQLQuery(sql);
		if(params != null && params.length > 0){
								for(int i=0; i<params.length; i++){
									h.setParameter(i, params[i]);
								}
							}
		return h.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).uniqueResult();
	}
	
	public List getListHQL(String hql) {
		
		return getListHQL(hql,null);
	}
	public List getListHQL(String hql,Object param) {
		return getListHQL(hql,new Object[]{param});
	}
	

	public List getListHQL(String hql,Object param,org.hibernate.transform.ResultTransformer resultTransformer) {
		return getListHQL(hql,new Object[]{param},resultTransformer);
	} 
	
	public List getListHQL(String hql,Object[] params) {
		Query h = getSession().createQuery(hql);
		if(params != null && params.length > 0){
								for(int i=0; i<params.length; i++){
									h.setParameter(i, params[i]);
								}
							}
		return h.list();
	}
	
	public List getListHQL(String hql,Object[] params,org.hibernate.transform.ResultTransformer resultTransformer) {
		Query h = getSession().createQuery(hql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		if(params != null && params.length > 0){
			for(int i=0; i<params.length; i++){
				h.setParameter(i, params[i]);
			}
		}
		return h.list();
	}
		
	public List getListSQL(String sql,Object[] params) {
		Query h = getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		if(params != null && params.length > 0){
			for(int i=0; i<params.length; i++){
				h.setParameter(i, params[i]);
			}
		}
		List list=h.list();
		return list;
	}
	public List<ZxxsXnxq> seleList(String sql){
		List<ZxxsXnxq> list =getSession().createQuery(sql).list();

		return list;
		
	}
	public List getListSQL(String sql){
		return this.getListSQL(sql, null);
	}
	
	//全国系统数据源
	@Resource
	private SessionFactory sessionFactoryQG;

	public void deleteQG(Object obj) {
		 getSessionQG().delete(obj);
	}
	public int deleteQG(String sql) {
		return getSessionQG().createSQLQuery(sql).executeUpdate();
	}
	public Object getQG(Class paramClass, Serializable paramSerializable) {
		return getSessionQG().get(paramClass, paramSerializable);
	}
	public int insertQG(String sql) {
		return getSessionQG().createSQLQuery(sql).executeUpdate();
	}
	public Object loadQG(Class paramClass, Serializable paramSerializable) {
		return getSessionQG().load(paramClass, paramSerializable);
	}
	public Serializable saveQG(Object obj) {
		return getSessionQG().save(obj);
	}
	public void updateQG(Object obj) {
		 getSessionQG().update(obj);
	}
	public void updateQG(String sql) {
		getSessionQG().createSQLQuery(sql).executeUpdate();
	}
	public void saveOrUpdateQG(Object obj) {
		getSessionQG().saveOrUpdate(obj);
	}
	public Connection getConnectionQG() {
		try {
			return SessionFactoryUtils.getDataSource(sessionFactoryQG).getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	protected org.hibernate.SessionFactory getSessionFactoryQG(){
		return sessionFactoryQG;
	}
	public Query createHQLQueryQG(String hql) {
		return getSessionQG().createQuery(hql);
	}
	public Query createSQLQueryQG(String sql) {
		return getSessionQG().createSQLQuery(sql);
	}
	public Session getSessionQG(){
		return sessionFactoryQG.getCurrentSession();
	}
	public void beginTransactionQG(){
		getSessionQG().beginTransaction();
	}
	public void rollbackQG(){
		getSessionQG().getTransaction().rollback();
	}
	public void commitQG(){
		getSessionQG().getTransaction().commit();
	}
	
	public Object getHQLUniqueResultQG(String hql){
		return getHQLUniqueResult(hql,null);
	}
	public Object getHQLUniqueResultQG(String hql,Object params){
		return getHQLUniqueResult(hql,new Object[]{params});
	}
	  
	public Object getHQLUniqueResultQG(String hql,Object[] params) {
		Query h = getSessionQG().createQuery(hql);
		if(params != null && params.length > 0){
								for(int i=0; i<params.length; i++){
									h.setParameter(i, params[i]);
								}
							}
		return h.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).uniqueResult();
	}
	
	
	
	public Object getSQLUniqueResultQG(String sql){
		return getHQLUniqueResult(sql,null);
	}
	public Object getSQLUniqueResultQG(String sql,Object params){
		return getHQLUniqueResult(sql,new Object[]{params});
	}
	
	public Object getSQLUniqueResultQG(String sql,Object[] params) {
		Query h = getSessionQG().createSQLQuery(sql);
		if(params != null && params.length > 0){
								for(int i=0; i<params.length; i++){
									h.setParameter(i, params[i]);
								}
							}
		return h.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).uniqueResult();
	}
	
	public List getListHQLQG(String hql) {
		
		return getListHQL(hql,null);
	}
	public List getListHQLQG(String hql,Object param) {
		return getListHQL(hql,new Object[]{param});
	}
	

	public List getListHQLQG(String hql,Object param,org.hibernate.transform.ResultTransformer resultTransformer) {
		return getListHQL(hql,new Object[]{param},resultTransformer);
	} 
	
	public List getListHQLQG(String hql,Object[] params) {
		Query h = getSessionQG().createQuery(hql);
		if(params != null && params.length > 0){
								for(int i=0; i<params.length; i++){
									h.setParameter(i, params[i]);
								}
							}
		return h.list();
	}
	
	public List getListHQLQG(String hql,Object[] params,org.hibernate.transform.ResultTransformer resultTransformer) {
		Query h = getSessionQG().createQuery(hql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		if(params != null && params.length > 0){
								for(int i=0; i<params.length; i++){
									h.setParameter(i, params[i]);
								}
							}
		return h.list();
	}
	
	
	public List getListSQLQG(String sql,Object[] params) {
		Query h = getSessionQG().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		if(params != null && params.length > 0){
								for(int i=0; i<params.length; i++){
									h.setParameter(i, params[i]);
								}
							}
		return h.list();
	}
	public List getListSQLQG(String sql){
		return this.getListSQLQG(sql, null);
	}
	public List findAllOrder(Class paramClass,String colName) {
		// 注意空格！
		return getSession().createQuery("FROM " + paramClass.getSimpleName()+ " order by "+colName).list();
	}
	
}
