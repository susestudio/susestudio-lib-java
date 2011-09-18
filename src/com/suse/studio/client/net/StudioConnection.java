package com.suse.studio.client.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class StudioConnection {
	
	private HttpURLConnection connection;
	private InputStream responseBodyStream;
	private String uri = null;
	private String encodedCredentials = null;

	public StudioConnection(String uri) {
		this.uri = uri;
	}
	
	public StudioConnection(String uri, String encodedCredentials) {
		this.uri = uri;
		this.encodedCredentials = encodedCredentials;
	}
	
	public <T> T get(Class<T> class1) throws IOException {
		if (encodedCredentials == null) {
			connection = Request.get(uri);
		} else {
			connection = Request.get(uri, encodedCredentials);
		}
		connection.connect();
		responseBodyStream = connection.getInputStream();

		T result = parseBodyStream(class1);
		connection.disconnect();
		return result;
	}
	
	public <T> T post(Class<T> class1) throws IOException {
		if (encodedCredentials == null) {
			connection = Request.post(uri);
		} else {
			connection = Request.post(uri, encodedCredentials);
		}
		connection.connect();
		responseBodyStream = connection.getInputStream();

		T result = parseBodyStream(class1);
		connection.disconnect();
		return result;
	}
	
    private <T> T parseBodyStream(Class<T> class1) {
    	T result = null;
        Serializer serializer = new Persister();
        try {
			result = serializer.read(class1, responseBodyStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
