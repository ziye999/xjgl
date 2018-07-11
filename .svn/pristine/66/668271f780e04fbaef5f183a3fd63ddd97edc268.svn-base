package com.jiajie.action.zzjg;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.zzjg.CusKwZkdw;
import com.jiajie.service.zzjg.ZkdwService;

@SuppressWarnings("serial")
public class ZkdwAction extends BaseAction{
	
	private CusKwZkdw zkdw;
	
	private String regioncodes;
	
	@Autowired
	private ZkdwService service;
	
	public void getListPage() {		
		writerPrint(service.getList(zkdw, getBspInfo()));
	}
	
	public void loadZkdw(){
		writerPrint(service.loadZkdw(zkdw.getRegioncode()));
	}
	
	public void addZkdw() {
		writerPrint(service.saveOrUpdateZkdw(zkdw));
	}	
	
	public void delZkdw() {	
		writerPrint(service.delZkdw(regioncodes));
	}	
	
	public void setService(ZkdwService service) {
		this.service = service;
	}
	public CusKwZkdw getZkdw() {
		return zkdw;
	}
	public void setZkdw(CusKwZkdw zkdw) {
		this.zkdw = zkdw;
	}
	public String getRegioncodes() {
		return regioncodes;
	}
	public void setRegioncodes(String regioncodes) {
		this.regioncodes = regioncodes;
	}
	
	public void getPZkdw() {
		writerPrint(service.getPZkdw(getBspInfo()));
	}
	
	public void checkZkdwcodeRepeat() {
		writerPrint(service.checkZkdwcodeRepeat(zkdw.getRegioncode()));
	}
}
