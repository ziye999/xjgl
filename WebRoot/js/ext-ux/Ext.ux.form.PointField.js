Ext.ns('Ext.ux.form');
Ext.ux.form.PointField = Ext.extend(Ext.form.TextField,{
	unitText:"",
	EnterQueryHandler:"",	
	checkModel:'single', 
	singleSelection:true,//是否单选。true为单选，默认为false。只有当type属性！=1时才能发挥作用。
	initComponent: function(){  
		Ext.ux.form.PointField.superclass.initComponent.call(this);
		var thiz = this;  
		var tree = new Ext.tree.TreePanel({  
			id:"treefield_"+this.id,
			checkModel: thiz.checkModel,  //对树的级联多选  
			onlyLeafCheckable: true,//对树所有结点都可选 
            region: 'center',
            width: 200,
            border : false,//表框 
            autoScroll: true,//自动滚动条
            animate : true,//动画效果 
            rootVisible: false,//根节点是否可见  
            split: true,  
            loader : new Ext.tree.TreeLoader({  
            	dataUrl : 'dropListAction_getPointTree.do?type=' + this.type
            }), 
            tbar:{
				items:[ 
			       "->",{xtype:"button",text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.win.hide()},scope:this}
			       ,"->",{xtype:"button",text:"选择",iconCls:"p-icons-submit",handler:this.selectsubimt,scope:this}
				  ]
			},  
            root : new Ext.tree.AsyncTreeNode({  
            }),  
            listeners: { 
            	dblclick:function(node){
            		thiz.typeValue = node.attributes.id;
        			thiz.typeValueName = node.attributes.text;
            		thiz.selectsubimt();
            	},
                click: function(node) {  
                    //得到node的text属性  
                	thiz.typeValue = node.attributes.id;
            		thiz.typeValueName = node.attributes.text;
                }
            }  
        });  
		tree.on('checkchange', function(node, checked) {
			node.expand();
			node.attributes.checked = checked;
			if(this.checkModel=='cascade'){
				node.eachChild(function(child) {
					child.ui.toggleCheck(checked);
					child.attributes.checked = checked;
					child.fireEvent('checkchange', child, checked);
				});
			}else if(this.checkModel=='single'){
				node.eachChild(function(child) {
					child.expand();
				});
			}
		}, tree);
		this.win = new Ext.ux.Window({
			id:"window_"+this.id,
		 	width:400,
		 	height:380,
		 	title:"组考考点",
		 	layout:'border',//布局方式
	 		items:[tree]
		});	
	},
	selectsubimt:function(){
		if(this.type=="1" && (this.typeValue==undefined || this.typeValue=="")){
			this.setValue("");
			Ext.MessageBox.alert("消息","请选择一条记录！");
			return;
		}else{
			this.setValue(this.typeValueName);
		}
		if(this.type!="1"){
			var selectedNode =  Ext.getCmp("treefield_"+this.id).getChecked(); 
			if(selectedNode == ""){
				Ext.MessageBox.alert("消息","请选择一条记录！");
				return;
			}
			this.schoolCodes = "";
			this.organCodes = "";
			this.schoolNames = "";
			this.organNames = "";
			this.allCodes = "";
			this.allNames = "";
			var parentIds = "";
			var isSingle = false;
			for(var i=0; i < selectedNode.length;i++) {
				parentIds += selectedNode[i].parentNode.id+",";
				if(selectedNode[i].index == "2") {
					this.schoolCodes += selectedNode[i].id+",";
					this.schoolNames += selectedNode[i].text.id+",";					
				}else if(selectedNode[i].index == "1") {
					this.organCodes += selectedNode[i].id+",";
					this.organNames += selectedNode[i].text.id+",";				
				}				
				this.allCodes += selectedNode[i].id+",";
				this.allNames += selectedNode[i].text+",";
			}
			var parentIdsAry = parentIds.split(",");
			var pNum = 0;
			for (var ii=0;ii<parentIdsAry.length;ii++) {
				if (parentIdsAry[ii]==parentIdsAry[0]) {
					pNum++;
				}
			}
			if (pNum<=1) {
				isSingle = true;
			}
			if(this.singleSelection){
				if(!isSingle){//selectedNode.length!=1
					Ext.MessageBox.alert("消息","只能选择一条记录！");
					return;
				}
				this.schoolCodes = this.schoolCodes.substring(0,this.schoolCodes.length-1);
				this.organCodes = this.organCodes.split(",")[0];
				this.schoolNames = this.schoolNames.substring(0,this.schoolNames.length-1);
				this.organNames = this.organNames.split(",")[0];
				this.allCodes = this.allCodes.split(",")[0];
				this.allNames = this.allNames.split(",")[0];
				this.setValue(this.allNames);
			}else {
				this.schoolCodes = this.schoolCodes.substring(0,this.schoolCodes.length-1);
				this.organCodes = this.organCodes.substring(0,this.organCodes.length-1);
				this.schoolNames = this.schoolNames.substring(0,this.schoolNames.length-1);
				this.organNames = this.organNames.substring(0,this.organNames.length-1);
				this.allCodes = this.allCodes.substring(0,this.allCodes.length-1);
				this.allNames = this.allNames.substring(0,this.allNames.length-1);
				this.setValue(this.allNames);
			}			
			
		}
		if(this.callback != null) {
			this.callback();
		}
		this.win.hide();
	},
	reset:function() {
		this.setValue("");
		this.schoolCodes = "";
		this.organCodes = "";
		this.schoolNames = "";
		this.organNames = "";
		this.allCodes = "";
		this.allNames = "";
		this.typeValue = "";
		var nodes = Ext.getCmp("treefield_"+this.id).getChecked();
		if(nodes && nodes.length){ 
			for(var i=0;i<nodes.length;i++){ 
				//设置UI状态为未选中状态 
				nodes[i].getUI().toggleCheck(false); 
				//设置节点属性为未选中状态 
				nodes[i].attributes.checked=false; 
			}
		}
	},
	getCodes:function() {
		return this.allCodes;
	},
	getValues:function() {
		return this.allNames;
	},
	getSchoolCodes:function() {
		return this.schoolCodes;
	},
	getSchoolNames:function() {
		return this.schoolNames;
	},
	getOrganCodes:function() {
		return this.organCodes;
	},
	getOrganNames:function() {
		return this.organNames;
	},
	getSchoolCode:function() {
		return this.typeValue;
	},	
	onRender : function(ct, position) {   
		Ext.ux.form.PointField.superclass.onRender.call(this, ct, position);	        
		this.createUnit(ct, position);
		this.registerEnterQueryEvent();
	},
	createUnit:function(ct, position){
		var thiz = this;
		if(this.width == undefined) {
			this.width = 180;
		}
		var curWwwPath=window.document.location.href;
		//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		var pathName=window.document.location.pathname;
		var pos=curWwwPath.indexOf(pathName);
		//获取主机地址，如： http://localhost:8083
		var localhostPaht=curWwwPath.substring(0,pos);
		//获取带"/"的项目名，如：/uimcardprj
		var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
		if (navigator.userAgent.indexOf("Chrome")>-1) {
			this.unitEl = ct.createChild({
				tag : 'div',            
	            style: 'cursor:hand;position:absolute;margin-top:-20px;margin-left:'+(this.width-30)+'px;height:18px;width:18px;background-image:url('+localhostPaht+projectName+'/img/icons/query.png);background-repeat:no-repeat;background-size:18px 18px;'
			});
		}else if (navigator.appName.indexOf("Microsoft Internet Explorer")!=-1) {
			this.unitEl = ct.createChild({
				tag : 'div',            
	            style: 'cursor:hand;position:absolute;margin-top:0px;margin-left:-18px;height:18px;width:18px;background-image:url('+localhostPaht+projectName+'/img/icons/query.png);background-repeat:no-repeat;background-size:18px 18px;'
			});
		}else {
			this.unitEl = ct.createChild({
				tag : 'div',            
	            style: 'cursor:hand;position:absolute;margin-top:-20px;margin-left:'+(this.width-30)+'px;height:18px;width:18px;background-image:url('+localhostPaht+projectName+'/img/icons/query.png);background-repeat:no-repeat;background-size:18px 18px;'
			});
		}
		
		this.unitEl.on('click', function(e,t) { 
			thiz.win.show();
		}); 

	    this.width = this.width - (this.unitText.replace(/[^\x00-\xff]/g, "xx").length * 6+ 10);   
		this.alignErrorIcon = function() {   
			this.errorIcon.alignTo(this.unitEl, 'tl-tr', [2, 0]);   
		};  
	},
	registerEnterQueryEvent:function(){		
		this.on('specialkey',function(field,e){if (e.getKey() == e.ENTER) {
			Ext.invoke(this.scope,this.EnterQueryHandler,null)
		}},this);
	}
});
Ext.reg("uxpointfield",Ext.ux.form.PointField);