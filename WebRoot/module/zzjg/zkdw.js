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
        		Ext.Msg.alert("提示","县级以下组织单位暂时不能加入!");
        		return;
        	}
    		Ext.getCmp('search_formZk').getForm().reset();
    		this.$load({"zkdw.regioncode":node.id,"zkdw.regionedu":Ext.getCmp('regionedu').getValue(),"zkdw.regionname":Ext.getCmp('regionname').getValue()})
    	},this.grid);    	
    	this.orgTree.on("afterrender",function(){
    		this.orgTree.expandAll(true);
    	},this);
    	this.grid.on("rowdblclick",function(grid, rowIndex, e ){
    		Ext.getCmp('pzkdwname').setValue(this.getOrgTreeSelectNode().text);
    		var regioncode = grid.store.getAt(rowIndex).get("REGIONCODE");
    		Ext.getCmp("inusem").setValue(grid.store.getAt(rowIndex).get("INUSEM"));
    		this.eidtWindow.show(null,function(){
    			this.editForm.$load({
    				params:{'zkdw.regioncode':regioncode},
					action:"zkdw_loadZkdw.do",
					handler:function(form,result,scope){						
						form.findField("inuse").setValue(grid.store.getAt(rowIndex).get("INUSEM"));	
					}
    			});
    		},this)
    	},this);
    },   
   	initComponent :function(){   
   		this.editForm   = this.createZkdwEditForm();
   		this.eidtWindow = this.createZkdwWindow();
   		this.eidtWindow.add(this.editForm);
   		
   		this.orgTree = new Ext.ux.TreePanel({region:"west",
   						rootVisible:false,
   						title:"上级单位",
   						collapseMode : "mini",
   						split:true,
   						minSize: 120,
   						width:200,
   						maxSize: 300,
   						autoScroll:true,
   						action:"zkdw_getPZkdw.do"
   		});
	   	
	   	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "组织单位",   	align:"center", sortable:true, dataIndex:"REGIONEDU"},
			{header: "地址",   		align:"center", sortable:true, dataIndex:"REGIONNAME"},
			{header: "地址缩写",   	align:"center", sortable:true, dataIndex:"REGIONSHORT"},
			{header: "组织等级",   	align:"center", sortable:true, dataIndex:"REGIONLEVEL"},
			{header: "地区类型",   	align:"center", sortable:true, dataIndex:"AREATYPE"},
			{header: "是否可用",   	align:"center", sortable:true, dataIndex:"INUSE"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			tbar:{
				cls:"ext_tabr",
				items:[ 
				  "->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteZkdw,scope:this}
				  ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateZkdw,scope:this}
				  ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.showEidtWindow()},scope:this}
				  ]
			},
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			pdf:true,
			excelTitle:"组织单位信息列表",
			action:"zkdw_getListPage.do",
			fields :["REGIONCODE","REGIONEDU","REGIONNAME","REGIONSHORT","REGIONLEVEL","AREATYPE","INUSE","INUSEM","PARENTCODE"],
			border:false
		});
			
		this.regionedu	= new Ext.form.TextField({fieldLabel:"组织单位",name:"zkdw.regionedu",id:"regionedu_sel",maxLength:200,width:160});
		this.regionname	= new Ext.form.TextField({fieldLabel:"地址",name:"zkdw.regionname",id:"regionname_sel",maxLength:200,width:160});
		var cx 			= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.initQueryDate,scope:this});
		var cz 			= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.search = new Ext.form.FormPanel({
			id:"search_formZk",
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
							{html:"组织单位：",baseCls:"label_right",width:100},
							{items:[this.regionedu],baseCls:"component",width:170},
							{html:"地址：",baseCls:"label_right",width:50}, 
							{items:[this.regionname],baseCls:"component",width:180},
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
    		Ext.Msg.alert("提示","请选择组织单位!");
    	}else {
	    	this.grid.$load({"zkdw.regioncode":this.getOrgTreeSelectNode().id,"zkdw.regionedu":this.regionedu.getValue(),"zkdw.regionname":this.regionname.getValue()});
    	}    	
    },
    getOrgTreeSelectNode:function(){
    	return this.orgTree.getSelectionModel().getSelectedNode();
    },
    checkZkdwcode: function(v) {
    	var error;    	
    	if(Ext.getCmp("REGIONCODE").getValue() == "" || Ext.getCmp("REGIONCODE").getValue() != "") {
	    	if("" == v) {
	    		return "组织单位号不能为空！";
	    	}
			var obj;
			var value;
			if (window.ActiveXObject) {
			    obj = new ActiveXObject('Microsoft.XMLHTTP');
			}else if (window.XMLHttpRequest) {
			    obj = new XMLHttpRequest();
			}
			var url = 'zkdw_checkZkdwcodeRepeat.do?zkdw.regioncode='+v;			
			obj.open('POST', url, false);
			obj.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
			obj.send(null);
			if(obj.responseText == "false"){
				error = "组织单位号重复！";
			}else {
				error = null;
			}	        
    	}
      	return error;
    },
    createZkdwEditForm:function(){
    	var pzkdwname		= new Ext.form.TextField({width:220,id:"pzkdwname",readOnly:true});
    	var parentcode		= new Ext.form.TextField({fieldLabel:"",hidden:true,name:"zkdw.parentcode",id:"parentcode"});    	
    	var regioncode		= new Ext.form.TextField({width:220,name:"zkdw.regioncode",id:"regioncode",maxLength:12,allowBlank :false,blankText:"组织单位号不能为空！",regex:/\b\d{12}\b/,regexText : '组考单位号为12位数字'});
		var regionedu		= new Ext.form.TextField({width:220,name:"zkdw.regionedu",id:"regionedu",maxLength:100,allowBlank :false,blankText:"组织单位名称不能为空！"});
		var regionname		= new Ext.form.TextField({width:220,name:"zkdw.regionname",id:"regionname",maxLength:200,allowBlank :false,blankText:"地址不能为空！"});
		var regionshort		= new Ext.form.TextField({width:220,name:"zkdw.regionshort",id:"regionshort",maxLength:100,allowBlank :false,blankText:"地址缩写不能为空！"});
		var regionlevel		= new Ext.form.TextField({width:220,name:"zkdw.regionlevel",id:"regionlevel",maxLength:1,allowBlank :false,blankText:"组织等级不能为空！"});
		var areatype		= new Ext.form.TextField({width:220,name:"zkdw.areatype",id:"areatype",maxLength:1,allowBlank :false,blankText:"地区类型不能为空！"});
		var inusem			= new Ext.form.TextField({hidden:true,name:"zkdw.inuse",id:"inusem"});
		var inuse 			= new Ext.ux.DictCombox({name:"inuse",dictCode:"ZD_SFBZ",width:220,allowBlank:false,blankText:"是否可用为必选项！",listeners:{
			"select":function(){
				Ext.getCmp("inusem").setValue(this.getValue());
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
			       {html:"上级单位：",baseCls:"label_right"},
			       {html:"<font class='required'>&nbsp;&nbsp;</font>",items:[pzkdwname],baseCls:"component",width:250},
			       {html:"组织单位号：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[regioncode],baseCls:"component",width:250},
			       {html:"组织单位：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[regionedu],baseCls:"component",width:250},
			       {html:"地址：",baseCls:"label_right"}, 
			       {html:"<font class='required'>*</font>",items:[regionname],baseCls:"component",width:250},
			       {html:"地址缩写：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[regionshort],baseCls:"component",width:250},
			       {html:"组织等级：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[regionlevel],baseCls:"component",width:250},
			       {html:"地区类型：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[areatype],baseCls:"component",width:250},
			       {html:"是否可用：",baseCls:"label_right"},
			       {html:"<font class='required'>*</font>",items:[inuse,inusem],baseCls:"component",width:250,colspan:2}			       
			    ] 
			}); 
		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"95%"},
			items:[
				{layout:'form',xtype:"fieldset",title:"组织单位信息",items:[panel]},
				{items:[parentcode]}
			]});
    },
    createZkdwWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addZkdw,scope:this});
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
			 	title:"组织单位信息维护"});			
    },
    showEidtWindow:function() {
    	if(this.getOrgTreeSelectNode() == null) {
    		Ext.Msg.alert("提示","请选择上级单位!");
    	}else {
    		if(this.getOrgTreeSelectNode().attributes.index == 2){
        		Ext.Msg.alert("提示","县级以下组织单位暂时不能加入!");        		
        	}else {
        		Ext.getCmp('pzkdwname').setValue(this.getOrgTreeSelectNode().text);
        		Ext.getCmp('parentcode').setValue(this.getOrgTreeSelectNode().id);
        		Ext.getCmp('regioncode').setReadOnly(false);
    	    	this.eidtWindow.show();
        	}    		
    	}    	
    },
    addZkdw:function(){
    	this.editForm.$submit({
    		action:"zkdw_addZkdw.do",
    		handler:function(form,result,thiz){
    			thiz.eidtWindow.hide();
    			thiz.grid.$load();
    		},
    		scope:this
    	});
    },
    deleteZkdw:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedRegioncodes = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var regioncodes = "'";
    	for(var i = 0; i < selectedRegioncodes.length; i++) {
    		if(i != selectedRegioncodes.length - 1) {
    			regioncodes += selectedRegioncodes[i].get("REGIONCODE") + "','";
    		}else {
    			regioncodes += selectedRegioncodes[i].get("REGIONCODE") + "'";
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
    						params:{'regioncodes':regioncodes},
    						action:"zkdw_delZkdw.do",
    						handler:function(){
    							thiz.grid.$load();
    						}
    					})
    					
    				}
    			}
    		})
    },
    updateZkdw:function(){
    	Ext.getCmp('pzkdwname').setValue(this.getOrgTreeSelectNode().text);
		var selectedRegioncodes = this.grid.getSelectionModel().getSelections();
    	if(selectedRegioncodes.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
    	}
		var regioncode = selectedRegioncodes[0].get("REGIONCODE");		
		Ext.getCmp("inusem").setValue(selectedRegioncodes[0].get("INUSEM"));	
		this.eidtWindow.show(null,function(){
			this.editForm.$load({
				params:{'zkdw.regioncode':regioncode},
				action:"zkdw_loadZkdw.do",
				handler:function(form,result,scope){
					form.findField("inuse").setValue(selectedRegioncodes[0].get("INUSEM"));
					Ext.getCmp('regioncode').setReadOnly(true);
				}
			});
		},this)
	}
});