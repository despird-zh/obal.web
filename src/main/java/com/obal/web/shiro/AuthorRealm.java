/*
 * Licensed to the Ultrabroad Company 
 * 
 */
package com.obal.web.shiro;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.obal.core.security.Principal;

/**
 * @author despird 2014-02-01
 * @version V0.1
 * */
public class AuthorRealm extends AuthorizingRealm {

	public static Logger logger = Logger.getLogger(AuthorRealm.class);
	@Override
	public boolean supports(AuthenticationToken token) {
		if (token instanceof AuthenToken) {
			return true;
		}

		return false;
	}

	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Set<String>			roles			= new HashSet<String>();
		Set<Permission>		permissions		= new HashSet<Permission>();
		Collection<Principal>	principalsList	= principals.byType(Principal.class);
		
		if (principalsList.isEmpty()) {
			throw new AuthorizationException("Empty principals list!");
		}
		//LOADING STUFF FOR PRINCIPAL 
		for (Principal userPrincipal : principalsList) {
			// Only when dctm standard mode we try to fetch the group and role information
			//if(ServiceConstants.REALM_TYPE_DCTM.equals(userPrincipal.getRealm())){
				// ignore group query processing
			//}			
		}
		//THIS IS THE MAIN CODE YOU NEED TO DO !!!!
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		info.setRoles(roles); //fill in roles 
		info.setObjectPermissions(permissions); //add permisions (MUST IMPLEMENT SHIRO PERMISSION INTERFACE)
		
		return info;
	}

	/**
	 * It Covers two kinds of Token processing:
	 * UbDctmToken   --> UbDctmAuthenInfo
	 * UbSpnegoToken --> UbSpnegoAuthenInfo
	 **/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		AuthenticationInfo authenInfo = null;
		if (token instanceof AuthenToken) {
			authenInfo = getDctmAuthenInfo((AuthenToken)token);
		}

		return authenInfo;
	}

	
	/**
	 * Get Dctm standard password authentication info via  UbDctmToken
	 **/
	private AuthenInfo getDctmAuthenInfo(AuthenToken ubToken){
		
		Principal principal = null;
		/*ILoginService loginsvc = null;
		try {
			
			loginsvc = ServiceFactory.getSingleton().getAdminService(ILoginService.class);

			principal = loginsvc.loadByAccount(ubToken.getUsername());
			
		} catch (SvcException se) {
			throw new AuthenticationException(se);
		}*/
		
		if (principal == null) {
			throw new UnknownAccountException("Login name [" + ubToken.getUsername() + "] not found!");
		} /* ignore
		else if(StringUtils.isEmpty(principal.getGroupName())) {
	          throw new UnknownAccountException("Unknown Identity. Please ask IT admin for adding you into a certain department.");
		}*/
		logger.info("Found user with username [" + ubToken.getUsername() + "]");
		
		/**
		 * From Documentum we cannot retrieve the decrypted password, coz docbase block these sensitive information
		 * So We must reset the principle's password with Token information.
		 * Beside we also reset the docbase.
		 * */
	
		return new AuthenInfo(principal, principal.password(), getName());
	}	

}
