
Ext.onReady(function(){
    
    var ksinfo = {
					num: 4,
					name: '第一考场',
					ks: [{id: '1-1',name: '1-1',  code: '10001'},
							{id: '1-2',name: '1-2', code: '10002'},
							{id: '1-3',name: '1-3', code: '10003'},
							{id: '1-4',name: '1-4', code: '10004'},
							{id: '2-1',name: '2-1', code: '10005'},
							{id: '2-2',name: '2-2', code: '10006'},
							{id: '2-3',name: '2-3', code: '10007'},
							{id: '2-4',name: '2-4', code: '10008'},
							{id: '3-1',name: '3-1', code: '10009'},
							{id: '3-2',name: '3-2', code: '10010'}]
				};

	var orgX, orgY;		
	
	
	var overrides = {
        b4StartDrag : function() {
        	if (!this.el) {
                this.el = Ext.get(this.getEl());
            }
        	this.originalXY = this.el.getXY();
        },
        onInvalidDrop : function() {
        },
        endDrag : function() {
        },
        onDragDrop : function(evtObj, targetElId) {
        	//拖动鼠标放开时，x偏移量
        	var dx = evtObj.xy[0] - orgX;
        	var dy = evtObj.xy[1] - orgY;
        	
        	//根据鼠标的坐标，得到行号和列号
        	var xPos = Math.ceil(dx / 100);
        	var yPos = Math.ceil(dy / 100);
        	
        	//将要替代的考生
        	var stuB = Ext.get(yPos+'-'+xPos);
        	
        	//判断要替代的考生是否存在
        	if(stuB != null) {
	        	this.el.setXY([stuB.getX(), stuB.getY()]);
	        	stuB.el.setXY([this.originalXY[0], this.originalXY[1]]);
	        	//交换拖动对象的id
	        	Ext.getDom(stuB.id).id = this.id;
	        	Ext.getDom(this.id).id = yPos+'-'+xPos;
        	} else {
        		this.el.setXY([(xPos-1)*100+orgX, (yPos-1)*100+orgY]);
        	}
        	
        },
        onDragEnter : function(evtObj, targetElId) {
        },
        onDragOut : function(evtObj, targetElId) {
        }
    };			
    var num = ksinfo['num'];
    var name = ksinfo['name'];
    var panel = Ext.create('Ext.Panel', {
    	id: 'inroom',
    	renderTo: Ext.getBody(),
    	width: num*100,
        layout: {
            type: 'table',
            columns: num,
            pack: 'center'
        },
		margin: '20 0 0 20',
        title: name,
        // applied to child components
        defaults: {frame:true, width:100, height: 100, header: false}
    });
    
    
    var ks = ksinfo['ks'];
    
    //设置拖动 目标组
    var inroomDDTarget = Ext.create('Ext.dd.DDTarget', 'inroom','stuDDGroup');
    
    Ext.each(ks, function(r) {
    	var id = r['id'];
    	var name = r['name'];
    	var code = r['code'];
    	var stu = Ext.create('Ext.Panel', {id: id,
								    		html: name + '<br/>考号：' + code + '<br/>' + code,   
								    		draggable: true});
    	
    	panel.add(stu);
    	
    	
    	var dd = Ext.create('Ext.dd.DD', stu, 'stuDDGroup', {
            isTarget  : true
        });
        
        //Apply the overrides object to the newly created instance of DD
        Ext.apply(dd, overrides);
    	
    	new Ext.ToolTip({
	        target: id,
	        html: '拖动交互位置',
	        trackMouse:true,
	        dismissDelay: 15000
	    });
    });
    
    var stu1 = Ext.get('1-1');
	
	//外层panel的x,y
	orgX = stu1.getX()
	orgY = stu1.getY()
    
});