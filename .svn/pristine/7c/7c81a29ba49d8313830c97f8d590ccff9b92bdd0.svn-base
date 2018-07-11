Ext.ns('Ext.ux.AtGrid');
/**
 * 树形
 * @class Ext.ux.TreePanel
 * @extends Ext.tree.TreePanel
 */
(function(){
	Ext.ux.AtGrid.PrintView = Ext.extend(Ext.Window,{
		constructor:function(config){
			this.unshow = false;
			this.gt_grid_id ="at_grid"+ Ext.id();
			this.atmodule = config.atmodule;
			
			this.B_print = new Ext.Button({text:"开始打印",iconCls:'p-icons-print',handler:this.doStartPrint,scope:this});
			this.B_Proges = new Ext.ProgressBar({text:'Waiting on you...',id:'pbar4', cls:'custom' });
			Ext.ux.AtGrid.PrintView.superclass.constructor.call(this,Ext.applyIf(config,{
				title:"打印预览",
				closeAction:'hide',
				plain:true,
				buttonAlign:"center",
				modal:true,
				labelAlign :'right',				
				shadow :false,
				collapsible :false,
				titleCollapse :false, 
				buttons:[this.B_print],
    			iconCls:'p-icons-print',
    			width:400,    			
    			maximizable :false,
    			html:this.getHTMLAtGridObject(),
    			items:[ this.B_Proges]			
			}));			
			this.on("hide",this.onHidden,this);
		},
		getHTMLAtGridObject:function(){			
			str = "<html><body><DIV style=visibility:hidden><object classid='clsid:D0CB6A0C-E656-4BF4-BD2D-6AE5EC183D7C' id='"+this.gt_grid_id+
						"' CODEBASE='"+JH.getRoot()+"'/ATGrid.CAB#version=2,2,11,108' align='center' width='100%' height='100%'>"+
						"<param name='filename' value='"+JH.getRoot()+"/plat/reports/"+this.atmodule+"'> "+
					"</object></DIV></body></html>"				
			return str;		
		},		
		getAtGrid :function(){
			return Ext.get(this.gt_grid_id).dom;						
		},
		printView:function(records){
			this.B_print.setDisabled(false);
			this.paramsRecord= records;
			this.cursor = 0;
			this.show(null,function(){
				if(this.unshow == true){
					this.hide();
				}
				this.B_Proges.updateText("共" +records.length +"条记录待打印");											
			},this);						
		},		
		getNextRecord :function(){			
			if(this.cursor<this.paramsRecord.length){
				return this.paramsRecord[this.cursor];
			}else{								
				Ext.invoke(this,"afterPrint",this.scope,this.paramsRecord);
				//this.hide();
				return null;
			}			
			return null;
		},		
		doStartPrint:function(){			
			var params = this.getNextRecord().data;
			this.loadPrintData(params);
			this.B_print.setDisabled(true);
		},		
		onHidden:function(){
			this.B_Proges.reset();
		},
		loadPrintData:function(params){
			JH.$request({
						params:params,
						method:this.method,
						loader:false,
						scope:this,
						handler:function(MsgBean,scope){
							scope.getAtGrid().ImportXML(MsgBean.data);
							if(scope.cursor==0){
								scope.doFristPrint();								
							}else{
								scope.doNextPrint();
							}							
						},
						/**
						 * 未知异常！硬件网络异常
						 */
						failure :function(response ,options ){
							
						}
			});
		},			
		/**
		 * 打印
		 */
		doFristPrint:function(){			
			var atGrid = this.getAtGrid();
			atGrid.SetTableStyle('PrintFullPage:yes;');   			
			if(atGrid.PrintEx("ShowPrintDlg:true;Print-Papersize:DMPAPER_USER")){				
				this.cursor++;
				this.updateProgess();
				if(this.getNextRecord()!=null){
					this.loadPrintData(this.getNextRecord().data);
				}
			};   
		},
		/**
		 * 连续打印
		 */
		doNextPrint:function(){
			var atGrid = this.getAtGrid();
			atGrid.SetTableStyle('PrintFullPage:yes;');   
			if(atGrid.PrintEx("ShowPrintDlg:false;Print-Papersize:DMPAPER_USER")){					
				this.cursor++;
				this.updateProgess();
				if(this.getNextRecord()!=null){						
					this.loadPrintData(this.getNextRecord().data);
				}					
			};   			
		},
		afterPrint:function(scope,record){
			
		},
		updateProgess:function(){
			var i=  this.cursor/this.paramsRecord.length;
			this.B_Proges.updateProgress(i, "正在打印第"+this.cursor+"条记录,共" + this.paramsRecord.length);
		}
	});
})();
Ext.reg('uxgtGrid', Ext.ux.AtGrid.PrintView);