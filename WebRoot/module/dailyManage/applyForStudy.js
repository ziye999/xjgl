var wjcllx = [['0', '请选择'],['3', '未申请'],['2', '未审核'],['1', '已审核']];
var wjcllxStore = new Ext.data.SimpleStore({  
	fields : ['value', 'text'],  
	data : wjcllx  
});
var filePath = "";
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
    	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true,
    		listeners:{
    			"selectionchange":function(sm){
    				var str = sm.getSelections();
    				if(str[0].get("FLAG")=="1"){
    					Ext.getCmp('sh').setDisabled(true);
    					Ext.getCmp('xg').setDisabled(true);
    					Ext.getCmp('dl').setDisabled(false);
    				}else if(str[0].get("FLAG")=="0"){
    					Ext.getCmp('sh').setDisabled(true);
    					Ext.getCmp('xg').setDisabled(false);
    					Ext.getCmp('dl').setDisabled(false)
    				}else{
    					Ext.getCmp('sh').setDisabled(false);
    					Ext.getCmp('xg').setDisabled(true);
    					Ext.getCmp('dl').setDisabled(true);
    				}
    			}
    		}
    	});
		var cm = [
		    sm,
			{header: "姓名",   align:"center", sortable:true, dataIndex:"XM"},
			{header: "身份证号",   align:"center", sortable:true, dataIndex:"SFZJH"},
			{header: "出生日期",   align:"center", sortable:true, dataIndex:"CSRQ"},
			//{header: "等级",   align:"center", sortable:true, dataIndex:"NJMC"},
			//{header: "科目",   align:"center", sortable:true, dataIndex:"BJMC"},
			{header: "申请日期",   align:"center", sortable:true, dataIndex:"CDATE"},
			{header: "不通过原因",   align:"center", sortable:true, dataIndex:"REASON"},
			{header: "状态",   align:"center", sortable:true, dataIndex:"ZT"}
		];		
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"日常管理-不通过申请",
			tbar:[ 
			      "->",{xtype:"button",text:"删除",id:"dl",iconCls:"p-icons-delete",handler:this.deleteApply,scope:this}
			      ,"->",{xtype:"button",text:"修改",id:"xg",iconCls:"p-icons-update",handler:this.addApplyForStudy,scope:this}
			      ,"->",{xtype:"button",text:"申请",id:"sh",iconCls:"p-icons-add",handler:this.addApplyForStudy,scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			action:"applyForStudy_getListPage.do",
			fields :["XM","PID","SFZJH","CSRQ","NJMC","BJMC","FLAG","ZT","XS_JBXX_ID","XXMC","XXBSM","XB","CDATE","REASON","YYID","MEMO","FILEPATH"],
			border:false
		});
	
		//搜索区域
		var sjjyj = new Ext.ux.Combox({width:190,id:"sjjyj_find",
			store:new Ext.data.JsonStore({
				autoLoad:false,
				url:'registration_sjjyje.do',
				fields:["CODEID","CODENAME"],
				listeners : { 
					"load":function(store){
						Ext.getCmp("sjjyj_find").setValue(store.getAt(0).get("CODEID"));
						Ext.getCmp("sjjyj_find").fireEvent('select',Ext.getCmp("sjjyj_find"));
						Ext.getCmp("sjjyj_find").disabled = "true"; 
					}
				}
			}),
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
									autoLoad:false,
									url:'registration_jyj.do?params='+id,
									fields:["CODEID","CODENAME"],
									listeners : { 
										"load":function(store){
												Ext.getCmp("xjjyj_find").setValue(store.getAt(0).get("CODEID"));
												Ext.getCmp("xjjyj_find").fireEvent('select',Ext.getCmp("xjjyj_find"));
												Ext.getCmp("xjjyj_find").disabled = "true";
											}
									}
								});
					Ext.getCmp("xjjyj_find").clearValue(); 
					Ext.getCmp("xjjyj_find").store=newStore;  
					newStore.reload();
					Ext.getCmp("xjjyj_find").bindStore(newStore);
				},
				scope:this
			}
		});
		var xjjyj	= new Ext.ux.Combox({ width:190,id:"xjjyj_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
									autoLoad:false,
									url:'registration_school.do?params='+id,
									fields:["CODEID","CODENAME"],
									listeners : { 
										"load":function(store){
												Ext.getCmp("sch_find").setValue(store.getAt(0).get("CODEID"));
												Ext.getCmp("sch_find").fireEvent('select',Ext.getCmp("sch_find"));
												Ext.getCmp("sch_find").disabled = "true";
							    		}
									}
								});
					Ext.getCmp("sch_find").clearValue(); 
                	Ext.getCmp("sch_find").store=newStore;  
                    newStore.reload();
                    Ext.getCmp("sch_find").bindStore(newStore);
                },
                scope:this
			}
		});
		var schoolname	= new Ext.ux.Combox({width:190,id:"sch_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
									autoLoad:false,
									url:'dropListAction_gradeBySchool.do?params='+id,
									fields:["CODEID","CODENAME"]
								});
					Ext.getCmp("nj_find").clearValue(); 
                	Ext.getCmp("nj_find").store=newStore;  
                    newStore.reload();
                    Ext.getCmp("nj_find").bindStore(newStore);
				},
                scope:this
			}
		});
		var nj_find	= new Ext.ux.Combox({dropAction:"grade",width:190,id:"nj_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
									autoLoad:false,
									url:'dropListAction_classByGrade.do?params='+id,
									fields:["CODEID","CODENAME"]
								});
					Ext.getCmp("bj_find").clearValue(); 
                 	Ext.getCmp("bj_find").store=newStore;  
                 	newStore.reload();
                 	Ext.getCmp("bj_find").bindStore(newStore);
				},
				scope:this
			}
		});
		var bj_find	= new Ext.ux.Combox({width:190,id:"bj_find"});
	
		var	njbj	= new Ext.ux.form.GradeClassField({name:"sup_organ_sel",type:"0",width:200,readOnly:true});
		var xjh_find = new Ext.form.TextField({fieldLabel:"姓名",id:"xjh_find",maxLength:200, width:190});
		var zt_find	= new Ext.form.ComboBox({id:"zt_find",store:wjcllxStore,value:'0',displayField:'text',valueField:'value',mode:'local',triggerAction:'all',width:190,editable:false});
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
						columns: 5
					}, 
					baseCls:"table",
					items:[
						{html:"姓名：",baseCls:"label_right",width:70},
						{items:[xjh_find],baseCls:"component",width:210},
						{html:"状态：",baseCls:"label_right",width:70},
						{items:[zt_find],baseCls:"component",width:210},
						//{html:"等级/科目：",baseCls:"label_right",width:120},
						//{items:[njbj],baseCls:"component",width:210},
						//{html:"",baseCls:"label_right",width:120},    
						{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:180}
					] 
				}]  
			}]  
    	})
		this.panel=new Ext.Panel({
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			items:[this.search,this.grid]
		});
	},
	/** 初始化界面 **/
    initFace:function(){
    	this.panel_top=new Ext.Panel({
			layout:"fit",
    		region:"north",
    		border:false,
    		items:[this.panel]
    	});
    	this.addPanel({layout:"fit",items:[this.panel_top]});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
	selectExamSubject:function(){
		//var bj = this.search.getForm().findField("sup_organ_sel").getClassCode();
		var xmxjh=Ext.getCmp('xjh_find').getValue();
		var flag=Ext.getCmp('zt_find').getValue();
		if(flag == "0"){
			flag= "";
		}
		if(flag == "2"){
			flag= "0";
		}
		this.grid.$load({"xmxjh":xmxjh,"flag":flag});//"bj":bj,
	},
	addApplyForStudy:function(){
		var sel  = this.grid.getSelectionModel().getSelections();
		if(sel.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
		var xxmc = sel[0].get("XXMC");
		var xxbsm = sel[0].get("XXBSM");
		var njmc = sel[0].get("NJMC");
		var bjmc = sel[0].get("BJMC");
		var xm = sel[0].get("XM");
		var xb = sel[0].get("XB");
		var csrq = sel[0].get("CSRQ");
		var sfzjh = sel[0].get("SFZJH");
		var yyid = sel[0].get("YYID");
		filePath =  sel[0].get("FILEPATH");
		
		var xxname = new Ext.form.Label({id:"xxname",text:xxmc});
		var xxbsm2 = new Ext.form.Label({id:"xxbsm2",text:xxbsm});
		var njmc2 = new Ext.form.Label({id:"njmc2",text:njmc});
		var bjmc2 = new Ext.form.Label({id:"bjmc2",text:bjmc});
		var xm2 = new Ext.form.Label({id:"xm2",text:xm});
		var xb2 = new Ext.form.Label({id:"xb2",text:xb});
		var csrq2 = new Ext.form.Label({id:"csrq2",text:csrq});
		var sfzjh2 = new Ext.form.Label({id:"sfzjh2",text:sfzjh});
		this.submitForm = new Ext.ux.FormPanel({
		       	fileUpload: true,
		       	frame:true,
		       	enctype: 'multipart/form-data',
		       	defaults:{xtype:"textfield",anchor:"95%"},
		       	items: [{fieldLabel:"附件",xtype:"textfield",name:"upload",inputType:'file'}]
		});
		this.search2 = new Ext.form.FormPanel({
				region: "north",
				height:200,
				items:[{  
					layout:'form',  
					xtype:'fieldset',  
					style:'margin:10 10',
					title:'考生个人基础信息',  
					items: [{
						xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 4
						}, 
						baseCls:"table",
						items:[
							{html:"参考单位名称：",baseCls:"label_right",width:120},
							{items:[xxname],baseCls:"component",width:210},
							{html:"参考单位标识码：",baseCls:"label_right",width:120},
							{items:[xxbsm2],baseCls:"component",width:210},
							//{html:"等级名称：",baseCls:"label_right",width:120}, 
							//{items:[njmc2],baseCls:"component",width:210},
							//{html:"科目名称：",baseCls:"label_right",width:120}, 
							//{items:[bjmc2],baseCls:"component",width:210},
							{html:"考生姓名：",baseCls:"label_right",width:120},
							{items:[xm2],baseCls:"component",width:210},
							{html:"性别：",baseCls:"label_right",width:120},
							{items:[xb2],baseCls:"component",width:210},
							{html:"出生日期：",baseCls:"label_right",width:120},
							{items:[csrq2],baseCls:"component",width:210},
							{html:"身份证号：",baseCls:"label_right",width:120},
							{items:[sfzjh2],baseCls:"component",width:210}
						] 
					}]  
				}]  
	    });
		var yy = new Ext.form.TextField({id:"yy",maxLength:200, width:190});
		var rq = new Ext.form.DateField({  
			width:190,
			name:"rq",
            editable:false, //不允许对日期进行编辑  
            format:"Y-m-d",  
            emptyText:"请选择日期..."  
        });
	    var shuom = new Ext.form.TextArea({  
	    	width:535,
	        xtype:'textarea',  
	        fieldLabel: 'empty',  
	        //allowBlank: false,  不能为空
	        id : 'shuom', 
	        grow: true,//表示可以根据内容自动延伸  
	        preventScrollbars: true//防止出现滚动条  
	        //emptyText: '空',  
	        //maxLength: 50,  
	        //minLength: 10,  
	        //value: '第一行\n第二行\n第三行\n第四行'  
	    });  
	    var downfile =  new Ext.Button({baseCls:"component",iconCls:"p-icons-download",text:"下载",handler:this.downloadFile,scope:this});
	    if(filePath==null || filePath==""){
	    	downfile=new Ext.Button({text:"上传",iconCls:"p-icons-upload",handler:this.uploadFile,scope:this});//{html:"未上传资料",baseCls:"component"}
	    }
	    if(yyid != ""){
	    	Ext.getCmp("yy").setValue(sel[0].get("REASON"));
	    	rq.setValue(sel[0].get("CDATE"));
	    	Ext.getCmp("shuom").setValue(sel[0].get("MEMO"));
	    }
	    var bs = new Ext.form.Label({id:"bs",text:"*",style:"color:red;"});
	    var bs2 = new Ext.form.Label({id:"bs2",text:"*",style:"color:red;"});
		this.search3 = new Ext.form.FormPanel({
				region: "north",
				height:200,
				items:[{  
					layout:'form',  
					xtype:'fieldset',  
					style:'margin:10 10',
					title:'考生不通过申请信息',  
					items: [{
						xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 4
						}, 
						baseCls:"table",
						items:[
							{html:"不通过原因：",baseCls:"label_right",width:120},
							{items:[yy,bs],baseCls:"component",width:210},
							{html:"申请日期：",baseCls:"label_right",width:120},
							{items:[rq,bs2],baseCls:"component",width:210},
							{html:"不通过说明：",baseCls:"label_right",width:120}, 
							{items:[shuom],baseCls:"component",width:210,colspan:3},
							{html:"资料：",baseCls:"label_right",width:120},
							{items:[downfile],baseCls:"component",colspan:3} 
						] 
	                }]  
		       }]  
	    	});
		var schan = new Ext.Button({text:"上传",iconCls:"p-icons-upload",handler:this.uploadFile,scope:this});
		var _b_save	= new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.saveApply,scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.win.hide()},scope:this});
		this.win = new Ext.Window({
    		closable:false,
    		layout:'form',//布局方式
    		title:'不通过申请',
    		tbar:{cls:"ext_tabr",items:["->",_b_cancel,"->",_b_save,"->",schan]},
    		maximized:true,//显示最大化按钮
    		//minimizable:true,//显示最小化按钮，未做处理
    		//closeAction:'hide',
    		constrainHeader:true,//设置窗口的顶部不会超出浏览器边界
    		defaultButton:0,//默认选中的按钮
    		resizable:true,//控制窗口是否可以通过拖拽改变大小
    		resizeHandles:'se',//控制拖拽方式，必须是在设置了resizable的情况下
    		modal:true,//弹出窗口后立刻屏蔽掉其他的组件，只有关闭窗口后才能操作其它组件
    		plain:true,//对窗口内部内容惊醒美化,可以看到整齐的边框
    		animateTarget:'target',//可以使窗口展示弹并缩回效果的动画
    		items:[this.search2,this.search3]
    	});
		if(yyid != ""){
			this.win.title= "不通过修改";
		}
    	this.win.show();
	},
	saveApply:function(){
		var sel2  = this.grid.getSelectionModel().getSelections();		
		var xjh = sel2[0].get("PID");
		var xm = sel2[0].get("XM");		
		var yid = sel2[0].get("YYID");					
		var yuany = Ext.getCmp("yy").getValue();
		var riq = this.search3.getForm().findField("rq").getValue();		
		if(yuany==""){
			Ext.MessageBox.alert("消息","不通过原因不能为空！");
			return;
		}
		if(riq==""){
			Ext.MessageBox.alert("消息","请选择申请日期！");
			return;
		}
		var yysm = Ext.getCmp("shuom").getValue();
		if(yid == ""){//保存
			var thiz=this;
	    	Ext.Ajax.request({   
	       		url:'applyForStudy_saveApply.do',
	       		params:{
	       			'xjh':xjh,'xm':xm,'yy':yuany,'sqrq':riq,'yysm':yysm,'filename':filePath
	        	},
	        	success: function(resp,opts) {
	        		var respText = Ext.util.JSON.decode(resp.responseText);
	        		Ext.MessageBox.alert("提示",respText.msg);
	        		thiz.selectExamSubject();thiz.selectExamSubject();
	        	},
	        	failure: function(resp,opts){
	        		Ext.Msg.alert('错误', "保存失败！");
	        	}  
	    	});
		}else{//修改
			var thiz=this;
			Ext.Ajax.request({   
	       		url:'applyForStudy_updateApply.do',
	       		params:{
	       			'yyid':yid,'yy':yuany,'sqrq':riq,'yysm':yysm,'filename':filePath
	        	},
	        	success: function(resp,opts) {
	        		var respText = Ext.util.JSON.decode(resp.responseText);
	        		Ext.MessageBox.alert("提示",respText.msg);
	        		thiz.selectExamSubject();thiz.selectExamSubject();
	        	},
	        	failure: function(resp,opts){
	        		Ext.Msg.alert('错误', "修改失败！");
	        	}  
			});
		}
		this.win.hide();
		filePath = "";
	},
	deleteApply:function(){
		var selectedBuildings  = this.grid.getSelectionModel().getSelections();
		if(selectedBuildings.length <= 0){
    		Ext.MessageBox.alert("消息","请选择需要删除的记录！");
    		return;
    	}
		var ids =new Array();
    	for (var i = 0; i < selectedBuildings.length; i++) {
    		ids[i]=selectedBuildings[i].get("YYID");
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
			       		url:'applyForStudy_delApply.do',
			       		params:{
			        		'yyid':ids
			        	},
			        	success: function(resp,opts) {
			        		var respText = Ext.util.JSON.decode(resp.responseText);
			        		Ext.MessageBox.alert("提示",respText.msg);
			        		thiz.selectExamSubject();thiz.selectExamSubject();
			        	},
			        	failure: function(resp,opts){
			        		Ext.Msg.alert('错误', "删除失败！");
			        	}  
			       });
				}
			}
		})    	
	},
	uploadFile:function(){
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
    	filePath  = this.submitForm.getForm().findField("upload").getRawValue();
    	if(filePath==""){ 
    		alert("请选择文件！");return false;
    	}else{
    		var objtype = filePath.substring(filePath.lastIndexOf('.'));
   		 	if(!/\.(doc|xls|txt|docs|gif|jpg|jpeg|png|bmp|tiff)$/.test(objtype)){
   		 		alert("文件类型必须是.doc,.xls,.txt,.docs,.gif,.jpeg,.jpg,.png,.bmp,.tiff中的一种!")
   		 		return false;
   		 	}	
    	}
    	this.submitForm.$submit({
    		action:"applyForStudy_exportExcelFile.do?filename="+filePath,
    		params:{
				form:this.submitForm.getForm().findField("upload")
			},
			scope:this,
			handler:function(form,result,scope){
				filePath=result.data;
				scope.fileUpWin.hide();
			}
		});	
	},
	downloadFile:function(){ 
    	var path = Ext.get("ServerPath").dom.value + "/uploadFile/"+filePath;
    	window.open(path);
    }
});
