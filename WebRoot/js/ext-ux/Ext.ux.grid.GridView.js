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
	Ext.ux.grid.GridView = Ext.extend(Ext.grid.GridView,{
		constructor:function(config){		
			Ext.ux.grid.GridView.superclass.constructor.call(this,config);
		},
		doRender : function(columns, records, store, startRow, colCount, stripe) {
	        var templates = this.templates,
	            cellTemplate = templates.cell,
	            rowTemplate = templates.row,
	            last = colCount - 1,
	            tstyle = 'width:' + this.getTotalWidth() + ';',
	            // buffers
	            rowBuffer = [],
	            colBuffer = [],
	            rowParams = {tstyle: tstyle},
	            meta = {},
	            len  = records.length,
	            alt,
	            column,
	            record, i, j, rowIndex;
	        //build up each row's HTML
	        for (j = 0; j < len; j++) {
	            record    = records[j];
	            colBuffer = [];
	            rowIndex = j + startRow;
	            //build up each column's HTML
	            for (i = 0; i < colCount; i++) {
	                column = columns[i];
	                meta.id    = column.id;
	                meta.css   = i === 0 ? 'x-grid3-cell-first ' : (i == last ? 'x-grid3-cell-last ' : '');
	                meta.attr  = meta.cellAttr = '';
	                meta.style = column.style;
	                if(this.cm.getColumnAt(i).store){
	                	for(var m=0 ; m<this.cm.getColumnAt(i).store.getCount();m++){
	                		var r = this.cm.getColumnAt(i).store.getAt(m);
	                		if(r.data.codeid==record.data[column.name]){
								record.data[column.name] = r.data.codename;
								break;
							}
	                	}
						
					}
	                meta.value = column.renderer.call(column.scope, record.data[column.name], meta, record, rowIndex, i, store,this.cm.getColumnAt(i).store);
	                /**
					if(this.cm.getColumnAt(i).store){
						this.cm.getColumnAt(i).store.each(function(record){
							if(record.data.codeid==meta.value){
								meta.value = record.data.codename;
							}
						})
					}*/
					
	                if (Ext.isEmpty(meta.value)) {
	                    meta.value = '&#160;';	                    
	                }
	                if (this.markDirty && record.dirty && typeof record.modified[column.name] != 'undefined') {
	                    meta.css += ' x-grid3-dirty-cell';
	                }	
	                colBuffer[colBuffer.length] = cellTemplate.apply(meta);
	            }	
	            alt = [];
	            //set up row striping and row dirtiness CSS classes
	            if (stripe && ((rowIndex + 1) % 2 === 0)) {
	                alt[0] = 'x-grid3-row-alt';
	            }	
	            if (record.dirty) {
	                alt[1] = ' x-grid3-dirty-row';
	            }	
	            rowParams.cols = colCount;
	            
	            if (this.getRowClass) {
	                alt[2] = this.getRowClass(record, rowIndex, rowParams, store);
	            }	
	            rowParams.alt   = alt.join(' ');
	            rowParams.cells = colBuffer.join('');	
	            rowBuffer[rowBuffer.length] = rowTemplate.apply(rowParams);
	        }	
	        return rowBuffer.join('');
   	 	}					
	})
})();
Ext.reg("uxgridview",Ext.ux.grid.GridView);