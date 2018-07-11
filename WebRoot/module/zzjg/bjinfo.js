var wjcllx = [['0', '不可用'],['1', '可用']];
var wjcllxStore = new Ext.data.SimpleStore({  
	fields : ['value', 'text'],  
	data : wjcllx  
});
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
    	this.grid.on("rowdblclick",function(grid, rowIndex, e ){
    		var xxmc = grid.store.getAt(rowIndex).get("XXMC");
    		var nj = grid.store.getAt(rowIndex).get("XX_NJXX_ID");
    		var bjmc = grid.store.getAt(rowIndex).get("BJMC");    		
    		var bjzt = grid.store.getAt(rowIndex).get("YXBZ");
    		Ext.getCmp("xxmc").setValue(xxmc);    		
    		Ext.getCmp("bjmc").setValue(bjmc);    
    		this.editForm.getForm().findField("nj").setValue(nj);
    		this.editForm.getForm().findField("bjzt").setValue(bjzt);
    		this.editWindow.show(null,function(){},this)    		    		
    	},this);
    },   
   	initComponent :function(){
   		this.editForm   = this.createBjForm();
   		this.editWindow = this.createWindow();
   		this.editWindow.add({items:[this.editForm]});
   		
   		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [sm,
		          {header: "参考单位",   	align:"center", sortable:true, dataIndex:"XXMC"},
		          {header: "等级",   	align:"center", sortable:true, dataIndex:"NJMC"},
		          {header: "科目",   	align:"center", sortable:true, dataIndex:'BJMC'},
		          {header: "状态",   	align:"center", sortable:true, dataIndex:"ZT"}];
   		
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:'科目信息表',
			id:"gd",
			page:true,
			rowNumber:true,
			tbar:[
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.returnCkdw,scope:this}
			      ,"->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteBj,scope:this}
			      ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateBj,scope:this}
			      ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.showEidtWindow()},scope:this}			      			      
			   ],
			region:"center",
			action:'bj_getListPage.do',
			fields:["XX_BJXX_ID","XX_JBXX_ID","XX_NJXX_ID","BH","BJMC","YXBZ","XXMC","ZT","NJMC"],
			border:false
		});
		//搜索区域
		var kc_sel		= new Ext.ux.form.TextField({name:"kc_sel",id:"kc_sel",width:180});
    	var cx 			= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.bjInfoShow,scope:this});
		var cz 			= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.search = new Ext.form.FormPanel({
			id:"search_form",
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
						columns: 3
					}, 
					baseCls:"table",
					items:[
					       {html:"科目：",baseCls:"label_right",width:170},
					       {items:[kc_sel],baseCls:"component",width:200},
					       {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
					      ] 
                  	}]  
		      	}]  
	    });
	},
	bjInfoShow:function(){
		var xx_jbxx_id = getLocationPram("xx_jbxx_id");
		this.grid.$load({"xx_jbxx_id":xx_jbxx_id});
	},
	createBjForm:function(){
		var xxmc = new Ext.form.TextField({id:"xxmc",maxLength:50,width:210,readOnly:true}); 
    	var nj   = new Ext.ux.Combox({autoLoad:true,name:"nj",dropAction:"njBySchool",params:getLocationPram("xx_jbxx_id"),width:210,allowBlank:false,blankText:"等级名称为必选项！"});
    	var bjmc = new Ext.form.TextField({id:"bjmc",maxLength:50,width:210,allowBlank:false,blankText:"科目名称为必选项！"});
    	var bjzt = new Ext.form.ComboBox({name:"bjzt",store:wjcllxStore,value:'1',displayField:'text',valueField:'value',mode:'local',triggerAction:'all',width:210,editable:false,allowBlank:false,blankText:"状态为必选项！"});
	
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
				       {html:"单位名称：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>", items:[xxmc],baseCls:"component",width:250},
				       {html:"等级名称：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>",items:[nj],baseCls:"component",width:250},
				       {html:"科目名称：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>",items:[bjmc],baseCls:"component",width:250},
				       {html:"状态：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>",items:[bjzt],baseCls:"component",width:250}
				      ]
			} 
		});
	},
	createWindow:function(){		
		var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.update,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.editWindow.hide()},scope:this});
		return new Ext.ux.Window({				
			width:400,
		 	height:200,
		 	tbar:{
				cls:"ext_tabr",
				items:[ 
				 	 "->",cancel
		 			 ,"->",save
				  ]
			},
		 	title:"科目信息维护"});		
	},	
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"border",items:[this.search,this.grid]});
    },
    initQueryDate:function(){
    	this.grid.$load({"xx_jbxx_id":getLocationPram("xx_jbxx_id")});
    },
    showEidtWindow:function() {
    	var xxmc = this.grid.store.getAt(0).get("XXMC");
    	Ext.getCmp('xxmc').setValue(xxmc);
    	Ext.getCmp("bjmc").setValue("");    
		this.editForm.getForm().findField("nj").setValue("");
		this.editForm.getForm().findField("bjzt").setValue("");
    	this.editWindow.show(null,function(){},this);
    },
	updateBj:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
    	}
    	var xxmc = selectedBuildings[0].get("XXMC");
		var nj = selectedBuildings[0].get("XX_NJXX_ID");
		var bjmc = selectedBuildings[0].get("BJMC");    		
		var bjzt = selectedBuildings[0].get("YXBZ");
		Ext.getCmp("xxmc").setValue(xxmc);
		Ext.getCmp("bjmc").setValue(bjmc);    
		this.editForm.getForm().findField("nj").setValue(nj);
		this.editForm.getForm().findField("bjzt").setValue(bjzt);		    	
		this.editWindow.show(null,function(){},this)
    },
    update:function(){
    	var bjmc = Ext.getCmp("bjmc").getValue();
    	var xx_njxx_id = this.editForm.getForm().findField("nj").getValue();
    	var bjzt = this.editForm.getForm().findField("bjzt").getValue();    	
    	if(bjmc==""){
    		Ext.Msg.alert('消息',"科目名称不能为空！");
    		return;
    	}
    	if(bjzt==""){
    		Ext.Msg.alert('消息',"状态不能为空！");
    		return;
    	}
    	var thiz = this;
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if (selectedBuildings!=null&&selectedBuildings!='') {
    		var xx_bjxx_id = selectedBuildings[0].get("XX_BJXX_ID");
    		var xx_jbxx_id = selectedBuildings[0].get("XX_JBXX_ID");
    		this.editForm.$submit({
    			action:'bj_update.do',
    			params: {    
    				'xx_bjxx_id':xx_bjxx_id,'xx_jbxx_id':xx_jbxx_id,'bjmc':bjmc,'xx_njxx_id':xx_njxx_id,'bjzt':bjzt
    	 		},    
        		handler:function(form,result,thiz){
        			thiz.editWindow.hide();
        			thiz.grid.$load({"xx_jbxx_id":xx_jbxx_id});
        			thiz.grid.$load({"xx_jbxx_id":xx_jbxx_id});
        		},
        		scope:this
        	});    		        	
    	}else {
    		var xx_jbxx_id=getLocationPram("xx_jbxx_id");
    		this.editForm.$submit({
    			action:'bj_update.do',
    			params: {    
    				'xx_bjxx_id':'','xx_jbxx_id':xx_jbxx_id,'bjmc':bjmc,'xx_njxx_id':xx_njxx_id,'bjzt':bjzt
    	 		},    
        		handler:function(form,result,thiz){
        			thiz.editWindow.hide();
        			thiz.grid.$load({"xx_jbxx_id":xx_jbxx_id});
        			thiz.grid.$load({"xx_jbxx_id":xx_jbxx_id});
        		},
        		scope:this
        	});
    	}   	    	
    },
    deleteBj:function(){
    	var thiz = this;
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
		if(selectedBuildings.length <= 0){
    		Ext.MessageBox.alert("消息","请选择需要删除的记录！");
    		return;
    	}
		
		var xx_bjxx_id = selectedBuildings[0].get("XX_BJXX_ID");
		var xx_jbxx_id = selectedBuildings[0].get("XX_JBXX_ID");
		Ext.Msg.confirm('消息','确认删除这'+selectedBuildings.length+'条记录？', function (id) {
    		if (id == 'yes') {
    			Ext.Ajax.request({
    				url: 'bj_del.do',
    				params: {'xx_bjxx_id':xx_bjxx_id},
    				success: function(resp,opts) {
			        	var respText = Ext.util.JSON.decode(resp.responseText);
			           	Ext.MessageBox.alert("提示",respText.msg);
			           	thiz.grid.$load({"xx_jbxx_id":xx_jbxx_id});
			           	thiz.grid.$load({"xx_jbxx_id":xx_jbxx_id});			           	
			        },
			        failure: function(resp,opts){
			            Ext.Msg.alert('错误', "删除失败！");
			        }			            				
    			});                         
		    }});    	  			
    },    
	returnCkdw:function(){
		window.location.href = Ext.get("ServerPath").dom.value+"/jsp/main.jsp?module=000002";
	}
});
