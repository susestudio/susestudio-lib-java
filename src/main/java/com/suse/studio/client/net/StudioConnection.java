package com.suse.studio.client.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.suse.studio.client.util.ParserUtils;
import com.suse.studio.client.util.StudioConfig;

public class StudioConnection {

    private String uri = null;
    private String encodedCredentials = null;
    
    private static final CacheManager  cacheManager  = new CacheManager("src/main/resources/ehcache.xml");

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
    	Element elem = getCache().get(uri);
    	if(elem!=null){
    		return (T)(elem.getObjectValue());
    	}
        HttpURLConnection connection = RequestFactory.getInstance().get(uri,
                encodedCredentials);
        connection.connect();
        InputStream responseBodyStream = connection.getInputStream();
//        byte[] bt=new byte[1];
//        while(responseBodyStream.read(bt)>0){
//        	System.out.write(bt);
//        }
        T result = ParserUtils.parseBodyStream(class1, responseBodyStream);
        connection.disconnect();
        getCache().put(new Element(uri, result));
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
        getCache().replace(new Element(uri, result));
        return result;
    }
    
    private Ehcache getCache() {
    	Ehcache ec=cacheManager.getEhcache("responses");
		return ec;
	}
}
