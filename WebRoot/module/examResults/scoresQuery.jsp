<%@ page import="java.util.Map.Entry"%>
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
				考生成绩表
			</div>
		</div>
		<table align="center" id="table_body">
			<thead>
				<tr>
					<td width="30px">序号</td>
					<td width="70px">考号</td>
					<td width="70px">身份证号</td>
					<td width="50px">姓名</td>
					<td width="30px">性别</td>
					<c:choose>
					<c:when test="${not empty kmList}">
					<c:forEach items="${kmList}" var="km" varStatus="kmvs">	
					<td style="text-align:center;">${km.KCH}</td>
					</c:forEach>
					</c:when>
					</c:choose>
				</tr>
			</thead>
		<tbody>
		<%--<c:choose>						
		<c:when test="${not empty list}">
		<c:forEach items="${list}" var="scores" varStatus="vs"> --%>
		<%
		List<Map<String,Object>> list=(List)request.getAttribute("list");
		List<Map<String,Object>> kmList=(List)request.getAttribute("kmList");
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map<String,Object> map=list.get(i);%>
				<tr>
					<td style="text-align:center;"><%=i+1%></td>
					<td style="text-align:right;"><%=map.get("EXAMCODE")%></td>
					<td style="text-align:right;"><%=map.get("SFZJH")%></td>
					<td style="text-align:right;"><%=map.get("XM")%></td>
					<td style="text-align:right;"><%=map.get("XB")%></td>
		<%		Set<Entry<String,Object>> set = map.entrySet();//取得键值对的对象set集合
				for(int j=0;j<kmList.size();j++){
					Map<String ,Object> kmmap=kmList.get(j);
					String subName=kmmap.get("KCH").toString();
					for (Entry<String, Object> en : set) {// 遍历键值对的集合
						String key=en.getKey();//en.getValue()
						if(key.equals(subName)){%>
							<td style="text-align:right;"><%=en.getValue() %></td>
		<%					break;
						}
					}													
				}%>
				</tr>
		<%	}
		}%>										
				<%--
				<tr>
					<td style="text-align:center;">${vs.index+1}</td>
					<td style="text-align:center;">${scores.XJH}</td>
					<td style="text-align:right;">${scores.EXAMCODE}</td>
					<td style="text-align:right;">${scores.XM}</td>
					<td style="text-align:right;">${scores.XB}</td>
					<c:choose>
					<c:when test="${not empty kmList}">
					<c:forEach items="${kmList}" var="kms" varStatus="kmvss">
					<c:set var="kmz" value="scores.${kms.SUBJECTNAME}"></c:set>	
					<td style="text-align:center;">${${kmz} }</td>															
					</c:forEach>
					</c:when>
					</c:choose>
				</tr>									
				</c:forEach>
				</c:when>
				</c:choose>--%>
			</tbody>
		</table>	
	</body>
</html>

