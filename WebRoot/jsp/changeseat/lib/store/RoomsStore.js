/***
 * Consumed by the Employee Scheduler panel
 */
Ext.define('MyApp.store.RoomsStore', {
    extend      : "Ext.data.Store",
    
    data: [{
				name : '楼房1',
				num: 4,
				rooms: [{id: '0001',name: '考场1', rs: '30人', code: '10001-10002'},
						{id: '0002',name: '考场2', rs: '30人', code: '10001-10002'},
						{id: '0003',name: '考场3', rs: '30人', code: '10001-10002'},
						{id: '0004',name: '考场4', rs: '30人', code: '10001-10002'},
						{id: '0005',name: '考场5', rs: '30人', code: '10001-10002'},
						{id: '0006',name: '考场6', rs: '30人', code: '10001-10002'}]
			},
			{
				name : '楼房2',
				num: 3,
				rooms: [{id: '0007',name: '考场1', rs: '30人', code: '10001-10002'},
						{id: '0008',name: '考场2', rs: '30人', code: '10001-10002'},
						{id: '0009',name: '考场3', rs: '30人', code: '10001-10002'},
						{id: '0010',name: '考场4', rs: '30人', code: '10001-10002'},
						{id: '0011',name: '考场5', rs: '30人', code: '10001-10002'},
						{id: '0012',name: '考场6', rs: '30人', code: '10001-10002'}]
			}
		]
//    proxy       : {
//        type    : 'ajax',
//        url     : 'examtimearrange_getDefault.do?lcId='+lcId+'&nj='+nj,
//        reader  : { type : 'json' }
//    }
});