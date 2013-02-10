package com.suse.studio.client.model.configuration;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Volume {

    @Element(required = false)
    private int size;

    @Element
    private String path;

    public int getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
