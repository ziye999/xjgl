<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link rel="stylesheet" href="css/css.css" type="text/css" />
<script type="text/javascript" src="./js/md5.js" ></script>
<title>湖南省行政执法人员考试系统</title>
<script type="text/javascript">	
var salt = "1#2$3%4(5)6@7!poeeww$3%4(5)djjkkldss";
function doLogin(){
	if(!check()) return;
    var password = document.getElementsByName("j_password")[0];
    password.value = hex_md5(password.value);
    document.forms[0].submit();
}

function check(){
	var j_username = document.forms[0].j_username;
	if(j_username.value==""){
		alert("用户名不能为空!");
		j_username.focus();
		return false;
	}
	j_username.value = j_username.value;
	var j_password = document.forms[0].j_password;
	if(j_password.value==""){
		alert("密码不能为空");
		j_password.focus();
		return false;
	}	
	return true;
}
function keypress(e){
	if(!window.event){
		e = e.which;
	}else{
		e = window.event.keyCode;
	}
	if(e==13||e==42){//'enter' key,*	
		doLogin();
	}
}
function ReShowCode(){
	var checkcodeimg = document.getElementById("checkcodeimg");
	var url="./loginAction_getRandcode.do?t="+Math.random();
	checkcodeimg.src = url; 
}
</script>

</head>
<body onkeydown="keypress(event);">
	<div class="top">
		<div class="head">
			<div class="logo">湖南行政执法考试报名系统</div>
			
			<div  class="logogo">业务咨询：0731-82821386&nbsp<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=3094235100&site=qq&menu=yes"><img style="vertical-align: middle;" border="0" src="http://pub.idqqimg.com/wpa/images/counseling_style_52.png" alt="qq咨询" title="qq咨询"/></a></div>
			<div class="lologo">报名时间：6月10日--6月22日</div>
			
			<div  class="logogo">技术支持：0731-82821017&nbsp<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=3391844986&site=qq&menu=yes"><img style="vertical-align: middle;" border="0" src="http://pub.idqqimg.com/wpa/images/counseling_style_52.png" alt="qq咨询" title="qq咨询"/></a></div>
			
		</div>
	</div>
	
	<form name="frmLogin" id="frmLogin" method="post" action="./loginAction_login.do">	
	<div class="banner">
		<div class="denglu">
			 <div class="dl_mm">
				   <div class="dl">
						 <div class="dl_l"><img src="img/tx_m.png" /></div><div class="dl_c"><input name="j_username" type="text" maxlength="50"/></div>						 
				   </div>
				   <div class="mm">
						 <div class="mm_l"><img src="img/suo_m.png" /></div><div class="mm_c"><input name="j_password" type="password" maxlength="20"/></div>						 
				   </div>
				   <div class="yzm">
				   		 <div class="yzm_l"><input name="rdmCode" id="rdmCode" type="text" maxlength="4"/></div>
				   		 <div class="yzm_c">				   		 	
       						<IMG id='checkcodeimg' style="cursor:hand;" border="0" src='./loginAction_getRandcode.do' onclick="ReShowCode()">       						
				   		 </div>						 
				   </div>
				   <div class="dl_an"><a href="#" onclick="doLogin();"><font style="color:#fff;font-size:16px;">登录</font></a></div>
				   <div class="dl_an"><a href="#" onclick="document.getElementById('frmLogin').reset();"><font style="color:#fff;font-size:16px;">重置</font></a></div>
				    <div class="dl_an"><a href="sqck.jsp"   target="_blank"onclick=""><font style="color:#fff;font-size:16px;">新增工作单位</font></a></div>
				   <div unselectable="on" style="width:400;font-size:12px;color:#FF0000;font-weight:bold;margin-bottom:25;"><%=request.getAttribute("errormsg")==null?"":request.getAttribute("errormsg")%></div>
			 </div> 
		</div>
	</div>
	</form>
</body>
</html>