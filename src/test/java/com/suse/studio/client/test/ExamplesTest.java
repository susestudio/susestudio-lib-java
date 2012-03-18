package com.suse.studio.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.suse.studio.client.model.Appliance;
import com.suse.studio.client.model.Appliances;
import com.suse.studio.client.model.DiskQuota;
import com.suse.studio.client.model.Status;
import com.suse.studio.client.model.User;
import com.suse.studio.client.util.ParserUtils;

/**
 * Test the data model by parsing the example files.
 */
public class ExamplesTest {

    @Test
    public void testGetUser() {
        User user = ParserUtils.parseBodyStream(User.class,
                getXMLStream("account.xml"));
        assertNotNull(user);
        assertEquals("uexample", user.getUsername());
        assertEquals("User Example", user.getDisplayName());
        assertEquals("user@example.com", user.getEmail());
//        Calendar cal = Calendar.getInstance();
//        cal.set(2009, 4, 14, 16, 51, 7);
//        assertEquals(0, cal.getTime().compareTo(user.getCreatedAt()));
        assertNotNull(user.getDiskQuota());
        DiskQuota diskQuota = user.getDiskQuota();
        assertEquals("15GB", diskQuota.getAvailable());
        assertEquals("4%", diskQuota.getUsed());
    }

    @Test
    public void testGetAppliances() {
        Appliances appliances = ParserUtils.parseBodyStream(
                Appliances.class, getXMLStream("appliances.xml"));
        assertNotNull(appliances);
        List<Appliance> list = appliances.getAppliances();
        assertEquals(2, list.size());
    }

    @Test
    public void testGetAppliance() {
        Appliance appliance = ParserUtils.parseBodyStream(
                Appliance.class, getXMLStream("appliance.xml"));
        assertNotNull(appliance);
        assertEquals(24, appliance.getId());
        assertEquals("Cornelius' JeOS", appliance.getName());
    }

    @Test
    public void testGetApplianceStatus() {
        Status status = ParserUtils.parseBodyStream(
                Status.class, getXMLStream("appliance_status.xml"));
        assertNotNull(status);
        assertEquals("error", status.getState());
    }

    private InputStream getXMLStream(String filename) {
        return ExamplesTest.class.getResourceAsStream(filename);
    }
}
