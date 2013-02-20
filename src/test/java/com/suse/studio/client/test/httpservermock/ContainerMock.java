package com.suse.studio.client.test.httpservermock;

import java.io.IOException;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;

//I wish I had... Java with closures

/**
 * A Simple framework container that captures a request and runs a Responder.
 */
class ContainerMock implements Container
{    
    /** The responder. */
    private Responder responder;
    
    /** A captured request, as received by ContainerServer. */
    private Request request;

    /**
     * Instantiate a new container mock.
     *
     * @param responder the responder
     */
    public ContainerMock(Responder responder) {
        this.responder = responder;
        this.request = null;
    }
    
    /**
     * Get the request.
     *
     * @return the request
     */
    public Request getRequest() {
        return request;
    }

    /**
     * @see org.simpleframework.http.core.Container#handle(org.simpleframework.http.Request, org.simpleframework.http.Response)
     */
    @Override
    public void handle(Request request, Response response) {
        try {
            this.request = request;
            responder.respond(response);
            response.close();
        } catch (IOException e) {
            // Simple does not allow throwing exceptions here
            e.printStackTrace();
        }
    }
}