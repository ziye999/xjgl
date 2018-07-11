package com.jiajie.util;
public class PageContext {
	@SuppressWarnings("unchecked")
	private static ThreadLocal Local_ROWINDEX= new ThreadLocal();
	@SuppressWarnings("unchecked")
	private static ThreadLocal Local_PAGESIZE= new ThreadLocal();
	@SuppressWarnings("unchecked")
	private static ThreadLocal LocaL_showPages= new ThreadLocal();
	public static int getRowIndex(){
		Integer value= (Integer)Local_ROWINDEX.get();
		if(value==null){
			return 0;
		}
		return value.intValue();
		
	}
	
	@SuppressWarnings("unchecked")
	public static void setRowIndex(int rowindex){
		Local_ROWINDEX.set(new Integer(rowindex));
	}
	
	public static void resetRowIndex(){
		Local_ROWINDEX.remove();
	}
	
	public static int getPageSize(){
		Integer value = (Integer)Local_PAGESIZE.get();
		if(value == null){
			return Integer.MAX_VALUE;
		}
		return value.intValue();
	}
	@SuppressWarnings("unchecked")
	public static void setPageSize(int pagesize){
		Local_PAGESIZE.set(new Integer(pagesize));
	}
	public static void resetPageSize(){
		Local_PAGESIZE.remove();
	}
	@SuppressWarnings("unchecked")
	public static void setShowPages(boolean showpages){
		
		LocaL_showPages.set(new Boolean(showpages));
	}
	public static boolean getShowPages(){
		Boolean showpages = (Boolean)LocaL_showPages.get();
		if(showpages==null){
			return false;
		}
		return showpages.booleanValue();
	}
}
