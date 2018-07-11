package com.jiajie.bean;
public class MsgBean extends SystemBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 784441174235822072L;
	private boolean success; //是否成功
	private String msg;      //返回消息
	private Object data;   //返回结果
	private boolean show = true;
	public static String EXPORT_SUCCESS = "导入成功！";
	public static String EXPORT_ERROR = "导入失败！";
	public static String SAVE_SUCCESS = "保存成功！";
	public static String SAVE_ERROR = "保存失败！";
	public static String DEL_SUCCESS = "删除成功！";
	public static String DEL_ERROR = "删除失败！";
	public static String GETDATA_ERROR = "获取数据失败！";
	
	public MsgBean(){};
	public MsgBean(boolean success,String msg,Object data,boolean show){
		this.success = success;
		this.msg     = msg;
		this.data  = data;
		this.show    = show;
	}
	public MsgBean(boolean success,String msg,Object result){
		this(success,msg,result,true);
	}
	public MsgBean(Object result){
		this(false,null,result,false);
	}
	public MsgBean(boolean success,String msg,boolean show){
		this(success, msg,null,show);
	}
	public MsgBean(boolean success,String msg){
		this(success, msg,null,true);
	}
	public MsgBean(Object result,String msg){
		this(true, msg,result);
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public boolean getShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public void setSuccessMsg(boolean success,String msg) {
		this.success = success;
		this.msg = msg;
	}
}
