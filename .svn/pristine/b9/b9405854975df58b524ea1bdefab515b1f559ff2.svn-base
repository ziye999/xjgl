var wjcllx = [['1', '考生名单审核'],['2', '补报考生审核'],['3', '删除考生审核']];
var wjcllxStore = new Ext.data.SimpleStore({  
  fields : ['value', 'text'],  
  data : wjcllx  
});
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
    		           if(str[0].get("DLFLAG")=="1"){
    		        	   Ext.getCmp('sh').setDisabled(true);
    		           }else{
    		        	   Ext.getCmp('sh').setDisabled(false);
    		           }
    			}}});
		var cm = [
		    sm,
			{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   align:"center", sortable:true, dataIndex:"XQ"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME",renderer : renderdel,listeners:{
				 "click":function(){    //监听"select"事件
	       				this.showLackOfTestStudent();
	                 },
	                 scope:this
					}},
			{header: "考试类型",   align:"center", sortable:true, dataIndex:"LX"},
			{header: "审核状态",   align:"center", sortable:true, dataIndex:"DLFLAG",renderer : renderdel2}
		];
		function renderdel(value, cellmeta, record, rowIndex, columnIndex, store){ 
  			var lcid = store.getAt(rowIndex).get('LCID'); 
            var str = "<a href='#' id='"+lcid+"'>"+value+"</a>";
            return str;  
        } 
		function renderdel2(value, cellmeta, record, rowIndex, columnIndex, store){ 
			var str = "";
  			if(value=="1"){
  				str = "已审核";
  			}else{
  				str="未审核";
  			}
            return str;  
        } 
	this.grid = new Ext.ux.GridPanel({
		cm:cm,
		sm:sm,
		title:"考生报名-删除考生审核",
		tbar:[ 
			  "->",{xtype:"button",id:"sh",text:"审核",iconCls:"p-icons-save",handler:this.updateAll,scope:this},
			  ],
		page:true,
		rowNumber:true,
		region:"center",
		excel:true,
		action:"review_getListPage.do",
		fields :["LCID","XN","XQ","EXAMNAME","LX","DWID","DWTYPE","DLFLAG"],
		border:false
		});
	
	//搜索区域
	var xn_find=new Ext.ux.form.XnxqField({width:210,id:"exdxn_find",readOnly:true});
	var xn_sh	= new Ext.form.ComboBox({id:"xn_sh",store:wjcllxStore,value:'3',displayField:'text',valueField:'value',mode:'local',triggerAction:'all',width:200,editable:false,listeners:{
		"select":function(combo, record, index){
			/*if(record.get("value")==2){
				parent.Ext.getCmp("myTabs").setActiveTab(1);
				Ext.getCmp("xn_sh").setValue(3);
			}
			if(record.get("value")==1){
				parent.Ext.getCmp("myTabs").setActiveTab(0);
				Ext.getCmp("xn_sh").setValue(3);
			}*/
		}
	}});
	var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamSubject,scope:this});
	var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
	
	this.search = new Ext.form.FormPanel({
		   region: "north",
	       height:100,
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
						columns: 6
						}, 
					baseCls:"table",
					items:[
						{html:"年度：",baseCls:"label_right",width:120},
						{items:[xn_find],baseCls:"component",width:210},
						{html:"审核类型：",baseCls:"label_right",width:120},
						{items:[xn_sh],baseCls:"component",width:210},
						{layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:160}
						] 
                }]  
	       }]  
    	})
		this.panel=new Ext.Panel({
			id:"panelED",
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			items:[this.search,this.grid]
		});
	},
	 /** 初始化界面 **/
    initFace:function(){
    	this.panel_top=new Ext.Panel({
			layout:"fit",
    		id:"panel_topED",
    		region:"north",
    		border:false,
    		items:[this.panel]
    	});
	this.addPanel({layout:"fit",items:[this.panel_top]});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
	selectExamSubject:function(){
		var xn=Ext.getCmp('exdxn_find').getValue();
		this.grid.$load({"xn":xn});
	},updateAll:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}
    	var lcid = selectedBuildings[0].get("LCID");
//    	var store3 = new Ext.data.JsonStore({
//    		autoLoad:false,
//    		url:'review_updateAll.do?ksid='+lcid
//    	});
//    	store3.reload();
    	var thiz=this;
    	Ext.Ajax.request({   
       		url:'review_updateDL.do',
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
	showLackOfTestStudent:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var ids =selectedBuildings[0].get("LCID");
    	this.lcid=ids;
    	this.createShowLackOfTestStudent();
    	var panel=Ext.getCmp("panel_topED");
  		panel.remove(Ext.getCmp("panelED"));
  		panel.add(this.panel_top2);
  		panel.doLayout(false);
  		this.CheatGrid.$load({"lcId":this.lcid});
    },
	createShowLackOfTestStudent:function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
					sm,
					{header: "参考单位",   align:"center", sortable:true, dataIndex:"XXMC"},
					{header: "姓名",   align:"center", sortable:true, dataIndex:"XM"},
					{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
					{header: "考号",   align:"center", sortable:true, dataIndex:"KSCODE"},
					{header: "身份证号",   align:"center", sortable:true, dataIndex:"SFZJH"}
				];
		this.CheatGrid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			id:"CheatGridE",
			title:"考生报名-删除考生审核",
			tbar:[ 
				  "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}
				  ,"->",{xtype:"button",text:"审核通过",iconCls:"p-icons-save",handler:this.updateExamRoom,scope:this},
				  ],
			page:true,
			rowNumber:true,
			region:"center",
			action:"review_getDeleListPage.do?lcid="+getLocationPram("lcid"),
			fields :["XXMC","XM","XB","KSCODE","XJH","SFZJH","SCKSID"],
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
		//var xjh		= new Ext.form.TextField({fieldLabel:"学号",id:"xjh_find",maxLength:200, width:190});
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
		         items: [
                    {
                    	xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 7
							}, 
						baseCls:"table",
						items:[
							{html:"上级单位：",baseCls:"label_right",width:120},
							{items:[sjjyj],baseCls:"component",width:210},
							{html:"组织单位：",baseCls:"label_right",width:120},
							{items:[xjjyj],baseCls:"component",width:210},
							{html:"参考单位：",baseCls:"label_right",width:120}, 
							{items:[schoolname],baseCls:"component",width:210},
							{baseCls:"label_right",width:0},
							{html:"等级：",baseCls:"label_right",width:120},
							{items:[nj],baseCls:"component",width:210},
							{html:"科目：",baseCls:"label_right",width:120}, 
							{items:[bj],baseCls:"component",width:210},
							{layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:280,colspan:2}
							//{html:"学号：",baseCls:"label_right",width:120},
							//{items:[xjh],baseCls:"component",width:210}
							] 
                    }]  
		       }]  
	    	});
		this.panel_top2=new Ext.Panel({
			id:"panel_topED2",
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			items:[this.CheatSearch,this.CheatGrid]
		});
	},
	selectExamSubject2:function(){
		var xx = Ext.getCmp('sch_find').getValue();
		var nj = Ext.getCmp('nj_find').getValue();
		var bj = Ext.getCmp('bj_find').getValue();
		//var xjh = Ext.getCmp('xjh_find').getValue();
		this.CheatGrid.$load({"xx":xx,"nj":nj,"bj":bj});//,"xjh":xjh
	},fanhui:function(){
		this.initComponent();
    	var panel=Ext.getCmp("panel_topED");
  		panel.remove(Ext.getCmp("panel_topED2"));
  		panel.add(this.panel);
  		panel.doLayout(false);
  		var xn=Ext.getCmp('exdxn_find').getValue();
		this.grid.$load({"xn":xn});
    },updateExamRoom:function(){
    	var selectedBuildings = this.CheatGrid.getSelectionModel().getSelections();
    	if(selectedBuildings.length < 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}
    	var ksid = "";
    	for(var i=0;i<selectedBuildings.length;i++){
    		ksid += "'" +selectedBuildings[i].get("SCKSID") +"',";
    	}
//    	var store3 = new Ext.data.JsonStore({
//    		autoLoad:false,
//    		url:'review_updateDl.do?ksid='+ksid
//    	});
//    	store3.reload();
//    	this.CheatGrid.$load();
    	var thiz=this;
    	Ext.Ajax.request({   
       		url:'review_updateDl.do',
       		params:{
       			'ksid':ksid
        	},
        success: function(resp,opts) {
        	var respText = Ext.util.JSON.decode(resp.responseText);
           	Ext.MessageBox.alert("提示",respText.msg);
           	thiz.selectExamSubject2();
        },
        failure: function(resp,opts){
            Ext.Msg.alert('错误', "审核失败！");
        }  
       });
    }
});