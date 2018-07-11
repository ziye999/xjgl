package com.jiajie.security;


import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiajie.bean.system.TQxglUserinfo;
import com.jiajie.service.core.UserInfoService;




public class MonitorAuthorizingRealm extends AuthorizingRealm{

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		 
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        if (username == null) {
           throw new AccountException(
                    "Null usernames are not allowed by this realm.");
        }
        //查询用户名和密码是否正确
        TQxglUserinfo userinfo = userInfoService.getUserLogin(username, password);
		if( userinfo != null ){  
			return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());  
		}
		return null;
	}
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		String username = (String) principals.fromRealm( getName() ).iterator().next();
//		if( username != null ){
//			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//			info.addRole(username);
//			info.addStringPermissions(this.Topermissions( userMapper.selectUserRole(username).getRoles()) );
//			return info;
//		}
		return null;
	}
	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}
	 
	/**
	* 清除所有用户授权信息缓存.
	*/
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
			if (cache != null) {
				for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	@Autowired
	protected UserInfoService userInfoService;

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
}
