<%@page import="com.jiajie.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<link rel="stylesheet" type="text/css" href='<%=basePath %>/js/ext/resources/css/ext-all.css' />
		<script type="text/javascript" src='<%=basePath %>/js/ext/adapter/ext/ext-base.js'/></script> 
		<script type="text/javascript" src='<%=basePath %>/js/ext/ext-all.js'/></script>
		<script type="text/javascript" src='<%=basePath %>/js/ext/ext-lang-zh_CN.js'/></script> 
		<script type="text/javascript" src="<%=basePath %>module/exambasic/replaceSt.js"></script>
	</head> 
<body style="font-size:12px;margin: 0px auto;background-color: #F0F0F0;">  
	<c:if test="${examInfo == null || fn:length(examInfo) == 0}">
		<div style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
				<div style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
					暂无数据！
				</div>
			</div>
	</c:if>
	<c:if test="${examInfo != null && fn:length(examInfo) > 0 }">
		<c:forEach var="exam" items="${examInfo}"> 
			<div style='background-color: #F0F0F0;width:90%;margin: 0px auto;padding:10px 10px 10px;'>
				<p style="font-size: 15px;">${exam.cont}<!--<button name="${exam.examInfoId}" onclick="replaceSt(this)">替换试题</button>--></p>
				<div>
					<c:set var="opts" value="${fn:split(exam.examOpt,'##,')}" />
					<c:forEach var="opt" items="${opts}"> 
						<c:set var="o" value="${fn:substring(opt,0,1)}" />
						<!--<c:if test="${fn:indexOf(exam.examAnsw,o) >= 0 }">
							<p style='color:red;'>${opt }</p>
						</c:if>
						<c:if test="${fn:indexOf(exam.examAnsw,o) == -1 }">
							<p>${opt }</p>
						</c:if>--> 
						<c:if test="${fn:length(opt) > 2 }">
							<p>${opt }</p>
						</c:if> 
					</c:forEach>
				</div>
			</div>	
		</c:forEach>
	</c:if>
</body
</html> 