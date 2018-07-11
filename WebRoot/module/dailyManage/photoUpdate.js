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
    	this.initForm();
    },    
  	initComponent :function(){   
  		this.ds =  new Ext.data.Store({ 
  			reader:new Ext.data.JsonReader({
  				fields: ["WTSM"]
  			})
  		});
  		this.griddata = new Ext.grid.GridPanel({
  			store:this.ds,
  			colModel: new Ext.grid.ColumnModel({
  				defaults: {
  					width: 600,
  					sortable: true
  				},
  				columns: [ 
  				          {header: '采集问题',  dataIndex: 'WTSM'}
  				         ]
  			})
  		});
   		this.sidePanel = new Ext.Panel({
   			region:'center',
    		border:'border',
    		id:'sidePanel',
    		height:538,
    		layout:'fit',
    		style:'background-color:#B2E0F9',
   			tbar:["->",{html:"",height:20}],
   			items:[this.griddata]				  
   		}); 
		this.grid = new Ext.Panel({
			id:"gridid",
			region:"center",
			border:true,
			height:700,
			items:[this.search,this.sidePanel]
		}); 
	}, 
	/** 对组件设置监听 **/
    initListener:function(){
    	var thiz = this; 
    },   	
    /** 初始化界面 **/
    initFace:function(){ 
    	this.addPanel(this.grid);
    },    
    initForm:function(){  
    	var thiz = this; 
		var cx1 = new Ext.Button({cls:"base_btn",text:"上传",handler: function() {			
			var form = Ext.getCmp('search_form4').getForm();
			var filePath     = form.findField("upload").getRawValue();
			//判断文件类型
			var objtype=filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
			if(filePath==""){ 
				alert("请选择文件！");return false;
			}else if(objtype!='.zip'){  
				alert("文件类型必须是*.zip!")
				return false; 
			} 	     
			if (form.isValid()) {                	
				form.submit({
					url : 'photo_saveUpFileInfo.do',
					params:{ 
						form:form.findField("upload")
					},
					waitMsg : '正在上传您的文件（注意上传压缩包不能太大），请耐心等候...',
					success : function(form,action) {   
						Ext.Msg.alert('提示信息', action.result.msg);
						thiz.ds.loadData(action.result.data);
					},
					failure : function(data) {
						Ext.Msg.alert("提示信息", "对不起，文件保存失败");
					}
				});
			}                
		},scope:this});  
		this.search = new Ext.form.FormPanel({
				fileUpload: true,
				id:"search_form4",
				region: "north",
				height:80,
				border:false,
				enctype: 'multipart/form-data',
				items:[{  
					layout:'form',  
					xtype:'fieldset',  
					style:'margin:10 10', 
					items: [{ 
						xtype:"panel",
						layout:"table", 
						layoutConfig: { 
							columns: 3
						}, 
						baseCls:"table",
						items:[  
						       {html:"选择ZIP文件：",baseCls:"label_right",width:120},
						       {items:[{fieldLabel:"附件",xtype:"textfield",name:"upload",inputType:'file'}],baseCls:"component",width:260},
						       {items:[cx1],baseCls:"component_btn",width:150,height:20}				
						      ] 
                    }]  
				}]           
	    });	 
    } 
});
