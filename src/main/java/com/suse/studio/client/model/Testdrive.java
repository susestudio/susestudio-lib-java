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

    @Element(required = false)
    private String url;

    @Element(required = false)
    private Server server;

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getBuildId() {
        return buildId;
    }

    public String getUrl() {
        return url;
    }

    private Server getServer() {
        return server;
    }

    public VNCServer getVNCServer() {
        return getServer().getVNC();
    }
}
