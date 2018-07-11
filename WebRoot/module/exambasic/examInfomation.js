var lcid = "";
Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
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
    	//单元格点击事件，控制按钮的禁用情况
    	var thiz = this;
    	this.maingrid.on("cellclick",function(grid,rowIndex,columnIndex, e){
    		var selected = thiz.maingrid.getStore().getAt(rowIndex);
		    lcid = selected.get("LCID");
    		if(columnIndex == 6){
    			var newStore = new Ext.data.JsonStore({
					autoLoad:false,
					url:'dropListAction_getKskmBykslc.do?params='+lcid,
					fields:["CODEID","CODENAME"]
    			});
    			Ext.getCmp("kspc_find").clearValue(); 
    			Ext.getCmp("kspc_find").store=newStore;
    			newStore.reload();
    			Ext.getCmp("kspc_find").bindStore(newStore);
    			
    			var newStore1 = new Ext.data.JsonStore({
					autoLoad:false,
					url:'dropListAction_kaoDianMc.do?params='+lcid,
					fields:["CODEID","CODENAME"]
				});
    			Ext.getCmp("kd_find").clearValue();  
				Ext.getCmp("kc_find").clearValue(); 
            	Ext.getCmp("kd_find").store=newStore1;  
                Ext.getCmp("kc_find").store.removeAll();
                newStore1.reload();
                Ext.getCmp("kd_find").bindStore(newStore1);
                
		    	thiz.tabPanel.setActiveTab(thiz.examInfoPanel);
  				thiz.examGrid.$load({"examinee.lcid":lcid});
    		}
		},this);
    },
   	initComponent :function(){   		
		this.mainPanel = this.createMainPanel();		
   		this.examInfoPanel = this.createExamInfoPanel();   		
   		this.examCheckPanel = this.createExamStudentCheckPanel();
   		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.mainPanel,this.examInfoPanel,this.examCheckPanel]   
        });
	},
	createMainPanel:function() {
    	//初始化搜索区
   		var xnxq	= new Ext.ux.form.XnxqField({ width:200,id:"infxnxq_find",readOnly:true});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.maingridExamStu,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.mainsearch.getForm().reset()},scope:this});
		
		this.mainsearch = new Ext.form.FormPanel({
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
							columns: 3
						}, 
						baseCls:"table",
						items:[
						       {html:"年度：",baseCls:"label_right",width:170},
						       {items:[xnxq],baseCls:"component",width:200},
						       {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
						      ] 
                    }]  
		       }]  
	    	})
		
		//初始化数据列表区
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
			sm,
			//{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
			//{header: "季度",   align:"center", sortable:true, dataIndex:"XQ"},
			{header: "年度",   align:"center", sortable:true, dataIndex:"XNXQ_ID"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME"},
			{header: "组织单位",   align:"center", sortable:true, dataIndex:"DWMC"},
			{header: "参考单位",   align:"center", sortable:true, dataIndex:"CKDW"},
			{header: "考生数量",   align:"center", sortable:true, dataIndex:"SL",
				renderer: function (data, metadata, record, rowIndex, columnIndex, store) {  
				      var xl = store.getAt(rowIndex).get('SL');
				      return "<a href='#'>"+xl+"</a>";  
				}
			},
			{header: "已验证",   align:"center", sortable:true, dataIndex:"YY"},
			{header: "未验证",   align:"center", sortable:true, dataIndex:"WY"},
			{header: "已登录",   align:"center", sortable:true, dataIndex:"YDL"},
			{header: "未登录",   align:"center", sortable:true, dataIndex:"WDL"},
			{header: "缺考",   align:"center", sortable:true, dataIndex:"QK"}
		];
		this.maingrid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:'考生数量统计',
			page:true,
			rowNumber:true,
			region:"center",
			action:"examInfomation_getListPage.do",
			fields :["LCID","XN","XQ","XNXQ_ID","EXAMNAME","DWMC","CKDW","SL","DWID","DWTYPE","YY","WY","YDL","WDL","QK"],
			border:false
		});
		return new Ext.Panel({layout:"border",region:"center",
		 	items:[this.mainsearch,this.maingrid]
		})
    },
    createExamInfoPanel:function(){
    	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
    	var cm = [sm,
    	          {header: "考生姓名",   align:"center", sortable:true, dataIndex:"XM",
    	           renderer: function (data, metadata, record, rowIndex, columnIndex, store) {  
    	        	   var xm = store.getAt(rowIndex).get('XM');  
    	        	   var ksid = store.getAt(rowIndex).get('KSID'); 
    	        	   var PARENTID = "000304";
    	        	   var MENUCODE = "00030401";
    	        	   if (Ext.get("ServerPath").getValue().split("jsp").length>1) {
    	        		   return "<a href='#' onclick='window.open(\""+Ext.get("ServerPath").getValue()+"/main.jsp?module=00030401&ksid="+ksid+"\")'>"+xm+"</a>";
    	        	   }else {
    	        		   return "<a href='#' onclick='window.open(\""+Ext.get("ServerPath").getValue()+"/jsp/main.jsp?module=00030401&ksid="+ksid+"\")'>"+xm+"</a>";
    	        	   }    	        	     
    	           }
			},
			{header: "考号",   align:"center", sortable:true, dataIndex:"KSCODE"},
			{header: "身份证件号",   align:"center", sortable:true, dataIndex:"SFZJH"},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
			{header: "参考单位",   align:"center", sortable:true, dataIndex:"XXMC"},
			{header: "考试批次",   align:"center", sortable:true, dataIndex:"KMMC"},
			{header: "考点名称",   align:"center", sortable:true, dataIndex:"KDMC"},
			{header: "考场名称",   align:"center", sortable:true, dataIndex:"KCMC"},
			{header: "座位号",   align:"center", sortable:true, dataIndex:"ZWH"},			
			{header: "是否验身份证",   align:"center", sortable:true, dataIndex:"ISKS"},
			{header: "是否登录",   align:"center", sortable:true, dataIndex:"ISDL"},
			{header: "提交状态",   align:"center", sortable:true, dataIndex:"ISSUBMIT"}
		];    	
		this.examGrid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			tbar:[ 
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}
			      ,"->",{xtype:"button",text:"考试记录代提交",iconCls:"p-icons-add",handler:this.submitJkCj,scope:this}
			      ,"->",{xtype:"button",text:"续考",iconCls:"p-icons-add",handler:this.submitXk,scope:this}
			     ],
			title:"考生信息列表",
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			excelTitle:"考生信息表",
			action:"examInfomation_getXsListPage.do",//teacher_getListPage.do
			fields :["KSID","LCID","KSCODE","EXAMNAME","XJH","XM","XXDM","XXMC","XXBSM","XJJYJ","XJCODE","SJJYJ","SJCODE",
			         "XNXQ_ID","XNXQ","XBM","XB","SFZJH","KMMC","KDMC","KCMC","ZWH","ISKS","ISDL","ISSUBMIT"],
			border:false
		});		
		//搜索区域
		var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel",width:210,readOnly:true});
    	var xmkhxjh_sel	= new Ext.ux.form.TextField({name:"xmkhxjh_sel",id:"xmkhxjh_sel",width:200});    	
    	var organ_lable = "参考单位：";    	
    	var kspc	= new Ext.ux.Combox({width:190,id:"kspc_find"});
    	var kc = new Ext.ux.Combox({width:190,id:"kc_find"});
		var kd = new Ext.ux.Combox({width:190,id:"kd_find",listeners:{
			"select":function(){
				var id=Ext.getCmp("kd_find").getValue();
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_examRoom.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kc_find").clearValue();  
            	Ext.getCmp("kc_find").store=newStore;  
                newStore.reload();
                Ext.getCmp("kc_find").bindStore(newStore);
			}
		}});
		var kshd = new Ext.Button({x:10,y:-10,cls:"base_btn",id:"checkStudent",text:"考生核对",handler:this.checkExamInfomation,scope:this});
		var cx = new Ext.Button({x:80,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamInfomation,scope:this});
		var cz = new Ext.Button({x:150,y:-10,cls:"base_btn",text:"重置",handler:function(){this.examSearch.getForm().reset()},scope:this});
		//var kssfz = new Ext.Button({x:220,y:-10,cls:"base_btn",id:"checkKssfz",text:"身份证验证",handler:this.checkSfz,width:90,scope:this});
		var kssfz = new Ext.Button({x:220,y:-10,cls:"base_btn",id:"checkKssfz",text:"修改身份证",handler:this.editsfz,width:90,scope:this});
		if(mBspInfo.getUserType() == "2") {
			this.examSearch = new Ext.form.FormPanel({
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
								columns: 5
							}, 
							baseCls:"table",
							items:[
							       //{html:organ_lable,baseCls:"label_right",width:70},
							       //{items:[organ_sel],baseCls:"component",width:210},
							       {html:"考试批次：",baseCls:"label_right",width:70},
							       {items:[kspc],baseCls:"component",width:210},
							       {html:"姓名/身份证号：",baseCls:"label_right",width:100},
							       {items:[xmkhxjh_sel],baseCls:"component",width:210},
							       {html:"",baseCls:"label_right",width:320}, 
							       {html:"考点：",baseCls:"label_right",width:70},
							       {items:[kd],baseCls:"component",width:210},
							       {html:"考场：",baseCls:"label_right",width:70},
							       {items:[kc],baseCls:"component",width:210},
							       {layout:"absolute", items:[kshd,cx,cz,kssfz],baseCls:"component_btn",width:320}
							      ] 
	                    }]  
			       }]  
		    });
		}else {
			this.examSearch = new Ext.form.FormPanel({
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
								columns: 6
							}, 
							baseCls:"table",
							items:[
							       {html:organ_lable,baseCls:"label_right",width:70},
							       {items:[organ_sel],baseCls:"component",width:210},
							       {html:"考试批次：",baseCls:"label_right",width:70},
							       {items:[kspc],baseCls:"component",width:210},
							       {html:"姓名/身份证号：",baseCls:"label_right",width:100},
							       {items:[xmkhxjh_sel],baseCls:"component",width:210},
							       {html:"考点：",baseCls:"label_right",width:70},
							       {items:[kd],baseCls:"component",width:210},
							       {html:"考场：",baseCls:"label_right",width:70},
							       {items:[kc],baseCls:"component",width:210},
							       {layout:"absolute", items:[kshd,cx,cz,kssfz],baseCls:"component_btn",width:260,colspan:2}
							      ] 
	                    }]  
			       }]  
		    });
		}		
	    return new Ext.Panel({layout:"border",region:"center",
		 	items:[this.examSearch,this.examGrid]
		})
    },
    createExamStudentCheckPanel:function(){
    	//搜索区域
   		var dataPanel=new Ext.Panel({
   			region:'center',
    		border:true,
   			id:"dataPanelEI",
   			html:"",
   			bodyStyle:"border:2px red solid"
   		});
		var print  = new Ext.Button({text:"打印",iconCls:"p-icons-print",handler:this.printExamInfo,scope:this});
		var fanhui = new Ext.Button({text:"返回",iconCls:"p-icons-unpassing",handler:this.returnExamInfo,scope:this});
		return new Ext.Panel({
				id:"MainExamRoomPanel",
	    		region:"north",
	    		tbar:[ 
	    		      "->",fanhui,
	    		      "->",print
			  	],
	    		height:600,
	    		width:"auto",
	    		layout:"border",
	    		border:true,
	    		items:[dataPanel]
	    });    	
    },
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel(this.tabPanel);
    },
    initQueryDate:function(){
    	var lcid = getLocationPram("lcid");
    	this.maingrid.$load({"lcid":lcid});
    },
    selectExamInfomation:function(){
    	var organ_sel = "";
    	var kspc_find = Ext.getCmp("kspc_find").getValue();
    	var kd_find = Ext.getCmp("kd_find").getValue();
    	var kc_find = Ext.getCmp("kc_find").getValue();    	
    	var xmkhxjh_sel = Ext.getCmp('xmkhxjh_sel').getValue();
    	if(mBspInfo.getUserType() != "2") {    	
    		organ_sel=this.examSearch.getForm().findField('organ_sel').getCodes();
    	}
    	this.examGrid.$load({"examinee.lcid":lcid,"schools":organ_sel,"kmid":kspc_find,"kdid":kd_find,"kcid":kc_find,"xmkhxjh":xmkhxjh_sel});    	
	},
	maingridExamStu:function(){
		var xnxq = Ext.getCmp('infxnxq_find').getCode();
  		this.maingrid.$load({"xnxqId":xnxq});
	},
	checkExamInfomation:function(){
		//核对考生信息
		var organ_sel = "";
		if(mBspInfo.getUserType() != "2") { 
			organ_sel = this.examSearch.getForm().findField('organ_sel').getCodes();
			if(organ_sel== "" || organ_sel==undefined){
				Ext.MessageBox.alert("消息","必须选择到参考单位！");
				return;
			}
		}		
		var xmkhxjh_sel = Ext.getCmp('xmkhxjh_sel').getValue();
		this.tabPanel.setActiveTab(this.examCheckPanel);
    	var iframe="<fieldset style='height:600; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReportP' name='frmReportP' width='100%'"+
						"height='600' src='examInfomation_getExamInfomationByBj.do?lcid="+lcid+"&xxid="+organ_sel+
						"&xmkhxjh="+xmkhxjh_sel+"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanelEI').innerHTML=iframe;    	
	},
	checkSfz:function(){
		var kspc_find = Ext.getCmp("kspc_find").getValue();
		var kd_find = Ext.getCmp("kd_find").getValue();
		var kc_find = Ext.getCmp("kc_find").getValue();
		var selected =  this.examGrid.getSelectionModel().getSelected();
		var delInfo = "";
		var param = "";
    	if(!selected){
    		if(kspc_find== "" || kspc_find==undefined){
    			Ext.MessageBox.alert("消息","必须选择到考试批次！");
    			return;
    		}    	
        	if(kd_find== "" || kd_find==undefined){
    			Ext.MessageBox.alert("消息","必须选择到考点！");
    			return;
    		}    	
        	if(kc_find== "" || kc_find==undefined){
    			Ext.MessageBox.alert("消息","必须选择到考场！");
    			return;
    		}
        	param = "pc="+kspc_find+"&kd="+kd_find+"&kc="+kc_find;
    	}else {
    		var selectedBuildings = this.examGrid.getSelectionModel().getSelections();
    		for(var i = 0; i < selectedBuildings.length; i++) {
        		delInfo += selectedBuildings[i].get("KSCODE")+";";    		
        	}
    		param = "lcid="+lcid+"&ks="+delInfo;
    	}    	    	    					
		window.open("index.htm?"+param);
	},
	submitJkCj:function(){
    	var selected =  this.examGrid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.examGrid.getSelectionModel().getSelections();
    	var thiz = this;
    	var delInfo = "";
    	for(var i = 0; i < selectedBuildings.length; i++) {
    		delInfo += selectedBuildings[i].get("KSCODE")+";";    		
    	} 
    	Ext.MessageBox.show({
    			title:"消息",
    			msg:"您确定要代提交成绩吗?",
    			buttons:Ext.MessageBox.OKCANCEL,
    			icon:Ext.MessageBox.QUESTION,    			
    			fn:function(b){
    				if(b=='ok'){
    					JH.$request({
    						timeout: 3600000,
    						params:{
    							"delInfo":delInfo,
    							"lcid":lcid   							
    						},
    						action:"examupdate_submitJkCj.do",
    						handler:function(){    							
    							thiz.examGrid.$load();
    						}
    					})    					
    				}
    			}
    	})
    },
    editsfz:function(){
    	var selected =  this.examGrid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.examGrid.getSelectionModel().getSelections();
    	if(selectedBuildings.length>1){
    		Ext.MessageBox.alert("消息","只能选择一条记录！");
    		return;
    	}
    	var thiz = this;
    	var delInfo = selectedBuildings[i].get("KSCODE");
    	Ext.MessageBox.show({
    			title:"消息",
    			msg:"您确定要把"+delInfo+"身份证改为："+sfzjh+"吗?",
    			buttons:Ext.MessageBox.OKCANCEL,
    			icon:Ext.MessageBox.QUESTION,    			
    			fn:function(b){
    				if(b=='ok'){
    					JH.$request({
    						timeout: 3600000,
    						params:{
    							"delInfo":delInfo,
    							"sfzjh":sfzjh   							
    						},
    						action:"exam_editsfzjh.do",
    						handler:function(){    							
    							thiz.examGrid.$load();
    						}
    					})    					
    				}
    			}
    	})
    },
    
    submitXk:function(){
    	var selected =  this.examGrid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.examGrid.getSelectionModel().getSelections();
    	var thiz = this;
    	var delInfo = "";
    	for(var i = 0; i < selectedBuildings.length; i++) {
    		delInfo += selectedBuildings[i].get("KSCODE")+";";    		
    	} 
    	Ext.MessageBox.show({
    			title:"消息",
    			msg:"您确定要续考吗?",
    			buttons:Ext.MessageBox.OKCANCEL,
    			icon:Ext.MessageBox.QUESTION,    			
    			fn:function(b){
    				if(b=='ok'){
    					JH.$request({
    						timeout: 3600000,
    						params:{
    							"delInfo":delInfo,
    							"lcid":lcid   							
    						},
    						action:"examupdate_submitXk.do",
    						handler:function(){    							
    							thiz.examGrid.$load();
    						}
    					})    					
    				}
    			}
    	})
    },
	fanhui:function(){
		this.tabPanel.setActiveTab(this.mainPanel);
  		this.maingrid.$load();
	},
	printExamInfo:function(){
		frmReportP.print();
	},
	returnExamInfo:function(){
		this.tabPanel.setActiveTab(this.examInfoPanel);
  		this.examGrid.$load({"examinee.lcid":lcid});
	}
});

function openPanel(PARENTID,MENUCODE){
	var tp = Ext.getCmp("treePanel_"+PARENTID); 
 	Ext.getCmp(tp.root.id).expand();
 	tp.selectPath("/"+PARENTID+"/"+MENUCODE,"",function(){
 		var node = tp.getSelectionModel().getSelectedNode(); 
 		mainobject.clickTree(node);
 	});
}
