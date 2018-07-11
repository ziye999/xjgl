Ext.namespace('system.application');   
Ext.debug  = true;   //开发模式    //上线后改成false;
var mainobject;
var hexcase=0;var b64pad="";var chrsz=8;function hex_md5(a){return binl2hex(core_md5(str2binl(a),a.length*chrsz))}function b64_md5(a){return binl2b64(core_md5(str2binl(a),a.length*chrsz))}function str_md5(a){return binl2str(core_md5(str2binl(a),a.length*chrsz))}function hex_hmac_md5(a,b){return binl2hex(core_hmac_md5(a,b))}function b64_hmac_md5(a,b){return binl2b64(core_hmac_md5(a,b))}function str_hmac_md5(a,b){return binl2str(core_hmac_md5(a,b))}function md5_vm_test(){return hex_md5("abc")=="900150983cd24fb0d6963f7d28e17f72"}function core_md5(l,f){l[f>>5]|=128<<((f)%32);l[(((f+64)>>>9)<<4)+14]=f;var k=1732584193;var j=-271733879;var h=-1732584194;var g=271733878;for(var o=0;o<l.length;o+=16){var e=k;var p=j;var n=h;var m=g;k=md5_ff(k,j,h,g,l[o+0],7,-680876936);g=md5_ff(g,k,j,h,l[o+1],12,-389564586);h=md5_ff(h,g,k,j,l[o+2],17,606105819);j=md5_ff(j,h,g,k,l[o+3],22,-1044525330);k=md5_ff(k,j,h,g,l[o+4],7,-176418897);g=md5_ff(g,k,j,h,l[o+5],12,1200080426);h=md5_ff(h,g,k,j,l[o+6],17,-1473231341);j=md5_ff(j,h,g,k,l[o+7],22,-45705983);k=md5_ff(k,j,h,g,l[o+8],7,1770035416);g=md5_ff(g,k,j,h,l[o+9],12,-1958414417);h=md5_ff(h,g,k,j,l[o+10],17,-42063);j=md5_ff(j,h,g,k,l[o+11],22,-1990404162);k=md5_ff(k,j,h,g,l[o+12],7,1804603682);g=md5_ff(g,k,j,h,l[o+13],12,-40341101);h=md5_ff(h,g,k,j,l[o+14],17,-1502002290);j=md5_ff(j,h,g,k,l[o+15],22,1236535329);k=md5_gg(k,j,h,g,l[o+1],5,-165796510);g=md5_gg(g,k,j,h,l[o+6],9,-1069501632);h=md5_gg(h,g,k,j,l[o+11],14,643717713);j=md5_gg(j,h,g,k,l[o+0],20,-373897302);k=md5_gg(k,j,h,g,l[o+5],5,-701558691);g=md5_gg(g,k,j,h,l[o+10],9,38016083);h=md5_gg(h,g,k,j,l[o+15],14,-660478335);j=md5_gg(j,h,g,k,l[o+4],20,-405537848);k=md5_gg(k,j,h,g,l[o+9],5,568446438);g=md5_gg(g,k,j,h,l[o+14],9,-1019803690);h=md5_gg(h,g,k,j,l[o+3],14,-187363961);j=md5_gg(j,h,g,k,l[o+8],20,1163531501);k=md5_gg(k,j,h,g,l[o+13],5,-1444681467);g=md5_gg(g,k,j,h,l[o+2],9,-51403784);h=md5_gg(h,g,k,j,l[o+7],14,1735328473);j=md5_gg(j,h,g,k,l[o+12],20,-1926607734);k=md5_hh(k,j,h,g,l[o+5],4,-378558);g=md5_hh(g,k,j,h,l[o+8],11,-2022574463);h=md5_hh(h,g,k,j,l[o+11],16,1839030562);j=md5_hh(j,h,g,k,l[o+14],23,-35309556);k=md5_hh(k,j,h,g,l[o+1],4,-1530992060);g=md5_hh(g,k,j,h,l[o+4],11,1272893353);h=md5_hh(h,g,k,j,l[o+7],16,-155497632);j=md5_hh(j,h,g,k,l[o+10],23,-1094730640);k=md5_hh(k,j,h,g,l[o+13],4,681279174);g=md5_hh(g,k,j,h,l[o+0],11,-358537222);h=md5_hh(h,g,k,j,l[o+3],16,-722521979);j=md5_hh(j,h,g,k,l[o+6],23,76029189);k=md5_hh(k,j,h,g,l[o+9],4,-640364487);g=md5_hh(g,k,j,h,l[o+12],11,-421815835);h=md5_hh(h,g,k,j,l[o+15],16,530742520);j=md5_hh(j,h,g,k,l[o+2],23,-995338651);k=md5_ii(k,j,h,g,l[o+0],6,-198630844);g=md5_ii(g,k,j,h,l[o+7],10,1126891415);h=md5_ii(h,g,k,j,l[o+14],15,-1416354905);j=md5_ii(j,h,g,k,l[o+5],21,-57434055);k=md5_ii(k,j,h,g,l[o+12],6,1700485571);g=md5_ii(g,k,j,h,l[o+3],10,-1894986606);h=md5_ii(h,g,k,j,l[o+10],15,-1051523);j=md5_ii(j,h,g,k,l[o+1],21,-2054922799);k=md5_ii(k,j,h,g,l[o+8],6,1873313359);g=md5_ii(g,k,j,h,l[o+15],10,-30611744);h=md5_ii(h,g,k,j,l[o+6],15,-1560198380);j=md5_ii(j,h,g,k,l[o+13],21,1309151649);k=md5_ii(k,j,h,g,l[o+4],6,-145523070);g=md5_ii(g,k,j,h,l[o+11],10,-1120210379);h=md5_ii(h,g,k,j,l[o+2],15,718787259);j=md5_ii(j,h,g,k,l[o+9],21,-343485551);k=safe_add(k,e);j=safe_add(j,p);h=safe_add(h,n);g=safe_add(g,m)}return Array(k,j,h,g)}function md5_cmn(e,g,f,d,c,h){return safe_add(bit_rol(safe_add(safe_add(g,e),safe_add(d,h)),c),f)}function md5_ff(h,f,g,e,k,j,i){return md5_cmn((f&g)|((~f)&e),h,f,k,j,i)}function md5_gg(h,f,g,e,k,j,i){return md5_cmn((f&e)|(g&(~e)),h,f,k,j,i)}function md5_hh(h,f,g,e,k,j,i){return md5_cmn(f^g^e,h,f,k,j,i)}function md5_ii(h,f,g,e,k,j,i){return md5_cmn(g^(f|(~e)),h,f,k,j,i)}function core_hmac_md5(g,c){var b=str2binl(g);if(b.length>16){b=core_md5(b,g.length*chrsz)}var e=Array(16),a=Array(16);for(var f=0;f<16;f++){e[f]=b[f]^909522486;a[f]=b[f]^1549556828}var d=core_md5(e.concat(str2binl(c)),512+c.length*chrsz);return core_md5(a.concat(d),512+128)}function safe_add(c,b){var a=(c&65535)+(b&65535);var d=(c>>16)+(b>>16)+(a>>16);return(d<<16)|(a&65535)}function bit_rol(a,b){return(a<<b)|(a>>>(32-b))}function str2binl(b){var a=Array();var c=(1<<chrsz)-1;for(var d=0;d<b.length*chrsz;d+=chrsz){a[d>>5]|=(b.charCodeAt(d/chrsz)&c)<<(d%32)}return a}function binl2str(a){var b="";var c=(1<<chrsz)-1;for(var d=0;d<a.length*32;d+=chrsz){b+=String.fromCharCode((a[d>>5]>>>(d%32))&c)}return b}function binl2hex(a){var d=hexcase?"0123456789ABCDEF":"0123456789abcdef";var b="";for(var c=0;c<a.length*4;c++){b+=d.charAt((a[c>>2]>>((c%4)*8+4))&15)+d.charAt((a[c>>2]>>((c%4)*8))&15)}return b}function binl2b64(b){var a="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";var d="";for(var f=0;f<b.length*4;f+=3){var c=(((b[f>>2]>>8*(f%4))&255)<<16)|(((b[f+1>>2]>>8*((f+1)%4))&255)<<8)|((b[f+2>>2]>>8*((f+2)%4))&255);for(var e=0;e<4;e++){if(f*8+e*6>b.length*32){d+=b64pad}else{d+=a.charAt((c>>6*(3-e))&63)}}}return d};
/**
 * 模块基类
 * @param {} tab
 */
system.application.baseClass = function(config){
    this.container = config.container;
    this.config = config;    
    this.init();    
}   

Ext.extend(system.application.baseClass, Ext.util.Observable, {   
    init : Ext.emptyFn,
    addPanel: function(basePanel){    		
	    this.container.add(basePanel);
	    this.container.doLayout();	        	        
	},
	removeAll: function(){
    	this.container.removeAll();
        this.container.doLayout();                
	},
	setBasePanel:function(basePanel){
		this.basePanel = basePanel;
	},
	getBasePanel:function(){
		return this.basePanel;
	},		 	 
	 
	/**
	  * 加载模块
	  * @param {} parentCompement    //调用者
	  * @param {} container			 //容器
	  * @param {} params			 //差数
	  */
	loadCompement:function(config){	 	
		if(this[config.compementname]){	 		
	 	
	 	}	 	
	 	Ext.applyIf(config,{
	 		parentCompement:"",
	 		container:"",
	 		ScritpUrl:"",
	 		params:{},
	 		callback:function(){}
	 	});
	 		 		
	 	var u = config.container.body.getUpdater();
	 	u.setRenderer({
			render : function(el, response, updateManager, callback){
				el.update(null, updateManager.loadScripts, callback); 
			}
	 	});
	 	u.update({
			url:config.ScritpUrl,
	    	method:"GET",
	    	scripts:true,
	    	callback:function(el ,success,response ,options ){	    		
	    		var f = eval(response.responseText); 	    				 	    				
	    		this[config.compementname] = new f(config);
	    		config.callback.call(config.scope,config.params);	    				 
	    	},
	    	scope:this,
	    	discardUrl:true,
	    	text:"加载中.....",
	    	nocache:true
		});
	 }	
});

//主程序类
system.application.app = function(){	
    this.init();    
}   

Ext.extend(system.application.app, Ext.util.Observable, {   
    loadMask:null,
    init: function(){    	
    	this.cookie = new Cookie();
    	var B_System_start = new Ext.Button({
			text : "开始",
			iconCls : "p-icons-world",
			menu:[{text:"退出系统",iconCls : "p-icons-bullet_blue",handler:this.logout,scope:this},"-",
//				  {text:"重新登入",iconCls : "p-icons-bullet_blue",handler:this.relogin,scope:this},
				  {text:"修改密码",iconCls : "p-icons-bullet_blue",handler:this.updataPassword,scope:this}
//				  ,{text:"主题风格",iconCls : "p-icons-bullet_blue",menu:[
//				  		{text:"黑色(光滑的)" ,color:"000000",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//				 		{text:"黑色"		    ,color:"545554",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//				  		{text:"深灰色"       ,color:"ABADAF",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//				  		{text:"灰色"         ,color:"D8D8D8",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//				  		{text:"深紫蓝色"     ,color:"424370",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//				  		{text:"紫蓝色"		,color:"52567E",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//				  		{text:"暗蓝灰色"		,color:"5E7189",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//				  		{text:"淡蓝色(默认)" ,color:"BDD3EF",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//				  		{text:"紫色"			,color:"9ACD68",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//				  		{text:"绿色"			,color:"9CD4C1",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//				 		{text:"红色"			,color:"FC6161",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//						{text:"薄荷红"		,color:"FFB5B5",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//				 		{text:"粉红色"		,color:"E2B4D5",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//						{text:"咖啡色"		,color:"C49E92",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//					    {text:"橙色"			,color:"FF8C37",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this},
//				 		{text:"红间灰"		,color:"C72929",iconCls : "p-icons-bullet_blue",handler:this.setThemePickeer,scope:this}
//				  		
//				  	]}
				  ]
		});
    	
    	this.accordion = new Ext.Panel({
		    title:"功能菜单",
            margins:'0 0 0 5',
		    layout:'accordion',
		    region:'west',
		    // hidden:true,
		    width: 200,
		    // bbar:["-", B_System_start,"-"],
			collapseMode : "mini",
            split:true,            
            listeners:{
            	afterrender:function(container,layout){
            		this.addCardNode()
            	},
            	scope:this
            },
            border:true,
            minSize: 180,
            maxSize: 240,
		    defaults:{layout:"fit",border:false},
		    items:[]
		});
        this.body = new Ext.TabPanel({
        	region			:'center',   
            margins			:'0 5 0 0',
            id				:"plat-iframe-container",
            activeTab		: 0,
			enableTabScroll : true,
			minTabWidth     : 135,
       		resizeTabs		: true,
       		border			: false,
        	tabWidth		: 135,
			border          : false,
			plugins         : [new Ext.ux.TabScrollerMenu({maxText: 15,pageSize : 5,menuPrefixText:"选项卡"}) ,new Ext.ux.TabCloseMenu()],
            bbar			: [B_System_start,{iconCls:"p-icons-checkclose",handler:this.logout,text:"退出系统"},"->","<font color=gray>版权所有：湖南省电大</font>","-","<font color=gray>技术支持：湖南嘉杰信息</font>"],
            items			: [{title:"批量报名流程图",autoLoad:{url:"jsp/lct.jsp"}}],
            tbar            : ["<font color=red>报名时间：6.10-6.22&nbsp&nbsp技术客服电话：0731-82821017&nbsp&nbsp业务客服电话：0731-82821386&nbsp&nbsp收款单位：湖南广播电视大学 开户行：交通银行长沙湘江中路支行&nbsp&nbsp账号：431899991010004246555&nbsp&nbsp付款时，请备注付款单位</font>"]     			  
                 			   
        }); 
        new Ext.Viewport({
        	layout:'border',
            items:[   
               {region:'north', margins:'5 5 -2 5',contentEl:'header',collapseMode : "mini",border:true,minSize: 70,maxSize: 70,
               	split:true
               	},   
                this.accordion,  
                this.body   
            ]   
        });
        JH.$setThemePickeer(this.cookie.get("plat_ThemePickeer"));
         
        this.loadDescktop();
        this.messageBoxListener(); 
        mainobject=this;                
    },
    
	messageBoxListener:function(){ 
		var thiz = this;
		//加一个方法调用弹出
		showmsgbox = function(){
			Ext.Ajax.request({
				url: 'messageBoxAction_getBox.do',
		        headers: {
		        	'userHeader': 'userMsg'
		        },
		        params: { a: 10, b: 20 },
		        method: 'GET',
		        success: function (response, options) {
		        	var responseJson = Ext.util.JSON.decode(response.responseText); 
		            var msgdata = responseJson.data;
		            var title = responseJson.msg;
		            var htmlcontent="";
		        	tipw=[];
		        	if(msgdata!=null){		        			
		        		for(var j=0; j<msgdata.length; j++){
				        	var content = msgdata[j].content;
				            var PARENTID = msgdata[j].PARENTID;
				            var MENUCODE = msgdata[j].MENUCODE;
			                htmlcontent+="<a href=\"javascript:openPanel('"+PARENTID+"','"+MENUCODE+"');\">"+content+"</a><br/>";
		        		}
			            tipw[0] = new Ext.ux.TipsWindow({
			                		title: title,
			                		autoHide: 200,//10秒自动关闭
			                		count:1,//设置弹出的是第几个
			                		html: htmlcontent
			                		});
			            tipw[0].show();		        						        					                    
		        	}  
		        },
		        failure: function (response, options) {
		        	Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
		        }
			});
		} 
		showmsgbox();
    },        
    loadDescktop:function(){
    	var node = null;
    	if (getMenuJs("module")!=null) {
    		node = {
    				text:menuMap.getName(getLocationPram("module")),
    	        	id:"tab-desk-grolb",    		
    	        	attributes:{scriptURL:getMenuJs("module")}
    		}
    	}
    	/*var node = {
        	text:"主   页",
        	id:"tab-desk-grolb",    		
        	attributes:{scriptURL:getMenuJs("module")}
        }*/
    	/*var moduleSrc = getMenuJs("module");
    	if(moduleSrc == null) {
    		moduleSrc = '/module/basicsinfo/desktop.js';
    	}
    	var node = {
    		text:"主   页",
    		id:"tab-desk-grolb",    		
    		attributes:{scriptURL:moduleSrc}
    	}*/
    	var tab = this.body.add( new Ext.Panel({
                id: id,
                title : node.text,
				layout : 'fit',
				//iconCls:"p-icons-world",
				tabTip : node.text,
				border : false,
				alive : true,
				listeners:{
					close:function(p){
						if(Ext.debug ===true){
							p.ownerCt.remove(p);
							this[p.id.replace('tab-',"")]= null;
							delete this[this[p.id.replace('tab-',"")]]						
						}																	
					},
					scope:this
				},			
				closable : true,
				tabWidth : 135,
				tabTip : node.text
    	}));         
    	tab.module=node.attributes;
    	this.body.setActiveTab(tab);
    	//加载模块
    	this.loadModel(node,tab);  
    },
    createTree:function(id){   
    	return new Ext.ux.TreePanel({ 
    		id:"treePanel_"+id,
          	rootVisible:false,
			autoScroll : true,
            root: new Ext.tree.AsyncTreeNode({
				    id: id,
					expanded: true
			}),
			action:"systemModuleAction_querySystemModule.do",
            bodyStyle:'padding:0px',
            listeners:{
            	click:this.clickTree,
            	scope:this
            }
		});
    },
    addCardNode:function(){
    	var thiz = this;
    	JH.$request({
	    	loader:false,
	    	action:"systemModuleAction_querySystemRootTree.do",
         	scope:thiz,
			handler:function(result,scope){ 
				Ext.each(result,function(o){			     	
			     	o.listeners={
			     		render:function(container,layout){  
			     			container.add(scope.createTree(o.id));
			     		},
			     		scope:scope
			     	}
				}); 
				thiz.accordion.add(result);
			    thiz.accordion.doLayout();
			}
    	})
    },    
    //点击菜单方法(node:被点击的节点)
    clickTree: function(node){
        //如果节点不是叶子则返回
     	if(Ext.isEmpty(node.attributes.scriptURL))return;
        if(!node.isLeaf()){   
            return false;   
        }
        this.openTabInterface(node);
    },
    openTabInterface:function(node){    
    	var id = 'tab-' + node.id;
        var tab = Ext.getCmp(id);
        if(!tab){
            tab = this.body.add( new Ext.Panel({
                id : id,
                title : node.text,
				layout : 'fit',
				tabTip : node.text,
				border : false,
				alive : true,
				listeners:{
					close:function(p){
						if(Ext.debug ===true){
							p.ownerCt.remove(p);
							this[p.id.replace('tab-',"")]= null;
							delete this[this[p.id.replace('tab-',"")]]						
						}																	
					},
					scope:this
				},
				closable : true,
				tabWidth : 135,
				tabTip : node.text
            }));
            tab.module=node.attributes;
            this.body.setActiveTab(tab);
            //加载模块
            this.loadModel(node,tab);
        }else{
            this.body.setActiveTab(tab);
        }
    },
	destroy:function(p){
		this.destroy();
	},
    //加载模块方法(id:模块ID;tab:模块显示在哪里)
    loadModel: function(node,tab){  
        //定义模块变量
        var model;
        if(this[node.id]){
            //如果模块类已存在，就直接实例化
            model = new this[node.id]({container:tab});
        }else{
        	this.loadMask =new Ext.LoadMask(tab.el,{msg:"正在初始化载模块,请稍后......",removeMask :true});
            this.loadMask.show();   
            Ext.Ajax.request({   
                method:'GET',
                url: JH.getRoot()+node.attributes.scriptURL  ,   
                scope: this,   
                success: function(response){
                	//获取模块类  
                	this[node.id] = eval(response.responseText);   
                	//实例化模块类
                	model = new this[node.id]({container:tab});
                	this.loadMask.hide();                		
                },
                callback:function(options ,success ,response ){
                	if(!success){
                		/*Ext.MessageBox.show({
                			title:"错误",
                			msg:"加载模块出错,请与技术人员联系!",
                			icon:Ext.MessageBox.ERROR,
                			bottons:Ext.MessageBox.OK
                		})*/
                	}
                }   
            });
        }
    },
    /**
     * @param {} b
     * @param {} e
     */
	setThemePickeer:function(b,e){
		var color = b.color;
		if(color){
			JH.$setThemePickeer(color);
			this.cookie.put("plat_ThemePickeer",color);
		}		
	},
	/**退出系统
	 * 
	 */
	logout:function(b,e){
		var thiz = this;	
		Ext.MessageBox.show({
			title:"提示：",
			msg:"您确定要退出系统吗?",
			icon:Ext.MessageBox.QUESTION,
			buttons:Ext.MessageBox.OKCANCEL,
			el:b.el.dom,
			fn:function(b){
				if(b=='ok'){
					Ext.Ajax.request({   
		                method:'GET',
		                url: JH.getRoot()+"/logoutAction_logout.do",
		                scope: thiz,
		                success: function(response){
					    	 window.location = JH.getRoot()+"/login.jsp";		                		
		                },
		                callback:function(options ,success ,response ){
		                	if(!success){
		                		/*Ext.MessageBox.show({
		                			title:"错误",
		                			msg:"加载模块出错,请与技术人员联系!",
		                			icon:Ext.MessageBox.ERROR,
		                			bottons:Ext.MessageBox.OK
		                		})*/
		                	}
		                }   
		            });
				}
			}
		})
	},
	updataPassword:function(){
		var oldpass = new Ext.form.TextField({id:"oldpass",maxLength:32,width:120,inputType:'password'}); 
    	var newpass1 = new Ext.form.TextField({id:"newpass1",maxLength:32,width:120,inputType:'password'});
    	var newpass2 = new Ext.form.TextField({id:"newpass2",maxLength:32,width:120,inputType:'password'});
    	
    	this.editPassForm = new Ext.ux.FormPanel({
			labelWidth:75,
			frame:false,
			defaults:{anchor:"95%"},
			items:{
				xtype:"panel",
				layout:"table", 
				layoutConfig: { 
					columns: 2
				}, 
				baseCls:"table",
				items:[
				       {html:"原始密码：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>", items:[oldpass],baseCls:"component",width:150},
				       {html:"新密码：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>",items:[newpass1],baseCls:"component",width:150},
				       {html:"再输入密码：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>",items:[newpass2],baseCls:"component",width:150}
				      ]
			} 
		});
    	
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.update,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.editPassWindow.hide();},scope:this});
		
		this.editPassWindow = new Ext.ux.Window({				
			 	width:290,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
			 	title:"修改密码"});
		this.editPassWindow.add({items:[this.editPassForm]});
		this.editPassWindow.show();
    },
    update:function(){
    	var oldpass = Ext.getCmp("oldpass").getValue();
    	var newpass1 = Ext.getCmp("newpass1").getValue();
    	var newpass2 = Ext.getCmp("newpass2").getValue();   	
    	if(oldpass==""){
    		Ext.Msg.alert('消息',"原始密码不能为空！");
    		return;
    	}
    	if(newpass1==""){
    		Ext.Msg.alert('消息',"新密码不能为空！");
    		return;
    	}
    	if(newpass2==""){
    		Ext.Msg.alert('消息',"再输入密码不能为空！");
    		return;
    	}
    	oldpass = hex_md5(oldpass);
    	newpass1 = hex_md5(newpass1);
    	newpass2 = hex_md5(newpass2);
    	this.editPassForm.$submit({
    		action:"userManager_updatePassword.do",
    		params:{
    			'oldpass':oldpass,
    			'newpass1':newpass1,
    			'newpass2':newpass2
    		},    		
    		handler:function(){
    			this.editPassWindow.hide(); 
    			this.editPassWindow.hide(); 
    		},
    		scope:this    		
    	}); 
    	this.editPassWindow.hide(); 
    }
});   

/**
 * 系统入口
 */
Ext.onReady(function(){        
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider({}));
	Ext.QuickTips.init();   
	MainBody = new system.application.app();		
});

function openPanel(PARENTID,MENUCODE){
	var tp = Ext.getCmp("treePanel_"+PARENTID); 
 	Ext.getCmp(tp.root.id).expand();
 	tp.selectPath("/"+PARENTID+"/"+MENUCODE,"",function(){
 		var node = tp.getSelectionModel().getSelectedNode(); 
 		mainobject.clickTree(node);
 	});
}