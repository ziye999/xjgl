Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
	    this.initDate();
		this.initComponent();
		this.initListener();
		this.initFace();
    },
    /** 初始化页面、内存等基本数据 **/
    initDate:function(){
   	 this.lowChar = 101;
   	 this.upprChar = 69;
   	 this.n = 3;
   	 this.flag2 = true; 
   	 
    },   
    /** 对组件设置监听 **/
    initListener:function(){
    	var that =this; 
    	this.grid.getStore().on("beforeload",function(t, o){
    		t.setBaseParam("roomid",that.kch.getValue());  
    		if(that.examdate.getValue()){
    			t.setBaseParam("examdate",that.examdate.getValue().format('Y-m-d')); 
    		}else{
    			t.setBaseParam("examdate","");
    		}
    	});
    },   
   	initComponent :function(){
   		var that = this;
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,			 
			{header: "考场名称",  width:150, align:"center", sortable:true, dataIndex:"ROOMNAME"},
			{header: "考试日期",   width:150,align:"center", sortable:true, dataIndex:"EXAMDATE"},
			{header: "考试开始时间",   width:150,align:"center", sortable:true, dataIndex:"starttime"}, 
			{header: "考试结束时间",   width:150,align:"center", sortable:true, dataIndex:"ENDTIME"},
			{header: "下载试卷状态", width:150,  align:"center", sortable:true, dataIndex:"DOWN_STATUS"},
			{header: "提交试卷状态", width:150,  align:"center", sortable:true, dataIndex:"SUBMIT_STATUS"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"考场进度", 
			page:true,
			rowNumber:true,
			region:"center",
			excel:false,
			pdf:false,
			excelTitle:"考场进度",
			action:"roomSubject_getListPage.do",
			fields :["ROOMNAME","EXAMDATE","starttime","ENDTIME","DOWN_STATUS","SUBMIT_STATUS"],
			border:false,
			viewConfig : {
		          forceFit : false,  //false表示不会自动按比例调整适应整个grid，true表示依据比例自动智能调整每列以适应grid的宽度，阻止水平滚动条的出现。dataCM(ColumnModel)中任意width的设置可覆盖此配置项。
		          autoFill : false   //false表示按照实际设置宽度显示每列，true表示当grid创建后自动展开各列，自适应整个grid.且，还会对超出部分进行缩减，让每一列的尺寸适应grid的宽度大小，阻止水平滚动条的出现。
		       } 
		});
		//搜索区域
		this.kch	=new Ext.form.ComboBox({ 
			width:200,
    		triggerAction : 'all',  
    		emptyText : '请选择',  
			mode: 'remote',
			lazyInit:true,
			editable:false, 
			store: new Ext.data.JsonStore({  
		        proxy:new Ext.data.HttpProxy({
		        	 method:'post',
		        	 url : 'roomSubject_getRoom.do'
		        }),
		        fields : ['text', 'value']
		    }),
			valueField: 'value',
		    displayField: 'text'
		}); 
		 
		this.examdate = new Ext.form.DateField({name:"examdate",editable:false,width:140,format:"Y-m-d"}); 
		
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:function(){this.grid.$load();},scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.search = new Ext.form.FormPanel({
			region: "north",
			height:100,
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
							{html:"科目：",baseCls:"label_right",width:170}, 
							{items:[this.kch],baseCls:"component",width:210}, 
							{html:"考试日期：",baseCls:"label_right",width:170}, 
							{items:[this.examdate],baseCls:"component",width:210}, 
							{layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
						] 
                    }]  
		       }]  
	    	})
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"border",
    		items:[{
			layout: 'border',
	        region:'center',
	        border: false,
	        split:true,
			margins: '2 0 5 5',
	        width: 275,
	        minSize: 100,
	        maxSize: 500,
			items: [this.search,this.grid]
		}]});
    	this.grid.$load(); 
    },
    initQueryDate:function(){    	
     
    }, 
   
});