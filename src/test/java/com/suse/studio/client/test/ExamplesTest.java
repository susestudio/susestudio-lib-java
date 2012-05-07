package com.suse.studio.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.suse.studio.client.model.Appliance;
import com.suse.studio.client.model.Appliances;
import com.suse.studio.client.model.DiskQuota;
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
        assertEquals("http://susestudio.com/appliance/edit/24",
                appliance.getEditUrl());
        assertEquals("http://susestudio.com/api/v1/user/appliance_icon/1234",
                appliance.getIconUrl());
        assertEquals("11.1", appliance.getBasesystem());
        Parent parent = appliance.getParent();
        assertEquals(1, parent.getId());
        assertEquals("openSUSE 11.1, Just enough OS (JeOS)", parent.getName());
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
}
