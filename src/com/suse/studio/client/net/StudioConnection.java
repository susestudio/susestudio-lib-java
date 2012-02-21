package com.suse.studio.client.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import com.suse.studio.util.ParserUtils;
import com.suse.studio.util.StudioConfig;

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
     * @param class1
     * @return instance of class1
     * @throws IOException
     */
    public <T> T get(Class<T> class1) throws IOException {
        HttpURLConnection connection = RequestFactory.getInstance().get(uri,
                encodedCredentials);
        connection.connect();
        InputStream responseBodyStream = connection.getInputStream();
        T result = ParserUtils.parseBodyStream(class1, responseBodyStream);
        connection.disconnect();
        return result;
    }

    /**
     * FIXME as soon as it's needed.
     *
     * @param class1
     * @return instance of class1
     * @throws IOException
     */
    public <T> T post(Class<T> class1) throws IOException {
        HttpURLConnection connection = RequestFactory.getInstance().post(uri,
                encodedCredentials);
        connection.connect();
        InputStream responseBodyStream = connection.getInputStream();
        T result = ParserUtils.parseBodyStream(class1, responseBodyStream);
        connection.disconnect();
        return result;
    }
}
