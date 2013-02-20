package com.suse.studio.client.test.httpservermock;

import org.simpleframework.http.Response;


/**
 * Convenience responder that always outputs a 501 "Not Implemented" error.
 */
public class NotImplementedResponder implements Responder {

    /**
     * @see com.suse.studio.client.test.httpservermock.Responder#respond(org.simpleframework.http.Response)
     */
    @Override
    public void respond(Response response) {
        response.setCode(505);
    }
}
