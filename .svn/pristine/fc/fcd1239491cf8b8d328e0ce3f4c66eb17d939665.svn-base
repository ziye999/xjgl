Ext.define('Ext.app.KsStore', {
    extend     : "Ext.data.Store",
    model       : "Ext.app.Resource"
    	,autoLoad    : true,

        proxy       : {
            url     : 'subExamRoomArrange_getKcStus.do?lcId='+lcid+'&kcid='+kcid,
            type    : 'ajax',
            reader  : { type : 'json' }
        }
//    ,data       : [{id:'1', items:[{height:80, id:'empty', header:false, html:''},{height:80, id:'xsid2', header:false, html:'2'}]},
//    			{id:'2', items:[{height:80, id:'xsid3', header:false, html:'3'},{height:80, id:'xsid4', header:false, html:'4'}]},
//    			{id:'3', items:[{height:80, id:'xsid5', header:false, html:'5'},{height:80, id:'xsid6', header:false, html:'6'}]},
//    			{id:'4', items:[{height:80, id:'xsid7', header:false, html:'7'},{height:80, id:'xsid8', header:false, html:'8'}]}]
});