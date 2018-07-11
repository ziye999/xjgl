/***
 * This is a simple MVC demo app where 2 scheduler view share data.
 * There is comments in each class describing how things work and you can also look up the configuration
 * properties for each class in the Sencha or Bryntum API docs
 *
 * http://docs.sencha.com/ext-js/4-1/
 * http://www.bryntum.com/docs
 */

// MVC relies on loading on demand, so we need to enable and configure the loader
Ext.Loader.setConfig({
    disableCaching  : true,
    enabled         :true
});
Ext.application({
    name        : 'MyApp',       // Our global namespace

    appFolder   : 'lib',  // The folder for the JS files

    controllers : [
        'ApController'
    ],

    // We'll create our own 'main' UI
    autoCreateViewport	: false,

    launch      : function() {
    	console.log("app launch begin");
    	Ext.create("Ext.container.Viewport",{
    		items : [{
	    			xtype : 'panel',
	    			title : 'test'
    			},
    			{
    				xtype: 'buildingRooms'
    			}
    		
    		]
			
    	});
    	console.log("app launch end");
    }
});


