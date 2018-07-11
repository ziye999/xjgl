<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!-- 项目根路径 -->
<c:set var="ProjectPath" value="${pageContext.request.contextPath}"></c:set>
<c:set var="ProjectPath" value="${pageContext.request.contextPath}"></c:set>
<!-- ${pageContext.request.contextPath } 项目根路径 ${ProjectPath}：/xjgl-->

<script type="text/javascript">
	var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
</script>

<input type="hidden" id="RootPath" value='<c:out value="${ProjectPath}"/>'>
<input type="hidden" id="ServerPath" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">  
  <head>
    <title>湖南省行政执法人员资格考试信息管理系统</title>
	<link rel="stylesheet" type="text/css" href='<c:out value="${ProjectPath}"/>/js/ext/resources/css/ext-all.css' />
	<link rel="stylesheet" type="text/css" href='<c:out value="${ProjectPath}"/>/css/icons.css' />
	<link rel="stylesheet" type="text/css" href='<c:out value="${ProjectPath}"/>/css/top.css' />
	<link rel="stylesheet" type="text/css" href='<c:out value="${ProjectPath}"/>/css/animated-dataview.css' />
	<link rel="stylesheet" type="text/css" href='<c:out value="${ProjectPath}"/>/css/ext_sty.css' />
	<link rel="stylesheet" type="text/css" href='<c:out value="${ProjectPath}"/>/css/Ext.ux.form.LovCombo.css' />
	
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext/adapter/ext/ext-base.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext/adapter/ext/ext-basex.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext/ext-all.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext/ext-lang-zh_CN.js'/></script>
	
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/util/Event.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Cookie.js'/></script>
	
	<!--  -->
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/util/JsHelper.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/util/MBspInfo.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.FormPanel.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.TreePanel.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.grid.ColumnModel.js'/></script>
	
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.grid.GridView.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.GridPanel.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.Window.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.TabScrollerMenu.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.TabCloseMenu.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/GroupSummary.js'/></script>
	
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/FileUploadField.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.Combox.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.DictCombox.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.ComboxTree.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.ComboxGrid.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.Button.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.form.TextField.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.form.XnxqField.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.form.KeMuField.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.form.OrganField.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.form.PointField.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.form.GradeClassField.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.form.LovCombo.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.ComboboxTree.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.TipsWindow.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.MzCombox.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.ZzmmCombox.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.WhcdCombox.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.ZflbCombox.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.FzdwCombox.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.form.VTypes.js'/></script>
	
	<!-- FusionCharts -->
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.ChartPanel.js'/></script>
	<!--报表 -->
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.AtGrid.PrintView.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.AtGrid.PrintView.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/LodopFuncs.js'/></script>
	
	<!-- 时间组件 -->
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Spinner.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/SpinnerField.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.form.TimePickerField.js'/></script>

	<script type='text/javascript' src='<c:out value="${ProjectPath}"/>/js/util/PublicClass.js'></script>
	<script type='text/javascript' src='<c:out value="${ProjectPath}"/>/js/util/MenuInfo.js'></script>
	
	<!-- Ext-ux -->
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/RowExpander.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.MyRowExpander.js'/></script>
	
	<!-- 桌面Potarl -->
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Portal.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/PortalColumn.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Portlet.js'/></script>
	<script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/ext-ux/Ext.ux.MaximizeTool.js'/></script>
	<script type="text/javascript">
	try {
		writeMyLsid('${username}','${username}','${pwd}');
	} catch (e) { 
	}
		var curWwwPath=window.document.location.href;
	    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	    var pathName=window.document.location.pathname;
	    var pos=curWwwPath.indexOf(pathName);
	    //获取主机地址，如： http://localhost:8083
	    var localhostPaht=curWwwPath.substring(0,pos);
	    //获取带"/"的项目名，如：/uimcardprj
	    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	    document.getElementById("ServerPath").value =  localhostPaht+projectName;
	</script>
	
	<style type="text/css">
		.div_font{
			color: red;
			font-size: 36px;
		}
	</style>
  </head>
  
  <body>
  
  <div id='header'>
  	<div class="ma_top"></div>
	<!--<div class="ma_t_l"></div>-->
  </div>
  <%@include file="template_base.jsp"%>
  <script type="text/javascript" src='<c:out value="${ProjectPath}"/>/js/main.js'/></script>  
  </body>
</html>
