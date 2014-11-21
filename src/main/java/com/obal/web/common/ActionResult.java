package com.obal.web.common;

/**
 * 该类用来通过JSON传递请求在后台的执行结果，并返回消息
 * 其状态通过BaseResult.state进行设置 
 **/
public class ActionResult  extends BaseResult{
	String message = null;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
