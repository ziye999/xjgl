Ext.ns('Ext.ux.grid');
/**
 * 表格系统扩展
 */
/**
 * sortInfo :{
 * 		field: 'fieldName',
	    direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
 *		 }
 */
(function(){
	Ext.ux.grid.ColumnModel = Ext.extend(Ext.grid.ColumnModel,{
		constructor:function(config){			
			Ext.ux.grid.ColumnModel.superclass.constructor.call(this,config);						
			for(var col= 0 ;col<config.length;col++){
				var cm  = config[col];
				if(cm.cfg){					
					cm.store = new Ext.data.JsonStore({
						autoLoad:false,
						baseParams :Ext.apply({method:Ext.isDefined(cm.cfg.method)?cm.cfg.method:'geiDropListByClassCode',service:Ext.isDefined(cm.cfg.service)?cm.cfg.service:'DropListService'},cm.cfg.params),
						url:JH.getAjaxURL(),
						fields:["codeid","codename","custom1","custom2","custom3","custom4","custom5"]
					});
					cm.store.load();
				}
			}		
		}		
	})
})();
Ext.reg("uxgridcm",Ext.ux.grid.ColumnModel);