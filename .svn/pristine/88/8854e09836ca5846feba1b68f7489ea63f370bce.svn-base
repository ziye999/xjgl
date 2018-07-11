package com.jiajie.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jiajie.util.bean.KsInfo;

public final class ExamKsInfo implements Serializable{
	/**
	 * 
	 */
	private static boolean paperClearFlag =false;
	private static final long serialVersionUID = 1L;
	private static ExamKsInfo examKsInfo = new ExamKsInfo();
	private List<KsInfo> kslist =null; 
	private Set<String> roomkm = null;
	
	//当前完成的Thread总数
		private int doneThreads = 0;
		
		private int threads = 0;
		
		private boolean isOk ;
		
		public synchronized void addThreadsNum(){
			threads = threads +1; 
		}
		
		public boolean isDoneByThreads(){
			return isOk;
		}
		
		public synchronized void addDoneThreadsNum(){
			doneThreads = doneThreads +1; 
			if(doneThreads==threads){
				isOk = true;
			}
		}
		
		public static void isDone(){
			ExamKsInfo.paperClearFlag = false;
		}
	
	//rukou
	public synchronized static boolean isPaperClearFlag() {
		if(examKsInfo.paperClearFlag){
			return false;
		}else{
			examKsInfo.paperClearFlag = true;
			ExamKsInfo eki = examKsInfo.getInstance();
			eki.kslist = new ArrayList<KsInfo>(); 
			eki.roomkm = new HashSet<String>();
			eki.doneThreads = 0;
			eki.threads = 0;
			eki.isOk = false;
			return true;
		} 
	}
	
	public static ExamKsInfo getInstance(){
		return examKsInfo;
	}
	
	public Set<String> getRoomkm() {
		return roomkm;
	}

	public synchronized void assKstoList(KsInfo ks){
		kslist.add(ks);
	}
	
	public int getKss(){
		int s = 0;
		if(kslist!=null){
			s = kslist.size();
		}
		return s; 
	}
	
	public synchronized KsInfo assignKs(){ 
		if(kslist.size()==0){
			return null;
		}else{ 
			return kslist.remove(0);
		}
	}
	
	private ExamKsInfo(){
		
	}
	
}

