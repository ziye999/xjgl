function replaceSt(t){
	alert(1);
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
	var cm = [
		sm,			 
		{header: "题型",   align:"center", sortable:true, dataIndex:"TX"}, 
		{header: "分值",   align:"center", sortable:true, dataIndex:"FZ"},   
		{header: "题目",   align:"center", sortable:true, dataIndex:"TM"}
	];
	var myProxy = new Ext.data.HttpProxy({
		url:'examst_replaceSt.do', 
		method:'post'
	}); 
	
	var myStore = new Ext.data.Store({
		method:'post',
		proxy:myProxy,
		fields:['TX','FZ','TM'],
		autoLoad:true
	});
	this.grid = new Ext.grid.GridPanel({
		cm:cm,
		sm:sm,
		title:"试题信息",  
		region:"center", 
		store:myStore,
		border:false
	}); 
	var stpanel = new Ext.Panel({layout:"border",region:"center",items:[this.grid]}); 
	var save = new Ext.Button({text:"选择",iconCls:"p-icons-save",handler:this.saveSetInfo,scope:this});
	var cancel = new Ext.Button({text:"取消",liconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide()},scope:this});
	var twin = new Ext.Window({
		 	width:845,
		 	tbar:{
				items:[ 
				 	 "->",cancel
		 			 ,"->",save
				  ]
			},
		 	title:"替换试题"}); 
	twin.show();
}