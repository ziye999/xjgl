package com.jiajie.service.core.impl;
/**
 * 获取所有角色菜单数据
 * **/
import java.util.List;

import org.springframework.stereotype.Service;

import com.jiajie.bean.system.TQxglRolemenu;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.RolemenuService;
@Service("rolemenuService")
@SuppressWarnings("all")
public class RolemenuServiceImpl extends ServiceBridge implements
		RolemenuService {
	public List<TQxglRolemenu> findAll() {
		return findAllOrder(TQxglRolemenu.class, "ROLECODE");
	}
}
