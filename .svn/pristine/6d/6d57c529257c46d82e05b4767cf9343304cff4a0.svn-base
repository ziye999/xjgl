<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.Map.Entry"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String pageSize="A4";
String orientations="P";
String width="0mm";
if("P".equals(orientations)){
	width = "210mm";
}else if ("L".equals(orientations)){
	width="297mm";
}
int dataList_length = 2;
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<meta http-equiv="Content-Language" content="zh-cn"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>		
		<link rel="stylesheet" href="<%=basePath %>css/Print.css" type="text/css"/>
		<script type="text/javascript">
			var _config_ = {
				pageSize : "<%=pageSize%>",
				orientation : "<%=orientations%>",
				top :0,
				bottom : 40,
				left : 15,
				right : 15
			};
		</script>
		<style type="">
			.innerTable{
				border-top:0px;
				border-left:0px;
			}
			.innerTable tr td{
				border-bottom:0px;
				border-right:0px;
				border:0px;
			}
			
		</style>
		<script language='javascript' type='text/javascript' src='<%=basePath %>js/Print.js'></script>
	</head>
	<body style="font-size:12px">
		<c:forEach items="${stuList}" var="ks" varStatus="status">
		<div style="float: left;border: solid 1px #000000;margin-left: 10px;margin-top: 10px;">
			<table class="innerTable">
				<tr>
					<td style="text-align: center;" valign="middle">
				    	<c:if test="${ks.path==null}">
				     	<img src="img<%=File.separator%>basicsinfo<%=File.separator%>mrzp_img.jpg" style="width:90px;height:auto;"/>
				     	</c:if>
				     	<c:if test="${ks.path!=null}">
				     	<img src="${ks.path}" width="90" height="100"/>
				     	</c:if>
			     	</td>
			   	</tr>
			   	<tr><td style="text-align: center;font-size: 16px;font-weight: bold;" >${ks.xm}</td></tr>
			  	<tr><td>&nbsp;身份证号：${ks.sfzjh}</td></tr>   
			</table>
		</div>
		</c:forEach>		
	</body>
</html>
