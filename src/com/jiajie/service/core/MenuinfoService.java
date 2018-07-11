package com.jiajie.service.core;

import java.util.List;

import com.jiajie.bean.system.TQxglMenuinfo;

/**
 * 
*    
* 项目名称：zypj   
* 类名称：MenuInfoService   
* 类描述：   
* 创建人：rock0304   
* 创建时间：Nov 20, 2014 2:50:30 PM   
* 修改人：rock0304   
* 修改时间：Nov 20, 2014 2:50:30 PM   
* 修改备注：   
* @version    
*
 */
public interface MenuinfoService{

	List<TQxglMenuinfo> findAll();//获取所有菜单数据
	
}
