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
    	var thiz=this;
    	Ext.Ajax.request({   
       		url:'scoresQuery_getUserType.do',
	        success: function(resp,opts) {
	        	var respText = resp.responseText;
	           	thiz.userType=respText;
	        }
        });
    },
    /** 对组件设置监听 **/
    initListener:function(){
    
    },
    initComponent :function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
			sm,
			{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   align:"center", sortable:true, dataIndex:"XQ"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME",renderer : renderdel,listeners:{
				"click":function(){    //监听"select"事件
					this.showStudentScores();
				},
				scope:this
			}},
			{header: "考试类型",   align:"center", sortable:true, dataIndex:"EXAMTYPE"},
			{header: "组织单位",	align:"center", sortable:true, dataIndex:"DWMC"},
			{header: "参考单位",	align:"center", sortable:true, dataIndex:"CKDW"}
		];
		function renderdel(value, cellmeta, record, rowIndex, columnIndex, store){
			var lcid = store.getAt(rowIndex).get('LCID'); 
			var str = "<a href='#' id='"+lcid+"'>"+value+"</a>";
			return str;  
		} 
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"成绩查询",
			tbar:[ 
			      "->",{xtype:"button",text:"成绩查询",iconCls:"p-icons-setting",handler:this.showStudentScores,scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			action:"resultsregister_getListPage.do",
			fields :["LCID","XN","XQ","EXAMNAME","DWMC","CKDW","SL","DWID","DWTYPE","EXAMTYPE"],
			border:false
		});
		//搜索区域
		var xn_find = new Ext.ux.form.XnxqField({width:190,id:"sqxn_find",readOnly:true});
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
		this.panel1=new Ext.Panel({
			id:"panel1",
			region:"north",
			width:"auto",
			layout:"border",
			border:false,
			items:[this.search,this.grid]
	    });	      	
	},
	createshowStudentScores :function(){
		this.context_panel=new Ext.Panel({
    		id:"context_panel",
		    region:"center",
		    width:"auto",
		    border:true
    	})
		//搜索区域
		//组织单位
		this.xuexiao_find= new Ext.ux.form.OrganField({name:"sup_organ_sel",readOnly:true});
		var nj_find = new Ext.ux.Combox({dropAction:"grade", width:150,id:"nj_find",params:"",
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_classByGradeAndSchool.do?params='+id,
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
		var bj_find = new Ext.ux.Combox({width:150,id:"bj_find"});
		//var km_find = new Ext.ux.Combox({dropAction:"getKskmBykslc", width:170,id:"km_find",params:this.lcid});
		//考生姓名，考号
		var xm_kh_xjh_find = new Ext.form.TextField({fieldLabel:"",id:"xm_kh_xjh_find",name:"xm_kh_xjh_find",maxLength:50,width:150});
		var xuexiao_find= new Ext.ux.form.OrganField({name:"sup_organ_sel",width:190,readOnly:true});
		var xm_kh_xjh = new Ext.form.TextField({fieldLabel:"",id:"xm_kh_xjh",name:"xm_kh_xjh",maxLength:50,width:190});
		var kspc	= new Ext.ux.Combox({width:190,id:"kspc_find"});
    	var kc = new Ext.ux.Combox({width:190,id:"kc_findR"});
		var kd = new Ext.ux.Combox({width:190,id:"kd_findR",listeners:{
			"select":function(){
				var id=Ext.getCmp("kd_findR").getValue();
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_examRoom.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("kc_findR").clearValue();  
            	Ext.getCmp("kc_findR").store=newStore;  
                newStore.reload();
                Ext.getCmp("kc_findR").bindStore(newStore);
			}
		}});
		
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectStudentScores,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.lackOfTestStudentSearch.getForm().reset()},scope:this});
		//判断用户类型
		if(this.userType==2){		
			this.lackOfTestStudentSearch = new Ext.form.FormPanel({
				region: "north",
				height:90,
				items:[{  
					layout:'form',  
					xtype:'fieldset',  
					style:'margin:5',
					title:'查询条件',  
					items: [{
						xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 7
						}, 
						baseCls:"table",  
						items:[
						       {html:"等级：",baseCls:"label_right",width:70},
						       {items:[nj_find],baseCls:"component",width:180},
						       {html:"科目：",baseCls:"label_right",width:50},
						       {items:[bj_find],baseCls:"component",width:160},
						       /*{html:"考试批次：",baseCls:"label_right",width:140},
							   {items:[km_find],baseCls:"component",width:190},*/
						       {html:"姓名/考号/身份证号：",baseCls:"label_right",width:150},
						       {items:[xm_kh_xjh_find],baseCls:"component",width:160},
						       {layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:150}							
							] 
	                  	}]  
			     	}]  
		    	});
	    	}else{
	    		this.lackOfTestStudentSearch = new Ext.form.FormPanel({
	    			region: "north",
	    			height:130,
	    			items:[{  
	    				layout:'form',  
	    				xtype:'fieldset',  
	    				style:'margin:5',
	    				title:'查询条件',  
	    				items: [{
	    					xtype : "panel",
						layout : "table",
						layoutConfig : {
							columns : 6
						},
						baseCls : "table",
							items:[
							
								 {html:"所属单位：",baseCls:"label_right",width:70}, 
						         {items:[xuexiao_find],baseCls : "component",width:200},
						         {html:"考试批次：",baseCls:"label_right",width:70},
						         {items:[kspc],baseCls:"component",width:200},						         
						         {html:"姓名/身份证号：",baseCls : "label_right",width : 140}, 
						         {items:[xm_kh_xjh],baseCls : "component",width : 200},
						         {html:"考点：",baseCls:"label_right",width:70},
						         {items:[kd],baseCls:"component",width:200},
						         {html:"考场：",baseCls:"label_right",width:70},
						         {items:[kc],baseCls:"component",width:200},
						         {layout:"absolute",items:[cx, cz],baseCls:"component_btn",width:160,colspan:2}
						         
													
							] 
	                    }]  
			       }]  
	    	});	    		
	    }
		this.lackOfTestStudentPanel=new Ext.Panel({
	    		id:"lackOfTestStudentPanel",
	    		title:"考生成绩查询",
	    		region:"north",
	    		height:500,
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		tbar:["->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}],
	    		items:[this.lackOfTestStudentSearch,this.context_panel]
		});	    	
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.panel_top2=new Ext.Panel({
    			layout:"fit",
	    		id:"panel_topS2",
	    		region:"north",
	    		border:false,
	    		items:[this.panel1]
	    });
    	this.addPanel({layout:"fit",items:[this.panel_top2]});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
    //显示缺考考生信息
	showStudentScores:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var ids =selectedBuildings[0].get("LCID");
    	this.lcid=ids;    	
    	this.createshowStudentScores();    	
    	var panel=Ext.getCmp("panel_topS2");
  		panel.remove(Ext.getCmp("panel1"));
  		panel.add(this.lackOfTestStudentPanel);
  		panel.doLayout(false);
  		var newStore = new Ext.data.JsonStore({
			autoLoad:false,
			url:'dropListAction_getKskmBykslc.do?params='+this.lcid,
			fields:["CODEID","CODENAME"]
		});
		Ext.getCmp("kspc_find").clearValue(); 
		Ext.getCmp("kspc_find").store=newStore;
		newStore.reload();
		Ext.getCmp("kspc_find").bindStore(newStore);
		
		var newStore1 = new Ext.data.JsonStore({
			autoLoad:false,
			url:'dropListAction_kaoDianMc.do?params='+this.lcid,
			fields:["CODEID","CODENAME"]
		});
		Ext.getCmp("kd_findR").clearValue();  
		Ext.getCmp("kc_findR").clearValue(); 
    	Ext.getCmp("kd_findR").store=newStore1;  
        Ext.getCmp("kc_findR").store.removeAll();
        newStore1.reload();
        Ext.getCmp("kd_findR").bindStore(newStore1);
  		
  		//this.studentScoresGrid.$load({"lcId":this.lcid});
    },
    //查询考试轮次
    selectExamRound:function(){
    	if (Ext.getCmp('sqxn_find').getValue()!="") {
    		var xnxq = Ext.getCmp('sqxn_find').getCode();
    		this.grid.$load({"xnxq_id":xnxq});
    	}else {
    		this.grid.$load({"xnxq_id":""});
    	}    	
    },
    //查询缺考考生
    selectStudentScores:function(){
    	var thiz=this;
    	var schoolId=this.lackOfTestStudentSearch.getForm().findField('sup_organ_sel').getCodes();
    	var nj=Ext.getCmp('nj_find').getValue();
    	var kspc_find = Ext.getCmp("kspc_find").getValue();
    	var kd_findR = Ext.getCmp("kd_findR").getValue();
    	var kc_findR = Ext.getCmp("kc_findR").getValue();
		var xm_kh_xjh=Ext.getCmp('xm_kh_xjh').getValue();
    	    		
    	Ext.Ajax.request({
    		url:"scoresQuery_getKskm.do",
    		params:{'lcId':thiz.lcid,'nj':nj},
    		success: function(resp,opts) {
    			var respText = resp.responseText;
    			eval("var km="+respText);
		        var cm = [
					{header: "组考单位",   align:"center", sortable:true, dataIndex:"REGION_EDU"},
					{header: "参考单位",   align:"center", sortable:true, dataIndex:"XXMC"},
					{header: "姓名",   align:"center", sortable:true, dataIndex:"XM"},
					{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
					{header: "身份证号",   align:"center", sortable:true, dataIndex:"SFZJH"},
					{header: "考试时间",   align:"center", sortable:true, dataIndex:"STARTTIME"},
					{header: "考点名称",   align:"center", sortable:true, dataIndex:"BUILDNAME"},
					{header: "考场名称",   align:"center", sortable:true, dataIndex:"ROOMNAME"},
					{header: "座位号",   align:"center", sortable:true, dataIndex:"ZWH"},
					{header: "分数",   align:"center", sortable:true, dataIndex:"SCORE"},
					{header: "是否合格",   align:"center", sortable:true, dataIndex:"KSJG"},
					{header: "备注",   align:"center", sortable:true, dataIndex:"BZ"},
					{header: "是否生成成绩",   align:"center", sortable:true, dataIndex:"CJZT"}
					
				];
				var val=["KSID","REGION_EDU","XXMC","XM","XB","SFZJH","STARTTIME","BUILDNAME","ROOMNAME","ZWH","SCORE","KSJG","BZ","CJZT"];
				
				thiz.studentScoresGrid = new Ext.ux.GridPanel({
					id:"studentScoresGrid",
					cm:cm,
					//sm:sm,
					tbar:[ 
					  "->",{xtype:"button",text:"生成成绩查询",iconCls:"p-icons-update",handler:function(){thiz.createScore(thiz)},scope:this}
					  ],
					page:true,
					excel:true,
					rowNumber:true,
					region:"center",
					action:"exam_searchSore.do",
					fields :val,
					border:false
				});
				var panel=Ext.getCmp("context_panel");
				var height=panel.getHeight();
				
				thiz.newPanel=new Ext.Panel({
					id:"newPanel",
		    		region:"north",
		    		height:height,
		    		width:"auto",
		    		layout:"border",
		    		border:false,
		    		items:[thiz.studentScoresGrid]
				});
	    		
  				panel.remove(Ext.getCmp("newPanel"));
  				panel.add(thiz.newPanel);
  				panel.doLayout(false);
    		    thiz.studentScoresGrid.$load({"lcId":thiz.lcid,"kmid":kspc_find,"kdid":kd_findR,"kcid":kc_findR,"sfzh":xm_kh_xjh,"schoolcode":schoolId});
	        }    			
    	});    		
    },
    
     createScore:function(){
        var thiz=this;
    	var schoolId=this.lackOfTestStudentSearch.getForm().findField('sup_organ_sel').getCodes();
    	var nj=Ext.getCmp('nj_find').getValue();
    	var kspc_find = Ext.getCmp("kspc_find").getValue();
    	var kd_findR = Ext.getCmp("kd_findR").getValue();
    	var kc_findR = Ext.getCmp("kc_findR").getValue();
		var xm_kh_xjh=Ext.getCmp('xm_kh_xjh').getValue();
     
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"您确定要生成成绩查询吗?",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,    			
    		fn:function(b){
    			if(b=='ok'){
    				JH.$request({
    					params:{"lcid":thiz.lcid,"kmid":kspc_find,"kdid":kd_findR,"kcid":kc_findR,"sfzh":xm_kh_xjh,"schoolcode":schoolId},
    					action:"exam_createScore.do",
    					handler:function(){
    						Ext.Msg.alert("提示",action.result.msg);
    						thiz.grid.$load();
    					}
    				})    				
    			}
    		}
    	})
    },
    
    //返回到轮次页面
    fanhui:function(){
    	this.initComponent();
    	var panel=Ext.getCmp("panel_topS2");
  		panel.remove(Ext.getCmp("lackOfTestStudentPanel"));
  		panel.add(this.panel1);
  		panel.doLayout(false);
  		this.grid.$load();
    },
    createPrintStudentScores:function(){
    	var panel=Ext.getCmp("panel_topS2");
		var height=panel.getHeight();
		var dataPanel2=new Ext.Panel({
    		region:'center',
    		border:true,
   			id:"dataPanel2",
   			html:"",
   			bodyStyle:"border:2px red solid"
   		});
    	this.printPanel=new Ext.Panel({
    		id:"printPanelS",
    		border:true,
    		title:"打印预览",
    		region:"north",
    		height:height,
    		layout:"border",
    		tbar:[ 
    		      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhuiStudentScores,scope:this}
    		      ,"->",{xtype:"button",text:"打印",iconCls:"p-icons-update",handler:this.printStudentScoresData,scope:this}
				],
			items:[dataPanel2]			
    	});
    },
    showPrintStudentScores:function(thiz){
    	var schoolId=this.xuexiao_find.getCodes();
    	if(schoolId==undefined){
    		schoolId="";
    	}
    	var nj=Ext.getCmp('nj_find').getValue();
    	var xm_kh_xjh=Ext.getCmp('xm_kh_xjh_find').getValue();
    	var bj="";
    	if(Ext.getCmp('bj_find')!=null){
    		bj=Ext.getCmp('bj_find').getValue();
    	}
    	thiz.createPrintStudentScores();
    	var panel=Ext.getCmp("panel_topS2");
  		Ext.getCmp("lackOfTestStudentPanel").hide();
  		panel.add(this.printPanel);
  		panel.doLayout(false);
  		var height=Ext.getCmp('dataPanel2').getHeight()-4;
  		var iframe="<fieldset style='height:348; width:100%; border:0px; padding:0;'>"+
  						"<iframe id='frmReportCj' name='frmReportCj' width='100%' height='"+height+
  						"' src='scoresQuery_printData.do?lcId="+thiz.lcid+"&xmKhXjh="+xm_kh_xjh+"&schoolId="+schoolId+
  						"&nj="+nj+"&bj="+bj+"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanel2').innerHTML=iframe;
    },
    fanhuiStudentScores:function(){
    	var panel=Ext.getCmp("panel_topS2");
  		panel.remove(Ext.getCmp("printPanelS"));
  		Ext.getCmp("lackOfTestStudentPanel").show();
  		panel.doLayout(false);
    },
    printStudentScoresData:function(){
    	frmReportCj.print();
    }            
});