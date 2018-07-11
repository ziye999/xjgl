/*var wjcllx = [['1', '考生名单审核'],['2', '补报考生审核'],['3', '删除考生审核']];
var wjcllxStore = new Ext.data.SimpleStore({  
	fields : ['value', 'text'],  
	data : wjcllx  
});*/
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
    				if(str[0].get("SHFLAG")=="1"){
    					Ext.getCmp('cfxy').setDisabled(false);
    					Ext.getCmp('zpxy').setDisabled(false);
    					Ext.getCmp('apxy').setDisabled(false);
    					Ext.getCmp('xapxy').setDisabled(false);    					
    					Ext.getCmp('sh').setDisabled(true);
    					Ext.getCmp('qxsh').setDisabled(false);
    					Ext.getCmp('sc').setDisabled(false);
    					Ext.getCmp('fb').setDisabled(false);
    					Ext.getCmp('fbcj').setDisabled(true);
    					if(str[0].get("SFFB")=="已发布"){
    						Ext.getCmp('fb').setDisabled(true);
    						Ext.getCmp('cfxy').setDisabled(true);
        					Ext.getCmp('zpxy').setDisabled(true);
        					Ext.getCmp('apxy').setDisabled(true);
        					Ext.getCmp('xapxy').setDisabled(true);    					
        					Ext.getCmp('sh').setDisabled(true);
        					Ext.getCmp('qxsh').setDisabled(true);
        					Ext.getCmp('sc').setDisabled(true);
        					Ext.getCmp('fbcj').setDisabled(false);
    					}
    				}else if(str[0].get("SHFLAG")=="0"){
    					Ext.getCmp('cfxy').setDisabled(true);
    					Ext.getCmp('zpxy').setDisabled(true);
    					Ext.getCmp('apxy').setDisabled(true);
    					Ext.getCmp('xapxy').setDisabled(true);    					
    					Ext.getCmp('sh').setDisabled(false);
    					Ext.getCmp('qxsh').setDisabled(true);
    					Ext.getCmp('sc').setDisabled(true);
    					Ext.getCmp('fb').setDisabled(true);
    					Ext.getCmp('fbcj').setDisabled(true);
    				}else{
    					Ext.getCmp('cfxy').setDisabled(true);
    					Ext.getCmp('zpxy').setDisabled(true);
    					Ext.getCmp('apxy').setDisabled(true);
    					Ext.getCmp('xapxy').setDisabled(true);	
    					Ext.getCmp('sh').setDisabled(true);
    					Ext.getCmp('qxsh').setDisabled(true);
    					Ext.getCmp('sc').setDisabled(true);
    					Ext.getCmp('fb').setDisabled(true);
    					Ext.getCmp('fbcj').setDisabled(true);
    				}
    			}
    		}
    	});
		var cm = [
		    sm,
			{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   align:"center", sortable:true, dataIndex:"XQ"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME",renderer : renderdel,listeners:{
				"click":function(){    //监听"select"事件
					this.showLackOfTestStudent();
				},
				scope:this
			}},
			{header: "考试类型",   align:"center", sortable:true, dataIndex:"LX"},
			{header: "组织单位",   align:"center", sortable:true, dataIndex:"MC"},
			{header: "参考单位",   	align:"center", sortable:true, dataIndex:"CKDW"},
			{header: "考生数量",   align:"center", sortable:true, dataIndex:"SL"},
			{header: "审核状态",   align:"center", sortable:true, dataIndex:"SHZT"},
			{header: "发布状态",   align:"center", sortable:true, dataIndex:"SFFB"}
		];
		function renderdel(value, cellmeta, record, rowIndex, columnIndex, store){ 
  			var lcid = store.getAt(rowIndex).get('LCID'); 
  			var shflag = store.getAt(rowIndex).get('SHFLAG');   			
            var str = "";
            if (shflag=="1") {
            	str = value;
            }else {
            	str = "<a href='#' id='"+lcid+"'>"+value+"</a>";
            }            
            return str;  
        } 
		 
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"考生报名-考生名单审核",
			tbar:[ 
			  "->",{xtype:"button",id:"qxsh",text:"取消审核",iconCls:"p-icons-delete",handler:this.updateAllno,scope:this}    
			  ,"->",{xtype:"button",id:"sh",text:"审核",iconCls:"p-icons-save",handler:this.updateAll,scope:this}
			  ,"->",{xtype:"button",id:"fb",text:"发布",iconCls:"p-icons-save",handler:this.fbZt,scope:this}
			  ,"->",{xtype:"button",id:"fbcj",text:"发布成绩",iconCls:"p-icons-save",handler:this.fbcj,scope:this}
			  ,"->",{xtype:"button",id:"sc",text:"生成考试批次",iconCls:"p-icons-save",handler:this.scPc,scope:this}
			  ,"->",{xtype:"button",id:"cfxy",text:"重复数据校验",iconCls:"p-icons-save",handler:this.updateAllCf,scope:this}
			  ,"->",{xtype:"button",id:"zpxy",text:"重复照片清空",iconCls:"p-icons-save",handler:this.updateAllZp,scope:this}
			  ,"->",{xtype:"button",id:"apxy",text:"补报考生安排",iconCls:"p-icons-upload",handler:this.updateAllBb,scope:this}
			  ,"->",{xtype:"button",id:"xapxy",text:"补报考生新安排",iconCls:"p-icons-upload",handler:this.scPcB,scope:this}
			],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			action:"review_getListPage.do",
			fields :["LCID","XN","XQ","EXAMNAME","LX","DWID","DWTYPE","SHFLAG","SHZT","SL","MC","CKDW","SFFB"],
			border:false
		});
	
		//搜索区域
		var xnxq =new Ext.ux.form.XnxqField({width:180,id:"revxnxq_find",readOnly:true});
		var mc = new Ext.form.TextField({fieldLabel:"",id:"mc_find",width:180});
		/*var xn_sh	= new Ext.form.ComboBox({id:"xn_sh",store:wjcllxStore,value:'1',displayField:'text',valueField:'value',mode:'local',triggerAction:'all',width:200,listeners:{
			"select":function(combo, record, index){
				if(record.get("value")==2){
					Ext.getCmp("myTabs").setActiveTab(1);
					Ext.getCmp("xn_sh").setValue(1);
				}
				if(record.get("value")==3){
					Ext.getCmp("myTabs").setActiveTab(2);
					Ext.getCmp("xn_sh").setValue(1);
				}
			}
		}});*/
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
						columns: 5
					}, 
					baseCls:"table",
					items:[
						{html:"年度：",baseCls:"label_right",width:120},
						{items:[xnxq],baseCls:"component",width:200},
						{html:"组织单位：",baseCls:"label_right",width:120},
						{items:[mc],baseCls:"component",width:200},
						{layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:160}
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
		this.myTabs = new Ext.TabPanel({
		    id:'myTabsE',
		    region:'center',
		    margins:'0 5 0 0',
		    activeTab: 0,
		    enableTabScroll:true,
		    minTabWidth:135,
		    resizeTabs:true,
		    headerStyle:'display:none',
		    border: false,
		    tabWidth:135,
		    border:false,
		    items:[
		           {layout: 'fit', index: 0, border: false, items: [this.panel],title:"考生名单审核"}//,
		           /*{layout: 'fit', index: 1, border: false,id:"tab2", 
			                html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+'/js/main.jsp?module=00030301'+'"> </iframe>',title:"补报考生审核"},
			       {layout: 'fit', index: 2, border: false,id:"tab3", 
				            html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+'/js/main.jsp?module=00030302'+'"> </iframe>',title:"删除考生审核"*/  
			    ]
		});
	},
	/** 初始化界面 **/
    initFace:function(){
    	this.panel_top=new Ext.Panel({
    		layout:"fit",
    		id:"panel_topER",
    		region:"north",
    		border:false,
    		items:[this.myTabs]
    	});
    	this.addPanel({layout:"fit",items:[this.panel_top]});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
    selectExamSubject:function(){
		var xn=Ext.getCmp('revxnxq_find').getValue();
		var mc=Ext.getCmp('mc_find').getValue();
		this.grid.$load({"xn":xn,"mc":mc});
	},
	scPc:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	} 
    	this.lcid = selectedBuildings[0].get("LCID");
    	this.scWin = this.createScWindow();
    	this.scWin.show();
	},
	scPcB:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	} 
    	this.lcid = selectedBuildings[0].get("LCID");
    	this.scBWin = this.createScBWindow();
    	this.scBWin.show();
	},
	createScWindow:function(){
    	var score = new Ext.form.TextField({name:"score",id:"score",value:"100",width:170,maxLength:3,allowBlank:false,blankText:"满分值不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText:"满分值只能是正整数！"});
		var timelen = new Ext.form.TextField({name:"timelen",id:"timelen",value:"90",width:170,maxLength:3,allowBlank:false,blankText:"考试时长不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText:"考试时长只能是正整数！"});		
		var start1 = new Ext.form.TextField({name:"start1",id:"start1",value:"08:30",width:170,maxLength:5,allowBlank:true,regex:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,regexText:"上午考试开始时间1格式不正确！"});
		var start2 = new Ext.form.TextField({name:"start2",id:"start2",value:"11:00",width:170,maxLength:5,allowBlank:true,regex:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,regexText:"上午考试开始时间2格式不正确！"});
		var start3 = new Ext.form.TextField({name:"start3",id:"start3",value:"13:30",width:170,maxLength:5,allowBlank:true,regex:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,regexText:"下午考试开始时间1格式不正确！"});
		var start4 = new Ext.form.TextField({name:"start4",id:"start4",value:"16:00",width:170,maxLength:5,allowBlank:true,regex:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,regexText:"下午考试开始时间2格式不正确！"});
		var start5 = new Ext.form.TextField({name:"start5",id:"start5",value:"",width:170,maxLength:5,allowBlank:true,regex:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,regexText:"晚上考试开始时间1格式不正确！"});
		var start6 = new Ext.form.TextField({name:"start6",id:"start6",value:"",width:170,maxLength:5,allowBlank:true,regex:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,regexText:"晚上考试开始时间2格式不正确！"});
		
    	this.search2 = new Ext.form.FormPanel({
    		region: "north",
    		height:400,
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
							{html:"满分值：",baseCls:"label_right",width:100},
							{html:"<font class='required'>*</font>",items:[score],baseCls:"component",width:190},
							{html:"考试时长：",baseCls:"label_right",width:100},
							{html:"<font class='required'>*</font>",items:[timelen],baseCls:"component",width:190},							
							{html:"上午开始时间1：",baseCls:"label_right",width:100},
							{html:"",items:[start1],baseCls:"component",width:190},
							{html:"上午开始时间2：",baseCls:"label_right",width:100},
							{html:"",items:[start2],baseCls:"component",width:190},
							{html:"下午开始时间1：",baseCls:"label_right",width:100},
							{html:"",items:[start3],baseCls:"component",width:190},
							{html:"下午开始时间2：",baseCls:"label_right",width:100},
							{html:"",items:[start4],baseCls:"component",width:190},
							{html:"晚上开始时间1：",baseCls:"label_right",width:100},
							{html:"",items:[start5],baseCls:"component",width:190},
							{html:"晚上开始时间2：",baseCls:"label_right",width:100},
							{html:"",items:[start6],baseCls:"component",width:190}
						] 
	                }]  
		       }]  
	    	});
	    var _b_save	  = new Ext.Button({text:"确定",iconCls:"p-icons-save",handler:this.scSubject,id:"scbutton",scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.scWin.hide()},scope:this});
		var _b_reset = new Ext.Button({text:"重置",iconCls:"p-icons-delete",handler:function(){this.search2.getForm().reset()},scope:this});
	    return new Ext.ux.Window({
     		title:"生成考试批次",	
     		height:380,
     		width:440,
     		tbar:{cls:"ext_tabr",
				  items:["->",_b_cancel,"->",_b_reset,"->",_b_save]},
			items:[this.search2]
     	});
    },
    scSubject:function(){
    	var fs = Ext.getCmp('score').getValue();
    	var sj = Ext.getCmp('timelen').getValue();    	
    	var start1 = Ext.getCmp('start1').getValue();
    	var start2 = Ext.getCmp('start2').getValue();
    	var start3 = Ext.getCmp('start3').getValue();
    	var start4 = Ext.getCmp('start4').getValue();
    	var start5 = Ext.getCmp('start5').getValue();
    	var start6 = Ext.getCmp('start6').getValue();
    	if (fs=="") {
    		Ext.MessageBox.alert("消息","满分值不能为空！");
    		return;
    	}
    	if (sj=="") {
    		Ext.MessageBox.alert("消息","考试时长不能为空！");
    		return;
    	}
    	if (start1==""&&start2==""&&start3==""&&start4==""&&start5==""&&start6=="") {
    		Ext.MessageBox.alert("消息","请至少要填写一个排考时间！");
    		return;
    	}
    	Ext.getCmp('scbutton').setDisabled(true);
    	var thiz=this;
    	Ext.Msg.confirm('消息','确认对这一轮次考试自动生成考试批次？', function (id) {
    		if (id == 'yes') {
    			Ext.Ajax.request({   
    				url:'review_updateSc.do',
    				timeout: 3600000,
    				params:{
    					'lcid':thiz.lcid,
    					'score':fs,
    					'timelen':sj,
    					'start1':start1,
    					'start2':start2,
    					'start3':start3,
    					'start4':start4,
    					'start5':start5,
    					'start6':start6    					
    				},
    				success: function(resp,opts) {
    					Ext.getCmp('scbutton').setDisabled(false);
    					thiz.scWin.hide();
    					var respText = Ext.util.JSON.decode(resp.responseText);
    					Ext.MessageBox.alert("提示",respText.msg);    					
    					thiz.selectExamSubject();thiz.selectExamSubject();    					
    				},
    				failure: function(resp,opts){
    					var respText = Ext.util.JSON.decode(resp.responseText);
			            Ext.Msg.alert('提示', respText.msg);
			        }
    			});
    		}
        });    	
    },
    createScBWindow:function(){
    	var scoreB = new Ext.form.TextField({name:"scoreB",id:"scoreB",value:"100",width:170,maxLength:3,allowBlank:false,blankText:"满分值不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText:"满分值只能是正整数！"});
		var timelenB = new Ext.form.TextField({name:"timelenB",id:"timelenB",value:"90",width:170,maxLength:3,allowBlank:false,blankText:"考试时长不能为空！",regex:/^[0-9]*[1-9][0-9]*$/,regexText:"考试时长只能是正整数！"});		
		var startB1 = new Ext.form.TextField({name:"startB1",id:"startB1",value:"08:00",width:170,maxLength:5,allowBlank:true,regex:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,regexText:"上午考试开始时间1格式不正确！"});
		var startB2 = new Ext.form.TextField({name:"startB2",id:"startB2",value:"10:00",width:170,maxLength:5,allowBlank:true,regex:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,regexText:"上午考试开始时间2格式不正确！"});
		var startB3 = new Ext.form.TextField({name:"startB3",id:"startB3",value:"13:00",width:170,maxLength:5,allowBlank:true,regex:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,regexText:"下午考试开始时间1格式不正确！"});
		var startB4 = new Ext.form.TextField({name:"startB4",id:"startB4",value:"15:00",width:170,maxLength:5,allowBlank:true,regex:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,regexText:"下午考试开始时间2格式不正确！"});
		var startB5 = new Ext.form.TextField({name:"startB5",id:"startB5",value:"18:00",width:170,maxLength:5,allowBlank:true,regex:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,regexText:"晚上考试开始时间1格式不正确！"});
		var startB6 = new Ext.form.TextField({name:"startB6",id:"startB6",value:"20:00",width:170,maxLength:5,allowBlank:true,regex:/^(0\d{1}|1\d{1}|2[0-3]):([0-5]\d{1})$/,regexText:"晚上考试开始时间2格式不正确！"});
		
    	this.searchB2 = new Ext.form.FormPanel({
    		region: "north",
    		height:400,
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
							{html:"满分值：",baseCls:"label_right",width:100},
							{html:"<font class='required'>*</font>",items:[scoreB],baseCls:"component",width:190},
							{html:"考试时长：",baseCls:"label_right",width:100},
							{html:"<font class='required'>*</font>",items:[timelenB],baseCls:"component",width:190},							
							{html:"上午开始时间1：",baseCls:"label_right",width:100},
							{html:"",items:[startB1],baseCls:"component",width:190},
							{html:"上午开始时间2：",baseCls:"label_right",width:100},
							{html:"",items:[startB2],baseCls:"component",width:190},
							{html:"下午开始时间1：",baseCls:"label_right",width:100},
							{html:"",items:[startB3],baseCls:"component",width:190},
							{html:"下午开始时间2：",baseCls:"label_right",width:100},
							{html:"",items:[startB4],baseCls:"component",width:190},
							{html:"晚上开始时间1：",baseCls:"label_right",width:100},
							{html:"",items:[startB5],baseCls:"component",width:190},
							{html:"晚上开始时间2：",baseCls:"label_right",width:100},
							{html:"",items:[startB6],baseCls:"component",width:190}
						] 
	                }]  
		       }]  
	    	});
	    var _b_saveB	  = new Ext.Button({text:"确定",iconCls:"p-icons-save",handler:this.scBSubject,id:"scbuttonB",scope:this});
		var _b_cancelB = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.scBWin.hide()},scope:this});
		var _b_resetB = new Ext.Button({text:"重置",iconCls:"p-icons-delete",handler:function(){this.searchB2.getForm().reset()},scope:this});
	    return new Ext.ux.Window({
     		title:"补充安排",	
     		height:380,
     		width:440,
     		tbar:{cls:"ext_tabr",
				  items:["->",_b_cancelB,"->",_b_resetB,"->",_b_saveB]},
			items:[this.searchB2]
     	});
    },
    scBSubject:function(){
    	var fs = Ext.getCmp('scoreB').getValue();
    	var sj = Ext.getCmp('timelenB').getValue();    	
    	var start1 = Ext.getCmp('startB1').getValue();
    	var start2 = Ext.getCmp('startB2').getValue();
    	var start3 = Ext.getCmp('startB3').getValue();
    	var start4 = Ext.getCmp('startB4').getValue();
    	var start5 = Ext.getCmp('startB5').getValue();
    	var start6 = Ext.getCmp('startB6').getValue();
    	if (fs=="") {
    		Ext.MessageBox.alert("消息","满分值不能为空！");
    		return;
    	}
    	if (sj=="") {
    		Ext.MessageBox.alert("消息","考试时长不能为空！");
    		return;
    	}
    	if (start1==""&&start2==""&&start3==""&&start4==""&&start5==""&&start6=="") {
    		Ext.MessageBox.alert("消息","请至少要填写一个排考时间！");
    		return;
    	}
    	Ext.getCmp('scbuttonB').setDisabled(true);
    	var thiz=this;
    	Ext.Msg.confirm('消息','确认对这一轮次考试进行补排？', function (id) {
    		if (id == 'yes') {
    			Ext.Ajax.request({   
    				url:'review_updateScB.do',
    				timeout: 3600000,
    				params:{
    					'lcid':thiz.lcid,
    					'score':fs,
    					'timelen':sj,
    					'start1':start1,
    					'start2':start2,
    					'start3':start3,
    					'start4':start4,
    					'start5':start5,
    					'start6':start6    					
    				},
    				success: function(resp,opts) {
    					Ext.getCmp('scbuttonB').setDisabled(false);
    					thiz.scBWin.hide();
    					var respText = Ext.util.JSON.decode(resp.responseText);
    					Ext.MessageBox.alert("提示",respText.msg);    					
    					thiz.selectExamSubject();thiz.selectExamSubject();    					
    				},
    				failure: function(resp,opts){
    					var respText = Ext.util.JSON.decode(resp.responseText);
			            Ext.Msg.alert('提示', respText.msg);
			        }
    			});
    		}
        });    	
    },
    updateAllCf:function(){
    	var thiz=this;		
    	Ext.Msg.confirm('消息','确认校验考生重复记录？', function (id) {
    		if (id == 'yes') {
    			Ext.Ajax.request({   
    				url:'review_updateAllCf.do',
    				success: function(resp,opts) {
    					var respText = Ext.util.JSON.decode(resp.responseText);
    					Ext.MessageBox.alert("提示",respText.msg);
    					thiz.selectExamSubject();thiz.selectExamSubject();
    				},
    				failure: function(resp,opts){
    					Ext.Msg.alert('错误', "校验失败！");
    				}
    			 });
    		}
        });
	},
	updateAllZp:function(){
    	var thiz=this;		
    	Ext.Msg.confirm('消息','确认清除考生重复照片？', function (id) {
    		if (id == 'yes') {
    			Ext.Ajax.request({   
    				url:'review_updateAllZp.do',
    				success: function(resp,opts) {
    					var respText = Ext.util.JSON.decode(resp.responseText);
    					Ext.MessageBox.alert("提示",respText.msg);
    					thiz.selectExamSubject();thiz.selectExamSubject();
    				},
    				failure: function(resp,opts){
    					Ext.Msg.alert('错误', "清除失败！");
    				}
    			 });
    		}
        });
	},
	updateAllBb:function(){
    	var thiz=this;		
    	Ext.Msg.confirm('消息','确认补报考生安排？', function (id) {
    		if (id == 'yes') {
    			Ext.Ajax.request({   
    				url:'review_updateAllBb.do',
    				success: function(resp,opts) {
    					var respText = Ext.util.JSON.decode(resp.responseText);
    					Ext.MessageBox.alert("提示",respText.msg);
    					thiz.selectExamSubject();thiz.selectExamSubject();
    				},
    				failure: function(resp,opts){
    					Ext.Msg.alert('错误', "安排失败！");
    				}
    			 });
    		}
        });
	},
    updateAll:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}
    	var lcid = selectedBuildings[0].get("LCID");
    	var thiz=this;
    	Ext.Msg.confirm('消息','确认审核这'+selectedBuildings.length+'条记录？', function (id) {
    		if (id == 'yes') {
    			Ext.Ajax.request({   
    				url:'review_updateAll.do',
    				params:{
    					'lcid':lcid
    				},
    				success: function(resp,opts) {
    					var respText = Ext.util.JSON.decode(resp.responseText);
    					Ext.MessageBox.alert("提示",respText.msg);
    					thiz.selectExamSubject();thiz.selectExamSubject();
    				},
    				failure: function(resp,opts){
    					var respText = Ext.util.JSON.decode(resp.responseText);
    					Ext.MessageBox.alert("提示",respText.msg);
    				}
    			 });
    		}
        });
	},
	updateAllno:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}
    	var lcid = selectedBuildings[0].get("LCID");
    	var thiz=this;
    	Ext.Ajax.request({   
       		url:'review_updateAllno.do',
       		params:{
       			'lcid':lcid
        	},
        	success: function(resp,opts) {
        		var respText = Ext.util.JSON.decode(resp.responseText);
        		Ext.MessageBox.alert("提示",respText.msg);
        		thiz.selectExamSubject();thiz.selectExamSubject();
        	},
        	failure: function(resp,opts){
        		var respText = Ext.util.JSON.decode(resp.responseText);
        		Ext.MessageBox.alert("提示",respText.msg);
        	}
    	});	
	},
	showLackOfTestStudent:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var shzt =selectedBuildings[0].get("SHFLAG");
//    	if (shzt=="1") {
//    		Ext.MessageBox.alert("消息","已审核考生信息不允许任何操作！");
//    		return;
//    	}
    	var ids =selectedBuildings[0].get("LCID");
    	this.lcid=ids;
    	this.createShowLackOfTestStudent();
    	var panel=Ext.getCmp("panel_topER");
  		panel.remove(Ext.getCmp("myTabsE"));
  		panel.add(this.panel_top2);
  		panel.doLayout(false);  		
  		this.CheatGrid.$load({"lcid":this.lcid});
    },
    createShowLackOfTestStudent:function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false,
			listeners:{
    			"selectionchange":function(sm){
    				var str = sm.getSelections();
    				if(str[0].get("FLAG")=="1"){
    					Ext.getCmp('xssh').setDisabled(true);    					
    				}else{
    					Ext.getCmp('xssh').setDisabled(false);    					
    				}
    			}
    		}
		});
		var cm = [
		          sm,
		          {header: "参考单位",   align:"center", sortable:true, dataIndex:"XXMC"},
		          {header: "姓名",   align:"center", sortable:true, dataIndex:"XM"},
		          {header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
		          {header: "考号",   align:"center", sortable:true, dataIndex:"KSCODE"},
		          {header: "身份证号",   align:"center", sortable:true, dataIndex:"SFZJH"}
				];
		this.CheatGrid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			id:"CheatGridER",
			title:"考生报名-考生名单审核",
			tbar:[ 
				  "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}
				  //,"->",{xtype:"button",id:"xssh",text:"审核通过",iconCls:"p-icons-save",handler:this.updateExamRoom,scope:this},
				  ],
			page:true,
			rowNumber:true,
			region:"center",
			action:"reviewKs_getListPage.do?lcid="+getLocationPram("lcid"),
			fields :["XXMC","XM","XB","KSCODE","XJH","SFZJH","SCKSID","FLAG"],
			border:false
		});
		this.CheatGrid.on("render",function(){    
			this.CheatGrid.selModel.selectAll();  
		    //延迟300毫秒  
		},this,{delay:400});
		//搜索区域
		/*var bj	= new Ext.ux.Combox({width:190,id:"bj_find"});
		var nj	= new Ext.ux.Combox({width:190,id:"nj_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_classByGrade.do?params='+id+'&schoolId='+Ext.getCmp("sch_find").getValue(),
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
					Ext.getCmp("bj_find").clearValue(); 
                    Ext.getCmp("nj_find").store=newStore;  
                    newStore.reload();
                    Ext.getCmp("nj_find").bindStore(newStore);
				},
                scope:this
			}
		});
		var xjjyj = new Ext.ux.Combox({ width:190,id:"xjjyj_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_school.do?params='+id,
										fields:["CODEID","CODENAME"]
					});
					Ext.getCmp("sch_find").clearValue(); 
					Ext.getCmp("nj_find").clearValue(); 
					Ext.getCmp("bj_find").clearValue(); 
                    Ext.getCmp("sch_find").store=newStore;  
                    newStore.reload();
                    Ext.getCmp("sch_find").bindStore(newStore);
				},
				scope:this
			}
		});
		var sjjyj	= new Ext.ux.Combox({dropAction:"sjjyj", width:190,id:"sjjyj_find",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
									autoLoad:false,
									url:'dropListAction_jyj.do?params='+id,
									fields:["CODEID","CODENAME"]
					});
					Ext.getCmp("xjjyj_find").clearValue(); 
					Ext.getCmp("sch_find").clearValue(); 
					Ext.getCmp("nj_find").clearValue(); 
					Ext.getCmp("bj_find").clearValue(); 
                    Ext.getCmp("xjjyj_find").store=newStore;  
                    newStore.reload();
                    Ext.getCmp("xjjyj_find").bindStore(newStore);
				},
				scope:this
			}
		});*/
		var xjh = new Ext.form.TextField({fieldLabel:"学号",id:"xjh_find",maxLength:200, width:190});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamSubject2,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.CheatSearch.getForm().reset()},scope:this});
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
						//{html:"上级单位：",baseCls:"label_right",width:80},
						//{items:[sjjyj],baseCls:"component",width:200},
						//{html:"组织单位：",baseCls:"label_right",width:80},
						//{items:[xjjyj],baseCls:"component",width:200},
						//{html:"参考单位：",baseCls:"label_right",width:80}, 
						//{items:[schoolname],baseCls:"component",width:200},
						//{html:"等级：",baseCls:"label_right",width:80},
						//{items:[nj],baseCls:"component",width:200},
						//{html:"科目：",baseCls:"label_right",width:80}, 
						//{items:[bj],baseCls:"component",width:200},						
						{html:"身份证号：",baseCls:"label_right",width:80},
						{items:[xjh],baseCls:"component",width:200},
						{layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:160,colspan:2}
					] 
		        }]  
		    }]  
		});
		this.panel_top2=new Ext.Panel({
			id:"panel_topER2",
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			items:[this.CheatSearch,this.CheatGrid]
		});
	},
	selectExamSubject2:function(){
		//var xx = Ext.getCmp('sch_find').getValue();
		//var nj = Ext.getCmp('nj_find').getValue();
		//var bj = Ext.getCmp('bj_find').getValue();
		var xjh = Ext.getCmp('xjh_find').getValue();
		this.CheatGrid.$load({"xjh":xjh});
		this.CheatGrid.$load({"xjh":xjh});
	},
	fanhui:function(){
		this.initComponent();
    	var panel=Ext.getCmp("panel_topER");
  		panel.remove(Ext.getCmp("panel_topER2"));
  		panel.add(this.myTabs);
  		panel.doLayout(false);
		var xn=Ext.getCmp('revxnxq_find').getValue();
		this.grid.$load({"xn":xn});
	},
	fbZt:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
		if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}
		var sh = selectedBuildings[0].get("SHZT");
		if(sh!="已审核"){
			Ext.MessageBox.alert("错误","只有审核之后才能发布！");
    		return;
		}
		var thiz=this;
		var lcid = selectedBuildings[0].get("LCID");
		Ext.MessageBox.show({
			title:"消息",
			msg:"确定要发布吗？发布之后可以打印准考证！",
			buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,    			
    		fn:function(b){
    			if(b=='ok'){
    				Ext.Ajax.request({   
    		       		url:'review_fbZT.do',
    		       		params:{'lcid':lcid},
    		       		success: function(resp,opts) {
    		       			var respText = Ext.util.JSON.decode(resp.responseText);
    		       			if (respText.msg!='') {
    		       				Ext.MessageBox.alert("提示",respText.msg);
    		       		
    		       			}
    		       			thiz.selectExamSubject();thiz.selectExamSubject();
    		       		},
    		       		failure: function(resp,opts){
    		       			Ext.Msg.alert('错误', "发布失败！");
    		       		}        	
    		    	});
    			}
    		}
		})
	},
	fbcj:function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
		if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}
		var thiz=this;
		var lcid = selectedBuildings[0].get("LCID");
		Ext.MessageBox.show({
			title:"消息",
			msg:"确定要发布成绩吗？发布成绩之后可以查看考生成绩！",
			buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,    			
    		fn:function(b){
    			if(b=='ok'){
    				Ext.Ajax.request({   
    		       		url:'review_fbZT.do',
    		       		params:{'lcid':lcid},
    		       		success: function(resp,opts) {
    		       			var respText = Ext.util.JSON.decode(resp.responseText);
    		       			if (respText.msg!='') {
    		       				Ext.MessageBox.alert("提示",respText.msg);
    		       		
    		       			}
    		       			thiz.selectExamSubject();thiz.selectExamSubject();
    		       		},
    		       		failure: function(resp,opts){
    		       			Ext.Msg.alert('错误', "发布失败！");
    		       		}        	
    		    	});
    			}
    		}
		})
	}
	/*updateExamRoom:function(){
		var selectedBuildings = this.CheatGrid.getSelectionModel().getSelections();
		if(selectedBuildings.length < 1){
			Ext.MessageBox.alert("消息","请选择一条数据！");
			return;
		}
		var ksid = "";
		for(var i=0;i<selectedBuildings.length;i++){
			ksid += "'" +selectedBuildings[i].get("SCKSID") +"',";
		}
		var thiz=this;    	
		Ext.Ajax.request({   
			url:'review_updateDl.do',
			params:{
				'ksid':ksid
			},
			success: function(resp,opts) {
				var respText = Ext.util.JSON.decode(resp.responseText);
				Ext.MessageBox.alert("提示",respText.msg);
				thiz.selectExamSubject2();
			},
			failure: function(resp,opts){
				Ext.Msg.alert('错误', "审核失败！");
			}  
		});    	
	}*/
});