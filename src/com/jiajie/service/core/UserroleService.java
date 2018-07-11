package com.jiajie.service.core;

import java.util.List;

import com.jiajie.bean.system.TQxglUserrole;

/**
 * 
*    
* 项目名称：zypj   
* 类名称：UserroleService   
* 类描述：   
* 创建人：rock0304   
* 创建时间：Nov 20, 2014 3:07:50 PM   
* 修改人：rock0304   
* 修改时间：Nov 20, 2014 3:07:50 PM   
* 修改备注：   
* @version    
*
 */
public interface UserroleService{
	public List<TQxglUserrole> findAll();//获取所有用户角色数据
}
