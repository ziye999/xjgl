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
    	this.eidtWindow.on("hide",function(){       		
    		this.editForm.$reset();    		
    	},this);
    	this.grid.on("rowdblclick",function(grid, rowIndex, e){
    		var flage = grid.store.getAt(rowIndex).get("FLAGE");
    	    if(flage==0){
    	    	Ext.MessageBox.alert("消息","此条记录不允许编辑！");
    	    	return;
    	    }
    	    Ext.getCmp("userInfo.usercode").setValue(grid.store.getAt(rowIndex).get("USERCODE"));
    	    this.editForm.getForm().findField("organ_self").setValue(grid.store.getAt(rowIndex).get("JG"));
    	    Ext.getCmp("userInfo.organ_sel").setValue(grid.store.getAt(rowIndex).get("JGZ"));
    	    Ext.getCmp("userInfo.loginid").setValue(grid.store.getAt(rowIndex).get("LOGINID"));
    	    Ext.getCmp("userInfo.username").setValue(grid.store.getAt(rowIndex).get("USERNAME"));
    	    Ext.getCmp("userInfo.loginpwd").setValue(grid.store.getAt(rowIndex).get("LOGINPWD"));    	    
    	    this.editForm.getForm().findField("yhlx").setValue(grid.store.getAt(rowIndex).get("USERTYPEV"));
    	    this.editForm.getForm().findField("userInfo.sex").setValue(grid.store.getAt(rowIndex).get("SEXV"));    	    
    	    this.editForm.getForm().findField("userInfo.state").setValue(grid.store.getAt(rowIndex).get("STATEV"));
    	    this.editForm.getForm().findField("userInfo.qssj").setValue(grid.store.getAt(rowIndex).get("QSSJ"));
        	this.editForm.getForm().findField("userInfo.jssj").setValue(grid.store.getAt(rowIndex).get("JSSJ"));
        	Ext.getCmp("userInfo.wx").setValue(grid.store.getAt(rowIndex).get("WX"));
        	Ext.getCmp("userInfo.sjhm").setValue(grid.store.getAt(rowIndex).get("SJHM"));
        	Ext.getCmp("userInfo.email").setValue(grid.store.getAt(rowIndex).get("EMAIL"));
        	Ext.getCmp("userInfo.e2id").setValue(grid.store.getAt(rowIndex).get("E2ID"));
        	Ext.getCmp("userInfo.jtdh").setValue(grid.store.getAt(rowIndex).get("JTDH"));   
        	Ext.getCmp("rolem").setValue(grid.store.getAt(rowIndex).get("ROLECODE"));
        	this.editForm.getForm().findField("role").setValue(grid.store.getAt(rowIndex).get("ROLECODE"));
        	this.eidtWindow.show();        	    		    		    		
    	},this);	
    },   
   	initComponent :function(){
   		this.editForm   = this.createBuildingEditForm();
   		this.eidtWindow = this.createBuildingWindow();
   		this.eidtWindow.add(this.editForm);
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "usercode",   align:"center", sortable:true, dataIndex:"USERCODE",hidden:true},
			{header: "登录帐号",   align:"center", sortable:true, dataIndex:"LOGINID"},
			{header: "姓名",   align:"center", sortable:true, dataIndex:"USERNAME"},
			{header: "性别",   align:"center", sortable:true, dataIndex:"SEX"},
			{header: "所属单位",   align:"center", sortable:true, dataIndex:"JG",width:200},
			{header: "用户类型",   align:"center", sortable:true, dataIndex:"USERTYPE"},
			{header: "是否启用",   align:"center", sortable:true, dataIndex:"STATE"},
			{header: "创建时间",   align:"center", sortable:true, dataIndex:"CREATETIME"},
			{header: "E2ID",   align:"center", sortable:true, dataIndex:"E2ID"},
			{header: "密码",   align:"center", sortable:true, dataIndex:"LOGINPWD",hidden:true},
			{header: "起始时间",   align:"center", sortable:true, dataIndex:"QSSJ",hidden:true},
			{header: "结束时间",   align:"center", sortable:true, dataIndex:"JSSJ",hidden:true},
			{header: "微信号",   align:"center", sortable:true, dataIndex:"WX",hidden:true},
			{header: "邮箱",   align:"center", sortable:true, dataIndex:"EMAIL",hidden:true},
			{header: "贯籍",   align:"center", sortable:true, dataIndex:"JTDH",hidden:true},
			{header: "手机号",   align:"center", sortable:true, dataIndex:"SJHM",hidden:true}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,			
			tbar:[ 
			      "->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteUsers,scope:this}
			      ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateUser,scope:this}
			      ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.editForm.$reset();this.eidtWindow.show();},scope:this}],
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			excelTitle:"用户信息表",
			action:"userManager_getListPage.do",
			fields :["USERCODE","LOGINID","USERNAME","SEX","JG","USERTYPE","STATE","CREATETIME","E2ID","QSSJ","JSSJ","WX","EMAIL","JTDH","SJHM","FLAGE","LOGINPWD","USERTYPEV","STATEV","SEXV","ROLECODE"],
			border:false
		});
		var dlzh = new Ext.form.TextField({fieldLabel:"登录帐号",id:"dlzh_query",maxLength:200, width:200});
		var xm = new Ext.form.TextField({fieldLabel:"姓名",id:"xm_query",maxLength:200, width:200});
		var yhlx = new Ext.form.ComboBox({
   				name:'yhlx',
   				mode: 'local', 
			    triggerAction: 'all',   
			    editable:false,
			    store: new Ext.data.ArrayStore({
			        id: 0,
			        fields: [
			                 'value',
			                 'text'
			                ],
			        data: [['1', '系统管理员'], ['2', '组考单位'],['3', '考生'],['4', '参考单位']]
			    }),
			    valueField: 'value',
			    displayField: 'text'
		});  
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.queryUser,scope:this});
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
                    		columns: 4
                    	}, 
                    	baseCls:"table",
                    	items:[
                    	       {html:"姓名：",baseCls:"label_right",width:170}, 
                    	       {items:[xm],baseCls:"component",width:210},
                    	       {html:"登录帐号：",baseCls:"label_right",width:170},
                    	       {items:[dlzh],baseCls:"component",width:210},
                    	       {html:"用户类型：",baseCls:"label_right",width:170},
                    	       {items:[yhlx],baseCls:"component",width:210},
                    	       {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
                    	     ] 
                  	}]  
			}]  
		})	    	
	    this.grid.getStore().on("beforeload",function(store,options){
	    	var params ={'xm':xm.getValue(),'dlzh':dlzh.getValue(),'userType':yhlx.getValue()};
	    	Ext.apply(store.baseParams,params);     		
    	});
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"border",items:[this.search,this.grid]});
    	this.eidtWindow.show();
    	this.eidtWindow.hide();
    },
    initQueryDate:function(){
    	this.grid.$load(); 
    },
    createBuildingEditForm:function(){
    	var usercode  = new Ext.form.TextField({id:"userInfo.usercode",maxLength:200, width:200,hidden:true});
    	var organ_self = new Ext.ux.form.OrganField({name:"organ_self",width:200,singleSelection:true,allowBlank:false,readOnly:true}); 
    	var loginid   = new Ext.form.TextField({id:"userInfo.loginid",maxLength:200, width:200,allowBlank:false});
		var username  = new Ext.form.TextField({id:"userInfo.username",maxLength:200, width:200,allowBlank:false});
		var loginpwd  = new Ext.form.TextField({id:"userInfo.loginpwd",maxLength:200, width:200,maxLength:200, width:200,allowBlank:false,inputType:'password'});
		var yhlx = new Ext.form.ComboBox({
				name:'yhlx',
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
				    data: [['1', '系统管理员'], ['2', '组考单位'],['3', '考生'],['4', '参考单位']]
				}),
				valueField: 'value',
				displayField: 'text'
		});
		var sex	= new Ext.form.ComboBox({
   				name:'userInfo.sex',
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
		var state = new Ext.form.ComboBox({
   				name:'userInfo.state',
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
			        data: [['1', '是'], ['0', '否']]
			    }),
			    valueField: 'value',
			    displayField: 'text'
			});  
		var qssj = new Ext.form.DateField({name:'userInfo.qssj',maxLength:200, width:200,format:'Y-m-d',allowBlank:false,editable:false});
		var jssj = new Ext.form.DateField({name:'userInfo.jssj',maxLength:200, width:200,format:'Y-m-d',allowBlank:false,editable:false});
		var wx = new Ext.form.TextField({id:"userInfo.wx",maxLength:200, width:200,allowBlank:true});
		var sjhm = new Ext.form.TextField({id:"userInfo.sjhm",maxLength:200, width:200,allowBlank:true,regex : /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,regexText : '电话号码格式错误！'});
		var email = new Ext.form.TextField({id:"userInfo.email",maxLength:200, width:200,allowBlank:true,regex : /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,regexText : '邮箱地址格式错误！'});
		var e2id = new Ext.form.TextField({id:"userInfo.e2id",maxLength:200, width:200,allowBlank:true});
		var jtdh = new Ext.form.TextField({id:"userInfo.jtdh",maxLength:200, width:200,allowBlank:true,regex : /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,regexText : '电话号码格式错误！'});
		var rolem = new Ext.form.TextField({hidden:true,name:"rolem",id:"rolem"});
		var organ_sel = new Ext.form.TextField({hidden:true,name:"userInfo.organ_sel",id:"userInfo.organ_sel"});
		var role = new Ext.ux.Combox({
			name:"role",
			dropAction:"getRole",
			width:200,
			allowBlank:false,
			blankText:"用户角色为必选项！",
			listeners:{
			"select":function(){
				Ext.getCmp("rolem").setValue(this.getValue());
			}
		}});
		
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[
				{html:"所属单位：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[organ_self],baseCls:"component",width:230},
				{html:"姓名：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[username],baseCls:"component",width:230},
				{html:"登录帐号：",baseCls:"label_right",width:100}, 
				{html:"<font class='required'>*</font>",items:[loginid],baseCls:"component",width:230},
				{html:"密码：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[loginpwd],baseCls:"component",width:230},				
				{html:"用户类型：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[yhlx],baseCls:"component",width:230},
				{html:"是否启用:",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[state],baseCls:"component",width:230},				
				{html:"性别：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[sex],baseCls:"component",width:230},
				{html:"E2ID：",baseCls:"label_right",width:100},
				{items:[e2id],baseCls:"component",width:230},
				{html:"起始时间：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[qssj],baseCls:"component",width:230},
				{html:"结束时间：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[jssj],baseCls:"component",width:230},
				{html:"手机号：",baseCls:"label_right",width:100},
				{items:[sjhm],baseCls:"component",width:230},
				{html:"家庭电话：",baseCls:"label_right",width:100},
				{items:[jtdh],baseCls:"component",width:230},
				{html:"邮箱：",baseCls:"label_right",width:100},
				{items:[email],baseCls:"component",width:230},  
				{html:"微信号：",baseCls:"label_right",width:100},
				{items:[wx],baseCls:"component",width:230},
				{html:"用户角色：",baseCls:"label_right",width:100},
				{html:"<font class='required'>*</font>",items:[role,rolem,organ_sel],baseCls:"component",width:230,colspan:2},
				{items:[usercode],baseCls:"component",width:230,hidden:true}
			]		
		});		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
				{layout:'form', xtype:"fieldset",title:"用户信息",items:[panel]}
			]});			
    },
    createBuildingWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addBuilding,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:this.cancle,scope:this});
		return new Ext.ux.Window({
			 	width:845,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
			 	title:"用户信息维护"});		
    },
    addBuilding:function(){
    	var thiz = this; 
    	var oc = this.editForm.getForm().findField('organ_self').getCodes(); 
    	var yhlx = this.editForm.getForm().findField('yhlx').getValue();
    	var rolem = Ext.getCmp("rolem").getValue();
    	if(!checkDate(this.editForm.getForm().findField("userInfo.qssj").getValue(),this.editForm.getForm().findField("userInfo.jssj").getValue())){
			Ext.MessageBox.alert("错误","起始日期大于结束日期！");
			return;
    	}
    	if (oc==null||oc==""||oc=="undefined") {
    		oc = Ext.getCmp("userInfo.organ_sel").getValue();
    	}
    	this.editForm.getForm().submit({
    		params:{
    			'userInfo.organ_sel':oc,
    			'userInfo.usertype':yhlx,
    			'userInfo.rolem':rolem
    		},
    		url:"userManager_opUser.do",
    		method:'post',     		
    		reset:true,
    		success: function(form, action) {
    			Ext.MessageBox.alert('提示',action.result.data);     			
				thiz.eidtWindow.hide();
				thiz.eidtWindow.hide();
    			thiz.grid.$load();
    		},
		  	failure : function(form, action){
		  		Ext.MessageBox.alert('提示',action.result.data);
		  	}
    	}); 
    },
    updateUser:function(){
    	var selectedUser = this.grid.getSelectionModel().getSelections();
	    if(selectedUser.length != 1){
	    	Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
	    	return;
	    } 
	    if(selectedUser[0].json.FLAGE==0){
	    	Ext.MessageBox.alert("消息","此条记录不允许编辑！");
	    	return;
	    }	    
	    Ext.getCmp("userInfo.usercode").setValue(selectedUser[0].json.USERCODE);
	    Ext.getCmp("userInfo.usercode").setValue(selectedUser[0].json.USERCODE);
	    this.editForm.getForm().findField("organ_self").setValue(selectedUser[0].json.JG);
	    Ext.getCmp("userInfo.organ_sel").setValue(selectedUser[0].json.JGZ);
	    Ext.getCmp("userInfo.loginid").setValue(selectedUser[0].json.LOGINID);
	    Ext.getCmp("userInfo.username").setValue(selectedUser[0].json.USERNAME);
	    Ext.getCmp("userInfo.loginpwd").setValue(selectedUser[0].json.LOGINPWD);
	    this.editForm.getForm().findField("yhlx").setValue(selectedUser[0].json.USERTYPEV);
	    this.editForm.getForm().findField("userInfo.sex").setValue(selectedUser[0].json.SEXV);	    
	    this.editForm.getForm().findField("userInfo.state").setValue(selectedUser[0].json.STATEV);
	    this.editForm.getForm().findField("userInfo.qssj").setValue(selectedUser[0].json.QSSJ);
    	this.editForm.getForm().findField("userInfo.jssj").setValue(selectedUser[0].json.JSSJ);
    	Ext.getCmp("userInfo.wx").setValue(selectedUser[0].json.WX);
    	Ext.getCmp("userInfo.sjhm").setValue(selectedUser[0].json.SJHM);
    	Ext.getCmp("userInfo.email").setValue(selectedUser[0].json.EMAIL);
    	Ext.getCmp("userInfo.e2id").setValue(selectedUser[0].json.E2ID);
    	Ext.getCmp("userInfo.jtdh").setValue(selectedUser[0].json.JTDH);
    	Ext.getCmp("rolem").setValue(selectedUser[0].json.ROLECODE);
    	this.editForm.getForm().findField("role").setValue(selectedUser[0].json.ROLECODE);
    	this.eidtWindow.show();
    },queryUser:function(){ 
    	this.grid.$load();
	},cancle:function(){
		this.eidtWindow.hide(null,function(){this.editForm.$reset();});
	},deleteUsers:function(){
		var that = this;
		var selectedUser = this.grid.getSelectionModel().getSelections();
		if(selectedUser.length == 0){
			Ext.MessageBox.alert("消息","请至少选择一条记录进行删除！");
			return;
	    }else{
	    	var str = [];
	    	for (var i = 0; i < selectedUser.length; i++) {
	    		if(selectedUser[i].json.FLAGE==0){
	    			Ext.MessageBox.alert("消息","您选择删除的记录中[<font color='red'>"+selectedUser[i].json.USERNAME+"</font>]不允许被编辑!");
	    			return;
			    }
	    		str[i] = selectedUser[i].json.USERCODE;
	    	}
	    	Ext.Msg.confirm('消息','确认删除这'+selectedUser.length+'条记录？', function (btn) {
	    		if (btn == 'yes') {
	    			Ext.Ajax.request({
	    				url: 'userManager_deleteUsers.do',
	    				params: { 'users': str },
	    				method: 'POST',
	    				callback: function (options, success, response) {
	    					if (success) {
	    						Ext.MessageBox.alert("消息",response.responseText);
	    						that.grid.$load();
	    					} else {
	    						Ext.MessageBox.alert("消息","删除失败!");
	    					}
	    				}
	    			});                         
			    }});
	    	}
		}
	}
);

function checkDate(sdate,edate){
	var strs= new Array(); //定义一数组 
	if(sdate.getTime() > edate.getTime()){
    	return false;
	}else{
    	return true;
	}	
}