<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.jiajie.util.StringUtil"%>
<%@ page import="java.io.File"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
	<c:if test="${stulist == null || fn:length(stulist) == 0}">
		<div style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
				<div style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
					暂无数据！
				</div>
			</div>
	</c:if>
	<c:if test="${stulist != null && fn:length(stulist) > 0 }">
		<c:if test="${zstype != 4 }">
			<c:forEach var="stu" items="${stulist}"> 
			<div style='background-color: white;line-height: 20px'>
				<br/>
				<p align='center' style='font-size: 32px'>${stu.TITLE}</p><br/>
				<table align='center' style="border: 0px;"> 
					<tr>
					 	<td style='border: 1px solid;width: 325px;height: 400px;'>
					 		<c:if test="${stu.PATH==null}">
					 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='img<%=File.separator%>basicsinfo<%=File.separator%>mrzp_img.jpg' style="width: 120px;height: 160px;"><br/>
					 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(加盖钢印有效)<br/>
					 		</c:if>
					 		<c:if test="${stu.PATH!=null}">
						 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='uploadFile<%=File.separator%>photo<%=File.separator%>${stu.PATH}' style="width: 120px;height: 160px;"><br/>
						 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(加盖钢印有效)<br/>
						 	</c:if>
						 	<p style="font-size: 16px;letter-spacing:1px;text-align:justify;line-height: 30px;">
							&nbsp;&nbsp;身份证号:<u>${stu.SFZJH}</u><br/>
						 	&nbsp;&nbsp;通过证编号:<u>${stu.BYZSH}</u><br/>
						 	&nbsp;&nbsp;查询网址:<u>http://www.chsi.com.cn/</u></p>
					 	</td> 
					 	<td style='border-bottom: 1px solid;border-right: 1px solid;border-top: 1px solid;width: 325px;height: 400px;'>
					 		<br/><br/><p style="font-size: 16px;letter-spacing:2px;text-align:justify;text-indent: 60px;line-height: 30px;">考生:&nbsp;<u><b>${stu.XM}&nbsp;</b></u>,性别:&nbsp;<b><u>${stu.XBM}</u>&nbsp;</b>。于&nbsp;<b><u>${stu.LQSJ}</u></b>&nbsp;在本单位<u>${stu.XD}</u>修业期满。准予${stu.BYTYPE}。</p>
							<br/><br/>
							<p style="font-size: 16px;text-indent: 10px;">组织单位(公章): </p>
							<br/>
							<h2 align='right'>年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日 </h2>
						</td>
					<tr> 
				</table> 
			</div>
			</c:forEach>
		</c:if>
		<c:if test="${zstype == 4 }">
			<c:forEach var="stu" items="${stulist}"> 
			<div style='background-color: white;line-height: 20px;height: 600px;'>
				<table align='center' style="border: 0px;"> 
					<tr>
						<td style='border-right: 2px dotted;border-bottom:0px;width: 325px;height: 450px;padding-right: 20px;'>
					 		<p align="center" style="font-size: 22px;"><b>湖南省普通中小学学历证明书</b></p>
						 	<p align="center">（存  根）</p>
						 	<p align="right">第 <u> 99 </u> 号</p>
						 	<p style="font-size: 16px;letter-spacing:2px;text-align:justify;text-indent: 60px;line-height: 30px;">
						 	考生 <u>${stu.XM} </u> ,身份证号 <u> ${stu.SFZJH } </u>,性别 <u>${stu.XBM }</u>,
						 	系<u>${fn:substring(stu.JG,0,fn:indexOf(stu.JG,'省')) }</u>省<u>${fn:substring(stu.JG,fn:indexOf(stu.JG,'省')+1,fn:indexOf(stu.JG,'市')) }</u>县(市,区)人。<u>${stu.BYSJ}</u>在<u>${stu.xxmc}</u>
						 	参考单位<u>${stu.XD }</u>通过(学制${stu.XDNS}年),原通过证书编号为<u>${stu.BYZSH }</u>因<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>,
						 	特发给学历证明.<br /><br />
						 	经手人 （签 字）<br /><br />
						 	校长（签 字）<br /><br /></p>
						 	<p align="right" style="font-size: 16px;">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</p>
					 	</td> 
					 	<td style='width: 325px;height: 450px;border: none;padding-left: 20px;'>
					 		<p align="center" style="font-size: 22px;">湖南省普通中小学学历证明书</p>
					 		<p align="right">&nbsp;</p>
						 	<p align="right">第 <u> 99 </u> 号</p>
						 	<p style="font-size: 16px;letter-spacing:2px;text-align:justify;text-indent: 60px;line-height: 30px;">
						 	考生 <u>${stu.XM} </u> ,身份证号 <u> ${stu.SFZJH } </u>,性别 <u>${stu.XBM }</u>,
						 	系<u>${fn:substring(stu.JG,0,fn:indexOf(stu.JG,'省')) }</u>省<u>${fn:substring(stu.JG,fn:indexOf(stu.JG,'省')+1,fn:indexOf(stu.JG,'市')) }</u>县(市,区)人。<u>${stu.BYSJ}</u>在<u>${stu.xxmc}</u>
						 	参考单位<u>${stu.XD }</u>通过(学制${stu.XDNS}年),原通过证书编号为<u>${stu.BYZSH }</u>因<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>,
						 	特发给学历证明，具有与通过证书同等效力。<br /><br /> 
						 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参考单位(印章):&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参考单位(公章):<br /><br />
						 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主管组织单位行政部门验印:</p>						 			
						 	<p align="right" style="font-size: 16px;">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</p>
						</td>
					<tr> 
				</table> 
			</div>
			</c:forEach>
		</c:if>
	</c:if>
</body>
</html>
