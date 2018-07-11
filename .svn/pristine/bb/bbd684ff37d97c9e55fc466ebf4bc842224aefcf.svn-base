var kdid = "";
var pjyj = "";
var plcid = "";
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
   		this.eidtWindow = this.createExamSchoolEditForm();
   		this.arrangeWindow = this.createArrangeWindow();
   		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [sm,
			{header: "上级单位",   align:"center", sortable:true, dataIndex:"SHI"},
			{header: "组织单位",   align:"center", sortable:true, dataIndex:"XIAN"},
			{header: "参考单位代码",   align:"center", sortable:true, dataIndex:"XXBSM"},
			{header: "参考单位名称",   align:"center", sortable:true, dataIndex:"XXMC"},
			{header: "参考单位地址",   align:"center", sortable:true, dataIndex:"XXDZ"},
			{header: "是否参加本次考试",   align:"center", sortable:true, dataIndex:"SFCK"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"参考单位安排",
			tbar:["->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.returnArrange,scope:this},
			      "->",{xtype:"button",text:"取消参考",iconCls:"p-icons-delete",handler:this.deleteArrange,scope:this},
			      //"->",{xtype:"button",text:"手动安排",iconCls:"p-icons-update",handler:this.arrangeStudent,scope:this},
			      "->",{xtype:"button",text:"参考设置",iconCls:"p-icons-update",handler:this.seatingArrangements,scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			excelTitle:"考生信息表",
			action:"examSchoolArrange_getListPage.do",
			fields :["XXBSM","XX_JBXX_ID","XXMC","XXDZ","XIAN","SHI","SFCK"],
			border:false
		});
		//搜索区域
		var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel",width:210,readOnly:true,type:"0"});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamSchool,scope:this});
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
							{html:"参考单位：",baseCls:"label_right",width:170},
							{items:[organ_sel],baseCls:"component",width:210},
							{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:170}
						] 
                 	}]  
		     	}]  
	    	})
	},
	createExamSchoolEditForm:function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "参考单位代码",   align:"center", sortable:true, dataIndex:"XXBSM"},
			{header: "参考单位名称",   align:"center", sortable:true, dataIndex:"XXMC"},
			{header: "参考单位剩余人数",   align:"center", sortable:true, dataIndex:"SYRS"},
			{header: "安排人数",   align:"center", sortable:true,
				renderer: function(value, cellmeta, record, rowIndex) {  
					return "<input type='text' id='aprs"+rowIndex+"'></input>";  
				} 
			}
		];
		this.kdsyzws = new Ext.form.Label({style:"padding-left:850px;",text:"考点剩余座位数：",id:"kdsyzws"});
		this.examSchoolGrid = new Ext.ux.GridPanel({
			id:"examSchoolGrid",
			cm:cm,
			sm:sm,
			height:410,
			page:true,
			rowNumber:true,
			excel:true,
			excelTitle:"考场剩余座位安排",
			region:"center",
			action:"examSchoolArrangeSt_getListPage.do",
			fields :["XXDM","LCID","XXMC","XXBSM","ZRS","YAP","SYRS"],
			border:false
		});
		this.kdrsPanel = new Ext.form.FormPanel({
		       	region: "north",
		       	height:20,
		       	items:[this.kdsyzws]  
	    })
	    this.editPanel = new Ext.Panel({
	    		height:500,
	    		layout:"border",
	    		border:false,
	    		items:[this.kdrsPanel,this.examSchoolGrid]
	    });
	    var save = new Ext.Button({text:"确定",iconCls:"p-icons-save",handler:this.autoStudentArrange,scope:this});
		var cancel = new Ext.Button({text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:1000,
			 	height:500,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
				items: [this.editPanel],
	           title:"自动安排考生"});
	},
	createExamSchoolWindow:function(){
		var save = new Ext.Button({text:"确定",iconCls:"p-icons-save",handler:this.autoStudentArrange,scope:this});
		var cancel = new Ext.Button({text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide()},scope:this});
		var thiz = this;
		return new Ext.ux.Window({
			 	width:800,
			 	height:500,
			 	tbar:{
			 		cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
				title:"考场剩余座位安排"});
	},
	createArrangeWindow:function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "参考单位",   align:"center", sortable:true, dataIndex:"XXMC"},
			{header: "等级",   align:"center", sortable:true, dataIndex:"NJMC"},
			{header: "科目",   align:"center", sortable:true, dataIndex:"BJMC"},
			{header: "姓名",   align:"center", sortable:true, dataIndex:"XM"},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
			{header: "考号",   align:"center", sortable:true, dataIndex:"KSCODE"}
		];
		this.arrangeStuGrid = new Ext.ux.GridPanel({
			id:"arrangeStuGrid",
			cm:cm,
			sm:sm,
			height:426,
			page:true,
			rowNumber:true,
			excel:true,
			excelTitle:"考生信息列表",
			region:"center",
			action:"examSchoolArrangeXs_getListPage.do",
			fields :["NJMC","BJMC","XM","XB","KSCODE","XJH","KSID","XXMC","XXDM"],
			border:false
		});
		var save = new Ext.Button({text:"确定",iconCls:"p-icons-save",handler:this.addStudentArrange,scope:this});
		var cancel = new Ext.Button({text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.arrangeWindow.hide()},scope:this});
		//查询区
		var bj	= new Ext.ux.Combox({width:190,id:"bj_find"});
		var nj	= new Ext.ux.Combox({ width:190,id:"nj_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_classByGrade.do?params='+id+"&schoolId="+Ext.getCmp("sch_find").getValue(),
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
		var xx	= new Ext.ux.Combox({width:190,id:"sch_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_gradeBySchool.do?params='+combo.getValue(),
										fields:["CODEID","CODENAME"]
					});
					Ext.getCmp("nj_find").clearValue(); 
					Ext.getCmp("bj_find").clearValue();
					Ext.getCmp("bj_find").store = {"CODEID":"","CODENAME":""}; 
					Ext.getCmp("nj_find").store=newStore;  
					newStore.reload();
					Ext.getCmp("nj_find").bindStore(newStore);
				},
				scope:this
			}
		});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamStudent,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.arrangeSear.getForm().reset()},scope:this});
		var zws = new Ext.form.Label({style:"padding-left:850px;",text:"考点剩余座位数：",id:"kdsyzws1"});
		this.arrangeSear = new Ext.form.FormPanel({
			region: "north",
			height:140,
			id:"search1_form",
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
							{items:[xx],baseCls:"component",width:210},
							{html:"等级：",baseCls:"label_right",width:120},
							{items:[nj],baseCls:"component",width:210},
							{html:"科目：",baseCls:"label_right",width:120}, 
							{items:[bj],baseCls:"component",width:210},
							{html:"",baseCls:"label_right",width:120},
							{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:210}
						] 
                    }]  
		       },zws]  
	    	})
	    	
	    	this.arrangePanel=new Ext.Panel({
	    		id:"arrangePanel",
	    		height:500,
	    		layout:"border",
	    		border:false,
	    		items:[this.arrangeSear,this.arrangeStuGrid]
	    	});
		var thiz = this;
		return new Ext.ux.Window({
			 	width:1000,
			 	height:500,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
				items: [this.arrangePanel],
				title:"手动安排考生"});
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"border",items:[this.search,this.grid]});
    },
    initQueryDate:function(){
    	this.grid.$load({"lcid":getLocationPram("lcid")});
    },
    selectExamSchool:function(){
    	//查询考点人数安排情况
    	var organ_sel=this.search.getForm().findField('organ_sel').getCodes();
    	this.grid.$load({"organ_sel":organ_sel,"lcid":getLocationPram("lcid")});
	},
	seatingArrangements:function(){
		//显示自动安排弹出框
		var selectedTeacher = this.grid.getSelectionModel().getSelections();
		if(selectedTeacher.length < 1){
			Ext.MessageBox.alert("消息","请选择一条记录进行设置！");
			return;
	    }
		var xxcode="";
		for (var i = 0; i < selectedTeacher.length; i++) {
			if(selectedTeacher[i].get("SFCK")=="否")
				xxcode += selectedTeacher[i].get("XX_JBXX_ID")+",";
	    }
		if(xxcode==""){
			Ext.MessageBox.alert("消息","你选择的参考单位已参考，无需再次设置！");
			return;
	    }else{
	    	xxcode=xxcode.substring(0,xxcode.length-1);
	    }
		var lcid=getLocationPram("lcid");
		var thiz=this;
		Ext.Ajax.request({   
       			url:'examSchoolArrange_saveCKSchool.do',
       			params:{
        			'lcid':lcid,
        			'xxcode':xxcode
        		},
        		success: function(resp,opts) {
                    Ext.Msg.alert("提示","设置成功！");
                    thiz.selectExamSchool();
                },
                failure: function(resp,opts) {
                       Ext.Msg.alert('错误', "设置失败！");
              	}      
		});
	},
	arrangeStudent:function(){
		//显示手动安排考生弹出框
		var selectedSchool = this.grid.getSelectionModel().getSelections();
    	if(selectedSchool.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行手动安排！");
    		return;
    	}
    	Ext.getCmp("kdsyzws1").setText("考点剩余座位数："+selectedSchool[0].get("SYZWS"));
    	plcid = selectedSchool[0].get("LCID");
    	pjyj = selectedSchool[0].get("REGION_CODE");
    	kdid = selectedSchool[0].get("KDID");
    	if(selectedSchool[0].get("SYZWS")<1){
			Ext.MessageBox.alert("消息","无剩余座位可安排！");
			return;
		}
    	var newStore = new Ext.data.JsonStore({
						autoLoad:false,
						url:'dropListAction_school.do?params='+pjyj,
						fields:["CODEID","CODENAME"]
					});
		Ext.getCmp("sch_find").clearValue();
		Ext.getCmp("nj_find").clearValue(); 
		Ext.getCmp("bj_find").clearValue(); 
    	Ext.getCmp("sch_find").store=newStore;  
        newStore.reload();
        Ext.getCmp("sch_find").bindStore(newStore);
		this.arrangeWindow.show();
		this.arrangeStuGrid.$load({"jyj":pjyj,"lcid":plcid});
	},
	selectExamStudent:function(){
		//选择需要安排到考点的考生查询区
		var xx=Ext.getCmp('sch_find').getValue();
		var nj=Ext.getCmp('nj_find').getValue();
		var bj=Ext.getCmp('bj_find').getValue();
		this.arrangeStuGrid.$load({"jyj":pjyj,"lcid":plcid,"xxdm":xx,"nj":nj,"bj":bj});
	},
	addStudentArrange:function(){
		var selected =  this.arrangeStuGrid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedStudent = this.arrangeStuGrid.getSelectionModel().getSelections();
    	var thiz = this;
    	var ksids = "";
    	var xxdms = "";
    	for(var i = 0; i < selectedStudent.length; i++) {
    		if(i != selectedStudent.length - 1) {
	    		ksids += selectedStudent[i].get("KSID") + ",";
	    		xxdms += selectedStudent[i].get("XXDM") + ",";
    		}else {
	    		ksids += selectedStudent[i].get("KSID");
	    		xxdms += selectedStudent[i].get("XXDM");
    		}
    	}
    	Ext.MessageBox.show({
    			title:"消息",
    			msg:"您确定要安排考生吗?",
    			buttons:Ext.MessageBox.OKCANCEL,
    			icon:Ext.MessageBox.QUESTION,
    			fn:function(b){
    				if(b=='ok'){
    					Ext.Ajax.request({             
			    		url :'examSchoolArrange_addStudentArrange.do',
			    		success: function(response,options){
				    				var respText = Ext.util.JSON.decode(response.responseText);
				    				Ext.MessageBox.alert("消息",respText.msg);
	    							thiz.arrangeWindow.hide();
	    							thiz.grid.$load();
			    				},
			    		params:{"ksids":ksids,"lcid":plcid,"xxdms":xxdms,"kdid":kdid},             
			    		waitMsg : '数据处理中...'//遮罩           
			    		});
			    					
    				}
    			}
    		})
	},
	autoStudentArrange:function(){
		//自动安排考生
		var selected =  this.examSchoolGrid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
		var selectedSchool = this.examSchoolGrid.getSelectionModel().getSelections();
		var count = this.examSchoolGrid.getStore().getCount();
		var xxdms = "";
		var aprses = "";
		for(var i = 0; i < count; i++) {
			var aprs = document.getElementById("aprs"+i).value;
			if(aprs==""){
				continue;
			}
			var str = /^[0-9]*$/;
			var reg = new RegExp(str);
			if(!reg.test(aprs)){
				Ext.MessageBox.alert("消息","请输入数字！");
				return;
			}
			var syrs  = this.examSchoolGrid.getStore().getAt(i).get("SYRS");
			if(aprs>syrs || aprs<1){
				Ext.MessageBox.alert("消息","安排人数不能大于剩余人数且大于0！");
				return;	
			}
    		if(i != selectedSchool.length - 1) {
	    		xxdms += this.examSchoolGrid.getStore().getAt(i).get("XXDM") + ",";
	    		aprses += aprs + ",";
    		}else {
	    		xxdms += this.examSchoolGrid.getStore().getAt(i).get("XXDM");
	    		aprses += aprs;
    		}
    	}
    	var thiz = this;
    	Ext.MessageBox.show({
    			title:"消息",
    			msg:"您确定要安排考生吗?",
    			buttons:Ext.MessageBox.OKCANCEL,
    			icon:Ext.MessageBox.QUESTION,
    			fn:function(b){
    				if(b=='ok'){
    					Ext.Ajax.request({             
			    		url :'examSchoolArrange_autoStudentArrange.do',
			    		success: function(response,options){
				    				var respText = Ext.util.JSON.decode(response.responseText);
				    				Ext.MessageBox.alert("消息",respText.msg);
	    							thiz.eidtWindow.hide();
	    							thiz.grid.$load();
			    				},
			    		params:{"kdid":kdid,"lcid":plcid,"xxdms":xxdms,"aprses":aprses},             
			    		waitMsg : '数据处理中...'//遮罩           
			    		});
			    					
    				}
    			}
    		})
	},
	deleteArrange:function(){
		var selectedTeacher = this.grid.getSelectionModel().getSelections();
	    	if(selectedTeacher.length < 1){
	    		Ext.MessageBox.alert("消息","请选择至少一条记录进行设置！");
	    		return;
	    	}
	    	var xxcode="";
	    	for (var i = 0; i < selectedTeacher.length; i++) {
	    		if(selectedTeacher[i].get("SFCK")=="是")
	    			xxcode += selectedTeacher[i].get("XX_JBXX_ID")+",";
	    	}
	    	if(xxcode==""){
	    		Ext.MessageBox.alert("消息","你选择的参考单位并不没有参加考试，无需取消！");
	    		return;
	    	}else{
	    		xxcode=xxcode.substring(0,xxcode.length-1);
	    	}
	    	var lcid=getLocationPram("lcid");
	    	var thiz=this;
	    	Ext.Ajax.request({   
       			url:'examSchoolArrange_deleteCKSchool.do',
       			params:{
        			'lcid':lcid,
        			'xxcode':xxcode
        		},
        		success: function(resp,opts) {
                    Ext.Msg.alert("提示","参考单位已取消！");
                     thiz.selectExamSchool();
                },
                failure: function(resp,opts) {
                       Ext.Msg.alert('错误', "设置失败！");
              	}  
      
       });
	},
	returnArrange:function(){
		window.location.href = Ext.get("ServerPath").dom.value+"/jsp/main.jsp?module=000402";
	}
});
