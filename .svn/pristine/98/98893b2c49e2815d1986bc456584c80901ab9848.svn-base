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
    	zstype:1
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
   		var sch	= new Ext.form.Field({ width:190,id:"sch"});
   		var bynd = new Ext.form.Field({ width:190,id:"bynd"});   		  
   		//var xjh	= new Ext.form.Field({ width:190,id:"xjh"});
		var organ_sel = new Ext.ux.form.OrganField({name:"organ_sel",width:200,readOnly:true});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.queryStu,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.mainsearch.getForm().reset();},scope:this});
		var mp = new MBspInfo();
		var items = [];
		var cols = 5;
		var heg = 90;
		items = [{html:"组织单位：",baseCls:"label_right",width:70},
		         {items:[organ_sel],baseCls:"component",width:200}, 
		         //{html:"学号：",baseCls:"label_right",width:70},
		         //{items:[xjh],baseCls:"component",width:200},
		         {layout:"absolute", items:[cx,cz],baseCls:"component",width:160}];		
		this.mainsearch = new Ext.form.FormPanel({
			id:"main_search_form",
			region: "north",
			height:heg,
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
		
		this.stuType = new Ext.ux.TreePanel({region:"west",
			          		rootVisible:false,
			          		title:"通过类型",
			          		collapseMode : "mini",
			          		split:true,
			          		minSize: 120,
			          		width:200, 
			          		maxSize: 300,
			          		autoScroll:true, 
			          		root: new Ext.tree.AsyncTreeNode({
			          			expanded: true,
			          			children: [{
			          				id:"type",
			          				text: '证书类型',
			          				children:[{
			          					id:"byz",
			          					text: '通过证书',
			          					leaf: true
			          				/*}, 
			          				{
			          					id:"jyz",
			          					text: '结业证',
			          					leaf: true
			          				}, 
			          				{
			          					id:"yyz",
			          					text: '不通过证',
			          					leaf: true
			          				}, 
			          				{
			          					id:"xlzm",
			          					text: '学历证明',
			          					leaf: true*/
			          				}]
			          			}]
			          		}),listeners: { click: funct}
   		}); 
   		
   		var that = this; 
   		function funct(n,e){
   			if(n.id=="byz"){  
   				that.statics.zstype = 1;
   			}else if(n.id == "jyz"){
   				that.statics.zstype = 2;
   			}else if(n.id == "yyz"){
   				that.statics.zstype = 3;
   			}else if(n.id == "xlzm"){
   				that.statics.zstype = 4;
   			} 
   			Ext.getDom("dataPanelT").innerHTML=''; 
   			Ext.getCmp('main_search_form').getForm().reset();
   		};
   		    		
   		this.dataPanelT=new Ext.Panel({
    		region:'center',
    		border:false,
   			id:"dataPanelT"
   		});
   	  
   		this.sidePanel = new Ext.Panel({
   			region:'center',
    		border:'border',
    		id:'sidePanel',
    		height:488,
    		style:'background-color:#B2E0F9',
   			tbar:[ 
				  "->",{xtype:"button",text:"打印",iconCls:"p-icons-print",handler:this.printInfo,scope:this}
				  ],
			items:[this.dataPanelT]
   		});
   		
   		this.sPanel=new Ext.Panel({
				layout: 'border',
		        region:'center',
		        autoScroll:true,
		        border: false,
		        split:true,
				margins: '2 0 5 5', 
				items:[this.mainsearch,this.sidePanel]
		}); 
   		return new Ext.Panel({
   			id:"mainPanel",
        	layout:"border",
    		items:[this.stuType,this.sPanel] 
        }); 
    },	
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel(this.tabPanel);
    	this.stuType.expandAll();
    	this.stuType.root.item(0).findChild("id","byz").select();
    },
    initQueryDate:function(){ 
   		
    },
    fanhui:function(){
		this.tabPanel.setActiveTab(this.mainPanel);
	},
	queryStu:function(){  
		this.loadStuInfo();		
	},
	printInfo:function(){
		frmReportT.print();
	},
	loadStuInfo:function(){
		var zgjyj = this.mainsearch.getForm().findField('organ_sel').getCodes();
		if(zgjyj==null||zgjyj=="undefined"){
			zgjyj="";
		}
		//var xh = Ext.getCmp("xjh").getValue();
		var height = Ext.getCmp('sidePanel').getHeight()-14;
		var iframe="<fieldset style='height:100%; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReportT' name='frmReportT' width='100%' height='"+height+
						"' src='certificatePrint_getStuCertiInfo.do?zgjyj="+zgjyj+"&zstype="+
						this.statics.zstype+"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
				   "</fieldset>";
    	Ext.getDom('dataPanelT').innerHTML=iframe;	
	}
});
