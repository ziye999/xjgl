<%@page import="java.io.File"%>
<%@page import="java.util.Map.Entry"%>
<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.io.File"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		<meta http-equiv="Content-Language" content="zh-cn"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>		
		<link rel="stylesheet" href="<%=basePath %>css/Print.css" type="text/css"/>
		<style type="">
			.outTable{
				border:solid 0px #000000;
			}
			.outTable tr{
				border:solid 0px #000000;
			}
			.withoutTable{
				border-top:0px;
				border-left:0px;
				border:solid 1px #000000;
				border-right:solid 0px #000000;
				width: 332px;
				height: 98%;
				margin-bottom: 10px;
			}
			.withoutTable tr td{
				border-bottom:dotted 1px #000000;
				font-weight: lighter;
				height: 36px;
				font-size: 16px;
			}
			.withoutTd{
				border-right:dotted 1px #000000;
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
		 <script type="text/javascript">
        	function createBarcode(){
        		var count = document.getElementById("count").value;
        		for(var i=0;i<count;i++){
        			//alert($("#src_"+i).val());
        			$("#bcTarget_"+i).empty().barcode($("#src_"+i).val(), "code11",{barWidth:2, barHeight:26,showHRI:false});
        		}
        	}
        </script>
	</head>
	<body onload="createBarcode();">
		<%	List xsList=(List)request.getAttribute("xsList");
			if(xsList!=null && xsList.size()>0){%>
		<input value="<%=xsList.size() %>" id="count" style="display: none;"/>
		<table align="center" id="table_body" class="outTable">
			<c:forEach items="${xsList}" var="xs"  varStatus="status">
				<c:if test="${(status.index+1)%2 == 1}">
					<tr><td style="border:solid 0px #000000;">
				</c:if>
				<c:if test="${(status.index+1)%2 == 0}">
					<td style="border:solid 0px #000000;">
				</c:if>
					<table class="withoutTable">
						<tr><td colspan="3" style="text-align: center;font-size: 16px;">考生证</td></tr>
						<tr>
							<td class="withoutTd" colspan="2">&nbsp;姓&nbsp;&nbsp;名：${xs.XM}</td>
							<td rowspan="3" style="text-align: center;width: 111px;">
								<c:if test="${xs.PATH==null}">
				     				<img src="img<%=File.separator%>basicsinfo<%=File.separator%>mrzp_img.jpg" width="90" height="100"/>
				     			</c:if>
				     			<c:if test="${xs.PATH!=null}">
				     				<img src="uploadFile<%=File.separator%>photo<%=File.separator%>${xs.PATH}" width="90" height="100"/>
				     			</c:if>
							</td>
						</tr>
						<tr><td class="withoutTd" colspan="2">&nbsp;性&nbsp;&nbsp;别：${xs.XB}</td></tr>
						<tr><td class="withoutTd" colspan="2">&nbsp;单位代码：${xs.XXBSM}</td></tr>
						<tr><td class="withoutTd" width="20%">&nbsp;单&nbsp;&nbsp;位：</td><td colspan="2" class="withoutTd">${xs.XXMC}</td></tr>
						<tr><td class="withoutTd" width="20%">&nbsp;课&nbsp;&nbsp;程：</td><td colspan="2" class="withoutTd">${xs.BJMC}</td></tr>
						<tr><td class="withoutTd" width="20%">&nbsp;身份证号：</td><td colspan="2" class="withoutTd">${xs.SFZJH}</td></tr>						
						<!--
						<tr>
							<td colspan="3" style="border-bottom:dotted 0px #000000;">
								<input id="src_${status.index}" value="${xs.SFZJH}" style="display: none;"></input>
								<div style="float: left;">&nbsp;条形码：</div>
								<div style="float: left;" id="bcTarget_${status.index}" ></div> 
							</td>
						</tr>
						-->
					</table>				
				<c:if test="${(status.index+1)%2 == 1}">
					</td>
				</c:if>
				<c:if test="${(status.index+1)%2 == 0}">
					</td></tr>
				</c:if>
				<c:if test="${(status.index+1)%6 == 0}">
					</table>
					<table class="outTable">
				</c:if>
			</c:forEach>	
			</table>
		<%	}%>
	</body>
</html>
