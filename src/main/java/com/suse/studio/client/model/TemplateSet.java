package com.suse.studio.client.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "template_set")
public class TemplateSet {

    @Element(required = false)
    private String name;

    @Element(required = false)
    private String description;

    @ElementList(required = false, inline = true, name = "template")
    private List<Template> templates;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Template> getTemplates() {
        return templates;
    }
}
