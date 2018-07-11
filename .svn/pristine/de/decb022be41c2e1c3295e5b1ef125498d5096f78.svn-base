package com.jiajie.text;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.bean.registrationSystem.ZxxsXnxq;
import com.jiajie.bean.system.TQxglRoleinfo;
import com.jiajie.service.core.RoleinfoService;
import com.jiajie.service.registrationSystem.AnnuaManagementlService;
import com.jiajie.text.SpringTestBase;

public class Annua extends SpringTestBase{
	@Autowired
	private AnnuaManagementlService annuaService;
	@Autowired
	private RoleinfoService roleService;
	
	@Test
	public void test() {
		
		List<TQxglRoleinfo> lis = roleService.findAll();
		
		for (TQxglRoleinfo tQxglRoleinfo : lis) {
			System.out.println(tQxglRoleinfo.getRolename());
		}
		List<ZxxsXnxq>  list = annuaService.select();
		
		for (ZxxsXnxq zxxsXnxq : list) {
			System.out.println(zxxsXnxq.getXnmc());
		}
	}

}
