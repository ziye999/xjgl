var roomcode_b = "";
Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
	    this.initDate();
    },
    /** 初始化页面、内存等基本数据 **/
    initDate:function(){
    	var thiz=this;
    	Ext.Ajax.request({   
       		url:'scoresQuery_getUserType.do',
	        success: function(resp,opts) {
	        	var respText = resp.responseText;
	           	thiz.userType="1";//respText;
	           	thiz.findPanel();
	           	thiz.initComponent();
				thiz.initListener();
				thiz.initFace();
				thiz.createWindow();
				
	        }
        });
    	
    },
    
	 /** 对组件设置监听 **/
    initListener:function(){
    	var thiz=this;
    	this.orgTree.on("click",function(node){
    		if(node.id!="root"){
	    		thiz.menuToSwitch();
	    		thiz.node=node.id;
	    		switch(node.id){
	    			case '01':
	    				Ext.getCmp("centerPanel").setTitle("优秀率统计");
	    				break;
	    			case '02':
	    				alert("合格率同意");
	    				Ext.getCmp("centerPanel").setTitle("合格率统计");
	    				break;
	    			case '03':
	    				Ext.getCmp("centerPanel").setTitle("平均分统计");
	    				break;
	    			case '04':
	    				Ext.getCmp("centerPanel").setTitle("最高分与最低分统计");
	    				break;
	    			case '05':
	    				Ext.getCmp("centerPanel").setTitle("平均分标准差统计");
	    				break;
	    			case '06':
	    				Ext.getCmp("centerPanel").setTitle("各科考试基本情况");
	    				break;
	    		}
    		}
    	},this.grid);

    },
   
   	initComponent :function(){
   		var root=new Ext.tree.TreeNode({
   			id:'root',
   			text:'成绩统计'
   		});
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'01',
   			text:'优秀率统计'
   		}));
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'02',
   			text:'合格率统计'
   		}));
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'03',
   			text:'平均分统计'
   		}));
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'04',
   			text:'最高分与最低分统计'
   		}));
   		var treeNode=new Ext.tree.TreeNode({
   			id:'05',
   			text:'平均分标准差统计'
   		});
   		treeNode.appendChild(new Ext.tree.TreeNode({
   			id:"99",
   			text:"zijidian"
   		}))
   		root.appendChild(treeNode);
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'06',
   			text:'各科考试基本情况'
   		}));
   		this.orgTree = new Ext.tree.TreePanel({
   						 region:"west",
			             rootVisible:true,
			             title:"成绩统计",
						 collapseMode : "mini",
			             split:true,
			             minSize: 120,
			             width:200,
			             maxSize: 300,
			             autoScroll:true,
			             root:root
   		});
   		
   		this.orgTree2 = new Ext.ux.TreePanel({region:"west",
			             rootVisible:false,
			             title:"楼房",
						 collapseMode : "mini",
			             split:true,
			             minSize: 120,
			             width:200,
			             maxSize: 300,
			             autoScroll:true,
   						 action:"room_getBuilding.do"
   		});
   		
   		var tree = new Ext.tree.TreePanel({  
			id:"treefield_",
			checkModel: 'cascade',   //对树的级联多选  
		    onlyLeafCheckable: false,//对树所有结点都可选 
            region: 'center',
            width: 200,
            border : false,//表框 
            autoScroll: true,//自动滚动条
            animate : true,//动画效果 
            rootVisible: false,//根节点是否可见  
            split: true,  
            loader : new Ext.tree.TreeLoader({  
            	dataUrl : 'room_getBuilding.do'//'dropListAction_getOrganTree.do'//
            }), 
            tbar:{
				items:[ 
			       "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.win.hide()},scope:this}
				  ,"->",{xtype:"button",text:"选择",iconCls:"p-icons-submit",handler:function() {
					  var selectedNode =  Ext.getCmp("treefield_"+this.id).getChecked();
					  if(selectedNode == ""){
				    		Ext.MessageBox.alert("消息","请选择一条记录！");
				    		return;
				    	}
					  this.schoolCodes = "";
					  this.organCodes = "";
					  this.schoolNames = "";
					  this.organNames = "";
					  this.allCodes = "";
					  this.allNames = "";
					  
					  for(var i=0; i < selectedNode.length;i++) {
							  if(selectedNode[i].id.length > 12) {
								  this.schoolCodes += selectedNode[i].id+",";
								  this.schoolNames += selectedNode[i].text+",";
							  }else {
								  this.organCodes += selectedNode[i].id+",";
								  this.organNames += selectedNode[i].text+",";
							  }
							  this.allCodes += selectedNode[i].id+",";
							  this.allNames += selectedNode[i].text+",";
					  }
					  this.schoolCodes = this.schoolCodes.substring(0,this.schoolCodes.length-1);
					  this.organCodes = this.organCodes.substring(0,this.organCodes.length-1);
					  this.schoolNames = this.schoolNames.substring(0,this.schoolNames.length-1);
					  this.organNames = this.organNames.substring(0,this.organNames.length-1);
					  this.allCodes = this.allCodes.substring(0,this.allCodes.length-1);
					  this.allNames = this.allNames.substring(0,this.allNames.length-1);
					  this.setValue(this.allNames);
					  if(this.callback != null) {
						  this.callback();
					  }
					  this.win.hide();
				  },scope:this}
				  ]
			},  
            root : new Ext.tree.AsyncTreeNode({  
            }),  
            listeners: {  
                click: function(node) {  
                    //得到node的text属性  
                    Ext.Msg.alert('消息', '你点击了: "' + node.attributes.text+"'");  
                }
            }  
        });
   		
   		
   		var dataPanelShow=new Ext.Panel({
    		region:'center',
    		border:false,
   			id:"dataPanelShowT",
   			bodyStyle:"border:2px red solid",
   			items:[tree]
   		});
   		this.dataPanel=new Ext.Panel({
    		region:'center',
    		border:false,
    		layout:"border",
    		tbar:[ 
					  "->",{xtype:"button",text:"打印",iconCls:"p-icons-update",handler:function(){this.printResultsData()},scope:this}
					  ],
   			items:[dataPanelShow]
   		});
		this.grid = new Ext.Panel({
			title:"优秀率统计",
			id:"centerPanel",
			region:"center",
			border:true,
			layout:"border",
			items:[this.search,this.dataPanel]
			});
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
		}]});
		
		
    },
    //初始化弹窗
    createWindow:function(){
    	//初始化轮次弹窗
    	this.editForm   = this.createLungCiEditform();
   		this.eidtWindow = this.createLungCiWindow();
   		this.eidtWindow.add(this.editForm);
   		this.eidtWindow.show();
   		this.lcGrid.$load();
   		this.eidtWindow.hide();
    },
    findPanel:function(){
		//搜索区域
    	//var xnxq_find = new Ext.ux.form.XnxqField({width:170,id:"xnxq_find",readOnly:true});//年度
    	var jyjg_find= new Ext.ux.form.OrganField({name:"jyjg_find",readOnly:true});//组织单位
    	var km_find =new Ext.form.TextField({id:"km_find",width:170,disabled:true});
    	
    	var cx = new Ext.Button({x:22,region:"west",cls:"base_btn",text:"查询",handler:this.selectResults,scope:this});
		var cz = new Ext.Button({x:96,region:"center",cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		//var dy = new Ext.Button({x:130,region:"east",cls:"base_btn",text:"打印",handler:this.dayinData,scope:this});
		
		if(this.userType==2){
			var fw_find= new Ext.form.RadioGroup({id:"fwGroup_find",items:[
    		{boxLabel: '轮次', name:'fw_find', inputValue: 'lc' ,value:"lc" ,checked: false},
    		{boxLabel: '参考单位', name:'fw_find', inputValue: 'xx' ,value:"xx",checked: false},
    		{boxLabel: '科目', xtype:"radio", name:'fw_find', inputValue: 'bj' ,value:"bj", checked: false}
    		],listeners:{
				 "change":this.radioListeners,
                    scope:this
				}
			});
			this.search = new Ext.form.FormPanel({
			       id:"search_form9",
			       region: "north",
			       height:90,
			       border:false,
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
								columns: 5
								}, 
							baseCls:"table",
							items:[
								{html:"统计范围：",baseCls:"label_right",width:100}, 
								{items:[fw_find],baseCls:"label_center",width:180},
								{html:"统计科目：",baseCls:"label_right",width:100},
								{items:[km_find],baseCls:"label_center",width:180},							
								{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:180,height:20}							
								] 
	                    }]  
			       }]  
		    	})
	    }else if(this.userType==1){
	    	var fw_find= new Ext.form.RadioGroup({id:"fwGroup_find",items:[
	    		{boxLabel: '轮次', name:'fw_find', inputValue: 'lc' ,value:"lc", checked: false},
	    		{boxLabel: '参考单位', name:'fw_find', inputValue: 'xx' ,value:"xx", checked: false}
    		],listeners:{
				 "change":this.radioListeners,
                    scope:this
				}
			});
		    this.search = new Ext.form.FormPanel({
		    	id:"search_form9",
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
								columns: 4
							}, 
							baseCls:"table",
							items:[
								{html:"组织单位：",baseCls:"label_right",width:100},
								{items:[jyjg_find],baseCls:"label_center",width:180},
								{html:"统计范围：",baseCls:"label_right",width:100}, 
								{items:[fw_find],baseCls:"label_center",width:180},
								{html:"统计科目：",baseCls:"label_right",width:100},
								{items:[km_find],baseCls:"label_center",width:180},
								{html:"",baseCls:"label_right",width:100},						
								{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:180,height:20}							
							] 
	                    }]  
			       }]  
		    	})
	    }
    },selectResults:function(){
        var height=Ext.getCmp('dataPanelShowT').getHeight()-4;
  		var iframe="<fieldset style='height:348; width:100%; border:0px; padding:0;background:#fff'>"+
						"<iframe id='frmReportTr' name='frmReportTr' width='100%'"+
								"height='"+height+"' src='../scoresQuery_printData.do' frameborder='0' scrolling='auto'"+
								"style='border:1px dashed #B3B5B4;' ></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanelShowT').innerHTML=iframe;
    },printResultsData:function(){
    	frmReportTr.print();
    },menuToSwitch:function(){
    	//搜索区还原
    	Ext.getCmp('search_form9').getForm().reset();
	    var iframe="<fieldset style='height:348; width:100%; border:0px; padding:0;background:#fff'>"+
				   "<iframe id='frmReportTr' name='frmReportTr' width='100%'frameborder='0' scrolling='auto'></iframe>"+
				   "</fieldset>";
	    //打印区还原
	    Ext.getDom('dataPanelShowT').innerHTML=iframe;
    
    },radioListeners:function(){
        var gVal=Ext.getCmp("fwGroup_find").getValue().value;//combo.getValue();           //取得ComboBox0的选择值
		if(gVal!="lc"){
			Ext.getCmp("km_find").enable();
		}else{
			Ext.getCmp("km_find").disable();
			this.checkedLungCi();
		}
    },
    //---------------选择轮次-----------------------
    checkedLungCi:function(){
    	alert("");
   		this.eidtWindow.show();
   		alert("dddd");
    },
    createLungCiWindow:function(){
    	var save = new Ext.Button({text:"确定",iconCls:"p-icons-save",handler:this.saveLungCi,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide();},scope:this});
		return new Ext.ux.Window({
			 	width:500,
			 	title:"选择考试名称",
			 	tbar:{
						cls:"ext_tabr",
						items:[ 
						 	 "->",cancel
				 			 ,"->",save
						  ]
					},
			 	buttonAlign:"center"});	
		
    },
    createLungCiEditform:function(){
    	var thiz=this;
    	var xnxq_lc_find = new Ext.ux.form.XnxqField({ width:240,id:"xnxq_lc_find",readOnly:true,callback:function(){
    		var xnxqId=Ext.getCmp("xnxq_lc_find").getCode();
    		thiz.lcGrid.$load({"xnxqId":xnxqId});
    		
    	}});
    	var cz = new Ext.Button({x:6,region:"center",cls:"base_btn",text:"重置",handler:function(){Ext.getCmp("xnxq_lc_find").setValue("");thiz.lcGrid.$load({"xnxqId":""});},scope:this});
		this.xnxqPanel = new Ext.Panel({ 
			id:"xnxqPanel",
			region:"north",
			height:34,
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 3 
				}, 
			items:[
				{html:"年度：",baseCls:"label_right",width:120},
				{items:[xnxq_lc_find],baseCls:"component",width:242},//html:"<font class='required'>*</font>",
				{layout:"absolute",items:[cz],baseCls:"component_btn",width:120,height:20}
			]
		
		});
		
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "年度",   align:"center", sortable:true, dataIndex:"XNMC"},
			{header: "季度",   align:"center", sortable:true, dataIndex:"XQMC"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME"}
		];
		this.lcGrid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"考试名称",
			page:true,
			rowNumber:true,
			region:"center",
			action:"examroomarrange_getListPage.do",
			fields :["LCID","EXAMNAME","STARTWEEK","STARTDAY","STARTDATE","ENDWEEK","ENDDAY","ENDDATE","EXAMTYPEM","XNMC","XQMC"],
			border:false
		});
		
		var panel=new Ext.Panel({
	    		id:"LcPanel",
	    		region:"north",
	    		width:"auto",
	    		height:400,
	    		layout:"border",
	    		border:false,
	    		items:[this.xnxqPanel ,this.lcGrid]
	    	});
	    return panel;
    },
    saveLungCi:function(){
    	this.eidtWindow.hide();
    	/*this.editForm.$submit({
    		action:"examteacherarrange_saveLuRuTeacher.do",
    		handler:function(form,result,thiz){
    			thiz.eidtWindow.hide();
    			thiz.selectExamTeacher();
    		},
    		scope:this
    	});*/
    }
});
