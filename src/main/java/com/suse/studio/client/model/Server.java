package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Server {

    @Element(required = false)
    private VNCServer vnc;

    public VNCServer getVNC() {
        return vnc;
    }
}
