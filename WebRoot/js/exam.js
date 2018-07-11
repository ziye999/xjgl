var zbjts = 0;

var flag = 1;
setInterval("dssj()", 1000);

init(); 

function init(){
	$.ajax({
		type : 'get',
		url : "http://10.1.17.73/fzzxks/userOpt/initPage.pp",
		dataType : 'jsonp',
		jsonp : 'callback',
		data : {"xsid" : xsid},
		jsonpCallback : "successCallback",
		success : function(data) {  
			if(data.succ==1){
				for(var key in data.pa.answerInfos) {
					var d = data.pa.answerInfos[key].split('##');
					if(data.pa.answerInfos[key].indexOf('##')>=0){
						var d =data.pa.answerInfos[key].split('##');
						for ( var i = 0; i < d.length; i++) {
							var daNum = document.getElementById(key+'_'+d[i]).name.substr(2);
							document.getElementById(key+'_'+d[i]).checked=true;
							document.getElementById(daNum).style.background = "#b3e3f1";
						}
					} else{
						var daNum = document.getElementById(key+'_'+data.pa.answerInfos[key]).name.substr(2);
						document.getElementById(key+'_'+data.pa.answerInfos[key]).checked=true;
						document.getElementById(daNum).style.background = "#b3e3f1";
					} 
					kssj = data.pa.sysj; 
				}  
			} 
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) { 
		}
	});
}

function dssj() {
	if (kssj <= 0) {
		if(flag == 1){
			alert('考试时间到,自动提交试卷!'); 
			submitExamDa();
		}
	} else {
		kssj--;
		document.getElementById("sysj").innerHTML = parseInt(kssj / 60) + ':'
				+ parseInt(kssj % 60);
	}
}

function toTop() {
	document.getElementsByTagName("BODY")[0].scrollTop = 0;
}

function toBottom() {
	document.getElementsByTagName("BODY")[0].scrollTop = document
			.getElementsByTagName("BODY")[0].scrollHeight;
}
// #b3e3f1
// #ffcc66
// #aeaeae
function setBj(obj) {
	if(flag==1){
		var daType = obj.id.substr(0, 2);
		var daNum = obj.id.substr(2);
		if (document.getElementById(daNum).style.backgroundColor != 'rgb(179, 227, 241)') {
			if (obj.checked) {
				document.getElementById(daNum).style.background = "#ffcc66";
				zbjts++;
			} else {
				document.getElementById(daNum).style.background = "#aeaeae";
				zbjts--;
			}
			document.getElementById("zbj").innerHTML = zbjts;
		} else {
			obj.checked = false;
		}
	}
}

function setDa(obj) {
	if(flag==1){
		var daType = obj.name.substr(0, 2);
		var daNum = obj.name.substr(2);
		var tmid = obj.value.substr(0, obj.value.indexOf('_'));
		var da = '';
		if (obj.type == 'radio') {
			if (obj.checked) {
				document.getElementById(daNum).style.background = "#b3e3f1";
				if (document.getElementById("bj" + daNum).checked) {
					document.getElementById("bj" + daNum).checked = false;
					zbjts--;
					document.getElementById("zbj").innerHTML = zbjts;
				}
			}
			da = obj.value.substr(obj.value.indexOf('_') + 1);
		} else if (obj.type == 'checkbox') {
			var cbs = document.getElementsByName(obj.name);
			var flag = true;
			for ( var i = 0; i < cbs.length; i++) {
				if (cbs[i].checked) {
					da = da + '##'
					+ cbs[i].value.substr(cbs[i].value.indexOf('_') + 1);
					flag = false;
				}
			}
			if (flag) {
				if (document.getElementById("bj" + daNum).checked) {
					document.getElementById("bj" + daNum).checked = false;
					zbjts--;
					document.getElementById("zbj").innerHTML = zbjts;
				}
				document.getElementById(daNum).style.background = "#aeaeae";
			} else {
				da = da.replace('##',''); 
				document.getElementById(daNum).style.background = "#b3e3f1";
				if (document.getElementById("bj" + daNum).checked) {
					document.getElementById("bj" + daNum).checked = false;
					zbjts--;
					document.getElementById("zbj").innerHTML = zbjts;
				}
			}
		}
		$.ajax({
			type : "post",
			dataType : 'jsonp',
			url : "http://10.1.17.73/fzzxks/subDa.pp",
			data : {
				"tmid" : tmid,
				"da" : da,
				"xsid" : xsid,
				"paperId":paperId,
				"sysj":kssj
			},
			success:function(data){
				writeMyAnswer(xsid, tmid, da); 
			},
			error:function(){
				writeMyAnswer(xsid, tmid, da); 
			}
		}); 
	}
}

function dingwei(n) {
	window.location.hash = "#" + n;
}

function submitExamDa() {
	alert('試卷提交成功!祝您心情愉快');
	if(flag==1){
		flag = 0;
		document.getElementById("tjexamda").disabled  = true;
		$.ajax({
			type : 'get',
			url : "http://10.1.17.73/fzzxks/userOpt/addExamDA.do",
			dataType : 'jsonp',
			jsonp : 'callback',
			data : $("#examForm").serialize(),
			jsonpCallback : "successCallback",
			success : function(data) {
				submitAnswer(xsid);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) { 
				submitAnswer(xsid);
			}
		});
	}
	
}
