package com.suse.studio.client.test.httpservermock;

import org.simpleframework.http.Response;

/**
 * Represents objects that can respond to an HTTP request.
 */
public interface Responder {
    /**
     * Respond to an HTTP request through a response object.
     * 
     * @param response a response object
     */
    public void respond(Response response);
}
