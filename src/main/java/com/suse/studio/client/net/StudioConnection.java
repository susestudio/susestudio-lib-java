package com.suse.studio.client.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import com.suse.studio.client.exception.SUSEStudioException;
import com.suse.studio.client.model.ErrorResult;
import com.suse.studio.client.util.ParserUtils;
import com.suse.studio.client.util.StudioConfig;

/**
 * Handles requests to SUSE Studio, serializing and deserializing objects as needed.
 * 
 * Note: you should not use this class directly as it is for internal use only. Please use the {@link com.suse.studio.client.SUSEStudio} class
 * instead.
 */
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
     * @throws SUSEStudioException if the request was not successful
     */
    public <T> T get(Class<T> clazz) throws SUSEStudioException {
        return request(clazz, "GET");
    }

    /**
     * Perform a POST request and ignore the result.
     *
     * @throws SUSEStudioException if the request was not successful
     */
    public void post() throws SUSEStudioException {
        request(null, "POST", null, true);
    }

    /**
     * Perform a POST request and parse the result into given {@link Class}.
     * 
     * @param clazz
     * @return instance of clazz
     * @throws SUSEStudioException if the request was not successful
     */
    public <T> T post(Class<T> clazz) throws SUSEStudioException {
        return request(clazz, "POST");
    }

    /**
     * Perform a PUT request persisting an object as XML in the request body, and parse the result into given
     * {@link Class}.
     * 
     * @param clazz
     * @param object
     * @return instance of clazz
     * @throws SUSEStudioException if the request was not successful
     */
    public <T> T put(Class<T> clazz, Object object) throws SUSEStudioException {
        return request(clazz, "PUT", object, false);
    }

    /**
     * Perform a DELETE request and parse the result into given {@link Class}.
     * 
     * @param clazz
     * @return instance of clazz
     * @throws SUSEStudioException if the request was not successful
     */
    public <T> T delete(Class<T> clazz) throws SUSEStudioException {
        return request(clazz, "DELETE");
    }

    /**
     * Perform an HTTP request and parse the result into given {@link Class}.
     * 
     * @param clazz result's expected class
     * @param method the HTTP method to use
     * @return instance of clazz
     * @throws SUSEStudioException if the request was not successful
     */
    private <T> T request(Class<T> clazz, String method) throws SUSEStudioException {
        return request(clazz, method, null, false);
    }

    /**
     * Perform an HTTP request and parse the result into given {@link Class}. Optionally, persist a given {@link Object}
     * in the request body.
     * 
     * @param clazz result's expected class
     * @param method the HTTP method to use
     * @param object object to persist in request body or null
     * @param ignoreResult Ignore the server response and return null. The parameter clazz will be ignored as well.
     * @return instance of clazz
     * @throws SUSEStudioException if the request was not successful
     */
    private <T> T request(Class<T> clazz, String method, Object object, boolean ignoreResult) throws SUSEStudioException {
        try {
            HttpURLConnection connection = RequestFactory.getInstance().initConnection(method, uri, encodedCredentials);

            if (object != null) {
                connection.setDoOutput(true);
                ParserUtils.persistInStream(object, connection.getOutputStream());
            }

            connection.connect();
            try {
                int responseCode = connection.getResponseCode();

                if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
                    if (ignoreResult) {
                        // don't care about the result or there is no result.
                        return null;
                    }
                    // request was successful, get response body
                    InputStream inputStream = connection.getInputStream();
                    try {
                        return ParserUtils.parseBodyStream(clazz, inputStream);
                    } finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                        }
                    }
                }

                // request was not successful, get a response error
                InputStream inputStream = connection.getErrorStream();
                if (inputStream != null) {
                    ErrorResult error;
                    try {
                        error = ParserUtils.parseBodyStream(ErrorResult.class, inputStream);
                    } catch (SUSEStudioException e) {
                        // ignore the content of the stream and use the responseCode for the exception.
                        throw new SUSEStudioException(String.valueOf(responseCode), connection.getResponseMessage(), e);
                    } finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                        }
                    }
                    throw new SUSEStudioException(error.getCode(), error.getMessage());
                } else {
                    throw new SUSEStudioException(String.valueOf(responseCode), connection.getResponseMessage());
                }
            } finally {
                try {
                    connection.disconnect();
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
            throw new SUSEStudioException(e);
        }
    }
}
