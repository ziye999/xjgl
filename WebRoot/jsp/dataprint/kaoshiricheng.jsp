<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jiajie.util.StringUtil"%>
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
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<meta http-equiv="Content-Language" content="zh-cn"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>		
		<link rel="stylesheet" href="<%=basePath %>css/Print.css" type="text/css"/>
		<style type="">
			.withoutTable{
				border-top:0px;
				border-left:0px;
				border:solid #000000 1px;				
			}			
		</style>
		<script type="text/javascript">
			var _config_ = {
				pageSize : "<%=pageSize%>",
				orientation : "<%=orientations%>",
				top :0,
				bottom : 40,
				left : 15,
				right : 15
			};
			function autoRowSpan(tbid,row,col) { 
				var lastValue=""; 
				var value=""; 
				var pos=1; 
				var tb = document.getElementById(tbid);				
				for(var i=row;i<tb.rows.length;i++){ 
					value = tb.rows[i].cells[col].innerText;
					if(lastValue == value){ 
						tb.rows[i].deleteCell(col); 
						tb.rows[i-pos].cells[col].rowSpan = tb.rows[i-pos].cells[col].rowSpan+1; 
						pos++; 
					}else{ 
						lastValue = value; 
						pos=1; 
					} 
				} 
			} 			
		</script>
		<script language='javascript' type='text/javascript' src='<%=basePath %>js/Print.js'></script>
	</head>
	<% 
	Map map = (Map)request.getAttribute("map"); 
	List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("list");%>
	<body style="font-size:12px" onload="autoRowSpan('table_body',1,0)">
	<%if(list==null || list.size()==0){ %>
		<div style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
			<div style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
				暂无数据！
			</div>
		</div>
	<%}else{ %>
		<div pagetitle="pagetitle" style="font-size:18px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
			<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:center;">
				湖南省行政执法人员资格电子化考试<br/>
				${objDz.DZ}<br/>
				日程表
			</div>
		</div>
		<table style="border: none;"><tr style="border: none;"><td height="10px" style="border: none;"></td></tr></table>
		<table align="center" id="table_body" class="withoutTable">
		<thead>
			<tr height="30px">
				<td width="300px"  align="center">
					地点 
				</td>
				<td width="80px"  align="center">
					日期 
				</td>
				<%
				int maxchangci = 0;
				for(Map<String, Object> m :list){
					String[] kms = StringUtil.str2Arr((String)m.get("KMS"), ",");
					if(kms!=null&&kms.length>maxchangci){
						maxchangci=kms.length;
					}
				}
				for(int i=1;i<=maxchangci;i++){ %>
				<td><%="第"+i+"批" %></td>
				<%} %>
			</tr> 
		</thead>
		<tbody>
		<%
		for (int j = 0; j < list.size(); j++) {
			Map<String, Object> m = list.get(j);
			String dzStr = m.get("DZ").toString();%>					
			<tr height="30px">
				<td width="300px"  align="center" >
					<%=dzStr.replaceAll(",","</br>")%>
				</td>
				<td style="text-align: center;" valign="middle" width="80px">
					<%=m.get("GDATE") %><br> 
					(<%=StringUtil.dayForWeek((String)m.get("GDATE")) %>)
				</td>
				<%
				for(int i=0;i<maxchangci;i++){
					String km = "";
					String[] kms = StringUtil.str2Arr((String)m.get("KMS"), ",");
					if(kms!=null&&i<kms.length){
						km=kms[i];
					}%>
				<td style="text-align: center;" valign="middle" width="120px">				
					<%=km %>
				</td>
				<%
				}%>
			</tr> 
		<%
		}%>
		</tbody>
	</table>
	<%}%>
	<input type="hidden" id="complete" value="ok"/>
</body>
</html>
