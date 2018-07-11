var roomcode_b = "";
Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
	    this.initDate();
    },
    /** 初始化页面、内存等基本数据 **/
    initDate:function(){
    	this.node="01";
    	var thiz=this;
    	Ext.Ajax.request({   
       		url:'scoresQuery_getUserType.do',
	        success: function(resp,opts) {	        	
	        	var respText = resp.responseText;
	           	thiz.userType=respText;
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
	    				Ext.getCmp("nj_find").enable();
	    				//Ext.getCmp("km_find").enable();
	    				Ext.getCmp("centerPanel").setTitle("优秀率统计");
	    				break;
	    			case '02':
	    				Ext.getCmp("nj_find").enable();
	    				//Ext.getCmp("km_find").enable();
	    				Ext.getCmp("centerPanel").setTitle("合格率统计");
	    				break;
	    			case '03':
	    				Ext.getCmp("nj_find").enable();
	    				//Ext.getCmp("km_find").enable();
	    				Ext.getCmp("centerPanel").setTitle("平均分统计");
	    				break;
	    			case '04':
	    				Ext.getCmp("nj_find").enable();
	    				//Ext.getCmp("km_find").enable();
	    				Ext.getCmp("centerPanel").setTitle("最高分与最低分统计");
	    				break;
	    			case '05':
	    				Ext.getCmp("nj_find").enable();
	    				//Ext.getCmp("km_find").enable();
	    				Ext.getCmp("centerPanel").setTitle("平均分标准差统计");
	    				break;
	    			case '06':
	    				Ext.getCmp("nj_find").enable();
	    				//Ext.getCmp("km_find").enable();
	    				Ext.getCmp("centerPanel").setTitle("各科考试基本情况");
	    				break;
	    		}
	    		thiz.closeWindow();
	    		thiz.createWindow();
    		}
    	},this.grid);
    },   
   	initComponent :function(){
   		var root=new Ext.tree.TreeNode({
   			expanded: true,
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
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'05',
   			text:'平均分标准差统计'
   		}));
   		if(this.userType=='1'){
	   		root.appendChild(new Ext.tree.TreeNode({
	   			id:'06',
	   			text:'各科考试基本情况'
	   		}));
   		}
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
   		var iframe="<fieldset style='height:348; width:100%; border:0px; padding:0;background:#fff'>"+
						"<iframe id='frmReportRs' name='frmReportRs' width='100%' height='403' "+
						"frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
   		var dataPanelShow=new Ext.Panel({
    		region:'center',
    		border:false,
   			id:"dataPanelShowR",
   			html:iframe
   			//bodyStyle:"border:2px red solid"
   		});
   		this.dataPanel=new Ext.Panel({
    		region:'center',
    		border:false,
    		layout:"border",
    		tbar:[ 
    		      "->",{xtype:"button",text:"打印",iconCls:"p-icons-print",handler:function(){this.printResultsData()},scope:this}
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
    		}]
    	});		
    },
    //初始化弹窗
    createWindow:function(){
    	//初始化轮次弹窗
    	this.editForm   = this.createLungCiEditform();
   		this.eidtWindow = this.createLungCiWindow();
   		this.eidtWindow.add(this.editForm);
   		
   		this.xxEditForm   = this.createXueXiaoEditform();
   		this.xxEidtWindow = this.createXueXiaoWindow();
   		this.xxEidtWindow.add(this.xxEditForm);
   		
   		this.njEditForm   = this.createNianJiEditform();
   		this.njEidtWindow = this.createNianJiWindow();
   		this.njEidtWindow.add(this.njEditForm);
   		
   		this.bjEditForm   = this.createBanJiEditform();
   		this.bjEidtWindow = this.createBanJiWindow();
   		this.bjEidtWindow.add(this.bjEditForm);
    },
    closeWindow:function(){
    	//关闭弹窗
   		this.eidtWindow.hide();
   		this.xxEidtWindow.hide();
   		this.njEidtWindow.hide();
   		this.bjEidtWindow.hide();
    },
    findPanel:function(){
		//搜索区域
    	//var km_find = new Ext.ux.form.XnxqField({width:170,id:"km_find",readOnly:true});//年度
    	//组织单位
    	//var jyjg_find= new Ext.ux.form.OrganField({name:"jyjg_find",id:"jyjg_find",readOnly:true});//组织单位
    	//var km_find =new Ext.form.TextField({id:"km_find",width:170,disabled:true});
    	//var km_find =new Ext.ux.form.KeMuField({width:180,id:"km_find",disabled:true});
    	var nj_find = new Ext.ux.Combox({dropAction:"grade",id:"nj_find",name:"nj_find",width:200,allowBlank:false,blankText:"等级不能为空！",disabled:true});
		var cx = new Ext.Button({x:22,region:"west",cls:"base_btn",text:"查询",handler:this.selectResults,scope:this});
		var cz = new Ext.Button({x:96,region:"center",cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		//var dy = new Ext.Button({x:130,region:"east",cls:"base_btn",text:"打印",handler:this.dayinData,scope:this});
		
		if(this.userType==2){
			var fw_find= new Ext.form.RadioGroup({
				id:"fwGroup_find",
				items:[
				       {boxLabel: '轮次', name:'fw_find', inputValue: 'lc', value:"lc", checked: false},
				       {boxLabel: '等级', name:'fw_find', inputValue: 'nj', value:"nj", checked: false},
				       {boxLabel: '科目', xtype:"radio", name:'fw_find', inputValue: 'bj', value:"bj", checked: false}
				      ],
				listeners:{
					"change":this.radioListeners,
					scope:this
				}
			});
			this.search = new Ext.form.FormPanel({
				id:"search_form8",
				region: "north",
				height:90,
				border:false,
				items:[{  
					layout:'form',  
					xtype:'fieldset',  
					style:'margin:5',
					title:'查询条件',  
					items: [{
	                    	xtype:"panel",
							layout:"table", 
							layoutConfig: { 
								columns: 5
							}, 
							baseCls:"table",
							items:[
							       {html:"统计范围：",baseCls:"label_right",width:70}, 
							       {items:[fw_find],baseCls:"label_center",width:180},
							       //{html:"统计科目：",baseCls:"label_right",width:70},
							       //{items:[km_find],baseCls:"label_center",width:180},							
							       {layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:160,height:20}							
							      ] 
	                   	}]  
			      	}]  
		    })
	    }else if(this.userType==1){
	    	var fw_find= new Ext.form.RadioGroup({
	    		id:"fwGroup_find",
	    		items:[
	    		       {boxLabel: '轮次', name:'fw_find', inputValue: 'lc' ,value:"lc", checked: false},
	    		       {boxLabel: '参考单位', name:'fw_find', inputValue: 'xx' ,value:"xx", checked: false}
	    		      ],
	    		listeners:{
	    			"change":this.radioListeners,
                    scope:this
				}
			});
		    this.search = new Ext.form.FormPanel({
		    	id:"search_form8",
		    	region: "north",
		    	height:90,
		    	border:false,
		    	items:[{  
		    		layout:'form',  
		    		xtype:'fieldset',  
		    		style:'margin:5',
		    		title:'查询条件',  
		    		items: [{
	                    	xtype:"panel",
							layout:"table", 
							layoutConfig: { 
								columns: 7
							}, 
							baseCls:"table",
							items:[
							       /*{html:"组织单位：",baseCls:"label_right",width:100},
									{items:[jyjg_find],baseCls:"label_center",width:180},*/
							       {html:"统计范围：",baseCls:"label_right",width:70}, 
							       {items:[fw_find],baseCls:"label_center",width:150},
							       {html:"等级：",baseCls:"label_right",width:50},
							       {items:[nj_find],baseCls:"label_center",width:220},
							       //{html:"统计科目：",baseCls:"label_right",width:70},
							       //{items:[km_find],baseCls:"label_center",width:180},
							       /*{html:"",baseCls:"label_right",width:100},*/						
							       {layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:160,height:20}							
								] 
	                   	}]  
			       }]  
		    })
	    }
    },
    selectResults:function(){
    	var fwGroup=Ext.getCmp("fwGroup_find").getValue();//combo.getValue();           //取得ComboBox0的选择值
    	if(fwGroup==null){
    		Ext.MessageBox.alert("提示","请选择统计范围！");
    		return;
    	}
    	var xjnj=Ext.getCmp("nj_find").getValue();
    	/*if(xjnj=="" && this.node=='06'){
    		Ext.MessageBox.alert("提示","请选择等级！");
    		return;
    	}*/
    	var gVal=fwGroup.value;
		/*var km=Ext.getCmp("km_find").getCode();
    	if(km==undefined){
    		km="";
    	}*/
    	var src="";
		if(gVal=="lc"){
			src="resultsStatistical_printDataForLC.do?tjfw="+this.lcid+"&tjlx="+this.node+"&xjnj="+xjnj;
		}else if(gVal=="xx"){
			src="resultsStatistical_printDataForXX.do?tjfw="+this.xxid+"&tjlx="+this.node+"&xjnj="+xjnj;//+"&tjkm="+km
		}else if(gVal=="nj"){
			src="resultsStatistical_printDataForNJ.do?tjfw="+this.nj+"&tjlx="+this.node;//+"&tjkm="+km
		}else if(gVal=="bj"){
			src="resultsStatistical_printDataForBJ.do?tjfw="+this.bj+"&tjlx="+this.node;//+"&tjkm="+km
		}
        //var height=Ext.getCmp('dataPanelShow').getHeight()-4;
        var height=403;//Ext.getCmp('dataPanelShow').getHeight()-4;
  		var iframe="<fieldset style='height:348; width:100%; border:0px; padding:0;background:#fff'>"+
						"<iframe id='frmReportRs' name='frmReportRs' width='100%' height='"+height+"' src='"+src+
						"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanelShowR').innerHTML=iframe;
    },
    printResultsData:function(){
    	frmReportRs.print();
    },
    menuToSwitch:function(){
    	//搜索区还原
    	Ext.getCmp('search_form8').getForm().reset();
	    var iframe="<fieldset style='height:348; width:100%; border:0px; padding:0;background:#fff'>"+
				  		"<iframe id='frmReportRs' name='frmReportRs' width='100%'frameborder='0' scrolling='auto'></iframe>"+
				   "</fieldset>";
	    //打印区还原
	    Ext.getDom('dataPanelShowR').innerHTML=iframe;    
    },
    radioListeners:function(){
    	var gVal=Ext.getCmp("fwGroup_find").getValue().value;//combo.getValue();           //取得ComboBox0的选择值
		if(gVal=="lc"){
			//Ext.getCmp("km_find").enable();
			this.checkedLungCi();
		}else if(gVal=="xx"){
			//Ext.getCmp("km_find").enable();
			this.checkedXueXiao();
		}else if(gVal=="nj"){
			//Ext.getCmp("km_find").enable();
			this.checkedNianJi();
		}else if(gVal=="bj"){
			//Ext.getCmp("km_find").enable();
			this.checkedBanJi();
		}
    },
    //---------------选择轮次-----------------------
    checkedLungCi:function(){
   		this.eidtWindow.show();
    },
    createLungCiWindow:function(){
    	return new Ext.ux.Window({
			 		width:300,
			 		title:"选择考试名称",
			 		buttonAlign:"center"
		});		
    },
    createLungCiEditform:function(){
    	var tree = new Ext.tree.TreePanel({  
			id:"checkLungCi",
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
            	dataUrl : 'resultsStatistical_getLungCi.do'//'dropListAction_getOrganTree.do'//
            }), 
            tbar:{
				items:[ 
				       "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide();},scope:this}
				       ,"->",{xtype:"button",text:"选择",iconCls:"p-icons-submit",handler:function() {
				    	   var selectedNode =  Ext.getCmp("checkLungCi").getChecked();
				    	   if(selectedNode == ""){
				    		   Ext.MessageBox.alert("消息","请选择一条记录！");
				    		   return;
				    	   }
				    	   var lcid = "";
				    	   for(var i=0; i < selectedNode.length;i++) {
				    		   lcid += selectedNode[i].id+",";
				    	   }
				    	   lcid =lcid.substring(0,lcid.length-1);
				    	   this.lcid=lcid;					 
				    	   this.eidtWindow.hide();
				       },scope:this}
				  ]
			},  
			root : new Ext.tree.AsyncTreeNode({}) 
			/*listeners: {  
           		click: function(node) {  
                	//得到node的text属性  
                    Ext.Msg.alert('消息', '你点击了: "' + node.attributes.text+"'");  
               	}
           	}*/
        });
		var panel=new Ext.Panel({
	    		id:"LcPanel",
	    		region:"north",
	    		width:"auto",
	    		height:400,
	    		layout:"border",
	    		border:false,
	    		items:[tree]
	    });
	    return panel;
    },    
    //---------------选择参考单位-----------------------
    checkedXueXiao:function(){
   		this.xxEidtWindow.show();
    },
    createXueXiaoWindow:function(){
    	return new Ext.ux.Window({
			 	width:300,
			 	title:"选择参考单位",
			 	buttonAlign:"center"
		});		
    },
    createXueXiaoEditform:function(){
    	var tree = new Ext.tree.TreePanel({  
			id:"checkXueXiao",
			checkModel: 'cascade',   //对树的级联多选  
		    onlyLeafCheckable: false,//对树所有结点都可选 
            region: 'center',
            enableDD: true,
            width: 280,
            border : false,//表框 
            autoScroll: true,//自动滚动条
            animate : true,//动画效果 
            rootVisible: false,//根节点是否可见  
            split: true,  
            loader : new Ext.tree.TreeLoader({  
            	dataUrl : 'resultsStatistical_getXueXiao.do'//'dropListAction_getOrganTree.do'//
            }), 
            tbar:{
            	items:[ 
            	       "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.xxEidtWindow.hide();},scope:this}
            	       ,"->",{xtype:"button",text:"选择",iconCls:"p-icons-submit",handler:function() {
            	    	   var selectedNode =  Ext.getCmp("checkXueXiao").getChecked();
            	    	   if(selectedNode == ""){
            	    		   Ext.MessageBox.alert("消息","请选择一条记录！");
            	    		   return;
            	    	   }
            	    	   var xxid = "";					  
            	    	   for(var i=0; i < selectedNode.length;i++) {
            	    		   xxid += selectedNode[i].id+",";
            	    	   }
            	    	   xxid = xxid.substring(0,xxid.length-1);
            	    	   this.xxid=xxid;
            	    	   this.xxEidtWindow.hide();
            	       },scope:this}
				  ]
			},  
            root : new Ext.tree.AsyncTreeNode({})  
			/*listeners: {  
           		click: function(node) {       
               		Ext.Msg.alert('消息', '你点击了: "' + node.attributes.text+"'");  
              	}
            }*/  
        });
		
		var panel=new Ext.Panel({
	    		id:"xxPanel",
	    		region:"north",
	    		width:"auto",
	    		height:400,
	    		layout:"border",
	    		border:false,
	    		items:[tree]
	    });
	    return panel;
    },
    //---------------选择等级-----------------------
    checkedNianJi:function(){
   		this.njEidtWindow.show();
    },
    createNianJiWindow:function(){
    	return new Ext.ux.Window({
			 	width:300,
			 	title:"选择等级",
			 	buttonAlign:"center"
		});		
    },
    createNianJiEditform:function(){
    	var thiz=this;
    	var tree = new Ext.tree.TreePanel({  
			id:"checkNianJi",
			checkModel: 'cascade',   //对树的级联多选  
		    onlyLeafCheckable: false,//对树所有结点都可选 
            region: 'center',
            enableDD: true,
            width: 280,
            border : false,//表框 
            autoScroll: true,//自动滚动条
            animate : true,//动画效果 
            rootVisible: false,//根节点是否可见  
            split: true,  
            loader : new Ext.tree.TreeLoader({  
            	dataUrl : 'resultsStatistical_getNianJi.do'//'dropListAction_getOrganTree.do'//
            }), 
            tbar:{
				items:[ 
				       "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.njEidtWindow.hide();},scope:this}
				       ,"->",{xtype:"button",text:"选择",iconCls:"p-icons-submit",handler:function() {
				    	   var selectedNode =  Ext.getCmp("checkNianJi").getChecked();
				    	   if(selectedNode == ""){
				    		   Ext.MessageBox.alert("消息","请选择一条记录！");
				    		   return;
				    	   }
				    	   var nj = "";
				    	   for(var i=0; i < selectedNode.length;i++) {
				    		   nj += selectedNode[i].id+",";
				    	   }
				    	   nj = nj.substring(0,nj.length-1);
				    	   this.nj=nj;
				    	   this.njEidtWindow.hide();
				       },scope:this}
				  ]
			},  
            root : new Ext.tree.AsyncTreeNode({})  
			/*listeners: {  
            	click: function(node) {       
               		Ext.Msg.alert('消息', '你点击了: "' + node.attributes.text+"'");  
             	}
            }*/  
        });
		
		var panel=new Ext.Panel({
	    		id:"njPanel",
	    		region:"north",
	    		width:"auto",
	    		height:400,
	    		layout:"border",
	    		border:false,
	    		items:[tree]
	    });
	    return panel;
    },
    //---------------选择科目-----------------------
    checkedBanJi:function(){
   		this.bjEidtWindow.show();
    },
    createBanJiWindow:function(){
    	return new Ext.ux.Window({
			 	width:300,
			 	title:"选择科目",
			 	buttonAlign:"center"
    	});		
    },
    createBanJiEditform:function(){
    	var tree = new Ext.tree.TreePanel({  
			id:"checkBanJi",
			checkModel: 'cascade',   //对树的级联多选  
		    onlyLeafCheckable: false,//对树所有结点都可选 
            region: 'center',
            enableDD: true,
            width: 280,
            border : false,//表框 
            autoScroll: true,//自动滚动条
            animate : true,//动画效果 
            rootVisible: false,//根节点是否可见  
            split: true,  
            loader : new Ext.tree.TreeLoader({  
            	dataUrl : 'resultsStatistical_getBanJi.do'//'dropListAction_getOrganTree.do'//
            }), 
            tbar:{
				items:[ 
				       "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.bjEidtWindow.hide();},scope:this}
				       ,"->",{xtype:"button",text:"选择",iconCls:"p-icons-submit",handler:function() {
				    	   var selectedNode =  Ext.getCmp("checkBanJi").getChecked();
				    	   if(selectedNode == ""){
				    		   Ext.MessageBox.alert("消息","请选择一条记录！");
				    		   return;
				    	   }
				    	   var bj="";					  
				    	   for(var i=0; i < selectedNode.length;i++) {	 
				    		   bj += selectedNode[i].id+",";
				    	   }
				    	   bj = bj.substring(0,bj.length-1);
				    	   this.bj=bj;
				    	   this.bjEidtWindow.hide();
				       },scope:this}
				  ]
			},  
            root : new Ext.tree.AsyncTreeNode({})  
			/*listeners: {  
           		click: function(node) {       
               		Ext.Msg.alert('消息', '你点击了: "' + node.attributes.text+"'");  
              	}
            }*/  
        });
		
		var panel=new Ext.Panel({
	    		id:"bjPanel",
	    		region:"north",
	    		width:"auto",
	    		height:400,
	    		layout:"border",
	    		border:false,
	    		items:[tree]
	    });
	    return panel;
    }
});
