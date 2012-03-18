package com.suse.studio.client.model;

import org.simpleframework.xml.Element;

public class Solution {

    @Element
    private String type;

    @Element(name = "package")
    private String pkg;

    public String getType() {
        return type;
    }

    public String getPkg() {
        return pkg;
    }
}
