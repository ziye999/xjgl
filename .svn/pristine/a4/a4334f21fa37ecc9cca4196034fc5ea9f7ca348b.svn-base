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
   	 this.opt='add';
   	 
    },   
    /** 对组件设置监听 **/
    initListener:function(){
    	var that =this;
    	this.eidtWindow.on("hide",function(){
    		this.flag2=true;
    		that.stPanel.show();
			that.xxPanel.hide();
    		this.editForm.$reset();    		
    	},this);
    	this.grid.getStore().on("beforeload",function(t, o){
    		t.setBaseParam("kch",that.kch.getValue());
    		t.setBaseParam("stbh",that.stbh.getValue());
    		t.setBaseParam("exam_type_id",that.examtype.getValue());
//    		t.setBaseParam("examst_xnxq",that.xnxq_ser.getValue());
//    		t.setBaseParam("examst_ksmc",that.ksmc_ser.getValue());
//    		t.setBaseParam("examst_kskm",that.kskm_ser.getValue());
//    		params={"kch":that.kch.getValue(),"examst_xnxq":that.xnxq_ser.getValue(),
//        			"examst_ksmc":that.ksmc_ser.getValue(),"examst_kskm":that.kskm_ser.getValue()};
//    		Ext.apply(store.baseParams,params);
    	});
    	this.grid.on("rowdblclick",function(grid, rowIndex, e ){
    		this.setUpdateForm(grid,rowIndex,e);
    	},this);
    	this.bj.on("click",function(t){
    		if(t.text=='编辑选项'){
    			that.bj.setText("编辑试题");
    			that.stPanel.hide();
    			var addXx = new Ext.Button({name:"addXx",text:"添加选项",handler:that.addXxInfo,width:50,scope:that}); 
    			var delXx = new Ext.Button({name:"delXx",text:"删除选项",handler:that.delXxInfo,width:50,scope:that}); 
    			if(that.opt=='add'){
    				if(that.xxPanel.items.getCount()!=10 && that.flag2){
    					that.flag2 = false;
    					that.xxPanel.removeAll();
    					var xx_a  = new Ext.form.TextArea({name:"xx_a",id:"xx_a",blankText:"至少填入A项和B项！",width:480,height:40});
    					var xx_b  = new Ext.form.TextArea({name:"xx_b",id:"xx_b",blankText:"至少填入A项和B项！",width:480,height:40});
    					var xx_c  = new Ext.form.TextArea({name:"xx_c",id:"xx_c",width:480,height:40});
    					var xx_d  = new Ext.form.TextArea({name:"xx_d",id:"xx_d",width:480,height:40}); 
    					that.xxPanel.add({html:"选项A：",baseCls:"label_right",width:150});
    					that.xxPanel.add({html:"<font class='required'>*</font>",items:[xx_a],baseCls:"component",width:500});
    					that.xxPanel.add({html:"选项B：",baseCls:"label_right",width:150});
    					that.xxPanel.add({html:"<font class='required'>*</font>",items:[xx_b],baseCls:"component",width:500});
    					that.xxPanel.add({html:"选项C：",baseCls:"label_right",width:150});
    					that.xxPanel.add({items:[xx_c],baseCls:"component",width:500});
    					that.xxPanel.add({html:"选项D：",baseCls:"label_right",width:150});
    					that.xxPanel.add({items:[xx_d],baseCls:"component",width:500});
    					that.xxPanel.add({items:[addXx],baseCls:"component",width:120});
    					that.xxPanel.add({items:[delXx],baseCls:"component",width:120});
    					that.xxPanel.removeClass("table");
    					that.xxPanel.doLayout();
    				}/*else{
    					Ext.getCmp("xx_a").setValue("");
    					Ext.getCmp("xx_b").setValue("");
    					Ext.getCmp("xx_c").setValue("");
    					Ext.getCmp("xx_d").setValue("");
    				}*/
    			}else if(that.opt=='update'){
    				Ext.Ajax.request({
    	        		url:'examst_getOptInfo.do',
    	        		method:'post',
    	        		params:{stid:Ext.getCmp("stid").getValue()},
    	        		success: function(r, o) {
    	        			var li = Ext.decode(r.responseText);
    	        			var chr = 97;
    	        			var upChr = 65;
    	        			that.xxPanel.removeAll();
    	        			for ( var i = 0; i < li.length; i++) {
    	        				that.xxPanel.add({id:"XX_"+String.fromCharCode(upChr),html:"选项"+String.fromCharCode(upChr)+"：",baseCls:"label_right",width:150});
    	        				if(chr==97 || chr == 98){
    	        					that.xxPanel.add({html:"<font class='required'>*</font>",items:[new Ext.form.TextArea({name:"xx_"+String.fromCharCode(chr),id:"xx_"+String.fromCharCode(chr),width:480,height:40})],baseCls:"component",width:500,id:"xxa_"+String.fromCharCode(chr)});
    	        				}else{
    	        					that.xxPanel.add({items:[new Ext.form.TextArea({name:"xx_"+String.fromCharCode(chr),id:"xx_"+String.fromCharCode(chr),width:480,height:40})],baseCls:"component",width:500,id:"xxa_"+String.fromCharCode(chr)});
    	        				}
    	        				Ext.getCmp("xx_"+String.fromCharCode(chr)).setValue(li[i]);
    	        				chr++;
    	        				upChr++; 
    						}
    	        			that.lowChar = chr;
    	        			that.upprChar= upChr;
    	        			that.xxPanel.add({items:[addXx],baseCls:"component",width:120});
        					that.xxPanel.add({items:[delXx],baseCls:"component",width:120});
        					that.xxPanel.removeClass("table");
        					that.xxPanel.doLayout();
    	        		},
    	        		failure: function(r, o) {
    	        			Ext.MessageBox.alert("消息","获取选项失败");
    	        		}
    	        	});
    			}
    			that.xxPanel.show();
    		}else{
    			that.bj.setText("编辑选项");
    			that.stPanel.show();
    			that.xxPanel.hide();
    		}
    	});
    },   
   	initComponent :function(){
   		var that = this;
   		this.editForm   = this.createExamstEditForm();
   		this.eidtWindow = this.createExamstWindow();
   		this.eidtWindow.add(this.editForm);
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,			
//			{header: "年度季度",  width:180, align:"center", sortable:true, dataIndex:"XNXQ"},
//			{header: "考试名称",  width:120, align:"center", sortable:true, dataIndex:"KSMC"},
//			{header: "考试批次",  width:120, align:"center", sortable:true, dataIndex:"KSKM"},
			{header: "科目",  width:120, align:"center", sortable:true, dataIndex:"KM"},
			{header: "题型",   width:60,align:"center", sortable:true, dataIndex:"TX"},
//			{header: "试题性质", width:60,  align:"center", sortable:true, dataIndex:"STXZ"},
//			{header: "试题难度", width:60,   align:"center", sortable:true, dataIndex:"STND"},
			{header: "分值",   width:60,align:"center", sortable:true, dataIndex:"FZ"},
			{header: "试题编号", width:60,  align:"center", sortable:true, dataIndex:"STBH"},
			{header: "是否启用", width:60,  align:"center", sortable:true, dataIndex:"SFQY"},
//			{header: "评分标准",  width:100, align:"center", sortable:true, dataIndex:"PFBZ"},
			{header: "题目",  width:100, align:"center", sortable:true, dataIndex:"TM"},
			{header: "答案",   width:80,align:"center", sortable:true, dataIndex:"DA"},
			{header: "录入人",  width:60, align:"center", sortable:true, dataIndex:"LRR"},
			{header: "录入时间", width:150, align:"center", sortable:true, dataIndex:"LRSJ"},
			{header: "修改人",  width:60, align:"center", sortable:true, dataIndex:"XGR"},
			{header: "修改时间", width:150,  align:"center", sortable:true, dataIndex:"XGSJ"}
		];
		this.grid = new Ext.ux.GridPanel({
			cm:cm,
			sm:sm,
			title:"试题信息",
			tbar:[ 
			      "->",{xtype:"button",text:"删除",iconCls:"p-icons-delete",handler:this.deleteSts,scope:this}
			      ,"->"
//			      ,{xtype:"button",text:"修改",iconCls:"p-icons-update",handler:this.updateExamst,scope:this}
//			      ,"->",{xtype:"button",text:"增加",iconCls:"p-icons-add",handler:function(){
//			    	  		this.kskm.store.removeAll();
//			    	  		this.ksmc.store.removeAll();this.eidtWindow.show();this.bj.hide();this.opt='add';this.bj.setText("编辑选项");},scope:this}
//			      ,"->"
			      ,{xtype:"button",text:"导入试题信息",iconCls:"p-icons-upload",handler:this.importExamst,scope:this}
			      ,"->",{xtype:"button",text:"下载模板",iconCls:"p-icons-download",handler:this.downloadTemplate,scope:this}
			    ],
			page:true,
			rowNumber:true,
			region:"center",
			excel:false,
			pdf:false,
			excelTitle:"试题信息表",
			action:"examst_getListPage.do",
			fields :["XNXQ","KSMC","KSKM","KM","TX","STXZ","STND","FZ","STBH","SFQY","PFBZ","TM","DA","LRR","LRSJ","XGR","XGSJ"],
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
		        	 url : 'examst_getKm.do'
		        }),
		        fields : ['text', 'value']
		    }),
			valueField: 'value',
		    displayField: 'text'
		}); 
		
		this.stbh =new Ext.form.TextField({width:180});
		
		this.examtype  = new Ext.form.ComboBox({
			name:"examtype",
		    triggerAction: 'all', 
		    mode: 'remote',
		    width:180,
		    allowBlank:false,
		    blankText:'请选择题型',
		    emptyText:'请选择题型',
		    hiddenName:'examtype',
		    editable:false,
		    store: new Ext.data.JsonStore({  
		        proxy:new Ext.data.HttpProxy({
		        	 method:'post',
		        	 url : 'examst_getTx.do'
		        }),
		        fields : ['value', 'text']
		    }),
		    valueField: 'value',
		    displayField: 'text'
		}); 
		
		this.xnxq_ser = new Ext.form.ComboBox({ 
    		name:'xnxq_ser',
    		triggerAction : 'all',  
    		width:180,
    		emptyText : '请选择',  
			mode: 'remote',
//			lazyInit:true,
			editable:false,
			hiddenName:'xnxq_ser',
			store: new Ext.data.JsonStore({  
		        proxy:new Ext.data.HttpProxy({
		        	 method:'post',
		        	 url : 'examst_getXnxq.do'
		        }),
		        fields : ['value', 'text']
		    }),
			valueField: 'value',
		    displayField: 'text',
		    listeners:{ 
		    	change:function(t,n,o){  
			    	that.ksmc_ser.store.load({params:{'examst_xnxq':n}});
			    }
			}
		});

    	this.ksmc_ser = new Ext.form.ComboBox({ 
    		name:'ksmc_ser',
    		triggerAction : 'all',  
    		width:180,
    		emptyText : '请选择',  
			mode: 'remote',
//			lazyInit:true,
			editable:false,
			hiddenName:'ksmc_ser',
			store: new Ext.data.JsonStore({  
		        proxy:new Ext.data.HttpProxy({
		        	 method:'post',
		        	 url : 'examst_getKsmc.do'
		        }),
		        fields : ['value', 'text'],
		        listeners:{
			    	beforeload : function(t, o){ 
			    	    t.baseParams={'examst_xnxq':that.xnxq_ser.getValue()};  
			    	}
			    }
		    }),
			valueField: 'value',
		    displayField: 'text',
		    listeners:{ 
		    	change:function(t,n,o){  
			    	that.kskm_ser.store.load({params:{'examst_ksmc':n}});
			    }
			}
		});
    	
    	this.kskm_ser = new Ext.form.ComboBox({ 
    		name:'kskm_ser',
    		triggerAction : 'all',  
    		width:180,
    		emptyText : '请选择',  
			mode: 'remote',
//			lazyInit:true,
			editable:false,
			hiddenName:'kskm_ser',
			store: new Ext.data.JsonStore({ 
		        proxy:new Ext.data.HttpProxy({
		        	 method:'post',
		        	 url : 'examst_getKskm.do'
		        }),
		        fields : ['value', 'text'],
		        listeners:{
			    	beforeload : function(t, o){ 
			    	    t.baseParams={'examst_ksmc':that.ksmc_ser.getValue()};  
			    	}
			    }
		    }),
			valueField: 'value',
		    displayField: 'text'
		});
		
		var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:function(){this.grid.$load();},scope:this});
		var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.search.getForm().reset()},scope:this});
		
		this.search = new Ext.form.FormPanel({
			region: "north",
			height:150,
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
//							{html:"年度季度：",baseCls:"label_right",width:170}, 
//							{items:[this.xnxq_ser],baseCls:"component",width:210},
//							{html:"考试名称：",baseCls:"label_right",width:170}, 
//							{items:[this.ksmc_ser],baseCls:"component",width:210},
//							{html:"考试批次：",baseCls:"label_right",width:170}, 
//							{items:[this.kskm_ser],baseCls:"component",width:210},
							{html:"科目：",baseCls:"label_right",width:170}, 
							{items:[this.kch],baseCls:"component",width:210},  
							{html:"题型：",baseCls:"label_right",width:170}, 
							{items:[this.examtype],baseCls:"component",width:210},
							{html:"试题编号：",baseCls:"label_right",width:170}, 
							{items:[this.stbh],baseCls:"component",width:210},
//							{layout:"absolute",baseCls:"component_btn",width:210},
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
    	this.eidtWindow.show();
    	this.eidtWindow.hide();
    },
    initQueryDate:function(){    	
     
    }, 
    createExamstEditForm:function(){
    	var that = this;
    	var flag = true; 
    	var loadFlag = true;
    	var loadFlag2= true;
    	
    	this.xnxq = new Ext.form.ComboBox({ 
    		name:'examst_xnxq',
    		triggerAction : 'all',  
    		width:180,
    		emptyText : '请选择',  
			mode: 'remote',
//			lazyInit:true,
			editable:false,
			hiddenName:'examst_xnxq',
			allowBlank:false,
			store: new Ext.data.JsonStore({  
		        proxy:new Ext.data.HttpProxy({
		        	 method:'post',
		        	 url : 'examst_getXnxq.do'
		        }),
		        fields : ['value', 'text']
		    }),
			valueField: 'value',
		    displayField: 'text',
		    listeners:{ 
		    	change:function(t,n,o){  
			    	    if(!loadFlag){
			    	    	that.ksmc.store.load({params:{'examst_xnxq':n}});
			    	    }
			    		that.ksmc.setValue('');
			    	}
			}
		});

    	this.ksmc = new Ext.form.ComboBox({ 
    		name:'examst_ksmc',
    		triggerAction : 'all',  
    		width:180,
    		emptyText : '请选择',  
			mode: 'remote',
//			lazyInit:true,
			editable:false,
			hiddenName:'examst_ksmc',
			allowBlank:false,
			store: new Ext.data.JsonStore({  
		        proxy:new Ext.data.HttpProxy({
		        	 method:'post',
		        	 url : 'examst_getKsmc.do'
		        }),
		        fields : ['value', 'text'],
		        listeners:{
			    	beforeload : function(t, o){ 
			    	    if(that.xnxq.getValue()==''){ 
			    	    	loadFlag = false;
			    	    	return false;
			    	    } 
			    	    if(loadFlag){  
			    	    	t.baseParams={'examst_xnxq':that.xnxq.getValue()};  
			    	    	loadFlag = false;
			    	    }
			    	}
			    }
		    }),
			valueField: 'value',
		    displayField: 'text',
		    listeners:{
		    	change : function(t, n, o){      
		    	    if(!loadFlag2){
		    	    	that.kskm.store.load({params:{'examst_ksmc':n}});
		    	    }
		    	    that.kskm.setValue('');
		    	} 
		    }
		});
    	
    	this.kskm = new Ext.form.ComboBox({ 
    		name:'examst.kmid',
    		triggerAction : 'all',  
    		width:180,
    		emptyText : '请选择',  
			mode: 'remote',
//			lazyInit:true,
			editable:false,
			hiddenName:'examst.kmid',
			allowBlank:false,
			store: new Ext.data.JsonStore({ 
		        proxy:new Ext.data.HttpProxy({
		        	 method:'post',
		        	 url : 'examst_getKskm.do'
		        }),
		        fields : ['value', 'text'],
		        listeners:{
			    	beforeload : function(t, o){      
			    	    if(that.ksmc.getValue()==''){ 
			    	    	loadFlag2 = false;
			    	    	return false;
			    	    } 
			    	    if(loadFlag2){ 
			    	    	t.baseParams={'examst_ksmc':that.ksmc.getValue()};  
			    	    	loadFlag2 = false;
			    	    }
			    	}
			    }
		    }),
			valueField: 'value',
		    displayField: 'text'
		});
    	
    	this.km = new Ext.form.ComboBox({ 
    		name:'examst.kch',
    		triggerAction : 'all',  
    		width:180,
    		emptyText : '请选择',  
			mode: 'remote',
//			lazyInit:true,
			editable:false,
			hiddenName:'examst.kch',
			allowBlank:false,
			store: new Ext.data.JsonStore({  
		        proxy:new Ext.data.HttpProxy({
		        	 method:'post',
		        	 url : 'examst_getKm.do'
		        }),
		        fields : ['value', 'text']
		    }),
			valueField: 'value',
		    displayField: 'text'
		});
    	
    	  
    	
    	this.bj =  new Ext.Button({text:"编辑选项",iconCls:"p-icons-save"});
    	var i = 2;
		this.tx  = new Ext.form.ComboBox({
			name:"examst.exam_type_id",
		    triggerAction: 'all', 
		    mode: 'remote',
		    width:180,
		    allowBlank:false,
		    blankText:'请选择题型',
		    emptyText:'请选择题型',
		    hiddenName:'examst.exam_type_id',
		    editable:false,
		    store: new Ext.data.JsonStore({  
		        proxy:new Ext.data.HttpProxy({
		        	 method:'post',
		        	 url : 'examst_getTx.do'
		        }),
		        fields : ['value', 'text']
		    }),
		    valueField: 'value',
		    displayField: 'text',
		    listeners:{"change":function(t,nv,ov){
		    	if(t.getRawValue()=='单选' || t.getRawValue()=='多选'){
		    		if(flag){
		    			flag=false;
		    			if(!that.eidtWindow.getTopToolbar().items.contains(that.bj)){
			    			that.eidtWindow.getTopToolbar().addButton(that.bj);
			    			that.eidtWindow.getTopToolbar().doLayout();
		    			}
		    			that.bj.show();
		    		}else{
		    			that.bj.show();
		    		}
		    	}else{ 
		    		that.bj.hide();
		    	}
		    }}
		}); 
		this.stxz = new Ext.form.ComboBox({ 
			name:"examst.exam_char",
		    triggerAction: 'all', 
		    mode: 'remote',
		    allowBlank:false,
		    width:180,
		    hiddenName:'examst.exam_char',
		    blankText:'请选择试题性质',
		    emptyText:'请选择试题性质',
		    editable:false,
		    store: new Ext.data.JsonStore({  
		        proxy:new Ext.data.HttpProxy({
		        	 method:'post',
		        	 url : 'examst_getXz.do'
		        }),
		        fields : ['value', 'text']
		    }),
		    valueField: 'value',
		    displayField: 'text'
		});
		
		
		this.stnd = new Ext.form.ComboBox({ 
			name:"examst.degree_id",
		    triggerAction: 'all', 
		    mode: 'remote',
		    width:180,
		    allowBlank:false,
		    hiddenName:'examst.degree_id',
		    blankText:'请选择试题难度',
		    emptyText:'请选择试题难度',
		    editable:false,
		    store: new Ext.data.JsonStore({  
		        proxy:new Ext.data.HttpProxy({
		        	 method:'post',
		        	 url : 'examst_getNd.do'
		        }),
		        fields : ['value', 'text']
		    }),
		    valueField: 'value',
		    displayField: 'text'
		});
		this.sfqy =  new Ext.form.ComboBox({ 
			name:"examst.exam_info_zt",
		    triggerAction: 'all', 
		    mode: 'local',
		    width:180,
		    allowBlank:false, 
		    blankText:'请选择是否启用',
		    emptyText:'请选择是否启用',
		    editable:false,
		    store: new Ext.data.ArrayStore({  
		        autoLoad:true,
		        fields : ['value', 'text'],
		        data:[['是','是'],['否','否']]
		    }),
		    valueField: 'value',
		    displayField: 'text'
		});
		
		var stid  = new Ext.form.TextField({name:"examst.exam_info_id",id:"stid",hidden:true});
		var fz  = new Ext.form.NumberField({name:"examst.scores",id:"fz",allowBlank:false,blankText:"分值不能为空！",width:180,maxValue:50,minValue:1});
		var stbh  = new Ext.form.TextField({name:"examst.exam_info_bh",id:"stbh",allowBlank:false,blankText:"试题编号不能为空！",width:180});
		
		var pfbz  = new Ext.form.TextArea({name:"examst.grade_stand",id:"pfbz",blankText:"评分标准不能为空！",width:530});
		var tm  = new Ext.form.TextArea({name:"examst.exam_cont",id:"tm",allowBlank:false,blankText:"题目不能为空！",width:530});
		var da  = new Ext.form.TextArea({name:"examst.exam_answ",id:"da",allowBlank:false,blankText:"答案不能为空！",width:530});
		
		this.submitForm = new Ext.ux.FormPanel({
        	fileUpload: true,
        	frame:true,
            enctype: 'multipart/form-data',
            defaults:{xtype:"textfield",anchor:"95%"},
            items: [{fieldLabel:"附件",xtype:"textfield",name:"upload",inputType:'file'}]
		});
		this.stPanel = new Ext.Panel({ 
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: { 
				columns: 4 
			}, 
			items:[			      
			    {html:"年度：",baseCls:"label_right",width:150},
			  	{html:"<font class='required'>*</font>",items:[this.xnxq],baseCls:"component",width:200},
			  	{html:"考试名称：",baseCls:"label_right",width:150},
			  	{html:"<font class='required'>*</font>",items:[this.ksmc],baseCls:"component",width:200},
				{html:"考试批次：",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[this.kskm],baseCls:"component",width:200},
				{html:"科目：",baseCls:"label_right",width:150},
			  	{html:"<font class='required'>*</font>",items:[this.km],baseCls:"component",width:200},
				{html:"题型：",baseCls:"label_right",width:150}, 
				{html:"<font class='required'>*</font>",items:[this.tx],baseCls:"component",width:200},
				{html:"试题性质：",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[this.stxz],baseCls:"component",width:200},
				{html:"试题难度：",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[this.stnd],baseCls:"component",width:200},
				{html:"分值：",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[fz],baseCls:"component",width:200},
				{html:"试题编号：",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[stbh],baseCls:"component",width:200},
				{html:"是否启用：",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[this.sfqy],baseCls:"component",width:200,colspan:3},
				{html:"评分标准：",baseCls:"label_right",width:150},
				{items:[pfbz],baseCls:"component",colspan:3},
				{html:"题目：",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[tm],baseCls:"component",colspan:3},
				{html:"答案：",baseCls:"label_right",width:150},
				{html:"<font class='required'>*</font>",items:[da,stid],baseCls:"component",colspan:3}
			]		
		});
		
		var xx_a  = new Ext.form.TextArea({name:"xx_a",id:"xx_a",blankText:"至少填入A项和B项！",width:480,height:40});
 		var xx_b  = new Ext.form.TextArea({name:"xx_b",id:"xx_b",blankText:"至少填入A项和B项！",width:480,height:40});
 		var xx_c  = new Ext.form.TextArea({name:"xx_c",id:"xx_c",width:480,height:40});
 		var xx_d  = new Ext.form.TextArea({name:"xx_d",id:"xx_d",width:480,height:40}); 
 		var addXx = new Ext.Button({name:"addXx",text:"添加选项",handler:this.addXxInfo,width:50,scope:this}); 
 		var delXx = new Ext.Button({name:"delXx",text:"删除选项",handler:this.delXxInfo,width:50,scope:this});
 		this.xxPanel = new Ext.Panel({ 
 			hidden:true,
			xtype:"panel",
			layout:"table", 
			baseCls:"table",
			layoutConfig: {  
				columns: 2 
			}, 
			items:[]
 		}); 
		
		return new Ext.ux.FormPanel({
			frame:false,
			defaults:{anchor:"100%"},
			items:[
				{layout:'form',xtype:"fieldset",title:"试题信息",items:[this.stPanel,this.xxPanel]}
			]});	 
    },
    createExamstWindow:function(){
    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addExamst,scope:this});
		var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.eidtWindow.hide()},scope:this});
		return new Ext.ux.Window({
			 	width:845,
			 	tbar:{
					cls:"ext_tabr",
					items:[ 
					 	 "->",cancel
			 			 ,"->",save
					  ]
				},
			 	title:"试题信息维护"});		
    },
    downloadTemplate:function(){
    	//下载模板
    	var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/examst.xls";
    	window.open(path);
    }, 
    addExamst:function(){ 
    	var that =this;
    	var optList = 0;
    	if(this.tx.getRawValue()=='单选' || this.tx.getRawValue()=='多选'){
	    	var a = Ext.getCmp("xx_a").getValue();
	    	var b = Ext.getCmp("xx_b").getValue(); 
	    	var c = Ext.getCmp("xx_c").getValue();
	    	var d = Ext.getCmp("xx_d").getValue(); 
	    	if(a=="" || b==""){
	    		Ext.Msg.alert("提示","至少要填入A项和B项，两则选项");
	    		return;
	    	}
	    	if(that.xxPanel.items.getCount()==10){
	    		if(c==null || c==""){
	    			if(d!=null && d!=""){
	    				Ext.Msg.alert("提示","请按照顺序填入选项!");
	    	    		return;
	    			}
	    		}
	    	}else{
	    		var len = (that.xxPanel.items.getCount()-2)/2-2;
	    		for(var i = 99 ;i<99+len;i++){
	    			var temp = Ext.getCmp("xx_"+String.fromCharCode(i)).getValue();
	    			if(temp == null || temp == ""){
	    				Ext.Msg.alert("提示","请把选项填写完整");
	    	    		return;
	    			}
	    		}
	    	}
	    	optList = that.xxPanel.items.getCount();
    	}
    	this.editForm.$submit({
    		action:"examst_optExamstInfo.do",
    		params:{"optList":optList},
    		handler:function(form,result,thiz){
    			Ext.getCmp("xx_a").reset();
    	    	Ext.getCmp("xx_b").reset();
    	    	Ext.getCmp("xx_c").reset();
    	    	Ext.getCmp("xx_d").reset();
    	    	that.xxPanel.removeAll(); 
    			thiz.eidtWindow.hide();
    			thiz.grid.$load();
    		},
    		scope:this
    	});
    },
    importExamst:function(){
    	//导入监考信息
    	var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",handler:this.exportFilesInfo,scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.fileUpWin.hide()},scope:this});
     	this.fileUpWin = new Ext.ux.Window({
     		title:"文件上传",	
     		width:400,
     		tbar:{cls:"ext_tabr",items:["->",_b_cancel,"->",_b_save]},
     		listeners:{
		 		hide:function(){
		 			this.submitForm.form.reset();
		 		},scope:this},
     		items:[this.submitForm]
     	});
     	this.fileUpWin.show(null,function(){},this);
    },
    exportFilesInfo:function(){
    	var filePath = this.submitForm.getForm().findField("upload").getRawValue();
    	//判断文件类型
    	var objtype=filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
    	var fileType=new Array(".xls",".xlsx");
    	if(filePath==""){ 
    		alert("请选择文件！");return false;
	    }else{
	    	if(!/\.(xls|xlsx)$/.test(objtype)){
	    		alert("文件类型必须是.xlsx,.xls中的一种!")
	    		return false;
	    	}
	    }
    	Ext.Msg.wait("正在上传","提示");
    	this.submitForm.getForm().submit({
    		url:"upfile_importExamstInfo.do",
    		params:{
    			form:this.submitForm.getForm().findField("upload")
			},
			scope:this,
			success: function(form, action) {
			       Ext.Msg.alert("提示",action.result.msg);
			       this.fileUpWin.hide();
				   this.grid.$load();
			    },
			failure: function(form, action) { 
				var that = this;
				   Ext.Msg.alert("提示",action.result.msg,function(b,t){
					   that.fileUpWin.hide();
					   that.grid.$load();  
					   if(action.result.data!=null && action.result.data!=''){
						   window.open(Ext.get("ServerPath").dom.value+"/"+action.result.data.replace(/\\/g, "\/"));
						}
				   });
			   }
		});	
    },
    deleteSts:function(){
    	var selected =  this.grid.getSelectionModel().getSelected();
    	if(!selected){
    		Ext.MessageBox.alert("消息","请选择一条记录！");
    		return;
    	}
    	var selectedSts = this.grid.getSelectionModel().getSelections();
    	var thiz = this;
    	var sts = "'";
    	for(var i = 0; i < selectedSts.length; i++) {
    		if(i != selectedSts.length - 1) {
    			sts += selectedSts[i].json.exam_info_id + "','";
    		}else {
    			sts += selectedSts[i].json.exam_info_id + "'";
    		}
    	} 
    	Ext.MessageBox.confirm("消息","您确定要删除吗?",function(b,t){
    		 if(b=='yes'){
    			 Ext.Ajax.request({
    				 url:'examst_delSts.do',
    				 method:'post',
    				 params:{sts:sts},
    				 success: function(response, opts) {
    					 Ext.MessageBox.alert("消息","删除成功");
    					 thiz.grid.$load({start:thiz.grid.getBottomToolbar().cursor});
    				   },
    				 failure: function(response, opts) {
    					 Ext.MessageBox.alert("消息","删除失败");
    				 }
    			 });
    		 }
    	}); 
    	
    },
    updateExamst:function(){
    	var that = this;
    	this.opt='update';
    	var selectedSt = this.grid.getSelectionModel().getSelections();
	    if(selectedSt.length != 1){
	    	Ext.MessageBox.alert("消息","请选择一条记录进行修改！");
	    	return;
	    }
	    var stid = selectedSt[0].json.exam_info_id;
	    Ext.getCmp("stid").setValue(stid);
	    this.xnxq.getStore().load({
	    	callback:function(){
	    		that.xnxq.setValue(selectedSt[0].json.xnxqVal);
	    		that.ksmc.getStore().load({callback:function(){
	    			that.ksmc.setValue(selectedSt[0].json.lcid);
	    			that.kskm.getStore().load({callback:function(){that.kskm.setValue(selectedSt[0].json.kmid);}});
	    		}});
	    	}
	    });
	    this.km.getStore().load({callback:function(){that.km.setValue(selectedSt[0].json.kch);}});
	    this.tx.getStore().load({callback:function(){that.tx.setValue(selectedSt[0].json.txid);}});
	    this.stxz.getStore().load({callback:function(){that.stxz.setValue(selectedSt[0].json.xzid);}});  
	    this.stnd.getStore().load({callback:function(){that.stnd.setValue(selectedSt[0].json.ndid);}});
    	Ext.getCmp("fz").setValue(selectedSt[0].get("FZ"));
    	Ext.getCmp("stbh").setValue(selectedSt[0].get("STBH"));    		
    	this.sfqy.setValue(selectedSt[0].get("SFQY"));
    	Ext.getCmp("pfbz").setValue(selectedSt[0].get("PFBZ"));
    	Ext.getCmp("tm").setValue(selectedSt[0].get("TM"));
    	Ext.getCmp("da").setValue(selectedSt[0].get("DA")); 
    	if(selectedSt[0].json.TX=="单选" || selectedSt[0].json.TX=="多选"){
    		if(that.bj){
				if(!that.eidtWindow.getTopToolbar().items.contains(that.bj)){
					that.eidtWindow.getTopToolbar().addButton(that.bj);
					that.eidtWindow.getTopToolbar().doLayout();
				}
				that.bj.show(); 
			}
    	}
    	this.eidtWindow.show();
    },
    fileup:function(){    	
    	var _b_save	  = new Ext.Button({text:"上传",iconCls:"p-icons-upload",handler:this.saveFilesInfo,scope:this});
		var _b_cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.fileUpWin.hide()},scope:this});
		     	
		this.fileUpWin = new Ext.ux.Window({
     		title:"文件上传",	
     		width:400,
     		tbar:{
				cls:"ext_tabr",
				items:["->",_b_cancel,"->",_b_save]
			},
     		listeners:{
		 		hide:function(){
		 			this.submitForm.form.reset();
		 		},scope:this},
     		items:[this.submitForm]     		
     	});
     	this.fileUpWin.show(null,function(){},this);
     },
     saveFilesInfo:function(){
    	 var filePath = this.submitForm.getForm().findField("upload").getRawValue();
    	 //判断文件类型
    	 var objtype=filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
    	 var fileType=new Array(".jpg",".jpeg",".gif",".bmp",".png",".tiff");
    	 if(filePath==""){ 
    		 alert("请选择图片！");return false;
    	 }else{
    		 if(!/\.(gif|jpg|jpeg|png|bmp|tiff)$/.test(objtype)){
    			 alert("图片类型必须是.gif,.jpeg,.jpg,.png,.bmp,.tiff中的一种!")
    			 return false;
    		 }
    	 }
    	 var file = document.getElementById("upload");
    	 this.submitForm.$submit({
    		 action:"upfile_saveUpFileInfo.do",
    		 params:{
    			 form:this.submitForm.getForm().findField("upload")
    		 },
    		 scope:this,
    		 handler:function(form,result,scope){
    			 scope.fileUpWin.hide();
    			 document.getElementById("imgPath").src = Ext.get("ServerPath").dom.value+result.data;
    			 Ext.getCmp("photopath").setValue(result.data);				
    		 }
    	 });
     },
     addXxInfo:function(){
    	 if(this.lowChar<=105){
    		 this.xxPanel.add({html:"选项"+String.fromCharCode(this.upprChar)+"：",baseCls:"label_right",width:150,id:"XX_"+String.fromCharCode(this.upprChar)});
    		 this.xxPanel.add({id:"xxa_"+String.fromCharCode(this.lowChar),items:[new Ext.form.TextArea({id:"xx_"+String.fromCharCode(this.lowChar),name:"xx_"+String.fromCharCode(this.lowChar),width:480,height:40})],baseCls:"component",width:200});
    		 this.xxPanel.doLayout();
    		 this.lowChar++;
    		 this.upprChar++;
    	 }
     },
     delXxInfo:function(){
    	 if(this.lowChar!=101){
    		 this.lowChar--;
        	 this.upprChar--;
        	 this.xxPanel.remove("XX_"+String.fromCharCode(this.upprChar));
        	 this.xxPanel.remove("xxa_"+String.fromCharCode(this.lowChar)); 
        	 this.xxPanel.doLayout();
    	 }
     }
});