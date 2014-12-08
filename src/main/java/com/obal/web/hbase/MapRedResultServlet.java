package com.obal.web.hbase;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapRedResultServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	static Logger LOGGER = LoggerFactory.getLogger(MapRedResultServlet.class);

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
		LOGGER.debug(uri.toString());
	}
}
