package com.obal.web.hbase;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.mapreduce.ResultSerialization;
import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.DataOutputBuffer;
import org.apache.hadoop.io.serializer.Deserializer;
import org.apache.hadoop.io.serializer.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapRedHookerServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	static Logger LOGGER = LoggerFactory.getLogger(MapRedHookerServlet.class);
	
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

		ResultSerialization rstSerialUtil = new ResultSerialization();
		Deserializer<Result> deserializer = rstSerialUtil.getDeserializer(Result.class);
		String uri = request.getRequestURI();
		uri = uri.substring(request.getContextPath().length());
		
		InputStream ins = request.getInputStream();
		String index = request.getHeader("dataindex");
		System.out.println("start receive "+index+" th result.");
		 // Transfer bytes from in to out
//	    byte[] buf = new byte[1024];
//	    int len;
//	    while ((len = ins.read(buf)) > 0) {
//	        
//	    	String str = new String(buf,0,len);
//	    	//System.out.print(str);
//	    }
//	    System.out.println();
		DataOutputBuffer doutbuf = new DataOutputBuffer();
		IOUtils.copy(ins, doutbuf);
		System.out.println("length of data "+index+" :"+doutbuf.getLength());
		DataInputBuffer dinbuf = new DataInputBuffer();
		dinbuf.reset(doutbuf.getData(),doutbuf.getLength());
		deserializer.open(dinbuf);
		Result result = deserializer.deserialize(new Result());
		for (KeyValue keyValue : result.raw()) {  
            System.out.println("--=["+index+"] Column:" + new String(keyValue.getFamily())
            		+ "/Qualifier:" + new String(keyValue.getQualifier())       
            		+ "/Val:" + new String(keyValue.getValue()));
        }  
		PrintWriter w = response.getWriter();
		w.write("__DONE__");
		response.flushBuffer();
		System.out.println("end receive "+index+" th result");
		LOGGER.debug(uri.toString());
	}
}
