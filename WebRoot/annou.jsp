<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@  taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   
   
   		<a title="查询" href="registrationSystem.action"></a>
    
    
    	查询得到的数据：${sessionScope.list }
    	
    	<c:forEach  items="${list}" var="s">
    		${s.xnxqid } | ${s.xnxqid } |${s.xqmc } | ${s.xxkssj }| ${s.xxjssj } |${s.ssdwm } | ${s.yxbz }
    	
    	</c:forEach>
  </body>
</html>
