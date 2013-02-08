package com.suse.studio.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.suse.studio.client.model.Appliance;
import com.suse.studio.client.model.Appliances;
import com.suse.studio.client.model.Build;
import com.suse.studio.client.model.DiskQuota;
import com.suse.studio.client.model.Gallery;
import com.suse.studio.client.model.GalleryAppliance;
import com.suse.studio.client.model.GalleryAppliances;
import com.suse.studio.client.model.Issue;
import com.suse.studio.client.model.Parent;
import com.suse.studio.client.model.Solution;
import com.suse.studio.client.model.Status;
import com.suse.studio.client.model.Template;
import com.suse.studio.client.model.TemplateSet;
import com.suse.studio.client.model.TemplateSets;
import com.suse.studio.client.model.Testdrive;
import com.suse.studio.client.model.Testdrives;
import com.suse.studio.client.model.User;
import com.suse.studio.client.model.VNCServer;
import com.suse.studio.client.test.util.TestUtils;

/**
 * Test the data model by parsing the example files.
 */
public class ExamplesTest {

    @Test
	public void testAccount() {
        User user = TestUtils.parseExampleFile(User.class, "account.xml");
        assertNotNull(user);
        assertEquals("uexample", user.getUsername());
        assertEquals("User Example", user.getDisplayName());
        assertEquals("user@example.com", user.getEmail());
        Date created = TestUtils.getDate(2009, 4, 14, 14, 51, 7);
        assertEquals(0, created.compareTo(user.getCreatedAt()));
        assertNotNull(user.getDiskQuota());
        DiskQuota diskQuota = user.getDiskQuota();
        assertEquals("15GB", diskQuota.getAvailable());
        assertEquals("4%", diskQuota.getUsed());        
        List<String> openIdUrls = user.getOpenIdUrls();
        assertNotNull(openIdUrls);
        assertEquals(1, openIdUrls.size());
        String openIdUrl = openIdUrls.get(0);
        assertEquals("http://user.example.com/", openIdUrl);
    }

    @Test
    public void testAppliances() {
        Appliances appliances = TestUtils.parseExampleFile(Appliances.class,
                "appliances.xml");
        assertNotNull(appliances);
        List<Appliance> list = appliances.getAppliances();
        assertEquals(2, list.size());
    }

    @Test
    public void testAppliance() {
        Appliance appliance = TestUtils.parseExampleFile(Appliance.class,
                "appliance.xml");
        assertNotNull(appliance);
        assertEquals(24, appliance.getId());
        assertEquals("Cornelius' JeOS", appliance.getName());
        assertNull(appliance.getArch());
        assertNull(appliance.getType());
        Date lastEdited = TestUtils.getDate(2009, 3, 24, 12, 9, 42);
        assertEquals(lastEdited, appliance.getLastEdited());
        assertNull(appliance.getEstimatedRawSize());
        assertNull(appliance.getEstimatedCompressedSize());
        assertEquals("http://susestudio.com/appliance/edit/24",
                appliance.getEditUrl());
        assertEquals("http://susestudio.com/api/v1/user/appliance_icon/1234",
                appliance.getIconUrl());
        assertEquals("11.1", appliance.getBasesystem());
        assertNull(appliance.getUuid());
        Parent parent = appliance.getParent();
        assertEquals(1, parent.getId());
        assertEquals("openSUSE 11.1, Just enough OS (JeOS)", parent.getName());
        List<Build> builds = appliance.getBuilds();
        assertNotNull(builds);
        assertEquals(1, builds.size());
        Build build = builds.get(0);
        assertEquals(28, build.getId());
        assertEquals("0.0.1", build.getVersion());
        assertEquals("oem", build.getImageType());
        assertEquals("238", build.getImageSize());
        assertEquals("87", build.getCompressedImageSize());
        assertEquals(
        		"http://susestudio.com/download/bf1a0f08884ebac13f30b0fc62dfc44a/Cornelius_JeOS.x86_64-0.0.1.oem.tar.gz",
        		build.getDownloadUrl());
    }

    @Test
    public void testApplianceStatus() {
        Status status = TestUtils.parseExampleFile(Status.class,
                "appliance_status.xml");
        assertNotNull(status);
        assertEquals("error", status.getState());
        List<Issue> issues = status.getIssues();
        Issue issue = issues.get(0);
        assertEquals("error", issue.getType());
        assertEquals("You must include a kernel package in your appliance.",
                issue.getText());
        Solution solution = issue.getSolution();
        assertEquals("install", solution.getType());
        assertEquals("kernel-default", solution.getPkg());
    }

    @Test
    public void testTemplateSets() {
        String[] names = { "openSUSE 11.1, Just enough OS (JeOS)",
                "openSUSE 11.1, Server", "openSUSE 11.1, Minimal X",
                "openSUSE 11.1, KDE 3 desktop", "openSUSE 11.1, KDE 4 desktop",
                "openSUSE 11.1, GNOME desktop" };
        String[] descriptions = { "Tiny, minimalistic appliances",
                "A text-only base", "Graphical system + IceWM",
                "Base system + KDE 3", "Base system + KDE 4",
                "Base system + GNOME" };
        String basesystem = "11.1";
        TemplateSets templateSets = TestUtils.parseExampleFile(
                TemplateSets.class, "template_sets.xml");
        assertNotNull(templateSets);
        List<TemplateSet> templateSetsList = templateSets.getTemplateSets();
        assertEquals(1, templateSetsList.size());
        TemplateSet templateSet = templateSetsList.get(0);
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
    public void testTestdrives() {
        Testdrives testdrives = TestUtils.parseExampleFile(Testdrives.class,
                "testdrives.xml");
        assertNotNull(testdrives);
        List<Testdrive> tdList = testdrives.getTestdrives();
        Testdrive testdrive = tdList.get(0);
        assertEquals("4", testdrive.getId());
        assertEquals("running", testdrive.getState());
        assertEquals("22", testdrive.getBuildId());
    }

    @Test
    public void testTestdrive() {
        Testdrive testdrive = TestUtils.parseExampleFile(Testdrive.class,
                "testdrive.xml");
        assertNotNull(testdrive);
        assertEquals("1234", testdrive.getId());
        assertEquals("new", testdrive.getState());
        assertEquals("12345", testdrive.getBuildId());
        assertEquals("http://node52.susestudio.com/testdrive/testdrive/start/11/22/abcdefgh1234567890",
                testdrive.getUrl());

        VNCServer vncServer = testdrive.getVNCServer();
        assertEquals("node52.susestudio.com", vncServer.getHost());
        assertEquals("5902", vncServer.getPort());
        assertEquals("1234567890", vncServer.getPassword());
    }
    
    @Test
    public void testGallery() {
    	Gallery gallery = TestUtils.parseExampleFile(Gallery.class, "gallery.xml");
        GalleryAppliances galleryAppliances = gallery.getAppliances();        
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
}
