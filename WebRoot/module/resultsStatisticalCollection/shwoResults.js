var grid2;
 var panel;
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
		var cm = [
			{header : "年度",align : "center",dataIndex : "XN"},
			{header : "季度",align : "center",dataIndex : "XQ"},
			{header : "考试名称",align : "center",dataIndex : "KSMC"}, 
			{header : "题型",align : "center",dataIndex : "TX"}, 
			{header : "满分",align : "center",dataIndex : "MF",renderer :renderdeMF},
			{header : "平均分",align : "center",dataIndex : "PJF",renderer :renderdePJF},
			{header : "最高分",align : "center",dataIndex : "ZGF",renderer :renderdeZGF},
			{header : "最低分",align : "center",dataIndex : "ZDF",renderer :renderdeZDF}
		];
		
		function renderdeMF(value, cellmeta, record, rowIndex, columnIndex, store){ 
			var str = "<input type='text' id='mf"+rowIndex+"' value='"+value+"' size = '5'/>"
			return str;  
        } 
		function renderdePJF(value, cellmeta, record, rowIndex, columnIndex, store){ 
			var str = "<input type='text' id='pjf"+rowIndex+"' value='"+value+"' size = '5'/>"
			return str;  
        } 
		function renderdeZGF(value, cellmeta, record, rowIndex, columnIndex, store){ 
			var str = "<input type='text' id='zgf"+rowIndex+"' value='"+value+"' size = '5'/>"
			return str;  
        } 
		function renderdeZDF(value, cellmeta, record, rowIndex, columnIndex, store){ 
			var str = "<input type='text' id='zdf"+rowIndex+"' value='"+value+"' size = '5'/>"
			return str;  
        } 
		this.grid = new Ext.ux.GridPanel({
					id : 'grid',
					cm : cm,
					title : "成绩统计补录-小题成绩补录",
					tbar : ["->", {
								xtype : "button",
								text : "取消",
								iconCls : "p-icons-checkclose",
								handler : this.closeResults,
								scope : this
							},"->", {
								
								xtype : "button",
								text : "保存",
								iconCls : "p-icons-save",
								handler : this.updateResults,
								scope : this
							}],
					page : true,
					rowNumber : true,
					region : "center",
					excel : true,
					action : "resultscollection_resultsListPage.do?lcid="+getLocationPram("lcid"),
					excelTitle : "小题补录表",
					fields : ["XTCJID", "XN", "XQ", "KSMC", "TX", "MF", "PJF", "ZGF", "ZDF"],
					border : false
		});

		// 搜索区域			
		this.submitForm = new Ext.ux.FormPanel({
				fileUpload : true,
				frame : true,
				enctype : 'multipart/form-data',
				defaults : {
					xtype : "textfield",
					anchor : "95%"
				},
				items:[
				       {fieldLabel:"文件",xtype:"textfield",name:"upload",inputType:'file'},
				       {id:"lcid",name:"lcid",inputType:'Hidden'}
					]
				});
	},
	/** 初始化界面 * */
	initFace : function() {
		this.addPanel({layout : "border",items : [this.grid]});
	},
	initQueryDate : function() {
		this.grid.$load();
	},
	updateResults : function() {
		var store = this.grid.getStore();
		var count = store.getCount(); //获取Store对象
		var sj = "";
		var param = "[{";
		for (var i = 0; i < count; i++) {
			var flag = 0;
			var mfi = document.getElementById("mf"+i).value;
			if(!pd(mfi,"满分",i+1)) return;
			var mf = store.getAt(i).get("MF");
			if(mfi != mf){
				sj += "\"mf\":\""+mfi+"\",";
				flag++;
			}
			var pjf = document.getElementById("pjf"+i).value;
			if(!pd(pjf,"平均分",i+1)) return;
			var pjfi = store.getAt(i).get("PJF");
			if(pjf != pjfi){
				sj += "\"pjf\":\""+pjf+"\",";
				flag++
			}
			var zgf = document.getElementById("zgf"+i).value;
			if(!pd(zgf,"最高分",i+1)) return;
			var zgfi = store.getAt(i).get("ZGF");
			if(zgf != zgfi){
				sj += "\"zgf\":\""+zgf+"\",";
				flag++
			}
			var zdf = document.getElementById("zdf"+i).value;
			if(!pd(zdf,"最低分",i+1)) return;
			var zdfi = store.getAt(i).get("ZDF");
			if(zdf != zdfi){
				sj += "\"zdf\":\""+zdf+"\",";
				flag++
			}
			if(flag != 0){
				var xtcid = store.getAt(i).get("XTCJID");
				sj += "\"xtcid\":\""+xtcid+"\"";
				param += sj+"},{";
				sj = "";
			}
		}
		param = param.substring(0,param.length-2)+"]";
		if(param == ']'){
			return;
		}
		Ext.Ajax.request({             
    		url :'resultscollection_updateResults.do',
    		success: function(response,options){
    				 var grid = Ext.getCmp("grid")
    				 grid.getStore().reload();
    				Ext.MessageBox.alert("消息","修改完成");
    		},
    		params : {'valueStr':param},             
    		waitMsg : '数据处理中...'      
    	});		
	},
	closeResults : function (){
		window.location.href=Ext.get("ServerPath").dom.value+'/jsp/main.jsp?module=000903';
	}
});

function pd(num,str,index){
	if(!isNaN(num)){
		if(num>10000){
    				Ext.MessageBox.alert("消息","第"+index+"行 '"+str+"' 输入值过大！");
			return false;
		}
        var dot = num.indexOf(".");
        if(dot != -1){
            var dotCnt = num.substring(dot+1,num.length);
            if(dotCnt.length > 2){
                Ext.MessageBox.alert("消息","第"+index+"行 '"+str+"' 小数位已超过2位！");
                return false;
            }
        }
    }else{
       Ext.MessageBox.alert("消息","第"+index+"行 '"+str+"' 输入不合法！");
        return false;
    }
    return true;
}
