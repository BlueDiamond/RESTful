package com.bd.rs.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
public class HelloWorldService {
	
	private static Logger logger = LoggerFactory.getLogger(HelloWorldService.class);
	
	int i = 0;

	@GET
	@Path("/{param}")
	@Produces("text/plain")
	@Consumes("text/plain")
	public Response getMsg(@PathParam("param") String msg) {
		
		logger.debug("visitor count: " + ++i);

		String output = "Jersey say : " + msg;

		return Response.status(200).entity(output).build();

	}

}