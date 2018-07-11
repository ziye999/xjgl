var lcid = "";
var wjzt = "";
var wjcllx = [['1', '扣除分数']];
var wjcllxStore = new Ext.data.SimpleStore({  
	fields : ['value', 'text'],  
	data : wjcllx  
});
Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
		this.initComponent();
		this.initFace();
		this.initQueryDate();
		this.initListener();
    },
    /** 初始化页面、内存等基本数据 **/
    initDate:function(){
    	
    },    
    /** 对组件设置监听 **/
    initListener:function(){
    	//单元格点击事件，控制按钮的禁用
    	var thiz = this;
    	this.grid.on("cellclick",function(grid,rowIndex,columnIndex, e){
    		var selected = thiz.grid.getStore().getAt(rowIndex);
		    lcid = selected.get("LCID");
		    wjzt = selected.get("WJFLAG");
		    var flag = selected.get("WJFLAG");
		    var type = selected.get("DWTYPE");
		    var wjkss = selected.get("WJKSS");
		    if(flag=="" && type==1 && wjkss>0){
	    		Ext.getCmp("tbar_report").setDisabled(false);
		    }else{
	    		Ext.getCmp("tbar_report").setDisabled(true);
	    	}
    		if(columnIndex == 4){
    			this.CheatSearch.getForm().reset();
    			Ext.getCmp('xmkhxjh_sel').setValue("");
		    	thiz.tabPanel.setActiveTab(thiz.CheatPanel);
  				//thiz.CheatGrid.$load({"lcid":lcid});
		    	var organ_sel = this.CheatSearch.getForm().findField('organ_sel').getCodes();
		    	var xmkhxjh_sel = Ext.getCmp('xmkhxjh_sel').getValue();    	
				this.CheatGrid.$load({"lcid":lcid,"schools":organ_sel,"xmkhxjh":xmkhxjh_sel});
  				thiz.initEditWindow();
    		}
		},this);
    	this.CheatGrid.on("cellclick",function(grid,rowIndex,columnIndex, e){    		
			var selected = thiz.CheatGrid.getStore().getAt(rowIndex);
	    	var flag = selected.get("FLAG");
	    	if(flag == ""){
	    		Ext.getCmp("tbar_update").setDisabled(false);
	    		Ext.getCmp("tbar_delete").setDisabled(false);
	    	}else{
	    		Ext.getCmp("tbar_update").setDisabled(true);
	    		Ext.getCmp("tbar_delete").setDisabled(true);
	    	}
		},this);
    },   
   	initComponent :function(){
		this.panel = this.createMainPanel();
    	this.CheatPanel= this.createCheatStuPanel();
		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.panel,this.CheatPanel]   
        });		
	},
	createMainPanel:function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
			sm,
			{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   align:"center", sortable:true, dataIndex:"XQ"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME",
				renderer: function (data, metadata, record, rowIndex, columnIndex, store) { 
					var name = store.getAt(rowIndex).get('EXAMNAME');
					wjzt = store.getAt(rowIndex).get('WJFLAG');
					return "<a href='#' >"+name+"</a>";					  
				}
			},
			{header: "考试类型",   align:"center", sortable:true, dataIndex:"EXAMTYPE"},
			{header: "组织单位",   align:"center", sortable:true, dataIndex:"DWMC"},
			{header: "参考单位",	 align:"center", sortable:true, dataIndex:"CKDW"},
			{header: "违纪人次",   align:"center", sortable:true, dataIndex:"WJKSS",
				renderer: function (data, metadata, record, rowIndex, columnIndex, store) {  
					var wjkss = store.getAt(rowIndex).get('WJKSS');  
					return wjkss==""?0:wjkss;  
				}
			},
			{header: "状态",   align:"center", sortable:true, dataIndex:"WJZT"}			
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"违纪情况",
			tbar:[ 
			      "->",{xtype:"button",id:"tbar_report",text:"上报",iconCls:"p-icons-upload",handler:this.reported,scope:this},
			      "->",{xtype:"button",text:"导入违纪考生",iconCls:"p-icons-upload",handler:this.exportFile,scope:this}
			      ,"->",{xtype:"button",text:"下载模板",iconCls:"p-icons-download",handler:this.downloadTemplate,scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			excelTitle:"违纪情况表",
			action:"CheatStudent_getListPage.do",
			fields :["LCID","XN","EXAMNAME","EXAMTYPE","DWMC","CKDW","WJKSS","XQ","WJFLAG","DWTYPE","WJZT"],
			border:false
		});
		//搜索区域
		var xnxq = new Ext.ux.form.XnxqField({ width:210,id:"csxnxq_find",readOnly:true});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectCheatStuInfo,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		this.search = new Ext.form.FormPanel({
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
							columns: 6
						}, 
						baseCls:"table",
						items:[
						       {html:"年度：",baseCls:"label_right",width:120},
						       {items:[xnxq],baseCls:"component",width:210},
						       {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
							] 
                   	}]  
		       }]  
	    })	    	
		return new Ext.Panel({	    		
	    		region:"north",
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.search,this.grid]
		});
	},
	createCheatStuPanel:function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "参考单位",   align:"center", sortable:true, dataIndex:"XXMC"},
			//{header: "等级",   align:"center", sortable:true, dataIndex:"NJMC"},
			//{header: "科目",   align:"center", sortable:true, dataIndex:"BJMC"},
			{header: "身份证件号",   align:"center", sortable:true, dataIndex:"SFZJH"},
			{header: "姓名",   align:"center", sortable:true, dataIndex:"XM"},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
			{header: "考试批次",   align:"center", sortable:true, dataIndex:"SUBJECTNAME"},
			{header: "违纪处理类型",   align:"center", sortable:true, dataIndex:"OPTTYPENAME"},
			{header: "扣除分数",   align:"center", sortable:true, dataIndex:"SCORE"},
			{header: "违纪情况",   align:"center", sortable:true, dataIndex:"WJQQ"},
			{header: "状态",   align:"center", sortable:true, dataIndex:"ZT"}
		];		
		this.CheatGrid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			id:"CheatGridS",
			tbar:[ 
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}
			      ,"->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",id:"tbar_delete",handler:this.deleteCheatStu,scope:this}
			      ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",id:"tbar_update",handler:this.updateCheatStu,scope:this}
			      ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",id:"tbar_add",handler:function(){
			    	  if(wjzt=="0" || wjzt=="1"){
			    		  Ext.MessageBox.alert("消息","已经上报或审核过的考试不能再增加新的违纪信息！");
			    	  }else {
			    		  this.addForm   = this.createAddEditForm();
				    	  this.addWindow = this.createAddWindow();
				  		  this.addWindow.add(this.addForm);
				  		  this.addWindow.show();
			    	  }			    	  
			      },scope:this}
			   ],
			page:true,
			rowNumber:true,
			excel:true,
			excelTitle:"违纪情况表",
			region:"center",
			action:"CheatStudent_getListPage.do",
			fields :["XXMC","WJID","KSCODE","SFZJH","XJH","XM","XB","SUBJECTNAME","SCORE","OPTTYPENAME","WJQQ","NJMC","BJMC","KMID","OPTTYPE","FLAG","ZT"],
			border:false
		});
		//Ext.getCmp("fh1").setVisible(false);
		//搜索区域
		var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel",width:210,readOnly:true});
    	var xmkhxjh_sel	= new Ext.ux.form.TextField({name:"xmkhxjh_sel",id:"xmkhxjh_sel",width:200});
    	var organ_lable = "参考单位：";
    	var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectCheatStu,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.CheatSearch.getForm().reset()},scope:this});
		if(mBspInfo.getUserType() == "2") {
			this.CheatSearch = new Ext.form.FormPanel({
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
								{html:"姓名/身份证件号：",baseCls:"label_right",width:120},
								{items:[xmkhxjh_sel],baseCls:"component",width:210},
								{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:210}
							] 
	                    }]  
			       }]  
		    });
		}else {
			this.CheatSearch = new Ext.form.FormPanel({
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
								{html:organ_lable,baseCls:"label_right",width:120},
								{items:[organ_sel],baseCls:"component",width:210},
								{html:"姓名/身份证件号：",baseCls:"label_right",width:120},
								{items:[xmkhxjh_sel],baseCls:"component",width:210},
								{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:210}
							] 
	                    }]  
			       }]  
		    });
		}		
	    return new Ext.Panel({
	    		title:"录入违纪情况",
	    		region:"north",
	    		height:500,
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.CheatSearch,this.CheatGrid]
	    	});
    },
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"fit",items:[this.tabPanel]});    	
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
    downloadTemplate:function(){
    	//下载模板
    	var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/cheatStudent_temp.xls";
    	window.open(path);
    },
    selectCheatStuInfo:function(){
    	var xn=Ext.getCmp('csxnxq_find').getCode();
    	this.grid.$load({"xnxqId":xn});
    },
    selectCheatStu:function(){
    	var organ_sel = "";
    	var xmkhxjh_sel = Ext.getCmp('xmkhxjh_sel').getValue();
    	if(mBspInfo.getUserType() != "2") {    	
    		organ_sel=this.CheatSearch.getForm().findField('organ_sel').getCodes();
    	}    	
		this.CheatGrid.$load({"lcid":lcid,"schools":organ_sel,"xmkhxjh":xmkhxjh_sel});		
    },
    initEditWindow:function(){
    	this.addForm   = this.createAddEditForm();
   		this.addWindow = this.createAddWindow();
   		this.addWindow.add(this.addForm);
   		this.eidtWindow.on("hide",function(){
    		this.addForm.$reset();
    	},this);
    },
    createAddEditForm:function(){
    	var wjid 	= new Ext.form.TextField({fieldLabel:"",hidden:true,name:"cheatStu.wjid",id:"wjid"});
    	var kslcid 	= new Ext.form.TextField({fieldLabel:"",hidden:true,name:"cheatStu.lcid",id:"lcid",value:lcid});
    	var kskh	= new Ext.form.TextField({name:"cheatStu.kskh",readOnly:false,id:"kskh",width:300,maxLength:20,regex:/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/,allowBlank:false,blankText:"身份证件号不能为空！"});///^[1-9]\d*$/
    	var kskmid 	= new Ext.form.TextField({hidden:true,name:"cheatStu.kmid",id:"kmid"});
		var kskm	= new Ext.ux.Combox({autoLoad:true,name:"kskm",dropAction:"getKskmBykslc",params:lcid,width:300,allowBlank:false,blankText:"考试批次为必选项！",listeners:{
			"select":function(){
				Ext.getCmp("kmid").setValue(this.getValue());
			}
		}});
		var opttype = new Ext.form.TextField({hidden:true,name:"cheatStu.opttype",id:"opttype"});
		var wjlx	= new Ext.form.ComboBox({name:"wjlx",store:wjcllxStore,displayField:'text',valueField:'value',mode:'local',triggerAction:'all',width:300,allowBlank:false,blankText:"处理类型为必选项！",editable:false,listeners:{
			"select":function(){
				Ext.getCmp("opttype").setValue(this.getValue());
				if(this.getValue()==2){
					Ext.getCmp("score").setDisabled(true);
				}else{
					Ext.getCmp("score").setDisabled(false);
				}
			}
		}});
		var kcfs	= new Ext.form.TextField({name:"cheatStu.score",id:"score",width:300,maxLength:5,regex: /^\d+(\.\d{1,2})?$/});
		var detail	= new Ext.form.TextArea({name:"cheatStu.detail",id:"detail",width:300,height:80,allowBlank:false,blankText:"考试批次为必选项！"});
		
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 2 
			}, 
			items:[
				{html:"身份证号：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[kskh],baseCls:"component",width:330},
				{html:"考试批次：",baseCls:"label_right",width:100}, 
				{html:"<font class='required'>*</font>",items:[kskm,kskmid],baseCls:"component",width:330},
				{html:"处理类型：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[opttype,wjlx],baseCls:"component",width:330},
				{html:"扣除分数：",baseCls:"label_right",width:100},
				{html:"",items:[kcfs],baseCls:"component",width:330},
				{html:"违纪情况：",baseCls:"label_right",width:100},
				{items:[detail],baseCls:"component",width:330,height:80}
			]
		});
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
				{layout:'form', xtype:"fieldset",title:"考生违纪情况",items:[panel]},
				{items:[wjid,kslcid]}
			]});
    },
    createAddWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addCheatStu,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){
			this.addWindow.hide();
			this.addForm   = this.createAddEditForm();
			this.addWindow = this.createAddWindow();
			this.addWindow.add(this.addForm);
		},scope:this});
		var thiz = this;
		return new Ext.ux.Window({
			 	width:500,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
				title:"考生违纪情况"});
    },
    addCheatStu:function(){   
    	var opttype = Ext.getCmp("opttype").getValue();    	
    	var score = Ext.getCmp("score").getValue();
    	if (opttype==1) {
    		if (score=="") {
    			Ext.MessageBox.alert("消息","请输入扣除分数！");
        		return;
    		}
    	}
    	if (parseFloat(score)>=100) {
    		Ext.MessageBox.alert("消息","请输入两位数字！");
    		return;
    	}
    	this.addForm.$submit({
    		action:"CheatStudent_addCheatStu.do",
    		handler:function(form,result,thiz){
    			thiz.addWindow.hide();
    			thiz.CheatGrid.$load();
    		},
    		scope:this
    	});
    },
    exportFile:function(){
    	//判断是否选择了轮次
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedCheatStu = this.grid.getSelectionModel().getSelections();
    	lcid = selectedCheatStu[0].get("LCID");
    	//判断该轮次的考试违纪是否已经上报，上报的不能再次导入
    	var wjflag = selectedCheatStu[0].get("WJFLAG");
    	if(wjflag=="0" || wjflag=="1"){
    		Ext.MessageBox.alert("消息","该考试的违纪考生情况已上报或审核，不能再次导入！");
    		return;
    	}
    	var _b_upFile = new Ext.Button({text:"上传",iconCls:"p-icons-upload",handler:this.exportCheatStuInfo,scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.fileUpWin.hide()},scope:this});
     	this.submitForm = new Ext.ux.FormPanel({
                 fileUpload: true,
                 frame:true,
                 enctype: 'multipart/form-data',
                 defaults:{xtype:"textfield",anchor:"95%"},
                 items: [{fieldLabel:"附件",xtype:"textfield",name:"upload",inputType:'file'}]
     	});
     	this.fileUpWin = new Ext.ux.Window({
     		title:"文件上传",	
     		width:400,
     		tbar:{cls:"ext_tabr",items:["->",_b_cancel,"->",_b_upFile]},
     		listeners:{
		 		hide:function(){
		 			this.submitForm.form.reset();
		 		},scope:this},
     		items:[this.submitForm]
     	});
     	var thiz = this;
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"导入时本考试之前所导入的违纪考生会失效，是否继续?",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,
    		fn:function(b){
    			if(b=='ok'){
    				thiz.fileUpWin.show();
    			}
    		}
    	})    	
    },
    exportCheatStuInfo:function(){
    	var filePath = this.submitForm.getForm().findField("upload").getRawValue();
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
    		action:"upfile_exportExcelCheatStuFile.do",
    		params:{
    			form:this.submitForm.getForm().findField("upload"),
				'lcid':lcid
			},
			scope:this,
			handler:function(form,result,scope){
				scope.fileUpWin.hide();
				scope.grid.$load();
			}
		});	
    },
    fanhui:function(){    	
    	this.CheatSearch.getForm().reset();
    	this.tabPanel.setActiveTab(this.panel);
  		this.grid.$load();
    },
    deleteCheatStu:function(){
    	var selected =  this.CheatGrid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedCheat = this.CheatGrid.getSelectionModel().getSelections();
    	var thiz = this;
    	var wjids = "'";
    	for(var i = 0; i < selectedCheat.length; i++) {
    		if(i != selectedCheat.length - 1) {
	    		wjids += selectedCheat[i].get("WJID") + "','";
    		}else {
	    		wjids += selectedCheat[i].get("WJID") + "'";
    		}
    	}
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"您确定要删除吗?",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,
    		fn:function(b){
    			if(b=='ok'){
    				JH.$request({
    					params:{'wjids':wjids},
    					action:"CheatStudent_delCheatStudent.do",
    					handler:function(){
    						thiz.CheatGrid.$load();
    					}
    				})
    			}
    		}
    	})
    },
    updateCheatStu:function(){
    	var selectedCheatStu = this.CheatGrid.getSelectionModel().getSelections();
    	if(selectedCheatStu.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
	    }
    	var wjid = selectedCheatStu[0].get("WJID");
    	Ext.getCmp("wjid").setValue(wjid);
    	Ext.getCmp("lcid").setValue(lcid);
    	Ext.getCmp("kskh").setValue(selectedCheatStu[0].get("SFZJH"));
    	this.addForm.getForm().findField("kskm").setValue(selectedCheatStu[0].get("KMID"));
    	Ext.getCmp("kmid").setValue(selectedCheatStu[0].get("KMID"));
    	this.addForm.getForm().findField("wjlx").setValue(selectedCheatStu[0].get("OPTTYPE"));
    	Ext.getCmp("opttype").setValue(selectedCheatStu[0].get("OPTTYPE"));
    	Ext.getCmp("score").setValue(selectedCheatStu[0].get("SCORE"));
    	if(selectedCheatStu[0].get("OPTTYPE")==2){
    		Ext.getCmp("score").setDisabled(true);
    	}else{
    		Ext.getCmp("score").setDisabled(false);
    	}
    	Ext.getCmp("detail").setValue(selectedCheatStu[0].get("WJQQ"));
    	Ext.getCmp("kskh").readOnly = true;
    	this.addForm.getForm().findField("kskm").setDisabled(true);
    	this.addWindow.show(null,function(){},this);
    },
    reported:function(){
    	//违纪处理考生上报
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
	    }
    	var selectedCheat = this.grid.getSelectionModel().getSelections();
    	var type = selectedCheat[0].get("DWTYPE");
    	if(type=="2"){
    		Ext.MessageBox.alert("消息","参考单位组织考试无需上报！");
    		return;
	    }
    	var thiz = this;
    	var dqlcid = selectedCheat[0].get("LCID");
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"您确定要上报吗?",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,
    		fn:function(b){
    			if(b=='ok'){
    				JH.$request({
    					params:{'lcid':dqlcid},
    					action:"CheatStudent_reportedCheatStudent.do",
    					handler:function(){
    						thiz.grid.$load();
    					}
    				})
    			}
    		}
    	})
    }
});
var getPath = function(){
	var curWwwPath=window.document.location.href;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName=window.document.location.pathname;
	var pos=curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8083
	var localhostPaht=curWwwPath.substring(0,pos);
	//获取带"/"的项目名，如：/uimcardprj
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	return localhostPaht+projectName;
}