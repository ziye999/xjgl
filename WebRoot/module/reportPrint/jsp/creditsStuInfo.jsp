<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--<link rel="stylesheet" type="text/css" href="styles.css">-->
<link rel="stylesheet" href="<%=basePath%>css/Print.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/dataprint/tcstyle.css" media="screen"/>
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
<script language='javascript' type='text/javascript' src='<%=basePath%>js/Print.js'></script>
</head>
<body style="font-size:12px">
	<div pagetitle="pagetitle" style="font-size:26px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
		<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:center;">
			<label style="font-size: 26px;">未达到144个学分换证统计表</label>
		</div>
	</div>		
<%	List<Map<String,Object>> list=(List<Map<String,Object>>)request.getAttribute("printList");
	if(list!=null && list.size()>0){%>
	<table align="center" id="table_body">
		<thead>
			<tr>								
				<td>序号</td>
				<td>姓名</td>
				<td>科目名称</td>
				<td>结业时间</td>
				<td>是否已满三年</td>
				<td>是否已换证</td>
			</tr>
		</thead>
		<tbody>
<%		for(int i=0;i<list.size();i++){
			Map<String,Object> map=list.get(i);%>
			<tr>
				<td style="text-align: center;"><%=i+1%></td>
				<td><%=map.get("XM")%></td>
				<td><%=map.get("BJMC")%></td>
				<td><%=map.get("BYJYNY")%></td>
				<td><%=map.get("SFTHREEYEAR")%></td>
				<td><%=map.get("SFDY")%></td>
			</tr>						
<%		}%>
		</tbody>
	</table>
	<div><div style="float: left;">制表人：${username }</div><div style="float: right;">时间：${time}</div></div> 
<%	}else{%>
	<script>
		document.getElementById("table_title").style.display="none";
	</script>
	<div style="font-size: 20px;">对不起，没有数据！</div>
<%	}%>											
</body>
</html>
