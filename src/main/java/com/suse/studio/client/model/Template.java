package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "template")
public class Template {

    @Element(name = "appliance_id")
    private long applianceId;

    @Element
    private String name;

    @Element
    private String description;

    @Element
    private String basesystem;

    public long getApplianceId() {
        return applianceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBasesystem() {
        return basesystem;
    }
}
