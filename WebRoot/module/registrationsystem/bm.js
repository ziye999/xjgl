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
var xn1="";
var ckdw1="";
var mc1="";
var zkdw1 = "";
var fpzt1 =  "";
var pz = "";
var bmlcid1 = "";
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
//    		var xnxqid = grid.store.getAt(rowIndex).get("XNXQ_ID");//获取被操作的数据记录
//    		var xnmc = grid.store.getAt(rowIndex).get("XNMC");
//    		
//    		var xqmc = grid.store.getAt(rowIndex).get("XQMC");
//    		Ext.getCmp("xn").setCode(xnmc+","+xqmc);
//    		var xxkssj = grid.store.getAt(rowIndex).get("XXKSSJ");
//    		var xxjssj = grid.store.getAt(rowIndex).get("XXJSSJ");
    		this.editForm.getForm().findField("JFPZ").setValue(grid.store.getAt(rowIndex).get("JFPZ"));
    		this.editForm.getForm().findField("SHZT").setValue(grid.store.getAt(rowIndex).get("SHZT"));
	
    		this.eidtWindow.show();
    	},this)
    },   
   	initComponent :function(){   		
   		this.editForm   = this.createExamroundForm();
   		this.editWindow = this.createWindow();
   		this.editWindow.add(this.editForm);
   		this.editForm1   = this.createExamroundForm1();
   		this.editWindow1 = this.createWindow1();
   		this.editWindow1.add(this.editForm1);
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true,
			listeners:{
    			"selectionchange":function(sm){
    				var str = sm.getSelections();
    				if(str[0].get("SHFLAG")=="通过"||str[0].get("SHFLAG")=="不通过" ||str[0].get("SHFLAG")=="已上报"){
    					Ext.getCmp('sh').setDisabled(true);
    					
    				}else{
    				
    					Ext.getCmp('sh').setDisabled(false);
    				
    				}
    			}
    		}
		});//1.CheckboxSelectionModel简写sm 2.实现sm是否显示
		var cm = [
			sm,
			{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
			{header: "季度",   align:"center", sortable:true, dataIndex:"XQ"},
			{header: "考试名称",   align:"center", sortable:true, dataIndex:"EXAMNAME"},
			{header: "组考单位",   align:"center", sortable:true, dataIndex:"MC"},
			{header: "参考单位",   	align:"center", sortable:true, dataIndex:"CKDW"},
			{header: "考生数量",   align:"center", sortable:true, dataIndex:"SL"},
			{header: "状态",   align:"center", sortable:true, dataIndex:"SHFLAG"},
			{header: "发票状态",   align:"center", sortable:true, dataIndex:"fpzt"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			tbar:{
				cls:"ext_tabr",
				items:[ 
				       "->",{xtype:"button",text:"导出xls", iconCls:"p-icons-save",handler:this.exporXls,scope:this}					     
				       ,"->",{xtype:"button",text:"取消上报",id:"sb", iconCls:"p-icons-save",handler:this.updateZt,scope:this}
				       ,"->",{xtype:"button",text:"审核",id:"sh", iconCls:"p-icons-save",handler:this.update,scope:this}
				       ,"->",{xtype:"button",text:"取消报名",id:"bm", iconCls:"p-icons-save",handler:this.updateBm,scope:this}
				      ]
			},
			page:true,
			rowNumber:true,
			region:"center",
			excel:true,
			excelTitle:"考生报名-考生名单审核",
			action:"bmsh_getListPage.do",
			fields :["BMLCID","XN","XQ","EXAMNAME","DWID","DWTYPE","SHFLAG","SHZT","SL","MC","CKDW","JFPZ","fpzt"],
			border:false
		});
		var xnxq =new Ext.ux.form.XnxqField({width:190,id:"revxnxq_find",readOnly:true});
		var ckdw = new Ext.form.TextField({fieldLabel:"",id:"ckdw_find",width:180});
		var zkdw = new Ext.form.TextField({fieldLabel:"",id:"zkdw_find",width:180});
		var mc = new Ext.form.ComboBox({
			id:'mc',
			mode: 'local', 
			triggerAction: 'all',   
			editable:false,
			width:180,
			value:"3",
			store: new Ext.data.ArrayStore({
				id: 0,
				fields: ['value','text'],
			    data: [['0', '全部'],['1', '通过'],['2', '不通过'],['3', '已缴费'],['4','已上报']]
			}),
			valueField: 'value',
			displayField: 'text'
		});
		var fpzt = new Ext.form.ComboBox({
			id:'fpzt',
			mode: 'local', 
			triggerAction: 'all',   
			editable:false,
			width:180,
			store: new Ext.data.ArrayStore({
				id: 0,
				fields: ['value','text'],
			    data: [['0', '全部'],['2', '已审核'],['1', '未申请'],['3','未审核']]
			}),
			valueField: 'value',
			displayField: 'text'
		});


		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamSubject,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});

		this.search = new Ext.form.FormPanel({
			region: "north",
			height:140,
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
						{html:"年度：",baseCls:"label_right",width:120},
						{items:[xnxq],baseCls:"component",width:210},
						{html:"状态：",baseCls:"label_right",width:120},
						{items:[mc],baseCls:"component",width:210},
						{html:"参考单位：",baseCls:"label_right",width:120},
						{items:[ckdw],baseCls:"component",width:210},
						{html:"组考单位：",baseCls:"label_right",width:120},
						{items:[zkdw],baseCls:"component",width:210},
						{html:"发票状态：",baseCls:"label_right",width:120},
						{items:[fpzt],baseCls:"component",width:210},
						{layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:160}
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
    	//人数
    	var rs = new Ext.form.TextField({name:"rs",id:"rs",value:"100",width:220,maxLength:3,allowBlank:false,readOnly:true});
		//缴费金额
      	var fy = new Ext.form.TextField({name:"timelen",id:"timelen",value:"95元/人",width:220,maxLength:7,allowBlank:false,blankText:"报名费用不能为空！",readOnly:true});		
		//缴费凭证
		var jfpz = new Ext.BoxComponent({autoEl: {tag: 'img',src: '',id:"jfpz"},id:"jfpz",width:600,height:280});
		//缴费金额
		var jfje = new Ext.form.TextField({name:"jfje",id:"jfje",width:220,maxLength:5,allowBlank:true,readOnly:true});
		//联系人
		var lxr = new Ext.form.TextField({name:"lxr",id:"lxr",width:220,allowBlank:true,readOnly:true});
		//联系电话
		var lxdh = new Ext.form.TextField({name:"lxdh",id:"lxdh",width:220,allowBlank:true,readOnly:true});
		//审核
		var sh1 = new Ext.form.TextField({name:"sh1",id:"sh1",width:220,allowBlank:false,regexText:"审核不通过原因不能为空"});
		var upload = new Ext.Button({x : 19,y : -10,cls : "base_btn",id:"upload",text : "查看大图",handler:this.ckp,scope : this});
		var ckjfpzpath = new Ext.form.TextField({id:"ckjfpzpath",name:"ckjfpzpath",hidden:true});
		var download = new Ext.Button({id:"download",x : 19,y : -10,cls : "base_btn",text : "下载凭证",handler:this.downloadZip,scope : this});
		var panel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4
			},
			items:[
					{html:"报名人数：",baseCls:"label_right",width:120},
					{html:"<font class='required'>*</font>",items:[rs],baseCls:"component",width:240},
					{html:"缴费标准：",baseCls:"label_right",width:120},
					{html:"<font class='required'>*</font>",items:[fy],baseCls:"component",width:240},							
					{html:"应缴费金额：",baseCls:"label_right",width:120},
					{html:"",items:[jfje],baseCls:"component",width:240},
					{html:"联系人：",baseCls:"label_right",width:120},
					{html:"",items:[lxr],baseCls:"component",width:240},
					{html:"联系电话：",baseCls:"label_right",width:120},
					{html:"",items:[lxdh],baseCls:"component",width:240},
					{html:"审核说明：",baseCls:"label_right",width:120},
					{html:"<font class='required'>*</font>",items:[sh1],baseCls:"component",width:240},
					{html:"查看凭证： ",baseCls:"label_right",width:120},
					{items:[upload],baseCls:"",width:240},
					{html:"",baseCls:"label_right",width:120},
					{items:[],baseCls:"",width:240},
					
					{html:"缴费凭证：",baseCls:"label_right",width:120,rowspan:8},
					{items:[jfpz,download],baseCls:"component",width:240,rowspan:8}
				  
					
					
					
			       ]		
		});
		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"99%"},
			items:[
					{layout:'form',xtype:"fieldset",title:"审核管理",items:[panel]},
			]});					
    },    
    createExamroundForm1:function(){	
    	
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
    ckp:function(){	
    	this.editWindow1.show();
    	Ext.getCmp("jfpz1").getEl().dom.src = pz;
    	
    },
    createWindow1:function(){
//		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-delete",handler:function(){this.editWindow1.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:1350,
				height:600,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 

//					 	 "->",cancel

					 	

					 
					  ]
				},
				listeners:{
			 		hide:function(){
			 			this.editForm1.form.reset();
			 		},scope:this},
	     		items:[this.editForm1],
			 	title:"凭证大图查看"});		
    },
    	   
    createWindow:function(){
    	var _b_cancel = new Ext.Button({text:"通过",iconCls:"p-icons-save",handler:this.updateExaminee,scope:this});
    	var save = new Ext.Button({text:"不通过",iconCls:"p-icons-checkclose",handler:this.addRound,scope:this});
    	
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-delete",handler:function(){this.editWindow.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:900,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
					 	,"->",_b_cancel
			 			 ,"->",save
					  ]
				},
				listeners:{
			 		hide:function(){
			 			this.editForm.form.reset();
			 		},scope:this},
	     		items:[this.editForm],
			 	title:"审核管理"});		
    },
    addRound:function(){

  
    	var thiz = this;
    	this.editForm.$submit({
    		action:"bmsh_btg.do",
    		params: {
    			'bmlcid':bmlcid1		
    		},
    		
    		handler:function(form,result,thiz){
    			thiz.editWindow.hide();
    			thiz.grid.$load();
    			thiz.selectExamSubject();
    		},
    		scope:this
    	});
    },    
  

    update:function(){    	
    	var selectedXnxq = this.grid.getSelectionModel().getSelections();
    	if(selectedXnxq.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录进行审核！");
    		return;
	    }
    	var bmlcid = selectedXnxq[0].get("BMLCID");
    	bmlcid1 = bmlcid;
     	this.editWindow.show(null,function(){
    		this.editForm.$load({
    		  	params:{'bmlcid':bmlcid},
    		  	action:"bmsh_getlist.do",
    		  	handler:function(form,result,scope){  
    		  			
    		  		var url = "";
    				if(result.data.JFPZ==undefined){
    					url = "";
    				}else{
    					url = "uploadFile/certificates/"+result.data.JFPZ;
    					var objtype=url.substring(url.lastIndexOf(".")).toLowerCase();
    			
						if(/\.(zip|ZIP)$/.test(objtype)){
    						Ext.getCmp("jfpz").hide();
    						Ext.getCmp("download").show();
							Ext.getCmp("upload").hide();
//    						ckjfpz.hidden=true;
//    						download.hidden=false;
    						Ext.getCmp("ckjfpzpath").setValue(url);
    					}else{
    						Ext.getCmp("jfpz").show();
    						Ext.getCmp("download").hide();
    						Ext.getCmp("upload").show();
//    						ckjfpz.hidden=false;
//    						download.hidden=true;
    						pz=url;
    	    				Ext.getCmp("jfpz").getEl().dom.src = url;
    					}
    				}
    		  		form.findField("rs").setValue(result.data.SL);//人数
    		  		form.findField("lxr").setValue(result.data.LXR);//联系人
    		  		form.findField("lxdh").setValue(result.data.LXDH);//联系电话
    		  		var sl =result.data.SL;
    		  		var jf = sl *95;
    		  		form.findField("jfje").setValue(sl*95);//缴费金额
    		 	}
    		  		
    		 });
    	},this)
    },
    updateExaminee:function(){
  
    
    	var thiz=this;
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"是否通过审核",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,    			
    		fn:function(b){
    			if(b=='ok'){
    				JH.$request({
    					params:{'bmlcid':bmlcid1},
    					action:"bmsh_update.do",
    					handler:function(){
    						thiz.grid.$load();
    						thiz.editWindow.hide();
    						thiz.selectExamSubject();
    					}
    				}) 

    				
    			}
    		}
    	})
    },
   
    updateZt:function(){
    	var selectedXnxq = this.grid.getSelectionModel().getSelections();
    	if(selectedXnxq.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录取消上报！");
    		return;
	    }
    	var bmlcid = selectedXnxq[0].get("BMLCID");
    	var thiz = this;
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"您确定要取消上报吗？",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,
    		fn:function(b){
    			if(b=='ok'){
    				Ext.Ajax.request({
    					url:'bmsh_updateZt.do',
    		       		params:{'bmlcid':bmlcid},
    		       		success: function(resp,opts) {
    		       			var respText = Ext.util.JSON.decode(resp.responseText);
    		       			if (respText.msg!='') {
    		       				Ext.MessageBox.alert("提示",respText.msg);
    		       			}
    		       			thiz.grid.$load();
    		       			thiz.selectExamSubject();
    		       		},
    		       		failure: function(resp,opts){
    		       			Ext.Msg.alert('错误', "取消上报失败！");
    		       		}
    				});
    			}
    		}
    	})
    },
    
    updateBm:function(){
    	var selectedXnxq = this.grid.getSelectionModel().getSelections();
    	if(selectedXnxq.length != 1){
    		Ext.MessageBox.alert("消息","请选择一条记录取消报名！");
    		return;
	    }
    	var bmlcid = selectedXnxq[0].get("BMLCID");
    	var thiz = this;
    	Ext.MessageBox.show({
    		title:"消息",
    		msg:"您确定要取消报名吗？",
    		buttons:Ext.MessageBox.OKCANCEL,
    		icon:Ext.MessageBox.QUESTION,
    		fn:function(b){
    			if(b=='ok'){
    				Ext.Ajax.request({
    					url:'bmsh_updateBm.do',
    		       		params:{'bmlcid':bmlcid},
    		       		success: function(resp,opts) {
    		       			var respText = Ext.util.JSON.decode(resp.responseText);
    		       			if (respText.msg!='') {
    		       				Ext.MessageBox.alert("提示",respText.msg);
    		       			}
    		       			thiz.grid.$load();
    		       			thiz.selectExamSubject();
    		       		},
    		       		failure: function(resp,opts){
    		       			Ext.Msg.alert('错误', "取消报名失败！");
    		       		}
    				});
    			}
    		}
    	})
    },
    
    exporXls:function(){
        Ext.Msg.wait("正在导出","提示");
        Ext.Ajax.request({
            url:"bmsh_dao.do",
            params:{
            	"xn":xn1,"ckdw":ckdw1,"mc":mc1,"zkdw":zkdw1,"fpzt":fpzt1
            },
            scope:this,
            success: function (r, options) {
                var result =Ext.decode(r.responseText);
                if(result.success){
                    Ext.Msg.alert("提示","导出成功!");
                    window.open(Ext.get("ServerPath").dom.value+"/"+result.data.replace(/\\/g,"\/"));
                }else{
                    Ext.Msg.alert("提示",result.msg);
                }
            },
            failure: function (response, options) {
            }
        });
    },
    selectExamSubject:function(){
		var xn=Ext.getCmp('revxnxq_find').getValue();
		var ckdw=Ext.getCmp('ckdw_find').getValue();
		var mc=Ext.getCmp('mc').getValue();
		var zkdw = Ext.getCmp('zkdw_find').getValue();
		var fpzt =  Ext.getCmp('fpzt').getValue();
		 xn1=xn;
		 ckdw1=ckdw;
		 mc1=mc;
		 zkdw1 = zkdw;
		 fpzt1 =  fpzt;
		this.grid.$load({"xn":xn,"ckdw":ckdw,"mc":mc,"zkdw":zkdw,"fpzt":fpzt});
	},
	downloadZip:function(){
		  var path = Ext.get("ServerPath").dom.value +"/"+ Ext.getCmp("ckjfpzpath").getValue();
		  window.open(path);
		  this.editWindow.hide();
	},

});



function checkDate(sdate,edate){
	var strs= new Array(); //定义一数组 
	if(sdate.getTime() > edate.getTime()){
    	return false;
	}else{
    	return true;
	}	
}