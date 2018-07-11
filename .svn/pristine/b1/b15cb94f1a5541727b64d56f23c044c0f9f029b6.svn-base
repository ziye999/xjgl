Ext.ux.TabCloseMenu = Ext.extend(Object, {
    closeTabText: '关闭当前页签',
    closeOtherTabsText: '关闭其他页签',
    closeAllTabsText: '关闭所有页签',
    iconCls:'p-icons-bullet_blue',
    showCloseAll: true,
    constructor : function(config){
        Ext.apply(this, config || {});
    },
    init : function(tabs){
        this.tabs = tabs;
        tabs.on({
            scope: this,
            contextmenu: this.onContextMenu,
            destroy: this.destroy
        });
    },
    destroy : function(){
        Ext.destroy(this.menu);
        delete this.menu;
        delete this.tabs;
        delete this.active;    
    },
    onContextMenu : function(tabs, item, e){
        this.active = item;
        var m = this.createMenu(),
            disableAll = true,
            disableOthers = true,
            closeAll = m.getComponent('closeall');
        m.getComponent('close').setDisabled(!item.closable);
        tabs.items.each(function(){
            if(this.closable){
                disableAll = false;
                if(this != item){
                    disableOthers = false;
                    return false;
                }
            }
        });
        m.getComponent('closeothers').setDisabled(disableOthers);
        if(closeAll){
            closeAll.setDisabled(disableAll);
        }        
        e.stopEvent();
        m.showAt(e.getPoint());
    },    
    createMenu : function(){
        if(!this.menu){
            var items = [{
                itemId: 'close',
                text: this.closeTabText,
                scope: this,
                iconCls:this.iconCls,
                handler: this.onClose
            }];
            if(this.showCloseAll){
                items.push('-');
            }
            items.push({
                itemId: 'closeothers',
                text: this.closeOtherTabsText,
                scope: this,
                iconCls:this.iconCls,  
                handler: this.onCloseOthers
            });
            if(this.showCloseAll){
                items.push({
                    itemId: 'closeall',
                    text: this.closeAllTabsText,
                    scope: this,
                    iconCls:this.iconCls,
                    handler: this.onCloseAll
                });
            }
            this.menu = new Ext.menu.Menu({
                items: items
            });
        }
        return this.menu;
    },
    onClose : function(){
    	this.tabs.remove(this.active);        
    },
    onCloseOthers : function(){
    	this.doClose(true);        
    },
    onCloseAll : function(){
    	this.doClose(false);        
    },
    doClose : function(excludeActive){
    	var items = [];
        this.tabs.items.each(function(item){
            if(item.closable){
                if(!excludeActive || item != this.active){
                    items.push(item);
                }    
            }
        }, this);
        Ext.each(items, function(item){        	
        	this.tabs.remove(item);            
        }, this);
    }
});
Ext.preg('tabclosemenu', Ext.ux.TabCloseMenu);