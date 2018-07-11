<%@page import="java.util.Map.Entry"%>
<%@ page language="java" pageEncoding="utf-8" import="java.util.*" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String pageSize="A4";
	String orientations="L";
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
		<script language='javascript' type='text/javascript' src='<%=basePath %>js/Print.js'></script>
	</head>
	<body style="font-size:12px">
			<div pagetitle="pagetitle" style="font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
				<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:center;">					
					考生违纪情况表
				</div>
			</div>
			<table align="center" id="table_body" class="withoutTable">
				<thead>
					<tr>
						<td width="30px">序号</td>
						<td width="100px">身份证号</td>
						<td width="80px">考号</td>
						<td width="200px">参考单位</td>
						<td width="80px">姓名</td>
						<td width="50px">性别</td>
						<td width="70px">考试批次</td>
						<td width="120px">违纪处理</td>
						<td width="70px">扣除分数</td>
						<td width="150px">违纪情况</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${cheatList}" var="student" varStatus="status">
						<tr>
							<td style="text-align:center;">${status.index+1}</td>
							<td>${student.SFZJH}</td>
							<td>${student.KSCODE}</td>
							<td>${student.XXMC}</td>
							<td>${student.XM}</td>
							<td style="text-align:center;">${student.XB}</td>
							<td>${student.SUBJECTNAME}</td>
							<td>${student.OPTTYPENAME}</td>
							<td>${student.SCORE}</td>
							<td>${student.WJQQ}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>	
	</body>
</html>

