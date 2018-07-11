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
		<c:forEach items="${dataList}" var="ks" varStatus="status">
		<div pagetitle="pagetitle" style="font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
			<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:center;">				
				湖南省行政执法人员资格电子化考试<br/>
				${objDz.DZ}<br/>
				考试情况登记表
			</div>
		</div>
		<table style="border: none;"><tr style="border: none;"><td height="10px" style="border: none;"></td></tr></table>
		<table align="center" id="table_body" class="withoutTable">
			<tr>									
					<td align="center" valign="middle">
						<table class="innerTable">
							<tr>
								<td><b>考&nbsp;&nbsp;点</b></td>
								<td style="border-bottom:1px solid #666666;">${ks.KDMC}</td>
								<td><b>考&nbsp;&nbsp;场</b></td>
								<td style="border-bottom:1px solid #666666;">${ks.KCBH}</td>
							</tr>
							<tr>
								<td style="width:62px;"><b>考试批次</b></td>
								<td style="width:250px;border-bottom:1px solid #666666;">${ks.PC}</td>
								<td style="width:62px;"><b>考试时间</b></td>
								<td style="border-bottom:1px solid #666666;">${ks.SJ}</td>
							</tr>							
							<tr>
								<td><b>考号起止</b></td>
								<td style="border-bottom:1px solid #666666;" colspan="3">${ks.KHQZ}</td>
							</tr>												     	
						</table>
						<table class="innerTable">
							<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>应考人数：</b>${ks.ksnum}</td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>实考人数：</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>缺考人数：</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>违纪人数：</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"><b>作弊人数：</b></td>
							</tr>
						</table>
						<table class="innerTable">
							<tr>
								<td style="width:62px;border-right:1px solid #666666;" rowspan="14"><b>缺考情况</b></td>
								<td style="border-bottom:1px solid #666666;border-right:1px solid #666666;"><b>缺考考生姓名</b></td>
								<td style="border-bottom:1px solid #666666;border-right:1px solid #666666;"><b>准考证号</b></td>
								<td style="border-bottom:1px solid #666666;border-right:1px solid #666666;"><b>座位号</b></td>
								<td style="border-bottom:1px solid #666666;border-right:1px solid #666666;"><b>缺考考生姓名</b></td>
								<td style="border-bottom:1px solid #666666;border-right:1px solid #666666;"><b>准考证号</b></td>
								<td style="border-bottom:1px solid #666666;"><b>座位号</b></td>								
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>
							</tr>																				
						</table>
						<table class="innerTable">
							<tr>
								<td style="width:62px;border-top:1px solid #666666;border-right:1px solid #666666;" rowspan="16"><b>考场纪律</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>违纪考生姓名</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>准考证号</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>座位号</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"><b>违纪代码（见下）</b></td>															
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>								
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>								
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>														
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>								
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>								
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>							
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>								
							</tr>
							<tr>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>作弊考生姓名</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>准考证号</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;border-right:1px solid #666666;"><b>座位号</b></td>
								<td style="border-bottom:1px solid #666666;border-top:1px solid #666666;"><b>作弊代码（见下）</b></td>															
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>								
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>								
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>								
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>								
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>								
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>								
							</tr>
							<tr>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;border-right:1px solid #666666;"></td>
								<td style="height:20px;"></td>								
							</tr>
						</table>
						<table class="innerTable">
							<tr>
								<td style="width:62px;border-top:1px solid #666666;border-right:1px solid #666666;" rowspan="4"><b>签名</b></td>
								<td style="border-top:1px solid #666666;" width="35%"><b>考点监考（签名）：</b></td>
								<td width="55%" style="border-top:1px solid #666666;border-bottom:1px solid #666666;"></td>																												
							</tr>
							<tr>
								<td><b>考点主考（签名）：</b></td>
								<td style="border-bottom:1px solid #666666;"></td>																							
							</tr>
							<tr>
								<td><b>考试办考试登记表录入人员（签名）：</b></td>
								<td style="border-bottom:1px solid #666666;"></td>																				
							</tr>
							<tr>
								<td><b>考试办审核人员（签名）：</b></td>
								<td style="border-bottom:1px solid #666666;"></td>																		
							</tr>							
						</table>
						<table class="innerTable">
							<tr>
								<td style="width:62px;border-top:1px solid #666666;border-right:1px solid #666666;" rowspan="4"><b>说明</b></td>
								<td style="border-top:1px solid #666666;">
									监考教师必须认真、完整地填写本《考试登记表》。<font style="font-weight:bold;TEXT-DECORATION: underline;">《考试登记表》以考点为单位统一整理、汇总。</font>装订后随<font style="font-weight:bold;TEXT-DECORATION: underline;">机（网）考考生数据光盘</font>统一回送到对应的省级电大，然后由省级电大统一回寄至考试办。
								</td>																	
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
