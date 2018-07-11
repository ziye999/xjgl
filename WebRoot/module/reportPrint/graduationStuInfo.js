var roomcode_b = "";
Ext.extend(system.application.baseClass,{
	/** 初始化 **/
    init: function(){
	    this.initDate();
    },
    /** 初始化页面、内存等基本数据 **/
    initDate:function(){
    	this.findPanel();
	  	this.initComponent();
		this.initListener();
		this.initFace();
		this.createWindow();
    },    
    /** 对组件设置监听 **/
    initListener:function(){
    	var thiz=this;
    	this.orgTree.on("click",function(node){
    		if(node.id!="root"){
	    		thiz.menuToSwitch();
	    		thiz.node=node.id;
	    		switch(node.id){
	    			case '01':
	    				Ext.getCmp("centerPanel").setTitle("考试不达标换证统计");
	    				break;
	    			case '02':
	    				Ext.getCmp("centerPanel").setTitle("综合素质不达标换证统计");
	    				break;
	    			case '03':
	    				Ext.getCmp("centerPanel").setTitle("未达到144个学分换证统计");
	    				break;
	    			case '04':
	    				Ext.getCmp("centerPanel").setTitle("不通过申请统计");
	    				break;
	    		}
    		}
    	},this.grid);
    },   
   	initComponent :function(){
   		var root=new Ext.tree.TreeNode({
   			expanded: true,
   			id:'root',
   			text:'统计表'
   		});
   		/*root.appendChild(new Ext.tree.TreeNode({
   			id:'01',
   			text:'考试不达标换证统计'
   		}));
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'02',
   			text:'综合素质不达标换证统计'
   		}));
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'03',
   			text:'未达到144个学分换证统计'
   		}));*/
   		root.appendChild(new Ext.tree.TreeNode({
   			id:'04',
   			text:'不通过申请统计'
   		}));
   		this.orgTree = new Ext.tree.TreePanel({
   						 region:"west",
			             rootVisible:true,
			             title:"不通过考生信息统计",
						 collapseMode : "mini",
			             split:true,
			             minSize: 120,
			             width:200,
			             maxSize: 300,
			             autoScroll:true,
			             root:root
   		});
   		var iframe="<fieldset style='height:453; width:100%; border:0px; padding:0;background:#fff'>"+
						"<iframe name='frmReport1' width='100%' height='453' frameborder='0' "+
						"scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
   		var dataPanelShow=new Ext.Panel({
    		region:'center',
    		border:false,
   			id:"dataPanelShow1",
   			html:iframe
   		});
   		this.dataPanel=new Ext.Panel({
    		region:'center',
    		border:false,
    		layout:"border",
    		tbar:[ 
    		      "->",{xtype:"button",text:"打印",iconCls:"p-icons-print",handler:function(){this.printResultsData()},scope:this}
    		     ],
   			items:[dataPanelShow]
   		});
		this.grid = new Ext.Panel({
			title:"考试不通过统计",			
			region:"center",
			border:true,
			layout:"border",
			items:[this.search,this.dataPanel]
		});
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({id:"panel",layout:"border",
    		items:[this.orgTree,{
			layout: 'border',
	        region:'center',
	        border: false,
	        split:true,
			margins: '2 0 5 5',
	        width: 275,
	        minSize: 100,
	        maxSize: 500,
			items: [this.grid]
		}]});		
    },
    //初始化弹窗
    createWindow:function(){
    	//初始化轮次弹窗
    },
    findPanel:function(){
    	//组织单位
    	var jyjg_find= new Ext.ux.form.OrganField({name:"jyjg_find",readOnly:true});//组织单位
		var cx = new Ext.Button({x:22,region:"west",cls:"base_btn",text:"查询",handler:this.selectResults,scope:this});
		var cz = new Ext.Button({x:96,region:"center",cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		this.search = new Ext.form.FormPanel({
			id:"search_form7",
			region: "north",
			height:90,
			border:false,
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
								{html:"组织单位：",baseCls:"label_right",width:100},
								{items:[jyjg_find],baseCls:"label_center",width:180},													
								{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:180,height:20}							
							] 
	             	}]  
			  	}]  
		    })	    
    },
    selectResults:function(){
    	var jyjg=this.search.getForm().findField("jyjg_find").getCodes();//combo.getValue();取得ComboBox0的选择值
    	/*if(jyjg==null){
    		Ext.MessageBox.alert("提示","请选择组织单位！");
    		return;
    	};*/    	
		var src="";		
		if(this.node=="01"){
			src="reportprint_graduationStuInfo.do?schoolid="+(jyjg==undefined?"":jyjg);
		}else if(this.node=="02"){
			src="reportprint_qualityStuInfo.do?schoolid="+(jyjg==undefined?"":jyjg);
		}else if(this.node=="03"){
			src="reportprint_creditsStuInfo.do?schoolid="+(jyjg==undefined?"":jyjg);
		}else if(this.node=="04"){
			src="reportprint_yiyeStuInfo.do?schoolid="+(jyjg==undefined?"":jyjg);
		}else{
			Ext.MessageBox.alert("提示","请选择统计类型！");
    		return;
		}

        //var height=Ext.getCmp('dataPanelShow').getHeight()-4;
        var height=330;//Ext.getCmp('dataPanelShow').getHeight()-4;
  		var iframe="<fieldset style='height:253; width:100%; border:0px; padding:0;background:#fff'>"+
						"<iframe name='frmReport1' width='100%' height='"+height+"' src='"+src+
						"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
					"</fieldset>";
    	Ext.getDom('dataPanelShow1').innerHTML=iframe;
    },
    printResultsData:function(){
    	//打印
    	frmReport1.print();
    },
    menuToSwitch:function(){
    	//搜索区还原
    	Ext.getCmp('search_form7').getForm().reset();
	    var iframe="<fieldset style='height:453; width:100%; border:0px; padding:0;background:#fff'>"+
				  		"<iframe name='frmReport1' width='100%'frameborder='0' scrolling='auto'></iframe>"+
				   "</fieldset>";
	    //打印区还原
	    Ext.getDom('dataPanelShow1').innerHTML=iframe;    
    }    
});
