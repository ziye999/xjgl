var datasourceWeek = new Ext.data.Store();
var days = [['0', '星期日'],['1', '星期一'],['2', '星期二'],['3', '星期三'],['4', '星期四'],['5', '星期五'],['6', '星期六']];        
var weeks = [['1', '第一周'], ['2', '第二周'], ['3', '第三周'], ['4', '第四周'], ['5', '第五周'], ['6', '第六周'],  
         	 ['7', '第七周'], ['8', '第八周'], ['9', '第九周'], ['10', '第一十零周'], ['11', '第一十一周'], ['12', '第一十二周'],
        	 ['13', '第一十三周'], ['14', '第一十四周'], ['15', '第一十五周'], ['16', '第一十六周'], ['17', '第一十七周'], ['18', '第一十八周'], 
        	 ['19', '第一十九周'], ['20', '第二十零周'], ['21', '第二十一周'], ['22', '第二十二周'], ['23', '第二十三周'], ['24', '第二十四周'], 
        	 ['25', '第二十五周'], ['26', '第二十六周'], ['27', '第二十七周'], ['28', '第二十八周'], ['29', '第二十九周'], ['30', '第三十零周']];
		
var storeday = new Ext.data.SimpleStore({  
	fields : ['value', 'text'],  
	data : days  
});
var xxkssj = "";
var xxjssj = "";
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
    	this.grid.on("rowdblclick",function(grid, rowIndex, e ){
    		var lcid = grid.store.getAt(rowIndex).get("LCID");
    		this.editWindow.show(null,function(){
    			Ext.getCmp("lcid").setValue(lcid);
    			this.editForm.getForm().findField("organ_sel").setValue(grid.store.getAt(rowIndex).get("ZKDW"));
    			Ext.getCmp("examtypem").setValue(grid.store.getAt(rowIndex).get("EXAMTYPEM"));
    			Ext.getCmp("dwid").setValue(grid.store.getAt(rowIndex).get("DWID"));
    			Ext.getCmp("dwtype").setValue(grid.store.getAt(rowIndex).get("DWTYPE"));
    			this.editForm.$load({
    				params:{'examround.lcid':lcid},
    				action:"examround_loadBuilding.do",
    				handler:function(form,result,scope){
    					Ext.getCmp("xnxq").setCode(result.data.xn+","+result.data.xqm);
    					form.findField("examtypemc").setValue(grid.store.getAt(rowIndex).get("EXAMTYPEM"));
    					form.findField("startdate").format = 'Y-m-d';
    					form.findField("startdate").setValue(grid.store.getAt(rowIndex).get("STARTDATE").replace("0:00:00",""));
    					form.findField("enddate").setValue(grid.store.getAt(rowIndex).get("ENDDATE").replace("0:00:00",""));
    				}
    			});
    		},this)
    	},this)
    },   
   	initComponent :function(){   		
   		this.editForm   = this.createExamroundForm();
   		this.editWindow = this.createWindow();
   		this.editWindow.add({items:[this.editForm]});
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "组考单位",  align:"center", sortable:true, dataIndex:"ZKDW"},
			{header: "年度",   	align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   	align:"center", sortable:true, dataIndex:"XQM"},
			{header: "考试名称",  align:"center", sortable:true, dataIndex:"EXAMNAME"},
			{header: "考试类型",  align:"center", sortable:true, dataIndex:"EXAMTYPEMC"},
			{header: "起始日期",  align:"center", sortable:true, dataIndex:"STARTDATE"},
			{header: "结束日期",  align:"center", sortable:true, dataIndex:"ENDDATE"}			
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			tbar:{
				cls:"ext_tabr",
				items:[ 
				  "->",{xtype:"button",text:"生成成绩查询",id:"sore",iconCls:"p-icons-update",handler:this.createScore,scope:this},
				  "->",{xtype:"button",text:"导入照片信息",id:"dr",iconCls:"p-icons-update",handler:this.exportKs,scope:this},
				
				 "->",{xtype:"button",text:"关联照片",iconCls:"p-icons-add",handler:this.glzp,scope:this},
				       "->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteRound,scope:this}
				       ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateRound,scope:this}
				       ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){Ext.getCmp("lcid").setValue("");this.editWindow.show()},scope:this}
				      ]
			},
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			excelTitle:"考试轮次列表",
			action:"examround_getListPage.do",
			fields :["LCID","XN","XQM","EXAMNAME","EXAMTYPEMC","STARTDATE","ENDDATE","EXAMTYPEM","ZKDW","DWID","DWTYPE"],
			border:false
		});
		
		var xn_find=new Ext.ux.form.XnxqField({name:"roundxn_find",id:"roundxn_find",width:180,readOnly:true});		
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
							columns: 7
						}, 
						baseCls:"table",
						items:[
							{html:"年度：",baseCls:"label_right",width:120},
							{items:[xn_find],baseCls:"component",width:180},
							{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:160}
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
    createExamroundForm:function(){	
    	var lcid = new Ext.form.TextField({fieldLabel:"",hidden:true,name:"examround.lcid",id:"lcid"});
    	var organ_sel = new Ext.ux.form.OrganField({name:"organ_sel",width:200,singleSelection:true,allowBlank:false,readOnly:true});
    	var xnxq = new Ext.ux.form.XnxqField({name:"xnxq",id:"xnxq",readOnly:true});    	
        //考试轮次名称
		var examname = new Ext.ux.form.TextField({name:"examround.examname",id:"examname",maxLength:50,allowBlank:false,blankText:"考试轮次不能为空！",width:170});
		//考试类型
		var dwid  	= new Ext.form.TextField({hidden:true,name:"examround.dwid",id:"dwid"});
		var dwtype  = new Ext.form.TextField({hidden:true,name:"examround.dwtype",id:"dwtype"});
		var examtypem  = new Ext.form.TextField({hidden:true,name:"examround.examtypem",id:"examtypem"});
		var examtypemc = new Ext.ux.Combox({
			dropAction:"examtype",
			name:"examtypemc",
			params:"",
			width:150,
			allowBlank:false,
			blankText:"考试类型为必选项！",
			listeners:{ 
				select:function() {
					Ext.getCmp("examtypem").setValue(this.getValue());
			}  
		}});
		//开始日期
		var startdate = new Ext.form.DateField({name:"startdate",editable:false,width:140,format:"Y-m-d", emptyText:"请选择日期...",blankText:"开始日期不能为空！"}); 
		//结束日期
		var enddate = new Ext.form.DateField({name:"enddate",editable:false,width:140,format:"Y-m-d", emptyText:"请选择日期...",blankText:"结束日期不能为空！"}); 
		
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4
			},
			items:[
			       {html:"组考单位：",baseCls:"label_right",width:100},
			       {html:"<font class='required'>*</font>",items:[organ_sel],baseCls:"component",width:210},
			       {html:"考试名称：",baseCls:"label_right",width:100},
			       {html:"<font class=required color=red>*</font>",items:[examname],baseCls:"component",width:200},			       
			       {html:"年度：",baseCls:"label_right",width:100},
			       {html:"<font class=required color=red>*</font>", items:[xnxq],baseCls:"component",width:200},
			       {html:"考试类型：",baseCls:"label_right",width:100},
			       {html:"<font class=required color=red>*</font>",items:[examtypemc,examtypem],baseCls:"component",width:200},																       				
			       {html:"起始日期：",baseCls:"label_right"},
			       {html:"<font class=required color=red>*</font>",items:[startdate],baseCls:"component",width:200},
			       {html:"结束日期：",baseCls:"label_right"},
			       {html:"<font class=required color=red>*</font>",items:[enddate],baseCls:"component",width:200}
				]		
		});
		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"95%"},
			items:[
					{layout:'form',xtype:"fieldset",title:"考试轮次",items:[panel]},
					{items:[lcid,dwid,dwtype]}
			]});					
    },    
    createWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addRound,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.editWindow.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:750,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
			 	title:"考试轮次维护"});		
    },
    addRound:function(){
    	if(Ext.getCmp("xnxq").getCode() == ""){
    		Ext.MessageBox.alert("错误","请选择年度！");
    		return;
    	}
    	if(this.editForm.getForm().findField("startdate").getValue()==""||this.editForm.getForm().findField("enddate").getValue()==""){
    		Ext.MessageBox.alert("错误","起始日期和结束日期不能为空！");
    		return;
    	}
    	if(!checkDate(this.editForm.getForm().findField("startdate").getValue(),this.editForm.getForm().findField("enddate").getValue())){
			Ext.MessageBox.alert("错误","起始日期大于结束日期！");
			return;
    	}    	   	
    	this.editForm.$submit({
    		action:"examround_addBuilding.do",
    		params: {
    			'lcid':Ext.getCmp("lcid").getValue(),
    			'examround.xnxqId':Ext.getCmp("xnxq").getCode(),
    			'examround.dwid':this.editForm.getForm().findField("organ_sel").getCodes()
    		},
    		handler:function(form,result,thiz){
    			thiz.editWindow.hide();
    			thiz.grid.$load();
    		},
    		scope:this
    	});
    	Ext.getCmp("lcid").setValue("");
    },    
    deleteRound:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedRounds = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var lcids = "'";
    	for(var i = 0; i < selectedRounds.length; i++) {
    		if(i != selectedRounds.length - 1) {
	    		lcids += selectedRounds[i].get("LCID") + "','";
    		}else {
	    		lcids += selectedRounds[i].get("LCID") + "'";
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
    					params:{'lcids':lcids},
    					action:"examround_delBuilding.do",
    					handler:function(){
    						thiz.grid.$load();
    					}
    				})    				
    			}
    		}
    	})
    },
    glzp:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedRounds = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var lcids = "'";
    	for(var i = 0; i < selectedRounds.length; i++) {
    		if(i != selectedRounds.length - 1) {
	    		lcids += selectedRounds[i].get("LCID") + "','";
    		}else {
	    		lcids += selectedRounds[i].get("LCID") + "'";
    		}
    	}
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"您确定要关联吗?",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,    			
    		fn:function(b){
    			if(b=='ok'){
    				JH.$request({
    					params:{'lcid':lcids},
    					action:"exam_glzp.do",
    					handler:function(result){
    					if(result.data!=null && result.data!=''){
						   window.open(Ext.get("ServerPath").dom.value+"/"+result.data.replace(/\\/g, "\/"));
						}
    					
							
    					}
    				})    				
    			}
    		}
    	})
    },
    
     createScore:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedRounds = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var lcids = "'";
    	for(var i = 0; i < selectedRounds.length; i++) {
    		if(i != selectedRounds.length - 1) {
	    		lcids += selectedRounds[i].get("LCID") + "','";
    		}else {
	    		lcids += selectedRounds[i].get("LCID") + "'";
    		}
    	}
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"您确定要生成成绩查询吗?",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,    			
    		fn:function(b){
    			if(b=='ok'){
    				JH.$request({
    					params:{'lcid':lcids},
    					action:"exam_createScore.do",
    					handler:function(){
    						thiz.grid.$load();
    					}
    				})    				
    			}
    		}
    	})
    },
    
     exportKs:function(){
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}    	
    	//导入照片信息
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
exportBkFilesInfo:function(){
   var filePath = this.submitForm.getForm().findField("upload").getRawValue();
    	//判断文件类型
    	var objtype=filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
    	var fileType=new Array(".zip",".ZIP");
    	if(filePath==""){ 
    		alert("请选择文件！");return false;
    	}else{
    		if(!/\.(zip|ZIP)$/.test(objtype)){
    			alert("文件类型必须是.zip,.ZIP中的一种!")
    			return false;
	        }
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var lcid = selectedBuildings[0].get("LCID"); 
    	Ext.getCmp('uploadButton').setDisabled(true);
    	this.submitForm.$submit({
    		timeout:3600000,
    		async:false,
    		action:"exam_exportPhontoInfo.do",
    		params:{
				form:this.submitForm.getForm().findField("upload"),
				'lcid':lcid
			},			
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
    
    updateRound:function(){    	
    	var selectedRounds = this.grid.getSelectionModel().getSelections();
    	if(selectedRounds.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
	    }
    	var lcid = selectedRounds[0].get("LCID");
    	Ext.getCmp("lcid").setValue(lcid);
    	Ext.getCmp("examtypem").setValue(selectedRounds[0].get("EXAMTYPEM"));
    	Ext.getCmp("dwid").setValue(selectedRounds[0].get("DWID"));
    	Ext.getCmp("dwtype").setValue(selectedRounds[0].get("DWTYPE"));
    	this.editWindow.show(null,function(){
    		this.editForm.$load({
    			params:{'examround.lcid':lcid},
    			action:"examround_loadBuilding.do",
    			handler:function(form,result,scope){      				
    				Ext.getCmp("xnxq").setCode(result.data.xn+","+result.data.xqm);
    				form.findField("organ_sel").setValue(selectedRounds[0].get("ZKDW"));
    				form.findField("examtypemc").setValue(selectedRounds[0].get("EXAMTYPEM"));
    				form.findField("startdate").format = 'Y-m-d';
    				form.findField("startdate").setValue(selectedRounds[0].get("STARTDATE").replace("0:00:00",""));
    				form.findField("enddate").setValue(selectedRounds[0].get("ENDDATE").replace("0:00:00",""));
    				if(Ext.getCmp("xnxq_id").getValue() != ""){
    					datasourceWeek.proxy = new Ext.data.HttpProxy({
    						url : 'dropListAction_weeks.do?params=xnxq:'+Ext.getCmp("xnxq_id").getValue(),
    						reader : new Ext.data.JsonReader({}, ['XXKSSJ','XXJSSJ','CODEID', 'CODENAME'])  
    					});  
		                datasourceWeek.load({
		                	callback : function(r, options, success) { 
		                		xxkssj = datasourceWeek.getAt(0).get('XXKSSJ');
		                		xxjssj = datasourceWeek.getAt(0).get('XXJSSJ');
		                	}
		                });
					}
    			}
    		});
    	},this)
    },
    selectExamSubject:function(){
    	var xn=Ext.getCmp('roundxn_find').getCode();
    	this.grid.$load({"xn":xn});
    }
});

function checkDate(sdate,edate){
	var strs= new Array(); //定义一数组 
	if(sdate.getTime() > edate.getTime()){
    	return false;
	}else{
    	return true;
	}	
}