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
					{header : "年度",align : "center",sortable : true,dataIndex : "XN"},
					{header : "季度",align : "center",sortable : true,dataIndex : "XQ"},
					{header : "考试名称",align : "center",sortable : true,dataIndex : "KSMC",renderer : renderdel,listeners:{
						"click":function(){    //监听"select"事件
							this.showManual();
						},
						scope:this
					}}, 
					{header : "考试时间",align : "center",sortable : true,dataIndex : "KSSJ"},
					{header : "参考人数",align : "center",sortable : true,dataIndex : "CKRS"},
					{header : "违纪人次",align : "center",sortable : true,dataIndex : "WJRC"}
				];
				
  		function renderdel(value, cellmeta, record, rowIndex, columnIndex, store){ 
  			var lcid = store.getAt(rowIndex).get('LCID'); 
  			//"+Ext.get("ServerPath").dom.value+"/jsp/main.jsp?module=00080101&lcid="+lcid+"
            var str = "<a href='#' id='"+lcid+"'>"+value+"</a>";			
            return str;  
        } 
       
		this.grid = new Ext.ux.GridPanel({
					cm : cm,
					sm : sm,
					title : "考试成绩-成绩修改",
					tbar : ["->", {
								xtype : "button",
								text : "成绩修正",
								iconCls : "p-icons-update",
								handler : this.autoModify,
								scope : this
							}],
					page : true,
					rowNumber : true,
					region : "center",
					excel : true,
					action : "modifyscore_getListPage.do",
					excelTitle : "轮次展示表",
					fields : ["LCID", "XN", "XQ", "KSMC","KSSJ","CKRS", "WJRC"],
					border : false
				});
				
		// 搜索区域		
		var xnxq = new Ext.ux.form.XnxqField({name:"modxnxq", id:"modxnxq_id",width:210,readOnly:true});
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
		this.panel=new Ext.Panel({
			region:"north",
			width:"auto",
			layout:"border",			
			border:false,
			id:"panelModify1",
			items:[this.search,this.grid]
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
	manualModifyShow : function() {
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : false});
		var cm = [
		          {header : "考试名称",align : "center",sortable : true,dataIndex : "KSMC"},
		          {header : "身份证号",align : "center",sortable : true,dataIndex : "SFZJH"}, 
		          {header : "考号",align : "center",sortable : true,dataIndex : "KH"},
		          {header : "姓名",align : "center",sortable : true,dataIndex : "XM"},
		          {header : "性别",align : "center",sortable : true,dataIndex : "XB"},
		          {header : "原始成绩",align : "center",sortable : true,dataIndex : "YSCJ"},
		          {header : "扣除分数",align : "center",sortable : true,dataIndex : "KCFS",renderer : renderdel},
		          {header : "最终分数",align : "center",sortable : true,dataIndex : "ZZFS"}
				];
				
  		function renderdel(value, cellmeta, record, rowIndex, columnIndex, store){ 
          	var str = "<input type='text' id='kcfs"+rowIndex+"' value='"+value+"' size = '5'/>"
	        return str;  
        } 
		this.grid1 = new Ext.ux.GridPanel({
					cm : cm,
					title : "考试成绩-成绩修改",
					tbar : ["->", {
								xtype : "button",
								text : "取消",
								iconCls : "p-icons-checkclose",
								handler : this.quxiao,
								scope : this
							},"->", {
								xtype : "button",
								text : "保存",
								iconCls : "p-icons-save",
								handler : this.manualModify,
								scope : this
							}],
					page : true,
					rowNumber : true,
					region : "center",
					excel : true,
					action : "modifyscore_cheatListPage.do?lcid="+this.lcid,
					excelTitle : "轮次展示表",
					fields : ["WJID", "KSMC", "KM", "KH","XJH","XM", "XB", "YSCJ", "KCFS", "ZZFS", "SFZJH"],
					border : false
				});
				
		// 搜索区域
		var km= new Ext.ux.Combox({dropAction:"getKsKm", id:"km_lcid", params:this.lcid, width:145});
		var xm_kh_xjh = new Ext.form.TextField({fieldLabel:"",id:"xm_kh_xjh",name:"xm_kh_xjh",maxLength:50,width:170});
		var cx = new Ext.Button({x : 17,y : -10,cls : "base_btn",text : "查询",handler : this.selectRound1,scope : this});
		var cz = new Ext.Button({x : 87,y : -10,cls : "base_btn",text : "重置",handler : function() {this.search1.getForm().reset();},scope : this});
	
		this.search1 = new Ext.form.FormPanel({
					region : "north",
					height : 90,
					items : [{
							layout : 'form',
							xtype : 'fieldset',							
							title : '查询条件',
							items : [{
									xtype : "panel",
									layout : "table",
									layoutConfig : {
										columns : 7
									},
									baseCls : "table",
									items : [
									         {html : "考试批次：",baseCls : "label_right",width : 80}, 
									         {items : [km],baseCls : "component",width : 210}, 
									         {html : "姓名/考号/身份证号：",baseCls : "label_right",width : 150}, 
									         {items : [xm_kh_xjh],baseCls : "component",width : 210}, 
									         {layout : "absolute",items : [cx, cz],baseCls : "component_btn",width : 160}
									        ]
									}]
							}]
		});
		this.panel_top2=new Ext.Panel({
			id:"panel_topM2",
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			items:[this.search1,this.grid1]
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
	},
	selectRound :function(){
		var xnxq_id=Ext.getCmp('modxnxq_id').getCode();
    	this.grid.$load({"xnxq_id":xnxq_id});
	},	
	autoModify :function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
    	}
    	var lcid = selectedBuildings[0].get("LCID");
		var msg = syncRequest("modifyscore_autoModify.do?lcid="+lcid);
		Ext.MessageBox.alert("消息",msg.msg);
	},
	showManual:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	this.lcid=selectedBuildings[0].get("LCID");    	
    	this.manualModifyShow();
    	var panel=Ext.getCmp("panel_topM");
  		panel.remove(Ext.getCmp("myTabsM"));
  		panel.add(this.panel_top2);
  		panel.doLayout(false);
  		this.grid1.$load({"lcid":this.lcid});
    },
	selectRound1 :function(){
		var km=Ext.getCmp('km_lcid').getValue();
		var xm_kh_xjh  = Ext.getCmp('xm_kh_xjh').getValue();
    	this.grid1.$load({"km":km,"xm_kh_xjh":xm_kh_xjh});
	},	
	manualModify :function(){
		var store = this.grid1.getStore();
		var count = store.getCount(); //获取Store对象
		var xgkf = "";
		for (var i = 0; i < count; i++) {
			var wjid = store.getAt(i).get("WJID")
			var kcfs = store.getAt(i).get("KCFS");
			var yscj = store.getAt(i).get("YSCJ");
			var xgkcfs = document.getElementById("kcfs"+i).value;
			if (xgkcfs!="") {
				if(!/^(([0-9]+[\.]?[0-9]{1})|[1-9])$/.test(xgkcfs)){
					if (parseFloat(xgkcfs)<0) {
						Ext.MessageBox.alert("消息","扣除分数请输入正数");
						return;
					}					
		    	}
			}						
			if(parseFloat(xgkcfs)>=parseFloat(yscj)){
				Ext.MessageBox.alert("消息","扣除分数不能大于等于原始分数");
				return;
			}
			if(!(kcfs == xgkcfs)){
				xgkf += wjid+"="+xgkcfs+";"
			}
		}
		var msg = syncRequest("modifyscore_manualModify.do?lcid="+this.lcid+"&xgkf="+xgkf);
		if(msg.success == true){
			Ext.MessageBox.alert("消息",msg.msg);
			var km=Ext.getCmp('km_lcid').getValue();
			var xm_kh_xjh  = Ext.getCmp('xm_kh_xjh').getValue();
	    	this.grid1.$load({"km":km,"xm_kh_xjh":xm_kh_xjh});
			//window.location.href=Ext.get("ServerPath").dom.value+'/jsp/main.jsp?module=000801';
		}else{
			Ext.MessageBox.alert("消息",msg.msg);
		}
	},	
	quxiao : function(){
		this.initComponent();
    	var panel=Ext.getCmp("panel_topM");
  		panel.remove(Ext.getCmp("panel_topM2"));
  		panel.add(this.myTabs);
  		panel.doLayout(false);
  		this.grid.$load();
		//window.location.href=Ext.get("ServerPath").dom.value+'/jsp/main.jsp?module=000801';
	} 
});

var syncRequest = function(url) {   
	var conn = Ext.lib.Ajax.getConnectionObject().conn;     
	try {     
		conn.open("POST", url, false);
		conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		conn.send(null);     
	} catch (e) {     
		Ext.Msg.alert('info','error');     
		return false;     
	}     
	return Ext.decode(conn.responseText);
}
function isPInt(str) {
    var g = /^([1-9]\d*|[0]{1,1})$/;
    return g.test(str);
}
