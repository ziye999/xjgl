<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.jiajie.util.StringUtil"%>
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
	System.out.print(basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><%=basePath %></title>
		<meta http-equiv="Content-Language" content="zh-cn" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />		
		<link rel="stylesheet" href="<%=basePath %>css/Print.css" type="text/css"/>
		<style type="">
			.withoutTable{
				border-top:0px;
				border-left:0px;
				border:solid #000000 1px;
				width:800px;
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
	<body>
	<%if(list==null || list.size()==0){ %>
		<div style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
			<div  style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
				暂无数据！
			</div>
		</div>
	<%}else{ %>
		<div pagetitle="pagetitle" style="font-size:18px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
			<div id="table_title" style="margin-left:auto;margin-right:auto;margin-top:0px;text-align:center;">
				湖南省行政执法人员资格电子化考试<br/>
				${objDz.DZ}<br/>
				考试门贴
				<!--%=list.size()人%--> 				
			</div>
		</div>
		<table style="border: none;"><tr style="border: none;"><td height="1px" style="border: none;"></td></tr></table>
		<table align="center" id="table_body" class="withoutTable"> 
		<input value="<%=list.size()%>" id="count" style="display: none;"/>
			<tr>
		<%
		//当前考场排到了多少号考生：当这个数字为%3=0时，需要换行
		int kcNum=0;
		int zwhI = 0;
		int nrH = 85;
		
		for(int i=0;i<list.size();i++){
			//当前考生号增加1
			kcNum++;
			Map<String, Object> objmap = list.get(i);
			String xm = objmap.get("XM")==null?"":objmap.get("XM").toString();
			String kscode = objmap.get("KSCODE")==null?"":objmap.get("KSCODE").toString();
			String sfzjh = objmap.get("SFZJH")==null?"":objmap.get("SFZJH").toString();
			String sj = objmap.get("SJ")==null?"":objmap.get("SJ").toString();
			String zwhStr = objmap.get("ZWH")==null?"":objmap.get("ZWH").toString();
			String nextZwh = "";
			//看下一个考生号是否为1，如果是，则要新起一页
			if(i<(list.size()-1)) {
				Map<String, Object> objmap1 = list.get(i+1);
				nextZwh = objmap1.get("ZWH")==null?"":objmap1.get("ZWH").toString();
			}
			%>
				<td style="text-align: center;" valign="middle" width="300px" height="<%=nrH%>px">  
					<div align="center"><font style="font-size:18px;font-weight:bold;"><%=xm%></font></div>
					<div align="left">&nbsp;&nbsp;&nbsp;&nbsp;考生考号：<%=kscode%></div>
					<div align="left">&nbsp;&nbsp;&nbsp;&nbsp;身份证号：<%=sfzjh%></div>
					<div align="left">&nbsp;&nbsp;&nbsp;&nbsp;<%="".equals(sj)?"":"考试时间："+sj%></div>
					<div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%="".equals(zwhStr)?"":"座位号："+zwhStr%></div>
				</td>		
				
		<%	
		//是否要换行，用当前考生号除3
		if((kcNum)%3==0){
			%></tr><tr><%
			//用空格补全
			}else if(nextZwh.equals("1") || i==list.size()-1){
				for(int j=0;j<3-((kcNum)%3);j++){%>
				<td></td>
			<%	}
			}
			//是否要换页，条件是下一个考生号为1，或已经满27人（每页最多27人）
			if ((nextZwh.equals("1") || (kcNum)%27==0) && i > 1) {
				if(nextZwh.equals("1")){
					kcNum=0;
				}
			%>
			</tr></table>
			<%if (i<list.size() && i>0) {%>
			<p style="page-break-after:always">&nbsp;</p>
			<div pagetitle="pagetitle" style="font-size:18px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 				
				<div id="table_title" style="margin-left:auto;margin-right:auto;margin-top:0px;text-align:center;">
					湖南省行政执法人员资格电子化考试<br/>
					${objDz.DZ}<br/>
					考试门贴							
				</div>
			</div>
			<table style="border: none;"><tr style="border: none;"><td height="1px" style="border: none;"></td></tr></table>
			<table align="center" id="table_body" class="withoutTable" ><tr>
			<%}
			}
			zwhI = Integer.valueOf(zwhStr).intValue();
		}%> 
			</tr>   
		</table>
		
	<%}%>
		<input type="hidden" id="complete" value="ok"/>	
	</body>
</html>