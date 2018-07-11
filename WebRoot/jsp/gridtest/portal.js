/**
 * @class Ext.app.Portal
 * @extends Object
 * A sample portal layout application class.
 */
Ext.define('Ext.app.Portal', {
    extend: 'Ext.container.Viewport',
    requires: ['Ext.app.PortalPanel',
    			'Ext.app.KsStore'],
    initComponent: function(){
    	var ksstore = new Ext.app.KsStore();
    	this.arr= new Array();  
    	var thiz = this;
    	Ext.Ajax.request({
            url: 'subExamRoomArrange_getKcStus.do?lcId='+lcid+'&kcid='+kcid,
            method: 'GET',
            async: false, 
            success: function (response, options) {            	
            	var respText = Ext.decode(response.responseText);            	
            	thiz.arr = respText;		        
            },
            failure: function (response, options) {
                Ext.MessageBox.alert('提示', '请求超时或网络故障！');
            }
        });
        Ext.apply(this, {
            id: 'app-viewport',
            layout: {
                type: 'border',
                padding: '0 5 5 5' // pad the layout from the window edges
            },
            items: [{
                xtype: 'container',
                region: 'center',
                layout: 'border',
                items: [{
                    id: 'app-portal',
                    xtype: 'portalpanel',
                    region: 'center',
                    //使用数据源
                    items:thiz.arr
                }]
            }]
        });        
        this.callParent(arguments);
    },
    onPortletClose: function(portlet) {
        this.showMsg('"' + portlet.title + '" was removed');
    },
    showMsg: function(msg) {
    	var el = Ext.get('app-msg'),
    	msgId = Ext.id();
        this.msgId = msgId;
        el.update(msg).show();
        Ext.defer(this.clearMsg, 3000, this, [msgId]);
    },
    clearMsg: function(msgId) {
        if (msgId === this.msgId) {
            Ext.get('app-msg').hide();
        }
    }
});
