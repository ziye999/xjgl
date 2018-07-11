Ext.ns('Ext.ux');
/**
 * 树形
 * @class Ext.ux.TreePanel
 * @extends Ext.tree.TreePanel
 */
(function(){
		 Ext.ux.Window = Ext.extend(Ext.Window,{
		constructor:function(config){
			Ext.ux.Window.superclass.constructor.call(this,Ext.applyIf(config,{
				closeAction:'hide',
				plain:true,
				buttonAlign:"center",
				modal:true,
				labelAlign :'right',
				constrainHeader :true,
				shadow :false,
				titleCollapse :true, 
				resizable:false
			//	draggable :false  不可移动
			}));
		}		
	});
})();
Ext.reg('uxWindow', Ext.ux.Window);