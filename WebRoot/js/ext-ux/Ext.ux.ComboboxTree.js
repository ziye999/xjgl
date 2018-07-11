Ext.ux.ComboboxTree = Ext.extend(Ext.form.ComboBox, {   
	id:'',//选填  
	url:'',//必填   
	fieldLabel : '下拉树',//选填     
	name:'',      
	treeRootText:'所有',      
	rootVisible:true,//是否显示根节点     
	allowUnLeafClick:false,//false表示不允许非子叶点击选中。     /******以上为必填或者选填项*******/           
	store:new Ext.data.SimpleStore({fields:[],data:[[]]}),       
	editable:false,      
	autoScroll : false,     
	mode: 'local',       
	triggerAction:'all',      
	emptyText : '请选择...',     
	width:150,      
	tpl: "<div id='tree'></div>",      
	selectedClass:'',       
	onSelect:Ext.emptyFn,      
	initComponent : function() {     
		Ext.ux.ComboboxTree.superclass.initComponent.call(this);/*** the root of opTree; */          
		this.opRoot = new Ext.tree.AsyncTreeNode({           
			id:'0',    
			text:this.treeRootText   
		});/*** 与后台通讯的加载器 */   
		this.loader = new Ext.tree.TreeLoader({        
			url:this.url,    
			listeners:{               
				"beforeload":function(treeloader,node){      
					treeloader.baseParams={           
						id:node.id                  
					}; 
				}           
			}   
		});
		this.tree = new Ext.tree.TreePanel({        
			anchor : '95%',        
			frame : false,        
			width:this.width-5,        
			height:150,        
			animate : false,        
			rootVisible : this.rootVisible,        
			autoScroll : true,
			loader : this.loader, 
			root : this.opRoot
		});       
		this.comboHidden = new Ext.form.Hidden({     
			id:'comboHidden',        
			name:'hidden',        
			value:''    
		})     
	},            
	/*** ----------------------------------* 树的单击事件处理  * @param node,event * ----------------------------------*/        
	treeClk : function(node, e) { 
		//if (!node.isLeaf() && !this.allowUnLeafClick) {                
		//	e.stopEvent();// 非叶子节点则不触发                
		//	return;            
		//}                 
		if(node.id=='0'){       
			return;      
		}          
		this.setValue(node.text);// 设置option值           
		e.stopEvent();
		//this.comboHidden.setValue(node.id);//设置隐藏域值         
		//this.collapse();// 隐藏option列表        
	},  
	beforeExpandnode: function(node, deep, animal) {
	
	},  
	reset : function(){       
		this.setValue();// 重置           
		this.comboHidden.setValue();//重置隐藏域值
	},//自定义方法，去的隐藏域值     
	getHidden : function(){       
		return this.comboHidden.getValue();     
	},           
	listeners : {            
		'expand' : {              
			fn : function() {
				if (!this.tree.rendered && this.tpl) {                            
					this.tree.render("tree"); 
				}                     
				this.tree.show();                
			},                 
			single : true           
		},                
		'render' : {                 
			fn : function() {                        
				this.tree.on('click', this.treeClk, this);
				this.tree.on('beforeexpandnode', this.beforeExpandnode, this); 		
			}
		}      
	}         
});   
Ext.reg('uxcomboxtree', Ext.ux.ComboboxTree); 