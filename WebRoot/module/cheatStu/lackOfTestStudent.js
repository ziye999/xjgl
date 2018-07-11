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
    
    },
    initComponent :function(){    	    		
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
			sm,
			{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   align:"center", sortable:true, dataIndex:"XQM"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME",renderer : renderdel,listeners:{
				"click":function(){    //监听"select"事件
					this.showLackOfTestStudent();
				},scope:this}
			},
			{header: "起始日期",   align:"center", sortable:true, dataIndex:"STARTDATE"},
			{header: "结束日期",   align:"center", sortable:true, dataIndex:"ENDDATE"},
			{header: "缺考人数",   align:"center", sortable:true, dataIndex:"WJRS"}
		];
		function renderdel(value, cellmeta, record, rowIndex, columnIndex, store){
			var lcid = store.getAt(rowIndex).get('LCID'); 
			var str = "<a href='#' id='"+lcid+"'>"+value+"</a>";
			return str;  
		} 
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"违纪考生管理——缺考考生管理",
			tbar:["->", {xtype : "button",text : "自动登记缺考考生",iconCls : "p-icons-upload",handler:this.submitQk,scope : this} 
			      ,"->",{xtype:"button",text:"导入缺考考生",iconCls:"p-icons-upload",handler:this.importLackOfTestStudent,scope:this}
			      ,"->",{xtype:"button",text:"下载模板",iconCls:"p-icons-download",handler:this.dowloadLackOfTestStudent,scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			action:"lackOfTestStudent_getListPage.do",
			fields :["LCID","XN","XQM","EXAMNAME","STARTDATE","ENDDATE","WJRS"],
			border:false
		});
		//搜索区域
		var xn_find = new Ext.ux.form.XnxqField({width:190,id:"ltxn_find",readOnly:true});
		//var xn_find = new Ext.ux.Combox({dropAction:"xn", width:170,id:"xn_find"});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamRound,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.search = new Ext.form.FormPanel({
			region: "north",
			height:90,
			items:[{  
				layout:'form',  
				xtype:'fieldset',  
				style:'margin:10',
				title:'查询条件',  
				items: [{
                    	xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 3
						}, 
						baseCls:"table",
						items:[
						       {html:"年度：",baseCls:"label_right",width:140},
						       {items:[xn_find],baseCls:"component",width:190},
						       {layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:160}
						] 
                 	}]  
			}]  
	    });
		
		this.panel=new Ext.Panel({
				id:"panelC",
	    		region:"north",
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.search,this.grid]
		});	    		    		    	
	},
	createShowLackOfTestStudent :function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "参考单位",   align:"center", sortable:true, dataIndex:"XXMC"},
			{header: "身份证件号",   	 align:"center", sortable:true, dataIndex:"SFZJH"},
			{header: "姓名",   	 align:"center", sortable:true, dataIndex:"XM"},
			{header: "性别",   	 align:"center", sortable:true, dataIndex:"MC"},
			{header: "考试批次",   align:"center", sortable:true, dataIndex:"SUBJECTNAME"},
			{header: "缺考原因",   align:"center", sortable:true, dataIndex:"DETAIL"}
		];
		
		this.lackOfTestStudentGrid = new Ext.ux.GridPanel({
			id:"lackOfTestStudentGrid",
			cm:cm,
			sm:sm,
			tbar:[ 
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}
			      ,"->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteLackOfTestStudent,scope:this}
			      ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateLackOfTestStudent,scope:this}
			      ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.showEidtWindow()},scope:this}
			   ],
			page:true,
			rowNumber:true,
			region:"center",
			action:"lackOfTestStudentD_getListPage.do",
			fields :["QKID","LCID","DETAIL","KSCODE","SFZJH","XJH","XM","KMID","SUBJECTNAME","XXMC","MC","DM"],
			border:false
		});
		//搜索区域
		//参考单位
		var xuexiao_find= new Ext.ux.form.OrganField({name:"sup_organ_sel",readOnly:true});
		//考生姓名，身份证件号
		var xm_kh_xjh_find = new Ext.form.TextField({fieldLabel:"",id:"xm_kh_xjh_find",name:"xm_kh_xjh_find",maxLength:50,width:170});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectLackOfTestStudent,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.lackOfTestStudentSearch.getForm().reset()},scope:this});
		if(mBspInfo.getUserType() == "2") {
			this.lackOfTestStudentSearch = new Ext.form.FormPanel({
				region: "north",
				height:90,
				items:[{  
					layout:'form',  
					xtype:'fieldset',  
					style:'margin:10',
					title:'查询条件',  
					items: [{
							xtype:"panel",
							layout:"table", 
							layoutConfig: { 
								columns: 3
							}, 
							baseCls:"table",  
							items:[
								//{html:"参考单位：",baseCls:"label_right",width:140},
								//{items:[xuexiao_find],baseCls:"component",width:190},
								{html:"姓名/身份证件号：",baseCls:"label_right",width:140},
								{items:[xm_kh_xjh_find],baseCls:"component",width:190},
								{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:190}							
							] 
	                    }]  
			       }]  
		    });
		}else {
			this.lackOfTestStudentSearch = new Ext.form.FormPanel({
				region: "north",
				height:90,
				items:[{  
					layout:'form',  
					xtype:'fieldset',  
					style:'margin:10',
					title:'查询条件',  
					items: [{
							xtype:"panel",
							layout:"table", 
							layoutConfig: { 
								columns: 5
							}, 
							baseCls:"table",  
							items:[
								{html:"参考单位：",baseCls:"label_right",width:140},
								{items:[xuexiao_find],baseCls:"component",width:190},
								{html:"姓名/身份证件号：",baseCls:"label_right",width:140},
								{items:[xm_kh_xjh_find],baseCls:"component",width:190},
								{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:190}							
							] 
	                    }]  
			       }]  
		    });
		}		
		this.lackOfTestStudentPanel=new Ext.Panel({
	    		id:"lackOfTestStudentPanel",
	    		title:"缺考考生",
	    		region:"north",
	    		height:500,
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.lackOfTestStudentSearch,this.lackOfTestStudentGrid]
		});	    	
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.panel_top=new Ext.Panel({
    			layout:"fit",
	    		id:"panel_topC",
	    		region:"north",
	    		border:false,
	    		items:[this.panel]
	    });
    	this.addPanel({layout:"fit",items:[this.panel_top]});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
    showEidtWindow:function() {
    	//this.editForm.$reset();
    	Ext.getCmp("qkid_lr").setValue("");
	   	Ext.getCmp("lcid_lr").setValue("");
	   	Ext.getCmp("kh_lr").setValue("");
	   	//Ext.getCmp("xjh_lr").setValue("");
	   	Ext.getCmp("xm_lr").setValue("");		   		
	   	Ext.getCmp("qkyy_lr").setValue("");
	   	Ext.getCmp("xb").setValue("");
	   	Ext.getCmp("km").setValue("");	
	   	this.editForm.getForm().findField("xb_lr").setValue("");
	   	this.editForm.getForm().findField("km_lr").setValue("");
		this.eidtWindow.show();  	
    },
    //显示缺考考生信息
	showLackOfTestStudent:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var ids =selectedBuildings[0].get("LCID");
    	this.lcid=ids;
    	this.createShowLackOfTestStudent();
    	var panel=Ext.getCmp("panel_topC");
  		panel.remove(Ext.getCmp("panelC"));
  		panel.add(this.lackOfTestStudentPanel);
  		panel.doLayout(false);
  		this.lackOfTestStudentGrid.$load({"lcId":this.lcid});
  		
  		this.editForm   = this.createaddLackOfTestStudent();
   		this.eidtWindow = this.createaddLackOfTestStudentWindow();
   		this.eidtWindow.add(this.editForm);
    },
    //查询考试轮次
    selectExamRound:function(){
    	var xnxq=Ext.getCmp('ltxn_find').getCode();
    	if(xnxq!="" && typeof(xnxq) != "undefined"){
    		var xn=xnxq.split(",")[0];
    		var xq=xnxq.split(",")[1];
    		this.grid.$load({"xn":xn,"xq":xq});
    	}else{
    		this.grid.$load({"xn":"","xq":""});
    	}
    },
    //查询缺考考生
    selectLackOfTestStudent:function(){    		
    	var xm_kh_xjh=Ext.getCmp('xm_kh_xjh_find').getValue();
    	var schoolId="";
    	if(mBspInfo.getUserType() != "2") {    	
    		schoolId=this.lackOfTestStudentSearch.getForm().findField('sup_organ_sel').getCodes();
    	}    	
    	this.lackOfTestStudentGrid.$load({"lcId":this.lcid,"xm_kh_xjh":xm_kh_xjh,"schoolId":schoolId});
    },
    //返回到轮次页面
    fanhui:function(){
    	this.initComponent();
    	var panel=Ext.getCmp("panel_topC");
  		panel.remove(Ext.getCmp("lackOfTestStudentPanel"));
  		panel.add(this.panel);
  		panel.doLayout(false);
  		this.grid.$load();
    },
    submitQk:function(){
		var selected = this.grid.getSelectionModel().getSelected();
		var selected = this.grid.getSelectionModel().getSelections();
		if (selected.length != 1) {
			Ext.MessageBox.alert("消息", "请选择一条考试记录登记！");
			return;
		}
		var selected = this.grid.getSelectionModel().getSelections();
		this.lcid = selected[0].get("LCID");
		var lcidP = this.lcid;		
    	var thiz = this;
		Ext.MessageBox.show({
			title:"消息",
			msg:"您确定要自动生成缺考考生信息吗?",
			buttons:Ext.MessageBox.OKCANCEL,
			icon:Ext.MessageBox.QUESTION,    			
			fn:function(b){
				if(b=='ok'){
					JH.$request({
						timeout: 3600000,
						params:{
							'lcId':lcidP
						},
						action:"lackOfTestStudent_submitQk.do",
						handler:function(){    							
							thiz.selectExamRound();
						}
					})    					
				}
			}
		})		
	},
    //修改缺考考生信息
    updateLackOfTestStudent:function(){    	
    	var selected =  this.lackOfTestStudentGrid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.lackOfTestStudentGrid.getSelectionModel().getSelections();
    	if(selectedBuildings.length>1){
    		Ext.MessageBox.alert("消息","一次只能修改一条记录！");
    		return;
    	}
    	Ext.getCmp("qkid_lr").setValue(selectedBuildings[0].get("QKID"));
	   	Ext.getCmp("lcid_lr").setValue(selectedBuildings[0].get("LCID"));
	   	Ext.getCmp("kh_lr").setValue(selectedBuildings[0].get("SFZJH"));
	   	//Ext.getCmp("xjh_lr").setValue(selectedBuildings[0].get("XJH"));
	   	Ext.getCmp("xm_lr").setValue(selectedBuildings[0].get("XM"));		   		
	   	Ext.getCmp("qkyy_lr").setValue(selectedBuildings[0].get("DETAIL"));
	   	Ext.getCmp("xb").setValue(selectedBuildings[0].get("DM"));
	   	Ext.getCmp("km").setValue(selectedBuildings[0].get("KMID"));	
	   	this.editForm.getForm().findField("xb_lr").setValue(selectedBuildings[0].get("DM"));
	   	this.editForm.getForm().findField("km_lr").setValue(selectedBuildings[0].get("KMID"));
	   	this.eidtWindow.show(); 	
    },
    //删除缺考考生
    deleteLackOfTestStudent:function(){
    	var selected =  this.lackOfTestStudentGrid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择需要删除的记录！");
    		return;
    	}
    	var selectedBuildings = this.lackOfTestStudentGrid.getSelectionModel().getSelections();
    	var ids =new Array();
    	for (var i = 0; i < selectedBuildings.length; i++) {
    		ids[i]=selectedBuildings[i].get("QKID");
    	}
      	var thiz=this;
      	Ext.MessageBox.show({
			title:"消息",
			msg:"您确定要删除吗?",
			buttons:Ext.MessageBox.OKCANCEL,
			icon:Ext.MessageBox.QUESTION,
			
			fn:function(b){
				if(b=='ok'){
					Ext.Ajax.request({   
			       		url:'lackOfTestStudent_deleteLackOfTestStudents.do',
			       		params:{
			        		"ids":ids
			        	},
			        	success: function(resp,opts) {
			        		var respText = Ext.util.JSON.decode(resp.responseText);
			        		Ext.MessageBox.alert("提示",respText.msg);
			        		thiz.selectLackOfTestStudent();
			        	},
			        	failure: function(resp,opts){
			        		Ext.Msg.alert('错误', "删除失败！");
			        	}        	
			      	});					
				}
			}
		});      	       
    },
    //下载导入缺考考生模板
    dowloadLackOfTestStudent:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择考试轮次！");
    		return;
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var ids =selectedBuildings[0].get("LCID");
    	this.lcid=ids;
    	window.location.replace("lackOfTestStudent_outExcel.do?lcId="+this.lcid);
    	//window.open("../export/excel/temp/lackOfTestStudent_temp.xls");
    },
    //-----------------------录入缺考考生开始----------------------------------
    createaddLackOfTestStudentWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.LuRuExamTeacheSubmit,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide()},scope:this});
		return new Ext.ux.Window({
				width:700,
			 	title:"缺考考生信息维护",
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					       "->",cancel
					       ,"->",save
					      ]
			 	},
			 	buttonAlign:"center"});			
    },
    createaddLackOfTestStudent:function(){
    	var lcid = new Ext.form.TextField({id:"lcid_lr",hidden:true,name:"lcId",value:this.lcid});
    	var qkid = new Ext.form.TextField({id:"qkid_lr",hidden:true,name:"qkId"});		
		var kh_lr = new Ext.form.TextField({name:"kh",id:"kh_lr",width:170,maxLength:100,maxLength:20,regex:/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/,allowBlank:false,blankText:"身份证件号不能为空！"});
		//var xjh_lr = new Ext.form.TextField({name:"xjh",id:"xjh_lr",width:170,maxLength:50,allowBlank:false,blankText:"学号不能为空！"});
		var xm_lr = new Ext.form.TextField({name:"xm",id:"xm_lr",width:170,maxLength:50,allowBlank:false,blankText:"姓名不能为空！"});
		var xbm   = new Ext.form.TextField({hidden:true,name:"xb",id:"xb"});
		var xb_lr = new Ext.ux.Combox({name:"xb_lr",dropAction:"sys_enum_xb",width:170,listeners:{
			"select":function(){
				Ext.getCmp("xb").setValue(this.getValue());
			}
		}});
		var kmid 	= new Ext.form.TextField({hidden:true,name:"km",id:"km"});
		var km_lr = new Ext.ux.Combox({name:"km_lr",dropAction:"getKskmBykslc",width:170,allowBlank:false,blankText:"缺考科目不能为空！",params:this.lcid,listeners:{
			"select":function(){
				Ext.getCmp("km").setValue(this.getValue());
			}
		}});
		var qkyy_lr = new Ext.form.TextArea({name:"qkyy",id:"qkyy_lr",width:480,height:48,maxLength:500,allowBlank:false,blankText:"缺考原因不能为空！"});

		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[
				{html:"身份证号：",baseCls:"label_right",width:130},
				{html:"<font class='required'>*</font>",items:[kh_lr],baseCls:"component",width:190},
				//{html:"学号：",baseCls:"label_right",width:130},
				//{html:"<font class='required'>*</font>",items:[xjh_lr],baseCls:"component",width:190},
				{html:"姓名：",baseCls:"label_right",width:130},
				{html:"<font class='required'>*</font>",items:[xm_lr],baseCls:"component",width:190},
				{html:"性别：",baseCls:"label_right",width:130},
				{html:"<font class='required'>*</font>",items:[xb_lr,xbm],baseCls:"component",width:190},
				{html:"缺考科目：",baseCls:"label_right",width:130},
				{html:"<font class='required'>*</font>",items:[km_lr,kmid],baseCls:"component",width:190},
				//{html:"",baseCls:"label_right",width:310,colspan:2 },				
				{html:"缺考原因：</br>（违纪缺考情况描述）",baseCls:"label_right",width:130},
				{html:"<font class='required'>*</font>",items:[qkyy_lr],baseCls:"component",width:503,height:60,colspan:3}				
			]		
		});
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
			       {layout:'form', xtype:"fieldset",title:"缺考考生信息维护",items:[panel]},
			       {items:[lcid,qkid]}
			]
		});				
    },
    LuRuExamTeacheSubmit:function(){
    	this.editForm.$submit({
    		params:{'lcId':this.lcid},
    		action:"lackOfTestStudent_saveLackOfTestStudent.do",
    		handler:function(form,result,thiz){
    			thiz.eidtWindow.hide();
    			thiz.selectLackOfTestStudent();
    		},
    		scope:this
    	});
    },
    //-----------------------录入缺考考生结束----------------------------------    
    //-----------------------导入缺考考生开始----------------------------------
    importLackOfTestStudent:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择考试轮次！");
    		return;
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var ids =selectedBuildings[0].get("LCID");
    	this.lcid=ids;
    	this.submitForm = new Ext.ux.FormPanel({
    			fileUpload: true,
    			frame:true,
    			enctype: 'multipart/form-data',
    			defaults:{xtype:"textfield",anchor:"95%"},
    			items: [{fieldLabel:"附件",xtype:"textfield",name:"upload",inputType:'file'}]
    	});
   		//导入教师信息
    	var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",handler:this.exportFilesInfo,scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.fileUpWin.hide()},scope:this});
     	this.fileUpWin = new Ext.ux.Window({
     			title:"文件上传",	
     			width:400,
     			tbar:{cls:"ext_tabr",items:["->",_b_cancel,"->",_b_save]},
     			listeners:{
     				hide:function(){
     					this.submitForm.form.reset();
     				},scope:this},
     			items:[this.submitForm]
     	});
     	this.fileUpWin.show(null,function(){},this);	   
    },
    exportFilesInfo:function(){
    	var filePath = this.submitForm.getForm().findField("upload").getRawValue();
	    //判断文件类型
    	var objtype = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
    	var fileType = new Array(".xls",".xlsx");
    	if(filePath==""){ 
    		alert("请选择文件！");return false;
    	}else{
    		if(!/\.(xls|xlsx)$/.test(objtype)){
    			alert("文件类型必须是.xlsx,.xls中的一种!")
    			return false;
    		}
    	}
    	this.submitForm.$submit({
    		action:"lackOfTestStudent_importLackOfTestStudent.do",
    		params:{
    			'lcId':this.lcid,
				form:this.submitForm.getForm().findField("upload")
			},
			handler:function(form,result,thiz){
				thiz.fileUpWin.hide();
				thiz.selectExamRound();
			},scope:this
		});	
    }   
    //-----------------------导入缺考考生结束----------------------------------
});