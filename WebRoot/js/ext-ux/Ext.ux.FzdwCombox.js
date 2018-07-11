Ext.ux.FzdwCombox = Ext.extend(Ext.form.ComboBox,{
	disabled:false,
	editable:false,
	emptyText:'请选择...',
	constructor:function(cfg){
		Ext.applyIf(cfg,{
			params:{},
			valueNotFoundText:"",
			forceSelection:true,
			lazyInit:true,
			Dynic:false,
			addEmpty:true,
			autoLoad:true
		});		
		Ext.applyIf(cfg,{
			store:new Ext.data.JsonStore({
						autoLoad:false,
//						url:"sign_getFzdw.do"
						url:"sign_getFzdww.do?dictCode="+cfg.dictCode,
						fields:["dm","mc"]
					}),
			 typeAhead:true,
			 triggerAction:'all',
			 lazyRender:false,
			 mode:'remote',
			 valueField:'dm',
			 displayField:'mc'
		});
		Ext.applyIf(this,cfg);		
		Ext.ux.FzdwCombox.superclass.constructor.call(this,cfg);
		if(this.Dynic){			
			this.on("expand",function(com){com.store.removeAll(true)});
		}
		if(this.autoLoad){
			this.store.load();
		}
	},
	onRender : function(ct, position){
		var disabled = this.disabled;
		this.disabled = false;
		Ext.ux.FzdwCombox.superclass.onRender.call(this, ct, position);
        if(disabled===true) this.setDisabled(disabled);               
    }
})
Ext.reg("uxfzdwCombox",Ext.ux.FzdwCombox);