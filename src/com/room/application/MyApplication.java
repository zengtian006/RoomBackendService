package com.room.application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import com.room.utils.CustomExceptionMapper;
import com.room.ws.RoomWS;

public class MyApplication extends ResourceConfig {
	public MyApplication() {
		register(RequestContextFilter.class);
		
		// register(org.glassfish.jersey.server.filter.UriConnegFilter.class);
		register(RoomWS.class);
		register(CustomExceptionMapper.class);

	}
}
