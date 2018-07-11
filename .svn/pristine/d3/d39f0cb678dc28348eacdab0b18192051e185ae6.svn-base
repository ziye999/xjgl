<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String pageSize = "A4";
	String orientations = "P";
	String width = "208mm";
	int dataList_length = 2;
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" href="<%=basePath%>css/Print.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/dataprint/tcstyle.css" media="screen" />

<script type="text/javascript">
			var _config_ = {
				pageSize : "<%=pageSize%>",
				orientation : "<%=orientations%>",
		top : 20,
		bottom : 20,
		left : 5,
		right : 5
	};
</script>
<script language='javascript' type='text/javascript'
	src='<%=basePath%>js/Print.js'></script>

</head>

<body style="font-size:12px">
	<table style="border: none">
		<%
			for (int i = 0; i < 2; i++) {
		%>
		<tr style="border: none;">
			<td style="border: none;margin: 0;">
				<div style="width:500px;height:358px; overflow:hidden; margin:0 auto;">
					<div style=" width:440px; height:268px;margin:0 10px; float:left; border:1px solid #666; padding:0 20px;">
						<h2 style="display:block; width:440px; height:50px; line-height:50px; color:#333; text-align:center; font-size:28px;font-weight:normal;margin: 0;">准考证test${param.nj }</h2>
						<span><img src="img/dataprint/img_01.jpg" style="float:left; width:130px; height:150px; margin-right:15px;"/>
						</span>

					 
						<font style="display:block; width:440px; text-align:center;"><img src="img/dataprint/code.jpg" />
						</font>
					</div>  
				</div>
				</td>
				</tr> 
		<%
			}
		%>

	</table>
</body>
</html>
