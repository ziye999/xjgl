Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
	    this.initDate();
		this.initComponent();
		this.initListener();
		this.initFace();
		this.initQueryDate();
    },
    /** 初始化页面、内存等基本数据 **/
    initDate:function(){
    	
    },
    
	 /** 对组件设置监听 **/
    initListener:function(){
    },
   
   	initComponent :function(){
		//搜索区域
//		var cx = new Ext.Button({cls:"base_btn",text:"查询",handler:this.selectExamInfomation,scope:this});
//		var cz = new Ext.Button({cls:"base_btn",text:"重置",handler:function(){Ext.getCmp('search_form').getForm().reset()},scope:this});
		var imagebox = new Ext.BoxComponent({autoEl: {tag: 'img',src: '../img/basicsinfo/mrzp_img.jpg',id:"imgPath"},id:"imgBox",width:110,height:130});
		this.baseShow = new Ext.form.FormPanel({
		       region: "center",
		       height:360,
		       
		       items:[{  
		         layout:'form',  
		         xtype:'fieldset',
		         width:1090,
		         style:'margin:10 10',
		         title:'考生个人基础信息',  
		         items: [
                    {
                    	xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 4
							}, 
						baseCls:"table",
						items:[
							{html:"身份证件号：",baseCls:"label_right",width:212},
							{items:[{id:"sfzjh",xtype:"label",text:""}],baseCls:"component",width:318},
							{html:"照片：",baseCls:"label_right",width:212,rowspan:4},
							{items:[imagebox],baseCls:"component",width:318,rowspan:4},
							{html:"身份证件类型：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"sfzjlx"}],baseCls:"component",width:318},							
							{html:"国籍/地区：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"gjdq"}],baseCls:"component",width:318},
							{html:"港澳台侨：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"gatq"}],baseCls:"component",width:318},
							{html:"性别：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"xb"}],baseCls:"component",width:318},
							{html:"姓名：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"xm"}],baseCls:"component",width:318},
							{html:"出生日期：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"csrq"}],baseCls:"component",width:318},
							{html:"户口所在地：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"hkszd"}],baseCls:"component",width:318},
							{html:"籍贯：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"jg"}],baseCls:"component",width:318},
							{html:"民族：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"mz"}],baseCls:"component",width:318},
							{html:"政治面貌：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"zzmm"}],baseCls:"component",width:318},
							{html:"健康状况：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"jkzk"}],baseCls:"component",width:318}							
							] 
                    }]  
		       },
		       {  
		         layout:'form',  
		         xtype:'fieldset', 
		         width:1090,
		         style:'margin:10 10 10 10',
		         title:'考生个人考试信息',  
		         items: [
                    {
                    	xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 4
							}, 
						baseCls:"table",
						items:[
							{html:"考号：",baseCls:"label_right",width:212},
							{items:[{id:"kh",xtype:"label",text:""}],baseCls:"component",width:318},
							{html:"参考单位：",baseCls:"label_right",width:212},
							{items:[{id:"school",xtype:"label",text:""}],baseCls:"component",width:318},
							{html:"等级：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"nj"}],baseCls:"component",width:318},
							{html:"科目：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"bj"}],baseCls:"component",width:318},
							{html:"联系方式：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"lxfs"}],baseCls:"component",width:318},
							{html:"家庭住址：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"jtzz"}],baseCls:"component",width:318},
							{html:"考试轮次：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"kslc"}],baseCls:"component",width:318},
							{html:"考试批次：",baseCls:"label_right",width:212},
							{items:[{xtype:"label",text:"",id:"kskm"}],baseCls:"component",width:318}
							] 
                    }]  
		       }
		       ]  
	    	})

	},
    /** 初始化界面 **/
    initFace:function(){
//    	this.addPanel({layout:"border",items:[this.baseShow,this.otherShow]});
    	this.addPanel({layout:"border",items:this.baseShow});
    },
    initQueryDate:function(){
    	var sfzjh = getLocationPram("sfzjh");
    	Ext.Ajax.request({             
    		url :'sign_getSFZ.do',//调用后台对于controller的shenHeQuanBuTongGuoXueLiShuJv'方法             
    		success: function(response,options){
    				var respText = Ext.util.JSON.decode(response.responseText); 
    				Ext.getCmp("gjdq").setText(respText.msg.GJDQH==undefined?"":respText.msg.GJDQH);
    				Ext.getCmp("gatq").setText(respText.msg.GATQ==undefined?"":respText.msg.GATQ);
    				Ext.getCmp("sfzjlx").setText(respText.msg.SFZJLX==undefined?"":respText.msg.SFZJLX);
    				Ext.getCmp("sfzjh").setText(respText.msg.SFZJH==undefined?"":respText.msg.SFZJH);
    				Ext.getCmp("xb").setText(respText.msg.XB==undefined?"":respText.msg.XB);
    				Ext.getCmp("xm").setText(respText.msg.XM==undefined?"":respText.msg.XM);
    				Ext.getCmp("csrq").setText(respText.msg.CSRQ==undefined?"":respText.msg.CSRQ);
    				Ext.getCmp("hkszd").setText(respText.msg.HKSZD==undefined?"":respText.msg.HKSZD);
    				Ext.getCmp("jg").setText(respText.msg.JG==undefined?"":respText.msg.JG);
    				Ext.getCmp("mz").setText(respText.msg.MZ==undefined?"":respText.msg.MZ);
    				Ext.getCmp("zzmm").setText(respText.msg.ZZMM==undefined?"":respText.msg.ZZMM);
    				Ext.getCmp("jkzk").setText(respText.msg.JKZK==undefined?"":respText.msg.JKZK);
    				Ext.getCmp("kh").setText(respText.msg.KSCODE==undefined?"":respText.msg.KSCODE);
    				Ext.getCmp("school").setText(respText.msg.XXMC==undefined?"":respText.msg.XXMC);
    				Ext.getCmp("nj").setText(respText.msg.NJMC==undefined?"":respText.msg.NJMC);
    				Ext.getCmp("bj").setText(respText.msg.BJMC==undefined?"":respText.msg.BJMC);
    				Ext.getCmp("lxfs").setText(respText.msg.LXDH==undefined?"":respText.msg.LXDH);
    				Ext.getCmp("jtzz").setText(respText.msg.JTZZ==undefined?"":respText.msg.JTZZ);
    				Ext.getCmp("kslc").setText(respText.msg.EXAMNAME==undefined?"":respText.msg.EXAMNAME);
    				Ext.getCmp("kskm").setText(respText.msg.KSKM==undefined?"":respText.msg.KSKM);
    				var url = "";
    				if(respText.msg.PATH==undefined){
    					url = "../img/basicsinfo/mrzp_img.jpg";
    				}else{
    					url = "../uploadFile/photo/"+respText.msg.PATH;
    				}
		    		Ext.getCmp("imgBox").getEl().dom.src = url;
    			},
    			params : {'sfzjh':sfzjh},             
    			waitMsg : '数据处理中...'//遮罩           
    			});
    }
}
);

var getPath = function(){
    	var curWwwPath=window.document.location.href;
	    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	    var pathName=window.document.location.pathname;
	    var pos=curWwwPath.indexOf(pathName);
	    //获取主机地址，如： http://localhost:8083
	    var localhostPaht=curWwwPath.substring(0,pos);
	    //获取带"/"的项目名，如：/uimcardprj
	    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	    return localhostPaht+projectName;
    }