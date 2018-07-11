<%@ page language="java" pageEncoding="utf-8" import="java.util.*" %>
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
				width:670px;
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
		<table align="center" id="table_body" class="withoutTable" style="border:none;">
			<tr style="border:none;">			
				<c:set var="sum" value="0"></c:set>
				<c:forEach items="${dataList}" var="ks" varStatus="status">														
					<c:if test="${sum % 9 == 0}">
						</tr></table>
					<c:if test="${fn:length(dataList)>status.index}">								
					<div pagetitle="pagetitle" style="font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
						<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:center;">	
							湖南省行政执法人员资格电子化考试<br/>			
							${objDz.DZ}<br/>
							考生签到表
						</div>
					</div>
					<table style="border: none;"><tr style="border: none;"><td height="5px" style="border: none;"></td></tr></table>
					<table align="center" id="table_body" class="withoutTable"><tr>
					</c:if>	
					</c:if>								
					<c:if test="${ks.ZWH==1&&sum>0}">
						<c:if test="${sum % 3 == 1}">
							<td width="223">&nbsp;</td><td width="223">&nbsp;</td></tr>						
						</c:if>
						<c:if test="${sum % 3 == 2}">
							<td width="223">&nbsp;</td></tr>						
						</c:if>											
						</tr></table>
						<c:set var="sum" value="0"></c:set>
						<div pagetitle="pagetitle" style="font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
							<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:center;">	
								湖南省行政执法人员资格电子化考试<br/>			
								${objDz.DZ}<br/>
								考生签到表
							</div>
						</div>
						<table style="border: none;"><tr style="border: none;"><td height="5px" style="border: none;"></td></tr></table>
						<table align="center" id="table_body" class="withoutTable"><tr>
					</c:if>									
					<td align="center" valign="middle">
						<table class="innerTable">
					    	<tr>
					    		<td style="text-align: center;" valign="middle" colspan="2">
						     		<c:if test="${ks.PATH==null}">
						     		<img src="img<%=File.separator%>basicsinfo<%=File.separator%>mrzp_img.jpg" width="90" height="100"/>
						     		</c:if>
						     		<c:if test="${ks.PATH!=null}">
						     		<img src="uploadFile<%=File.separator%>photo<%=File.separator%>${ks.PATH}" width="90" height="100"/>
						     		</c:if>
					     		</td>
					     	</tr>
					     	<tr><td style="text-align:center;font-size:16px;font-weight:bold;" colspan="2">${ks.XM}</td></tr>
					     	<tr><td colspan="2">考生考号：${ks.KSCODE}</td></tr>
					     	<tr><td colspan="2">身份证号：${ks.SFZJH}</td></tr> 
					     	<c:if test="${ks.SJ!=null}">
					     	<tr><td colspan="2">考试时间：${ks.SJ}</td></tr>
					     	<tr><td width="33%">考生签名：</td><td width="67%" style="border-bottom:1px solid #666666;">(座位号：${ks.ZWH})</td></tr>
					     	</c:if>
					     	<tr><td colspan="2" height="10px"></td></tr>
						</table>
					</td>
					<c:set var="sum" value="${sum+1}"></c:set>
					<c:if test="${sum % 3 == 0}">
						</tr>
					</c:if>	
					<c:if test="${fn:length(dataList)-1==status.index&&sum % 3 == 1}">
						<td width="223">&nbsp;</td><td width="223">&nbsp;</td></tr>						
					</c:if>
					<c:if test="${fn:length(dataList)-1==status.index&&sum % 3 == 2}">
						<td width="223">&nbsp;</td></tr>						
					</c:if>																			
				</c:forEach>
			</tr>
		</table>
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