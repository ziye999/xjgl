var responseArray;
var isUpdate = "";
Ext.extend(system.application.baseClass, {
	/** 初始化 * */
	init : function() {
		this.initDate();
		this.initComponent();
		this.initListener();
		this.initFace();
		this.initQueryDate();
	},
	/** 初始化页面、内存等基本数据 * */
	initDate : function() {
		
	},
	/** 对组件设置监听 * */
	initListener : function() {
		/*this.editUpWindow.on("hide",function(){
    		this.editUpForm.$reset();
    	},this);
    	this.gridUp.on("rowdblclick",function(grid, rowIndex, e){
    		isUpdate = "Yes";
    		var lcid = grid.store.getAt(rowIndex).get("LCID");
    		this.editUpWindow.show(null,function(){
    			Ext.getCmp("kh").setValue(grid.store.getAt(rowIndex).get("KH")).disable();
    			Ext.getCmp("xjh").setValue(grid.store.getAt(rowIndex).get("XJH")).disable();
    			for (var i = 0; i < responseArray.length; i++) {
				 	Ext.getCmp(responseArray[i].KMID).setValue(grid.store.getAt(rowIndex).get(responseArray[i].KM));
				}
    		},this)
    	},this);*/
	},
	initComponent : function() {
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : false});
		var cm = [sm,
					{header: "组考单位",   align:"center", sortable:true, dataIndex:"zkdw"},
					{header: "参考单位",   align:"center", sortable:true, dataIndex:"ckdw"},
					{header: "姓名",   align:"center", sortable:true, dataIndex:"xm"},
					{header: "性别",   align:"center", sortable:true, dataIndex:"xb"},
					{header: "身份证号",   align:"center", sortable:true, dataIndex:"sfzjh"},
					{header: "考试时间",   align:"center", sortable:true, dataIndex:"kssj"},
					{header: "考点名称",   align:"center", sortable:true, dataIndex:"kdmc"},
					{header: "考场名称",   align:"center", sortable:true, dataIndex:"kcmc"},
					{header: "座位号",   align:"center", sortable:true, dataIndex:"zwh"},
					{header: "分数",   align:"center", sortable:true, dataIndex:"score"},
					{header: "是否合格",   align:"center", sortable:true, dataIndex:"sfhg"},
					{header: "违纪情况",   align:"center", sortable:true, dataIndex:"wj"},
					{header: "备注",   align:"center", sortable:true, dataIndex:"bz"}
		];				
		this.grid = new Ext.ux.GridPanel({
					cm : cm,
					sm : sm,
					title : "考试成绩-成绩录入",
					tbar : [ 
							 "->", {xtype : "button",text : "导入违纪情况",iconCls : "p-icons-upload",handler : this.exportwj,scope : this}
							,"->", {xtype : "button",text : "导入考生信息更正情况",iconCls : "p-icons-upload",handler : this.exportxxgz,scope : this}
					        ,"->", {xtype : "button",text : "生成xls跟zip",iconCls : "p-icons-upload",handler : this.submitCj,scope : this}
							,"->", {xtype : "button",text : "生成成绩查询",iconCls : "p-icons-download",handler : this.submitCjSearch,scope : this}
							,"->", {xtype:"button",text:"下载考试信息更正模板",id:"gzmb",iconCls:"p-icons-download",handler:this.downloadxxgzTemplate,scope:this}
							,"->", {xtype:"button",text:"下载违纪模板",id:"wjmb",iconCls:"p-icons-download",handler:this.downloadwjTemplate,scope:this}
							,"->", {xtype:"button",text:"导出考生试卷",id:"dcsj",iconCls:"p-icons-download",handler:this.exportXls,scope:this}
							
						],
					page : true,
					rowNumber : true,
					region : "center",
					excel : true,
					action : "exam_getscoreList.do",
					excelTitle : "成绩导入模板",
					fields : ["zkdw","ckdw","xm","xb","sfzjh","kssj","kdmc","kcmc","zwh","score","sfhg","wj","bz"],
					border : false
		});
				
		//搜索区域
		var xm_kh_xjh_find = new Ext.form.TextField({fieldLabel:"",id:"xm_kh_xjh_find",name:"xm_kh_xjh_find",maxLength:50,width:150});
		var cx = new Ext.Button({x : 17,y : -10,cls : "base_btn",text : "查询",handler : this.selectRound,scope : this});
		var cz = new Ext.Button({x : 87,y : -10,cls : "base_btn",text : "重置",handler : function() {this.search.getForm().reset();},scope : this});

		this.search = new Ext.form.FormPanel({
					region : "north",
					height : 90,
					items : [{
							layout : 'form',
							xtype : 'fieldset',
							style : 'margin:10 10',
							title : '查询条件',
							items : [{
									xtype : "panel",
									layout : "table",
									layoutConfig : {
										columns : 7
									},
									baseCls : "table",
									items : [
											{html : "身份证：",baseCls : "label_right",width : 120}, 
											{items : [xm_kh_xjh_find],baseCls : "component",width : 210}, 
											{layout : "absolute",items : [cx, cz],baseCls : "component_btn",width : 160}
										]
								}]
							}]
		})

		this.submitForm = new Ext.ux.FormPanel({
				fileUpload : true,
				frame : true,
				enctype : 'multipart/form-data',
				defaults : {
					xtype : "textfield",
					anchor : "95%"
				},
				items:[{fieldLabel:"文件",xtype:"textfield",name:"upload",inputType:'file'}]
		});
		this.panel=new Ext.Panel({
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			items:[this.search,this.grid]
		});
		this.myTabs = new Ext.TabPanel({
		    id:'myTabsR',
		    region:'center',
		    margins:'0 5 0 0',
		    activeTab: 0,
		    enableTabScroll:true,
		    minTabWidth:135,
		    resizeTabs:true,
		    headerStyle:'display:none',
		    border: false,
		    tabWidth:135,
		    border:false,
		    items:[{layout: 'fit', index: 0, border: false, items: [this.panel],title:"成绩修正审核"}]
		});
	},
	/** 初始化界面 * */
	initFace : function() {
		this.panel_top=new Ext.Panel({
			layout:"fit",
    		id:"panel_topR",
    		region:"north",
    		border:false,
    		items:[this.myTabs]
    	});
    	this.addPanel({layout:"fit",items:[this.panel_top]});
	},
	initQueryDate : function() {
		this.grid.$load();
	},
	 exportwj:function(){
    	//导入违纪
    	var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",id:"uploadButton",handler:this.exportFilesInfo,scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.fileUpWin.hide()},scope:this});
     	this.fileUpWin = new Ext.ux.Window({
     		title:"文件上传",	
     		width:400,     		
     		tbar:{cls:"ext_tabr",items:["->",_b_cancel,"->",_b_save]},
     		listeners:{
		 		hide:function(){
		 			this.submitForm.form.reset();
		 		},scope:this},
     		items:[this.submitForm]
     	});
     	this.fileUpWin.show(null,function(){},this);
    },
	 exportxxgz:function(){
    	//导入信息更正
    	var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",id:"uploadButton",handler:this.exportxxgzFilesInfo,scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.fileUpWin.hide()},scope:this});
     	this.fileUpWin = new Ext.ux.Window({
     		title:"文件上传",	
     		width:400,     		
     		tbar:{cls:"ext_tabr",items:["->",_b_cancel,"->",_b_save]},
     		listeners:{
		 		hide:function(){
		 			this.submitForm.form.reset();
		 		},scope:this},
     		items:[this.submitForm]
     	});
     	this.fileUpWin.show(null,function(){},this);
    },
	
	exportFilesInfo : function() {
		var filePath = this.submitForm.getForm().findField("upload").getRawValue();
		// 判断文件类型
		var objtype = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
		var fileType = new Array(".xls");
		if (filePath == "") {
			alert("请选择文件！");
			return false;
		} else {
			if (!/\.(xls|xlsx)$/.test(objtype)) {
				alert("文件类型必须是.xls中的一种!")
				return false;
			}
		}
	    	this.submitForm.$submit({
    		timeout:3600000,
    		async:false,
    		action:"exam_exportKswj.do",
    		params:{
				form:this.submitForm.getForm().findField("upload")
				
			},			
			success: function(form, action) {
			       Ext.Msg.alert("提示",action.result.msg);
			       this.fileUpWin.hide();
				   this.grid.$load();
			    },
			failure: function(form, action) { 
				var that = this;
				   Ext.Msg.alert("提示",action.result.msg,function(b,t){
					   that.fileUpWin.hide();
					   that.grid.$load();  
					   if(action.result.data!=null && action.result.data!=''){
						   window.open(Ext.get("ServerPath").dom.value+"/"+action.result.data.replace(/\\/g, "\/"));
						}
				   });
			   },
			scope:this
		});    		    	    	   	
   
  },
  
	exportxxgzFilesInfo : function() {
		var filePath = this.submitForm.getForm().findField("upload").getRawValue();
		// 判断文件类型
		var objtype = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
		var fileType = new Array(".xls");
		if (filePath == "") {
			alert("请选择文件！");
			return false;
		} else {
			if (!/\.(xls|xlsx)$/.test(objtype)) {
				alert("文件类型必须是.xls中的一种!")
				return false;
			}
		}
	    	this.submitForm.$submit({
    		timeout:3600000,
    		async:false,
    		action:"exam_exportKsxxgz.do",
    		params:{
				form:this.submitForm.getForm().findField("upload")
				
			},			
			success: function(form, action) {
			       Ext.Msg.alert("提示",action.result.msg);
			       this.fileUpWin.hide();
				   this.grid.$load();
			    },
			failure: function(form, action) { 
				var that = this;
				   Ext.Msg.alert("提示",action.result.msg,function(b,t){
					   that.fileUpWin.hide();
					   that.grid.$load();  
					   if(action.result.data!=null && action.result.data!=''){
						   window.open(Ext.get("ServerPath").dom.value+"/"+action.result.data.replace(/\\/g, "\/"));
						}
				   });
			   },
			scope:this
		});    		    	    	   	
   
  },
	
   downloadxxgzTemplate:function(){
	   var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/xxgz.xls";
	   window.open(path);   		
   },
   downloadwjTemplate:function(){
	   var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/wj.xls";
	   window.open(path);   		
   },	
	submitCj:function(){
		Ext.MessageBox.show({
			title:"消息",
			msg:"您确定要生成成绩xls跟zip包么?",
			buttons:Ext.MessageBox.OKCANCEL,
			icon:Ext.MessageBox.QUESTION,    			
			fn:function(b){
				if(b=='ok'){
					JH.$request({
						timeout: 36000000,
						params:{
							
						},
						action:"exam_getExamZkdwDetail.do",
						handler:function(){    							
							var responseArrayM = Ext.util.JSON.decode(response.responseText);  
							Ext.Msg.alert('消息',responseArrayM.msg); 
						}
					})    					
				}
			}
		})		
	},
	submitCjSearch:function(){
		Ext.MessageBox.show({
			title:"消息",
			msg:"您确定要生成成绩查询么?",
			buttons:Ext.MessageBox.OKCANCEL,
			icon:Ext.MessageBox.QUESTION,    			
			fn:function(b){
				if(b=='ok'){
					JH.$request({
						timeout: 3600000,
						params:{
							
						},
						action:"exam_createScore.do",
						handler:function(){    							
							var responseArrayM = Ext.util.JSON.decode(response.responseText);  
							Ext.Msg.alert('消息',responseArrayM.msg); 
						}
					})    					
				}
			}
		})		
	},
	
	
	 exportXls:function(){
	    	Ext.Msg.wait("正在导出","提示");
	    	var xm_kh_xjh=Ext.getCmp('xm_kh_xjh_find').getValue();
	    	if(xm_kh_xjh == ""){
	    		Ext.MessageBox.alert("消息","身份证不能为空！");
				return;
	    	}
	    	 Ext.Ajax.request({
	    		url:"exam_exportPaper.do", 
	    		params:{ 
	    			'sfzjh':xm_kh_xjh,
				},
				scope:this,
				success: function (r, options) {
					var result =Ext.decode(r.responseText); 
					if(result.success){
						Ext.Msg.alert("提示",result.msg);
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
	    },
	selectRound :function(){
		var xm_kh_xjh=Ext.getCmp('xm_kh_xjh_find').getValue();
    	this.grid.$load({"sfzjh":xm_kh_xjh});
	},
	createExamupdateForm:function(){		
    	var kh = new Ext.form.TextField({fieldLabel:"",id:"kh",name:"kh",maxLength:50,width:170}); 
		//var xjh = new Ext.form.TextField({fieldLabel:"",id:"xjh",name:"xjh",maxLength:50,width:170});
		var items = new Array();		
		items [0] = {html:"考号：",baseCls:"label_right",width:150};
		items [1] = {html:"<font class=required color=red>*</font>", items:[kh],baseCls:"component",width:200};
		//items [2] = {html:"学号：",baseCls:"label_right",width:150};
		//items [3] = {html:"<font class=required color=red>*</font>",items:[xjh],baseCls:"component",width:200};
		
		var count = 2
		for (var i = 0; i < responseArray.length; i++) {
		 	items [count] = {html:responseArray[i].KM+"分数：",baseCls:"label_right",width:150};
		 	count++;
		 	items [count] = {html:"<font class=required color=red>*</font>", items:[new Ext.form.TextField({id:responseArray[i].KM,name:responseArray[i].KM,maxLength:50,width:170})],baseCls:"component",width:200};
			count++;
		}
			
		return new Ext.ux.FormPanel({
			labelWidth:75,
			height : 250,
			frame:false,
			defaults:{anchor:"90%"},
			items:{
				xtype:"panel",
				layout:"table", 
				layoutConfig: {
					columns: 4
				}, 
				baseCls:"table",
				items:items
			}
		});
    },    
    createWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addExamupdate,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.editUpWindow.hide();isUpdate = "No";},scope:this});
		return new Ext.ux.Window({
				width:750,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
			 	title:"增加"});			
    },
	fanhui :function(){
		this.initComponent();
    	var panel=Ext.getCmp("panel_topR");
  		panel.remove(Ext.getCmp("panel_topR1"));
  		panel.add(this.myTabs);
  		panel.doLayout(false);
  		this.grid.$load();
		//window.location.href=Ext.get("ServerPath").dom.value+'/jsp/main.jsp?module=000601';
	}
});

var syncRequest = function(url) {	
	var conn = Ext.lib.Ajax.getConnectionObject().conn;     
	try {     
		conn.open("POST", url, false);
		conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		conn.send(null);     
	}catch (e) {     
		Ext.Msg.alert('info','error');     
		return false;     
	}     
	responseArray = Ext.decode(conn.responseText);     
}