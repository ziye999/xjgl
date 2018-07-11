 (function(){
	MBspInfo = Ext.extend(Ext.util.Observable,{
		getUserId:function(){
			return Ext.get('UserId').dom.value;
		},
		getUserName:function(){
			return Ext.get('UserName').dom.value;
		},
		getUserType:function(){
			return Ext.get('UserType').dom.value;
		},
		getOrganCode:function(){
			return Ext.get('OrganCode').dom.value;
		},
		getOrganName:function(){
			return Ext.get('OrganName').dom.value;
		}	
	});	
})();

