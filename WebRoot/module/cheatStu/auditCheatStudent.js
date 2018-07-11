var lcid = "";
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
    	//单元格点击事件，控制按钮的禁用情况
    	var thiz = this;
    	this.grid.on("cellclick",function(grid,rowIndex,columnIndex, e){
    		var selected = thiz.grid.getStore().getAt(rowIndex);
		    lcid = selected.get("LCID");
		    var flag = selected.get("WJFLAG");
		    if(flag == "0" ){
	    		Ext.getCmp("tbar_audit").disabled = false;
	    	}else{
	    		Ext.getCmp("tbar_audit").disabled = true;
	    	}
    		if(columnIndex == 4){
		    	thiz.tabPanel.setActiveTab(thiz.CheatPanel);
  				thiz.CheatAGrid.$load({"lcid":lcid});
  				//thiz.initEditWindow();
    		}
		},this);
    },   
    initComponent :function(){
    	this.panel = this.createMainPanel();
    	this.CheatPanel= this.createCheatStuEditForm();
		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.panel,this.CheatPanel]   
        }); 
	},
	createMainPanel:function() {
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
			sm,
			{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   align:"center", sortable:true, dataIndex:"XQ"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME",
				renderer: function (data, metadata, record, rowIndex, columnIndex, store) { 
					  var name = store.getAt(rowIndex).get('EXAMNAME');  
					  //lcid = store.getAt(rowIndex).get('LCID');  
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
				}},
			{header: "状态",   align:"center", sortable:true, dataIndex:"WJZT"}			
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"违纪情况",
			tbar:[ 
			      "->",{xtype:"button",text:"审核",id:"tbar_audit",iconCls:"p-icons-passing",handler:this.auditCheat,scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			excelTitle:"违纪情况表",
			action:"CheatStudent_getExamRound.do",
			fields :["LCID","XN","XQ","EXAMNAME","EXAMTYPE","DWMC","CKDW","WJKSS","WJFLAG","WJZT"],
			border:false
		});
		//搜索区域
		var xnxq	= new Ext.ux.form.XnxqField({ width:210,id:"auxnxq_find",readOnly:true});
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
	createCheatStuEditForm:function(){
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "参考单位",   align:"center", sortable:true, dataIndex:"XXMC"},
			//{header: "等级",   align:"center", sortable:true, dataIndex:"NJMC"},
			//{header: "科目",   align:"center", sortable:true, dataIndex:"BJMC"},
			{header: "身份证件号",   align:"center", sortable:true, dataIndex:"SFZJH"},
			{header: "考号",   align:"center", sortable:true, dataIndex:"KSCODE"},
			{header: "姓名",   align:"center", sortable:true, dataIndex:"XM"},
			{header: "性别",   align:"center", sortable:true, dataIndex:"XB"},
			{header: "考试批次",   align:"center", sortable:true, dataIndex:"SUBJECTNAME"},
			{header: "违纪处理类型",   align:"center", sortable:true, dataIndex:"OPTTYPENAME"},
			{header: "扣除分数",   align:"center", sortable:true, dataIndex:"SCORE"},
			{header: "违纪情况",   align:"center", sortable:true, dataIndex:"WJQQ"},
			{header: "状态",   align:"center", sortable:true, dataIndex:"ZT"}
		];

		this.CheatAGrid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			tbar:[ 
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}
			     ],
			page:true,
			rowNumber:true,
			excel:true,
			excelTitle:"违纪情况表",
			region:"center",
			action:"CheatStudent_getListPage.do",
			fields :["XXMC","WJID","KSCODE","XJH","XM","XB","SUBJECTNAME","SCORE","OPTTYPENAME","WJQQ","NJMC","BJMC","KMID","OPTTYPE","SFZJH","ZT"],
			border:false
		});
		//Ext.getCmp("fh1").setVisible(false);
		//搜索区域
		var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel",width:210,readOnly:true});
    	var xmkhxjh_sel	= new Ext.ux.form.TextField({name:"xmkhxjh_sel",id:"xmkhxjh_sel",width:200});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectCheatStu,scope:this});
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
							columns: 6
						}, 
						baseCls:"table",
						items:[
							{html:"参考单位：",baseCls:"label_right",width:80},
							{items:[organ_sel],baseCls:"component",width:210},
							{html:"姓名/考号/身份证号：",baseCls:"label_right",width:150},
							{items:[xmkhxjh_sel],baseCls:"component",width:210},
							{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:210}
						] 
                    }]  
		       }]  
		});
		return new Ext.Panel({
	    		title:"录入违纪情况",
	    		region:"north",
	    		height:500,
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.CheatSearch,this.CheatAGrid]
	    });
    },
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"fit",items:[this.tabPanel]});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
    selectCheatStuInfo:function(){
    	var xn=Ext.getCmp('auxnxq_find').getCode();
    	this.grid.$load({"xnxqId":xn});
    },
    selectCheatStu:function(){
    	var organ_sel = this.CheatSearch.getForm().findField('organ_sel').getCodes();
    	var xmkhxjh_sel = Ext.getCmp('xmkhxjh_sel').getValue();
		this.CheatAGrid.$load({"lcid":lcid,"schools":organ_sel,"xmkhxjh":xmkhxjh_sel});
    },
    fanhui:function(){
  		this.tabPanel.setActiveTab(this.panel);
  		this.grid.$load();
    },
    auditCheat:function(){
    	//审核违纪
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var thiz = this;
    	var selectedCheat = this.grid.getSelectionModel().getSelections();
    	var dqlcid = selectedCheat[0].get("LCID");
    	Ext.MessageBox.show({
    			title:"消息",
    			msg:"您确定要审核吗?",
    			buttons:Ext.MessageBox.OKCANCEL,
    			icon:Ext.MessageBox.QUESTION,
    			fn:function(b){
    				if(b=='ok'){
    					JH.$request({
    						params:{'lcid':dqlcid},
    						action:"CheatStudent_auditCheatStudent.do",
    						handler:function(){
    							thiz.grid.$load();
    						}
    					})
    				}
    			}
    	})
    }
});

