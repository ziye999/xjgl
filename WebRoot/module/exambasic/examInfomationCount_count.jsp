<%@page import="java.util.Map.Entry"%>
<%@ page language="java" pageEncoding="utf-8" import="java.util.*" %>
<%@ page import="java.io.PrintWriter" %>
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
		<meta http-equiv="Content-Language" content="zh-cn" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
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
					<%
						List countList=(List)request.getAttribute("countList");
						if(countList!=null && countList.size()>0){
						String type = request.getAttribute("type").toString();
							if(!"jyj_2".equals(type)){
					%>
							<%=((HashMap)countList.get(0)).get("XXMC").toString() %>考生报名信息统计表
					<%	
							}else{
					%>
							考生报名信息统计表
					<%
							}
						}
					 %>
				</div>
			</div>
			<% 
				if(countList!=null && countList.size()>0){
			 %>
			<table align="center" id="table_body" class="withoutTable">
				<thead>
					<tr>
						<td width="30px">序号</td>
						<c:if test="${type=='jyj_2'}">
							<td width="250px">参考单位</td>
						</c:if>
						<c:if test="${type=='school_3' || type=='school_2'}">
							<td width="120px">等级</td>
						</c:if>
						<c:if test="${type=='school_3'}">
							<td width="120px">科目</td>
						</c:if>
						<td width="100px">参考人数</td>
						<td>考试批次</td>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${countList}" var="count"  varStatus="status">
						<tr>
							<td style="text-align:center;">${status.index+1}</td>
							<c:if test="${type=='jyj_2'}">
								<td>${count.XXMC}</td>
							</c:if>
							<c:if test="${type=='school_3' || type=='school_2'}">
								<td>${count.NJMC}</td>
							</c:if>
							<c:if test="${type=='school_3'}">
								<td>${count.BJMC}</td>
							</c:if>
							<td style="text-align:center;">${count.RS}</td>
							<td>${count.KSKM}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>	
			<% }else{ %>
			<div style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
				<div  style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
					暂无数据！
				</div>
			</div>
			<%} %>
	</body>
</html>

