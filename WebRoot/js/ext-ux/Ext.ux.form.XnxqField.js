Ext.ns('Ext.ux.form');
Ext.ux.form.XnxqField = Ext.extend(Ext.form.TextField,{
	unitText:"",
	EnterQueryHandler:"",	
	initComponent: function(){  
		var thiz = this;
		var action = "dropListAction_getXnxqListPage.do";
		if(this.action != null) {
			action = this.action;
		}		
		//在组件初始化期间调用的代码  
        //因为配置对象应用到了“this”，所以属性可以在这里被覆盖，或者添加新的属性  
        //（如items,tools,buttons）
		//this.readOnly = true;
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		var cm = [
			sm,
			{header: "年度名称",   align:"center", sortable:true, dataIndex:"XNMC"},
			{header: "季度名称",   align:"center", sortable:true, dataIndex:"XQMC"}
		];
		this.store = new Ext.data.JsonStore({
    		autoLoad:false,
    		url:action,
    		fields :["XNMC","XQMC","XQCODE"]
    	});
		if(this.code != null && this.code != "") {
			this.xnxqCode = this.code
		}
		var thiz = this;
		this.store.reload({
			callback: function(records, options, success){
				if(this.xnxqCode != null && this.xnxqCode != "") {
					var total = this.getCount();//数据行数
					for(var i=0;i<total;i++){
					  var xnxqCode_m = this.getAt(i).data['XNMC'] + "," + this.getAt(i).data['XQCODE'];
					  if(xnxqCode_m == this.xnxqCode) {
						  this.xnxqTextValue = this.getAt(i).data['XNMC'] + "年度 " + this.getAt(i).data['XQMC'];
						  this.xnxqCode = this.getAt(i).data['XNMC']  + "," + this.getAt(i).data['XQCODE'];
						  thiz.setValue(this.xnxqTextValue);
						  break;
					  }
					}
				}
		    }
		});
		this.grid = new Ext.grid.GridPanel({
			store:this.store,
			columns:cm,
			sm:sm,
			tbar:{
				items:[ 
				   "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function(){thiz.win.hide()},scope:this},
				   "->",{xtype:"button",text:"选择",iconCls:"p-icons-submit",handler:function() {
					   var selected =  this.grid.getSelectionModel().getSelected();
					   if(!selected){
						   Ext.MessageBox.alert("消息","请选择一条记录！");
						   return;
					   }
					   var selecteds = this.grid.getSelectionModel().getSelections();
					   for(var i = 0; i < selecteds.length; i++) {
						   this.xnxqTextValue = selecteds[i].get("XNMC") + "年度 " + selecteds[i].get("XQMC");
						   this.xnxqCode = selecteds[i].get("XNMC") + "," + selecteds[i].get("XQCODE");
					   }
					   this.setValue(this.xnxqTextValue);
					   if(this.callback != null) {
						   this.callback();
					   }
					   this.win.hide();
				   },scope:this}
				]
			},
			region:"center",
			border:false,
			  	listeners: { 
			  		dblclick:function(){
			  			var selected =  thiz.grid.getSelectionModel().getSelected();
			  			if(!selected){
				    		Ext.MessageBox.alert("消息","请选择一条记录！");
				    		return;
				    	}
			  			var selecteds = thiz.grid.getSelectionModel().getSelections();
				    	for(var i = 0; i < selecteds.length; i++) {
				    		thiz.xnxqTextValue = selecteds[i].get("XNMC") + "年度 " + selecteds[i].get("XQMC");
				    		thiz.xnxqCode = selecteds[i].get("XNMC") + "," + selecteds[i].get("XQCODE");
				    	}
				    	thiz.setValue(thiz.xnxqTextValue);
				    	if(thiz.callback != null) {
				    		thiz.callback();
				    	}
				    	thiz.win.hide();				  				  		 
			  		}
			  	}
		});
		this.win = new Ext.ux.Window({
			width:280,
			height:380,
			title:"年度季度",
			layout:'border',//布局方式
			items:[this.grid]
	 	});
        Ext.apply(this, { 
            propA: 3 
        });        
        //调用父类代码之前             
        //调用父类构造函数（必须）  
        Ext.ux.form.XnxqField.superclass.initComponent.apply(this, arguments);  
        this.win.show();
        this.win.hide();
        //调用父类代码之后  
        //如：设置事件处理和渲染组件  
    },
	onRender : function(ct, position) {   
		Ext.ux.form.XnxqField.superclass.onRender.call(this, ct, position);   
		this.createUnit(ct, position); 
	    this.registerEnterQueryEvent();
	},
	getCode:function() {
		return this.xnxqCode;
	},
	setCode:function(code) {
		this.xnxqCode = code;
		if(this.xnxqCode != null && this.xnxqCode != "") {
			var total = this.grid.getStore().getCount();//数据行数
			for(var i=0;i<total;i++){
				var xnxqCode_m = this.grid.getStore().getAt(i).data['XNMC'] + "," + this.grid.getStore().getAt(i).data['XQCODE'];
				if(xnxqCode_m == this.xnxqCode) {
					this.xnxqTextValue = this.grid.getStore().getAt(i).data['XNMC'] + "年度 " + this.grid.getStore().getAt(i).data['XQMC'];
					this.xnxqCode = this.grid.getStore().getAt(i).data['XNMC'] + "," + this.grid.getStore().getAt(i).data['XQCODE'];
					this.setValue(this.xnxqTextValue);
					break;
				}
			}
		}
	},
	reset:function() {
		this.setValue("");
		this.xnxqCode = "";
		this.grid.getSelectionModel().clearSelections();
	},
	createUnit:function(ct, position){
		var thiz = this;		
		if(this.width == undefined) {
			this.width = 180;
		}
		var curWwwPath=window.document.location.href;
		//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		var pathName=window.document.location.pathname;
		var pos=curWwwPath.indexOf(pathName);
		//获取主机地址，如： http://localhost:8083
		var localhostPaht=curWwwPath.substring(0,pos);
		//获取带"/"的项目名，如：/uimcardprj
		var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
		if (navigator.userAgent.indexOf("Chrome")>-1) {
			this.unitEl = ct.createChild({
	            tag : 'div',
	            style: 'cursor:hand;position:absolute;margin-top:-20px;margin-left:'+(this.width-30)+'px;height:18px;width:18px;background-image:url('+localhostPaht+projectName+'/img/icons/query.png);background-repeat:no-repeat;background-size:18px 18px;'
			});
		}else if (navigator.appName.indexOf("Microsoft Internet Explorer")!=-1) {
			this.unitEl = ct.createChild({
	            tag : 'div',
	            style: 'cursor:hand;position:absolute;margin-top:0px;margin-left:-18px;height:18px;width:18px;background-image:url('+localhostPaht+projectName+'/img/icons/query.png);background-repeat:no-repeat;background-size:18px 18px;'
			});
		}else {
			this.unitEl = ct.createChild({
	            tag : 'div',
	            style: 'cursor:hand;position:absolute;margin-top:-20px;margin-left:'+(this.width-30)+'px;height:18px;width:18px;background-image:url('+localhostPaht+projectName+'/img/icons/query.png);background-repeat:no-repeat;background-size:18px 18px;'
			});
		}
		
		this.unitEl.on('click', function(e,t) { 
			//e是一个标准化的事件对象(Ext.EventObject) 
			//t就是点击的目标元素，这是个Ext.Element. 
			//对象指针this也指向t 
			thiz.win.show();
			if(this.xnxqCode != null && this.xnxqCode != "") {
				var total = thiz.grid.getStore().getCount();//数据行数
				for(var i=0;i<total;i++){
					var xnxqCode_m = thiz.grid.getStore().getAt(i).data['XNMC'] + "," + thiz.grid.getStore().getAt(i).data['XQCODE'];
					if(xnxqCode_m == this.xnxqCode) {
						thiz.grid.getSelectionModel().selectRow(i,true);
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
Ext.reg("uxxnqfield",Ext.ux.form.XnxqField);