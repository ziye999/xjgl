Ext.ux.ComboxTree =Ext.extend(Ext.form.ComboBox,{
	treeId :null,
	tree:null,
	constructor:function(cfg){
		this.tree = cfg.tree,
		this.treeId = Ext.id();
		Ext.applyIf(cfg, {
					    typeAhead: true,
					    triggerAction: 'all',
					    mode: 'local',
					    store: new Ext.data.Store({proxy: new Ext.data.MemoryProxy([[]]),reader: new Ext.data.ArrayReader({},[[],[]])})
		});	  
		Ext.ux.ComboxTree.superclass.constructor.call(this,{   
			store:new Ext.data.SimpleStore({fields:[],data:[[]]}),   
			editable:false,   
			mode: 'local',   
			triggerAction:'all',   
			tpl: '<div style="height:'+this.height+'px"><div id='+this.treeId+'></div></div>',   
			selectedClass:'', 
			onSelect:Ext.emptyFn   
		});
	},
	onTriggerClick : function(){
	 	var a = [this.el].concat(this.listAlign);
	 	this.tpl = "<div id='"+  this.treeId+"'></div>";
        this.tree.render(this.treeId);       
   		box.tpl = "<div id='"+config.divid+"'></div>";
        Ext.ux.ComboxTree.superclass.onTriggerClick.call(this);
    }
})
Ext.reg('uxcomboxtree',Ext.ux.ComboxTree);