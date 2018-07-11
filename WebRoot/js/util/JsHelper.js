(function(){
	JsHelper = Ext.extend(Ext.util.Observable,{
		getCssRoot:function(){
			return this.getRoot()+"/js/ext/resources/css/";
		},	
		getRoot:function(){
			return Ext.get('RootPath').dom.value;
		},
		getCurrentService:function(){
			return this.getCurrentMoudle().service;
		},
		getCurrentMoudle:function(){
			return Ext.getCmp("plat-iframe-container").getActiveTab().module;
		},
		getDynamicAction:function(){
			return "/DynamicAction.do";
		},
		getAjaxURL:function(){
			return this.getRoot()+this.getDynamicAction();
		},
		setUSER:function(user){
			this.USER  = user;
		},
		getUSER:function(){
			return this.USER;
		},	
		/**
		 * maskEl：进度条的显示位置
		 */
		$request:function(config){
			var thiz = this;
			Ext.applyIf(config,{loader:true});
			var loadMask = new Ext.LoadMask(Ext.isDefined(config.maskEl)?config.maskEl:Ext.getBody(), {
				msg : Ext.isDefined(config.msgTitle)?config.msgTitle:"正在访问后台,请稍后... ..."
			});
			if(config.loader){
				loadMask.show();
			}
			Ext.applyIf(config,{
				params:{},
				url :config.action,
				handler:function(){},
				scope:this,
				method :"POST",	
				/**
				 * 系统对结果处理，包括对可捕获异常处理
				 */
				success :function(response ,options ){
				
				},
				callback :function(options ,success ,response ){				
					loadMask.hide();
					if(success){
						var MsgBean = thiz.$parseJSON(response.responseText);
						if(Ext.isEmpty(MsgBean))return;
						if(MsgBean.show  && MsgBean.success){
							Ext.Msg.show({
								title:'消息',
								msg: MsgBean.msg,
								buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.INFO,
								scope:config.scope,
								fn: function(){config.handler(MsgBean,config.scope)}
							});
						}else if(MsgBean.show && !MsgBean.success){					  	
							Ext.Msg.show({
								title:'错误',
								msg: MsgBean.msg,
								buttons: Ext.Msg.OK,
								icon: Ext.MessageBox.ERROR
							});
						}else{
							config.handler(MsgBean,config.scope)
						}
					}
				},
				/**
				 * 未知异常！硬件网络异常
				 */
				failure :function(response ,options ){				
					loadMask.hide();
					Ext.Msg.show({
						title:'出现未处理异常信息,请检验!',
						msg: response.responseText,
						buttons: Ext.Msg.OK,
						icon: Ext.MessageBox.ERROR
					});
				},
				scope:this,		
				isUpload :true
			});		
			config.method="POST";
			Ext.Ajax.request(config);		
		},
		/**
		 * 解析字符串成Object
		 * @param {} s
		 * @return {}
		 */
		$parseJSON:function(s){
			var result = null;
			if(s!=null && s!=undefined && s.length>1) {
				var l = s.substring(0,1);
				var r = s.substring(s.length-1);
				if(l=="[" && r=="]") {
					result = eval(s);
				}
				if(l=="{" && r=="}") {
					result = eval("("+s+")");
				}
			}else {
				result = s;
			}
			return result;
		},
		/**
		 * 指定主题
		 */
		$setThemePickeer:function(color){
			if(!Ext.isDefined(color))return;
			switch (color) {
				case '000000' : // 黑色（光滑的）
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-slickness.css");
					break;
				case '545554' : // 黑色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-black.css");
					break;
				case 'ABADAF' : // 深灰色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-darkgray.css");
					break;
				case 'D8D8D8' : // 灰色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-gray.css");
					break;
				case '424370' : // 深紫蓝色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-midnight.css");
					break;
				case '52567E' : // 紫蓝色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-indigo.css");
					break;
				case '5E7189' : // 暗蓝灰色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-slate.css");
					break;
				case 'BDD3EF' : // 淡蓝色（默认）
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext-all.css");
					break;
				case 'D1C5FF' : // 紫色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-purple.css");
					break;
				case '9ACD68' : // 橄榄色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-olive.css");
					break;
				case '9CD4C1' : // 绿色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-green.css");
					break;
				case 'FC6161' : // 红色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-red5.css");
					break;
				case 'FFB5B5' : // 薄荷红
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-peppermint.css");
					break;
				case 'E2B4D5' : // 粉红色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-pink.css");
					break;
				case 'C49E92' : // 咖啡色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-chocolate.css");
					break;
				case 'FF8C37' : // 橙色
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext/xtheme-orange.css");
					break;
				case 'C72929' : // 红间灰
					Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"/ext/xtheme-silverCherry.css");
					break;
				default:Ext.util.CSS.swapStyleSheet("theme", this.getCssRoot()+"ext-all.css");
			}					
		},
		getId:function(){
			return Ext.id();
		},
		//end	
		$applyIfValue:function(params,baseparams){
	
		},
		maskImg:function(continer,m){
			m.mask = document.createElement("div");
			//m.mask.id = m.oim.__maskid + "_mask";
			m.mask.style.position  = "absolute";
			m.mask.style.width = m.maskX + "px";
			m.mask.style.height = m.maskY + "px";
			m.mask.style.left = 555 + "px";
			m.mask.style.top = 40 + "px";    
			m.mask.style.backgroundImage  = "url("+m.src+")";
			m.mask.style.backgroundRepeat = "no-repeat";     
			m.mask.style.display = "none";
			m.mask.style.zIndex = 3000;  
			continer.el.dom.appendChild(m.mask);
			return m;
		},
		setEditDisabled:function(from,option){
			var ss = from.findByType('textfield');
			Ext.each(ss,function(s){
				s.setDisabled(option)
			});
		}
	});	
})();