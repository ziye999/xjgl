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
		          {header : "姓名",align : "center",sortable : true,dataIndex : "XM"},
		          {header : "身份证号",align : "center",sortable : true,dataIndex : "SFZH"}, 
		          {header : "出生日期",align : "center",sortable : true,dataIndex : "CSRQ"},
		          //{header : "等级",align : "center",sortable : true,dataIndex : "NJ"},
		          //{header : "科目",align : "center",sortable : true,dataIndex : "BJ"},
		          {header : "申请日期",align : "center",sortable : true,dataIndex : "SQRQ"},
		          {header : "不通过原因",align : "center",sortable : true,dataIndex : "YYYY"},
		          {header : "审核状态",align : "center",sortable : true,dataIndex : "ZT"},
		          {header : "资料",align : "center",sortable : true,dataIndex : "FILEPATH",renderer : renderdelFILEPATH,listeners:{
		        	  "click":function(){     
		        		  var selectedBuildings = this.grid.getSelectionModel().getSelections();
		        		  var FILEPATH =selectedBuildings[0].get("FILEPATH");
		        		  if(FILEPATH!=null && FILEPATH!=''){
		        			  var path = Ext.get("ServerPath").dom.value + "/uploadFile/"+FILEPATH;
		        			  var XMLHTTP = new XMLHttpRequest();		        			  
		        			  XMLHTTP.open("GET",path,false);
		        			  XMLHTTP.send();
		        			  if(XMLHTTP.readyState==4){
	        					  if(XMLHTTP.status=="404"){
	        						  Ext.Msg.alert('消息','文件不存在！');		        						  
	        					  }else{
	        						  window.open(path); 
	        					  }
	        				  }		        			  	        			 
		        		  }		        		  
		        	  },
		        	  scope:this
		          }}
				];
				
  		function renderdel(value, cellmeta, record, rowIndex, columnIndex, store){ 
            var str;
			if(value == '0'){
				str = '未审核';
			}
			if(value == '1'){
				str = '已审核';
			}
            return str;  
        }
  		function renderdelFILEPATH(value, cellmeta, record, rowIndex, columnIndex, store){ 
            var str;
			if(value!=null && value!=''){
				str = '下载资料';
			}else{
				str="无";
			} 
            return str;  
        }
       
		this.grid = new Ext.ux.GridPanel({
					id:"grid",
					cm : cm,
					sm : sm,
					title : "日常管理-不通过申请审核",
					tbar : ["->", {xtype : "button",text : "审核",iconCls : "p-icons-update",handler : this.audit,scope : this}],
					page : true,
					rowNumber : true,
					region : "center",
					excel : true,
					action : "breakstudy_getListPage.do",
					excelTitle : "不通过审核信息表",
					fields : ["YYID","XM","XJH","SFZH","ZT","CSRQ","NJ","BJ","SQRQ","YYYY","SHZT","FILEPATH"],
					border : false
		});
				
		// 搜索区域		
		var school = new Ext.ux.form.OrganField({name:"sup_organ_sel",width:190,readOnly:true});		
		var storeday = new Ext.data.SimpleStore({  
			fields : ['value', 'text'],  
			data : [['0', '未审核'],['1', '已审核']]  
		});
		
		var flag = new Ext.form.ComboBox({
			store : storeday,
			name:"flag",
            id:"flag",
			displayField : 'text',  
    		valueField : 'value', 
			width:110,
			mode : 'local',
			triggerAction : 'all',
			editable:false
		});
		
		var xm_xjh_sfz = new Ext.form.TextField({fieldLabel:"",id:"xm_xjh_sfz",name:"xm_xjh_sfz",maxLength:50,width:190});
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
						         {html : "组织单位：",baseCls : "label_right",width : 70}, 
						         {items : [school],baseCls : "component",width : 190},
						         {html : "姓名/身份证：",baseCls : "label_right",width : 120}, 
						         {items : [xm_xjh_sfz],baseCls : "component",width : 200}, 
						         {html : "审核状态：",baseCls : "label_right",width : 70}, 
						         {items : [flag],baseCls : "component",width : 120}, 
						         {layout : "absolute",items : [cx, cz],baseCls : "component_btn",width : 150}												
								]
					}]
				}]
		})
	},
	/** 初始化界面 * */
	initFace : function() {
		this.addPanel({layout : "border",items : [this.search, this.grid]});
	},
	initQueryDate : function() {
		this.grid.$load();
	},	
	audit : function(){
		var selectedBuildings = this.grid.getSelectionModel().getSelections();
		if(selectedBuildings.length <= 0){
			Ext.MessageBox.alert("消息","请选择考生审核！");
			return;
		}
		for (var i = 0; i < selectedBuildings.length; i++) {
			if(selectedBuildings[i].get("SHZT") == 1){
				Ext.MessageBox.alert("消息","选项中有已审核的考生！");
				return;
			}
		}
		var yyid="";
		for (var i = 0; i < selectedBuildings.length; i++) {
			yyid += selectedBuildings[i].get("YYID")+",";
		}
		Ext.MessageBox.show({
			title:"消息",
			msg:"您确定要审核吗?",
			buttons:Ext.MessageBox.OKCANCEL,
			icon:Ext.MessageBox.QUESTION,
			
			fn:function(b){
				if(b=='ok'){
					Ext.Ajax.request({    	   
						 url: 'breakstudy_audit.do',
						 params: {    
							 'yyids':yyid
						 },    
						 success: function(response, options) {     
							 var responseArray = Ext.util.JSON.decode(response.responseText);  
							 Ext.Msg.alert('消息',responseArray.msg);        
			  				 var grid = Ext.getCmp("grid")
			  				 grid.getStore().reload();
						}    
					})  					
				}
			}
		});		
	},
	selectRound :function(){
		var schools = this.search.getForm().findField('sup_organ_sel').getCodes();
		var flag=Ext.getCmp('flag').getValue();
		var xm_xjh_sfz=Ext.getCmp('xm_xjh_sfz').getValue();
    	this.grid.$load({"schools":schools,"flag":flag,"xm_xjh_sfz":xm_xjh_sfz});
	}	
});

