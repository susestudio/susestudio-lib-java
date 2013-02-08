package com.suse.studio.client.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestFactory {

    private static RequestFactory instance = new RequestFactory();

    private RequestFactory() {
    }

    public static RequestFactory getInstance() {
        return instance;
    }

    /**
     * Init a {@link HttpURLConnection} object from a given uri.
     *
     * @param method
     * @param uri
     * @param encodedCredentials
     * @return
     * @throws IOException
     */
    public HttpURLConnection initConnection(String method, String uri,
            String encodedCredentials) throws IOException {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod(method);
            if (encodedCredentials != null) {
                connection.setRequestProperty("Authorization", "BASIC "
                        + encodedCredentials);
            }
            return connection;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
