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

package com.suse.studio.client.test.util;

import java.util.concurrent.Callable;

import com.suse.studio.client.SUSEStudio;
import com.suse.studio.client.exception.SUSEStudioException;
import com.suse.studio.client.test.httpservermock.HttpServerMock;

/**
 * Convenience requester that is specific to make SUSEStudio requests.
 *
 * @param <T> a generic result type
 */
public abstract class SUSEStudioRequester<T> implements Callable<T> {

    /** A dummy username. */
    private static String DUMMY_USER = "dummy";
    
    /** A dummy api key. */
    private static String DUMMY_API_KEY = "3735928559";


    /**
     * Run a request.
     * 
     * @see java.util.concurrent.Callable#call()
     */
    public T call() {
        T ret = null;
        try {
            ret = request(new SUSEStudio(DUMMY_USER, DUMMY_API_KEY, "http://localhost:" + HttpServerMock.PORT));
        } catch (SUSEStudioException e) {
            // Catch it in here, we are expecting it
        }
        return ret;
    }

    /**
     * Run a request to SUSE Studio.
     *
     * @param suseStudio the SUSE studio object to make the request
     * @return a generic request result
     * @throws SUSEStudioException if the request has an error
     */
    public abstract T request(SUSEStudio suseStudio) throws SUSEStudioException;
}
