Ext.ns('Ext.ux');
/**
 * 添加submit方法
 * @class Ext.ux.FormPanel
 * @extends Ext.form.FormPanel
 */
(function(){
	Ext.ux.ChartPanel = Ext.extend(Ext.Panel,{		
			url:"",
			iframeId:"",
			width:null,
			baseParams:{},
			height:null,
			constructor:function(config){
				this.iframeId = Ext.id();
				var service=Ext.isDefined(config.service)?config.service:JH.getCurrentService();
				var	method =config.method;
				Ext.applyIf(config,{
					width:"100%",
					height:"100%",
					anchor:"100% 100%"										
					//html:"<iframe id="+this.iframeId+" width='100%' height='100%' frameborder='0'  src=''/>"
				});
				this.url =  JH.getAjaxURL()+"?service="+service+"&method="+method+"&temp="+new Date().getTime();
				Ext.ux.ChartPanel.superclass.constructor.call(this,config);				
				this.on("resize",function(Component, adjWidth,adjHeight,rawWidth,rawHeight ) {
							this.width = this.getInnerWidth();
							this.height = this.ownerCt.getInnerHeight();
							this.repaint(this.params);
				},this);								
			},
			show :function(ct,position){
				Ext.ux.ChartPanel.superclass.show.call(this,ct,position);
				this.width = this.getInnerWidth();
				this.height = this.ownerCt.getInnerHeight();
			},
			repaint:function(params){
				Ext.apply(this.baseParams,params);
				JH.$request({
					method:"POST",
					msgTitle:'正在生成.......',
					maskEl:this.el,
					params:Ext.apply({width:this.width,height:this.height,ajax_request:"1"},this.baseParams),
					url:this.url,
					scope:this,
					loader:true,
					handler: function(MsgBean, scope) {
						scope.insert(0,{
							width:scope.width,
							heigth:scope.height,
							html:MsgBean.data
						});
						scope.doLayout();
						//Ext.get(this.iframeId).dom.src=JH.getRoot()+"/plat/chart/FusionChartView.jsp";
					}
				})
			}
	});
})();
/**
 * 注册
 */
Ext.reg('uxform', Ext.ux.FormPanel);