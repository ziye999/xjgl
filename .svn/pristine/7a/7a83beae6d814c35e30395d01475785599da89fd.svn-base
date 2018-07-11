Ext.ns('Ext.ux');
Ext.ux.MaximizeTool = function() {
	this.init = function(ct) {
		var maximizeTool = {id : 'maximize',handler : handleMaximize,scope : ct,qtip : '放大'};
		var minTool = {id : 'close',handler : handleClose,scope : ct,qtip : '关闭'};
		ct.tools=[maximizeTool,minTool];	
		
	};
	function handleMaximize(event, toolEl, panel) {
		panel.originalOwnerCt = panel.ownerCt;
		panel.originalPosition = panel.ownerCt.items.indexOf(panel);
		panel.originalSize = panel.getSize();
		if(!panel.fristCompany){
			panel.fristCompany = panel.getComponent(0);
		}
		if (!toolEl.window) {
			var defaultConfig = {
				width : (Ext.getBody().getSize().width - 350),
				height : (Ext.getBody().getSize().height - 200),
				resizable : true,
				draggable : true,
				closable : true,
				closeAction : 'hide',
				hideBorders : true,
				plain : true,
				layout : 'fit',
				modal:true,
				autoScroll : false,
				border : false,
				frame : true,
				pinned : true
			};
			toolEl.window = new Ext.Window(defaultConfig);
			toolEl.window.on('hide', handleMinimize, panel);
		}
		if (!panel.dummyComponent) {
			var dummyCompConfig = {
				title : panel.title,
				width : panel.getSize().width,
				height : panel.getSize().height,
				html : '&nbsp;'
			};
			panel.dummyComponent = new Ext.Panel(dummyCompConfig);
		}
		toolEl.window.setTitle(panel.title,panel.iconCls);
		toolEl.window.add(panel.fristCompany);
		
		if (panel.tools['close'])
			panel.tools['close'].setVisible(false);
		panel.tools['maximize'].setVisible(false);
		panel.setVisible(false);
		panel.originalOwnerCt.insert(panel.originalPosition,panel.dummyComponent);
		panel.originalOwnerCt.doLayout();
		panel.dummyComponent.setSize(panel.originalSize);
		panel.dummyComponent.setVisible(true);
		toolEl.window.show(toolEl.el);
	};
	function handleMinimize(window) {
		this.setVisible(true);
		this.add(this.fristCompany);
		this.dummyComponent.setVisible(false);
		this.originalOwnerCt.insert(this.originalPosition, this);
		this.originalOwnerCt.doLayout();
		this.setSize(this.originalSize);
		this.tools['maximize'].setVisible(true);
		if (this.tools['toggle'])
			this.tools['toggle'].setVisible(true);
		if (this.tools['close'])
			this.tools['close'].setVisible(true);
	}	
	function handleClose(event, toolEl, panel){
		panel.display=0,
		panel.setVisible(false);
	}
};