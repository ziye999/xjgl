Ext.ns('App');
Ext.Loader.setConfig({ enabled : true, disableCaching : true });
Ext.Loader.setPath('Sch', '../../js/Sch');

Ext.require([
    'Sch.panel.SchedulerGrid',
    'Sch.plugin.Zones'
]);


Ext.onReady(function () {
    App.SchedulerDemo.init();
});

App.SchedulerDemo = {

    // Initialize application
    init : function () {
        Ext.define('Event', {
            extend : 'Sch.model.Event',
            fields : [
                {name : 'Title'},
                {name : 'Type'}
            ]
        });
		var button1 = new Ext.Button({
		    text:'toggle button ',
		    enableToggle:true,
		    handler: function(){
		    	
		    }
		});
		
		var subject = new Ext.form.ComboBox({  
			fieldLabel : '考试批次', 
            name : 'usersex_id',  
            id : 'usersex_id',  
            hiddenName : 'usersex_id',  
            typeAhead : true,  
            triggerAction : 'all',  
            lazyRender : true,  
            mode : 'local',  
            store : new Ext.data.ArrayStore({  
                        fields : ['value', 'text'],  
                        data : [["1", '男'], ["2", '女']]  
                    }),  
            valueField : 'value',  
            displayField : 'text',  
            emptyText : '请选择性别',  
            editable : false,  
            selectOnFocus : true,  
            width : 85  
        });
        //event编辑窗
        var eventEditor    = Ext.create('Sch.plugin.EventEditor', {
    		width		: 400,
    		hideOnBlur	: false,
        	fieldsPanelConfig : {
		        layout      : 'form',
		
		        style       : 'background : #fff',
		        border      : false,
		        cls         : 'editorpanel',
		        labelAlign  : 'top',
		
		        defaults    : {
		            width : 135
		        },
		
		        items       : [
		        	subject
		        ]
		    },
	        //本地化
	        l10n : {
	        	saveText    : '保存',
        		deleteText  : '删除科目',
        		cancelText  : '取消'

	        },
	        
	        timeConfig      : {
	            minValue    : '08:00',
	            maxValue    : '18:00'
	        },
	        
	        beforeeventsave : function( editor, eventRecord, eOpts) {
	        	alert(1);
	        },
	        
	        beforeeventdelete: function( editor, eventRecord, eOpts) {
	        	alert(1);
	        }
	    });
        
        var sched = Ext.create("Sch.panel.SchedulerGrid", {
            height                  : 300,
            width                   : 1000,
            rowHeight               : 60,
            eventBarTextField       : 'Title',
            viewPreset              : 'hourAndDay',
            startDate               : new Date(2011, 11, 9, 7),
            endDate                 : new Date(2011, 11, 9, 20),
            orientation             : 'horizontal',
            constrainDragToResource : false,
            eventBarIconClsField    : 'Type',
            snapToIncrement         : true,
            //constrainDragToResource : true,
            eventResizeHandles      : 'end',
            
            plugins         : [
	            eventEditor
	        ],
            
            

            viewConfig : {
                // Experimental for CSS3 enabled browsers only
                eventAnimations : true
            },

            eventBodyTemplate : new Ext.XTemplate(
                '<dl><dt>{[Ext.Date.format(values.StartDate, "G:i")]}</dt><dd>{Title}</dd>'
            ),

            eventRenderer : function (event, resource, data) {
                data.cls = resource.data.Name;
                return event.data;
            },

            lockedViewConfig : {
                stripeRows  : false,
                getRowClass : function (resource) {
                    return resource.data.Name;
                }
            },

            // Setup static columns
            columns          : [
                {header : 'Name', sortable : true, width : 100, dataIndex : 'Name'}
            ],

            timeAxisColumnCfg : {
                text : '时间'
            },

            // Store holding all the resources
            resourceStore     : Ext.create("Sch.data.ResourceStore", {
                model : 'Sch.model.Resource',
                data  : [
                    {Id : '1', Name : '2015-12-01'},
                    {Id : '2', Name : '2015-12-02'},
                    {Id : '3', Name : '2015-12-03'},
                    {Id : '4', Name : '2015-12-04'}
                ]
            }),

            // Store holding all the events
            eventStore        : Ext.create("Sch.data.EventStore", {
                model : 'Event',
                data  : [
                    {ResourceId : '1', Type : 'Call', Title : 'Assignment 1', StartDate : "2011-12-09 10:00", EndDate : "2011-12-09 11:00"},
                    {ResourceId : '2', Type : 'Call', Title : 'Customer call', StartDate : "2011-12-09 14:00", EndDate : "2011-12-09 16:00"},
                    {ResourceId : '3', Type : 'Meeting', Title : 'Assignment 2', StartDate : "2011-12-09 10:00", EndDate : "2011-12-09 12:00"}
                ]
            }),


            onEventCreated : function (newEventRecord) {
                // Overridden to provide some defaults before adding it to the store
                newEventRecord.set({
                    Title : "Hey, let's meet",
                    Type  : 'Meeting'
                });
            },


            listeners : {
                columnwidthchange : function (view, width) {
                    sched && sched.getDockedItems('toolbar')[ 0 ].child('slider').setValue(width)
                }
            }

        });

        new Ext.Button({
		    renderTo:Ext.getBody(),
		    text:'toggle button ',
		    enableToggle:true,
		    handler: function(){
		    	
		    }
		});
        
        sched.render(Ext.getBody());
    }
};
