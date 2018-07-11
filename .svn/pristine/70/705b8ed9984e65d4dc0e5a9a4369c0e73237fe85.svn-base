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
		var thiz = this;
    	this.orgTree.on("click",function(node){ 
    		/*var str = node.id;
    		if(str.indexOf("_")<=0){
    			Ext.Msg.alert("提示","没有对应的科目信息!");
	    		return;
	    	}
	    	var nj = str.split("_")[1];
	    	if(node.attributes.index != 2){
	    		Ext.Msg.alert("提示","必须选择到参考单位!");
	    		return;
	    	}*/
    		var organ = node.id;//node.parentNode.id;,"nj":nj 
	    	thiz.grid.$load({"organ":organ}); 
    	},this.grid);
    },   
   	initComponent :function(){
		this.panel = this.createMainPanel();
    	this.printPanel= this.createPrintPanel();
		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
            id:"tabPanel",
       		headerStyle: 'display:none',
       		border:false,
            items: [this.panel,this.printPanel]   
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
   						 action:"dropListAction_getOrganNjBjTree.do?type=5"//设置type=5，不显示科目信息
   		}); 	
   		
	    var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "参考单位", align:"center", sortable:true, dataIndex:"BJMC"},
			{header: "人数", align:"center", sortable:true, dataIndex:"XSNUM"},
			{header: "照片数", align:"center", sortable:true, dataIndex:"XSPICNUM"}
		];	
	    this.grid = new Ext.ux.GridPanel({
	    	cm:cm,
	    	sm:sm,
	    	title:"照片情况信息",
	    	tbar:[ 
	    	      "->",{xtype:"button",text:"打印",iconCls:"p-icons-print",handler:this.showCheatStudent,scope:this}
	    	     ],
	    	page:true,
	    	rowNumber:true,
	    	excel:true,
	    	pdf:true,
	    	excelTitle:"考生基本信息表",
	    	region:"center",
	    	action:"rollCard_getClassListPage.do",
	    	fields :["XX_BJXX_ID","BJMC","XSNUM","XSPICNUM"],
	    	border:false
		});
		return new Ext.Panel({
	    		id:"CheatPanel",
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
					items: [this.grid]
				}]
	    });
	},
    createPrintPanel:function(){
		var dataPanel1=new Ext.Panel({
    		region:'center',
    		border:true,
   			id:"dataPanelP1",
   			html:"",
   			bodyStyle:"border:2px"
   		});
    	return new Ext.Panel({
    		border:true,
    		title:"打印预览",
    		region:"north",
    		layout:"border",
    		tbar:[ 
    		      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhuiMain,scope:this}
    		      ,"->",{xtype:"button",text:"打印",iconCls:"p-icons-print",handler:this.printCheatStudent,scope:this}
				],
			items:[dataPanel1]			
    	});
    },
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"fit",items:[this.tabPanel]});
    },
    initData:function(){
    	this.grid.$load();
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
    	var bjids = "";
    	for(var i = 0; i < selectedStudent.length; i++) {
    		if(i != selectedStudent.length - 1) {
	    		bjids += selectedStudent[i].get("XX_BJXX_ID") + ",";
    		}else {
	    		bjids += selectedStudent[i].get("XX_BJXX_ID");
    		}
    	}
    	var node = this.orgTree.getSelectionModel().getSelectedNode();
    	if (node==null) {
    		Ext.MessageBox.alert("消息","请选择对应参考单位！");
    		return;
    	}
    	/*var str = node.id;
		if(str.indexOf("_")<=0){
    		return;
    	}
	    var nj = str.split("_")[1];*/
    	var organ = node.id; 
    	this.tabPanel.setActiveTab(this.printPanel);
  		var height=Ext.getCmp('dataPanelP1').getHeight()-4;
  		var iframe="<fieldset style='height:"+height+"; width:100%; border:0px; padding:0;background:#FFFFFF;'>"+
						"<iframe id='frmReportPi' name='frmReportPi' width='100%' height='"+height+
						"' src='rollCard_selectPicInfo.do?organ="+organ+"&bjids="+bjids+
						"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanelP1').innerHTML=iframe;
    },
    fanhuiMain:function(){
    	this.tabPanel.setActiveTab(this.panel);
    	var node = this.orgTree.getSelectionModel().getSelectedNode();
    	/*var str = node.id;
		if(str.indexOf("_")<=0){
    		return;
    	}
	    var nj = str.split("_")[1];*/
    	var organ = node.id;//node.parentNode.id;,"nj":nj 
    	this.grid.$load({"organ":organ});
    },
    printCheatStudent:function(){
    	frmReportPi.print();
    }    
});
