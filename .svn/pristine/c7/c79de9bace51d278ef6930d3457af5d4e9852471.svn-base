package com.jiajie.action.basicsinfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.basicsinfo.CusKwBuilding;
import com.jiajie.service.basicsinfo.BuildingService;

@SuppressWarnings("serial")
public class BuildingAction extends BaseAction{
	
	private CusKwBuilding building;
	
	private String lfids;
	
	@Autowired
	private BuildingService service;
	
	public void getListPage() {
		writerPrint(service.getList(building,getBspInfo()));
	}
	
	public void loadBuilding(){
		writerPrint(service.getBuilding(building.getLfid()));
	}
	
	public void addBuilding() {
		if("".equals(building.getLfid())) {
			building.setLfid(null);
		}
		writerPrint(service.saveOrUpdateBuilding(building));
	}	
	
	//修改人： 包敏    修改日期：2018-05-18 修改原因： 关联考点
	public void relateBuilding() {
		if("".equals(building.getLfid())) {
			building.setLfid(null);
		}
		writerPrint(service.relateBuilding(building));
	}	
	
	public void loadRelateBuilding(){
		writerPrint(service.getBuilding(building.getLfid()));
	}
	//修改人： 包敏    修改日期：2018-05-18 修改原因： 关联考点
	
	
	
	public void delBuilding() {
		writerPrint(service.delBuilding(lfids));
	}
	
	public void getCkdw() {
		writerPrint(service.getCkdw(getBspInfo()));
	}
	
	public void setService(BuildingService service) {
		this.service = service;
	}
	public CusKwBuilding getBuilding() {
		return building;
	}
	public void setBuilding(CusKwBuilding building) {
		this.building = building;
	}
	public String getLfids() {
		return lfids;
	}
	public void setLfids(String lfids) {
		this.lfids = lfids;
	}
	
}
