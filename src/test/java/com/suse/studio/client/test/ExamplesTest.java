package com.suse.studio.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.suse.studio.client.model.Appliance;
import com.suse.studio.client.model.Appliances;
import com.suse.studio.client.model.DiskQuota;
import com.suse.studio.client.model.Issue;
import com.suse.studio.client.model.Parent;
import com.suse.studio.client.model.TestDrives;
import com.suse.studio.client.model.TestDrive;
import com.suse.studio.client.model.Solution;
import com.suse.studio.client.model.Status;
import com.suse.studio.client.model.User;
import com.suse.studio.client.test.util.TestUtils;
import com.suse.studio.client.util.ParserUtils;

/**
 * Test the data model by parsing the example files.
 */
public class ExamplesTest {

    @Test
    public void testAccount() {
        User user = ParserUtils.parseBodyStream(User.class,
                TestUtils.getInputStream("account.xml"));
        assertNotNull(user);
        assertEquals("uexample", user.getUsername());
        assertEquals("User Example", user.getDisplayName());
        assertEquals("user@example.com", user.getEmail());
        // TODO: Find a way to conveniently compare dates
        // Calendar cal = Calendar.getInstance();
        // cal.set(2009, 4, 14, 16, 51, 7);
        // assertEquals(0, cal.getTime().compareTo(user.getCreatedAt()));
        assertNotNull(user.getDiskQuota());
        DiskQuota diskQuota = user.getDiskQuota();
        assertEquals("15GB", diskQuota.getAvailable());
        assertEquals("4%", diskQuota.getUsed());
    }
    
//!----start

	@Test
    public void testTestDrives() {
        TestDrives testDrives = ParserUtils.parseBodyStream(TestDrives.class,
                TestUtils.getInputStream("testdrives.xml"));
        TestDrive testDrive =testDrives.getTestDrive();
        assertEquals("running", testDrive.getState());
        assertEquals("4", testDrive.getId());
        assertEquals("22", testDrive.getBuildId());
        
    }
    
//!------ends

    @Test
    public void testAppliances() {
        Appliances appliances = ParserUtils.parseBodyStream(Appliances.class,
                TestUtils.getInputStream("appliances.xml"));
        assertNotNull(appliances);
        List<Appliance> list = appliances.getAppliances();
        assertEquals(2, list.size());
    }

    @Test
    public void testAppliance() {
        Appliance appliance = ParserUtils.parseBodyStream(Appliance.class,
                TestUtils.getInputStream("appliance.xml"));
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
        Status status = ParserUtils.parseBodyStream(Status.class,
                TestUtils.getInputStream("appliance_status.xml"));
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
}
