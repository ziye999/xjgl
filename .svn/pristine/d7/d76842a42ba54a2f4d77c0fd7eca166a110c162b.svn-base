Ext.ux.Combox = Ext.extend(Ext.form.ComboBox,{
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
						url:"dropListAction_"+cfg.dropAction+".do?params="+encodeURI(cfg.params),
						fields:["CODEID","CODENAME"]
			}),
			typeAhead:true,
			triggerAction:'all',
			lazyRender:false,
			mode:'remote',
			valueField:'CODEID',
			displayField:'CODENAME'
		});
		Ext.applyIf(this,cfg);		
		Ext.ux.Combox.superclass.constructor.call(this,cfg);
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
        Ext.ux.Combox.superclass.onRender.call(this, ct, position);        
        if(disabled===true) this.setDisabled(disabled);
    }
})
Ext.reg("uxcombox",Ext.ux.Combox);