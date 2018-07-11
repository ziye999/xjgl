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
    		var njmc = grid.store.getAt(rowIndex).get("NJMC");
    		var rxnf = grid.store.getAt(rowIndex).get("RXNF");
    		var jyjd = grid.store.getAt(rowIndex).get("JYJD");
    		var djdm = grid.store.getAt(rowIndex).get("XJNJDM");
    		var njzt = grid.store.getAt(rowIndex).get("NJZT");
    		Ext.getCmp("xxmc").setValue(xxmc);
    		Ext.getCmp("njmc").setValue(njmc);
    		Ext.getCmp("rxnf").setValue(rxnf);
    		Ext.getCmp("jyjd").setValue(jyjd);
    		Ext.getCmp("dj").setValue(djdm);
    		this.editForm.getForm().findField("njzt").setValue(njzt);
    		this.editWindow.show(null,function(){},this)    		    		
    	},this);
    },   
   	initComponent :function(){
   		this.editForm   = this.createNjForm();
   		this.editWindow = this.createWindow();
   		this.editWindow.add({items:[this.editForm]});
   		
   		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [sm,
		          {header: "参考单位",   	align:"center", sortable:true, dataIndex:"XXMC"},
		          {header: "等级名称",   	align:"center", sortable:true, dataIndex:"NJMC"},
		          {header: "年份",   	align:"center", sortable:true, dataIndex:'RXNF'},
		          {header: "学习阶段",   	align:"center", sortable:true, dataIndex:"JYJD"},
		          {header: "等级",   	align:"center", sortable:true, dataIndex:"DJMC"},
		          {header: "状态",   	align:"center", sortable:true, dataIndex:'ZT'}];
   		
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:'等级信息表',
			id:"gd",
			page:true,
			rowNumber:true,
			tbar:[ 
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.returnCkdw,scope:this}	
			      ,"->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteNj,scope:this}
			      ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateNj,scope:this}
			      ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.showEidtWindow()},scope:this}			      		      
			   ],
			region:"center",
			action:'nj_getListPage.do',
			fields:["XX_NJXX_ID","XX_JBXX_ID","NJMC","RXNF","JYJD","XJNJDM","NJZT","XXMC","ZT","DJMC"],
			border:false
		});
		//搜索区域
		var nj_sel		= new Ext.ux.form.TextField({name:"nj_sel",id:"nj_sel",width:180});
    	var cx 			= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.njInfoShow,scope:this});
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
					       {html:"等级名称：",baseCls:"label_right",width:170},
					       {items:[nj_sel],baseCls:"component",width:200},
					       {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
					      ] 
                  	}]  
		      	}]  
	    });
	},
	njInfoShow:function(){
		var xx_jbxx_id = getLocationPram("xx_jbxx_id");
		this.grid.$load({"xx_jbxx_id":xx_jbxx_id});
	},
	createNjForm:function(){
		var xxmc = new Ext.form.TextField({id:"xxmc",maxLength:50,width:210,readOnly:true}); 
    	var njmc = new Ext.form.TextField({id:"njmc",maxLength:50,width:210,allowBlank:false,blankText:"等级名称为必选项！"});
    	var rxnf = new Ext.form.TextField({id:"rxnf",maxLength:4,width:210,allowBlank:false,blankText:"年份为必选项！"});
    	var jyjd = new Ext.form.TextField({id:"jyjd",maxLength:1,width:210,allowBlank:false,blankText:"学习阶段为必选项！"});
    	var dj	= new Ext.ux.Combox({id:"dj",dropAction:"sys_enum_dj",width:210,allowBlank:false,blankText:"等级为必选项！"});
    	var njzt = new Ext.form.ComboBox({name:"njzt",store:wjcllxStore,value:'1',displayField:'text',valueField:'value',mode:'local',triggerAction:'all',width:210,editable:false,allowBlank:false,blankText:"状态为必选项！"});

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
				       {html:"<font class=required color=red>*</font>", items:[njmc],baseCls:"component",width:250},
				       {html:"年份：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>",items:[rxnf],baseCls:"component",width:250},
				       {html:"学习阶段：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>",items:[jyjd],baseCls:"component",width:250},
				       {html:"等级：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>",items:[dj],baseCls:"component",width:250},
				       {html:"状态：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>",items:[njzt],baseCls:"component",width:250}
				      ]
			} 
		});
	},
	createWindow:function(){		
		var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.update,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.editWindow.hide()},scope:this});
		return new Ext.ux.Window({				
			width:400,
		 	height:270,
		 	tbar:{
				cls:"ext_tabr",
				items:[ 
				 	 "->",cancel
		 			 ,"->",save
				  ]
			},
		 	title:"等级信息维护"});		
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
    	Ext.getCmp("njmc").setValue("");
		Ext.getCmp("rxnf").setValue("");
		Ext.getCmp("jyjd").setValue("");
		Ext.getCmp("dj").setValue("");
		this.editForm.getForm().findField("njzt").setValue("");
    	this.editWindow.show(null,function(){},this);	
    },
	updateNj:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
    	}
    	var xxmc = selectedBuildings[0].get("XXMC");
		var njmc = selectedBuildings[0].get("NJMC");
		var rxnf = selectedBuildings[0].get("RXNF");
		var jyjd = selectedBuildings[0].get("JYJD");
		var djdm = selectedBuildings[0].get("XJNJDM");
		var dj = selectedBuildings[0].get("DJMC");
		var njzt = selectedBuildings[0].get("NJZT");
		Ext.getCmp("xxmc").setValue(xxmc);
		Ext.getCmp("njmc").setValue(njmc);
		Ext.getCmp("rxnf").setValue(rxnf);
		Ext.getCmp("jyjd").setValue(jyjd);
		Ext.getCmp("dj").setValue(djdm);
		this.editForm.getForm().findField("njzt").setValue(njzt);		    	
		this.editWindow.show(null,function(){},this)
    },
    update:function(){
    	var njmc = Ext.getCmp("njmc").getValue();
    	var rxnf = Ext.getCmp("rxnf").getValue();
    	var jyjd = Ext.getCmp("jyjd").getValue();
    	var djdm = Ext.getCmp("dj").getValue();
    	var njzt = this.editForm.getForm().findField("njzt").getValue();    	
    	if(njmc==""){
    		Ext.Msg.alert('消息',"等级名称不能为空！");
    		return;
    	}
    	if(rxnf==""){
    		Ext.Msg.alert('消息',"年份不能为空！");
    		return;
    	}
    	if(djdm==""){
    		Ext.Msg.alert('消息',"等级不能为空！");
    		return;
    	}
    	if(njzt==""){
    		Ext.Msg.alert('消息',"状态不能为空！");
    		return;
    	}
    	var thiz = this;
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if (selectedBuildings!=null&&selectedBuildings!='') {
    		var xx_njxx_id = selectedBuildings[0].get("XX_NJXX_ID");
    		var xx_jbxx_id = selectedBuildings[0].get("XX_JBXX_ID");
    		this.editForm.$submit({
    			action:'nj_update.do',
    			params: {    
    				'xx_njxx_id':xx_njxx_id,'xx_jbxx_id':xx_jbxx_id,'njmc':njmc,'rxnf':rxnf,'jyjd':jyjd,'djdm':djdm,'njzt':njzt
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
    			action:'nj_update.do',
    			params: {    
    	 			'xx_njxx_id':'','xx_jbxx_id':xx_jbxx_id,'njmc':njmc,'rxnf':rxnf,'jyjd':jyjd,'djdm':djdm,'njzt':njzt
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
    deleteNj:function(){
    	var thiz = this;
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
		if(selectedBuildings.length <= 0){
    		Ext.MessageBox.alert("消息","请选择需要删除的记录！");
    		return;
    	}
		
		var xx_njxx_id = selectedBuildings[0].get("XX_NJXX_ID");
		var xx_jbxx_id = selectedBuildings[0].get("XX_JBXX_ID");
		Ext.Msg.confirm('消息','确认删除这'+selectedBuildings.length+'条记录？', function (id) {
    		if (id == 'yes') {
    			Ext.Ajax.request({
    				url: 'nj_del.do',
    				params: {'xx_njxx_id':xx_njxx_id},
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
    		}
        });    	  			
    },
	returnCkdw:function(){
		window.location.href = Ext.get("ServerPath").dom.value+"/jsp/main.jsp?module=000002";
	}
});
