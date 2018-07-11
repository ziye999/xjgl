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
    	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
		    sm,
			{header: "组考单位代码",   align:"center", sortable:true, dataIndex:"XXBSM"},
			{header: "组考单位名称",   align:"center", sortable:true, dataIndex:"XXMC"},
			{header: "楼层总数",   	align:"center", sortable:true, dataIndex:"LCS"},
			{header: "座位总数",   	align:"center", sortable:true, dataIndex:"ZWS"},
			{header: "设置状态",   	align:"center", sortable:true, dataIndex:"ZT"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"考点设定-设置考点组考单位",
			tbar:[ 
				  "->",{xtype:"button",id:"shd",text:"取消",iconCls:"p-icons-checkclose",handler:this.quxiaoAll,scope:this},
				  "->",{xtype:"button",id:"sh",text:"设置",iconCls:"p-icons-submit",handler:this.updateAll,scope:this}
				 ],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			action:"examsiteschool_getListPage.do",
			fields :["XXBSM","XXMC","SCHOOLID","LCS","ZWS","SETSTATUS","ZT"],
			border:false
		});
	
		//搜索区域		
		var sup_organ_sel = new Ext.ux.form.OrganField({name:"sup_organ_sel",width:210,readOnly:true});
		var xnxq_find=new Ext.ux.form.XnxqField({width:180,id:"esxnxq_find",readOnly:true,
			callback:function(){    //监听"select"事件
				var id=Ext.getCmp("esxnxq_find").getCode();           //取得ComboBox0的选择值
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
		var lc_find	= new Ext.ux.Combox({width:170,id:"lc_find"});
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
							columns: 8
						}, 
						baseCls:"table",
						items:[
						       {html:"组织单位：",baseCls:"label_right",width:70},
						       {items:[sup_organ_sel],baseCls:"component",width:210},
						       {html:"年度：",baseCls:"label_right",width:50},
						       {items:[xnxq_find],baseCls:"component",width:180},
						       {html:"考试名称：",baseCls:"label_right",width:70},
						       {items:[lc_find],baseCls:"component",width:180},
						       {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:155}
						      ] 
				}]  
			}]  
		})
		this.panel=new Ext.Panel({
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
    		region:"north",
    		border:false,
    		items:[this.panel]
    	});
    	this.addPanel({layout:"fit",items:[this.panel_top]});
    },
    initQueryDate:function(){
    	//this.grid.$load();
    },
	selectExamSubject:function(){
		var jyj=this.search.getForm().findField('sup_organ_sel').getCodes();
		var lc=Ext.getCmp('lc_find').getValue();
		/*if(jyj == "" || jyj == null){
			Ext.MessageBox.alert("消息","请选择组织单位！");
			return;
		}*/
		if(lc == ""){
			Ext.MessageBox.alert("消息","请选择考试名称！");
			return;
		}
		this.grid.$load({"jyj":jyj,"lcid":lc});
	},
	updateAll:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length < 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}
    	var array=new Array();
    	var lc=Ext.getCmp('lc_find').getValue();
    	for(var i=0;i<selectedBuildings.length;i++){
    		var obj =new Object();
    		obj.schoolid = selectedBuildings[i].get("SCHOOLID");
    		obj.xxmc = selectedBuildings[i].get("XXMC");
    		obj.lcs = selectedBuildings[i].get("LCS");
    		obj.zws = selectedBuildings[i].get("ZWS");
    		if(selectedBuildings[i].get("SETSTATUS")=='0')
    		array[i]=obj;
    	}
    	var thiz=this;
    	var str=JSON.stringify(array)
    	Ext.Ajax.request({   
       		url:'examsiteschool_saveExamSchool.do',
       		params:{
       			'lcid':lc,'objList':str
        	},
        	success: function(resp,opts) {
        		var respText = Ext.util.JSON.decode(resp.responseText);
        		Ext.MessageBox.alert("提示",respText.msg);
        		thiz.selectExamSubject();thiz.selectExamSubject();
        	},
        	failure: function(resp,opts){
        		Ext.Msg.alert('错误', "保存失败！");
        	}        	
    	});
	},
	quxiaoAll:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length < 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}
    	var array=new Array();
    	var lc=Ext.getCmp('lc_find').getValue();
    	for(var i=0;i<selectedBuildings.length;i++){
    		var obj =new Object();
    		obj.schoolid = selectedBuildings[i].get("SCHOOLID");
    		obj.xxmc = selectedBuildings[i].get("XXMC");
    		obj.lcs = selectedBuildings[i].get("LCS");
    		obj.zws = selectedBuildings[i].get("ZWS");
    		array[i]=obj;
    	}
    	var thiz=this;
    	var str=JSON.stringify(array)
    	Ext.Ajax.request({   
       		url:'examsiteschool_deleteExamSchool.do',
       		params:{
       			'lcid':lc,'objList':str
        	},
        	success: function(resp,opts) {
        		var respText = Ext.util.JSON.decode(resp.responseText);
        		Ext.MessageBox.alert("提示",respText.msg);
        		thiz.selectExamSubject();thiz.selectExamSubject();
        	},
        	failure: function(resp,opts){
        		Ext.Msg.alert('错误', "操作失败！");
        	}        
    	});
	}
});