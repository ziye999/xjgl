
Ext.onReady(function(){
	/*Ext.create('Ext.data.Store', {
	    storeId:'employeeStore',
	    fields:['firstname', 'lastname', 'seniority', 'dep', 'hired'],
	    data:[
	        {firstname:"Michael", lastname:"Scott", seniority:7, dep:"Management<br/>123", hired:"01/10/2004"},
	        {firstname:"Kevin", lastname:"Malone", seniority:4, dep:"Accounting", hired:"06/10/2007"},
	        {firstname:"Michael", lastname:"Halpert", seniority:3, dep:"Sales", hired:"02/22/2006"},
	        {firstname:"Michael", lastname:"Schrute", seniority:2, dep:"Sales", hired:"04/01/2004"},
	        {firstname:"Angela", lastname:"Martin", seniority:5, dep:"Accounting", hired:"10/21/2008"}
	    ],
	    groupField: 'firstname',
    	groupDir: 'DESC'
	});
	
	Ext.create('Ext.grid.Panel', {
	    title: 'Column Demo',
	    store: Ext.data.StoreManager.lookup('employeeStore'),
	    columns: [
	        {text: 'First Name',  dataIndex:'firstname'},
	        {text: 'Last Name',  dataIndex:'lastname'},
	        {text: 'Hired Month',  dataIndex:'hired', xtype:'datecolumn', format:'M'},
	        {text: 'Department (Yrs)', xtype:'templatecolumn', tpl:'{dep} ({seniority})'}
	    ],
	    hideHeaders: true,
		columnLines: true,
		draggable: true,
	    width: 400,
	    forceFit: true,
	    renderTo: Ext.getBody()
	});*/
	
	/*Ext.create('Ext.grid.property.Grid', {
	    title: 'Properties Grid',
	    width: 300,
	    renderTo: Ext.getBody(),
	    source: {
	        "(name)": "My Object",
	        "Created": Ext.Date.parse('10/15/2006', 'm/d/Y'),
	        "Available": false,
	        "Version": 0.01,
	        "Description": "A test object"
	    }
	});*/
	
	/*Ext.create('Ext.data.Store', {
	    storeId:'employeeStore',
	    fields:['firstname', 'lastname', 'seniority', 'dep', 'hired'],
	    data:[
	        {firstname:"Michael", lastname:"Scott"},
	        {firstname:"Dwight", lastname:"Schrute"},
	        {firstname:"Jim", lastname:"Halpert"},
	        {firstname:"Kevin", lastname:"Malone"},
	        {firstname:"Angela", lastname:"Martin"}
	    ]
	});
	
	Ext.create('Ext.grid.Panel', {
	    title: 'Action Column Demo',
	    store: Ext.data.StoreManager.lookup('employeeStore'),
	    columns: [
	        {text: 'First Name',  dataIndex:'firstname'},
	        {text: 'Last Name',  dataIndex:'lastname'},
	        {
	            xtype:'actioncolumn',
	            width:50,
	            items: [{
	                icon: 'extjs/examples/shared/icons/fam/cog_edit.png',  // Use a URL in the icon config
	                tooltip: 'Edit',
	                handler: function(grid, rowIndex, colIndex) {
	                    var rec = grid.getStore().getAt(rowIndex);
	                    alert("Edit " + rec.get('firstname'));
	                }
	            },{
	                icon: 'extjs/examples/restful/images/delete.png',
	                tooltip: 'Delete',
	                handler: function(grid, rowIndex, colIndex) {
	                    var rec = grid.getStore().getAt(rowIndex);
	                    alert("Terminate " + rec.get('firstname'));
	                }
	            }]
	        }
	    ],
	    width: 250,
	    renderTo: Ext.getBody()
	});*/
	
    
    var kcinfo = [{
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
				];
    
					
				
    Ext.each(kcinfo, function(v) {
        var buildingName = v['name'];
        var num = v['num'];
        var panel = Ext.create('Ext.Panel', {
        	renderTo: Ext.getBody(),
        	width: num*150,
	        layout: {
	            type: 'table',
	            columns: num,
	            pack: 'center'
	        },
			margin: '20 0 0 20',
	        title: buildingName,
	        // applied to child components
	        defaults: {frame:true, width:150, height: 150, header: false}
	    });
        
	    
	    var rooms = v['rooms'];
	    
	    Ext.each(rooms, function(r) {
	    	var id = r['id'];
	    	var name = r['name'];
	    	var rs = r['rs'];
	    	var code = r['code'];
	    	panel.add({
	    		id: id,
	    		xtype: 'panel',
	    		html: name + '<br/>' + rs + '<br/>' + code,   
	    		
	    		tools:[{
				    type:'refresh',
				    tooltip: 'Refresh form Data',
				    handler: function(event, toolEl, panelHeader) {
				    }
				}],
				
	    		listeners: {
	    			render : function() {
			          	Ext.fly(this.el).on('click',
				            function(e, t) {
				             	alert('Hi');
		            	});
			    	}
    			}
	    	});
	    	
	    	new Ext.ToolTip({
		        target: id,
		        html: '点击调整'+name+'座次',
		        trackMouse:true,
		        dismissDelay: 15000
		    });
	    });
    	
	});
    
	/*Ext.define('Ext.ux.Image', {
	    extend: 'Ext.Component', // subclass Ext.Component
	    alias: 'widget.managedimage', // this component will have an xtype of 'managedimage'
	    autoEl: {
	        tag: 'img',
	        src: Ext.BLANK_IMAGE_URL,
	        cls: 'my-managed-image'
	    },
	
	    // Add custom processing to the onRender phase.
	    // Add a ‘load’ listener to the element.
	    onRender: function() {
	        this.autoEl = Ext.apply({}, this.initialConfig, this.autoEl);
	        this.callParent(arguments);
	        this.el.on('load', this.onLoad, this);
	    },
	
	    onLoad: function() {
	        this.fireEvent('load', this);
	    },
	
	    setSrc: function(src) {
	        if (this.rendered) {
	            this.el.dom.src = src;
	        } else {
	            this.src = src;
	        }
	    },
	
	    getSrc: function(src) {
	        return this.el.dom.src || this.src;
	    }
	});
	
	var image = Ext.create('Ext.ux.Image');

	Ext.create('Ext.panel.Panel', {
	    title: 'Image Panel',
	    height: 200,
	    renderTo: Ext.getBody(),
	    items: [ image ]
	});
	
	image.on('load', function() {
	    console.log('image loaded: ', image.getSrc());
	});
	
	image.setSrc('http://www.sencha.com/img/sencha-large.png');*/
	
	
	/*Ext.define('User', {
	    extend: 'Ext.data.Model',
	    fields: ['id', 'name', 'age', 'gender'],
	    validations: [
	        {type: 'presence', name: 'name'},
	        {type: 'length',   name: 'name', min: 5},
	        {type: 'format',   name: 'age', matcher: /\d+/},
	        {type: 'inclusion', name: 'gender', list: ['male', 'female']},
	        {type: 'exclusion', name: 'name', list: ['admin']}
	    ]
	});
	
	
	var newUser = Ext.create('User', {
	    name: 'admin',
	    age: 'twenty-nine',
	    gender: 'not a valid gender'
	});
	
	// run some validation on the new user we just created
	var errors = newUser.validate();
	
	console.log('Is User valid?', errors.isValid()); //returns 'false' as there were validation errors
	console.log('All Errors:', errors.items); //returns the array of all errors found on this model instance
	
	console.log('Age Errors:', errors.getByField('age')); //returns the errors for the age field
*/	/*// Uses the User Model's Proxy
	Ext.create('Ext.data.Store', {
	    model: 'User'
	});
	
	// Gives us a reference to the User class
	var User = Ext.ModelMgr.getModel('User');
	
	var ed = Ext.create('User', {
	    name: 'Ed Spencer',
	    age : 25
	});
	
	// We can save Ed directly without having to add him to a Store first because we
	// configured a RestProxy this will automatically send a POST request to the url /users
	ed.save({
	    success: function(ed) {
	        console.log("Saved Ed! His ID is "+ ed.getId());
	    }
	});
	
	// Load User 1 and do something with it (performs a GET request to /users/1)
	User.load(1, {
	    success: function(user) {
	        console.log("Loaded user 1: " + user.get('name'));
	    }
	});*/
	
	/*Ext.define('User', {
	    extend: 'Ext.data.Model',
	    fields: ['id', 'name'],
	    proxy: {
	        type: 'ajax',
	        url : 'test.jsp',
	        reader: {
	            type: 'json',
	            root: 'users'
	        }
	    },
	
	    hasMany: 'Post' // shorthand for { model: 'Post', name: 'posts' }
	});
	
	Ext.define('Post', {
	    extend: 'Ext.data.Model',
	    fields: ['id', 'user_id', 'title', 'body'],
	
	    proxy: {
	        type: 'ajax',
	        url : 'test.jsp',
	        reader: {
	            type: 'json',
	            root: 'posts'
	        }
	    },
	    belongsTo: 'User',
	    hasMany: { model: 'Comment', name: 'comments' }
	});
	
	Ext.define('Comment', {
	    extend: 'Ext.data.Model',
	    fields: ['id', 'post_id', 'name', 'message'],
	
	    belongsTo: 'Post'
	});
	  console.log("User: ");
	// Loads User with ID 1 and related posts and comments using User's Proxy
	User.load(1, {
	    success: function(user) {
	        console.log("User: " + user.get('name'));
	
	        user.posts().each(function(post) {
//	            console.log("Comments for post: " + post.get('title'));
	
	            post.comments().each(function(comment) {
//	                console.log(comment.get('message'));
	            });
	            
	            // get the user reference from the post's belongsTo association
				post.getUser(function(user) {
				    console.log('Just got the user reference from the post: ' + user.get('name'))
				});
				
				// try to change the post's user
				post.setUser(100, {
				    callback: function(product, operation) {
				        if (operation.wasSuccessful()) {
				            console.log('Post\'s user was updated');
				        } else {
				            console.log('Post\'s user could not be updated');
				        }
				    }
				});
	        });
	        
			user.posts().add({
			    title: 'Ext JS 4.0 MVC Architecture',
			    body: 'It\'s a great Idea to structure your Ext JS Applications using the built in MVC Architecture...'
			});
			
//			user.posts().sync();
			
//			user.posts().each(function(post) {
//	            console.log("Comments for post: " + post.get('title'));
//	
//	            post.comments().each(function(comment) {
//	                console.log(comment.get('message'));
//	            });
//	        });
	    }
	});*/
	
	/*jasmine.getEnv().addReporter(new jasmine.TrivialReporter());
    jasmine.getEnv().execute();
	
	describe("Basic Assumptions", function() {

	    it("has ExtJS4 loaded", function() {
	        expect(Ext).toBeDefined();
	        expect(Ext.getVersion()).toBeTruthy();
	        expect(Ext.getVersion().major).toEqual(4);
	    });
	
	    it("has loaded AM code",function(){
	        expect(AM).toBeDefined();
	    });
	});*/
});