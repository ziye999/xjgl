Ext.form.VTypes = function(){
   	var postalcode = /^[0-9]{6}$/;
    var email = /^(\w+)([-+.][\w]+)*@(\w[-\w]*\.){1,5}([A-Za-z]){2,4}$/;
    var phone= /^\d{3,4}-\d{7,8}(-\d{3,4})?$/;
    var idcode =/^\d{15}|\d{18}$ / ;
	var mobile  =/^0*(13|15|18)\d{9}$/ ;
    return {
        'email' : function(value,field){
            return email.test(value,field);
        },
        'emailText' : '请输入email格式,<br>如： "qjemp@qjwxsoft.com.cn"',
        'postalcode':function(value,field){
        	return postalcode.test(value);
        },
        'postalcodeText':"邮政编码格式为6位数字,</br>如：000009",
        'phone':function(value,field){
        	return phone.test(value);
        },
        'phoneText':"请输入电话格式 <br>如：010-12345678-800",
        'idcode':function(value,field){
        	return idcode.test(value)
        },
        'idcodeText':"请输入15-18位数的身份证号",
        'mobile':function(value,field){
        	return mobile.test(value)&&value.length<=18;
        },
        'mobileText':"请输入正确手机号码,<br>以13,15,18开头",
        'password' : function(val, field) {        	
	        if (field.initialPassField) {	        	
	            var pwd = Ext.getCmp(field.initialPassField);
	            return (val == pwd.getValue());
	        }
	        return true;
	    },
	    'passwordText':'密码不一致!'
    };
}();