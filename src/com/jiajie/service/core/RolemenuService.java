package com.jiajie.service.core;

import java.util.List;

import com.jiajie.bean.system.TQxglRolemenu;

/**
 * 
*    
* 项目名称：zypj   
* 类名称：RolemenuService   
* 类描述：   
* 创建人：rock0304   
* 创建时间：Nov 20, 2014 3:07:50 PM   
* 修改人：rock0304   
* 修改时间：Nov 20, 2014 3:07:50 PM   
* 修改备注：   
* @version    
*
 */
public interface RolemenuService {
	public List<TQxglRolemenu> findAll();//获取所有角色菜单数据
}
