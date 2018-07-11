Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
		this.initComponent();
		this.initFace();
		this.initListener();
		this.initData();
    },    
	/** 对组件设置监听 **/
    initListener:function(){
    
    },   
   	initComponent :function(){
   		this.panel = this.createMainPanel();
    	this.printCarPanel= this.createPrintPanel();
		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
            id:"tabPanel",
       		headerStyle: 'display:none',
       		border:false,
            items: [this.panel,this.printCarPanel]   
        }); 
	},
	createMainPanel:function(){
		//搜索区域
		this.orgTree = new Ext.ux.TreePanel({region:"west",
			             rootVisible:false,
			             title:"组织单位",
						 collapseMode : "mini",
			             split:true,
			             minSize: 120,
			             width:200,
			             maxSize: 300,
			             autoScroll:true,
   						 action:"dropListAction_getOrganNjBjTree.do"
   		});
    	var xbm = new Ext.form.TextField({hidden:true,name:"xbm",id:"xbm"});
		var xb	= new Ext.ux.Combox({id:"xb",dropAction:"sys_enum_xb", width:200,listeners:{
			"select":function(){
				Ext.getCmp("xbm").setValue(Ext.getCmp("xb").getValue());
			}
		}});
		var xmkhxjh_sel	= new Ext.ux.form.TextField({name:"xmxjh_sel",id:"xmxjh_sel",width:200});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectStudentInfo,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset();Ext.getCmp("xbm").setValue('');},scope:this});
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
							{html:"性别：",baseCls:"label_right",width:100},
							{items:[xb],baseCls:"component",width:210},
							{html:"姓名：",baseCls:"label_right",width:100},
							{items:[xmkhxjh_sel],baseCls:"component",width:210},
							{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:170}
						] 
                    }]  
		       }]  
	    })
	    var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "所属单位",   align:"center", sortable:true, dataIndex:"XXMC"},
			//{header: "等级",   align:"center", sortable:true, dataIndex:"NJMC"},
			{header: "科目",   align:"center", sortable:true, dataIndex:"BJMC"},
			{header: "姓名",   align:"center", sortable:true, dataIndex:"XM"},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
			//{header: "民族",   align:"center", sortable:true, dataIndex:"MZ"},
			//{header: "出生年月",   align:"center", sortable:true, dataIndex:"CSRQ"},
			{header: "身份证号",   align:"center", sortable:true, dataIndex:"SFZJH"}
		];	
	    this.grid = new Ext.ux.GridPanel({
	    	cm:cm,
	    	sm:sm,
	    	title:"考生基本信息",
	    	tbar:[ 
	    	      "->",{xtype:"button",text:"生成考生卡",iconCls:"p-icons-update",handler:this.showCheatStudent,scope:this}
	    	     ],
	    	page:true,
	    	rowNumber:true,
	    	excel:true,
	    	pdf:true,
	    	excelTitle:"考生基本信息表",
	    	region:"center",
	    	action:"rollCard_getListPage.do",
	    	fields :["XXMC","NJMC","BJMC","GRBSM","XM","XB","MZ","SFZJH","CSRQ"],
	    	border:false
		});
		return new Ext.Panel({
	    		id:"CheatCardPanel",
	    		title:"考生基本信息",
	    		region:"north",
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.orgTree,{
	    			layout: 'border',
			        region:'center',
			        border: false,
			        split:true,
					margins: '2 0 5 5',
			        minSize: 100,
			        maxSize: 500,
					items: [this.search,this.grid]
				}]
	    });
	},
    createPrintPanel:function(){
		var dataCardPanel=new Ext.Panel({
    		region:'center',
    		border:true,
   			id:"dataCardPanel",
   			html:"",
   			bodyStyle:"border:2px"
   		});
    	return new Ext.Panel({
    		id:"printCarPanel",
    		border:true,
    		title:"打印预览",
    		region:"north",
    		layout:"border",
    		tbar:[ 
    		      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhuiMain,scope:this}
    		      ,"->",{xtype:"button",text:"打印",iconCls:"p-icons-print",handler:this.printCheatStudent,scope:this}
				],
			items:[dataCardPanel]			
    	});
    },
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"fit",items:[this.tabPanel]});
    },
    initData:function(){
    	this.grid.$load();
    },
    selectStudentInfo:function(){
    	//查询考生基本信息
    	var treeSelected = this.orgTree.getSelectionModel().getSelectedNode();
    	if(treeSelected == null) {
    		Ext.Msg.alert("提示","请选择机构节点!");
    		return;
    	}
    	if(treeSelected.attributes.index != 2){
    		Ext.Msg.alert("提示","必须选择到参考单位!");
    		return;
    	}
    	var xxdm = treeSelected.id;
    	/*var njbj = treeSelected.id;
    	var nj = treeSelected.parentNode.id;
    	if(nj.indexOf("_")>0){
    		nj = nj.split("_")[1];
    	}*/
    	var xb = Ext.getCmp("xbm").getValue();
    	var xmxjh = Ext.getCmp("xmxjh_sel").getValue();
    	this.grid.$load({"organ":xxdm,"xbm":xb,"xmxjh":xmxjh});//"njbj":njbj,"nj":nj,
    },
    showCheatStudent:function(){
    	if(this.grid.store.getCount()<1){
    		Ext.MessageBox.alert("消息","请先查询出数据！");
    		return;
    	}
    	//打印页面，显示考生基本信息
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedStudent = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var xjh = "";
    	for(var i = 0; i < selectedStudent.length; i++) {
    		if(i != selectedStudent.length - 1) {
	    		xjh += selectedStudent[i].get("GRBSM") + ",";
    		}else {
	    		xjh += selectedStudent[i].get("GRBSM");
    		}
    	}
    	this.tabPanel.setActiveTab(this.printCarPanel);
  		var height=Ext.getCmp('dataCardPanel').getHeight()-4;
  		var iframe="<fieldset style='height:"+height+"; width:100%; border:0px; padding:0;background:#FFFFFF;'>"+
						"<iframe id='frmCardReport' name='frmCardReport' width='100%' height='"+height+
						"' src='rollCard_selectStudentInfo.do?xjh="+xjh+"' frameborder='0' scrolling='auto'"+
						"style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataCardPanel').innerHTML=iframe;
    },
    fanhuiMain:function(){
    	this.tabPanel.setActiveTab(this.panel);
    	var njbj = "";
    	var treeSelected = this.orgTree.getSelectionModel().getSelectedNode();
    	if(treeSelected != null) {
    		njbj = treeSelected.id;
    	}
    	var xb = Ext.getCmp("xbm").getValue();
    	var xmxjh = Ext.getCmp("xmxjh_sel").getValue();
    	this.grid.$load({"njbj":njbj,"xbm":xb,"xmxjh":xmxjh});
    },
    printCheatStudent:function(){
    	frmCardReport.print();
    }    
});
