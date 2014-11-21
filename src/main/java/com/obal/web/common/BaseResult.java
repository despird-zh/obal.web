/*
 * Licensed to the Ultrabroad Company 
 * 
 */
package com.obal.web.common;

/**
 * This class is used to wrap the result of server side action process.<br>
 * It is designed as per ExtJS's ajax call result format. <br>
 * Theoretically We suggest every action's result wrapper class should extend this class.
 * 
 * 
 * @author Gary Diao mailto:diaogc@ultrabraod.com.cn 2012-07-01
 * @version V0.1
 **/
public class BaseResult {

	protected String state = null;

	/**
	 * Get the state of Action process result, the value of state should defined in WebConstants class.
	 * While if action need a private state value, the state constant should be defined in Action class
	 * 
	 * @return String the state of result.
	 **/
	public String getState() {
		return state;
	}

	/**
	 * Set the state of action result
	 * @param String the state 
	 **/
	public void setState(String state) {
		this.state = state;
	}	
	
}
