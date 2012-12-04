package com.suse.studio.client.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class VNCServer {

    @Element
    private String host;

    @Element
    private String port;

    @Element
    private String password;

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }
}
