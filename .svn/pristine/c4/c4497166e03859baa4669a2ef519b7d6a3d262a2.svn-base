Ext.ns('Ext.ux');
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
	Ext.ux.GridPanel = Ext.extend(Ext.grid.GridPanel,{
		constructor:function(config){
			Ext.applyIf(config,{
				excelType:'local',
				rowNumber:true,
				page:true,
				grouping:false,
				pagecombox:true,
				excelTitle:"",
				sortInfo:null,
				sm: new Ext.grid.CheckboxSelectionModel({singleSelect :true}),
				rowSelect:false,
				rowSingleSelect:false,
				displayInfo:true,
				excel:false,
				showtotalpage:true,
				viewConfig :{autoFill :true}
			});
			if(!config.view){
				config.view = new  Ext.ux.grid.GridView(config.viewConfig);
			}			
			if(config.rowNumber){
				config.cm.splice(0,0,new Ext.grid.RowNumberer({header:"序号",width:40}));
			}
			if(config.rowSelect){
				var sm = new Ext.grid.CheckboxSelectionModel({singleSelect :config.rowSingleSelect});
				config.cm.splice(1,0,sm);
				config.sm = sm;
			}
			
			config.cm = new Ext.ux.grid.ColumnModel(config.cm);			
			if(config.grouping){
				config.store = new Ext.data.GroupingStore({
								baseParams :{method:config.method,service:Ext.isDefined(config.service)?config.service:JH.getCurrentService()},
								url:JH.getAjaxURL(),
							    reader: new Ext.data.JsonReader({   
					                remoteSort:true, 
					              	autoLoad:false,
					              	root: 'resultList',
							    	totalProperty: 'totalPages',
					                fields:config.fields
					            }),   
					            sortInfo:config.sortInfo,
					            groupField:Ext.isDefined(config.groupField)?config.groupField:config.fileds[0]
				});   
			}else{
				config.store = new Ext.data.JsonStore({
								root: 'resultList',							
						    	totalProperty: 'totalPages',
								url:config.action,
								sortInfo:config.sortInfo,
								fields:config.fields
				});
			}
			
			if(config.page){
				var combox = this.getCombox();
				var displayMsg= '显示第{0}到{1}数据' ;
				if(config.showtotalpage){						  
					displayMsg= '显示第{0}到{1}数据,共{2}条' ;
				}
										
				config.bbar  = new Ext.PagingToolbar({
							displayInfo :true,
							pageSize:20,
							store:config.store,
							displayMsg : displayMsg ,
							emptyMsg :'无数据',
							displayInfo :config.displayInfo
							//items:["-","每页数显示:",combox,"条"]
				});
				if(config.excel){ 
					config.bbar.add("-");
					config.bbar.add({
							tooltip :"导出EXCEL",
							xtype:"button",
							iconCls:"p-icons-excel",
							handler:this.exportExcel,
							scope:this
					});
				}
				if(config.pdf){
					config.bbar.add("-");
					config.bbar.add({
							tooltip :"导出PDF",
							xtype:"button",
							iconCls:"p-icons-pdf",
							handler:this.exportPdf,
							scope:this
					});
				}				
				if(config.pagecombox){
					var combox = this.getCombox();
					config.bbar.add("-");
					config.bbar.add("每页数显示:");
					config.bbar.add(combox);
					config.bbar.add("条");
					combox.on("select",this.doPageSizeSelect,config.bbar);
				}				
			}
			Ext.applyIf(config,{
				//store:config.store,
				//cm:config.cm,
				//bbar:config.bbar,
				//border:false,
				//RowNumberer:true,
				loadMask:{msg:'数据读取中,请稍后......'}
			});
			Ext.ux.GridPanel.superclass.constructor.call(this,config);
			
			if(config.showtotalpage){
				this.store.baseParams.showtotalpage = true;
			}else{
				this.store.baseParams.showtotalpage = false;
			}
		},
		getCombox:function(items){
			return new Ext.form.ComboBox({
					    typeAhead: true,
					    triggerAction: 'all',
					    lazyRender:true,
					    mode: 'local',
					    name:"limit",
					    value:20,
					    width:60,
					    store: new Ext.data.ArrayStore({
					        fields: [
					            'limit',
					            'displayText'
					        ],
					        data: [[20, '20'], [50, '50'], [100, '100'],
					        	   [150, '150'], [200, '200']
					        	   , [400, '400'], [800, '800'], [1200, '1200']]
					    }),
					    valueField: 'limit',
					    displayField: 'displayText'
					    
			});
		},
		/**
		 * 选择每页显示条数
		 */
		doPageSizeSelect:function(combo,record,index){		
			this.pageSize = record.get('limit');
			this.cursor = record.get('start');
			this.doLoad(this.cursor);
		},
		/**
		 * 查询数据
		 */
		
		$load:function(params,callback,scope,add){ 
			if(this.store){			
				Ext.apply(this.store.baseParams,{
					start:this.getBottomToolbar().cursor,
					limit:this.getBottomToolbar().pageSize
				})
				Ext.apply(this.store.baseParams,params);
				this.store.load({params:{},callback :callback ,scope :scope,add:add});
			}else {
				Ext.apply(this.store.baseParams,params);
				this.store.load({params:{},callback :callback ,scope :scope,add:add});
			}
		},		
		/**
		 *  导出excel
		 */
		exportExcel:function(){
			var that = this;
			var columns = [];
			var cm = this.getColumnModel();
			var data    = [];
			var count = cm.getColumnCount();
			for(var i = 0 ;i< cm.config.length;i++){
				var column = cm.config[i];
				if(column.hidden==true){continue;}
				if(Ext.isDefined(column.exportable) && column.exportable==false)continue;
				if(!Ext.isEmpty(column.dataIndex)){
					columns.push({
						header:column.header,
						width: column.width,
						dataIndex:column.dataIndex,
						aligns:column.align
					});
				}
			}
			if(that.excelType=='local'){
				/*Ext.each(cm.config,function(column){
				if(!Ext.isEmpty(column.dataIndex)){
					this.push({
								header:column.header,
								width: column.width,
								dataIndex:column.dataIndex,
								aligns:column.align
					});
				}						
			},columns);*/
				
				this.store.each(function(record){
					data.push(record.data);
				},this);
				JH.$request({
					msgTitle:"正在导出EXCEL,请稍后......",
					action:"excelAction_exportExcel.do",
					maskEl:this.el,
					scope:this,
					handler:function(MsgBean,scope){
						if(!Ext.isEmpty(MsgBean.data)){
							var url = "excelAction_downLoadExcelByTag.do";
							var u = Ext.urlEncode({
								//	title:scope.excelTitle,
								tag:MsgBean.data
							});
							url+="?"+u;
							//url+="&title="+scope.excelTitle;						
							window.location.href=url
						}
					},
					params:{headers:Ext.util.JSON.encode(columns),dataset:Ext.util.JSON.encode(data),excelTitle:this.excelTitle}
				})
			}else{ 
				JH.$request({
					msgTitle:"正在导出EXCEL,请稍后......",
					action:"excelAction_exportExcelByLink.do",
					maskEl:this.el,
					scope:this, 
					handler:function(MsgBean,scope){
						if(!Ext.isEmpty(MsgBean.data)){
							var url = "excelAction_downLoadExcelByTag.do";
							var u = Ext.urlEncode({
								//	title:scope.excelTitle,
								tag:MsgBean.data
							});
							url+="?"+u;
							//url+="&title="+scope.excelTitle;						
							window.location.href=url
						}
					},
					params:{link:that.excelType,headers:Ext.util.JSON.encode(columns),excelTitle:that.excelTitle}
				})
			}
		},
		exportPdf:function(){
			var columns = [];
			var data    = [];
			var cm = this.getColumnModel();
			var count = cm.getColumnCount();
			
			/*Ext.each(cm.config,function(column){
				if(!Ext.isEmpty(column.dataIndex)){
					this.push({
								header:column.header,
								width: column.width,
								dataIndex:column.dataIndex,
								aligns:column.align
					});
				}						
			},columns);*/
			
			for(var i = 0 ;i< cm.config.length;i++){
				var column = cm.config[i];					
				if(Ext.isDefined(column.exportable) && column.exportable==false)continue;
				if(!Ext.isEmpty(column.dataIndex)){
					columns.push({
									header:column.header,
									width: column.width,
									dataIndex:column.dataIndex,
									aligns:column.align
					});
				}
			}				
				
			this.store.each(function(record){
				data.push(record.data);
			},this);
			JH.$request({
				msgTitle:"正在导出PDF,请稍后......",
				action:"pdfAction_exportPdf.do",
				maskEl:this.el,
				scope:this,
				handler:function(MsgBean,scope){					
					if(!Ext.isEmpty(MsgBean.data)){
						var url = "pdfAction_downLoadPdfByTag.do";
						var u = Ext.urlEncode({
								//title:scope.excelTitle,
								tag:MsgBean.data
						});
						url+="?"+u;
						//url+="&title="+scope.excelTitle;
						window.location.href=url
					}
				},
				params:{headers:Ext.util.JSON.encode(columns),dataset:Ext.util.JSON.encode(data),excelTitle:this.excelTitle}
			})
		}		
	})
})();
Ext.reg("uxgrid",Ext.ux.GridPanel);