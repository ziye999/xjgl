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
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
		    sm,
			{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   align:"center", sortable:true, dataIndex:"XQ"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME"},
			{header: "考试时间",   align:"center", sortable:true, dataIndex:"DT"},
			{header: "优秀标准",   align:"center", sortable:true, dataIndex:"UPLINE"},
			{header: "不合格标准",   align:"center", sortable:true, dataIndex:"DOWNLINE"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"成绩统计补录-设置统计标准",
			tbar:[ 
			      "->",{xtype:"button",text:"设置标准",iconCls:"p-icons-update",handler:this.setbiaoz,scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			action:"setstandard_getListPage.do",
			fields :["LCID","EXAMNAME","XN","XQ","DT","UPLINE","DOWNLINE","BZID"],
			border:false
		});
	
		//搜索区域
		//var sup_organ_sel = new Ext.ux.form.OrganField({name:"sup_organ_sel",width:190,readOnly:true});
		var xnxq_find=new Ext.ux.form.XnxqField({width:190,id:"stanxnxq_find",readOnly:true,
			callback:function(){    //监听"select"事件
				var id=Ext.getCmp("stanxnxq_find").getCode();           //取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
								autoLoad:false,
								url:'dropListAction_examround.do?params='+id,
								fields:["CODEID","CODENAME"]
							});
				Ext.getCmp("lc_find1").clearValue(); 
				Ext.getCmp("lc_find1").store=newStore;  
				newStore.reload();
				Ext.getCmp("lc_find1").bindStore(newStore);
			}
		});
		var lc_find	= new Ext.ux.Combox({width:180,id:"lc_find1"});
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
					       {html:"年度：",baseCls:"label_right",width:120},
					       {items:[xnxq_find],baseCls:"component",width:190},
					       {html:"考试名称：",baseCls:"label_right",width:120},
					       {items:[lc_find],baseCls:"component",width:190},
					       {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
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
	selectExamSubject:function(){
		var xn=Ext.getCmp('stanxnxq_find').getValue();
		var lc=Ext.getCmp('lc_find1').getValue();
		this.grid.$load({"xn":xn,"lcid":lc});
	},
    setbiaoz:function(){
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length < 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}
    	var lcid = selectedBuildings[0].get("LCID");
    	var _b_save	  = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:function(){
    		var reg = /^\d+(\.\d+)?$/;
    		var v1 = Ext.getCmp("vl").getValue();
    		var v2 = Ext.getCmp("vl2").getValue();
    		if(!reg.test(v1)){
    			return;
    		}
    		if(!reg.test(v2)){
    			return;
    		}
			if(parseFloat(v1)<parseFloat(v2)){
    			Ext.MessageBox.alert("消息","上限值必须大于或等于下限值！");
    			return;
    		}
			if(parseInt(v1)<1){
    			Ext.MessageBox.alert("消息","上限值不能小于1！");
    			return;
    		}
			if(parseInt(v2)<1){
    			Ext.MessageBox.alert("消息","下限值不能小于1！");
    			return;
    		}
			if(parseFloat(v1)>100){
    			Ext.MessageBox.alert("消息","上限值不能大于100！");
    			return;
    		} 
    		var bzid = selectedBuildings[0].get("BZID");
    		var thiz=this;
    		if(bzid=="" || bzid ==null){
    			Ext.Ajax.request({   
               		url:'setstandard_saveStandard.do',
               		params:{
               			'lcid':lcid,'upline':v1,'downline':v2
                	},
                	success: function(resp,opts) {
                		var respText = Ext.util.JSON.decode(resp.responseText);
                		Ext.MessageBox.alert("提示",respText.msg);
                		thiz.selectExamSubject();
                	},
                	failure: function(resp,opts){
                		Ext.Msg.alert('错误', "设置失败！");
                	}  
    			});
    		}else{
    			Ext.Ajax.request({   
               		url:'setstandard_updateStandard.do',
               		params:{
               			'bzid':bzid,'upline':v1,'downline':v2
                	},
                	success: function(resp,opts) {
                		var respText = Ext.util.JSON.decode(resp.responseText);
                		Ext.MessageBox.alert("提示",respText.msg);
                		thiz.selectExamSubject();
                	},
                	failure: function(resp,opts){
                		Ext.Msg.alert('错误', "设置失败！");
                	}                	
    			});
    		}
        	thiz.fileUpWin.hide();
    	},scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.fileUpWin.hide()},scope:this});
		var vl	= new Ext.form.TextField({id:"vl",maxLength:18,width:50,regex : /^\d+(\.\d+)?$/,regexText : '只能为非负浮点数！'});
		var vl2	= new Ext.form.TextField({id:"vl2",maxLength:18,width:50,regex : /^\d+(\.\d+)?$/,regexText : '只能为非负浮点数！'});
		var fh	= new Ext.form.Label({id:"fh",text:"%"});
		var fh2	= new Ext.form.Label({id:"fh2",text:"%"});
		var fh3	= new Ext.form.Label({id:"fh3",text:"满分的"});
		var fh4	= new Ext.form.Label({id:"fh4",text:"满分的"});
		this.search3 = new Ext.form.FormPanel({
			region: "north",
			height:80,
			items:[{  
				layout:'form',  
				xtype:'fieldset',  
				style:'margin:10 10',
				items: [{
	                	xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 6
						}, 
						baseCls:"table",
						items:[
						       {html:"分数上限：",id:"t1",baseCls:"label_right",width:80},
						       {items:[fh3,vl,fh],baseCls:"component",width:120},
						       {html:"分数下限：",id:"t2",baseCls:"label_right",width:80},
						       {items:[fh4,vl2,fh2],baseCls:"component",width:120}
							] 
	                }]  
		      	}]  
		});
		
     	this.fileUpWin = new Ext.ux.Window({
     		title:"统计标准",	
     		height:140,
     		width:480,
     		tbar:{
     			cls:"ext_tabr",
     			items:["->",_b_cancel,"->",_b_save]
			},
			items:[this.search3]
     	});
     	this.fileUpWin.show(null,function(){},this);
    }
});
