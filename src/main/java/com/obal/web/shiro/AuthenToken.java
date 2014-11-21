
package com.obal.web.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author despird 2014-3-2
 * 
 **/
public class AuthenToken extends UsernamePasswordToken {
	
	private static final long serialVersionUID = -3725717335876726792L;

	private String language = null;

	/**
	 * Constructor for user password and language.
	 * 
	 * @param user the user 
	 * @param password the password
	 * @param language the language 
	 **/
	public AuthenToken(String user,String password, String language){
		
		super(user, password);
		this.language = language;
	}
	
	/**
	 * Get the language setting
	 * 
	 * @return String the language 
	 **/
	public String language() {
		return language;
	}

}
