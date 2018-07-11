/**
 * Basic grid panel, which is associated with a DragZone. See the GridPanel class in the Sencha API docs for configuration options.
 */
Ext.define("MyApp.view.UnplannedTaskGrid", {
    extend      : "Ext.grid.GridPanel",
    alias       : 'widget.unplannedtaskgrid',
    requires    : [
        'MyApp.store.UnplannedTaskStore',
        'MyApp.view.UnplannedTaskDragZone'
    ],
    cls         : 'taskgrid',	
    initComponent : function() {
        Ext.apply(this, {
            viewConfig  : { columnLines : false },
            store   : new MyApp.store.UnplannedTaskStore(),
            columns : [
                {header : '未安排考试批次', sortable:true, flex : 1, dataIndex : 'Name'},
                {header : '考试时长', sortable:true, width:70, dataIndex : 'Duration'},
                {header : 'Id', sortable:true, width:70, dataIndex : 'Id',hidden:true}
            ]
        });
        this.callParent(arguments);
    },
    afterRender : function() {
        this.callParent(arguments);
        // Setup the drag zone
        new MyApp.view.UnplannedTaskDragZone(this.getEl(),{
            grid : this
        });
    }
});    