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
    	this.grid.on("rowdblclick",function(grid , rowIndex, e ){
    		this.editWindow.show(null,function(){
    			var selectedBuildings = this.grid.getSelectionModel().getSelections();
    			var xxid = selectedBuildings[0].get("XXID");
				var xxmc = selectedBuildings[0].get("XXMC");
				var xxdm = selectedBuildings[0].get("XXDM");
				Ext.getCmp("xxmc").setValue(xxmc);
				Ext.getCmp("xxdm").setValue(xxdm);    			
    		},this)
    	},this)
    },   
   	initComponent :function(){   		
   		this.editForm   = this.createExamroundForm();
   		this.editWindow = this.createWindow();
   		this.editWindow.add({items:[this.editForm]});
		
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});		
		var cm = [sm,
				{header : "参考单位代码",align : "center",sortable : true,dataIndex : "XXDM"},
				{header : "参考单位名称",align : "center",sortable : true,dataIndex : "XXMC"},
				{header : "校长姓名",align : "center",sortable : true,dataIndex : "XZXM"}, 
				{header : "邮政编码",align : "center",sortable : true,dataIndex : "YZBM"},
				{header : "参考单位地址",align : "center",sortable : true,dataIndex : "XXDZ"}
			];
	
		this.grid = new Ext.ux.GridPanel({
			id:'grid',
			cm:cm,
			sm:sm,
			title:"日常管理-生成参考单位代码",
			tbar:{
				cls:"ext_tabr",
				items:[ 
				       "->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateBuilding,scope:this}
				       ,"->",{xtype:"button",text:"生成",iconCls:"p-icons-refresh",handler:this.generate,scope:this}			  
				      ]
			},
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			excelTitle:"参考单位代码表",
			action:"generateschoolcode_getListPage.do",
			fields :["XXID", "XXDM", "XXMC", "XZXM","YZBM","XXDZ"],
			border:false
		});
		
		var xuexiao_find= new Ext.ux.form.OrganField({name:"sup_organ_sel",width:200,readOnly:true});
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
								columns : 3
							},
							baseCls : "table",
							items : [
							         {html : "参考单位：",baseCls : "label_right",width : 120}, 
							         {items : [xuexiao_find],baseCls : "component",width : 200}, 
							         {layout : "absolute",items : [cx, cz],baseCls : "component_btn",width : 160}
							        ]
						}]
					}]
			})			
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout : "border",items : [this.search, this.grid]});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },    
    createExamroundForm:function(){		
    	var xxmc = new Ext.form.TextField({fieldLabel:"",id:"xxmc",name:"xxmc",maxLength:50,width:200}); 
		var xxdm = new Ext.form.TextField({fieldLabel:"",id:"xxdm",name:"xxdm",maxLength:50,width:200});
		
		return new Ext.ux.FormPanel({
			labelWidth:75,
			frame:false,
			defaults:{anchor:"95%"},items:{
			xtype:"panel",
			layout:"table", 
			layoutConfig: { 
				columns: 2
			}, 
			baseCls:"table",
			items:[
				{html:"参考单位名称：",baseCls:"label_right",width:100},
				{html:"<font class=required color=red>*</font>", items:[xxmc],baseCls:"component",width:250},
				{html:"参考单位代码：",baseCls:"label_right",width:100},
				{html:"<font class=required color=red>*</font>",items:[xxdm],baseCls:"component",width:250}
			]
		}});
    },    
    createWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.update,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.editWindow.hide();isUpdate = "No";},scope:this});
		return new Ext.ux.Window({
				id:'windowUp',
			 	width:400,
			 	tbar:{
					cls:"ext_tabr",
					items:["->",cancel,"->",save]
				},
			 	title:"修改"});	
    },    
    updateBuilding:function(){    		
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
    	}
    	var xxdm = selectedBuildings[0].get("XXDM");
    	if(xxdm == ""){
			Ext.MessageBox.alert("消息","请先生成参考单位代码！");
			return;
		}
		this.editWindow.show(null,function(){
			var xxid = selectedBuildings[0].get("XXID");
			var xxmc = selectedBuildings[0].get("XXMC");
			var xxdm = selectedBuildings[0].get("XXDM");
			Ext.getCmp("xxmc").setValue(xxmc);
			Ext.getCmp("xxdm").setValue(xxdm);
			Ext.getCmp('xxmc').disable();
		},this)
    },
    update : function(){
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var xxid = selectedBuildings[0].get("XXID");
    	var xxcd = Ext.getCmp("xxdm").getValue();
    	 var re = /^([0-9]\d*)$/;
    	if(xxcd < 0 || xxcd > 999 || !re.test(xxcd)){
    		Ext.Msg.alert('消息',"请输入0-999之间的正整数");
    		return;
    	}
		this.editForm.$submit({
			action:'generateschoolcode_update.do',
			params: {    
				'xxids':xxid
	 		},    
    		handler:function(form,result,thiz){
    			thiz.editWindow.hide();
    			thiz.grid.$load();
    		},
    		scope:this
    	});
    },
    selectRound :function(){
		var schools = this.search.getForm().findField('sup_organ_sel').getCodes();
		this.grid.$load({"xxids":schools});
	},	
	generate :function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length < 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}
    	var xxids = "";
    	for(var i=0;i<selectedBuildings.length;i++){
    		xxids += selectedBuildings[i].get("XXID") +",";
    	}
		/*if(xxids == ""){
			Ext.Msg.alert('消息',"该组织单位下无直属参考单位！");
			return;
		}*/		
		Ext.Ajax.request({    	   
			 url: 'generateschoolcode_generate.do',
			 params: {    
				 'xxids':xxids
			 },    
			 success: function(response, options) {     
				 var responseArray = Ext.util.JSON.decode(response.responseText);  
				 Ext.Msg.alert('消息',responseArray.msg);        
  				 var grid = Ext.getCmp("grid")
  				 grid.getStore().reload();
			}    
		});
	}
});
