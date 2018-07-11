package com.jiajie.action.core;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jiajie.action.BaseAction;
import com.jiajie.bean.system.TQxglMenuinfo;
import com.jiajie.bean.system.TQxglUserinfo;
import com.jiajie.core.minterface.EduSSOInterface;
import com.jiajie.service.core.ControlCenterService;
import com.jiajie.service.core.LoginService;
import com.jiajie.service.core.UserInfoService;
import com.jiajie.util.Const;
import com.jiajie.util.FunctionMenu;
import com.jiajie.util.bean.MBspInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
public class ControlCenterAction extends BaseAction{
	private String errormsg;
	private String e2id;
	private String id_number;
	private String username;
	private String sex;
	private String forward_path;
	@Autowired
	private ControlCenterService centerService;
	
	public ControlCenterService getCenterService() {
		return centerService;
	}

	public void setCenterService(ControlCenterService centerService) {
		this.centerService = centerService;
	}

	public String execute() throws Exception {
		forward_path = "jsp"+File.separator+"main.jsp";
		String password = "";
		String username = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String tokenKey = request.getParameter("tokenKey");
		//使用tokenKey获取用户e2id
		String message = "";
		if(tokenKey != null && !"".equals(tokenKey)) {
			message = EduSSOInterface.invoke(Const.getConst("eduSSO")+"/sso/loginMessage", "key="+tokenKey);
		}else {
			forward_path = File.separator+"xjgl"+File.separator+"nologin.jsp";
			
			return "controlcenter";
		}
		//判断用户是否在eduSSO登录成功
		JSONObject  jasonObject = JSONObject.fromObject(message);
		Map<String, String> eduSSOMap = (Map<String, String>)jasonObject;
		if("0".equals(eduSSOMap.get("status"))) {
			forward_path = File.separator+"xjgl"+File.separator+"nologin.jsp";
			
			return "controlcenter";
		}
		//如果用户登录eduSSO则获取e2id并判断该e2id在评价系统中是否有对应的用户。如果没有，则新增用户数据
		String e2id = eduSSOMap.get("E2ID").toString();
		if("".equals(e2id)) {
			forward_path = File.separator+"xjgl"+File.separator+"nologin.jsp";
			
			return "controlcenter";
		}
		
		//从数据库加载该e2id用户
		List userList = centerService.findUserByE2ID(e2id);
		if(userList != null && userList.size() > 0) {//存在用户
			//是用户在shiro框架中注册
			Map<String, String> userMap = (Map<String, String>)userList.get(0);
			username = userMap.get("LOGINID").toString();
			password = userMap.get("LOGINPWD").toString();
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			 try {
				 // 验证是否登录成功
				subject.login(token);
	         } catch (UnknownAccountException uae) {
	        	 // 用户名不存在
	             errormsg =  "用户名密码错误!";
	             forward_path = File.separator+"xjgl"+File.separator+"nologin.jsp";
	             
	 			return "controlcenter";
	         } catch (IncorrectCredentialsException ice) {
	        	 // 用户名对应的密码错误
	             errormsg =  "用户名对应的密码错误!";
	             forward_path = File.separator+"xjgl"+File.separator+"nologin.jsp";
	             
	 			return "controlcenter";
	         } catch (LockedAccountException lae) {
	        	 // 登录用户被锁定，登录失败
	             errormsg =  "登录用户被锁定，登录失败!";
	             forward_path = File.separator+"xjgl"+File.separator+"nologin.jsp";
	             
	 			return "controlcenter";
	         } catch (AuthenticationException ae) {
	        	  ae.printStackTrace();
	              errormsg = "登录失败!";
	              forward_path = File.separator+"xjgl"+File.separator+"nologin.jsp";
	              
	  			return "controlcenter";
	         }
			 HttpSession session = ServletActionContext.getRequest().getSession();
				ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
				UserInfoService userinfoService = (UserInfoService) context.getBean("userInfoService");
				TQxglUserinfo userinfo = userinfoService.getUserLogin(username, password);
				session.setAttribute("userloginBean", userinfo);
				
				
				LoginService service = (LoginService) context.getBean("loginService");
				MBspInfo mBspInfo = service.getMBspInfo(userinfo.getUsercode());
				mBspInfo.setUserId(userinfo.getUsercode());
				getRequest().getSession().setAttribute("mBspInfo", mBspInfo);
				//将权限集放入session
				List<TQxglMenuinfo> menutreeList = FunctionMenu.getMenuInstance().getMenuInfoByUser(userinfo.getUsercode(), context);
				session.setAttribute("menutreeList", menutreeList);
		}else {//用户不存在
    		forward_path = File.separator+"xjgl"+File.separator+"nologin.jsp";			
			return "controlcenter";
		}
		//登录成功
        return "controlcenter";
    }

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public String getE2id() {
		return e2id;
	}

	public void setE2id(String e2id) {
		this.e2id = e2id;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getForward_path() {
		return forward_path;
	}

	public void setForward_path(String forward_path) {
		this.forward_path = forward_path;
	}
	
}
