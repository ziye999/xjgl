var kxx = "";
var lcid = "";
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
    	this.panels = [];
    	this.params=[];
    	this.kmMap = [];
    	this.paperId = '';
    	this.saveType = 'info';
    }, 
    /** 对组件设置监听 **/
    initListener:function(){
    	this.grid.getStore().on("beforeload",function(t,o){
    		t.setBaseParam("examround",Ext.getCmp("examround_find2").getValue());
    		t.setBaseParam("course",Ext.getCmp("course_find2").getValue());
    		t.setBaseParam("zjzt",Ext.getCmp("zjzt").getValue());
    	});
    },    
    initComponent :function(){
    	this.editForm   = this.createExamzjEditForm();
    	this.setblForm   = this.createSetblForm();
    	this.setkmblForm   = this.createSetkmblForm();
    	this.eidtWindow = this.createExamzjWindow();
    	this.eidtWindow.add(this.setblForm);
    	this.eidtWindow.add(this.setkmblForm);
 	   	this.eidtWindow.add(this.editForm);
		this.mainPanel = this.createMainPanel();
   		this.previewExamPanel = this.createPreviewExamForm();
   		
   		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.mainPanel,this.previewExamPanel]   
        }); 
	},
	/** 初始化界面 **/
    initFace:function(){
    	this.addPanel(this.tabPanel);
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
    createMainPanel:function(){ 
    	var sm =new Ext.grid.CheckboxSelectionModel({singleSelect:false});
    	var cm = [
    				sm,
    				{header: "年度季度",  align:"center", sortable:true, dataIndex:"xnxq"},
    				{header: "考试名称",  align:"center", sortable:true, dataIndex:"examname"},
    				{header: "考试批次",  align:"center", sortable:true, dataIndex:"subjectname"},
    				{header: "科目",  align:"center", sortable:true, dataIndex:"kch"},
    				{header: "试卷名称",  align:"center", sortable:true, dataIndex:"paperDesc"},
    				{header: "组卷类型",  align:"center", sortable:true, dataIndex:"paperType"},
    				{header: "考试时长",  align:"center", sortable:true, dataIndex:"examTime"},
    				{header: "考试日期",  align:"center", sortable:true, dataIndex:"examDate"},
    				{header: "试卷总分",  align:"center", sortable:true, dataIndex:"score"},
    				{header: "试卷状态",  align:"center", sortable:true, dataIndex:"statu"},
    				{header: "组卷状态",  align:"center", sortable:true, dataIndex:"zjzt"},
    				{header: "已组卷人数",  align:"center", sortable:true, dataIndex:"zjrs"},
    				{header: "应组卷人数",  align:"center", sortable:true, dataIndex:"yzjrs"}
    			];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"组卷管理",
			tbar:[ 
			      "->",{xtype:"button",text:"组卷",iconCls:"p-icons-update",handler:this.ksZj,scope:this}
			      //,{xtype:"button",text:"预览试卷",iconCls:"p-icons-update",handler:this.addExamSection,scope:this}
			      ,"->",{xtype:"button",text:"设置科目比例",iconCls:"p-icons-update",handler:this.setKmbl,scope:this}
			     // ,"->",{xtype:"button",text:"设置试题比例",iconCls:"p-icons-update",handler:this.setStbl,scope:this}
			      //,"->",{xtype:"button",text:"组卷信息设置",iconCls:"p-icons-update",handler:this.setZj,scope:this}
			    ],
			    page:true,
			    rowNumber:true,
			    region:"center",
			    excel:true,
			    action:"examzj_getListPage.do",
			    fields :["xnxq","examname","subjectname","kch","paperDesc","paperType","examTime","examDate","score","statu","zjzt","zjrs","yzjrs"],
			    border:false
		});
	
		var xn_find=new Ext.ux.form.XnxqField({name:"xn_find",id:"xn_find2",readOnly:true,width:180,
			callback:function(){					
				var id=Ext.getCmp("xn_find2").getCode();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
									autoLoad:false,
									url:'dropListAction_examround.do?params='+id,
									fields:["CODEID","CODENAME"]
								});
				Ext.getCmp("examround_find2").clearValue();  
                Ext.getCmp("examround_find2").store=newStore;  
                newStore.reload();
                Ext.getCmp("examround_find2").bindStore(newStore);
			}
		});		
		var examround_find2 = new Ext.ux.Combox({width:170,id:"examround_find2"});
		var course_find2 = new Ext.ux.Combox({width:170,id:"course_find2",dropAction:"course"});
		var zjzt = new Ext.form.ComboBox({ 
			id:"zjzt",
			name:"zjzt", 
		    triggerAction: 'all', 
		    mode: 'local',
		    width:160,   
		    editable:false, 
		    store: new Ext.data.ArrayStore({
		    	autoLoad : true,  
		        fields: [
		            'value',
		            'text'
		        ],
		        data: [["0", "未组"], ["1","已组已完成"],["2","已组未完成"]]
		    }),
		    valueField: 'value',
		    displayField: 'text'
		});
		
		
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.queryInfo,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		
		this.search = new Ext.form.FormPanel({
			region: "north",
			height:130,
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
					       	{html:"年度：",baseCls:"label_right",width:120},
					       	{items:[xn_find],baseCls:"component",width:180},
					       	{html:"考试名称：",baseCls:"label_right" ,width:120},
					       	{items:[examround_find2],baseCls:"component",width:180},
					       	{html:"科目：",baseCls:"label_right",width:120},
					       	{items:[course_find2],baseCls:"component",width:180},
					       	{html:"组卷状态：",baseCls:"label_right",width:120},
					       	{items:[zjzt],baseCls:"component",width:180},
					       	{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:160}
						] 
				}]  
			}]  
	    })
		return new Ext.Panel({layout:"border",region:"center",items:[this.search,this.grid]})
    },
    createPreviewExamForm:function(){
    	var kc_sel		= new Ext.ux.form.TextField({name:"kc_sel",id:"kc_sel",width:180});
    	var cx 			= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.previewExamInfo,scope:this});
		var cz 			= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.mainsearch.getForm().reset()},scope:this});
		var that = this;
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
					       {html:"题目名称：",baseCls:"label_right",width:170},
					       {items:[kc_sel],baseCls:"component",width:200},
					       {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
					      ] 
                  	}]  
		      	}]  
	    	})
		 
		this.dataPanel=new Ext.Panel({
    		region:'center',
    		border:false,
   			id:"dataPanel",
   			listeners:{
   				render : function(t){ 
   					var height = Ext.getCmp('sidePanel').getHeight()-14;
   					var iframe="<fieldset style='height:100%; width:100%; border:0px; padding:0;'>"+
   							"<iframe id='frmReport' name='frmReport' width='100%'"+
   									"height='"+height+"' src='examzj_previewExamInfo.do?paperId="+that.paperId+"' frameborder='0' scrolling='auto'"+
   									"style='border:1px dashed #B3B5B4;' ></iframe>"+
   						"</fieldset>";
   			    	Ext.getDom('dataPanel').innerHTML=iframe;
   				} 
   			}
   		});
		
		this.sidePanel = new Ext.Panel({
   			region:'center',
    		border:'border',
    		id:'sidePanel',
    		height:488,
    		style:'background-color:#B2E0F9',
   			tbar:[ 
				  "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.returnMainPanel,scope:this}
				  ],
			items:[this.dataPanel]
			
   		});
		
		return new Ext.Panel({
			layout: 'border',
	        region:'center',
	        autoScroll:true,
	        border: false,
	        split:true,
			margins: '2 0 5 5', 
			items:[this.sidePanel]
		});  
    },
    previewExamInfo:function() {
    	var height = Ext.getCmp('sidePanel').getHeight()-14;
		var iframe="<fieldset style='height:100%; width:100%; border:0px; padding:0;'>"+
				"<iframe id='frmReport' name='frmReport' width='100%'"+
						"height='"+height+"' src='examzj_previewExamInfo.do?tmmc="+Ext.getCmp("kc_sel").getValue()+"&paperId="+this.paperId+"' frameborder='0' scrolling='auto'"+
						"style='border:1px dashed #B3B5B4;' ></iframe>"+
			"</fieldset>";
    	Ext.getDom('dataPanel').innerHTML=iframe;	
    },
    addExamSection:function(){
    	var that = this;
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行预览！");
    		return;
    	} 
    	if(selectedBuildings[0].json.paperType=='个人个性组卷'){
    		Ext.MessageBox.alert("消息","个性组卷无法进行预览！");
    		return;
    	}
    	this.paperId = selectedBuildings[0].json.paperId;
    	this.tabPanel.setActiveTab(this.previewExamPanel);
    }, 
    returnMainPanel:function(){
    	kxx = "";
    	this.tabPanel.setActiveTab(this.mainPanel);
    	this.initQueryDate();
    	this.grid2.getStore().removeAll();
    	this.selectExamSubject();
    },
   setZj:function(v){ 
	   this.saveType = 'info';
	   var selectInfo = this.grid.getSelectionModel().getSelections();
	   if(selectInfo.length != 1){
		   Ext.MessageBox.alert("消息","请选择一条记录进行设置！");
	    	return;
	   }
	   this.setblForm.hide();
	   this.setkmblForm.hide();
	   this.editForm.show();
	   Ext.getCmp("kmid").setValue(selectInfo[0].json.kmid);
	   Ext.getCmp("paperId").setValue(selectInfo[0].json.paperId);
	   this.pType.setValue(selectInfo[0].json.ptype); 
	   this.statu.setValue(selectInfo[0].json.statuValue);
	   Ext.getCmp("score").setValue(selectInfo[0].get("score"));
	   Ext.getCmp("examTime").setValue(selectInfo[0].get("examTime"));
	   this.examDate.setValue(selectInfo[0].get("examDate"));
	   this.eidtWindow.setHeight(220);
	   this.eidtWindow.setWidth(800);
	   this.eidtWindow.doLayout(); 
	   this.eidtWindow.show(); 
   },
   createExamzjEditForm:function(){
	   	var kmid = new Ext.form.TextField({name:"kmid",id:"kmid",hidden:true});
	   	var paperId = new Ext.form.TextField({name:"paperId",id:"paperId",hidden:true});
	   	var examTime  = new Ext.form.TextField({name:"examTime",id:"examTime",readOnly:true,allowBlank:false,blankText:"考试时长不能为空！"});
	   	var score  = new Ext.form.NumberField({name:"score",id:"score",readOnly:true,allowBlank:false,blankText:"试卷总分不能为空！"});
		this.examDate  = new Ext.form.DateField({name:"examDate",width:160,allowBlank:false,format:'Y-m-d'});
		this.statu = new Ext.form.ComboBox({ 
			name:"statu",
		    triggerAction: 'all', 
		    mode: 'local',
		    width:160,
		    allowBlank:false,
		    blankText:'请选择试卷状态',
		    emptyText:'请选择试卷状态',
		    editable:false,
		    hiddenName:'statu',
		    store: new Ext.data.ArrayStore({
		    	autoLoad : true,  
		        fields: [
		            'value',
		            'text'
		        ],
		        data: [["0", "失效"], ["1","启用"]]
		    }),
		    valueField: 'value',
		    displayField: 'text'
		});
		this.pType = new Ext.form.ComboBox({ 
			name:"pType",
			width:160,
		    triggerAction: 'all', 
		    mode: 'local',
		    hiddenName:'pType',
		    allowBlank:false,
		    blankText:'请选择组卷类型',
		    emptyText:'请选择组卷类型',
		    editable:false,
		    store: new Ext.data.ArrayStore({
		    	autoLoad : true,  
		        fields: [
		            'value',
		            'text'
		        ],
		        data: [["0", "个人个性组卷"], ["1","统一组卷"]]
		    }),
		    valueField: 'value',
		    displayField: 'text'
		});
		 
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[			       
				{html:"组卷类型：",baseCls:"label_right",width:150},
				{items:[this.pType],baseCls:"component",width:200},
				{html:"考试时长：",baseCls:"label_right",width:150}, 
				{items:[examTime],baseCls:"component",width:200},
				{html:"考试日期：",baseCls:"label_right",width:150},
				{items:[this.examDate],baseCls:"component",width:200},
				{html:"总分：",baseCls:"label_right",width:150},
				{items:[score],baseCls:"component",width:200},
				{html:"试卷状态：",baseCls:"label_right",width:150},
				{items:[this.statu,kmid,paperId],baseCls:"component",width:200}
			]		
		});
		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
				{layout:'form',xtype:"fieldset",title:"组卷设置",items:[panel]}
			]});	 
   },
   createExamzjWindow:function(){
   	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.saveSetInfo,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:1050, 
			 	maximizable:true,
				autoScroll:true,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
			 	title:"组卷设置"});		
   },
   saveSetInfo:function(){ 
	   var that = this;	
	   var url = 'examzj_saveSetInfo.do';
	   if(this.saveType == 'bili'){
//		   if(!this.editForm.getForm().isValid()){
//			   Ext.MessageBox.alert("消息","您输入存在非法输入!");
//			   return;
//		   }
		   var arrs = Ext.getCmp("sttype").findByType(Ext.form.NumberField);
//		   var arrs_1 = Ext.getCmp("ny").findByType(Ext.form.NumberField);
		   var all = 0;
		   var t = 0;
		   var stss = new Array(); 
		   for ( var i = 0; i < arrs.length; i++) { 
			   var n = i +1;
			   if(n%2!=0){ 
				   t = arrs[i].getValue();
			   }else{ 
				   if(arrs[i].getValue()==0 && t>0){
					   Ext.MessageBox.alert("消息","每道题所占分为0表示题库无此类型题！所以不能设置题量");
					   return;
				   } 
				  if(t>0 && arrs[i].getValue()>0){
					   all = all +t*arrs[i].getValue();
//					   stss.push(t/arrs[i].getValue());
				   }				  
			   }
		   } 
		   if(all != Ext.getCmp("t_zf").getValue()){
			   Ext.MessageBox.alert("消息","设置试题的分数必须与总分一致，设置分为:"+all+";总分为:"+Ext.getCmp("t_zf").getValue());
			   return;
		   }
//		   all = 0;
//		   for ( var i = 0; i < arrs_1.length; i++) {
//			   all = all+arrs_1[i].getValue();
//		   }
//		   if(all != 100){
//			   Ext.MessageBox.alert("消息","难易比例设置必须为100%");
//			   return;
//		   }
//		   
//		   for(var i = 0;i<stss.length;i++){ 
//			   for ( var n = 0; n < arrs_1.length; n++) { 
//				   var pd = stss[i]*arrs_1[n].getValue()/100+'';
//				   if(pd.indexOf('.')>0){
//					   Ext.MessageBox.alert("消息","设置的难易比例与试题数计算不是整数");
//					   return;
//				   }
//			   }
//		   }
//		   
		   url = 'examzj_saveSetExamInfo.do';
		   this.setblForm.$submit({
			   action:url,
			   params: {'paperId':Ext.getCmp("paperId").getValue() }, 
			   handler:function(form,result,thiz){
				   if(result.success){
					   Ext.MessageBox.alert("消息","设置成功!请设置科目比例");
					   thiz.eidtWindow.hide();
					   thiz.grid.$load();
				   }
			   },
			   scope:this
		   });
	   } else if(this.saveType == 'info'){
		   if(!this.editForm.getForm().isValid()){
			   Ext.MessageBox.alert("消息","您输入存在非法输入!");
			   return;
		   }
		   this.editForm.$submit({
			   action:url,
			   handler:function(form,result,thiz){
				   if(result.success){ 
					   thiz.eidtWindow.hide();
					   thiz.grid.$load();
				   }
			   },
			   scope:this
		   });
	   }else if(this.saveType == 'kemu'){
		   var sjzf = Number(Ext.getCmp('yczfs').title);
		   var zf = 0;
		   for(var i = 0 ; i<that.panels.length;i++){
			   if(!this.setkmblForm.getForm().isValid()){
				   Ext.MessageBox.alert("消息","您输入存在非法输入!");
				   return;
			   }
			   zf = zf + Number(Ext.getCmp('zfs_'+that.panels[i]).getValue());
			   var arrs = Ext.getCmp("type"+that.panels[i]).findByType(Ext.form.NumberField);
//			   var kmzts = 0; 
//			   for ( var j = 0; j < arrs.length; j++) { 
//				   kmzts = kmzts+arrs[j].getValue();
//			   }
//			   if(kmzts!=Ext.getCmp("zts_"+that.panels[i]).getValue()){
//				   Ext.MessageBox.alert("消息","您设置的科目题数与总题数不符！");
//				   return;
//			   }
		   } 
		   if(zf!=sjzf){
			   Ext.MessageBox.alert("消息","试卷总分为"+sjzf+"分,您只设置了"+zf+"分!");
			   return;
		   }
		   url = 'examzj_saveKmblExamInfo.do'; 
		   this.setkmblForm.$submit({
			   action:url,
			   params: {'paperId':Ext.getCmp("paperId").getValue(),'kmMap':JSON.stringify(that.kmMap),'pms': JSON.stringify(that.params)}, 
			   handler:function(form,result,thiz){
				   if(result.success){
					   Ext.MessageBox.alert("消息","设置成功");
					   thiz.eidtWindow.hide();
					   thiz.grid.$load();
				   }
			   },
			   scope:this
		   });
	   }
   },
   setStbl:function(){
	   var selectInfo = this.grid.getSelectionModel().getSelections();
	   if(selectInfo[0].json.paperId=='' || selectInfo[0].json.paperId==null){
		   Ext.MessageBox.alert("消息","您先设置组卷信息!");
		   return;
	   }
	   var that = this;
	   this.saveType = 'bili';
	   if(selectInfo.length != 1){
		   Ext.MessageBox.alert("消息","请选择一条记录进行设置！");
	    	return;
	   } 
	   Ext.getCmp("paperId").setValue(selectInfo[0].json.paperId); 
	   if(Ext.getCmp("sttype").findByType(Ext.form.NumberField).length==0){
		   Ext.Ajax.request({
			   url: 'examzj_getSzInfo.do',
			   method:'post',
			   params:{paperId:selectInfo[0].json.paperId},
			   success: function(r, o) {  
				  var obj = Ext.decode(r.responseText); 
				  for ( var i = 0; i < obj.data.sttype.length; i++) {
					  Ext.getCmp("sttype").add({html:obj.data.sttype[i].text+": ",baseCls:"label_right",width:150});
					  Ext.getCmp("sttype").add({items:[new Ext.form.NumberField({id:obj.data.sttype[i].id,width:50,maxValue:100,minValue:0,value:0})],html:"道",baseCls:"component",width:100});
					  Ext.getCmp("sttype").add({items:[new Ext.form.NumberField({id:obj.data.sttype[i].id+"_fz",width:50,maxValue:100,minValue:0,value:obj.data.sttype[i].t_score,readOnly:true})],html:"分/题",baseCls:"component",width:100});
					  Ext.getCmp(obj.data.sttype[i].id).setValue(obj.data.sttype[i].value);
				  }
				  Ext.getCmp("sttype").add({html:"总分: ",baseCls:"label_right",width:150});
				  Ext.getCmp("sttype").add({items:[new Ext.form.TextField({id:"t_zf",width:50,maxValue:100,minValue:0,value:selectInfo[0].json.score,disabled:true})],html:"分",baseCls:"component",width:100});
//				  for ( var i = 0; i < obj.data.ny.length; i++) {
//					  Ext.getCmp("ny").add({html:obj.data.ny[i].text+": ",baseCls:"label_right",width:150});
//					  Ext.getCmp("ny").add({items:[new Ext.form.NumberField({id:obj.data.ny[i].id,width:50,maxValue:100,minValue:0,value:0})],html:"%",baseCls:"component",width:100});
//					  Ext.getCmp(obj.data.ny[i].id).setValue(obj.data.ny[i].value);
//				  }
				  that.setblForm.doLayout(); 
			   },
			   failure: function(r, o) {
				   Ext.MessageBox.alert("消息","系统初始化界面失败！");
			   }
			});
	   }else{   
		   Ext.Ajax.request({
			   url: 'examzj_getSzInfo.do',
			   method:'post',
			   params:{paperId:selectInfo[0].json.paperId},
			   success: function(r, o) { 
				   var obj = Ext.decode(r.responseText); 
					  for ( var i = 0; i < obj.data.sttype.length; i++) {
						  Ext.getCmp(obj.data.sttype[i].id).setValue(obj.data.sttype[i].value);
						  Ext.getCmp(obj.data.sttype[i].id+"_fz").setValue(obj.data.sttype[i].t_score);
					  }
					  for ( var i = 0; i < obj.data.ny.length; i++) {
						  Ext.getCmp(obj.data.ny[i].id).setValue(obj.data.ny[i].value);
					  }
			   }
		   });
	   }
	   this.editForm.hide(); 
	   this.setkmblForm.hide();
	   this.setblForm.show();
	   this.eidtWindow.setHeight(200);
	   this.eidtWindow.setWidth(800);
	   this.eidtWindow.doLayout(); 
	   this.eidtWindow.show();
   },
   setKmbl:function(){
	   //科目比例设置
	   var selectInfo = this.grid.getSelectionModel().getSelections();
//	   if(selectInfo[0].json.paperId=='' || selectInfo[0].json.paperId==null){
//		   Ext.MessageBox.alert("消息","您先设置组卷信息!");
//		   return;
//	   }
	   var that = this;
	   var flag = true;
	   this.saveType = 'kemu';
	   if(selectInfo.length != 1){
		   Ext.MessageBox.alert("消息","请选择一条记录进行设置！");
		   return;
	   } 
	   if(true){
		   Ext.Ajax.request({
			   url: 'examzj_getKmszInfo.do',
			   method:'post',
			   params:{paperId:selectInfo[0].json.paperId,kmid:selectInfo[0].json.kmid},
			   success: function(r, o) { 
				  var obj = Ext.decode(r.responseText); 
				  that.panels = [];
				  that.kmMap  = [];
				  that.params = [];
				  if(obj.success){
					  Ext.getCmp("paperId").setValue(obj.data.paperId);
					  that.setkmblForm.removeAll();
					  that.setkmblForm.add({id:'yczfs',layout:'form',xtype:"fieldset",title:selectInfo[0].json.score,hidden:true} );
					  for ( var i = 0; i < obj.data.txbl.length; i++) {
						  that.panels.push(obj.data.txbl[i].id);
						  var panel = new Ext.Panel({
							   	id:"type"+obj.data.txbl[i].id,
							   	width:1050,
								xtype:"panel",
								layout:"table", 
								baseCls:"table",
								layoutConfig: { 
									columns: 8
								}, 
								items:[ 
								]});	
						  for(var j = 0 ; j < obj.data.kch.length;j++){
							  if(obj.data.txbl[i].id==obj.data.kch[j].tid){
								  that.kmMap.push(j+':'+obj.data.kch[j].kch);
								  var kms = obj.data.kmts[obj.data.txbl[i].id+'_'+obj.data.kch[j].kch]; 
								  if(kms==null){
									  kms = 0;
								  }
								  var n = 0;
								  if(obj.data.kMap[obj.data.txbl[i].id+'_'+obj.data.kch[j].kch]!=null){
									  n = obj.data.kMap[obj.data.txbl[i].id+'_'+obj.data.kch[j].kch];
								  }
								  Ext.getCmp("type"+obj.data.txbl[i].id).add({html:obj.data.kch[j].kch+"<font color='red'>("+n+"): ",baseCls:"label_right",width:150});
								  if(n>obj.data.txbl[i].ts){
									  n = obj.data.txbl[i].ts;
								  }
								  Ext.getCmp("type"+obj.data.txbl[i].id).add({items:[new Ext.form.NumberField(
										  {id:"type"+obj.data.txbl[i].id+"_"+j,
											  width:50,maxValue:n,minValue:0,value:kms,decimalPrecision:0,
											  maxText:'试题库只有'+n+'道题!或超过了设置的总题数',
											  listeners:{ 
												  change:function(t,n,o){  
													  var tp = t.id.substr(4,t.id.indexOf('_')-4);
													  var fz = Ext.getCmp("typeFz"+tp).getValue();
													  Ext.getCmp("zts_"+tp).setValue(Number(Ext.getCmp("zts_"+tp).getValue())+n-o);
													  Ext.getCmp("zfs_"+tp).setValue(Number(Ext.getCmp("zts_"+tp).getValue())*fz);
												  }
											  }})],html:"道",baseCls:"component",width:100});
								  that.params.push("type"+obj.data.txbl[i].id+"_"+j);
							  }
						  }
						  var txts = obj.data.txbl[i].txts;
						  if(txts==null || txts==''){
							  txts = 0;
						  }
						  var txfz = Number(txts)*Number(obj.data.txbl[i].score);
						  if(isNaN(txfz)){
							  txfz = 0;
						  }
						  Ext.getCmp("type"+obj.data.txbl[i].id).add({html:"总题数: ",baseCls:"label_right",width:150});
						  Ext.getCmp("type"+obj.data.txbl[i].id).add({items:[new Ext.form.TextField({id:"zts_"+obj.data.txbl[i].id,width:50,disabled:true,value:txts})],html:"道",baseCls:"component",width:100});
						  Ext.getCmp("type"+obj.data.txbl[i].id).add({html:"总分数: ",baseCls:"label_right",width:150});
						  Ext.getCmp("type"+obj.data.txbl[i].id).add({items:[new Ext.form.TextField({id:"zfs_"+obj.data.txbl[i].id,width:50,disabled:true,value:txfz})],html:"道",baseCls:"component",width:100});
//						  Ext.getCmp("type"+obj.data.txbl[i].id).add({html:"其他: ",baseCls:"label_right",width:150});
//						  Ext.getCmp("type"+obj.data.txbl[i].id).add({items:[new Ext.form.NumberField({id:"qt_"+obj.data.txbl[i].id,width:50,value:obj.data.txbl[i].ts})],html:"道",baseCls:"component",width:100});
						  that.setkmblForm.add({layout:'form',xtype:"fieldset",title:obj.data.txbl[i].mc+'(每题'+obj.data.txbl[i].score+"分)",items:[panel]} );
						  Ext.getCmp("type"+obj.data.txbl[i].id).add({items:[new Ext.form.TextField(
								  {id:"typeFz"+obj.data.txbl[i].id,value:obj.data.txbl[i].score,hidden:true})]});
					  }
					  that.setkmblForm.doLayout(); 
					  that.editForm.hide();
					  that.setblForm.hide(); 
					  that.setkmblForm.show();
					  that.eidtWindow.setHeight(600);
					  that.eidtWindow.setWidth(1050);
					  that.eidtWindow.doLayout(); 
					  that.eidtWindow.show(); 
				  }else{
					  Ext.MessageBox.alert("消息",obj.msg);
					  flag = false;
					  return ;
				  }
			   },
			   failure: function(r, o) {
				   Ext.MessageBox.alert("消息","系统初始化界面失败！");
			   }
			});
	   } 
   },
   createSetkmblForm:function(){ 
//	   var panel = new Ext.Panel({
//		   	id:'kmPanel',
//			xtype:"panel",
//			layout:"table", 
//			baseCls:"table",
//			layoutConfig: { 
//				columns: 6 
//			}, 
//			items:[			         
//			]});	
	    
	   return new Ext.ux.FormPanel({
		   frame:false, 
		   width:1050,
		   defaults:{anchor:"100%"},
		   items:[
		   ]});

   },
   createSetblForm:function(){ 
	   var panel = new Ext.Panel({
		   	id:'sttype',
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 6 
			}, 
			items:[			         
			]});	
	   
	   var panel2 = new Ext.Panel({ 
		   	id:'ny',
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[			         
			]});	
	   
	   return new Ext.ux.FormPanel({
		   frame:false,
		   defaults:{anchor:"100%"},
		   items:[
		          {layout:'form',xtype:"fieldset",title:"试卷比例设置",items:[panel]}
//		          ,{layout:'form',xtype:"fieldset",title:"难易比例设置",items:[panel2]}
		   ]});

   },
   ksZj:function(){ 
	   var selectInfo = this.grid.getSelectionModel().getSelections();
	   if(selectInfo.length != 1){
		   Ext.MessageBox.alert("消息","请选择一条记录进行设置！");
	    	return;
	   }
	   Ext.Msg.wait('正在组卷，请稍候','提示'); 
	   Ext.Ajax.request({
		   url: 'examzj_ksZj.do',
		   method:'post',
		   timeout:900000,
		   params:{paperId:selectInfo[0].json.paperId,kmid:selectInfo[0].json.kmid},
		   success: function(r, o) {
			   Ext.Msg.hide();
			   Ext.MessageBox.alert("成功",Ext.decode(r.responseText).msg);
		   },
		   failure:function(r,o){
			   if(r.status){
				   Ext.MessageBox.alert("<font color='red'>超时</font>","发送或处理超时!");
			   }else{
				   Ext.Msg.hide();
				   Ext.MessageBox.alert("<font color='red'>失败</font>",Ext.decode(r.responseText).msg);
			   }
			   
		   }
	   });
   },
   queryInfo:function(){
	   if(Ext.getCmp("xn_find2").getValue()!=null && Ext.getCmp("xn_find2").getValue() != ''){
		   if(Ext.getCmp("examround_find2").getValue()==null || Ext.getCmp("examround_find2").getValue()==''){
			   Ext.MessageBox.alert("消息","请选择到考试名称");
			   return;
		   }
	   }
	   this.grid.$load();
   }
});