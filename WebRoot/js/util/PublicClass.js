/**
 * StringBuffer 类型(模拟java中的StringBuffer)  
 * 构造方法参数 String类型 
 * 方法1： append     
 * 方法2:  toString   
 * @param {} str
 */
function StringBuffer(str){
	this._strings = new Array(str);
	StringBuffer._initialized  = false;
	if(StringBuffer._initialized ==false){
		StringBuffer.prototype.append   = function(str){
			this._strings.push(str);
			return this;
		};
		StringBuffer.prototype.toString = function(){
			return this._strings.join("");
		};
	}
	StringBuffer._initialized = true;
}
/**
 *  计算点是否在面内
 * @param {} pPoints面对象的点集合，相当于Geometry.point2Ds json
 * @param {} plPolyCounts面对象子对象集合，相当于Geometry.parts
 * @param {} lCount面对象子对象集合总数，相当于Geometry.parts.length
 * @param {} point要检测的点对象
 * @return {Boolean}
 */
function PointInPolyPolygon(pPoints,plPolyCounts, lCount, point){
	if(pPoints == null || plPolyCounts == null || lCount < 0 ){
		return false ;
	}

	var counter = 0;
	var i,k, nCount;
	var xinters;
	var p1,p2;
	var sumnCount = 0;	
	for(k=0; k<lCount; k++){
		nCount = plPolyCounts[k];
		///////////////////////////////////////////////////
		p1 = pPoints[0+sumnCount];
		for (i=1;i<=nCount;i++){
			p2 = pPoints[i % nCount+sumnCount];
			if (point.y >Math.min(p1.y,p2.y)){
					if (point.y <= Math.max(p1.y,p2.y)){
						if (point.x <= Math.max(p1.x,p2.x)){
							if (p1.y != p2.y){
								xinters = (point.y-p1.y)*(p2.x-p1.x)/(p2.y-p1.y)+p1.x;
								//if (p1.x == p2.x || point.x <= xinters)
								if( IS0(p1.x - p2.x) || (point.x < xinters) || IS0(point.x - xinters) ){
									counter++;
								}
							}
						}
					}
				}
				p1 = p2;
		}
		///////////////////////////////////////////////////
		sumnCount += nCount;
		//pPoints += nCount;
	}
	if (counter % 2 == 0)	{
		return false;
	}else{
		return true;
	}
}
function IS0(v){
	var EP = 0.0000000001;
	var	NEP = -0.0000000001;
	if((v > NEP) && (v < EP)){
		return true;
	}else{
		return false;
	}
}
function getLocationPram(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(r[2]); return null;
}
function getMenuJs(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return menuMap.get(unescape(r[2])); return null;
}
//定义map     
function Map(){      
	this.container = {};    
	this.containername = {};
}       
//将key-value放入map中     
Map.prototype.put = function(key,value,name){      
	try{          
		if(key!=null && key != "")     
			this.container[key] = value;      
		this.containername[key] = name;    
	}catch(e){      
		return e;      
	}
};        
//根据key从map中取出对应的value     
Map.prototype.get = function(key){      
	try{
		return this.container[key];        
	}catch(e){   
		return e;      
	}  
};      
Map.prototype.getName = function(key){      
	try{	  
		return this.containername[key];      	  
	}catch(e){      
		return e;      
	}      
};	 
//判断map中是否包含指定的key     
Map.prototype.containsKey=function(key){       
	try{     
		for(var p in this.container){     
			if(this.p==key)     
				return true;     
		}  
		return false;  
	}catch(e){
		return e;     
	}  
}  
//判断map中是否包含指定的value     
Map.prototype.containsValue = function(value){      
	try{  
		for(var p in this.container){     
			if(this.container[p] === value)      
				return true;      
		}        
		return false;        
	}catch(e){      
		return e;      
	}      
};          
//删除map中指定的key     
Map.prototype.remove = function(key){      
	try{
		delete this.container[key];      
		delete this.containername[key];      
	}catch(e){      
		return e;      
	}      
};        
//清空map     
Map.prototype.clear = function(){      
	try{      
		delete this.container;  
		delete this.containername;
		this.container = {};      
		this.containername = {}; 
	}catch(e){      
		return e;      
	}      
};      
  
//判断map是否为空     
Map.prototype.isEmpty = function(){   
	if(this.keyArray().length==0)     
		return true;     
	else      
		return false;     
};  
//获取map的大小     
Map.prototype.size=function(){  
	return this.keyArray().length;     
}       
//返回map中的key值数组     
Map.prototype.keyArray=function(){  
	var keys=new Array();     
	for(var p in this.container){    
		keys.push(p);     
	}         
	return keys;     
}       
//返回map中的value值数组     
Map.prototype.valueArray=function(){        
	var values=new Array();     
	var keys=this.keyArray();     
	for(var i=0;i<keys.length;i++){     
		values.push(this.container[keys[i]]);     
	}           
	return values;     
}

function getSessionId(){
   var c_name = 'JSESSIONID';
   if(document.cookie.length>0){
      c_start=document.cookie.indexOf(c_name + "=")
      if(c_start!=-1){ 
        c_start=c_start + c_name.length+1 
        c_end=document.cookie.indexOf(";",c_start)
        if(c_end==-1) c_end=document.cookie.length
        return unescape(document.cookie.substring(c_start,c_end));
      }
   }
}