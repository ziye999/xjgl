/*!
 * Ext JS Library 3.3.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.onReady(function() {
    Ext.define('Image', {
	    extend: 'Ext.data.Model',
	    fields: [
	        { name:'src', type:'string' },
	        { name:'caption', type:'string' }
	    ]
	});
	
	Ext.create('Ext.data.Store', {
	    id:'imagesStore',
	    model: 'Image',
	    data: [
	        { src:'http://www.sencha.com/img/20110215-feat-drawing.png', caption:'Drawing & Charts' },
	        { src:'http://www.sencha.com/img/20110215-feat-data.png', caption:'Advanced Data' },
	        { src:'http://www.sencha.com/img/20110215-feat-html5.png', caption:'Overhauled Theme' },
	        { src:'http://www.sencha.com/img/20110215-feat-perf.png', caption:'Performance Tuned' }
	    ]
	});
	
	var imageTpl = new Ext.XTemplate(
	    '<tpl for=".">',
	        '<div style="margin-bottom: 10px;" class="thumb-wrap">',
	          '<img src="{src}" />',
	          '<br/><span>{caption}</span>',
	        '</div>',
	    '</tpl>'
	);
	
	
	Ext.create('Ext.Panel', {
        id: 'images-view',
        frame: true,
        collapsible: true,
        width: 535,
        renderTo: Ext.getBody(),
        title: 'Simple DataView (0 items selected)',
        items: Ext.create('Ext.view.View', {
			    store: Ext.data.StoreManager.lookup('imagesStore'),
			    tpl: imageTpl,
			    height: 300,
			    autoScroll  : true,
			    draggable: {delegate: '.thumb-wrap'},
			    itemSelector: 'div.thumb-wrap',
			    emptyText: 'No images available',
			    renderTo: Ext.getBody()
			})
	});
	
	
    
});