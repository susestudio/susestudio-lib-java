package com.suse.studio.client.model;

import org.simpleframework.xml.Element;

public class Issue {

    @Element
    String type;

    @Element
    String text;

    @Element(required = false)
    Solution solution;

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public Solution getSolution() {
        return solution;
    }
}
