Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
	    this.initDate();
		this.initComponent();
		this.initListener();
		this.initFace();
    },
    /** 初始化页面、内存等基本数据 **/
    initDate:function(){
    	
    },    
	/** 对组件设置监听 **/
    initListener:function(){
    	this.eidtWindow.on("hide",function(){
    		this.editForm.$reset();
    	},this);
    	this.orgTree.on("click",function(node){
    		if(node.attributes.index == 2){
    			Ext.getCmp('search_form2').getForm().reset();
    			this.$load({"building.schoolid":node.id,"building.lfid":Ext.getCmp('lfid').getValue(),"building.buildname":Ext.getCmp('buildname').getValue()})
    		}else {
    			Ext.Msg.alert("提示","只能对有组考的组织单位添加考点信息!");
        		return;
    		}
    	},this.grid);    	
    	this.orgTree.on("afterrender",function(){
    		this.orgTree.expandAll(true);
    	},this);
    	this.grid.on("rowdblclick",function(grid, rowIndex, e ){
    		Ext.getCmp('schoolid').setValue(this.getOrgTreeSelectNode().id);
    		Ext.getCmp('ckdwname').setValue(this.getOrgTreeSelectNode().text);
    		var lfid = grid.store.getAt(rowIndex).get("lfid");
    		this.eidtWindow.show(null,function(){
    			this.editForm.$load({
    				params:{'building.lfid':lfid},
    				action:"building_loadBuilding.do",
    				handler:function(form,result,scope){
    					
    				}
    			});
    		},this)
    	},this);
    },   
   	initComponent :function(){   		
   		this.editForm   = this.createCkdwEditForm();
   		this.eidtWindow = this.createCkdwWindow();
   		this.eidtWindow.add(this.editForm);
   		
   		
   		//修改人： 包敏  修改日期： 2018-05-17 修改原因： 增加关联考点窗口
   		this.relationForm = this.createRelationForm();
   		this.relationWindow = this.createRelationWindow();
   		this.relationWindow.add(this.relationForm);
   	    //修改人： 包敏  修改日期： 2018-05-17 修改原因： 增加关联考点窗口
   		
   		this.orgTree = new Ext.ux.TreePanel({region:"west",
			             rootVisible:false,
			             title:"考点单位",
						 collapseMode : "mini",
			             split:true,
			             minSize: 120,
			             width:200,
			             maxSize: 300,
			             autoScroll:true,
   						 action:"building_getCkdw.do"
   		});
	   		
   		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "楼房名称",   	align:"center", sortable:true, dataIndex:"buildname"},
			{header: "地址",   		align:"center", sortable:true, dataIndex:"address"},
			{header: "楼层",   		align:"center", sortable:true, dataIndex:"floors"},
			{header: "房间数",   		align:"center", sortable:true, dataIndex:"rooms"},
			{header: "单层房间数",   	align:"center", sortable:true, dataIndex:"perfrooms"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			tbar:{
				cls:"ext_tabr",
				items:[ 
				       "->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteBuilding,scope:this}
				       ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateBuilding,scope:this}
				       ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.showEidtWindow()},scope:this}
				       ,"->",{xtype:"button",text:"关联考点",iconCls:"p-icons-add",handler:function(){this.showRelationWindow()},scope:this}
				      ]
			},
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			pdf:true,
			excelTitle:"楼房信息列表",
			action:"building_getListPage.do",
			fields :["lfid","buildname","address","floors","rooms","perfrooms"],
			border:false
		});
			
		this.buildname	= new Ext.form.TextField({fieldLabel:"楼房名称",name:"building.buildname",id:"buildname_sel",maxLength:200,width:160});
		var cx 			= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.initQueryDate,scope:this});
		var cz 			= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.search = new Ext.form.FormPanel({
			id:"search_form2",
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
		    		          {html:"楼房名称：",baseCls:"label_right",width:170},
		    		          {items:[this.buildname],baseCls:"component",width:170},
		    		          {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
							] 
		    	   }]  
		       }]		       		      
	    });	    	
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"border",
    		items:[this.orgTree,{
			layout: 'border',
	        region:'center',
	        border: false,
	        split:true,
			margins: '2 0 5 5',
	        width: 275,
	        minSize: 100,
	        maxSize: 500,
			items: [this.search,this.grid]
		}]});
    },
    initQueryDate:function(){
    	if(this.getOrgTreeSelectNode() == null) {
    		Ext.Msg.alert("提示","请选择组织单位!");
    	}else {
	    	this.grid.$load({"building.schoolid":this.getOrgTreeSelectNode().id,"building.buildname":this.buildname.getValue()});
    	}	    	
    },
    getOrgTreeSelectNode:function(){
    	return this.orgTree.getSelectionModel().getSelectedNode();
    },
    //修改人： 包敏， 修改时间： 2018-05-17 修改原因： 增加关联考点功能
    createRelationForm:function(){
    	var ckdwname	= new Ext.form.TextField({width:220,id:"ckdwname1",readOnly:true});
    	var schoolid	= new Ext.form.TextField({fieldLabel:"",hidden:true,name:"building.schoolid",id:"schoolid"});
    	var lfid		= new Ext.form.TextField({fieldLabel:"",hidden:true,name:"building.lfid",id:"lfid"});    	
    	var point_sel = new Ext.ux.form.PointField({name:"point_sel",width:200,singleSelection:true,allowBlank:false,readOnly:true});
    	//var buildname	= new Ext.form.TextField({width:220,name:"building.buildname",id:"buildname",maxLength:50,allowBlank :false,blankText:"楼房名称不能为空！"});
		//var address		= new Ext.form.TextField({width:220,name:"building.address",id:"address",maxLength:200,allowBlank :false,blankText:"地址不能为空！"});
		//var floors		= new Ext.form.NumberField({width:220,name:"building.floors",id:"floors",maxLength:3,allowBlank :false,blankText:"楼层不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText : '楼层为正整数！'});
		//var rooms		= new Ext.form.NumberField({width:220,name:"building.rooms",id:"rooms",maxLength:5,allowBlank :false,blankText:"房间数不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText : '房间数为正整数！'});
		//var perfrooms	= new Ext.form.NumberField({width:220,name:"building.perfrooms",id:"perfrooms",maxLength:3,allowBlank :false,blankText:"单层房间数不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText : '单层房间数为正整数！'});
    	//var kdname	= new Ext.ux.DictCombox({name:"kdname",dictCode:"kd_list",value:"1",width:220,allowBlank:false,blankText:"考场类型为必选！",listeners:{ 
		//	select:function() {
		//		Ext.getCmp("kdname").setValue(this.getValue());
		//	}  
		//}})
    	
    	/*
    	var comb1Store =  new Ext.data.SimpleStore({
            fields : ['SHOWNAME', 'VALUE'],
            data : [['湖南电大考点', '0'], ['望城考点', '1']]
        });
		var comb1 = new Ext.form.ComboBox({
		mode : 'local',//本地数据
		store : comb1Store,
		width : 200,   
		displayField : 'SHOWNAME',
		valueField : 'VALUE',
		fieldLabel : '类型'     
		});
		*/
    	
  
    	
    	
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 2 
			}, 
			items:[
			       {html:"组织单位：",baseCls:"label_right"},
			       {html:"<font class='required'>&nbsp;&nbsp;</font>",items:[ckdwname],baseCls:"component",width:250},
			       {html:"关联考点：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[point_sel],baseCls:"component",width:210}
			       //{html:"关联考点：",baseCls:"label_right"},
			       //{html:"<font class='required'>*</font>",items:[comb1],baseCls:"component",width:250,colspan:2}
			       /*
			        *  {html:"<font class='required'>*</font>",items:[kdname,lfid],baseCls:"component",width:250,colspan:2}
			       {html:"<font style='font-weight:bold;color:#102D91;'>楼房名称：</font>",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[buildname],baseCls:"component",width:250},
			       {html:"<font style='font-weight:bold;color:#102D91;'>地址：</font>",baseCls:"label_right"}, 
			       {html:"<font class='required'>*</font>",items:[address],baseCls:"component",width:250},
			       {html:"楼层：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[floors],baseCls:"component",width:250},
			       {html:"房间数：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[rooms],baseCls:"component",width:250},
			       {html:"单层房间数：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[perfrooms],baseCls:"component",width:250}	
			       */													
				] 
			}); 		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"95%"},
			items:[
				{layout:'form', xtype:"fieldset",title:"考点信息",items:[panel]},
				{items:[lfid,schoolid]}
			]});
    },
    createRelationWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.relateBuilding,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.relationWindow.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:400,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
			 	title:"关联相关考点"});		
    },
    showRelationWindow:function() {
    	if(this.getOrgTreeSelectNode() == null) {
    		Ext.Msg.alert("提示","请选择组织单位!");
    	}else {
    		if(this.getOrgTreeSelectNode().attributes.index == 2){        		 
        		Ext.getCmp('schoolid').setValue(this.getOrgTreeSelectNode().id);         	
        		Ext.getCmp('ckdwname1').setValue(this.getOrgTreeSelectNode().text);
        		this.relationWindow.show();
        	}else {
        		Ext.Msg.alert("提示","只能对有组考的组织单位添加考点信息!");
        	}    		
    	}    	
    },
    
    
  //修改人： 包敏， 修改时间： 2018-05-17 修改原因： 增加关联考点功能
    
    createCkdwEditForm:function(){
    	var ckdwname	= new Ext.form.TextField({width:220,id:"ckdwname",readOnly:true});
    	var schoolid	= new Ext.form.TextField({fieldLabel:"",hidden:true,name:"building.schoolid",id:"schoolid"});
    	var lfid		= new Ext.form.TextField({fieldLabel:"",hidden:true,name:"building.lfid",id:"lfid"});    	
		var buildname	= new Ext.form.TextField({width:220,name:"building.buildname",id:"buildname",maxLength:50,allowBlank :false,blankText:"楼房名称不能为空！"});
		var address		= new Ext.form.TextField({width:220,name:"building.address",id:"address",maxLength:200,allowBlank :false,blankText:"地址不能为空！"});
		var floors		= new Ext.form.NumberField({width:220,name:"building.floors",id:"floors",maxLength:3,allowBlank :false,blankText:"楼层不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText : '楼层为正整数！'});
		var rooms		= new Ext.form.NumberField({width:220,name:"building.rooms",id:"rooms",maxLength:5,allowBlank :false,blankText:"房间数不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText : '房间数为正整数！'});
		var perfrooms	= new Ext.form.NumberField({width:220,name:"building.perfrooms",id:"perfrooms",maxLength:3,allowBlank :false,blankText:"单层房间数不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText : '单层房间数为正整数！'});
		
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 2 
			}, 
			items:[
			       {html:"组织单位：",baseCls:"label_right"},
			       {html:"<font class='required'>&nbsp;&nbsp;</font>",items:[ckdwname],baseCls:"component",width:250},
			       {html:"<font style='font-weight:bold;color:#102D91;'>楼房名称：</font>",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[buildname],baseCls:"component",width:250},
			       {html:"<font style='font-weight:bold;color:#102D91;'>地址：</font>",baseCls:"label_right"}, 
			       {html:"<font class='required'>*</font>",items:[address],baseCls:"component",width:250},
			       {html:"楼层：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[floors],baseCls:"component",width:250},
			       {html:"房间数：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[rooms],baseCls:"component",width:250},
			       {html:"单层房间数：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[perfrooms],baseCls:"component",width:250}														
				] 
			}); 		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"95%"},
			items:[
				{layout:'form', xtype:"fieldset",title:"考点信息",items:[panel]},
				{items:[lfid,schoolid]}
			]});
    },
    createCkdwWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addBuilding,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:400,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
			 	title:"楼房信息维护"});		
    },
    showEidtWindow:function() {
    	if(this.getOrgTreeSelectNode() == null) {
    		Ext.Msg.alert("提示","请选择组织单位!");
    	}else {
    		if(this.getOrgTreeSelectNode().attributes.index == 2){        		 
        		Ext.getCmp('schoolid').setValue(this.getOrgTreeSelectNode().id);         	
        		Ext.getCmp('ckdwname').setValue(this.getOrgTreeSelectNode().text);
        		this.eidtWindow.show();
        	}else {
        		Ext.Msg.alert("提示","只能对有组考的组织单位添加考点信息!");
        	}    		
    	}    	
    },
    addBuilding:function(){
    	this.editForm.$submit({
    		action:"building_addBuilding.do",
    		handler:function(form,result,thiz){
    			thiz.eidtWindow.hide();
    			thiz.grid.$load();
    		},
    		scope:this
    	});
    },
    deleteBuilding:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var lfids = "'";
    	for(var i = 0; i < selectedBuildings.length; i++) {
    		if(i != selectedBuildings.length - 1) {
	    		lfids += selectedBuildings[i].get("lfid") + "','";
    		}else {
	    		lfids += selectedBuildings[i].get("lfid") + "'";
    		}
    	}
    	Ext.MessageBox.show({
    			title:"消息",
    			msg:"您确定要删除吗?",
    			buttons:Ext.MessageBox.OKCANCEL,
    			icon:Ext.MessageBox.QUESTION,
    			
    			fn:function(b){
    				if(b=='ok'){
    					JH.$request({
    						params:{'lfids':lfids},
    						action:"building_delBuilding.do",
    						handler:function(){
    							thiz.grid.$load();
    						}
    					})    					
    				}
    			}
    		})
    },
    updateBuilding:function(){
    	Ext.getCmp('schoolid').setValue(this.getOrgTreeSelectNode().id);
    	Ext.getCmp('ckdwname').setValue(this.getOrgTreeSelectNode().text);
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
    	}
    	var lfid = selectedBuildings[0].get("lfid");
		this.eidtWindow.show(null,function(){
			this.editForm.$load({
				params:{'building.lfid':lfid},
				action:"building_loadBuilding.do",
				handler:function(form,result,scope){
								
				}
			});
		},this)
	},
    //增加人： 包敏 增加时间：    2018-05-18 增加原因: 关联考点
    relateBuilding:function(){
	this.relationForm.$submit({
		action:"building_relateBuilding.do",
		params: {
			'building.lfid':this.relationForm.getForm().findField("point_sel").getCodes()
		},
		handler:function(form,result,thiz){
			thiz.relationWindow.hide();
			thiz.grid.$load();
		},
		scope:this
	});
	//增加人： 包敏 增加时间：    2018-05-18 增加原因: 关联考点
}
});