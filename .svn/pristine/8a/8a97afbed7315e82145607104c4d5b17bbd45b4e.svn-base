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
    	this.editForm   = this.createSubjectEditForm();
   		this.eidtWindow = this.createSubjectWindow();
   		this.eidtWindow.add(this.editForm);
   		
   		this.editFormCopy   = this.createSubjectEditFormCopy();
   		this.eidtWindowCopy = this.createSubjectWindowCopy();
   		this.eidtWindowCopy.add(this.editFormCopy);   
   		
   		var thiz=this;
   		this.eidtWindow.on({"show":function(){
   			var newStore = new Ext.data.JsonStore({
				autoLoad:false,
				url:'dropListAction_examround.do?params='+thiz.XNXQ_ID,
				fields:["CODEID","CODENAME"]
			});
			
            setTimeout(function(){
            	if(thiz.kmid!=null&&thiz.kmid!=undefined){
            		thiz.editForm.$load({
	    				params:{'kmid':thiz.kmid},
	    				action:"examsubject_getExamSubject.do",
	    				handler:function(form,result,scope){
	    					form.findField("lcid").clearValue();  
							form.findField("lcid").store=newStore;  
							newStore.reload();
							form.findField("lcid").bindStore(newStore);
							setTimeout(function(){
								form.findField("lcid").setValue(result.data.lcid);
							},200);
	    				}
	    			});
            	}
            },200);            
   		}});
    },    
	/** 对组件设置监听 **/
    initListener:function(){
    	this.eidtWindow.on("hide",function(){
    		this.kmid=undefined;
    		this.editForm.$reset();
    	},this);
    	this.grid.on("rowdblclick",function(grid, rowIndex, e ){
    		this.kmid = grid.store.getAt(rowIndex).get("KMID");
    		this.XNXQ_ID = grid.store.getAt(rowIndex).get("XN")+","+grid.store.getAt(rowIndex).get("XQ");
    		this.LCID = grid.store.getAt(rowIndex).get("LCID");
    		this.eidtWindow.show();    		
    	},this);
    },   
   	initComponent :function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "年度",   	align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   	align:"center", sortable:true, dataIndex:"XQM"},
			{header: "考试批次",  align:"center", sortable:true, dataIndex:"SUBJECTNAME"},
			{header: "成绩形式",  align:"center", sortable:true, dataIndex:"RESULTSTYLE"},
			{header: "考试名称",  align:"center", sortable:true, dataIndex:"EXAMNAME"},
			{header: "满分值",   	align:"center", sortable:true, dataIndex:"SCORE"},
			{header: "考试时长",  align:"center", sortable:true, dataIndex:"TIMELENGTH"},
			{header: "考试日期",  align:"center", sortable:true, dataIndex:"KSRQ"},
			{header: "考试开始时间",  align:"center", sortable:true, dataIndex:"KSSJ"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"考试批次",
			tbar:[ 
			      "->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteSubject,scope:this}
			      ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateSubject,scope:this}
			      ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.eidtWindow.show();},scope:this}
			      //,"->",{xtype:"button",text:"复制科目",iconCls:"p-icons-add",handler:function(){this.eidtWindowCopy.show();},scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			action:"examsubject_getListPage.do",
			fields :["KMID","XNXQ_ID","XN","XQM","XQ","SUBJECTNAME","RESULTSTYLE","EXAMNAME","LCID","SCORE","TIMELENGTH","KSRQ","KSSJ","JSSJ"],
			border:false
		});
		var xn_find=new Ext.ux.form.XnxqField({name:"subxn_find",width:180,readOnly:true,
			callback:function(){					
				var id=xn_find.getCode();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
									autoLoad:false,
									url:'dropListAction_examround.do?params='+id,
									fields:["CODEID","CODENAME"]
								});
				Ext.getCmp("examround_find").clearValue();  
                Ext.getCmp("examround_find").store=newStore;  
                newStore.reload();
                Ext.getCmp("examround_find").bindStore(newStore);
			}
		});		
		var examround_find = new Ext.ux.Combox({width:170,id:"examround_find"});
		var course_find = new Ext.ux.Combox({width:170,id:"course_find",dropAction:"course"});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamSubject,scope:this});
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
							columns: 7
						}, 
						baseCls:"table",
						items:[
							{html:"年度：",baseCls:"label_right",width:50},
							{items:[xn_find],baseCls:"component",width:180},
							{html:"考试名称：",baseCls:"label_right" ,width:70},
							{items:[examround_find],baseCls:"component",width:180},
							{html:"科目：",baseCls:"label_right",width:50},
							{items:[course_find],baseCls:"component",width:180},
							{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:150}
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
    createSubjectEditForm:function(){
    	var kmid = new Ext.form.TextField({fieldLabel:"",id:"kmid",hidden:true,name:"kmid"});
    	var xnxqVal = new Ext.form.TextField({fieldLabel:"",id:"xnxqValue",hidden:true,name:"xnxqValue"});
    	var lcid = new Ext.ux.Combox({hiddenName:"lcid",name:"lcid",width:170,allowBlank:false,blankText:"考试名称不能为空！"});
		var xn=new Ext.ux.form.XnxqField({name:"xnxqId",id:"xnxqId",width:180,readOnly:true,
			callback:function(){
    			var id=Ext.getCmp("xnxqId").getCode();//取得ComboBox0的选择值
				Ext.getCmp("xnxqValue").setValue(id);
				var newStore = new Ext.data.JsonStore({
									autoLoad:false,
									url:'dropListAction_examround.do?params='+id,
									fields:["CODEID","CODENAME"]
				});
				lcid.clearValue();  
				lcid.store=newStore;  
                newStore.reload();
                lcid.bindStore(newStore);
    	}});		
		var kch = new Ext.ux.Combox({dropAction:"course",hiddenName:"kch",name:"kch",width:170,allowBlank:false,blankText:"科目不能为空！",
			listeners:{ 
				select:function(combo,record,opts) {
					var id=this.getRawValue();
		            Ext.getCmp("subjectname").setValue(id);
				}  
			}   
		});
		var subjectname = new Ext.form.TextField({hiddenName:"subjectname",id:"subjectname",width:170,maxLength:100,allowBlank:false,blankText:"考试批次不能为空！"});
		var resultstyle = new Ext.ux.DictCombox({dictCode:"ZD_CJXS",hiddenName:"resultstyle",name:"resultstyle",width:170,allowBlank:false,blankText:"成绩形式不能为空！"});
		var score = new Ext.form.TextField({name:"score",id:"score",width:170,maxLength:3,allowBlank:false,blankText:"满分值不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText:"满分值只能是正整数！"});
		var timelength = new Ext.form.TextField({name:"timelength",id:"timelength",width:170,maxLength:3,allowBlank:false,blankText:"考试时长不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText:"考试时长只能是正整数！"});

		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[
				{html:"年度：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[xn],baseCls:"component",width:190},
				{html:"考试名称：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[lcid],baseCls:"component",width:190},
				{html:"科目：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[kch],baseCls:"component",width:190},
				{html:"考试批次：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[subjectname],baseCls:"component",width:190},
				{html:"成绩形式：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[resultstyle],baseCls:"component",width:190},
				{html:"满分值：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[score],baseCls:"component",width:190},
				{html:"考试时长：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[timelength],baseCls:"component",width:190}
			]		
		});
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
				{layout:'form', xtype:"fieldset",title:"考试批次",items:[panel]},
				{items:[kmid,xnxqVal]}
			]});				
    },
    createSubjectWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.saveOrUpdateExamSubject,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide();},scope:this});
		return new Ext.ux.Window({
			 	width:660,
			 	title:"考试批次信息维护",
			 	tbar:{
						cls:"ext_tabr",
						items:[ 
						 	 "->",cancel
				 			 ,"->",save
						  ]
					},
			 	buttonAlign:"center"});		
    },
    saveOrUpdateExamSubject:function(){
    	var xnxq = Ext.getCmp("xnxqValue").value;
    	if (xnxq=="") {
    		Ext.MessageBox.alert("消息","请选择年度季度！");
    		return;
    	}    	
    	var thiz = this;
    	this.editForm.$submit({
    		action:"examsubject_saveOrUpdateExamSubject.do",
    		handler:function(form,result,thiz){
    			thiz.eidtWindow.hide();
    			thiz.grid.$load();
    		},
    		scope:this
    	});
    },
    createSubjectEditFormCopy:function(){
    	var xnxqVal = new Ext.form.TextField({fieldLabel:"",id:"xnxqValue_copy",hidden:true,name:"xnxqValue"});
    	var lcid = new Ext.ux.Combox({hiddenName:"lcid",name:"lcid_copy",width:170,allowBlank:false,blankText:"考试名称不能为空！"});
		var xn=new Ext.ux.form.XnxqField({name:"xnxqId",id:"xnxqId_copy",width:180,readOnly:true,
			callback:function(){
    			var id=Ext.getCmp("xnxqId_copy").getCode();//取得ComboBox0的选择值
				Ext.getCmp("xnxqValue_copy").setValue(id);
				var newStore = new Ext.data.JsonStore({
									autoLoad:false,
									url:'dropListAction_examround.do?params='+id,
									fields:["CODEID","CODENAME"]
								});
				lcid.clearValue();  
				lcid.store=newStore;  
                newStore.reload();
                lcid.bindStore(newStore);
    	}});		
		var resultstyle = new Ext.ux.DictCombox({dictCode:"ZD_CJXS",hiddenName:"resultstyle",name:"resultstyle_copy",width:170,allowBlank:false,blankText:"成绩形式不能为空！"});
		var score = new Ext.form.TextField({name:"score",id:"score_copy",width:170,maxLength:3,allowBlank:false,blankText:"满分值不能为空！",regex:/^\d+$/,regexText:"满分值只能是数字！"});
		var timelength = new Ext.form.TextField({name:"timelength",id:"timelength_copy",width:170,maxLength:3,allowBlank:false,blankText:"考试时长不能为空！",regex:/^\d+$/,regexText:"考试时长只能是数字！"});

		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[
				{html:"年度：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[xn],baseCls:"component",width:190},
				{html:"考试名称：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[lcid],baseCls:"component",width:190},
				{html:"成绩形式：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[resultstyle],baseCls:"component",width:190},
				{html:"满分值：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[score],baseCls:"component",width:190},
				{html:"考试时长：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[timelength],baseCls:"component",width:190}
			]		
		});
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
				{layout:'form', xtype:"fieldset",title:"考试批次",items:[panel]},
				{items:[xnxqVal]}
			]});				
    },
    createSubjectWindowCopy:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.saveOrUpdateExamSubjectCopy,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindowCopy.hide();},scope:this});
		return new Ext.ux.Window({
			 	width:660,
			 	title:"考试批次信息维护",
			 	tbar:{
						cls:"ext_tabr",
						items:[ 
						 	 "->",cancel
				 			 ,"->",save
						  ]
					},
			 	buttonAlign:"center"});			
    },
    saveOrUpdateExamSubjectCopy:function(){
    	this.editFormCopy.$submit({
    		action:"examsubject_copyExamSubject.do",
    		handler:function(form,result,thiz){
    			thiz.eidtWindowCopy.hide();
    			thiz.grid.$load();
    		},
    		scope:this
    	});
    },    
    deleteSubject:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedSubjects = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var ids = "'";
    	for(var i = 0; i < selectedSubjects.length; i++) {
    		if(i != selectedSubjects.length - 1) {
	    		ids += selectedSubjects[i].get("KMID") + "','";
    		}else {
	    		ids += selectedSubjects[i].get("KMID") + "'";
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
    					params:{"ids":ids},
    					action:"examsubject_deleteExamSubject.do",
    					handler:function(){
    						thiz.grid.$load();
    					}
    				})    				
    			}
    		}
    	})
    },
    updateSubject:function(){
    	var selectedSubjects = this.grid.getSelectionModel().getSelections();
	    if(selectedSubjects.length != 1){
	    	Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
	    	return;
	    }
	    this.kmid = selectedSubjects[0].get("KMID");
	    this.XNXQ_ID = selectedSubjects[0].get("XN")+","+selectedSubjects[0].get("XQ");
	    this.LCID = selectedSubjects[0].get("LCID");
	    this.eidtWindow.show();    	
    },
    selectExamSubject:function(){
    	var xn=this.search.getForm().findField("subxn_find").getCode();
    	var course=Ext.getCmp('course_find').getValue();
    	var examround=Ext.getCmp('examround_find').getValue();
    	/*if(xn==""||xn==undefined){
    		Ext.MessageBox.alert("消息","请选择年度！");
	    	return;
    	}*/
    	this.grid.$load({"xnxqId":xn,"kch":course,"lcid":examround});
    },
    copyCourse:function(){
    	var thiz=this;
    	Ext.Ajax.request({
    		url : 'user_save.action',
			method : 'post',
			params : {
				'userName' : document.getElementById('userName').value,
				'password' : document.getElementById('password').value
			},
			success : function(response, options) {
				Ext.Msg.alert("复制成功！");
				thiz.selectExamSubject();
			},
			failure : function() {
				Ext.Msg.alert("出错了！");
			}
		});    		
    }
});