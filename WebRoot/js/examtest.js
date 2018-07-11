var zbjts = 0;
var flag2 = true;
var yds = '';
var nowtime=1;
var initFlag = 0;
var sdf = 0;
var yjfda = [];
var wdaNums = []; 
setInterval("dssj()", 1000); 

if(getCookie('SYSSESSIONID')==null){
	window.location.href='login.html';
}   

if(getCookie('DESUR')!=null && getCookie('DESUR')!=xsid){
	var urpage = getCookie('DESUR');
	window.location.href='login.html';
} else{ 
	var myDate=new Date();
	myDate.setFullYear(2050,1,1);
	document.cookie = 'DESUR='+xsid+';expires='+myDate.toGMTString()+'; path=/';
}

var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?772081addee3ad8c904d571fe62cb20e";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s); 
})();

document.onkeydown = function (e) {
		var ev = window.event || e;
		var code = ev.keyCode || ev.which;
		if (code == 116) {
		ev.keyCode ? ev.keyCode = 0 : ev.which = 0;
		cancelBubble = true;
		return false;
	}
}

var omitformtags=["input", "textarea", "select"]

omitformtags=omitformtags.join("|")

function disableselect(e){ 
if (omitformtags.indexOf(e.target.tagName.toLowerCase())==-1) 
return false 
}

function reEnable(){ 
return true 
}

if (typeof document.onselectstart!="undefined") 
document.onselectstart=new Function ("return false") 
else{ 
document.onmousedown=disableselect 
document.onmouseup=reEnable 
} 


function todoExam(){ 
	var todo = '';
	while(true){
		todo =  urllist[parseInt(200*Math.random())];
		if(todo!=xsid){
			break;
		}
	}
	
	location.href = todo+'.html?'+new Date().getTime();
	
}
var zdtj = true;
function dssj() {
	if(flag2){
		if (kssj <= 0) { 
			if(zdtj){
				zdtj=false;
				submitExamDa(); 
			}
		} else {
			kssj--;
			if(timelength*60-kssj==1800){ 
				document.cookie = 'DESUR='+';expires='+new Date().toGMTString()+'; path=/';
			}
			if(kssj==900){
				SAlert('离考试结束还有15分钟');
				$('#sysj').css('color','red');
			}
			if(document.getElementById("sysj")!=null){
				document.getElementById("sysj").innerHTML = parseInt(kssj / 60) + ':'
				+ parseInt(kssj % 60);
			}
		}
	}
	
	 
}
 

function toTop() {
	window.location.hash = "#pageTop";
}

function toBottom() {
	window.location.hash = "#pageButtom";
}
// #b3e3f1
// #ffcc66
// #aeaeae
function setBj(obj) {
	if(flag2){
		var daNum = obj.id.substr(2);
		if (obj.checked) {
			document.getElementById(daNum).style.background = "#ffcc66";
			zbjts++;
		} else {
			zbjts--;
			var cbs = document.getElementsByName("da"+daNum); 
			var flag = false;
			for ( var i = 0; i < cbs.length; i++) {
				if (cbs[i].checked) { 
					flag = true;
				}
			}
			if(flag){
				document.getElementById(daNum).style.background = "#b3e3f1";
			}else{
				document.getElementById(daNum).style.background = "#aeaeae";
			}
		}
		document.getElementById("zbj").innerHTML = zbjts;
	}
}


function setst(vtl) {   
	document.getElementById(vtl).click();
}

function setDa(obj) {  
    var score = 1;
	if(flag2){
		var daNum = obj.name.substr(2);
		$("#zq"+daNum).show();
		var tmid = obj.value.substr(0, obj.value.indexOf('_'));  
		var da = '';
		if (obj.type == 'radio') {
			if (obj.checked) {
				if(yds.indexOf(daNum)==-1){
					ydts++;
					wdts--;
					yds = yds+daNum+'#';
				} 
				if(document.getElementById(daNum).style.background.indexOf('rgb(255, 204, 102)')==-1 && document.getElementById(daNum).style.background.indexOf('#ffcc66')==-1){
					document.getElementById(daNum).style.background = "#b3e3f1";
				}
			}
			da = obj.value.substr(obj.value.indexOf('_') + 1);
		} else if (obj.type == 'checkbox') {
			score = 2;
			var cbs = document.getElementsByName(obj.name);
			var flag = true;
			if(obj.checked){
				for ( var i = 0; i < cbs.length; i++) {
					if (cbs[i].checked) {
						da = da + '##'
						+ cbs[i].value.substr(cbs[i].value.indexOf('_') + 1);
						if(cbs[i]!=obj){
							if(flag){
								flag = false;
							}
						}
					}
				} 
			}else{
				for ( var i = 0; i < cbs.length; i++) {
					if (cbs[i].checked) {
						da = da + '##'
						+ cbs[i].value.substr(cbs[i].value.indexOf('_') + 1);
						flag = false;
					}
				} 
			}
			if (flag) {
				if(obj.checked){
					if(document.getElementById(daNum).style.background.indexOf('rgb(179, 227, 241)')==-1){
						ydts++;
						wdts--;
						yds = yds+daNum+'#';
					}
					if(document.getElementById(daNum).style.background.indexOf('rgb(255, 204, 102)')==-1 && document.getElementById(daNum).style.background.indexOf('#ffcc66')==-1){
						document.getElementById(daNum).style.background = "#b3e3f1";
						
					} 
				}else{
					if(document.getElementById(daNum).style.background.indexOf('rgb(174, 174, 174)')==-1){
						ydts--;
						wdts++;
						yds = yds.split(daNum+'#').join('');
					}
					if(document.getElementById(daNum).style.background.indexOf('rgb(255, 204, 102)')==-1 && document.getElementById(daNum).style.background.indexOf('#ffcc66')==-1){
						document.getElementById(daNum).style.background = "#aeaeae";
					} 
				}  
			}
		}
		 
		$("#fydts").html(ydts);
		$("#fwdts").html(wdts);
		
		var wd = da.split('##').join(''); 
		
		if(damap[tmid]==wd){ 
			if($.inArray(tmid, yjfda)==-1){
				sdf = sdf +score;
				yjfda.push(tmid);  
			}
			
			var ind = $.inArray(daNum,wdaNums);  
			if(ind!=-1){ 
				wdaNums.splice($.inArray(daNum,wdaNums),1);
			}
		} else{ 
			var inx = $.inArray(tmid, yjfda);
			if(inx>=0){
				yjfda.splice(inx,1);
				sdf = sdf - score;
			} 
			var idx = $.inArray(daNum,wdaNums); 
			if(idx==-1){
				wdaNums.push(daNum);
			}
		} 
	} 
}

function dingwei(n) { 
	window.scroll(0,$('#'+n).offset().top-120); 
}

function SAlert(str,type){
	var dh = $(window).width()/2-180; 
	var hh = $(window).height()/2+$(window).scrollTop()-50;  
	var m = 'alertOk()';
	if(type=='todo'){
		m = 'alertTodo()';
	}
	document.getElementById("light").innerHTML = str+"<br><br><br><div style='text-align: center;width:100%;'><button width='80px' onclick='"+m+"'>确 定</button></td></tr></div>"; 
	$('#light').css('top', hh+'px').css('left',dh+'px').show(); 
	$('#fade').show();    
}

function confirmAlert(str){
	var dh = $(window).width()/2-180; 
	var hh = $(window).height()/2+$(window).scrollTop()-50;   
	document.getElementById("light").innerHTML = str+"<br><br><br><div style='text-align: center;width:100%;'><button width='80px' onclick='confirmOk()'>确 定</button><button width='80px' onclick='confirmCancle()'>取 消</button></td></tr></div>"; 
	$('#light').css('top', hh+'px').css('left',dh+'px').show(); 
	$('#fade').show(); 
}

function confirmCancle(){
	$('#light').hide();
	$('#fade').hide();
}

function alertOk(){
	$('#light').hide();
	$('#fade').hide(); 
}

function alertTodo(){
	$('#light').hide();
	$('#fade').hide(); 
	confirmAlert("确定提交！");
}

function confirmOk(){ 
	$('#light').hide();
	$('#fade').hide();  
	$(".rightAnswer").show();
	for ( var i = 0; i < wdaNums.length; i++) {
		$("#"+wdaNums[i]).css("background-color","red");
	} 
	document.getElementById("score").innerHTML ="得分:"+sdf;
	$("#todoExam").show();
	$("input[type='checkbox']").attr("disabled","disabled");
	$("input[type='radio']").attr("disabled","disabled");
	$("#tjexamda").attr("disabled","disabled");
	flag2 = false;
}

function submitExamDa() { 
	if(flag2){ 
		if(timelength*60-kssj<=1800){
			SAlert("考试半个小时后才能提交试卷！",'ok');
			return "";
		} 
		SAlert("您总计做了"+ydts+"题目，未完成"+wdts+"！<br>本次模拟考试所得分：<font color='red'>"+sdf+'</font>','todo');  
	}
}

function quit(){ 
	location.href = 'login.html';
}
