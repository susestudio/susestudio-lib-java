package com.suse.studio.client.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.suse.studio.util.Config;

public class Request {
	
	public static HttpURLConnection get(String uri) throws IOException {
		return request("GET", uri);
	}
	
	public static HttpURLConnection get(String uri, String encodedCredentials) throws IOException {
		return request("GET", uri, encodedCredentials);
	}
	
	public static HttpURLConnection post(String uri) throws IOException {
		return request("POST", uri);
	}
	
	public static HttpURLConnection post(String uri, String encodedCredentials) throws IOException {
		return request("POST", uri, encodedCredentials);
	}

	private static HttpURLConnection request(String method, String uri) throws IOException {
		URL url;
        try {
            url = new URL(Config.getBaseURL() + uri);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);

        return connection;
	}

	private static HttpURLConnection request(String method, String uri, String encodedCredentials) throws IOException {
    	HttpURLConnection connection = request(method, uri);
        connection.setRequestProperty("Authorization", "BASIC " + encodedCredentials);
        return connection;
    }

}
