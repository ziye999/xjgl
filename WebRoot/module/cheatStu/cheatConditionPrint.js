var organ = "";
var xnxq = "";
var kslc = "";
Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
		this.initComponent();
		this.initFace();
		this.initData();
		this.initListener();
    },    
	/** 对组件设置监听 **/
    initListener:function(){
    
    },   
   	initComponent :function(){
		this.panel = this.createMainPanel();
    	this.printPanel= this.createPrintPanel();
		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
            headerStyle: 'display:none',
       		border:false,
            items: [this.panel,this.printPanel]   
        }); 
	},
	createMainPanel:function(){
		//搜索区域
   		var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel",width:210,readOnly:true,type:"1"});
    	var organ_lable = "参考单位：";
    	var kslc	= new Ext.ux.Combox({width:200,name:"kslc_find",id:"kslc_find"});
		var xnxq	= new Ext.ux.form.XnxqField({width:210,id:"xnxq_findC",readOnly:true,callback:function(){
						var id=Ext.getCmp("xnxq_findC").getCode();//取得ComboBox0的选择值
						var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
						Ext.getCmp("kslc_find").clearValue();  
                    	Ext.getCmp("kslc_find").store=newStore;  
                        newStore.reload();
                        Ext.getCmp("kslc_find").bindStore(newStore);
				}});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectCheatInfo,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		this.search = new Ext.form.FormPanel({
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
							columns: 4
						}, 
						baseCls:"table",
						items:[							
							{html:"年度：",baseCls:"label_right",width:120},
							{items:[xnxq],baseCls:"component",width:210},
							{html:"考试名称：",baseCls:"label_right",width:120},
							{items:[kslc],baseCls:"component",width:210},
							{html:organ_lable,baseCls:"label_right",width:120},
							{items:[organ_sel],baseCls:"component",width:210},
							{html:"",baseCls:"label_right",width:120},
							{layout:"absolute", items:[cx,cz],baseCls:"component",width:210}
						] 
                    }]  
		       }]  
	    })
	    var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "参考单位",   align:"center", sortable:true, dataIndex:"XXMC"},
			//{header: "等级",   align:"center", sortable:true, dataIndex:"NJMC"},
			//{header: "科目",   align:"center", sortable:true, dataIndex:"BJMC"},
			{header: "身份证件号",   align:"center", sortable:true, dataIndex:"SFZJH"},
			{header: "考号",   align:"center", sortable:true, dataIndex:"KSCODE"},
			{header: "姓名",   align:"center", sortable:true, dataIndex:"XM"},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
			{header: "考试批次",   align:"center", sortable:true, dataIndex:"SUBJECTNAME"},
			{header: "违纪处理",   align:"center", sortable:true, dataIndex:"OPTTYPENAME"},
			{header: "扣除分数",   align:"center", sortable:true, dataIndex:"SCORE"},
			{header: "违纪情况",   align:"center", sortable:true, dataIndex:"WJQQ"},
			{header: "状态",   align:"center", sortable:true, dataIndex:"ZT"}
		];	
	    this.grid = new Ext.ux.GridPanel({
	    	cm:cm,
	    	sm:sm,
	    	title:"违纪情况",
	    	tbar:[ 
	    	      "->",{xtype:"button",text:"打印预览",iconCls:"p-icons-print",handler:this.showCheatStudent,scope:this}
	    	     ],
	    	page:true,
	    	rowNumber:true,
	    	excel:true,
	    	pdf:true,
	    	excelTitle:"违纪考生表",
	    	region:"center",
	    	action:"CheatStudent_getListPage.do",
	    	fields :["LCID","XXMC","KSCODE","XJH","XM","XB","SUBJECTNAME","OPTTYPENAME","SCORE","WJQQ","NJMC","BJMC","WJID","SFZJH","ZT"],
	    	border:false
		});
		return new Ext.Panel({
	    		title:"违纪情况",
	    		region:"north",
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.search,this.grid]
	    });
	},
    createPrintPanel:function(){
		var dataPanel=new Ext.Panel({
    		region:'center',
    		border:true,
   			id:"dataPanelC",
   			html:"",
   			bodyStyle:""
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
			items:[dataPanel]
			
    	});
    },
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"fit",items:[this.tabPanel]});
    },
    initData:function(){
    	//this.grid.$load();
    },
    selectCheatInfo:function(){
    	//查询违纪信息
    	xnxq = Ext.getCmp("xnxq_findC").getValue();
    	if(xnxq==""){
    		Ext.MessageBox.alert("消息","请选择年度！");
    		return;
    	}
    	kslc = Ext.getCmp("kslc_find").getValue();
    	if(kslc==""){
    		Ext.MessageBox.alert("消息","请选择考试名称！");
    		return;
    	}    	
    	organ = this.search.getForm().findField("organ_sel").getSchoolCode()==undefined?"":this.search.getForm().findField("organ_sel").getSchoolCode();    	
    	this.grid.$load({"schools":organ,"lcid":kslc,"flag":"1"});
    },
    showCheatStudent:function(){
    	if(this.grid.store.getCount()<1){
    		Ext.MessageBox.alert("消息","请先查询出数据！");
    		return;
    	}
    	//打印页面，显示违纪考生
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedStudent = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var wjid = "";
    	for(var i = 0; i < selectedStudent.length; i++) {
    		if(i != selectedStudent.length - 1) {
	    		wjid += selectedStudent[i].get("WJID") + ",";
    		}else {
	    		wjid += selectedStudent[i].get("WJID");
    		}
    	}
    	this.tabPanel.setActiveTab(this.printPanel);
  		var height=Ext.getCmp('dataPanelC').getHeight()-4;
  		var iframe="<fieldset style='height:"+height+"; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReportC' name='frmReportC' width='100%' height='"+height+
						"' src='CheatStudent_getCheatStudentByLcid.do?lcid="+kslc+"&flag=1&schools="+organ+"&wjid="+wjid+
						"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanelC').innerHTML=iframe;
    },
    fanhuiMain:function(){
    	this.tabPanel.setActiveTab(this.panel);
    	this.grid.$load({"schools":organ,"lcid":kslc,"flag":"1"});
    },
    printCheatStudent:function(){
    	frmReportC.print();
    }    
});
