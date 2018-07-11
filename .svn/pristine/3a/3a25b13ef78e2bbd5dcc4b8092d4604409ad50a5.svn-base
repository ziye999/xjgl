<%@page import="com.jiajie.util.bean.MBspOrgan"%>
<%@page import="com.jiajie.util.bean.MBspInfo"%>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core"   prefix="c"%>
<c:set var="ProjectPath" value="${pageContext.request.contextPath}"/>
<%
	MBspInfo mb = (MBspInfo)request.getSession().getAttribute("mBspInfo");
	MBspOrgan mo = mb.getOrgan();
	
%>

<!-- 项目根路径 -->
<input type="hidden" id="RootPath" value='<c:out value="${ProjectPath}"/>'>
<input type="hidden" id="UserId" value='<%=mb.getUserId()%>'>
<input type="hidden" id="UserName" value='<%=mb.getUserName()%>'>
<input type="hidden" id="UserType" value='<%=mb.getUserType()%>'>
<input type="hidden" id="OrganCode" value='<%=mo.getOrganCode()%>'>
<input type="hidden" id="OrganName" value='<%=mo.getOrganName()%>'>



<script type="text/javascript">
	var mBspInfo = new MBspInfo();
	var JH = new JsHelper();
</script>
