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
    	this.editWindow.on("hide",function(){
    		this.editForm.$reset();
    	},this);
    },
    initComponent :function(){
    	this.editForm   = this.createExamroundForm();
   		this.editWindow = this.createWindow();
   		this.editWindow.add({items:[this.editForm]});
    	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
		    sm,
		    {header: "所属单位",   align:"center", sortable:true, dataIndex:"XXMC"},
		    //{header: "科目",   align:"center", sortable:true, dataIndex:"BJMC"},
		    //{header: "等级",   align:"center", sortable:true, dataIndex:"NJMC"},
			{header: "考生姓名",   align:"center", sortable:true, dataIndex:"XM"},
			{header: "通过年度",   align:"center", sortable:true, dataIndex:"BYND"},
			{header: "通过阶段",   align:"center", sortable:true, dataIndex:"BYJYJD"},
			{header: "通过时间",   align:"center", sortable:true, dataIndex:"BYJYNY"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"学业管理-导入通过考生名单",
			tbar:[ 
			      "->",{xtype:"button",text:"删除",id:"dl",iconCls:"p-icons-delete",handler:this.deleteCJ,scope:this}
			      ,"->",{xtype:"button",text:"修改",id:"xg",iconCls:"p-icons-update",handler:this.updateCJ,scope:this}
			      ,"->",{xtype:"button",text:"导入通过考生名单",iconCls:"p-icons-upload",handler:this.uploadCj,scope:this}
			      ,"->",{xtype:"button",text:"下载模板",iconCls:"p-icons-download",handler:this.downloadTemplate,scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			action:"academicGrade_getListPage.do",
			fields :["XS_XSBY_ID","XXMC","BJMC","NJMC","GRBSM","XM","BYND","BYJYJD","BYJYNY"],
			border:false
		});
	
		//搜索区域
		var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel",width:210,readOnly:true}); 
    	var organ_lable = "组织单位：";    				
    	this.submitForm = new Ext.ux.FormPanel({
    		fileUpload: true,
    		frame:true,
    		enctype: 'multipart/form-data',
    		defaults:{xtype:"textfield",anchor:"95%"},
    		items: [{fieldLabel:"附件",xtype:"textfield",name:"upload",inputType:'file'}]
    	});
	
    	var xjh_find = new Ext.form.TextField({fieldLabel:"姓名",id:"xjh_find",maxLength:200, width:190});
    	var cx = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamSubject,scope:this});
    	var cz = new Ext.Button({x:157,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
	 
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
						{html:organ_lable,baseCls:"label_right",width:120},
						{items:[organ_sel],baseCls:"component",width:210},
						{html:"姓名：",baseCls:"label_right",width:120},
						{items:[xjh_find],baseCls:"component",width:210},
						{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:260}
					] 
    			}]  
    		}]  
    	})
		this.panel=new Ext.Panel({
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			items:[this.search,this.grid]
		});
	},
	/** 初始化界面 **/
    initFace:function(){
    	this.panel_top=new Ext.Panel({
			layout:"fit",
    		region:"north",
    		border:false,
    		items:[this.panel]
    	});
    	this.addPanel({layout:"fit",items:[this.panel_top]});
    },
    initQueryDate:function(){
    	//this.grid.$load();
    },
	selectExamSubject:function(){ 
    	var organ_sel = this.search.getForm().findField('organ_sel').getCodes();		
		var xmxjh=Ext.getCmp('xjh_find').getValue();
		this.grid.$load({"schools":organ_sel,"xmxjh":xmxjh});
	},
	createExamroundForm:function(){
		var byjyny = new Ext.form.DateField({width:150,name:"byjyny",editable:false,format:"Ymd",emptyText:"请选择日期..."});
		var byjyjd = new Ext.form.TextField({id:"byjyjd",maxLength:150,width:150});
		return new Ext.ux.FormPanel({
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
				       {html:"通过时间：",baseCls:"label_right",width:100},
				       {items:[byjyny],baseCls:"component",width:160},
				       {html:"通过阶段：",baseCls:"label_right",width:100},
				       {items:[byjyjd],baseCls:"component",width:160}				       
				     ]
			} 
		});
    },
    createWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.update,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.editWindow.hide()},scope:this});
		return new Ext.ux.Window({
				width:290,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
			 	title:"修改信息"});	
    },
	updateCJ:function(){
		var sel  = this.grid.getSelectionModel().getSelections();
		if(sel.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
		var byjyny = sel[0].get("BYJYNY");
		var byjyjd = sel[0].get("BYJYJD");
		this.editForm.getForm().findField("byjyny").setValue(byjyny);
		Ext.getCmp("byjyjd").setValue(byjyjd);
		this.editWindow.show(null,function(){},this);
	},
	update:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
		var tgid = selectedBuildings[0].get("XS_XSBY_ID");
		var byjyny = this.editForm.getForm().findField("byjyny").getValue();		
		var byjyjd = Ext.getCmp("byjyjd").getValue();
		var re = /^([A-Za-z0-9]\d*)$/;
		var d = new Date();
		var todayStr = '';
		todayStr+=d.getFullYear();
		todayStr+=d.getMonth()+1;
		todayStr+=d.getDate();		
    	if(byjyjd.length<1||!re.test(byjyjd)||byjyjd.length>1){
    		Ext.Msg.alert('消息',"请输入不超过一位数的正整数或字母！");
    		return;
    	}    	
    	if (byjyny!="") {
    		byjyny = byjyny.format('Ymd');
    		if (byjyny>todayStr) {
        		Ext.Msg.alert('消息',"通过日期大于当前日期！");
        		return;
        	}
    	}    	
    	this.editForm.$submit({
			action:'academicGrade_updateCJ.do',
			params: {    
			 	'tgid':tgid,'byjyny':byjyny,'byjyjd':byjyjd
	 		},    
    		handler:function(form,result,thiz){
    			thiz.editWindow.hide();
    			thiz.selectExamSubject();
    		},
    		scope:this
    	});
	},
	deleteCJ:function(){
		var thiz=this;
		var selectedBuildings  = this.grid.getSelectionModel().getSelections();
		if(selectedBuildings.length <= 0){
    		Ext.MessageBox.alert("消息","请选择需要删除的记录！");
    		return;
    	}
		var tgids = "";
    	for(var i = 0; i < selectedBuildings.length; i++) {
    		if(i != selectedBuildings.length - 1) {
    			tgids += selectedBuildings[i].get("XS_XSBY_ID") + ",";
    		}else {
    			tgids += selectedBuildings[i].get("XS_XSBY_ID");
    		}
    	}
    	Ext.MessageBox.confirm('提示', '确定删除吗？', function(id){
    		if(id=="yes"){	
				Ext.Ajax.request({   
			    	url:'academicGrade_deleteCJ.do',
			       	params:{
			       		'tgid':tgids
			        },
			        success: function(resp,opts) {
			        	var respText = Ext.util.JSON.decode(resp.responseText);
			           	Ext.MessageBox.alert("提示",respText.msg);
			           	thiz.selectExamSubject();
			        },
			        failure: function(resp,opts){
			            Ext.Msg.alert('错误', "删除失败！");
			        }  
			    });
    		}
    	});    
	},
	uploadCj:function(){
		var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",handler:this.exportFilesInfo,scope:this});
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
	exportFilesInfo:function(){
		var filePath     = this.submitForm.getForm().findField("upload").getRawValue();
		//判断文件类型
		var objtype=filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
		var fileType=new Array(".xls",".xlsx");
		if(filePath==""){ 
			alert("请选择文件！");return false;
		}else{
			if(!/\.(xls|xlsx)$/.test(objtype)){
				alert("文件类型必须是.xlsx,.xls中的一种!")
				return false;
			}
		}
	    this.submitForm.$submit({
	    	action:"academicGrade_exportExcelFile.do",
	    	params:{
				form:this.submitForm.getForm().findField("upload")
			},
			scope:this,
			handler:function(form,result,scope){
				scope.fileUpWin.hide();
				scope.selectExamSubject();
			}
	    });	
   },
   downloadTemplate:function(){
	   	var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/finalscore.xls";
	   	window.open(path);   		
   }
});