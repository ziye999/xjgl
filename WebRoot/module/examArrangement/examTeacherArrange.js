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
    	this.panel = this.createMainPanel();
    	this.examTeacherPanel = this.createszExamTeacher();
    	this.invigilatingArrangementPanel = this.createIAPanel();
    	//初始化巡考安排win界面
    	this.patrolArrangeWin = this.createPatrolArrangeWin();
    	this.patrolArrangeWin.hide();
    	//初始化监考安排win界面
    	this.ivatrolArrangeWin = this.createInvigilatorArrangeWin();
    	this.ivatrolArrangeWin.hide();
    	//初始化查询监考安排界面
    	this.ivaWin = this.createivaWin();
    	//初始化查询巡考安排界面
    	this.paWin = this.createpaWin();
    	this.ivaWin.hide();
    	this.paWin.hide();
    	this.adjustInvigilatorWin = this.createAdjustInvigilatorWin();
	},
	createMainPanel:function() {
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
			sm,
			{header: "年度",   	align:"center", sortable:true, dataIndex:"XNMC"},
			{header: "季度",   	align:"center", sortable:true, dataIndex:"XQMC"},
			{header: "轮次名称",  align:"center", sortable:true, dataIndex:"EXAMNAME"},
			{header: "考试类型",  align:"center", sortable:true, dataIndex:"EXAMTYPEM"},
			{header: "起始日期",  align:"center", sortable:true, dataIndex:"STARTDATE"},
			{header: "结束日期",  align:"center", sortable:true, dataIndex:"ENDDATE"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"监考安排",
			tbar:[ 
			      "->",{xtype:"button",text:"监考安排",iconCls:"p-icons-update",handler:this.jianKaoAnPai,scope:this}
			      ,"->",{xtype:"button",text:"设置监考老师",iconCls:"p-icons-setting",handler:this.szExamTeacher,scope:this}			  
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			action:"examroomarrange_getListPage.do",
			fields :["LCID","EXAMNAME","STARTWEEK","STARTDAY","STARTDATE","ENDWEEK","ENDDAY","ENDDATE","EXAMTYPEM","XNMC","XQMC"],
			border:false
		});
		//搜索区域
		//var xn_find = new Ext.ux.Combox({dropAction:"xn", width:170,id:"xn_find"});
		var xn_find = new Ext.ux.form.XnxqField({ width:190,id:"teaxn_find",readOnly:true});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamRound,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.search = new Ext.form.FormPanel({
			id:"search_form",
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
		return new Ext.Panel({
	    		region:"north",
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.search,this.grid]
		});
	},
	createszExamTeacher :function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "所属单位",   align:"center", sortable:true, dataIndex:"SCHOOLNAME"},
			{header: "监考老师姓名",   align:"center", sortable:true, dataIndex:"TEANAME"},
			{header: "工号",  		align:"center", sortable:true, dataIndex:"GH"},
			{header: "性别",   		align:"center", sortable:true, dataIndex:"XB"},
			{header: "科目",   		align:"center", sortable:true, dataIndex:"KCMC"}
		];
		this.examTeacherGrid = new Ext.ux.GridPanel({
			id:"examTeacherGrid",
			cm:cm,
			sm:sm,
			tbar:[ 
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}
			      ,"->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteExamTeacher,scope:this}
			      ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateExamTeacher,scope:this}
			      ,"->",{xtype:"button",text:"导入监考老师",iconCls:"p-icons-upload",handler:this.daoRuExamTeacher,scope:this}
			      ,"->",{xtype:"button",text:"下载导入模板",iconCls:"p-icons-download",handler:this.xiaZaiMoBan,scope:this}
			      ,"->",{xtype:"button",text:"录入监考老师",iconCls:"p-icons-add",handler:function(){this.luRuExamTeacher("录入监考老师信息",null)},scope:this}
			      ,"->",{xtype:"button",text:"抽取监考老师",iconCls:"p-icons-add",handler:this.chouQuExamTeacher,scope:this}			  
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			action:"examteacherarrange_getListPage.do",
			fields :["JKLSID","LCID","SCHOOLNAME","TEANAME","TEATYPE","GH","XBM","XB","SUBJECT","KCMC","MAINFLAG","ISBZR","SCHOOLID","JSID"],
			border:false
		});
		//搜索区域
		var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel",width:210,readOnly:true});
		//监考老师姓名
		var jklsxm_find = new Ext.form.TextField({fieldLabel:"",id:"jklsxm_find",name:"jklsxm_find",maxLength:10,width:170});
		//监考老师工号
		var lrjkls_find=new Ext.form.Checkbox({id:"lrjkls_find",boxLabel:"录入的监考老师",listeners:{
			"check":function(combo,record,number){ //监听"select"事件
				if(combo.checked){
					organ_sel.clearValue();
					organ_sel.disable();
				}else{
					organ_sel.enable();
				};    
			},
			scope:this
		}});
		
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamTeacher,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.examRoomSearch.getForm().reset()},scope:this});
				
		this.examRoomSearch = new Ext.form.FormPanel({
			id:"examRoomSearch_form",
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
							columns: 6
						}, 
						baseCls:"table",  
						items:[
							{html:"参考单位：",baseCls:"label_right",width:140},
							{items:[organ_sel],baseCls:"component",width:210},
							{html:"监考老师姓名/工号：",baseCls:"label_right",width:140},
							{items:[jklsxm_find],baseCls:"component",width:190},
							{items:[lrjkls_find],baseCls:" label_center",style:{size:'12px'},width:140},
							{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:190}							
						] 
                 	}]  
		       }]  
	    	});
	    	return new Ext.Panel({
	    		id:"examTeacherPanel",
	    		title:"设置监考老师",
	    		region:"north",
	    		height:500,
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.examRoomSearch,this.examTeacherGrid]
	    	});	    	
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.tabPanel = new Ext.TabPanel({   
    		activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.panel,this.examTeacherPanel,this.invigilatingArrangementPanel]   
        }); 
    	this.addPanel({layout:"fit",items:[this.tabPanel]});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
    //设置监考老师
	szExamTeacher:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var ids =selectedBuildings[0].get("LCID");
    	this.lcid=ids;
    	this.tabPanel.setActiveTab(this.examTeacherPanel);
  		this.examTeacherGrid.$load({"lcId":this.lcid});
    },   
    //查询考试轮次
    selectExamRound:function(){
    	//var xn=Ext.getCmp('xn_find').getValue();
    	var xnxqValue=Ext.getCmp('teaxn_find').getCode();
    	this.grid.$load({"xnxqId":xnxqValue});
    },
    //查询监考老师
    selectExamTeacher:function(){
    	var organ_sel=this.examRoomSearch.getForm().findField('organ_sel').getCodes();
    	var name=Ext.getCmp('jklsxm_find').getValue();
    	var lrjkls=Ext.getCmp('lrjkls_find').getValue();
    	this.examTeacherGrid.$load({"lcId":this.lcid,"organ":organ_sel,"name":name,"lrjkls":lrjkls});
    },
    //返回到轮次页面
    fanhui:function(){
    	this.tabPanel.setActiveTab(this.panel);
  		this.grid.$load();
    },
    //修改监考老师信息
    updateExamTeacher:function(){
    	var selected =  this.examTeacherGrid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.examTeacherGrid.getSelectionModel().getSelections();
    	if(selectedBuildings.length>1){
    		Ext.MessageBox.alert("消息","一次只能修改一条记录！");
    		return;
    	}
    	var obj=new Object();
    	obj.jklsid = selectedBuildings[0].get("JKLSID");
    	obj.lcid = selectedBuildings[0].get("LCID");
    	obj.schoolname = selectedBuildings[0].get("SCHOOLNAME");
    	obj.teaname = selectedBuildings[0].get("TEANAME");
    	obj.teatype = selectedBuildings[0].get("TEATYPE");
    	obj.gh = selectedBuildings[0].get("GH");
    	obj.xbm = selectedBuildings[0].get("XBM");
    	obj.subject = selectedBuildings[0].get("SUBJECT");
    	obj.mainflag = selectedBuildings[0].get("MAINFLAG");
    	obj.schoolid = selectedBuildings[0].get("SCHOOLID");
    	obj.jsid = selectedBuildings[0].get("JSID");
    	this.luRuExamTeacher("修改监考老师信息",obj);    	
    },
    //删除监考老师
    deleteExamTeacher:function(){
    	var selected =  this.examTeacherGrid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择需要删除的记录！");
    		return;
    	}
    	var selectedBuildings = this.examTeacherGrid.getSelectionModel().getSelections();
    	var ids =new Array();
    	for (var i = 0; i < selectedBuildings.length; i++) {
    		ids[i]=selectedBuildings[i].get("JKLSID");
    	}
      	var thiz=this;
      	Ext.Msg.confirm('消息','确认删除这'+selectedBuildings.length+'条记录？', function (id) {
    		if (id == 'yes') {
    			Ext.Ajax.request({   
    				url:'examteacherarrange_deleteExamTeacher.do',
    				params:{
    					'ids':ids
    				},
    				success: function(resp,opts) {
    					var respText = Ext.util.JSON.decode(resp.responseText);
    					Ext.MessageBox.alert("提示",respText.msg);
    					thiz.selectExamTeacher();
    				},
    				failure: function(resp,opts){
    					Ext.Msg.alert('错误', "删除失败！");
    				}        	
    			});                         
		    }
    	});      
    },
    //下载导入监考老师模板
    xiaZaiMoBan:function(){
    	var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/examTeacher_temp.xls";
    	window.open(path);    	
    },
    //-------------------------抽取监考老师------------------------------------------
    chouQuExamTeacher:function(){
	    this.editForm   = this.createChouQuExamTeacherEditform();
   		this.eidtWindow = this.createChouQuExamTeacherWindow();
   		this.eidtWindow.add(this.editForm);
   		this.eidtWindow.show();
    },
    createChouQuExamTeacherWindow:function(){
    	var thiz=this;
    	return new Ext.ux.Window({
			 	width:1000,
			 	buttonAlign:"center",
			 	listeners:{"close":function(){
			 		thiz.selectExamTeacher();
			 	},
			 	scope:this
		}});		
    },
    createChouQuExamTeacherEditform:function(){
    	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "所属单位",   align:"center", sortable:true, dataIndex:"XXMC"},
			{header: "监考老师姓名",   align:"center", sortable:true, dataIndex:"XM"},
			{header: "工号",   align:"center", sortable:true, dataIndex:"GH"},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
			{header: "科目",   align:"center", sortable:true, dataIndex:"KCMC"}
		];
		this.examTeacherGrid_cq = new Ext.ux.GridPanel({
			id:"examTeacherGrid_cq",
			cm:cm,
			sm:sm,
			tbar:[ 
			      "->",{text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide();},scope:this}
			      ,"->",{id:"saveB",xtype:"button",text:"保存",iconCls:"p-icons-save",handler:this.saveChouQuTeacher,scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			action:"examteacherarrangeT_getListPage.do",
			fields :["JSID","XM","GH","XBM","XB","BZR_M","SCHOOLID","XXMC","ISBZR","RJKMM","KCMC"],
			border:false
		});
		//搜索区域
		var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel1",width:210,readOnly:true});
		//监考老师姓名
		var jklsxm_find = new Ext.form.TextField({fieldLabel:"",id:"jklsxm_cq_find",name:"jklsxm_cq_find",maxLength:10,width:170});
		//监考老师工号
		var jklsgh_find = new Ext.form.TextField({fieldLabel:"",id:"jklsgh_cq_find",name:"jklsgh_cq_find",maxLength:10,width:170})
		
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectTeacher,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.examRoomSearch_cq.getForm().reset()},scope:this});
				
		this.examRoomSearch_cq = new Ext.form.FormPanel({
			id:"examRoomSearch_form_cq",
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
							columns: 6
						}, 
						baseCls:"table",  
						items:[
							{html:"参考单位：",baseCls:"label_right",width:120},
							{items:[organ_sel],baseCls:"component",width:210},
							{html:"监考老师姓名/工号：",baseCls:"label_right",width:140},
							{items:[jklsxm_find],baseCls:"component",width:180},
							{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:190}						
						] 
                  	}]  
		       }]  
		});
		var examTeacherPanel=new Ext.Panel({
	    		id:"examTeacherPanel_cq",
	    		title:"设置监考老师",
	    		region:"north",
	    		height:500,
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.examRoomSearch_cq,this.examTeacherGrid_cq]
		});
		return examTeacherPanel
    },
    //查询老师信息
    selectTeacher:function(){
    	var organ_sel=this.examRoomSearch_cq.getForm().findField('organ_sel1').getCodes();
    	var name=Ext.getCmp('jklsxm_cq_find').getValue();
    	this.examTeacherGrid_cq.$load({"lcId":this.lcid,"organ":organ_sel,"name":name});
    },
    saveChouQuTeacher:function(){
    	var selected =  this.examTeacherGrid_cq.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择需保存的教师信息！");
    		return;
    	}
    	var selectedBuildings = this.examTeacherGrid_cq.getSelectionModel().getSelections();
    	var array=new Array();
    	for(var i=0;i<selectedBuildings.length;i++){
    		var obj =new Object();
    		obj.jsid =selectedBuildings[i].get("JSID");
    		obj.xm =selectedBuildings[i].get("XM");
    		obj.gh =selectedBuildings[i].get("GH");
    		obj.xbm =selectedBuildings[i].get("XBM");
    		obj.bzrm =selectedBuildings[i].get("BZR_M");
    		obj.schoolid =selectedBuildings[i].get("SCHOOLID");
    		obj.xxmc =selectedBuildings[i].get("XXMC");
    		obj.rjkmm =selectedBuildings[i].get("RJKMM");
    		array[i]=obj;
    	}
    	var thiz=this;
    	var str=JSON.stringify(array)
    	Ext.Ajax.request({   
       		url:'examteacherarrange_saveChouQuTeacher.do',
       		params:{
        		'lcId':thiz.lcid,'objList':str
        	},
        	success: function(resp,opts) {
        		Ext.MessageBox.alert("提示","保存成功！");
        		thiz.selectTeacher();
        		thiz.selectExamTeacher();
            },
            failure: function(resp,opts) {
            	var respText = Ext.util.JSON.decode(resp.responseText);
            	Ext.Msg.alert('错误', respText.error);
            }        
    	});
    	Ext.getCmp('saveB').setDisabled(true);
    },
    //-----------------------抽取监考老师结束----------------------------------    
    //-----------------------录入监考老师开始----------------------------------
    luRuExamTeacher:function(title,obj){
    	this.editForm   = this.createLuRuExamTeacherEditform(title,obj);
   		this.eidtWindow = this.createLuRuExamTeacherWindow(title);
   		this.eidtWindow.add(this.editForm);
   		this.eidtWindow.show();
   		var xbF = this.editForm.getForm().findField("xb");
   		var kmF = this.editForm.getForm().findField("km");
   		setTimeout(function(){
	   		if(obj!=null){
		   		Ext.getCmp("jklsid_lr").setValue(obj.jklsid);
		   		Ext.getCmp("lcid_lr").setValue(obj.lcid);
		   		Ext.getCmp("xxid_lr").setValue(obj.schoolid);
		   		Ext.getCmp("jsid_lr").setValue(obj.jsid);
		   		Ext.getCmp("teatype_lr").setValue(obj.teatype);
		   		Ext.getCmp("xuexiao_lr").setValue(obj.schoolname);
		   		Ext.getCmp("name_lr").setValue(obj.teaname);
		   		Ext.getCmp("gh_lr").setValue(obj.gh);
		   		Ext.getCmp("xbm").setValue(obj.xbm);
		   		Ext.getCmp("subject").setValue(obj.subject);
		   		
		   		xbF.setValue(obj.xbm);   		
		   		kmF.setValue(obj.subject);		   		
	   		}
   		},200);
    },
    createLuRuExamTeacherWindow:function(title){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.LuRuExamTeacheSubmit,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:700,
			 	title:title,
			 	tbar:{
						cls:"ext_tabr",
						items:[ 
						 	 "->",cancel
				 			 ,"->",save
						  ]
					},
			 	buttonAlign:"center"});			
    },
    createLuRuExamTeacherEditform:function(title,obj){    	
    	var jklsid = new Ext.form.TextField({id:"jklsid_lr",hidden:true,name:"jklsid"});
    	var lcid = new Ext.form.TextField({id:"lcid_lr",hidden:true,name:"lcid"});
    	var xxid = new Ext.form.TextField({id:"xxid_lr",hidden:true,name:"schoolid"});
    	var jsid = new Ext.form.TextField({id:"jsid_lr",hidden:true,name:"jsid"});
    	var teatype = new Ext.form.TextField({id:"teatype_lr",hidden:true,name:"teatype"});
		
		var xx_lr = new Ext.form.TextField({name:"schoolname",id:"xuexiao_lr",width:170,maxLength:100,allowBlank:false,blankText:"所属单位不能为空！"});
		var xm_lr = new Ext.form.TextField({name:"teaname",id:"name_lr",width:170,maxLength:50,allowBlank:false,blankText:"姓名不能为空！"});
		var gh_lr = new Ext.form.TextField({name:"gh",id:"gh_lr",width:170,maxLength:50,allowBlank:false,blankText:"工号不能为空！"});
		
		var xbm  = new Ext.form.TextField({hidden:true,name:"xbm",id:"xbm"});
		var subject  = new Ext.form.TextField({hidden:true,name:"subject",id:"subject"});
		var xb_lr = new Ext.ux.DictCombox({dictCode:"ZD_XB",name:"xb",width:170,allowBlank:false,blankText:"性别不能为空！",listeners:{
			"select":function(){
				Ext.getCmp("xbm").setValue(this.getValue());
			}
		}});
		var kc_lr = new Ext.ux.Combox({dropAction:"course",name:"km",width:170,allowBlank:false,blankText:"科目不能为空！",listeners:{
			"select":function(){
				Ext.getCmp("subject").setValue(this.getValue());
			}
		}});
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[
				{html:"所属单位：",baseCls:"label_right",width:120},
				{html:"<font class='required'>*</font>",items:[xx_lr],baseCls:"component",width:190},
				{html:"姓名：",baseCls:"label_right",width:120},
				{html:"<font class='required'>*</font>",items:[xm_lr],baseCls:"component",width:190},
				{html:"工号：",baseCls:"label_right",width:120},
				{html:"<font class='required'>*</font>",items:[gh_lr],baseCls:"component",width:190},
				{html:"性别：",baseCls:"label_right",width:120},
				{html:"<font class='required'>*</font>",items:[xb_lr,xbm],baseCls:"component",width:190},
				{html:"科目：",baseCls:"label_right",width:120},
				{html:"<font class='required'>*</font>",items:[kc_lr,subject],baseCls:"component",width:190}			
			]		
		});
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
				{layout:'form', xtype:"fieldset",title:title,items:[panel]},
				{items:[jklsid,lcid,xxid,jsid,teatype]}
			]
		});				
    },
    LuRuExamTeacheSubmit:function(){
    	this.editForm.$submit({
    		action:"examteacherarrange_saveLuRuTeacher.do",
    		params:{
    			'lcId':this.lcid
    		},
    		handler:function(form,result,thiz){
    			thiz.eidtWindow.hide();
    			thiz.selectExamTeacher();
    		},
    		scope:this
    	}); 
    },
    //-----------------------录入监考老师结束----------------------------------    
    //-----------------------导入监考老师开始----------------------------------
    daoRuExamTeacher:function(){
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
    	var filePath     = this.submitForm.getForm().findField("upload").getRawValue();
    	//判断文件类型
    	var objtype=filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
    	var fileType=new Array(".xls",".xlsx");
    	if(filePath==""){ 
    		alert("请选择文件！");return false;
    	}else{
    		if(!/\.(xls|xlsx)$/.test(objtype)){
    			alert("文件类型必须是.xlsx,.xls中的一种!")
    			return false;
    		}
    	}
    	this.submitForm.$submit({
    		action:"examteacherarrange_saveDaoRuTeacher.do",
    		params:{
    			'lcId':this.lcid,
				form:this.submitForm.getForm().findField("upload")
			},
			handler:function(form,result,thiz){
				thiz.fileUpWin.hide();
				thiz.selectExamTeacher();
			},scope:this
		});	
    },   
    //-----------------------导入监考老师结束----------------------------------
    createIAPanel:function() {
    	var thiz = this;
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
			sm,
			{header: "考点", align:"center", sortable:true, dataIndex:"POINTNAME"},
			{header: "考场数", align:"center", sortable:true, dataIndex:"KCS"},
			{header: "监考老师数量", align:"center", sortable:true, dataIndex:"LSSL"},
			{header: "已安排监考老师人数", align:"center", sortable:true, dataIndex:"YAPJK",renderer:this.openIvaShowWin,listeners:{
				"click":function(e){
					//this.ivas_search_form.getForm().findField("ivas_nj_find").reset();
					this.ivas_search_form.getForm().findField("ivas_km_find").reset();
					var selected =  thiz.iagrid.getSelectionModel().getSelected();
					var selectedkds = this.iagrid.getSelectionModel().getSelections();
					var kdids = "";
					for(var i = 0; i < selectedkds.length; i++) {
						if(i != selectedkds.length - 1) {
							kdids += selectedkds[i].get("KDID") + "','";
						}else {
							kdids += selectedkds[i].get("KDID") + "";
						}
					}
					thiz.kdid = kdids;
					thiz.ivasearch();
					thiz.ivaWin.show();
				},scope:this}
			},
			{header: "已安排巡考老师人数",   align:"center", sortable:true, dataIndex:"YAPXK",renderer:this.openPaShowWin,listeners:{
				"click":function(){
					Ext.getCmp("xklsxmgh_find").reset();
					var selected =  thiz.iagrid.getSelectionModel().getSelected();
					var selectedkds = this.iagrid.getSelectionModel().getSelections();
					var kdids = "";
					for(var i = 0; i < selectedkds.length; i++) {
						if(i != selectedkds.length - 1) {
							kdids += selectedkds[i].get("KDID") + "','";
						}else {
							kdids += selectedkds[i].get("KDID") + "";
						}
					}
					thiz.kdid = kdids;
					thiz.pasearch();
					thiz.paWin.show();
				},scope:this}
			}
		];
		this.iagrid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"监考安排",
			tbar:[ 
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}
			      ,"->",{xtype:"button",text:"巡考安排",iconCls:"p-icons-update",handler:this.patrolArrangeBtn,scope:this}
			      ,"->",{xtype:"button",text:"监考安排",iconCls:"p-icons-update",handler:this.invigilatorArrangeBtn,scope:this}			  
			],
			page:true,
			rowNumber:true,
			region:"center",
			action:"examteacherarrangeIA_getListPage.do",
			fields :["LCID","KDID","POINTNAME","KCS","LSSL","YAPJK","YAPXK","SYRS"],
			border:false
		});
		//搜索区域
		var xn_find = new Ext.ux.form.XnxqField({ width:180,id:"iaxn_find",readOnly:true});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamRound,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.iasearch.getForm().reset()},scope:this});
		
		this.iasearch = new Ext.form.FormPanel({
			id:"iasearch_form",
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
	    return new Ext.Panel({
	    		id:"iapanel",
	    		region:"north",
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.iagrid]
	    });
	},
	ivasearch:function() {
		//var njid = this.ivas_search_form.getForm().findField("ivas_nj_find").getValue();"njid":njid,
		var kmid = this.ivas_search_form.getForm().findField("ivas_km_find").getValue();
		this.ivas_store.load({params:{"kmid":kmid,"lcid":this.lcid,"kdid":this.kdid}});
	},
	pasearch:function() {
		var xmgh = Ext.getCmp("xklsxmgh_find").getValue();
		this.pas_store.load({params:{"xmgh":xmgh,"lcid":this.lcid,"kdid":this.kdid}});
	},
	//调整监考老师窗口
	createAdjustInvigilatorWin:function() {		
		this.haitGridStore = new Ext.data.JsonStore({
			autoLoad:false,
			url:"examteacherarrangeHas_getListPage.do",
			fields :["JKAPID","JKLSID","TEANAME","GH","SFBZR","SFZJK","KCMC"]
		});
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var hasCols = [{align:"center", header: "工号", width: 73, sortable: true, dataIndex: 'GH'},
		               {header: "姓名",align:"center", width: 70, sortable: true, dataIndex: 'TEANAME'},
		               {header: "是否主监考",align:"center", width: 73, sortable: true, dataIndex: 'SFZJK',
		            	   renderer : function(value, metadata, model, rowIndex, colIndex, store) { 
		            		   if(value == '是') {
		            			   return '<input type="radio" name="iszjkls"  checked value="'+model.data.JKLSID+'" />';
		            		   }else {
		            			   return '<input type="radio" name="iszjkls" value="'+model.data.JKLSID+'" />';
		            		   }
		            	   }
		               }];
		var noneCols = [{header: "工号",align:"center", width: 100, sortable: true, dataIndex: 'GH'},
		 				{header: "姓名",align:"center", width: 100, sortable: true, dataIndex: 'TEANAME'}];	
		this.firstGrid = new Ext.grid.GridPanel({
			ddGroup          : 'secondGridDDGroup',
			store            : this.haitGridStore,
			columns          : hasCols,
 			enableDragDrop   : true,
 			stripeRows       : true,
 			title            : '已安排监考老师'
		});
		this.naitGridStore = new Ext.data.JsonStore({
			autoLoad:false,
			url:"examteacherarrangeNone_getListPage.do",
			fields :["JKAPID","JKLSID","TEANAME","GH","SFBZR","SFZJK","KCMC"]
		});
		this.secondGrid = new Ext.grid.GridPanel({
			ddGroup          : 'firstGridDDGroup',
			store            : this.naitGridStore,
			columns          : noneCols,
 			enableDragDrop   : true,
 			stripeRows       : true,
 			title            : '未安排监考老师'
		});
		var thiz = this;
		this.rcode = "";
		this.secondGrid.on('mouseover',function(e){//添加mouseover事件
			var index = thiz.firstGrid.getView().findRowIndex(e.getTarget());//根据mouse所在的target可以取到列的位置
			if(index!==false){//当取到了正确的列时，（因为如果传入的target列没有取到的时候会返回false）
				var record = thiz.naitGridStore.getAt(index);//把这列的record取出来
				var str = Ext.encode(record.data);//组装一个字符串，这个需要你自己来完成，这儿我把他序列化
				thiz.rcode = record.data.name;
				var rowEl = Ext.get(e.getTarget());//把target转换成Ext.Element对象
				thiz.secondGrid.store.remove(records);
				rowEl.set({
					'ext:qtip':str  //设置它的tip属性
				},false);
			}
		});
		return new Ext.ux.Window({
			layout: 'hbox',
			tbar:[
			      "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function() {this.adjustInvigilatorWin.hide()},scope:this}
			      ,"->",{xtype:"button",text:"保存",iconCls:"p-icons-save",handler:this.saveAdjustInvigilator,scope:this}
				 ],
			defaults     : { flex : 1 }, //auto stretch
			layoutConfig : { align : 'stretch' },
		 	width:650,
		 	height:300,
		 	items:[this.firstGrid,this.secondGrid],
		 	title:"调整监考老师"});
	},
	//保存监考老师调整
	saveAdjustInvigilator:function() {
		var thiz = this;
		if(this.haitGridStore.getCount() == 0) {
			Ext.MessageBox.alert("消息","请添加监考老师！");
			return;
		}
		var iszjklsCheck = document.getElementsByName("iszjkls");
		var iszjklsid = "";
		for(var i=0;i<iszjklsCheck.length;i++) {
		     if(iszjklsCheck[i].checked)
		    	 iszjklsid = iszjklsCheck[i].value;
		}
		if(iszjklsid == "") {
			Ext.MessageBox.alert("消息","请选择主监考老师！");
			return;
		}
		var jklsids = "";
		var teanames = "";
		for (var i = 0 ; i < this.haitGridStore.getCount() ; i++){
			jklsids += this.haitGridStore.getAt(i).get('JKLSID');
			teanames += this.haitGridStore.getAt(i).get('TEANAME');
			if(i != this.haitGridStore.getCount() - 1) {
				jklsids +=",";
				teanames +=",";
			}
    	}
		JH.$request({
			params:{"iszjklsid":iszjklsid,"jklsids":jklsids,"lcid":this.lcid,"kdid":this.kdid,"kcid":this.kcid,"njid":this.njid,"rcid":this.rcid,"teanames":teanames},
			action:"examteacherarrange_saveAdjustInvigilator.do", 
			handler:function(){
				thiz.adjustInvigilatorWin.hide();
				thiz.ivasearch();
			}
		}); 
	},
	createivaWin:function() {
		//初始化调整监考老师窗口
		var thiz = this;
		this.kmData = new Ext.data.Store();
		//搜索区域
		//var ivas_nj_find = new Ext.ux.Combox({dropAction:"grade",name:"ivas_nj_find",width:160});
		var ivas_km_find = new Ext.form.ComboBox({store:this.kmData,name:"ivas_km_find",width:160,
            mode : 'local',
            triggerAction : 'all',
            emptyText: '请选择...',  
            editable: false,
			valueField:'CODEID',displayField:'CODENAME'});
		var search_btn = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.ivasearch,scope:this});
		var cz_btn = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.ivas_search_form.getForm().reset()},scope:this});
		
		this.ivas_search_form = new Ext.form.FormPanel({
			id:"ivas_search_form",
			region: "north",
			height:120,
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
							//{html:"等级：",baseCls:"label_right",width:150},
							//{items:[ivas_nj_find],baseCls:"component",width:190},
							{html:"科目：",baseCls:"label_right",width:150},
							{items:[ivas_km_find],baseCls:"component",width:190},
							{layout:"absolute",items:[search_btn,cz_btn],baseCls:"component_btn",width:160}
						] 
                 	}]  
		      	}]  
	    });
		var cm = [
			{header: "考试日期",   align:"center", sortable:true, dataIndex:"KSRQ",width:120},
			{header: "考试时间段",   align:"center", sortable:true, dataIndex:"KSSJ",width:130},
			//{header: "等级",   align:"center", sortable:true, dataIndex:"NJ",width:90},
			{header: "科目",   align:"center", sortable:true, dataIndex:"KM",width:160},
			{header: "考场",   align:"center", sortable:true, dataIndex:"KC",width:70},
			{header: "考场人数",   align:"center", sortable:true, dataIndex:"KCRS",width:70},
			{header: "主监考",   align:"center", sortable:true, dataIndex:"ZJKNAME",width:120},
			{header: "监考老师",   align:"center", sortable:true, dataIndex:"JKNAMES",width:170},
			{header: "操作",  align:"center",
				xtype:'actioncolumn', 
	            width:70,
	            items: [{
	            	icon: Ext.get("ServerPath").dom.value+'/img/icons/operation.gif',  // Use a URL in the icon config
	                tooltip: '调整监考老师',
	                handler: function(grid, rowIndex, colIndex) {
	                	var rec = grid.getStore().getAt(rowIndex);
	                    var lcid = rec.get('LCID');
	                    var kdid = rec.get('KDID');
	                    var kcid = rec.get('KCID');
	                    var rcid = rec.get('RCID');
	                    var njid = rec.get('NJID');
	                    thiz.lcid = lcid;
	                    thiz.kdid = kdid;
	                    thiz.kcid = kcid;
	                    thiz.rcid = rcid;
	                    thiz.njid = njid;
	                    thiz.haitGridStore.load({params:{"lcid":lcid,"kdid":kdid,"kcid":kcid,"rcid":rcid,"njid":njid}});
	                    thiz.naitGridStore.load({params:{"lcid":lcid,"kdid":kdid,"kcid":kcid,"rcid":rcid,"njid":njid}});
	                    thiz.adjustInvigilatorWin.show();
	                    
	                    // used to add records to the destination stores
	         			var firstGridDropTargetEl =  thiz.firstGrid.getView().scroller.dom;
	         			var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
	         				ddGroup    : 'firstGridDDGroup',
	         				notifyDrop : function(ddSource, e, data){
	         					var records =  ddSource.dragData.selections;
	         					Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
	         					thiz.firstGrid.store.add(records);
	         					thiz.firstGrid.store.sort('name', 'ASC');
	         					return true;
	         				}
	         			});
	         		        
	         			var secondGridDropTargetEl = thiz.secondGrid.getView().scroller.dom;
	         			var secondGridDropTarget = new Ext.dd.DropTarget(secondGridDropTargetEl, {
	         				ddGroup    : 'secondGridDDGroup',
	         				notifyDrop : function(ddSource, e, data){
	         					var records =  ddSource.dragData.selections;
	         					Ext.each(records, ddSource.grid.store.remove, ddSource.grid.store);
	         					thiz.secondGrid.store.add(records);
	         					thiz.secondGrid.store.sort('name', 'ASC');
	         					thiz.rcode = "";
	         		            return true;
	         				}
	         			});	                    
	                }
	            }]}
		];
		cm.splice(0,0,new Ext.grid.RowNumberer({header:"序号",width:40}));
		this.ivas_store = new Ext.data.JsonStore({
    		autoLoad:false,
    		url:"examteacherarrangeIAS_getListPage.do",
    		fields :["JKAPIDS","KSRQ","KSSJ","NJ","KM","KC","KCRS","ZJKNAME","JKNAMES","LCID","KDID","KCID","RCID","NJID"]
    	});
		this.ivas_store.load({params:{"lcid":this.lcid,"kdid":this.kdid}});
	    this.ivas_grid = new Ext.grid.GridPanel({
		store:this.ivas_store,
		tbar:[
		      "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function() {this.ivaWin.hide();this.iagrid.$load({"lcid":this.lcid});},scope:this}
			  ],
		columns:cm,
		region:"center"
		});
		return new Ext.ux.Window({
				layout:"border",
			 	width:1000,
			 	height:400,
			 	items:[this.ivas_grid,this.ivas_search_form],   
			 	title:"监考安排"});	
	},
	createpaWin:function() {
		//搜索区域
		var xklsxmgh_find = new Ext.form.TextField({fieldLabel:"",id:"xklsxmgh_find",name:"xklsxmgh_find",maxLength:10,width:160});
		var search_btn = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.pasearch,scope:this});
		var cz_btn = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.pas_search_form.getForm().reset()},scope:this});
		
		this.pas_search_form = new Ext.form.FormPanel({
			id:"pas_search_form",
			region: "north",
			height:120,
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
							{html:"姓名/工号：",baseCls:"label_right",width:150},
							{items:[xklsxmgh_find],baseCls:"component",width:190},
							{layout:"absolute",items:[search_btn,cz_btn],baseCls:"component_btn",width:160}
						] 
                 	}]  
		      	}]  
	    });
		var cm = [
			{header: "姓名",   align:"center", sortable:true, dataIndex:"TEANAME",width:200},
			{header: "工号",   align:"center", sortable:true, dataIndex:"GH",width:250},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB",width:100},
			{header: "巡考范围",   align:"center", sortable:true, dataIndex:"XKFW",width:400}
		];
		cm.splice(0,0,new Ext.grid.RowNumberer({header:"序号",width:40}));
		this.pas_store = new Ext.data.JsonStore({
			autoLoad:false,
    		url:"examteacherarrangePAS_getListPage.do",
    		fields :["TEANAME","GH","XB","XKFW"]
    	});
		this.pas_store.load({params:{"lcid":this.lcid,"kdid":this.kdid}});
	    this.pas_grid = new Ext.grid.GridPanel({
	    	store:this.pas_store,
	    	tbar:[
	    	      "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function() {this.paWin.hide()},scope:this}
	    	     ],
	    	columns:cm,
	    	region:"center"
		});
		return new Ext.ux.Window({
				layout:"border",
			 	width:1000,
			 	height:400,
			 	items:[this.pas_grid,this.pas_search_form],   
			 	title:"巡考安排"});	
	},
	createInvigilatorArrangeWin:function() {
		//搜索区域
		var jklssl_find = new Ext.form.NumberField({width:160,name:"jklssl_find",id:"jklssl_find",allowBlank:false,blankText:"每个考场监考数不能为空！"});
		var sfcxap_find	= new Ext.ux.DictCombox({dictCode:"ZD_SFBZ",width:160,name:"sfcxap_find",allowBlank:false,blankText:"请选择不同科目是否重新安排！"});
		//var nj_find = new Ext.ux.Combox({dropAction:"grade",name:"nj_find",width:160,allowBlank:false,blankText:"请选择等级！"});
		var zdap_btn = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"自动安排",handler:this.autoInvigilatorArrange,scope:this});
		
		this.ivasearch_form = new Ext.form.FormPanel({
			id:"ivasearch_form",
			region: "north",
			height:120,
			items:[{  
				layout:'form',  
				xtype:'fieldset',  
				style:'margin:10',
				title:'安排条件',  
				items: [{
						xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 5
						}, 
						baseCls:"table",
						items:[
							{html:"每个考场监考数：",baseCls:"label_right",width:150},
							{items:[jklssl_find],baseCls:"component",width:190},
							{html:"不同科目是否重新安排：",baseCls:"label_right",width:150},
							{items:[sfcxap_find],baseCls:"component",width:190},
							//{html:"等级：",baseCls:"label_right",width:150},
							//{items:[nj_find],baseCls:"component",width:190},
							{items:[zdap_btn],baseCls:"component_btn",width:100}
						] 
                    }]  
		       }]  
	    	});
		var cm = [
			{header: "考试日期",   align:"center", sortable:true, dataIndex:"KSRQ",width:140},
			{header: "考试时间段",   align:"center", sortable:true, dataIndex:"KSSJ",width:150},
			//{header: "等级",   align:"center", sortable:true, dataIndex:"NJ",width:110},
			{header: "科目",   align:"center", sortable:true, dataIndex:"KM",width:80},
			{header: "考场",   align:"center", sortable:true, dataIndex:"KC",width:90},
			{header: "考场人数",   align:"center", sortable:true, dataIndex:"KCRS",width:80},
			{header: "主监考",   align:"center", sortable:true, dataIndex:"ZJKNAME",width:140},
			{header: "监考老师",   align:"center", sortable:true, dataIndex:"JKNAMES",width:170}
		];
		cm.splice(0,0,new Ext.grid.RowNumberer({header:"序号",width:40}));
		this.ivastore = new Ext.data.JsonStore({
    		autoLoad:false,
    		url:"examteacherarrangeI_getListPage.do",
    		fields :["RCID","KCID","KSRQ","KSSJ","KC","NJ","KM","KCRS","ZJKID","ZJKNAME","JKIDS","JKNAMES","JKLS","FLAG","MSG","LSRSCONT"]
    	});
	    this.ivagrid = new Ext.grid.EditorGridPanel({
	    	store:this.ivastore,
	    	tbar:[
	    	      {xtype:"label",id:"jklsrslabel",text:"监考老师人数：0",scope:this}
	    	      ,"->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function() {this.ivatrolArrangeWin.hide()},scope:this}
				  ,"->",{xtype:"button",text:"保存",iconCls:"p-icons-save",handler:this.saveInvigilatorArrangeBtn,scope:this}
				 ],
		    columns:cm,
		    region:"center"
		});
		return new Ext.ux.Window({
				layout:"border",
			 	width:1000,
			 	height:400,
			 	items:[this.ivagrid,this.ivasearch_form],   
			 	title:"监考安排"});	
	},
	createPatrolArrangeWin:function(){
		//搜索区域
		var xklssl_find = new Ext.form.NumberField({width:160,name:"xklssl_find",id:"xklssl_find"});
		var zdap_btn = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"自动安排",handler:this.autoPatrolArrange,scope:this});
		
		this.pasearch_form = new Ext.form.FormPanel({
			id:"pasearch_form",
			region: "north",
			height:90,
			items:[{  
				layout:'form',  
				xtype:'fieldset',  
				style:'margin:10',
				title:'安排条件',  
				items: [{
                    	xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 3
						}, 
						baseCls:"table",
						items:[
							{html:"巡考老师数量：",baseCls:"label_right",width:140},
							{items:[xklssl_find],baseCls:"component",width:190},
							{items:[zdap_btn],baseCls:"component_btn",width:190}
						] 
                    }]  
		       }]  
	    });
		var cm = [
			{header: "姓名",   align:"center", sortable:true, dataIndex:"TEANAME",width:200},
			{header: "工号",   align:"center", sortable:true, dataIndex:"GH",width:250},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB",width:100},
			{header: "巡考范围",   align:"center", sortable:true, dataIndex:"XKFW",width:400,editor:new Ext.grid.GridEditor(  
				new Ext.form.TextField({})  
			)}
		];
		cm.splice(0,0,new Ext.grid.RowNumberer({header:"序号",width:40}));
		this.pastore = new Ext.data.JsonStore({
    		autoLoad:false,
    		url:"examteacherarrangeP_getListPage.do",
    		fields :["JKLSID","TEANAME","GH","XB","XKFW"]
    	});
	    this.pagrid = new Ext.grid.EditorGridPanel({
	    	store:this.pastore,
	    	tbar:[ 
	    	      "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function() {this.patrolArrangeWin.hide()},scope:this}
				  ,"->",{xtype:"button",text:"保存",iconCls:"p-icons-save",handler:this.savePatrolArrangeBtn,scope:this}
				 ],
			columns:cm,
			region:"center"
		});
		return new Ext.ux.Window({
				layout:"border",
			 	width:1000,
			 	height:400,
			 	items:[this.pagrid,this.pasearch_form],   
			 	title:"巡考安排"});	
    },
    patrolArrangeBtn:function() {
    	var selected =  this.iagrid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedkds = this.iagrid.getSelectionModel().getSelections();
    	var kdids = "";
    	var yapxk = "";
    	for(var i = 0; i < selectedkds.length; i++) {
    		if(i != selectedkds.length - 1) {
    			kdids += selectedkds[i].get("KDID") + "','";
    			yapxk += selectedkds[i].get("YAPXK") + "','";
    		}else {
    			kdids += selectedkds[i].get("KDID") + "";
    			yapxk += selectedkds[i].get("YAPXK") + "";
    		}
    	}
    	this.kdid = kdids;
    	var thiz = this;
    	if(Number(yapxk) != 0) {
    		Ext.MessageBox.show({
    			title:"消息",
    			msg:"是否重新安排巡考老师?",
    			buttons:Ext.MessageBox.OKCANCEL,
    			icon:Ext.MessageBox.QUESTION,
    			fn:function(b){
    				if(b=='ok'){
    					Ext.getCmp("xklssl_find").reset();
    					thiz.pastore.removeAll();
    					thiz.patrolArrangeWin.show();
    				}
    			}
    		})
    	}else {
    		Ext.getCmp("xklssl_find").reset();
    		this.pastore.removeAll();
    		this.patrolArrangeWin.show();
    	}
    },
    //监考安排
    invigilatorArrangeBtn:function() {
    	var selected =  this.iagrid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedkds = this.iagrid.getSelectionModel().getSelections();
    	var kdids = "";
    	var yapjk = "";
    	for(var i = 0; i < selectedkds.length; i++) {
    		if(i != selectedkds.length - 1) {
    			kdids += selectedkds[i].get("KDID") + "','";
    			yapjk += selectedkds[i].get("YAPJK") + "','";
    		}else {
    			kdids += selectedkds[i].get("KDID") + "";
    			yapjk += selectedkds[i].get("YAPJK") + "";
    		}
    	}
    	this.kdid = kdids;
    	var thiz = this;
    	if(Number(yapjk) != 0) {
    		Ext.MessageBox.show({
    			title:"消息",
    			msg:"是否重新安排监考老师?",
    			buttons:Ext.MessageBox.OKCANCEL,
    			icon:Ext.MessageBox.QUESTION,
    			fn:function(b){
    				if(b=='ok'){
    					Ext.getCmp("jklssl_find").reset();
    					thiz.ivasearch_form.getForm().findField("sfcxap_find").reset();
    					//thiz.ivasearch_form.getForm().findField("nj_find").reset();
    					Ext.getCmp("jklssl_find").reset();
    					Ext.getCmp("jklsrslabel").setText("监考老师人数：0");
    					thiz.ivastore.removeAll();
    					thiz.ivatrolArrangeWin.show();
    				}
    			}
    		})
    	}else {
    		Ext.getCmp("jklssl_find").reset();
    		thiz.ivasearch_form.getForm().findField("sfcxap_find").reset();
    		//thiz.ivasearch_form.getForm().findField("nj_find").reset();
			Ext.getCmp("jklssl_find").reset();
    		this.ivastore.removeAll();
    		this.ivatrolArrangeWin.show();
    	}
    },
    //保存巡考老师安排
    savePatrolArrangeBtn:function() {
    	var jklsids = "";
    	var xkfws = "";
    	var teanames = "";
    	for (var i = 0 ; i < this.pastore.getCount() ; i++){
    		if(i != this.pastore.getCount() - 1) {
    			jklsids += this.pastore.getAt(i).get('JKLSID') + ",";
    			xkfws += this.pastore.getAt(i).get('XKFW') + ",";
    			teanames += this.pastore.getAt(i).get('TEANAME') + ",";
    		}else {
    			jklsids += this.pastore.getAt(i).get('JKLSID');
    			xkfws += this.pastore.getAt(i).get('XKFW');
    			teanames += this.pastore.getAt(i).get('TEANAME');
    		}
    	}
    	var thiz = this;
    	JH.$request({
			params:{"lcid":thiz.lcid,"kdid":thiz.kdid,"jklsids":jklsids,"xkfws":xkfws,"teanames":teanames},
			action:"examteacherarrange_savePatrolArrange.do", 
			handler:function(){
				//thiz.patrolArrangeWin.hide();
				thiz.iagrid.$load({"lcid":thiz.lcid});
			}
		}); 
    },
    //保存监考老师
    saveInvigilatorArrangeBtn:function() {
    	var dataArray = new Array();
    	for (var i = 0 ; i < this.ivastore.getCount() ; i++){
    		var obj = new Object();
    		//obj.nj = this.ivasearch_form.getForm().findField("nj_find").getValue();
    		obj.zjkid = this.ivastore.getAt(i).get('ZJKID');
    		obj.zjkname = this.ivastore.getAt(i).get('ZJKNAME');
    		obj.jkids = this.ivastore.getAt(i).get('JKIDS');
    		obj.jknames = this.ivastore.getAt(i).get('JKNAMES');
    		obj.lcid = this.lcid;
    		obj.kdid = this.kdid;
    		obj.kcid = this.ivastore.getAt(i).get('KCID');
    		obj.rcid = this.ivastore.getAt(i).get('RCID');
    		dataArray.push(obj);
    	}
    	var dataJson = JSON.stringify(dataArray);
    	var thiz = this;
    	JH.$request({
			params:{"dataJson":dataJson},
			action:"examteacherarrange_saveInvigilatorArrange.do", 
			handler:function(){
				thiz.ivatrolArrangeWin.hide();
				thiz.iagrid.$load({"lcid":thiz.lcid});
			}
		}); 
    },
    //自动安排巡考老师
    autoPatrolArrange:function() {
    	var xklssl = Ext.getCmp("xklssl_find").getValue();
    	if(xklssl == "") {
    		Ext.MessageBox.alert("消息","请录入巡考老师数量！");
    		return;
    	}
    	if(xklssl <= 0) {
    		Ext.MessageBox.alert("消息","巡考老师数量不能为负或为零！");
    		return;
    	}
    	this.pastore.load({params:{"xklssl":xklssl,"lcid":this.lcid,"kdid":this.kdid}});
    },
    //自动安排监考老师
    autoInvigilatorArrange:function() {
    	var jklssl_find = Ext.getCmp("jklssl_find").getValue();
    	if(jklssl_find == "") {
    		Ext.MessageBox.alert("消息","请录入监考老师数量！");
    		return;
    	}
    	if(jklssl_find <= 0) {
    		Ext.MessageBox.alert("消息","监考老师数量不能为负或为零！");
    		return;
    	}
    	var sfcxap_find = this.ivasearch_form.getForm().findField("sfcxap_find").getValue();
    	//var nj_find = this.ivasearch_form.getForm().findField("nj_find").getValue();"nj":nj_find,
    	if(sfcxap_find == "") {
    		Ext.MessageBox.alert("消息","请选择不同科目是否重新安排！");
    		return;
    	}
    	var thiz = this;
    	this.ivastore.load({params:{"jklssl":jklssl_find,"sfcxap":sfcxap_find,"lcid":this.lcid,"kdid":this.kdid},
    		callback :function(r,options,success) { 
    			if(r[0].data.FLAG == "0") {
    				Ext.Msg.show({
    					title:'消息',
    					msg: r[0].data.MSG,
    					buttons: Ext.Msg.OK,
    					icon: Ext.MessageBox.INFO
    				});
    				thiz.ivastore.removeAll();
    			}else {
    				Ext.getCmp("jklsrslabel").setText("监考老师人数："+r[0].data.LSRSCONT);
    			}
    		}
    	});    	
    },
	//监考安排
    jianKaoAnPai:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
	    }
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var ids =selectedBuildings[0].get("LCID");
    	this.lcid=ids;
    	this.tabPanel.setActiveTab(this.invigilatingArrangementPanel);
    	this.iagrid.$load({"lcid":this.lcid});
    	this.kmData.proxy = new Ext.data.HttpProxy({
    		url : 'dropListAction_getKskmBykslc.do?params='+this.lcid,
    		reader : new Ext.data.JsonReader({}, Ext.data.Record.create(['CODEID', 'CODENAME']))  
    	});  
    	this.kmData.load();	
    },
    //显示查看抽取巡考老师
    openIvaShowWin:function(value, p, record) {
    	return String.format(
                '<a href="javascript:void(0);">{0}</a>',
                value, record.data.forumtitle, record.id, record.data.forumid);
    },
    //显示查看巡考安排老师
    openPaShowWin:function(value, p, record) {
    	return String.format( 
                '<a href="javascript:void(0);">{0}</a>',
                value, record.data.forumtitle, record.id, record.data.forumid);
    }
});
