<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
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
				top :0,
				bottom : 40,
				left : 15,
				right : 15
			};
</script>
<script language='javascript' type='text/javascript'
	src='<%=basePath%>js/Print.js'></script>
</head>
<body style="font-size:12px">
	<div pagetitle="pagetitle" style="font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
				<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:center;">
					各科考试基本情况	
				</div>
			</div>			
			<%
				List<Map<String,Object>> list=(List<Map<String,Object>>)request.getAttribute("list");
				String km="";
				if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					Map<String,Object> map=list.get(i);
					if(!km.equals(map.get("EXAMNAME").toString()+map.get("XXMC").toString())){
						km=map.get("EXAMNAME").toString()+map.get("XXMC").toString();
						String lcmc=map.get("EXAMNAME").toString();
						String xxmc=map.get("XXMC").toString();
				%>				
				<table align="center" id="table_body">
					<div>
						<div style="height: 26px;text-align:left;padding-top: 10px"><label>考试名称：<%=lcmc %></label></div>
						<div style="height: 26px;text-align:left;padding-top: 10px"><label>参考单位名称：<%=xxmc %></label></div>
					</div>
							<thead>
								<tr>
									<td width="80mm" rowspan="2">学科</td>
									<td width="40mm" colspan="2">分析性指标</td>
									<td width="40mm">评价性指标</td>
									<td width="40mm" colspan="3">基础性指标</td>
								</tr>
								<tr>
									<td width="40mm">实际参考人数</td>
									<td width="40mm">单科平均分</td>
									<td width="40mm">合格率</td>
									<td width="40mm">难度系数</td>
									<td width="40mm">标准差</td>
									<td width="40mm">差异系数</td>
								</tr>
							</thead>
							<tbody>
							<% 							
							}							
							%>						
							<tr>
								<td ><%=map.get("KCMC")==null?"":map.get("KCMC") %></td>
								<td ><%=map.get("CKRS")==null?"":map.get("CKRS") %></td>
								<td ><%=map.get("SCORE")==null?"":map.get("SCORE") %></td>
								<td ><%=map.get("HGL")==null?"":map.get("HGL") %></td>
								<td ><%=map.get("NDXS")==null?"":map.get("NDXS") %></td>
								<td ><%=map.get("BZC")==null?"":map.get("BZC") %></td>
								<td ><%=map.get("CYXS")==null?"":map.get("CYXS") %></td>
							</tr>
					<% 
					if(i+1<list.size()){
						Map<String,Object> map2=list.get(i+1);
						if(!km.equals(map2.get("EXAMNAME").toString()+map.get("XXMC").toString())){%>
								<tbody>
							</table>
							<div><div style="float: left;">制表人：${username }</div><div style="float: right;">时间：${time}</div></div>
						<%}
					}else{ %>
							<tbody>
						</table>
						<div><div style="float: left;">制表人：${username }</div><div style="float: right;">时间：${time}</div></div>
					<%}
				}
				}else{
				%>
				<script>
					document.getElementById("table_title").style.display="none";
				</script>
				<div style="color: red;font-size: 20px;">对不起，没有数据！</div>
				<%
					
				}
			 %>
					
						
</body>
</html>
