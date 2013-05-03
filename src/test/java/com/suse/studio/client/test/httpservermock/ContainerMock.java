/**
 * Copyright (c) 2011-2013 SUSE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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