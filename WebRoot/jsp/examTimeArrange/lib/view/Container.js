/*
* This is the main UI container which instantiates each of the child UI components
* */
Ext.define("MyApp.view.Container", {
    extend      : 'Ext.Panel',
    alias       : 'widget.appcontainer',
    requires    : [
        'MyApp.store.AvailabilityStore',
        'MyApp.store.ResourceStore',
        'MyApp.store.EventStore',
        'MyApp.store.UnplannedTaskStore',
        'MyApp.view.EmployeeScheduler',
        'MyApp.view.AvailabilityGrid',
        'MyApp.view.UnplannedTaskGrid'
    ],
    // Some panel configs
    layout      : 'border',
    //width       : 900,
    height      : 380,
    border      : false,
    // Custom configs for this panel, which will be passed on to the two child scheduler panels
    startDate   : null,
    endDate     : null,
    initComponent : function() {
        var eventStore = new MyApp.store.EventStore();        
        console.info(eventStore)
        var resourceStore = new MyApp.store.ResourceStore();
        var availabilityStore = new MyApp.store.AvailabilityStore();
        resourceStore.availabilityStore = availabilityStore;        
        Ext.apply(this, {
        	tbar:[ 
			  "->",{xtype:"button",text:"保存",iconCls:"p-icons-add",handler:function () {
				  var ecStore = Ext.getCmp('ec').eventStore.data.items;
				  var delStore = Ext.getCmp('uptg').store;
				  var dels = '';
				  var flag = true;
				  delStore.each(function(r){
					  if(flag){
						  dels = r.get("Id"); 
						  flag = false;
					  }else{
						  dels = dels +',' + r.get("Id"); 
					  } 
				  }); 
				  var num = ecStore.length;
				  var array=new Array(); 
				  if(num>0){
					  for(var i=0;i<num;i++){
						  var obj=new Object();
						  //科目id										
						  var Id = ecStore[i].get('Id');
						  obj.id=Id;
						  //考试日期
						  var ResourceId = ecStore[i].get('ResourceId');
						  obj.resourceId=ResourceId;
						  //开始时间
						  var StartDate = ecStore[i].get('StartDate');
						  obj.startDate=ResourceId+" "+getTime(StartDate);
						  //结束时间
						  var EndDate = ecStore[i].get('EndDate');
						  obj.endDate=ResourceId+" "+getTime(EndDate);										
						  array[i]=obj;
					  }
				  }				    
				  Ext.Ajax.request({
					  url: 'examtimearrange_saveExamTimeArrange.do',
					  params: { lcId: lcId, kd:kd,objList:JSON.stringify(array),dels:dels},
					  method: 'post',					  
					  success: function (response, options) {
						  Ext.MessageBox.alert('提示', response.responseText);						  
					  },
					  failure: function (response, options) {
						  Ext.MessageBox.alert('提示', '请求超时或网络故障！');						  
					  }
				  });
			  },scope:this}			  
			],
			items   : [{
             	xtype           : 'employeescheduler',
                id				: 'ec',
                region          : 'center',
                startDate       : this.startDate,
                endDate         : this.endDate,
                resourceStore   : resourceStore,
                resourceZones   : availabilityStore,
                eventStore      : eventStore,
                plugins     	: [
			        Ext.create('Sch.plugin.EventTools', {
				        items : [
				            { type: 'delete', handler: function(event, toolEl, toolsMenu, tool) {
				            	var record = toolsMenu.getRecord();
				            	switch(tool.type) {
				            	case 'delete':
				            		eventStore.remove(record);
				            		var EndDate = record.get("EndDate");
				            		var StartDate = record.get("StartDate");
				            		var Id = record.get("Id");
				            		var Name = record.get("Name");
				            		var Duration = (EndDate-StartDate)/1000/60/60;
				            		record.set('Duration',Duration);
				            		
				            		var uptgStore = Ext.getCmp('uptg').store;
				            		uptgStore.add(record);
				            		toolsMenu.hide();
				            		break;
				            	}
				            },   
				            tooltip: 'Remove Event', visibleFn: function(model) { return model.get('Deletable'); } }
				        ]
				    })
			    ]
            },
            {
            	id				: 'uptg',
                xtype           : 'unplannedtaskgrid',
                width           : 180,
                split           : true,
                region          : 'east',
                weight          : 20
            }]
        });
        this.callParent(arguments);
        // In a real life application, you'd probably batch these store loads to just use one Ajax request.
        eventStore.load();
        resourceStore.load();
        availabilityStore.load();
    }
});    