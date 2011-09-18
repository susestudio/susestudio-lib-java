/*
 * Copyright (c) 2011 Novell Inc.
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

package com.suse.studio.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.suse.studio.client.data.Appliance;
import com.suse.studio.client.data.Appliances;
import com.suse.studio.util.Base64;

/**
 * Library class for the SUSE Studio REST API.
 * TODO: Implement the REST of the API.
 * 
 * @author Johannes Renner
 */
public class SUSEStudioClient {

    /* The credentials */
    private final String user;
    private final String apiKey;

    // The base URL for accessing the API
    private String baseURL = "http://susestudio.com/api/v2";
    
    /**
     * Constructor
     */
    public SUSEStudioClient(String user, String apiKey) {
        if (user == null || apiKey == null) {
            throw new RuntimeException("We need the user and API key!");
        }
    	this.user = user;
        this.apiKey = apiKey;
    }

    /**
     * List all appliances of the current user.
     * GET /api/v2/user/appliances
     * 
     * @return list of the current user's appliances
     */
    public List<Appliance> getAppliances() throws IOException {
        // Init the URL
        URL url;
        try {
            url = new URL(baseURL + "/user/appliances");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        // Init the connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Write auth header
        connection.setRequestProperty("Authorization", "BASIC "
                + getEncodedCredentials());

        // Do the request
        connection.connect();
        InputStream responseBodyStream = connection.getInputStream();

        // Parse the resulting XML
        Appliances result = null;

        Serializer serializer = new Persister();
        try {
			result = serializer.read(Appliances.class, responseBodyStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        connection.disconnect();

        return result.getAppliances();
    }

    /**
     * Return the encoded credentials. 
     * 
     * @return encoded credentials as {@link String}
     */
    private String getEncodedCredentials() {
        return Base64.encodeBytes((user + ":" + apiKey).getBytes());
    }
}
