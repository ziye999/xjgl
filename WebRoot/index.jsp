<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.FileInputStream"%>
<!-- 项目根路径 -->
<c:set var="ProjectPath" value="${pageContext.request.contextPath}"></c:set>
<input type="hidden" id="RootPath" value='<c:out value="${ProjectPath}"/>'>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>湖南省行政执法人员资格考试信息管理系统</title>
	<script type='text/javascript' src='js/util/PublicClass.js'></script>
	<script type='text/javascript' src='js/util/MenuInfo.js'></script>
	<!-- <link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css"/>  
	<script src="js/ext/adapter/ext/ext-base.js"></script>
	<script src="js/ext/ext-all.js"></script> -->
  </head>
 <body>
<h1>框架说明：</h1>
<%
	String fileName = request.getSession().getServletContext().getRealPath("") + "\\开发说明文档\\框架说明.txt";  
    String fileContent = "";     
    try   
    {       
        File f = new File(fileName);      
        if(f.isFile()&&f.exists())  
        {       
            InputStreamReader read = new InputStreamReader(new FileInputStream(f),"UTF-8");       
            BufferedReader reader=new BufferedReader(read);       
            String line;       
            while ((line = reader.readLine()) != null)   
            {        
                fileContent += line+"</br>";
            }         
            read.close();      
        }     
    } catch (Exception e)   
    {         
        e.printStackTrace();     
    }     
    out.print(fileContent);
 %>
 </br>
 </br>
 <h1>功能列表：</h1>
 <div id="menu"></div> 
	<script type="text/javascript">
		var keyArray = menuMap.keyArray();
		var htmlStr="";
		for(var i=0;i < keyArray.length;i++) { 
			htmlStr += "<a title='"+menuMap.get(keyArray[i])+"' href='jsp/main.jsp?module="+keyArray[i]+"'>jsp/mainin.jsp?module="+keyArray[i]+"("+menuMap.getName(keyArray[i])+")</a></br>";
		}
		document.getElementById("menu").innerHTML=htmlStr;	
		
	</script>

  </body>
</html>
