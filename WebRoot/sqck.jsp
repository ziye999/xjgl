<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <!-- 重置文件 -->
  <link rel="stylesheet" href="css/normalize.css">
  <link rel="stylesheet" href="css/style.css">
  <title>申请注册参考单位</title>
   <style>  
    .bor{  
        border:3px solid red;  
    }  
 </style> 
 

  <script type="text/javascript">
  
		function doLogin1(){
		 if(!check()) return;
			
			document.getElementById('frmLogin').submit();
		}
		
			function check(){
		 var SSZGJYXZDM = document.getElementById('SSZGJYXZDM').value;
		 
		  if(SSZGJYXZDM==""){
		    alert("组考法制办不能为空!");
		   SSZGJYXZDM.focus();
		    return false;
		  }
		 
		 var XXMC = document.getElementById('XXMC').value;
		  if(XXMC==""){
		    alert("参考单位不能为空");
		 	 XXMC.focus();
		    return false;
		  } 
       var XZXM = document.getElementById('XZXM').value;
	      if(XZXM==""){
	        alert("联系人不能为空");
	        XZXM.focus();
	        return false;
	      } 
	       var BGDH = document.getElementById('BGDH').value;
	      if(BGDH==""){
	        alert("联系电话不能为空");
	       BGDH.focus();
	        return false;
	      } 
	       var YZBM = document.getElementById('YZBM').value;
	      if(YZBM==""){
	        alert("邮政编码不能为空");
	       YZBM.focus();
	        return false;
	      } 
	         var XXDZ = document.getElementById('XXDZ').value;
	      if(XXDZ==""){
	        alert("单位地址不能为空");
	       XXDZ.focus();
	        return false;
	      } 
		  return true;
		}
  
  	
 </script>
</head>
<body>
<form name="frmLogin" id="frmLogin" method="post" action="./sqAction_savesqck.do">	
  <div class="reg_div">
    <p>申请注册参考单位</p>
    <div unselectable="on" style="width:400;font-size:16px;color:#FF0000;font-weight:bold;margin-bottom:25;"><%=request.getAttribute("errormsg")==null?"":request.getAttribute("errormsg")%></div>
	
    <ul class="reg_ul">
      <li>
          <span>组考法制办：</span>
<!--           <input type="text" name="SSZGJYXZMC" id="SSZGJYXZMC" value="" placeholder="法制办" class="reg_user"> -->
          	<select  name="SSZGJYXZDM" id="SSZGJYXZDM" class="reg_user" >
			   <option value="">请选择...</option>
			   <c:forEach items="${list}" var="var">
                  <option value="${var.regioncode}"> ${var.regionedu}</option>
                </c:forEach>
			</select>
          <span class="tip user_hint"></span>
      </li>
      <li>
          <span>参考单位：</span>
          <input type="text" name="XXMC" id="XXMC" value="" placeholder="参考单位" class="reg_password">
          <span class="tip password_hint"></span>
      </li>
      <li>
          <span>负责人：</span>
          <input type="text" name="XZXM" id="XZXM"  value="" placeholder="负责人" class="reg_confirm">
          <span class="tip confirm_hint"></span>
      </li>
   
      <li>
          <span>联系电话：</span>
          <input type="text" name="BGDH" id="BGDH" value="" placeholder="联系电话" maxlength="11" onkeyup="value=value.replace(/[^\d]/g,'')" class="reg_mobile">
          <span class="tip mobile_hint"></span>
      </li>
       <li>
          <span>邮政编码：</span>
          <input type="text" name="YZBM" id="YZBM"  value="" placeholder="邮政编码"  maxlength="6" onkeyup="value=value.replace(/[^\d]/g,'')" class="reg_email">
          <span class="tip email_hint"></span>
      </li>
        <li>
          <span>单位地址：</span>
          <input type="text" name="XXDZ" id="XXDZ"  value="" placeholder="单位地址" class="reg_email">
          <span class="tip email_hint"></span>
      </li>
      <li>
     
        <button type="button" name="button"  onclick="doLogin1();" class="red_button">申请</button>
      </li>
    </ul>
  </div>
  </form>
  <script type="text/javascript" src="js/jquery.min.js"></script>
  <script type="text/javascript" src="js/script.js"></script>
   <script type="text/javascript">  
    $(document).ready(function(){  
        $("input").focus(function(){  
            $(this).addClass("bor");  
        });  
        $("input").blur(function(){  
            $(this).removeClass("bor");  
        });  
    });  
</script>  
  <div style="text-align:center;">
</div>
</body>
</html>
