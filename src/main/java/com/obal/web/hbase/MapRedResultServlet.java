package com.obal.web.hbase;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapRedResultServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	static Logger LOGGER = LoggerFactory.getLogger(MapRedResultServlet.class);
	
	int count = 0;
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		requestDispatch(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		requestDispatch(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		requestDispatch(request, response);
	}

	private void requestDispatch(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String uri = request.getRequestURI();
		uri = uri.substring(request.getContextPath().length());
		
		InputStream ins = request.getInputStream();
		 // Transfer bytes from in to out
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = ins.read(buf)) > 0) {
	        
	    	String str = new String(buf,0,len);
	    	System.out.print(str);
	    }
	    System.out.println();
	    count++;
		PrintWriter w = response.getWriter();
		w.write("__DONE__");
		response.flushBuffer();
		System.out.println("this is "+count+"th request");
		LOGGER.debug(uri.toString());
	}
}
