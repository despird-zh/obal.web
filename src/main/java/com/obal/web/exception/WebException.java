package com.obal.web.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.helpers.MessageFormatter;

import com.obal.exception.BaseException;

public class WebException extends BaseException{


	private static final long serialVersionUID = 1L;
	
	private static Properties web_exceps = null;
	
	static{
		
		Class<?> selfclazz = WebException.class;
		String fullname = selfclazz.getName();
		fullname = fullname.replace('.', '/');
		fullname = fullname + ".properties";
		InputStream is = selfclazz.getClassLoader().getResourceAsStream(fullname);
		web_exceps = new Properties();
		try {
			web_exceps.load(is);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public WebException(String errorcode, String... param) {
		super(errorcode, param);
		this.message = findMessage(errorcode, param);
	}
	
    public WebException(String errorcode, Throwable cause,String ...param) {
        super(errorcode, cause);
        this.message = findMessage(errorcode, param);
    }
    
    public WebException(Throwable cause) {
        super(cause);
    }
    	
	protected String findMessage(String errorcode,String ... param){
		
		String messagePattern = web_exceps.getProperty(errorcode, errorcode);
		if(errorcode.equals(messagePattern)){
			return super.findMessage(errorcode, param);
		}
		return MessageFormatter.arrayFormat(messagePattern, param).getMessage();
	}

}
