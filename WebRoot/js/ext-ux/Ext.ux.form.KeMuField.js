Ext.ns('Ext.ux.form');
Ext.ux.form.KeMuField = Ext.extend(Ext.form.TextField,{
	unitText:"",
	EnterQueryHandler:"",
	initComponent: function(){  
		var action = "dropListAction_getKeCheng.do";
		if(this.action != null) {
			action = this.action;
		}				
		//在组件初始化期间调用的代码  
        //因为配置对象应用到了“this”，所以属性可以在这里被覆盖，或者添加新的属性  
        //（如items,tools,buttons）
		//this.readOnly = true;
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = [
			sm,
			{header: "科目名称", width:150, align:"center", sortable:true, dataIndex:"CODENAME"}
		];
		this.store = new Ext.data.JsonStore({
    		autoLoad:false,
    		url:action,
    		fields :["CODEID","CODENAME"]
    	});
		if(this.code != null && this.code != "") {
			this.xnxqCode = this.code
		}
		var thiz = this;
		this.store.reload({
			callback: function(records, options, success){
				
		    }
		});
		var grid = new Ext.grid.GridPanel({
			id:"xnxqgrid_" + this.id,
			store:this.store,
			columns:cm,
			sm:sm,
			tbar:{
				items:[ 
				  "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function(){Ext.getCmp("xnxqwin_"+this.id).hide()},scope:this}
				  ,"->",{xtype:"button",text:"选择",iconCls:"p-icons-submit",handler:function() {
					  var selected =  Ext.getCmp("xnxqgrid_"+this.id).getSelectionModel().getSelected();
					  if(!selected){
						  Ext.MessageBox.alert("消息","请选择一条记录！");
						  return;
					  }
					  var selecteds = Ext.getCmp("xnxqgrid_"+this.id).getSelectionModel().getSelections();
				      var textValue="";
				      var textCode="";
				      for(var i = 0; i < selecteds.length; i++) {
				    	  textValue+=	selecteds[i].get("CODENAME")+",";
				    	  textCode+=selecteds[i].get("CODEID")+",";
				      }
				      textValue=textValue.substr(0,textValue.length-1);
				      textCode=textCode.substr(0,textCode.length-1);
				      this.xnxqTextValue = textValue;
				      this.xnxqCode = textCode;
					  this.setValue(this.xnxqTextValue);
					  if(this.callback != null) {
						  this.callback();
					  }
					  Ext.getCmp("xnxqwin_"+this.id).hide();
				  },scope:this}
				]
			},
			region:"center",
			border:false
		});
		var win = new Ext.ux.Window({
			 	id:"xnxqwin_"+this.id,
				width:200,
			 	height:380,
			 	title:"科目信息",
			 	layout:'border',//布局方式
			 	items:[Ext.getCmp("xnxqgrid_"+this.id)]
	 	});
        Ext.apply(this, { 
            propA: 3 
        });  
        
        //调用父类代码之前             
        //调用父类构造函数（必须）  
        Ext.ux.form.KeMuField.superclass.initComponent.apply(this, arguments);  
        Ext.getCmp("xnxqwin_"+this.id).show();
        Ext.getCmp("xnxqwin_"+this.id).hide();
        //调用父类代码之后  
        //如：设置事件处理和渲染组件  
    },
	onRender : function(ct, position) {   
		Ext.ux.form.KeMuField.superclass.onRender.call(this, ct, position);   
		this.createUnit(ct, position);
		this.registerEnterQueryEvent();
	},
	getCode:function() {
		return this.xnxqCode;
	},
	setCode:function(code) {
		this.xnxqCode = code;
		/*if(this.xnxqCode != null && this.xnxqCode != "") {
			var total = Ext.getCmp("xnxqgrid_"+this.id).getStore().getCount();//数据行数
			for(var i=0;i<total;i++){
			  var xnxqCode_m = Ext.getCmp("xnxqgrid_"+this.id).getStore().getAt(i).data['CODENAME'];
			  if(xnxqCode_m == this.xnxqCode) {
				  this.xnxqTextValue = Ext.getCmp("xnxqgrid_"+this.id).getStore().getAt(i).data['CODENAME'] ;
				  this.xnxqCode = Ext.getCmp("xnxqgrid_"+this.id).getStore().getAt(i).data['CODEID'];
				  this.setValue(this.xnxqTextValue);
				  break;
			  }
			}
		}*/
	},
	reset:function() {
		this.setValue("");
		this.xnxqCode = "";
		Ext.getCmp("xnxqgrid_"+this.id).getSelectionModel().clearSelections();
	},
	createUnit:function(ct, position){
		var thiz = this;
		if(this.width == undefined) {
			this.width = 180;
		}
		this.unitEl = ct.createChild({
            tag : 'div',
            id:'KeMuField-search-btn'+this.id,
            style: 'cursor:hand;position:absolute;margin-top:-20px;margin-left:'+(this.width-30)+'px;height:18px;width:18px;background-image:url(../img/icons/query.png);background-repeat:no-repeat;background-size:18px 18px;'
		});
		Ext.get("KeMuField-search-btn"+this.id).on('click', function(e,t) { 
			// e是一个标准化的事件对象(Ext.EventObject) 
			// t就是点击的目标元素，这是个Ext.Element. 
			// 对象指针this也指向t 
			if(thiz.disabled){
				return;
			}
			Ext.getCmp("xnxqwin_"+thiz.id).show();
			if(this.xnxqCode != null && this.xnxqCode != "") {
				var total = Ext.getCmp("xnxqgrid_"+thiz.id).getStore().getCount();//数据行数
				for(var i=0;i<total;i++){
				  var xnxqCode_m = Ext.getCmp("xnxqgrid_"+thiz.id).getStore().getAt(i).data['CODEID'];
				  if(xnxqCode_m == this.xnxqCode) {
					  Ext.getCmp("xnxqgrid_"+thiz.id).getSelectionModel().selectRow(i,true);
					  break;
				  }
				}
			}
		}); 

	    this.width = this.width - (this.unitText.replace(/[^\x00-\xff]/g, "xx").length * 6+ 10);   
		this.alignErrorIcon = function() {   
			this.errorIcon.alignTo(this.unitEl, 'tl-tr', [2, 0]);   
		};   
	},
	registerEnterQueryEvent:function(){		
		this.on('specialkey',function(field,e){if (e.getKey() == e.ENTER) {
			Ext.invoke(this.scope,this.EnterQueryHandler,null)
		}},this);
	}
});
Ext.reg("kemufield",Ext.ux.form.KeMuField);