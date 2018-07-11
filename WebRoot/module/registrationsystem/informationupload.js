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
		this.mainPanel = this.createMainPanel();
//		this.informationForm = this.createInformationForm();
//   		this.informationWindow = this.createInformationWindow();
//   		this.informationWindow.add(this.informationForm);
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
   		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.mainPanel]   
        }); 
	},
	/** 初始化界面 **/
    initFace:function(){
    	this.addPanel(this.tabPanel);
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
    
    createMainPanel:function(){
    	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
    
		var cm = [
		    sm,	
			{header: "年度",   		align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   		align:"center", sortable:true, dataIndex:"XQ"},
			{header: "组织单位",   	align:"center", sortable:true, dataIndex:"MC"},
			{header: "参考单位",   	align:"center", sortable:true, dataIndex:"OLDXXMC"},
			{header: "姓名",   		align:"center", sortable:true, dataIndex:"OLDXM"},
			{header: "性别",   		align:"center", sortable:true, dataIndex:"OLDXB"},
			{header: "身份证",   		align:"center", sortable:true, dataIndex:"OLDSFZJH"},
			{header: "申请修改姓名",   		align:"center", sortable:true, dataIndex:"NEWXM"},
			{header: "申请修改性别",   		align:"center", sortable:true, dataIndex:"NEWXB"},
			{header: "申请修改身份证",   		align:"center", sortable:true, dataIndex:"NEWSFZJH"},
			{header: "申请修改参考单位",   	align:"center", sortable:true, dataIndex:"NEWXXMC"},
			{header: "申请时间",   	align:"center", sortable:true, dataIndex:"CDATE"},
			{header: "申请状态",   align:"center", sortable:true, dataIndex:"STATUE"}
		];		
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"考生信息变更",
			tbar:{

			  	cls:"ext_tabr",
				items:[ 
			     
			      "->",{xtype:"button",text:"下载信息更正表模板",id:"xz",iconCls:"p-icons-download",handler:this.downloadTemplate,scope:this}
			      ,"->", {xtype : "button",text : "导入考生信息更正表",iconCls : "p-icons-upload",handler : this.exportxxgz,scope : this}
			    ]},
			    page:true,
			    rowNumber:true,
			    region:"center",
			    excel:true,
			    action:"uploadinfor_getListPage.do",
			    fields :["XN","XQ","OLDXM","OLDXXMC","CDATE","STATUE","MC","OLDXB","OLDSFZJH","NEWXM","NEWXB","NEWSFZJH","NEWXXMC"],
			    border:false
		});
	
		//搜索区域		
		var xn_find=new Ext.ux.form.XnxqField({width:180,id:"exrxn_find",width:210,readOnly:true});
		var sfzjh_find = new Ext.form.TextField({fieldLabel:"",id:"sfzjh_find",name:"sfzjh_find",width:180});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectInformation,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
			
		this.search = new Ext.form.FormPanel({
			region: "north",
			height:90,
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
							{html:"年度：",baseCls:"label_right",width:120},
							{items:[xn_find],baseCls:"component",width:210},
							{html:"身份证：",baseCls:"label_right",width:120},
							{items:[sfzjh_find],baseCls:"component",width:210},
							{layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
						] 
				}]  
			}]  
	    })
		return new Ext.Panel({layout:"border",region:"center",items:[this.search,this.grid]})
    },
    
//    createInformationForm:function(){
//    	var oldxm = new Ext.form.TextField({id:"oldxm",name:"oldxm",maxLength:200, width:200,allowBlank:false});
//    	var oldsex = new Ext.form.ComboBox({
//				name:'oldsex',
//				mode: 'local', 
//		    triggerAction: 'all',   
//		    editable:false,
//		    width:200,
//		    store: new Ext.data.ArrayStore({
//		        id: 0,
//		        fields: [
//		                 'value',
//		                 'text'
//		                ],
//		        data: [['1', '男'], ['2', '女']]
//		    }),
//		    valueField: 'value',
//		    displayField: 'text'
//		});
//    	var oldsfzjh = new Ext.form.TextField({id:"oldsfzjh",name:"oldsfzjh",maxLength:200, width:200,allowBlank:false});
//    	var oldxxmc = new Ext.form.TextField({id:"oldxxmc",name:"oldxxmc",maxLength:200, width:200,allowBlank:false});
//    	var newxm = new Ext.form.TextField({id:"newxm",name:"newxm",maxLength:200, width:200});
//    	var newsex = new Ext.form.ComboBox({
//			name:'newsex',
//			mode: 'local', 
//	    triggerAction: 'all',   
//	    editable:false,
//	    width:200,
//	    store: new Ext.data.ArrayStore({
//	        id: 0,
//	        fields: [
//	                 'value',
//	                 'text'
//	                ],
//	        data: [['1', '男'], ['2', '女']]
//	    }),
//	    valueField: 'value',
//	    displayField: 'text'
//    	});
//    	var newsfzjh = new Ext.form.TextField({id:"newsfzjh",name:"newsfzjh",maxLength:200, width:200});
//    	var newxxmc = new Ext.form.TextField({id:"newxxmc",name:"newxxmc",maxLength:200, width:200});
//    	var panel = new Ext.Panel({ 
//			xtype:"panel",
//			layout:"table", 
//			baseCls:"table",
//			layoutConfig: { 
//				columns: 4 
//			}, 
//			items:[
//				{html:"原始姓名：",baseCls:"label_right",width:212},
//				{html:"<font class='required'>*</font>",items:[oldxm],baseCls:"component",width:230},
//				{html:"原始性别:",baseCls:"label_right",width:212},
//				{html:"<font class='required'>*</font>",items:[oldsex],baseCls:"component",width:230},	
//				{html:"原始身份证号：",baseCls:"label_right",width:212}, 
//				{html:"<font class='required'>*</font>",items:[oldsfzjh],baseCls:"component",width:230},
//				{html:"原始参考单位：",baseCls:"label_right",width:212},
//				{html:"<font class='required'>*</font>",items:[oldxxmc],baseCls:"component",width:230},				
//				{html:"修改姓名：",baseCls:"label_right",width:212},
//				{items:[newxm],baseCls:"component",width:230},
//				{html:"修改性别:",baseCls:"label_right",width:212},
//				{items:[newsex],baseCls:"component",width:230},	
//				{html:"修改身份证号：",baseCls:"label_right",width:212}, 
//				{items:[newsfzjh],baseCls:"component",width:230},
//				{html:"修改参考单位：",baseCls:"label_right",width:212},
//				{items:[newxxmc],baseCls:"component",width:230}
//			]
//		});		
//		return new Ext.ux.FormPanel({
//			frame:false,
//			defaults:{anchor:"100%"},
//			items:[
//				{layout:'form',width:950, xtype:"fieldset",title:"申请考生信息变更",items:[panel]}
//			]});
//    },
//    
//    createInformationWindow:function(){
//    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.saveInformation,scope:this});
//    	
//		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.informationWindow.hide()},scope:this});
//		
//		return new Ext.ux.Window({
//			width:980,
//		 	height:250,
//			 	tbar:{
//					cls:"ext_tabr",
//					items:[ 
//					 	 "->",cancel
//			 			 ,"->",save
//					  ]
//				},
//				listeners:{
//			 		hide:function(){
//			 			this.informationForm.form.reset();
//			 		},scope:this},
//	     		items:[this.informationForm],
//			 	title:"考生信息变更"});	
//   },
    exportxxgz:function(){
    	
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
    		action:"uploadinfor_exportKsxxgz.do",
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
    selectInformation:function(){
    	var xn=Ext.getCmp('exrxn_find').getValue();
    	var sfzjh=Ext.getCmp('sfzjh_find').getValue();
		this.grid.$load({"xnxq":xn,"sfzjh":sfzjh});
    },
    
    downloadTemplate:function(){
 	   var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/examregistratio.xls";
 	   window.open(path);   		
    }
});