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
    	
    },    
    /** 对组件设置监听 **/
    initListener:function(){    	
    	this.eidtWindow.on("hide",function(){       		
    		this.editForm.$reset();    		
    	},this);
    	this.orgTree.on("click",function(node){
    		if(node.attributes.index == 2){
    			Ext.getCmp('search_form3').getForm().reset();
    			this.$load({"teacher.schoolid":node.id,"teacher.xm":Ext.getCmp('teacher_xm_find').getValue()})
    		}else {
    			Ext.Msg.alert("提示","只能对有组考的组织单位添加监考老师信息!");
        		return;
    		}
    	},this.grid);    	
    	this.orgTree.on("afterrender",function(){
    		this.orgTree.expandAll(true);
    	},this);
    	this.grid.on("rowdblclick",function(grid, rowIndex, e ){
    		var jsid = grid.store.getAt(rowIndex).get("JSID");
    		Ext.getCmp('schoolid').setValue(grid.store.getAt(rowIndex).get("SCHOOLID"));
    		Ext.getCmp('ckdwname').setValue(grid.store.getAt(rowIndex).get("XXMC"));    		
    		Ext.getCmp("xbm").setValue(grid.store.getAt(rowIndex).get("XBM"));
    		Ext.getCmp("mzm").setValue(grid.store.getAt(rowIndex).get("MZM"));
    		Ext.getCmp("sfzjlxm").setValue(grid.store.getAt(rowIndex).get("SFZJLXM"));
    		Ext.getCmp("jgName").setValue(grid.store.getAt(rowIndex).get("JGMC"));
    		Ext.getCmp("zzmmm").setValue(grid.store.getAt(rowIndex).get("ZZMMM"));
    		Ext.getCmp("jkzkm").setValue(grid.store.getAt(rowIndex).get("JKZKM"));
    		Ext.getCmp("rjkmm").setValue(grid.store.getAt(rowIndex).get("RJKMM"));    		
    		var url = grid.store.getAt(rowIndex).get("PHOTOPATH") == ""?"/img/basicsinfo/mrzp_img.jpg":grid.store.getAt(rowIndex).get("PHOTOPATH");
    		url = Ext.get("ServerPath").dom.value+url+"?time="+new Date().getTime();
    		Ext.getCmp("imgBox").getEl().dom.src = url;
    		this.eidtWindow.show(null,function(){
    			this.editForm.$load({
    				params:{'teacher.jsid':jsid},
    				action:"teacher_loadTeacher.do",
    				handler:function(form,result,scope){
    					form.findField("xb").setValue(grid.store.getAt(rowIndex).get("XBM"));
    					form.findField("mz").setValue(grid.store.getAt(rowIndex).get("MZM"));
    					form.findField("sfzjlx").setValue(grid.store.getAt(rowIndex).get("SFZJLXM"));
    					form.findField("teacher.csrq").setValue(grid.store.getAt(rowIndex).get("CSRQ"));
    					form.findField("zzmm").setValue(grid.store.getAt(rowIndex).get("ZZMMM"));
    					form.findField("jkzk").setValue(grid.store.getAt(rowIndex).get("JKZKM"));
    					form.findField("rjkm").setValue(grid.store.getAt(rowIndex).get("RJKMM"));
    				}
    			});
    		},this)
    	},this);
    },   
   	initComponent :function(){
   		this.editForm   = this.createTeacherEditForm();
   		this.eidtWindow = this.createTeacherWindow();
   		this.eidtWindow.add(this.editForm);
   		
   		this.orgTree = new Ext.ux.TreePanel({region:"west",
            rootVisible:false,
            title:"所属单位",
            collapseMode : "mini",
            split:true,
            minSize: 120,
            width:200,
            maxSize: 300,
            autoScroll:true,
            action:"teacher_getCkdw.do"
   		});
   		
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "监考姓名",   align:"center", sortable:true, dataIndex:"XM"},
			{header: "教职工编号",   align:"center", sortable:true, dataIndex:"GH"},
			{header: "身份证号",   align:"center", sortable:true, dataIndex:"SFZJH"},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
			{header: "籍贯",   align:"center", sortable:true, dataIndex:"JGMC"},
			{header: "民族",   align:"center", sortable:true, dataIndex:"MZ"},
			{header: "出生年月",   align:"center", sortable:true, dataIndex:"CSRQ"},
			{header: "住址",   align:"center", sortable:true, dataIndex:"JTZZ"},
			{header: "电话",   align:"center", sortable:true, dataIndex:"LXDH"},
			{header: "科目",   align:"center", sortable:true, dataIndex:"RJKM"}			
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"监考信息",
			tbar:[ 
			      "->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteTeacher,scope:this}
			      ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateTeacher,scope:this}
			      ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.showEidtWindow()},scope:this}
			      ,"->",{xtype:"button",text:"导入监考信息",iconCls:"p-icons-upload",handler:this.exportTeacher,scope:this}
			      ,"->",{xtype:"button",text:"下载模板",iconCls:"p-icons-download",handler:this.downloadTemplate,scope:this}
			    ],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			pdf:true,
			excelTitle:"监考信息表",
			action:"teacher_getListPage.do",
			fields :["JSID","XM","GH","SFZJH","XB","JGMC","MZ","CSRQ","JTZZ","LXDH","RJKM","BZR","XBM","MZM","SFZJLXM","JG","ZZMMM","JKZKM","BZR_M","PHOTOPATH","RJKMM","SCHOOLID","XXMC"],
			border:false
		});
		//搜索区域
		var teachername	= new Ext.form.TextField({fieldLabel:"监考姓名",id:"teacher_xm_find",maxLength:200, width:200});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectTeacherInfo,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.search = new Ext.form.FormPanel({
			region: "north",
			height:90,
			id:"search_form3",
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
							{html:"姓名/职工编号：",baseCls:"label_right",width:170}, 
							{items:[teachername],baseCls:"component",width:210},
							{layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
						] 
                    }]  
		       }]  
	    	})
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"border",
    		items:[this.orgTree,{
			layout: 'border',
	        region:'center',
	        border: false,
	        split:true,
			margins: '2 0 5 5',
	        width: 275,
	        minSize: 100,
	        maxSize: 500,
			items: [this.search,this.grid]
		}]});
    	this.eidtWindow.show();
    	this.eidtWindow.hide();
    },
    initQueryDate:function(){    	
    	if(this.getOrgTreeSelectNode() == null) {
    		Ext.Msg.alert("提示","请选择所属单位!");
    	}else {
    		this.grid.$load({"teacher.schoolid":this.getOrgTreeSelectNode().id,"teacher.xm":Ext.getCmp('teacher_xm_find').getValue()});	    	
    	}
    },
    getOrgTreeSelectNode:function(){
    	return this.orgTree.getSelectionModel().getSelectedNode();
    },
    createTeacherEditForm:function(){
    	var ckdwname	= new Ext.form.TextField({width:240,id:"ckdwname",readOnly:true});
    	var schoolid	= new Ext.form.TextField({fieldLabel:"",hidden:true,name:"teacher.schoolid",id:"schoolid"});    	
    	var jsid = new Ext.form.TextField({fieldLabel:"",hidden:true,name:"teacher.jsid",id:"jsid"});
		var xm	 = new Ext.form.TextField({maxLength:50,name:"teacher.xm",id:"xm",allowBlank:false,blankText:"姓名不能为空！"});
		var xbm  = new Ext.form.TextField({hidden:true,name:"teacher.xbm",id:"xbm"});
		var xb	 = new Ext.ux.Combox({name:"xb",dropAction:"sys_enum_xb",width:140,allowBlank:false,blankText:"性别为必选项！",listeners:{
			"select":function(){
				Ext.getCmp("xbm").setValue(this.getValue());
			}
		}});
		var csrq = new Ext.form.DateField({width:140,name:"teacher.csrq",editable:false,format:"Y-m-d",emptyText:"请选择日期..."}); 
		
		var treeLoader = new Ext.tree.TreeLoader({//树节点的数据来源
			dataUrl:"adminTree_getAdminRegionTree.do?node="
		});
		var jgm = new Ext.form.TextField({name:"teacher.jg",id:"jg",hidden:true});
		var jg = new Ext.form.TextField({id:"jgName",maxLength:100,
		listeners:{render: function(p) {
			p.getEl().on('focus', function(p){ 
				//组建籍贯树形菜单
				var _b_save	  = new Ext.Button({text:"确定",iconCls:"p-icons-upload",handler:function(){this.jgWin.hide()},scope:this});
				var _b_cancel = new Ext.Button({text:"关闭",iconCls:"p-icons-checkclose",handler:function(){jg.setValue("");jgm.setValue("");this.jgWin.hide()},scope:this});
		   		this.adminTree = new Ext.ux.TreePanel({
		   			autoScroll:true,
		   			rootVisible:false,
		   			collapseMode : "mini",
		   			split:true,
		   			loader: treeLoader,
		   			height:500,
		   			listeners:{
		   				beforeload:function(node,e){
		   					treeLoader.dataUrl = "adminTree_getAdminRegionTree.do?node="+node.id;
		   				},
		   				click:function(node,e){
		   					Ext.getCmp('jgName').setValue(node.text); 
		   					Ext.getCmp('jg').setValue(node.id); 
		   				}
		   			}
				});
		   		this.jgWin = new Ext.ux.Window({
		     		title:"行政区划",	
		     		width:800,
					tbar:{
						cls:"ext_tabr",
						items:[ 
						 	 "->",_b_save
				 			 ,"->",_b_cancel
						  ]
					},
		     		items:[this.adminTree]
		     	}).show();
			 });   
		}},
		allowBlank:false,blankText:"籍贯不能为空！"});
		var mzm = new Ext.form.TextField({hidden:true,name:"teacher.mzm",id:"mzm"});
		var mz	= new Ext.ux.DictCombox({name:"mz",dictCode:"ZD_MZ",width:140,allowBlank:false,blankText:"民族为必选项！",listeners:{
			"select":function(){
				Ext.getCmp("mzm").setValue(this.getValue());
			}
		}});
		var gh	= new Ext.form.TextField({name:"teacher.gh",id:"gh",maxLength:50,allowBlank:false,blankText:"工号不能为空！"});
		var sfzjlxm = new Ext.form.TextField({hidden:true,name:"teacher.sfzjlxm",id:"sfzjlxm"});
		var sfzjlx = new Ext.ux.DictCombox({name:"sfzjlx",dictCode:"ZD_ZJLX",width:240,allowBlank:false,blankText:"身份证件类型为必选项！",listeners:{
			"select":function(){
				Ext.getCmp("sfzjlxm").setValue(this.getValue());
			}
		}});
		var sfzh = new Ext.form.TextField({name:"teacher.sfzjh",id:"sfzjh",width:240,maxLength:50,allowBlank:false,blankText:"身份证件号不能为空！",regex:/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/});//,regex : /^(\d{18,18}|\d{15,15}|\d{17,17}x)$/,regexText : '身份证件号错误！'
		var lxdh = new Ext.form.TextField({name:"teacher.lxdh",id:"lxdh",maxLength:50,width:240,regex : /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,regexText : '电话号码格式错误！'});
		var jtzz = new Ext.form.TextField({name:"teacher.jtzz",id:"jtzz",maxLength:200,width:240});
		var zzmmm = new Ext.form.TextField({hidden:true,name:"teacher.zzmmm",id:"zzmmm"});
		var zzmm = new Ext.ux.DictCombox({name:"zzmm",dictCode:"ZD_ZZMM",width:240,allowBlank:false,blankText:"政治面貌为必选项！",listeners:{
			"select":function(){
				Ext.getCmp("zzmmm").setValue(this.getValue());
			}
		}});
		var jkzz = new Ext.form.TextField({hidden:true,name:"teacher.jkzkm",id:"jkzkm"});
		var jkzzm = new Ext.ux.DictCombox({name:"jkzk",dictCode:"ZD_JKZK",width:240,allowBlank:false,blankText:"健康状况为必选项！",listeners:{
			"select":function(){
				Ext.getCmp("jkzkm").setValue(this.getValue());
			}
		}});
		var rjkmm = new Ext.form.TextField({hidden:true,name:"teacher.rjkmm",id:"rjkmm"});
		var rjkm = new Ext.ux.Combox({name:"rjkm",dropAction:"getKeChengBySchool",width:240,allowBlank:false,blankText:"科目为必选项！",listeners:{
			"select":function(){
				Ext.getCmp("rjkmm").setValue(this.getValue());
			}
		}});
		
		var photoPath = new Ext.form.TextField({hidden:true,name:"teacher.photopath",id:"photopath"});
		var path = Ext.get("ServerPath").dom.value+'/img/basicsinfo/mrzp_img.jpg';
		var imagebox = new Ext.BoxComponent({autoEl:{tag:'img',src:path,id:"imgPath"},id:"imgBox",width:90,height:110});
		var fileBtn = new Ext.Button({cls:"base_btn",iconCls:"p-icons-upload",text:"上传",handler:this.fileup,scope:this,width:80});
		this.submitForm = new Ext.ux.FormPanel({
        	fileUpload: true,
        	frame:true,
            enctype: 'multipart/form-data',
            defaults:{xtype:"textfield",anchor:"95%"},
            items: [{fieldLabel:"附件",xtype:"textfield",name:"upload",inputType:'file'}]
		});
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 6 
			}, 
			items:[			       
				{html:"姓名：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[xm],baseCls:"component",width:160},
				{html:"性别：",baseCls:"label_right",width:100}, 
				{html:"<font class='required'>*</font>",items:[xb,xbm],baseCls:"component",width:160},
				{html:"出生日期：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[csrq],baseCls:"component",width:160},
				{html:"籍贯：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[jg,jgm],baseCls:"component",width:160},
				{html:"民族：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[mz,mzm],baseCls:"component",width:160},
				{html:"职工编号：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[gh],baseCls:"component",width:160},
				{html:"身份证件类型：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[sfzjlx,sfzjlxm],baseCls:"component",width:260,colspan:2},
				{html:"身份证件号：",baseCls:"label_right",width:160},
				{html:"<font class='required'>*</font>",items:[sfzh],baseCls:"component",width:260,colspan:2},
				{html:"联系电话：",baseCls:"label_right",width:100},{items:[lxdh],baseCls:"component",width:260,colspan:2},
				{html:"家庭住址：",baseCls:"label_right",width:160},{items:[jtzz],baseCls:"component",width:260,colspan:2},
				{html:"政治面貌：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[zzmm,zzmmm],baseCls:"component",width:260,colspan:2},
				{html:"照片：",baseCls:"label_right",width:160,rowspan:4},{items:[imagebox,fileBtn,photoPath],baseCls:"component",width:260,rowspan:4,colspan:2},
				{html:"健康状况：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[jkzz,jkzzm],baseCls:"component",width:260,colspan:2},
				{html:"科目：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[rjkm,rjkmm],baseCls:"component",width:260,colspan:2},
				{html:"所属单位：",baseCls:"label_right"},
			    {html:"<font class='required'>&nbsp;&nbsp;</font>",items:[ckdwname],baseCls:"component",width:260,colspan:2}
			]		
		});
		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
				{layout:'form',xtype:"fieldset",title:"监考信息",items:[panel]},
				{items:[jsid,schoolid]}
			]});				
    },
    createTeacherWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addTeacher,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:845,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
			 	title:"监考信息维护"});		
    },
    downloadTemplate:function(){
    	//下载模板
    	var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/teacherInfo_temp.xls";
    	window.open(path);
    },
    showEidtWindow:function() {
    	if(this.getOrgTreeSelectNode() == null) {
    		Ext.Msg.alert("提示","请选择所属单位!");
    	}else {
    		if(this.getOrgTreeSelectNode().attributes.index == 2){        		 
    			//Ext.getCmp('ckdwname').setValue(this.getOrgTreeSelectNode().text);
        		Ext.getCmp('schoolid').setValue(this.getOrgTreeSelectNode().id);
        		Ext.getCmp("imgBox").getEl().dom.src = Ext.get("ServerPath").dom.value+'/img/basicsinfo/mrzp_img.jpg';
        		this.eidtWindow.show();
        	}else {
        		Ext.Msg.alert("提示","只能对有组考的组织单位添加监考老师信息!");
        	}     		
    	}    	
    },
    addTeacher:function(){
    	if(checkDate(new Date(),this.editForm.getForm().findField("teacher.csrq").getValue())){
			Ext.MessageBox.alert("错误","出生日期不能大于当前日期！");
			return;
    	}    	
    	this.editForm.$submit({
    		action:"teacher_addTeacher.do",
    		handler:function(form,result,thiz){
    			thiz.eidtWindow.hide();
    			thiz.grid.$load();
    		},
    		scope:this
    	});
    },
    exportTeacher:function(){
    	//导入监考信息
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
    		action:"upfile_exportExcelTeacherFile.do",
    		params:{
    			form:this.submitForm.getForm().findField("upload")
			},
			scope:this,
			handler:function(form,result,scope){
				scope.fileUpWin.hide();
				scope.grid.$load();
				scope.grid.$load();
			}
		});	
    },
    deleteTeacher:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedTeacher = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var jsids = "'";
    	for(var i = 0; i < selectedTeacher.length; i++) {
    		if(i != selectedTeacher.length - 1) {
	    		jsids += selectedTeacher[i].get("JSID") + "','";
    		}else {
	    		jsids += selectedTeacher[i].get("JSID") + "'";
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
    					params:{'jsids':jsids},
    					action:"teacher_delTeacher.do",
    					handler:function(){
    						thiz.grid.$load();
    					}
    				})    					
    			}
    		}
    	})
    },
    updateTeacher:function(){    	
    	var selectedTeacher = this.grid.getSelectionModel().getSelections();
	    if(selectedTeacher.length != 1){
	    	Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
	    	return;
	    }
    	var jsid = selectedTeacher[0].get("JSID");
    	Ext.getCmp('schoolid').setValue(selectedTeacher[0].get("SCHOOLID"));
    	Ext.getCmp('ckdwname').setValue(selectedTeacher[0].get("XXMC"));
    	Ext.getCmp("xbm").setValue(selectedTeacher[0].get("XBM"));
    	Ext.getCmp("mzm").setValue(selectedTeacher[0].get("MZM"));
    	Ext.getCmp("sfzjlxm").setValue(selectedTeacher[0].get("SFZJLXM"));
    	Ext.getCmp("jgName").setValue(selectedTeacher[0].get("JGMC"));
    	Ext.getCmp("zzmmm").setValue(selectedTeacher[0].get("ZZMMM"));
    	Ext.getCmp("jkzkm").setValue(selectedTeacher[0].get("JKZKM"));    		
    	Ext.getCmp("rjkmm").setValue(selectedTeacher[0].get("RJKMM"));
    	var url = selectedTeacher[0].get("PHOTOPATH") == ""?"/img/basicsinfo/mrzp_img.jpg":selectedTeacher[0].get("PHOTOPATH");
    	url = Ext.get("ServerPath").dom.value+url+"?time="+new Date().getTime();
    	Ext.getCmp("imgBox").getEl().dom.src = url;
    	this.eidtWindow.show(null,function(){
    		this.editForm.$load({
    			params:{'teacher.jsid':jsid},
    			action:"teacher_loadTeacher.do",
    			handler:function(form,result,scope){    					
    				form.findField("xb").setValue(selectedTeacher[0].get("XBM"));
					form.findField("mz").setValue(selectedTeacher[0].get("MZM"));
					form.findField("sfzjlx").setValue(selectedTeacher[0].get("SFZJLXM"));
					form.findField("teacher.csrq").setValue(selectedTeacher[0].get("CSRQ"));
					form.findField("zzmm").setValue(selectedTeacher[0].get("ZZMMM"));
					form.findField("jkzk").setValue(selectedTeacher[0].get("JKZKM"));
					form.findField("rjkm").setValue(selectedTeacher[0].get("RJKMM"));
    			}
    		});
    	},this)
    },
    fileup:function(){    	
    	var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",handler:this.saveFilesInfo,scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.fileUpWin.hide()},scope:this});
		     	
		this.fileUpWin = new Ext.ux.Window({
     		title:"文件上传",	
     		width:400,
     		tbar:{
				cls:"ext_tabr",
				items:["->",_b_cancel,"->",_b_save]
			},
     		listeners:{
		 		hide:function(){
		 			this.submitForm.form.reset();
		 		},scope:this},
     		items:[this.submitForm]     		
     	});
     	this.fileUpWin.show(null,function(){},this);
     },
     saveFilesInfo:function(){
    	 var filePath = this.submitForm.getForm().findField("upload").getRawValue();
    	 //判断文件类型
    	 var objtype=filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
    	 var fileType=new Array(".jpg",".jpeg",".gif",".bmp",".png",".tiff");
    	 if(filePath==""){ 
    		 alert("请选择图片！");return false;
    	 }else{
    		 if(!/\.(gif|jpg|jpeg|png|bmp|tiff)$/.test(objtype)){
    			 alert("图片类型必须是.gif,.jpeg,.jpg,.png,.bmp,.tiff中的一种!")
    			 return false;
    		 }
    	 }
    	 var file = document.getElementById("upload");
    	 var jsid = Ext.getCmp("jsid").getValue();
    	 this.submitForm.$submit({
    		 action:"upfile_saveUpFileInfo.do",
    		 params:{
    			 form:this.submitForm.getForm().findField("upload")
    		 },
    		 scope:this,
    		 handler:function(form,result,scope){
    			 scope.fileUpWin.hide();
    			 document.getElementById("imgPath").src = Ext.get("ServerPath").dom.value+result.data;
    			 Ext.getCmp("photopath").setValue(result.data);				
    		 }
    	 });
     },
     selectTeacherInfo:function(){
    	 var xm=Ext.getCmp('teacher_xm_find').getValue();
    	 this.grid.$load({"teacher.xm":xm});    	
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
function checkDate(sdate,edate){
	var strs= new Array(); //定义一数组 
	if(sdate.getTime() > edate.getTime()){
    	return false;
	}else{
    	return true;
	}	
}