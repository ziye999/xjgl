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
    initComponent :function(){
    	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true,
    		listeners:{
    			"selectionchange":function(sm){
    				var str = sm.getSelections();
    				if(str[0].get("CJXZFLAG")=="1"){
    					Ext.getCmp('sh').setDisabled(true);
    				}else{
    					Ext.getCmp('sh').setDisabled(false);
    				}
    			}
    		}
    	});
		var cm = [
		    sm,
			{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   align:"center", sortable:true, dataIndex:"XQ"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME",renderer : renderdel,listeners:{
				"click":function(){    //监听"select"事件
					this.showReview();
				},
				scope:this
			}},
			{header: "考试类型",   align:"center", sortable:true, dataIndex:"LX"},
			{header: "审核状态",   align:"center", sortable:true, dataIndex:"CJXZZT"}
		];
		function renderdel(value, cellmeta, record, rowIndex, columnIndex, store){ 
  			var lcid = store.getAt(rowIndex).get('LCID');
  			//"+Ext.get("ServerPath").dom.value+"/jsp/main.jsp?module=00080201&lcid="+lcid+"
            var str = "<a href='#' id='"+lcid+"'>"+value+"</a>";
            return str;  
        } 
		
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"成绩修正-修正成绩审核",
			tbar:[ 
			      "->",{xtype:"button",id:"sh",text:"审核",iconCls:"p-icons-save",handler:this.updateAll,scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			action:"gradeAmendment_getListPage.do",
			fields :["LCID","XN","XQ","EXAMNAME","LX","DWID","DWTYPE","CJXZFLAG","CJXZZT"],
			border:false
		});
	
		//搜索区域
		var xnxq_find=new Ext.ux.form.XnxqField({width:210,id:"gdxnxq_find",readOnly:true,
			callback:function(){    //监听"select"事件
				var id=Ext.getCmp("gdxnxq_find").getCode();           //取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
						autoLoad:false,
						url:'dropListAction_examround.do?params='+id,
						fields:["CODEID","CODENAME"]
					});
				Ext.getCmp("lc_find").clearValue(); 
				Ext.getCmp("lc_find").store=newStore;  
				newStore.reload();
				Ext.getCmp("lc_find").bindStore(newStore);
			}
		});
		var lc_find	= new Ext.ux.Combox({width:200,id:"lc_find"});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamSubject,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
	
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
						columns: 6
					}, 
					baseCls:"table",
					items:[
					       {html:"年度：",baseCls:"label_right",width:90},
					       {items:[xnxq_find],baseCls:"component",width:210},
					       {html:"考试名称：",baseCls:"label_right",width:90},
					       {items:[lc_find],baseCls:"component",width:210},
					       {layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:160}
						] 
				}]  
	       }]  
    	})
		this.panel=new Ext.Panel({
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			id:"panelAdt",
			items:[this.search,this.grid]
		});	
		this.myTabs = new Ext.TabPanel({
		    id:'myTabsG',
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
		    items:[{layout: 'fit', index: 0, border: false, items: [this.panel],title:"成绩修正审核"}]
		});
	},
	gradeShow:function(){
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
			cm:cm,
			id:"CheatGridG",
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
		var nj	= new Ext.ux.Combox({width:190,id:"nj_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_classByGrade.do?params='+id+'&schoolId='+schoolname.getValue(),
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
		this.panel_top2=new Ext.Panel({
			id:"panel_topG2",
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			items:[this.CheatSearch,this.CheatGrid]
		});
	},
	/** 初始化界面 **/
    initFace:function(){
    	this.panel_top=new Ext.Panel({
			layout:"fit",
    		id:"panel_topG",
    		region:"north",
    		border:false,
    		items:[this.myTabs]
    	});
    	this.addPanel({layout:"fit",items:[this.panel_top]});    
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
	selectExamSubject:function(){
		var xnxq=Ext.getCmp('gdxnxq_find').getValue();
		var lcid=Ext.getCmp('lc_find').getValue();
		this.grid.$load({"xnxq":xnxq,"lcid":lcid});
	},
	updateAll:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}
    	var lcid = selectedBuildings[0].get("LCID");
    	var thiz=this;
    	Ext.Ajax.request({   
       		url:'gradeAmendment_updateAll.do',
       		params:{
       			'lcid':lcid
        	},
        	success: function(resp,opts) {
        		var respText = Ext.util.JSON.decode(resp.responseText);
        		Ext.MessageBox.alert("提示",respText.msg);
        		thiz.selectExamSubject();thiz.selectExamSubject();
        	},
        	failure: function(resp,opts){
        		Ext.Msg.alert('错误', "审核失败！");
        	}  
    	});
	},
	showReview:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var ids =selectedBuildings[0].get("LCID");    	
    	this.lcid=ids;
    	this.gradeShow();
    	var panel=Ext.getCmp("panel_topG");
  		panel.remove(Ext.getCmp("myTabsG"));
  		panel.add(this.panel_top2);
  		panel.doLayout(false);	
  		this.CheatGrid.$load({"lcid":this.lcid});
    },
    selectExamSubject2:function(){
		var xx = Ext.getCmp('sch_find').getValue();
		var nj = Ext.getCmp('nj_find').getValue();
		var bj = Ext.getCmp('bj_find').getValue();
		var xjh = Ext.getCmp('xjh_find').getValue();
		this.CheatGrid.$load({"xx":xx,"nj":nj,"bj":bj,"sfzjh":xjh});
	},
    fanhui:function(){
    	this.initComponent();
    	var panel=Ext.getCmp("panel_topG");
  		panel.remove(Ext.getCmp("panel_topG2"));
  		panel.add(this.myTabs);
  		panel.doLayout(false);
  		this.grid.$load();
    }
});
