package com.suse.studio.client.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import com.suse.studio.client.model.ErrorResult;
import com.suse.studio.client.util.ParserUtils;
import com.suse.studio.client.util.StudioConfig;

public class StudioConnection {

    private String uri = null;
    private String encodedCredentials = null;

    /**
     * Init a connection to SUSE Studio with a given configuration.
     * 
     * @param uri
     * @param config
     */
    public StudioConnection(String uri, StudioConfig config) {
        this.uri = config.getBaseURL() + uri;
        this.encodedCredentials = config.getEncodedCredentials();
    }

    /**
     * Perform a GET request and parse the result into given {@link Class}.
     * 
     * @param clazz
     * @return instance of clazz
     * @throws IOException
     * @throws StudioException if SUSE Studio returns an error response
     */
    public <T> T get(Class<T> clazz) throws IOException, StudioException {
        return request(clazz, "GET");
    }

    /**
     * Perform a POST request and parse the result into given {@link Class}.
     * 
     * @param clazz
     * @return instance of clazz
     * @throws IOException
     * @throws StudioException if SUSE Studio returns an error response
     */
    public <T> T post(Class<T> clazz) throws IOException, StudioException {
        return request(clazz, "POST");
    }

    /**
     * Perform a PUT request persisting an object as XML in the request body, and parse the result into given
     * {@link Class}.
     * 
     * @param clazz
     * @param object
     * @return instance of clazz
     * @throws IOException
     * @throws StudioException if SUSE Studio returns an error response
     */
    public <T> T put(Class<T> clazz, Object object) throws IOException, StudioException {
        return request(clazz, "PUT", object);
    }

    /**
     * Perform a DELETE request and parse the result into given {@link Class}.
     * 
     * @param clazz
     * @return instance of clazz
     * @throws IOException
     * @throws StudioException if SUSE Studio returns an error response
     */
    public <T> T delete(Class<T> clazz) throws IOException, StudioException {
        return request(clazz, "DELETE");
    }

    /**
     * Perform an HTTP request and parse the result into given {@link Class}.
     * 
     * @param clazz result's expected class
     * @param method the HTTP method to use
     * @return instance of clazz
     * @throws IOException
     * @throws StudioException if SUSE Studio returns an error response
     */
    private <T> T request(Class<T> clazz, String method) throws IOException, StudioException {
        return request(clazz, method, null);
    }

    /**
     * Perform an HTTP request and parse the result into given {@link Class}. Optionally, persist a given {@link Object}
     * in the request body.
     * 
     * @param clazz result's expected class
     * @param method the HTTP method to use
     * @param object object to persist in request body or null
     * @return instance of clazz
     * @throws IOException
     * @throws StudioException if SUSE Studio returns an error response
     */
    private <T> T request(Class<T> clazz, String method, Object object) throws IOException, StudioException {
        HttpURLConnection connection = RequestFactory.getInstance().initConnection(method, uri, encodedCredentials);

        if (object != null) {
            connection.setDoOutput(true);
            ParserUtils.persistInStream(object, connection.getOutputStream());
        }

        connection.connect();
        int responseCode = connection.getResponseCode();

        if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
            // request was successful, get response body
            InputStream inputStream = connection.getInputStream();
            T result = ParserUtils.parseBodyStream(clazz, inputStream);
            connection.disconnect();
            return result;
        }

        // request was not successful, get a response error
        InputStream inputStream = connection.getErrorStream();
        ErrorResult error = ParserUtils.parseBodyStream(ErrorResult.class, inputStream);
        throw new StudioException(error.getCode(), error.getMessage());
    }
}
