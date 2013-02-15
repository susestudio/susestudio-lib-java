package com.suse.studio.client.test.httpservermock;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

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
     * Run an HTTP request-response pair on this mock server.
     * 
     * @param requester a method that will produce an HTTP request to this server (wrapped in a Callable object). Can
     *            return a convenience result that will be returned in ExchangeDetails
     * @param responder an object that can respond to the HTTP request
     * @param <T> type of the requester result
     * @return an object that encapsulates the HTTP request as received by this server and the requester result
     * @throws Exception if requester throws an exception
     */
    public <T> ExchangeDetails<T> runExchange(Callable<T> requester, Responder responder) throws Exception {
        ContainerMock containerMock = new ContainerMock(responder);

        Connection connection = null;
        T result = null;
        try {
            connection = new SocketConnection(new ContainerServer(containerMock));

            connection.connect(new InetSocketAddress(PORT));

            result = requester.call();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return new ExchangeDetails<T>(containerMock.getRequest(), result);
    }
}
