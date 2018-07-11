/**
 * 
 * config :{}
 *         borderColor: "表格边框颜色" ;
 *         tableAlign:  "表格位置" 默认 "center".	
 *         rowHeigh  :  "每行的高度"  默认 24 。
 *         headerbgcolor:"标题背景颜色  默认:#EFF3F7"
 * 		   cm:          "表格列配置项" Array   "参考Ext.grid.cm";
 * 		   waitMsg:
 *    	   isRowNumber  :是否添加序号,
 *   	   dynicLoad:每次都访问
 *         paramsName   :fields 中的名字作为参数  ，默认传给后台所有该展开列的值 参数名为：record
 * 						{
 * 							width  :"列宽"    默认 标题长度
 * 							align: "文字位置", 默认:居中
 * 							sum :  是否统计  默认 false
 * 							sumconfig ：{
 * 											title  :"统计标题"
 * 											sumtype:"统计类型"     两种类型 ： "sum"-----"求和"  "count"  ---"统计个数"  默认count 
 * 											unit   ："统计单位"
 * 										} 
 * 						}
 * 
 * @class myRowExpander
 * @extends Ext.ux.grid.RowExpander
 */
myRowExpander = Ext.extend(Ext.ux.grid.RowExpander, {
	constructor : function(config) {
		Ext.apply(this, config, {
					borderColor : "blue",
					tableAlign : "left",
					rowHeigh : 24,
					headerbgcolor : "#EFF3F7",
					dynicLoad:false,
					isRowNumber:false,
					waitMsg:"正在加载数据.....",
					store:new Ext.data.JsonStore({
								root: 'resultList',
						    	totalProperty: 'totalPages',
								baseParams :{method:config.method,service:Ext.isDefined(config.service)?config.service:JH.getCurrentService()},
								url:JH.getAjaxURL(),
								sortInfo:config.sortInfo,
								fields:config.fields
							})
		});

		var cm = this.cm;
		for (var i = 0; i < cm.length; i++) {
			Ext.applyIf(cm[i], {
						width : cm[i]["header"].length * 20,
						align : "center",
						sum : false,
						sumconfig : {}
			});
			Ext.applyIf(cm[i].sumconfig, {
						title : "",
						unit : "",
						sumtype : "count"
			})
		};

		if (this.tpl) {
			if (typeof this.tpl == 'string') {
				this.tpl = new Ext.XTemplate(this.tpl);
			}
		} else {
			this.tpl = new Ext.XTemplate(this.createtableModule(this.cm));
		}
		myRowExpander.superclass.constructor.call(this);
	},
	load : function(record, body, rowIndex) {
		var myMask = new Ext.LoadMask(body, {
					msg :this.waitMsg
		});
		myMask.show();
		var params =  record.data;	
		this.store.load({
			params : params,
			callback : function(records, options) {
				body.innerHTML = this.getBodyContent(record, rowIndex, records);
				myMask.hide();
			},
			scope : this
		});
	},
	onRender: function() {
        this.grid.store.on("load",function(store,records,option){
        	var length = store.data.length;
	 			for(var i=0; i<length; i++) {
	 				this.collapseRow(i);
	 			}
        },this);
        myRowExpander.superclass.onRender.call(this);
    }
});
Ext.override(myRowExpander, {
	getBodyContent : function(record, row, records) {
		var cm = this.cm;
		var type = new this.store.recordType();
		var title = "";
		for (var i = 0; i < cm.length; i++) {
			if (cm[i].sum) {
				var count = this.store.getCount();
				if (cm[i].sumconfig.sumtype == "sum") {
					count = this.store.sum(cm[i].dataIndex, 0);
				}
				var unit = cm[i].sumconfig.unit;
				title = cm[i].sumconfig.title + ":" + count + " " + unit;
				type.set(cm[i]["dataIndex"], title);
				records.push(type);
			}
		};
		
		var columns = [];
		for (var i = 0; i < records.length; i++) {
			columns.push(records[i].data);
		}
		var data = {
			row : columns
		};
		if (!this.enableCaching) {
			return this.tpl.apply(data);
		}
		var content = this.bodyContent[record.id];
		if (!content) {
			content = this.tpl.apply(data);
			this.bodyContent[record.id] = content;
		}
		return content;
	},
	beforeExpand : function(record, body, rowIndex) {
		if (this.fireEvent('beforeexpand', this, record, body, rowIndex) !== false) {
			if (body.innerHTML == "") {
				this.load(record, body, rowIndex);
			}else if(this.dynicLoad){
				this.load(record, body, rowIndex);
			}
			return true;
		} else {
			return false;
		}
	},
	createtableModule : function(cm) {
		var buffer = [];
		for (var i = 0; i < cm.length; i++) {
			if (i == 0) {
				var body = '<br>'+ '<table  bgcolor="'+ this.borderColor+ '" border="0" cellPadding="0" cellSpacing="1" align="'+ this.tableAlign + '"> ' + '<tr  bgcolor="#EFF3F7"  >';
				buffer.push(body)
				if(this.isRowNumber){
					buffer.push('<td   bgcolor=' + this.headerbgcolor + '  width="50"  height="' + this.rowHeigh+ '" align="center"><br><b>序号</b></td>');	
				}
			}
			buffer.push('<td   bgcolor=' + this.headerbgcolor + '  width="'+ cm[i].width + '"  height="' + this.rowHeigh+ '" align="' + cm[i].align + '"><br><b>'+ cm[i]["header"] + '</b></td>');
		}
		buffer.push(' </tr> <tpl for="row"><tr bgcolor="#EFF3F7" >');
		if(this.isRowNumber){
			buffer.push('<td   bgcolor=' + this.headerbgcolor + '  width="50"  height="' + this.rowHeigh+ '" align="center"><br><b>{#}</b></td>');	
		}
		for (var i = 0; i < cm.length; i++) {						
			buffer.push(' <td  align="' + cm[i].align + '"  height="'+ this.rowHeigh + '"><br>{' + cm[i]["dataIndex"]+ '}</td>');
		}
		buffer.push('</tpl></table>');		
		return buffer.join("")
	}
});
Ext.reg('myrowexpander', myRowExpander);
Ext.grid.myRowExpander = myRowExpander;