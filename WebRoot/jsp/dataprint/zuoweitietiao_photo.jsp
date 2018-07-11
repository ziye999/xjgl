<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.Map.Entry"%>
<%@ page import="java.io.File"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String pageSize="A4";
	String orientations="P";
	String width="0mm";
	if("P".equals(orientations)){
		width = "210mm";
	}else if ("L".equals(orientations)){
		width="297mm";
	}
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
			.innerTable{
				 border-collapse: collapse;border: none;
			}
			.innerTable tr{
				border: none;
			}
			.innerTable tr td{
				border: solid #FFFFFF  1px;
			}
			.innerTd{
				font-weight:normal;font-size: 13px;height: 22px;
			}
			.sideDiv{
				width: 326px;height: 146px;border: solid #000 1px;
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
		<script type="text/javascript" src="<%=basePath %>js/barcode/jquery-1.4.4.min.js"></script>
        <script type="text/javascript" src="<%=basePath %>js/barcode/jquery-barcode.js"></script>
	</head>

	<body style="font-size:12px">
	<%	List dataList=(List)request.getAttribute("dataList");			
		if(dataList!=null && dataList.size()>0){
			int dataLength = dataList.size();%>
		<input value="<%=dataList.size()%>" id="count" style="display: none;"/>		
		<c:forEach items="${dataList}" var="ks" varStatus="status">
			<div class="sideDiv" style="margin-top: 20px;float: left;margin-left: 10px;">				
				<table class="innerTable" style="margin-top: 14px;">
					<tr>
						<td rowspan="5" width="96" class="innerTd">
							<c:if test="${ks.PATH==null}">
				     			<img src="img<%=File.separator%>basicsinfo<%=File.separator%>mrzp_img.jpg" style="float:left; width:100px; height:110px; margin-left:6px;"/>
				     		</c:if>
				     		<c:if test="${ks.PATH!=null}">
				     			<img src="uploadFile<%=File.separator%>photo<%=File.separator%>${ks.PATH}" style="float:left; width:100px; height:110px; margin-left:6px;"/>
				     		</c:if>
						</td>
						<td class="innerTd" style="font-size: 14px;font-weight: bold;">座&nbsp;位&nbsp;号：${ks.ZWH}</td>
			     	</tr>
					<tr><td class="innerTd">姓&nbsp;&nbsp;名：${ks.XM}</td></tr>
					<tr><td class="innerTd">考&nbsp;&nbsp;号：${ks.KSCODE}</td></tr>
					<tr><td class="innerTd">身份证号：${ks.SFZJH}</td></tr>
					<c:if test="${ks.PCMC!=null}">
					<tr><td class="innerTd">考试时间：${ks.PCMC}</td></tr>
					</c:if>
				</table>
			</div>			
		</c:forEach>		
	<%	}else {%>
		<div style="width:<%=width%>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
			<div style="width:<%=width%>;margin-left:auto;margin-right:auto;text-align:center;">
				暂无数据！
			</div>
		</div>
	<%	}%>
	<input type="hidden" id="complete" value="ok"/>
	</body>
</html>
