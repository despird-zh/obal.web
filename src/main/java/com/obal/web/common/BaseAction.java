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


/**
 * @author gdiao 
 * @version V1.0 2013-1-4
 * 
 * 该类为UB公司项目中全部动作的基础类型
 * 
 * @author gdiao
 * @version 1.1 2014-1-10
 * 有时需要通过Ajax或URL提供token的控制方式防止重复提交，为满足此种情况增加Token属性以及resetToken值的方法
 * 在JSP中的Url有如下示例代码:
 * <s:url var="linkdelete" namespace="/admin/insecure/upload" action="DeleteLatestUpload" >
 * 	<s:param name="struts.token.name" value="%{'token'}"/>
 * 	<s:param name="token" value="%{token}"/>
 * </s:url>
 **/
public abstract class BaseAction extends ActionSupport implements ServletRequestAware,
	ServletResponseAware,SessionAware{

	private static final long serialVersionUID = 1L;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;
	
	private Object jsonData = null;
	
	/** Token */
	private String token;

	/**
	 * 获得Token信息 
	 **/
	public String getToken() {
	    return token;
	}

	/**
	 * 设置Token信息 
	 **/
	public void setToken(String token) {
	    this.token = token;
	}
	
	/**
	 * 重新设置Token信息 
	 **/
	public void resetToken(){
		
		Map<String, Object> context = getActionContext().getValueStack().getContext();
	    Object myToken = context.get("token");
	    if (myToken == null) {
	        myToken = TokenHelper.setToken("token");
	        context.put("token", myToken);
	    }
	    token = myToken.toString();
	}
	
	/**
	 * 默认构造函数 
	 **/
	public BaseAction(){}
	
	/**
	 * 设置响应对象 
	 **/
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	/**
	 * 设置请求对象 
	 **/
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	/**
	 * 设置会话的Session属性集信息 
	 **/
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	 
	/**
	 * 读取JSON数据 ，该数据将会被转化长JSON格式返回到客户端的Response中 
	 **/
	public Object getJsonData() {
		return this.jsonData;
	}

	/**
	 * 设置JSON数据，该数据将会被转化长JSON格式返回到客户端的Response中 
	 **/
	public void setJsonData(Object _jsonData) {
		this.jsonData = _jsonData;
	}
	
	/**
	 * 将Request中的数据封装到指定Bean中
	 * 
	 * @param  Object beanObj 写入信息的Bean对象
	 **/
	public void readBean(Object beanObj){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(beanObj); 

		binder.bind(request); 
	}
	
	/**
	 * 从Shiro中获得Principle信息
	 * 
	 * @return Principal 用户的认证基本信息
	 **/
	public Principal getPrincipalFromShiro(){
		
		Subject currentUser = SecurityUtils.getSubject();
		return (Principal)currentUser.getPrincipal();
	}
	
	/**
	 * 获得Session中的属性
	 * @return Object session中的对象 
	 **/
	public Object getSessionAttr(String key) {
		  return session.get(key);
	}
	
	/**
	 * 设置Session中的属性
	 * @param name - 属性名称
	 * @param value - 属性值 
	 **/
	public void setSessionAttr(String name, Object value) {
		session.remove(name);
		session.put(name, value);
	}
	
	/**
	 * 动作执行的上下文 
	 **/
	protected ActionContext getActionContext() {
		 return ActionContext.getContext();
	}
	
	/**
	 *  获得Request中的属性信息
	 *  @param name 属性名称
	 *  @return Object 属性值
	 **/
	protected Object getRequestAttr(String name) {
		return request.getAttribute(name);
	}
	
	/**
	 * 设置属性值
	 * @param name - 属性名称
	 * @param value - 属性值 
	 **/
	public void setRequestAttr(String name, Object value) {
		  request.setAttribute(name, value);
	}
	
	/**
	 *  获得Request中的参数信息
	 *  @param name 属性名称
	 *  @return String 属性值
	 **/
	public String getRequestParam(String name) {
		
		  return request.getParameter(name);
	}

	/**
	 *  获得Request中的参数信息
	 *  @param name 属性名称
	 *  @return String[] 属性值
	 **/
	public String[] getRequestParamValues(String name) {
		
		  return request.getParameterValues(name);
	}
	
	/**
	 *  获得应用本地的真实路径，eg:c:\slsl\ss
	 *  
	 **/
	public String getRootRealPath(){
		
		ActionContext ac = ActionContext.getContext();     
        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);     
        return sc.getRealPath("/"); 
		
	}
	
	public abstract String execute() throws WebException;
	
}
