var responseArray;
var isUpdate = "";
Ext.extend(system.application.baseClass, {
	/** 初始化 * */
	init : function() {
		this.initDate();
		this.initComponent();
		this.initListener();
		this.initFace();
		this.initQueryDate();
	},
	/** 初始化页面、内存等基本数据 * */
	initDate : function() {
		
	},
	/** 对组件设置监听 * */
	initListener : function() {
		this.mainPanel = this.createMainPanel();
		this.scorePanel = this.createScorePanel();
		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.mainPanel,this.scorePanel]   
        });
	},
	initComponent : function() {
		
	},
	/** 初始化界面 * */
	initFace : function() {
		this.addPanel(this.tabPanel);
	},
	initQueryDate : function() {
		this.grid.$load();
	},
	
	createMainPanel:function(){
		var xnxq	= new Ext.ux.form.XnxqField({width:180,id:"xnxq_find",readOnly:true,callback:function(){
			var id=Ext.getCmp("xnxq_find").getCode();// 取得ComboBox0的选择值
			var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
				}
		});
		var cx = new Ext.Button({x : 17,y : -10,cls : "base_btn",text : "查询",handler : this.selectRound,scope : this});
		var cz = new Ext.Button({x : 87,y : -10,cls : "base_btn",text : "重置",handler : function() {this.search.getForm().reset();},scope : this});

		this.search = new Ext.form.FormPanel({
					region : "north",
					height : 90,
					items : [{
							layout : 'form',
							xtype : 'fieldset',
							style : 'margin:10 10',
							title : '查询条件',
							items : [{
									xtype : "panel",
									layout : "table",
									layoutConfig : {
										columns : 7
									},
									baseCls : "table",
									items : [
											{html : "年度：",baseCls : "label_right",width : 120}, 
											{items : [xnxq],baseCls : "component",width : 210}, 
											{layout : "absolute",items : [cx, cz],baseCls : "component_btn",width : 160}
										]
								}]
							}]
		});
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : false});
		var cm = [sm,
		          	{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
					{header: "季度",   align:"center", sortable:true, dataIndex:"XQ"},
					{header: "考试名称",   align:"center", sortable:true, dataIndex:"KSMC"},
		          	{header: "组考单位",   align:"center", sortable:true, dataIndex:"ZKDW"},
					{header: "参考单位",   align:"center", sortable:true, dataIndex:"CKDW"},
					{header: "考生数量",   align:"center", sortable:true, dataIndex:"KSSL"},
					{header: "合格人数",   align:"center", sortable:true, dataIndex:"HGRS"},
					{header: "未合格人数",   align:"center", sortable:true, dataIndex:"WHGRS"},
					{header: "已考",   align:"center", sortable:true, dataIndex:"YK"},
					{header: "缺考",   align:"center", sortable:true, dataIndex:"QK"}
				
		];				
		this.grid = new Ext.ux.GridPanel({
					cm : cm,
					sm : sm,
					title : "考试成绩-成绩录入",
					tbar : [ 
					        "->", {xtype : "button",text : "查看考生成绩",iconCls : "p-icons-upload",handler : this.selectscore,scope : this}			
						],
					page : true,
					rowNumber : true,
					region : "center",
					excel : true,
					action : "resultsList_getListPage.do",
					excelTitle : "成绩导入模板",
					fields : ["LCID","BMLCID","XN","XQ","KSMC","ZKDW","CKDW","KSSL","HGRS","WHGRS","YK","QK"],
					border : false
		});
		return new Ext.Panel({layout:"border",region:"center",items:[this.search,this.grid]})
	},
	
	createScorePanel:function(){
		var sfzjh = new Ext.form.TextField({fieldLabel:"",id:"sfzjh",name:"sfzjh",maxLength:50,width:150});
		var name = new Ext.form.TextField({fieldLabel:"",id:"name",name:"name",maxLength:50,width:150});
		var sfhg = new Ext.form.ComboBox({
			id:'sfhg',
			mode: 'local', 
			triggerAction: 'all',   
			editable:false,
			width:160,
			store: new Ext.data.ArrayStore({
				id: 0,
				fields: ['value','text'],
			    data: [['1', '全部'], ['2', '是'],['3', '否']]
			}),
			valueField: 'value',
			displayField: 'text'
		});
		var wj = new Ext.form.ComboBox({
			id:'wj',
			mode: 'local', 
			triggerAction: 'all',   
			editable:false,
			width:160,
			store: new Ext.data.ArrayStore({
				id: 0,
				fields: ['value','text'],
			    data: [['1', '全部'], ['2', '是'],['3', '否']]
			}),
			valueField: 'value',
			displayField: 'text'
		});
		var cx1 = new Ext.Button({x : 17,y : -10,cls : "base_btn",text : "查询",handler : this.selectstudent,scope : this});
		var cz1 = new Ext.Button({x : 87,y : -10,cls : "base_btn",text : "重置",handler : function() {this.search1.getForm().reset();},scope : this});
		
		this.search1 = new Ext.form.FormPanel({
			region : "north",
			height : 140,
			items : [{
					layout : 'form',
					xtype : 'fieldset',
					style : 'margin:10 10',
					title : '查询条件',
					items : [{
							xtype : "panel",
							layout : "table",
							layoutConfig : {
								columns : 4
							},
							baseCls : "table",
							items : [
									{html : "姓名：",baseCls : "label_right",width : 100}, 
									{items : [name],baseCls : "component",width : 180}, 
									{html : "身份证：",baseCls : "label_right",width : 100}, 
									{items : [sfzjh],baseCls : "component",width : 180},
									{html : "是否合格：",baseCls : "label_right",width : 100}, 
									{items : [sfhg],baseCls : "component",width : 180}, 
									{html : "是否违纪：",baseCls : "label_right",width : 100}, 
									{items : [wj],baseCls : "component",width : 180},
									{layout : "absolute",items : [cx1, cz1],baseCls : "component_btn",width : 160}
								]
						}]
					}]
		});
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : false});
		var cm1 = [sm,
					{header: "组考单位",   align:"center", sortable:true, dataIndex:"zkdw",readOnly:true},
					{header: "参考单位",   align:"center", sortable:true, dataIndex:"ckdw",readOnly:true},
					{header: "姓名",   align:"center", sortable:true, dataIndex:"xm",readOnly:true},
					{header: "性别",   align:"center", sortable:true, dataIndex:"xb",readOnly:true},
					{header: "身份证号",   align:"center", sortable:true, dataIndex:"sfzjh",readOnly:true},
					{header: "考试时间",   align:"center", sortable:true, dataIndex:"kssj",readOnly:true},
					{header: "考点名称",   align:"center", sortable:true, dataIndex:"kdmc",readOnly:true},
					{header: "考场名称",   align:"center", sortable:true, dataIndex:"kcmc",readOnly:true},
					{header: "座位号",   align:"center", sortable:true, dataIndex:"zwh",readOnly:true},
					{header: "分数",   align:"center", sortable:true, dataIndex:"score",readOnly:true},
					{header: "是否合格",   align:"center", sortable:true, dataIndex:"sfhg",readOnly:true},
					{header: "违纪情况",   align:"center", sortable:true, dataIndex:"wj",readOnly:true},
					{header: "备注",   align:"center", sortable:true, dataIndex:"bz",readOnly:true}
		];		
//		"->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this
		this.grid1 = new Ext.ux.GridPanel({
					cm : cm1,
					sm : sm,
					title : "考生成绩查询",
					tbar : [
					        "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.returnMainPanel,scope:this}
					        ],
					page : true,
					rowNumber : true,
					region : "center",
					excel : true,
					action : "resultsList_getStudentScoreListPage.do",
					excelTitle : "成绩导入模板",
					fields : ["zkdw","ckdw","xm","xb","sfzjh","kssj","kdmc","kcmc","zwh","score","sfhg","wj","bz"],
					border : false
		});
		return new Ext.Panel({layout:"border",region:"center",items:[this.search1,this.grid1]});
	},
	
	selectRound :function(){
		var xnxq=Ext.getCmp('xnxq_find').getValue();
    	this.grid.$load({"xnxq":xnxq});
	},
	
	selectscore:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条轮次进行查看！");
    		return;
    	}
    	var bmlcid = selectedBuildings[0].get("BMLCID");
    	var zkdw = selectedBuildings[0].get("ZKDW");
    	var lcid = selectedBuildings[0].get("LCID");
    	Ext.Ajax.request({		
    		url:"resultsList_checkStatue.do",
    		params:{
    			'bmlcid':bmlcid,
    			'zkdw':zkdw,
    			'lcid':lcid
    		},
    		scope:this,
    		success: function (r, options) {			
    			var result =Ext.decode(r.responseText);	
    			if(result.data.FBCJ=='0'){
    				this.tabPanel.setActiveTab(this.scorePanel);
    		    	this.grid1.$load({"bmlcid":bmlcid,"zkdw":zkdw,"lcid":lcid});
    			}
    			else{
    				Ext.Msg.alert("错误","成绩还在审核中，现在不能查看成绩！");
    			}
    		},
    		failure: function (response, options) {
    			Ext.Msg.alert("错误","查询失败！");
    		}
    });
    	
	},
	
	selectstudent:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
		var bmlcid = selectedBuildings[0].get("BMLCID");
		var zkdw = selectedBuildings[0].get("ZKDW");
		var lcid = selectedBuildings[0].get("LCID");
    	var name = Ext.getCmp('name').getValue();
    	var sfzjh = Ext.getCmp('sfzjh').getValue();
    	var sfhg = Ext.getCmp('sfhg').getValue();
    	var wj = Ext.getCmp('wj').getValue();
    	this.grid1.$load({"lcid":lcid,"zkdw":zkdw,"bmlcid":bmlcid,"name":name,"sfzjh":sfzjh,"sfhg":sfhg,"wj":wj});
	},
	
	//返回到轮次页面
	returnMainPanel:function(){
    	this.tabPanel.setActiveTab(this.mainPanel);
    	this.initQueryDate();
    	this.grid1.getStore().removeAll();
    	this.selectRound();
    },
});

var syncRequest = function(url) {	
	var conn = Ext.lib.Ajax.getConnectionObject().conn;     
	try {     
		conn.open("POST", url, false);
		conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		conn.send(null);     
	}catch (e) {     
		Ext.Msg.alert('info','error');     
		return false;     
	}     
	responseArray = Ext.decode(conn.responseText);     
}