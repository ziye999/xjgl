<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title></title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--<link rel="stylesheet" type="text/css" href="styles.css">-->
<style type="text/css">
table{
	border: none 0px; 
}
td{
	border: solid 1px; 
	text-align: center;
	background:#EAF4FD;
}
label{
	font-size: 14px;
	cursor: hand ;
}
</style>
<script type="text/javascript">
function winHide() {
	this.win.hide();window.parent.document.getElementById('portalif').src = "";
}
var thiz = this;
this.win = new window.parent.Ext.ux.Window({
			width:740,
			height:500,
			tbar:[ 
		  		"->",{xtype:"button",text:"取消",iconCls:"p-icons-checkclose",handler:function(){thiz.winHide()}}
		  		,"->",{xtype:"button",text:"保存",iconCls:"p-icons-save",handler:function(){
					var portalif = window.parent.document.getElementById('portalif');
					var portalifwin = portalif.window || portalif.contentWindow;
					portalifwin.save(thiz); // 调用iframe中的a函数			
		  		}}
			],
 			items: [{  
 				title: '座位调整', 
				header:false, 
				html : "<iframe id='portalif' style='border-top-width: 0px; border-left-width: 0px; border-bottom-width: 0px; width: 728px; height: 455px; border-right-width: 0px' src='./jsp/gridtest/portal.jsp?lcId="+
						thiz.lcid+"&kcid="+thiz.kcid+"&kcmc="+thiz.kcmc+"' frameborder='0' width='100%' scrolling='no' height='100%'></iframe>", 
				border:false 
			}],
			title:"考场座位调整"
});	
function changeOveColor(obj) {
	obj.style.background = "#DAE9F3";
}
function changeOutColor(obj) {
	obj.style.background = "#EAF4FD";
}
function openWin(lcid,kcid,kcmc) { 
	var thiz = this;
	this.lcid = lcid;
	this.kcid = kcid;
	this.kcmc = kcmc;
	this.win.items.itemAt(0).html = "<iframe id='portalif' style='border-top-width: 0px; border-left-width: 0px; border-bottom-width: 0px; width: 728px; height: 455px; border-right-width: 0px' src='"+
		"./jsp/gridtest/portal.jsp?lcId="+this.lcid+"&kcid="+this.kcid+"&kcmc="+this.kcmc+"' frameborder='0' width='100%' scrolling='no' height='100%'></iframe>"
	if(window.parent.document.getElementById('portalif')!= null) {
		window.parent.document.getElementById('portalif').src = "./jsp/gridtest/portal.jsp?lcId="+this.lcid+"&kcid="+this.kcid+"&kcmc="+this.kcmc;
	}
	this.win.show();
}
</script>
  </head>
  <%
    List<Map<String, Object>> dataList = (List<Map<String, Object>>)request.getAttribute("data");
    boolean flag = false;
    String bt = "";
    String fbt = "";
    String jxl = "";
    if(dataList != null && dataList.size() > 0) {
    	flag = true;
    	bt = dataList.get(0).get("BT").toString();
    	fbt = dataList.get(0).get("FBT").toString();
    	jxl = dataList.get(0).get("BUILDNAME").toString();
    }%>
  <body style='background:#fff;overflow-x:auto;' align=center>
  <%
  	if(!flag) {%>
  	<div style="width: 100%;height: 200px;margin-top: 40px;" align="center">没有查询到数据！</div>
  <%
  	}%>
  	<div id='bt' style="font-weight: bold;font-size: 24px;margin: 10px;width: 100%;" align="center"><%=bt %></div>
    <div id='fbt' style="font-size: 16px;margin-top: 10px;width: 100%;" align="center"><%=fbt %></div> 
  		<table style="margin: auto;" cellspacing=0>
  			<tr><th><%=jxl %></th></tr>
  		<%	if(dataList != null && dataList.size() > 0) {
  				String lcmc_m = "";
    			for(int i = 0;i < dataList.size(); i++) {
    				String lcmc = dataList.get(i).get("LC")==null?"":dataList.get(i).get("LC").toString();
    				String kcmc = dataList.get(i).get("KCMC")==null?"":dataList.get(i).get("KCMC").toString();
    				String jsmc = dataList.get(i).get("ROOMNAME")==null?"":dataList.get(i).get("ROOMNAME").toString();
    				String kcrs = dataList.get(i).get("ANRS")==null?"":dataList.get(i).get("ANRS").toString();
    				String khqz = dataList.get(i).get("KHQZ")==null?"":dataList.get(i).get("KHQZ").toString();
    				String lcid = dataList.get(i).get("LCID")==null?"":dataList.get(i).get("LCID").toString();
    				String kcid = dataList.get(i).get("KCID")==null?"":dataList.get(i).get("KCID").toString();    				
   					if(!lcmc_m.equals(lcmc)) {
   						if(i == 0) {%>
	   		<tr>
   		<%				}else {%>
	   		</tr>
	   		<tr>
   		<%				}%>
   				<td style="font-weight: bold;font-size: 20px;background:#DAE9F3;">
   					<%=lcmc%>
   				</td>
   		<%			}%>
    			<td style='width:120px;height:120px;cursor: hand ;' onclick="openWin('<%=lcid %>','<%=kcid %>','<%=kcmc %>')" onmousemove="changeOveColor(this)" onmouseout="changeOutColor(this)">
    				<input name="lcid" type="hidden" value="<%=lcid %>">
    				<input name="kcid" type="hidden" value="<%=kcid %>">
    				<label style="font-size: 22px;font-weight: bold;"><%=kcmc %></label><br>
    				<label><%=jsmc %>(<%=kcrs %>人)</label><br>
    				<label >考号起止：</label><br>
    				<label><%=khqz %></label><br>
    			</td>
    	<%			if(i == dataList.size()-1) {%>
			</tr>
		<%		}
    			lcmc_m = lcmc;
    		}
    	}%>
  	</table>
  </body>
</html>
