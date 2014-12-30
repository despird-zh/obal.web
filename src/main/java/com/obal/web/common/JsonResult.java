package com.obal.web.common;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;




import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpResponse;

import com.opensymphony.xwork2.ActionInvocation;


public class JsonResult extends StreamResult {

	private static final long serialVersionUID = -5625482038953808264L;
	
	static Logger logger = LoggerFactory.getLogger(JsonResult.class);
	static BaseJsonConverter converter = new BaseJsonConverter();
	@Override
	public void execute(ActionInvocation invocation) {
				
		BaseAction action = null;
		if(invocation.getAction() instanceof BaseAction){
			
			action = (BaseAction)invocation.getAction();
		}else{
			logger.debug("Not the specified action ... return!");
			return;
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		/**
		 * Declare the converter outside the function to save the memory. and high perfomr
		 **/
		//BaseJacksonConverter converter = new BaseJacksonConverter();
		try {
			logger.debug("Start Parsing json data..");
			Object jd = action.getJsonData();
			if(jd == null){
				logger.debug("Json Data is null");
			}else{
				if(logger.isDebugEnabled()){
					
					ObjectMapper om= converter.getObjectMapper();					
					logger.debug("Json Data:" + om.writeValueAsString(jd));
				}					
				
				if(response.isCommitted()){
					logger.debug("Response is already closed!");
					return;					
				}
		
				converter.writeInternal(jd, new ServletServerHttpResponse(response));
			}
			logger.debug("End Parsing json data..");
		} catch (HttpMessageNotWritableException e) {

			logger.error("Error when writing json data to httpresponse",e);

		} catch (IOException e) {

			logger.error("Error when writing json data to httpresponse",e);
		}
	}
}
