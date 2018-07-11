var wjcllx = [['0', '否'],['1', '是']];
var wjcllxStore = new Ext.data.SimpleStore({  
	fields : ['value', 'text'],  
	data : wjcllx  
});
var sszgjyxzdm;
var xxjbxxid;
var xxmc;
var addNj;
var addBj;
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
    		Ext.getCmp('search_formCkZ').getForm().reset();
    		sszgjyxzdm = node.id;    		
    		xxmc = node.text;    		    		
    		this.$load({"ckdw.sszgjyxzdm":node.id,"ckdw.xxmc":""});
    	},this.grid);    	
    	this.orgTree.on("afterrender",function(){
    		this.orgTree.expandAll(true);
    	},this);
    	this.grid.on("rowdblclick",function(grid, rowIndex, e ){
    		Ext.getCmp('zkdwnameZ').setValue(this.getOrgTreeSelectNode().text);
    		xxjbxxid = grid.store.getAt(rowIndex).get("xxjbxxid");
    		this.eidtWindow.show(null,function(){
    			this.editForm.$load({
    				params:{'ckdw.xxjbxxid':xxjbxxid},
    				action:"ckdw_loadCkdw.do",
    				handler:function(form,result,scope){
    					
    				}
    			});
    		},this)
    	},this);    	
    },   
   	initComponent :function(){  
   		this.mainPanel = this.createMainPanel();
   		this.editForm   = this.createCkdwEditForm();
   		this.eidtWindow = this.createCkdwWindow();
   		this.eidtWindow.add(this.editForm);
   		   		
   		this.orgTree = new Ext.ux.TreePanel({region:"west",
			             rootVisible:false,
			             title:"组织单位",
						 collapseMode : "mini",
			             split:true,
			             minSize: 120,
			             width:200,
			             maxSize: 300,
			             autoScroll:true,
   						 action:"ckdw_getZkdw.do"
   		});	
   		this.panelCkdw = new Ext.Panel({   			
   			layout:"border",
    		items:[this.orgTree,{
    			layout: 'border',
    	        region:'center',
    	        border: false,
    	        split:true,
    			margins: '2 0 5 5',
    	        width: 275,
    	        minSize: 100,
    	        maxSize: 500,
    			items: [this.mainPanel]
    		}]
   		});
   		this.myTabs = new Ext.TabPanel({
		    id:'myTabsZ',
		    region:'center',
		    margins:'0 5 0 0',
		    activeTab: 0,
		    enableTabScroll:true,
		    minTabWidth:135,
		    resizeTabs:true,
		    headerStyle:'display:none',
		    border: false,
		    tabWidth:135,
		    border:false,
		    items:[{layout: 'fit', index: 0, border: false, items: [this.panelCkdw],title:"组考单位"}]
		});   		
	},
	createMainPanel:function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "单位部署编码",   align:"center", sortable:true, dataIndex:"xxbsm",width:120},
			{header: "组考单位名称",   align:"center", sortable:true, dataIndex:"xxmc",width:200},
			{header: "单位地址",   	align:"center", sortable:true, dataIndex:"xxdz",width:220},
			{header: "单位负责人",   	align:"center", sortable:true, dataIndex:"xzxm",width:100},
			{header: "负责人移动电话", 	align:"center", sortable:true, dataIndex:"xzsjhm",width:100},
			{header: "邮政编码",   	align:"center", sortable:true, dataIndex:"yzbm",width:100}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			tbar:{
				cls:"ext_tabr",
				items:[ 
				       "->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteCkdw,scope:this}
				       ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateCkdw,scope:this}
				       ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.showEditWindow()},scope:this}				       
				      ]
			},
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			pdf:true,
			excelTitle:"组考单位信息列表",
			action:"ckdw_getListPage.do?dwlx=1",
			fields :["xxjbxxid","xxbsm","xxmc","xxdz","xzxm","xzsjhm","bgdh","yzbm","sszgjyxzdm","sszgjyxzmc"],
			border:false
		});
			
		this.xxmc	= new Ext.form.TextField({fieldLabel:"组考单位名称",name:"ckdw.xxmc",id:"xxmc_selZ",maxLength:200,width:160});
		var cx 		= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.initQueryDate,scope:this});
		var cz 		= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.search = new Ext.form.FormPanel({
			id:"search_formCkZ",
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
		    		          {html:"组考单位名称：",baseCls:"label_right",width:170},
		    		          {items:[this.xxmc],baseCls:"component",width:170},
		    		          {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
							] 
		    	   }]  
		       }]		       		      
	    })
		return new Ext.Panel({layout:"border",region:"center",items:[this.search,this.grid]})
	},
	createNjPanel:function(){ 
		this.editNjForm   = this.createNjForm();		
   		this.editNjWindow = this.createNjWindow();   		
   		this.editNjWindow.add({items:[this.editNjForm]});   		
   		
   		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [sm,
		          {header: "组考单位",   	align:"center", sortable:true, dataIndex:"XXMC"},
		          {header: "等级名称",   	align:"center", sortable:true, dataIndex:"NJMC"},
		          {header: "年份",   	align:"center", sortable:true, dataIndex:'RXNF'},
		          {header: "学习阶段",   	align:"center", sortable:true, dataIndex:"JYJD"},
		          {header: "等级",   	align:"center", sortable:true, dataIndex:"DJMC"},
		          {header: "状态",   	align:"center", sortable:true, dataIndex:'ZT'}];
   		
		this.gridNj = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:'等级信息表',
			id:"gdZ",
			page:true,
			rowNumber:true,
			tbar:[ 
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.returnCkdw,scope:this}	
			      ,"->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteNj,scope:this}
			      ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateNj,scope:this}
			      ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.showEidtNjWindow()},scope:this}			      		      
			   ],
			region:"center",
			action:'nj_getListPage.do',
			fields:["XX_NJXX_ID","XX_JBXX_ID","NJMC","RXNF","JYJD","XJNJDM","NJZT","XXMC","ZT","DJMC"],
			border:false
		});		
		//搜索区域
		var nj_sel	= new Ext.ux.form.TextField({name:"nj_sel",id:"nj_selZ",width:180});
    	var cx 		= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.njInfoShow,scope:this});
		var cz 		= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.searchNj.getForm().reset()},scope:this});
		
		this.searchNj = new Ext.form.FormPanel({
			id:"searchNj_formZ",
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
		this.panel_top2=new Ext.Panel({
			id:"panel_topCK2Z",
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			items:[this.searchNj,this.gridNj]
		});		
	},
	createBjPanel:function(){ 
		this.editBjForm   = this.createBjForm();
   		this.editBjWindow = this.createBjWindow();
   		this.editBjWindow.add({items:[this.editBjForm]});
   		
   		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [sm,
		          {header: "组考单位",   	align:"center", sortable:true, dataIndex:"XXMC"},
		          {header: "等级",   	align:"center", sortable:true, dataIndex:"NJMC"},
		          {header: "科目",   	align:"center", sortable:true, dataIndex:'BJMC'},
		          {header: "状态",   	align:"center", sortable:true, dataIndex:"ZT"}];
   		
		this.gridBj = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:'科目信息表',
			id:"gdZ",
			page:true,
			rowNumber:true,
			tbar:[
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.returnCkdw,scope:this}
			      ,"->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteBj,scope:this}
			      ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateBj,scope:this}
			      ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.showEidtBjWindow()},scope:this}			      			      
			   ],
			region:"center",
			action:'bj_getListPage.do',
			fields:["XX_BJXX_ID","XX_JBXX_ID","XX_NJXX_ID","BH","BJMC","YXBZ","XXMC","ZT","NJMC"],
			border:false
		});
		//搜索区域
		var kc_sel	= new Ext.ux.form.TextField({name:"kc_sel",id:"kc_selZ",width:180});
    	var cx 		= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.bjInfoShow,scope:this});
		var cz 		= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.searchBj.getForm().reset()},scope:this});
		
		this.searchBj = new Ext.form.FormPanel({
			id:"searchBj_formZ",
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
		this.panel_top3=new Ext.Panel({
			id:"panel_topCK3Z",
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			items:[this.searchBj,this.gridBj]
		});	
	},
	njInfoShow:function(){
		var xx_jbxx_id = xxjbxxid;
		var njmc = Ext.getCmp("nj_selZ").getValue();
		this.gridNj.$load({"xx_jbxx_id":xx_jbxx_id,"njmc":njmc});
	},
	bjInfoShow:function(){
		var xx_jbxx_id = xxjbxxid;
		var bjmc = Ext.getCmp("kc_selZ").getValue();
		this.gridBj.$load({"xx_jbxx_id":xx_jbxx_id,"bjmc":bjmc});
	},
    /** 初始化界面 **/
    initFace:function(){    	
    	this.panel_top=new Ext.Panel({
			layout:"fit",
    		id:"panel_topCKZ",
    		region:"north",
    		border:false,
    		items:[this.myTabs]
    	});
    	this.addPanel({layout:"fit",items:[this.panel_top]});  	
    },
    initQueryDate:function(){
    	if(this.getOrgTreeSelectNode() == null) {
    		Ext.Msg.alert("提示","请选择组织单位!");
    	}else {
    		sszgjyxzdm = this.getOrgTreeSelectNode().id; 
    		xxmc = this.getOrgTreeSelectNode().text;
    		this.grid.$load({"ckdw.sszgjyxzdm":this.getOrgTreeSelectNode().id,"ckdw.xxmc":this.xxmc.getValue()});
    	}	    	
    },
    getOrgTreeSelectNode:function(){
    	return this.orgTree.getSelectionModel().getSelectedNode();
    },    
    createCkdwEditForm:function(){
    	var zkdwname	= new Ext.form.TextField({width:220,id:"zkdwnameZ",readOnly:true});
    	var sszgjyxzdm	= new Ext.form.TextField({fieldLabel:"",hidden:true,name:"ckdw.sszgjyxzdm",id:"sszgjyxzdmZ"});
    	var xxjbxxid	= new Ext.form.TextField({fieldLabel:"",hidden:true,name:"ckdw.xxjbxxid",id:"xxjbxxidZ"});
    	var xxbsm		= new Ext.form.TextField({width:220,name:"ckdw.xxbsm",id:"xxbsmZ",maxLength:15,allowBlank :false,blankText:"单位部署编码不能为空！"});
		var xxmc		= new Ext.form.TextField({width:220,name:"ckdw.xxmc",id:"xxmcZ",maxLength:100,allowBlank :false,blankText:"组考单位名称不能为空！"});
		var xxywmc		= new Ext.form.TextField({width:220,name:"ckdw.xxywmc",id:"xxywmcZ",maxLength:100,allowBlank :true});
		var xxdz		= new Ext.form.TextField({width:220,name:"ckdw.xxdz",id:"xxdzZ",maxLength:180,allowBlank :false,blankText:"单位地址不能为空！"});
		var xzxm		= new Ext.form.TextField({width:220,name:"ckdw.xzxm",id:"xzxmZ",maxLength:36,allowBlank :false,blankText:"负责人姓名不能为空！"});
		var xzsjhm		= new Ext.form.TextField({width:220,name:"ckdw.xzsjhm",id:"xzsjhmZ",maxLength:20,allowBlank :true,regex : /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,regexText : '电话号码格式错误！'});
		var bgdh		= new Ext.form.TextField({width:220,name:"ckdw.bgdh",id:"bgdhZ",maxLength:30,allowBlank :true});
		var yzbm		= new Ext.form.TextField({width:220,name:"ckdw.yzbm",id:"yzbmZ",maxLength:6,allowBlank :true});
		var dwdzxx		= new Ext.form.TextField({width:220,name:"ckdw.dwdzxx",id:"dwdzxxZ",maxLength:40,allowBlank :true,regex : /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,regexText : '邮箱地址格式错误！'});
		var xxxz		= new Ext.form.NumberField({width:220,name:"ckdw.xxxz",id:"xxxzZ",maxLength:3,allowBlank :true});
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 2 
			}, 
			items:[
			       	{html:"组织单位：",baseCls:"label_right"},
			       	{html:"<font class='required'>&nbsp;&nbsp;</font>",items:[zkdwname],baseCls:"component",width:250},
			       	{html:"单位编码：",baseCls:"label_right"},
					{html:"<font class='required'>*</font>",items:[xxbsm],baseCls:"component",width:250},
					{html:"组考单位：",baseCls:"label_right"},
					{html:"<font class='required'>*</font>",items:[xxmc],baseCls:"component",width:250},
					{html:"英文名称：",baseCls:"label_right"},
					{html:"",items:[xxywmc],baseCls:"component",width:250},
					{html:"<font style='font-weight:bold;color:#102D91;'>考点地址：</font>",baseCls:"label_right"},
					{html:"<font class='required'>*</font>",items:[xxdz],baseCls:"component",width:250},
					{html:"负责人：",baseCls:"label_right"},
					{html:"<font class='required'>*</font>",items:[xzxm],baseCls:"component",width:250},					
					{html:"负责人电话：",baseCls:"label_right"},{html:"",items:[xzsjhm],baseCls:"component",width:250},
					{html:"办公电话：",baseCls:"label_right"},{html:"",items:[bgdh],baseCls:"component",width:250},
					{html:"邮政编码：",baseCls:"label_right"},{html:"",items:[yzbm],baseCls:"component",width:250},
					{html:"邮箱地址：",baseCls:"label_right"},{html:"",items:[dwdzxx],baseCls:"component",width:250},					
					{html:"学制：",baseCls:"label_right"},{html:"",items:[xxxz],baseCls:"component",width:250}
				] 
		}); 		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"95%"},
			items:[
				{layout:'form', xtype:"fieldset",title:"组考单位信息",items:[panel]},
				{items:[xxjbxxid,sszgjyxzdm]}
		]});
    },
    createCkdwWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addCkdw,scope:this});
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
			 	title:"组考单位信息维护"});		
    },
    showEditWindow:function() {
    	if(this.getOrgTreeSelectNode() == null) {
    		Ext.Msg.alert("提示","请选择组织单位!");
    	}else {
    		Ext.getCmp('zkdwnameZ').setValue(this.getOrgTreeSelectNode().text);
    		Ext.getCmp('xxbsmZ').setReadOnly(false);
    		Ext.getCmp('xxjbxxidZ').setValue("");
    		Ext.getCmp('xxbsmZ').setValue("");
    		Ext.getCmp('xxmcZ').setValue("");
    		Ext.getCmp('xxywmcZ').setValue("");
    		Ext.getCmp('xxdzZ').setValue("");
    		Ext.getCmp('xzxmZ').setValue("");
    		Ext.getCmp('xzsjhmZ').setValue("");
    		Ext.getCmp('bgdhZ').setValue("");
    		Ext.getCmp('yzbmZ').setValue("");
    		Ext.getCmp('dwdzxxZ').setValue("");
    		Ext.getCmp('xxxzZ').setValue("");
    		this.eidtWindow.show();
    	}    	
    },
    addCkdw:function(){
    	if (this.getOrgTreeSelectNode()!=null) {
    		Ext.getCmp('sszgjyxzdmZ').setValue(this.getOrgTreeSelectNode().id);
    	}    	    	
    	this.editForm.$submit({
    		action:"ckdw_addCkdw.do?dwlx=1",
    		handler:function(form,result,thiz){
    			thiz.eidtWindow.hide();
    			thiz.grid.$load();
    		},
    		scope:this
    	});
    },
    deleteCkdw:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedCkdws = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var xxdms = "'";
    	for(var i = 0; i < selectedCkdws.length; i++) {
    		if(i != selectedCkdws.length - 1) {
    			xxdms += selectedCkdws[i].get("xxjbxxid") + "','";
    		}else {
    			xxdms += selectedCkdws[i].get("xxjbxxid") + "'";
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
    						params:{'xxdms':xxdms},
    						action:"ckdw_delCkdw.do",
    						handler:function(){
    							thiz.grid.$load();
    						}
    					})    					
    				}
    			}
    	});
    },
    updateCkdw:function(){    	
		var selectedCkdws = this.grid.getSelectionModel().getSelections();
    	if(selectedCkdws.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
    	}  
    	if (this.getOrgTreeSelectNode()!=null) {
    		Ext.getCmp('zkdwnameZ').setValue(this.getOrgTreeSelectNode().text);
    	}else {
    		Ext.getCmp('zkdwnameZ').setValue(selectedCkdws[0].get("sszgjyxzmc"));
    		Ext.getCmp('sszgjyxzdmZ').setValue(selectedCkdws[0].get("sszgjyxzdm"));
    	}
    	var xxdm = selectedCkdws[0].get("xxjbxxid");
		Ext.getCmp('xxbsmZ').setReadOnly(true);
		Ext.getCmp('xxjbxxidZ').setValue(selectedCkdws[0].get("xxjbxxid"));
		Ext.getCmp('xxbsmZ').setValue(selectedCkdws[0].get("xxbsm"));
		Ext.getCmp('xxmcZ').setValue(selectedCkdws[0].get("xxmc"));
		Ext.getCmp('xxywmcZ').setValue(selectedCkdws[0].get("xxywmc"));
		Ext.getCmp('xxdzZ').setValue(selectedCkdws[0].get("xxdz"));
		Ext.getCmp('xzxmZ').setValue(selectedCkdws[0].get("xzxm"));
		Ext.getCmp('xzsjhmZ').setValue(selectedCkdws[0].get("xzsjhm"));
		Ext.getCmp('bgdhZ').setValue(selectedCkdws[0].get("bgdh"));
		Ext.getCmp('yzbmZ').setValue(selectedCkdws[0].get("yzbm"));
		Ext.getCmp('dwdzxxZ').setValue(selectedCkdws[0].get("dwdzxx"));
		Ext.getCmp('xxxzZ').setValue(selectedCkdws[0].get("xxxz"));
		this.eidtWindow.show(null,function(){},this);
	},
	updateCkdwNj:function(){
		var selectedCkdws = this.grid.getSelectionModel().getSelections();
    	if(selectedCkdws.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行年级设置！");
    		return;
    	}
		var xxdm = selectedCkdws[0].get("xxjbxxid");
		xxjbxxid = xxdm;
		this.createNjPanel();
    	var panel=Ext.getCmp("panel_topCKZ");
    	panel.remove(Ext.getCmp("myTabsZZ"));
  		panel.add(this.panel_top2);
  		panel.doLayout(false);
  		this.gridNj.$load({"xx_jbxx_id":xxdm});  				
    },
    updateCkdwBj:function(){
    	var selectedCkdws = this.grid.getSelectionModel().getSelections();
    	if(selectedCkdws.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行科目设置！");
    		return;
    	}
		var xxdm = selectedCkdws[0].get("xxjbxxid");
		xxjbxxid = xxdm;
		this.createBjPanel();
    	var panel=Ext.getCmp("panel_topCKZ");
    	panel.remove(Ext.getCmp("myTabsZZ"));
  		panel.add(this.panel_top3);
  		panel.doLayout(false);
  		this.gridBj.$load({"xx_jbxx_id":xxdm});				
    },
    createNjForm:function(){
		var xxmc = new Ext.form.TextField({id:"xxmcZ",maxLength:50,width:210,readOnly:true}); 
    	var njmc = new Ext.form.TextField({id:"njmcZ",maxLength:30,width:210,allowBlank:false,blankText:"等级名称为必选项！"});
    	var rxnf = new Ext.form.TextField({id:"rxnfZ",maxLength:4,width:210,allowBlank:false,blankText:"年份为必选项！",regex:/^[0-9]*[1-9][0-9]*$/,regexText : '年份为正整数！'});
    	var jyjd = new Ext.form.TextField({id:"jyjdZ",maxLength:1,width:210,allowBlank:false,blankText:"学习阶段为必选项！"});
    	var dj	 = new Ext.ux.Combox({name:"djZ",dropAction:"sys_enum_dj",width:210,allowBlank:false,blankText:"等级为必选项！"});
    	var njzt = new Ext.form.ComboBox({name:"njztZ",store:wjcllxStore,value:'1',displayField:'text',valueField:'value',mode:'local',triggerAction:'all',width:210,editable:false,allowBlank:false,blankText:"状态为必选项！"});

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
    createNjWindow:function(){    	
		var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.updateNjSave,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.editNjWindow.hide()},scope:this});
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
	showEidtNjWindow:function() {
		addNj = true;	
		Ext.getCmp('xxmcZ').setValue(xxmc);
    	Ext.getCmp("njmcZ").setValue("");
		Ext.getCmp("rxnfZ").setValue("");
		Ext.getCmp("jyjdZ").setValue("");
		this.editNjForm.getForm().findField("djZ").setValue("");
		this.editNjForm.getForm().findField("njztZ").setValue("");
		Ext.getCmp("njmcZ").setReadOnly(false);
    	this.editNjWindow.show(null,function(){},this);	
    },
	updateNj:function(){		
    	var selectedBuildings = this.gridNj.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
    	}
    	addNj = false;
    	var xxmc = selectedBuildings[0].get("XXMC");
		var njmc = selectedBuildings[0].get("NJMC");
		var rxnf = selectedBuildings[0].get("RXNF");
		var jyjd = selectedBuildings[0].get("JYJD");
		var djdm = selectedBuildings[0].get("XJNJDM");
		var dj = selectedBuildings[0].get("DJMC");
		var njzt = selectedBuildings[0].get("NJZT");
		Ext.getCmp("xxmcZ").setValue(xxmc);
		Ext.getCmp("njmcZ").setValue(njmc);
		Ext.getCmp("rxnfZ").setValue(rxnf);
		Ext.getCmp("jyjdZ").setValue(jyjd);
		this.editNjForm.getForm().findField("djZ").setValue(djdm);
		this.editNjForm.getForm().findField("njztZ").setValue(njzt);	
		Ext.getCmp("njmcZ").setReadOnly(true);
		this.editNjWindow.show(null,function(){},this)
    },
    updateNjSave:function(){
    	var njmc = Ext.getCmp("njmcZ").getValue();
    	var rxnf = Ext.getCmp("rxnfZ").getValue();
    	var jyjd = Ext.getCmp("jyjdZ").getValue();
    	var djdm = this.editNjForm.getForm().findField("djZ").getValue();
    	var njzt = this.editNjForm.getForm().findField("njztZ").getValue();    	
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
    	var selectedBuildings = this.gridNj.getSelectionModel().getSelections();     	
    	if (selectedBuildings!=null&&selectedBuildings!=''&&!addNj) {
    		var xx_njxx_id = selectedBuildings[0].get("XX_NJXX_ID");
    		var xx_jbxx_id = selectedBuildings[0].get("XX_JBXX_ID");
    		this.editNjForm.$submit({
    			action:'nj_update.do',
    			params: {    
    				'xx_njxx_id':xx_njxx_id,'xx_jbxx_id':xx_jbxx_id,'njmc':njmc,'rxnf':rxnf,'jyjd':jyjd,'djdm':djdm,'njzt':njzt
    	 		},    
        		handler:function(form,result,thiz){
        			thiz.editNjWindow.hide();
        			thiz.gridNj.$load({"xx_jbxx_id":xx_jbxx_id});
        			thiz.gridNj.$load({"xx_jbxx_id":xx_jbxx_id});
        		},
        		scope:this
        	});    		        	
    	}else {
    		var xx_jbxx_id=xxjbxxid;
    		this.editNjForm.$submit({
    			action:'nj_update.do',
    			params: {    
    	 			'xx_njxx_id':'','xx_jbxx_id':xx_jbxx_id,'njmc':njmc,'rxnf':rxnf,'jyjd':jyjd,'djdm':djdm,'njzt':njzt
    	 		},    
        		handler:function(form,result,thiz){
        			thiz.editNjWindow.hide();
        			thiz.gridNj.$load({"xx_jbxx_id":xx_jbxx_id});
        			thiz.gridNj.$load({"xx_jbxx_id":xx_jbxx_id});
        		},
        		scope:this
        	});
    	}   	    	
    },
    deleteNj:function(){
    	var thiz = this;
    	var selectedBuildings = this.gridNj.getSelectionModel().getSelections();
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
			           	thiz.gridNj.$load({"xx_jbxx_id":xx_jbxx_id});
			           	thiz.gridNj.$load({"xx_jbxx_id":xx_jbxx_id});			           	
			        },
			        failure: function(resp,opts){
			            Ext.Msg.alert('错误', "删除失败！");
			        }     				
			   });
    		}
        });    	  			
    },
    createBjForm:function(){
		var xxmc = new Ext.form.TextField({id:"xxmc1Z",maxLength:50,width:210,readOnly:true}); 
    	var nj   = new Ext.ux.Combox({autoLoad:true,name:"njZ",dropAction:"njBySchool",params:xxjbxxid,width:210,allowBlank:false,blankText:"等级名称为必选项！"});
    	var bjmc = new Ext.form.TextField({id:"bjmcZ",maxLength:30,width:210,allowBlank:false,blankText:"科目名称为必选项！"});
    	var bjzt = new Ext.form.ComboBox({name:"bjztZ",store:wjcllxStore,value:'1',displayField:'text',valueField:'value',mode:'local',triggerAction:'all',width:210,editable:false,allowBlank:false,blankText:"状态为必选项！"});
	
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
	createBjWindow:function(){		
		var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.updateBjSave,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.editBjWindow.hide()},scope:this});
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
    showEidtBjWindow:function() {
    	addBj = true;
    	Ext.getCmp('xxmc1Z').setValue(xxmc);
    	Ext.getCmp("bjmcZ").setValue("");    
		this.editBjForm.getForm().findField("njZ").setValue("");
		this.editBjForm.getForm().findField("bjztZ").setValue("");
    	this.editBjWindow.show(null,function(){},this);
    },
	updateBj:function(){		
		var selectedBuildings = this.gridBj.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
    	}
    	addBj = false;
    	var xxmc = selectedBuildings[0].get("XXMC");
		var nj = selectedBuildings[0].get("XX_NJXX_ID");
		var bjmc = selectedBuildings[0].get("BJMC");    		
		var bjzt = selectedBuildings[0].get("YXBZ");
		Ext.getCmp("xxmc1Z").setValue(xxmc);
		Ext.getCmp("bjmcZ").setValue(bjmc);    
		this.editBjForm.getForm().findField("njZ").setValue(nj);
		this.editBjForm.getForm().findField("bjztZ").setValue(bjzt);		    	
		this.editBjWindow.show(null,function(){},this)
    },
    updateBjSave:function(){
    	var bjmc = Ext.getCmp("bjmcZ").getValue();
    	var xx_njxx_id = this.editBjForm.getForm().findField("njZ").getValue();
    	var bjzt = this.editBjForm.getForm().findField("bjztZ").getValue();    	
    	if(bjmc==""){
    		Ext.Msg.alert('消息',"科目名称不能为空！");
    		return;
    	}
    	if(bjzt==""){
    		Ext.Msg.alert('消息',"状态不能为空！");
    		return;
    	}
    	var thiz = this;
    	var selectedBuildings = this.gridBj.getSelectionModel().getSelections();
    	if (selectedBuildings!=null&&selectedBuildings!=''&&!addBj) {
    		var xx_bjxx_id = selectedBuildings[0].get("XX_BJXX_ID");
    		var xx_jbxx_id = selectedBuildings[0].get("XX_JBXX_ID");
    		this.editBjForm.$submit({
    			action:'bj_update.do',
    			params: {    
    				'xx_bjxx_id':xx_bjxx_id,'xx_jbxx_id':xx_jbxx_id,'bjmc':bjmc,'xx_njxx_id':xx_njxx_id,'bjzt':bjzt
    	 		},    
        		handler:function(form,result,thiz){
        			thiz.editBjWindow.hide();
        			thiz.gridBj.$load({"xx_jbxx_id":xx_jbxx_id});
        			thiz.gridBj.$load({"xx_jbxx_id":xx_jbxx_id});
        		},
        		scope:this
        	});    		        	
    	}else {
    		var xx_jbxx_id=xxjbxxid;
    		this.editBjForm.$submit({
    			action:'bj_update.do',
    			params: {    
    				'xx_bjxx_id':'','xx_jbxx_id':xx_jbxx_id,'bjmc':bjmc,'xx_njxx_id':xx_njxx_id,'bjzt':bjzt
    	 		},    
        		handler:function(form,result,thiz){
        			thiz.editBjWindow.hide();
        			thiz.gridBj.$load({"xx_jbxx_id":xx_jbxx_id});
        			thiz.gridBj.$load({"xx_jbxx_id":xx_jbxx_id});
        		},
        		scope:this
        	});
    	}   	    	
    },
    deleteBj:function(){
    	var thiz = this;
    	var selectedBuildings = this.gridBj.getSelectionModel().getSelections();
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
			           	thiz.gridBj.$load({"xx_jbxx_id":xx_jbxx_id});
			           	thiz.gridBj.$load({"xx_jbxx_id":xx_jbxx_id});			           	
			        },
			        failure: function(resp,opts){
			            Ext.Msg.alert('错误', "删除失败！");
			        }			            				
    			});                         
		    }});    	  			
    },
    returnCkdw:function(){
    	this.initComponent();
    	this.initListener();
    	var panel=Ext.getCmp("panel_topCKZ");
  		panel.remove(Ext.getCmp("panel_topCK2Z"));
  		panel.remove(Ext.getCmp("panel_topCK3Z"));
  		panel.add(this.myTabs);
  		panel.doLayout(false);
  		this.grid.$load({"ckdw.sszgjyxzdm":sszgjyxzdm,"ckdw.xxjbxxid":xxjbxxid});		
	}
});