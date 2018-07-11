<%@page import="java.util.Map.Entry"%>
<%@ page language="java" pageEncoding="UTF-8" import="java.util.*" %>
<%@ page import="java.io.PrintWriter"%>
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
			.innerTable{
				 border-collapse:collapse;border:none;width:560px;
			}
			.innerTable tr{
				border:none;
			}
			.innerTable tr td{
				border:solid #FFFFFF 1px;
			}
			.innerTd{
				font-weight:normal;font-size:16px;height:25px;border: none;
			}
			.sideDiv{
				width:620px;height:420px;border:solid #FFFFFF 1px;
			}
			.innerP{
				font-size:15px;margin:0 auto;text-align:left;line-height:18px;color:#333;border: none;								
			}
		</style>
		<script type="text/javascript">
			var _config_ = {
				pageSize : "<%=pageSize%>",
				orientation : "<%=orientations%>",
				top :0,
				bottom : 40,
				left : 15,
				right : 15,
				isHiddenPagination : true
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
        			$("#bcTarget_"+i).empty().barcode($("#src_"+i).val(), "code11",{barWidth:2,barHeight:30,showHRI:false});
        		}        		
        	}
        </script>
	</head>
	<body style="font-size:12px;border: none;" onload="createBarcode();">	
		<%
			List dataList=(List)request.getAttribute("dataList");
				if(dataList!=null && dataList.size()>0){
				int dataLength = dataList.size();
		%>
		<input value="<%=dataList.size() %>" id="count" style="display: none;"/>
			<c:forEach items="${dataList}" var="ks" varStatus="status">		
			<div id="zkz${status.index}">		
			 	<table style="border: none;width:620px;">			 		
					<tr style="border: none;">
						<td height="340px" style="border: none;">
							<table style="width:620px;height:340px;border:solid #000 1px;">
								<tr style="border: none;"><td colspan="3" style="text-align:center;font-size:22px;height:35px;font-weight:bold;border: none;">湖南省行政执法人员资格电子化考试</td></tr>
								<tr style="border: none;"><td colspan="3" style="text-align:center;font-size:22px;height:35px;border: none;">准考证</td></tr>
								<tr style="border: none;align:center;">
									<td width="3px" rowspan="8" style="border: none;"></td>
									<td valign="top" align="center" rowspan="8" width="130" class="innerTd" style="font-size:15px;border: none;">
										<c:if test="${ks.PATH==null}">
						     				<img src="img<%=File.separator%>basicsinfo<%=File.separator%>mrzp_img.jpg" style="float:left; width:110px; height:130px; margin-right:10px;"/>
						     				<c:if test="${ks.KCBH==null}">
						     				</br>未排考的考生照片暂时不显示
						     				</c:if>
						     			</c:if>
						     			<c:if test="${ks.PATH!=null}">
						     			<!-- http://zfksgl.17el.cn/xjgl/ -->
						     				<img src="uploadFile<%=File.separator%>photo<%=File.separator%>${ks.PATH}" style="float:left; width:110px; height:130px; margin-right:10px;"/>
						     			</c:if>
									</td>
									<td class="innerTd" style="font-size:15px;border: none;">姓名：${ks.XM}</td>									
								</tr>
								<tr style="border: none;"><td colspan="2" class="innerTd" style="font-size:15px;border: none;">性别：${ks.XB}</td></tr>	
								<tr style="border: none;"><td colspan="2" class="innerTd" style="font-size:15px;border: none;">考场号：第${ks.KCBH}考场</td></tr>
								<tr style="border: none;"><td colspan="2" class="innerTd" style="font-size:15px;border: none;">座位号：${ks.ZWH}</td></tr>
								<tr style="border: none;"><td colspan="2" class="innerTd" style="font-size:15px;border: none;">身份证号：${ks.SFZJH}</td></tr>
								<c:if test="${ks.PCMC!=null}">
								<tr style="border: none;"><td colspan="2" class="innerTd" style="font-size:15px;border: none;">考试时间：${ks.PCMC}</td></tr>
								</c:if>								
								<tr style="border: none;"><td colspan="2" class="innerTd" colspan="2" style="font-size:15px;border: none;">参考单位：${ks.XX}</td></tr>
								<tr style="border: none;"><td colspan="2" class="innerTd" colspan="2" style="font-size:15px;border: none;">考场地址：${ks.KDDZ}&nbsp;第${ks.FLOORS}楼${ks.ROOMNAME}</td></tr>
								<!--
								<tr>
									<td colspan="2">
										<input id="src_${status.index}" value="${ks.SFZJH}" style="display: none;"></input>
										<div id="bcTarget_${status.index}" style="margin:10px 0px;"></div> 
									</td>
								</tr>
								-->
							</table>						
						</td>
					</tr>
			 	</table>
				<table style="border:none;width:620px;"><tr style="border:none;"><td height="30px" style="border:none;"></td></tr></table>
			 	<table style="border:none;width:620px;">
					<tr style="border:none;">
						<td height="440px" style="border:none;">
							<table style="border:solid #000 1px;margin:0;float:left;padding:0 5px;width:620px;height:440px;">
								<tr style="border: none;"><td style="border: none;">
									<p style="width:620px;font-weight:bold;font-size:22px;margin:0 auto;text-align:center;line-height:50px;color:#333;border: none;">考生须知</p>
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">
									&nbsp;一、考生必须于开考前15分钟凭准考证和有效《居民身份证》原件进入考场，不按要求携带证件者，不得进入考场，如身份证有遗失者，需要出示派出所证明；开考30分钟后不得入场；开考30分钟内不得离场。
									</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">
									&nbsp;二、考生签到后对号入座，将准考证、身份证放置桌面左上角备查。
									</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">
									&nbsp;三、考生必须按照准考证规定的考试时间、场次、座位号参加考试，按要求输入本人身份证信息进入考试系统，认真阅读考场纪律，按照答题要求作答。在系统显示身份证信息时，考生须认真核对，发现错漏须及时向监考老师提出，监考老师在《考生信息更改表》上更正后，考生签名确认。
									</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">
									&nbsp;四、考试人员应严格遵守以下规定，违反者按作弊论处，成绩无效，并且将考生信息报省法制办和省电大。
									</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">&nbsp;&nbsp;（一）不得携带手机、电子接收（发射）及储存器材等物品进入考场；</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">&nbsp;&nbsp;（二）不得携带任何与考试有关的书籍和资料；</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">&nbsp;&nbsp;（三）不得就试题答案内容向监考人员询问；</p>									
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">&nbsp;&nbsp;（四）保持考场安静、清洁，禁止吸烟，严禁交头接耳、左顾右盼、夹带、偷看资料，不准窥视他人屏幕。</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">
									&nbsp;五、由他人冒名顶替考试的成绩无效，被替考人员、指使他人或者代替他人考试者报省法制办处理。
									</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">
									&nbsp;六、考生应当尊重考场工作人员、自觉接受监考人员的监督和检查，服从监考人员安排。
									</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">
									&nbsp;七、机器出现故障须举手询问，不得擅自处理；若违规操作造成相关设备损坏，应照价赔偿。
									</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">
									&nbsp;八、考试时间终止或提前交卷后，考试人员应按要求结束考试程序，退离考场。不得在考场附近逗留，喧哗。
									</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">
									&nbsp;九、任何与本场考试无关的人员，一律不得擅自进入考场。
									</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">
									&nbsp;十、考试结束，考生按考试要求结束考试程序，离开考场，提前结束考试退场者不得在考场内逗留喧哗。
									</p>								
									<p style="font-size:14px;margin:0 auto;text-align:left;line-height:20px;color:#333;border: none;">
									&nbsp;十一、考生应在考试前一天熟悉考场地址和交通路线。
									</p>
									<p style="line-height:5px;border: none;"></p>
								</td></tr>																															
							</table>
						</td>
					</tr>
			 	</table>
			 </div>
		 	<p style="page-break-after:always">&nbsp;</p>
			</c:forEach>
			<%
			}else {%>
			<div style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
				<div style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
					暂无数据！
				</div>
			</div>
			<%
			}%>	
			<input type="hidden" id="complete" value="ok"/>
	</body>
</html>
