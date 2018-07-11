<%@ page language="java" pageEncoding="utf-8" import="java.util.*" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.util.Map.Entry"%>
<%@ page import="java.io.PrintWriter"%>
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
			<%
				List countList=(List)request.getAttribute("countList");
				if(countList!=null && countList.size()>0){
					int j = 0;
					for(int i=0;i<countList.size();i++){
						Map map = (HashMap)countList.get(i);
						if(i==0){
						%>
							<div pagetitle="pagetitle" style="font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
								<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:center;">
									<%=map.get("XXMC")==null?"":map.get("XXMC").toString()%>报名信息表
								</div>
							</div>
							<table align="center" id="table_body" class="withoutTable">
								<thead>
									<tr>
										<td width="30px">序号</td>
										<!--
										<td width="100px">等级</td>
										<td width="100px">科目</td>
										-->										
										<td width="80px">身份证件号</td>
										<td width="80px">考号</td>
										<td width="80px">姓名</td>
										<td width="50px">性别</td>
										<td>考试批次</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="text-align: center;"><%=++j %></td>
										<!--
										<td><%=map.get("NJMC")==null?"":map.get("NJMC").toString()%></td>
										<td><%=map.get("BJMC")==null?"":map.get("BJMC").toString()%></td>
										-->
										<td><%=map.get("SFZJH")==null?"":map.get("SFZJH").toString()%></td>
										<td><%=map.get("KSCODE")==null?"":map.get("KSCODE").toString()%></td>
										<td><%=map.get("XM")==null?"":map.get("XM").toString()%></td>
										<td style="text-align: center;"><%=map.get("XB")==null?"":map.get("XB").toString()%></td>
										<td><%=map.get("KSKM")==null?"":map.get("KSKM").toString()%></td>
									</tr>
						<% 
						}
						if (map!=null) {
							String xxdm = map.get("XXDM")==null?"":map.get("XXDM").toString();
							if(i>0){
								String xxdmB = "";
								if (countList.size()-1>=i-1) {
									xxdmB = ((HashMap)countList.get(i-1)).get("XXDM")==null?"":((HashMap)countList.get(i-1)).get("XXDM").toString();
								}
								if(!"".equals(xxdmB)&&!xxdm.equals(xxdmB)){
									j=0;%>
								</tbody>
							</table>
							<div pagetitle="pagetitle" style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
								<div id="table_title" style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
									<%=map.get("XXMC")==null?"":map.get("XXMC").toString()%>报名信息表
								</div>
							</div>
							<table align="center" id="table_body" class="withoutTable">
								<thead>
									<tr>
										<td width="30px">序号</td>
										<!--
										<td width="100px">等级</td>
										<td width="100px">科目</td>
										-->
										<td width="80px">身份证件号</td>
										<td width="80px">考号</td>
										<td width="80px">姓名</td>
										<td width="50px">性别</td>
										<td>考试批次</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="text-align: center;"><%=++j%></td>
										<!--
										<td><%=map.get("NJMC")==null?"":map.get("NJMC").toString()%></td>
										<td><%=map.get("BJMC")==null?"":map.get("BJMC").toString()%></td>
										-->
										<td><%=map.get("SFZJH")==null?"":map.get("SFZJH").toString()%></td>
										<td><%=map.get("KSCODE")==null?"":map.get("KSCODE").toString()%></td>
										<td><%=map.get("XM")==null?"":map.get("XM").toString()%></td>
										<td style="text-align: center;"><%=map.get("XB")==null?"":map.get("XB").toString()%></td>
										<td><%=map.get("KSKM")==null?"":map.get("KSKM").toString()%></td>
									</tr>
								<%
								}else{
								%>
									<tr>
										<td style="text-align: center;"><%=++j%></td>
										<!--
										<td><%=map.get("NJMC")==null?"":map.get("NJMC").toString()%></td>
										<td><%=map.get("BJMC")==null?"":map.get("BJMC").toString()%></td>
										-->
										<td><%=map.get("SFZJH")==null?"":map.get("SFZJH").toString()%></td>
										<td><%=map.get("KSCODE")==null?"":map.get("KSCODE").toString()%></td>
										<td><%=map.get("XM")==null?"":map.get("XM").toString()%></td>
										<td style="text-align: center;"><%=map.get("XB")==null?"":map.get("XB").toString()%></td>
										<td><%=map.get("KSKM")==null?"":map.get("KSKM").toString()%></td>
									</tr>
								<% 
								}
							}
						}
					}
					%>
					</tbody>
				</table>
				<table style="border: none;"><tr style="border: none;"><td height="180px" style="border: none;"></td></tr></table>	
					<%
				}else{
					%>
					<div pagetitle="pagetitle" style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
						<div id="table_title" style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
							暂无数据！
						</div>
					</div>
					<%
				}
			 %>												
	</body>
</html>

