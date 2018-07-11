<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.jiajie.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
// System.out.println(map);
String riqiss = "",kaochangss="";
List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("list");
for(Map<String, Object> riqimap:list){
	String riqi=(String)riqimap.get("EXAMDATE");
	if(StringUtils.indexOf(riqiss,riqi)<0){
		riqiss+=riqi+",";
	}
	String changci = ""+riqimap.get("KCBH")+"考场<br/>"+riqimap.get("BUILDNAME")+riqimap.get("ROOMNAME")+"<br/>考生人数："+riqimap.get("STUCOUNT");
	if(StringUtils.indexOf(kaochangss,changci)<0){
		kaochangss+=changci+",";
	}
}
String[] riqis = StringUtils.split(riqiss, ",");
String[] kaochangs = StringUtils.split(kaochangss, ",");
Map<String,String> starttimesmap = new HashMap<String,String>();
for(String riqi:riqis){
	for(Map<String, Object> riqimap:list){
		String starttime = ""+riqimap.get("STARTTIME");
		String riqitemp=""+riqimap.get("EXAMDATE");
		if(StringUtils.equals(riqi, riqitemp)){
			String temp = starttimesmap.get(riqi);
			if(temp==null){
				starttimesmap.put(riqi, starttime+",");
			}else{
				if(StringUtils.indexOf(temp,starttime)<0){
					starttimesmap.put(riqi, temp+starttime+",");					
				}
			}
		}
	}
// System.out.println("starttimes:"+starttimesmap.get(riqi));
}
// System.out.println("riqis:"+riqis);
// System.out.println("kaochangs:"+kaochangs); 
%>
<body style="font-size:12px">
	<%if(list==null || list.size()==0){ %>
	<div style="width:<%=width %>;font-size:20px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
		<div  style="width:<%=width %>;margin-left:auto;margin-right:auto;text-align:center;">
			暂无数据！
		</div>
	</div>
	<%}else{ %>
	<div pagetitle="pagetitle" style="font-size:18px;font-weight:bold;margin-left:auto;margin-right:auto;text-align:center;"> 
		<div id="table_title" style="margin-left:auto;margin-right:auto;text-align:left;">
			${obj.XNMC }年度${obj.XQMC }${obj.EXAMNAME }${obj.POINTNAME }监考安排表
		</div>
	</div>
	<table align="center" id="table_body" class="withoutTable"> 
		<thead>
			<tr>
				<td rowspan="2">
				考场\场次
				</td> 
				<%for(String riqi:riqis){ %>				
				<td colspan="<%=starttimesmap.get(riqi).split(",").length%>">
				<%=riqi %>
				</td> 
				<%} %>
			</tr> 
			<tr>
			<%for(String riqi:riqis){ 
				String[] starttimes =  starttimesmap.get(riqi).split(",");
				for(int i=1;i<=starttimes.length;i++){%>
				<td>
				第<%=i %>场
				</td> 
				<%}
			}%>
			</tr>
		</thead>
		<tbody>
		<%
		for(String kaochang:kaochangs){%>
			<tr height="30px">
				<td style="text-align: center;" valign="middle" width="100px">
				<%=kaochang %>
				</td>
			 	<%for(String riqi:riqis){ 
					String[] starttimes =  starttimesmap.get(riqi).split(",");
					for(int i=0;i<starttimes.length;i++){
						String teachers="";
						for(Map<String, Object> riqimap:list){
							String changcitemp = ""+riqimap.get("KCBH")+"考场<br/>"+riqimap.get("BUILDNAME")+riqimap.get("ROOMNAME")+"<br/>考生人数："+riqimap.get("STUCOUNT");
							String riqitemp=(String)riqimap.get("EXAMDATE");
							String starttime = ""+riqimap.get("STARTTIME");
							String TEANAME = ""+ riqimap.get("TEANAME");
							if(StringUtils.equals(kaochang, changcitemp)&&StringUtils.equals(riqi, riqitemp)&&StringUtils.equals(starttimes[i], starttime)){
								if(StringUtils.equals("1", ""+riqimap.get("MAINFLAG")))
									TEANAME="<font style=\"font-size:12px;font-weight:bold;\">"+TEANAME+"</font>";
								teachers+=TEANAME+",";
							}
						}
						teachers=StringUtils.stripEnd(teachers, ",");%>
				<td style="text-align: center;" valign="middle" width="100px">
				<%=teachers %>
				</td> 
				<%}
			}%>
			</tr>  
		</tbody> 
		<%}%>
	</table>
	<%} %>
	<input type="hidden" id="complete" value="ok"/>
</body>
</html>
