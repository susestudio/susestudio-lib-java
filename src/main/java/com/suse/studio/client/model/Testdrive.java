package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Testdrive {

    @Element
    private String id;

    @Element
    private String state;

    @Element(name = "build_id")
    private String buildId;

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getBuildId() {
        return buildId;
    }
}
