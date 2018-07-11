Ext.ux.ComboxGrid =Ext.extend(Ext.form.ComboBox,{
	gridid:null,
	constructor:function(config){		
		this.gridid = Ext.id()+'_comboxgrid';
		Ext.applyIf(config,{
			store:new Ext.data.SimpleStore({fields:["CODEID","CODENAME"],data:[[]]}),  
			editable:false,   
			mode: 'local',   
			triggerAction:'all',  
			tpl: '<div id='+this.gridid+'></div>',  
			selectCfg:{
				codeid:"",
				codename:""
			},
			cm:[],
			fields:[],
			height:200,
			rowNumber:false,
			selectedClass:'',
			method:"",
			service:Ext.isDefined(config.service)?config.service:JH.getCurrentService(),
			onSelect:Ext.emptyFn,
			valueField:'CODEID',
			displayField:'CODENAME'
		});
		config.grid =  new Ext.ux.GridPanel({
			rowNumber:config.rowNumber,
			height:config.height,
			pagecombox:false,
			displayInfo:false,
			method:config.method,
			service:config.service,
			cm:config.cm,
			fields:config.fields
		});
		Ext.ux.ComboxGrid.superclass.constructor.call(this,config);
		this.on("expand",this.doExpand,this);
		this.grid.on("rowclick",this.rowclick,this);
	},
	onViewClick:function(){
		
	},
	doExpand:function(){
		this.grid.render(this.gridid);  
		this.grid.$load();   
	},
	rowclick:function(grid,rowindex,e){
		var codeid = grid.store.getAt(rowindex).data[this.selectCfg.CODEID];
		var codename = grid.store.getAt(rowindex).data[this.selectCfg.CODENAME];
		var rt = this.store.recordType;
		var r = new rt();
		r.data["CODEID"] = codeid;
		r.data["CODENAME"] = codename;
		if(this.fireEvent('beforeselect', this, r, rowindex) !== false){
          	this.setRawValue(r.data["CODENAME"]);
          	this.hiddenValue = r.data["CODEID"];
            this.collapse();
            this.fireEvent('select', this, r, rowindex);
        }
	}
})
Ext.reg('uxcomboxgrid',Ext.ux.ComboxGrid);