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
			{header: "修改时间",   	align:"center", sortable:true, dataIndex:"UPDATEDATE"}
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
			      ,"->", {xtype:"button",text:"下载违纪模板",id:"wjmb",iconCls:"p-icons-download",handler:this.downloadwjTemplate,scope:this}
			      ,"->", {xtype : "button",text : "导入违纪情况",iconCls : "p-icons-upload",handler : this.exportwj,scope : this}
			    ]},
			    page:true,
			    rowNumber:true,
			    region:"center",
			    excel:true,
			    action:"uploadinfor_getListPage.do",
			    fields :["XN","XQ","OLDXM","OLDXXMC","UPDATEDATE","MC","OLDXB","OLDSFZJH","NEWXM","NEWXB","NEWSFZJH","NEWXXMC"],
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
    		action:"uploadinfor_exportKswj.do",
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
    
    downloadwjTemplate:function(){
 	   var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/wj.xls";
 	   window.open(path);   		
    },
    
    downloadTemplate:function(){
 	   var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/ksxxgz.xls";
 	   window.open(path);   		
    }
});