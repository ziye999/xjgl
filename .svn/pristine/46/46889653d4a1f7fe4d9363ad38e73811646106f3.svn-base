var datasourceWeek = new Ext.data.Store();

var pz = "";
var bmlcid1 = "";
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
    	this.editWindow.on("hide",function(){
    		this.editForm.$reset();
    	},this);
    	this.grid.on("rowdblclick",function(grid, rowIndex, e ){//grid网格组件可用于以表格格式显示数据。//是打开网格grid.on
    		// form 窗体小部件是从用户获取数据。
//    		var xnxqid = grid.store.getAt(rowIndex).get("XNXQ_ID");//获取被操作的数据记录
//    		var xnmc = grid.store.getAt(rowIndex).get("XNMC");
//    		
//    		var xqmc = grid.store.getAt(rowIndex).get("XQMC");
//    		Ext.getCmp("xn").setCode(xnmc+","+xqmc);
//    		var xxkssj = grid.store.getAt(rowIndex).get("XXKSSJ");
//    		var xxjssj = grid.store.getAt(rowIndex).get("XXJSSJ");
    		this.editForm.getForm().findField("JFPZ").setValue(grid.store.getAt(rowIndex).get("JFPZ"));
    		this.editForm.getForm().findField("SHZT").setValue(grid.store.getAt(rowIndex).get("SHZT"));
	
    		this.eidtWindow.show();
    	},this)
    },   
   	initComponent :function(){   		
   		this.editForm   = this.createExamroundForm();
   		this.editWindow = this.createWindow();
   		this.editWindow.add(this.editForm);
   		this.editForm1   = this.createExamroundForm1();
   		this.editWindow1 = this.createWindow1();
   		this.editWindow1.add(this.editForm1);
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true,
			listeners:{
    			"selectionchange":function(sm){
    				var str = sm.getSelections();
    				if(str[0].get("SHFLAG")=="通过"||str[0].get("SHFLAG")=="不通过" ||str[0].get("SHFLAG")=="已上报"){
    					Ext.getCmp('sh').setDisabled(true);
    					
    				}else{
    				
    					Ext.getCmp('sh').setDisabled(false);
    				
    				}
    			}
    		}
		});//1.CheckboxSelectionModel简写sm 2.实现sm是否显示
		var cm = [
			sm,
			{header: "所属法制办",   align:"center", sortable:true, dataIndex:"SSZGJYXZMC"},
			{header: "参考单位",   align:"center", sortable:true, dataIndex:"XXMC"},
			{header: "申请人",   align:"center", sortable:true, dataIndex:"XZXM"},
			{header: "联系电话",   align:"center", sortable:true, dataIndex:"BGDH"},
			{header: "邮政编码",   align:"center", sortable:true, dataIndex:"YZBM"},
			{header: "审核状态",   align:"center", sortable:true, dataIndex:"zt"},
		
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			tbar:{
				cls:"ext_tabr",
				items:[ 
				       "->",{xtype:"button",text:"审核通过",id:"sb", iconCls:"p-icons-save",handler:this.updateExaminee,scope:this}
//				       ,"->",{xtype:"button",text:"审核",id:"sh", iconCls:"p-icons-save",handler:this.update,scope:this}
				     
				      ]
			},
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			excelTitle:"考生报名-考生名单审核",
			action:"sq_getSq.do",
			fields :["SSZGJYXZMC","XXMC","XZXM","BGDH","YZBM","XX_JBXX_ID","zt"],
			border:false
		});
		
		var ckdw = new Ext.form.TextField({fieldLabel:"",id:"ckdw_find",width:180});
		var zkdw = new Ext.form.TextField({fieldLabel:"",id:"zkdw_find",width:180});
		var zt = new Ext.form.ComboBox({
			id:'zt',
			mode: 'local', 
			triggerAction: 'all',   
			editable:false,
			width:180,
			value:'0',
			store: new Ext.data.ArrayStore({
				id: 0,
				fields: ['value','text'],
			    data: [['2', '全部'],['0', '未审核'],['1', '审核通过']]
			}),
			valueField: 'value',
			displayField: 'text'
		});


		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamSubject,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});

		this.search = new Ext.form.FormPanel({
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
						columns: 4
					}, 
					baseCls:"table",
					items:[
					
						{html:"参考单位：",baseCls:"label_right",width:120},
						{items:[ckdw],baseCls:"component",width:210},
						{html:"组考单位：",baseCls:"label_right",width:120},
						{items:[zkdw],baseCls:"component",width:210},
						{html:"审核状态：",baseCls:"label_right",width:120},
						{items:[zt],baseCls:"component",width:210},
						{layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:160}
					] 
				}]  
			}]  
    	})
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"border",items:[this.search,this.grid]});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
  
    //添加数据
    createExamroundForm:function(){	
    	//人数
    	var rs = new Ext.form.TextField({name:"rs",id:"rs",value:"100",width:220,maxLength:3,allowBlank:false,readOnly:true});
		//缴费金额
      	var fy = new Ext.form.TextField({name:"timelen",id:"timelen",value:"95元/人",width:220,maxLength:7,allowBlank:false,blankText:"报名费用不能为空！",readOnly:true});		
		//缴费凭证
		var jfpz = new Ext.BoxComponent({autoEl: {tag: 'img',src: '',id:"jfpz"},id:"jfpz",width:600,height:280});
		//缴费金额
		var jfje = new Ext.form.TextField({name:"jfje",id:"jfje",width:220,maxLength:5,allowBlank:true,readOnly:true});
		//联系人
		var lxr = new Ext.form.TextField({name:"lxr",id:"lxr",width:220,allowBlank:true,readOnly:true});
		//联系电话
		var lxdh = new Ext.form.TextField({name:"lxdh",id:"lxdh",width:220,allowBlank:true,readOnly:true});
		//审核
		var sh1 = new Ext.form.TextField({name:"sh1",id:"sh1",width:220,allowBlank:false,regexText:"审核不通过原因不能为空"});
		var upload = new Ext.Button({x : 19,y : -10,cls : "base_btn",id:"upload",text : "查看大图",handler:this.ckp,scope : this});
		var ckjfpzpath = new Ext.form.TextField({id:"ckjfpzpath",name:"ckjfpzpath",hidden:true});
		var download = new Ext.Button({id:"download",x : 19,y : -10,cls : "base_btn",text : "下载凭证",handler:this.downloadZip,scope : this});
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4
			},
			items:[
					{html:"报名人数：",baseCls:"label_right",width:120},
					{html:"<font class='required'>*</font>",items:[rs],baseCls:"component",width:240},
					{html:"缴费标准：",baseCls:"label_right",width:120},
					{html:"<font class='required'>*</font>",items:[fy],baseCls:"component",width:240},							
					{html:"应缴费金额：",baseCls:"label_right",width:120},
					{html:"",items:[jfje],baseCls:"component",width:240},
					{html:"联系人：",baseCls:"label_right",width:120},
					{html:"",items:[lxr],baseCls:"component",width:240},
					{html:"联系电话：",baseCls:"label_right",width:120},
					{html:"",items:[lxdh],baseCls:"component",width:240},
					{html:"审核说明：",baseCls:"label_right",width:120},
					{html:"<font class='required'>*</font>",items:[sh1],baseCls:"component",width:240},
					{html:"查看凭证： ",baseCls:"label_right",width:120},
					{items:[upload],baseCls:"",width:240},
					{html:"",baseCls:"label_right",width:120},
					{items:[],baseCls:"",width:240},
					
					{html:"缴费凭证：",baseCls:"label_right",width:120,rowspan:8},
					{items:[jfpz,download],baseCls:"component",width:240,rowspan:8}
				  
					
					
					
			       ]		
		});
		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"99%"},
			items:[
					{layout:'form',xtype:"fieldset",title:"审核管理",items:[panel]},
			]});					
    },    
    createExamroundForm1:function(){	
    	
		//缴费凭证
		var jfpz1 = new Ext.BoxComponent({autoEl: {tag: 'img',src: 'img/basicsinfo/mrzp_img.jpg',id:"jfpz1"},id:"jfpz1",width:900,height:480});
	
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4
			},
			items:[		
					{items:[jfpz1],baseCls:"component",width:600,rowspan:8}

			       ]		
		});
		
		
		return new Ext.ux.FormPanel({
			frame:false,
			
			  collapsible: true,
			items:[
					{layout:'form',  width:1200,xtype:"fieldset",title:"凭证查看",items:[panel]},
			]});					
    },    
    ckp:function(){	
    	this.editWindow1.show();
    	Ext.getCmp("jfpz1").getEl().dom.src = pz;
    	
    },
    createWindow1:function(){
//		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-delete",handler:function(){this.editWindow1.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:1350,
				height:600,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 

//					 	 "->",cancel

					 	

					 
					  ]
				},
				listeners:{
			 		hide:function(){
			 			this.editForm1.form.reset();
			 		},scope:this},
	     		items:[this.editForm1],
			 	title:"凭证大图查看"});		
    },
    	   
    createWindow:function(){
    	var _b_cancel = new Ext.Button({text:"通过",iconCls:"p-icons-save",handler:this.updateExaminee,scope:this});
    	var save = new Ext.Button({text:"不通过",iconCls:"p-icons-checkclose",handler:this.addRound,scope:this});
    	
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-delete",handler:function(){this.editWindow.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:900,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
					 	,"->",_b_cancel
			 			 ,"->",save
					  ]
				},
				listeners:{
			 		hide:function(){
			 			this.editForm.form.reset();
			 		},scope:this},
	     		items:[this.editForm],
			 	title:"审核管理"});		
    },
    addRound:function(){

  
    	var thiz = this;
    	this.editForm.$submit({
    		action:"bmsh_btg.do",
    		params: {
    			'bmlcid':bmlcid1		
    		},
    		
    		handler:function(form,result,thiz){
    			thiz.editWindow.hide();
    			thiz.grid.$load();
    			thiz.selectExamSubject();
    		},
    		scope:this
    	});
    },    
  

    update:function(){    	
    	var selectedXnxq = this.grid.getSelectionModel().getSelections();
    	if(selectedXnxq.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行审核！");
    		return;
	    }
    	var bmlcid = selectedXnxq[0].get("BMLCID");
    	bmlcid1 = bmlcid;
     	this.editWindow.show(null,function(){
    		this.editForm.$load({
    		  	params:{'bmlcid':bmlcid},
    		  	action:"bmsh_getlist.do",
    		  	handler:function(form,result,scope){  
    		  			
    		  		var url = "";
    				if(result.data.JFPZ==undefined){
    					url = "";
    				}else{
    					url = "uploadFile/certificates/"+result.data.JFPZ;
    					var objtype=url.substring(url.lastIndexOf(".")).toLowerCase();
    			
						if(/\.(zip|ZIP)$/.test(objtype)){
    						Ext.getCmp("jfpz").hide();
    						Ext.getCmp("download").show();
							Ext.getCmp("upload").hide();
//    						ckjfpz.hidden=true;
//    						download.hidden=false;
    						Ext.getCmp("ckjfpzpath").setValue(url);
    					}else{
    						Ext.getCmp("jfpz").show();
    						Ext.getCmp("download").hide();
    						Ext.getCmp("upload").show();
//    						ckjfpz.hidden=false;
//    						download.hidden=true;
    						pz=url;
    	    				Ext.getCmp("jfpz").getEl().dom.src = url;
    					}
    				}
    		  		form.findField("rs").setValue(result.data.SL);//人数
    		  		form.findField("lxr").setValue(result.data.LXR);//联系人
    		  		form.findField("lxdh").setValue(result.data.LXDH);//联系电话
    		  		var sl =result.data.SL;
    		  		var jf = sl *95;
    		  		form.findField("jfje").setValue(sl*95);//缴费金额
    		 	}
    		  		
    		 });
    	},this)
    },
    updateExaminee:function(){
  
    	var selectedXnxq = this.grid.getSelectionModel().getSelections();
    	if(selectedXnxq.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行审核！");
    		return;
	    }
    	var xxjbxxid = selectedXnxq[0].get("XX_JBXX_ID");
    	var thiz=this;
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"是否通过审核",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,    			
    		fn:function(b){
    			if(b=='ok'){
    				JH.$request({
    					params:{'xxjbxxid':xxjbxxid},
    					action:"sq_upZt.do",
    					handler:function(){
    						thiz.grid.$load();
    						thiz.editWindow.hide();
    						thiz.selectExamSubject();
    					}
    				}) 

    				
    			}
    		}
    	})
    },
   
    updateZt:function(){
    	var selectedXnxq = this.grid.getSelectionModel().getSelections();
    	if(selectedXnxq.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行审核！");
    		return;
	    }
    	var xxjbxxid = selectedXnxq[0].get("XX_JBXX_ID");
    	alert(xxjbxxid)
    	var thiz = this;
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"您确定要审核通过吗？",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,
    		fn:function(b){
    			if(b=='ok'){
    				Ext.Ajax.request({
    					url:'sq_updte.do',
    		       		params:{'xxjbxxid':xxjbxxid},
    		       		success: function(resp,opts) {
    		       			var respText = Ext.util.JSON.decode(resp.responseText);
    		       			if (respText.msg!='') {
    		       				Ext.MessageBox.alert("提示",respText.msg);
    		       			}
    		       			thiz.grid.$load();
    		       			thiz.selectExamSubject();
    		       		},
    		       		
    				});
    			}
    		}
    	})
    },
    

    selectExamSubject:function(){
		
		var ckdw=Ext.getCmp('ckdw_find').getValue();
		var zt = Ext.getCmp('zt').getValue();
		var zkdw = Ext.getCmp('zkdw_find').getValue();
		this.grid.$load({"ckdw":ckdw,"zkdw":zkdw,"zt":zt});
	},


});



