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
import com.suse.studio.client.model.Configuration;
import com.suse.studio.client.model.Gallery;
import com.suse.studio.client.model.ScheduleBuildResult;
import com.suse.studio.client.model.Status;
import com.suse.studio.client.model.SuccessResult;
import com.suse.studio.client.model.TemplateSet;
import com.suse.studio.client.model.TemplateSets;
import com.suse.studio.client.model.Testdrive;
import com.suse.studio.client.model.Testdrives;
import com.suse.studio.client.model.User;
import com.suse.studio.client.model.Version;
import com.suse.studio.client.net.StudioConnection;
import com.suse.studio.client.net.StudioException;
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

    // Supported image types
    public static enum ImageType {
        ec2, iso, net, oem, oemiso, vmx, xen
    }

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
        this.config = StudioConfig.getInstance();
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
     * @throws StudioException if Suse Studio returns an error response
     */
    public User getUser() throws IOException, StudioException {
        StudioConnection sc = new StudioConnection("/user/account", config);
        User result = sc.get(User.class);
        return result;
    }

    /**
     * Return the running API version including the minor version.
     *
     * GET /api/v2/user/api_version
     *
     * @return API version including minor version
     * @throws IOException
     * @throws StudioException if Suse Studio returns an error response
     */
    public String getApiVersion() throws IOException, StudioException {
        StudioConnection sc = new StudioConnection("/user/api_version", config);
        Version version = sc.get(Version.class);
        return version.getValue();
    }

    /**
     * Get all appliances of the current user.
     *
     * GET /api/v2/user/appliances
     *
     * @return list of the current user's appliances
     * @throws IOException
     * @throws StudioException if Suse Studio returns an error response
     */
    public List<Appliance> getAppliances() throws IOException, StudioException {
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
     * @throws StudioException if Suse Studio returns an error response
     */
    public Appliance getAppliance(long id) throws IOException, StudioException {
        StringBuilder uri = new StringBuilder("/user/appliances/");
        uri.append(id);
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        Appliance result = sc.get(Appliance.class);
        return result;
    }

    /**
     * Get information about the status of an appliance given by id.
     *
     * GET /api/v2/user/appliances/<id>/status
     *
     * @return status of appliance with given id
     * @throws IOException
     * @throws StudioException if Suse Studio returns an error response
     */
    public Status getApplianceStatus(long id) throws IOException, StudioException {
        StringBuilder uri = new StringBuilder("/user/appliances/");
        uri.append(id);
        uri.append("/status");
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        Status status = sc.get(Status.class);
        return status;
    }
    
    /**
     * Clones an existing appliance in a new appliance
     *
     * POST /api/v2/user/appliances?clone_from=<appliance_id>&name=<name>&arch=<arch>
     *
     * @param id original appliance identifier
     * @param name new appliance name or null for automatic generation
     * @param arch new appliance architecture ("i686" or "x86_64") or null for "i686"
     * @return status of appliance with given id
     * @throws IOException
     * @throws StudioException if Suse Studio returns an error response
     */
    public Appliance cloneApplianceFrom(long id, String name, String arch) throws IOException, StudioException {
        StringBuilder uri = new StringBuilder("/user/appliances?clone_from=");
        uri.append(id);
        if (name != null) {
            uri.append("&name=");
            uri.append(name);
        }
        if (arch != null) {
            uri.append("&arch=");
            uri.append(arch);
        }
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        Appliance appliance = sc.post(Appliance.class);
        return appliance;
    }
    
    /**
     * Deletes an appliance.
     *
     * DELETE /api/v2/user/appliances/<id>
     *
     * @param id
     * @throws IOException
     * @throws StudioException if Suse Studio returns an error response
     */
    public void deleteAppliance(long id) throws IOException, StudioException {
        StringBuilder uri = new StringBuilder("/user/appliances/");
        uri.append(id);
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        sc.delete(SuccessResult.class);
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
     * @throws StudioException if Suse Studio returns an error response
     */
    public Gallery getGallery(String queryType) throws IOException, StudioException {
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
     * @throws StudioException if Suse Studio returns an error response
     */
    public Gallery searchGallery(String searchquery) throws IOException, StudioException {
        StringBuilder uri = new StringBuilder("/user/gallery/appliances?search=");
        uri.append(searchquery);
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        Gallery gallery = sc.get(Gallery.class);
        return gallery;
    }

    /**
     * Return the configuration of appliance with given id.
     *
     * GET /api/v2/user/appliances/<id>/configuration
     *
     * @param id
     * @return configuration
     * @throws IOException
     * @throws StudioException if Suse Studio returns an error response
     */
    public Configuration getConfiguration(long id) throws IOException, StudioException {
        StringBuilder uri = new StringBuilder("/user/appliances/");
        uri.append(id);
        uri.append("/configuration");
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        Configuration configuration = sc.get(Configuration.class);
        return configuration;
    }

    /**
     * Return the template set with given id.
     *
     * GET /api/v2/user/template_sets/<name>
     *
     * @param name
     * @return list of template sets
     * @throws IOException
     * @throws StudioException if Suse Studio returns an error response
     */
    public TemplateSet getTemplateSet(String name) throws IOException, StudioException {
        StringBuilder uri = new StringBuilder("/user/template_sets/");
        uri.append(name);
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        TemplateSet templateSet = sc.get(TemplateSet.class);
        return templateSet;
    }
    
    /**
     * List all template sets.
     *
     * GET /api/v2/user/template_sets
     *
     * @return list of template sets
     * @throws IOException
     * @throws StudioException if Suse Studio returns an error response
     */
    public List<TemplateSet> getTemplateSets() throws IOException, StudioException {
        StringBuilder uri = new StringBuilder("/user/template_sets");
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        TemplateSets templateSets = sc.get(TemplateSets.class);
        return templateSets.getTemplateSets();
    }

    /**
     * Return a list of running testdrives.
     *
     * GET /api/v2/user/testdrives
     *
     * @return list of running testdrives
     * @throws IOException
     * @throws StudioException if Suse Studio returns an error response
     */
    public List<Testdrive> getTestdrives() throws IOException, StudioException {
        StudioConnection sc = new StudioConnection("/user/testdrives", config);
        Testdrives testdrives = sc.get(Testdrives.class);
        return testdrives.getTestdrives();
    }

    /**
     * Start a new testdrive session of the given build. Note that testdrive sessions will be
     * aborted if no client has connected after 60 seconds.
     *
     * POST /api/v2/user/testdrives?build_id=<build_id>
     *
     * @param buildId
     * @return result object
     * @throws IOException
     * @throws StudioException if Suse Studio returns an error response
     */
    public Testdrive startTestdrive(long buildId) throws IOException, StudioException {
        StringBuilder uri = new StringBuilder("/user/testdrives?build_id=");
        uri.append(buildId);
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        return sc.post(Testdrive.class);
    }

    /**
     * Schedule a new build for appliance with a given ID and image type.
     *
     * POST user/running_builds?appliance_id=<applianceID>&force=<force>&image_type=<imgType>
     * @param applianceID
     * @param imgType
     * @return result object
     * @throws IOException
     * @throws StudioException if Suse Studio returns an error response
     */
    public ScheduleBuildResult scheduleBuild(long applianceID, ImageType imgType)
            throws IOException, StudioException {
        StringBuilder uri = new StringBuilder("/user/running_builds?appliance_id=");
        uri.append(applianceID);
        uri.append("&force=");
        uri.append("true");
        uri.append("&image_type=");
        uri.append(imgType);
        StudioConnection sc = new StudioConnection(uri.toString(), config);
        return sc.post(ScheduleBuildResult.class);
    }
}
