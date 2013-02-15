package com.suse.studio.client.test.httpservermock;

import org.simpleframework.http.Request;

/**
 * Encapsulates details of an HTTP request-response pair.
 *
 * @param <T> generic request result type @see {@link HttpServerMock}
 */
public class ExchangeDetails<T> {

    /** The HTTP request, as received by the HTTP server. */
    private Request receivedRequest;
    
    /** A generic result produced by an HTTP request. */
    private T result;

    /**
     * Standard constructor.
     *
     * @param request an HTTP request
     * @param result a generic result of an HTTP request
     */
    public ExchangeDetails(Request request, T result) {
        super();
        this.receivedRequest = request;
        this.result = result;
    }

    /**
     * Get the received request.
     *
     * @return the received request
     */
    public Request getReceivedRequest() {
        return receivedRequest;
    }

    /**
     * Get the result.
     *
     * @return the result
     */
    public T getResult() {
        return result;
    }
}