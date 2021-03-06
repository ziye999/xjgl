
var xn1="";
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
   
   	
   		//1.CheckboxSelectionModel简写sm 2.实现sm是否显示
   		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
   		//JTDM空字段
		var cm = [sm, 
		          	{header : "证件编号",align : "center",sortable : true,dataIndex : "JTDM"},
		      		{header : "证件类型",align : "center",sortable : true,dataIndex : "JTDM"},
		          	{header : "姓名",align : "center",sortable : true,dataIndex : "XM"},
		          	{header : "身份证号码",align : "center",sortable : true,dataIndex : "SFZJH"},
		          	{header : "性别",align : "center",sortable : true,dataIndex : "xbm"},
		          	{header : "职务",align : "center",sortable : true,dataIndex : "ZW"},
		          	{header : "执法类别",align : "center",sortable : true,dataIndex : "zflx"},
		          	{header : "执法范围",align : "center",sortable : true,dataIndex : "zffw"},
		          	{header : "工作单位",align : "center",sortable : true,dataIndex : "XXMC"},
					{header : "发证机关",align : "center",sortable : true,dataIndex : "fzdw"},
					{header : "发证日期",align : "center",sortable : true,dataIndex : "JTDM"},
					{header : "有效期",align : "center",sortable : true,dataIndex : "JTDM"},
					{header : "民族",align : "center",sortable : true,dataIndex : "MZM"},
					{header : "政治面貌",align : "center",sortable : true,dataIndex : "ZZMM"},
					{header : "文化程度",align : "center",sortable : true,dataIndex : "WHCD"},
					{header : "所属法制办",align : "center",sortable : true,dataIndex : "SSZGJYXZMC"},
					{header : "年度",align : "center",sortable : true,dataIndex : "XN"},
		      		{header : "季度",align : "center",sortable : true,dataIndex : "xq"}
				];
				 
		this.grid = new Ext.ux.GridPanel({
					cm : cm,
					sm : sm,
					tbar:{
						cls:"ext_tabr",
						items:[ 
						       "->",{xtype:"button",text:"导出xls",id:"sb", iconCls:"p-icons-save",handler:this.exporXls,scope:this}
						      ]
					},
					title : "考试成绩", 		
				    page:true,
				    excel:true,
				    rowNumber:true,
				    region:"center",
				    excel:true,
					action : "cj_getPage.do",
					
					fields : ["XM", "SFZJH", "xbm", "ZW","zflx","zffw","XXMC","fzdw","MZM","ZZMM","WHCD","SSZGJYXZMC","nj","ZY","XN","xq","JTDM"],
					border : false

				
				});
		var xnxq =new Ext.ux.form.XnxqField({width:190,id:"revxnxq_find",readOnly:true});
		
//		var mc = new Ext.form.ComboBox({
//			id:'mc',
//			mode: 'local', 
//			triggerAction: 'all',   
//			editable:false,
//			width:180,
//			value:"3",
//			store: new Ext.data.ArrayStore({
//				id: 0,
//				fields: ['value','text'],
//			    data: [['0', '全部'],['1', '通过'],['2', '不通过'],['3', '已缴费'],['4','已上报']]
//			}),
//			valueField: 'value',
//			displayField: 'text'
//		});
	     
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
						{items:[xnxq],baseCls:"component",width:210},
					
				
						{layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:160}
					] 
				}]  
			}]  
    	})
	  return new Ext.Panel({layout:"border",region:"center",items:[this.search,this.grid]})
	},

   
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"border",region:"center",items:[this.search,this.grid]});
    },
    initQueryDate:function(){
    	this.grid.$load();
    },
  
   
   exporXls:function(){
        Ext.Msg.wait("正在导出","提示");
        Ext.Ajax.request({
            url:"cj_daochu.do",
            params:{
            	"xn":xn1
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
		xn1 = xn;
		this.grid.$load({"xn":xn});
	},
});



