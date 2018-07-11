/***
 * Consumed by the Employee Scheduler panel
 */
Ext.define('MyApp.store.EventStore', {
    extend      : "Sch.data.EventStore",
    proxy       : {
        type    : 'ajax',
        url     : 'examtimearrange_getDefault.do?lcId='+lcId+'&kd='+kd,
        reader  : { type : 'json' }
    }
});