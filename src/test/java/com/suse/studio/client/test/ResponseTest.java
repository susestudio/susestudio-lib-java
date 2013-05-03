/**
 * Copyright (c) 2011-2013 SUSE
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

package com.suse.studio.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.suse.studio.client.SUSEStudio;
import com.suse.studio.client.SUSEStudio.ImageType;
import com.suse.studio.client.exception.SUSEStudioException;
import com.suse.studio.client.model.Appliance;
import com.suse.studio.client.model.Build;
import com.suse.studio.client.model.DiskQuota;
import com.suse.studio.client.model.Gallery;
import com.suse.studio.client.model.GalleryAppliance;
import com.suse.studio.client.model.GalleryAppliances;
import com.suse.studio.client.model.Issue;
import com.suse.studio.client.model.Match;
import com.suse.studio.client.model.Parent;
import com.suse.studio.client.model.Repository;
import com.suse.studio.client.model.ScheduleBuildResult;
import com.suse.studio.client.model.Solution;
import com.suse.studio.client.model.Status;
import com.suse.studio.client.model.Template;
import com.suse.studio.client.model.TemplateSet;
import com.suse.studio.client.model.Testdrive;
import com.suse.studio.client.model.User;
import com.suse.studio.client.model.VNCServer;
import com.suse.studio.client.model.configuration.ApplianceUser;
import com.suse.studio.client.model.configuration.Autostart;
import com.suse.studio.client.model.configuration.Configuration;
import com.suse.studio.client.model.configuration.Database;
import com.suse.studio.client.model.configuration.DatabaseUser;
import com.suse.studio.client.model.configuration.Firewall;
import com.suse.studio.client.model.configuration.LVM;
import com.suse.studio.client.model.configuration.Locale;
import com.suse.studio.client.model.configuration.Network;
import com.suse.studio.client.model.configuration.Script;
import com.suse.studio.client.model.configuration.Scripts;
import com.suse.studio.client.model.configuration.Settings;
import com.suse.studio.client.model.configuration.Volume;
import com.suse.studio.client.test.httpservermock.HttpServerMock;
import com.suse.studio.client.test.util.SUSEStudioRequester;
import com.suse.studio.client.test.util.TestExampleResponder;
import com.suse.studio.client.test.util.TestUtils;

/**
 * Test that responses are appropriately parsed.
 */
public class ResponseTest {

    // Scripts included in configuration.xml
    private static final String EXPECTED_SCRIPT_1 = "#!/bin/bash -e\n      #\n      # This script is executed at the end of appliance creation.  Here you can do\n      # one-time actions to modify your appliance before it is ever used, like\n      # removing files and directories to make it smaller, creating symlinks,\n      # generating indexes, etc.\n      #\n      # The 'kiwi_type' variable will contain the format of the appliance (oem =\n      # disk image, vmx = VMware, iso = CD/DVD, xen = Xen).\n      #\n      \n      # read in some variables\n      . /studio/profile\n      \n      #======================================\n      # Prune extraneous files\n      #--------------------------------------\n      # Remove all documentation\n      docfiles=`find /usr/share/doc/packages -type f |grep -iv \"copying\\|license\\|copyright\"`\n      rm -f $docfiles\n      rm -rf /usr/share/info\n      rm -rf /usr/share/man\n      \n      # fix the setlocale error\n      sed -i 's/en_US.UTF-8/POSIX/g' /etc/sysconfig/language\n      \n      exit 0";
    private static final String EXPECTED_SCRIPT_2 = "#!/bin/bash\n      #\n      # This script is executed whenever your appliance boots.  Here you can add\n      # commands to be executed before the system enters the first runlevel.  This\n      # could include loading kernel modules, starting daemons that aren't managed\n      # by init files, asking questions at the console, etc.\n      #\n      # The 'kiwi_type' variable will contain the format of the appliance (oem =\n      # disk image, vmx = VMware, iso = CD/DVD, xen = Xen).\n      #\n      \n      # read in some variables\n      . /studio/profile\n      \n      if [ -f /etc/init.d/suse_studio_firstboot ]\n      then\n        \n        # Put commands to be run on the first boot of your appliance here\n        echo \"Running SUSE Studio first boot script...\"\n      \n      fi";

    @Test
    public void testGetUser() throws Exception {
        SUSEStudioRequester<User> requester = new SUSEStudioRequester<User>() {
            public User request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getUser();
            }
        };
        TestExampleResponder responder = new TestExampleResponder("account.xml");
        User result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
        assertEquals("uexample", result.getUsername());
        assertEquals("User Example", result.getDisplayName());
        assertEquals("user@example.com", result.getEmail());
        Date created = TestUtils.getDate(2009, 4, 14, 14, 51, 7);
        assertEquals(0, created.compareTo(result.getCreatedAt()));
        assertNotNull(result.getDiskQuota());
        DiskQuota diskQuota = result.getDiskQuota();
        assertEquals("15GB", diskQuota.getAvailable());
        assertEquals("4%", diskQuota.getUsed());
        List<String> openIdUrls = result.getOpenIdUrls();
        assertNotNull(openIdUrls);
        assertEquals(1, openIdUrls.size());
        String openIdUrl = openIdUrls.get(0);
        assertEquals("http://user.example.com/", openIdUrl);
    }

    @Test
    public void testGetApiVersion() throws Exception {
        SUSEStudioRequester<String> requester = new SUSEStudioRequester<String>() {
            public String request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getApiVersion();
            }
        };
        TestExampleResponder responder = new TestExampleResponder("api_version.xml");
        String result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
        assertEquals("1.0", result);
    }

    @Test
    public void testGetAppliances() throws Exception {
        SUSEStudioRequester<List<Appliance>> requester = new SUSEStudioRequester<List<Appliance>>() {
            public List<Appliance> request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getAppliances();
            }
        };
        TestExampleResponder responder = new TestExampleResponder("appliances.xml");
        List<Appliance> result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAppliance() throws Exception {
        SUSEStudioRequester<Appliance> requester = new SUSEStudioRequester<Appliance>() {
            public Appliance request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getAppliance(0);
            }
        };
        TestExampleResponder responder = new TestExampleResponder("appliance.xml");
        Appliance result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
        assertEquals(24, result.getId());
        assertEquals("Cornelius' JeOS", result.getName());
        assertNull(result.getArch());
        assertNull(result.getType());
        Date lastEdited = TestUtils.getDate(2009, 3, 24, 12, 9, 42);
        assertEquals(lastEdited, result.getLastEdited());
        assertEquals(result.getEstimatedRawSize(), "560 MB");
        assertEquals(result.getEstimatedCompressedSize(), "160 MB");
        assertEquals("http://susestudio.com/appliance/edit/24", result.getEditUrl());
        assertEquals("http://susestudio.com/api/v1/user/appliance_icon/1234", result.getIconUrl());
        assertEquals("11.1", result.getBasesystem());
        assertNull(result.getUuid());
        Parent parent = result.getParent();
        assertEquals(1, parent.getId());
        assertEquals("openSUSE 11.1, Just enough OS (JeOS)", parent.getName());
        List<Build> builds = result.getBuilds();
        assertNotNull(builds);
        assertEquals(1, builds.size());
        Build build = builds.get(0);
        assertEquals(28, build.getId());
        assertEquals("0.0.1", build.getVersion());
        assertEquals("oem", build.getImageType());
        assertEquals(238, build.getImageSize());
        assertEquals(87, build.getCompressedImageSize());
        assertEquals(
                "http://susestudio.com/download/bf1a0f08884ebac13f30b0fc62dfc44a/Cornelius_JeOS.x86_64-0.0.1.oem.tar.gz",
                build.getDownloadUrl());
    }

    @Test
    public void testGetApplianceStatus() throws Exception {
        SUSEStudioRequester<Status> requester = new SUSEStudioRequester<Status>() {
            public Status request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getApplianceStatus(0);
            }
        };
        TestExampleResponder responder = new TestExampleResponder("appliance_status.xml");
        Status result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
        assertEquals("error", result.getState());
        List<Issue> issues = result.getIssues();
        Issue issue = issues.get(0);
        assertEquals("error", issue.getType());
        assertEquals("You must include a kernel package in your appliance.", issue.getText());
        Solution solution = issue.getSolution();
        assertEquals("install", solution.getType());
        assertEquals("kernel-default", solution.getPkg());
    }

    @Test
    public void testCloneApplianceFrom() throws Exception {
        SUSEStudioRequester<Appliance> requester = new SUSEStudioRequester<Appliance>() {
            public Appliance request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.cloneApplianceFrom(1, "one", "i586");
            }
        };
        TestExampleResponder responder = new TestExampleResponder("appliance.xml");
        Appliance result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
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
        TestExampleResponder responder = new TestExampleResponder("success.xml");
        Boolean result = new HttpServerMock().getResult(requester, responder);

        assertTrue(result);
    }

    @Test
    public void testGetGallery() throws Exception {
        SUSEStudioRequester<Gallery> requester = new SUSEStudioRequester<Gallery>() {
            public Gallery request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getGallery("latest");
            }
        };
        TestExampleResponder responder = new TestExampleResponder("gallery.xml");
        Gallery result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
        GalleryAppliances galleryAppliances = result.getAppliances();
        assertNotNull(galleryAppliances);
        assertEquals(7999, galleryAppliances.getCount());
        assertEquals(800, galleryAppliances.getPages());
        assertEquals(1, galleryAppliances.getCurrentPage());
        List<GalleryAppliance> list = galleryAppliances.getAppliances();
        assertNotNull(list);
        assertEquals(10, list.size());
        GalleryAppliance galleryAppliance = list.get(0);
        assertEquals(217901, galleryAppliance.getId());
        assertEquals("ownCloud in a box", galleryAppliance.getName());
        assertEquals("Cornelius Schumacher", galleryAppliance.getPublisher());
        assertEquals("cschumacher", galleryAppliance.getUsername());
        assertEquals("http://owncloud.org", galleryAppliance.getHomepage());
        assertEquals("OwnCloud Description", galleryAppliance.getDescription());
        assertEquals(53, galleryAppliance.getRatings());
        assertEquals(4.13207547169811, galleryAppliance.getAverageRating(), 0.001);
        assertEquals(183, galleryAppliance.getComments());
        assertEquals("12.2", galleryAppliance.getBasedOn());
        Date date = TestUtils.getDate(2013, 0, 28, 14, 35, 58);
        assertEquals(date, galleryAppliance.getDate());
    }

    @Test
    public void testSearchGallery() throws Exception {
        SUSEStudioRequester<Gallery> requester = new SUSEStudioRequester<Gallery>() {
            public Gallery request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.searchGallery("dadada");
            }
        };
        TestExampleResponder responder = new TestExampleResponder("gallery.xml");
        Gallery result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
    }

    @Test
    public void testGetConfiguration() throws Exception {
        SUSEStudioRequester<Configuration> requester = new SUSEStudioRequester<Configuration>() {
            public Configuration request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getConfiguration(0);
            }
        };
        TestExampleResponder responder = new TestExampleResponder("configuration.xml");
        Configuration result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
        assertEquals(24, result.getId());
        assertEquals("LAMP Server", result.getName());
        assertEquals("This is a LAMP server.\n\nIt provides Linux, Apache, MySQL, and Perl.", result.getDescription());
        assertEquals("0.0.1", result.getVersion());
        assertEquals("oem", result.getType());
        assertEquals("http://susestudio.com", result.getWebsite());

        List<String> tags = result.getTags();
        assertNotNull(tags);
        assertEquals(2, tags.size());
        String[] expectedTags = { "lamp", "server" };
        for (int i = 0; i < expectedTags.length; i++) {
            assertEquals(expectedTags[i], tags.get(i));
        }

        Locale locale = result.getLocale();
        assertNotNull(locale);
        assertEquals("english-uk", locale.getKeyboardLayout());
        assertEquals("en_GB.UTF-8", locale.getLanguage());
        assertEquals("Europe/Berlin", locale.getLocation());

        Network network = result.getNetwork();
        assertNotNull(network);
        assertEquals("manual", network.getType());
        assertEquals("lampserver", network.getHostname());
        assertEquals("192.168.1.100", network.getIp());
        assertEquals("255.255.255.0", network.getNetmask());
        assertEquals("192.168.1.1", network.getRoute());
        List<String> nameServers = network.getNameServers();
        assertNotNull(nameServers);
        assertEquals(2, nameServers.size());
        String[] expectedNameServers = { "192.168.1.1", "192.168.1.2" };
        for (int i = 0; i < expectedNameServers.length; i++) {
            assertEquals(expectedNameServers[i], nameServers.get(i));
        }

        Firewall firewall = result.getFirewall();
        assertNotNull(firewall);
        assertEquals(true, firewall.isEnabled());
        List<String> openPorts = firewall.getOpenPorts();
        assertNotNull(openPorts);
        assertEquals(2, openPorts.size());
        String[] expectedOpenPorts = { "ssh", "http" };
        for (int i = 0; i < expectedOpenPorts.length; i++) {
            assertEquals(expectedOpenPorts[i], openPorts.get(i));
        }

        List<ApplianceUser> applianceUsers = result.getApplianceUsers();
        assertNotNull(applianceUsers);
        assertEquals(3, applianceUsers.size());
        // root
        ApplianceUser user = applianceUsers.get(0);
        assertEquals(0, user.getUid());
        assertEquals("root", user.getName());
        assertEquals("linux", user.getPassword());
        assertEquals("root", user.getGroup());
        assertEquals("/bin/bash", user.getShell());
        assertEquals("/root", user.getHomeDirectory());
        // tux
        user = applianceUsers.get(1);
        assertEquals(1000, user.getUid());
        assertEquals("tux", user.getName());
        assertEquals("linux", user.getPassword());
        assertEquals("users", user.getGroup());
        assertEquals("/bin/bash", user.getShell());
        assertEquals("/home/tux", user.getHomeDirectory());
        // webdev
        user = applianceUsers.get(2);
        assertEquals(1001, user.getUid());
        assertEquals("webdev", user.getName());
        assertEquals("linux1234", user.getPassword());
        assertEquals("users", user.getGroup());
        assertEquals("/bin/bash", user.getShell());
        assertEquals("/home/webdev", user.getHomeDirectory());

        List<String> eulas = result.getEulas();
        assertNotNull(eulas);
        assertEquals(1, eulas.size());
        assertEquals("This is an End User License Agreement.\n", eulas.get(0));

        List<Database> databases = result.getDatabases();
        assertNotNull(databases);
        assertEquals(1, databases.size());
        Database database = databases.get(0);
        assertEquals("pgsql", database.getType());
        List<DatabaseUser> databaseUsers = database.getDatabaseUsers();
        assertNotNull(databaseUsers);
        assertEquals(1, databaseUsers.size());
        DatabaseUser databaseUser = databaseUsers.get(0);
        assertNotNull(databaseUser);
        assertEquals("db_user", databaseUser.getUsername());
        assertEquals("linux", databaseUser.getPassword());
        List<String> userDatabases = databaseUser.getDatabases();
        assertNotNull(userDatabases);
        assertEquals(1, userDatabases.size());
        assertEquals("project_db", userDatabases.get(0));

        List<Autostart> autostarts = result.getAutostarts();
        assertNotNull(autostarts);
        assertEquals(1, autostarts.size());
        Autostart autostart = autostarts.get(0);
        assertNotNull(autostart);
        assertEquals("/usr/bin/someprogram", autostart.getCommand());
        assertEquals("Launch \"someprogram\"", autostart.getDescription());
        assertEquals(true, autostart.isEnabled());
        assertEquals("tux", autostart.getUser());

        Settings settings = result.getSettings();
        assertNotNull(settings);
        assertEquals(512, settings.getMemorySize());
        assertEquals(16, settings.getDiskSize());
        assertEquals(512, settings.getSwapSize());
        assertEquals(false, settings.isPaeEnabled());
        assertEquals(true, settings.isXenHostModeEnabled());
        assertEquals(true, settings.isCdromEnabled());
        assertEquals(false, settings.isWebYaSTEnabled());
        assertEquals(false, settings.isLiveInstallerEnabled());
        assertEquals(true, settings.isPublicClonable());
        assertEquals(3, settings.getRunlevel());
        assertEquals("tux", settings.getAutomaticLogin());

        LVM lvm = result.getLvm();
        assertNotNull(lvm);
        assertEquals(true, lvm.isEnabled());
        assertEquals("systemVG", lvm.getVolumeGroup());
        List<Volume> volumes = lvm.getVolumes();
        assertNotNull(volumes);
        assertEquals(2, volumes.size());
        // volume 1
        Volume volume = volumes.get(0);
        assertEquals(1000, volume.getSize());
        assertEquals("/", volume.getPath());
        // volume 2
        volume = volumes.get(1);
        assertEquals(100000, volume.getSize());
        assertEquals("/home", volume.getPath());

        Scripts scripts = result.getScripts();
        assertNotNull(scripts);
        Script bootScript = scripts.getBootScript();
        Script buildScript = scripts.getBuildScript();
        assertNotNull(buildScript);
        assertEquals(true, buildScript.isEnabled());
        assertEquals(EXPECTED_SCRIPT_1, buildScript.getScript());
        assertNotNull(bootScript);
        assertEquals(true, bootScript.isEnabled());
        assertEquals(EXPECTED_SCRIPT_2, bootScript.getScript());
        Script autoYaSTScript = scripts.getAutoYaSTScript();
        assertNotNull(autoYaSTScript);
        assertEquals(false, autoYaSTScript.isEnabled());
        assertNull(autoYaSTScript.getScript());
    }

    @Test
    public void testSetConfiguration() throws Exception {
        SUSEStudioRequester<Boolean> requester = new SUSEStudioRequester<Boolean>() {
            @Test
            public Boolean request(SUSEStudio suseStudio) throws SUSEStudioException {
                Configuration configuration = TestUtils.parseExampleFile(Configuration.class, "configuration.xml");
                suseStudio.setConfiguration(0, configuration);
                return true;
            }
        };
        TestExampleResponder responder = new TestExampleResponder("success.xml");
        Boolean result = new HttpServerMock().getResult(requester, responder);

        assertTrue(result);
    }

    @Test
    public void testGetTemplateSet() throws Exception {
        SUSEStudioRequester<TemplateSet> requester = new SUSEStudioRequester<TemplateSet>() {
            public TemplateSet request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getTemplateSet("default");
            }
        };
        TestExampleResponder responder = new TestExampleResponder("template_set.xml");
        TemplateSet result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
    }

    @Test
    public void testGetTemplateSets() throws Exception {
        SUSEStudioRequester<List<TemplateSet>> requester = new SUSEStudioRequester<List<TemplateSet>>() {
            public List<TemplateSet> request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getTemplateSets();
            }
        };
        TestExampleResponder responder = new TestExampleResponder("template_sets.xml");
        List<TemplateSet> result = new HttpServerMock().getResult(requester, responder);

        String[] names = { "openSUSE 11.1, Just enough OS (JeOS)",
                "openSUSE 11.1, Server",
                "openSUSE 11.1, Minimal X",
                "openSUSE 11.1, KDE 3 desktop",
                "openSUSE 11.1, KDE 4 desktop",
                "openSUSE 11.1, GNOME desktop" };
        String[] descriptions = { "Tiny, minimalistic appliances",
                "A text-only base",
                "Graphical system + IceWM",
                "Base system + KDE 3",
                "Base system + KDE 4",
                "Base system + GNOME" };
        String basesystem = "11.1";

        assertNotNull(result);
        assertEquals(1, result.size());
        TemplateSet templateSet = result.get(0);
        assertEquals("default", templateSet.getName());
        assertEquals("SUSE default templates", templateSet.getDescription());
        assertEquals(6, templateSet.getTemplates().size());
        for (int idx = 0; idx < templateSet.getTemplates().size(); idx++) {
            Template template = templateSet.getTemplates().get(idx);
            assertEquals(idx + 1, template.getApplianceId());
            assertEquals(names[idx], template.getName());
            assertEquals(descriptions[idx], template.getDescription());
            assertEquals(basesystem, template.getBasesystem());
        }
    }

    @Test
    public void testGetTestdrives() throws Exception {
        SUSEStudioRequester<List<Testdrive>> requester = new SUSEStudioRequester<List<Testdrive>>() {
            public List<Testdrive> request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getTestdrives();
            }
        };
        TestExampleResponder responder = new TestExampleResponder("testdrives.xml");
        List<Testdrive> result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
        Testdrive testdrive = result.get(0);
        assertEquals("4", testdrive.getId());
        assertEquals("running", testdrive.getState());
        assertEquals("22", testdrive.getBuildId());
    }

    @Test
    public void testStartTestdrive() throws Exception {
        SUSEStudioRequester<Testdrive> requester = new SUSEStudioRequester<Testdrive>() {
            public Testdrive request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.startTestdrive(0);
            }
        };
        TestExampleResponder responder = new TestExampleResponder("testdrive.xml");
        Testdrive result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
        assertEquals("1234", result.getId());
        assertEquals("new", result.getState());
        assertEquals("12345", result.getBuildId());
        assertEquals("http://node52.susestudio.com/testdrive/testdrive/start/11/22/abcdefgh1234567890", result.getUrl());

        VNCServer vncServer = result.getVNCServer();
        assertEquals("node52.susestudio.com", vncServer.getHost());
        assertEquals("5902", vncServer.getPort());
        assertEquals("1234567890", vncServer.getPassword());
    }

    @Test
    public void testScheduleBuild() throws Exception {
        SUSEStudioRequester<ScheduleBuildResult> requester = new SUSEStudioRequester<ScheduleBuildResult>() {
            public ScheduleBuildResult request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.scheduleBuild(0, ImageType.iso);
            }
        };
        TestExampleResponder responder = new TestExampleResponder("build.xml");
        ScheduleBuildResult result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
        assertEquals(result.getId(), 28);
    }

    @Test
    public void testGetRepositories() throws Exception {
        SUSEStudioRequester<List<Repository>> requester = new SUSEStudioRequester<List<Repository>>() {
            public List<Repository> request(SUSEStudio suseStudio) throws SUSEStudioException {
                return suseStudio.getRepositories(null, null);
            }
        };
        TestExampleResponder responder = new TestExampleResponder("repositories.xml");
        List<Repository> result = new HttpServerMock().getResult(requester, responder);

        assertNotNull(result);
        assertEquals(1, result.size());
        Repository repository = result.get(0);
        assertEquals(7, repository.getId());
        assertEquals("Moblin Base", repository.getName());
        assertEquals("11.1", repository.getBaseSystem());
        assertEquals("http://download.opensuse.org/repositories/Moblin:/Base/openSUSE_11.1", repository.getBaseUrl());
        assertEquals(null, repository.getRepoTag());
        List<Match> m = repository.getMatches();
        assertNotNull(m);
        assertEquals(2, m.size());
        assertEquals(m.get(0).getKey(), "repository_name");
        assertEquals(m.get(0).getValue(), "moblin base");
        assertEquals(m.get(1).getKey(), "repository_base_url");
        assertEquals(m.get(1).getValue(), "http://download.opensuse.org/repositories/moblin:/base/opensuse_11.1");
    }
}
