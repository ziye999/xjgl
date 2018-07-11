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
    	
    },
    
	 /** 对组件设置监听 **/
    initListener:function(){
    },
   
   	initComponent :function(){
		this.grids = new Ext.Panel({
			html: "<img src='img/20150721113841.gif'>"
		});
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel(this.grids);
    	
    }
});