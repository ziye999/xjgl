Ext.namespace('system.application');   
Ext.debug  = true;   //开发模式    //上线后改成false;
var mainobject;

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
    addPanel  : function(basePanel){    		
	    this.container.add(basePanel);
	    this.container.doLayout();	        	        
	},
	removeAll  : function(){
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
	    	callback :function(el ,success,response ,options ){	    			
	    		var f = eval(response.responseText); 
	    		this[config.compementname] = new f(config);
	    		config.callback.call(config.scope,config.params);
	    	},
	    	scope:this,
	    	discardUrl :true,
	    	text :"加载中.....",
	    	nocache :true
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
				  {text:"修改密码",iconCls : "p-icons-bullet_blue",handler:this.updataPassword,scope:this}
			]
		});
    	this.accordion = new Ext.Panel({
		    title:"功能菜单",
            margins:'0 0 0 5',
		    layout:'accordion',
		    region:'west',
		    width: 200,
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
            region:'center',   
            margins:'0 5 0 0',
            id:"plat-iframe-container",
            activeTab: 0,
			enableTabScroll : true,
			minTabWidth     : 135,
       		resizeTabs: true,
       		headerStyle: 'display:none',
       		border:false,
        	tabWidth: 135,
			border          : false,
            items: []   
        });
    	new Ext.Viewport({
            layout:'border',
            items: [{region:'north',margins:'5 5 -2 5',collapseMode : "mini",border:true,minSize: 70,maxSize: 70,split:true},
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
    	var node = {
    		text:"主   页",
    		id:"tab-desk-grolb",    		
    		attributes:{scriptURL:getMenuJs("module")}
    	}
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
				iconCls:"p-icons-world",
				tabTip : node.text,
				border : false,
				alive : true,				
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
			action:"systemModuleAction_querySystemModule.do",//根据一级菜单查询二级菜单
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
	    	action:"systemModuleAction_querySystemRootTree.do",//根据用户查询一级菜单
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
                id: id,
                title : node.text,//标题
				layout : 'fit',//布局
				tabTip : node.text,//标签提示
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
        	this.loadMask = new Ext.LoadMask(tab.el,{msg:"正在初始化载模块,请稍后......",removeMask :true});
            this.loadMask.show();   
            Ext.Ajax.request({   
                method:'GET',
                url: JH.getRoot()+node.attributes.scriptURL,   
                scope: this,   
                success: function(response){
                	//获取模块类  
                	this[node.id] = eval(response.responseText);   
                	//实例化模块类
                	model = new this[node.id]({container:tab});
                	this.loadMask.hide();                		
                },
                callback:function(options,success,response ){
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
    /**
	 * 退出系统
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
