var responseArray;
var isUpdate = "";
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
    	syncRequest("examupdate_getSubject.do?lcid="+this.lcid);
    },    
    /** 对组件设置监听 **/
    initListener:function(){
    	this.editWindow.on("hide",function(){
    		this.editForm.$reset();
    	},this);
    	this.grid.on("rowdblclick",function(grid , rowIndex, e ){
    		isUpdate = "Yes";
    		this.lcid = grid.store.getAt(rowIndex).get("LCID");
    		this.editWindow.show(null,function(){
    			Ext.getCmp("kh").setValue(grid.store.getAt(rowIndex).get("KH")).disable();
    			//Ext.getCmp("xjh").setValue(grid.store.getAt(rowIndex).get("XJH")).disable();
    			for (var i = 0; i < responseArray.length; i++) {
				 	Ext.getCmp(responseArray[i].KMID).setValue(grid.store.getAt(rowIndex).get(responseArray[i].KM));
				}
    		},this)
    	},this)
    },   
   	initComponent :function(){   		
   		this.editForm   = this.createExamupdateForm();
   		this.editWindow = this.createWindow();
   		this.editWindow.add({items:[this.editForm]});
		
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});		
		var cm = new Array();
		var fields = new Array();
		
		cm[0] = sm ;
		cm[1] = {header: "考号",   align:"center", sortable:true, dataIndex:"KH"};
		//cm[2] = {header: "学号",   align:"center", sortable:true, dataIndex:"XJH"};
		cm[2] = {header: "姓名",   align:"center", sortable:true, dataIndex:"XM"};
		cm[3] = {header: "上限分数",   align:"center", sortable:true, dataIndex:"SXFS"};
		
		fields[0] = "KH";
		//fields[1] = "XJH";
		fields[1] = "XM";
		fields[2] = "SXFS";
		
		for (var i = 0; i < responseArray.length; i++) {
			cm[5+i] = {header: responseArray[i].KM, align:"center", sortable:true, dataIndex:responseArray[i].KM};
			fields[3+i] = responseArray[i].KM;
		}
	
		this.grid = new Ext.ux.GridPanel({
			id:'grid',
			cm:cm,
			sm:sm,
			title:"考试成绩-成绩修改",
			tbar:{
				cls:"ext_tabr",
				items:[ 
				       "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}
				       ,"->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteExamupdate,scope:this}
				       ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateExamupdate,scope:this}
				       ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.editWindow.show();Ext.getCmp("kh").enable();},scope:this}
				     ]
			},
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			excelTitle:"考试轮次列表",
			action:"examupdate_getListPage.do?lcid="+this.lcid,
			fields :fields,
			border:false
		});
		
		var xnxq_find=new Ext.ux.form.XnxqField({width:190,id:"euxnxq_find",readOnly:true,
			callback:function(){    //监听"select"事件
				var id=Ext.getCmp("euxnxq_find").getCode();           //取得ComboBox0的选择值
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
		var lc_find	= new Ext.ux.Combox({width:190,id:"lc_find"});
		var xuexiao_find= new Ext.ux.form.OrganField({name:"sup_organ_sel",width:190,readOnly:true});
		var xm_kh_xjh = new Ext.form.TextField({fieldLabel:"",id:"xm_kh_xjh",name:"xm_kh_xjh",maxLength:50,width:190});
		var cx = new Ext.Button({x : 17,y : -10,cls : "base_btn",text : "查询",handler : this.selectRound,scope : this});
		var cz = new Ext.Button({x : 87,y : -10,cls : "base_btn",text : "重置",handler : function() {this.search.getForm().reset();},scope : this});

		this.search = new Ext.form.FormPanel({
				region : "north",
				height : 130,
				items : [{
					layout : 'form',
					xtype : 'fieldset',
					style : 'margin:10 10',
					title : '查询条件',
					items : [{
						xtype : "panel",
						layout : "table",
						layoutConfig : {
							columns : 5
						},
						baseCls : "table",
						items : [
						         {html : "年度：",baseCls:"label_right",width:120},
						         {items : [xnxq_find],baseCls:"component",width:200},
						         {html : "考试名称：",baseCls:"label_right",width:120},
						         {items : [lc_find],baseCls:"component",width:200},
						         {html : "",baseCls:"label_right",width:120},
						         {html : "所属单位：",baseCls : "label_right",width : 120}, 
						         {items : [xuexiao_find],baseCls : "component",width : 200}, 
						         {html : "姓名/考号：",baseCls : "label_right",width : 120}, 
						         {items : [xm_kh_xjh],baseCls : "component",width : 200}, 
						         {layout : "absolute",items : [cx, cz],baseCls : "component_btn",width : 160}
						        ]
					}]
				}]
			})			
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({
    		layout : "border",
    		items : [this.search, this.grid]
    	});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },   
    createExamupdateForm:function(){		
    	var kh = new Ext.form.TextField({fieldLabel:"",id:"kh",name:"kh",maxLength:50,width:170}); 
		//var xjh = new Ext.form.TextField({fieldLabel:"",id:"xjh",name:"xjh",maxLength:50,width:170});
		var items = new Array();		
		items [0] = {html:"考号：",baseCls:"label_right",width:150};
		items [1] = {html:"<font class=required color=red>*</font>", items:[kh],baseCls:"component",width:200};
		//items [2] = {html:"学号：",baseCls:"label_right",width:150};
		//items [3] = {html:"<font class=required color=red>*</font>",items:[xjh],baseCls:"component",width:200};
			
		var count = 2
		for (var i = 0; i < responseArray.length; i++) {
		 	items [count] = {html:responseArray[i].KM+"分数：",baseCls:"label_right",width:150};
		 	count++;
		 	items [count] = {html:"<font class=required color=red>*</font>", items:[new Ext.form.TextField({id:responseArray[i].KMID,name:responseArray[i].KMID,maxLength:50,width:170})],baseCls:"component",width:200};
			count++;
		}
		
		return new Ext.ux.FormPanel({
			labelWidth:75,
			height : 250,
			frame:false,
			defaults:{anchor:"90%"},
			items:{
				xtype:"panel",
				layout:"table", 
				layoutConfig: {
					columns: 4
				}, 
				baseCls:"table",
				items:items
			}
		});
    },    
    createWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addExamupdate,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.editWindow.hide();isUpdate = "No";},scope:this});
		return new Ext.ux.Window({
				id:'window',
			 	width:750,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
			 	title:"增加"});			
    },
    addExamupdate:function(){
    	var addInfo ="";
    	var kh  = Ext.getCmp("kh").getValue();
    	//var xjh  = Ext.getCmp("xjh").getValue();
    	for (var i = 0; i < responseArray.length; i++) {
    		var score = Ext.getCmp(responseArray[i].KMID).getValue();
    		if(!/^[1-9]*[1-9][0-9]*$/.test(score)){
				Ext.MessageBox.alert("消息","分数请输入数字");
				return;
	    	}    		
		 	addInfo += responseArray[i].KMID+"="+score+",";
		}
    	var lc_find=this.search.getForm().findField("lc_find").getValue(); 
    	var lcidP = this.lcid;
		Ext.Ajax.request({
			url:"examupdate_addOrUpdateStuScore.do",
			params: {
				'addInfo':addInfo,
				'lcid' :lcidP,
				'lc_find':lc_find,
				'kh':kh,
				//'xjh':xjh,
				'isUpdate':isUpdate
			},
			success: function(response, options) {
				isUpdate = "No";
				var msg = Ext.util.JSON.decode(response.responseText);  
				if(msg.success==false){
					Ext.Msg.alert('消息',msg.msg);    
				}else {
					Ext.Msg.alert('消息',msg.msg);   
		    		var grid = Ext.getCmp("grid");
		    		var window = Ext.getCmp("window");
			 	 	grid.getStore().reload();
			 	 	window.hide();
				}
			 }
		});		    
    },    
    deleteExamupdate:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var delInfo = "";
    	for(var i = 0; i < selectedBuildings.length; i++) {
    		delInfo += selectedBuildings[i].get("KH")+";";
    		//delInfo += selectedBuildings[i].get("XJH")+";";
    	}
    	var lc_find=this.search.getForm().findField("lc_find").getValue();  
    	var lcidP = this.lcid;
    	Ext.MessageBox.show({
    			title:"消息",
    			msg:"您确定要删除吗?",
    			buttons:Ext.MessageBox.OKCANCEL,
    			icon:Ext.MessageBox.QUESTION,    			
    			fn:function(b){
    				if(b=='ok'){
    					JH.$request({
    						params:{
    							'delInfo':delInfo,
    							'lcid':lcidP,
    							'lc_find':lc_find
    						},
    						action:"examupdate_deleteStuScore.do",
    						handler:function(){
    							thiz.grid.$load();
    						}
    					})    					
    				}
    			}
    	})
    },
    updateExamupdate:function(){
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
	    }
    	this.editWindow.show(null,function(){
    		Ext.getCmp("kh").setValue(selectedBuildings[0].get("KH")).disable();
    		//Ext.getCmp("xjh").setValue(selectedBuildings[0].get("XJH")).disable();
    		for (var i = 0; i < responseArray.length; i++) {
    			Ext.getCmp(responseArray[i].KMID).setValue(selectedBuildings[0].get(responseArray[i].KM));
			}
    	},this)
    	isUpdate = "Yes";
    },
    selectRound :function(){
    	var xnxq=Ext.getCmp("euxnxq_find").getValue();
    	var lc_find=this.search.getForm().findField("lc_find").getValue();
    	if(lc_find==""){
    		Ext.MessageBox.alert("提示","请选择考试名称！");
    		return;
    	}
		var xuexiao=this.search.getForm().findField('sup_organ_sel').getValue();
		var xm_kh_xjh=Ext.getCmp('xm_kh_xjh').getValue();
		syncRequest("examupdate_getSubject.do?lcid="+lc_find);
		this.grid.$load({"xuexiao":xuexiao,"xm_kh_xjh":xm_kh_xjh,"lc_find":lc_find});
	},
	fanhui :function(){
		window.location.href=Ext.get("ServerPath").dom.value+'/jsp/main.jsp?module=000601';
	}
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
