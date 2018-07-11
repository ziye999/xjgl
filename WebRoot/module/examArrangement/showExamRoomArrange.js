var kcapzgz = "kcapzgz1";
var wskc = 0;
var zwpl = "zwpl1";
var pksx = "pksx1";
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
		//this.mainPanel = this.createMainExamRoomPanel();
   		this.arrangePanel = this.createArrangePanel();
   		this.tabPanel = new Ext.TabPanel({   
            activeTab: 0,
       		headerStyle: 'display:none',
       		border:false,
            items: [this.arrangePanel]   
        }); 
	},
	createArrangePanel:function(){
	    this.show_panel=new Ext.Panel({
    		id:"show_panel",
		    region:"center",
		    html:"",
		    width:"auto",
		    height:600,
		    border:false
    	});
		
		this.context_panel=new Ext.Panel({
    		id:"context_panel",
		    region:"center",
		    tbar:[ 
			  "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}
			],
		    items:[this.show_panel],
		    width:"auto",
		    height:600,
		    border:true
    	});
    	
		//教学楼
    	var store = new Ext.data.JsonStore({
    		url : 'dropListAction_jiaoXueLou.do',
    		fields : ['CODEID', 'CODENAME']
    	});
    	store.reload();
		var jxl_find = new Ext.ux.Combox({width:170,id:"jxl_find",store:store});
		//考点名称  
		var kdmc_find = new Ext.ux.Combox({dropAction:"kaoDianMc",allowBlank:false, width:170,id:"kdmc_find",params:getLocationPram("lcid"),
			listeners:{
				"select":function(combo,record,number){    //监听"select"事件
					var id=combo.getValue();           //取得ComboBox0的选择值
					var newStore = new Ext.data.JsonStore({
										autoLoad:false,
										url:'dropListAction_jiaoXueLou.do?params='+id,
										fields:["CODEID","CODENAME"]
					});
					Ext.getCmp("jxl_find").clearValue(); 
					Ext.getCmp("jxl_find").store=newStore;  
					newStore.reload();
					Ext.getCmp("jxl_find").bindStore(newStore);
				},
				scope:this
			}
		});
		
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamRoom,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.examRoomSearch.getForm().reset()},scope:this});
				
		this.examRoomSearch = new Ext.form.FormPanel({
			region: "north",
			height:90,
			items:[{  
				layout:'form',  
				xtype:'fieldset',  
				style:'margin:10',
				title:'查询条件',  
				items: [{
                    	xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 5
						}, 
						baseCls:"table",  
						items:[
						    {html:"考点：",baseCls:"label_right",width:140},
							{items:[kdmc_find],baseCls:"component",width:190},
							{html:"楼房：",baseCls:"label_right",width:140},
							{items:[jxl_find],baseCls:" component",width:190},
							{layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:190}
						] 
                    }]  
		       }]  
	    	});
		return new Ext.Panel({
    		id:"examRoomPanel",
    		title:"考场安排",
    		region:"north",
    		height:500,
    		width:"auto",
    		layout:"border",
    		border:false,
    		items:[this.examRoomSearch,this.context_panel]
    	});
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.addPanel({layout:"fit",items:[this.tabPanel]});
    },
    initQueryDate:function(){
    	
    },
	returnMain:function(){
		window.location.href = Ext.get("ServerPath").dom.value+"/jsp/main.jsp?module=000404";
	},
	examRoomArrange:function(){
		var thiz=this;
		Ext.Ajax.request({
			url: 'examroomarrange_arrangeStu.do',
		   	success: function(response,options){
		   		var msginfo=response.responseText;
		   		if(msginfo!="success")
		   			Ext.Msg.alert("错误",msginfo);
		   		else{
		   			Ext.Msg.alert("提示","保存成功！");
		   			thiz.tabPanel.setActiveTab(thiz.arrangePanel);
		   		}
		   	},
		   	failure: function(resp,opts) {
		        Ext.Msg.alert('错误', "出错了！");
		    },
		   	params: {
		   		'kcapzgz':kcapzgz,
				'wskc':Ext.getCmp('wskc').value,
				'zwpl':zwpl,
				'pksx':pksx,
				'lcid':getLocationPram("lcid")
			}
    	});
	},
	//查询教室
	selectExamRoom:function() {
		var kdmc_id = Ext.getCmp("kdmc_find").getValue();
		var jxl_id = Ext.getCmp("jxl_find").getValue();
		if(kdmc_id == "") {
			Ext.MessageBox.alert("提示","请选择考点！");
    		return;
		}
		if(jxl_id == "") {
			Ext.MessageBox.alert("提示","请选择楼房！");
    		return;
		}
		var iframe="<fieldset style='height:100%; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReportS' name='frmReportS' width='100%' height='800' src='subExamRoomArrange_getExamRoomArrange.do?lcId="+
						getLocationPram("lcid")+"&lfid="+jxl_id+"&kdid="+kdmc_id+"' frameborder='0' scrolling='auto'></iframe>"+
					"</fieldset>";
		Ext.getDom("show_panel").innerHTML=iframe;
		//安排	
	},
	fanhui:function(){
		window.location.href = Ext.get("ServerPath").dom.value+"/jsp/main.jsp?module=000404";
	}
});
