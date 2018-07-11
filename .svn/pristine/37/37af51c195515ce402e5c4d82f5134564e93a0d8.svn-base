var kcapzgz = "kcapzgz1";
var wskc = 0;
var zwpl = "zwpl1";
var pksx = "pksx1";
var lcid;
var schoolid;
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
    	this.radiogroup1.on('change', function (rdgroup, checked) {
    		kcapzgz = checked.getRawValue();
    	});
    	/*this.radiogroup3.on('change', function (rdgroup, checked) {
    		zwpl = checked.getRawValue();
    	});*/
    	this.radiogroup4.on('change', function (rdgroup, checked) {
    		pksx = checked.getRawValue();
    	});
    },
    initComponent :function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
			sm,
			{header: "年度",   align:"center", sortable:true, dataIndex:"XNMC"},
			{header: "季度",   align:"center", sortable:true, dataIndex:"XQMC"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME"},
			{header: "考试类型",   align:"center", sortable:true, dataIndex:"EXAMTYPEM"},
			{header: "起始日期",   align:"center", sortable:true, dataIndex:"STARTDATE"},
			{header: "结束日期",   align:"center", sortable:true, dataIndex:"ENDDATE"},
			{header: "考点数量",   align:"center", sortable:true, dataIndex:"COUNTKD"},
			{header: "考生人数",   align:"center", sortable:true, dataIndex:"COUNTKS"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"考场安排",
			tbar:[ 
			      "->",{xtype:"button",text:"座位调整",iconCls:"p-icons-update",handler:this.showkaoChang,scope:this},
			      "->",{xtype:"button",text:"考场安排",iconCls:"p-icons-update",handler:this.kaoChangAnPai,scope:this},
			      "->",{xtype:"button",text:"设置考场",iconCls:"p-icons-update",handler:this.szExamRoom,scope:this}			  
			     ],
		    page:true,
		    rowNumber:true,
		    region:"center",
		    action:"examroomarrange_getListPage.do",
		    fields :["LCID","EXAMNAME","STARTWEEK","STARTDAY","STARTDATE","ENDWEEK","ENDDAY","ENDDATE","EXAMTYPEM","XNMC","XQMC","COUNTKD","COUNTKS"],
		    border:false
		});
		//搜索区域
		var xn_find = new Ext.ux.form.XnxqField({ width:180,id:"erxn_find",readOnly:true});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamRound,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.search = new Ext.form.FormPanel({
			region: "north",
			height:90,
			items:[{  
					layout:'form',  
					xtype:'fieldset',  
					style:'margin:10',
					title:'查询条件',  
					items: [{
                    		xtype:"panel",
                    		layout:"table", 
                    		layoutConfig: { 
                    			columns: 3
							}, 
							baseCls:"table",
							items:[
							       {html:"年度：",baseCls:"label_right",width:140},
							       {items:[xn_find],baseCls:"component",width:190},
							       {layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:160}
							      ] 
                    	}]  
		       	}]  
	    });
	    this.panel=new Ext.Panel({
	    			id:"panelE",
	    			region:"north",
	    			width:"auto",
	    			layout:"border",
	    			border:false,
	    			items:[this.search,this.grid]
	    });
	    this.mainPanel = this.createMainExamRoomPanel();   		
   		this.arrangePanel = this.createArrangePanel();   		
	},
	createSZExamRoom :function(){
		/*var store = new Ext.data.JsonStore({
			autoLoad:true,
			url:'examroomarrange_getListPage.do',
			fields:["LCID","EXAMNAME","STARTWEEK","STARTDAY","STARTDATE","ENDWEEK","ENDDAY","ENDDATE","EXAMTYPEM","XNMC","XQMC"]
		});
       	store.load();*/
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
			sm,
			{header: "考点名称",   align:"center", sortable:true, dataIndex:"POINTNAME"},
			{header: "教学楼",   align:"center", sortable:true, dataIndex:"BUILDNAME"},
			{header: "教室名称",   align:"center", sortable:true, dataIndex:"ROOMNAME"},
			{header: "教室容量",   align:"center", sortable:true, dataIndex:"YXZWS"},
			{header: "考场编号",   align:"center", sortable:true, dataIndex:"KCBH"},
			{header: "考场容量",   align:"center", sortable:true, dataIndex:"SEATS"},
			{header: "行数",   align:"center", sortable:true, dataIndex:"XS"},
			{header: "列数",   align:"center", sortable:true, dataIndex:"YS"}
		];
		this.examRoomGrid = new Ext.ux.GridPanel({
			id:"examRoomGrid",
			cm:cm,
			sm:sm,
			tbar:[ 
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}
			      ,"->",{xtype:"button",text:"设置",iconCls:"p-icons-update",handler:this.updateExamRoom,scope:this}			  
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			action:"examroomarrangeR_getListPage.do?lcId="+lcid,
			fields :["KCID","POINTNAME","BUILDNAME","ROOMNAME","ROOMID","YXZWS","JSLX","KCBH","SEATS","XS","YS"],
			border:false
		});
		//搜索区域
		//教学楼
		var jxl_find1  = new Ext.ux.Combox({width:170,name:"jxl_find1",store:store});
		var store 	  = new Ext.data.JsonStore({url:'',fields:['CODEID', 'CODENAME']});
		//考点名称  
		var kdmc_find1 = new Ext.ux.Combox({name:"kdmc_find1",dropAction:"kaoDianMc",allowBlank:false,width:180,params:lcid,
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_jiaoXueLou.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
					jxl_find1.clearValue(); 
					jxl_find1.store=newStore;  
					newStore.reload();
					jxl_find1.bindStore(newStore);
				},
				scope:this
			}
		});
		//教室类型
		var jslx_find = new Ext.ux.DictCombox({name:"jslx_find",dictCode:"ZD_JSLX", width:170});
		var tyrl_h_find = new Ext.form.TextField({x:100,y:-10,fieldLabel:"",id:"tyrl_h_find",name:"jsrl_h_find",maxLength:2,width:30,regex:/^\d+$/,regexText:"只能是数字！"});
		var tyrl_l_find = new Ext.form.TextField({x:145,y:-10,fieldLabel:"",id:"tyrl_l_find",name:"jsrl_l_find",maxLength:2,width:30,regex:/^\d+$/,regexText:"只能是数字！"});
		var tyrl_hl_find = new Ext.form.Label({x:135,y:-10,text:"*"});
		var tyrl_hlts_find = new Ext.form.Label({x:2,y:-10,text:"统一考场容量：",width:120});
		var msg_sz = new Ext.form.Label({x:2,y:-10,text:"*设置考场容量会根据（考点名称，教学楼，教室类型）查找考场，考点名称必选！",style:"color:red",width:120});
		
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamRoom1,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.examRoomSearch.getForm().reset()},scope:this});
		var sz = new Ext.Button({x:20,y:-10,cls:"base_btn",text:"设置",handler:this.saveExamRoom,scope:this});
		
		this.examRoomSearch = new Ext.form.FormPanel({
			region: "north",
			height:120,
			items:[{  
					layout:'form',  
					xtype:'fieldset',  
					style:'margin:10',
					title:'查询条件',  
					items: [{
                    		xtype:"panel",
                    		layout:"table", 
                    		layoutConfig: { 
                    			columns: 7
							}, 
							baseCls:"table",  
							items:[
							       {html:"考点名称：",baseCls:"label_right",width:70},
							       {items:[kdmc_find1],baseCls:"component",width:190},
							       {html:"教学楼：",baseCls:"label_right",width:85},
							       {items:[jxl_find1],baseCls:"component",width:180},
							       {html:"教室类型：",baseCls:"label_right",width:70},
							       {items:[jslx_find],baseCls:"component",width:180},
							       {layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:160},
							       {html:"考场容量：",baseCls:"label_right",width:70},
							       {layout:"absolute",
							    	   items:[
							    	          tyrl_hlts_find,
							    	          tyrl_h_find,
							    	          tyrl_hl_find,
							    	          tyrl_l_find
							    	         ],
							    	   baseCls:"component",width:190},
							    	   {layout:"absolute",items:[sz],baseCls:"component_btn",width:85},
							    	   {items:[msg_sz],baseCls:" component",colspan:4,width:590}							
							   ] 
                    	}]  
		       	}]  
	    });
		this.examRoomPanel=new Ext.Panel({
	    		id:"examRoomPanel",
	    		title:"设置考场",
	    		region:"north",
	    		height:500,
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.examRoomSearch,this.examRoomGrid]
		});	    	
	},
	createMainExamRoomPanel:function(){
		var arrange = new Ext.Button({text:"安排",iconCls:"p-icons-update",handler:this.examRoomArrange,scope:this});
		var fanhui = new Ext.Button({text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui1,scope:this});
		this.radiogroup1 = new Ext.form.RadioGroup({
            	width: 500,
            	items: [{
            		name: 'kcapzgz',
            		inputValue: 'kcapzgz1',
            		boxLabel: '先安排容量大的考场',
            		checked: true
                 }, 
                 {
                	 name: 'kcapzgz',
                     inputValue: 'kcapzgz2',
                     boxLabel: '先安排容量小的考场'
                 }]
		});
	    this.radiogroup2 = new Ext.form.NumberField({
	    		width: 50,
	    		id:  'wskc',
		    	fieldLabel : "尾数考场最多人数设定",
		    	labelStyle : "width:230;text-align:right;",
		    	maxValue:9,
		    	maxText:"输入值太大",
		    	allowDecimals:false,
		    	allowNegative:false,
		    	nanText:'请输入有效整数',
		    	allowNegative : false
     	});
        /*this.radiogroup3 = new Ext.form.RadioGroup({
        		width: 500,
        		items: [{
        			name: 'zwpl',
        			inputValue: 'zwpl1',
        			boxLabel: '梅花桩',
        			checked: true
                }, 
                {
                	name: 'zwpl',
                	inputValue: 'zwpl2',
                	boxLabel: '矩阵型'
                }]
        });*/
        this.radiogroup4 = new Ext.form.RadioGroup({
        		width: 750,
        		items: [{
        			name: 'pksx',
        			inputValue: 'pksx1',
        			boxLabel: '随机',
        			checked: true
        		}, 
        		/*{
        			name: 'pksx',
        			inputValue: 'pksx2',
        			boxLabel: '按学号'
        		},*/ 
        		{
        			name: 'pksx',
        			inputValue: 'pksx3',
        			boxLabel: '按科目'
        		}]
        });
		return new Ext.Panel({
				id:"MainExamRoomPanel",
	    		title:"考场安排",
	    		region:"north",
	    		tbar:[ 
	    		      "->",fanhui,
	    		      "->",arrange
			  	],
	    		height:500,
	    		width:"auto",
	    		layout:"table", 
				layoutConfig: { 
					columns: 1
				}, 
	    		border:false,
	    		items:[
	    			{xtype:'fieldset',title:'考场安排总规则',autoHeight:true,items: [this.radiogroup1],style:"margin:30"},
	    			{xtype:'fieldset',title:'尾数考场安排规则',autoHeight:true,items: [this.radiogroup2],style:"margin:30;"},
	    			//{xtype:'fieldset',title:'座位排列',autoHeight:true,items: [this.radiogroup3],style:"margin:30"},
	    			{xtype:'fieldset',title:'排考顺序',autoHeight:true,items: [this.radiogroup4],style:"margin:30"}]
	    });
	},	
	createArrangePanel:function(){
	    this.show_panel=new Ext.Panel({
    		id:"show_panel",
		    region:"center",
		    html:"",
		    width:"auto",
		    height:600,
		    border:false
    	});
		
		this.context_panel=new Ext.Panel({
    		id:"context_panel",
		    region:"center",
		    tbar:[ 
			  "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui2,scope:this}
			],
		    items:[this.show_panel],
		    width:"auto",
		    height:300,
		    border:true,
		    autoScroll:true
    	});
		var kskmid 	= new Ext.form.TextField({hidden:true,name:"kmid",id:"kmid"});
		var kskm	= new Ext.ux.Combox({autoLoad:true,name:"kskm",dropAction:"getKskmBykslc",params:lcid,width:170,allowBlank:false,blankText:"考试批次为必选项！",listeners:{
			"select":function(){
				Ext.getCmp("kmid").setValue(this.getValue());
			}
		}});
		//教学楼
    	var store = new Ext.data.JsonStore({url : 'dropListAction_jiaoXueLou.do',fields : ['CODEID', 'CODENAME']});
    	store.reload();
		var jxl_find2 = new Ext.ux.Combox({width:170,id:"jxl_find2",store:store});
		//考点名称  
		var kdmc_find2 = new Ext.ux.Combox({dropAction:"kaoDianMc",allowBlank:false, width:170,id:"kdmc_find2",params:lcid,
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_jiaoXueLou.do?params='+id,
										fields:["CODEID","CODENAME"]
					});
					Ext.getCmp("jxl_find2").clearValue(); 
					Ext.getCmp("jxl_find2").store=newStore;  
					newStore.reload();
					Ext.getCmp("jxl_find2").bindStore(newStore);
				},
				scope:this
			}
		});		
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamRoom2,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.examRoomSearch.getForm().reset()},scope:this});
				
		this.examRoomSearch = new Ext.form.FormPanel({
			region: "north",
			height:90,
			items:[{  
				layout:'form',  
				xtype:'fieldset',  
				style:'margin:10',
				title:'查询条件',  
				items: [{
                    	xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 7
						}, 
						baseCls:"table",  
						items:[
						    {html:"考试批次：",baseCls:"label_right",width:70},
						    {items:[kskm,kskmid],baseCls:"component",width:180},   
						    {html:"考点：",baseCls:"label_right",width:50},
							{items:[kdmc_find2],baseCls:"component",width:180},
							{html:"楼房：",baseCls:"label_right",width:50},
							{items:[jxl_find2],baseCls:" component",width:180},
							{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:160}
						] 
                    }]  
		       }]  
	    	});
		return new Ext.Panel({
    		id:"examRoomPanel",
    		title:"考场安排",
    		region:"north",
    		height:500,
    		width:"auto",
    		layout:"border",
    		border:false,
    		items:[this.examRoomSearch,this.context_panel]
    	});
	},
	/** 初始化界面 **/
    initFace:function(){
    	this.panel_top=new Ext.Panel({
    			layout:"fit",
	    		id:"panel_topE",
	    		region:"north",
	    		border:false,
	    		items:[this.panel]
    	});
    	this.addPanel({layout:"fit",items:[this.panel_top]});
    	/*if(mBspInfo.getUserType() == "2") {
    		Ext.getCmp("examSchool").disabled = true;
    	}else if(mBspInfo.getUserType() == "1"){
    		Ext.getCmp("examSchool").disabled = false;
    	}*/
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
	szExamRoom:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	lcid=selectedBuildings[0].get("LCID");
    	this.createSZExamRoom();
    	var panel=Ext.getCmp("panel_topE");
  		panel.remove(Ext.getCmp("panelE"));
  		panel.add(this.examRoomPanel);
  		panel.doLayout(false);
    },
    kaoChangAnPai:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
	    }
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	lcid=selectedBuildings[0].get("LCID");
    	this.mainPanel = this.createMainExamRoomPanel();   		
    	var panel=Ext.getCmp("panel_topE");
  		panel.remove(Ext.getCmp("panelE"));  		
  		panel.add(this.mainPanel);
  		panel.doLayout(false);
    	//window.location.href = Ext.get("ServerPath").dom.value+"/jsp/main.jsp?module=00040401&lcid="+ids;
    },
    showkaoChang:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
	    }
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	lcid=selectedBuildings[0].get("LCID");
    	var panel=Ext.getCmp("panel_topE");
    	this.arrangePanel = this.createArrangePanel();
  		panel.remove(Ext.getCmp("panelE"));
  		panel.add(this.arrangePanel);
  		panel.doLayout(false);
    	//window.location.href = Ext.get("ServerPath").dom.value+"/jsp/main.jsp?module=00040402&lcid="+ids;
    },
    selectExamRound:function(){
    	var xn=Ext.getCmp('erxn_find').getCode();
    	this.grid.$load({"xnxqId":xn});
    },
    selectExamRoom1:function(){
    	var kdmc=this.examRoomSearch.getForm().findField('kdmc_find1').getValue();
    	schoolid=kdmc;
    	var jxl=this.examRoomSearch.getForm().findField('jxl_find1').getValue();
    	var jslx=this.examRoomSearch.getForm().findField('jslx_find').getValue();
    	if(kdmc==""){
    		Ext.MessageBox.alert("提示","请选择考点名称！");
    		return;
    	}
    	this.examRoomGrid.$load({"lcId":lcid,"schoolId":kdmc,"lfId":jxl,"jslx":jslx});
    },
    saveExamRoom:function(){
    	var kdmc=this.examRoomSearch.getForm().findField('kdmc_find1').getValue();
    	var jxl=this.examRoomSearch.getForm().findField('jxl_find1').getValue();
    	var jslx=this.examRoomSearch.getForm().findField('jslx_find').getValue();
    	if(kdmc==""){
    		Ext.MessageBox.alert("提示","请选择考点名称！");
    		return;
    	};
    	var hang=Ext.getCmp('tyrl_h_find').getValue();
    	var lie=Ext.getCmp('tyrl_l_find').getValue();
    	if(hang=="" || lie==""){
    		Ext.MessageBox.alert("提示","请填写考试容量行列数！");
    		return;
    	}
    	if(isNaN(hang) || isNaN(lie)) { 
    		Ext.MessageBox.alert("提示","考试容量，行列必须为数字！");
    		return;
    	}
    	var thiz = this;
    	Ext.Ajax.request({
    		url: 'examroomarrange_saveExamRoom.do',
    		params: {"lcId":lcid,"schoolId": kdmc,"lfId":jxl,"jslx":jslx,"hang":hang,"lie":lie,"random":Math.random()},
    		method: 'POST',
    		success: function (response, options) {
    			if(response.responseText=="success"){
    				Ext.MessageBox.alert('成功', '考场设置成功！');                    	
    				thiz.examRoomGrid.$load({"lcId":lcid,"schoolId":kdmc,"lfId":jxl,"jslx":jslx});                		
                }else if(response.responseText=="error"){
                	Ext.MessageBox.alert('失败', '考场设置失败！教室容量不够！');
                }
    		},
    		failure: function (response, options) {
    			Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
    		}
    	});           
    },
    fanhui:function(){
    	this.initComponent();
    	var panel=Ext.getCmp("panel_topE");
  		panel.remove(Ext.getCmp("examRoomPanel"));
  		panel.add(this.panel);
  		panel.doLayout(false);
  		this.grid.$load();  		
    },
    fanhui1:function(){
    	this.initComponent();
    	var panel=Ext.getCmp("panel_topE");
  		panel.remove(this.mainPanel);
  		panel.add(this.panel);
  		panel.doLayout(false);
  		this.grid.$load();  		
	},
	fanhui2:function(){
		this.initComponent();
    	var panel=Ext.getCmp("panel_topE");
  		panel.remove(this.arrangePanel);
  		panel.add(this.panel);
  		panel.doLayout(false);
  		this.grid.$load();  		
	},
    updateExamRoom:function(){
    	var selected =  this.examRoomGrid.getSelectionModel().getSelected();
	    if(!selected){
	    	Ext.MessageBox.alert("消息","请选择一条记录！");
	    	return;
	    }
	    var selectedBuildings = this.examRoomGrid.getSelectionModel().getSelections();
	    var ids =selectedBuildings[0].get("KCID");
	    var roomid =selectedBuildings[0].get("ROOMID");
	    var yxzws =selectedBuildings[0].get("YXZWS");
	    var xs =selectedBuildings[0].get("XS");
	    var ys =selectedBuildings[0].get("YS");
	   	
	    this.kcid=ids;
	    this.editForm   = this.createBuildingEditForm(lcid,schoolid,roomid,ids,yxzws,xs,ys);
   		this.eidtWindow = this.createBuildingWindow();
   		this.eidtWindow.add(this.editForm);
   		this.eidtWindow.show();	   
    },
    createBuildingWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.updateExamRoomSubmit,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide()},scope:this});
		return new Ext.ux.Window({
			width:600,
			title:"考场设置",
			tbar:{
				cls:"ext_tabr",
				items:[ 
				       "->",cancel
				       ,"->",save
				      ]
			},
			buttonAlign:"center"});			
    },
    createBuildingEditForm:function(lcid,schoolid,roomid,kcid,yxzws,hang,lie){
    	var lcid = new Ext.form.TextField({id:"lcid",hidden:true,name:"lcid",value:lcid});
    	var schoolid = new Ext.form.TextField({id:"schoolid",hidden:true,name:"schoolid",value:schoolid});
    	var roomid = new Ext.form.TextField({id:"roomid",hidden:true,name:"roomid",value:roomid});
    	var kcid = new Ext.form.TextField({id:"kcid",hidden:true,name:"kcid",value:kcid});
    	var yxzws = new Ext.form.TextField({id:"yxzws",hidden:true,name:"yxzws",value:yxzws});
		var hang = new Ext.form.TextField({name:"hang",id:"hang",value:hang,width:140,maxLength:3,allowBlank:false,blankText:"行数不能为空！",regex:/^\d+$/,regexText:"行数只能是数字！"});
		var lie = new Ext.form.TextField({name:"lie",id:"lie",value:lie,width:140,maxLength:3,allowBlank:false,blankText:"列数不能为空！",regex:/^\d+$/,regexText:"列数只能是数字！"});

		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[
			       {html:"座位行数：",baseCls:"label_right",width:100},
			       {html:"<font class='required'>*</font>",items:[hang],baseCls:"component",width:160},
			       {html:"座位列数：",baseCls:"label_right",width:100},
			       {html:"<font class='required'>*</font>",items:[lie],baseCls:"component",width:160}			
			      ]		
		});
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
				{layout:'form', xtype:"fieldset",title:"考场座位数调整",items:[panel]},
				{items:[kcid,yxzws,lcid,schoolid,roomid]}
			]
		});				
    },
    updateExamRoomSubmit:function(){
    	var hang=Ext.getCmp("hang").getValue();
    	var lie=Ext.getCmp("lie").getValue();
    	var yxzws=Ext.getCmp("yxzws").getValue();
    	if(yxzws<hang*lie){
    		Ext.MessageBox.alert("提示","教室容量小于设置的座位数！\n请重新设置！");	
    		return false;
    	}
    	this.editForm.$submit({
    		action:"examroomarrange_saveOrUpdateExamRoom.do",
    		handler:function(form,result,thiz){
    			thiz.eidtWindow.hide();
    			thiz.examRoomGrid.$load();
    		},
    		scope:this
    	});
    },
    examRoomArrange:function(){
		var wskcNum = Ext.getCmp('wskc').value;
		if (parseInt(wskcNum)>=10) {
			Ext.MessageBox.alert("消息","尾数考场最多人数设定太大！");
			return;
		}
		var thiz=this;
		Ext.Ajax.request({
			url: 'examroomarrange_arrangeStu.do',
		   	success: function(response,options){
		   		var msginfo=response.responseText;
		   		if(msginfo!="success")
		   			Ext.Msg.alert("错误",msginfo);
		   		else{
		   			Ext.Msg.alert("提示","保存成功！");
		   			//window.location.href = Ext.get("ServerPath").dom.value+"/jsp/main.jsp?module=00040402&lcid="+getLocationPram("lcid");
		   			thiz.tabPanel.setActiveTab(thiz.arrangePanel);
		   		}
		   	},
		   	failure: function(resp,opts) {
		        Ext.Msg.alert('错误', "出错了！");
		    },
		   	params: {'kcapzgz':kcapzgz,
				wskc:Ext.getCmp('wskc').value,
				zwpl:zwpl,
				pksx:pksx,
				lcid:lcid
			}
    	});
	},	
	//查询教室
	selectExamRoom2:function() {
		var kmid = Ext.getCmp("kmid").getValue();
		var kdmc_id = Ext.getCmp("kdmc_find2").getValue();
		var jxl_id = Ext.getCmp("jxl_find2").getValue();
		if(kdmc_id == "") {
			Ext.MessageBox.alert("提示","请选择考点！");
    		return;
		}
		if(jxl_id == "") {
			Ext.MessageBox.alert("提示","请选择楼房！");
    		return;
		}
		var iframe="<fieldset style='height:100%; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReportR' name='frmReportR' width='100%' height='800' src='subExamRoomArrange_getExamRoomArrange.do?lcId="+
						lcid+"&kmid="+kmid+"&lfid="+jxl_id+"&kdid="+kdmc_id+"' frameborder='0' scrolling='auto'></iframe>"+
					"</fieldset>";
		Ext.getDom("show_panel").innerHTML=iframe;
		//安排	
	}	
});