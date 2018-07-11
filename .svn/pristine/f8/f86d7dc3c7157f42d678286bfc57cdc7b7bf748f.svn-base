Ext.ns("Ext.ux");
Ext.ns("Ext.ux.menu");
Ext.ux.Button = Ext.extend(Ext.Button,{
	constructor:function(cfg){
		Ext.ux.Button.superclass.constructor.call(this,cfg);
	},
	// private
    onRender : function(ct, position){
    	Ext.ux.Button.superclass.onRender.call(this,ct, position);       
    	this.setDisabled(!this.PermissionsPointControl());
    },
    /**
     *    
     * @param {} e
     */
	onClick : function(e){
		if(!this.PermissionsPointControl()){
			Ext.MessageBox.show({
				title:"友情提示",
				msg:"您没有该操作权限,请与管理员联系!",
				icon:Ext.MessageBox.WARNING,
				buttons:Ext.MessageBox.OK
			})
			return ;
		}		
 		Ext.ux.Button.superclass.onClick.call(this,e);		
    },
    /**
     * 权限点控制
     */
    PermissionsPointControl:function(){
    	if(!Ext.isDefined(this.PermissionCode))return true;
    	var getCurrentMoudle = JH.getCurrentMoudle();
    	var PermissionCode    = this.PermissionCode;
    	var userid     		  =JH.USER.userid;
    	var returnValue = false;
    	if(!Ext.isEmpty(getCurrentMoudle) && !Ext.isEmpty(PermissionCode)){    		
    		SAC.hasPermission(userid,getCurrentMoudle.id,PermissionCode,function(r){    		 
    			returnValue =  r;
    		});
    	};
    	return returnValue;
    }
});
Ext.reg("uxbutton",Ext.ux.Button);
Ext.ux.menu.Item = Ext.extend(Ext.menu.Item,{
	constructor:function(cfg){
		Ext.ux.menu.Item.superclass.constructor.call(this,cfg);
	},
	// private
    onRender : function(ct, position){    
    	Ext.ux.menu.Item.superclass.onRender.call(this,ct, position);
    	//this.setDisabled(!this.PermissionsPointControl());
    },
    /**
     *    
     * @param {} e
     */
	onClick : function(e){
		if(!this.PermissionsPointControl()){
			Ext.MessageBox.show({
				title:"友情提示",
				msg:"您没有该操作权限,请与管理员联系!",
				icon:Ext.MessageBox.WARNING,
				buttons:Ext.MessageBox.OK
			})
			return ;
		}	
 		Ext.ux.menu.Item.superclass.onClick.call(this,e);		
    },
    /**
     * 权限点控制
     */
    PermissionsPointControl:function(){
    	var getCurrentMoudle = JH.getCurrentMoudle();
    	var PermissionCode    = this.PermissionCode;
    	var userid     		  =JH.USER.userid;
    	var returnValue = false;
    	if(!Ext.isEmpty(getCurrentMoudle) && !Ext.isEmpty(PermissionCode)){    		
    		SAC.hasPermission(userid,getCurrentMoudle.id,PermissionCode,function(r){    		 	
    			returnValue =  r;
    		});
    	};
    	return returnValue;
    }
});
Ext.reg("uxmenuitem",Ext.ux.menu.Item);