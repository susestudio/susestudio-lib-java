package com.suse.studio.client.test.httpservermock;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

import org.simpleframework.http.Request;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

/**
 * Mocks a Web server allowing HTTP client classes to be tested.
 * 
 * @param <T>
 */
public class HttpServerMock {

    /**
     * Port the mock server will listen to.
     */
    public static int PORT = 12345;

    /**
     * Run an HTTP requester on this mock server, record the request and return it.
     * 
     * @param requester a method that will produce an HTTP request to this server (wrapped in a Callable object)
     * @return an object that encapsulates the HTTP request as received by this server
     * @throws Exception if network errors arise
     */
    public Request getRequest(Callable<?> requester) throws Exception {
        return runExchange(requester, new NotImplementedResponder(), true).request;
    }

    /**
     * Run an HTTP requester on this mock server, respond with responder, record the requester's result and return it.
     * 
     * @param requester a method that will produce an HTTP request to this server (wrapped in a Callable object)
     * @param responder an object that can respond to the HTTP request
     * @return the requester's result
     * @throws Exception if requester throws an exception or network errors arise
     */
    public <T> T getResult(Callable<T> requester, Responder responder) throws Exception {
        return runExchange(requester, responder, false).result;
    }

    /**
     * Run an HTTP request-response pair on this mock server.
     * 
     * @param requester a method that will produce an HTTP request to this server (wrapped in a Callable object). Can
     *            return a convenience result that will be returned in ExchangeDetails
     * @param responder an object that can respond to the HTTP request
     * @param <T> type of the requester result
     * @param ignoreRequesterExceptions if true, ignore exceptions thrown by the requester object
     * @return an object that encapsulates the HTTP request as received by this server and the requester result
     * @throws Exception if requester throws an exception or network errors arise
     */
    private <T> ExchangeDetails<T> runExchange(Callable<T> requester,
            Responder responder,
            boolean ignoreRequesterExceptions) throws Exception {
        ContainerMock containerMock = new ContainerMock(responder);

        Connection connection = null;
        ExchangeDetails<T> exchangeDetails = new ExchangeDetails<T>();
        try {
            connection = new SocketConnection(new ContainerServer(containerMock));

            connection.connect(new InetSocketAddress(PORT));

            try {
                exchangeDetails.result = requester.call();
            } catch (Exception e) {
                if (!ignoreRequesterExceptions) {
                    throw e;
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        
        exchangeDetails.request = containerMock.getRequest();

        return exchangeDetails;
    }

    /**
     * Details of a request-response pair.
     */
    class ExchangeDetails<T> {

        /** An HTTP request, as received by the HTTP server. */
        Request request = null;
        
        /** A generic result produced by an HTTP request. */
        T result = null;
    }
}
