var lcid = "";
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
    	
    },    
    initComponent:function(){
		var cm = [
		          {header: "姓名",   align:"center", sortable:true, dataIndex:"XM"},
		          {header: "身份证号",   align:"center", sortable:true, dataIndex:"SFZJH"},
		          {header: "考号",   align:"center", sortable:true, dataIndex:"EXAMCODE"},
		          {header: "考试批次",   align:"center", sortable:true, dataIndex:"SUBJECTNAME"},
		          {header: "原始成绩",   align:"center", sortable:true, dataIndex:"YSCJ"},
		          {header: "违纪扣分",   align:"center", sortable:true, dataIndex:"KCFS"},
		          {header: "最终成绩",   align:"center", sortable:true, dataIndex:"ZZCJ"}
				];
		this.CheatGrid = new Ext.ux.GridPanel({
			id:"cheatGridSG",
			cm:cm,
			title:"成绩修正-修正成绩审核",
			tbar:[ 
				  "->",{xtype:"button",text:"关闭",iconCls:"p-icons-refresh",handler:this.fanhui,scope:this}
				  ],
			page:true,
			rowNumber:true,
			region:"center",
			action:"gradeAmendmentXs_getListPage.do?lcid="+getLocationPram("lcid"),
			fields :["LCID","XM","EXAMCODE","XJH","SUBJECTNAME","YSCJ","KCFS","ZZCJ","SFZJH"],
			border:false
		});
		this.CheatGrid.on("render",function(){    
			this.CheatGrid.selModel.selectAll();  
		    //延迟300毫秒  
		},this,{delay:400});
		//搜索区域
		var bj	= new Ext.ux.Combox({width:190,id:"bj_find"});
		var nj	= new Ext.ux.Combox({width:190,id:"nj_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_classByGrade.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
					Ext.getCmp("bj_find").clearValue(); 
					Ext.getCmp("bj_find").store=newStore;  
					newStore.reload();
					Ext.getCmp("bj_find").bindStore(newStore);
				},
				scope:this
			}
		});
		var schoolname	= new Ext.ux.Combox({width:190,id:"sch_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_gradeBySchool.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
					Ext.getCmp("nj_find").clearValue(); 
					Ext.getCmp("bj_find").clearValue(); 
					Ext.getCmp("nj_find").store=newStore;  
					newStore.reload();
					Ext.getCmp("nj_find").bindStore(newStore);
				},
				scope:this
			}
		});
		var xjjyj	= new Ext.ux.Combox({ width:190,id:"xjjyj_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_school.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
					Ext.getCmp("sch_find").clearValue(); 
					Ext.getCmp("nj_find").clearValue(); 
					Ext.getCmp("bj_find").clearValue(); 
					Ext.getCmp("sch_find").store=newStore;  
					newStore.reload();
					Ext.getCmp("sch_find").bindStore(newStore);
				},
				scope:this
			}
		});
		var sjjyj	= new Ext.ux.Combox({dropAction:"sjjyj", width:190,id:"sjjyj_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_jyj.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
					Ext.getCmp("xjjyj_find").clearValue(); 
					Ext.getCmp("sch_find").clearValue(); 
					Ext.getCmp("nj_find").clearValue(); 
					Ext.getCmp("bj_find").clearValue(); 
					Ext.getCmp("xjjyj_find").store=newStore;  
					newStore.reload();
					Ext.getCmp("xjjyj_find").bindStore(newStore);
				},
				scope:this
			}
		});
		var xjh = new Ext.form.TextField({fieldLabel:"身份证号",id:"xjh_find",maxLength:200, width:190});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamSubject2,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.CheatSearch.getForm().reset()},scope:this});
		this.CheatSearch = new Ext.form.FormPanel({
			region: "north",
			height:130,
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
						       {html:"上级单位：",baseCls:"label_right",width:70},
						       {items:[sjjyj],baseCls:"component",width:200},
						       {html:"组织单位：",baseCls:"label_right",width:70},
						       {items:[xjjyj],baseCls:"component",width:200},
						       {html:"参考单位：",baseCls:"label_right",width:70}, 
						       {items:[schoolname],baseCls:"component",width:200},
						       {items:[cx],baseCls:"component_btn",width:100},
						       {html:"等级：",baseCls:"label_right",width:70},
						       {items:[nj],baseCls:"component",width:200},
						       {html:"科目：",baseCls:"label_right",width:70}, 
						       {items:[bj],baseCls:"component",width:200},
						       {html:"身份证号：",baseCls:"label_right",width:70},
						       {items:[xjh],baseCls:"component",width:200},
						       {items:[cz],baseCls:"component_btn",width:100}
						      ] 
                   	}]  
		      	}]  
	    });
	},
	/** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"border",items:[this.CheatSearch,this.CheatGrid]});
    },
    initQueryDate:function(){
    	this.CheatGrid.$load();
    },
	selectExamSubject2:function(){
		var xx = Ext.getCmp('sch_find').getValue();
		var nj = Ext.getCmp('nj_find').getValue();
		var bj = Ext.getCmp('bj_find').getValue();
		var xjh = Ext.getCmp('xjh_find').getValue();
		this.CheatGrid.$load({"xx":xx,"nj":nj,"bj":bj,"sfzjh":xjh});
	},
	fanhui:function(){
		window.location.href=Ext.get("ServerPath").dom.value+'/jsp/main.jsp?module=000802';
    }
});
