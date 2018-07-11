<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="java.net.URLConnection"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String j_username = request.getParameter("j_username").toString();
String j_password = request.getParameter("j_password").toString();
String rdmCode = request.getParameter("rdmCode").toString();
%>
<html>
  <head>
  <script type="text/javascript" src="./js/md5.js" ></script>
  </head>  
  <body style='display:none'>
  <form name="frmLogin" method="post" action="">
    <div class="login_con">
        <div class="login_user">
       		<input type="text"  maxlength="20" name="j_username" style="font-size: 14px" value="<%= j_username%>"/>
        </div>
        <div class="login_psw">
       		<input type="password" maxlength="20" name="j_password" value="<%= j_password%>"/>
        </div>
        <div class="login_ca">
       		<input type="text" maxlength="4" name="rdmCode"  id="rdmCode" style="font-size: 14px" value="<%= rdmCode%>"/>
    	</div>
    </div>	
  </form>
  </body>
</html>
<script type="text/javascript">	
var salt = "1#2$3%4(5)6@7!poeeww$3%4(5)djjkkldss";
function doLogin(){
    document.forms[0].action ="http://192.168.40.59:8080/memsdev/j_bsp_security_check/caLdap";
    document.forms[0].submit();
}
doLogin();
</script>	