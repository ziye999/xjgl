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
    	this.context_panel=new Ext.Panel({
    		id:"context_panel1",
		    region:"center",
		    html:"",
		    width:"auto",
		    height:600,
		    border:true
    	})
		//搜索区域
    	var kd_findT = new Ext.ux.Combox({id:"kd_findT",name:"kd_findT",width:170});
    	var lc_find	= new Ext.ux.Combox({width:170,name:"lc_find",listeners:{
			"select":function(){
				var lcid=this.getValue();
				var newStore1 = new Ext.data.JsonStore({
					autoLoad:false,
					url:'dropListAction_kaoDianMc.do?params='+lcid,
					fields:["CODEID","CODENAME"]
				});
				Ext.getCmp("kd_findT").clearValue();  
				Ext.getCmp("kd_findT").store=newStore1;  
				newStore1.reload();
				Ext.getCmp("kd_findT").bindStore(newStore1); 
				               
			}
		}});
		var xnxq_find=new Ext.ux.form.XnxqField({width:180,id:"timexnxq_find",readOnly:true,
			callback:function(){//监听"select"事件
				var id=Ext.getCmp("timexnxq_find").getCode();//取得ComboBox0的选择值
				var newStore = new Ext.data.JsonStore({
									autoLoad:false,
									url:'dropListAction_examround.do?params='+id,
									fields:["CODEID","CODENAME"]
								});
				lc_find.clearValue(); 
				lc_find.store=newStore;  
				newStore.reload();
				lc_find.bindStore(newStore);				
				Ext.getCmp("kd_findT").clearValue();
				Ext.getCmp("kd_findT").store.removeAll();
			}
		});		
		var scmb = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"生成模板",handler:this.createTempalte,scope:this});
		var xzmb = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"下载模板",handler:this.downLoadTemplate,scope:this});
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamRound,scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.search = new Ext.form.FormPanel({
			region: "north",
			height:130,
			items:[{  
				layout:'form',  
				xtype:'fieldset',  
				style:'margin:10',
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
							{items:[xnxq_find],baseCls:"component",width:180},
							{html:"考试名称：",baseCls:"label_right",width:120},
							{items:[lc_find],baseCls:"component",width:180},
							{html:"考点：",baseCls:"label_right",width:120},
							{items:[kd_findT],baseCls:"component",width:180},
							{layout:"absolute",items:[scmb,xzmb],baseCls:"component_btn",width:160},
							{layout:"absolute",items:[cx,cz],baseCls:"component",width:180}
						] 
                    }]  
		       }]  
	    	});
	    	
	    	this.panel=new Ext.Panel({
	    		region:"north",
	    		width:"auto",
	    		layout:"border",
	    		border:false,
	    		items:[this.search,this.context_panel]
	    	});	    	
	},
    /** 初始化界面 **/
    initFace:function(){
    	this.panel_top=new Ext.Panel({
    			layout:"fit",
	    		region:"north",
	    		border:false,
	    		items:[this.panel]
	    });
    	this.addPanel({layout:"fit",items:[this.panel_top]});
    },
    initQueryDate:function(){
    	//this.grid.$load();
    },
    selectExamRound:function(){
    	var xnxq=Ext.getCmp("timexnxq_find").getValue();
    	if(xnxq==""){
    		Ext.MessageBox.alert("提示","请选择年度！");
    		return;
    	}
    	var lc=this.search.getForm().findField("lc_find").getValue();
    	if(lc==""){
    		Ext.MessageBox.alert("提示","请选择考试名称！");
    		return;
    	}
    	var kd=this.search.getForm().findField("kd_findT").getValue();
    	var iframe="<fieldset style='height:100%; width:100%; border:0px; padding:0;'>"+
						"<iframe id='frmReportM' name='frmReportM' width='100%' height='450' src='examtimearrange_goExamTimeArrange.do?lcId="+lc+"&kd="+kd+
						"' frameborder='0' scrolling='auto'></iframe>"+
					"</fieldset>";
    	Ext.getDom("context_panel1").innerHTML=iframe;    	
    },
    createTempalte:function(){
    	//生成模板
    	var xnxq=Ext.getCmp("timexnxq_find").getValue();
    	if(xnxq==""){
    		Ext.MessageBox.alert("提示","请选择年度！");
    		return;
    	}
    	var lc=this.search.getForm().findField("lc_find").getValue();
    	if(lc==""){
    		Ext.MessageBox.alert("提示","请选择考试名称！");
    		return;
    	}
    	Ext.Ajax.request({    	   
    		url: 'examtimearrange_createTemplate.do',
    		params: {    
    			'lcid':lc
    		},    
    		success: function(response, options) {    
    			//获取响应的json字符串    
    			var responseArray = Ext.util.JSON.decode(response.responseText);  
				Ext.Msg.alert('消息',responseArray.msg);          
    		}  
		});
    },
    downLoadTemplate:function(){
		var xnxq=Ext.getCmp("timexnxq_find").getValue();
    	if(xnxq==""){
    		Ext.MessageBox.alert("提示","请选择年度！");
    		return;
    	}
    	var lc=this.search.getForm().findField("lc_find").getValue();
    	if(lc==""){
    		Ext.MessageBox.alert("提示","请选择考试名称进行下载！");
    		return;
    	}
		var url = Ext.get("ServerPath").dom.value + "/export/excel/TimeArrangeTemplate/"+lc+".xls";
		Ext.Ajax.request({    	   
			url: 'examtimearrange_checkFile.do',
			params: {    
				'lcid':lc
			},    
			success: function(response, options) {     
				var responseArray = Ext.util.JSON.decode(response.responseText);  
				if(responseArray.success==true){    
					location.href = url; 
				}else{    
					Ext.Msg.alert('消息','该考试没有模版，请先生成模版');        
				}    
			}    
		}); 
	}    
});