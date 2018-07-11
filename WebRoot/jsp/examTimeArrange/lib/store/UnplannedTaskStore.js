/***
 * Consumed by the Unplanned Task Grid
 */
Ext.define("MyApp.store.UnplannedTaskStore", {
    extend      : "Ext.data.Store",
    model       : 'MyApp.model.UnplannedTask',
    requires    : [
        'MyApp.model.UnplannedTask'
    ],
    autoLoad    : true,
    proxy       : {
        url     : 'examtimearrange_getSubject.do?lcId='+lcId+'&kd='+kd,
        type    : 'ajax',
        reader  : { type : 'json' }
    }
});