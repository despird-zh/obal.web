
package com.obal.web.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class CredMatcher implements CredentialsMatcher {
	public static Logger logger = Logger.getLogger(CredMatcher.class);
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		
		logger.debug("entering matcher...");
		if((info instanceof AuthenInfo) && (token instanceof AuthenToken)){

			return authenModeDctm((AuthenToken)token, (AuthenInfo)info);
		}
		
		return true;
	}
	
	/**
	 * Compare the Dctm standard mode processing 
	 **/ 
	private boolean authenModeDctm(AuthenToken token, AuthenInfo info){
		
		String login = ((AuthenToken)token).getUsername();
		String password = new String(((AuthenToken)token).getPassword());
		
		/*ILoginService loginsvc = null;
		try{
			
			//loginsvc = ServiceFactory.getSingleton().getService(null, ILoginService.class);
			loginsvc = ServiceFactory.getSingleton().getAdminService(ILoginService.class);
			loginsvc.authenticate(login, password, docBase);
			
		}catch(SvcException de){
			logger.debug("Exception when authentication:"+login);
			//de.printStackTrace();
			return false;
		}*/
		return true;
	}

}
