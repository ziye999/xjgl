package com.jiajie.service.core.impl;
/**
 *用户角色
 * */
import java.util.List;

import org.springframework.stereotype.Service;

import com.jiajie.bean.system.TQxglUserrole;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.UserroleService;
@Service("userroleService")
@SuppressWarnings("all")
public class UserroleServiceImpl extends ServiceBridge implements
		UserroleService {
	public List<TQxglUserrole> findAll() {
		return findAllOrder(TQxglUserrole.class, "USERCODE");
	}
}
