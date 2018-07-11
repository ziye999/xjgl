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
    	
    },
    /** 对组件设置监听 **/
    initListener:function(){
    	
    },    
    initComponent :function(){
		this.mainPanel = this.createMainPanel();
   		this.chouQuPanel = this.createChouQuForm();
   		this.ChouquWin = this.createChouquWindow();
   		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.mainPanel,this.chouQuPanel]   
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
    	//显示考试轮次主页面
    	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true,
			listeners:{
    			"selectionchange":function(sm){
    				var str = sm.getSelections();
    				if(str[0].get("SHFLAG")=="已上报"||str[0].get("SHFLAG")=="已审核"){
    					Ext.getCmp('sb').setDisabled(true);
    					Ext.getCmp('cq').setDisabled(true);
    					Ext.getCmp('dr').setDisabled(true);
    					Ext.getCmp('dr2').setDisabled(true);
    					Ext.getCmp('xz').setDisabled(true);
    				}else{
    					Ext.getCmp('sb').setDisabled(false);
    					Ext.getCmp('cq').setDisabled(false);
    					Ext.getCmp('dr').setDisabled(false);
    					Ext.getCmp('dr2').setDisabled(false);
    					Ext.getCmp('xz').setDisabled(false);
    				}
    			}
    		}});
		var cm = [
		    sm,
			{header: "年度",   		align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   		align:"center", sortable:true, dataIndex:"XQ"},
			{header: "考试名称",  	align:"center", sortable:true, dataIndex:"EXAMNAME"},
			{header: "组织单位",   	align:"center", sortable:true, dataIndex:"MC"},
			{header: "参考单位",   	align:"center", sortable:true, dataIndex:"CKDW"},
			{header: "考生数量",   	align:"center", sortable:true, dataIndex:"SL"},
			{header: "上报审核状态",   align:"center", sortable:true, dataIndex:"SHFLAG"},
			{header: "缴费通过单位个数",   align:"center", sortable:true, dataIndex:"TGDW"}
		];		
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"考生报名-生成考生名单",
			tbar:[ 
			      "->",{xtype:"button",text:"上报",id:"sb",iconCls:"p-icons-upload",handler:this.updateExaminee,scope:this}
			      ,"->",{xtype:"button",text:"抽取",id:"cq",iconCls:"p-icons-update",handler:this.addExamSection,scope:this}
			      ,"->",{xtype:"button",text:"导入考生信息",id:"dr",iconCls:"p-icons-upload",handler:this.exportKs,scope:this}
			      ,{xtype:"button",text:"导入补考考生",id:"dr2",iconCls:"p-icons-upload",handler:this.exportBkKs,scope:this},"->",{xtype:"button",text:"下载模板",id:"xz",iconCls:"p-icons-download",handler:this.downloadTemplate,scope:this}
			    ],
			    page:true,
			    rowNumber:true,
			    region:"center",
			    excel:true,
			    action:"registration_getListPage.do",
			    fields :["LCID","XN","XQ","EXAMNAME","MC","CKDW","SL","DWID","DWTYPE","SHFLAG","TGDW"],
			    border:false
		});
	
		//搜索区域		
		var xn_find=new Ext.ux.form.XnxqField({width:180,id:"exrxn_find",width:210,readOnly:true});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamSubject,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.submitForm = new Ext.ux.FormPanel({
			fileUpload: true,
			frame:true,
			enctype: 'multipart/form-data',
			defaults:{xtype:"textfield",anchor:"95%"},
			items: [{fieldLabel:"附件",xtype:"textfield",name:"upload",inputType:'file'}]
		});
	
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
						columns: 3
					}, 
					baseCls:"table",
					items:[
							{html:"年度：",baseCls:"label_right",width:120},
							{items:[xn_find],baseCls:"component",width:210},
							{layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
						] 
				}]  
			}]  
	    })
		return new Ext.Panel({layout:"border",region:"center",items:[this.search,this.grid]})
    },
    createChouQuForm:function(){
    	var sfzjh		= new Ext.ux.form.TextField({name:"sfzjh",id:"sfzjh",width:180});
    	var cx 			= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.maingridExamStu,scope:this});
		var cz 			= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.mainsearch.getForm().reset()},scope:this});
		
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
					       {html:"身份证件号：",baseCls:"label_right",width:170},
					       {items:[sfzjh],baseCls:"component",width:200},
					       {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
					      ] 
                  	}]  
		      	}]  
	    	})
		
    	//选择轮次点击抽取后的主页面
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [sm,
		          {header: "参考单位",   	align:"center", sortable:true, dataIndex:"XXMC"},
		          //{header: "等级",   	align:"center", sortable:true, dataIndex:"NJMC"},
		          //{header: "科目",   	align:"center", sortable:true, dataIndex:'BJMC'},
		          {header: "姓名",   	align:"center", sortable:true, dataIndex:"XM"},
		          {header: "性别",   	align:"center", sortable:true, dataIndex:"XB"},
		          {header: "考号",   	align:"center", sortable:true, dataIndex:'KSCODE'},
		          {header: "身份证号",   	align:"center", sortable:true, dataIndex:"SFZJH"}];
   		
		this.grid2 = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:'考生信息表',
			page:true,
			rowNumber:true,
			tbar:[ 
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.returnMainPanel,scope:this}
			      ,"->",{id:"qd",xtype:"button",text:"保存",iconCls:"p-icons-save",handler:this.addCqksSubject,scope:this}
			      ,"->",{id:"cq1",xtype:"button",text:"抽取",iconCls:"p-icons-update",handler:function(){
			      if(kxx == ""){
			    	  Ext.MessageBox.alert("消息","请设置考号！");
			    	  return;
			      }
			  	  this.ChouquWin.show()},scope:this}
			      ,"->",{xtype:"button",text:"设置考号",iconCls:"p-icons-update",handler:this.setExam,scope:this}
			   ],
			region:"center",
			action:'registrationXs_getListPage.do',
			fields:["XS_JBXX_ID","XM","XB","KSCODE","XJH","SFZJH","XXMC"],
			border:false
		});		
	    return new Ext.Panel({layout:"border",region:"center",items:[this.mainsearch,this.grid2]})
    },
    maingridExamStu:function() {
    	var sfzjh = Ext.getCmp('sfzjh').getValue();
    	this.grid2.$load({"limit":this.grid2.getBottomToolbar().pageSize,"sfzjh":sfzjh});
    },
    createChouquWindow:function(){
    	//设置抽取条件，显示设置抽取条件弹出框
    	var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel",width:210,readOnly:true});
    	var organ_lable = "参考单位：";
    	//查询条件复选
    	/*var myCheckboxGroup =  new Ext.form.CheckboxGroup({
			id:'myGroup',  
            xtype: 'checkboxgroup',
            fieldLabel: 'Single Column',
            itemCls: 'x-check-group-alt',
            columns : 4,
            items: [
                 {boxLabel: '流失', name:'0',checked: true},
                 {boxLabel: '休学', name:'1',checked: true},
                 {boxLabel: '退学', name:'2',checked: true},
                 {boxLabel: '转出', name:'3',checked: true}
         	]
     	});*/
    	this.search2 = new Ext.form.FormPanel({
    		region: "north",
    		height:200,
    		items:[{  
    			layout:'form',  
    			xtype:'fieldset',  
    			style:'margin:10 10',
    			title:'',  
    			items: [{
    					xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 2
						}, 
						baseCls:"table",
						items:[
							{html:organ_lable,baseCls:"label_right",width:140},
							{items:[organ_sel],baseCls:"component",width:210}
							/*{html:"不抽取考生学籍状态：",baseCls:"label_right",width:160},
							{items:[myCheckboxGroup],baseCls:"component",width:210}*/
						] 
	                }]  
		       }]  
	    	});
	    var _b_save	  = new Ext.Button({text:"确定",iconCls:"p-icons-save",handler:this.selectCqksSubject,scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.ChouquWin.hide()},scope:this});
		var _b_reset = new Ext.Button({text:"重置",iconCls:"p-icons-delete",handler:function(){this.search2.getForm().reset()},scope:this});
	    return new Ext.ux.Window({
     		title:"抽取考生",	
     		height:180,
     		width:440,
     		tbar:{cls:"ext_tabr",
				  items:["->",_b_cancel,"->",_b_reset,"->",_b_save]},
			items:[this.search2]
     	});
    },
    addExamSection:function(){
    	//主页面根据轮次进行数据抽取
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行抽取！");
    		return;
    	}
    	lcid = selectedBuildings[0].get("LCID");
    	this.tabPanel.setActiveTab(this.chouQuPanel);
    	this.mainsearch.getForm().reset();
    },
	selectExamSubject:function(){
		//主页面根据年度查询数据
		var xn=Ext.getCmp('exrxn_find').getValue();
		this.grid.$load({"XN":xn});
		Ext.getCmp('qd').setDisabled(false);//禁用相关的表单元素
	},
    selectCqksSubject:function(){
    	if(kxx == ''){
    		Ext.MessageBox.alert("消息","请设置考号！");
    		return;
    	}    	
    	var organ_sel = this.search2.getForm().findField("organ_sel").getCodes();
    	/*var s = '';
    	for (var i = 0; i < Ext.getCmp('myGroup').items.length; i++){    
           s = s +','+Ext.getCmp('myGroup').items.itemAt(i).checked;
        }*/
    	var sfzjh = Ext.getCmp('sfzjh').getValue();
    	this.grid2.$load({"school":organ_sel,"khao":kxx,"lcid":lcid,"sfzjh":sfzjh});
    	this.ChouquWin.hide();
    },
    addCqksSubject:function(){
    	//保存抽取的考生
    	var selected =  this.grid2.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	Ext.getCmp('qd').setDisabled(true);
    	var selectedStudent = this.grid2.getSelectionModel().getSelections();
    	var thiz = this;
    	var xjh = "";
    	var ksh = "";
    	for(var i = 0; i < selectedStudent.length; i++) {
    		if(i != selectedStudent.length - 1) {
    			xjh += selectedStudent[i].get("XJH") + ",";
	    		ksh += selectedStudent[i].get("KSCODE") + ",";
    		}else {
    			xjh += selectedStudent[i].get("XJH");
	    		ksh += selectedStudent[i].get("KSCODE");
    		}
    	}
    	Ext.Msg.confirm('消息','确认保存这'+selectedStudent.length+'条记录？', function (id) {
    		if (id == 'yes') {
    			Ext.Ajax.request({   
    	       		url:'registration_addCqxs.do',
    	       		params:{
    	       			'lcid':lcid,
    	       			'xjh':xjh,
    	       			'khao':ksh
    	       		},
    	       		success: function(resp,opts) {
    	       			var respText = Ext.util.JSON.decode(resp.responseText);
    	       			if (respText.msg!='') {
    	       				Ext.MessageBox.alert("提示",respText.msg);
    	       			}       			
    	       			thiz.selectExamSubject();
    	       			thiz.selectCqksSubject();
    	       		},
    	       		failure: function(resp,opts){
    	       			Ext.Msg.alert('错误', "保存失败！");
    	       		}  
    	    	}); 
    		}
    	});   	
    },
    updateExaminee:function(){
    	//修改状态，上报
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}
    	var lcid = selectedBuildings[0].get("LCID");
    	var thiz=this;
    	Ext.Ajax.request({   
       		url:'registration_updateCqxs.do',
       		params:{
       			'lcid':lcid
       		},
       		success: function(resp,opts) {
       			var respText = Ext.util.JSON.decode(resp.responseText);
       			if (respText.msg!='') {
       				Ext.MessageBox.alert("提示",respText.msg);
       			}
       			thiz.selectExamSubject();
       			thiz.selectExamSubject();
       		},
       		failure: function(resp,opts){
       			Ext.Msg.alert('错误', "审核失败！");
       		}        	
    	});
    },
    returnMainPanel:function(){
    	kxx = "";
    	this.tabPanel.setActiveTab(this.mainPanel);
    	this.initQueryDate();
    	this.grid2.getStore().removeAll();
    	this.selectExamSubject();
    },
    setExam:function(){
    
    	//设置考号
    	var _b_save	= new Ext.Button({text:"确定",iconCls:"p-icons-save",handler:function(){
    		var reg = /^\d+$/;
    		if(!reg.test(Ext.getCmp("kh").getValue())){
    			return;
    		}
    		if(Ext.getCmp("kh").getValue().length>10){
        		Ext.MessageBox.alert("消息","考号长度不能超过十位！");
        		return;
        	}
    		Ext.getCmp('cq1').setDisabled(false);
    		kxx = Ext.getCmp("kh").getValue();
    		this.fileUpWin.hide()
    	},scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.fileUpWin.hide()},scope:this});
		var kh	= new Ext.form.TextField({id:"kh",maxLength:10,width:190,regex : /^\d+$/,regexText : '考号只能为数字！'});
		Ext.getCmp('kh').setValue(kxx);
		this.search3 = new Ext.form.FormPanel({
			region: "north",
		    height:150,
		    items:[{  
		    	layout:'form',  
		    	xtype:'fieldset',  
		    	style:'margin:10 10',
		    	items: [{
		    		xtype:"panel",
		    		layout:"table", 
					layoutConfig: { 
						columns: 2
					}, 
					baseCls:"table",
					items:[
						{html:"设置考号：",baseCls:"label_right",width:120},
						{items:[kh],baseCls:"component",width:210}
					] 
		    	}]  
		    }]  
		});
		
     	this.fileUpWin = new Ext.ux.Window({
     		title:"设置考号",	
     		height:150,
     		width:400,
     		tbar:{
				cls:"ext_tabr",
				items:["->",_b_cancel,"->",_b_save]
			},
			items:[this.search3]
     	});
     	this.fileUpWin.show(null,function(){     	
     	},this);
    },
    exportKs:function(){
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}    	
    	//导入教师信息
    	var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",id:"uploadButton",handler:this.exportFilesInfo,scope:this});
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
    exportBkKs:function(){
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}    	
    	//导入教师信息
    	var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",id:"uploadButton",handler:this.exportBkFilesInfo,scope:this});
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
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var lcid = selectedBuildings[0].get("LCID"); 
    	Ext.getCmp('uploadButton').setDisabled(true);
    	this.submitForm.$submit({
    		timeout:3600000,
    		async:false,
    		action:"exam_exportKsInfo.do",
    		params:{
				form:this.submitForm.getForm().findField("upload"),
				'lcid':lcid
			},			
//			handler:function(form,result,scope){
//				alert(result);
//				Ext.getCmp('uploadButton').setDisabled(false);
//				scope.fileUpWin.hide();	
//				scope.grid.$load();	
//				scope.grid.$load();
//			},
			success: function(form, action) {
			       Ext.Msg.alert("提示",action.result.msg);
			       this.fileUpWin.hide();
				   this.grid.$load();
			    },
			failure: function(form, action) { 
				var that = this;
				   Ext.Msg.alert("提示",action.result.msg,function(b,t){
					   that.fileUpWin.hide();
					   that.grid.$load();  
					   if(action.result.data!=null && action.result.data!=''){
						   window.open(Ext.get("ServerPath").dom.value+"/"+action.result.data.replace(/\\/g, "\/"));
						}
				   });
			   },
			scope:this
		});    		    	    	   	
   },
   exportBkFilesInfo:function(){
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
   	var selectedBuildings = this.grid.getSelectionModel().getSelections();
   	var lcid = selectedBuildings[0].get("LCID"); 
   	Ext.getCmp('uploadButton').setDisabled(true);
   	this.submitForm.$submit({
   		timeout:3600000,
   		async:false,
   		action:"examst_exportBkKsInfo.do",
   		params:{
				form:this.submitForm.getForm().findField("upload"),
				'lcid':lcid
			},			
//			handler:function(form,result,scope){
//				alert(result);
//				Ext.getCmp('uploadButton').setDisabled(false);
//				scope.fileUpWin.hide();	
//				scope.grid.$load();	
//				scope.grid.$load();
//			},
			success: function(form, action) {
			       Ext.Msg.alert("提示",action.result.msg);
			       this.fileUpWin.hide();
				   this.grid.$load();
			    },
			failure: function(form, action) { 
				var that = this;
				   Ext.Msg.alert("提示",action.result.msg,function(b,t){
					   that.fileUpWin.hide();
					   that.grid.$load();  
					   if(action.result.data!=null && action.result.data!=''){
						   window.open(Ext.get("ServerPath").dom.value+"/"+action.result.data.replace(/\\/g, "\/"));
						}
				   });
			   },
			scope:this
		});    		    	    	   	
  },
   downloadTemplate:function(){
	   var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/examregistration.xls";
	   window.open(path);   		
   }
});
