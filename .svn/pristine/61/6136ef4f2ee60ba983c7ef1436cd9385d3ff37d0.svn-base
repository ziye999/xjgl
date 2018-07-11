var zpcjId = "";
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
		    var flag = selected.get("FLAG");
		    zpcjId = selected.get("ZPCJ_ID");
		    if(flag == "0" ){
	    		Ext.getCmp("tbar_audit").setDisabled(false);
	    	}else{
	    		Ext.getCmp("tbar_audit").setDisabled(true);
	    	}
    		if(columnIndex == 5){
		    	thiz.tabPanel.setActiveTab(thiz.printPanel);
  				var height=Ext.getCmp('dataPanelA').getHeight()-4;
		  		var iframe="<fieldset style='height:"+height+"; width:100%; border:0px; padding:0;'>"+
								"<iframe id='frmReportA' name='frmReportA' width='100%' height='"+height+
								"' src='photo_getPhotoListReport.do?ZPCJ_ID="+zpcjId+
								"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
							"</fieldset>";
		    	Ext.getDom('dataPanelA').innerHTML=iframe;
    		}
		},this);
    },   
   	initComponent :function(){
		this.panel = this.createMainPanel();
    	this.printPanel= this.createDataPanel();
		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.panel,this.printPanel]   
        }); 
	},
	createMainPanel:function() {
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
			sm,
			{header: "参考单位",   align:"center", sortable:true, dataIndex:"XXMC"},
			{header: "参考单位人数",   align:"center", sortable:true, dataIndex:"XXRS"},
			{header: "照片数量",   align:"center", sortable:true, dataIndex:"ZPSL"},
			{header: "照片包名称",   align:"center", sortable:true, dataIndex:"WJMC",
				renderer: function (data, metadata, record, rowIndex, columnIndex, store) { 
					var name = store.getAt(rowIndex).get('WJMC');  
					return "<a href='javascript:void(0);' >"+name+"</a>";  
				}},
			{header: "状态",   align:"center", sortable:true, dataIndex:"FLAG",
				renderer: function (data, metadata, record, rowIndex, columnIndex, store) {  
					var wjkss = store.getAt(rowIndex).get('FLAG');  
					if(wjkss==""){
						return "未上报";
					}else if(wjkss=="0"){
				      	return "已上报";
					}else if(wjkss=="1"){
						return "已审核";
					} 
			}}			
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"照片信息",
			tbar:[ 
			      "->",{xtype:"button",text:"审核",id:"tbar_audit",iconCls:"p-icons-passing",handler:this.auditPhoto,scope:this}
			     ],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			excelTitle:"照片信息表",
			action:"photo_getListPage.do",
			fields :["XXMC","XXRS","ZPSL","WJMC","WJFLAG","ZPCJ_ID","FLAG"],
			border:false
		});
		//搜索区域
		var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel",width:210,readOnly:true});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectPhotoUpdate,scope:this});
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
							{html:"组织单位：",baseCls:"label_right",width:120},
							{items:[organ_sel],baseCls:"component",width:210},
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
	createDataPanel:function(){
		var dataPanel=new Ext.Panel({
    		region:'center',
    		border:true,
   			id:"dataPanelA",
   			html:"",
   			bodyStyle:""
   		});
    	return new Ext.Panel({
    		border:true,
    		title:"打印预览",
    		region:"north",
    		layout:"border",
    		tbar:[ 
    		      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhuiMain,scope:this}
				],
			items:[dataPanel]			
    	});
    },
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"fit",items:[this.tabPanel]});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
    selectPhotoUpdate:function(){
    	//查询照片更新包
    	var school = this.search.getForm().findField("organ_sel").getCodes();
    	this.grid.$load({"school":school});
    },
    fanhuiMain:function(){
    	//点击返回时，将后台解压的zip包删除掉
    	JH.$request({
			params:{"zpcjId":zpcjId},
			action:"photo_deleteZip.do",
			handler:function(){}
		})
		this.tabPanel.setActiveTab(this.panel);
  		this.grid.$load();
    },
    auditPhoto:function(){
    	//审核照片
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var thiz = this;
    	var selectedCheat = this.grid.getSelectionModel().getSelections();
    	var zpcjId = selectedCheat[0].get("ZPCJ_ID");
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"您确定要审核吗?",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,
    		fn:function(b){
    			if(b=='ok'){
    				JH.$request({
    					params:{"zpcjId":zpcjId},
    					action:"photo_auditPhotoUpdate.do",
    					handler:function(){
    						thiz.grid.$load();
    					}
    				})
    			}
    		}
    	})
    }
});
