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
		var iframe="<fieldset style='height:428; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='428' "+
						"frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
		if(mBspInfo.getUserType() == "1") {
			this.jyjOrgTree.on("click",function(node){  
		      	Ext.getCmp("search_form5").getForm().reset();
		      	Ext.getDom('jyjdataPanel').innerHTML=iframe;
	    	},this.grid);
	    	this.jyjOrgTree.on("afterRender",function(node){
	    		this.getSelectionModel().select(node.getNodeById("jyj_1"));
			},this.grid);
		}else if(mBspInfo.getUserType() == "2"){
			this.schoolOrgTree.on("click",function(node){  
		      	Ext.getCmp("search_form6").getForm().reset();
		      	Ext.getDom('dataPanelEIC').innerHTML=iframe;
	    	},this.grid);
	    	this.schoolOrgTree.on("afterRender",function(node){
	    		this.getSelectionModel().select(node.getNodeById("school_1"));
			},this.grid);
		}		
    },   
   	initComponent :function(){
   		this.jyjPanel = this.createJyjPanel();
    	this.schoolPanel = this.createSchoolPanel();
		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.jyjPanel,this.schoolPanel]   
		}); 
	},
	createJyjPanel:function(){
   		this.jyjOrgTree = new Ext.ux.TreePanel({region:"west",
			         	rootVisible:true,
			         	title:"报名信息统计",
			         	collapseMode : "mini",
			         	split:true,
			         	minSize: 120,
			         	width:200,
			         	maxSize: 300,
			         	autoScroll:true,
			         	root: new Ext.tree.AsyncTreeNode({
			         		expanded: true,
			         		text: '报名信息统计',
				            children: [{
				            	id:"jyj_1",
				                text: '报名详细情况',
				                leaf: true
				            }, 
				            {
				            	id:"jyj_2",
				                text: '按参考单位',
				                leaf: true
				            }]
				        })
   		});
   		var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel",width:180,readOnly:true,type:"1"});
		var kslc1	= new Ext.ux.Combox({width:170,id:"kslc_find1"});
		var xnxq1	= new Ext.ux.form.XnxqField({ width:180,id:"infxnxq_find1",readOnly:true,callback:function(){
						var id=Ext.getCmp("infxnxq_find1").getCode();//取得ComboBox0的选择值
						var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
						Ext.getCmp("kslc_find1").clearValue();  
                    	Ext.getCmp("kslc_find1").store=newStore;  
                        newStore.reload();
                        Ext.getCmp("kslc_find1").bindStore(newStore);
				}});
		var cx1 = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.jyjSelect,scope:this});
		var cz1 = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search1.getForm().findField("organ_sel").reset();this.search1.getForm().reset();},scope:this});
		this.search1 = new Ext.form.FormPanel({
			region: "north",
			height:130,
			id:"search_form5",
			items:[{  
				layout:'form',  
				xtype:'fieldset',  
				style:'margin:10 10',
				title:'查询条件',  
				items: [{
						xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 4
						}, 
						baseCls:"table",
						items:[
							{html:"年度：",baseCls:"label_right",width:120},
							{items:[xnxq1],baseCls:"component",width:180},
							{html:"考试名称：",baseCls:"label_right",width:120}, 
							{items:[kslc1],baseCls:"component",width:180},
							{html:"组织单位：",baseCls:"label_right",width:120},
							{items:[organ_sel],baseCls:"component",width:180},
							{html:"",baseCls:"label_right",width:120}, 
							{layout:"absolute", items:[cx1,cz1],baseCls:"component",width:180}
						] 
                    }]  
		       }]  
	    	})
	    		    	
	    var dataPanel=new Ext.Panel({
    		region:'center',
    		border:true,
   			id:"jyjdataPanel",
   			html:""
   		});
        var panel = new Ext.Panel({   
                id:"jyjPrintPanel",  
                border:true,
	    		title:"报名信息统计",
	    		region:"center",
	    		layout:"border",
	    		tbar:[ 
					  "->",{xtype:"button",text:"打印",iconCls:"p-icons-print",handler:this.printCheatStudent,scope:this}
				],
				items:[dataPanel]
            }); 
         return new Ext.Panel({
        	 layout:"border",
        	 items:[this.jyjOrgTree,{
        		 layout: 'border',
        		 region:'center',
        		 border: false,
        		 split:true,
        		 margins: '2 0 5 5',
        		 width: 275,
        		 minSize: 100,
        		 maxSize: 500,
        		 items: [this.search1,panel]
        	 }]
        });
	},
	createSchoolPanel:function(){
   		this.schoolOrgTree = new Ext.ux.TreePanel({region:"west",
			          	rootVisible:false,
			          	title:"报名信息统计",
			          	collapseMode : "mini",
			          	split:true,
			          	minSize: 120,
			          	width:200,
			          	maxSize: 300,
			          	autoScroll:true,
			          	root: new Ext.tree.AsyncTreeNode({
			          		expanded: true,
				            children: [{
				            	id:"school_1",
				                text: '报名详细情况',
				                leaf: true
				            }//, 
				            /*{
				            	id:"school_2",
				                text: '按年级',
				                leaf: true
				            }, 
				            {
				            	id:"school_3",
				                text: '按班级',
				                leaf: true
				            }*/]
				        })
   		});
		var organ_sel	= new Ext.ux.form.GradeClassField({name:"sup_organ_sel",id:"organ_njbj",width:180,type:"0"});
		var kslc1	= new Ext.ux.Combox({width:170,id:"kslc_find2"});
		var xnxq1	= new Ext.ux.form.XnxqField({ width:180,id:"infxnxq_find2",readOnly:true,callback:function(){						
						var id=Ext.getCmp("infxnxq_find2").getCode();//取得ComboBox0的选择值
						var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
						Ext.getCmp("kslc_find2").clearValue();  
                    	Ext.getCmp("kslc_find2").store=newStore;  
                        newStore.reload();
                        Ext.getCmp("kslc_find2").bindStore(newStore);
				}});
		var cx1 = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.schoolSelect,scope:this});
		var cz1 = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){Ext.getCmp("organ_njbj").reset();this.search2.getForm().reset();},scope:this});
		
		this.search2 = new top.Ext.form.FormPanel({
			region: "north",
			height:130,
			id:"search_form6",
			items:[{  
				layout:'form',  
				xtype:'fieldset',  
				style:'margin:10 10',
				title:'查询条件',  
				items: [{
                    	xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 4
						}, 
						baseCls:"table",
						items:[
							{html:"年度：",baseCls:"label_right",width:120},
							{items:[xnxq1],baseCls:"component",width:180},
							{html:"考试名称：",baseCls:"label_right",width:120},
							{items:[kslc1],baseCls:"component",width:180},
							{html:"科目：",baseCls:"label_right",width:120},
							{items:[organ_sel],baseCls:"component",width:180},
							{html:"",baseCls:"label_right",width:120},
							{layout:"absolute", items:[cx1,cz1],baseCls:"component",width:180}
						] 
                    }]  
		       }]  
	    	})
	    var dataPanel=new Ext.Panel({
    		region:'center',
    		border:true,
   			id:"dataPanelEIC",
   			html:""
   		});
        var panel = new Ext.Panel({   
                id:"schoolPrintPanel",  
                border:true,
	    		title:"报名信息统计",
	    		region:"center",
	    		layout:"border",
	    		tbar:[ 
					  "->",{xtype:"button",text:"打印",iconCls:"p-icons-print",handler:this.printCheatStudent,scope:this}
				],
				items:[dataPanel]
        });
        return new Ext.Panel({
        	layout:"border",
    		items:[this.schoolOrgTree,{
    			layout: 'border',
    			region:'center',
    			border: false,
    			split:true,
    			margins: '2 0 5 5',
    			width: 275,
    			minSize: 100,
    			maxSize: 500,
    			items: [this.search2,panel]
			}]
        });
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"fit",items:[this.tabPanel]});
    },
    initQueryDate:function(){
    	if(mBspInfo.getUserType() == "2") {
    		this.tabPanel.setActiveTab(this.schoolPanel);
    	}
    },
    jyjSelect:function(){
    	var node = this.jyjOrgTree.getSelectionModel().getSelectedNode();
    	if(node == null){
    		Ext.MessageBox.alert("消息","请选择左边树菜单统计类型！");
    		return;
    	}
    	var school = Ext.getCmp("search_form5").getForm().findField("organ_sel").getSchoolCode();
    	if(school == undefined){
    		school = "";
    	}
    	var xnxq = Ext.getCmp("infxnxq_find1").getValue();
    	var kslc = Ext.getCmp("kslc_find1").getValue();
    	if(kslc == ""){
    		Ext.MessageBox.alert("消息","请选择年度和考试名称！");
    		return;
    	}
		var height=Ext.getCmp('jyjdataPanel').getHeight()-4;
  		var iframe="<fieldset style='height:"+height+"; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+
						"' src='examInfomation_getExamInfoCount.do?type="+node.id+"&schools="+school+"&kslc="+kslc+
						"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('jyjdataPanel').innerHTML=iframe;
    },
    schoolSelect:function(){
    	var node = this.schoolOrgTree.getSelectionModel().getSelectedNode();
    	if(node == null){
    		Ext.MessageBox.alert("消息","请选择左边树菜单统计类型！");
    		return;
    	}
    	var njbj = Ext.getCmp("organ_njbj").getClassCode();
    	if(njbj == undefined){
    		njbj = "";
    	}
    	var xnxq = Ext.getCmp("infxnxq_find2").getValue();
    	var kslc = Ext.getCmp("kslc_find2").getValue();
    	if(kslc == ""){
    		Ext.MessageBox.alert("消息","请选择年度和考试名称！");
    		return;
    	}
		var height=Ext.getCmp('dataPanelEIC').getHeight()-4;
  		var iframe="<fieldset style='height:"+height+"; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+
						"' src='examInfomation_getExamInfoCount.do?type="+node.id+"&schools="+njbj+"&kslc="+kslc+
						"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanelEIC').innerHTML=iframe;
    },
    printCheatStudent:function(){
    	frmReport.print();    	
    }
});
