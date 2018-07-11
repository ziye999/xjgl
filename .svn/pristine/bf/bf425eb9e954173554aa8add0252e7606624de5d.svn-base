Ext.ns('Ext.ux');
/**
 * 树形
 * @class Ext.ux.TreePanel
 * @extends Ext.tree.TreePanel
 */
Ext.ux.TreePanel = Ext.extend(Ext.tree.TreePanel,{
	constructor:function(config){
		Ext.apply(this, config,{
			rootVisible:false,
			collapseMode:"mini",
            split:true,
            autoScroll : true,
            //tools:[{id:'refresh',handler:function(){
            //  this.root.reload();
            //},scope:this}], 
			border:false,
			loader: new Ext.tree.TreeLoader({
			    url:  config.action
			}),
			root: new Ext.tree.AsyncTreeNode({
				id: 'root',
				text: '树形菜单',
				expanded: true
			})
		});
		Ext.ux.TreePanel.superclass.constructor.call(this,config);
	}
});
Ext.reg('uxtree', Ext.ux.TreePanel);