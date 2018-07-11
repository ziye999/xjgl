var kxx = "";
var lcid = "";
var bmlcid = "";
var xsjbxxid ="";
var imagepath1 = "";
var xxmc1 = "";
var sf="";
var pzpath="";
var pz="";
var zkdw1="";
var bmlcid2="";
var sh="";
var sl="";
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
		this.mainPanel = this.createMainPanel();
   		this.chouQuPanel = this.createChouQuForm();
   		this.invoicePanel = this.createCheckInvoiceForm();
   		this.editForm   = this.createExamroundForm();
   		this.Form1   = this.createForm1();
   		this.editWindow = this.createWindow();
   		this.editWindow.add(this.editForm);
   		this.window = this.createWindow1();
   		this.window.add(this.Form1);
   		this.ChouquWin = this.createChouquWindow();
   		this.updateForm = this.createUpdateForm();
   		this.updateWindow = this.createUpdateWindow();
   		this.updateWindow.add(this.updateForm);
   		this.certificatesFrom = this.createCertificatesForm();
   		this.certificatesWindow = this.createCertificatesWindow();
   		this.certificatesWindow.add(this.certificatesFrom);
   		this.selectCertificatesFrom = this.createSelectCertificatesForm();
   		this.selectCertificatesWindow = this.createSelectCertificatesWindow();
   		this.selectCertificatesWindow.add(this.selectCertificatesFrom);
   		this.FangdaForm = this.createFangdaForm();
   		this.FangdaWindow = this.createFangdaWindow();
   		this.FangdaWindow.add(this.FangdaForm);
   		this.invoiceForm = this.createInvoiceForm();
   		this.invoiceWindow = this.createInvoiceWindow();
 		this.invoiceWindow.add(this.invoiceForm);
 		this.updateinvoiceForm = this.createupdateInvoiceForm();
        this.updateinvoiceWindow = this.createupdateInvoiceWindow();
        this.updateinvoiceWindow.add(this.updateinvoiceForm);
   	
   		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.mainPanel,this.chouQuPanel,this.invoicePanel]   
        }); 
	},
	/** 初始化界面 **/
    initFace:function(){
    	this.addPanel(this.tabPanel);
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
    createupdateInvoiceForm:function(){
        var NAME = new Ext.form.TextField({id:"NAME",name:"NAME",maxLength:200, width:200});
        var DUTY = new Ext.form.TextField({id:"DUTY",name:"DUTY",maxLength:200, width:200});
        var ADDRESS = new Ext.form.TextField({id:"ADDRESS",name:"ADDRESS",maxLength:200, width:200});
        var TELLPHONE = new Ext.form.TextField({id:"TELLPHONE",name:"TELLPHONE",maxLength:200, width:200});
        var BANKNAME = new Ext.form.TextField({id:"BANKNAME",name:"BANKNAME",maxLength:200, width:200});
        var BANKNUM = new Ext.form.TextField({id:"BANKNUM",name:"BANKNUM",maxLength:200, width:200});
        var PHONE = new Ext.form.TextField({id:"PHONE",name:"PHONE",maxLength:200, width:200});
        var EMAIL = new Ext.form.TextField({id:"EMAIL",name:"EMAIL",maxLength:200, width:200});
        var REMARK = new Ext.form.TextField({id:"REMARK",name:"REMARK",maxLength:200, width:200});
        var NUMBER = new Ext.form.TextField({id:"NUMBER",name:"NUMBER",maxLength:200, width:200});

        var panel = new Ext.Panel({
            xtype:"panel",
            layout:"table",
            baseCls:"table",
            layoutConfig: {
                columns: 4
            },
            items:[
                {html:"单位名称：",baseCls:"label_right",width:170},
                {html:"<font class='required'>*</font>",items:[NAME],baseCls:"component",width:230},
                {html:"税务号：",baseCls:"label_right",width:170},
                {html:"<font class='required'>*</font>",items:[DUTY],baseCls:"component",width:230},
                {html:"单位地址：",baseCls:"label_right",width:170},
                {html:"<font class='required'>*</font>",items:[ADDRESS],baseCls:"component",width:230},
                {html:"联系电话：",baseCls:"label_right",width:170},
                {html:"<font class='required'>*</font>",items:[TELLPHONE],baseCls:"component",width:230},
                {html:"电子邮箱：",baseCls:"label_right",width:170},
                {html:"<font class='required'>*</font>",items:[EMAIL],baseCls:"component",width:230},
                {html:"开户行：",baseCls:"label_right",width:170},
                {items:[BANKNAME],baseCls:"component",width:230},
                {html:"银行账号：",baseCls:"label_right",width:170},
                {items:[BANKNUM],baseCls:"component",width:230},
                {html:"推送手机：",baseCls:"label_right",width:170},
                {html:"<font class='required'>*</font>",items:[PHONE],baseCls:"component",width:230},
                {html:"备注：",baseCls:"label_right",width:170},
                {items:[REMARK],baseCls:"component",width:230},
                {html:"报名人数：",baseCls:"label_right",width:170},
                {html:"<font class='required'>*</font>",items:[NUMBER],baseCls:"component",width:230},
            ]
        });


        return new Ext.ux.FormPanel({
            frame:false,
            defaults:{anchor:"100%"},
            items:[
                {layout:'form',  width:1000,xtype:"fieldset",title:"修改发票",items:[panel]},
            ]});
    },
    createupdateInvoiceWindow:function(){
        var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.updateInvoice,scope:this});

        var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.updateinvoiceWindow.hide()},scope:this});
        return new Ext.ux.Window({
            width:1000,
            height:300,
            tbar:{
                cls:"ext_tabr",
                items:[
                    "->",cancel
                    ,"->",save
                ]
            },
            listeners:{
                hide:function(){
                    this.updateinvoiceForm.form.reset();
                },scope:this},
            items:[this.updateinvoiceForm],
            title:"修改发票"});
    },
    createInvoiceForm:function(){
    	var dwname = new Ext.form.TextField({id:"dwname",name:"dwname",maxLength:200, width:200});
    	var dpno = new Ext.form.TextField({id:"dpno",name:"dpno",maxLength:200, width:200});
    	var dwaddress = new Ext.form.TextField({id:"dwaddress",name:"dwaddress",maxLength:200, width:200});
    	var lxrdh = new Ext.form.TextField({id:"lxrdh",name:"lxrdh",maxLength:200, width:200});
    	var email = new Ext.form.TextField({id:"email",name:"email",maxLength:200, width:200});
    	var bankName = new Ext.form.TextField({id:"bankName",name:"bankName",maxLength:200, width:200});
    	var bankNum = new Ext.form.TextField({id:"bankNum",name:"bankNum",maxLength:200, width:200});
    	var phone = new Ext.form.TextField({id:"phone",name:"phone",maxLength:200, width:200});
		var remark = new Ext.form.TextField({id:"remark",name:"remark",maxLength:200, width:200});
		var number = new Ext.form.TextField({id:"number",name:"number",maxLength:200, width:200});

    	var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4
			},
			items:[		
			       {html:"单位名称：",baseCls:"label_right",width:170},
			       {html:"<font class='required'>*</font>",items:[NAME],baseCls:"component",width:230},
			       {html:"税务号：",baseCls:"label_right",width:170},
			       {html:"<font class='required'>*</font>",items:[DUTY],baseCls:"component",width:230},
			       {html:"单位地址：",baseCls:"label_right",width:170},
			       {html:"<font class='required'>*</font>",items:[ADDRESS],baseCls:"component",width:230},
			       {html:"联系电话：",baseCls:"label_right",width:170},
			       {html:"<font class='required'>*</font>",items:[TELLPHONE],baseCls:"component",width:230},
			       {html:"电子邮箱：",baseCls:"label_right",width:170},
			       {html:"<font class='required'>*</font>",items:[EMAIL],baseCls:"component",width:230},
			       {html:"开户行：",baseCls:"label_right",width:170},
			       {items:[BANKNAME],baseCls:"component",width:230},
			       {html:"银行账号：",baseCls:"label_right",width:170},
			       {items:[BANKNUM],baseCls:"component",width:230},
			       {html:"推送手机：",baseCls:"label_right",width:170},
			       {html:"<font class='required'>*</font>",items:[PHONE],baseCls:"component",width:230},
			       {html:"备注：",baseCls:"label_right",width:170},
			       {items:[REMARK],baseCls:"component",width:230},
			       {html:"报名人数：",baseCls:"label_right",width:170},
			       {html:"<font class='required'>*</font>",items:[NUMBER],baseCls:"component",width:230},
			       ]		
		});
		
		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
					{layout:'form',  width:1000,xtype:"fieldset",title:"申请发票",items:[panel]},
			]});
    },
    createInvoiceWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.saveInvoice,scope:this});
    	
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.invoiceWindow.hide()},scope:this});
    	return new Ext.ux.Window({
		 	width:1000,
			height:300,
		 	tbar:{
				cls:"ext_tabr",
				items:[ 
				       "->",cancel
				       ,"->",save
				  ]
			},
			listeners:{
		 		hide:function(){
		 			this.invoiceForm.form.reset();
		 		},scope:this},
     		items:[this.invoiceForm],
		 	title:"申请发票"});	
    },
    createFangdaForm:function(){	
    	
		//缴费凭证
		var jfpz1 = new Ext.BoxComponent({autoEl: {tag: 'img',src: 'img/basicsinfo/mrzp_img.jpg',id:"jfpz1"},id:"jfpz1",width:900,height:480});
	
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4
			},
			items:[		
					{items:[jfpz1],baseCls:"component",width:600,rowspan:8}

			       ]		
		});
		
		
		return new Ext.ux.FormPanel({
			frame:false,
			
			  collapsible: true,
			items:[
					{layout:'form',  width:1200,xtype:"fieldset",title:"凭证查看",items:[panel]},
			]});					
    },
    createFangdaWindow:function(){
    	
		
		return new Ext.ux.Window({
			 	width:1350,
				height:600,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	
					 
					  ]
				},
				listeners:{
			 		hide:function(){
			 			this.FangdaForm.form.reset();
			 		},scope:this},
	     		items:[this.FangdaForm],
			 	title:"凭证大图查看"});		
    },
    createMainPanel:function(){
    	//显示考试轮次主页面
    	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true,
			listeners:{
    			"selectionchange":function(sm){
    				var str = sm.getSelections();
    				if(str[0].get("fpzt")=="已申请"||str[0].get("fpzt")=="已审核"){
    					Ext.getCmp('tjfpsq').setDisabled(true);
    					Ext.getCmp('sqfp').setDisabled(true);
    				}
    				if(str[0].get("SHFLAG")=="审核通过"){
                        Ext.getCmp('sqfp').setDisabled(false);
					}
    				
    				if(str[0].get("SHFLAG")=="已上报"||str[0].get("SHFLAG")=="审核通过"||str[0].get("SHFLAG")=="已缴费"||str[0].get("SHFLAG")=="审核不通过"){
    					Ext.getCmp('sb').setDisabled(true);
    					Ext.getCmp('dr').setDisabled(true);
    					Ext.getCmp('dr1').setDisabled(true);
    					Ext.getCmp('scjf').setDisabled(true);
    					if(str[0].get("SHFLAG")=="已上报"||str[0].get("SHFLAG")=="审核不通过"){
    						Ext.getCmp('scjf').setDisabled(false);
    					}
    				}else{
    					Ext.getCmp('sb').setDisabled(false);
    					Ext.getCmp('dr').setDisabled(false);
    					Ext.getCmp('dr1').setDisabled(false);

    					Ext.getCmp('dr2').setDisabled(false);
    					Ext.getCmp('xz').setDisabled(false);    
    					Ext.getCmp('scjf').setDisabled(false); 



    				}
    			}
    		}});
    
		var cm = [
		    sm,
			{header: "年度",   		align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   		align:"center", sortable:true, dataIndex:"XQ"},
			{header: "考试名称",  	align:"center", sortable:true, dataIndex:"EXAMNAME"},
			{header: "组织单位",   	align:"center", sortable:true, dataIndex:"MC"},
			{header: "参考单位",   	align:"center", sortable:true, dataIndex:"CKDW"},
			{header: "考生数量",   	align:"center", sortable:true, dataIndex:"SL"},
			{header: "上报审核缴费状态",   align:"center", sortable:true, dataIndex:"SHFLAG"},
			{header: "有照片",   align:"center", sortable:true, dataIndex:"YZP"},
			{header: "没照片",   align:"center", sortable:true, dataIndex:"MZP"},
			{header: "审核原因",   align:"center", sortable:true, dataIndex:"SHSM"},
			{header: "发票状态",   align:"center", sortable:true, dataIndex:"fpzt"}
		];		
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"考生报名-生成考生名单",
			tbar:{

			  	cls:"ext_tabr",
				items:[ 
				   "->",{xtype:"button",text:"查看发票申请信息",id:"ckfp",iconCls:"p-icons-upload",handler:this.checkInvoice,scope:this}
			      ,"->",{xtype:"button",text:"申请发票",id:"sqfp",iconCls:"p-icons-upload",handler:this.shenqingInvoice,scope:this}
			      ,"->",{xtype:"button",text:"提交发票申请",id:"tjfpsq",iconCls:"p-icons-upload",handler:this.commitInvoice,scope:this}
			      ,"->",{xtype:"button",text:"查看凭证",id:"ckjf",iconCls:"p-icons-upload",handler:this.selectCertificates,scope:this}
			      ,"->",{xtype:"button",text:"上传缴费凭证",id:"scjf",iconCls:"p-icons-update",handler:this.uploadCertificates,scope:this}

			      ,"->",{xtype:"button",text:"上报",id:"sb",iconCls:"p-icons-upload",handler:this.updateExaminee,scope:this}
			      ,"->",{xtype:"button",text:"查看考生信息",id:"cq",iconCls:"p-icons-update",handler:this.addExamSection,scope:this}
//			      ,"->",{xtype:"button",text:"关联照片",iconCls:"p-icons-add",handler:this.glzp,scope:this}
			      ,"->",{xtype:"button",text:"导入照片信息",id:"dr1",iconCls:"p-icons-update",handler:this.exportK,scope:this}
			      ,"->",{xtype:"button",text:"导入考生信息",id:"dr",iconCls:"p-icons-upload",handler:this.exportKs,scope:this}
//			      ,{xtype:"button",text:"导入补考考生",id:"dr2",iconCls:"p-icons-upload",handler:this.exportBkKs,scope:this}
			      ,"->",{xtype:"button",text:"下载模板",id:"xz",iconCls:"p-icons-download",handler:this.downloadTemplate,scope:this}
			    ]},
			    page:true,
			    rowNumber:true,
			    region:"center",
			    excel:true,
			    action:"signup_getListPage.do",
			    fields :["BMLCID","XN","XQ","EXAMNAME","MC","CKDW","SL","DWID","DWTYPE","SHFLAG","YZP","MZP","SHSM","fpzt"],
			    border:false
		});
	
		//搜索区域		
		var xn_find=new Ext.ux.form.XnxqField({width:180,id:"exrxn_find",width:210,readOnly:true});
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
						columns: 3
					}, 
					baseCls:"table",
					items:[
							{html:"年度：",baseCls:"label_right",width:120},
							{items:[xn_find],baseCls:"component",width:210},
							{layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
						] 
				}]  
			}]  
	    })
		return new Ext.Panel({layout:"border",region:"center",items:[this.search,this.grid]})
    },
    createCheckInvoiceForm:function(){
    	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
    	var cm = [sm,
		          {header: "单位名称",   	align:"center", sortable:true, dataIndex:"NAME"},
		          {header: "单位地址",   	align:"center", sortable:true, dataIndex:"ADDRESS"},
		          {header: "联系电话",   	align:"center", sortable:true, dataIndex:"TELLPHONE"},
		          {header: "电子邮箱",   	align:"center", sortable:true, dataIndex:"EMAIL"},
		          {header: "开户行",   	align:"center", sortable:true, dataIndex:"BANKNAME"},
		          {header: "银行账号",   	align:"center", sortable:true, dataIndex:"BANKNUM"},
		          {header: "税务号",   	align:"center", sortable:true, dataIndex:"DUTY"},
		          {header: "报名人数",   	align:"center", sortable:true, dataIndex:"NUMBER"},
		          {header: "推送手机",   	align:"center", sortable:true, dataIndex:"PHONE"},
		          {header: "备注",   	align:"center", sortable:true, dataIndex:"REMARK"},
		          {header: "审核状态",   	align:"center", sortable:true, dataIndex:"STATE"}   
		         ];
		
		this.grid3 = new Ext.ux.GridPanel({
			cm:cm,
			title:'发票申请详情',
			excel:true,
			page:true,
			rowNumber:true,
			tbar:[ 
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.returnMainPanel,scope:this}
			      ,"->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteInvoice,scope:this}
			      ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateInvoices,scope:this}
			      ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:this.selectInvoice,scope:this}
			   ],
			region:"center",
			action:'upload_getPage.do',
			fields:["ID","NAME","DUTY","ADDRESS","BANKNAME","BANKNUM","PHONE","EMAIL","REMARK","NUMBER","STATE","TELLPHONE"],
			border:false
		});		
	    return new Ext.Panel({layout:"border",region:"center",items:[this.grid3]})
    },
    createChouQuForm:function(){
    	var sfz		= new Ext.ux.form.TextField({name:"sfz",id:"sfz",width:180});
    	var xm		= new Ext.ux.form.TextField({name:"xm",id:"name",width:180});
    	var cx 			= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.maingridExamStu,scope:this});
		var cz 			= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.mainsearch.getForm().reset()},scope:this});
		var zp = new Ext.form.ComboBox({
			id:'zp',
			mode: 'local', 
			triggerAction: 'all',   
			editable:false,
			width:160,
			store: new Ext.data.ArrayStore({
				id: 0,
				fields: ['value','text'],
			    data: [['1', '全部'], ['2', '已上传'],['3', '未上传']]
			}),
			valueField: 'value',
			displayField: 'text'
		});
		this.mainsearch = new Ext.form.FormPanel({
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
						columns: 4
					}, 
					baseCls:"table",
					items:[
					       {html:"身份证件号：",baseCls:"label_right",width:170},
					       {items:[sfz],baseCls:"component",width:200},
					       {html:"姓名：",baseCls:"label_right",width:170},
					       {items:[xm],baseCls:"component",width:200},
					       {html:"是否上传照片：",baseCls:"label_right",width:170},
					       {items:[zp],baseCls:"component",width:200},
					       {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
					      ] 
                  	}]  
		      	}]  
	    	})
	
    	//选择轮次点击抽取后的主页面

//		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});

		var cm = [sm,
		          {header: "组考单位",   	align:"center", sortable:true, dataIndex:"SSZGJYXZMC"},
		          {header: "参考单位",   	align:"center", sortable:true, dataIndex:"XXMC"},
		          {header: "姓名",   	align:"center", sortable:true, dataIndex:"XM"},
		          {header: "身份证号",   	align:"center", sortable:true, dataIndex:"SFZJH"},
		          {header: "性别",   	align:"center", sortable:true, dataIndex:"xbm"},
		          {header: "民族",   	align:"center", sortable:true, dataIndex:"MZM"},
		          {header: "文化程度",   	align:"center", sortable:true, dataIndex:"WHCD"},
		          {header: "政治面貌",   	align:"center", sortable:true, dataIndex:"ZZMM"},
		          {header: "专业",   	align:"center", sortable:true, dataIndex:"ZY"},
		          {header: "职务",   	align:"center", sortable:true, dataIndex:"ZW"},
		          {header: "执法类别",   	align:"center", sortable:true, dataIndex:"zflx"},
		          {header: "执法范围",   	align:"center", sortable:true, dataIndex:"zffw"},
		          {header: "发证机关",   	align:"center", sortable:true, dataIndex:"fzdw"},
		          {header: "备注",   	align:"center", sortable:true, dataIndex:"remark"},
		         ];
		
		this.grid2 = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:'考生信息表',
			excel:true,
			page:true,
			excel:true,
			rowNumber:true,
			tbar:[ 
			      
			      "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.returnMainPanel,scope:this}
			      ,"->",{xtype:"button",text:"删除",id:"sc",iconCls:"p-icons-delete",handler:this.deleteRound,scope:this}

			      ,"->",{xtype:"button",text:"修改",id:"xg",iconCls:"p-icons-update",handler:this.update,scope:this}
//			      ,"->",{xtype:"button",text:"增加",id:"zj",iconCls:"p-icons-add",handler:function(){this.showEidtWindow();Ext.getCmp("img").getEl().dom.src="img/basicsinfo/mrzp_img.jpg";Ext.getCmp("imagepath").setValue("");},scope:this}
			      ,"->",{xtype:"button",text:"增加",id:"zj",iconCls:"p-icons-add",handler:function(){this.showEidtWindow();Ext.getCmp("img").getEl().dom.src="img/basicsinfo/mrzp_img.jpg";Ext.getCmp("imagepath").setValue("");},scope:this}
			      ,"->",{xtype:"button",text:"查看",id:"ck",iconCls:"p-icons-update",handler:this.selectSFZJH,scope:this}

			   ],
			region:"center",
			action:'sign_getPage.do',
			fields:["XS_JBXX_ID","SSZGJYXZMC","XM","xbm","SFZJH","XXMC","MZM","ZZMM","ZY","ZW","WHCD","zflx","zffw","fzdw","remark","bmlcid","dm"],
			border:false
		});		
	    return new Ext.Panel({layout:"border",region:"center",items:[this.mainsearch,this.grid2]})
    },
    uploadCertificates:function(){
    	alert("建议使用谷歌，360极速，火狐浏览器上传，凭证只能上传一张图片或一个zip格式的压缩包，且上传后将不能再增加、修改考生");
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    	  	Ext.MessageBox.alert("消息","请选一个轮次上传缴费凭证！");
    	  	return;
    	}
    	
    	var sbshjf = selectedBuildings[0].get("SHFLAG");
    	if(sbshjf=="已缴费"||sbshjf=="审核通过"){
      		Ext.MessageBox.alert("错误","已缴费或者审核通过不能上传缴费凭证");
      		return;
      	}
    	if(sbshjf=="未上报"){
    		Ext.MessageBox.alert("错误","未上报不能上传缴费凭证");
      		return;
    	}
    	
    	var bmlcid = selectedBuildings[0].get("BMLCID");
    	var thiz = this;
     				thiz.certificatesWindow.show(null,function(){
     					thiz.certificatesFrom.$load({
     		      			params:{'bmlcid':bmlcid},
     		      			action:"upload_getKsrs.do",
     		      			handler:function(form,result,scope){      	
     		    				form.findField("bmrs").setValue(result.data.SL);
     		    				form.findField("jfbz").setValue(result.data.JFBZ+"元/人");
     		    				form.findField("yjje").setValue(result.data.JFJE);
     		    				form.findField("lxrname").setValue(result.data.LXR);
     		    				form.findField("lxdhno").setValue(result.data.LXDH);
     		      			}		
     		      		})
     		      	},this)
    	
    },
    maingridExamStu:function() {
    	var sfz = Ext.getCmp('sfz').getValue();
    	var xm = Ext.getCmp('name').getValue();
    	var zp = Ext.getCmp('zp').getValue();
    	this.grid2.$load({"limit":this.grid2.getBottomToolbar().pageSize,"sfzjh":sfz,"xm":xm,"zp":zp});
    },
    createSelectCertificatesForm:function(){
    	var ckbmrs = new Ext.form.TextField({id:"ckbmrs",name:"ckbmrs",readOnly:true,maxLength:200, width:200});
    	var ckjfbz = new Ext.form.TextField({id:"ckjfbz",name:"ckjfbz",readOnly:true,maxLength:200, width:200});
    	var ckyjje = new Ext.form.TextField({id:"ckyjje",name:"ckyjje",readOnly:true,maxLength:200, width:200});
    	var cklxr = new Ext.form.TextField({id:"cklxr",name:"cklxr",readOnly:true,maxLength:200, width:200});
    	var cklxdh = new Ext.form.TextField({id:"cklxdh",name:"cklxdh",readOnly:true,maxLength:200, width:200});
    	var ckjfpz = new Ext.BoxComponent({autoEl: {tag: 'img',src: '',id:"ckjfpz"},id:"ckjfpz",width:610,height:130});
    	var fangda = new Ext.Button({x : 19,y : -10,cls : "base_btn",id :"fangda",text : "查看大图",handler:this.ckp,scope : this});
    	var ckjfpzpath = new Ext.form.TextField({id:"ckjfpzpath",name:"ckjfpzpath",hidden:true});
    	var download = new Ext.Button({id:"download",x : 19,y : -10,cls : "base_btn",text : "下载凭证",handler:this.downloadZip,scope : this});
    	var panel1 = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[
				{html:"报名人数：",baseCls:"label_right",width:150},
				{items:[ckbmrs],baseCls:"component",width:230},
				{html:"缴费标准：",baseCls:"label_right",width:150},
				{items:[ckjfbz],baseCls:"component",width:230},
				{html:"应缴费金额:",baseCls:"label_right",width:150},
				{items:[ckyjje],baseCls:"component",width:230},
				{html:"联系人：",baseCls:"label_right",width:150},
				{items:[cklxr],baseCls:"",width:230},
				{html:"联系电话：",baseCls:"label_right",width:150},
				{items:[cklxdh],baseCls:"",width:230},
				{html:"查看凭证： ",baseCls:"label_right",width:150},
				{items:[fangda],baseCls:"",width:230},
				{html:"缴费凭证：",baseCls:"label_right",width:150,rowspan:6},
				{items:[ckjfpz,download],baseCls:"component",width:230,rowspan:6},
								
				
				
			]		
		});		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
				{layout:'form',width:950, xtype:"fieldset",title:"上传缴费凭证",items:[panel1]}
			]});
    },
    createSelectCertificatesWindow:function(){
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.selectCertificatesWindow.hide()},scope:this});
		
		return new Ext.ux.Window({
			width:980,
		 	height:360,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
					  ]
					
				},
				listeners:{
			 		hide:function(){
			 			this.createSelectCertificatesForm.form.reset();
			 		},scope:this},
	     		items:[this.createSelectCertificatesForm],
			 	title:"查看缴费凭证"});		
	
    },
    //上传缴费凭证
    createCertificatesForm:function(){
    	var bmrs = new Ext.form.TextField({id:"bmrs",name:"bmrs",readOnly:true,maxLength:200, width:200});
    	var jfbz = new Ext.form.TextField({id:"jfbz",name:"jfbz",readOnly:true,maxLength:200, width:200});
    	var yjje = new Ext.form.TextField({id:"yjje",name:"yjje",readOnly:true,maxLength:200, width:200});
    	var scjfpz = new Ext.Button({x : 17,y : -10,cls : "base_btn",text : "上传凭证",handler:this.saveCertificates,scope : this});
    	var jfpz = new Ext.BoxComponent({autoEl: {tag: 'img',id:"jfpz"},id:"jfpz",width:228,height:130});
    	var jfpzpath = new Ext.form.TextField({id:"jfpzpath",name:"jfpzpath",hidden:true});
    	var lxrname = new Ext.form.TextField({id:"lxrname",name:"lxrname",maxLength:200, width:200});
    	var lxdhno = new Ext.form.TextField({id:"lxdhno",name:"lxdhno",maxLength:200, width:200});
    	var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[
				{html:"报名人数：",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[bmrs],baseCls:"component",width:230},
				{html:"缴费标准：",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[jfbz],baseCls:"component",width:230},
				{html:"应缴费金额:",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[yjje],baseCls:"component",width:230},
				{html:"联系人：",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[lxrname],baseCls:"component",width:230},
				{html:"联系电话：",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[lxdhno],baseCls:"component",width:230},
				{html:"上传缴费：",baseCls:"label_right",width:150},
				{items:[scjfpz],baseCls:"",width:230},
				{html:"缴费凭证：",baseCls:"label_right",width:150,rowspan:6},
				{items:[jfpz],baseCls:"component",width:230,rowspan:6},
								
				
				
			]		
		});		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
				{layout:'form',width:950, xtype:"fieldset",title:"上传缴费凭证",items:[panel]}
			]});
    },
    //修改数据
    createUpdateForm:function(){	
    	var bmlcid1 = bmlcid1;
    	var usercode3  = new Ext.form.TextField({id:"userInfo.usercode3",maxLength:200, width:200,hidden:true});
    	var xsjbxxid3  = new Ext.form.TextField({id:"xsjbxxid3",maxLength:200, width:200,hidden:true});
    	
    	var organ_self = new Ext.ux.form.OrganField({name:"organ_self",width:200,singleSelection:true,allowBlank:false,readOnly:true}); 
    	var sfzjh3  = new Ext.form.TextField({name:"sfzjh",id:"sfzjh3",maxLength:200, width:200,allowBlank:false});
		var xm3 = new Ext.form.TextField({id:"xm3",name:"xm3",maxLength:200, width:200,allowBlank:false});
		var ZY3 = new Ext.form.TextField({id:"ZY3",name:"ZY",maxLength:200, width:200,allowBlank:false});
		var loginpwd3  = new Ext.form.TextField({id:"userInfo.loginpwd3",maxLength:200, width:200,maxLength:200, width:200,allowBlank:false,inputType:'password'});
		var MZM3 = new Ext.form.TextField({id:"MZM3",name:"MZM",hidden:true});

		var MZ3	= new Ext.ux.MzCombox({
			name:"mz",
			dictCode:"zd_mz",
			value:"01",
			width:200,
			allowBlank:false,
			blankText:"民族必须选！",
			listeners:{ 
				select:function() {
					Ext.getCmp("MZM3").setValue(this.getValue());
				}  
			}
		});
		
		var FZDWM3 = new Ext.form.TextField({id:"FZDWM3",name:"fzdw",hidden:true});
		var FZDW3 = new Ext.ux.FzdwCombox({
			name:"fz",
			dictCode:"zd_fzdw",
			value:"1",
			width:200,
			allowBlank:false,
			blankText:"发证单位必须选！",
			listeners:{ 
				select:function() {
					Ext.getCmp("FZDWM3").setValue(this.getValue());
				}  
			}
		});
		var ZW3 = new Ext.form.TextField({id:"ZW3",name:"ZW",maxLength:200, width:200,allowBlank:false});
		var zflb3 = new Ext.ux.ZflbCombox({
			name:"zfl",
			dictCode:" zd_zflb",
			value:"01",
			width:200,
			allowBlank:false,
			blankText:"执法类型必须选！",
			listeners:{ 
				select:function() {
					Ext.getCmp("zflx3").setValue(this.getValue());
				}  
			}
		});
		var zflx3 = new Ext.form.TextField({id:"zflx3",name:"zflx",hidden:true});
	
		var zffw3 = new Ext.form.TextField({id:"zffw3",name:"zffw",maxLength:200, width:200,allowBlank:false});
		var XXMC3 = new Ext.form.TextField({id:"XXMC3",name:"XXMC",maxLength:200, width:200,allowBlank:false,readOnly:true});
		var remark3 = new Ext.form.TextField({name:"remark",id:"remark3",maxLength:200, width:200});
		var ZZMM3 =new Ext.form.TextField({id:"ZZMM3",name:"ZZMM",hidden:true});
		var zzmm3 = new Ext.ux.ZzmmCombox({
			name:"ZZM",
			dictCode:"zd_zzmm",
			value:"01",
			width:200,
			allowBlank:false,
			blankText:"政治面貌必须选！",
			listeners:{ 
				select:function() {
					Ext.getCmp("ZZMM3").setValue(this.getValue());
				}  
			}
		});
		var WHCD3 =new Ext.form.TextField({id:"WHCD3",name:"WHCD",hidden:true});
		var whcd3 = new Ext.ux.WhcdCombox({
			name:"whc",
			dictCode:"zd_xd",
			value:"01",
			width:200,
			allowBlank:false,
			blankText:"文化程度必须选！",
			listeners:{ 
				select:function() {
					Ext.getCmp("WHCD3").setValue(this.getValue());
				}  
			}
		});
	
 
		var sex3 = new Ext.form.ComboBox({
   				name:'sex3',
   				mode: 'local', 
			    triggerAction: 'all',   
			    editable:false,
			    width:200,
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			                 'value',
			                 'text'
			                ],
			        data: [['1', '男'], ['2', '女']]
			    }),
			    valueField: 'value',
			    displayField: 'text'
			});
		var imagepath3 = new Ext.form.TextField({id:"imagepath3",name:"imagepath",hidden:true});
		var imagebox3 =	new Ext.BoxComponent({autoEl: {tag: 'img',src: 'img/basicsinfo/mrzp_img.jpg',id:"imgPath3"},id:"img3",width:110,height:130});
		var upload3 = new Ext.Button({x : 17,y : -10,cls : "base_btn",text : "上传照片",handler:this.saveUpload1,scope : this});
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[
				{html:"姓名：",baseCls:"label_right",width:212},
				{html:"<font class='required'>*</font>",items:[xm3],baseCls:"component",width:230},
				{html:"照片：",baseCls:"label_right",width:212,rowspan:3},
				{items:[imagebox3],baseCls:"component",width:106,rowspan:3},
				{html:"性别:",baseCls:"label_right",width:212},
				{html:"<font class='required'>*</font>",items:[sex3],baseCls:"component",width:230},	
				{html:"文化程度：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'>*</font>",items:[WHCD3,whcd3],baseCls:"component",width:230,colspan:2},
				
				{html:"身份证号：",baseCls:"label_right",width:212},
				{html:"<font class='required'>*</font>",items:[sfzjh3],baseCls:"component",width:230},				
				{html:"",baseCls:"label_right",width:212},
				{items:[upload3],baseCls:"",width:318},
				{html:"专业：",baseCls:"label_right",width:212},
				{html:"<font class='required'>*</font>",items:[ZY3],baseCls:"component",width:230},	
				
				{html:"民族：",baseCls:"label_right",width:212},
				{html:"<font class='required'>*</font>",items:[MZM3,MZ3],baseCls:"component",width:230,colspan:2},
				{html:"工作单位：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'>*</font>",items:[XXMC3],baseCls:"component",width:230},
				{html:"政治面貌：",baseCls:"label_right",width:212},
				{html:"<font class='required'>*</font>",items:[ZZMM3,zzmm3],baseCls:"component",width:230,colspan:2},
				{html:"职务：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'>*</font>",items:[ZW3],baseCls:"component",width:230},
				{html:"执法类型：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'>*</font>",items:[zflx3,zflb3],baseCls:"component",width:230,colspan:2},
				{html:"执法范围：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'>*</font>",items:[zffw3],baseCls:"component",width:230},
				{html:"发证机关：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'>*</font>",items:[FZDWM3,FZDW3],baseCls:"component",width:230},
				{html:"备注：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'></font>",items:[remark3],baseCls:"component",width:230},
				{items:[usercode3],baseCls:"component",width:230,hidden:true}
			]		
		});		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
				{layout:'form',width:950, xtype:"fieldset",title:"考生信息管理",items:[panel]}
			]});		
    },

    

    //添加数据
    createExamroundForm:function(){	
    	var bmlcid1 = bmlcid1;
    	var usercode  = new Ext.form.TextField({id:"userInfo.usercode",maxLength:200, width:200,hidden:true});
    	var xsjbxxid  = new Ext.form.TextField({id:"xsjbxxid",maxLength:200, width:200,hidden:true});
    	
    	var organ_self = new Ext.ux.form.OrganField({name:"organ_self",width:200,singleSelection:true,allowBlank:false,readOnly:true}); 
    	var sfzjh  = new Ext.form.TextField({name:"sfzjh",id:"sfzjh",maxLength:200, width:200,allowBlank:false,regex:/(^\d{15}$)|(^\d{17}([0-9]|X)$)/,regexText:"输入的身份证号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X"});
		var xm = new Ext.form.TextField({id:"xm",maxLength:200, width:200,allowBlank:false});
		var ZY = new Ext.form.TextField({id:"ZY",maxLength:200, width:200,allowBlank:false});
		var loginpwd  = new Ext.form.TextField({id:"userInfo.loginpwd",maxLength:200, width:200,maxLength:200, width:200,allowBlank:false,inputType:'password'});
		var MZM = new Ext.form.TextField({id:"MZM",name:"MZM",hidden:true});

		var MZ	= new Ext.ux.MzCombox({
			name:"mz",
			dictCode:"zd_mz",
			value:"01",
			width:200,
			allowBlank:false,
			blankText:"民族必须选！",
			listeners:{ 
				select:function() {
					Ext.getCmp("MZM").setValue(this.getValue());
				}  
			}
		});
		var FZDWM = new Ext.form.TextField({id:"FZDWM",name:"FZDWM",hidden:true});
		var FZDW = new Ext.ux.FzdwCombox({
			name:"fz",
			dictCode:"zd_fzdw",
			value:"69",
			width:200,
			allowBlank:false,
			blankText:"发证单位必须选！",
			listeners:{ 
				select:function() {
					Ext.getCmp("FZDWM").setValue(this.getValue());
				}  
			}
		});

		
		var ZW = new Ext.form.TextField({id:"ZW",maxLength:200, width:200,allowBlank:false});
		var zflb = new Ext.ux.ZflbCombox({
			name:"zfl",
			dictCode:" zd_zflb",
			value:"01",
			width:200,
			allowBlank:false,
			blankText:"执法类型必须选！",
			listeners:{ 
				select:function() {
					Ext.getCmp("zfl").setValue(this.getValue());
				}  
			}
		});
		var zflx = new Ext.form.TextField({id:"zflx",name:"zflx",hidden:true});
	
		var zffw = new Ext.form.TextField({id:"zffw",maxLength:200, width:200,allowBlank:false});

		var XXMC1 = new Ext.form.TextField({id:"XXMC1",maxLength:200, width:200,allowBlank:false,readOnly:true});

		var remark = new Ext.form.TextField({name:"remark",id:"remark",maxLength:200, width:200});
		var ZZMM =new Ext.form.TextField({id:"ZZMM",name:"ZZMM",hidden:true});
		var zzmm = new Ext.ux.ZzmmCombox({
			name:"ZZM",
			dictCode:"zd_zzmm",
			value:"01",
			width:200,
			allowBlank:false,
			blankText:"政治面貌必须选！",
			listeners:{ 
				select:function() {
					Ext.getCmp("ZZMM").setValue(this.getValue());
				}  
			}
		});
		var WHCD =new Ext.form.TextField({id:"WHCD",name:"WHCD",hidden:true});
		var whcd = new Ext.ux.WhcdCombox({
			name:"whc",
			dictCode:"zd_xd",
			value:"01",
			width:200,
			allowBlank:false,
			blankText:"文化程度必须选！",
			listeners:{ 
				select:function() {
					Ext.getCmp("WHCD").setValue(this.getValue());
				}  
			}
		});
	
 
		var sex = new Ext.form.ComboBox({
   				name:'sex',
   				mode: 'local', 
			    triggerAction: 'all',   
			    editable:false,
			    width:200,
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			                 'value',
			                 'text'
			                ],
			        data: [['1', '男'], ['2', '女']]
			    }),
			    valueField: 'value',
			    displayField: 'text'
			});
		var imagepath = new Ext.form.TextField({id:"imagepath",name:"imagepath",hidden:true});
		var imagebox =	new Ext.BoxComponent({autoEl: {tag: 'img',src: 'img/basicsinfo/mrzp_img.jpg',id:"imgPath"},id:"img",width:110,height:130});
		var upload = new Ext.Button({x : 17,y : -10,cls : "base_btn",text : "上传照片",handler:this.saveUpload,scope : this});
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[
				{html:"姓名：",baseCls:"label_right",width:212},
				{html:"<font class='required'>*</font>",items:[xm],baseCls:"component",width:230},
				{html:"照片：",baseCls:"label_right",width:212,rowspan:3},
				{items:[imagebox],baseCls:"component",width:106,rowspan:3},
				{html:"性别:",baseCls:"label_right",width:212},
				{html:"<font class='required'>*</font>",items:[sex],baseCls:"component",width:230},	
				
				{html:"文化程度：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'>*</font>",items:[WHCD,whcd],baseCls:"component",width:230,colspan:2},
				{html:"身份证号：",baseCls:"label_right",width:212},
				{html:"<font class='required'>*</font>",items:[sfzjh],baseCls:"component",width:230},				
				{html:"",baseCls:"label_right",width:212},
				{items:[upload],baseCls:"",width:318},
				
				{html:"专业：",baseCls:"label_right",width:212},
				{html:"<font class='required'>*</font>",items:[ZY],baseCls:"component",width:230},	
				
				{html:"民族：",baseCls:"label_right",width:212},
				{html:"<font class='required'>*</font>",items:[MZM,MZ],baseCls:"component",width:230,colspan:2},
				{html:"工作单位：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'>*</font>",items:[XXMC1],baseCls:"component",width:230},
				{html:"政治面貌：",baseCls:"label_right",width:212},
				{html:"<font class='required'>*</font>",items:[ZZMM,zzmm],baseCls:"component",width:230,colspan:2},
				{html:"职务：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'>*</font>",items:[ZW],baseCls:"component",width:230},
				{html:"执法类型：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'>*</font>",items:[zflx,zflb],baseCls:"component",width:230,colspan:2},
				{html:"执法范围：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'>*</font>",items:[zffw],baseCls:"component",width:230},
				{html:"发证机关：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'></font>",items:[FZDW],baseCls:"component",width:230},
				{html:"备注：",baseCls:"label_right",width:212}, 
				{html:"<font class='required'></font>",items:[remark],baseCls:"component",width:230},
				{items:[usercode],baseCls:"component",width:230,hidden:true}
			]		
		});		
		return new Ext.ux.FormPanel({
			frame:false,
			
			items:[
				{layout:'form',  width:950,xtype:"fieldset",title:"考生信息管理",items:[panel]}
			]});			
    }, 
    createForm1:function(){	
    	var sfzjh2  = new Ext.form.TextField({name:"sfzjh",id:"sfzjh2",maxLength:200, width:200,allowBlank:false,readOnly:true});
    	var xm2  = new Ext.form.TextField({name:"xm",id:"xm2",maxLength:200, width:200,allowBlank:false,readOnly:true});
    	var xbm2  = new Ext.form.TextField({name:"xbm",id:"xbm2",maxLength:200, width:200,allowBlank:false,readOnly:true});
    	var MZM2 = new Ext.form.TextField({name:"MZM",id:"MZM2",maxLength:200, width:200,allowBlank:false,readOnly:true});
    	var WHCD2  = new Ext.form.TextField({name:"WHCD",id:"WHCD2",maxLength:200, width:200,allowBlank:false,readOnly:true});
    	var ZZMM2  = new Ext.form.TextField({name:"ZZMM",id:"ZZMM2",maxLength:200, width:200,allowBlank:false,readOnly:true});
    	var ZY2  = new Ext.form.TextField({name:"ZY",id:"ZY2",maxLength:200, width:200,allowBlank:false,readOnly:true});
    	var XXMC2  = new Ext.form.TextField({name:"XXMC",id:"XXMC2",maxLength:200, width:200,allowBlank:false,readOnly:true});
    	var ZW2  = new Ext.form.TextField({name:"ZW",id:"ZW2",maxLength:200, width:200,allowBlank:false,readOnly:true});
    	var zflx2  = new Ext.form.TextField({name:"zflx",id:"zflx2",maxLength:200, width:200,allowBlank:false,readOnly:true});
    	var zffw2  = new Ext.form.TextField({name:"zffw",id:"zffw2",maxLength:200, width:200,allowBlank:false,readOnly:true});
    	var fzdw2  = new Ext.form.TextField({name:"fzdw",id:"fzdw2",maxLength:200, width:200,allowBlank:false,readOnly:true});
    	var remark2 = new Ext.form.TextField({name:"remark",id:"remark2",maxLength:200, width:200,readOnly:true});
    	var imagebox2 = new Ext.BoxComponent({autoEl: {tag:'img',src: 'img/basicsinfo/mrzp_img.jpg'},id:"box1",width:110,height:130});
    	return new Ext.ux.FormPanel({ 
				frame:false,
		       items:[{  
		         layout:'form',  
		         xtype:'fieldset',
		         width:1090,
		         style:'margin:10 10',
		         title:'考生个人基础信息',  
		         items: [
                    {
                    	xtype:"panel",
						layout:"table",  
						baseCls:"table",
						layoutConfig: { 
							columns: 4
							}, 
						baseCls:"table",
						items:[
							{html:"姓名：",baseCls:"label_right",width:212},
							{items:[xm2],baseCls:"component",width:318},	
							{html:"照片：",baseCls:"label_right",width:212,rowspan:4},
							{items:[imagebox2],baseCls:"component",width:318,rowspan:4},

							
							{html:"身份证件号码：",baseCls:"label_right",width:212},
							{items:[sfzjh2],baseCls:"component",width:318},	
							{html:"性别：",baseCls:"label_right",width:212},
							{items:[xbm2],baseCls:"component",width:318},
							
							{html:"民族：",baseCls:"label_right",width:212},
							{items:[MZM2],baseCls:"component",width:318},
						
							{html:"文化程度：",baseCls:"label_right",width:212},
							{items:[WHCD2],baseCls:"component",width:318},
							
							{html:"政治面貌：",baseCls:"label_right",width:212},
							{items:[ZZMM2],baseCls:"component",width:318},
							{html:"专业：",baseCls:"label_right",width:212},
							{items:[ZY2],baseCls:"component",width:318},	
						
							] 
                    }]  
		       },
		       
		       {  
		         layout:'form',  
		         xtype:'fieldset', 
		         width:1090,
		         style:'margin:10 10 10 10',
		         title:'考生个人考试信息',  
		         items: [
                    {
                    	xtype:"panel",
						layout:"table", 
						baseCls:"table",
						layoutConfig: { 
							columns: 4
							}, 
						baseCls:"table",
						items:[
							{html:"工作单位：",baseCls:"label_right",width:212},
							{items:[XXMC2],baseCls:"component",width:318},
							{html:"职务：",baseCls:"label_right",width:212},
							{items:[ZW2],baseCls:"component",width:318},
							{html:"执法类别：",baseCls:"label_right",width:212},
							{items:[zflx2],baseCls:"component",width:318},
							{html:"执法范围:",baseCls:"label_right",width:212},
							{items:[zffw2],baseCls:"component",width:318},
							{html:"发证机关：",baseCls:"label_right",width:212},
							{items:[fzdw2],baseCls:"component",width:318},
							{html:"备注：",baseCls:"label_right",width:212},
							{items:[remark2],baseCls:"component",width:318},
							] 
                    }]  
		       }
		       ]  
	    	})			
    }, 
    createCertificatesWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.ssaveCertificates,scope:this});
    	
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){Ext.getCmp("jfpz").getEl().dom.src="";this.certificatesWindow.hide()},scope:this});
		
		return new Ext.ux.Window({
			width:980,
		 	height:360,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
			
					  ]
					
				},
				listeners:{
			 		hide:function(){
			 			this.certificatesFrom.form.reset();
			 		},scope:this},
	     		items:[this.certificatesFrom],
			 	title:"上传缴费凭证"});		
	
    },
    createUpdateWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.updateKs,scope:this});
    	
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.updateWindow.hide()},scope:this});
		
		return new Ext.ux.Window({
			width:980,
		 	height:450,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
			
					  ]
					
				},
				listeners:{
			 		hide:function(){
			 			this.updateForm.form.reset();
			 		},scope:this},
	     		items:[this.updateForm],
			 	title:"考生信息维护"});		
	
    },
    createWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.saveZx,scope:this});
    	
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){ Ext.getCmp("img").getEl().dom.src=  "img/basicsinfo/mrzp_img.jpg";this.editWindow.hide()},scope:this});
		
		return new Ext.ux.Window({
			width:980,
		 	height:450,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
			
					  ]
					
				},
				listeners:{
			 		hide:function(){
			 			this.editForm.form.reset();
			 		},scope:this},
	     		items:[this.editForm],
			 	title:"考生信息维护"});		
	
    },

    showEidtWindow:function() {
    	var thiz = this;
       				if(sf=="已上报"||sf=="审核通过"||sf=="已缴费"||sf=="审核不通过"){
       		    		Ext.MessageBox.alert("错误","轮次已上报不能添加考生");
       		    		return;
       		    	}
       		    	Ext.getCmp("xsjbxxid").setValue("");
       		    	Ext.getCmp('XXMC1').setValue(xxmc1);
       		    	var zkdw = zkdw1;
       		    	thiz.editWindow.show(null,function(){
       		        	thiz.editForm.$load({
       		      			params:{'zkdw1':zkdw},
       		      			action:"sign_getFzdwDm.do",
       		      			handler:function(form,result,scope){  
       		      				form.findField("fz").setValue(result.data.dm);      
       		      			}
       		      		})
       		      	},this)
    },
   
    createWindow1:function(){
//    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addRound,scope:this});
		var cancel = new Ext.Button({text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.window.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:1200,
			 	height:500,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
					  ]
		
				},
				listeners:{
			 		hide:function(){
			 			this.Form1.form.reset();
			 		},scope:this},
	     		items:[this.Form1],
			 	title:"考生信息查看"});		
	
    },
    updateKs:function(){
    	if(Ext.getCmp("xm3").getValue() == ""){
    		Ext.MessageBox.alert("错误","姓名不能为空！");
    		return;
    	}
    	if(Ext.getCmp("sfzjh3").getValue() == ""){
    		Ext.MessageBox.alert("错误","身份证号不能为空！");
    		return;
    	}
    	var selectedBuildings = this.grid2.getSelectionModel().getSelections();
    	var sfzjh1 = selectedBuildings[0].get("SFZJH");
    	var imagepath = Ext.getCmp("imagepath3").getValue();
    	var thiz = this;
    	this.updateForm.$submit({
    		url:"sign_update.do",
    		params: {
    			'sfzjh1':sfzjh1,
    			'imagepath':imagepath,
    			'bmlcid':bmlcid1
    		},
    		handler:function(form,result,thiz){
    			thiz.updateWindow.hide();
    			thiz.grid2.$load();
    		},
    		scope:this
    	});
    },
    saveZx:function(){
    
	
    	if(Ext.getCmp("xm").getValue() == ""){
    		Ext.MessageBox.alert("错误","姓名不能为空！");
    		return;
    	}
    	if(Ext.getCmp("sfzjh").getValue() == ""){
    		Ext.MessageBox.alert("错误","身份证号不能为空！");
    		return;
    	}
  
    	if(Ext.getCmp("imagepath").getValue() == "" || Ext.getCmp("imagepath").getValue() == null){
    		Ext.MessageBox.alert("错误","照片不能为空！");
    		return;
    	}
    	var thiz = this;
    	this.editForm.$submit({
    		url:"sign_save.do",
    		params: {
    			'bmlcid':bmlcid1,
    			'imagepath':Ext.getCmp("imagepath").getValue(),
    			'xsjbxxid':Ext.getCmp("xsjbxxid").getValue()
  			
    		},
    		handler:function(form,result,thiz){
    			thiz.editWindow.hide();
    			thiz.grid2.$load();
    		},
    		scope:this
    	});
    	Ext.getCmp("xsjbxxid").setValue("");
    }, 

    saveCertificates:function(){
    	var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",id:"uploadButton",handler:this.savePic,scope:this});   	
		this.fileUpWin = new Ext.ux.Window({
     		title:"文件上传",	
     		width:400,     		
     		tbar:{cls:"ext_tabr",items:["->",_b_save]},
     		listeners:{
		 		hide:function(){
		 			this.submitForm.form.reset();
		 		},scope:this},
     		items:[this.submitForm]
     	});
     	this.fileUpWin.show(null,function(){},this);
    },
    savePic:function(){
    	var filePath = this.submitForm.getForm().findField("upload").getRawValue();

    	var objtype=filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
    	if(filePath==""){ 
    		alert("请选择文件！");return false;
    	}else{
    		if(!/\.(jpg|JPG|bmp|BMP|png|PNG|zip|ZIP)$/.test(objtype)){
    			alert("文件类型必须是.jpg,.bmp,.png,.zip中的一种!")
    			return false;
	        }
    	}
    	
    	this.submitForm.$submit({
    		timeout:3600000,
    		async:false,
    		action:"sign_exportCertificates.do",
    		params:{
				form:this.submitForm.getForm().findField("upload"),
			},			
			success: function(form, action) {
			    this.fileUpWin.hide();			   
			    var path = action.result.data;
			    Ext.getCmp("jfpzpath").setValue(path);
			    var objtype=path.substring(path.lastIndexOf(".")).toLowerCase();
				if(/\.(zip|ZIP)$/.test(objtype)){
					Ext.getCmp("jfpz").hide();
					
				}else{
					Ext.getCmp("jfpz").show();
					pzpath = Ext.getCmp("jfpzpath").getValue();
				    var url = "uploadFile/certificates/"+pzpath;
				    Ext.getCmp("jfpz").getEl().dom.src = url;

				}		    
			   
			    },
			failure: function(form, action) { 
				var that = this;
				   Ext.Msg.alert("提示",action.result.msg,function(b,t){
					   that.fileUpWin.hide(); 
					   if(action.result.data!=null && action.result.data!=''){
						   window.open(Ext.get("ServerPath").dom.value+"/"+action.result.data.replace(/\\/g, "\/"));
						}
				   });
			   },
			scope:this
		});
    
    },
    saveUpload:function(){	
    	
    	//上传照片信息
    	var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",id:"uploadButton",handler:this.saveIMG,scope:this});   	
		this.fileUpWin = new Ext.ux.Window({
     		title:"文件上传",	
     		width:400,     		
     		tbar:{cls:"ext_tabr",items:["->",_b_save]},
     		listeners:{
		 		hide:function(){
		 			this.submitForm.form.reset();
		 		},scope:this},
     		items:[this.submitForm]
     	});
     	this.fileUpWin.show(null,function(){},this);
    },
    saveIMG:function(){ 	
    	
    	var filePath = this.submitForm.getForm().findField("upload").getRawValue();

    	var objtype=filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
    	if(filePath==""){ 
    		alert("请选择文件！");return false;
    	}else{
    		if(!/\.(jpg|JPG)$/.test(objtype)){
    			alert("文件类型必须是.jpg,.JPG中的一种!")
    			return false;
	        }
    	}
    	
    	this.submitForm.$submit({
    		timeout:3600000,
    		async:false,
    		action:"sign_exportPhonto.do",
    		params:{
				form:this.submitForm.getForm().findField("upload"),
			},			
			success: function(form, action) {
			    this.fileUpWin.hide();			   
			    var path = action.result.data;
			    Ext.getCmp("imagepath").setValue(path);
			    
			    imagepath1 = Ext.getCmp("imagepath").getValue();
			    var url = "uploadFile/photo/"+imagepath1;
			    Ext.getCmp("img").getEl().dom.src = url;
			   
			    },
			failure: function(form, action) { 
				var that = this;
				   Ext.Msg.alert("提示",action.result.msg,function(b,t){
					   that.fileUpWin.hide(); 
					   if(action.result.data!=null && action.result.data!=''){
						   window.open(Ext.get("ServerPath").dom.value+"/"+action.result.data.replace(/\\/g, "\/"));
						}
				   });
			   },
			scope:this
		});
    },

    saveUpload1:function(){	
    	var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",id:"uploadButton",handler:this.saveIMG1,scope:this});
	
		this.fileUpWin = new Ext.ux.Window({
     		title:"文件上传",	
     		width:400,     		
     		tbar:{cls:"ext_tabr",items:["->",_b_save]},
     		listeners:{
		 		hide:function(){
		 			this.submitForm.form.reset();
		 		},scope:this},
     		items:[this.submitForm]
     	});
     	this.fileUpWin.show(null,function(){},this);
    },
    
    saveIMG1:function(){
    	    	
    	var filePath = this.submitForm.getForm().findField("upload").getRawValue();
    	
    	var objtype=filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
    	var selectedBuildings = this.grid2.getSelectionModel().getSelections();
    	if(filePath==""){ 
    		alert("请选择文件！");return false;
    	}else{
    		if(!/\.(jpg|JPG)$/.test(objtype)){
    			alert("文件类型必须是.jpg,.JPG中的一种!")
    			return false;
	        }
    	}
    	
    	this.submitForm.$submit({
    		timeout:3600000,
    		async:false,
    		action:"sign_exportPhonto.do",
    		params:{
				form:this.submitForm.getForm().findField("upload"),
			},			
			success: function(form, action) {
				Ext.Msg.alert("提示",action.result.msg);
			    this.fileUpWin.hide();			   
			    var path = action.result.data;
			    Ext.getCmp("imagepath3").setValue(path);
			   
			    imagepath1 = Ext.getCmp("imagepath3").getValue();
			    var url = "uploadFile/photo/"+imagepath1;
			    Ext.getCmp("img3").getEl().dom.src = url;
			    },
			failure: function(form, action) { 
				var that = this;
				   Ext.Msg.alert("提示",action.result.msg,function(b,t){
					   that.fileUpWin.hide(); 
					   if(action.result.data!=null && action.result.data!=''){
						   window.open(Ext.get("ServerPath").dom.value+"/"+action.result.data.replace(/\\/g, "\/"));
						}
				   });
			   },
			scope:this
		});
    },
    createChouquWindow:function(){
    	//设置抽取条件，显示设置抽取条件弹出框
    	var organ_sel	= new Ext.ux.form.OrganField({name:"organ_sel",width:210,readOnly:true});
    	var organ_lable = "参考单位：";
    	this.search2 = new Ext.form.FormPanel({
    		region: "north",
    		height:200,
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
							{html:organ_lable,baseCls:"label_right",width:140},
							{items:[organ_sel],baseCls:"component",width:210}
							
						] 
	                }]  
		       }]  
	    	});
	    var _b_save	  = new Ext.Button({text:"确定",iconCls:"p-icons-save",handler:this.selectCqksSubject,scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.ChouquWin.hide()},scope:this});
		var _b_reset = new Ext.Button({text:"重置",iconCls:"p-icons-delete",handler:function(){this.search2.getForm().reset()},scope:this});
	    return new Ext.ux.Window({
     		title:"抽取考生",	
     		height:180,
     		width:440,
     		tbar:{cls:"ext_tabr",
				  items:["->",_b_cancel,"->",_b_reset,"->",_b_save]},
			items:[this.search2]
     	});
    },
    checkInvoice:function(){
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条轮次进行查看！");
    		return;
    	}
    	bmlcid2= selectedBuildings[0].get("BMLCID");
    	sl= selectedBuildings[0].get("SL");
    	this.tabPanel.setActiveTab(this.invoicePanel);
    	this.grid3.$load({"bmlcid":bmlcid});
    },
    addExamSection:function(){
    	//主页面根据轮次进行数据抽取
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条轮次进行查看！");
    		return;
    	}
    	var bmlcid = selectedBuildings[0].get("BMLCID");
    	bmlcid1 = selectedBuildings[0].get("BMLCID");
    	xxmc1 = selectedBuildings[0].get("CKDW");
    	zkdw1 = selectedBuildings[0].get("MC").replace('法制办','人民政府');
    	sf = selectedBuildings[0].get("SHFLAG");
  
    	this.tabPanel.setActiveTab(this.chouQuPanel);
    	this.grid2.$load({"bmlcid":bmlcid});
    },
    exportK:function(){
    	var thiz = this;
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
		alert("照片以身份证号命名, .jpg格式,并压缩成zip格式的压缩包");
		if(selectedBuildings.length != 1){
			Ext.MessageBox.alert("消息","请选择一条数据！");
			return;
		}    
		//导入照片信息
		var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",id:"uploadButton",handler:this.exportKsFilesInfo,scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.fileUpWin.hide()},scope:this});
       				
       				thiz.fileUpWin = new Ext.ux.Window({
       					title:"上传照片",	
       					width:400,     		
       					tbar:{cls:"ext_tabr",items:["->",_b_cancel,"->",_b_save]},
       					listeners:{
       						hide:function(){
       							thiz.submitForm.form.reset();
       						},scope:this},
       						items:[thiz.submitForm]
       				});
       				thiz.fileUpWin.show(null,function(){},this);
    },

  exportKsFilesInfo:function(){
	  	
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
    	var bmlcid = selectedBuildings[0].get("BMLCID"); 
    	Ext.getCmp('uploadButton').setDisabled(true);
    	this.submitForm.$submit({
    		timeout:3600000,
    		async:false,
    		action:"sign_exportPhontoInfo.do",
    		params:{
				form:this.submitForm.getForm().findField("upload"),
				'bmlcid':bmlcid
			},			
			success: function(form, action) {
			       Ext.Msg.alert("提示",action.result.msg);
			       this.fileUpWin.hide();
				   //this.grid.$load();
				   this.returnMainPanel();
			    },
			failure: function(form, action) { 
				var that = this;
				   Ext.Msg.alert("提示",action.result.msg,function(b,t){
					   that.fileUpWin.hide();
					   that.grid.$load();  
					   
					   if(action.result.data!=null && action.result.data!=''){
						   window.open(Ext.get("ServerPath").dom.value+"/"+action.result.data.replace(/\\/g, "\/"));
						}
					   that.returnMainPanel();
				   });
			   },
			scope:this
		});    		    	    	   	
  		
  },
    
	selectExamSubject:function(){
		//主页面根据年度查询数据
		var xn=Ext.getCmp('exrxn_find').getValue();
		this.grid.$load({"XN":xn});
		Ext.getCmp('qd').setDisabled(false);//禁用相关的表单元素
	},
    selectCqksSubject:function(){
    	if(kxx == ''){
    		Ext.MessageBox.alert("消息","请设置考号！");
    		return;
    	}    	
    	var organ_sel = this.search2.getForm().findField("organ_sel").getCodes();

    	var sfzjh = Ext.getCmp('sfzjh').getValue();
    	this.grid2.$load({"school":organ_sel,"khao":kxx,"lcid":lcid,"sfzjh":sfzjh});
    	this.ChouquWin.hide();
    },
    //新增考生
    addCqksSubject:function(){
    	//保存抽取的考生
    	var selected =  this.grid2.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	Ext.getCmp('qd').setDisabled(true);
    	var selectedStudent = this.grid2.getSelectionModel().getSelections();
    	var thiz = this;
    	var xjh = "";
    	var ksh = "";
    	for(var i = 0; i < selectedStudent.length; i++) {
    		if(i != selectedStudent.length - 1) {
    			xjh += selectedStudent[i].get("XJH") + ",";
	    		ksh += selectedStudent[i].get("KSCODE") + ",";
    		}else {
    			xjh += selectedStudent[i].get("XJH");
	    		ksh += selectedStudent[i].get("KSCODE");
    		}
    	}
    	Ext.Msg.confirm('消息','确认保存这'+selectedStudent.length+'条记录？', function (id) {
    		if (id == 'yes') {
    			Ext.Ajax.request({   
    	       		url:'registration_addCqxs.do',
    	       		params:{
    	       			'lcid':lcid,
    	       			'xjh':xjh,
    	       			'khao':ksh
    	       		},
    	       		success: function(resp,opts) {
    	       			var respText = Ext.util.JSON.decode(resp.responseText);
    	       			if (respText.msg!='') {
    	       				Ext.MessageBox.alert("提示",respText.msg);
    	       			}       			
    	       			thiz.selectExamSubject();
    	       			thiz.selectCqksSubject();
    	       		},
    	       		failure: function(resp,opts){
    	       			Ext.Msg.alert('错误', "保存失败！");
    	       		}  
    	    	}); 
    		}
    	});   	
    },
    //修改状态，上报
    updateExaminee:function(){
    	var thiz=this;
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
		if(selectedBuildings.length != 1){
			Ext.MessageBox.alert("消息","请选择一条数据！");
			return;
		}
		var bmlcid = selectedBuildings[0].get("BMLCID");
		var ksrs = selectedBuildings[0].get("SL");
		var yzprs = selectedBuildings[0].get("YZP");
		var mzprs = selectedBuildings[0].get("MZP");
       				
       				if(ksrs==''){ksrs=0}
       				if(ksrs==0){
       					Ext.MessageBox.alert("错误","没有考生不能上报！");
       					return;
       				}
       				Ext.MessageBox.show({
       					title:"消息",
       					msg:"您确定要上报吗?当前已有考生"+ksrs+"人，其中"+yzprs+"名考生已经上传照片，"+mzprs+"名考生没有上传照片！",
       					buttons:Ext.MessageBox.OKCANCEL,
       					icon:Ext.MessageBox.QUESTION,    			
       					fn:function(b){
       						if(b=='ok'){
       							if(mzprs==0){
       								Ext.MessageBox.show({
       									title:"消息",
       									msg:"您确定要上报吗?当前已有考生"+ksrs+"人，上报之后不能增加、修改、删除！",
       									buttons:Ext.MessageBox.OKCANCEL,
       									icon:Ext.MessageBox.QUESTION,
       									fn:function(b){
       										if(b=='ok'){
       											Ext.Ajax.request({   
       												url:'signup_updateCqxs.do',
       												params:{'bmlcid':bmlcid},
       												success: function(resp,opts) {
       													var respText = Ext.util.JSON.decode(resp.responseText);
       													if (respText.msg!='') {
       														Ext.MessageBox.alert("提示",respText.msg);
       													}
       													thiz.returnMainPanel();
       												},
       												failure: function(resp,opts){
       													Ext.Msg.alert('错误', "上报失败！");
       												}        	
       											});
        		    				
       										}
       									}
       								})
       							}else{
       								Ext.Msg.alert('错误', "请把考生照片上传后才能上报！");
       							}    				
       						}
       					}
       				})
    },
    returnMainPanel:function(){
    	kxx = "";
    	this.tabPanel.setActiveTab(this.mainPanel);
    	this.initQueryDate();
    	this.grid2.getStore().removeAll();
    	this.selectExamSubject();
    },
    setExam:function(){
    	//设置考号
    	var _b_save	= new Ext.Button({text:"确定",iconCls:"p-icons-save",handler:function(){
    		var reg = /^\d+$/;
    		if(!reg.test(Ext.getCmp("kh").getValue())){
    			return;
    		}
    		if(Ext.getCmp("kh").getValue().length>10){
        		Ext.MessageBox.alert("消息","考号长度不能超过十位！");
        		return;
        	}
    		Ext.getCmp('cq1').setDisabled(false);
    		kxx = Ext.getCmp("kh").getValue();
    		this.fileUpWin.hide()
    	},scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.fileUpWin.hide()},scope:this});
		var kh	= new Ext.form.TextField({id:"kh",maxLength:10,width:190,regex : /^\d+$/,regexText : '考号只能为数字！'});
		Ext.getCmp('kh').setValue(kxx);
		this.search3 = new Ext.form.FormPanel({
			region: "north",
		    height:150,
		    items:[{  
		    	layout:'form',  
		    	xtype:'fieldset',  
		    	style:'margin:10 10',
		    	items: [{
		    		xtype:"panel",
		    		layout:"table", 
					layoutConfig: { 
						columns: 2
					}, 
					baseCls:"table",
					items:[
						{html:"设置考号：",baseCls:"label_right",width:120},
						{items:[kh],baseCls:"component",width:210}
					] 
		    	}]  
		    }]  
		});
		
     	this.fileUpWin = new Ext.ux.Window({
     		title:"设置考号",	
     		height:150,
     		width:400,
     		tbar:{
				cls:"ext_tabr",
				items:["->",_b_cancel,"->",_b_save]
			},
			items:[this.search3]
     	});
     	this.fileUpWin.show(null,function(){     	
     	},this);
    },
    //上传考生信息
    exportKs:function(){
    	var thiz = this;
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
		if(selectedBuildings.length != 1){
			Ext.MessageBox.alert("消息","请选择一条数据！");
			return;
		}    	
		var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",id:"uploadButton",handler:this.exportFilesInfo,scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.fileUpWin.hide()},scope:this});
       				
       				thiz.fileUpWin = new Ext.ux.Window({
       					title:"文件上传",	
       					width:400,     		
       					tbar:{cls:"ext_tabr",items:["->",_b_cancel,"->",_b_save]},
       					listeners:{
       						hide:function(){
       							thiz.submitForm.form.reset();
       						},scope:this},
       						items:[thiz.submitForm]
       				});
       				thiz.fileUpWin.show(null,function(){},this);
    },
   
    exportBkKs:function(){
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	if(selectedBuildings.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条数据！");
    		return;
    	}    	
    	//导入教师信息
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
     //上传考生信息
    exportFilesInfo:function(){
    	var filePath = this.submitForm.getForm().findField("upload").getRawValue();
    	//判断文件类型
    	var objtype=filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
    	var fileType=new Array(".xls");
    	if(filePath==""){ 
    		alert("请选择文件！");return false;
    	}else{
    		if(!/\.(xls)$/.test(objtype)){
    			alert("文件类型必须是.xls中的一种!")
    			return false;
	        }
    	}
    	var selectedBuildings = this.grid.getSelectionModel().getSelections();
    	var bmlcid = selectedBuildings[0].get("BMLCID"); 
    	var dwid = selectedBuildings[0].get("DWID");
    	var CKDW = selectedBuildings[0].get("CKDW");
    	Ext.getCmp('uploadButton').setDisabled(true);
    	this.submitForm.$submit({
    		timeout:3600000,
    		async:false,
    		action:"sign_exportKsInfo.do",
    		params:{
				form:this.submitForm.getForm().findField("upload"),
				'bmlcid':bmlcid,
				'dwid':dwid,
				'CKDW':CKDW,
			},			
//			handler:function(form,result,scope){
//				alert(result);
//				Ext.getCmp('uploadButton').setDisabled(false);
//				scope.fileUpWin.hide();	
//				scope.grid.$load();	
//				scope.grid.$load();
//			},
			success: function(form, action) {
			       Ext.Msg.alert("提示",action.result.msg);
			       this.fileUpWin.hide();
				  // this.grid.$load();
				   this.returnMainPanel();
			    },
			failure: function(form, action) { 
				var that = this;
				   Ext.Msg.alert("提示",action.result.msg,function(b,t){
					   that.fileUpWin.hide();
					  // that.grid.$load();  
					   
					   if(action.result.data!=null && action.result.data!=''){
						   window.open(Ext.get("ServerPath").dom.value+"/"+action.result.data.replace(/\\/g, "\/"));
						   
					   }
					   that.returnMainPanel();
				   });
			   },
			scope:this
		});    		    	    	   	
   },

  //查看考生信息
  selectSFZJH:function(){    	
  	var selected = this.grid2.getSelectionModel().getSelections();
  	if(selected.length != 1){

  	Ext.MessageBox.alert("消息","请选一个考生进行查看！");
  		return;
	   }
  	var sfzjh = selected[0].get("SFZJH");
   	this.window.show(null,function(){
  		this.Form1.$load({
  			params:{'sfzjh':sfzjh},
  			action:"sign_getSFZ.do",
  			handler:function(form,result,scope){  
  			
  				var url = "";
				if(result.data.PATH==undefined){
					url = "img/basicsinfo/mrzp_img.jpg";
				}else{
					url = "uploadFile/photo/"+result.data.PATH;
				
				}
				Ext.getCmp("box1").getEl().dom.src = url;
//				form.findField("FZDWM").setValue(result.data.fzdw);
//  				form.findField("sfzjh").setValue(respText.msg.sfzjh);//身份证号
//  				form.findField("xm").setValue(respText.msg.xm);//姓名
//  				form.findField("WHCD").setValue(respText.msg.WHCD);//文化程度
//  				form.findField("ZZMM").setValue(respText.msg.ZZMM);//政治面貌
//  				form.findField("ZY").setValue(respText.msg.ZY);//专业
//  				form.findField("MZM").setValue(respText.msg.MZM);//民族
//  				form.findField("ZW").setValue(respText.msg.ZW);//职务
// 				form.findField("zflx").setValue(respText.msg.zflx);//执法类别
//  				form.findField("zffw").setValue(respText.msg.zffw);//执法范围
//  				form.findField("remark").setValue(respText.msg.remark);//备注
//  				form.findField("ISTJ").setValue(respText.msg.ISTJ);//是否提交
//  				form.findField("XXMC").setValue(respText.msg.XXMC);//工作单位
//  				form.findField("fzdw").setValue(respText.msg.fzdw);
  			}
  		
  		});
  	},this)
  },
//修改考生信息
  update:function(){
	  var thiz = this;
	  var selectedXnxq = this.grid2.getSelectionModel().getSelections();
	  var sfzjh = selectedXnxq[0].get("SFZJH");
     				
     				if(sf=="已上报"||sf=="审核通过"||sf=="已缴费"||sf=="审核不通过"){
     					Ext.MessageBox.alert("错误","轮次已上报不能修改考生信息");
     					return;
     				}
     				
     				if(selectedXnxq.length != 1){
     					Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
     					return;
     				}
     				
     				
     				thiz.updateWindow.show(null,function(){
     					thiz.updateForm.$load({
     						params:{'sfzjh':sfzjh},
     						action:"sign_getSFZ.do",
     						handler:function(form,result,scope){      	
     							//行内获取信息 	
     							var url = "";
     							if(result.data.PATH==undefined){
     								url = "img/basicsinfo/mrzp_img.jpg";
     							}else{
     								url = "uploadFile/photo/"+result.data.PATH;
     							}
     							form.findField("fz").setValue(result.data.fzdw3);
     							Ext.getCmp("img3").getEl().dom.src = url;
     							Ext.getCmp("imagepath3").setValue(result.data.PATH);
     							form.findField("xm3").setValue(result.data.xm)//姓名
     							form.findField("sex3").setValue(result.data.xbm);//性别 

//					alert(Ext.getCmp("FZDWM3").getValue);
//					form.findField("ZY3").setValue(result.data.ZY);//专业
//					form.findField("ZW3").setValue(result.data.ZW);//职务
//					form.findField("zflx3").setValue(result.data.zfl);//执法类别
//					form.findField("XXMC3").setValue(result.data.XXMC);//工作单位
//  				form.findField("MZM3").setValue(result.data.mz);//民族
//  				form.findField("sfzjh3").setValue(result.data.sfzjh);//身份证号  			
//  				form.findField("WHCD3").setValue(result.data.whc);//文化程度
//  				form.findField("ZZMM3").setValue(result.data.ZZM);//政治面貌
//  				form.findField("remark3").setValue(selectedXnxq[0].get("remark"));//备注

  				
  	
     						}
     					});
     				},this)
  	},

  //删除考生数据
  deleteRound:function(){
	  var thiz =this;
	  var selectedXnxq = this.grid2.getSelectionModel().getSelections();
	  if(selectedXnxq.length != 1){
		  Ext.MessageBox.alert("消息","请选择一条记录进行删除！");
		  return;
	  }
	  if(sf=="已上报"||sf=="审核通过"||sf=="已缴费"||sf=="审核不通过"){
		  Ext.MessageBox.alert("错误","轮次已上报不能删除考生");
		  return;
	  }
	  var sfzjh = selectedXnxq[0].get("SFZJH");
	  var bmlcid = selectedXnxq[0].get("bmlcid"); 
     				
     				Ext.MessageBox.show({
     					title:"消息",
     					msg:"您确定要删除吗?",
     					buttons:Ext.MessageBox.OKCANCEL,
     					icon:Ext.MessageBox.QUESTION,    			
     					fn:function(b){
     						if(b=='ok'){
     							JH.$request({
     								params:{'sfzjh':sfzjh,'bmlcid':bmlcid},
     								action:"sign_delte.do",
     								handler:function(){
     									thiz.grid2.$load();
     								}
     							})    				
     						}
     					}
     				})
     },
  //上传缴费凭证
  ssaveCertificates:function(){
	  var selectedBuildings = this.grid.getSelectionModel().getSelections();
	  var bmlcid = selectedBuildings[0].get("BMLCID");
	  var pzpath = Ext.getCmp("jfpzpath").getValue();
	  var lxrname = Ext.getCmp("lxrname").getValue();
	  var lxdhno = Ext.getCmp("lxdhno").getValue();
	  
	  if(pzpath==null||pzpath==""){
		  Ext.MessageBox.alert("消息","请先上传缴费凭证！");
		  return;
	  }
	  var thiz = this;
	  thiz.certificatesFrom.$submit({
  		url:"upload_saveCertificate.do",
  		params: {
  			'bmlcid':bmlcid,
  			'pzpath':pzpath,
  			'lxr':lxrname,
  			'lxdh':lxdhno,
  		},
  		handler:function(form,result,thiz){
  			thiz.certificatesWindow.hide();
  			thiz.grid.$load();
  			thiz.selectExamSubject();
			thiz.store.reload();
  		},
  		scope:thiz
  	});
  },

  selectCertificates:function(){
	  var selectedBuildings = this.grid.getSelectionModel().getSelections();
	  if(selectedBuildings.length != 1){
		Ext.MessageBox.alert("消息","请选一个轮次查看缴费凭证！");
  	  	return;
	  }
	  var sh = selectedBuildings[0].get("SHFLAG");
  	  if(sh!="已缴费" && sh!="审核通过" && sh!="审核不通过"){
    		Ext.MessageBox.alert("错误","已缴费、审核通过、审核不通过才能查看缴费凭证");
    		return;
      }
	  var bmlcid = selectedBuildings[0].get("BMLCID");
	  this.selectCertificatesWindow.show(null,function(){
	  		this.selectCertificatesFrom.$load({
	  			params:{'bmlcid':bmlcid},
	  			action:"upload_getKsrs.do",
	  			handler:function(form,result,scope){
	  				
	  				var url = "";
    				if(result.data.JFPZ==undefined){
    					url = "";
    				}else{
    					url = "uploadFile/certificates/"+result.data.JFPZ;
    					var objtype=url.substring(url.lastIndexOf(".")).toLowerCase();				
						if(/\.(zip|ZIP)$/.test(objtype)){
    						Ext.getCmp("ckjfpz").hide();
    						Ext.getCmp("download").show();
							Ext.getCmp("fangda").hide();
    						Ext.getCmp("ckjfpzpath").setValue(url);
    					}else{
    						Ext.getCmp("ckjfpz").show();
    						Ext.getCmp("download").hide();
    						Ext.getCmp("fangda").show();
    						pz=url;
    	    				Ext.getCmp("ckjfpz").getEl().dom.src = url;
    					}
//    					var xmlHttp ;  
//    			        if(window.ActiveXObject)
//    			         {  
//    			          xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
//    			         }  
//    			         else if (window.XMLHttpRequest)  
//    			         {  
//    			          xmlHttp = new XMLHttpRequest();  
//    			         }   
//    			        xmlHttp.open("Get",url,false);  
//    			        xmlHttp.send();  
//    			        if(xmlHttp.status==404){
//    			        	url = "uploadFile/photo/"+result.data.JFPZ;
//    			        }
    			        
    				}
    				
    		  		form.findField("ckbmrs").setValue(result.data.SL);//人数
    		  		form.findField("ckjfbz").setValue(result.data.JFBZ);//缴费标准
    		  		form.findField("ckyjje").setValue(result.data.JFJE);//缴费金额
    		  		form.findField("cklxr").setValue(result.data.LXR);//联系人
    		  		form.findField("cklxdh").setValue(result.data.LXDH);//联系人

	 			}
	  		});
	  	},this)
  },
  selectInvoice:function(){
	  if(sh!='审核通过'){
		  Ext.MessageBox.alert("错误","您的凭证未审核通过，暂时不予申请发票！如有任何疑问，请联系业务客服电话0731-82821386！！！！");
		  return;
	  }
	  var bmlcid = bmlcid2;
	  this.invoiceWindow.show(null,function(){
		  this.invoiceForm.$load({
			params:{'bmlcid':bmlcid},
	  		action:"upload_getInvoice.do",
	  		handler:function(form,result,scope){
	  			form.findField("lxrdh").setValue(result.data.LXDH);
	  		}
	  		});
	  },this)
  },
  saveInvoice:function(){
	  var thiz = this;
	  thiz.invoiceForm.$submit({
  		url:"upload_saveInvoice.do",
  		params: {
  			'bmlcid':bmlcid2
  		},
  		handler:function(form,result,thiz){
//  			Ext.Ajax.request({		
//  				url:"upload_getSum.do",
//  				params:{
//  					'bmlcid':bmlcid2
//  				},
//  				scope:this,
//  				success: function (r, options) {			
//  					var result =Ext.decode(r.responseText);
//  					var sums = result.data.sum;
//  					if(sums==sl){
//  						Ext.MessageBox.show({
//  		 					title:"消息",
//  		 					msg:"您的发票申请信息已经全部申请，需要现在提交吗？提交后不能修改，增加，删除发票信息！",
//  		 					buttons:Ext.MessageBox.OKCANCEL,
//  		 					icon:Ext.MessageBox.QUESTION,    			
//  		 					fn:function(b){
//  		 						if(b=='ok'){
//  		 							Ext.Ajax.request({   
//  											url:'upload_updateFpzt.do',
//  											params:{'bmlcid':bmlcid2},
//  											success: function(resp,opts) {
//  												var respText = Ext.util.JSON.decode(resp.responseText);
//  												if (respText.msg!='') {
//  													Ext.MessageBox.alert("提示",respText.msg);
//  												}
//  											},
//  											failure: function(resp,opts){
//  												Ext.Msg.alert('错误', "提交失败！");
//  											}        	
//  										});
//  		 						}
//  				}
//  		  	  })	
//  			
//  		}
//  				}
//  		
//  	  });
  			thiz.invoiceWindow.hide();
  			thiz.grid3.$load();
  			thiz.selectExamSubject();
  		},
  		scope:thiz
	  });
    },
  shenqingInvoice:function(){
	  var selectedBuildings = this.grid.getSelectionModel().getSelections();
	  if(selectedBuildings.length != 1){
		  Ext.MessageBox.alert("错误","请选择一条数据！");
		  return;
	  }
	  sh = selectedBuildings[0].get("SHFLAG");
	  if(sh!='审核通过'){
		  Ext.MessageBox.alert("错误","您的凭证未审核通过，暂时不予申请发票！如有任何疑问，请联系业务客服电话0731-82821386！！！！");
		  return;
	  }
	  bmlcid2 = selectedBuildings[0].get("BMLCID");
	  sl = selectedBuildings[0].get("SL");
	  this.tabPanel.setActiveTab(this.invoicePanel);
  	  this.grid3.$load({"bmlcid":bmlcid2});
  },
  deleteInvoice:function(){
      var thiz =this;
      var selected =  this.grid3.getSelectionModel().getSelected();
      if(!selected){
          Ext.MessageBox.alert("消息","请选择一条记录！");
          return;
      }
      var selectedBuildings = this.grid3.getSelectionModel().getSelections();
      var id =selectedBuildings[0].get("ID");

      Ext.MessageBox.show({
          title:"消息",
          msg:"您确定要删除吗?",
          buttons:Ext.MessageBox.OKCANCEL,
          icon:Ext.MessageBox.QUESTION,
          fn:function(b){
              if(b=='ok'){
                  JH.$request({
                      params:{
                    	  'bmlcid':bmlcid2,
                    	  'orderNum':id
                    	  },
                      action:"upload_updateUse.do",
                      handler:function(){
                          thiz.grid3.$load();
                      }
                  })
              }
          }
      })
  },
  updateInvoice:function(){
      var selectedBuildings = this.grid3.getSelectionModel().getSelections();
      var orderNum =selectedBuildings[0].get("ID");
      var thiz = this;
      thiz.updateinvoiceForm.$submit({
          url:"upload_updateInvoice.do",
          params: {
              'orderNum':orderNum,
              'bmlcid':bmlcid2
          },
          handler:function(form,result,thiz){
              thiz.updateinvoiceWindow.hide();
              thiz.grid3.$load();
          },
          scope:this
      });

  },
  updateInvoices:function(){
      var selected =  this.grid3.getSelectionModel().getSelected();
      if(!selected){
          Ext.MessageBox.alert("消息","请选择一条记录！");
          return;
      }
      var selectedBuildings = this.grid3.getSelectionModel().getSelections();
      var ids =selectedBuildings[0].get("ID");
      this.updateinvoiceWindow.show(null,function(){
          this.updateinvoiceForm.$load({
              params:{
            	  'orderNum':ids
              },
              action:"upload_getInvoice2.do",
              handler:function(form,result,scope){
                  form.findField("NAME").setValue(result.data.NAME);
              }
          });
      },this)
  },
  commitInvoice:function(){
	  var thiz=this;
  	  var selectedBuildings = this.grid.getSelectionModel().getSelections();
  	  if(selectedBuildings.length != 1){
  		  Ext.MessageBox.alert("消息","请选择一条数据！");
  		  return;
  	  }
  	  var bmlcid = selectedBuildings[0].get("BMLCID");
  	  var ksrs = selectedBuildings[0].get("SL");
  	  Ext.Ajax.request({		
		url:"upload_getSum.do",
		params:{
			'bmlcid':bmlcid
		},
		scope:this,
		success: function (r, options) {			
			var result =Ext.decode(r.responseText);
			var sums = result.data;
			if(sums==ksrs){
				Ext.MessageBox.show({
 					title:"消息",
 					msg:"您确定要提交申请吗?提交后不能修改发票信息，不能重复提交！",
 					buttons:Ext.MessageBox.OKCANCEL,
 					icon:Ext.MessageBox.QUESTION,    			
 					fn:function(b){
 						if(b=='ok'){
 							Ext.Ajax.request({   
									url:'upload_updateFpzt.do',
									params:{'bmlcid':bmlcid},
									success: function(resp,opts) {
										var respText = Ext.util.JSON.decode(resp.responseText);
										if (respText.msg!='') {
											Ext.MessageBox.alert("提示",respText.msg);
										}
										thiz.returnMainPanel();
									},
									failure: function(resp,opts){
										Ext.Msg.alert('错误', "提交失败！");
									}        	
								});
 						}
 					}
 					})
			}else{
				Ext.Msg.alert('错误', "申请发票人数与总人数不相等，请补全发票申请信息后提交！");
			}
		}
  	  })
  },
  ckp:function(){	
  	this.FangdaWindow.show();
  	Ext.getCmp("jfpz1").getEl().dom.src = pz;
  	
  },
  downloadZip:function(){
	  var path = Ext.get("ServerPath").dom.value +"/"+ Ext.getCmp("ckjfpzpath").getValue();
	  window.open(path);
	  this.selectCertificatesWindow.hide();
  },
  downloadTemplate:function(){
	   var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/examregistratio.xls";
	   window.open(path);   		
  }
});
