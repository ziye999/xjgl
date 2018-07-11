Ext.ns('Ext.ux.form');
Ext.ux.form.TextField = Ext.extend(Ext.form.TextField,{
	unitText:"",
	EnterQueryHandler:"",
	onRender : function(ct, position) {   
		Ext.ux.form.TextField.superclass.onRender.call(this, ct, position);   	        
		this.createUnit(ct, position);
		this.registerEnterQueryEvent();
	},
	createUnit:function(ct, position){
		if (this.unitText != '') {   
			this.unitEl = ct.createChild({   
				tag : 'font',   
				html : this.unitText   
			});   
			if (typeof(this.width) != 'undefined' && this.width > 0) {   
				this.width = this.width - (this.unitText.replace(/[^\x00-\xff]/g, "xx").length * 6+ 10);   
			}   
			this.alignErrorIcon = function() {   
				this.errorIcon.alignTo(this.unitEl, 'tl-tr', [2, 0]);   
			};   
		}   
	},
	registerEnterQueryEvent:function(){		
		this.on('specialkey',function(field,e){if (e.getKey() == e.ENTER) {
			Ext.invoke(this.scope,this.EnterQueryHandler,null)
		}},this);
	}
});

Ext.reg("uxtextfield",Ext.ux.form.TextField);
Ext.ux.form.NumberField = Ext.extend(Ext.form.NumberField,{
	unitText:"",
	EnterQueryHandler:"",
	onRender : function(ct, position) {   
		Ext.ux.form.NumberField.superclass.onRender.call(this, ct, position);   
		this.createUnit(ct, position);
		this.registerEnterQueryEvent();
	},
	createUnit:function(ct, position){
		if (this.unitText != '') {   
			this.unitEl = ct.createChild({   
				tag : 'font',   
				html : this.unitText   
			});	           
			if (typeof(this.width) != 'undefined' && this.width > 0) {   
				this.width = this.width - (this.unitText.replace(/[^\x00-\xff]/g, "xx").length * 6 + 10);   
			}   
			this.alignErrorIcon = function() {   
				this.errorIcon.alignTo(this.unitEl, 'tl-tr', [2, 0]);   
			};   
		}   
	},
	registerEnterQueryEvent:function(){
		this.on('specialkey',function(field,e){if (e.getKey() == e.ENTER) {
		  	Ext.invoke(this.scope,this.EnterQueryHandler,null)
		}},this);
	}
});
Ext.reg("uxtextnumber",Ext.ux.form.NumberField);