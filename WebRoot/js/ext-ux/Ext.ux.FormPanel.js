Ext.ns('Ext.ux');
/**
 * 添加submit方法
 * @class Ext.ux.FormPanel
 * @extends Ext.form.FormPanel
 */
(function(){
	Ext.ux.FormPanel = Ext.extend(Ext.form.FormPanel,{
		/**
		 * 表单提交
		 * config:{
		 * 		callback:访问服务器成功后调用
		 * }
		 * @param {} config
		 */			
		constructor:function(config){
			Ext.applyIf(config,{
				labelWidth:65,
				bodyStyle:"padding:5px",
				frame:true,border:true,
				labelAlign :'right'
			})
			Ext.ux.FormPanel.superclass.constructor.call(this,config);
		},
		getInvalidFields:function() {
		    var invalidFields = [];
		    Ext.suspendLayouts();
		    this.form.getFields().filterBy(function(field) {
		        if (field.validate()) return;
		        invalidFields.push(field);
		    });
		    Ext.resumeLayouts(true);
		    return invalidFields;
		},
		/**
		 * sumbit提交
		 * showMask：是否显示进度条：默认true.
		 * waitTitle :"请稍后",  进度条标题
		 * waitMsg :"正在提交... ...  进度条"
		 */
		$submit:function(config){
			if(!this.getForm().isValid())return;
			Ext.applyIf(config,{handler:function(){}});
			Ext.applyIf(config,{
						showMask:true,							
						clientValidation:true,
						params:{},
						timeout: 3600000,
						async:false,
						success: function(form, action) {
							if(Ext.isDefined(action.result.show) && action.result.show){
								Ext.Msg.show({
									title:'消息!',
									msg: action.result.msg,
									buttons: Ext.Msg.OK,
									icon: Ext.MessageBox.INFO,
									scope:config.scope,
									fn: function(){config.handler(form,action.result,config.scope)}
								});
							}else{
								config.handler(form,action.result,config.scope);
							}
				    	},
				    	/**
				    	 * 异常处理
				    	 */
				    	failure: function(form, action) {
				    		switch (action.failureType) {
				    			//未指定clientValidation=true   验证表单提交的错误
					            case Ext.form.Action.CLIENT_INVALID:
					            	Ext.Msg.show({
					            		title:'错误',
					            		msg: '提交的表单可能含有错误的数值!',
					            		buttons: Ext.Msg.OK,
					            		icon: Ext.MessageBox.ERROR
									});
					            	break;
					            //故障类型时返回时发生通讯错误尝试发送一个请求到远程服务器。该反应可能进行检查，以提供进一步的信息。
					            case Ext.form.Action.CONNECT_FAILURE:
					            	Ext.Msg.show({
					            		title:'错误信息',
					            		msg: '无数据!',
					            		buttons: Ext.Msg.OK,
					            		icon: Ext.MessageBox.ERROR
									});
					            	break;
					            //故障类型时返回响应的成功属性设置为false，或没有字段值是在响应的数据属性返回。    
					            case Ext.form.Action.LOAD_FAILURE:
					            	Ext.Msg.show({
					            		title:'错误信息',
					            		msg: action.result.msg,
					            		buttons: Ext.Msg.OK,
					            		icon: Ext.MessageBox.ERROR
					            	});
					            	break;
					            //故障类型时返回服务器端处理失败，结果成功的属性设置为false。在表单提交的情况下，现场特定的错误消息可能会返回结果的错误的财产。
					            case Ext.form.Action.SERVER_INVALID :
					            	Ext.Msg.show({
					            		title:'错误信息',
					            		msg: action.result.msg,
					            		buttons: Ext.Msg.OK,
					            		icon: Ext.MessageBox.ERROR
					            	});
					            	break;
					            default :  
					            	Ext.Msg.show({
					            		title:'错误信息',
					            		msg: '未知错误!',
					            		buttons: Ext.Msg.OK,
					            		icon: Ext.MessageBox.ERROR
					            	});						                
				    		}
				    	}
				});
				/**
				 * 拼接URL地址 注入当前service
				 */
				Ext.applyIf(config,{url:config.action,timeout:3600000,async:false});				
				Ext.each(this.items.items,function(f){				
					if( Ext.isDefined(Ext.ux.ComboxGrid) && f instanceof Ext.ux.ComboxGrid){
						delete this[f.name];
						this[f.name] = f.hiddenValue;
					}
				},config.params);
				config.method="POST";
				config.timeout=3600000;
				config.async=false;
				if(config.showMask){
					Ext.applyIf(config,{
						waitTitle :"请稍后",
						waitMsg :"正在提交... ..."						
					});
				}												
				this.getForm().submit(config)
		},
		/**
		 * 
		 */	
		$load:function(config){
			Ext.applyIf(config,{handler:function(){}});			
			var loadMask = new Ext.LoadMask(Ext.isDefined(config.maskEl)?config.maskEl:this.el.dom, {
				msg : Ext.isDefined(config.msgTitle)?config.msgTitle:"加载数据中,请稍后... ..."
			});
			//if(config.loader){
				loadMask.show();
			//}
			Ext.applyIf(config,{
						params:{},
						//waitTitle :"请稍后",
						url: config.action,
						//waitMsg :"正在加载数据... ...",
						success: function(form, action) {
							loadMask.hide();
							var MsgBean = JH.$parseJSON(action.response.responseText);
							config.handler(form,MsgBean,config.scope);
				    	},
				    	/**
				    	 * 异常处理
				    	 */
				    	failure: function(form, action) {
				    		loadMask.hide();
				    		switch (action.failureType) {					        	
					            //故障类型时返回时发生通讯错误尝试发送一个请求到远程服务器。该反应可能进行检查，以提供进一步的信息。
					            case Ext.form.Action.CONNECT_FAILURE:
					            	Ext.Msg.show({
					            		title:'错误信息',
					            		msg: '无数据!',
					            		buttons: Ext.Msg.OK,
					            		icon: Ext.MessageBox.ERROR
									});
					            	break;
					            //故障类型时返回响应的成功属性设置为false，或没有字段值是在响应的数据属性返回。    
					            case Ext.form.Action.LOAD_FAILURE:
					            	Ext.Msg.show({
					            		title:'错误信息',
					            		msg: action.result.msg,
					            		buttons: Ext.Msg.OK,
					            		icon: Ext.MessageBox.ERROR
					            	});
					            	break;
					            //故障类型时返回服务器端处理失败，结果成功的属性设置为false。在表单提交的情况下，现场特定的错误消息可能会返回结果的错误的财产。
					            case Ext.form.Action.SERVER_INVALID :
					            	Ext.Msg.show({
					            		title:'错误信息',
					            		msg: action.result.msg,
					            		buttons: Ext.Msg.OK,
					            		icon: Ext.MessageBox.ERROR
					            	});
					            	break;
					            default :  
					            	Ext.Msg.show({
					            		title:'错误信息',
					            		msg: '未知错误!',
					            		buttons: Ext.Msg.OK,
					            		icon: Ext.MessageBox.ERROR
					            	});
				    		}
				    	}
				});
				/**
				 * 拼接URL地址 注入当前service
				 */
				Ext.applyIf(config,{url:config.action});
				config.method="POST";
				this.getForm().load(config)
		},
		/**
		 * 
		 */
		$reset:function(){this.getForm().reset()}
	});
})();	 
/**
 * 注册
 */
Ext.reg('uxform', Ext.ux.FormPanel);