Ext.extend(system.application.baseClass, {
	/** 初始化 * */
	init : function() {
		this.initDate();
		this.initComponent();
		this.initListener();
		this.initFace();
		this.initQueryDate();
	},
	/** 初始化页面、内存等基本数据 * */
	initDate : function() {
	
	},
	/** 对组件设置监听 * */
	initListener : function() {
		
	},
	initComponent : function() {
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : false});
		var cm = [sm,
					{header : "年度",align : "center",sortable : true,dataIndex : "XN"},
					{header : "季度",align : "center",sortable : true,dataIndex : "XQ"},
					{header : "考试名称",align : "center",sortable : true,dataIndex : "KSMC",renderer : renderdel}, 
					{header : "组织单位",align : "center",sortable : true,dataIndex : "ZKDW"}, 
					{header : "考生数量",align : "center",sortable : true,dataIndex : "KSSL"}
				];
				
  		function renderdel(value, cellmeta, record, rowIndex, columnIndex, store){ 
  			var lcid = store.getAt(rowIndex).get('LCID'); 
            var str = "<a href='"+Ext.get("ServerPath").dom.value+"/jsp/main.jsp?module=00090301&lcid="+lcid+"'>"+value+"</a>";			
            return str;  
        } 
       
		this.grid = new Ext.ux.GridPanel({
					id:"grid",
					cm : cm,
					sm : sm,
					title : "成绩统计补录-小题成绩补录",
					tbar : [
					        "->", {xtype : "button",text : "成绩导入",iconCls : "p-icons-upload",handler : this.exportResults,scope : this}
							,"->",{xtype:"button",text:"下载模板",iconCls:"p-icons-download",handler:this.downloadTemplate,scope:this}
						   ],
					page : true,
					rowNumber : true,
					region : "center",
					excel : true,
					action : "resultscollection_getListPage.do",
					excelTitle : "轮次展示表",
					fields : ["LCID", "XN", "XQ", "KSMC", "ZKDW", "KSSL"],
					border : false
				});
				
		// 搜索区域		
		var xnxq	= new Ext.ux.form.XnxqField({name:"retcxnxq", id:"retcxnxq_id",width:200,readOnly:true});
		var cx = new Ext.Button({x : 17,y : -10,cls : "base_btn",text : "查询",handler : this.selectRound,scope : this});
		var cz = new Ext.Button({x : 87,y : -10,cls : "base_btn",text : "重置",handler : function() {this.search.getForm().reset();},scope : this});

		this.search = new Ext.form.FormPanel({
			region : "north",
			height : 90,
			items : [{
				layout : 'form',
				xtype : 'fieldset',
				style : 'margin:10 10',
				title : '查询条件',
				items : [{
					xtype : "panel",
					layout : "table",
					layoutConfig : {
						columns : 7
					},
					baseCls : "table",
					items : [
					         {html : "年度：",baseCls : "label_right",width : 120}, 
					         {items : [xnxq],baseCls : "component",width : 200}, 
					         {layout : "absolute",items : [cx, cz],baseCls : "component_btn",width : 160}
					        ]
					}]
				}]
		})

		this.submitForm = new Ext.ux.FormPanel({
				fileUpload : true,
				frame : true,
				enctype : 'multipart/form-data',
				defaults : {
					xtype : "textfield",
					anchor : "95%"
				},
				items:[{fieldLabel:"文件",xtype:"textfield",name:"upload",inputType:'file'}]
		});
	},
	/** 初始化界面 * */
	initFace : function() {
		this.addPanel({layout : "border",items : [this.search, this.grid]});
	},
	initQueryDate : function() {
		this.grid.$load();
	},
	exportResults : function() {
		var selected = this.grid.getSelectionModel().getSelected();
		var selected = this.grid.getSelectionModel().getSelections();
		if (selected.length != 1) {
			Ext.MessageBox.alert("消息", "请选择一条记录进行导入！");
			return;
		}
	
		var _b_save = new Ext.Button({text : "上传",iconCls : "p-icons-upload",handler : this.exportFilesInfo,scope : this});
		var _b_cancel = new Ext.Button({text : "取消",iconCls : "p-icons-checkclose",handler : function() {this.fileUpWin.hide();},scope : this});
		this.fileUpWin = new Ext.ux.Window({
					title : "成绩导入",
					width : 400,
					tbar : {
						cls : "ext_tabr",
						items : ["->", _b_cancel, "->", _b_save]
					},
					listeners : {
						hide : function() {
							this.submitForm.form.reset();
						},
						scope : this
					},
					items : [this.submitForm]
				});
		this.fileUpWin.show(null, function() {}, this);
	},
	exportFilesInfo : function() {
		var filePath = this.submitForm.getForm().findField("upload").getRawValue();
		// 判断文件类型
		var objtype = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
		var fileType = new Array(".xls", ".xlsx");
		if (filePath == "") {
			alert("请选择文件！");
			return false;
		} else {
			if (!/\.(xls|xlsx)$/.test(objtype)) {
				alert("文件类型必须是.xlsx,.xls中的一种!")
				return false;
			}
		}
		var selected = this.grid.getSelectionModel().getSelections();
		var lcid = selected[0].get("LCID");
		this.submitForm.$submit({
				action : "resultscollection_importFile.do",
				params : {
					'upload' : this.submitForm.getForm().findField("upload"),
					'lcid' : lcid
				},
				handler:function(form, result, thiz) {
					thiz.fileUpWin.hide();
					thiz.grid.$load();
				},
				scope : this
		});
	},
	selectRound :function(){
		var xnxq_id=Ext.getCmp('retcxnxq_id').getCode();
    	this.grid.$load({"xnxq_id":xnxq_id});
	},
	downloadTemplate:function(){
		var path = Ext.get("ServerPath").dom.value + "/export/excel/temp/resultscollection_temp.xls";
    	window.open(path);		
	}	
});

