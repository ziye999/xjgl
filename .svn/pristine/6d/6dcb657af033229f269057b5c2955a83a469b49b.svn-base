package com.jiajie.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import com.jiajie.bean.PageBean;
import com.jiajie.exceptions.SystemException;
import com.jiajie.util.PageContext;

public class PageManager extends DaoSupportImpl{
	public PageManager(){
	};
	public PageBean getHQLPageBean(String hql){
		return getHQLPageBean(hql, null);
	}
	public PageBean getHQLPageBean(String hql,Object param){
		return getHQLPageBean(hql, new Object[]{param});
	}
	public PageBean getHQLPageBean(String hql,Object[] params){
		return getHQLPageBean(hql, params,PageContext.getRowIndex(),PageContext.getPageSize());
	}
	public PageBean getHQLPageBean(String hql,int offset,int pagesize){
		return getHQLPageBean(hql,null, offset, pagesize);
	}
	public PageBean getHQLPageBean(String hql,Object param,int rowindex,int pagesize){
		return getHQLPageBean(hql, new Object[]{param}, rowindex, pagesize);
	}
	
	@SuppressWarnings("unchecked")
	public PageBean getHQLPageBean(String hql,Object[] params,int rowindex,int pagesize){
		//获取记录总数
		String countHql = getCountHQLQuery(hql);
		Query hqlquery = createHQLQuery(countHql);
		if(params != null && params.length > 0){
			for(int i=0; i<params.length; i++){
				hqlquery.setParameter(i, params[i]);
			}
		}
		
		int totalPages = 0;
		Object o = hqlquery.uniqueResult() ;
		if(o instanceof Integer){
			totalPages = ((Integer) o ).intValue();
		}else if(o instanceof Long){
			totalPages = ((Long) o ).intValue();
		}else if(o instanceof java.math.BigDecimal){
			totalPages = ((java.math.BigDecimal) o ).intValue();
		}else {
			totalPages = Integer.valueOf(o.toString()).intValue();
		}
		//获取结果集
		hqlquery = createHQLQuery(hql);
		if(params != null && params.length > 0){
			for(int i=0; i<params.length; i++){
				hqlquery.setParameter(i, params[i]);
			}
		}
		hqlquery.setFirstResult(rowindex);
		hqlquery.setMaxResults(pagesize);
				
		List resultList = null;
		if(hql.indexOf("select")==-1){
			resultList = hqlquery.list();
		}else{
			resultList = hqlquery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		}
		PageBean pb = new PageBean();
		pb.setResultList(resultList);
		pb.setTotalPages(totalPages);
		return pb;
	}
	
	/**
	 * 根据HQL语句，获得查询总记录数的HQL语句
	 * 经过转换，可以得到：
	 * @param hql
	 * @return
	 */
	private String getCountHQLQuery(String hql){
		int index = hql.indexOf("from");
		if(index != -1){
			return "select count(*) " + hql.substring(index);
		}
		throw new SystemException("错误：无效的HQL查询语句【"+hql+"】");
	}
	
	private String getCountSQLQuery(String sql){
		String msql = sql.toLowerCase();
		String countSql = "";
		int index = msql.indexOf("order");
		if(index>0){
			msql = msql.substring(0,index);
		}
		countSql =  "select count(1) from ("+msql+") countSql" ;
		return countSql ;		
	}
	
	//-----------SQL分页方式------//	
	public PageBean getSQLPageBean(String sql){
		return getSQLPageBean(sql, null);
	}
	public PageBean getSQLPageBean(String sql,Object param){
		return getSQLPageBean(sql, new Object[]{param});
	}
	public PageBean getSQLPageBean(String sql,Object[] params){
		return getSQLPageBean(sql, params,PageContext.getRowIndex(),PageContext.getPageSize());
	}
	public PageBean getSQLPageBean(String sql,int offset,int pagesize){
		return getSQLPageBean(sql,null, offset, pagesize);
	}
	public PageBean getSQLPageBean(String sql,Object param,int rowindex,int pagesize){
		return getSQLPageBean(sql, new Object[]{param}, rowindex, pagesize);
	}
	@SuppressWarnings("unchecked")
	public PageBean getSQLPageBean(String sql,Object[] params,int rowindex,int pagesize){
		//获取记录总数
		String countsql = getCountSQLQuery(sql);
		Query sqlquery = this.getSession().createSQLQuery(countsql);
		if(params != null && params.length > 0){
			for(int i=0; i<params.length; i++){
				sqlquery.setParameter(i, params[i]);
			}
		}
		
		int totalPages = 0;
		Object o = sqlquery.uniqueResult();			
		if(o instanceof Integer){
			totalPages = ((Integer) o).intValue();
		}else if(o instanceof Long){
			totalPages = ((Long) o).intValue();
		}else if(o instanceof java.math.BigDecimal){
			totalPages = ((java.math.BigDecimal) o).intValue();
		}else {
			totalPages = Integer.valueOf(o.toString()).intValue();
		}
		//获取结果集		
		sqlquery = createSQLQuery(sql);
		if(params != null && params.length > 0){
			for(int i=0; i<params.length; i++){
				sqlquery.setParameter(i, params[i]);
			}
		}
		sqlquery.setFirstResult(rowindex);
		sqlquery.setMaxResults(pagesize);
		List resultList = sqlquery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		PageBean pb = new PageBean();
		pb.setResultList(resultList);
		pb.setTotalPages(totalPages);
		return pb;
	}	
	
	@SuppressWarnings("unchecked")
	public List getHQLList(String hql,Object[] params,int rowindex,int pagesize){
		//获取记录总数
		String countHql = getCountHQLQuery(hql);
		Query hqlquery = createHQLQuery(countHql);
		if(params != null && params.length > 0){
			for(int i=0; i<params.length; i++){
				hqlquery.setParameter(i, params[i]);
			}
		}		
//		int totalPages = 0;
//		Object o = hqlquery.uniqueResult() ;
//		if(o instanceof Integer){
//			totalPages = ((Integer) o ).intValue();
//		}
//		if(o instanceof Long){
//			totalPages = ((Long) o ).intValue();
//		}
//		if(o instanceof java.math.BigDecimal){
//			totalPages = ((java.math.BigDecimal) o ).intValue();
//		}
		//获取结果集
		hqlquery = createHQLQuery(hql);
		if(params != null && params.length > 0){
			for(int i=0; i<params.length; i++){
				hqlquery.setParameter(i, params[i]);
			}
		}
		hqlquery.setFirstResult(rowindex);
		hqlquery.setMaxResults(pagesize);
		
		List resultList = null;
		if(hql.indexOf("select")==-1){
			resultList = hqlquery.list();
		}else{
			resultList = hqlquery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
		}
		return resultList;
	}
	
	public int getHQLTotalPages(String hql){
		return getHQLTotalPages(hql,null);
	}
	
	public int getHQLTotalPages(String hql,Object[] params){
		//获取记录总数
		String countHql = getCountHQLQuery(hql);
		Query hqlquery = createHQLQuery(countHql);
		if(params != null && params.length > 0){
			for(int i=0; i<params.length; i++){
				hqlquery.setParameter(i, params[i]);
			}
		}
		
		int totalPages = 0;
		Object o = hqlquery.uniqueResult() ;
		if(o instanceof Integer){
			totalPages = ((Integer) o ).intValue();
		}else if(o instanceof Long){
			totalPages = ((Long) o ).intValue();
		}else if(o instanceof java.math.BigDecimal){
			totalPages = ((java.math.BigDecimal) o ).intValue();
		}else {
			totalPages = Integer.valueOf(o.toString()).intValue();
		}
		return totalPages;
	}	
		
	protected boolean isNull(String value){
		if(value==null){
			return true;
		}
		if(value.equals("")){
			return true;
		}
		if(value.equals("null") || value.equals("NULL") || value.equals("undefined")){
			return true;
		}
		return false;		
	} 
	
}
