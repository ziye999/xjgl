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
   		var dataPanelShow=new Ext.Panel({
    		region:'center',
    		border:false,
   			id:"dataPanelShow"
   		});
   		this.dataPanel=new Ext.Panel({
   			title:'无照片考生信息',
    		region:'center',
    		border:false,
    		layout:"border",
    		tbar:[ 
    		      "->",{xtype:"button",text:"打印",iconCls:"p-icons-print",handler:function(){this.printResultsData()},scope:this}
    		     ],
   			items:[dataPanelShow]
   		});
		
   		var organ_sel	= new Ext.ux.form.GradeClassField({name:"organ_sel",width:210});
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
						       {html:"参考单位：",baseCls:"label_right",width:140},
						       {items:[organ_sel],baseCls:"component",width:210},
						       {layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:160}
						      ] 
                  	}]  
		      	}]  
	    })
		this.grid = new Ext.Panel({
			title:"优秀率统计",
			region:"center",
			border:true,
			layout:"border",
			items:[this.search,this.dataPanel]
		});
	},		
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"border",items:[this.search,this.dataPanel]});    	
    },
    initQueryDate:function(){
    	var height=330;//Ext.getCmp('dataPanel').getHeight()-35;
    	var iframe="<fieldset style='height:403; width:100%; border:0px; padding:0;background:#fff'>"+
    					"<iframe name='frmReportN' width='100%' height='"+height+
    					"' src='reportprint_studentNotPhotoInfo.do' frameborder='0' scrolling='auto'"+
						"style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanelShow').innerHTML=iframe;    	
    },
    selectExamSubject:function(){
    	var xxdm=this.search.getForm().findField('organ_sel').getCodes();
    	var height=330;//Ext.getCmp('dataPanel').getHeight()-35;
    	if (xxdm==undefined) {
    		xxdm = "";
    	}
  		var iframe="<fieldset style='height:403; width:100%; border:0px; padding:0;background:#fff'>"+
						"<iframe name='frmReportN' width='100%' height='"+height+
						"' src='reportprint_studentNotPhotoInfo.do?xxdm="+xxdm+
						"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanelShow').innerHTML=iframe;
    },
    printResultsData:function(){
    	frmReportN.print();
    }
});
