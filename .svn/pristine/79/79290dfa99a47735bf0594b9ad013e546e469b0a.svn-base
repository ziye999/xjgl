<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
<link rel="stylesheet" type="text/css" href="css/xindex.css"
	media="screen" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ymPrompt/skin/simple_gray/ymPrompt.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/ymPrompt/ymPrompt.js"></script>
	<link rel="stylesheet" type="text/css" href="css/shouye.css" media="screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/sel/js/area.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/sel/js/location.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/sel/js/select2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/sel/js/select2_locale_zh-CN.js"></script>

<link href="${pageContext.request.contextPath}/sel/css/common.css" rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/sel/css/select2.css" rel="stylesheet"/>

<style type="text/css">

.topdiv {
	margin-top: 20px;
	margin-left:10px;
	margin-right:10px;
	border: 1px solid #CCCCCC;
}
.title {
	/*border: 1px solid #E6EAEC;
	  width: 100%;
	*/
	background-color: #F5F5F5;
	padding-top:10px;
	padding-left:10px;
	height: 30px;
}
.title a {
	float: left;
}
a.message {
	 color: #FF0000;
}
</style>
<script type="text/javascript">
  </script>
  </head>
  
<body>
<div align="center">
<div class="page">
    <div class="top">	
      <div class="logo"></div>
      <div  class="clear" style="height: 0.1%;"></div>
	</div>
</div> 
   <div align="center" class="topdiv" style="width: 74%;">
		<div id="d_title" class="title">
			<label id="title">添加用户</label>
		</div>
		<div id="dForm" class="addcontent">
			<form 
				id="addForm" name="addForm" method="post">
				<input type="hidden" name="usercode" value="${map.user.USERCODE}" />
				<input type="hidden" name="cdate" value="${map.user.CDATE}" />
				<input type="hidden" name="nowPage" value="${nowPage}" />
				<table class="form_tab" width="100%">
					<tr>
						<td align="right" width="110"><label class="message">*</label> 账号类型：</td>
						<td>
							<select id="usertype" name="usertype" class="sel">
							</select>
						</td>
						
						<td align="right" width="110">
							<label class="message">*</label> 姓名：</td>
						<td>
							<input type="text" id="username" name="username"  maxlength="25" class="hy" />
						</td>
						
						<td rowspan="4" width="140">
							<div align="center">
								<img alt="头像" src="images/a.png" width="140" height="145" id="imghead">
							</div>
						</td>
						
					</tr>
					<tr>
						<td align="right"><label class="message">*</label>登录账号：</td>
						<td>
							<input type="text" name="loginid" id="loginid" maxlength="20"  class="hy" />
						</td>
						
						<td align="right"><label class="message">*</label>密码：</td>
						<td>
							<input type="password" name="loginpwd" id="loginpwd" maxlength="10" class="hy"/>
						</td>
						
					</tr>
					<tr>
						<td align="right" width="110">性别：</td>
						<td>
							<select id="sex" name="sex" class="sel">
							</select>
							
						</td>
						<td align="right">
							出生日期：</td>
						<td>
							<input type="text" name="csrq" id="csrq" class="hy" onclick="laydate()" readonly="readonly"/>
						</td>
						
					</tr>
					<tr>
						<td align="right"><label class="message">*</label> 省份：</td>
						<td colspan="3">
							<select id="loc_province" style="width:120px;">
						    </select>
						    <select id="loc_city" style="width:120px; margin-left: 10px">
						    </select>
						    <select id="loc_town" style="width:120px;margin-left: 10px">
						    </select>
						</td>
						
						
					</tr>
					<tr>
						<td align="right" width="110"><label class="message">*</label> E²ID：</td>
						<td> 
							<input type="text" id="e2id" name="e2id"  maxlength="20" class="hy" readonly="readonly"/>
						</td>
						<td align="right">微信号：</td>
						<td>
							<input type="text" id="wx" name="wx" class="hy" maxlength="25"/>
						</td>
						
						<td align="center" >
							<div style="margin-top:5px;width:120px;height: 40px;">
							<input type="button" name="uploadWinBt" value="上传头像" class="find_sub" />
							<input type="hidden" id="zpdz" />
							</div>
						</td>
					</tr>
					<tr>
						<td align="right" width="110">手机号码：</td>
						<td> 
							<input type="text" id="sjhm" name="sjhm"  maxlength="20" class="hy" />
						</td>
						<td align="right">电子邮件：</td>
						<td>
							<input type="text" id="dzyj" name="dzyj" class="hy" maxlength="50"/>
						</td>
						
						<td align="center" >
						</td>
					</tr>
					<tr>
						<td colspan="5" align="center"><label class="message" id="message"></label></td>
					</tr>
					<tr>
						<td colspan="5" align="center"><br>
							<input type="submit" class='find_sub' value="提交" id="subBt"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		       				<input class='find_sub' type="button" value="取消" onclick="window.location.href = '/zypj'"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
<div style="height: 2%;"></div>
<div class="bottom">
		<div class="bottom_nav">
        <a href="" target="_blank">用户须知</a>|<a href="" target="_blank">常见问题</a>|<a href="" target="_blank">关于我们</a>
    </div>
    	<p> Copyright © 2014湖南省电化教育馆   版权所有</p>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
<SCRIPT type="text/javascript">
	$(this).load(function(){
		initDroplist($("#usertype"),"ZD_USERTYPE");
		initDroplist($("#sex"),"ZD_XB");
		$("#e2id").val(getCookie("e2id"));
	});
	function initDroplist(selObj,tableName,isSel) {
		if(isSel == null || isSel == "") {
			isSel = "true";
		}
		$.post("droplist_getDroplist.do?tableName="+tableName+"&isSel="+isSel, function(data) {
			var jsonData = eval("("+data+")");
			for(var i = 0; i < jsonData.length; i++) {
				var dm = jsonData[i].DM;
				var mc = jsonData[i].MC;
				selObj.append( "<option value='"+dm+"'>"+mc+"</option>" );
			}
		});
	}
	$(function(){
		$(":button[name='uploadWinBt']").click(function(){
			ymPrompt.win({message:'./uploadzp.jsp',width:700,height:490,title:'照片上传',handler:function handler(){},maxBtn:true,minBtn:true,iframe:true})
			return false;
		});
		/*提交表单*/
			$("#subBt").click(function(){
				
				if($("#usertype").val()==""){
					$("#message").text("*请选择账号类型！");
					$("#usertype").focus();
					return false;
				}
				if($("#username").val()==""){
					$("#message").text("*请输入姓名！");
					$("#username").focus();
					return false;
				}
				if($("#loginid").val()==""){
					$("#message").text("*请输入登录账号！");
					$("#loginid").focus();
					return false;
				}
				if(logBool){
					$("#message").text("*登录账号已存在！");
					$("#loginid").focus();
					return false;
				}
				if($("#loginpwd").val()==""){
					$("#message").text("*请输入密码！");
					$("#loginpwd").focus();
					return false;
				}
				if(pwdformat){ 
					$("#message").text("*密码必须由6-16个英文字母和数字的字符串组成！"); 
					$("#loginpwd").focus();
					return false;
				}
				/**if($("#csrq").val()==""){
					$("#message").text("*请选择出生时间！");
					return false;
				}**/
				if($('#loc_town').val()==""){
					$("#message").text("请选择省份！");
					return false;
				}
				if($('#e2id').val()==""){
					$("#message").text("E²ID不能为空！");
					return false;
				}
				
				$.ajax({
					type : "POST",
					url : "user_addSupUser.do",
					data : {"usertype":$("#usertype").val(),"username":$("#username").val(),"loginid":$("#loginid").val(),"loginpwd":$("#loginpwd").val(),"loc_town":$("#loc_town").val(),
					"sex":$("#sex").val(),"csrq":$("#csrq").val(),"e2id":$("#e2id").val(),"wx":$("#wx").val(),"sjhm":$("#sjhm").val(),"dzyj":$("#dzyj").val(),"zpdz":$("#zpdz").val()},
					success : function(data){
						if(data == "true") {
							ymPrompt.alert('提交成功，请等待审核！',null,null,'提示',function () {window.location.href = "/zypj"})
						}
					}
				});
				return false;
			});
			/*验证登录账号是否存在*/
			var logBool=false;
			$(":text[name='loginid']").blur(function(){
				var loginid=$(this).val();
				$.ajax({
					type : "POST",
					url : "user_isLoginid.do",
					data : {"loginid":loginid},
					success : function(data){
						if(data=="true"){
							$("#message").text("*登录账号已存在！");
							logBool=true;
						}else{
							$("#hmessage").text("");
							logBool=false;
						} 
					}
				});
			});
			var pwdformat=false;
			$(":password[name='loginpwd']").blur(function(){
				//var reg=/^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$/g; 
				var reg=/^[A-Za-z0-9]{6,16}$/g; 
				var password=$(":password[name='loginpwd']").val();
				if(!reg.test(password)) { 
					$("#message").text("*密码必须由6-16个英文字母和数字的字符串组成！"); 
					$(":text[name='loginpwd']").focus();
					pwdformat=true;
				}else{
					$("#message").text("");
					pwdformat=false;
				}
				
			});
	});
	function setImg(url) {
		$("#zpdz").val(url);
		$("#imghead").attr("src",url);
	}
</SCRIPT>
</body>
</html>
