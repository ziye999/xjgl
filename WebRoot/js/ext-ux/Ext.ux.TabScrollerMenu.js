Ext.ns('Ext.ux');
Ext.ux.TabScrollerMenu =  Ext.extend(Object, {
	pageSize       : 5,
	maxText        : 15,    
	menuPrefixText : 'Items',
	constructor    : function(config) {
		config = config || {};
		Ext.apply(this, config);
	},
	init : function(tabPanel) {
		Ext.apply(tabPanel, this.parentOverrides);
		tabPanel.tabScrollerMenu = this;
		var thisRef = this;
		tabPanel.on({
			render : {
				scope  : tabPanel,
				single : true,
				fn     : function() { 
					var newFn = tabPanel.createScrollers.createSequence(thisRef.createPanelsMenu, this);
					tabPanel.createScrollers = newFn;
				}
			}
		});
	},
	createPanelsMenu : function() {
		var h = this.stripWrap.dom.offsetHeight;
		var rtScrBtn = this.header.dom.firstChild;
		Ext.fly(rtScrBtn).applyStyles({
			right : '18px'
		});
		
		var stripWrap = Ext.get(this.strip.dom.parentNode);
		stripWrap.applyStyles({
			 'margin-right' : '36px'
		});
		var scrollMenu = this.header.insertFirst({
			cls:'x-tab-tabmenu-right'
		});
		scrollMenu.setHeight(h);
		scrollMenu.addClassOnOver('x-tab-tabmenu-over');
		scrollMenu.on('click', this.showTabsMenu, this);	
		this.scrollLeft.show = this.scrollLeft.show.createSequence(function() {
			scrollMenu.show();												 						 
		});
		this.scrollLeft.hide = this.scrollLeft.hide.createSequence(function() {
			scrollMenu.hide();								
		});		
	},
   	getPageSize : function() {
		return this.pageSize;
	},
	setPageSize : function(pageSize) {
		this.pageSize = pageSize;
	},
    getMaxText : function() {
		return this.maxText;
	},
    setMaxText : function(t) {
		this.maxText = t;
	},
  	getMenuPrefixText : function() {
		return this.menuPrefixText;
	},
 	setMenuPrefixText : function(t) {
		this.menuPrefixText = t;
	},
	parentOverrides : {
		showTabsMenu : function(e) {		
			if  (this.tabsMenu) {
				this.tabsMenu.destroy();
                this.un('destroy', this.tabsMenu.destroy, this.tabsMenu);
                this.tabsMenu = null;
			}
            this.tabsMenu =  new Ext.menu.Menu();
            this.on('destroy', this.tabsMenu.destroy, this.tabsMenu);
            this.generateTabMenuItems();

            var target = Ext.get(e.getTarget());
			var xy     = target.getXY();
			xy[1] += 24;			
			this.tabsMenu.showAt(xy);
		},
		generateTabMenuItems : function() {
			var curActive  = this.getActiveTab();
			var totalItems = this.items.getCount();
			var pageSize   = this.tabScrollerMenu.getPageSize();
			if (totalItems > pageSize)  {
				var numSubMenus = Math.floor(totalItems / pageSize);
				var remainder   = totalItems % pageSize;
				for (var i = 0 ; i < numSubMenus; i++) {
					var curPage = (i + 1) * pageSize;
					var menuItems = [];
					for (var x = 0; x < pageSize; x++) {				
						index = x + curPage - pageSize;
						var item = this.items.get(index);
						menuItems.push(this.autoGenMenuItem(item));
					}
					this.tabsMenu.add({
						text : this.tabScrollerMenu.getMenuPrefixText() + ' '  + (curPage - pageSize + 1) + ' - ' + curPage,
						menu : menuItems,
						iconCls:"p-icons-bullet_blue"
					});					
				}
				if (remainder > 0) {
					var start = numSubMenus * pageSize;
					menuItems = [];
					for (var i = start ; i < totalItems; i ++ ) {					
						var item = this.items.get(i);
						menuItems.push(this.autoGenMenuItem(item));
					}					
					this.tabsMenu.add({
						text : this.tabScrollerMenu.menuPrefixText  + ' ' + (start + 1) + ' - ' + (start + menuItems.length),
						menu : menuItems
					});
				}
			}else {
				this.items.each(function(item) {
					if (item.id != curActive.id && !item.hidden) {
                        this.tabsMenu.add(this.autoGenMenuItem(item));
					}
				}, this);
			}
		},
		autoGenMenuItem : function(item) {
			var maxText = this.tabScrollerMenu.getMaxText();
			var text    = Ext.util.Format.ellipsis(item.title, maxText);
			return {
				text      : text,
				handler   : this.showTabFromMenu,
				scope     : this,
				disabled  : item.disabled,
				tabToShow : item,
				iconCls   : item.iconCls
			}		
		},
		showTabFromMenu : function(menuItem) {
			this.setActiveTab(menuItem.tabToShow);
		}	
	}	
});
Ext.reg('tabscrollermenu', Ext.ux.TabScrollerMenu);