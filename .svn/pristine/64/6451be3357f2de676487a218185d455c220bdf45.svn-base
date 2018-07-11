package com.jiajie.util.bean;

public class KsTaskInfo {
	private Boolean isok;
	
	private int usedThreads=0;
	
	private int doneThreads=0;

	public Boolean isok() {
		return isok;
	}
 
	public void setUsedThreads(int usedThreads) {
		isok = false;
		this.usedThreads = usedThreads;
	}

	public int getDoneThreads() {
		return doneThreads;
	}

	public void setDoneThreads(int doneThreads) {
		this.doneThreads = doneThreads;
		if(this.doneThreads == this.usedThreads){
			isok = true;
		}
	}
	
}
