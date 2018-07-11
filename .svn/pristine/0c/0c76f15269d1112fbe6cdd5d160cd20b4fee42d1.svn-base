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
		
	},
	initComponent : function() {
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : false});
		var cm = [sm, 
					{header : "组考单位",align : "center",sortable : true,dataIndex : "ZKDW"},
					{header : "参考单位",align : "center",sortable : true,dataIndex : "CKDW"},
					{header : "学员身份证号码",align : "center",sortable : true,dataIndex : "SFZJH"},
					{header : "考试时间",align : "center",sortable : true,dataIndex : "KSSJ"},
					{header : "成绩",align : "center",sortable : true,dataIndex : "CJ"}
				];
				 
		this.grid = new Ext.ux.GridPanel({
					cm : cm,
					sm : sm,
					title : "考试成绩", 
					page : true,
					rowNumber : true,
					region : "center",
					excel : true,
					excelType:'com.jiajie.service.scoreModify.impl.StuScoreServiceImpl',
					action : "stuScore_getListPage.do",
					excelTitle : "考试成绩",
					fields : ["KSID", "ZKDW", "CKDW", "SFZJH","KSSJ","CJ"],
					border : false
				});
		 
		this.panel=new Ext.Panel({
			region:"north",
			width:"auto",
			layout:"border",			
			border:false,
			id:"panelModify1",
			items:[this.grid]
		});
		this.myTabs = new Ext.TabPanel({
		    id:'myTabsM',
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
		    items:[{layout: 'fit', index: 0, border: false, items: [this.panel],title:"修正成绩"}]
		});
	},  
	/** 初始化界面 * */
	initFace : function() {
		this.panel_top=new Ext.Panel({
			layout:"fit",
    		id:"panel_topM",
    		region:"north",
    		border:false,
    		items:[this.myTabs]
    	});
    	this.addPanel({layout:"fit",items:[this.panel_top]});
	},
	initQueryDate : function() {
		this.grid.$load();
	}
});

