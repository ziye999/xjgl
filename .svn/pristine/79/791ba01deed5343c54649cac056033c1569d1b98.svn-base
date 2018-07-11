Ext.ns('Ext.AtGrid');
/**
 * 树形
 * @class Ext.ux.TreePanel
 * @extends Ext.tree.TreePanel
 */
(function(){
	Ext.AtGrid.PrintView = Ext.extend(Ext.Window,{
		constructor:function(config){						
			this.unshow = false;
			this.gt_grid_id ="at_grid"+ Ext.id();
			this.atmodule = config.atmodule;
			this.B_export = new Ext.Button({text:"导出Excel",handler:this.exportExcel,scope:this});
			this.B_printPreview = new Ext.Button({text:"打印预览",handler:this.PrintPreview,scope:this});
			this.B_print = new Ext.Button({text:"打  印",iconCls:'p-icons-print',handler:this.Print,scope:this});
			Ext.AtGrid.PrintView.superclass.constructor.call(this,Ext.applyIf(config,{
				title:"打印预览",
				closeAction:'hide',
				plain:true,
				buttonAlign:"center",
				modal:true,
				labelAlign :'right',
				constrainHeader :true,
				shadow :false,
				collapsible :true,
				titleCollapse :true, 
				buttons:[this.B_export,this.B_printPreview,this.B_print],
    			iconCls:'p-icons-print',
    			width:screen.availWidth-10,
    			height:screen.availHeight-50,
    			maximizable :true,
    			autoScroll:true,
    			layout:"fit",
    			html:this.getHTMLAtGridObject()
    			//draggable :false  不可移动
			}));
		},
		getHTMLAtGridObject:function(){			
			str = "<html><body><DIV><object classid='clsid:D0CB6A0C-E656-4BF4-BD2D-6AE5EC183D7C' id='"+this.gt_grid_id+
						"' CODEBASE='"+JH.getRoot()+"'/ATGrid.CAB#version=2,2,11,108' align='center' width='100%' height='100%'>"+
						"<param name='filename' value='"+JH.getRoot()+"/plat/reports/"+this.atmodule+"'>"+
					"</object></DIV></body></html>"				
			return str;	
		},		
		getAtGrid :function(){
			return Ext.get(this.gt_grid_id).dom;						
		},
		printView:function(params){
			this.show(null,function(){
				if(this.unshow == true){
					this.hide();
				}			
				JH.$request({
						params:params,
						method:this.method,
						scope:this,
						handler:function(MsgBean,scope){
							if(scope.unshow == true){
								scope.Print();
							}
							scope.getAtGrid().ImportXML(MsgBean.data);
						}
				});
			},this);
		},
		/**
		 * 导出ExceL
		 */
		exportExcel:function(){
			var atGrid = this.getAtGrid();
			var  filename = atGrid.ShowDlgOpenFile('','*.xls;',false);   
			if( filename=='' ){   
				alert('文件名无效!');   
				return;   
			}   
			atGrid.SaveAs(filename); 
		},
		/**
		 * 打印预览
		 */
		PrintPreview:function(){
			var atGrid = this.getAtGrid();
			atGrid.PrintPreviewEx('PreviewOption-PrintDlg:true');   
		},
		/**
		 * 打印
		 */
		Print:function(){			
			var atGrid = this.getAtGrid();
			atGrid.SetTableStyle('PrintFullPage:yes;');   
			if(atGrid.PrintEx("ShowPrintDlg:true;Print-Papersize:DMPAPER_USER")){
				Ext.invoke(this,"afterPrint",this.scope);
			};   
		},
		afterPrint:function(scope){
			
		}
	});
})();
Ext.reg('gtGrid', Ext.AtGrid.PrintView);