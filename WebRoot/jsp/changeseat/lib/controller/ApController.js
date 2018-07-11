/***
 * This controller glues together the application and handles the 'drop' event from the drop zone.
 * When the drop happens it simply removes it from one store, and inserts the task into the event store of the EmployeeScheduler.
 */
Ext.define("MyApp.controller.ApController", {
    extend : "Ext.app.Controller",

    views : [
        'BuildingRooms'
    ],

    refs : [
        // This auto-generates a "getEmployeeScheduler" getter for this ComponentQuery selector
        // See http://docs.sencha.com/ext-js/4-1/#!/api/Ext.app.Controller-cfg-refs
        { ref : "apcontroller", selector : 'apcontroller' }
    ],

    init : function() {
		console.log("controller init");
        this.control({
        	'buildingRooms':{
        		render : function(){
        			console.log("panel render");
        		}
        	}
        });
    }
})