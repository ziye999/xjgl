package com.jiajie.service.core.impl;
/**
 * 菜单信息
 * **/
import java.util.List;

import org.springframework.stereotype.Service;

import com.jiajie.bean.system.TQxglMenuinfo;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.core.MenuinfoService;
@Service("menuinfoService")
@SuppressWarnings("all")
public class MenuinfoServiceImpl extends ServiceBridge implements MenuinfoService{

	public List<TQxglMenuinfo> findAll() {
		System.out.println(findAllOrder(TQxglMenuinfo.class,"orderid"));
		return findAllOrder(TQxglMenuinfo.class,"orderid");
	}

}
