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
		//搜索区域
   		var dataPanel=new Ext.Panel({
    		region:'center',
    		border:true,
   			id:"dataPanelEC",
   			html:"",
   			bodyStyle:"border:2px red solid"
   		});
		var print	  = new Ext.Button({text:"打印",iconCls:"p-icons-print",handler:this.printExamInfo,scope:this});
		var fanhui = new Ext.Button({text:"返回",iconCls:"p-icons-reply",handler:this.returnMain,scope:this});
		this.mainPanel = new Ext.Panel({
	    		id:"MainExamRoomPanel",
	    		region:"north",
	    		tbar:[ 
				  "->",fanhui,
				  "->",print
			  	],
	    		height:500,
	    		width:"auto",
	    		layout:"border",
	    		border:true,
	    		items:[dataPanel]
	    	});
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"fit",items:[this.mainPanel]});
    },
    initQueryDate:function(){
    	var bjid = getLocationPram("bjid");
    	var lcid = getLocationPram("lcid");
//    	alert(bjid+","+lcid);
    	var height=Ext.getCmp('dataPanelEC').getHeight()-4;
    	var iframe="<fieldset style='height:348; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReportI' name='frmReportI' width='100%'"+
								"height='"+height+"' src='examInfomation_getExamInfomationByBj.do?lcid="+lcid+"&bjid="+bjid+"' frameborder='0' scrolling='auto'"+
								"style='border:1px dashed #B3B5B4;' ></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanelEC').innerHTML=iframe;
    },printExamInfo:function(){
    	frmReportI.print();
    },returnMain:function(){
    	window.location.href = Ext.get("ServerPath").dom.value+"/jsp/main.jsp?module=000304";
    }
}
);

