<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.jiajie.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<% 
String selecttype = request.getParameter("selecttype");
Map map = (Map)request.getAttribute("map"); 
// System.out.println(map);

List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("list");
 
%>
<body style="font-size:12px">
	<%if(list==null || list.size()==0){ %>
	<div style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
		<div  style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
			暂无数据！
		</div>
	</div>
	<%}else{ %>
	<div pagetitle="pagetitle" style="font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
		<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:center;">
			<br/>${obj.XNMC }年度${obj.XQMC }${obj.EXAMNAME }考生信息对照表 <br/>
		</div>
	</div>
	<table align="center" id="table_body" class="withoutTable">
	<%if(selecttype.equals("true")){%>
	<thead>
		<tr>  
			<td>批次 </td>
			<td>考场 </td>  
			<!--td>考号</td-->  
			<td>姓名</td>  
			<td>身份证号</td>   
		</tr> 
	</thead>
	<tbody>
		<%for(Map<String, Object> objmap : list){ %>
		<tr height="30px">
			<td><%=objmap.get("PCMC")==null?"":objmap.get("PCMC")%></td>
			<td><%=objmap.get("KCBH")%>考场</td> 
			<!--td><%=objmap.get("KSCODE")%></td--> 
			<td><%=objmap.get("XM")%></td> 
			<td><%=objmap.get("SFZJH")%></td> 
		</tr>
		<%}%>
	</tbody>
	<%}else{%>
	<thead>
		<tr>  
			<td>等级</td>  
			<td>科目</td>
			<td>批次</td>	  
			<!--td>考号</td--> 
			<td>姓名</td>  
			<td>身份证号</td>   
		</tr>
	</thead>
	<tbody>
		<%for(Map<String, Object> objmap : list){ %>
		<tr height="30px">
			<td><%=objmap.get("NJMC")%></td> 
			<td><%=objmap.get("BJMC")%></td> 
			<td><%=objmap.get("PCMC")==null?"":objmap.get("PCMC")%></td>
			<!--td><%=objmap.get("KSCODE")%></td--> 
			<td><%=objmap.get("XM")%></td> 
			<td><%=objmap.get("SFZJH")%></td> 
		</tr> 
		<%}
	%></tbody><%	
	}%>    
	</table>
	<%}%>
	<input type="hidden" id="complete" value="ok"/>
</body>
</html>
