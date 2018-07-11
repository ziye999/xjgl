package com.jiajie.service.core.impl;
/**
 * 获取所有角色数据
 * **/
import java.util.List;

import org.springframework.stereotype.Service;

import com.jiajie.bean.system.TQxglRoleinfo;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.RoleinfoService;

@Service("roleinfoService")
@SuppressWarnings("all")
public class RoleinfoServiceImpl extends ServiceBridge implements RoleinfoService{

	public List<TQxglRoleinfo> findAll() {
		return findAllOrder(TQxglRoleinfo.class,"ROLECODE");
	}

}
