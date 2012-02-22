/*
 * Copyright (c) 2012 Novell
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

import com.suse.studio.client.model.Appliance;
import com.suse.studio.client.model.Appliances;
import com.suse.studio.client.model.Gallery;
import com.suse.studio.client.model.User;
import com.suse.studio.client.net.StudioConnection;
import com.suse.studio.client.util.Base64;
import com.suse.studio.client.util.StudioConfig;

/**
 * Library class for the SUSE Studio REST API.
 */
public class SUSEStudio {

    // Generic suffix for the v2 API
    private final String URL_API_SUFFIX = "/api/v2";

    // Currently available query types for gallery
    public static final String GALLERY_LATEST = "latest";
    public static final String GALLERY_POPULAR = "popular";
    public static final String GALLERY_USERNAME = "username";

    // Every instance needs a config object
    private StudioConfig config;

    /**
     * Create a client object by providing user and API key. This client will
     * talk to the public SUSE Studio default URL (http://susestudio.com).
     *
     * @param user
     * @param apiKey
     */
    public SUSEStudio(String user, String apiKey) {
        if (user == null || apiKey == null) {
            throw new RuntimeException("We need the user and API key!");
        }
        // Create the configuration
        this.config = new StudioConfig();
        // Encode the credentials
        String credentials = Base64.encodeBytes((user + ":" + apiKey).getBytes());
        this.config.put(StudioConfig.KEY_ENCODED_CREDS, credentials);
    }

    /**
     * Create a client by providing user, API key and SUSE Studio base URL.
     * Passing <code>null</code> as the url is supported, we will fall back to
     * the public SUSE Studio default URL (http://susestudio.com) in this case.
     *
     * @param user
     * @param apiKey
     * @param url
     */
    public SUSEStudio(String user, String apiKey, String url) {
        this(user, apiKey);
        if (url != null) {
            // Remove trailing slashes
            while (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }
            config.put(StudioConfig.KEY_BASE_URL, url + URL_API_SUFFIX);
        }
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
        StudioConnection sc = new StudioConnection("/user/account", config);
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
        StudioConnection sc = new StudioConnection("/user/appliances", config);
        Appliances result = sc.get(Appliances.class);
        return result.getAppliances();
    }

    /**
     * Get the details of an appliance given by id.
     *
     * GET /api/v2/user/appliances/<id>
     *
     * @return details of appliance with given id
     * @throws IOException
     */
    public Appliance getAppliance(long id) throws IOException {
        StringBuilder uri = new StringBuilder("/user/appliances/");
        uri.append(id);
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        Appliance result = sc.get(Appliance.class);
        return result;
    }

    /**
     * Query appliances from SUSE Gallery (latest|popular|username).
     *
     * GET /api/v2/user/gallery/appliances
     *
     * @param queryType
     *            the type of the query, choose from constants
     * @return list of appliances queried from gallery
     * @throws IOException
     */
    public Gallery getGallery(String queryType) throws IOException {
        StringBuilder uri = new StringBuilder("/user/gallery/appliances?");
        uri.append(queryType);
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        Gallery gallery = sc.get(Gallery.class);
        return gallery;
    }

    /**
     * Search for appliances within SUSE Gallery.
     *
     * GET /api/v2/user/gallery/appliances
     *
     * @param searchquery
     *            query string
     * @return list of appliances queried from gallery
     * @throws IOException
     */
    public Gallery searchGallery(String searchquery) throws IOException {
        StringBuilder uri = new StringBuilder("/user/gallery/appliances?search=");
        uri.append(searchquery);
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        Gallery gallery = sc.get(Gallery.class);
        return gallery;
    }
}
