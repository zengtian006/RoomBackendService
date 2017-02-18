package com.room.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements
		ExceptionMapper<IllegalArgumentException> {

	public Response toResponse(IllegalArgumentException exception) {
		return Response.ok("Illegal Argument Exception Caught").build();

	}

}
