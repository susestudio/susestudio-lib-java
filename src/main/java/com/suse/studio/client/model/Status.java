package com.suse.studio.client.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Status {

    @Element
    private String state;

    @ElementList
    private List<Issue> issues;

    public String getState() {
        return state;
    }

    public List<Issue> getIssues() {
        return issues;
    }
}
