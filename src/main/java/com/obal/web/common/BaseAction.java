package com.obal.web.common;


import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.TokenHelper;
import org.springframework.web.bind.ServletRequestDataBinder;

import com.obal.core.security.Principal;
import com.obal.web.exception.WebException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;



public abstract class BaseAction extends ActionSupport implements ServletRequestAware,
	ServletResponseAware,SessionAware{

	private static final long serialVersionUID = 1L;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;
	
	private Object jsonData = null;

	private String token;


	public String getToken() {
	    return token;
	}


	public void setToken(String token) {
	    this.token = token;
	}

	public void resetToken(){
		
		Map<String, Object> context = getActionContext().getValueStack().getContext();
	    Object myToken = context.get("token");
	    if (myToken == null) {
	        myToken = TokenHelper.setToken("token");
	        context.put("token", myToken);
	    }
	    token = myToken.toString();
	}
	

	public BaseAction(){}
	

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Object getJsonData() {
		return this.jsonData;
	}

	public void setJsonData(Object _jsonData) {
		this.jsonData = _jsonData;
	}
	

	public void readBean(Object beanObj){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(beanObj); 

		binder.bind(request); 
	}
	

	public Principal getPrincipalFromShiro(){
		
		Subject currentUser = SecurityUtils.getSubject();
		return (Principal)currentUser.getPrincipal();
	}
	

	public Object getSessionAttr(String key) {
		  return session.get(key);
	}
	

	public void setSessionAttr(String name, Object value) {
		session.remove(name);
		session.put(name, value);
	}
	

	protected ActionContext getActionContext() {
		 return ActionContext.getContext();
	}
	

	protected Object getRequestAttr(String name) {
		return request.getAttribute(name);
	}

	public void setRequestAttr(String name, Object value) {
		  request.setAttribute(name, value);
	}
	

	public String getRequestParam(String name) {
		
		  return request.getParameter(name);
	}


	public String[] getRequestParamValues(String name) {
		
		  return request.getParameterValues(name);
	}

	public String getRootRealPath(){
		
		ActionContext ac = ActionContext.getContext();     
        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);     
        return sc.getRealPath("/"); 
		
	}
	
	public abstract String execute() throws WebException;
	
}
