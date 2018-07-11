<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Portal Layout Sample</title>
	<link href="<%=basePath%>jsp/css/ext-theme-classic-all.css" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath%>jsp/css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>jsp/gridtest/portal.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/icons.css" />
    <!-- GC -->
    <!-- <x-compile> -->
    <!-- <x-bootstrap> -->
   	<script src="<%=basePath%>jsp/js/ext-all-debug-w-comments.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/util/PublicClass.js" type="text/javascript"></script>    
    <script src="<%=basePath%>jsp/js/ext-lang-zh_CN.js" type="text/javascript"></script>
    <!-- </x-bootstrap> -->
    <!-- shared example code -->
    <script type="text/javascript" src="<%=basePath%>jsp/gridtest/classes/examples.js"></script>
    <script type="text/javascript">
        Ext.Loader.setPath('Ext.app', 'classes');
        var lcid = getLocationPram("lcid");
        var kcid = getLocationPram("kcid");
        var kcmc = getLocationPram("kcmc");
    </script>
    <script type="text/javascript" src="<%=basePath%>jsp/gridtest/portal.js"></script>
    <script type="text/javascript">
        Ext.require([
            'Ext.layout.container.*',
            'Ext.resizer.Splitter',
            'Ext.fx.target.Element',
            'Ext.fx.target.Component',
            'Ext.window.Window',
            'Ext.app.Portlet',
            'Ext.app.PortalPanel',
            'Ext.app.PortalDropZone',
            'Ext.app.KsStore'
        ]);
        
		var resultMap = new Ext.util.HashMap();		
        Ext.onReady(function(){	        
        	var portal = Ext.create('Ext.app.Portal');
        });        
    </script>
    <!-- </x-compile> -->
    <style type="text/css">
    	.zwtdlabelname{
    		font-size: 18px;
    		font-weight: bold;
    	}
    	.zwtdlabel{
    		font-size: 13px;
    	}
    </style>
</head>
<script type="text/javascript">
function save(thiz) {
	var apidsDoc = document.getElementsByName("apid_sys");	
	var array = new Array();
	for(var i=0;i < apidsDoc.length;i++) {
		var obj = new Object();
		obj.apid = apidsDoc[i].value;
		obj.xys = apidsDoc[i].parentNode.previousSibling.value;  
		array.push(obj);
	}	
	Ext.Ajax.request({
       	url: 'subExamRoomArrange_updataKcStu.do?arrayData='+JSON.stringify(array),
        method: 'GET',
        async: false, 
        success: function (response, options) {
			Ext.MessageBox.alert('提示', '考场座位调整保存成功！',function() {
				thiz.win.hide();
			    thiz.winHide();
		  	});
      	},
        failure: function (response, options) {
        	Ext.MessageBox.alert('提示', '请求超时或网络故障！');
      	}
  	});		
}
</script>
<body>
    <span id="app-msg" style="display:none;"></span>
</body>
</html>
