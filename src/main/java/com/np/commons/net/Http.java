package com.np.commons.net;

import java.net.http.HttpClient;
import java.time.Duration;

public class Http {

	private static HttpClient client = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.ofSeconds(10))
			.build();

	private static Http singleton;
	
	private Http(int timeout)
	{
		client = HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_1_1)
				.connectTimeout(Duration.ofSeconds(timeout))
				.build();
	}
	
	public static synchronized Http getInstance(int timeout)
	{
		if (singleton == null)
		{
			singleton = new Http(timeout);
		}
		
		return singleton;
	}
	
	public static synchronized Http getInstance()
	{
		return singleton;
	}
	
	public HttpClient getClient() {
		return client;
	}
}
