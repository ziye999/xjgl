var isInit=false;
//查询类型，点查询按钮时，改变其值
var search_type = "";
Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
	    this.initDate();
		this.initComponent();
		this.initListener();
		this.initFace(); 
    },
    /** 初始化页面、内存等基本数据 **/
    initDate:function(){
    	this.initForm();
    },
  	initComponent :function(){
   		var root=new Ext.tree.TreeNode({
   			expanded: true,
   			id:'root',
   			text:'数据打印'
   		});
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'01',
   			text:'考试日程表'
   		}));
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'02',
   			text:'考点考场分布表'
   		}));
   		/*root.appendChild(new Ext.tree.TreeNode({
   			id:'03',
   			text:'考点监考表'
   		}));
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'04',
   			text:'考号考生信息对照表'
   		}));*/
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'05',
   			text:'考试门贴'
   		}));
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'06',
   			text:'考生签到表'
   		}));
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'07',
   			text:'准考证'
   		}));
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'08',
   			text:'座位贴条'
   		}));   		
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'09',
   			text:'考试情况登记表'
   		}));
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'10',
   			text:'考生信息更改表'
   		}));
   		
   		this.orgTree = new Ext.tree.TreePanel({
   						 region:"west",
			             rootVisible:true,
			             title:"数据打印",
						 collapseMode : "mini",
			             split:true,
			             minSize: 120,
			             width:200,
			             maxSize: 300,
			             autoScroll:true,
			             root:root
   		});
   		this.dataPanel=new Ext.Panel({
    		region:'center',
    		border:false,
   			id:"dataPanel"
   		});
   		this.sidePanel = new Ext.Panel({
   			region:'center',
    		border:'border',
    		id:'sidePanel',
    		height:488,
    		style:'background-color:#B2E0F9',
   			tbar:[ "->",
   			      {id:"preview", xtype:"button",text:"预览",iconCls:"p-icons-print",handler:this.perviewExamCard,scope:this},
				  {xtype:"button",text:"打印",iconCls:"p-icons-print",handler:this.dayinData,scope:this}
				  ],
			items:[this.dataPanel]
   		});
   		this.tabPanel = new Ext.TabPanel({   
   			activeTab: 0,
   			headerStyle: 'display:none',
   			border:false, 
   			items: [this.search01,this.search02,this.search05,this.search06,this.search07,this.search08,this.search09,this.search10]   
   		}); 
		this.grid = new Ext.Panel({
			id:"gridid",
			region:"center",
			border:true,
			height:600,
			items:[this.tabPanel,this.sidePanel]
		}); 
		Ext.getCmp('preview').hide();
	}, 
	/** 对组件设置监听 **/
    initListener:function(){
    	var thiz = this;
    	this.orgTree.on("click",function(node){      		
    		var panel = Ext.getCmp("search_form"+node.id);    		
    		if(panel != undefined){ 
	      		thiz.tabPanel.setActiveTab(panel); 
	      		panel.getForm().reset(); 
	      		Ext.getDom('dataPanel').innerHTML="";	      		
    		}
    		if (node.id=="08") {
    			thiz.selectExamSeatInfo();
    		}
    		if (node.id=="07") {
    			Ext.getCmp('preview').show();
    		} else {
    			Ext.getCmp('preview').hide();
    		}
    	},this.grid);
    },
    /** 初始化界面 **/
    initFace:function(){ 
    	this.addPanel({id:"panel",layout:"border",
    		items:[this.orgTree,{
    			layout: 'border',
    			region:'center',
    			border: false,
    			split:true,
    			margins: '2 0 5 5',
    			width: 275,
    			minSize: 100,
    			maxSize: 500,
    			items: [this.grid]
    		}]
    	});
    },    
    initForm:function(){ 
		//搜索区域 		
		//考试日程表
		this.organ1 = new Ext.ux.form.OrganField({name:"organ_sel1",width:210,readOnly:true});
		var nj1		= new Ext.ux.Combox({dropAction:"grade",width:200,id:"nj_find01"});
		var kd1 = new Ext.ux.Combox({width:200,id:"kd_find1"});
		var kslc1	= new Ext.ux.Combox({width:200,id:"kslc_find1",listeners:{
			"select":function(){
				var id=Ext.getCmp("kslc_find1").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_kaoDianMc.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kd_find1").clearValue();   
            	Ext.getCmp("kd_find1").store=newStore;   
                newStore.reload();
                Ext.getCmp("kd_find1").bindStore(newStore);
			}
		}});		
		var xnxq1	= new Ext.ux.form.XnxqField({ width:210,id:"xnxq_find1",readOnly:true,callback:function(){
						var id=Ext.getCmp("xnxq_find1").getCode();//取得ComboBox0的选择值
						var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
						Ext.getCmp("kslc_find1").clearValue(); 
						Ext.getCmp("kd_find1").clearValue(); 
                    	Ext.getCmp("kslc_find1").store=newStore;
                    	Ext.getCmp("kd_find1").store.removeAll();
                        newStore.reload();
                        Ext.getCmp("kslc_find1").bindStore(newStore);
				}});
		var cx1 = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectKSRCSubject,scope:this});
		var cz1 = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search01.getForm().reset()},scope:this});
		
		if(mBspInfo.getUserType() == "2") {
			this.search01 = new Ext.form.FormPanel({
    			id:"search_form01",
    			region: "north",
    			height:90,
    			border:false,
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
    							{html:"年度：",baseCls:"label_right",width:120},
    							{items:[xnxq1],baseCls:"component",width:210},
    							{html:"考试名称：",baseCls:"label_right",width:120},
    							{items:[kslc1],baseCls:"component",width:210},
    							{items:[cx1],baseCls:"component_btn",width:120},
    							{html:"考点：",baseCls:"label_right",width:120}, 
    							{items:[kd1],baseCls:"component",width:210},
    							{html:"",baseCls:"label_right",width:120},   
    							{html:"",baseCls:"component",width:210},
    							{items:[cz1],baseCls:"component_btn",width:120}
    						] 
                      	}]  
    		       }]  
    	    });
    	}else{
    		this.search01 = new Ext.form.FormPanel({
    			id:"search_form01",
    			region: "north",
    			height:120,
    			border:false,
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
    							{html:"年度：",baseCls:"label_right",width:120},
    							{items:[xnxq1],baseCls:"component",width:210},
    							{html:"考试名称：",baseCls:"label_right",width:120},
    							{items:[kslc1],baseCls:"component",width:210},
    							{items:[cx1],baseCls:"component_btn",width:120},
    							{html:"组织单位：",baseCls:"label_right",width:120}, 
    							{items:[this.organ1],baseCls:"component",width:210},
    							{html:"考点：",baseCls:"label_right",width:120}, 
    							{items:[kd1],baseCls:"component",width:210}, 
    							{items:[cz1],baseCls:"component_btn",width:120}
    						] 
                      	}]  
    		       }]  
    	    });
    	}		 
		 
		//考点考场分布 
		var kd2 = new Ext.ux.Combox({width:200,id:"kd_find2"});
		var kslc2	= new Ext.ux.Combox({width:200,id:"kslc_find2",listeners:{
			"select":function(){
				var id=Ext.getCmp("kslc_find2").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_kaoDianMc.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kd_find2").clearValue();   
            	Ext.getCmp("kd_find2").store=newStore;   
                newStore.reload();
                Ext.getCmp("kd_find2").bindStore(newStore);
			}
		}});
		var xnxq2	= new Ext.ux.form.XnxqField({ width:210,id:"xnxq_find2",readOnly:true,callback:function(){
						var id=Ext.getCmp("xnxq_find2").getCode();//取得ComboBox0的选择值 
						var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
						Ext.getCmp("kslc_find2").clearValue(); 
						Ext.getCmp("kd_find2").clearValue();   
                    	Ext.getCmp("kslc_find2").store=newStore;
                    	Ext.getCmp("kd_find2").store.removeAll(); 
                        newStore.reload();
                        Ext.getCmp("kslc_find2").bindStore(newStore);
				}});
		var cx2 = new Ext.Button({cls:"base_btn",text:"查询",handler:this.selectKDKCSubject,scope:this});
		var cz2 = new Ext.Button({cls:"base_btn",text:"重置",handler:function(){this.search02.getForm().reset()},scope:this});
 
		//考点监考
		/*var kd3 = new Ext.ux.Combox({width:200,id:"kd_find3"});
		var kslc3	= new Ext.ux.Combox({width:200,id:"kslc_find3",listeners:{
			"select":function(){
				var id=Ext.getCmp("kslc_find3").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_kaoDianMc.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kd_find3").clearValue();   
            	Ext.getCmp("kd_find3").store=newStore;   
                newStore.reload();
                Ext.getCmp("kd_find3").bindStore(newStore);
			}
		}});
		var xnxq3	= new Ext.ux.form.XnxqField({ width:210,id:"xnxq_find3",readOnly:true,callback:function(){
						var id=Ext.getCmp("xnxq_find3").getCode();//取得ComboBox0的选择值
						var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
						Ext.getCmp("kslc_find3").clearValue(); 
						Ext.getCmp("kd_find3").clearValue();   
                    	Ext.getCmp("kslc_find3").store=newStore;
                    	Ext.getCmp("kd_find3").store.removeAll(); 
                        newStore.reload();
                        Ext.getCmp("kslc_find3").bindStore(newStore);
				}});
		var cx3 = new Ext.Button({cls:"base_btn",text:"查询",handler:this.selectKDJKSubject,scope:this});
		var cz3 = new Ext.Button({cls:"base_btn",text:"重置",handler:function(){this.search03.getForm().reset()},scope:this});
		 
		//考号考生信息对照
		this.organ4 = new Ext.ux.form.OrganField({name:"organ_sel4",width:210,readOnly:true,type:"1"}); 
		var kspc4	= new Ext.ux.Combox({width:200,id:"kspc_find4"});
		var kslc4	= new Ext.ux.Combox({width:200,id:"kslc_find4",listeners:{
			"select":function(){
				var id=Ext.getCmp("kslc_find4").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_getKskmBykslc.do?params='+id,
								fields:["CODEID","CODENAME"]
				});
				Ext.getCmp("kspc_find4").clearValue(); 
				Ext.getCmp("kspc_find4").store=newStore;
				newStore.reload();
				Ext.getCmp("kspc_find4").bindStore(newStore);
			}
		}});
		var xnxq4	= new Ext.ux.form.XnxqField({ width:210,id:"xnxq_find4",readOnly:true,callback:function(){
						var id=Ext.getCmp("xnxq_find4").getCode();//取得ComboBox0的选择值
						var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
						Ext.getCmp("kslc_find4").clearValue(); 
                    	Ext.getCmp("kslc_find4").store=newStore;
                        newStore.reload();
                        Ext.getCmp("kslc_find4").bindStore(newStore);
				}});
		
				
		var selecttype1 = new Ext.form.Radio({ x:2,region:"west", 
			name:'selecttype',    
			boxLabel:'按考场' , value: '1',checked:true
		}); 
	   	var selecttype2 =new Ext.form.Radio({  x:60,region:"east",
	   		name:'selecttype',  value: '2',  
	   		boxLabel:'按科目' 
		});
		var cx4 = new Ext.Button({x:10,y:-1,cls:"base_btn",text:"查询",handler:this.selectKHDZSubject,scope:this});
		var cz4 = new Ext.Button({x:80,y:-1,cls:"base_btn",text:"重置",handler:function(){this.search04.getForm().reset()},scope:this});*/
		 
		//考试门签
		var kc5 = new Ext.ux.Combox({width:190,id:"kc_find5"});
		var kd5 = new Ext.ux.Combox({width:190,id:"kd_find5",listeners:{
			"select":function(){
				var id=Ext.getCmp("kd_find5").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_examRoom.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kc_find5").clearValue();  
            	Ext.getCmp("kc_find5").store=newStore;  
                newStore.reload();
                Ext.getCmp("kc_find5").bindStore(newStore);
			}
		}});
		var kspc5	= new Ext.ux.Combox({width:190,id:"kspc_find5"});
		var kslc5	= new Ext.ux.Combox({width:190,id:"kslc_find5",listeners:{
			"select":function(){
				var id=Ext.getCmp("kslc_find5").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_kaoDianMc.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kd_find5").clearValue();  
				Ext.getCmp("kc_find5").clearValue(); 
            	Ext.getCmp("kd_find5").store=newStore;  
                Ext.getCmp("kc_find5").store.removeAll();
                newStore.reload();
                Ext.getCmp("kd_find5").bindStore(newStore);
                
                var newStore1 = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_getKskmBykslc.do?params='+id,
								fields:["CODEID","CODENAME"]
                });
                Ext.getCmp("kspc_find5").clearValue(); 
                Ext.getCmp("kspc_find5").store=newStore1;
                newStore1.reload();
                Ext.getCmp("kspc_find5").bindStore(newStore1);
			}
		}});
		var xnxq5	= new Ext.ux.form.XnxqField({width:190,id:"xnxq_find5",readOnly:true,callback:function(){
				var id=Ext.getCmp("xnxq_find5").getCode();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
				Ext.getCmp("kslc_find5").clearValue(); 
				Ext.getCmp("kd_find5").clearValue();  
				Ext.getCmp("kc_find5").clearValue(); 
                Ext.getCmp("kslc_find5").store=newStore;
                Ext.getCmp("kd_find5").store.removeAll();
                Ext.getCmp("kc_find5").store.removeAll();
                newStore.reload();
                Ext.getCmp("kslc_find5").bindStore(newStore);
				}
		});
		var cx5 = new Ext.Button({x:10,y:-1,cls:"base_btn",text:"查询",handler:this.selectKSMQSubject,scope:this});
		var cz5 = new Ext.Button({x:80,y:-1,cls:"base_btn",text:"重置",handler:function(){this.search05.getForm().reset()},scope:this});
	
		//考生基本信息对照表查询区控件
		var kc6 = new Ext.ux.Combox({width:190,id:"kc_find6"});
		var kd6 = new Ext.ux.Combox({width:190,id:"kd_find6",listeners:{
			"select":function(){
				var id=Ext.getCmp("kd_find6").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_examRoom.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kc_find6").clearValue();  
            	Ext.getCmp("kc_find6").store=newStore;  
                newStore.reload();
                Ext.getCmp("kc_find6").bindStore(newStore);
			}
		}});
		var kspc6	= new Ext.ux.Combox({width:190,id:"kspc_find6"});
		var kslc6	= new Ext.ux.Combox({width:190,id:"kslc_find6",listeners:{
			"select":function(){
				var id=Ext.getCmp("kslc_find6").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_kaoDianMc.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kd_find6").clearValue();  
				Ext.getCmp("kc_find6").clearValue(); 
            	Ext.getCmp("kd_find6").store=newStore;  
                Ext.getCmp("kc_find6").store.removeAll();
                newStore.reload();
                Ext.getCmp("kd_find6").bindStore(newStore);
                
                var newStore1 = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_getKskmBykslc.do?params='+id,
								fields:["CODEID","CODENAME"]
                });
                Ext.getCmp("kspc_find6").clearValue(); 
                Ext.getCmp("kspc_find6").store=newStore1;
                newStore1.reload();
                Ext.getCmp("kspc_find6").bindStore(newStore1);
			}
		}});
		var xnxq6	= new Ext.ux.form.XnxqField({width:190,id:"xnxq_find6",readOnly:true,callback:function(){
				var id=Ext.getCmp("xnxq_find6").getCode();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
				Ext.getCmp("kslc_find6").clearValue(); 
				Ext.getCmp("kd_find6").clearValue();  
				Ext.getCmp("kc_find6").clearValue(); 
                Ext.getCmp("kslc_find6").store=newStore;
                Ext.getCmp("kd_find6").store.removeAll();
                Ext.getCmp("kc_find6").store.removeAll();
                newStore.reload();
                Ext.getCmp("kslc_find6").bindStore(newStore);
			}
		});
		var cx6 = new Ext.Button({x:10,y:-1,cls:"base_btn",text:"查询",handler:this.selectExamBasicInfo,scope:this});
		var cz6 = new Ext.Button({x:80,y:-1,cls:"base_btn",text:"重置",handler:function(){this.search06.getForm().reset()},scope:this});
		
		//打印准考证
		//var njbj7	= new Ext.ux.form.GradeClassField({name:"sup_organ_sel",width:210,id:"organ_njbj7",type:"0"});enableKeyEvents:true,
		var sfzjh = new Ext.form.TextField({fieldLabel:"身份证件号",name:"sfzjh",id:"sfzjh",maxLength:200,width:180});	
		/*listeners:{ 
			keypress:function(nf, newv, oldv) {
				window.open("index.htm");					
			}
		}*/
		var xuexiao_find= new Ext.ux.form.OrganField({name:"sup_organ_sel",width:190,readOnly:true});
		this.organ7 = new Ext.ux.form.OrganField({name:"organ_sel7",width:180,readOnly:true,type:"1",
			callback:function(){
				Ext.getCmp('sfzjh').setValue('');
				var school = this.getSchoolCode();
				Ext.getCmp('treefield_organ_njbj7').loader.dataUrl= "dropListAction_getGradeClassTree.do?type=0&schoolCode="+school;
				Ext.getCmp('treefield_organ_njbj7').root.reload();
				Ext.getCmp("window_organ_njbj7").show();
				Ext.getCmp("window_organ_njbj7").hide();
			}
		});
		var kc7 = new Ext.ux.Combox({width:180,id:"kc_find7"});
		var kd7 = new Ext.ux.Combox({width:180,id:"kd_find7",listeners:{
			"select":function(){
				var id=Ext.getCmp("kd_find7").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_examRoom.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kc_find7").clearValue();  
            	Ext.getCmp("kc_find7").store=newStore;  
                newStore.reload();
                Ext.getCmp("kc_find7").bindStore(newStore);
			}
		}});
		var kspc7	= new Ext.ux.Combox({width:180,id:"kspc_find7"});
		var kslc7	= new Ext.ux.Combox({width:180,id:"kslc_find7",listeners:{
			"select":function(){
				var id=Ext.getCmp("kslc_find7").getValue();
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_getKskmBykslc.do?params='+id,
								fields:["CODEID","CODENAME"]
                });
                Ext.getCmp("kspc_find7").clearValue(); 
                Ext.getCmp("kspc_find7").store=newStore;
                newStore.reload();
                Ext.getCmp("kspc_find7").bindStore(newStore);
                
                var newStore1 = new Ext.data.JsonStore({
					autoLoad:false,
					url:'dropListAction_kaoDianMc.do?params='+id,
					fields:["CODEID","CODENAME"]
				});
                Ext.getCmp("kd_find7").clearValue();  
                Ext.getCmp("kc_find7").clearValue(); 
                Ext.getCmp("kd_find7").store=newStore1;  
                Ext.getCmp("kc_find7").store.removeAll();
                newStore1.reload();
                Ext.getCmp("kd_find7").bindStore(newStore1);
			}
		}});
		var xnxq7	= new Ext.ux.form.XnxqField({width:180,id:"xnxq_find7",readOnly:true,callback:function(){
				var id=Ext.getCmp("xnxq_find7").getCode();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
				Ext.getCmp("kslc_find7").clearValue(); 
                Ext.getCmp("kslc_find7").store=newStore;
                newStore.reload();
                Ext.getCmp("kslc_find7").bindStore(newStore);
			}
		});
		var cx7 = new Ext.Button({x:10,y:-1,cls:"base_btn",text:"查询",handler:this.selectExamCard,scope:this,id:"searchZk"});
		var cz7 = new Ext.Button({x:80,y:-1,cls:"base_btn",text:"导出PDF",handler:this.exportPdf,scope:this});
		var cc7 = new Ext.Button({x:150,y:-1,cls:"base_btn",text:"导出xls",handler:this.exportXls,scope:this});
		
		//座位贴条查询区控件
		/*var kc8 = new Ext.ux.Combox({width:190,id:"kc_find8"});
		var kd8 = new Ext.ux.Combox({width:190,id:"kd_find8",listeners:{
			"select":function(){
				var id=Ext.getCmp("kd_find8").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_examRoom.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kc_find8").clearValue();  
            	Ext.getCmp("kc_find8").store=newStore;  
                newStore.reload();
                Ext.getCmp("kc_find8").bindStore(newStore);
			}
		}});
		var kspc8	= new Ext.ux.Combox({width:190,id:"kspc_find8"});
		var kslc8	= new Ext.ux.Combox({width:190,id:"kslc_find8",listeners:{
			"select":function(){
				var id=Ext.getCmp("kslc_find8").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_kaoDianMc.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kd_find8").clearValue();  
				Ext.getCmp("kc_find8").clearValue(); 
            	Ext.getCmp("kd_find8").store=newStore;  
                Ext.getCmp("kc_find8").store.removeAll();
                newStore.reload();
                Ext.getCmp("kd_find8").bindStore(newStore);
                
                var newStore1 = new Ext.data.JsonStore({
					autoLoad:false,
					url:'dropListAction_getKskmBykslc.do?params='+id,
					fields:["CODEID","CODENAME"]
                });
                Ext.getCmp("kspc_find8").clearValue(); 
                Ext.getCmp("kspc_find8").store=newStore1;
                newStore1.reload();
                Ext.getCmp("kspc_find8").bindStore(newStore1);
			}
		}});
		var xnxq8	= new Ext.ux.form.XnxqField({width:190,id:"xnxq_find8",readOnly:true,callback:function(){
				var id=Ext.getCmp("xnxq_find8").getCode();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
				Ext.getCmp("kslc_find8").clearValue(); 
				Ext.getCmp("kd_find8").clearValue();  
				Ext.getCmp("kc_find8").clearValue(); 
                Ext.getCmp("kslc_find8").store=newStore;
                Ext.getCmp("kd_find8").store.removeAll();
                Ext.getCmp("kc_find8").store.removeAll();
                newStore.reload();
                Ext.getCmp("kslc_find8").bindStore(newStore);
			}
		});
		var photoGroup = new Ext.form.CheckboxGroup({
				x:150,y:-10,
				width:100,
                id:'photoGroup',     
                name: 'model_type', 
                style: 'margin:0 10',
                columns: 1,       
                items: [     
                    {boxLabel: '显示照片', name: '1', checked: true}   
                ]     
            });     
		var cx8 = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamSeatInfo,scope:this});
		var cz8 = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search08.getForm().reset()},scope:this});*/
		var dy8 = new Ext.Button({x:157,y:-10,x:130,cls:"base_btn",text:"打印",handler:this.dayinData,scope:this});
		
		var kc9 = new Ext.ux.Combox({width:190,id:"kc_find9"});
		var kd9 = new Ext.ux.Combox({width:190,id:"kd_find9",listeners:{
			"select":function(){
				var id=Ext.getCmp("kd_find9").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_examRoom.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kc_find9").clearValue();  
            	Ext.getCmp("kc_find9").store=newStore;  
                newStore.reload();
                Ext.getCmp("kc_find9").bindStore(newStore);
			}
		}});
		var kspc9	= new Ext.ux.Combox({width:190,id:"kspc_find9"});
		var kslc9	= new Ext.ux.Combox({width:190,id:"kslc_find9",listeners:{
			"select":function(){
				var id=Ext.getCmp("kslc_find9").getValue();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_kaoDianMc.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kd_find9").clearValue();  
				Ext.getCmp("kc_find9").clearValue(); 
            	Ext.getCmp("kd_find9").store=newStore;  
                Ext.getCmp("kc_find9").store.removeAll();
                newStore.reload();
                Ext.getCmp("kd_find9").bindStore(newStore);
                
                var newStore1 = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_getKskmBykslc.do?params='+id,
								fields:["CODEID","CODENAME"]
                });
                Ext.getCmp("kspc_find9").clearValue(); 
                Ext.getCmp("kspc_find9").store=newStore1;
                newStore1.reload();
                Ext.getCmp("kspc_find9").bindStore(newStore1);
			}
		}});
		var xnxq9	= new Ext.ux.form.XnxqField({width:190,id:"xnxq_find9",readOnly:true,callback:function(){
				var id=Ext.getCmp("xnxq_find9").getCode();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
				Ext.getCmp("kslc_find9").clearValue(); 
				Ext.getCmp("kd_find9").clearValue();  
				Ext.getCmp("kc_find9").clearValue(); 
                Ext.getCmp("kslc_find9").store=newStore;
                Ext.getCmp("kd_find9").store.removeAll();
                Ext.getCmp("kc_find9").store.removeAll();
                newStore.reload();
                Ext.getCmp("kslc_find9").bindStore(newStore);
			}
		});
		var cx9 = new Ext.Button({x:10,y:-1,cls:"base_btn",text:"查询",handler:this.selectExamDjInfo,scope:this});
		var cz9 = new Ext.Button({x:80,y:-1,cls:"base_btn",text:"重置",handler:function(){this.search09.getForm().reset()},scope:this});
		
		var kd10 = new Ext.ux.Combox({width:190,id:"kd_find10"});
		var kspc10 = new Ext.ux.Combox({width:190,id:"kspc_find10"});
		var kslc10 = new Ext.ux.Combox({width:190,id:"kslc_find10",listeners:{
			"select":function(){
				var id=Ext.getCmp("kslc_find10").getValue();
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_kaoDianMc.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kd_find10").clearValue();  
				Ext.getCmp("kd_find10").store=newStore;                  
                newStore.reload();
                Ext.getCmp("kd_find10").bindStore(newStore);
                
                var newStore1 = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_getKskmBykslc.do?params='+id,
								fields:["CODEID","CODENAME"]
                });
                Ext.getCmp("kspc_find10").clearValue(); 
                Ext.getCmp("kspc_find10").store=newStore1;
                newStore1.reload();
                Ext.getCmp("kspc_find10").bindStore(newStore1);
			}
		}});
		var xnxq10 = new Ext.ux.form.XnxqField({width:190,id:"xnxq_find10",readOnly:true,callback:function(){
				var id=Ext.getCmp("xnxq_find10").getCode();
				var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_ExamRoundByXnxq.do?params='+id,
										fields:["CODEID","CODENAME"]
									});
				Ext.getCmp("kslc_find10").clearValue(); 
				Ext.getCmp("kd_find10").clearValue();  
				Ext.getCmp("kslc_find10").store=newStore;
                Ext.getCmp("kd_find10").store.removeAll();
                newStore.reload();
                Ext.getCmp("kslc_find10").bindStore(newStore);
			}
		});
		var cx10 = new Ext.Button({x:10,y:-1,cls:"base_btn",text:"查询",handler:this.selectExamKsInfo,scope:this});
		var cz10 = new Ext.Button({x:80,y:-1,cls:"base_btn",text:"重置",handler:function(){this.search10.getForm().reset()},scope:this});
		
		this.search02 = new Ext.form.FormPanel({
			id:"search_form02",
			region: "north",
			height:120,
			border:false,
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
					       {html:"年度：",baseCls:"label_right",width:120},
					       {items:[xnxq2],baseCls:"component",width:210},
					       {html:"考试名称：",baseCls:"label_right",width:120},
					       {items:[kslc2],baseCls:"component",width:210},
					       {items:[cx2],baseCls:"component_btn",width:120},
					       {html:"考点：",baseCls:"label_right",width:120}, 
					       {items:[kd2],baseCls:"component",width:210},
					       {html:"",baseCls:"label_right",width:120},   
					       {html:"",baseCls:"component",width:210}, 	
					       {items:[cz2],baseCls:"component_btn",width:120}
					      ] 
				}]  
			}]  
	    });
		
		/*this.search03 = new Ext.form.FormPanel({
			id:"search_form03",
			region: "north",
			height:120,
			border:false,
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
					       {html:"年度：",baseCls:"label_right",width:120},
					       {items:[xnxq3],baseCls:"component",width:210},
					       {html:"考试名称：",baseCls:"label_right",width:120},
					       {items:[kslc3],baseCls:"component",width:210},
					       {items:[cx3],baseCls:"component_btn",width:120},
					       {html:"考点：",baseCls:"label_right",width:120}, 
					       {items:[kd3],baseCls:"component",width:210},
					       {html:"",baseCls:"label_right",width:120},   
					       {html:"",baseCls:"component",width:210}, 	
					       {items:[cz3],baseCls:"component_btn",width:120}	
					      ] 
				}]  
			}]  
	    }); 
		
		this.search04 = new Ext.form.FormPanel({
			id:"search_form04",
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
					       {html:"年度：",baseCls:"label_right",width:80},
					       {items:[xnxq4],baseCls:"component",width:210},
					       {html:"考试名称：",baseCls:"label_right",width:80},
					       {items:[kslc4],baseCls:"component",width:210},
					       {layout:"absolute",items:[selecttype1,selecttype2],baseCls:"component",width:160,height:20},
					       {html:"考试批次：",baseCls:"label_right",width:80},
					       {items:[kspc4],baseCls:"component",width:210},
					       {html:"参考单位：",baseCls:"label_right",width:80},  
					       {items:[this.organ4],baseCls:"component",width:210},
					       {layout:"absolute",items:[cx4,cz4],baseCls:"component_btn",width:160,height:20}  					       
					      ] 
				}]  
			}]  
	    });*/
		
		this.search05 = new Ext.form.FormPanel({
			id:"search_form05",
			region: "north",
			height:120,
			border:false,
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
					       {html:"年度：",baseCls:"label_right",width:50},
					       {items:[xnxq5],baseCls:"component",width:200},
					       {html:"考试名称：",baseCls:"label_right",width:70},
					       {items:[kslc5],baseCls:"component",width:200},
					       {html:"考试批次：",baseCls:"label_right",width:70},
					       {items:[kspc5],baseCls:"component",width:200},
					       {html:"考点：",baseCls:"label_right",width:50}, 
					       {items:[kd5],baseCls:"component",width:200},
					       {html:"考场：",baseCls:"label_right",width:70}, 
					       {items:[kc5],baseCls:"component",width:200},	
					       {layout:"absolute",items:[cx5,cz5],baseCls:"component_btn",width:160,height:20,colspan:2}
					      ] 
				}]  
			}]  
		}); 
	    
	    //考生签到表搜索区
	    this.search06 = new Ext.form.FormPanel({
	    	id:"search_form06",
	    	region: "north",
	    	height:120,
	    	border:false,
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
                           {html:"年度：",baseCls:"label_right",width:45},
                           {items:[xnxq6],baseCls:"component",width:200},
                           {html:"考试名称：",baseCls:"label_right",width:70},
                           {items:[kslc6],baseCls:"component",width:200},
                           {html:"考试批次：",baseCls:"label_right",width:70},
					       {items:[kspc6],baseCls:"component",width:200},
                           {html:"考点：",baseCls:"label_right",width:45}, 
                           {items:[kd6],baseCls:"component",width:200},
                           {html:"考场：",baseCls:"label_right",width:70}, 
                           {items:[kc6],baseCls:"component",width:200},	                           
                           {layout:"absolute",items:[cx6,cz6],baseCls:"component_btn",width:160,height:20,colspan:2}
                          ] 
	    		}]  
	    	}]  
	    });  
	    	
	    //打印准考证搜索区
	   	this.search07 = new Ext.form.FormPanel({
	   		id:"search_form07",
	   		region: "north",
	   		height:150,
	   		border:false,
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
					       {html:"年度：",baseCls:"label_right",width:70},
					       {items:[xnxq7],baseCls:"component",width:190},
					       {html:"考试名称：",baseCls:"label_right",width:70},
					       {items:[kslc7],baseCls:"component",width:190},
					       {html:"所属单位：",baseCls:"label_right",width:70}, 
					       {items:[xuexiao_find],baseCls : "component",width:190},					       
					     
					       {html:"考试批次：",baseCls:"label_right",width:70},
					       {items:[kspc7],baseCls:"component",width:190},
					       {html:"考点：",baseCls:"label_right",width:70}, 
					       {items:[kd7],baseCls:"component",width:190},
					       {html:"考场：",baseCls:"label_right",width:70}, 
					       {items:[kc7],baseCls:"component",width:190},
					       //{html:"身份证号：",baseCls:"label_right",width:70}, 
					       //{items:[sfzjh],baseCls:"component",width:190},
					       {layout:"absolute",items:[cx7,cz7,cc7],baseCls:"component_btn",width:180,height:20,colspan:6}
					     
						] 
	   			}]  
	   		}]  
	    }); 
	    	
	   	//座位贴条搜索区
	    this.search08 = new Ext.form.FormPanel({
	    	id:"search_form08",
	    	region: "north",
	    	height:0,
	    	border:false
	    	/*items:[{120  
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
	    			       {html:"年度：",baseCls:"label_right",width:45},
	    			       {items:[xnxq8],baseCls:"component",width:200},
	    			       {html:"考试名称：",baseCls:"label_right",width:70},
	    			       {items:[kslc8],baseCls:"component",width:200},
	    			       {html:"考试批次：",baseCls:"label_right",width:70},
					       {items:[kspc8],baseCls:"component",width:200},
	    			       {html:"考点：",baseCls:"label_right",width:45}, 
	    			       {items:[kd8],baseCls:"component",width:200},
	    			       {html:"考场：",baseCls:"label_right",width:70}, 
	    			       {items:[kc8],baseCls:"component",width:200},	
	    			       {layout:"absolute",items:[cx8,cz8,photoGroup],baseCls:"component_btn",width:200,colspan:2}
	    			      ] 
	    		}] 
	    	}]*/  
	    }); 
	    
	    this.search09 = new Ext.form.FormPanel({
	    	id:"search_form09",
	    	region: "north",
	    	height:120,
	    	border:false,
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
                           {html:"年度：",baseCls:"label_right",width:45},
                           {items:[xnxq9],baseCls:"component",width:200},
                           {html:"考试名称：",baseCls:"label_right",width:70},
                           {items:[kslc9],baseCls:"component",width:200},
                           {html:"考试批次：",baseCls:"label_right",width:70},
					       {items:[kspc9],baseCls:"component",width:200},
                           {html:"考点：",baseCls:"label_right",width:45}, 
                           {items:[kd9],baseCls:"component",width:200},
                           {html:"考场：",baseCls:"label_right",width:70}, 
                           {items:[kc9],baseCls:"component",width:200},	                           
                           {layout:"absolute",items:[cx9,cz9],baseCls:"component_btn",width:160,height:20,colspan:2}
                          ] 
	    		}]  
	    	}]  
	    });
	    
	    this.search10 = new Ext.form.FormPanel({
	    	id:"search_form10",
	    	region: "north",
	    	height:120,
	    	border:false,
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
                           {html:"年度：",baseCls:"label_right",width:45},
                           {items:[xnxq10],baseCls:"component",width:200},
                           {html:"考试名称：",baseCls:"label_right",width:70},
                           {items:[kslc10],baseCls:"component",width:200},
                           {items:[cx10],baseCls:"component_btn",width:120},
                           {html:"考试批次：",baseCls:"label_right",width:70},
					       {items:[kspc10],baseCls:"component",width:200},
                           {html:"考点：",baseCls:"label_right",width:45}, 
                           {items:[kd10],baseCls:"component",width:200},
                           {items:[cz10],baseCls:"component_btn",width:120}
                          ] 
	    		}]  
	    	}]  
	    });
    },
	//考试日程
	selectKSRCSubject:function(){ 
		search_type = '';
    	var xnxq = Ext.getCmp("xnxq_find1").getCode();
		var lcid = Ext.getCmp('kslc_find1').getValue();
		var kdid = Ext.getCmp('kd_find1').getValue();
		if(kdid == ""){
    		Ext.MessageBox.alert("消息","必须选择到考点！");
			return;
    	}
    	var school = this.organ1.getCodes(); 
    	var nj = Ext.getCmp('nj_find01').getValue();
    	if(school==undefined || school=="") {
    		school="";			
    	}    	
    	var height = Ext.getCmp('sidePanel').getHeight()-100;
		var iframe="<fieldset style='height:100%; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+
						"' src='dataprint_kaoshiricheng.do?xnxq="+xnxq+"&lcid="+lcid+"&kdid="+kdid+"&school="+school+"&nj="+nj+
						"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
		Ext.getDom('dataPanel').innerHTML=iframe;	
	},
	//考点考场分布
    selectKDKCSubject:function(){ 
    	search_type = '';
    	var xnxq = Ext.getCmp("xnxq_find2").getCode();
		var lcid = Ext.getCmp('kslc_find2').getValue(); 
    	var kdid = Ext.getCmp('kd_find2').getValue(); 
    	if(kdid == ""){
    		Ext.MessageBox.alert("消息","必须选择到考点！");
			return;
    	}
    	var height = Ext.getCmp('sidePanel').getHeight()-14;
    	var iframe="<fieldset style='height:100%; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+
						"' src='dataprint_kaodianfenbu.do?xnxq="+xnxq+"&lcid="+lcid+"&kdid="+kdid+
						"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanel').innerHTML=iframe;	
	},
	//考点监考表
	selectKDJKSubject:function(){ 
		search_type = '';
    	var xnxq = Ext.getCmp("xnxq_find3").getCode();
		var lcid = Ext.getCmp('kslc_find3').getValue(); 
    	var kdid = Ext.getCmp('kd_find3').getValue(); 
    	if(kdid == ""){
    		Ext.MessageBox.alert("消息","必须选择到考点！");
			return;
    	}
    	var height = Ext.getCmp('sidePanel').getHeight()-14;
		var iframe="<fieldset style='height:100%; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+
						"' src='dataprint_kaodianjiankao.do?xnxq="+xnxq+"&lcid="+lcid+"&kdid="+kdid+
						"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
		Ext.getDom('dataPanel').innerHTML=iframe;	
	},
	//考号考生信息对照
    selectKHDZSubject:function(){  
    	search_type = '';
    	var xnxq = Ext.getCmp("xnxq_find4").getCode();
    	var selecttype = Ext.getCmp('search_form04').form.findField("selecttype").getValue();
    	var lcid = Ext.getCmp('kslc_find4').getValue(); 
    	if(lcid == ""){
    		Ext.MessageBox.alert("消息","请选择考试名称！");
			return;
    	}    	
    	var kmid = Ext.getCmp('kspc_find4').getValue();    	
    	var school = this.organ4.getSchoolCode();  
    	/*if(mBspInfo.getUserType() == "1" && (school==undefined || school=="")) {
    		Ext.MessageBox.alert("消息","请选择组织单位！");
			return;
    	}*/
    	if (school==undefined) {
    		school = "";
    	}
    	var height = Ext.getCmp('sidePanel').getHeight()-14;
    	var iframe="<fieldset style='height:100%; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+
						"' src='dataprint_kaohaoduizhao.do?xnxq="+xnxq+"&lcid="+lcid+"&kmid="+kmid+"&school="+school+"&selecttype="+selecttype+
						"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanel').innerHTML=iframe;	
	},
	//考试门签
    selectKSMQSubject:function(){ 
    	search_type = '';
    	var lcid = Ext.getCmp('kslc_find5').getValue(); 
    	var kdid = Ext.getCmp('kd_find5').getValue();
    	var kcid = Ext.getCmp('kc_find5').getValue();
    	if(kcid == ""){
    		Ext.MessageBox.alert("消息","必须选择到考场！");
			return;
    	}
    	var kmid = Ext.getCmp('kspc_find5').getValue();    	
    	var height = Ext.getCmp('sidePanel').getHeight()-150;
    	var iframe="<fieldset style='height:100%; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+
						"' src='dataprint_kaoshimenqian.do?lcid="+lcid+"&kmid="+kmid+"&kcid="+kcid+
						"&kdid="+kdid+"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanel').innerHTML=iframe;	
	}, 
	selectExamBasicInfo:function(){
		search_type = '';
    	//考生基本信息对照表
    	var lcid = Ext.getCmp('kslc_find6').getValue();
    	var kd = Ext.getCmp('kd_find6').getValue();
    	var kc = Ext.getCmp('kc_find6').getValue();
    	if(kc == ""){
    		Ext.MessageBox.alert("消息","必须选择到考场！");
			return;
    	}
    	var kmid = Ext.getCmp('kspc_find6').getValue(); 
    	var height = Ext.getCmp('sidePanel').getHeight()-172;
    	var iframe="<fieldset style='height:"+height+"; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+
						"' src='dataprint_getExamBasicInfo.do?lcid="+lcid+"&kmid="+kmid+"&kcid="+kc+
						"&kdid="+kd+"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanel').innerHTML=iframe;
    },
    selectExamCard:function(){
    	search_type = 'zkz';
    	//查询准考证
    	var lcid = Ext.getCmp('kslc_find7').getValue(); 
    	if(lcid == ""){
    		Ext.MessageBox.alert("消息","请选择考试名称！");
			return;
    	}
    	var school = this.organ7.getSchoolCode(); 
    	var kd = Ext.getCmp('kd_find7').getValue();
    	var kc = Ext.getCmp('kc_find7').getValue();
    	//var sfzjh = Ext.getCmp('sfzjh').getValue();
    	/*if(mBspInfo.getUserType() == "1" && (school==undefined || school=="")) {
    		Ext.MessageBox.alert("消息","请选择参考单位！");
			return;
    	}
    	if(njbj==""){
			Ext.MessageBox.alert("消息","请选择科目！");
			return;    	
    	}*/
    	if (school==undefined) {
    		school = "";
    	}
    	var xuexiao=this.search07.getForm().findField('sup_organ_sel').getCodes();
    	var kmid = Ext.getCmp('kspc_find7').getValue();
    	var height = Ext.getCmp('sidePanel').getHeight()-172;//"&sfzjh="+sfzjh+
    	var iframe="<fieldset style='height:"+height+"; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+
						"' src='dataprint_getExamCard.do?lcid="+lcid+"&kmid="+kmid+"&school="+school+
						"&kcid="+kc+"&kdid="+kd+"&xuexiao="+xuexiao+"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanel').innerHTML=iframe;
    },
    selectExamCardDc:function(){
    	search_type = '';
    	var lcid = Ext.getCmp('kslc_find7').getValue(); 
    	if(lcid == ""){
    		Ext.MessageBox.alert("消息","请选择考试名称！");
			return;
    	}
    	if (Ext.get("ServerPath").getValue().split("jsp").length>1) {
    		window.open(Ext.get("ServerPath").getValue()+"/main.jsp?module=000304&lcid="+lcid);
 	   }else {
 		   window.open(Ext.get("ServerPath").getValue()+"/jsp/main.jsp?module=000304&lcid="+lcid);
 	   }
    },
    selectExamSeatInfo:function(){
    	search_type = '';
    	//查询座位贴条数据
    	/*var cbitems = Ext.getCmp("photoGroup").items; 
    	var flag = false;
		for (var i = 0; i < cbitems.length; i++) {    
		    if (cbitems.itemAt(i).checked) {    
		        flag = true;  
		    }    
		} 
    	var lcid = Ext.getCmp('kslc_find8').getValue(); 
    	var kc = Ext.getCmp('kc_find8').getValue();
    	if(kc == ""){
    		Ext.MessageBox.alert("消息","必须选择到考场！");
			return;
    	}
    	var kd = Ext.getCmp('kd_find8').getValue();
    	var kmid = Ext.getCmp('kspc_find8').getValue();
    	?lcid="+lcid+"&kmid="+kmid+"&kcid="+kc+"&kdid="+kd+"&flag="+flag*/
    	var height = Ext.getCmp('sidePanel').getHeight()-30;
    	var iframe="<fieldset style='height:"+height+"; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+
						"' src='dataprint_getExamSeatInfo.do' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanel').innerHTML=iframe;
    },
    selectExamDjInfo:function(){
    	search_type = '';
    	//考生基本信息对照表
    	var lcid = Ext.getCmp('kslc_find9').getValue();
    	var kd = Ext.getCmp('kd_find9').getValue();
    	var kc = Ext.getCmp('kc_find9').getValue();
    	if(kc == ""){
    		Ext.MessageBox.alert("消息","必须选择到考场！");
			return;
    	}
    	var kmid = Ext.getCmp('kspc_find9').getValue(); 
    	var height = Ext.getCmp('sidePanel').getHeight()-172;
    	var iframe="<fieldset style='height:"+height+"; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+
						"' src='dataprint_getExamDjInfo.do?lcid="+lcid+"&kmid="+kmid+"&kcid="+kc+
						"&kdid="+kd+"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanel').innerHTML=iframe;
    },
    selectExamKsInfo:function(){
    	search_type = '';
    	//考生基本信息对照表
    	var lcid = Ext.getCmp('kslc_find10').getValue();
    	var kd = Ext.getCmp('kd_find10').getValue();
    	if(kd == ""){
    		Ext.MessageBox.alert("消息","必须选择到考点！");
			return;
    	}
    	var kmid = Ext.getCmp('kspc_find10').getValue(); 
    	var height = Ext.getCmp('sidePanel').getHeight()-172;
    	var iframe="<fieldset style='height:"+height+"; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReport' name='frmReport' width='100%' height='"+height+
						"' src='dataprint_getExamKsInfo.do?lcid="+lcid+"&kmid="+kmid+"&kdid="+kd+
						"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanel').innerHTML=iframe;
    },
    exportPdf:function(){
    	Ext.Msg.wait("正在导出","提示");
    	var lcid = Ext.getCmp('kslc_find7').getValue(); 
    	if(lcid == ""){
    		Ext.MessageBox.alert("消息","请选择考试名称！");
			return;
    	}
    	var school = this.organ7.getSchoolCode(); 
    	var kd = Ext.getCmp('kd_find7').getValue();
    	var kc = Ext.getCmp('kc_find7').getValue(); 
    	if (school==undefined) {
    		school = "";
    	}
    	var xuexiao=this.search07.getForm().findField('sup_organ_sel').getCodes();
    	var kmid = Ext.getCmp('kspc_find7').getValue(); 
    	 Ext.Ajax.request({
    		url:"dataprint_getExportPdf.do", 
    		params:{ 
    			'school':school,
    			'lcid':lcid,
    			'kmid':kmid,
    			'kdid':kd,
    			'xuexiao':xuexiao,
    			'kcid':kc
			},
			scope:this,
			success: function (r, options) {
				var result =Ext.decode(r.responseText); 
				if(result.success){
					Ext.Msg.alert("提示","导出成功!");
					if(result.data!=null && result.data!=''){
						window.open(Ext.get("ServerPath").dom.value+"/"+result.data.replace(/\\/g, "\/"));
					}
				}else{
					Ext.Msg.alert("提示",result.msg);
				}
    		},
    		failure: function (response, options) {
    		}
		});	
    },
    
     exportXls:function(){
    	Ext.Msg.wait("正在导出","提示");
    	var lcid = Ext.getCmp('kslc_find7').getValue(); 
    	if(lcid == ""){
    		Ext.MessageBox.alert("消息","请选择考试名称！");
			return;
    	}
    	var school = this.organ7.getSchoolCode(); 
    	var kd = Ext.getCmp('kd_find7').getValue();
    	var kc = Ext.getCmp('kc_find7').getValue(); 
    	if (school==undefined) {
    		school = "";
    	}
    	var xuexiao=this.search07.getForm().findField('sup_organ_sel').getCodes();
    	var kmid = Ext.getCmp('kspc_find7').getValue(); 
    	 Ext.Ajax.request({
    		url:"exam_exportXls.do", 
    		params:{ 
    			'school':school,
    			'lcid':lcid,
    			'kmid':kmid,
    			'kdid':kd,
    			'xuexiao':xuexiao,
    			'kcid':kc
			},
			scope:this,
			success: function (r, options) {
				var result =Ext.decode(r.responseText); 
				if(result.success){
					Ext.Msg.alert("提示","导出成功!");
					if(result.data!=null && result.data!=''){
						window.open(Ext.get("ServerPath").dom.value+"/"+result.data.replace(/\\/g, "\/"));
					}
				}else{
					Ext.Msg.alert("提示",result.msg);
				}
    		},
    		failure: function (response, options) {
    		}
		});	
    },
    
    dayinData:function(){
    	//打印
    	if(Ext.getDom('frmReport') == null) {
    		Ext.MessageBox.alert("消息","请先检索数据！");
			return;
    	}
    	if(Ext.getDom('frmReport').contentWindow.document.getElementById("complete") == null 
    			|| Ext.getDom('frmReport').contentWindow.document.getElementById("complete").value != "ok") {
    		Ext.MessageBox.alert("消息","请等待数据加载完毕！");
    		return;
    	} 
    	//将css样式导入lodop打印中，避免失效
    	var cssPath = curWwwPath.substring(0,curWwwPath.indexOf('loginAction_login.do'))+"css/Print.css"
    	var strStyleCSS="<link href='"+cssPath+"' type='text/css' rel='stylesheet'>";
    	var LODOP =getLodop(); 
    	
    	//准考证单独，因为数据量太大，分别将每一页发给打印机，注意不要关掉浏览器
    	if(search_type == 'zkz'){
    		var total = parseInt(Ext.getDom('frmReport').contentWindow.document.getElementById('count').value);
    		var start, end;
    		Ext.MessageBox.alert("消息","共"+total+"张准考证，请填写打印起止页！", function() {
        		//填写起始页
        		Ext.MessageBox.prompt("请填写起始页", "", callbackStart, this, true, "1"); 
                function callbackStart(id, text) {
            		start=parseInt(text);
            		if(start < 1) {
            			start = 0;
            		} 
            		if(start > total) {
            			start = total;
            		}
            		if(isNaN(start)){
            			Ext.MessageBox.alert("消息","填写数字不合法！");
            			return;
            		}
                	
                	//填写结束页
                    Ext.MessageBox.prompt("请填写结束页", "", callbackEnd, this, true, total); 
                    function callbackEnd(id, text) {
                		end=parseInt(text);
                		if(end < 1) {
                			end = 0;
                		} 
                		if(end > total) {
                			end = total;
                		}
                		if(start > end) {
                			var tmp;
                			tmp = end;
                			end = start
                			start = tmp;
                		}
                		if(isNaN(end)){ 
                			Ext.MessageBox.alert("消息","填写数字不合法！");
                			return;
                		}
                    	
                    	 //开始打印
                    	for (i = start-1; i <end; i++) {
                    		LODOP.ADD_PRINT_HTM("1%","3%", "99%","100%",strStyleCSS+Ext.getDom('frmReport').contentWindow.document.getElementById('zkz'+i).innerHTML);
                    		LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
                    		LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",1);
                    		LODOP.PRINT();
                		}
                    	Ext.MessageBox.alert("消息","将打印"+(end-start+1)+"张准考证，请等待打印完成！");
                    } 
                    
                 
                } 
                
    		});
            
    	} else {
    		//lodop打印
    	
    	
        	LODOP.ADD_PRINT_HTM("1%","3%", "99%","100%",strStyleCSS+Ext.getDom('frmReport').contentWindow.document.body.innerHTML);
        	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
        	LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",1);
        	LODOP.PREVIEW();
    	}
    },
    perviewExamCard:function(){
    	//打印
    	if(Ext.getDom('frmReport') == null) {
    		Ext.MessageBox.alert("消息","请先检索数据！");
			return;
    	}
    	if(Ext.getDom('frmReport').contentWindow.document.getElementById("complete") == null 
    			|| Ext.getDom('frmReport').contentWindow.document.getElementById("complete").value != "ok") {
    		Ext.MessageBox.alert("消息","请等待数据加载完毕！");
    		return;
    	} 
    	//将css样式导入lodop打印中，避免失效
    	var cssPath = curWwwPath.substring(0,curWwwPath.indexOf('loginAction_login.do'))+"css/Print.css"
    	var strStyleCSS="<link href='"+cssPath+"' type='text/css' rel='stylesheet'>";
    	var LODOP =getLodop(); 
    	
    	LODOP.ADD_PRINT_HTM("1%","3%", "99%","100%",strStyleCSS+Ext.getDom('frmReport').contentWindow.document.body.innerHTML);
    	LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
    	LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",1);
    	LODOP.PREVIEW();
    }        
});