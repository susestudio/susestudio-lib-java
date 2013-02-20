package com.suse.studio.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;

import com.suse.studio.client.SUSEStudio;
import com.suse.studio.client.SUSEStudio.ImageType;
import com.suse.studio.client.exception.SUSEStudioException;
import com.suse.studio.client.model.Appliance;
import com.suse.studio.client.model.Gallery;
import com.suse.studio.client.model.ScheduleBuildResult;
import com.suse.studio.client.model.Status;
import com.suse.studio.client.model.TemplateSet;
import com.suse.studio.client.model.Testdrive;
import com.suse.studio.client.model.User;
import com.suse.studio.client.model.configuration.Configuration;
import com.suse.studio.client.test.httpservermock.HttpServerMock;
import com.suse.studio.client.test.util.SUSEStudioRequester;
import com.suse.studio.client.test.util.TestUtils;

/**
 * Test that appropriate requests are sent to SUSE Studio server.
 */
public class RequestTest {

    @Test
    public void testGetUser() throws Exception {
        SUSEStudioRequester<User> requester = new SUSEStudioRequester<User>() {
            public User request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getUser();
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals("/api/v2/user/account", request.getPath().toString());
    }
    
    @Test
    public void testGetApiVersion() throws Exception {
        SUSEStudioRequester<String> requester = new SUSEStudioRequester<String>() {
            public String request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getApiVersion();
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals("/api/v2/user/api_version", request.getPath().toString());
    }

    @Test
    public void testGetAppliances() throws Exception {
        SUSEStudioRequester<List<Appliance>> requester = new SUSEStudioRequester<List<Appliance>>() {
            public List<Appliance> request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getAppliances();
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals("/api/v2/user/appliances", request.getPath().toString());
    }

    @Test
    public void testGetAppliance() throws Exception {
        SUSEStudioRequester<Appliance> requester = new SUSEStudioRequester<Appliance>() {
            public Appliance request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getAppliance(0);
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals("/api/v2/user/appliances/0", request.getPath().toString());
    }

    @Test
    public void testGetApplianceStatus() throws Exception {
        SUSEStudioRequester<Status> requester = new SUSEStudioRequester<Status>() {
            public Status request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getApplianceStatus(0);
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals("/api/v2/user/appliances/0/status", request.getPath().toString());
    }

    @Test
    public void testCloneApplianceFrom() throws Exception {
        SUSEStudioRequester<Appliance> requester = new SUSEStudioRequester<Appliance>() {
            public Appliance request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.cloneApplianceFrom(1, "one", "i586");
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("POST", request.getMethod());
        assertEquals("/api/v2/user/appliances", request.getPath().toString());

        Query query = request.getQuery();
        assertEquals("1", query.get("clone_from"));
        assertEquals("one", query.get("name"));
        assertEquals("i586", query.get("arch"));
    }

    @Test
    public void testDeleteAppliance() throws Exception {
        SUSEStudioRequester<Boolean> requester = new SUSEStudioRequester<Boolean>() {
            @Test
            public Boolean request(SUSEStudio suseStudio) throws SUSEStudioException {
                suseStudio.deleteAppliance(0);
                return true;
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("DELETE", request.getMethod());
        assertEquals("/api/v2/user/appliances/0", request.getPath().toString());
    }

    @Test
    public void testGetGallery() throws Exception {
        SUSEStudioRequester<Gallery> requester = new SUSEStudioRequester<Gallery>() {
            public Gallery request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getGallery("latest");
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals("/api/v2/user/gallery/appliances", request.getPath().toString());

        Query query = request.getQuery();
        assertEquals("", query.get("latest"));
    }

    @Test
    public void testSearchGallery() throws Exception {
        SUSEStudioRequester<Gallery> requester = new SUSEStudioRequester<Gallery>() {
            public Gallery request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.searchGallery("dadada");
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals("/api/v2/user/gallery/appliances", request.getPath().toString());

        Query query = request.getQuery();
        assertEquals("dadada", query.get("search"));
    }

    @Test
    public void testGetConfiguration() throws Exception {
        SUSEStudioRequester<Configuration> requester = new SUSEStudioRequester<Configuration>() {
            public Configuration request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getConfiguration(0);
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals("/api/v2/user/appliances/0/configuration", request.getPath().toString());
    }

    @Test
    public void testSetConfiguration() throws Exception {
        final Configuration configuration = TestUtils.parseExampleFile(Configuration.class, "configuration.xml");

        SUSEStudioRequester<Boolean> requester = new SUSEStudioRequester<Boolean>() {
            @Test
            public Boolean request(SUSEStudio suseStudio) throws SUSEStudioException {
                suseStudio.setConfiguration(0, configuration);
                return true;
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("PUT", request.getMethod());
        assertEquals("/api/v2/user/appliances/0/configuration", request.getPath().toString());
        assertEquals(TestUtils.persistInString(configuration), request.getContent());
    }

    @Test
    public void testGetTemplateSet() throws Exception {
        SUSEStudioRequester<TemplateSet> requester = new SUSEStudioRequester<TemplateSet>() {
            public TemplateSet request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getTemplateSet("default");
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals("/api/v2/user/template_sets/default", request.getPath().toString());
    }

    @Test
    public void testGetTemplateSets() throws Exception {
        SUSEStudioRequester<List<TemplateSet>> requester = new SUSEStudioRequester<List<TemplateSet>>() {
            public List<TemplateSet> request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getTemplateSets();
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals("/api/v2/user/template_sets", request.getPath().toString());
    }

    @Test
    public void testGetTestdrives() throws Exception {
        SUSEStudioRequester<List<Testdrive>> requester = new SUSEStudioRequester<List<Testdrive>>() {
            public List<Testdrive> request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getTestdrives();
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("GET", request.getMethod());
        assertEquals("/api/v2/user/testdrives", request.getPath().toString());
    }

    @Test
    public void testStartTestdrive() throws Exception {
        SUSEStudioRequester<Testdrive> requester = new SUSEStudioRequester<Testdrive>() {
            public Testdrive request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.startTestdrive(0);
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("POST", request.getMethod());
        assertEquals("/api/v2/user/testdrives", request.getPath().toString());

        Query query = request.getQuery();
        assertEquals("0", query.get("build_id"));
    }

    @Test
    public void testScheduleBuild() throws Exception {
        SUSEStudioRequester<ScheduleBuildResult> requester = new SUSEStudioRequester<ScheduleBuildResult>() {
            public ScheduleBuildResult request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.scheduleBuild(0, ImageType.iso);
            }
        };
        Request request = new HttpServerMock().getRequest(requester);

        assertNotNull(request);
        assertEquals("POST", request.getMethod());
        assertEquals("/api/v2/user/running_builds", request.getPath().toString());

        Query query = request.getQuery();
        assertEquals("0", query.get("appliance_id"));
        assertEquals("true", query.get("force"));
        assertEquals("iso", query.get("image_type"));
    }
}
