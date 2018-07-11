var isupdate = "";
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
    		var rolename = grid.store.getAt(rowIndex).get("ROLENAME");
    		var meno = grid.store.getAt(rowIndex).get("MENO");
    		var state = grid.store.getAt(rowIndex).get("STATE");
    		Ext.getCmp("mc").setValue(rolename);//获取控件在Ext框架里面的对象
    		Ext.getCmp("shuom").setValue(meno);
    		this.editForm.getForm().findField("zt").setValue(state);
    		this.editWindow.show(null,function(){},this)    		    		
    	},this);
    },    
    initComponent :function(){
    	this.editForm   = this.createExamroundForm();
   		this.editWindow = this.createWindow();
   		this.editWindow.add({items:[this.editForm]});
    	
    	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true,
    		listeners:{    			
    			"render":function(){
    				var hd_checker = this.getEl().select('div.x-grid3-hd-checker');
    				if (hd_checker.hasClass('x-grid3-hd-checker')) {  
    					hd_checker.removeClass('x-grid3-hd-checker');//去掉全选框
    				} 
    			}
    		}    		
    	});
		var cm = [
		    sm,
			{header: "角色名称",   align:"center", sortable:true, dataIndex:"ROLENAME"},
			{header: "角色说明",   align:"center", sortable:true, dataIndex:"MENO"},
			{header: "角色状态",   align:"center", sortable:true, dataIndex:"ZT"}
		];
		
		this.grid = new Ext.ux.GridPanel({			
			cm:cm,
			sm:sm,
			title:"系统管理-角色管理",
			tbar:[ 
			      "->",{xtype:"button",id:"sc",text:"删除",iconCls:"p-icons-delete",handler:this.delRoleinfo,scope:this},
			      "->",{xtype:"button",id:"xg",text:"修改",iconCls:"p-icons-update",handler:this.updateRole,scope:this},
			      "->",{xtype:"button",id:"zj",text:"增加",iconCls:"p-icons-add",handler:this.addRole,scope:this},
			      "->",{xtype:"button",id:"sq",text:"菜单授权",iconCls:"p-icons-update",handler:this.roleinfoTree,scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			action:"roleinfogl_getListPage.do",
			fields :["ROLECODE","ROLENAME","MENO","STATE","ZT"],
			border:false
		});
	
		this.panel=new Ext.Panel({			
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			items:[this.grid]
		});
	},
	/** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"fit",items:[this.panel]});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
    delRoleinfo:function(){
    	var thiz = this;
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
		if(selectedBuildings.length <= 0){
    		Ext.MessageBox.alert("消息","请选择需要删除的记录！");
    		return;
    	}
		
		var rolecode2 = selectedBuildings[0].get("ROLECODE");
		Ext.Msg.confirm('消息','确认删除这'+selectedBuildings.length+'条记录？', function (btn) {
    		if (btn == 'yes') {
    			Ext.Ajax.request({
    				url: 'roleinfogl_delRole.do',
    				params: {'rolecode':rolecode2},
    				method: 'POST',
    				callback: function (options, success, response) {
    					if (success) {
    						Ext.MessageBox.alert("消息",response.responseText);
    						thiz.grid.$load(); 
    						thiz.grid.$load(); 
    					} else {
    						Ext.MessageBox.alert("消息","删除失败!");
    					}
    				}
    			});                         
		    }});    	  			
    },
    createExamroundForm:function(){	
    	var mc = new Ext.form.TextField({id:"mc",maxLength:50,width:120}); 
    	var shuom = new Ext.form.TextField({id:"shuom",maxLength:50,width:120});
    	//var zt = new Ext.form.ComboBox({name:"zt",store:wjcllxStore,value:'0',displayField:'text',valueField:'value',mode:'local',triggerAction:'all',width:120,editable:false});
    	var zt = new Ext.form.ComboBox({
				name:'zt',
				mode: 'local', 
				triggerAction: 'all',   
				editable:false,
				width:120,
				store: new Ext.data.ArrayStore({
		        id: 0,
		        fields: [
		                 'value',
		                 'text'
		                ],
		        data: [['1', '可用'], ['0', '不可用']]
		    }),
		    valueField: 'value',
		    displayField: 'text'
		}); 
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
				       {html:"角色名称：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>", items:[mc],baseCls:"component",width:150},
				       {html:"角色说明：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>",items:[shuom],baseCls:"component",width:150},
				       {html:"角色状态：",baseCls:"label_right",width:100},
				       {html:"<font class=required color=red>*</font>",items:[zt],baseCls:"component",width:150}
				      ]
			} 
		});
    },    
    createWindow:function(){    	
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.update,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.editWindow.hide();isUpdate="No";},scope:this});		
		return new Ext.ux.Window({				
			 	width:290,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
			 	title:"角色信息维护"});
    },
    addRole:function(){
    	Ext.getCmp("mc").setValue("");
		Ext.getCmp("shuom").setValue("");		
		this.editForm.getForm().findField("zt").setValue("");
		this.editWindow.show(null,function(){},this);
		isupdate = ""; 	
    },	
    updateRole:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
    	}
    	var rolename = selectedBuildings[0].get("ROLENAME");
		var meno = selectedBuildings[0].get("MENO");
		var state = selectedBuildings[0].get("STATE");
		Ext.getCmp("mc").setValue(rolename);
		Ext.getCmp("shuom").setValue(meno);		
		this.editForm.getForm().findField("zt").setValue(state);
		this.editWindow.show(null,function(){},this);
		isupdate = "true";
    },    
    update:function(){
    	var rolename2 = Ext.getCmp("mc").getValue();
    	var meno = Ext.getCmp("shuom").getValue();
    	var state = this.editForm.getForm().findField("zt").getValue();       	
    	if(rolename2==""){
    		Ext.Msg.alert('消息',"角色名称不能为空！");
    		return;
    	}
    	if(meno==""){
    		Ext.Msg.alert('消息',"角色说明不能为空！");
    		return;
    	}
    	if(state==""){
    		Ext.Msg.alert('消息',"角色状态不能为空！");
    		return;    	
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if (selectedBuildings!=null&&selectedBuildings!=''&&isupdate=='true') {
    		var rolecode = selectedBuildings[0].get("ROLECODE");
        	var rolename = selectedBuildings[0].get("ROLENAME");
        	var bloor = "";
        	if(rolename==rolename2){
        		bloor = "0";
        	}else{
        		bloor = "1";
        	}
        	this.editForm.$submit({
    			action:'roleinfogl_update.do',
    			params: {    
    			 	'rolecode':rolecode,'rolename':rolename2,'meno':meno,'state':state,'bloor':bloor
    	 		},    
        		handler:function(form,result,thiz){
        			thiz.editWindow.hide();
        			thiz.grid.$load();
        			thiz.grid.$load();
        		},
        		scope:this
        	});
    	}else{
    		this.editForm.$submit({
    			action:'roleinfogl_update.do',
    			params: {    
    			 	'rolecode':'','rolename':rolename2,'meno':meno,'state':state,'bloor':''
    	 		},    
        		handler:function(form,result,thiz){
        			thiz.editWindow.hide();
        			thiz.grid.$load();
        			thiz.grid.$load();
        		},
        		scope:this
        	});
    	}    	
    },
    roleinfoTree: function(){ 
    	var selectedBuildings  = this.grid.getSelectionModel().getSelections();
		if(selectedBuildings.length <= 0){
    		Ext.MessageBox.alert("消息","请选择需要授权的记录！");
    		return;
    	}
		var rolecode2 = selectedBuildings[0].get("ROLECODE");
    	var thiz = this;
		var tree = new Ext.tree.TreePanel({ 
			id:'treefield',
			checkModel: 'cascade',   //对树的级联多选  
		    onlyLeafCheckable: false,//对树所有结点都可选 
            region: 'center',
            width: 200,
            border : false,//表框 
            autoScroll: true,//自动滚动条
            animate : true,//动画效果 
            rootVisible: false,//根节点是否可见  
            split: true,  
            loader : new Ext.tree.TreeLoader({  
            	dataUrl : 'roleinfogl_getMenuinfoTree.do?rolecode='+rolecode2
            }), 
            tbar:{
				items:[ 
				       "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.win2.hide()},scope:this}
				       ,"->",{xtype:"button",text:"选择",iconCls:"p-icons-submit",handler:this.selectsubimt,scope:this}
				      ]
			},  
            root : new Ext.tree.AsyncTreeNode({  }),  
            listeners: { 
            	dblclick:function(node){
	                thiz.typeValue = node.attributes.id;
	                thiz.typeValueName = node.attributes.text;
            	},
                click: function(node) {  
                    //得到node的text属性  
                	thiz.typeValue = node.attributes.id;
                	thiz.typeValueName = node.attributes.text;
                }
            } 
        }); 
		tree.on('checkchange', function(node, checked) {
			node.expand();
			node.attributes.checked = checked;
			node.eachChild(function(child) {
				child.ui.toggleCheck(checked);
				child.attributes.checked = checked;
				child.fireEvent('checkchange', child, checked);
			});
		}, tree);
		this.win2 = new Ext.ux.Window({			
		 	width:400,
		 	height:380,
		 	title:"菜单",
		 	layout:'border',//布局方式
	 		items:[tree]
		});	
		this.win2.show();
	},
	selectsubimt:function(){
		var selectedBuildings  = this.grid.getSelectionModel().getSelections();
		var rolecode2 = selectedBuildings[0].get("ROLECODE");
		var selectedNode =  Ext.getCmp('treefield').getChecked();
		if(selectedNode == ""){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
		var menucode = "";
		for(var i=0; i < selectedNode.length;i++) {
			menucode += selectedNode[i].id+",";
		}
		var thiz = this;
		Ext.Ajax.request({   
       		url:'roleinfogl_addRoleMenu.do',
       		params:{
       			'rolecode':rolecode2,'menucode':menucode
        	},
        	success: function(resp,opts) {
        		var respText = Ext.util.JSON.decode(resp.responseText);
        		Ext.MessageBox.alert("提示",respText.msg);
        		thiz.win2.hide();
        	}
		});
	}
});