var roomcode_b = "";
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
    		if(node.attributes.index == 3){
    			Ext.getCmp('search_form1').getForm().reset();
    			this.$load({"room.lfid":node.id,"room.fjid":Ext.getCmp('FJID').getValue(),"room.roomname":Ext.getCmp('ROOMNAME').getValue()})
    		}else {
    			Ext.Msg.alert("提示","只能对考点添加考场信息!");
        		return;
    		}
    	},this.grid);    	
    	this.orgTree.on("afterrender",function(){
    		this.orgTree.expandAll(true);
    	},this);
    	this.grid.on("rowdblclick",function(grid , rowIndex, e ){
    		var fjid = grid.store.getAt(rowIndex).get("FJID");    		
    		roomcode_b = grid.store.getAt(rowIndex).get("ROOMCODE");
    		Ext.getCmp("TYPE_M").setValue(grid.store.getAt(rowIndex).get("TYPE_M"));    		
    		this.eidtWindow.show(null,function(){
    			this.editForm.$load({
    				params:{'room.fjid':fjid},
					action:"room_loadRoom.do",
					handler:function(form,result,scope){
						form.findField("typeM").setValue(grid.store.getAt(rowIndex).get("TYPE_M"));
					}
    			});
    		},this)
    	},this);
    },   
   	initComponent :function(){   		
   		this.editForm   = this.createRoomEditForm();
   		this.eidtWindow = this.createRoomWindow();
   		this.eidtWindow.add(this.editForm);
   		
   		this.orgTree = new Ext.ux.TreePanel({region:"west",
			             rootVisible:false,
			             title:"考点",
						 collapseMode : "mini",
			             split:true,
			             minSize: 120,
			             width:200,
			             maxSize: 300,
			             autoScroll:true,
   						 action:"room_getBuilding.do"
   		});
	   		
   		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "考场号",   	align:"center", sortable:true, dataIndex:"ROOMCODE"},
			{header: "考场名称", 	align:"center", sortable:true, dataIndex:"ROOMNAME"},
			{header: "楼层",   	align:"center", sortable:true, dataIndex:"FLOOR"},
			{header: "容量",   	align:"center", sortable:true, dataIndex:"SEATS"},
			{header: "考场类型", 	align:"center", sortable:true, dataIndex:"TYPENAME"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			tbar:{
				cls:"ext_tabr",
				items:[ 
				       "->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteRoom,scope:this}
				       ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateRoom,scope:this}
				       ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.showEidtWindow()},scope:this}
				      ]
			},
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			pdf:true,
			excelTitle:"考场信息列表",
			action:"room_getListPage.do",
			fields :["FJID","ROOMCODE","ROOMNAME","FLOOR","SEATS","TYPENAME","TYPE_M","LFID"],
			border:false
		});
			
		this.roomname	= new Ext.form.TextField({fieldLabel:"考场名称",name:"room.roomname",id:"roomname_sel",maxLength:200,width:160});
		this.floor		= new Ext.form.TextField({fieldLabel:"楼层",name:"room.floor",id:"floor_sel",maxLength:200,width:160});
		var cx 			= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.initQueryDate,scope:this});
		var cz 			= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.search = new Ext.form.FormPanel({
			id:"search_form1",
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
                    		       {html:"考场名称：",baseCls:"label_right",width:100},
                    		       {items:[this.roomname],baseCls:"component",width:170},
                    		       {html:"楼层：",baseCls:"label_right",width:100}, 
                    		       {items:[this.floor],baseCls:"component",width:170},
                    		       {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:160}
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
    		Ext.Msg.alert("提示","请选择考点!");
    	}else {
	    	this.grid.$load({"room.lfid":this.getOrgTreeSelectNode().id,"room.roomname":this.roomname.getValue(),"room.floor":this.floor.getValue()});
    	}	    
    },
    getOrgTreeSelectNode:function(){
    	return this.orgTree.getSelectionModel().getSelectedNode();
    },
    checkRoomcode: function(v) {
    	var error;    	
    	if(Ext.getCmp("FJID").getValue() == "" || (Ext.getCmp("FJID").getValue() != "" && Ext.getCmp("ROOMCODE").getValue() != roomcode_b)) {
	    	if("" == v) {
	    		return "考场号不能为空！";
	    	}
	    	if(v.length>32) {
	    		return "考场号不能大于32位！";
	    	}
			var obj;
			var value;
			if (window.ActiveXObject) {
			    obj = new ActiveXObject('Microsoft.XMLHTTP');
			} else if (window.XMLHttpRequest) {
			    obj = new XMLHttpRequest();
			}
			var url = 'room_checkRoomcodeRepeat.do?room.roomcode='+v;			
			obj.open('POST', url, false);
			obj.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
			obj.send(null);
			if(obj.responseText == "false"){
				error = "考场号重复！";
			}else {
				error = null;
			}	        
    	}
      	return error;
    },
    createRoomEditForm:function(){
    	var buildname	= new Ext.form.TextField({width:220,id:"BUILDNAME",readOnly:true});
    	var fjid		= new Ext.form.TextField({width:220,fieldLabel:"",hidden:true,name:"room.fjid",id:"FJID"});
		var roomcode	= new Ext.form.TextField({width:220,name:"room.roomcode",id:"ROOMCODE",maxLength:32,allowBlank :false,validator:this.checkRoomcode});
		var roomname	= new Ext.form.TextField({width:220,name:"room.roomname",id:"ROOMNAME",maxLength:50,allowBlank :false,blankText:"考场名称不能为空！"});		
		var lfid		= new Ext.form.TextField({width:220,fieldLabel:"",hidden:true,name:"room.lfid",id:"LFID"});
		var floor		= new Ext.form.NumberField({width:220,name:"room.floor",id:"FLOOR",maxLength:3,allowBlank :false,blankText:"楼层不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText : '楼层为正整数！'});
		var seats		= new Ext.form.NumberField({width:220,name:"room.seats",id:"SEATS",maxLength:2,allowBlank :false,blankText:"容量不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText : '容量为正整数！'});
		var typem		= new Ext.form.TextField({hidden:true,name:"room.typeM",id:"TYPE_M"});
		var typename	= new Ext.ux.DictCombox({
			name:"typeM",
			dictCode:"ZD_JSLX",
			value:"1",
			width:220,
			allowBlank:false,
			blankText:"考场类型为必选！",
			listeners:{ 
			select:function() {
				Ext.getCmp("TYPE_M").setValue(this.getValue());
			}  
		}});
				
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 2 
			}, 
			items:[
			       {html:"楼房：",baseCls:"label_right"},
			       {html:"<font class='required'>&nbsp;&nbsp;</font>",items:[buildname],baseCls:"component",width:250},
			       {html:"考场号：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[roomcode],baseCls:"component",width:250},
			       {html:"<font style='font-weight:bold;color:#102D91;'>考场名称：</font>",baseCls:"label_right"}, 
			       {html:"<font class='required'>*</font>",items:[roomname],baseCls:"component",width:250},
			       {html:"<font style='font-weight:bold;color:#102D91;'>楼层：</font>",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[floor],baseCls:"component",width:250},
			       {html:"容量：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[seats],baseCls:"component",width:250},
			       {html:"考场类型：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[typename,typem],baseCls:"component",width:250,colspan:2}
			      ] 
			}); 
		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"95%"},
			items:[
				{layout:'form', xtype:"fieldset",title:"考场信息",items:[panel]},
				{items:[fjid,lfid]}
			]});
    },
    createRoomWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addRoom,scope:this});
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
			 	title:"考场信息维护"});		
    },
    showEidtWindow:function() {
    	if(this.getOrgTreeSelectNode() == null) {
    		Ext.Msg.alert("提示","请选择考点!");
    	}else {
    		if(this.getOrgTreeSelectNode().attributes.index == 3){        		 
    			Ext.getCmp('BUILDNAME').setValue(this.getOrgTreeSelectNode().text);
        		Ext.getCmp('LFID').setValue(this.getOrgTreeSelectNode().id);
        		this.eidtWindow.show();
        	}else {
        		Ext.Msg.alert("提示","只能对考点添加考场信息!");
        	}    		
    	}    	
    },
    addRoom:function(){
    	this.editForm.$submit({
    		action:"room_addRoom.do",
    		handler:function(form,result,thiz){
    			thiz.eidtWindow.hide();
    			thiz.grid.$load();
    		},
    		scope:this
    	});
    },
    deleteRoom:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedRooms = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var fjids = "'";
    	for(var i = 0; i < selectedRooms.length; i++) {
    		if(i != selectedRooms.length - 1) {
	    		fjids += selectedRooms[i].get("FJID") + "','";
    		}else {
	    		fjids += selectedRooms[i].get("FJID") + "'";
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
    						params:{'fjids':fjids},
    						action:"room_delRoom.do",
    						handler:function(){
    							thiz.grid.$load();
    						}
    					})
    					
    				}
    			}
    		})
    },
    updateRoom:function(){
		var selectedRooms = this.grid.getSelectionModel().getSelections();
    	if(selectedRooms.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
    	}
		var fjid = selectedRooms[0].get("FJID");
		roomcode_b = selectedRooms[0].get("ROOMCODE");
		Ext.getCmp("TYPE_M").setValue(selectedRooms[0].get("TYPE_M"));		
		this.eidtWindow.show(null,function(){
			this.editForm.$load({
				params:{'room.fjid':fjid},
				action:"room_loadRoom.do",
				handler:function(form,result,scope){
					form.findField("typeM").setValue(selectedRooms[0].get("TYPE_M"));
				}
			});
		},this)
	}
});