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
		<meta http-equiv="Content-Language" content="zh-cn" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />		
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
		</script>
		<script language='javascript' type='text/javascript' src='<%=basePath %>js/Print.js'></script>
	</head>
	<% 
	Map map = (Map)request.getAttribute("map"); 
	List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("list");%>
	<body style="font-size:12px">
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
				考点分布表
			</div>
		</div>			
	<%
		String BUILDNAME="",FLOOR=""; 
		int cols=1,colst=1,coltemp =1;
		boolean isnewbuild= false;
		for (int j = 0; j < list.size(); j++) {
			Map<String, Object> m = list.get(j);
			if(!BUILDNAME.equals(m.get("BUILDNAME"))){
				isnewbuild=true;
				BUILDNAME=m.get("BUILDNAME")==null?"":m.get("BUILDNAME").toString();
				FLOOR="";
				if(j!=0)
					out.print("</tr></table>");
				String floortemp = "";
				for(int t = 0; t < list.size(); t++){
					Map<String, Object> maptemp = list.get(t); 
					if(BUILDNAME.equals(maptemp.get("BUILDNAME"))){
						if(!floortemp.equals(""+maptemp.get("FLOOR"))){ 
							floortemp=""+maptemp.get("FLOOR");
							cols=coltemp>cols?coltemp:cols;
							coltemp =1;
						}else{
							coltemp++;
						}					
					}else{
						cols=coltemp>cols?coltemp:cols;
						coltemp =1;
					}
					if(t==list.size()-1){
						cols=coltemp>cols?coltemp:cols;					
					}
				} 
				colst=cols;%> 
		<br>
		<table align="center" class="withoutTable"> 
		<thead><tr><td colspan="100"><%=BUILDNAME%></td></tr></thead>
		<%
		}else{
			isnewbuild=false;
		}
		if(!FLOOR.equals(""+m.get("FLOOR"))){ 
			FLOOR=""+m.get("FLOOR");
			if(!isnewbuild){
				for(int tt=0;tt<colst;tt++){
					out.print("<td></td>");
				}
				colst=cols;
				out.print("</tr>");
			}
		%> 
		<tr>
		<td style="text-align: center;" valign="middle" width="120px">第<%=FLOOR%>层</td>
		<%} 
		colst--;
		%>
		<td style="text-align: center;" valign="middle">
		 <font style="font-size:11px;font-weight:bold;"><%=m.get("KCBH")%>考场</font><br/>
		<%=m.get("ROOMNAME")%><br/>
		考生人数：<%=m.get("KSNUM")%>个考生,<%=m.get("KMNUM")%>批
		<!--
		<br/>
		<%if(m.get("MINKSCODE")!=null&&!m.get("MINKSCODE").equals("")) {%>
		<%=m.get("MINKSCODE") %>--<%=m.get("MAXKSCODE")%>
		<%}%>
		-->
		</td>  
		<% 
		}%>  
		</tr>   
	</table>
	<%}%>
	<input type="hidden" id="complete" value="ok"/>
</body>
</html>
