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
    	this.grid.on("rowdblclick",function(grid, rowIndex, e ){//grid网格组件可用于以表格格式显示数据。//是打开网格grid.on
    		// form 窗体小部件是从用户获取数据。
    		var xnxqid = grid.store.getAt(rowIndex).get("XNXQ_ID");//获取被操作的数据记录
    		var xnmc = grid.store.getAt(rowIndex).get("XNMC");
    		
    		var xqmc = grid.store.getAt(rowIndex).get("XQMC");
    		Ext.getCmp("xn").setCode(xnmc+","+xqmc);
    		var xxkssj = grid.store.getAt(rowIndex).get("XXKSSJ");
    		var xxjssj = grid.store.getAt(rowIndex).get("XXJSSJ");
    		this.editForm.getForm().findField("xxkssj").setValue(grid.store.getAt(rowIndex).get("XXKSSJ"));
    		this.editForm.getForm().findField("xxjssj").setValue(grid.store.getAt(rowIndex).get("XXJSSJ"));
    		Ext.getCmp("xnxqid").setValue(xnxqid);
    		Ext.getCmp("xnmc").setValue(xnmc);
    		Ext.getCmp("xqmc").setValue(xqmc);
    		Ext.getCmp("xxkssj").setValue(xxkssj);
    		Ext.getCmp("xxjssj").setValue(xxjssj);
    		var yxbz = grid.store.getAt(rowIndex).get("YXBZ");
    		this.editForm.getForm().findField("yxbz").setValue(grid.store.getAt(rowIndex).get("YXBZ"));
    		Ext.getCmp("yxbz").setValue(yxbz);
    		Ext.getCmp("gxr").setValue(gxr);
    		var gxr = grid.store.getAt(rowIndex).get("GXR"); 		
    		this.eidtWindow.show();
    	},this)
    },   
   	initComponent :function(){   		
   		this.editForm   = this.createExamroundForm();
   		this.editWindow = this.createWindow();
   		this.editWindow.add(this.editForm);
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});//1.CheckboxSelectionModel简写sm 2.实现sm是否显示
		var cm = [
			sm,
			{header: "年度",   	align:"center", sortable:true, dataIndex:"XNMC"},
			{header: "季度",   	align:"center", sortable:true, dataIndex:"XQMC"},
			{header: "起始日期",  align:"center", sortable:true, dataIndex:"XXKSSJ"},
			{header: "结束日期",  align:"center", sortable:true, dataIndex:"XXJSSJ"},	
			{header: "状态",  align:"center", sortable:true, dataIndex:"YXBZ"},
			{header: "轮次数",  align:"center", sortable:true, dataIndex:"GXR"},
			{header: "报名轮次数",  align:"center", sortable:true, dataIndex:"cklc"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			tbar:{
				cls:"ext_tabr",
				items:[ 
				       "->",{xtype:"button",text:"启动轮次",iconCls:"p-icons-add",handler:this.glzp,scope:this},
				       "->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteRound,scope:this}
				       ,"->",{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.update,scope:this}
				       ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){this.editForm.$reset();Ext.getCmp("xnxqid").setValue("");this.editWindow.show()},scope:this}
				      ]
			},
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			excelTitle:"年度管理表",
			action:"annuaManagementl_getListPage.do",
			fields :["XNXQ_ID","XNMC","XQMC","XXKSSJ","XXJSSJ","YXBZ","GXR","cklc"],
			border:false
		});
		
		var xn_find=new Ext.ux.form.XnxqField({width:180,id:"exrxn_find",width:210,readOnly:true});
		var xxkssj_find = 	new Ext.form.DateField({id:"xxkssj_find",editable:false,width:140,format:"Y-m-d"});
		var xxjssj_find = new Ext.form.DateField({id:"xxjssj_find",editable:false,width:140,format:"Y-m-d"});
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
							columns: 7
						}, 
						baseCls:"table",
						items:[

							{html:"年度：",baseCls:"label_right",width:120},
							{items:[xn_find],baseCls:"component",width:210},
							{html:"开始日期：",baseCls:"label_right",width:50},
							{items:[xxkssj_find],baseCls:"component",width:170},
							{html:"结束日期：",baseCls:"label_right",width:50},
							{items:[xxjssj_find],baseCls:"component",width:170},
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
    //添加数据
    createExamroundForm:function(){	
    	var xnxqid = new Ext.form.TextField({fieldLabel:"",hidden:true,name:"xnxqid",id:"xnxqid"});
        //考试轮次名称
    	var xnmc = new Ext.form.TextField({id:"xnmc",maxLength:50,allowBlank:false,blankText:"年度不能为空！",width:170});
		var xqmc = new Ext.form.TextField({id:"xqmc",maxLength:50,allowBlank:false,blankText:"季度不能为空！",width:170});
		//开始日期
		var xxkssj = new Ext.form.DateField({name:"xxkssj",editable:false,width:140,format:"Y-m-d", emptyText:"请选择日期...",blankText:"开始日期不能为空！"}); 
		//结束日期
		var xxjssj = new Ext.form.DateField({name:"xxjssj",editable:false,width:140,format:"Y-m-d", emptyText:"请选择日期...",blankText:"结束日期不能为空！"}); 
		//状态
//
//		var yxbz = new Ext.form.ComboBox({
//			
//			name:'yxbz',
//			readonly:true,
//			mode: 'local', 
//			triggerAction: 'all',   
//			editable:false,
//			width:120,
//			store: new  Ext.data.SimpleStore({
//				 id: 0,
//				fields: ['value','text' ],
//				data: [['1', '已开始'], ['0', '未开始']]
//		    }),
//		   
//		    valueField: 'value',
//		    displayField: 'text'
//		    
//		}); 
//	
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4
			},
			items:[
			      		       
			       {html:"年度：",baseCls:"label_right",width:100},
			       {html:"<font class=required color=red>*</font>", items:[xnmc],baseCls:"component",width:200},
			       {html:"季度：",baseCls:"label_right",width:100},
			       {html:"<font class=required color=red>*</font>",items:[xqmc],baseCls:"component",width:200},															       				
			       {html:"起始日期：",baseCls:"label_right"},
			       {html:"<font class=required color=red>*</font>",items:[xxkssj],baseCls:"component",width:200},
			       {html:"结束日期：",baseCls:"label_right"},
			       {html:"<font class=required color=red>*</font>",items:[xxjssj],baseCls:"component",width:200},
//			       {html:"状态：",baseCls:"label_right",width:100},
//			       {html:"<font class=required color=red>*</font>",items:[yxbz],baseCls:"component",width:200},	
//			       	
			      
			       
				]		
		});
		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"99%"},
			items:[
					{layout:'form',xtype:"fieldset",title:"年度管理",items:[panel]},
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
				listeners:{
			 		hide:function(){
			 			this.editForm.form.reset();
			 		},scope:this},
	     		items:[this.editForm],
			 	title:"年度管理维护"});		
    },
    addRound:function(){
    	var xn = Ext.getCmp("xnmc").getValue();
    	var xq = Ext.getCmp("xqmc").getValue();
    	if(xn == ""){
    		Ext.MessageBox.alert("错误","年度不能为空！");
    		return;
    	}
    	if(xn.toString().length!=4){
    		Ext.MessageBox.alert("错误","年度格式不对，请输入4位年度！");
    		return;
    	}
//    	if(xq!=1&&xq!=2){
//    		Ext.MessageBox.alert("错误","季度请输入1或者2！");
//    		return;
//    	}
    	if(this.editForm.getForm().findField("xxkssj").getValue()==""||this.editForm.getForm().findField("xxjssj").getValue()==""){
    		Ext.MessageBox.alert("错误","起始日期和结束日期不能为空！");
    		return;
    	}
    	if(!checkDate(this.editForm.getForm().findField("xxkssj").getValue(),this.editForm.getForm().findField("xxjssj").getValue())){
			Ext.MessageBox.alert("错误","起始日期大于结束日期！");
			return;
    	}  
    	var thiz = this;
    	this.editForm.$submit({
    		action:"annuaManagementl_saveZxxsXnxq.do",
    		params: {
    			'xnxqid':Ext.getCmp("xnxqid").getValue()		
    		},
    		
    		handler:function(form,result,thiz){
    			thiz.editWindow.hide();
    			thiz.grid.$load();
    		},
    		scope:this
    	});
    	Ext.getCmp("xnxqid").setValue("");
    },    
    deleteRound:function(){
    	
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}

    	var selectedRounds = this.grid.getSelectionModel().getSelections();
    	var yxbz = selectedRounds[0].get("YXBZ");
    	
    	if(yxbz == "已开始"){
    		Ext.MessageBox.alert("消息","年度已启动不可删除！");
    		return;
   		}
    	var thiz = this;
    	var xnxqids = "'";
    
    	for(var i = 0; i < selectedRounds.length; i++) {
    		if(i != selectedRounds.length - 1) {
    			xnxqids += selectedRounds[i].get("XNXQ_ID") + "','";
    		}else {
    			xnxqids += selectedRounds[i].get("XNXQ_ID") + "'";
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
    					params:{'xnxqids':xnxqids},
    					action:"annuaManagementl_deleteZxxsXnxq.do",
    					handler:function(){
    						thiz.grid.$load();
    					}
    				})    				
    			}
    		}
    	})
    },

    update:function(){    	
    	var selectedXnxq = this.grid.getSelectionModel().getSelections();
    	if(selectedXnxq.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
    		return;
	    }
    	var xnxqid = selectedXnxq[0].get("XNXQ_ID");
     	this.editWindow.show(null,function(){
    		this.editForm.$load({
    			params:{'xnxqid':xnxqid},
    			action:"annuaManagementl_getZxxs.do",
    			handler:function(form,result,scope){      				
    				Ext.getCmp("xnxqid").setValue(selectedXnxq[0].get("XNXQ_ID"));
    				form.findField("xnmc").setValue(selectedXnxq[0].get("XNMC"));
    				form.findField("xqmc").setValue(selectedXnxq[0].get("XQMC"))
    				var xk = selectedXnxq[0].get("XXKSSJ");
    				var xj = selectedXnxq[0].get("XXJSSJ");
    				var x = xk.substring(0,4);
    				var x1 = xk.substring(4, 6);
    				var x2 = xk.substring(6,8);
    				
    				var j = xj.substring(0,4);
    				var j1 = xj.substring(4, 6);
    			
    				var j2 = xj.substring(6,8);
    				
    				var xk1 = x+"-"+x1+"-"+x2;
    				var xj1 = j+"-"+j1+"-"+j2;
    				
//    				alert(xk1);
    			
//    				form.findField("WHCD3").setValue(result.data.XXKSSJ);
//    				form.findField("WHCD3").setValue(result.data.XXJSSJ);
    				form.findField("xxkssj").setValue(xk1);
    				form.findField("xxjssj").setValue(xj1);
    				form.findField("yxbz").setValue(selectedXnxq[0].get("YXBZ"));
    				form.findField("gxr").setValue(selectedXnxq[0].get("GXR"));
    				
    			}
    		});
    	},this)
//    	this.editWindow.show();
    	
//    	Ext.getCmp("xnxqid").setValue(xnxqid);
//    	Ext.getCmp("xnxqid").setValue(selectedXnxq[0].get().XNXQ_ID);
//    	Ext.getCmp("xnmc").setValue(selectedXnxq[0].json.XNMC);
//    	Ext.getCmp("xqmc").setValue(selectedXnxq[0].json.XQMC);
//    	Ext.getCmp("xxkssj").setValue(selectedXnxq[0].json.XXKSSJ);
//    	Ext.getCmp("xxjssj").setValue(selectedXnxq[0].json.XXJSSJ);
//    	Ext.getCmp("yxbz").setValue(selectedXnxq[0].json.YXBZ);
//    	
//    	Ext.getCmp("xnxqid").setValue(selectedXnxq[0].get("XNXQ_ID"));
//    	Ext.getCmp("xnmc").setValue(selectedXnxq[0].get("XNMC"));
//     	Ext.getCmp("xqmc").setValue(selectedXnxq[0].get("XQMC"));	
//     	Ext.getCmp("xxkssj").setValue(selectedXnxq[0].get("XXKSSJ"));  
//     	Ext.getCmp("xxjssj").setValue(selectedXnxq[0].get("XXJSSJ"));
//     	this.editForm.getForm().findField("yxbz").setValue(selectedXnxq[0].get("YXBZ"));
//    	roleForm.form.findField('yxbz').setRawValue(selectedXnxq[0].get("YXBZ"));
//    	var yxbzValue = selectedXnxq[0].get("YXBZ");//获取性别原来的值  
//    	var sexNode = mForm.findField('yxbz').getStore().findRecord('sexId', yxbzValue);//在性别下拉框对应的store中查找与原值相等的记录，就是sexValu为1，则在store中查找value对于1的记录，也就是<option value=1></option>。  
//    	mForm.findField('yxbz').select(sexNode);//下拉框中选中该记录，也就选中该<option></option>  
     

    },
    glzp:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
     
    	var selectedRounds = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var xnxqids = "'";
    	for(var i = 0; i < selectedRounds.length; i++) {
    		if(i != selectedRounds.length - 1) {
    			xnxqids += selectedRounds[i].get("XNXQ_ID") + "','";
    		}else {
    			xnxqids += selectedRounds[i].get("XNXQ_ID") + "'";
    		}
    	}
    	var xxkssj = selectedRounds[0].get("XXKSSJ");
    	var xxjssj = selectedRounds[0].get("XXJSSJ");
//    	alert(xxjssj);
//    	alert(xxkssj);
//   
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"您确定要启动轮次吗?",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,    			
    		fn:function(b){
    			if(b=='ok'){
    				JH.$request({
    					params:{'xnxqids':xnxqids,'xxkssj':xxkssj,'xxjssj':xxjssj},
    					action:"annuaManagementl_addBuilding.do",
    					handler:function(){
    						thiz.grid.$load();
    					}
    				})    				
    			}
    		}
    	})
    },
    
    selectExamSubject:function(){	
    	
    	var xxkssj=Ext.getCmp('xxkssj_find').getValue();
    	var xxjssj=Ext.getCmp('xxjssj_find').getValue();
    	var xn=Ext.getCmp('exrxn_find').getValue();
    	if(xxkssj!=null && xxkssj!=''){
    		if(xxjssj!=null && xxjssj!=''){
    			if(!checkDate(xxkssj,xxjssj)){
    				Ext.MessageBox.alert("错误","起始日期大于结束日期！");
    				return;
    			}  
    		}
    	}
		this.grid.$load({"XN":xn,"xxjssj":xxjssj,"xxkssj":xxkssj});
    	
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