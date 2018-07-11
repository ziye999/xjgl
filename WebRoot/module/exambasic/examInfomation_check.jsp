<%@ page language="java" pageEncoding="utf-8" import="java.util.*" %>
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
		<link rel="stylesheet" href="<%=basePath%>css/Print.css" type="text/css"/>
		<style type="">
			.withoutTable{
				border-top:0px;
				border-left:0px;
				border:dotted 1px #000000;
			}
			.withoutTable tr td{
				border-bottom:0px;
				border-right:0px;
				border:dotted 1px #000000;
			}
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
		<script language='javascript' type='text/javascript' src='<%=basePath %>js/Print.js'></script>
	</head>
	<body style="font-size:12px">
		<%
		List ksList=(List)request.getAttribute("ksList");
		if(ksList!=null && ksList.size()>0){
			String njmc = ((HashMap)ksList.get(0)).get("NJMC")==null?"":((HashMap)ksList.get(0)).get("NJMC").toString();
			String bjmc = ((HashMap)ksList.get(0)).get("BJMC")==null?"":((HashMap)ksList.get(0)).get("BJMC").toString();%>
		<div pagetitle="pagetitle" style="font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
			<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:center;">					
				<%=njmc+bjmc%>考生信息核对表
			</div>
		</div>
		<table align="center" id="table_body" class="withoutTable">
			<tr>
			<c:forEach items="${ksList}" var="ks" varStatus="status">
			<c:if test="${(status.index+1) % 3 == 1}">
			<tr>
			</c:if>
				<td align="center" valign="middle">
					<table class="innerTable">
						<tr><td  style="text-align: center;" valign="middle">
							<c:if test="${ks.PATH==null || ks.PATH==''}">
						    	<img src="img<%=File.separator%>basicsinfo<%=File.separator%>mrzp_img.jpg" width="90" height="100"/>
						    </c:if>
						    <c:if test="${ks.PATH!=null}">
						     	<img src="uploadFile<%=File.separator%>photo<%=File.separator%>${ks.PATH}" width="90" height="100"/>
						 	</c:if>
					  	</td></tr>
					    <tr><td style="text-align: center;font-size: 16px;font-weight: bold;" >${ks.XM}</td></tr>
					    <tr><td>&nbsp;考号：${ks.KSCODE}</td></tr>
					    <tr><td>&nbsp;身份证号：${ks.SFZJH}</td></tr>   
					</table>
				</td>
			<c:if test="${(status.index+1) % 3 == 0}">
			</tr>
			</c:if>
			</c:forEach>
			</tr>
		</table>
		<%	
		}else{%>	
		<div pagetitle="pagetitle" style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
			<div id="table_title" style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
				暂无数据！
			</div>
		</div>
		<%
		}%>
	</body>
</html>
