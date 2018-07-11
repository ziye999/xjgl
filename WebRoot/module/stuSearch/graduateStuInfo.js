Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
		this.initComponent();
		this.initFace();
		this.initQueryDate(); 
    },
    /** 初始化页面、内存等基本数据 **/
    initDate:function(){ 
    	
    },
    statics:{
    	zstype: 'h'
    },
   	initComponent :function(){
   		this.mainPanel = this.createMainPanel();
   		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.mainPanel]   
        }); 
	},
	createMainPanel:function() {
    	//初始化搜索区
   		var zgjyj = new Ext.form.Field({ width:190,id:"zgjyj"}); 
   		var bynd = new Ext.ux.Combox({dropAction:"bynd", width:190,id:'box_bynd'});
   		var xsxm = new Ext.form.Field({ width:190,id:"xsxm"});
   		//var xjh	= new Ext.form.Field({ width:190,id:"xjh"});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.queryStu,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.mainsearch.getForm().reset();},scope:this});
		var organ_sel = new Ext.ux.form.OrganField({name:"organ_sel",width:200,readOnly:true});
		var mp = new MBspInfo();
		var items = [];
		var cols = 4;
		if(mp.getUserType()==2){ 
			items = [{html:"通过年度：",baseCls:"label_right",width:170},
			         {items:[bynd],baseCls:"component",width:200},
			         {html:"考生姓名：",baseCls:"label_right",width:170},
			         {items:[xsxm],baseCls:"component",width:200},
			         //{html:"学号：",baseCls:"label_right",width:170},
			         //{items:[xjh],baseCls:"component",width:200},
			         {html:"",baseCls:"label_right",width:170},
			         {layout:"absolute", items:[cx,cz],baseCls:"component",width:200}];
		}else if(mp.getUserType() == 1){
			cols = 4;
			items = [{html:"组织单位：",baseCls:"label_right",width:170},
			         {items:[organ_sel],baseCls:"component",width:200}, 
			         {html:"通过年度：",baseCls:"label_right",width:170},
			         {items:[bynd],baseCls:"component",width:200},
			         {html:"考生姓名：",baseCls:"label_right",width:170},
			         {items:[xsxm],baseCls:"component",width:200},
			         //{html:"学号：",baseCls:"label_right",width:170},
			         //{items:[xjh],baseCls:"component",width:200},
			         {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:120}];
		}
		this.mainsearch = new Ext.form.FormPanel({
			region: "north",
			height:120,
			items:[{  
				layout:'form',  
				xtype:'fieldset',  
				style:'margin:10 10',
				title:'查询条件',  
				items: [{
                    	xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: cols
						}, 
						baseCls:"table",
						items:items
                    }]
		       }]  
	    })
		
		//初始化数据列表区
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
			sm,
			{header: "考生姓名",   align:"center", sortable:true, dataIndex:"XSXM"},
			{header: "通过年度",   align:"center", sortable:true, dataIndex:"BYND"},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
			{header: "出生日期",   align:"center", sortable:true, dataIndex:"CSRQ"},
			{header: "通过年月",   align:"center", sortable:true, dataIndex:"BYNY"},
			{header: "通过证书号",   align:"center", sortable:true, dataIndex:"BYZSH"}			
		];
		this.maingrid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:'通过考生名单',
			page:true,
			rowNumber:true,
			region:"center",
			action:"studentQuery_getListPage.do",
			fields :["XSXM","XJH","BYND","XB","CSRQ","BYNY","BYZSH"],
			border:false
		});
		
   		return new Ext.Panel({
        	layout:"border",
    		items:[{
    			layout: 'border',
    			region:'center',
    			border: false,
    			split:true,
    			margins: '2 0 5 5', 
    			items:[this.mainsearch,this.maingrid]
			}]
        }); 
    },
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel(this.tabPanel);    	
    },
    initQueryDate:function(){
    	this.maingrid.$load();
    },
    fanhui:function(){
		this.tabPanel.setActiveTab(this.mainPanel);
  		this.maingrid.$load();
	},
	queryStu:function(){
		var zgjyj = this.mainsearch.getForm().findField('organ_sel').getCodes();
		this.maingrid.$load({zgjyj:zgjyj,type:this.statics.zstype,bynd:Ext.getCmp("box_bynd").getValue(),xsxm:xsxm.value});//,xjh:xjh.value
	}
});
