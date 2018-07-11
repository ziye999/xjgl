<%@ page language="java" pageEncoding="UTF-8" import="java.util.*" %>
<%@ page import="java.util.Map.Entry"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.io.File"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		<style type="">
			.withoutTable{
				border-top:0px;
				border-left:0px;
				border:solid #000000 1px;
				width:665px;
			}
			.withoutTable tr td{
				border-bottom:0px;
				border-right:0px;
				border:dotted 1px #000000;
			}
			.innerTable{
				border-top:0px;
				border-left:0px;
			}
			.innerTable tr td{
				border-bottom:0px;
				border-right:0px;
				border:0px;
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
	<body style="font-size:12px">
	<%	List dataList=(List)request.getAttribute("dataList");
		if(dataList!=null && dataList.size()>0){%>
		<c:forEach items="${dataList}" var="ks" varStatus="status">
		<div pagetitle="pagetitle" style="font-size:22px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
			<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:center;">				
				湖南省行政执法人员资格电子化考试
				${objDz.DZ}
				考生信息更改表
			</div>
		</div>
		<table style="border: none;"><tr style="border: none;"><td height="10px" style="border: none;"></td></tr></table>
		<table align="center" id="table_body" class="withoutTable">
			<tr>									
				<td align="center" valign="middle">
					<table class="innerTable">
						<tr>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;" rowspan="2"><b>序号</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;" rowspan="2"><b>考试时间</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666; text-align: center;" colspan="4"><b>考生报名信息</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;text-align: center;" colspan="4"><b>考生正确信息（此栏只填更正的信息）</b></td>
						</tr>
						<tr>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>姓名</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>性别</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>身份证号码</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>参考单位</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>姓名</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>性别</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>身份证号码</b></td>								
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>参考单位</b></td>
						</tr>
						<tr>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>1</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>2</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>3</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>4</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>5</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>6</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>7</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>8</b></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
							<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>9</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>10</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>11</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>12</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>13</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>14</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>15</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>16</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>17</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>18</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>19</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>20</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>21</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>22</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>23</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>24</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
						<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>25</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"></td>								
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"></td>
						</tr>
					</table>
				</td>																						
			</tr>
		</table>
		</c:forEach>
	<%	}else{%>
		<div pagetitle="pagetitle" style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
			<div id="table_title" style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
				暂无数据！	
			</div>
		</div>
	<%	}%>	
		<input type="hidden" id="complete" value="ok"/>
	</body>
</html>
