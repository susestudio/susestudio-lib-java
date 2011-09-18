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
import java.util.List;

import com.suse.studio.client.data.Appliance;
import com.suse.studio.client.data.Appliances;
import com.suse.studio.client.data.User;
import com.suse.studio.client.net.StudioConnection;
import com.suse.studio.util.Base64;

/**
 * Library class for the SUSE Studio REST API.
 * TODO: Implement the REST of the API.
 * 
 * @author Johannes Renner
 */
public class SUSEStudioClient {

    private final String user;
    private final String apiKey;

    public static final String baseURL = "http://susestudio.com/api/v2";

    public SUSEStudioClient(String user, String apiKey) {
        if (user == null || apiKey == null) {
            throw new RuntimeException("We need the user and API key!");
        }
    	this.user = user;
        this.apiKey = apiKey;
    }
    
    /**
     * GET /api/v2/user/account
     * @return current user
     * @throws IOException
     */
    public User getUser() throws IOException {
    	StudioConnection sc = new StudioConnection("/user/account", getEncodedCredentials());
        User result = sc.get(User.class);
        return result;
    }

    /**
     * Get all appliances of the current user.
     * GET /api/v2/user/appliances
     * 
     * @return list of the current user's appliances
     * @throws IOException 
     */
    public List<Appliance> getAppliances() throws IOException {
    	StudioConnection sc = new StudioConnection("/user/appliances", getEncodedCredentials());
        Appliances result = sc.get(Appliances.class);
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
