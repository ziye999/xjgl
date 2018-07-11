package com.jiajie.action.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.jiajie.action.BaseAction;
import com.jiajie.bean.system.TQxglMenuinfo;
import com.jiajie.bean.system.TQxglUserinfo;
import com.jiajie.service.core.LoginService;
import com.jiajie.service.core.UserInfoService;
import com.jiajie.util.FunctionMenu;
import com.jiajie.util.MemCached;
import com.jiajie.util.StringUtil;
import com.jiajie.util.bean.MBspInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@SuppressWarnings("serial")
public class LoginAction extends BaseAction{
	@Autowired
	private LoginService service;
	private String errormsg = "";
	
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public LoginService getService() {
		return service;
	}
	public void setService(LoginService service) {
		this.service = service;
	}
	public String login() {
		String j_username = getRequest().getParameter("j_username")==null?"":getRequest().getParameter("j_username").toString();
		String j_password = getRequest().getParameter("j_password")==null?"":getRequest().getParameter("j_password").toString();
		String rdmCode = getRequest().getParameter("rdmCode")==null?"":getRequest().getParameter("rdmCode").toString();
		String randonKey = (String) getRequest().getSession().getAttribute(StringUtil.LOGIN_RANDOM_CODE_KEY);
		
		errormsg = "";
		if (j_username==null||"".equals(j_username)||j_password==null||"".equals(j_password)) {
			errormsg =  "请输入用户名或密码!";
			getRequest().setAttribute("errormsg", errormsg);
            return "login";
		}
//		if (randonKey!=null&&!randonKey.equals(rdmCode)) {
//			errormsg =  "验证码输入错误!";
//			getRequest().setAttribute("errormsg", errormsg);
//            return "login";
//		}
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(j_username, j_password);
		try {
			//验证是否登录成功
			subject.login(token);
			getRequest().setAttribute("errormsg", errormsg);
        } catch (UnknownAccountException uae) {
       	 	//用户名不存在
            errormsg =  "用户名不存在或者被锁定!";
			getRequest().setAttribute("errormsg", errormsg);
            return "login";
        } catch (IncorrectCredentialsException ice) {
       	 	//用户名对应的密码错误
            errormsg =  "用户名密码错误!";
			getRequest().setAttribute("errormsg", errormsg);
            return "login";
        } catch (LockedAccountException lae) {
       	 	//登录用户被锁定，登录失败
            errormsg =  "登录用户被锁定，登录失败!";
			getRequest().setAttribute("errormsg", errormsg);
            return "login";
        } catch (AuthenticationException ae) {
        	ae.printStackTrace();
        	errormsg = "登录失败!";
 			getRequest().setAttribute("errormsg", errormsg);
 			return "login";
        }
        HttpSession session = ServletActionContext.getRequest().getSession();
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
		UserInfoService userinfoService = (UserInfoService) context.getBean("userInfoService");
		TQxglUserinfo userinfo = userinfoService.getUserLogin(j_username, j_password);
		Date qs = userinfo.getQssj();
		Date js = userinfo.getJssj();
		Date today = new Date();
		if (today.after(qs)&&today.before(js)) {
			session.setAttribute("userloginBean", userinfo);
		}else {
			errormsg = "不在用户有效期，不能登录!";
 			getRequest().setAttribute("errormsg", errormsg);
 			return "login";			
		}		
		MBspInfo mBspInfo = service.getMBspInfo(userinfo.getUsercode());
		mBspInfo.setUserId(userinfo.getUsercode());
		getRequest().getSession().setAttribute("mBspInfo", mBspInfo);
		getRequest().setAttribute("username", j_username);
		getRequest().setAttribute("pwd", j_password);
		//将权限集放入session
//		System.out.println("用户主键"+userinfo.getUsercode()+"-----"+userinfo.getUsername());
		List<TQxglMenuinfo> menutreeList = FunctionMenu.getMenuInstance().getMenuInfoByUser(userinfo.getUsercode(), context);
		session.setAttribute("menutreeList", menutreeList);
		
		//写入缓存
		String keyVal = getRequest().getSession().getId();
		MemCached.getInstance().set(keyVal, mBspInfo);
		MemCached.getInstance().set(keyVal+"mt", menutreeList);
		MemCached.getInstance().set(keyVal+"lf", "0");
		Cookie cook = new Cookie("KSGLSESSIONID", keyVal);
		getResponse().addCookie(cook);
		
		try {
			getRequest().getRequestDispatcher("jsp"+File.separator+"main.jsp").forward(getRequest(), getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "main";
	}	
	
	public void clientLogin() throws IOException {
		long startTime=System.currentTimeMillis();   //获取开始时间
		String j_username = getRequest().getParameter("j_username")==null?"":getRequest().getParameter("j_username").toString();
		String j_password = getRequest().getParameter("j_password")==null?"":getRequest().getParameter("j_password").toString();
		//String rdmCode = getRequest().getParameter("rdmCode")==null?"":getRequest().getParameter("rdmCode").toString();
		//String randonKey = (String) getRequest().getSession().getAttribute(StringUtil.LOGIN_RANDOM_CODE_KEY);
		//System.out.println(".....");
		JSONObject json = new JSONObject();
		errormsg = "";
		if (j_username==null||"".equals(j_username)||j_password==null||"".equals(j_password)) {
			errormsg =  "请输入用户名或密码!";
			json.put("re","3");
			  json.put("msg",errormsg);
			  writerPrint(json.toString());
		}
//		if (randonKey!=null&&!randonKey.equals(rdmCode)) {
//			errormsg =  "验证码输入错误!";
//			getRequest().setAttribute("errormsg", errormsg);
//			getResponse().getOutputStream().print(2);
//			return;
//		}
//		Subject subject = SecurityUtils.getSubject();
//		UsernamePasswordToken token = new UsernamePasswordToken(j_username, j_password);
//		try {
//			//验证是否登录成功
//			subject.login(token);
//			getRequest().setAttribute("errormsg", errormsg);
//			
//        } catch (UnknownAccountException uae) {
//       	 	//用户名不存在
//            errormsg =  "用户名不存在或者被锁定!";
////			getRequest().setAttribute("errormsg", errormsg);
////			getResponse().getOutputStream().print(3);
//			 json.put("re","3");
//			  json.put("msg",errormsg);
//			  writerPrint(json.toString());
//			
//			
//			return;
//        } catch (IncorrectCredentialsException ice) {
//       	 	//用户名对应的密码错误
//            errormsg =  "用户名密码错误!";
//			
//			json.put("re","4");
//			  json.put("msg",errormsg);
//			  writerPrint(json.toString());
//			return;
//        } catch (LockedAccountException lae) {
//       	 	//登录用户被锁定，登录失败
//            errormsg =  "登录用户被锁定，登录失败!";
//            json.put("re","5");
//			  json.put("msg",errormsg);
//			  writerPrint(json.toString());
//			return;
//        } catch (AuthenticationException ae) {
//        	ae.printStackTrace();
//        	errormsg = "登录失败!";
//        	json.put("re","6");
//			  json.put("msg",errormsg);
//			  writerPrint(json.toString());
// 			return;
//        }
        HttpSession session = ServletActionContext.getRequest().getSession();
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
		UserInfoService userinfoService = (UserInfoService) context.getBean("userInfoService");
		TQxglUserinfo userinfo = userinfoService.getUserLogin(j_username, j_password);
		
		if(userinfo==null){
   	 	//用户名不存在
			 errormsg =  "用户名不存在或密码错误!";
			 json.put("re","3");
			  json.put("msg",errormsg);
			  writerPrint(json.toString());
		}
//		Date qs = userinfo.getQssj();
//		Date js = userinfo.getJssj();
//		Date today = new Date();
//		if (today.after(qs)&&today.before(js)) {
//			session.setAttribute("userloginBean", userinfo);
//		}else {
//			errormsg = "不在用户有效期，不能登录!";
// 			getRequest().setAttribute("errormsg", errormsg);
// 			getResponse().getOutputStream().print(7);
// 			return;
//		}		
		String user_id=userinfo.getUsercode();
		List  list =userinfoService.getKCList(user_id);
		JSONArray jsonArray = new JSONArray();
		//getResponse().getOutputStream().print("succs");
		for (int i = 0; i < list.size(); i++) {
			   Object[] object=(Object[]) list.get(i);  
	            String kcmc=(String) object[1]; 
	            String kcid=(String) object[0]; 
              JSONObject jo = new JSONObject();
              jo.put("kcmc", URLEncoder.encode(kcmc, "utf-8"));
              jo.put("kcid", kcid);
              jsonArray.add(jo);

          }
		  json.put("re","success");
		  json.put("data",jsonArray);
		  long endTime=System.currentTimeMillis(); //获取结束时间
		   System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
		  writerPrint(json.toString());
		
		 
	}	
	
	
	/**
	 * 生成随机数图片
	 */ 
	public void getClientRandcode()throws Exception{
		System.setProperty("java.awt.headless","true");
		HttpSession session = getRequest().getSession();
		int width=62, height=22;//设置图片大小
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		//不减2将无法显示右边框和下边框
		g.fillRect(1, 1, width-2, height-2);//设定边框
		g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
		//g.setColor(getRandColor(111,133,random));
		//产生随机线
		for (int i=0;i<2;i++){
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(13);
			int yl = random.nextInt(15);
			g.drawLine(x,y,x+xl,y+yl);
		}
		//产生随机点
		//g.setColor(getRandColor(130,150,random));
		//产生5个随机数
		String sRand="";
		for (int i=0;i<4;i++){
			//g.setFont(getsFont(random));
			g.setFont(new Font("宋体",Font.BOLD,16));
			g.setColor(new Color(random.nextInt(101),random.nextInt(111),random.nextInt(121)));
			String rand=String.valueOf(getRandomString(random.nextInt(10)));
			sRand+=rand;
			//将字符画在图片上时定位光标,如设置字符间随机间距
			g.translate(random.nextInt(1),random.nextInt(1));
			g.drawString(rand,10*i+3,16);
		}
		//使用session保存随机码
		String strouttime="10000";
		int outtime=10000;
		//当资源文件中没有设置session过期时间系统默认过期为120秒
		if(strouttime!=null){
			try{
				outtime=Integer.parseInt(strouttime);
			}catch(Exception e){
				e.printStackTrace();
				//LogManage.errorLog("System set Verify code out time format error");
			}	  
		}
		session.setMaxInactiveInterval(outtime);
		session.setAttribute(StringUtil.LOGIN_RANDOM_CODE_KEY,sRand);   
		g.dispose();
		getResponse().getOutputStream().print(sRand);
//		ImageIO.write(image, "JPEG", getResponse().getOutputStream());
	}
	  
	
		
	/**
	 * 生成随机数图片
	 */ 
	public void getRandcode()throws Exception{
		System.setProperty("java.awt.headless","true");
		HttpSession session = getRequest().getSession();
		int width=62, height=22;//设置图片大小
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		//不减2将无法显示右边框和下边框
		g.fillRect(1, 1, width-2, height-2);//设定边框
		g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
		//g.setColor(getRandColor(111,133,random));
		//产生随机线
		for (int i=0;i<2;i++){
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(13);
			int yl = random.nextInt(15);
			g.drawLine(x,y,x+xl,y+yl);
		}
		//产生随机点
		//g.setColor(getRandColor(130,150,random));
		//产生5个随机数
		String sRand="";
		for (int i=0;i<4;i++){
			//g.setFont(getsFont(random));
			g.setFont(new Font("宋体",Font.BOLD,16));
			g.setColor(new Color(random.nextInt(101),random.nextInt(111),random.nextInt(121)));
			String rand=String.valueOf(getRandomString(random.nextInt(10)));
			sRand+=rand;
			//将字符画在图片上时定位光标,如设置字符间随机间距
			g.translate(random.nextInt(1),random.nextInt(1));
			g.drawString(rand,10*i+3,16);
		}
		//使用session保存随机码
		String strouttime="10000";
		int outtime=10000;
		//当资源文件中没有设置session过期时间系统默认过期为120秒
		if(strouttime!=null){
			try{
				outtime=Integer.parseInt(strouttime);
			}catch(Exception e){
				e.printStackTrace();
				//LogManage.errorLog("System set Verify code out time format error");
			}	  
		}
		session.setMaxInactiveInterval(outtime);
		session.setAttribute(StringUtil.LOGIN_RANDOM_CODE_KEY,sRand);   
		g.dispose();
		ImageIO.write(image, "JPEG", getResponse().getOutputStream());
	}
	  
	private String getRandomString(int num){
		String randstring = "123zxcvbnmlk456789jhgfdsaqwertyuip";
		return String.valueOf(randstring.charAt(num));
	} 
}
