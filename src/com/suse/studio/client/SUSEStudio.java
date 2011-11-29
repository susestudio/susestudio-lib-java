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
import com.suse.studio.util.Prefs;

/**
 * Library class for the SUSE Studio REST API.
 */
public class SUSEStudio {

    // The encoded credentials
    private final String credentials;

    /**
     * Create a client object by providing user and API key. This client will
     * talk to the public SUSE Studio default URL.
     *
     * @param user
     * @param apiKey
     */
    public SUSEStudio(String user, String apiKey) {
        if (user == null || apiKey == null) {
            throw new RuntimeException("We need the user and API key!");
        }
        // Encode the credentials
        this.credentials = Base64.encodeBytes((user + ":" + apiKey).getBytes());
    }

    /**
     * Create a client by providing user, API key and a base URL of the API.
     *
     * @param user
     * @param apiKey
     * @param url
     */
    public SUSEStudio(String user, String apiKey, String url) {
        this(user, apiKey);
        Prefs.put(Prefs.BASE_URL, url);
    }

    /**
     * Get information about the user associated with this client.
     *
     * GET /api/v2/user/account
     *
     * @return current user
     * @throws IOException
     */
    public User getUser() throws IOException {
        StudioConnection sc = new StudioConnection("/user/account", credentials);
        User result = sc.get(User.class);
        return result;
    }

    /**
     * Get all appliances of the current user.
     *
     * GET /api/v2/user/appliances
     *
     * @return list of the current user's appliances
     * @throws IOException
     */
    public List<Appliance> getAppliances() throws IOException {
        StudioConnection sc = new StudioConnection("/user/appliances", credentials);
        Appliances result = sc.get(Appliances.class);
        return result.getAppliances();
    }
}
