<%@page import="java.util.Map.Entry"%>
<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%@ page import="java.io.PrintWriter"%>
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
		<%	List xsList=(List)request.getAttribute("xsList");
			if(xsList!=null && xsList.size()>0){
				Map map1 = (HashMap)xsList.get(0);
				String xxmc = "";
				String nj = "";
				String bj = "";
				if (map1!=null&&map1.size()>0) {
					xxmc = map1.get("XXMC")==null?"":map1.get("XXMC").toString();
					nj = map1.get("NJMC")==null?"":map1.get("NJMC").toString();
					bj = map1.get("BJMC")==null?"":map1.get("BJMC").toString();
				}%>
			<div pagetitle="pagetitle" style="font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
				<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:center;">
					<%=xxmc+nj+bj%>考生花名册
				</div>
			</div>
			<table align="center" id="table_body" class="withoutTable">
				<thead>
					<tr>
						<td width="30px">序号</td>
						<td width="100px">姓名</td>
						<td width="70px">性别</td>
						<td width="70px">民族</td>
						<td width="100px">出生年月</td>
						<td width="170px">身份证号</td>						
					</tr>
				</thead>
				<tbody>
		<%  	for(int i=0;i<xsList.size();i++){
					Map map = (HashMap)xsList.get(i);%>
					<tr>
						<td style="text-align: center;"><%=i+1 %></td>
						<td><%=map.get("XM")==null?"":map.get("XM") %></td>
						<td style="text-align: center;"><%=map.get("XB")==null?"":map.get("XB") %></td>
						<td><%=map.get("MZ")==null?"":map.get("MZ") %></td>
						<td style="text-align: center;"><%=map.get("CSRQ")==null?"":map.get("CSRQ") %></td>
						<td ><%=map.get("SFZJH")==null?"":map.get("SFZJH") %></td>						
					</tr>
		<% 		}%>
				</tbody>
			</table>	
		<%	}else{%>
			<div pagetitle="pagetitle" style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
				<div id="table_title" style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
					暂无数据！
				</div>
			</div>
		<%	}%>
	</body>
</html>

