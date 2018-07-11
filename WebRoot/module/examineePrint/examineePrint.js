var isInit=false;
// 查询类型，点查询按钮时，改变其值
var search_type = "";
Ext.extend(system.application.baseClass,{
	/** 初始化 * */
    init: function(){
	    this.initDate();
		this.initComponent();
		this.initListener();
		this.initFace(); 
    },
    /** 初始化页面、内存等基本数据 * */
    initDate:function(){
    	this.initForm();
    },
  	initComponent :function(){
   		this.dataPanel=new Ext.Panel({
    		region:'center',
    		border:false,
   			id:"dataPanel"
   		});
   		this.sidePanel = new Ext.Panel({
   			region:'center',
    		border:'border',
    		id:'sidePanel',
    		height:488,
    		style:'background-color:#B2E0F9',
   			tbar:[ "->",
   			      {id:"preview", xtype:"button",text:"预览",iconCls:"p-icons-print",handler:this.perviewExamCard,scope:this},
				  {xtype:"button",text:"打印",iconCls:"p-icons-print",handler:this.dayinData,scope:this}
				  ],
			items:[this.dataPanel]
   		});
   		this.tabPanel = new Ext.TabPanel({   
   			activeTab: 0,
   			headerStyle: 'display:none',
   			border:false, 
   			items: [this.search07]   
   		});
		this.grid = new Ext.Panel({
			id:"gridid",
			region:"center",
			border:true,
			height:600,
			items:[this.tabPanel,this.sidePanel]
		}); 
		Ext.getCmp('preview');
	}, 
	/** 对组件设置监听 * */
    initListener:function(){
// var thiz = this;
// this.orgTree.on("click",function(node){
// var panel = Ext.getCmp("search_form"+node.id);
// if(panel != undefined){
// thiz.tabPanel.setActiveTab(panel);
// panel.getForm().reset();
// Ext.getDom('dataPanel').innerHTML="";
// }
// if (node.id=="08") {
// thiz.selectExamSeatInfo();
// }
// if (node.id=="07") {
// Ext.getCmp('preview').show();
// } else {
// Ext.getCmp('preview').hide();
// }
// },this.grid);
    },
    /** 初始化界面 * */
    initFace:function(){
    	this.addPanel({id:"panel",layout:"border",
    		items:[this.grid]
    	});
    },    
    initForm:function(){ 
		// 搜索区域
		// 打印准考证
		var xnxq7	= new Ext.ux.form.XnxqField({width:180,id:"xnxq_find7",readOnly:true,callback:function(){
		var id=Ext.getCmp("xnxq_find7").getCode();// 取得ComboBox0的选择值
		var newStore = new Ext.data.JsonStore({
									autoLoad:false,
									url:'dropListAction_ExamRoundByXnxq.do?params='+id,
									fields:["CODEID","CODENAME"]
								});
			}
		});
        var name_find = new Ext.form.TextField({fieldLabel:"",id:"name_find",name:"name_find",width:180});
        var sfzjh_find = new Ext.form.TextField({fieldLabel:"",id:"sfzjh_find",name:"sfzjh_find",width:180});
            
		var cx7 = new Ext.Button({x:10,y:-1,cls:"base_btn",text:"查询",handler:this.selectExamCard,scope:this,id:"searchZk"});
		var cz7 = new Ext.Button({x:80,y:-1,cls:"base_btn",text:"导出PDF",handler:this.exportPdf,scope:this});
		var cc7 = new Ext.Button({x:150,y:-1,cls:"base_btn",text:"导出xls",handler:this.exportXls,scope:this});
		
				
		var kd10 = new Ext.ux.Combox({width:190,id:"kd_find10"});
		var kspc10 = new Ext.ux.Combox({width:190,id:"kspc_find10"});
		var kslc10 = new Ext.ux.Combox({width:190,id:"kslc_find10",listeners:{
// "select":function(){
// var id=Ext.getCmp("kslc_find10").getValue();
// var newStore = new Ext.data.JsonStore({
// autoLoad:false,
// url:'dropListAction_kaoDianMc.do?params='+id,
// fields:["CODEID","CODENAME"]
// });
// Ext.getCmp("kd_find10").clearValue();
// Ext.getCmp("kd_find10").store=newStore;
// newStore.reload();
// Ext.getCmp("kd_find10").bindStore(newStore);
//                
// var newStore1 = new Ext.data.JsonStore({
// autoLoad:false,
// url:'dropListAction_getKskmBykslc.do?params='+id,
// fields:["CODEID","CODENAME"]
// });
// Ext.getCmp("kspc_find10").clearValue();
// Ext.getCmp("kspc_find10").store=newStore1;
// newStore1.reload();
// Ext.getCmp("kspc_find10").bindStore(newStore1);
// }
		}});
		var xnxq10 = new Ext.ux.form.XnxqField({width:190,id:"xnxq_find10",readOnly:true,callback:function(){
				var id=Ext.getCmp("xnxq_find10").getCode();
				var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
				Ext.getCmp("kslc_find10").clearValue(); 
				Ext.getCmp("kd_find10").clearValue();  
				Ext.getCmp("kslc_find10").store=newStore;
                Ext.getCmp("kd_find10").store.removeAll();
                newStore.reload();
                Ext.getCmp("kslc_find10").bindStore(newStore);
			}
		});
		var cx10 = new Ext.Button({x:10,y:-1,cls:"base_btn",text:"查询",handler:this.selectExamKsInfo,scope:this});
		var cz10 = new Ext.Button({x:80,y:-1,cls:"base_btn",text:"重置",handler:function(){this.search10.getForm().reset()},scope:this}); 
	    	
	    // 打印准考证搜索区
	   	this.search07 = new Ext.form.FormPanel({
	   		id:"search_form07",
	   		region: "north",
	   		height:150,
	   		border:false,
	   		items:[{  
	   			layout:'form',  
	   			xtype:'fieldset',  
	   			style:'margin:10 10',
	   			title:'查询条件',  
	   			items: [{ 
                    xtype:"panel",
                    layout:"table", 
                    layoutConfig: { 
                    	columns: 6
                    }, 
					baseCls:"table",
					items:[
					       {html:"年度：",baseCls:"label_right",width:70},
					       {items:[xnxq7],baseCls:"component",width:190},
					       {html:"姓名：",baseCls:"label_right",width:70},
					       {items:[name_find],baseCls:"component",width:190},
					       {html:"身份证：",baseCls:"label_right",width:70}, 
					       {items:[sfzjh_find],baseCls : "component",width:190},
					       {layout:"absolute",items:[cx7,cz7,cc7],baseCls:"component_btn",width:180,height:20,colspan:6}
					     
						] 
	   			}]  
	   		}]  
	    });
	    
	    this.search10 = new Ext.form.FormPanel({
	    	id:"search_form10",
	    	region: "north",
	    	height:120,
	    	border:false,
	    	items:[{  
	    		layout:'form',  
	    		xtype:'fieldset',  
	    		style:'margin:10 10',
	    		title:'查询条件',  
	    		items: [{ 
                    xtype:"panel",
                    layout:"table", 
                    layoutConfig: { 
                    	columns: 5
                    }, 
                    baseCls:"table",
                    items:[ 
                           {html:"年度：",baseCls:"label_right",width:45},
                           {items:[xnxq10],baseCls:"component",width:200},
                           {html:"考试名称：",baseCls:"label_right",width:70},
                           {items:[kslc10],baseCls:"component",width:200},
                           {items:[cx10],baseCls:"component_btn",width:120},
                           {html:"考试批次：",baseCls:"label_right",width:70},
					       {items:[kspc10],baseCls:"component",width:200},
                           {html:"考点：",baseCls:"label_right",width:45}, 
                           {items:[kd10],baseCls:"component",width:200},
                           {items:[cz10],baseCls:"component_btn",width:120}
                          ] 
	    		}]  
	    	}]  
	    });
    },

    selectExamCard:function(){
    	search_type = 'zkz';
    	var xnxq = Ext.getCmp('xnxq_find7').getValue(); 
    	if(xnxq == ""){
    		Ext.MessageBox.alert("消息","请选择年度！");
			return;
    	}
    	Ext.Ajax.request({		
    		url:"examineePrint_dayin.do",
    		params:{
    			'xnxq':Ext.getCmp('xnxq_find7').getValue()
    		},
    		scope:this,
    		success: function (r, options) {			
    			var result =Ext.decode(r.responseText);	
    			if(result.data.SFFB=='0'){
    		    	// 查询准考证
    				var name=Ext.getCmp('name_find').getValue();
    				var sfzjh=Ext.getCmp('sfzjh_find').getValue();
    				var height = Ext.getCmp('sidePanel').getHeight()-172;
    				var iframe="<fieldset style='height:"+height+"; width:100%; border:0px; padding:0;'>"+"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+"'src='examineePrint_getExamCard.do?xnxq="+xnxq+"&sfzjh="+sfzjh+"&name="+name+"' frameborder='0' scrolling='auto' style='border:1px dashed#B3B5B4;'></iframe>"+"</fieldset>";
    				Ext.getDom('dataPanel').innerHTML=iframe;
    			}
    			else{
    				Ext.Msg.alert("错误","未排考，现在还不能查询准考证！");
    			}
    		},
    		failure: function (response, options) {
    			Ext.Msg.alert("错误","查询失败！");
    		}
    });
    	
    },

    exportPdf:function(){
    	Ext.Msg.wait("正在导出","提示");
    	var xnxq = Ext.getCmp('xnxq_find7').getValue(); 
    	if(xnxq == ""){
    		Ext.MessageBox.alert("消息","请选择年度！");
			return;
    	}
    	Ext.Ajax.request({
    		
    		url:"examineePrint_dayin.do",
    		params:{
    			'xnxq':Ext.getCmp('xnxq_find7').getValue()
    		},
    		scope:this,
    		success: function (r, options) {
    			
    			var result =Ext.decode(r.responseText);
    			
    			if(result.data.SFFB=='0'){
    				
    		    	var name=Ext.getCmp('name_find').getValue();
    		    	var sfzjh=Ext.getCmp('sfzjh_find').getValue(); 
    		    	 Ext.Ajax.request({
    		    		url:"examineePrint_getExportPdf.do",
    		    		params:{ 
    		    			'xnxq':xnxq,
    		    			'name':name,
    		    			'sfzjh':sfzjh
    					},
    					scope:this,
    					success: function (r, options) {
    						var result =Ext.decode(r.responseText); 
    						if(result.success){
    							Ext.Msg.alert("提示","导出成功!");
    							if(result.data!=null && result.data!=''){
    								window.open(Ext.get("ServerPath").dom.value+"/"+result.data.replace(/\\/g, "\/"));
    							}
    						}else{
    							Ext.Msg.alert("提示",result.msg);
    						}
    		    		},
    		    		failure: function (response, options) {
    		    		}
    				});
    			}else{
    				Ext.Msg.alert("错误","未排考，现在还不能导出准考证PDF！");
    			}
    		},
    		failure: function (response, options) {
    			Ext.Msg.alert("错误","导出失败！");
    		}
    	});
    		
    },
    
    exportXls:function(){
    	Ext.Msg.wait("正在导出","提示");
    	var xnxq = Ext.getCmp('xnxq_find7').getValue(); 
    	if(xnxq == ""){
    		Ext.MessageBox.alert("消息","请选择年度！");
			return;
    	}
    	Ext.Ajax.request({
    		
    		url:"examineePrint_dayin.do",
    		params:{
    			'xnxq':Ext.getCmp('xnxq_find7').getValue()
    		},
    		scope:this,
    		success: function (r, options) {
    			
    			var result =Ext.decode(r.responseText);
    			
    			if(result.data.SFFB=='0'){
    				
    		    	var name=Ext.getCmp('name_find').getValue();
    		    	var sfzjh=Ext.getCmp('sfzjh_find').getValue();
    		    	Ext.Ajax.request({
    		    		url:"examineePrint_exportXls.do",
    		    		params:{
    		    			'xnxq':xnxq,
    		    			'name':name,
    		    			'sfzjh':sfzjh
    		    		},
    		    		scope:this,
    		    		success: function (r, options) {
    		    			var result =Ext.decode(r.responseText);
    		    			if(result.success){
    		    				Ext.Msg.alert("提示","导出成功!");
    		    				if(result.data!=null && result.data!=''){
    		    					window.open(Ext.get("ServerPath").dom.value+"/"+result.data.replace(/\\/g,"\/"));
    		    				}
    		    			}else{
    		    				Ext.Msg.alert("提示",result.msg);
    		    			}
    		    		},
    		    		failure: function (response, options) {
    		    		}
    		    	});
    			}else{
    				Ext.Msg.alert("错误","未排考，现在还不能导出准考证EXCEL！");
    			}
    		},
    		failure: function (response, options) {
    			Ext.Msg.alert("错误","导出失败！");
    		}
    	});
    	
    },
    
    dayinData:function(){
    	// 打印
    	if(Ext.getDom('frmReport') == null) {
    		Ext.MessageBox.alert("消息","请先检索数据！");
			return;
    	}
    	if(Ext.getDom('frmReport').contentWindow.document.getElementById("complete") == null 
    			|| Ext.getDom('frmReport').contentWindow.document.getElementById("complete").value != "ok") {
    		Ext.MessageBox.alert("消息","请等待数据加载完毕！");
    		return;
    	}
    	
    	Ext.Ajax.request({
    		
    		url:"examineePrint_dayin.do",
    		params:{
    			'xnxq':Ext.getCmp('xnxq_find7').getValue()
    		},
    		scope:this,
    		success: function (r, options) {
    			
    			var result =Ext.decode(r.responseText);
    			
    			if(result.data.SFFB=='0'){
    				// 将css样式导入lodop打印中，避免失效
    		    	var cssPath = curWwwPath.substring(0,curWwwPath.indexOf('loginAction_login.do'))+"css/Print.css"
    		    	var strStyleCSS="<link href='"+cssPath+"' type='text/css' rel='stylesheet'>";
    		    	var LODOP =getLodop(); 
    		    	
    		    	// 准考证单独，因为数据量太大，分别将每一页发给打印机，注意不要关掉浏览器
    		    	if(search_type == 'zkz'){
    		    		var total = parseInt(Ext.getDom('frmReport').contentWindow.document.getElementById('count').value);
    		    		var start, end;
    		    		Ext.MessageBox.alert("消息","共"+total+"张准考证，请填写打印起止页！", function() {
    		        		// 填写起始页
    		        		Ext.MessageBox.prompt("请填写起始页", "", callbackStart, this, true, "1"); 
    		                function callbackStart(id, text) {
    		            		start=parseInt(text);
    		            		if(start < 1) {
    		            			start = 0;
    		            		} 
    		            		if(start > total) {
    		            			start = total;
    		            		}
    		            		if(isNaN(start)){
    		            			Ext.MessageBox.alert("消息","填写数字不合法！");
    		            			return;
    		            		}
    		                	
    		                	// 填写结束页
    		                    Ext.MessageBox.prompt("请填写结束页", "", callbackEnd, this, true, total); 
    		                    function callbackEnd(id, text) {
    		                		end=parseInt(text);
    		                		if(end < 1) {
    		                			end = 0;
    		                		} 
    		                		if(end > total) {
    		                			end = total;
    		                		}
    		                		if(start > end) {
    		                			var tmp;
    		                			tmp = end;
    		                			end = start
    		                			start = tmp;
    		                		}
    		                		if(isNaN(end)){ 
    		                			Ext.MessageBox.alert("消息","填写数字不合法！");
    		                			return;
    		                		}
    		                    	
    		                    	 // 开始打印
    		                    	for (i = start-1; i <end; i++) {
    		                    		LODOP.ADD_PRINT_HTM("1%","3%", "99%","100%",strStyleCSS+Ext.getDom('frmReport').contentWindow.document.getElementById('zkz'+i).innerHTML);
    		                    		LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
    		                    		LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",1);
    		                    		LODOP.PRINT();
    		                		}
    		                    	Ext.MessageBox.alert("消息","将打印"+(end-start+1)+"张准考证，请等待打印完成！");
    		                    } 
    		                    
    		                 
    		                } 
    		                
    		    		});
    		            
    		    	} else {
    		    		// lodop打印
    		    	
    		    	
    		        	LODOP.ADD_PRINT_HTM("1%","3%", "99%","100%",strStyleCSS+Ext.getDom('frmReport').contentWindow.document.body.innerHTML);
    		        	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
    		        	LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",1);
    		        	LODOP.PREVIEW();
    		    	}	
    				
    			}else{
    				Ext.Msg.alert("错误","未排考，现在还不能打印准考证！");
    			}
    		},
    		failure: function (response, options) {
    			Ext.Msg.alert("错误","打印失败！");
    		}
    	});
    },
    perviewExamCard:function(){
    	// 打印
    	if(Ext.getDom('frmReport') == null) {
    		Ext.MessageBox.alert("消息","请先检索数据！");
			return;
    	}
    	if(Ext.getDom('frmReport').contentWindow.document.getElementById("complete") == null 
    			|| Ext.getDom('frmReport').contentWindow.document.getElementById("complete").value != "ok") {
    		Ext.MessageBox.alert("消息","请等待数据加载完毕！");
    		return;
    	} 
    	Ext.Ajax.request({
    		
    		url:"examineePrint_dayin.do",
    		params:{
    			'xnxq':Ext.getCmp('xnxq_find7').getValue()
    		},
    		scope:this,
    		success: function (r, options) {
    			
    			var result =Ext.decode(r.responseText);
    			
    			if(result.data.SFFB=='0'){
    				// 将css样式导入lodop打印中，避免失效
    				var cssPath = curWwwPath.substring(0,curWwwPath.indexOf('loginAction_login.do'))+"css/Print.css"
    		    	var strStyleCSS="<link href='"+cssPath+"' type='text/css' rel='stylesheet'>";
    		    	var LODOP =getLodop(); 
    		    	
    		    	LODOP.ADD_PRINT_HTM("1%","3%", "99%","100%",strStyleCSS+Ext.getDom('frmReport').contentWindow.document.body.innerHTML);
    		    	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
    		    	LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",1);
    		    	LODOP.PREVIEW();
    			}else{
    				Ext.Msg.alert("错误","未排考，现在还不能预览准考证！");
    			}
    		},
    		failure: function (response, options) {
    			Ext.Msg.alert("错误","预览失败！");
    		}
    	
    	});      
    }
}); 