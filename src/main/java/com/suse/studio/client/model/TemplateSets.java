package com.suse.studio.client.model;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "template_sets")
public class TemplateSets {

    @Attribute(required = false)
    private String type;

    @ElementList(required = false, inline = true, name = "template_set")
    private List<TemplateSet> templateSets;

    public List<TemplateSet> getTemplateSets() {
        if (templateSets == null) {
            templateSets = new ArrayList<TemplateSet>();
        }
        return templateSets;
    }

    public String getType() {
        return type;
    }
}
