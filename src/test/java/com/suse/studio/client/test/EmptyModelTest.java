package com.suse.studio.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.suse.studio.client.model.Appliance;
import com.suse.studio.client.model.Appliances;
import com.suse.studio.client.model.Build;
import com.suse.studio.client.model.Gallery;
import com.suse.studio.client.model.GalleryAppliance;
import com.suse.studio.client.model.GalleryAppliances;
import com.suse.studio.client.model.Repositories;
import com.suse.studio.client.model.Repository;
import com.suse.studio.client.model.Template;
import com.suse.studio.client.model.TemplateSet;
import com.suse.studio.client.model.TemplateSets;
import com.suse.studio.client.model.Testdrive;
import com.suse.studio.client.model.Testdrives;
import com.suse.studio.client.test.util.TestUtils;

/**
 * Test the data model by parsing files with specific parts intentionally left empty.
 */
public class EmptyModelTest {

    @Test
    public void testEmptyAppliances() {
        Appliances appliances = TestUtils.parseExampleFile(Appliances.class, "empty_appliances.xml");
        assertNotNull(appliances);
        List<Appliance> list = appliances.getAppliances();
        assertEquals(0, list.size());
    }

    @Test
    public void testEmptyBuildAppliance() {
        Appliance appliance = TestUtils.parseExampleFile(Appliance.class, "empty_build_appliance.xml");
        List<Build> builds = appliance.getBuilds();
        assertNotNull(builds);
        assertEquals(0, builds.size());
    }

    @Test
    public void testEmptyTemplateSets() {
        TemplateSets templateSets = TestUtils.parseExampleFile(TemplateSets.class, "empty_template_sets.xml");
        assertNotNull(templateSets);
        List<TemplateSet> templateSetsList = templateSets.getTemplateSets();
        assertEquals(0, templateSetsList.size());
    }

    @Test
    public void testEmptyTemplateSet() {
        TemplateSet templateSet = TestUtils.parseExampleFile(TemplateSet.class, "empty_template_set.xml");
        assertNotNull(templateSet);
        List<Template> templateSetsList = templateSet.getTemplates();
        assertEquals(0, templateSetsList.size());
    }

    @Test
    public void testEmptyTestdrives() {
        Testdrives testdrives = TestUtils.parseExampleFile(Testdrives.class, "empty_testdrives.xml");
        assertNotNull(testdrives);
        List<Testdrive> list = testdrives.getTestdrives();
        assertEquals(0, list.size());
    }

    @Test
    public void testEmptyGallery() {
        Gallery gallery = TestUtils.parseExampleFile(Gallery.class, "empty_gallery.xml");
        GalleryAppliances galleryAppliances = gallery.getAppliances();
        assertNotNull(galleryAppliances);
        List<GalleryAppliance> list = galleryAppliances.getAppliances();
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test
    public void testEmptyRepositories() {
        Repositories repositories = TestUtils.parseExampleFile(Repositories.class, "empty_repositories.xml");
        assertNotNull(repositories);
        List<Repository> list = repositories.getRepositories();
        assertEquals(0, list.size());
    }
}
