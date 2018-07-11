/*
* This is the main UI container which instantiates each of the child UI components
* */
Ext.define("MyApp.view.Container", {
    extend      : 'Ext.Panel',
    alias       : 'widget.appcontainer',

    requires    : [
        'MyApp.store.RoomsStore',
        'MyApp.view.BuildingRomms'
    ],

    // Some panel configs
    layout      : 'border',
    //width       : 900,
    height      : 450,
    border      : false,

    // Custom configs for this panel, which will be passed on to the two child scheduler panels
    startDate   : null,
    endDate     : null,

    initComponent : function() {
        var roomsStore = new MyApp.store.RoomsStore();
        
        Ext.apply(this, {
            items   : [
                {
                    xtype           : 'buildingRooms',
                    id				: 'br',
                    region          : 'center'
                    
                }
            ]
        });

        this.callParent(arguments);

        // In a real life application, you'd probably batch these store loads to just use one Ajax request.
        roomsStore.load();
    }
});
    