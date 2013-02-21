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
