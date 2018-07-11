Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
	    this.initDate();
		this.initComponent();
		this.initListener();
		this.initFace();
		this.mianPanelQueryDate();		
    },
    /** 初始化页面、内存等基本数据 **/
    initDate:function(){
    	
    },    
	/** 对组件设置监听 **/
    initListener:function(){
    
    },
    /** 组件初始化 **/
   	initComponent :function(){
   		this.mainPanel = this.createMainPanel();
   		this.deletePanel = this.createDeletePanel();
   		this.supplementPanel = this.createSupplementPanel();
   		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.mainPanel,this.deletePanel,this.supplementPanel]   
        });       
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel(this.tabPanel);
    },
    mianPanelQueryDate:function(){
    	this.maingrid.$load();
    },
    createMainPanel:function() {
    	//初始化搜索区
   		this.xnxq_sel	= new Ext.ux.form.XnxqField({name:"exmxnxq",id:"exmxnxq_sel",width:200,readOnly:true,
   			listeners : {
  	          'change' : {
  	           fn : function(field) {  	              	
  	           },
  	           scope : this
  	          }
   			}
   		});//,code:'2007-2008,1'});
   		
   		//Ext.getCmp('xnxq_sel').setCode("2015-2016,1");
		var cx 			= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.maingridExamStu,scope:this});
		var cz 			= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.mainsearch.getForm().reset()},scope:this});
		
		this.mainsearch = new Ext.form.FormPanel({
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
					       {html:"年度：",baseCls:"label_right",width:170},
					       {items:[this.xnxq_sel],baseCls:"component",width:200},
					       {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
					      ] 
                  	}]  
		      	}]  
	    	})
		
		//初始化数据列表区
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true,
			listeners:{
    			"selectionchange":function(sm){
    				var str = sm.getSelections();
    				if(str[0].get("SHFLAG")=="1"){
    					Ext.getCmp('xgsb').setDisabled(true);
    					Ext.getCmp('xgsc').setDisabled(true);
    					Ext.getCmp('xgbb').setDisabled(true);
    				}else{
    					Ext.getCmp('xgsb').setDisabled(false);
    					Ext.getCmp('xgsc').setDisabled(false);
    					Ext.getCmp('xgbb').setDisabled(false);
    				}
    			}
    		}
    	});
		var cm = [
			sm,
			{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   align:"center", sortable:true, dataIndex:"XQ"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME"},
			{header: "组织单位",   align:"center", sortable:true, dataIndex:"MC"},
			{header: "参考单位",   	align:"center", sortable:true, dataIndex:"CKDW"},
			{header: "考生数量",   align:"center", sortable:true, dataIndex:"SL"},
			{header: "审核状态",   align:"center", sortable:true, dataIndex:"SHZT"}
		];
		this.maingrid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:'考生报名-修改参考考生',
			tbar:{
				cls:"ext_tabr",
				items:[ 
				  "->",{xtype:"button",id:"xgsb",text:"上报",iconCls:"p-icons-upload",handler:this.uploadExamStu,scope:this}
				  ,"->",{xtype:"button",id:"xgsc",text:"删除",iconCls:"p-icons-delete",handler:this.switchDeletePanel,scope:this}
				  ,"->",{xtype:"button",id:"xgbb",text:"补报",iconCls:"p-icons-update",handler:this.switchSupplementPanel,scope:this}
				  ]
			},
			page:true,
			rowNumber:true,
			region:"center",
			action:"examstumodify_getListPage.do",
			fields :["LCID","XN","XQ","EXAMNAME","MC","CKDW","SL","DWID","DWTYPE","SHZT","SHFLAG"],
			border:false
			});
		return new Ext.Panel({layout:"border",region:"center",
		 	items:[this.mainsearch,this.maingrid]
		})
    },
    createDeletePanel:function() {
    	//初始化搜索区
    	var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel",width:180,readOnly:true});
    	var organ_lable = "参考单位：";
    	var xmkhxjh_sel	= new Ext.ux.form.TextField({name:"xmkhxjh_sel",id:"xmkhxjh_sel",width:180});    	    	
   		/*var store = new Ext.data.JsonStore({
    		autoLoad:false,
    		url:"dropListAction_getXjzt.do",
    		fields :["NAME","CODE"]
    	});
   		store.reload({});
   		var xjzt_sel	= new Ext.ux.form.LovCombo({
   			store : store,
   			name : "serviceitemno",
   			displayField : "NAME",
   			valueField : "CODE",
   			mode : "local",
   			id:"xjzt_sel",
   			editable : false,
   			triggerAction : "all"});*/
   		
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.deletegridExamStu,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:this.deleteformReset,scope:this});		
		this.deletesearch = new Ext.form.FormPanel({
			   region: "north",
		       height:120,
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
		        	 items:[{html:organ_lable,baseCls:"label_right",width:150},
							{items:[organ_sel],baseCls:"component",width:190},
							{html:"姓名/考号/身份证号：",baseCls:"label_right",width:150},
							{items:[xmkhxjh_sel],baseCls:"component",width:190},
							//{html:"",baseCls:"label_right",width:150},
							{layout:"absolute", items:[cx,cz],baseCls:"component",width:190}] 
                  	}]  
		       	}]  
		})
		
		//初始化数据列表区
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "姓名",   align:"center", sortable:true, dataIndex:"XM"},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
			{header: "考号",   align:"center", sortable:true, dataIndex:"KSCODE"},
			{header: "身份证号",   align:"center", sortable:true, dataIndex:"SFZJH"}
		];
		this.deletegrid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:'考生报名-删除不参考考生',
			tbar:{
				cls:"ext_tabr",
				items:[ 
				       "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.switchMainPanel,scope:this},
				       "->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteExamStuOpe,scope:this}
				      ]
			},
			page:true,
			rowNumber:true,
			excel:true,
			region:"center",
			action:"examstumodifyIn_getListPage.do",
			fields :["KSID","LCID","XM","XB","KSCODE","XJH","SFZJH"],
			border:false
		});
		return new Ext.Panel({layout:"border",region:"center",
		 	items:[this.deletesearch,this.deletegrid]
		})
    },
    deleteformReset:function(){
    	this.deletesearch.getForm().findField('organ_sel').reset();
		//Ext.getCmp('xjzt_sel').deselectAll();
		Ext.getCmp('xmkhxjh_sel').reset();
    },
    supformReset:function(){
    	this.supsearch.getForm().findField('sup_organ_sel').reset();
		//Ext.getCmp('sup_xjzt_sel').deselectAll();
		Ext.getCmp('sup_xmkhxjh_sel').reset();
    },
    //初始化补报面板
    createSupplementPanel:function() {
    	//初始化搜索区
    	var organ_sel	= new Ext.ux.form.OrganField({name:"sup_organ_sel",readOnly:true});
    	var organ_lable = "参考单位：";    	
    	var xmkhxjh_sel	= new Ext.ux.form.TextField({name:"sup_xmkhxjh_sel",id:"sup_xmkhxjh_sel",width:180});    	
   		/*var store = new Ext.data.JsonStore({
    		autoLoad:false,
    		url:"dropListAction_getXjzt.do",
    		fields :["NAME","CODE"]
    	});
   		store.reload({});
   		var xjzt_sel	= new Ext.ux.form.LovCombo({
   			store : store,
   			name : "serviceitemno",
   			displayField : "NAME",
   			valueField : "CODE",
   			mode : "local",
   			id:"sup_xjzt_sel",
   			editable : false,
   			triggerAction : "all"});*/
   		
		var cx 			= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.supplementgridExamStu,scope:this});
		var cz 			= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:this.supformReset,scope:this});
		
		this.supsearch = new Ext.form.FormPanel({
			   region: "north",
		       height:120,
		       items:[{  
		         layout:'form',  
		         xtype:'fieldset',  
		         style:'margin:10 10',
		         title:'查询条件',  
		         items: [
                    {
                    	xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 5
						}, 
						baseCls:"table",
						items:[
							{html:organ_lable,baseCls:"label_right",width:150},
							{items:[organ_sel],baseCls:"component",width:190},
							{html:"姓名/身份证号：",baseCls:"label_right",width:150},
							{items:[xmkhxjh_sel],baseCls:"component",width:190},
							//{html:"学籍状态：",baseCls:"label_right",width:110},
							//{items:[xjzt_sel],baseCls:"component",width:190},
							//{html:"",baseCls:"label_right",width:150},
							{layout:"absolute", items:[cx,cz],baseCls:"component",width:190}
							] 
                    }]  
		       }]  
	    	})
		
		//初始化数据列表区
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "姓名",   align:"center", sortable:true, dataIndex:"XM"},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
			{header: "身份证号",   align:"center", sortable:true, dataIndex:"SFZJH"}
		];
		this.supgrid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:'考生报名-补报参考考生',
			tbar:{
				cls:"ext_tabr",
				items:[ 
				  "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.switchMainPanel,scope:this}
				  ,"->",{xtype:"button",text:"补报",iconCls:"p-icons-update",handler:this.supExamStuOpe,scope:this}
				  ]
			},
			page:true,
			rowNumber:true,
			region:"center",
			action:"examstumodifyNo_getListPage.do",
			fields :["XM","XB","XJH","SFZJH"],
			border:false
			});
		return new Ext.Panel({layout:"border",region:"center",
		 	items:[this.supsearch,this.supgrid]
		})
    },
    //主界面查询
    maingridExamStu:function() {
    	var xnxqCode = Ext.getCmp('exmxnxq_sel').getCode();
    	this.maingrid.$load({"xnxqId":xnxqCode});
    },
    //删除考生界面查询
    deletegridExamStu:function() {
    	var organ_sel = this.deletesearch.getForm().findField('organ_sel').getCodes();
    	var xmkhxjh_sel = Ext.getCmp('xmkhxjh_sel').getValue();
    	//var xjzt_sel = Ext.getCmp('xjzt_sel').getCheckedValue();
    	var msg = "";
    	if(msg != "") {
    		Ext.MessageBox.alert("消息",msg);
			return;
    	}
    	this.deletegrid.$load({"cusKwExamround.lcid":this.lcid,"schools_s":organ_sel,"xmxhxj_s":xmkhxjh_sel});//,"xjzts_s":xjzt_sel
    },
    //补报考生界面查询
    supplementgridExamStu:function() {
    	var organ_sel = this.supsearch.getForm().findField('sup_organ_sel').getCodes();
    	var xmkhxjh_sel = Ext.getCmp('sup_xmkhxjh_sel').getValue();
    	//var xjzt_sel = Ext.getCmp('sup_xjzt_sel').getCheckedValue();
    	var msg = "";
    	if(msg != "") {
    		Ext.MessageBox.alert("消息",msg);
			return;
    	}
    	this.supgrid.$load({"cusKwExamround.lcid":this.lcid,"schools_s":organ_sel,"xmxhxj_s":xmkhxjh_sel});//,"xjzts_s":xjzt_sel
    },
    //删除面板切换
    switchDeletePanel:function() {
    	var selected =  this.maingrid.getSelectionModel().getSelected();
		if(!selected){
			Ext.MessageBox.alert("消息","请选择一条记录！");
			return;
		}
		var selecteds = this.maingrid.getSelectionModel().getSelections();
		for(var i = 0; i < selecteds.length; i++) {
			this.lcid = selecteds[i].get("LCID");
		}
		this.tabPanel.setActiveTab(this.deletePanel);
		//this.deletegridExamStu();
    },
    //补报面板切换
    switchSupplementPanel:function() {
    	var selected =  this.maingrid.getSelectionModel().getSelected();
		if(!selected){
			Ext.MessageBox.alert("消息","请选择一条记录！");
			return;
		}
		var selecteds = this.maingrid.getSelectionModel().getSelections();
		if(selecteds.length > 1){
			Ext.MessageBox.alert("消息","请选择一条记录！");
			return;
		}
		if(selecteds[0].get("SL") ==  '0'){
			Ext.MessageBox.alert("消息","未抽取考生的考试，请先抽取考生！");
			return;
		}
		for(var i = 0; i < selecteds.length; i++) {
			this.lcid = selecteds[i].get("LCID");
		}
		this.tabPanel.setActiveTab(this.supplementPanel);
		//this.supplementgridExamStu();
    },
    //删除操作
    deleteExamStuOpe:function() {
    	var selected =  this.deletegrid.getSelectionModel().getSelected();
		if(!selected){
			Ext.MessageBox.alert("消息","请选择一条记录！");
			return;
		}
		var selectedExamStu = this.deletegrid.getSelectionModel().getSelections();
    	var thiz = this;
    	var ksids = "'";
    	for(var i = 0; i < selectedExamStu.length; i++) {
    		if(i != selectedExamStu.length - 1) {
    			ksids += selectedExamStu[i].get("KSID") + "','";
    		}else {
    			ksids += selectedExamStu[i].get("KSID") + "'";
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
    						timeout: 3600000,
    						params:{"lcid":thiz.lcid,"ksids":ksids},
    						action:"examstumodify_delExamStu.do",
    						handler:function(){
    							thiz.deletegridExamStu();
    						}
    					});
    					
    				}
    			}
    		})
    },
    //补报操作
    supExamStuOpe:function() {
    	var selected =  this.supgrid.getSelectionModel().getSelected();
		if(!selected){
			Ext.MessageBox.alert("消息","请选择一条记录！");
			return;
		}
		var selectedExamStu = this.supgrid.getSelectionModel().getSelections();
    	var thiz = this;
    	var xjhs = "'";
    	for(var i = 0; i < selectedExamStu.length; i++) {
    		if(i != selectedExamStu.length - 1) {
    			xjhs += selectedExamStu[i].get("XJH") + "','";
    		}else {
    			xjhs += selectedExamStu[i].get("XJH") + "'";
    		}
    	}
    	JH.$request({
			params:{"xjhs":xjhs,"cusKwExamround.lcid":thiz.lcid},
			action:"examstumodify_supExamStu.do", 
			handler:function(){
				thiz.supplementgridExamStu();
			}
		});
    },
    //上报
    uploadExamStu:function(){ 
    	var selecteds = this.maingrid.getSelectionModel().getSelections();
		if(selecteds.length < 1){
			Ext.MessageBox.alert("消息","请选择一条记录！");
			return;
		} 
		for(var i = 0; i < selecteds.length; i++) {
			this.lcid = selecteds[i].get("LCID");
		}
		var lcidP = this.lcid;
    	JH.$request({
			params:{"cusKwExamround.lcid":lcidP},
			action:"examstumodify_uploadExamStu.do", 
			handler:function(){
				thiz.supplementgridExamStu();
			}
		});
    },
    //切换到主界面
    switchMainPanel:function() {
    	this.tabPanel.setActiveTab(this.mainPanel);
		this.mianPanelQueryDate();
		this.deletegrid.getStore().removeAll();
		this.supgrid.getStore().removeAll();
		this.supformReset();
		this.deleteformReset();		
    }
});
